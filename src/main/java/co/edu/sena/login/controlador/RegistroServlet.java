package co.edu.sena.login.controlador;

import co.edu.sena.login.dao.UsuarioDAO;
import co.edu.sena.login.util.CsrfUtil;
import co.edu.sena.login.util.ValidadorUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/** Controlador MVC para el registro público con rol USUARIO por defecto. */
@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegistroServlet.class.getName());
    private final UsuarioDAO dao = new UsuarioDAO();

    /** GET: presenta el formulario de registro. */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CsrfUtil.token(req);
        req.getRequestDispatcher("/WEB-INF/vistas/registro.jsp").forward(req, resp);
    }

    /** POST: valida datos, hace hash BCrypt e inserta un usuario en TiDB. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (!CsrfUtil.valido(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Formulario caducado; actualiza la página.");
            return;
        }
        String nombre = normalizar(req.getParameter("nombre"));
        String usuario = normalizar(req.getParameter("usuario"));
        String correo = normalizar(req.getParameter("correo"));
        String clave = req.getParameter("contrasena");
        String confirmar = req.getParameter("confirmarContrasena");
        String error = ValidadorUsuario.validarRegistro(nombre, usuario, correo, clave, confirmar);
        if (error != null) {
            mostrarError(req, resp, error);
            return;
        }
        try {
            String hash = BCrypt.hashpw(clave, BCrypt.gensalt(12));
            dao.registrar(nombre, usuario, correo, hash);
            CsrfUtil.rotar(req);
            resp.sendRedirect(req.getContextPath() + "/login?registrado=1");
        } catch (SQLException ex) {
            // 23000 es el SQLSTATE estándar de violación de restricciones (UNIQUE).
            if ("23000".equals(ex.getSQLState())) {
                mostrarError(req, resp, "El usuario o correo ya está registrado.");
            } else {
                LOGGER.log(Level.SEVERE, "Error al registrar usuario", ex);
                mostrarError(req, resp, "No se pudo crear la cuenta. Comprueba la conexión e inténtalo de nuevo.");
            }
        }
    }

    private String normalizar(String valor) {
        return valor == null ? null : valor.trim();
    }

    private void mostrarError(HttpServletRequest req, HttpServletResponse resp, String mensaje)
            throws ServletException, IOException {
        req.setAttribute("error", mensaje);
        req.getRequestDispatcher("/WEB-INF/vistas/registro.jsp").forward(req, resp);
    }
}

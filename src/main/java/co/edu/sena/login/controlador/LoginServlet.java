package co.edu.sena.login.controlador;

import co.edu.sena.login.dao.UsuarioDAO;
import co.edu.sena.login.modelo.Usuario;
import co.edu.sena.login.util.CsrfUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/** Controlador MVC que presenta el formulario y valida credenciales del usuario. */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
    private final UsuarioDAO dao = new UsuarioDAO();

    /** GET: muestra la vista JSP o redirige a un usuario con sesión activa. */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession sesion = req.getSession(false);
        if (sesion != null && sesion.getAttribute("usuarioAutenticado") instanceof Usuario) {
            resp.sendRedirect(req.getContextPath() + "/panel");
            return;
        }
        CsrfUtil.token(req);
        req.getRequestDispatcher("/WEB-INF/vistas/login.jsp").forward(req, resp);
    }

    /** POST: valida CSRF, verifica BCrypt e inicia la sesión según el rol. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (!CsrfUtil.valido(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Formulario caducado; actualiza la página.");
            return;
        }
        String usuario = req.getParameter("usuario");
        String contrasena = req.getParameter("contrasena");
        if (usuario == null || usuario.isBlank() || usuario.length() > 30
                || contrasena == null || contrasena.isEmpty()) {
            mostrarError(req, resp, "Introduce el usuario y la contraseña.");
            return;
        }
        try {
            UsuarioDAO.RegistroAutenticacion registro = dao.buscarPorUsuario(usuario.trim());
            if (registro == null || !BCrypt.checkpw(contrasena, registro.hashContrasena())) {
                mostrarError(req, resp, "Usuario o contraseña incorrectos.");
                return;
            }
            // Rota el ID para mitigar la fijación de sesión y guarda solo el perfil público.
            HttpSession sesion = req.getSession(true);
            req.changeSessionId();
            sesion.setAttribute("usuarioAutenticado", registro.usuario());
            sesion.setMaxInactiveInterval(30 * 60);
            CsrfUtil.rotar(req);
            resp.sendRedirect(req.getContextPath() + "/panel");
        } catch (SQLException | IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, "Error al autenticar", ex);
            mostrarError(req, resp, "No se pudo verificar el acceso. Inténtalo nuevamente.");
        }
    }

    private void mostrarError(HttpServletRequest req, HttpServletResponse resp, String mensaje)
            throws ServletException, IOException {
        req.setAttribute("error", mensaje);
        req.getRequestDispatcher("/WEB-INF/vistas/login.jsp").forward(req, resp);
    }
}

package co.edu.sena.login.controlador;

import co.edu.sena.login.util.CsrfUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/** Cierre de sesión: invalida la sesión HTTP para impedir reutilizarla. */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    /** POST protegido con CSRF; nunca cierra sesión mediante GET. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        if (!CsrfUtil.valido(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        HttpSession sesion = req.getSession(false);
        if (sesion != null) sesion.invalidate();
        resp.sendRedirect(req.getContextPath() + "/login?salida=1");
    }
}

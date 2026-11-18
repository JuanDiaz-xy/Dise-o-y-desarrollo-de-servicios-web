package co.edu.sena.login.controlador;

import co.edu.sena.login.util.CsrfUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Vista protegida de usuario autenticado. AuthFilter controla el acceso. */
@WebServlet("/panel")
public class PanelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CsrfUtil.token(req);
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        req.getRequestDispatcher("/WEB-INF/vistas/panel.jsp").forward(req, resp);
    }
}

package co.edu.sena.login.controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Ruta con autorización por rol: solo accesible para ADMIN desde AuthFilter. */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        req.getRequestDispatcher("/WEB-INF/vistas/admin.jsp").forward(req, resp);
    }
}

package co.edu.sena.login.filtro;

import co.edu.sena.login.modelo.Usuario;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/** Impide el acceso anónimo y restringe /admin al rol ADMIN. */
@WebFilter(urlPatterns = {"/panel", "/logout", "/admin"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession sesion = req.getSession(false);
        if (sesion == null || !(sesion.getAttribute("usuarioAutenticado") instanceof Usuario)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        // Solo el rol ADMIN puede abrir /admin, incluso si conoce su URL directa.
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioAutenticado");
        if ("/admin".equals(req.getServletPath()) && !"ADMIN".equals(usuario.getRol())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso reservado al administrador.");
            return;
        }
        chain.doFilter(request, response);
    }
}

package co.edu.sena.login.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/** Token aleatorio asociado a la sesión para proteger formularios POST. */
public final class CsrfUtil {
    private static final String CLAVE = "csrfToken";
    private static final SecureRandom RANDOM = new SecureRandom();
    private CsrfUtil() { }

    /** Obtiene o crea un token CSRF para el formulario. */
    public static String token(HttpServletRequest request) {
        HttpSession sesion = request.getSession(true);
        String valor = (String) sesion.getAttribute(CLAVE);
        if (valor == null) {
            valor = crear();
            sesion.setAttribute(CLAVE, valor);
        }
        return valor;
    }

    /** Comprueba el token enviado sin crear una nueva sesión. */
    public static boolean valido(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion == null) return false;
        Object guardado = sesion.getAttribute(CLAVE);
        String recibido = request.getParameter("csrfToken");
        if (!(guardado instanceof String) || recibido == null) return false;
        return MessageDigest.isEqual(((String) guardado).getBytes(StandardCharsets.UTF_8),
                recibido.getBytes(StandardCharsets.UTF_8));
    }

    /** Genera un token nuevo tras cambios de autenticación. */
    public static void rotar(HttpServletRequest request) {
        request.getSession(true).setAttribute(CLAVE, crear());
    }

    private static String crear() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}

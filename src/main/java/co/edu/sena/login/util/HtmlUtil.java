package co.edu.sena.login.util;

/** Codifica caracteres especiales antes de mostrarlos dentro del HTML. */
public final class HtmlUtil {
    private HtmlUtil() { }

    /** Evita que nombres de usuarios o mensajes insertados terminen siendo HTML activo. */
    public static String escape(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}

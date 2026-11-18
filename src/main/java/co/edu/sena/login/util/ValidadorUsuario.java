package co.edu.sena.login.util;

import java.util.regex.Pattern;

/** Reglas del formulario, siempre verificadas del lado del servidor. */
public final class ValidadorUsuario {
    private static final Pattern USUARIO = Pattern.compile("[A-Za-z0-9._-]{4,30}");
    private static final Pattern CORREO = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private ValidadorUsuario() { }

    /** Devuelve null si los datos son válidos; si no, un mensaje seguro para el usuario. */
    public static String validarRegistro(String nombre, String usuario,
            String correo, String clave, String confirmar) {
        if (nombre == null || nombre.isBlank() || nombre.length() > 100) {
            return "Escribe un nombre de máximo 100 caracteres.";
        }
        if (usuario == null || !USUARIO.matcher(usuario).matches()) {
            return "El usuario debe tener entre 4 y 30 caracteres: letras, números, punto, guion o guion bajo.";
        }
        if (correo == null || correo.length() > 150 || !CORREO.matcher(correo).matches()) {
            return "Introduce un correo electrónico válido.";
        }
        if (clave == null || clave.length() < 10 || clave.length() > 72) {
            return "La contraseña debe tener entre 10 y 72 caracteres.";
        }
        if (!clave.equals(confirmar)) {
            return "Las contraseñas no coinciden.";
        }
        return null;
    }
}

package co.edu.sena.login.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Pruebas unitarias de validación, sin depender de TiDB ni de Render. */
class ValidadorUsuarioTest {
    @Test void datosCorrectos() {
        assertNull(ValidadorUsuario.validarRegistro("Aprendiz Demo", "aprendiz_demo", "demo@example.com", "ClaveSegura2026!", "ClaveSegura2026!"));
    }
    @Test void clavesDiferentes() {
        assertNotNull(ValidadorUsuario.validarRegistro("Aprendiz Demo", "aprendiz_demo", "demo@example.com", "ClaveSegura2026!", "OtraClave2026!"));
    }
    @Test void usuarioInvalido() {
        assertNotNull(ValidadorUsuario.validarRegistro("Aprendiz Demo", "a", "demo@example.com", "ClaveSegura2026!", "ClaveSegura2026!"));
    }
    @Test void contrasenaCorta() {
        assertNotNull(ValidadorUsuario.validarRegistro("Aprendiz Demo", "aprendiz_demo", "demo@example.com", "corta", "corta"));
    }
}

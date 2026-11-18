package co.edu.sena.login.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Comprueba la codificación de texto controlado por el usuario. */
class HtmlUtilTest {
    @Test void eliminaInterpretacionHtml() {
        assertEquals("&lt;script&gt;&amp;&quot;&#39;", HtmlUtil.escape("<script>&\"'"));
    }
    @Test void gestionaNulos() {
        assertEquals("", HtmlUtil.escape(null));
    }
}

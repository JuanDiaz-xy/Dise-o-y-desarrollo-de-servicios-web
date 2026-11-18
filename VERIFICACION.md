# Verificación realizada al preparar el proyecto

- Se revisó la estructura de **26+ archivos** del proyecto y el `pom.xml` se analizó como XML válido.
- Todas las clases Java de producción y los archivos de prueba se compilaron con `javac --release 17` usando **stubs temporales** para las APIs externas (Servlet, BCrypt, JUnit), únicamente para verificar firmas y sintaxis.
- Se ejecutaron **4 comprobaciones locales** de `ValidadorUsuario` y `HtmlUtil`: ruta correcta, usuario inválido, contraseñas distintas y escape HTML.
- Se verificó que la instrucción `CMD` del Dockerfile es JSON válido.

**Pendiente:** No fue posible ejecutar `mvn verify`, crear la imagen Docker, desplegar en Render ni probar la conexión a TiDB en este entorno sin acceso a dependencias externas. Por tanto, aún no está verificada la aplicación extremo a extremo. El despliegue y las capturas deben realizarse con la cuenta del aprendiz.

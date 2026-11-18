# Ruta rápida de entrega · GA7-2201096-AA5-EV03

**No instales XAMPP, ni MySQL Server, ni Tomcat local para empezar.**

1. **TiDB Cloud:** crea una instancia Starter MySQL-compatible y ejecuta `sql/schema.sql` en el SQL Editor. MySQL Workbench es opcional.
2. **GitHub:** crea repositorio público y sube el contenido descomprimido, dejando `Dockerfile` y `pom.xml` en la raíz.
3. **Render:** New → Web Service → repositorio de GitHub → runtime **Docker**. Configura las variables `DB_URL`, `DB_USER` y `DB_PASSWORD` con los datos privados de **Connect** en TiDB (TLS). Health check: `/health`.
4. **Prueba:** abre `https://TU-APP.onrender.com/registro`, crea una cuenta de prueba, inicia sesión, prueba el panel y cierra sesión. Si falla revisa los registros de Render.
5. **Capturas:** guarda pantallazos reales de Login, Registro, Panel, y código (`Usuario`, `UsuarioDAO`, `ConexionDAO`, `LoginServlet`, `RegistroServlet`, `LogoutServlet`, JSP, `Dockerfile`).
6. **PDF:** completa portada, introducción, capturas, usuario/clave solo **de prueba**, GitHub público y URL real de Render. **Entrega un solo PDF** antes del 19/11/2026, según hora de plataforma.

**NetBeans:** instálalo únicamente para cumplir con las capturas del IDE, abriendo este proyecto Maven. JDK 17 para compilar localmente. La compilación y el servidor en Render vienen dentro del Dockerfile.

Más detalles, pruebas y referencias: `README.md`.

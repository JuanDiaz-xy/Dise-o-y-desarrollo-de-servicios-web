# Diseño y desarrollo de servicios web

**Evidencia:** GA7-2201096-AA5-EV03 · **Stack:** Java 17, Maven, JSP, Jakarta Servlets 6, Apache Tomcat 10.1, TiDB Cloud, MySQL Connector/J, Docker y Render.

Proyecto demostrativo de inicio de sesión y registro de usuarios con arquitectura MVC. Incluye control de sesiones, restricción de acceso por rol, validación del formulario, prevención de SQL injection con `PreparedStatement`, protección CSRF y hash BCrypt. **No trae usuarios creados ni contraseñas reales.**

## 1. Configurar base de datos TiDB Cloud

1. Crea una instancia/cluster de TiDB Cloud en https://tidbcloud.com/ si aún no la tienes.
2. Abre la opción **Connect** y toma el **host**, **puerto**, **usuario**, **contraseña** y **nombre de base de datos**. Usa conexión pública con TLS conforme a tu instancia.
3. En el **SQL Editor** de TiDB (más directo que Workbench), elige la base de datos correcta y ejecuta el script `sql/schema.sql`. Si prefieres **MySQL Workbench**, crea una conexión con esos datos y TLS habilitado, y ejecuta el mismo SQL.
4. Asegúrate de que la URL JDBC de conexión incluye el nombre de tu base de datos. Ejemplo **ilustrativo** (cambia el host y nombre):
   `jdbc:mysql://HOST_REAL:4000/test?sslMode=VERIFY_IDENTITY&serverTimezone=UTC`
5. **No publiques ni compartas las credenciales** de TiDB. Solo configúralas en las variables secretas de Render.

TiDB Cloud requiere TLS en sus conexiones estándar; `sslMode=VERIFY_IDENTITY` verifica identidad y certificado del servidor. Para detalles de tu tipo de cluster, consulta el apartado de conexión de TiDB Cloud.

## 2. GitHub

1. Descomprime esta carpeta en tu equipo. En GitHub crea un repositorio público nuevo (p. ej., `servicios-web-login`).
2. Sube los **archivos y carpetas interiores del proyecto** (incluido `Dockerfile` y `pom.xml`) al repositorio raíz. No subas el ZIP como único archivo.
3. No subas `.env` ni claves. El archivo `.env.example` es solo una plantilla.
4. Guarda la URL de GitHub para incluirla en el PDF.

## 3. Desplegar en Render (sin instalar Tomcat local)

1. En Render: **New > Web Service**, conecta el repositorio de GitHub.
2. Selecciona **Docker** como runtime (Render puede detectar automáticamente el `Dockerfile`).
3. En **Environment**, añade estos valores, siempre con los datos reales de TiDB:

   | Variable | Valor |
   |---|---|
   | `DB_URL` | `jdbc:mysql://HOST:4000/BASE?sslMode=VERIFY_IDENTITY&serverTimezone=UTC` |
   | `DB_USER` | Usuario de TiDB |
   | `DB_PASSWORD` | Contraseña de TiDB |
   | `PORT` | `10000` (opcional; viene por defecto) |

4. Como **Health Check Path** puedes indicar `/health`.
5. Ejecuta el despliegue y espera a que Render muestre `Live`. Prueba `https://TU-SERVICIO.onrender.com/health` (debe responder `{"status":"ok"}`). Esta ruta confirma Tomcat, **no** confirma la conexión con TiDB.
6. Prueba la URL `/registro`, crea una cuenta y entra por `/login`. Comprueba `/panel`, `Cerrar sesión` y que `/panel` te redirija a `/login` después de salir.

**Nota:** El `Dockerfile` compila el WAR con Maven y lo publica en Tomcat 10.1. La aplicación usa el puerto de `PORT` (predeterminado: 10000), necesario para Render. El WAR se despliega como raíz: no necesitas `/servicios-web-login` en la URL.

## 4. NetBeans (para evidencia del código fuente)

Si la guía exige NetBeans, instala JDK 17 y Apache NetBeans, y abre la carpeta de este proyecto con **File > Open Project**. Al ser un proyecto Maven, NetBeans reconocerá `pom.xml`. Para revisar o capturar el código **no es necesario** tener Tomcat ni TiDB instalados localmente. Para ejecutarlo localmente sí necesitas Tomcat 10.1 y proporcionar las variables de conexión a TiDB.

No es necesario XAMPP: XAMPP no sustituye a Tomcat en una aplicación JSP/Servlet.

## 5. Evidencias a reunir para un único PDF

- [ ] Portada: identificación del aprendiz, programa, ficha, instructor, centro y evidencia.
- [ ] Introducción y explicación breve del sistema.
- [ ] Captura real de `/login` funcionando en Render.
- [ ] Captura real de `/registro` funcionando en Render.
- [ ] Captura real del `/panel` tras iniciar sesión.
- [ ] Capturas organizadas de `Usuario`, `UsuarioDAO`, `ConexionDAO`, `LoginServlet`, `RegistroServlet`, `LogoutServlet`, `AuthFilter`, JSP y `Dockerfile`.
- [ ] Captura de prueba de registro de usuario en TiDB (no mostrar hashes ni secretos).
- [ ] URL del repositorio público GitHub.
- [ ] URL del proyecto funcional Render.
- [ ] Un usuario y contraseña **de prueba** creados por ti con el formulario (no uses cuentas reales).

## 6. Pruebas mínimas de aceptación

1. `/health` responde `{"status":"ok"}`.
2. Registro con datos válidos crea un usuario en TiDB (rol `USUARIO`).
3. Registrar dos veces el mismo usuario o correo muestra un error de duplicado.
4. Login correcto abre `/panel` y muestra el rol de la cuenta.
5. Login erróneo indica que las credenciales no son correctas.
6. Abrir `/panel` sin sesión redirige a `/login`.
7. Cerrar sesión impide reutilizar el acceso a `/panel`.
8. Un usuario normal recibe HTTP 403 si intenta abrir `/admin`; el rol `ADMIN` sí puede entrar.
9. La tabla guarda `password_hash`, **nunca** la clave en texto plano.

**Prueba opcional del rol ADMIN:** registra una segunda cuenta de prueba y, desde el SQL Editor privado de TiDB, ejecuta `UPDATE usuarios SET rol = 'ADMIN' WHERE usuario = 'NOMBRE_DE_PRUEBA';`. Vuelve a iniciar sesión con esa cuenta y comprueba `/admin`. Nunca permitas asignar ADMIN desde el formulario público.

## 7. Arquitectura del código

```text
servicios-web-login/
├── Dockerfile                 # Construcción y Tomcat 10.1 para Render
├── pom.xml                    # Dependencias Java Maven, empaquetado WAR
├── sql/schema.sql             # Tabla usuarios para TiDB
└── src/
    ├── main/java/co/edu/sena/login/
    │   ├── modelo/Usuario.java
    │   ├── dao/ConexionDAO.java, UsuarioDAO.java
    │   ├── controlador/LoginServlet.java, RegistroServlet.java,
    │   │   PanelServlet.java, AdminServlet.java, LogoutServlet.java, HealthServlet.java
    │   ├── filtro/AuthFilter.java
    │   └── util/CsrfUtil.java, HtmlUtil.java, ValidadorUsuario.java
    ├── main/webapp/
    │   ├── index.jsp
    │   ├── WEB-INF/vistas/login.jsp, registro.jsp, panel.jsp, admin.jsp
    │   └── css/estilos.css
    └── test/java/...          # Pruebas unitarias independientes de TiDB
```

## 8. Limitaciones y seguridad

- El proyecto es una base educativa; **requiere pruebas reales** contra TiDB y Render antes de declarar que está funcionando.
- Las credenciales deben ir en Render Environment, nunca en el repositorio ni en el documento público (salvo una cuenta de prueba desechable).
- Solo los usuarios registrados pueden iniciar sesión. El registro público **no puede** elegir el rol `ADMIN`. Solo las cuentas con ese rol pueden acceder a `/admin`; esta zona demuestra autorización sin operaciones administrativas peligrosas.
- Para uso de producción real faltaría configurar límites de intentos, recuperación de contraseña, correos verificados y monitoreo de seguridad.
# Dise-o-y-desarrollo-de-servicios-web

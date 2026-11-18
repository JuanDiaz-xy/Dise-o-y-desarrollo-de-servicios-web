# Sistema web de autenticación y registro

Aplicación web desarrollada con **Java 17, JSP, Jakarta Servlets 6 y Apache Tomcat 10.1**. Utiliza arquitectura **MVC**, persistencia mediante **JDBC compatible con MySQL/TiDB Cloud** y despliegue con **Docker en Render**.

## Funcionalidades

- Registro de usuarios con rol `USUARIO` predeterminado.
- Autenticación con contraseñas protegidas mediante BCrypt.
- Sesiones HTTP y protección de rutas privadas.
- Acceso a `/admin` restringido al rol `ADMIN`.
- Cierre de sesión mediante POST y validación CSRF.
- Consultas SQL parametrizadas mediante `PreparedStatement`.
- Endpoint de salud HTTP para el contenedor.

## Estructura del proyecto

```text
├── Dockerfile                       # Compilación Maven y Tomcat
├── pom.xml                          # Dependencias y empaquetado WAR
├── sql/schema.sql                   # Estructura de usuarios
├── src/main/java/co/edu/sena/login/
│   ├── modelo/                      # Entidad Usuario
│   ├── dao/                         # Conexión y acceso a datos
│   ├── controlador/                 # Servlets
│   ├── filtro/                      # Protección de acceso
│   └── util/                        # Validaciones y utilidades
├── src/main/webapp/
│   ├── WEB-INF/vistas/              # Vistas JSP
│   ├── css/                         # Estilos
│   └── index.jsp
└── src/test/java/                  # Pruebas unitarias
```

## Base de datos

El esquema utilizado es `sena_login` y la tabla es `usuarios`. En la consola SQL de TiDB, selecciona la base de datos y ejecuta el script [sql/schema.sql](sql/schema.sql) si la tabla aún no existe.

La conexión se configura mediante variables de entorno, **nunca mediante credenciales en el código o en GitHub**:

| Variable | Descripción |
| --- | --- |
| `DB_URL` | URL JDBC proporcionada por TiDB Cloud, con nombre de base de datos y TLS |
| `DB_USER` | Usuario de la base de datos |
| `DB_PASSWORD` | Contraseña de la base de datos |
| `PORT` | Puerto HTTP del servicio (opcional; predeterminado `10000`) |

Consulta [`.env.example`](.env.example) para ver el formato. El host, puerto y parámetros TLS definitivos deben corresponder a **TiDB Cloud → Connect**.

## Compilación y despliegue

Compilación con JDK 17 y Maven:

```bash
mvn clean verify
```

En Render, crea un **Web Service** desde este repositorio, utiliza el runtime **Docker** y configura `DB_URL`, `DB_USER` y `DB_PASSWORD` como variables privadas. El [Dockerfile](Dockerfile) compila el WAR y lo ejecuta en Tomcat.

Ruta de health check: `/health`. Esta ruta confirma la respuesta HTTP del servidor, no la conectividad de la base de datos.

## Rutas

| Ruta | Propósito |
| --- | --- |
| `/registro` | Crear una cuenta |
| `/login` | Iniciar sesión |
| `/panel` | Página protegida del usuario autenticado |
| `/admin` | Página disponible solo para `ADMIN` |
| `/logout` | Cerrar la sesión mediante POST |
| `/health` | Comprobar la respuesta del servicio |

## Verificación funcional

Después de desplegar, comprobar registro en TiDB, acceso correcto e incorrecto, restricciones de sesión y rol, y cierre de sesión. El proyecto incluye pruebas unitarias en `src/test/java`.

**Estado:** el código fuente está preparado para desplegarse; las pruebas en una instancia publicada de Render y la conexión de extremo a extremo con TiDB deben verificarse antes de declararlo operativo.

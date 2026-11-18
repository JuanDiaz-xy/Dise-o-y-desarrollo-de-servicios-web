<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="co.edu.sena.login.util.HtmlUtil,co.edu.sena.login.util.CsrfUtil" %>
<%-- Vista MVC de registro; la contraseña y el rol se procesan en el servidor. --%>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Crear cuenta | Servicios Web SENA</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
    <main class="layout">
        <section class="bienvenida" aria-label="Presentación">
            <div class="marca"><span class="marca-icono">SW</span> <span>Servicios Web</span></div>
            <div class="bienvenida-contenido">
                <div class="etiqueta">CREA TU CUENTA</div>
                <h1>Comienza con una cuenta segura.</h1>
                <p>El registro guarda los datos en TiDB Cloud y protege las contraseñas mediante BCrypt.</p>
                <div class="puntos"><span>● Acceso con usuario</span><span>● Validación segura</span><span>● Rol inicial USUARIO</span></div>
            </div>
            <small>Proyecto formativo · Diseño y desarrollo de servicios web</small>
        </section>
        <section class="form-area">
            <div class="form-card registro-card">
                <span class="mini-etiqueta">ÚNETE</span>
                <h2>Crear una cuenta</h2>
                <p class="subtitulo">Completa los datos para registrarte.</p>
                <% if (request.getAttribute("error") != null) { %>
                    <div class="alerta alerta-error" role="alert"><%= HtmlUtil.escape((String)request.getAttribute("error")) %></div>
                <% } %>
                <form method="post" action="<%= request.getContextPath() %>/registro">
                    <input type="hidden" name="csrfToken" value="<%= CsrfUtil.token(request) %>">
                    <label for="nombre">Nombre completo</label>
                    <input id="nombre" name="nombre" type="text" maxlength="100" placeholder="Nombres y apellidos" autocomplete="name" required>
                    <label for="usuario">Nombre de usuario</label>
                    <input id="usuario" name="usuario" type="text" minlength="4" maxlength="30" pattern="[A-Za-z0-9._-]{4,30}" placeholder="Ej. aprendiz_demo" autocomplete="username" required>
                    <label for="correo">Correo electrónico</label>
                    <input id="correo" name="correo" type="email" maxlength="150" placeholder="correo@ejemplo.com" autocomplete="email" required>
                    <label for="contrasena">Contraseña</label>
                    <input id="contrasena" name="contrasena" type="password" minlength="10" maxlength="72" placeholder="Mínimo 10 caracteres" autocomplete="new-password" required>
                    <label for="confirmarContrasena">Confirmar contraseña</label>
                    <input id="confirmarContrasena" name="confirmarContrasena" type="password" minlength="10" maxlength="72" placeholder="Repite tu contraseña" autocomplete="new-password" required>
                    <p class="ayuda">Tu cuenta tendrá el rol USUARIO por defecto.</p>
                    <button type="submit" class="boton">Crear cuenta <span aria-hidden="true">→</span></button>
                </form>
                <p class="pie-form">¿Ya tienes cuenta? <a href="<%= request.getContextPath() %>/login">Inicia sesión</a></p>
            </div>
        </section>
    </main>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="co.edu.sena.login.util.HtmlUtil,co.edu.sena.login.util.CsrfUtil" %>
<%-- Vista MVC de inicio de sesión; la autenticación se hace en LoginServlet. --%>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="color-scheme" content="light">
    <title>Iniciar sesión | Servicios Web SENA</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
    <main class="layout">
        <section class="bienvenida" aria-label="Presentación">
            <div class="marca"><span class="marca-icono">SW</span> <span>Servicios Web</span></div>
            <div class="bienvenida-contenido">
                <div class="etiqueta">Evidencia GA7-2201096-AA5-EV03</div>
                <h1>Tu espacio de acceso seguro.</h1>
                <p>Módulo de autenticación desarrollado con Java, JSP, Servlets y arquitectura MVC.</p>
                <div class="puntos"><span>● Registro de usuarios</span><span>● Sesiones protegidas</span><span>● Base de datos TiDB</span></div>
            </div>
            <small>Proyecto formativo · Diseño y desarrollo de servicios web</small>
        </section>
        <section class="form-area">
            <div class="form-card">
                <span class="mini-etiqueta">BIENVENIDO</span>
                <h2>Iniciar sesión</h2>
                <p class="subtitulo">Ingresa tus credenciales para continuar.</p>
                <% if (request.getAttribute("error") != null) { %>
                    <div class="alerta alerta-error" role="alert"><%= HtmlUtil.escape((String)request.getAttribute("error")) %></div>
                <% } %>
                <% if ("1".equals(request.getParameter("registrado"))) { %>
                    <div class="alerta alerta-exito" role="status">Cuenta creada correctamente. Ya puedes iniciar sesión.</div>
                <% } %>
                <% if ("1".equals(request.getParameter("salida"))) { %>
                    <div class="alerta alerta-exito" role="status">Has cerrado sesión correctamente.</div>
                <% } %>
                <form method="post" action="<%= request.getContextPath() %>/login">
                    <input type="hidden" name="csrfToken" value="<%= CsrfUtil.token(request) %>">
                    <label for="usuario">Nombre de usuario</label>
                    <input id="usuario" name="usuario" type="text" maxlength="30" placeholder="Tu usuario" autocomplete="username" required autofocus>
                    <label for="contrasena">Contraseña</label>
                    <input id="contrasena" name="contrasena" type="password" placeholder="Tu contraseña" autocomplete="current-password" required>
                    <button type="submit" class="boton">Entrar a mi cuenta <span aria-hidden="true">→</span></button>
                </form>
                <p class="pie-form">¿No tienes cuenta? <a href="<%= request.getContextPath() %>/registro">Regístrate aquí</a></p>
            </div>
        </section>
    </main>
</body>
</html>

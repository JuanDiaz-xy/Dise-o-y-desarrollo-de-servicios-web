<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="co.edu.sena.login.modelo.Usuario,co.edu.sena.login.util.HtmlUtil,co.edu.sena.login.util.CsrfUtil" %>
<%-- Vista privada: solo se alcanza desde PanelServlet después de AuthFilter. --%>
<% Usuario usuario = (Usuario) session.getAttribute("usuarioAutenticado"); %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Panel privado | Servicios Web SENA</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body class="body-panel">
    <header class="barra-superior"><div class="marca marca-oscura"><span class="marca-icono">SW</span> Servicios Web</div>
        <form method="post" action="<%= request.getContextPath() %>/logout">
            <input type="hidden" name="csrfToken" value="<%= CsrfUtil.token(request) %>">
            <button class="salir" type="submit">Cerrar sesión →</button>
        </form>
    </header>
    <main class="panel-contenido">
        <div class="etiqueta-panel">ÁREA PRIVADA · SESIÓN ACTIVA</div>
        <h1>¡Bienvenido, <%= HtmlUtil.escape(usuario.getNombre()) %>!</h1>
        <p class="subtitulo">La autenticación fue exitosa. Esta página requiere iniciar sesión.</p>
        <div class="panel-grid">
            <article class="dato"><span>Nombre de usuario</span><strong><%= HtmlUtil.escape(usuario.getUsuario()) %></strong></article>
            <article class="dato"><span>Correo electrónico</span><strong><%= HtmlUtil.escape(usuario.getCorreo()) %></strong></article>
            <article class="dato"><span>Rol de acceso</span><strong><%= HtmlUtil.escape(usuario.getRol()) %></strong></article>
        </div>
        <% if ("ADMIN".equals(usuario.getRol())) { %>
            <p><a href="<%= request.getContextPath() %>/admin">Abrir zona de administración →</a></p>
        <% } %>
        <div class="alerta alerta-exito panel-info">✓ La sesión se controla desde el servidor y se invalida al cerrar sesión.</div>
    </main>
</body>
</html>

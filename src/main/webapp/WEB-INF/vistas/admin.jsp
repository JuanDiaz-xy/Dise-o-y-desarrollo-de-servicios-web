<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="co.edu.sena.login.modelo.Usuario,co.edu.sena.login.util.HtmlUtil" %>
<%-- Ejemplo de vista restringida a un rol: autorización en AuthFilter. --%>
<% Usuario usuario = (Usuario) session.getAttribute("usuarioAutenticado"); %>
<!doctype html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Administración | Servicios Web SENA</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body class="body-panel">
<header class="barra-superior"><div class="marca marca-oscura"><span class="marca-icono">SW</span> Servicios Web</div>
<a href="<%= request.getContextPath() %>/panel">Volver al panel</a></header>
<main class="panel-contenido">
  <div class="etiqueta-panel">ACCESO POR ROL · ADMIN</div>
  <h1>Zona administrativa</h1>
  <p class="subtitulo">Hola, <%= HtmlUtil.escape(usuario.getNombre()) %>. Tu rol ADMIN autoriza esta sección.</p>
  <div class="alerta alerta-exito panel-info">✓ Los usuarios con rol USUARIO reciben un error 403 si intentan abrir esta URL.</div>
</main>
</body>
</html>

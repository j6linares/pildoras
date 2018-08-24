<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Relación de Paneles</title>
<link rel="stylesheet" type="text/css" href="css/host.css" media="screen" />
</head>

<body>
<%@ include file="cabecera.jsp" %>
<p class="cuerpo">  
<table>
	<tr class="cabeceraLista">
		<td >Id</td>
		<td >Nombre</td>
		<td >Titulo</td>
		<td >Entorno</td>
		<td >Subtitulo</td>
		<td >Fecha y Hora</td>
		<td >Fecha</td>
		<td >Hora</td>
		<td >Mensaje</td>
		<td >Pagina</td>
		<td >Paginas</td>
		<td >Acción&nbsp;
			<c:url var="urlNuevo" value="paneles">
				<c:param name="operacion" value="nuevo"></c:param>
			</c:url>
			<br><a href="${urlNuevo}">Nuevo Panel</a>
			<!-- <input type="button" value="Nuevo Panel" onclick="window.location.href='cPanel.jsp'"> -->
			<button class="button textoFijoBrillo" style="vertical-align:middle" onclick="window.location.href='${urlNuevo}'">
				<span>Nuevo</span>
			</button>
		</td>
	</tr>
	<c:forEach var="panel" items="${listaPaneles}">
		<c:url var="urlEditar" value="paneles">
			<c:param name="operacion" value="editar"></c:param>
			<c:param name="id" value="${panel.id}"></c:param>
		</c:url>
		<c:url var="urlBorrar" value="paneles">
			<c:param name="operacion" value="borrar"></c:param>
			<c:param name="id" value="${panel.id}"></c:param>
		</c:url>
		<c:url var="urlEliminar" value="paneles">
			<c:param name="operacion" value="delete"></c:param>
			<c:param name="id" value="${panel.id}"></c:param>
		</c:url>
		<tr class="lineaLista textoSalida">
			<td><f:formatNumber type="number" pattern="0000000000" value="${panel.id}"></f:formatNumber> </td>
			<td>${panel.nombre}</td>
			<td>${panel.titulo}</td>
			<td>${panel.entorno}</td>
			<td>${panel.subtitulo}</td>
			<td><f:formatDate value="${panel.fecha}" type="both" pattern="dd-MM-yyyy HH:mm" /></td>
			<td><f:formatDate value="${panel.fecha}" type="both" pattern="dd-MM-yyyy" /></td>
			<td><f:formatDate value="${panel.fecha}" type="both" pattern="HH:mm" /></td>
			<td>${panel.mensaje}</td>
			<td>${panel.pagina}</td>
			<td>${panel.paginas}</td>
			<td>
				<a class="boton" href="${urlEditar}">Editar</a>
				<a class="boton" href="${urlEliminar}">X</a>
				<a class="boton" href="${urlBorrar}">Borrar</a>
			</td>
		</tr>
	</c:forEach>
	
</table>
<!-- 
<div id="contenedorBoton">
	<input type="button" value="Nuevo Panel" onclick="window.location.href='cPanel.jsp'">
</div>
 -->
</p>
<%@ include file="pie.jsp" %>  
</body>
</html>
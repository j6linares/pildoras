<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Editar un panel</title>
</head>
<body>
<%@ include file="cabecera.jsp" %>
<p class="cuerpo">  
<c:url var="urlVolver" value="ControladorPanel">
			<c:param name="operacion" value="listar"></c:param>
</c:url>
	
<form name="uPanel" method="get" action="paneles">
	<input type="hidden" name="operacion" value="update">
	<input type="hidden" name="id" value="${panel.id }">
	<table>
		<tr>
			<td class="textoFijo">Id Panel:</td>
			<f:formatNumber var="idf" type="number" pattern="0000000000" value="${panel.id}"></f:formatNumber> 
			<td><input class="textoSalida" type="text" name="nombre" value="${idf }" disabled="disabled" maxlength="10" size="10"> </td>
		</tr>
		<tr>
			<td class="textoFijo">Nombre Panel:</td>
			<td><input class="textoEntrada" type="text" name="nombre" value="${panel.nombre }" maxlength="8" size="8"> </td>
		</tr>
		<tr>
			<td class="textoFijo">Título:</td>
			<td><input class="textoEntrada" type="text" name="titulo" value="${panel.titulo }" maxlength="70" size="70"> </td>
		</tr>
		<tr>
			<td class="textoFijo">Subtitulo:</td>
			<td><input class="textoEntrada" type="text" name="subtitulo" value="${panel.subtitulo }" maxlength="70" size="70"> </td>
		</tr>
		<tr>
			<td class="textoFijo">Entorno:</td>
			<td><input class="textoEntrada" type="text" name="entorno" value="${panel.entorno }" maxlength="15" size="15"> </td>
		</tr>
		<tr>
			<f:formatDate var="fechaf" value="${panel.fecha}" type="both" pattern="dd-MM-yyyy" />
			<td class="textoFijo">Fecha:</td>
			<td><input class="textoEntrada" type="text" name="fecha" maxlength="10" size="10"
				value="${fechaf}"> </td>
		</tr>
		<tr>
			<td class="textoFijo">Mensaje:</td>
			<td><input class="textoEntrada" type="text" name="mensaje" value="${panel.mensaje}" maxlength="80" size="80"> </td>
		</tr>
		<tr>
			<td class="textoFijo">Página:</td>
			<td><input class="textoEntrada" type="text" name="pagina" value="${panel.pagina }" maxlength="2" size="2"> </td>
		</tr>
		<tr>
			<td class="textoFijo">Páginas:</td>
			<td><input class="textoEntrada" type="text" name="paginas" value="${panel.paginas }" maxlength="2" size="2"> </td>
		</tr>
		<tr>
			<td>&nbsp; </td>
			<td>
				<!-- <input type="submit" value="Guardar" name="guardar"> -->
				<button class="boton" type="submit" name="guardar">Guardar</button>
				<button class="boton" type="reset" name="restaurar">Restaurar</button>
			</td>
			
		</tr>
		<!-- 
		<tr>
			<td></td>
			<td><a href="${urlVolver}">Volver</a></td>
		</tr>
		 -->
	</table>
</form>
</p>
<%@ include file="pie.jsp" %>  
</body>
</html>
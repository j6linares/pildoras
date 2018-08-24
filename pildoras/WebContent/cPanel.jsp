<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Crear un panel</title>
</head>
<body>
<%@ include file="cabecera.jsp" %>
<p class="cuerpo">  
<form name="cPanel" method="get" action="paneles">
	<input type="hidden" name="operacion" value="crear">
	<table>
		<tr>
			<td class="textoFijo">Nombre Panel:</td>
			<td><input class="textoEntrada" type="text" name="nombre" maxlength="8" size="8"> </td>
		</tr>
		<tr>
			<td class="textoFijo">Título:</td>
			<td><input class="textoEntrada" type="text" name="titulo" maxlength="70" size="70"> </td>
		</tr>
		<tr>
			<td class="textoFijo">Subtitulo:</td>
			<td><input class="textoEntrada" type="text" name="subtitulo" maxlength="70" size="70"> </td>
		</tr>
		<tr>
			<td class="textoFijo">Entorno:</td>
			<td><input class="textoEntrada" type="text" name="entorno" maxlength="15" size="15"> </td>
		</tr>
		<tr>
			<td class="textoFijo">Fecha:</td>
			<td><input class="textoEntrada" type="text" name="fecha" maxlength="10" size="10"> </td>
		</tr>
		<tr>
			<td class="textoFijo">Mensaje:</td>
			<td><input class="textoEntrada" type="text" name="mensaje" maxlength="80" size="80"> </td>
		</tr>
		<tr>
			<td class="textoFijo">Página:</td>
			<td><input class="textoEntrada" type="text" name="pagina" maxlength="2" size="2"> </td>
		</tr>
		<tr>
			<td class="textoFijo">Páginas:</td>
			<td><input class="textoEntrada" type="text" name="paginas"  maxlength="2" size="2"> </td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<button class="boton" type="submit" name="guardar">Guardar</button>
				<button class="boton" type="reset" name="borrar">Borrar</button>
			</td>
		</tr>
	</table>
</form>
</p>
<%@ include file="pie.jsp" %>  
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SQLError</title>
</head>
<body>
<%@ include file="cabecera.jsp" %>
<p class="cuerpo">  
<form name="SQLError" method="get" action="paneles">
	<input type="hidden" name="operacion" value="listar">
	<table>
		<tr>
			<td class="textoFijo">SQLState:</td>
			<td class="textoSalidaBrillo">${sqlex.getSQLState()} </td>
		</tr>
		<tr>
			<td class="textoFijo">Error code:</td>
			<td class="textoSalidaBrillo">${sqlex.getErrorCode()} </td>
		</tr>
		<tr>
			<td class="textoFijo">Mensaje:</td>
			<td class="textoSalidaBrillo">${sqlex.getMessage()} </td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td>
				<button class="boton" type="submit" name="ok">OK</button>
				
			</td>
		</tr>
	</table>
</form>
</p>
<%@ include file="pie.jsp" %>  
</body>
</html>
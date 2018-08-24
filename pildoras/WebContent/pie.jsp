<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>pie pildoras</title>
<link rel="stylesheet" type="text/css" href="css/host.css" media="screen" />
</head>
<body>
<div class="pie">
	<br>
	<label class="textoSalidaBrillo"> ${datosPanel.mensaje }</label>
	<hr width="100%" />
	<div id="contenedorBoton">
		<button class="button textoFijoBrillo" style="vertical-align:middle" onclick="window.location.href='paneles'">
			<span>Volver
			</span>
		</button>
		
	</div>
</div>

</body>
</html>
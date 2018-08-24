<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cabecera pildoras</title>
</head>
<body>
<div class="izda">
<br><label class="textoSalida" >${datosPanel.nombre }</label>
<br><label class="textoEntradaError" >${datosPanel.entorno }</label>
<hr width="100%" />
</div>
<div class="centro">
<br><label class="textoSalidaBrillo" >${datosPanel.titulo }</label>
<br><label class="textoSalida" >${datosPanel.subtitulo }</label>
<hr width="100%" />
</div>
<div class="dcha">
<br><label class="textoFijo" >Fecha: </label> 
<label class="textoSalida" ><f:formatDate value="${datosPanel.fecha }" type="both" pattern="dd-MM-yyyy" /></label>
<br><label class="textoFijo" >Hora: </label>
<label class="textoSalida" ><f:formatDate value="${datosPanel.fecha }" type="both" pattern="HH:mm:ss" /></label>
<hr width="100%" />
</div>

<br/>
</body>
</html>
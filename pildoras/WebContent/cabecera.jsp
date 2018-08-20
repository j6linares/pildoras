<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>cabecera pildoras</title>
</head>
<body>
<%@ page import="java.util.*" %>
<%! Date fecha=new Date(); %>
Fecha: <%=fecha %>
<br/>Fecha con tag <c:out value="<%=fecha %>"></c:out>
<br/>Fecha formateada con tags <f:formatDate value="<%=fecha %>" type="both" pattern="dd-MM-yyyy HH:mm" />
</body>
</html>
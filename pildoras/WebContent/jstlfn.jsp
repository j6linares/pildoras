<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>jstl functions</title>
</head>
<body>

<c:set var="datos" value="Ana, Lopez, Directora, 75000"></c:set>
<c:set var="datosArray" value="${fn:split(datos,',')}"></c:set>
<c:forEach var="dato" items="${datosArray}">
	<input type="text" value="${dato}"><br/>
</c:forEach>
<br>
<input type="text" value="${datosArray[0]}"><br/>
<input type="text" value="${datosArray[1]}"><br/>
<input type="text" value="${datosArray[2]}"><br/>
<input type="text" value="${datosArray[3]}"><br/>

<%@ page import="java.util.*" %>
<%
Enumeration e=session.getAttributeNames();
while (e.hasMoreElements()) {
	String name = (String)e.nextElement();
	String value = session.getAttribute(name).toString();
	out.println("nombre: " + name + " valor: " + value);
}
%>
<p><a href="request.jsp">Request</a>
</body>
</html>
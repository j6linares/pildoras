<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>pildorasinformáticas</title>
</head>
<body>
<%@ include file="cabecera.jsp" %>  

<form name="formulario" action="sesionjstl.jsp" method="post">

<p>Artículos a comprar:</p>
  <p>
    <label>
      <input type="checkbox" name="articulos" value="agua " >
      Agua </label>
    <br>
    <label>
      <input type="checkbox" name="articulos" value="leche" >
      Leche </label>
    <br>
     <label>
      <input type="checkbox" name="articulos" value="pan" >
      Pan </label>
    <br>
     <label>
      <input type="checkbox" name="articulos" value="mazanas" >
      Manzanas </label>
    <br>
     <label>
      <input type="checkbox" name="articulos" value="carne" >
      Carne </label>
      <br>
       <label>
      <input type="checkbox" name="articulos" value="pescado" >
      Pescado </label>
  </p>
  <p>
<input type="submit" name="boton" id="boton" value="Enviar">
<input type="submit" name="boton" id="boton" value="Vaciar">
<br/>
</form>
<table border="2">
<tr>
<c:forEach items="${sessionScope}" var="item">
  <td>${item.key}=${item.value}</td>
</c:forEach>
</tr>
</table>
<%
out.println("<p><strong>Datos de la peticion:</strong> ");
out.println("<br>ContextPath="+request.getContextPath());
out.println("<br>AuthType="+request.getAuthType());
out.println("<br>AuthCharacterEncoding="+request.getCharacterEncoding());
out.println("<br>ContentType="+request.getContentType());
out.println("<br>LocalAddr="+request.getLocalAddr());
out.println("<br>LocalName="+request.getLocalName());
out.println("<br>LocalPort="+request.getLocalPort());
out.println("<br>Method="+request.getMethod());
out.println("<br>PathInfo="+request.getPathInfo());
out.println("<br>PathTranslated="+request.getPathTranslated());
out.println("<br>QueryString="+request.getQueryString());
out.println("<br>Protocol="+request.getProtocol());
out.println("<br>RemoteAddr="+request.getRemoteAddr());
out.println("<br>RemoteHost="+request.getRemoteHost());
out.println("<br>RemotePort="+request.getRemotePort());
out.println("<br>RemoteUser="+request.getRemoteUser());
out.println("<br>RequestURI="+request.getRequestURI());
out.println("<br>RequestedSessionId="+request.getRequestedSessionId());
out.println("<br>Scheme="+request.getScheme());
out.println("<br>ServerName="+request.getServerName());
out.println("<br>ServerPort="+request.getServerPort());
out.println("<br>ServletPath="+request.getServletPath());

%>
<%@ page import="java.util.*" %>
<%

//variables de peticion
Enumeration p=request.getAttributeNames();
out.println("<p><strong>Variables de peticion:</strong> ");
out.println("<table border='1'> ");
out.println("<tr><th>nombre</th><th>valor</th></tr> ");

while (p.hasMoreElements()) {
	String name = (String)p.nextElement();
	String value = session.getAttribute(name).toString();
	out.println("<tr><td> " + name + "</td> <td>" + value+"</td></tr>");
}
out.println("</table> ");
/*
//variables de la cabecera
Enumeration h=request.getHeaderNames();
out.println("<p><strong>Variables de la cabecera:</strong> ");
out.println("<table border='1'> ");
out.println("<tr><th>nombre</th><th>valor</th></tr> ");

while (h.hasMoreElements()) {
	String name = (String)h.nextElement();
	String value = session.getAttribute(name).toString();
	out.println("<tr><td> " + name + "</td> <td>" + value+"</td></tr>");
}
out.println("</table> ");
*/
//variables de sesion
Enumeration s=session.getAttributeNames();
out.println("<strong>Variables de sesion:</strong> ");
out.println("<table border='1'> ");
out.println("<tr><th>nombre</th><th>valor</th></tr> ");

while (s.hasMoreElements()) {
	String name = (String)s.nextElement();
	String value = session.getAttribute(name).toString();
	out.println("<tr><td> " + name + "</td> <td>" + value+"</td></tr>");
}
out.println("</table> ");

//vaciar el carrito 
/*
String boton=request.getParameter("boton");
if (boton.equals("Vaciar")) {
	session.removeAttribute("elementos");
}
*/
/*
//recuperar de sesion la lista de elementos
out.println("<br>");
List<String> listaElementos = (List<String>) session.getAttribute("elementos");
if (listaElementos==null){
	out.println("lista de elementos vacia!");
	listaElementos=new ArrayList<String>();
	session.setAttribute("elementos", listaElementos);
} else {
	out.println("lista de elementos previa en sesión ="+listaElementos);
}

//recuperar de la peticion los elementos pedidos
out.println("<br>");
String[] elementos = request.getParameterValues("articulos");
if (elementos!=null){
	out.println("Elementos pedidos en la peticion="+elementos);
	for (String e: elementos) {
		listaElementos.add(e);
		out.println("<br>"+e);
	}
	out.println("lista de elementos posterior en sesión ="+listaElementos);

} else {
	out.println("No se ha pedido nada!");
}

out.println("<br>Lista de elementos de sesion java:<ul>");
if (listaElementos!=null) {
	for (String e: listaElementos) {
		out.println("<li>"+e+"</li>");
	}
}

out.println("</ul><br>Lista de elementos de pedidos java:<ul>");
if (elementos!=null) {
	for (String e: elementos) {
		out.println("<li>"+e+"</li>");
	}
}
out.println("</ul>");

//pasar e carrito
if (listaElementos!=null){
	out.println("listaElementos.size()="+listaElementos.size());
if (listaElementos.size()>0){
	String[] carrito = new String[listaElementos.size()];
	int i=0;
	for (String le: listaElementos){
		carrito[i++]=le;
	}
	out.println("carrito="+carrito.length);
}
}
*/
%>

<!-- 
<br>Lista de elementos pedidos jstl:
<ol>
<c:forEach var="el" items="${elementos}">
	<li>${el}</li>
</c:forEach>
</ol>
<br>Lista de elementos de sesion jstl:
<ol>
<c:forEach var="els" items="${carrito}">
	<li>${els}</li>
</c:forEach>
</ol>
 -->
</body>
</html>
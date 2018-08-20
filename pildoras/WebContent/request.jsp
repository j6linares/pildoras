<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>request</title>
</head>
<body>
<%@ include file="cabecera.jsp" %>  
<p><a href="javascript:history.back()">Volver</a>
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
<c:set var="cookies" value="<%=request.getCookies() %>" />
<c:forEach var="cookie" items="${cookies}">
	<br>${cookie}<br/>
</c:forEach>

<%@ page import="java.util.*" %>
<%

//variables de peticion
Enumeration p=request.getAttributeNames();
out.println("<p><strong>Variables de peticion:</strong> ");
out.println("<table border='1'> ");
out.println("<tr><th>nombre</th><th>valor</th></tr> ");

while (p.hasMoreElements()) {
	String name = (String)p.nextElement();
	String value = request.getAttribute(name).toString();
	out.println("<tr><td> " + name + "</td> <td>" + value+"</td></tr>");
}
out.println("</table> ");

//variables de la cabecera
Enumeration h=request.getHeaderNames();
out.println("<p>h= "+h);
out.println("<p><strong>Variables de la cabecera:</strong> ");
out.println("<table border='1'> ");
out.println("<tr><th>nombre</th><th>valor</th></tr> ");

while (h.hasMoreElements()) {
	String name = (String)h.nextElement();
	String value = request.getHeader(name.toString());
	out.println("<tr><td> " + name + "</td> <td>" + value+"</td></tr>");
	
}

out.println("</table> ");

//variables de la cookies
Cookie[] cs=request.getCookies();
out.println("<p>cs= "+cs);
out.println("<p><strong>Variables de las cookies:</strong> ");
out.println("<table border='1'> ");
out.println("<tr>"
		+"<td>nombre</td>"
		+"<td>valor</td>"
		+"<th>comment</th>"
		+"<th>domain</th>"
		+"<th>maxage</th>"
		+"<th>secure</th>"
		+"<th>path</th>"
		+"<th>version</th>"
	+"</tr>"
);
for (Cookie c: cs){
	String name = c.getName();
	String value = c.getValue();
	String comment= c.getComment();
	String domain = c.getDomain();
	int maxage= c.getMaxAge();
	boolean secure = c.getSecure();
	String path=c.getPath();
	int vers=c.getVersion();
	out.println("<tr>"
					+"<td> " + name + "</td>"
					+"<td>" + value+"</td>"
					+"<th>"+comment+"</th>"
					+"<th>"+domain+"</th>"
					+"<th>"+maxage+"</th>"
					+"<th>"+secure+"</th>"
					+"<th>"+path+"</th>"
					+"<th>"+vers+"</th>"
				+"</tr>");
}
out.println("</table> ");
%>
<p><a href="javascript:history.back()">Volver</a>
</body>
</html>
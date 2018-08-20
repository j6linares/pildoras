<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>pildorasinformáticas</title>
</head>
<body>
<%@ page import="java.util.*" %>
<%! Date fecha=new Date(); %>
Fecha: <%=fecha %>
<form name="formulario" action="sesion.jsp" method="post">

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
<br/>
</form>
<ul>
<%

List<String> listaElementos = (List<String>) session.getAttribute("elementos");
if (listaElementos==null){
	listaElementos=new ArrayList<String>();
	session.setAttribute("elementos", listaElementos);
}

	
String[] elementos = request.getParameterValues("articulos");
//out.println("Elementos="+elementos);
if (elementos!=null){
	for (String e: elementos) {
		listaElementos.add(e);
		//out.println("<li>"+e+"</li>");
	}
}
for (String e: listaElementos) {
	out.println("<li>"+e+"</li>");
}

%>
</ul>

</body>
</html>
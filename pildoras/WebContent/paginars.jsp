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
<%! java.util.Date fecha=new java.util.Date(); %>
<br>Fecha: <%=fecha %>
<%! int nrolinxpag=10;  %>
<%! int nropagxbloq=20; %>
<%! int pag=1; %>
<%
//definir las variables de paginacionrs por defecto
Cookie cookie;


//leer las cookies del usuario
Cookie[] cookies=request.getCookies();
boolean primeraVez=true;
if (cookies!=null){
	for (Cookie c: cookies){
		if ("paginars.nrolinxpag".equals(c.getName())){
			primeraVez=false;
		}

	}
}

if (primeraVez){
	//primera vez, crear las cookies
	//out.println("<p>Primera vez!");
	cookie = new Cookie("paginars.nrolinxpag", String.valueOf(nrolinxpag));
	cookie.setMaxAge(365*24*60*60);
	response.addCookie(cookie);
	cookie = new Cookie("paginars.nropagxbloq", String.valueOf(nropagxbloq));
	cookie.setMaxAge(365*24*60*60);
	response.addCookie(cookie);
} else {
	//resto de veces
	//out.println("<p>resto de veces!");
	//leer los parámetros de la página
	String linxpag=request.getParameter("linxpag");
	if (linxpag==null) {
		linxpag="10";//String.valueOf(Integer.MAX_VALUE);
	} 
	//out.println("<p>parametros de jsp!");

	String pagxbloq=request.getParameter("pagxbloq");
	if (pagxbloq==null) {
		pagxbloq="20";//String.valueOf(Integer.MAX_VALUE);
	}
	//out.println("<p>linxpag="+linxpag);
	
	//leer las cookies
	//out.println("<p>cookies="+cookies.length);
	for (Cookie c: cookies){
		//out.println("<p>cookie "+c.getName()+"="+c.getValue());
		
		if ("paginars.nrolinxpag".equals(c.getName())){
			nrolinxpag=Integer.parseInt(c.getValue());
			//out.println("<p>cookie nrolinxpag="+linxpag);

			if (nrolinxpag!=Integer.parseInt(linxpag)){
				//la cookie no coincide con lo del usuario
				nrolinxpag=Integer.parseInt(linxpag);
				//primera vez, crear las cookies
				cookie = new Cookie("paginars.nrolinxpag", String.valueOf(nrolinxpag));
				cookie.setMaxAge(365*24*60*60);
				response.addCookie(cookie);

			}
		}
		if ("paginars.nropagxbloq".equals(c.getName())){
			nropagxbloq=Integer.parseInt(c.getValue());
			if (nropagxbloq!=Integer.parseInt(pagxbloq)){
				//la cookie no coincide con lo del usuario
				nropagxbloq=Integer.parseInt(pagxbloq);
	
				//primera vez, crear las cookies
				cookie = new Cookie("paginars.nropagxbloq", String.valueOf(nropagxbloq));
				cookie.setMaxAge(365*24*60*60);
				response.addCookie(cookie);

			}
		}
	}
}

%>

<form name="formulario" action="paginars.jsp" method="post">

     <label>Número de líneas por página: </label>
     <input type="text" name="linxpag" maxlength="2" value="<%=nrolinxpag%>">
    <br>
    <label>Número de páginas por bloque: </label>
    <input type="text" name="pagxbloq" maxlength="2" value="<%=nropagxbloq%>">
    <br/>
    <label>Ir a página: </label>
    <input type="text" name="pag" maxlength="2" value="<%=pag%>">
    <br/>
    <input type="submit" name="boton" id="boton" value="[[">
    &nbsp;
    <input type="submit" name="boton" id="boton" value="[">
    &nbsp;
    <input type="submit" name="boton" id="boton" value="((">
    &nbsp;
    <input type="submit" name="boton" id="boton" value="(">
    &nbsp;
    <input type="submit" name="boton" id="boton" value="<<">
    &nbsp;
    <input type="submit" name="boton" id="boton" value="<">
    &nbsp;
	<input type="submit" name="boton" id="boton" value="Ir A">
	&nbsp;
	<input type="submit" name="boton" id="boton" value=">">
	&nbsp;
	<input type="submit" name="boton" id="boton" value=">>">
	&nbsp;
	<input type="submit" name="boton" id="boton" value=")">
	&nbsp;
	<input type="submit" name="boton" id="boton" value="))">
	&nbsp;
	<input type="submit" name="boton" id="boton" value="]">
	&nbsp;
	<input type="submit" name="boton" id="boton" value="]]">
     
	<br/>
</form>

<%@ page import="jdbc.controlador.ConexionDB" %>
<%@ page import="jdbc.vista.*" %>
<%@ page import="java.sql.*" %>
<%
System.out.println("cargando clase jdbc!");
//Class.forName("com.mysql.jdbc.Driver");
//System.out.println("cargada!");
Connection conn;
try {
	conn=(new ConexionDB()).conectaDB();
	//conn = DriverManager.getConnection("jdbc:mysql://madppvlkin:3306/mysqldesa", "mysqldesa", "mysqld3s4");	
	System.out.println("conectado!");
	DatabaseMetaData dbMetaData = conn.getMetaData();
	String catalog = null;
	String schemaPattern = null;
	String tableNamePattern = "%";
	String[] types = null;
	ResultSet rsT = dbMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);
	PaginacionRS pagina=new PaginacionRS(rsT);
	
	pagina.setNroLinxPag(nrolinxpag);
	System.out.println("Nro de líneas por página: "+pagina.getNroLinxPag());
	pagina.setNroPagxBloq(nropagxbloq);
	System.out.println("Nro de páginas por bloque: "+pagina.getNroPagxBloq());
	pagina.calcularPaginacion();
	 

	String choice = request.getParameter("boton");
	int nropagina=1;
    switch (choice) {
		case "[[":
			nropagina=pagina.primerBloque();
			break;
		case "[":
			nropagina=pagina.bloqueAnterior();
			break;			        
    	case "((":
    		nropagina=pagina.primeraPaginaBloque();
    		break;
    	case "(":
    		nropagina=pagina.paginaAnteriorBloque();
    		break;			        
	    case "<<":
	    	nropagina=pagina.primeraPagina();
            break;
        case "<":
        	nropagina=pagina.paginaAnterior();
            break;
        case ">":
        	nropagina=pagina.siguientePagina();
            break;
        case ">>":
        	nropagina=pagina.ultimaPagina();
            break;
    	case ")":
    		nropagina=pagina.siguientePaginaBloque();
    		break;
    	case "))":
    		nropagina=pagina.ultimaPaginaBloque();
    		break;			        
		case "]":
			nropagina=pagina.siguienteBloque();
			break;			        
		case "]]":
			nropagina=pagina.ultimoBloque();
			break;
        default:
            // Ir A
            //leer los parámetros de la página
			String pag=request.getParameter("pag");
			if (pag==null) {
				pag="1";//String.valueOf(Integer.MAX_VALUE);
			}
        	try {
        	 	nropagina = Integer.parseInt(pag);
        		
        	} catch (NumberFormatException | IndexOutOfBoundsException ex){
        		out.println("Solo números de página válidos! De 1 a "+pagina.getNroPaginas());
        	}
        		
    }
	out.println(pagina.mostrarPaginaHtml(nropagina)); 
	
		out.println(""
				+"<br>&emsp;[[ primer bloque [ bloque anterior ] siguiente bloque ]] último bloque"
				+"<br>&emsp;(( primera página del bloque ( página anterior del bloque ) siguiente página del bloque )) última página del bloque"
				+ "<br>&emsp;<< primera página < pagina anterior > siguiente página >> última página"
				+ "<br>&emsp;Nro.Página"
				+"<br><br>[[ [ (( ( << < Nro.Pag. > >> ) )) ] ]] fin=salir");

	conn.close();

	
} catch (SQLException e) {
	System.out.println("Error SQL! ");
	System.out.println("SQLState="+e.getSQLState());
	System.out.println("ErrorCode="+e.getErrorCode() );
	System.out.println("Message="+e.getMessage() );
	e.printStackTrace();
} finally {
	;
}
%>

</body>
</html>
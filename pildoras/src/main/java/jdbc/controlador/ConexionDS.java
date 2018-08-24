package jdbc.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import jdbc.vista.PaginacionRS;

/**
 * Servlet implementation class ConexionDS
 */
@WebServlet("/ConexionDS")
public class ConexionDS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Definir el DataDource
	@Resource(name="jdbc/mysqldesa", lookup="java:jboss/datasources/MySQLDESA")
	private DataSource dsPool;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConexionDS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter salida=response.getWriter();
		response.setContentType("text/plain");
		
		//crear conexion con db
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		
		try {
			conn=dsPool.getConnection();
			System.out.println("conectado!");
			DatabaseMetaData dbMetaData = conn.getMetaData();
			String catalog = null;
			String schemaPattern = null;
			String tableNamePattern = "%";
			String[] types = null;
			ResultSet rsT = dbMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);
			
			PaginacionRS pagina=new PaginacionRS(rsT);
			
			//pagina.setNroLinxPag(3);
			System.out.println("Nro de líneas por página: "+pagina.getNroLinxPag());
			//pagina.setNroPagxBloq(7);
			System.out.println("Nro de páginas por bloque: "+pagina.getNroPagxBloq());
			
			pagina.calcularPaginacion();
			salida.println(pagina.mostrarPaginaHtml(1));
			/*
			String query= "SELECT * FROM mitabla";
			st=conn.createStatement();
			rs=st.executeQuery(query);
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				String apellidos = rs.getString("apellidos");
				salida.println(nombre+" "+apellidos);
			}*/
		} catch (SQLException sqlex) {
			System.out.println("Error SQL! "+dsPool.toString());
			System.out.println("SQLState="+sqlex.getSQLState());
			System.out.println("ErrorCode="+sqlex.getErrorCode() );
			System.out.println("Message="+sqlex.getMessage() );
			sqlex.printStackTrace();
		} finally {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

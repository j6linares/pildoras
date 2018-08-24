package jdbc.controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import jdbc.modelo.ModeloPanel;
import jdbc.modelo.Panel;

/**
 * Servlet implementation class ControladorPanel
 */
@WebServlet("/paneles")
public class ControladorPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private ModeloPanel modeloPanel;
    
    //Definir el DataDource
  	@Resource(name="jdbc/mysqldesa", lookup="java:jboss/datasources/MySQLDESA")
  	private DataSource pool;
    
  	@Override
	public void init() throws ServletException {
		super.init();
		
		//modeloPanel con el pool de conexiones
		try {
			modeloPanel=new ModeloPanel(pool);
		} catch (Exception ex) {
			throw new ServletException(ex);
		} finally {
		
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String operacion=(String) request.getParameter("operacion");
		if (operacion==null){
			operacion="listar";
		}
		
		switch (operacion) {
		case "listar":
			listarPaneles(request, response);
			break;
		case "nuevo":
			nuevoPanel(request, response);
			break;
		case "crear":
			crearPanel(request, response);
			break;
		case "editar":
			editarPanel(request, response);
			break;
		case "update":
			updatePanel(request, response);
			break;
		case "borrar":
			borrarPanel(request, response);
			break;
		case "delete":
			deletePanel(request, response);
			break;
		default:
			listarPaneles(request, response);
			break;
		}
		
		
	}
	private void nuevoPanel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("datosPanel", modeloPanel.buscarPanel("cPanel"));
		} catch (SQLException sqlex) {
			System.out.println("Error SQL! "+pool.toString());
			System.out.println("SQLState="+sqlex.getSQLState());
			System.out.println("ErrorCode="+sqlex.getErrorCode() );
			System.out.println("Message="+sqlex.getMessage() );
			sqlex.printStackTrace();
		}
		//enviar a la jsp destino
		RequestDispatcher dispatcher=request.getRequestDispatcher("/cPanel.jsp");
		dispatcher.forward(request, response);
		
	}

	private void deletePanel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// enviar el objeto al modelo para
		// delete en bbdd
		try {
			modeloPanel.deletePanel(Integer.parseInt(request.getParameter("id")));
		} catch (SQLException sqlex) {
			System.out.println("Error SQL! "+pool.toString());
			System.out.println("SQLState="+sqlex.getSQLState());
			System.out.println("ErrorCode="+sqlex.getErrorCode() );
			System.out.println("Message="+sqlex.getMessage() );
			sqlex.printStackTrace();
		}
		// volver a listar
		listarPaneles(request, response);		
	}

	private void borrarPanel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// obtener el id de la url
		int id= Integer.parseInt(request.getParameter("id"));
		
		// leer panel de la bd
		// enviar el objeto al modelo
		Panel panel=null;
		try {
			panel=modeloPanel.leerPanel(id);
		
		if (panel==null){
			//si no existe el panel volvemos a la lista
			listarPaneles(request, response);
		} else {
			// pasar como atributo
			request.setAttribute("panel", panel);
			request.setAttribute("datosPanel", modeloPanel.buscarPanel("dPanel"));
			//enviar a la jsp destino
			RequestDispatcher dispatcher=request.getRequestDispatcher("/dPanel.jsp");
			dispatcher.forward(request, response);
		}	
		} catch (SQLException sqlex) {
			System.out.println("Error SQL! "+pool.toString());
			System.out.println("SQLState="+sqlex.getSQLState());
			System.out.println("ErrorCode="+sqlex.getErrorCode() );
			System.out.println("Message="+sqlex.getMessage() );
			sqlex.printStackTrace();
		}
	}


	private void updatePanel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// leer datos del formulario
		String nombre= request.getParameter("nombre");
		String titulo= request.getParameter("titulo");
		String entorno= request.getParameter("entorno");
		String subtitulo= request.getParameter("subtitulo");
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		Date fecha=null;
		try {
			fecha=sdf.parse(request.getParameter("fecha"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String mensaje= request.getParameter("mensaje");
		int pagina=0;
		try {
			pagina=Integer.parseInt(request.getParameter("pagina"));
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
		int paginas=0;
		try {
			paginas=Integer.parseInt(request.getParameter("paginas"));
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
				
		// crear un objeto con los datos
		Panel panel= new Panel(nombre, titulo, entorno, subtitulo, fecha, mensaje, pagina, paginas);
		panel.setId(Integer.parseInt(request.getParameter("id")));
		// enviar el objeto al modelo para
		// update en bbdd
		try {
			modeloPanel.updatePanel(panel);
		} catch (SQLException sqlex) {
			System.out.println("Error SQL! "+pool.toString());
			System.out.println("SQLState="+sqlex.getSQLState());
			System.out.println("ErrorCode="+sqlex.getErrorCode() );
			System.out.println("Message="+sqlex.getMessage() );
			sqlex.printStackTrace();
		}
		// volver a listar
		listarPaneles(request, response);		
	}

	private void editarPanel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// obtener el id de la url
		int id= Integer.parseInt(request.getParameter("id"));
		
		// leer panel de la bd
		// enviar el objeto al modelo
		Panel panel=null;
		try {
			panel=modeloPanel.leerPanel(id);
		
		if (panel==null){
			//si no existe el panel volvemos a la lista
			listarPaneles(request, response);
		} else {
			// pasar como atributo
			request.setAttribute("panel", panel);
			request.setAttribute("datosPanel", modeloPanel.buscarPanel("uPanel"));
			//enviar a la jsp destino
			RequestDispatcher dispatcher=request.getRequestDispatcher("/uPanel.jsp");
			dispatcher.forward(request, response);
		}	
		
		} catch (SQLException sqlex) {
			System.out.println("Error SQL! "+pool.toString());
			System.out.println("SQLState="+sqlex.getSQLState());
			System.out.println("ErrorCode="+sqlex.getErrorCode() );
			System.out.println("Message="+sqlex.getMessage() );
			sqlex.printStackTrace();
		}
	}

	private void crearPanel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// leer datos del formulario
		String nombre= request.getParameter("nombre");
		String titulo= request.getParameter("titulo");
		String entorno= request.getParameter("entorno");
		String subtitulo= request.getParameter("subtitulo");
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		Date fecha=null;
		try {
			fecha=sdf.parse(request.getParameter("fecha"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String mensaje= request.getParameter("mensaje");
		int pagina=0;
		try {
			pagina=Integer.parseInt(request.getParameter("pagina"));
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
		int paginas=0;
		try {
			paginas=Integer.parseInt(request.getParameter("paginas"));
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
		// crear un objeto con los datos
		Panel panel= new Panel(nombre, titulo, entorno, subtitulo, fecha, mensaje, pagina, paginas);
		// enviar el objeto al modelo para
		// insert en bbdd
		try {
			modeloPanel.crearPanel(panel);
		} catch (SQLException sqlex) {
			System.out.println("Error SQL! "+pool.toString());
			System.out.println("SQLState="+sqlex.getSQLState());
			System.out.println("ErrorCode="+sqlex.getErrorCode() );
			System.out.println("Message="+sqlex.getMessage() );
			sqlex.printStackTrace();
		}
		// volver a listar
		listarPaneles(request, response);
		
	}

	private void listarPaneles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Panel> paneles;
		try {
			paneles=modeloPanel.getPaneles();
			//agregar los paneles a la request
			request.setAttribute("listaPaneles", paneles);
			request.setAttribute("datosPanel", modeloPanel.buscarPanel("rPanel"));
			//enviar a la jsp destino
			RequestDispatcher dispatcher=request.getRequestDispatcher("/rPanel.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException sqlex) {
			System.out.println("Error SQL! "+pool.toString());
			System.out.println("SQLState="+sqlex.getSQLState());
			System.out.println("ErrorCode="+sqlex.getErrorCode() );
			System.out.println("Message="+sqlex.getMessage() );
			sqlex.printStackTrace();
			errorSQL(sqlex, request, response);
		}
		
	}

	private void errorSQL(SQLException sqlex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//agregar los paneles a la request
		request.setAttribute("sqlex", sqlex);
		request.setAttribute("datosPanel", new Panel("SQLError"
				,"Error SQL en la aplicación"
				,"SQLException"
				,""
				, new Date()
				,"> Se ha producido un error SQL en la aplicación, revise el log"
				, 0
				, 0));
		//enviar a la jsp destino
		RequestDispatcher dispatcher=request.getRequestDispatcher("/SQLError.jsp");
		dispatcher.forward(request, response);
	}

}

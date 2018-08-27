package jdbc.controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	private static final Logger LOGGER = Logger.getLogger(ControladorPanel.class.getName());
	private static final String DATOS_PANEL = "datosPanel";
	
	private static ModeloPanel modeloPanel;
    
    //Definir el DataDource
  	@Resource(name="jdbc/mysqldesa", lookup="java:jboss/datasources/MySQLDESA")
  	private DataSource pool;
    
  	@Override
	public void init() throws ServletException {
		super.init();
		
		//modeloPanel con el pool de conexiones
		modeloPanel=new ModeloPanel(pool);
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
  	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operacion=request.getParameter("operacion");
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
			request.setAttribute(DATOS_PANEL, modeloPanel.buscarPanel("cPanel"));
		} catch (SQLException sqlex) {
			errorSQL(sqlex, request, response);
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
			errorSQL(sqlex, request, response);
		}
		// volver a listar
		listarPaneles(request, response);		
	}

	private void borrarPanel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		irAPanel("dPanel", request, response);
	}


	private void irAPanel(String nombreJsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
					request.setAttribute(DATOS_PANEL, modeloPanel.buscarPanel(nombreJsp));
					//enviar a la jsp destino
					RequestDispatcher dispatcher=request.getRequestDispatcher("/"+nombreJsp+".jsp");
					dispatcher.forward(request, response);
				}	
				} catch (SQLException sqlex) {
					errorSQL(sqlex, request, response);
				}
		
	}

	private void updatePanel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// crear un objeto con los datos
		Panel panel= leerDatosFormulario(request);
		panel.setId(Integer.parseInt(request.getParameter("id")));
		// enviar el objeto al modelo para
		// update en bbdd
		try {
			modeloPanel.updatePanel(panel);
		} catch (SQLException sqlex) {
			errorSQL(sqlex, request, response);
		}
		// volver a listar
		listarPaneles(request, response);		
	}

	private void editarPanel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		irAPanel("uPanel", request, response);
	}

	private void crearPanel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Panel panel=leerDatosFormulario(request);
		// enviar el objeto al modelo para
		// insert en bbdd
		try {
			modeloPanel.crearPanel(panel);
		} catch (SQLException sqlex) {
			errorSQL(sqlex, request, response);
		}
		// volver a listar
		listarPaneles(request, response);
		
	}

	private Panel leerDatosFormulario(HttpServletRequest request) {
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
					LOGGER.info(e.getMessage());
				}
				String mensaje= request.getParameter("mensaje");
				int pagina=0;
				try {
					pagina=Integer.parseInt(request.getParameter("pagina"));
				} catch (NumberFormatException nfe) {
					LOGGER.info(nfe.getMessage());
				}
				
				int paginas=0;
				try {
					paginas=Integer.parseInt(request.getParameter("paginas"));
				} catch (NumberFormatException nfe) {
					LOGGER.info(nfe.getMessage());
				}
				
				// crear un objeto con los datos
				Panel panel= new Panel(nombre, titulo, entorno, subtitulo, fecha, mensaje, pagina, paginas);
				
		return panel;
	}

	private void listarPaneles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Panel> paneles;
		try {
			paneles=modeloPanel.getPaneles();
			//agregar los paneles a la request
			request.setAttribute("listaPaneles", paneles);
			request.setAttribute(DATOS_PANEL, modeloPanel.buscarPanel("rPanel"));
			//enviar a la jsp destino
			RequestDispatcher dispatcher=request.getRequestDispatcher("/rPanel.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException sqlex) {
			errorSQL(sqlex, request, response);
		}
		
	}

	private void errorSQL(SQLException sqlex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.log(Level.SEVERE, "Error SQL! {0}",pool);
		LOGGER.log(Level.SEVERE, "SQLState="+sqlex.getSQLState());
		LOGGER.log(Level.SEVERE, "ErrorCode="+sqlex.getErrorCode() );
		LOGGER.log(Level.SEVERE, "Message="+sqlex.getMessage() );
		LOGGER.log(Level.SEVERE, "SQLException", sqlex);
		//agregar los paneles a la request
		request.setAttribute("sqlex", sqlex);
		request.setAttribute(DATOS_PANEL, new Panel("SQLError"
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

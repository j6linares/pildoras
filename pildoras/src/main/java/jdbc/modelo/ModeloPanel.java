package jdbc.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

public class ModeloPanel {
	
	private DataSource ds;

	public ModeloPanel(DataSource ds) {
		this.ds = ds;
	}

	public List<Panel> getPaneles() throws SQLException {
		List<Panel> paneles=new ArrayList<>();
		String query="SELECT * FROM panel";
		
		try (Connection conn=ds.getConnection();
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery(query);)
		{
			while(rs.next()){
				Panel panel=new Panel(
						  rs.getString("nombre")
						, rs.getString("titulo")
						, rs.getString("entorno")
						, rs.getString("subtitulo")
						, rs.getDate("fecha")
						, rs.getString("mensaje")
						, rs.getInt("pagina")
						, rs.getInt("paginas")
						);
				panel.setId(rs.getInt("id"));
				paneles.add(panel);
				
			}
		} 
		return paneles;
	}
	
	public void crearPanel(Panel panel) throws SQLException {
		//query sql
		String query="INSERT INTO panel (nombre, titulo, entorno, subtitulo, fecha, mensaje, pagina, paginas)"+
				" VALUES(?,?,?,?,?,?,?,?)";
		
		//conexion con bd
		try (Connection conn=ds.getConnection();
			PreparedStatement pst=conn.prepareStatement(query);)
		{
			//establecer paramentros
			pst.setString(1, panel.getNombre());
			pst.setString(2, panel.getTitulo());
			pst.setString(3, panel.getEntorno());
			pst.setString(4, panel.getSubtitulo());
			
			java.sql.Date fecha=null;
			if (panel.getFecha()!=null) {
				java.util.Date date = panel.getFecha();
				fecha = new java.sql.Date(date.getTime());
			}
			
			pst.setDate(5, fecha);
			pst.setString(6, panel.getMensaje());
			pst.setInt(7, panel.getPagina());
			pst.setInt(8, panel.getPaginas());
			//ejecutar query
			pst.executeUpdate();
		} 
	}

	public Panel leerPanel(int id) throws SQLException {
		Panel panel=null;
		// leer el panel
		String query="SELECT * FROM panel WHERE id=?";
					
		//conectar con bd
		try (Connection conn=ds.getConnection();
			PreparedStatement pst=conn.prepareStatement(query);)
		{
			pst.setInt(1, id);
			try (ResultSet rs=pst.executeQuery();) 
			{
				if (rs.next()){
					panel=new Panel(
							  rs.getString("nombre")
							, rs.getString("titulo")
							, rs.getString("entorno")
							, rs.getString("subtitulo")
							, rs.getDate("fecha")
							, rs.getString("mensaje")
							, rs.getInt("pagina")
							, rs.getInt("paginas")
							);
					panel.setId(rs.getInt("id"));
				}
			}
		} 
		
		//si panel es null no existe en la bd
		return panel;
		
	}

	public Panel buscarPanel(String nombre) throws SQLException {
		Panel panel=null;
		// leer el panel
		String query="SELECT * FROM panel WHERE nombre=?";
		
		//conectar con bd
		try (Connection conn=ds.getConnection();
			PreparedStatement pst=conn.prepareStatement(query);)
		{
			pst.setString(1, nombre);
			try (ResultSet rs=pst.executeQuery();) 
			{
				if (rs.next()){
					panel=new Panel(
							  rs.getString("nombre")
							, rs.getString("titulo")
							, rs.getString("entorno")
							, rs.getString("subtitulo")
							, new Date()
							, rs.getString("mensaje")
							, rs.getInt("pagina")
							, rs.getInt("paginas")
							);
					panel.setId(rs.getInt("id"));
				} else {
					panel=new Panel(nombre
							,""
							,""
							,""
							, new Date()
							,""
							, 0
							, 0);
				}
			}
		} 
		
		//si panel es null no existe en la bd
		return panel;
		
	}

	public void updatePanel(Panel panel) throws SQLException {
		//query sql
		String query="UPDATE panel set nombre=?, titulo=?, entorno=?, subtitulo=?, fecha=?, mensaje=?, pagina=?, paginas=?"+
						" WHERE id=?";
		//conexion con bd
		try (Connection conn=ds.getConnection();
				PreparedStatement pst=conn.prepareStatement(query);) 
		{
			//establecer paramentros
			pst.setString(1, panel.getNombre());
			pst.setString(2, panel.getTitulo());
			pst.setString(3, panel.getEntorno());
			pst.setString(4, panel.getSubtitulo());
					
			java.sql.Date fecha=null;
			if (panel.getFecha()!=null) {
				java.util.Date date = panel.getFecha();
				fecha = new java.sql.Date(date.getTime());
			}
					
			pst.setDate(5, fecha);
			pst.setString(6, panel.getMensaje());
			pst.setInt(7, panel.getPagina());
			pst.setInt(8, panel.getPaginas());
			pst.setInt(9, panel.getId());
			//ejecutar query
			pst.executeUpdate();
		} 
	}
	
	public void deletePanel(int id) throws SQLException {
		//query sql
		String query="DELETE FROM panel "+
						" WHERE id=?";
		//conexion con bd
		try (Connection conn=ds.getConnection();
				PreparedStatement pst=conn.prepareStatement(query);) 
		{
			//establecer paramentros
			pst.setInt(1, id);
			//ejecutar query
			pst.executeUpdate();
		} 
	}
}

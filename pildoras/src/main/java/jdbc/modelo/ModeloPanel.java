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
		List<Panel> paneles=new ArrayList<Panel>();
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		try {
			conn=ds.getConnection();
			st=conn.createStatement();
			String query="SELECT * FROM panel";
			rs=st.executeQuery(query);
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
		} finally {
			if (rs!=null) rs.close();
			if (st!=null) st.close();
			if (conn!=null) conn.close();
		}
		return paneles;
	}
	
	public void crearPanel(Panel panel) throws SQLException {
		Connection conn=null;
		PreparedStatement pst=null;
		try {
			//conexion con bd
			conn=ds.getConnection();
			//query sql
			String query="INSERT INTO panel (nombre, titulo, entorno, subtitulo, fecha, mensaje, pagina, paginas)"+
					" VALUES(?,?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(query);
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
		} finally {
			pst.close();
			conn.close();
		}
	}

	public Panel leerPanel(int id) throws SQLException {
		Panel panel=null;
		Connection conn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			//conectar con bd
			conn=ds.getConnection();
			// leer el panel
			String query="SELECT * FROM panel WHERE id=?";
			pst=conn.prepareStatement(query);
			pst.setInt(1, id);
			rs=pst.executeQuery();
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
		} finally {
			rs.close();
			pst.close();
			conn.close();
		}
		
		//si panel es null no existe en la bd
		return panel;
		
	}

	public Panel buscarPanel(String nombre) throws SQLException {
		Panel panel=null;
		Connection conn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			//conectar con bd
			conn=ds.getConnection();
			// leer el panel
			String query="SELECT * FROM panel WHERE nombre=?";
			pst=conn.prepareStatement(query);
			pst.setString(1, nombre);
			rs=pst.executeQuery();
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
		} finally {
			rs.close();
			pst.close();
			conn.close();
		}
		
		//si panel es null no existe en la bd
		return panel;
		
	}

	public void updatePanel(Panel panel) throws SQLException {
		Connection conn=null;
		PreparedStatement pst=null;
		try {
			//conexion con bd
			conn=ds.getConnection();
			//query sql
			String query="UPDATE panel set nombre=?, titulo=?, entorno=?, subtitulo=?, fecha=?, mensaje=?, pagina=?, paginas=?"+
							" WHERE id=?";
			pst=conn.prepareStatement(query);
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
		} finally {
			pst.close();
			conn.close();
		}
		
	}
	
	public void deletePanel(int id) throws SQLException {
		Connection conn=null;
		PreparedStatement pst=null;
		try {
			//conexion con bd
			conn=ds.getConnection();
			//query sql
			String query="DELETE FROM panel "+
							" WHERE id=?";
			pst=conn.prepareStatement(query);
			//establecer paramentros
			pst.setInt(1, id);
			//ejecutar quer
			pst.executeUpdate();
		} finally {
			pst.close();
			conn.close();
		}
		
	}

}

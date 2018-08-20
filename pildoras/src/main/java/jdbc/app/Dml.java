package jdbc.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.controlador.ConexionDB;
import jdbc.modelo.MetaDatosDB;

public class Dml {
	
	public static void main(String[] args) {
		try {
			// 1. conexion
				Connection conn = (new ConexionDB()).conectaDB();
				MetaDatosDB.mostrarMetaDatosDB(conn);
				
			// 2. statement
				Statement st = conn.createStatement();
				
			// 3. execute
				String query = "INSERT INTO mitabla (nombre, apellidos) VALUES ('Julian', 'Garcia Linares') ";
				System.out.println("numero de filas insertadas "+st.executeUpdate(query));
				query = "UPDATE mitabla SET apellidos = 'García Linares' WHERE nombre = 'Julian'";
				System.out.println("numero de filas actualizadas "+st.executeUpdate(query));
				query = "SELECT * FROM mitabla";
				ResultSet rs = st.executeQuery(query);
			// 4. resultset
				while (rs.next()) {
					if (rs.getRow() == 1) {
						//titulo
						System.out.println("Nombre y Apellidos");
					}
					System.out.println(""+rs.getString("nombre")+" "+rs.getString("apellidos"));
				}
			// 5. PreparedStatement
				query = "SELECT * FROM mitabla WHERE nombre = ?";
				PreparedStatement pst = conn.prepareStatement(query);
				pst.setString(1, "Julian");
				MetaDatosDB.leerRs(pst.executeQuery());
				
			// cerrar statement y conexion
				st.close();
				pst.close();
				conn.close();
				
		} catch (SQLException e) {
			System.out.println("Error SQL! ");
			System.out.println("SQLState="+e.getSQLState());
			System.out.println("ErrorCode="+e.getErrorCode() );
			System.out.println("Message="+e.getMessage() );
			e.printStackTrace();
		} finally {
			
		}

	}

	private static void leerRs(ResultSet rs) throws SQLException {
		if (rs == null) {
			System.out.println("Resulset vacio!");
		} else {
			while (rs.next()) {
				if (rs.getRow() == 1) {
					//titulo
					System.out.println("Nombre y Apellidos");
				}
				System.out.println(""+rs.getString("nombre")+" "+rs.getString("apellidos"));
			}
		}
		
	}

	

}

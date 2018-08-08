package jdbc.modelo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MetaDatosDB {
	
	public static void mostrarMetaDatosDB(Connection conn) throws SQLException {
			DatabaseMetaData dbMetaData = conn.getMetaData();
			System.out.println("Gestor de DB: "+dbMetaData.getDatabaseProductName());
			System.out.println("Version del gestor: "+dbMetaData.getDatabaseProductVersion());
			System.out.println("Driver de DB: "+dbMetaData.getDriverName());
			System.out.println("Version del driver: "+dbMetaData.getDriverVersion());
			
			String catalog = null;
			String schemaPattern = null;
			String tableNamePattern = "mi%";
			String[] types = null;
			ResultSet rsT = dbMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);
			leerRs(rsT);
			
			String columnNamePattern = null;
			ResultSet rsC = dbMetaData.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
			leerRs(rsC);

	}
	
	private static void leerRs(ResultSet rs) throws SQLException {
		
		if (rs == null) {
			System.out.println("Resulset vacio!");
		} else {
			ResultSetMetaData rsMetaData = rs.getMetaData();
			System.out.println("Columnas del rs:"+rsMetaData.getColumnCount());
			
			while (rs.next()) {
				if (rs.getRow() == 1) {
					//titulos
					System.out.println("Filas del result set:");
					for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
						System.out.print("\t"+rsMetaData.getColumnName(i));
					}
					System.out.print("\n");
				}
				for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
					System.out.print("\t"+rs.getString(rsMetaData.getColumnName(i)));
				}
				System.out.print("\n");
				
			}
		}
		
	}

}

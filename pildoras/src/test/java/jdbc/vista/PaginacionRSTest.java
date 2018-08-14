package jdbc.vista;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import jdbc.controlador.ConexionDB;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaginacionRSTest {
	
	private Connection conn;
	
	@Before
	public void inicio(){
		System.out.println("inicio test");
		try {
			conn=(new ConexionDB()).conectaDB("jdbc:mysql://madppvlkin:3306/mysqldesa", "mysqldesa", "mysqld3s4");
			System.out.println("conectado!");
		} catch (SQLException e) {
			System.out.println("Error SQL! ");
			System.out.println("SQLState="+e.getSQLState());
			System.out.println("ErrorCode="+e.getErrorCode() );
			System.out.println("Message="+e.getMessage() );
			e.printStackTrace();
		}
		
	}
	@After
	public void fin() throws SQLException{
		System.out.println("fin test");
		
		if	(conn!=null || !conn.isClosed())
			conn.close();
		
		
	}
	
	@Test
	public void test1() throws SQLException {
		assertTrue(conn.getAutoCommit());
		
	}
	
	@Test(expected=MySQLSyntaxErrorException.class)
	public void test2ConectaDBMySQLSyntaxErrorException() throws SQLException {
		conn=(new ConexionDB()).conectaDB("db");
		fail();
		
		
	}
	
	@Test(expected=SQLException.class)
	public void test3ConectaDBException() throws SQLException {
		conn=(new ConexionDB()).conectaDB("mysqldesa","localhost");
		fail();
		
	}
	
	@Test
	public void test4() throws SQLException {
		DatabaseMetaData dbMetaData = conn.getMetaData();
		assertTrue("Gestor de DB: "+dbMetaData.getDatabaseProductName()
					, "MySQL".equals(dbMetaData.getDatabaseProductName()));
		System.out.println("Version del gestor: "+dbMetaData.getDatabaseProductVersion());
		System.out.println("Driver de DB: "+dbMetaData.getDriverName());
		System.out.println("Version del driver: "+dbMetaData.getDriverVersion());
		
		String catalog = null;
		String schemaPattern = null;
		String tableNamePattern = "%";
		String[] types = null;
		ResultSet rsT = dbMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);
		PaginacionRS pagina=new PaginacionRS(rsT);
		pagina.setNroLinxPag(3);
		pagina.setNroPagxBloq(7);
		pagina.calcularPaginacion();
		System.out.println("Paginacion de filas:");
		for (int f = 1; f <= pagina.getNroFilas(); f++) {
			pagina.irAFila(f);
			System.out.println("Fila "+pagina.getNroFila()+" >>"+(pagina.toString()));
		}
		System.out.println("Paginacion de páginas:");
		for (int p = 1; p <= pagina.getNroPaginas(); p++) {
			pagina.irAPagina(p);
			System.out.println("Pag. "+pagina.getNroPagina()+" >>"+(pagina.toString()));
			System.out.println("Paginacion de líneas de la página: "+pagina.getNroPagina());
			for (int l = 1; l <= pagina.getNroLinxPag(); l++) {
				pagina.irALinea(l);
				System.out.println("\tLin. "+pagina.getNroLineaPagina()+" >>"+(pagina.toString()));
				//puede q no esten todas las lineas de la página rellenas
				if (!(pagina.getNroFila() < pagina.getNroFilas())){
					System.out.println("\tFin de cursor");
					break;
				}
			}
		}
		System.out.println("Paginacion de bloques:");
		for (int b = 1; b <= pagina.getNroBloques(); b++) {
			pagina.irABloque(b);
			System.out.println("Bloq. "+pagina.getNroBloque()+" >>"+(pagina.toString()));
			System.out.println("Paginacion de páginas del bloque: "+pagina.getNroBloque());
			for (int p = 1; p <= pagina.getNroPaginasBloque(); p++) {
				pagina.irAPaginaBloque(p);
				System.out.println("\tPag. "+pagina.getNroPaginaBloque()+" >>"+(pagina.toString()));
				System.out.println("\tPaginacion de líneas de la página: "+pagina.getNroPaginaBloque());
				for (int l = 1; l <= pagina.getNroLinxPag(); l++) {
					pagina.irALinea(l);
					System.out.println("\t\tLin. "+pagina.getNroLineaPagina()+" >>"+(pagina.toString()));
					//puede q no esten todas las lineas de la página rellenas
					if (!(pagina.getNroFila() < pagina.getNroFilas())){
						System.out.println("\t\tFin de cursor");
						break;
					}
				}
			}
		}
		
//		String columnNamePattern = null;
//		ResultSet rsC = dbMetaData.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
//		leerRs(rsC);
		assertTrue(true);
		
	}
	
	@Test
	public void test5() throws SQLException {
		DatabaseMetaData dbMetaData = conn.getMetaData();
		assertTrue("Gestor de DB: "+dbMetaData.getDatabaseProductName()
					, "MySQL".equals(dbMetaData.getDatabaseProductName()));
		System.out.println("Version del gestor: "+dbMetaData.getDatabaseProductVersion());
		System.out.println("Driver de DB: "+dbMetaData.getDriverName());
		System.out.println("Version del driver: "+dbMetaData.getDriverVersion());
		
		String catalog = null;
		String schemaPattern = null;
		String tableNamePattern = "%";
		String[] types = null;
		ResultSet rsT = dbMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);
		PaginacionRS pagina=new PaginacionRS(rsT);
		pagina.setNroLinxPag(3);
		pagina.setNroPagxBloq(7);
		pagina.calcularPaginacion();
		
		System.out.println("Paginacion de páginas:");
		for (int p = 1; p <= pagina.getNroPaginas(); p++) {
			pagina.mostrarPagina(p);
		}
		
		assertTrue(true);
		
	}

}

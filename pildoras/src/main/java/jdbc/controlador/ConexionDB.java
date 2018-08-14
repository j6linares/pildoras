package jdbc.controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
	
	private Connection conn=null;
	private static String url="jdbc:mysql://madppvlkin:3306/mysqldesa";
	private static String driver = "jdbc";
	private static String protocolo = "mysql";
	private static String servidorDB = "madppvlkin";
	private static String puertoDB = "3306";
	private static String DB = "mysqldesa";
	private static String usuarioDB = "mysqldesa";
	private static String passwdDB = "mysqld3s4";
	
	public ConexionDB() {
		
	}
	
	
	public Connection conectaDB(String url, String usuario, String passwd) throws SQLException {
		// 1. conexion
		conn = DriverManager.getConnection(url, usuario, passwd);	
		return conn;
	}
	
	public Connection conectaDB() throws SQLException {
		url = driver+":"+protocolo+"://"+servidorDB+":"+puertoDB+"/"+DB;
		
		return conectaDB(url, usuarioDB, passwdDB);
	}
	
	public Connection conectaDB(String db) throws SQLException {
		url = driver+":"+protocolo+"://"+servidorDB+":"+puertoDB+"/"+db;
		DB=db;
		
		return conectaDB(url, usuarioDB, passwdDB);
	}
	
	public Connection conectaDB(String servidor, String db) throws SQLException {
		url = driver+":"+protocolo+"://"+servidor+":"+puertoDB+"/"+db;
		DB=db;
		servidorDB=servidor;
		
		return conectaDB(url, usuarioDB, passwdDB);
	}
	
	public Connection conectaDB(String servidor, String puerto, String db, String usuario, String passwd) throws SQLException {
		url = driver+":"+protocolo+"://"+servidor+":"+puerto+"/"+db;
		servidorDB=servidor;
		puertoDB=puerto;
		DB=db;
		
		return conectaDB(url, usuario, passwd);
	}
}

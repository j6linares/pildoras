package jdbc.vista;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class PaginacionRS {
	//cursor
	private ResultSet rs;
	private ResultSetMetaData rsMetaData;
	private String cursor;
	
	//configuración de la paginación 
	private int nroLinxPag;
	private int nroPagxBloq;
	
	//datos de posición
	private int nroBloque;
	private int nroPagina;
	private int nroLinea;
	private int nroFila;
	
	// datos del cursor
	private int nroColumnas;
	private int nroFilas;
	private int nroPaginas;
	private int nroBloques;
	
	//constructor
	public PaginacionRS(ResultSet rs) throws SQLException {
		//cursor
		this.rs=rs;
		rsMetaData=rs.getMetaData();
		cursor=rs.getCursorName();
		
		//configuracion por defecto
		nroLinxPag=10;
		nroPagxBloq=20;
		
		//datos del cursor 
		nroColumnas=rsMetaData.getColumnCount();
		
		//posicionamos el curosr al ppio
		rs.beforeFirst();
		
		if (!rs.next()) {
			//no hay datos en el cursor
			nroBloques=0;
			nroPaginas=0;
			nroFilas=0;
			
			//datos de posición
			nroPagina=0;
			nroLinea=0;
			nroFila=0;
		} else {
			//estoy en el first=beforeFirst()+next()
			nroFila=rs.getRow();
			if (rs.last()){
				nroFilas=rs.getRow();
			}
			nroPaginas=(nroFilas/nroLinxPag)+1;
			nroBloques=(nroPaginas/nroPagxBloq)+1;
			posicionNroFila(nroFila);
			
			
		}
		
	}
	
	//lógica
	private void posicionNroBloque(int nroBloque) throws SQLException {
		if (nroBloque==0) {
			throw new IndexOutOfBoundsException("Nº de bloque fuera de límite inferior");
		}
		//nro de fila de la primera linea del bloque
		posicionNroFila((nroBloque-1)*(nroLinxPag * nroPagxBloq));
	}
	
	private void posicionNroPagina(int nroPagina) throws SQLException {
		if (nroPagina==0 || nroPagina>nroPagxBloq) {
			throw new IndexOutOfBoundsException("Nº de página fuera de los límites del bloque");
		}
		//nro de fila de la primera linea de la página del bloque
		posicionNroFila(((nroBloque-1)*(nroLinxPag * nroPagxBloq))+(nroPagina*nroLinxPag));
	}
	
	private void posicionNroLinea(int nroLinea) throws SQLException {
		if (nroLinea==0 || nroLinea>nroLinxPag) {
			throw new IndexOutOfBoundsException("Nº de línea fuera de los límites de la página");
		}
		//nro de fila de la linea de la página del bloque
		posicionNroFila(((nroBloque-1)*(nroLinxPag * nroPagxBloq))+(nroPagina*nroLinxPag)+nroLinea);
	}
	
	private void posicionNroFila(int nroFila) throws SQLException {
		if (nroFila==0) {
			nroBloque=0;
			nroPagina=0;
			nroLinea=0;
		} else if (nroFila>nroFilas) {
			throw new IndexOutOfBoundsException("Nº de fila fuera de límite superior");
		} else {
			int nroFilasxBloque=(nroLinxPag * nroPagxBloq); 
			nroBloque = (nroFila/nroFilasxBloque)+1;
			nroPagina = nroFila-((nroBloque-1)*nroFilasxBloque);
			nroLinea = nroFila-((nroBloque-1)*nroFilasxBloque)-((nroPagina-1)*nroLinxPag);
			rs.absolute(nroLinea);
		}
	}

	//getters+setters
	//cursor
	public String getCursor() {
		return cursor;
	}
	//configura paginacion
	public int getNroLinxPag() {
		return nroLinxPag;
	}
	public void setNroLinxPag(int nroLinxPag) {
		this.nroLinxPag = nroLinxPag;
	}

	public int getNroPagxBloq() {
		return nroPagxBloq;
	}
	public void setNroPagxBloq(int nroPagxBloq) {
		this.nroPagxBloq = nroPagxBloq;
	}

	//datos del cursor
	public int getNroColumnas() {
		return nroColumnas;
	}
	public int getNroFilas() {
		return nroFilas;
	}
	public int getNroPaginas() {
		return nroPaginas;
	}
	public int getNroBloques() {
		return nroBloques;
	}
	
	//datos de posicion
	public int getNroBloque() {
		return nroBloque;
	}
	public int getNroPagina() {
		return nroPagina;
	}
	public int getNroLinea() {
		return nroLinea;
	}
	public int getNroFila() {
		return nroFila;
	}
	
	//toString
	@Override
	public String toString() {
		return "PaginacionRS [cursor=" + cursor + ", nroLinxPag=" + nroLinxPag + ", nroPagxBloq=" + nroPagxBloq
				+ ", nroBloque=" + nroBloque + ", nroPagina=" + nroPagina + ", nroLinea=" + nroLinea + ", nroFila="
				+ nroFila + ", nroColumnas=" + nroColumnas + ", nroFilas=" + nroFilas + ", nroPaginas=" + nroPaginas
				+ ", nroBloques=" + nroBloques + "]";
	}

}

package jdbc.vista;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class PaginacionRS {
	//cursor
	private ResultSet rs;
	private ResultSetMetaData rsMetaData;
	private String cursor;
	
	//configuraci�n de la paginaci�n 
	private int nroLinxPag;
	private int nroPagxBloq;
	
	//datos de posici�n
	private int nroBloque;
	private int nroPagina;
	private int nroPaginaBloque;
	private int nroPaginasBloque;
	private int nroLineaPagina;
	private int nroLineasPagina;
	private int nroFila;
	
	// datos del cursor
	private int nroColumnas;
	private int nroFilas;
	
	// datos de la paginaci�n
	private int nroBloques;
	private int nroPaginas;
	
	//constructor
	public PaginacionRS(ResultSet rs) throws SQLException {
		//cursor
		this.rs=rs;
		rsMetaData=rs.getMetaData();
		//cursor=rs.getCursorName();
		
		//configuracion por defecto
		nroLinxPag=10;
		nroPagxBloq=20;
		
		//datos del cursor 
		nroColumnas=rsMetaData.getColumnCount();
		calcularPaginacion();

	}
	
	
	//metodos
	public void recalcularPaginacion(int pagxbloq, int linxpag) throws SQLException{
		if (pagxbloq==0 || linxpag==0){
			throw new IndexOutOfBoundsException("N� de p�ginas x bloque o lineas x pagina fuera de l�mite inferior");
		} else {
			this.nroPagxBloq=pagxbloq;
			this.nroLinxPag=linxpag;
			calcularPaginacion();
		}
	
	}
	public void calcularPaginacion() throws SQLException{
		//posicionamos el cursor al ppio
		rs.beforeFirst();
		
		if (!rs.next()) {
			//no hay datos en el cursor
			nroBloques=0;
			nroPaginas=0;
			nroFilas=0;
			
			//datos de posici�n
			nroBloque=0;
			nroPaginaBloque=0;
			nroPaginasBloque=0;
			nroPagina=0;
			nroLineaPagina=0;
			nroLineasPagina=0;
			nroFila=0;
		} else {
			//estoy en el first=beforeFirst()+next()
			nroFila=rs.getRow();
			if (rs.last()){
				nroFilas=rs.getRow();
			}
			nroPaginas=(nroFilas/nroLinxPag);
			if (nroFilas%nroLinxPag != 0) {
				nroPaginas++;
			}
			nroBloques=(nroPaginas/nroPagxBloq);
			if (nroPaginas%nroPagxBloq != 0) {
				nroBloques++;
			}
			posicionNroFila(nroFila);
		}	
	}
	
	public void irABloque(int bloq) throws SQLException {
		//posicionNroFila((bloq-1)*(nroLinxPag*nroPagxBloq)+1);
		posicionNroBloque(bloq);
	}
	
	public void irAPagina(int pag) throws SQLException {
		//posicionNroFila((pag-1)*nroLinxPag+1);
		posicionNroPagina(pag);
	}
	
	public void irAPaginaBloque(int pag) throws SQLException {
		//irAPagina((nroBloque-1)*nroPagxBloq+pag);
		posicionNroPaginaBloque(pag);
	}
	
	public void irALinea(int linea) throws SQLException {
		posicionNroLinea(linea);
	}
	
	public void irAFila(int fila) throws SQLException {
		posicionNroFila(fila);
	}
	

	//l�gica
	private void posicionNroBloque(int nroBloque) throws SQLException {
		if (nroBloque==0 || nroBloque>nroBloques) {
			throw new IndexOutOfBoundsException("N� de bloque fuera de l�mites");
		}
		//nro de fila de la primera linea del bloque
		posicionNroFila((nroBloque-1)*(nroLinxPag * nroPagxBloq)+1);
	}
	
	private void posicionNroPagina(int nroPagina) throws SQLException {
		if (nroPagina==0 || nroPagina>nroPaginas) {
			throw new IndexOutOfBoundsException("N� de p�gina fuera de los l�mites");
		}
		//nro de fila de la primera linea de la p�gina del bloque
		posicionNroFila(((nroPagina-1)*nroLinxPag)+1);
	}
	
	private void posicionNroPaginaBloque(int nroPagina) throws SQLException {
		if (nroPagina==0 || nroPagina>nroPaginasBloque) {
			throw new IndexOutOfBoundsException("N� de p�gina fuera de los l�mites del bloque");
		}
		//nro de fila de la primera linea de la p�gina del bloque
		posicionNroFila(((nroBloque-1)*(nroLinxPag * nroPagxBloq))+((nroPagina-1)*nroLinxPag)+1);
	}
	
	private void posicionNroLinea(int nroLinea) throws SQLException {
		if (nroLinea==0 || nroLinea>nroLineasPagina) {
			throw new IndexOutOfBoundsException("N� de l�nea fuera de los l�mites de la p�gina");
		}
		//nro de fila de la linea de la p�gina del bloque
		posicionNroFila(((nroBloque-1)*(nroLinxPag * nroPagxBloq))+((nroPaginaBloque-1)*nroLinxPag)+nroLinea);
	}
	
	private void posicionNroFila(int nroFila) throws SQLException {
		if (nroFila==0 || nroFila>nroFilas) {
			nroBloque=0;
			nroPaginaBloque=0;
			nroPaginasBloque=0;
			nroPagina=0;
			nroLineaPagina=0;
			nroLineasPagina=0;
			throw new IndexOutOfBoundsException("N� de fila fuera de los l�mites");
		} else {
			//calcula los datos de posicion de una fila
		
			//posicionamos la fila
			this.nroFila=nroFila;
			
			//posicionamos el bloque donde est� ubicada la fila
			this.nroBloque=bloqueDeFila(nroFila);
			
//			int nroFilasxBloque=(this.nroLinxPag * this.nroPagxBloq); 
//			nroBloque = (nroFila/nroFilasxBloque);
//			if (nroFila%nroFilasxBloque != 0){
//				nroBloque++;
//			}
			
			
			//calculamos n�mero de paginas del bloque 
			this.nroPaginasBloque=paginasDelBloque(this.nroBloque);
			
//			this.nroPaginasBloque=nroPagxBloq;
//			if (nroBloque == nroBloques) {
//				//ultimo bloque
//				int nroFilasBloque=(nroFilas-(nroPagxBloq*nroLinxPag*(nroBloque-1)));
//				this.nroPaginasBloque=nroFilasBloque/nroLinxPag;
//				if (nroFilasBloque%nroPagxBloq != 0) {
//					this.nroPaginasBloque++;
//				}
//			}
			
			//posicionamos la p�gina dentro del bloque
			this.nroPaginaBloque=paginaDeBloque(nroFila);
			
//			nroPaginaBloque = (nroFila-((nroBloque-1)*nroFilasxBloque))/nroLinxPag;
//			if ((nroFila-((nroBloque-1)*nroFilasxBloque))%nroLinxPag != 0){
//				nroPaginaBloque++;
//			}
			
			//posicionamos la p�gina absoluta
			this.nroPagina=paginaDeFila(nroFila);
//			nroPagina = nroFila/nroLinxPag;
//			if (nroFila%nroLinxPag != 0){
//				nroPagina++;
//			}
					
			//posicionamos la linea de la p�gina
			this.nroLineaPagina=lineaDePagina(nroFila);
//			nroLineaPagina = nroFila-((nroBloque-1)*nroFilasxBloque)-((nroPaginaBloque-1)*nroLinxPag);
			
			//calculamos el n�mero de lineas de la ultima p�gina del ultimo bloque
			this.nroLineasPagina=lineasDePagina(this.nroPaginaBloque);
//			nroLineasPagina=nroLinxPag;
//			if (nroBloque == nroBloques && nroPaginaBloque == nroPaginasBloque) {
//				//ultimo p�gina del �ltimo bloque 
//				nroLineasPagina=(nroFilas
//						-(nroPagxBloq*nroLinxPag*(nroBloque-1)) //filas de bloques anteriores
//						-((nroPaginaBloque-1)*nroLinxPag) //filas de pags anteriores del �ltimo bloque 
//						) //+ (nroLineaPagina-1)
//						;
//			}
			
			//posicionamos el cursor en la fila
			rs.absolute(nroFila);
			
		}
	}

	private int lineasDePagina(int paginaBloque) {
		int lineasPag=this.nroLinxPag;
		if (this.nroBloque == this.nroBloques && this.nroPaginaBloque == this.nroPaginasBloque) {
			//ultimo p�gina del �ltimo bloque 
			lineasPag=this.nroFilas
					-(this.nroPagxBloq*this.nroLinxPag*(this.nroBloque-1)) //filas de bloques anteriores
					-((paginaBloque-1)*this.nroLinxPag) //filas de pags anteriores del �ltimo bloque 
					;
		}
		return lineasPag;

	}


	private int lineaDePagina(int fila) {
		
		int filasxBloque=(this.nroLinxPag * this.nroPagxBloq); 
		return fila
				-((this.nroBloque-1)*filasxBloque)
				-((this.nroPaginaBloque-1)*this.nroLinxPag)
				;
	}


	private int paginaDeFila(int fila) {
		int pag = fila/this.nroLinxPag;
		if (fila%this.nroLinxPag != 0){
			pag++;
		}
		return pag;
	}


	private int paginaDeBloque(int fila) {
		
		int filasxBloque=(this.nroLinxPag * this.nroPagxBloq); 
		int pagBloq = (fila-((this.nroBloque-1)*filasxBloque))/this.nroLinxPag;
		if ((fila-((this.nroBloque-1)*filasxBloque))%this.nroLinxPag != 0){
			pagBloq++;
		}
		return pagBloq;
	}


	private int paginasDelBloque(int bloque) {
		
		int pagsBloq=this.nroPagxBloq;
		if (bloque == this.nroBloques) {
			//ultimo bloque
			int filasBloque=(this.nroFilas-(this.nroPagxBloq*this.nroLinxPag*(bloque-1)));
			pagsBloq=filasBloque/this.nroLinxPag;
			if (filasBloque%this.nroPagxBloq != 0) {
				pagsBloq++;
			}
		}
		return pagsBloq;
	}
		 
	private int bloqueDeFila(int fila) {
		
		int nroFilasxBloque=(this.nroLinxPag * this.nroPagxBloq); 
		int bloque = (fila / nroFilasxBloque);
		if (fila % nroFilasxBloque != 0){
			bloque++;
		}
		return bloque;
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
	public int getNroPaginaBloque() {
		return nroPaginaBloque;
	}
	public int getNroPaginasBloque() {
		return nroPaginasBloque;
	}
	public int getNroPagina() {
		return nroPagina;
	}
	public int getNroLineaPagina() {
		return nroLineaPagina;
	}
	public int getNroLineasPagina() {
		return nroLineasPagina;
	}
	public int getNroFila() {
		return nroFila;
	}

	//toString
	@Override
	public String toString() {
		return "PaginacionRS ["
				+ ", nroFila=" + nroFila
				+ ", nroFilas=" + nroFilas 
				+ ", nroLineaPagina=" + nroLineaPagina 
				+ ", nroLineasPagina=" + nroLineasPagina 
				+ ", nroPagina=" + nroPagina
				+ ", nroPaginas=" + nroPaginas 
				+ ", nroPaginaBloque=" + nroPaginaBloque 
				+ ", nroPaginasBloque=" + nroPaginasBloque
				+ ", nroBloque=" + nroBloque
				+ ", nroBloques=" + nroBloques
				+ ", nroLinxPag=" + nroLinxPag 
				+ ", nroPagxBloq=" + nroPagxBloq 
				+ ", nroColumnas=" + nroColumnas 
				+ "]";
	}
	
	
}

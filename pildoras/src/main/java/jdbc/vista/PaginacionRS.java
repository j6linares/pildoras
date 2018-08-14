package jdbc.vista;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class PaginacionRS implements Paginable {
	//cursor
	private ResultSet rs;
	private ResultSetMetaData rsMetaData;
	private String cursor;
	
	//configuración de la paginación 
	private int nroLinxPag;
	private int nroPagxBloq;
	private boolean circular;
	
	//datos de posición
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
	
	// datos de la paginación
	private int nroBloques;
	private int nroPaginas;
	
	//constructor
	public PaginacionRS(ResultSet rs) throws SQLException {
		//cursor
		this.rs=rs;
		rsMetaData=rs.getMetaData();
		//cursor=rs.getCursorName();
		
		//configuracion por defecto
		nroLinxPag=Integer.MAX_VALUE;
		nroPagxBloq=Integer.MAX_VALUE;
		circular=false;
		
		//datos del cursor 
		nroColumnas=rsMetaData.getColumnCount();
		calcularPaginacion();

	}
	
	//metodos
	public void recalcularPaginacion(int pagxbloq, int linxpag) throws SQLException{
		if (pagxbloq==0 || linxpag==0){
			throw new IndexOutOfBoundsException("Nº de páginas x bloque o lineas x pagina fuera de límite inferior");
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
			
			//datos de posición
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
	@Override
	public void irABloque(int bloq) {
		posicionNroBloque(bloq);
	}
	@Override
	public void irAPagina(int pag) {
		posicionNroPagina(pag);
	}
	@Override
	public void irAPaginaBloque(int pag) {
		posicionNroPaginaBloque(pag);
	}
	
	public void irALinea(int linea) throws SQLException {
		posicionNroLinea(linea);
	}
	
	public void irAFila(int fila) throws SQLException {
		posicionNroFila(fila);
	}
	
	public void mostrarPaginaBloque(int pagBloque) throws SQLException {
		mostrarPagina((this.nroBloque-1)*this.nroPagxBloq+pagBloque);
		
	}
	public void mostrarPrimeraPaginaBloque(int bloque) throws SQLException {
		mostrarPagina((bloque-1)*this.nroPagxBloq+1);
		
	}
	
	public void mostrarPagina(int pag) throws SQLException {
		irAPagina(pag);
		System.out.println("Bloque: "+this.nroBloque+" de "+this.nroBloques
		+" con "+this.nroPaginasBloque+" páginas");
		System.out.println("Página de Bloque: "+this.nroPaginaBloque+" de "+this.nroPaginasBloque);
		System.out.println("Página: "+pag+" de "+this.getNroPaginas()
			+" con "+this.nroColumnas+" columnas");
		for (int l = 1; l <= this.getNroLineasPagina(); l++) {
			if (l == 1) {
				//cabecera de datos
				for (int c = 1; c <= this.nroColumnas; c++) {
					System.out.print("\t\t"+rsMetaData.getColumnName(c));
				}
				System.out.print("\n");
			} 
			System.out.print("\t"+this.nroFila+" ");
			//datos de línea
			for (int c = 1; c <= this.nroColumnas; c++) {
				System.out.print("\t"+rs.getString(rsMetaData.getColumnName(c)));
			}
			System.out.print("\n");
			if (l < this.getNroLineasPagina())
				irAFila(this.nroFila+1);
		}
	}
	@Override
	public int primeraPagina(){
		if (this.nroPaginas>0)
			return 1;
		else return this.nroPaginas;
	}
	@Override
	public int siguientePagina(){
		if (this.nroPagina<this.nroPaginas)
			return this.nroPagina+1;
		else if (circular)
			return primeraPagina(); //paginación circular
		else 
			return this.nroPagina;
	}
	@Override
	public int paginaAnterior(){
		if (this.nroPagina>1)
			return this.nroPagina-1;
		else if (circular) 
			return ultimaPagina(); //paginación circular
		else 
			return this.nroPagina;
	}
	@Override
	public int ultimaPagina(){
		return this.nroPaginas;
	}
	@Override
	public int primeraPaginaBloque(){
		if (this.nroPaginasBloque>0)
			return 1;
		else return this.nroPaginasBloque;
	}
	@Override
	public int siguientePaginaBloque(){
		if (this.nroPaginaBloque<this.nroPaginasBloque)
			return this.nroPaginaBloque+1;
		else if (circular) 
			return primeraPaginaBloque(); //paginación circular
		else 
			return this.nroPaginaBloque;
	}
	@Override
	public int paginaAnteriorBloque(){
		if (this.nroPaginaBloque>1)
			return this.nroPaginaBloque-1;
		else if (circular) 
			return ultimaPaginaBloque(); //paginación circular
		else
			return this.nroPaginaBloque;
	}
	@Override
	public int ultimaPaginaBloque(){
		return this.nroPaginasBloque;
	}
	@Override
	public int primerBloque(){
		if (this.nroBloques>0)
			return 1;
		else return this.nroBloques;
	}
	@Override
	public int siguienteBloque(){
		if (this.nroBloque<this.nroBloques)
			return this.nroBloque+1;
		else if (circular) 
			return primerBloque(); //paginación circular
		else
			return this.nroBloque;
	}
	@Override
	public int bloqueAnterior(){
		if (this.nroBloque>1)
			return this.nroBloque-1;
		else if (circular) 
			return ultimoBloque(); //paginación circular
		else 
			return this.nroBloque;
	}
	@Override
	public int ultimoBloque(){
		return this.nroBloques;
	}
	//lógica
	private void posicionNroBloque(int nroBloque) {
		if (nroBloque==0 || nroBloque>nroBloques) {
			throw new IndexOutOfBoundsException("Nº de bloque fuera de límites");
		}
		//nro de fila de la primera linea del bloque
		posicionNroFila((nroBloque-1)*(nroLinxPag * nroPagxBloq)+1);
	}
	
	private void posicionNroPagina(int nroPagina) {
		if (nroPagina==0 || nroPagina>nroPaginas) {
			throw new IndexOutOfBoundsException("Nº de página fuera de los límites");
		}
		//nro de fila de la primera linea de la página del bloque
		posicionNroFila(((nroPagina-1)*nroLinxPag)+1);
	}
	
	private void posicionNroPaginaBloque(int nroPagina) {
		if (nroPagina==0 || nroPagina>nroPaginasBloque) {
			throw new IndexOutOfBoundsException("Nº de página fuera de los límites del bloque");
		}
		//nro de fila de la primera linea de la página del bloque
		posicionNroFila(((nroBloque-1)*(nroLinxPag * nroPagxBloq))+((nroPagina-1)*nroLinxPag)+1);
	}
	
	private void posicionNroLinea(int nroLinea) {
		if (nroLinea==0 || nroLinea>nroLineasPagina) {
			throw new IndexOutOfBoundsException("Nº de línea fuera de los límites de la página");
		}
		//nro de fila de la linea de la página del bloque
		posicionNroFila(((nroBloque-1)*(nroLinxPag * nroPagxBloq))+((nroPaginaBloque-1)*nroLinxPag)+nroLinea);
	}
	
	private void posicionNroFila(int nroFila) {
		if (nroFila==0 || nroFila>nroFilas) {
			nroBloque=0;
			nroPaginaBloque=0;
			nroPaginasBloque=0;
			nroPagina=0;
			nroLineaPagina=0;
			nroLineasPagina=0;
			throw new IndexOutOfBoundsException("Nº de fila fuera de los límites");
		} else {
			//calcula los datos de posicion de una fila
		
			//posicionamos la fila
			this.nroFila=nroFila;
			
			//posicionamos el bloque donde está ubicada la fila
			this.nroBloque=bloqueDeFila(nroFila);
			
			//calculamos número de paginas del bloque 
			this.nroPaginasBloque=paginasDelBloque(this.nroBloque);
			
			//posicionamos la página dentro del bloque
			this.nroPaginaBloque=paginaDeBloque(nroFila);
			
			//posicionamos la página absoluta
			this.nroPagina=paginaDeFila(nroFila);
					
			//posicionamos la linea de la página
			this.nroLineaPagina=lineaDePagina(nroFila);
			
			//calculamos el número de lineas de la ultima página del ultimo bloque
			this.nroLineasPagina=lineasDePagina(this.nroPaginaBloque);
			
			//posicionamos el cursor en la fila
			try {
				rs.absolute(nroFila);
			} catch (SQLException sqle) {
				throw new IndexOutOfBoundsException(sqle.getMessage());
			}
			
		}
	}

	private int lineasDePagina(int paginaBloque) {
		int lineasPag=this.nroLinxPag;
		if (this.nroBloque == this.nroBloques && this.nroPaginaBloque == this.nroPaginasBloque) {
			//ultimo página del último bloque 
			lineasPag=this.nroFilas
					-(this.nroPagxBloq*this.nroLinxPag*(this.nroBloque-1)) //filas de bloques anteriores
					-((paginaBloque-1)*this.nroLinxPag) //filas de pags anteriores del último bloque 
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
			if (filasBloque%this.nroLinxPag != 0) {
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
	@Override
	public int getNroColumnas() {
		return nroColumnas;
	}
	@Override
	public int getNroFilas() {
		return nroFilas;
	}
	@Override
	public int getNroPaginas() {
		return nroPaginas;
	}
	@Override
	public int getNroBloques() {
		return nroBloques;
	}
	
	//datos de posicion
	@Override
	public int getNroBloque() {
		return nroBloque;
	}
	@Override
	public int getNroPaginaBloque() {
		return nroPaginaBloque;
	}
	@Override
	public int getNroPaginasBloque() {
		return nroPaginasBloque;
	}
	@Override
	public int getNroPagina() {
		return nroPagina;
	}
	@Override
	public int getNroLineaPagina() {
		return nroLineaPagina;
	}
	@Override
	public int getNroLineasPagina() {
		return nroLineasPagina;
	}
	@Override
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

	@Override
	public int getNroPaginasxBloque() {
		return this.nroPagxBloq;
	}

	@Override
	public int getNroLineasxPagina() {
		return this.nroLinxPag;
	}

	
	
}

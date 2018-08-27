package jdbc.vista;

import java.util.zip.DataFormatException;

public abstract class Paginacion implements Paginable {
	//cursor
	
	//configuraci�n de la paginaci�n 
	private int nroLinxPag;
	private int nroPagxBloq;
	private boolean circular;
	
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
	
	//constructores
	public Paginacion() {
		//filas y columnas
		this.nroColumnas=0;
		this.nroFilas=0;
	}
	
	public Paginacion(int filas, int columnas) throws DataFormatException {
		if (filas<=0 || columnas<=0){
			throw new DataFormatException("N� de filas o columnas negativas");
		} 
		//filas y columnas a paginar
		this.nroColumnas=columnas;
		this.nroFilas=filas;

		calcularPaginacion(Integer.MAX_VALUE, Integer.MAX_VALUE);

	}
	
	//metodos
	public void paginacion(int filas, int columnas) {
		//filas y columnas a paginar
		this.nroColumnas=columnas;
		this.nroFilas=filas;

		calcularPaginacion(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
	
	public void calcularPaginacion(int linxpag, int pagxbloq) {
		calcularPaginacion(linxpag, pagxbloq, false);
	}
	
	public void calcularPaginacion(int lineasXpagina, int paginasXbloque, boolean consultaCircular) {
		if (paginasXbloque<=0 || lineasXpagina<=0){
			throw new IndexOutOfBoundsException("N� de p�ginas x bloque o lineas x pagina fuera de l�mite inferior");
		} 
		//configuracion por defecto
		this.setNroLinxPag(lineasXpagina);
		this.setNroPagxBloq(paginasXbloque);
		circular=consultaCircular;
		
		//datos del cursor 
		if (!hayDatos()) {
			//no hay datos en el cursor
			nroBloques=0;
			nroPaginas=0;
			//nroFilas=0;
			
			//datos de posici�n
			nroBloque=0;
			nroPaginaBloque=0;
			nroPaginasBloque=0;
			nroPagina=0;
			nroLineaPagina=0;
			nroLineasPagina=0;
			nroFila=0;
		} else {
			//posicionar en el primer dato
			nroFila=1;
			
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
	
	private boolean hayDatos() {
		//true si hay datos en el cursor
		return this.nroFilas>0;
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
	
	public void irALinea(int linea) {
		posicionNroLinea(linea);
	}
	
	public void irAFila(int fila) {
		posicionNroFila(fila);
	}
	
	
	@Override
	public int primeraPagina(){
		int pag=this.nroPaginas;
		if (this.nroPaginas>0)
			pag=1;
		posicionNroPagina(pag); 
		return pag;
	}
	
	@Override
	public int siguientePagina(){
		int pag=this.nroPagina;
		if (this.nroPagina<this.nroPaginas)
			pag= this.nroPagina+1;
		else if (circular)
			pag= primeraPagina(); //paginaci�n circular
		posicionNroPagina(pag); 
		return pag;
	}
	
	@Override
	public int paginaAnterior(){
		int pag=this.nroPagina;
		if (this.nroPagina>1)
			pag= this.nroPagina-1;
		else if (circular) 
			pag= ultimaPagina(); //paginaci�n circular
		posicionNroPagina(pag); 
		return pag;
	}
	
	@Override
	public int ultimaPagina(){
		int pag=this.nroPaginas;
		posicionNroPagina(pag);
		return pag;
	}
	
	@Override
	public int primeraPaginaBloque(){
		int pag=this.nroPaginasBloque;
		if (this.nroPaginasBloque>0)
			pag=1;
		posicionNroPaginaBloque(pag); 
		return pag;
	}
	
	@Override
	public int siguientePaginaBloque(){
		int pag=this.nroPaginaBloque;
		if (this.nroPaginaBloque<this.nroPaginasBloque)
			pag= this.nroPaginaBloque+1;
		else if (circular) 
			pag= primeraPaginaBloque(); //paginaci�n circular
		posicionNroPaginaBloque(pag); 
		return pag;
	}
	
	@Override
	public int paginaAnteriorBloque(){
		int pag=this.nroPaginaBloque;
		if (this.nroPaginaBloque>1)
			pag= this.nroPaginaBloque-1;
		else if (circular) 
			pag= ultimaPaginaBloque(); //paginaci�n circular
		posicionNroPaginaBloque(pag);
		return pag;
	}
	
	@Override
	public int ultimaPaginaBloque(){
		int pag=this.nroPaginasBloque;
		posicionNroPaginaBloque(pag);
		return pag;
	}
	
	@Override
	public int primerBloque(){
		int bloq=this.nroBloques;
		if (this.nroBloques>0)
			bloq= 1;
		posicionNroBloque(bloq); 
		return bloq; 
	}
	
	@Override
	public int siguienteBloque(){
		int bloq=this.nroBloque;
		if (this.nroBloque<this.nroBloques)
			bloq= this.nroBloque+1;
		else if (circular) 
			bloq= primerBloque(); //paginaci�n circular
		posicionNroBloque(bloq);
		return bloq;
	}
	
	@Override
	public int bloqueAnterior(){
		int bloq=this.nroBloque;
		if (this.nroBloque>1)
			bloq= this.nroBloque-1;
		else if (circular) 
			bloq= ultimoBloque(); //paginaci�n circular
		posicionNroBloque(bloq); 
		return bloq;
	}
	
	@Override
	public int ultimoBloque(){
		int bloq=this.nroBloques;
		posicionNroBloque(bloq);
		return bloq;
	}
	
	//l�gica
	private void posicionNroBloque(int nroBloque) {
		if (nroBloque==0 || nroBloque>nroBloques) {
			throw new IndexOutOfBoundsException("N� de bloque fuera de l�mites");
		}
		//nro de fila de la primera linea del bloque
		posicionNroFila((nroBloque-1)*(nroLinxPag * nroPagxBloq)+1);
	}
	
	private void posicionNroPagina(int nroPagina) {
		if (nroPagina==0 || nroPagina>nroPaginas) {
			throw new IndexOutOfBoundsException("N� de p�gina fuera de los l�mites");
		}
		//nro de fila de la primera linea de la p�gina del bloque
		posicionNroFila(((nroPagina-1)*nroLinxPag)+1);
	}
	
	private void posicionNroPaginaBloque(int nroPagina) {
		if (nroPagina==0 || nroPagina>nroPaginasBloque) {
			throw new IndexOutOfBoundsException("N� de p�gina fuera de los l�mites del bloque");
		}
		//nro de fila de la primera linea de la p�gina del bloque
		posicionNroFila(((nroBloque-1)*(nroLinxPag * nroPagxBloq))+((nroPagina-1)*nroLinxPag)+1);
	}
	
	private void posicionNroLinea(int nroLinea) {
		if (nroLinea==0 || nroLinea>nroLineasPagina) {
			throw new IndexOutOfBoundsException("N� de l�nea fuera de los l�mites de la p�gina");
		}
		//nro de fila de la linea de la p�gina del bloque
		posicionNroFila(((nroBloque-1)*(nroLinxPag * nroPagxBloq))+((nroPaginaBloque-1)*nroLinxPag)+nroLinea);
	}
	
	private void posicionNroFila(int nroFila) {
		if (nroFila<=0 || nroFila>nroFilas) {
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
			
			//calculamos n�mero de paginas del bloque 
			this.nroPaginasBloque=paginasDelBloque(this.nroBloque);
			
			//posicionamos la p�gina dentro del bloque
			this.nroPaginaBloque=paginaDeBloque(nroFila);
			
			//posicionamos la p�gina absoluta
			this.nroPagina=paginaDeFila(nroFila);
					
			//posicionamos la linea de la p�gina
			this.nroLineaPagina=lineaDePagina(nroFila);
			
			//calculamos el n�mero de lineas de la ultima p�gina del ultimo bloque
			this.nroLineasPagina=lineasDePagina(this.nroPaginaBloque);
			
			//posicionar el cursor
			posicionarEnFila();
		}
	}

	public abstract void posicionarEnFila() ;

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
		long nroFilasxBloque = ((long) this.nroLinxPag) * this.nroPagxBloq;
	    if (nroFilasxBloque > Integer.MAX_VALUE
	    		|| nroFilasxBloque < Integer.MIN_VALUE) {
	    	nroFilasxBloque=Integer.MAX_VALUE;
	    }
	    
	   return fila
				-((this.nroBloque-1)*(int) nroFilasxBloque)
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
		
		long nroFilasxBloque = ((long) this.nroLinxPag) * this.nroPagxBloq;
	    if (nroFilasxBloque > Integer.MAX_VALUE
	    		|| nroFilasxBloque < Integer.MIN_VALUE) {
	    	nroFilasxBloque=Integer.MAX_VALUE;
	    }
	    
		int pagBloq = (fila-((this.nroBloque-1)*(int) nroFilasxBloque))/this.nroLinxPag;
		if ((fila-((this.nroBloque-1)*(int) nroFilasxBloque))%this.nroLinxPag != 0){
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
		
		long nroFilasxBloque = ((long) this.nroLinxPag) * this.nroPagxBloq;
	    if (nroFilasxBloque > Integer.MAX_VALUE
	    		|| nroFilasxBloque < Integer.MIN_VALUE) {
	    	nroFilasxBloque=Integer.MAX_VALUE;
	    }
	    int bloque = (fila / (int) nroFilasxBloque);
		if (fila % nroFilasxBloque != 0){
			bloque++;
		}
		return bloque;
	}

	//getters+setters
	//cursor
	
	//configura paginacion
	public int getNroLinxPag() {
		return nroLinxPag;
	}
	private void setNroLinxPag(int nroLinxPag) {
		this.nroLinxPag = nroLinxPag;
	}

	public int getNroPagxBloq() {
		return nroPagxBloq;
	}
	private void setNroPagxBloq(int nroPagxBloq) {
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

	@Override
	public int getNroPaginasxBloque() {
		return this.nroPagxBloq;
	}

	@Override
	public int getNroLineasxPagina() {
		return this.nroLinxPag;
	}

	//toString
	@Override
	public String toString() {
		return "Paginacion ["
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

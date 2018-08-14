package jdbc.vista;

public interface Paginable {
	
	public abstract int getNroPaginasxBloque();
	public abstract int getNroLineasxPagina();

	public abstract void irAPagina(int pagina) throws IndexOutOfBoundsException;
	public abstract int primeraPagina();
	public abstract int siguientePagina();
	public abstract int paginaAnterior();
	public abstract int ultimaPagina();
	
	public abstract int getNroPaginas();

	public abstract void irAPaginaBloque(int pagina) throws IndexOutOfBoundsException;
	public abstract int primeraPaginaBloque();
	public abstract int siguientePaginaBloque();
	public abstract int paginaAnteriorBloque();
	public abstract int ultimaPaginaBloque();
	
	public abstract int getNroPaginasBloque();
	
	public abstract void irABloque(int pagina) throws IndexOutOfBoundsException;
	public abstract int primerBloque();
	public abstract int siguienteBloque();
	public abstract int bloqueAnterior();
	public abstract int ultimoBloque();
	
	public abstract int getNroBloques();

	
	public abstract int getNroBloque();
	public abstract int getNroPaginaBloque();
	public abstract int getNroPagina();
	
	public abstract int getNroLineaPagina();
	public abstract int getNroLineasPagina();
	public abstract int getNroFila();
	public abstract int getNroFilas();
	public abstract int getNroColumnas();
}

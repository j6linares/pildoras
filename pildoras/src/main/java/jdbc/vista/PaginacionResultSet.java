package jdbc.vista;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PaginacionResultSet extends Paginacion {
	
	private static final Logger LOGGER = Logger.getLogger(PaginacionResultSet.class.getName());


	//cursor
	private ResultSet rs;
	private ResultSetMetaData rsMetaData;
	private String cursor;
		
	public PaginacionResultSet(ResultSet rs) throws SQLException {
		super();
		
		//cursor
		this.rs=rs;
		rsMetaData=rs.getMetaData();
		//cursor=rs.getCursorName();
				
		//datos del cursor 
		int columnas=rsMetaData.getColumnCount();
		int filas=0;
		if (rs.last()) 
				filas=rs.getRow();
		if (filas<=0 || columnas <=0) {
			throw new SQLException("Número de filas y/o columnas del ResultSet erroneo.");
		} else {
			paginacion(filas, columnas);
		} 
	}
	
	@Override
	public void posicionarEnFila() {
		//posicionamos el cursor en la fila
		try {
			rs.absolute(this.getNroFila());
		} catch (SQLException sqle) {
			throw new IndexOutOfBoundsException(sqle.getMessage());
		}
	}

	public List<List<String>> mostrarPagina(int pag) throws SQLException {
		List<String> linea=null;
		List<List<String>> listaDatos=new ArrayList<>();
		
		String html="<p>";
		
		irAPagina(pag);
		
		LOGGER.info("Bloque: "+this.getNroBloque()+" de "+this.getNroBloques()
		+" con "+this.getNroPaginasBloque()+" páginas");
		html+="<br>"+"Bloque: "+this.getNroBloque()+" de "+this.getNroBloques()
				+" con "+this.getNroPaginasBloque()+" páginas";
		LOGGER.info("Página de Bloque: "+this.getNroPaginaBloque()+" de "+this.getNroPaginasBloque());
		html+="<br>"+"Página de Bloque: "+this.getNroPaginaBloque()+" de "+this.getNroPaginasBloque();
		LOGGER.info("Página: "+pag+" de "+this.getNroPaginas()
			+" con "+this.getNroColumnas()+" columnas");
		html+="<br>"+"Página: "+pag+" de "+this.getNroPaginas()
			+" con "+this.getNroColumnas()+" columnas";
		
		html+="<br>"+"<table style=\"width:100%\">";
		for (int l = 1; l <= this.getNroLineasPagina(); l++) {
			linea = new ArrayList<>();
			
			if (l == 1) {
				//cabecera de datos
				html+="\n"+"<tr>";
				html+="<th>Nº Fila</th>";
				for (int c = 1; c <= this.getNroColumnas(); c++) {
					html+="<th>"+rsMetaData.getColumnName(c)+"</th>";
					LOGGER.info("\t\t"+rsMetaData.getColumnName(c));
					linea.add(rsMetaData.getColumnName(c));
				}
				html+="</tr>";
				LOGGER.info("\n");
				listaDatos.add(linea);
			} 
			linea = new ArrayList<>();
			
			LOGGER.info("\t"+this.getNroFila()+" ");
			html+="\n"+"<tr>";
			html+="<td>"+this.getNroFila()+"</td>";
			//datos de línea
			for (int c = 1; c <= this.getNroColumnas(); c++) {
				html+="<td>"+rs.getString(rsMetaData.getColumnName(c))+"</td>";
				LOGGER.info("\t"+rs.getString(rsMetaData.getColumnName(c)));
				linea.add(rs.getString(rsMetaData.getColumnName(c)));
			}
			LOGGER.info("\n");
			listaDatos.add(linea);
			html+="</tr>";
			if (l < this.getNroLineasPagina())
				irAFila(this.getNroFila()+1);
		}
		html+="</table>";
		LOGGER.info(html);
		LOGGER.info(listaDatos.toString());
		
		return listaDatos;
	}
}

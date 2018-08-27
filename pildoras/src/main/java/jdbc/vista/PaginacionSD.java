package jdbc.vista;

import java.sql.SQLException;
import java.util.List;

public class PaginacionSD extends Paginacion {

	//cursor
	public PaginacionSD(int filas, int columnas) {
		super();
		if (filas<=0 || columnas <=0) {
			throw new IndexOutOfBoundsException("Número de filas y/o columnas del ResultSet erroneo.");
		} else {
			paginacion(filas, columnas);
		} 
	}
	
	@Override
	public void posicionarEnFila() {
		//posicionamos el cursor en la fila, al ser sin datos no hace falta
	}

}

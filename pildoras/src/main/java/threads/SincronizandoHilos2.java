package threads;

import util.Bitacora;

public class SincronizandoHilos2 {

	public static void main(String[] args) {
		HilosVarios hilo1 = new HilosVarios();
		Tarea hilo2 = new Tarea(hilo1);
		
		hilo2.start();
		hilo1.start();
		
		String msg="Fin de tareas!";
		Bitacora.LOG.info(msg);
	}

}

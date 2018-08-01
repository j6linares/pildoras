package threads;

import util.Bitacora;

public class SincronizandoHilos {

	public static void main(String[] args) {
		HilosVarios hilo1 = new HilosVarios();
		HilosVarios hilo2 = new HilosVarios();
		
		hilo1.start();
		try {
			hilo1.join();
		} catch (InterruptedException e) {
			Bitacora.LOG.severe(e.toString());
			Thread.currentThread().interrupt();
		}
		hilo2.start();
		try {
			hilo2.join();
		} catch (InterruptedException e) {
			Bitacora.LOG.severe(e.toString());
			Thread.currentThread().interrupt();
		}
		
		String msg = "Fin de tareas!";
		Bitacora.LOG.info(msg);
	}

}

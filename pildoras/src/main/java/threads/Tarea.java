package threads;

import util.Bitacora;

public class Tarea extends Thread {
	
	// hilo al q hay q esperar para ejecutar la tarea
	private Thread hilo;
	
	public Tarea(Thread hilo) {
		this.hilo = hilo;
	}
	
	@Override
	public void run(){
		
		//sincronizar la tarea con el hilo
		try {
			hilo.join();
		} catch (InterruptedException e1) {
			Bitacora.LOG.severe(e1.toString());
			Thread.currentThread().interrupt();
		}
		
		//tarea a realizar, con el hilo this.getName()
		for (int i = 0; i < 10; i++) {
			String msg=i+" Ejecutando hilo "+this.getName();
			Bitacora.LOG.info(msg);
			try {
				//sleep bloque el hilo y si se interrumpe puede lanzar la excepcion
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Bitacora.LOG.severe(e.toString());
				Thread.currentThread().interrupt();
			}
		}
		
	}

}

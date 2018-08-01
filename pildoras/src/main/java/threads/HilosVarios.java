package threads;

import util.Bitacora;

public class HilosVarios extends Thread {
	
	@Override
	public void run(){
		
		for (int i = 0; i < 10; i++) {
			String msg=i+" Ejecutando hilo "+this.getName();
			Bitacora.LOG.info(msg);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Bitacora.LOG.severe(e.toString());
				Thread.currentThread().interrupt();
			}
		}
		
	}

}

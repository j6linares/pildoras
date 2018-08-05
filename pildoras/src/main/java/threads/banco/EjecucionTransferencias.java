package threads.banco;

import java.util.Random;
import java.util.logging.Level;

import util.Bitacora;

public class EjecucionTransferencias implements Runnable {
	
	private Banco banco;
	private int cuenta;
	private double importeMaximo;

	public EjecucionTransferencias(Banco b, int de, double max) {
		banco=b;
		cuenta=de;
		importeMaximo=max;
	}

	@Override
	public void run() {
		int veces = (int) importeMaximo;
		while (veces > 0) {
			//destino aleatorio
			Random r = new Random();
			int destino = r.nextInt(100);
			double cantidad=importeMaximo*Math.random();
			try {
				banco.transferenciaSincronizada(cuenta, destino, cantidad);
				Thread.sleep((new Random()).nextInt(10));
			} catch (InterruptedException e) {
				Bitacora.LOG.severe(e.toString());
				Thread.currentThread().interrupt();
			}
			
			veces-=destino;
		}
		Bitacora.LOG.log(Level.INFO, "***** Fin run() "+Thread.currentThread().getName());

	}

}

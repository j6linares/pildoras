package threads.banco;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import util.Bitacora;

public class Banco {
	
	private final double[] cuentas;
	private Lock bloqueo= new ReentrantLock();

	public Banco() {
		this.cuentas = new double[100];
		for (int i = 0; i < cuentas.length; i++) {
			this.cuentas[i]=2000;
		}
	}
	
	public double getSaldo() {
		double saldo = 0;
		for (double c : cuentas) {
			saldo += c;
		}
		return saldo;
	}
	
	public void transferencia (int origen, int destino, double importe) {
		
		bloqueo.lock();
		try {
			
		if (this.cuentas[origen]< importe){
			return;
		}
		Bitacora.LOG.info(Thread.currentThread().getName());
		
		this.cuentas[origen]-=importe;
		System.out.printf("%10.2f de %d para %d", importe, origen, destino);
		this.cuentas[destino]+=importe;
		System.out.printf(" Saldo total %10.2f%n", getSaldo());
		
		} finally {
			bloqueo.unlock();
		}
	}

	public double[] getCuentas() {
		return cuentas;
	}

}

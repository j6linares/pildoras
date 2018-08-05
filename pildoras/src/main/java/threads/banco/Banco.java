package threads.banco;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import util.Bitacora;

public class Banco {
	
	private final double[] cuentas;
	private Lock bloqueo= new ReentrantLock();
	private Condition haySaldo;

	public Banco() {
		this.cuentas = new double[100];
		for (int i = 0; i < cuentas.length; i++) {
			this.cuentas[i]=2000;
		}
		haySaldo=bloqueo.newCondition();
	}
	
	public double getSaldo() {
		double saldo = 0;
		for (double c : cuentas) {
			saldo += c;
		}
		return saldo;
	}
	
	public void transferencia (int origen, int destino, double importe) throws InterruptedException {
		
		bloqueo.lock();
		try {
			
		while (this.cuentas[origen]< importe){
			System.out.println("********* Esperando saldo en "+origen+", saldo=("+this.cuentas[origen]+")"+importe+" Hilo="+Thread.currentThread().getName());
			haySaldo.await();
		}
		Bitacora.LOG.info(Thread.currentThread().getName());
		
		this.cuentas[origen]-=importe;
		System.out.printf("%10.2f de %d para %d", importe, origen, destino);
		this.cuentas[destino]+=importe;
		System.out.printf(" Saldo total %10.2f%n", getSaldo());
		haySaldo.signalAll();
		} finally {
			bloqueo.unlock();
		}
	}

	public double[] getCuentas() {
		return cuentas;
	}

	public synchronized void transferenciaSincronizada (int origen, int destino, double importe) throws InterruptedException {
		
		while (this.cuentas[origen]< importe){
			System.out.println("********* Esperando saldo en "+origen+", saldo=("+this.cuentas[origen]+")"+importe+" Hilo="+Thread.currentThread().getName());
			wait();
		}
		Bitacora.LOG.info(Thread.currentThread().getName());
		
		this.cuentas[origen]-=importe;
		System.out.printf("%10.2f de %d para %d", importe, origen, destino);
		this.cuentas[destino]+=importe;
		System.out.printf(" Saldo total %10.2f%n", getSaldo());
		notifyAll();
		
	}

}

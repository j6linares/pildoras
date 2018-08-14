package poo;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class UsoTimerInterna {

	public static void main(String[] args) {
		Reloj reloj = new Reloj(2000, true);
		reloj.parar();
		reloj.enMarcha();
		JOptionPane.showMessageDialog(null, "Parar temporizador!");
		reloj.parar();
		JOptionPane.showMessageDialog(null, "Pulsa Aceptar!");
		
	}
	
	
}

class Reloj{
	private int intervalo;
	private boolean sonido;
	private Timer temporizador;
	
	public Reloj(int intervalo, boolean sonido){
		this.intervalo=intervalo;
		this.sonido=sonido;
		ActionListener oyente = new Oyente2();
		temporizador = new Timer(intervalo, oyente);
		
	}
	public void enMarcha(){
		if (temporizador != null)
			temporizador.start();
		
	}
	
	public void parar(){
		if (temporizador != null)
			temporizador.stop();
	}
	
	//clase interna
	private class Oyente2 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Date date=new Date();
			System.out.println(date);
			if (sonido)
				Toolkit.getDefaultToolkit().beep();
		}

	}
}


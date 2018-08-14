package poo;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class UsoTimerInternaLocal {

	public static void main(String[] args) {
		Reloj1 reloj = new Reloj1(2000, true);
		reloj.parar();
		reloj.enMarcha();
		JOptionPane.showMessageDialog(null, "Parar temporizador!");
		reloj.parar();
		JOptionPane.showMessageDialog(null, "Pulsa Aceptar!");
		
	}
	
	
}

class Reloj1{
	private int intervalo;
	private boolean sonido;
	private Timer temporizador;
	
	public Reloj1(int intervalo, boolean sonido){
		//clase interna local, solo se usa una vez en el constructor
		class Oyente2 implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					Date date=new Date();
					System.out.println(date);
					if (sonido)
						Toolkit.getDefaultToolkit().beep();
				}

		}
		
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
	
	
}


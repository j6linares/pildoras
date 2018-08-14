package poo;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class UsoTimerInternaLocal2 {

	public static void main(String[] args) {
		Reloj2 reloj = new Reloj2();
		reloj.parar();
		reloj.enMarcha(2000, true);
		JOptionPane.showMessageDialog(null, "Parar temporizador!");
		reloj.parar();
		JOptionPane.showMessageDialog(null, "Pulsa Aceptar!");
		
	}
	
	
}

class Reloj2{
	private Timer temporizador;
	
	public void enMarcha(int intervalo, boolean sonido){
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
				
	ActionListener oyente = new Oyente2();
	temporizador = new Timer(intervalo, oyente);
	temporizador.start();
		
	}
	
	public void parar(){
		if (temporizador != null)
			temporizador.stop();
	}
	
	
}


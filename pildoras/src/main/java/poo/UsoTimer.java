package poo;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class UsoTimer {

	public static void main(String[] args) {
		ActionListener oyente = new Oyente();
		Timer temporizador = new Timer(5000, oyente);
		temporizador.start();
		JOptionPane.showMessageDialog(null, "Pulsa Aceptar!");
		
	}
	
	
}
//clase externa
class Oyente implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Date date=new Date();
		System.out.println(date);
		Toolkit.getDefaultToolkit().beep();
	}

}
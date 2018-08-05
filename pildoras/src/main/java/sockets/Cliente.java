package sockets;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import sockets.cliente.ClienteServidor;


public class Cliente {

	public static void main(String[] args) {
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame  {
	
	public MarcoCliente(){
		
		setBounds(600,300,280,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		}

	
	
}

class LaminaMarcoCliente extends JPanel implements Runnable{
	
	public LaminaMarcoCliente(){
	
		nick=new JTextField(8);
		
		add(nick);	
		
		JLabel texto=new JLabel("chat");
		
		add(texto);
		
		ip=new JTextField(8);
		
		add(ip);	
		
		areatexto=new JTextArea(12,20);
		
		add(areatexto,BorderLayout.CENTER);
		
	
		campo1=new JTextField(20);
	
		add(campo1);		
		
		miboton=new JButton("Enviar");
		
		EnviarTexto mievento = new EnviarTexto();
		miboton.addActionListener(mievento);
		
		add(miboton);	
		
		Thread hilo = new Thread(this);
		hilo.start();
		
		
	}
	
	@Override
	public void run() {
		while (true) {
			ClienteServidor serv = new ClienteServidor();
			serv.setPuerto(9090); //escucha por el puerto 9090
			//areatexto.append("\n"+serv.recibirMensaje());
			Paquete paquete = (Paquete) serv.recibirObjeto();
			System.out.println("Recibir paquete en cliente: "+serv);
			areatexto.append("\n"+paquete.getNick()+": "+paquete.getMensaje()+" para "+paquete.getIp());
			
		}
		
	}	
	private class EnviarTexto implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			/*try {
				Socket misocket = new Socket("192.168.56.1", 9999);
				DataOutputStream flujoSalida = new DataOutputStream(misocket.getOutputStream());
				flujoSalida.writeUTF(campo1.getText());
				flujoSalida.close();
				
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}*/
			sockets.cliente.ClienteServidor clte = new sockets.cliente.ClienteServidor();
			clte.setIp("192.168.56.1");
			clte.setPuerto(9999);
			clte.setMsg(campo1.getText());
			
			Paquete paquete = new Paquete(campo1.getText());
			paquete.setIp(ip.getText());
			paquete.setNick(nick.getText());
			
			clte.setPaquete(paquete);
			System.out.println("Enviar paquete desde cliente: "+clte);
			clte.enviarMensaje();
			areatexto.append("\n"+paquete.getNick()+": "+paquete.getMensaje()+" para "+paquete.getIp());
			
			System.out.println(campo1.getText());
			
		}
		
	}
	
		
		
		
	private JTextField campo1;
	private JTextField nick;
	private JTextField ip;
	private	JTextArea areatexto;
	
	private JButton miboton;
	
}


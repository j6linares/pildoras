package sockets;



import javax.swing.*;

import sockets.chat.ClienteServidor;

import java.awt.*;

public class Servidor  {

	public static void main(String[] args) {
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame implements Runnable {
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);
		
		Thread hilo = new Thread(this);
		hilo.start();
		
		}
	
	private	JTextArea areatexto;

	@Override
	public void run() {
		/*System.out.println("estoy a la escucha!");
		try {
			ServerSocket servidor = new ServerSocket(9999);
			while (true) {
				Socket misocket = servidor.accept();
				
				DataInputStream flujoEntrada = new DataInputStream(misocket.getInputStream());
				String msgTexto = flujoEntrada.readUTF();
				areatexto.append("\n"+msgTexto.toString());
				misocket.close();
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}*/
		while (true) {
			ClienteServidor serv = new ClienteServidor();
			serv.setPuerto(9999); //recibe por el puerto 9999
			//areatexto.append("\n"+serv.recibirMensaje());
			Paquete paquete = (Paquete) serv.recibirObjeto();
			serv.setPaquete(paquete);
			System.out.println("Recibir paquete en el servidor: "+serv);
			areatexto.append("\n"+paquete.getNick()+": "+paquete.getMensaje()+" para "+paquete.getIp());
			//re-enviar el paquete al cliente destinatario
			serv.setPuerto(9090); //envia por el puerto 9090
			serv.setIp(paquete.getIp()); //a la ip del paquete recibido
			System.out.println("Enviar paquete desde servidor: "+serv);
			serv.enviarMensaje();
		}
		
	}
}

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
	
	private static final long serialVersionUID = 1L;
	private	JTextArea areatexto;
	private ClienteServidor serv=new ClienteServidor();;


	//constructor
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
	
	//run
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
		System.out.println("estoy a la escucha de objetos  ");
		
		while (true) {
			serv.setPuerto(9999); //escucha por el puerto 9999
			//areatexto.append("\n"+serv.recibirMensaje());
			Paquete paquete = (Paquete) serv.recibirObjeto();
			System.out.println("Recibir paquete en el servidor: "+serv);
			System.out.println("Paquete recibido en el servidor: "+paquete);
			if (paquete == null) {
				serv.setPuerto(9090); //envia por el puerto 9090
				//enviar a todos los clientes la lista de ips
				for (String ip: serv.getClientes()) {
					serv.setIp(ip); //a la ip del cliente
					paquete=new Paquete("ips"); //mensaje ips conectadas
					paquete.setIps(serv.getClientes());
					serv.setPaquete(paquete);
					
					System.out.println("Enviar paquete de ips conectadas desde el servidor al cliente: "+serv);
					serv.enviarMensaje();
				} 
			} else {
				areatexto.append("\n"+paquete.getNick()+": "+paquete.getMensaje()+" para "+paquete.getIp());
				//re-enviar el paquete al cliente destinatario
				serv.setPuerto(9090); //envia por el puerto 9090
				serv.setIp(paquete.getIp()); //a la ip destino del paquete recibido
				serv.setPaquete(paquete);
				
				System.out.println("Enviar paquete recibido del cliente desde el servidor al destinatario: "+serv);
				serv.enviarMensaje();
			}
		}
		
	}
}

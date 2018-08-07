package sockets;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import sockets.chat.ClienteServidor;


public class Cliente {

	public static void main(String[] args) {
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame  {
	
	private static final long serialVersionUID = 1L;

	public MarcoCliente(){
		
		setBounds(600,300,280,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		
		addWindowListener(new EnvioOnline());
		}
	
}

class EnvioOnline extends WindowAdapter {
	
	public void windowOpened(WindowEvent evento) {
		ClienteServidor clte = new ClienteServidor();
		clte.setIp("192.168.1.38"); //ip del servidor eth
		clte.setPuerto(9999);
		clte.setMsg("online");
		
		System.out.println("Enviar paquete de conexion al servidor desde cliente: "+clte);
		clte.enviarMensaje();
		
	}
}

class LaminaMarcoCliente extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	private JTextField campo1;
	private JLabel nick;
	private JComboBox<String> ip;
	private	JTextArea areatexto;

	private JButton miboton;

	public LaminaMarcoCliente(){
		
		String nick_usuario = JOptionPane.showInputDialog("Nick: ");
		JLabel n_nick =new JLabel("Nick: ");
		add(n_nick);
	
		nick=new JLabel();
		nick.setText(nick_usuario);
		
		add(nick);	
		
		JLabel texto=new JLabel(" Online:");
		
		add(texto);
		
		ip=new JComboBox<String>();
		ip.addItem("192.168.1.61");
		ip.addItem("192.168.1.38");
		ip.addItem("192.168.1.56");
		
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
			System.out.println("Paquete recibido en cliente: "+paquete);
			if (paquete != null && paquete.getMensaje().equals("ips")) {
				ip.removeAllItems();
				//mensaje de ips conectadas
				System.out.println("clientes conectados al chat");
				for (String s : paquete.getIps()) {
					System.out.println(""+s);
					ip.addItem(s);
				}
			} else {
				//paquete de cliente a cliente
				areatexto.append("\n"+paquete.getNick()+": "+paquete.getMensaje()+" para "+paquete.getIp());
			}
			
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
			ClienteServidor clte = new ClienteServidor();
			clte.setIp("192.168.1.38"); //ethernet
			//clte.setIp("127.0.0.1"); //ip del servidor
			clte.setPuerto(9999);
			clte.setMsg(campo1.getText());
			
			Paquete paquete = new Paquete(campo1.getText());
			paquete.setIp(ip.getSelectedItem().toString());
			paquete.setNick(nick.getText());
			
			clte.setPaquete(paquete);
			System.out.println("Enviar paquete desde cliente: "+clte);
			clte.enviarMensaje();
			areatexto.append("\n"+paquete.getNick()+": "+paquete.getMensaje()+" para "+paquete.getIp());
			
			System.out.println("Texto enviado: "+campo1.getText());
			
		}
		
	}
		
}


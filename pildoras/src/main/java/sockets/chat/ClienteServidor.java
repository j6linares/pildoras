package sockets.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import sockets.Paquete;

public class ClienteServidor {
	
	private String ip;
	private int puerto;
	private String msg;
	private Object paquete;
	private List<String> clientes = new ArrayList<String>();
	
	public void enviarMensaje(){
		
		try {
			Socket socket = new Socket(this.ip, this.puerto);
			ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
			salida.writeObject(paquete);
			salida.close();
			socket.close();
			
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			ioe.printStackTrace();
		} finally {
			
		}
		
		
	}
	
	public String recibirMensaje(){
		System.out.println("estoy a la escucha!");
		String msgTexto=null;
		try {
			ServerSocket servidor = new ServerSocket(this.puerto);
			Socket socket = servidor.accept();
				
			ObjectInputStream flujoEntrada = new ObjectInputStream(socket.getInputStream());
			msgTexto = ((Paquete) flujoEntrada.readObject()).getMensaje();
			flujoEntrada.close();
			
			//detectar cliente conectado
			guardarClienteConectado(socket);
				
			socket.close();
			servidor.close();
				
			
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			
		}
		return msgTexto;
	}
	
	public Object recibirObjeto(){
		Object obj=null;
		try {
			ServerSocket servidor = new ServerSocket(this.puerto);
			Socket socket = servidor.accept();
			
			ObjectInputStream flujoEntrada = new ObjectInputStream(socket.getInputStream());
			obj = flujoEntrada.readObject();
			flujoEntrada.close();
			
			//detectar cliente conectado
			if (obj == null)
				guardarClienteConectado(socket);
				
			socket.close();
			servidor.close();
				
			
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			
		}
		return obj;
	}
	
	private void guardarClienteConectado(Socket socket) {
		
		//detectar la ip del cliente
		InetAddress iaCliente = socket.getInetAddress();
		String ipCliente = iaCliente.getHostAddress();
		if (!clientes.contains(ipCliente)) {
			System.out.println("Nuevo cliente conectado al servidor desde la ip="+ipCliente);
			clientes.add(ipCliente);
		} 
		System.out.println("clientes conectados:");
		for (String ip : clientes) {
			System.out.println(ip);
		}
		
	}

	//getter+setter
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPuerto() {
		return puerto;
	}
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getPaquete() {
		return paquete;
	}

	public void setPaquete(Object paquete) {
		this.paquete = paquete;
	}
	
	public List<String> getClientes() {
		return clientes;
	}

	public void setClientes(List<String> clientes) {
		this.clientes = clientes;
	}

	@Override
	public String toString() {
		return "ClienteServidor [ip=" + ip + ", puerto=" + puerto + ", msg=" + msg + ", paquete=" + paquete
				+ ", clientes=" + clientes + "]";
	}

	
	
	

}

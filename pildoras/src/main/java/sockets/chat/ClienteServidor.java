package sockets.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import sockets.Paquete;

public class ClienteServidor {
	
	private String ip;
	private int puerto;
	private String msg;
	private Object paquete;
	
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
				
			socket.close();
			servidor.close();
				
			
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			
		}
		return msgTexto;
	}
	
	public Object recibirObjeto(){
		System.out.println("estoy a la escucha de objetos por el puerto: "+this.puerto);
		Object obj=null;
		try {
			ServerSocket servidor = new ServerSocket(this.puerto);
			Socket socket = servidor.accept();
				
			ObjectInputStream flujoEntrada = new ObjectInputStream(socket.getInputStream());
			obj = flujoEntrada.readObject();
			flujoEntrada.close();
				
			socket.close();
			servidor.close();
				
			
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			
		}
		return obj;
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

	@Override
	public String toString() {
		return "ClienteServidor [ip=" + ip + ", puerto=" + puerto + ", msg=" + msg + ", paquete=" + paquete + "]";
	}
	
	

}

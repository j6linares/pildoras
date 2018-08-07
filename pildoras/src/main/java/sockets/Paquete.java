package sockets;

import java.io.Serializable;
import java.util.List;

public class Paquete implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String mensaje;
	private String nick;
	private String ip;
	private List<String> ips;

	//constructor
	public Paquete(String msg) {
		this.mensaje = msg;
	}

	//getter+setter
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public List<String> getIps() {
		return ips;
	}

	public void setIps(List<String> ips) {
		this.ips = ips;
	}

	@Override
	public String toString() {
		return "Paquete [mensaje=" + mensaje + ", nick=" + nick + ", ip=" + ip + ", ips=" + ips + "]";
	}
	

}

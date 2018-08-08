package jdbc.modelo;

public class MiTabla {
	
	private String nombre;
	private String apellidos;
	
	//constructor
	public MiTabla() {
		nombre="";
		apellidos="";
	}
	//getters+setters

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	

}

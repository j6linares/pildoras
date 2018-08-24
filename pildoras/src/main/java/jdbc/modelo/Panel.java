package jdbc.modelo;

import java.util.Date;

public class Panel {
	
	private int id;
	private String nombre;
	private String titulo;
	private String subtitulo;
	private String entorno;
	private Date fecha;
	private String mensaje;
	private int pagina;
	private int paginas;
	
	//cons
	public Panel(String nombre, String titulo, String entorno, String subtitulo, Date fecha
			, String mensaje, int pagina, int paginas) {
		super();
		this.nombre = nombre;
		this.titulo = titulo;
		this.subtitulo = subtitulo;
		this.entorno = entorno;
		this.fecha = fecha;
		this.mensaje = mensaje;
		this.pagina = pagina;
		this.paginas = paginas;
	}
	
	//getter+setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getSubtitulo() {
		return subtitulo;
	}
	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}
	public String getEntorno() {
		return entorno;
	}
	public void setEntorno(String entorno) {
		this.entorno = entorno;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public int getPagina() {
		return pagina;
	}
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	public int getPaginas() {
		return paginas;
	}
	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	//toString()
	@Override
	public String toString() {
		return "Panel [id=" + id + ", nombre=" + nombre + ", titulo=" + titulo + ", subtitulo=" + subtitulo
				+ ", entorno=" + entorno + ", fecha=" + fecha + ", mensaje=" + mensaje + ", pagina=" + pagina
				+ ", paginas=" + paginas + "]";
	}
	
	

}

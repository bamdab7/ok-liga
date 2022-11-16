package okligaNPC;

public class Jugadores {
	
	private int codigo;
	private String nombre;
	private int id_equipo;
	
	public Jugadores() {
		super();
	}

	public Jugadores(int codigo, String nombre, int id_equipo) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.id_equipo = id_equipo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId_equipo() {
		return id_equipo;
	}

	public void setId_equipo(int id_equipo) {
		this.id_equipo = id_equipo;
	}
	
}

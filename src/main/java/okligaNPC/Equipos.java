package okligaNPC;

public class Equipos {

	private int codigoEq;
	private String nombreEq;
	private String localidad;
	
	public Equipos() {
		super();
	}

	public Equipos(int codigoEq, String nombreEq, String localidad) {
		super();
		this.codigoEq = codigoEq;
		this.nombreEq = nombreEq;
		this.localidad = localidad;
	}

	public int getCodigoEq() {
		return codigoEq;
	}

	public void setCodigoEq(int codigoEq) {
		this.codigoEq = codigoEq;
	}

	public String getNombreEq() {
		return nombreEq;
	}

	public void setNombreEq(String nombreEq) {
		this.nombreEq = nombreEq;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
}

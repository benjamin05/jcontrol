package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;

public class Municipio implements Serializable {

	private static final long serialVersionUID = -219617364858985264L;

	private MunicipioId id;
	private EstadoRepublica estadoRepublica;
	private String nombre;
	private String rango1;
	private String rango2;
	private String rango3;
	private String rango4;

	public MunicipioId getId() {
		return this.id;
	}

	public void setId( MunicipioId id ) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre( String nombre ) {
		this.nombre = nombre;
	}

	public String getRango1() {
		return this.rango1;
	}

	public void setRango1( String rango1 ) {
		this.rango1 = rango1;
	}

	public String getRango2() {
		return this.rango2;
	}

	public void setRango2( String rango2 ) {
		this.rango2 = rango2;
	}

	public String getRango3() {
		return this.rango3;
	}

	public void setRango3( String rango3 ) {
		this.rango3 = rango3;
	}

	public String getRango4() {
		return this.rango4;
	}

	public void setRango4( String rango4 ) {
		this.rango4 = rango4;
	}

	public EstadoRepublica getEstadoRepublica() {
		return estadoRepublica;
	}

	public void setEstadoRepublica( EstadoRepublica estadoRepublica ) {
		this.estadoRepublica = estadoRepublica;
	}

}

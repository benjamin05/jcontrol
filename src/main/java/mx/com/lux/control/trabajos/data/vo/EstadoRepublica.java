package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;

public class EstadoRepublica implements Serializable {

	private static final long serialVersionUID = -4667124024528260700L;

	private String idEstado;
	private String nombre;
	private String edo1;
	private String rango1;
	private String rango2;

	public String getIdEstado() {
		return this.idEstado;
	}

	public void setIdEstado( String idEstado ) {
		this.idEstado = idEstado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre( String nombre ) {
		this.nombre = nombre;
	}

	public String getEdo1() {
		return this.edo1;
	}

	public void setEdo1( String edo1 ) {
		this.edo1 = edo1;
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

}

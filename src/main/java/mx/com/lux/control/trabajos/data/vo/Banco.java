package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;

public class Banco implements Serializable {

	private static final long serialVersionUID = -2394578157606309130L;

	private Integer id;
	private String descripcion;
	private String tipo;

	Banco() {
	}

	public Integer getId() {
		return id;
	}

	public void setId( Integer id ) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion( String descripcion ) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo( String tipo ) {
		this.tipo = tipo;
	}

}

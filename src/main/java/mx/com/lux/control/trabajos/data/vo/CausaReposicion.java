package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;

public class CausaReposicion implements Serializable {

	private static final long serialVersionUID = 3060497211882155799L;

	private Integer id;
	private String descripcion;

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
}

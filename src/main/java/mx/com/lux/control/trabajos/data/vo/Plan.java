package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;

public class Plan implements Serializable {

	private static final long serialVersionUID = 4744036332309122896L;

	private String id;
	private String descripcion;
	private String idBancoDep;

	Plan() {
	}

	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion( String descripcion ) {
		this.descripcion = descripcion;
	}

	public String getIdBancoDep() {
		return idBancoDep;
	}

	public void setIdBancoDep( String idBancoDep ) {
		this.idBancoDep = idBancoDep;
	}

}

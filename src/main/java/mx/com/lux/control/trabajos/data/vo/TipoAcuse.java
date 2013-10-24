package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;

public class TipoAcuse implements Serializable {

	private static final long serialVersionUID = 7749421570675906161L;

	private String id;
	private String pagina;
	private String descripcion;

	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina( String pagina ) {
		this.pagina = pagina;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion( String descripcion ) {
		this.descripcion = descripcion;
	}
}

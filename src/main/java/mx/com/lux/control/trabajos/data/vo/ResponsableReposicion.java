package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;

public class ResponsableReposicion implements Serializable {

	private static final long serialVersionUID = -90425731514591795L;

	private String responsable;

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable( String responsable ) {
		this.responsable = responsable;
	}
}

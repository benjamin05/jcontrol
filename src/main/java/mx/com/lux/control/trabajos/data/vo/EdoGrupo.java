package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class EdoGrupo implements Serializable {

	private static final long serialVersionUID = -9050886174028804107L;

	private Integer id;
	private String descripcion;
	private Set<JbEstado> estados = new HashSet<JbEstado>();

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

	public Set<JbEstado> getEstados() {
		return estados;
	}

	public void setEstados( Set<JbEstado> estados ) {
		this.estados = estados;
	}
}

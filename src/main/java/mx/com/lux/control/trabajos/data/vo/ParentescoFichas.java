package mx.com.lux.control.trabajos.data.vo;
// default package
// Generated 6/09/2011 02:19:05 PM by Hibernate Tools 3.4.0.CR1

/**
 * ParentescoFichas generated by hbm2java
 */
public class ParentescoFichas implements java.io.Serializable {

	private String idParentesco;
	private String descripcion;

	public ParentescoFichas() {
	}

	public ParentescoFichas( String idParentesco, String descripcion ) {
		this.idParentesco = idParentesco;
		this.descripcion = descripcion;
	}

	public String getIdParentesco() {
		return this.idParentesco;
	}

	public void setIdParentesco( String idParentesco ) {
		this.idParentesco = idParentesco;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion( String descripcion ) {
		this.descripcion = descripcion;
	}

}

package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.util.Date;

public class Prisma implements Serializable {

	private static final long serialVersionUID = -695219196297530221L;

	private Integer id;
	private String prisma;
	private String descripcion;
	private Character idSync;
	private Date fechaModificacion;
	private String idMod;
	private Sucursal sucursal;

	public Integer getId() {
		return id;
	}

	public void setId( Integer id ) {
		this.id = id;
	}

	public String getPrisma() {
		return prisma;
	}

	public void setPrisma( String prisma ) {
		this.prisma = prisma;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion( String descripcion ) {
		this.descripcion = descripcion;
	}

	public Character getIdSync() {
		return idSync;
	}

	public void setIdSync( Character idSync ) {
		this.idSync = idSync;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion( Date fechaModificacion ) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getIdMod() {
		return idMod;
	}

	public void setIdMod( String idMod ) {
		this.idMod = idMod;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal( Sucursal sucursal ) {
		this.sucursal = sucursal;
	}
}

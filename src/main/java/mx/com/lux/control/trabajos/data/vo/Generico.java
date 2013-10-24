package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.util.Date;

public class Generico implements Serializable {

	private static final long serialVersionUID = 1640642840848656973L;

	private Character id;
	private String descripcion;
	private Character idSync;
	private Date fechaModificacion;
	private String idMod;
	private Integer idSucursal;
	private String surte;

	public Character getId() {
		return id;
	}

	public void setId( Character id ) {
		this.id = id;
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
		if( fechaModificacion != null ) {
			this.fechaModificacion = new Date( fechaModificacion.getTime() );
		}
	}

	public String getIdMod() {
		return idMod;
	}

	public void setIdMod( String idMod ) {
		this.idMod = idMod;
	}

	public Integer getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal( Integer idSucursal ) {
		this.idSucursal = idSucursal;
	}

	public String getSurte() {
		return surte;
	}

	public void setSurte( String surte ) {
		this.surte = surte;
	}
}

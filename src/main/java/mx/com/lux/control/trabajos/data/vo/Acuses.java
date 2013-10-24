package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.util.Date;

public class Acuses implements Serializable {

	private static final long serialVersionUID = -6960816525501043302L;

	private int idAcuse;
	private String contenido;
	private Date fechaCarga;
	private Date fechaAcuso;
	private String idTipo;
	private String folio;
	private Integer intentos;

	public int getIdAcuse() {
		return this.idAcuse;
	}

	public void setIdAcuse( int idAcuse ) {
		this.idAcuse = idAcuse;
	}

	public String getContenido() {
		return this.contenido;
	}

	public void setContenido( String contenido ) {
		this.contenido = contenido;
	}

	public Date getFechaCarga() {
		return this.fechaCarga;
	}

	public void setFechaCarga( Date fechaCarga ) {
		this.fechaCarga = fechaCarga;
	}

	public Date getFechaAcuso() {
		return this.fechaAcuso;
	}

	public void setFechaAcuso( Date fechaAcuso ) {
		this.fechaAcuso = fechaAcuso;
	}

	public String getIdTipo() {
		return this.idTipo;
	}

	public void setIdTipo( String idTipo ) {
		this.idTipo = idTipo;
	}

	public String getFolio() {
		return this.folio;
	}

	public void setFolio( String folio ) {
		this.folio = folio;
	}

	public Integer getIntentos() {
		return this.intentos;
	}

	public void setIntentos( Integer intentos ) {
		this.intentos = intentos;
	}
}

package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;

public class JbEstado implements Serializable {

	private static final long serialVersionUID = 6673068232362340541L;

	private String idEdo;
	private String llamada;
	private String descr;

	public JbEstado() {
	}

	public JbEstado( String idEdo ) {
		this.idEdo = idEdo;
	}

	public JbEstado( String idEdo, String llamada, String descr ) {
		this.idEdo = idEdo;
		this.llamada = llamada;
		this.descr = descr;
	}

	public String getIdEdo() {
		return this.idEdo;
	}

	public void setIdEdo( String idEdo ) {
		this.idEdo = idEdo;
	}

	public String getLlamada() {
		return this.llamada;
	}

	public void setLlamada( String llamada ) {
		this.llamada = llamada;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr( String descr ) {
		this.descr = descr;
	}
}

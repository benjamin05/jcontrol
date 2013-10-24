package mx.com.lux.control.trabajos.data.vo;

// Generated 21/07/2011 02:01:22 PM by Hibernate Tools 3.4.0.CR1

import java.sql.Timestamp;
import java.util.Date;

/**
 * JbGrupo generated by hbm2java
 */
public class JbGrupo implements java.io.Serializable {

	private int idGrupo;
	private String nombreGrupo;
	//	private String estado;
	private JbEstado estado;
	private String emp;
	private Date fechaPromesa;
	private Date volverLlamar;
	private Integer numLlamada;
	private Timestamp fecha;
	private String idMod;

	public JbGrupo() {
	}

	public JbGrupo( int idGrupo, Timestamp fecha, String idMod ) {
		this.idGrupo = idGrupo;
		this.fecha = fecha;
		this.idMod = idMod;
	}

	public JbGrupo( int idGrupo, String nombreGrupo, JbEstado estado, String emp, Date fechaPromesa, Date volverLlamar, Integer numLlamada, Timestamp fecha, String idMod ) {
		this.idGrupo = idGrupo;
		this.nombreGrupo = nombreGrupo;
		this.estado = estado;
		this.emp = emp;
		this.fechaPromesa = fechaPromesa;
		this.volverLlamar = volverLlamar;
		this.numLlamada = numLlamada;
		this.fecha = fecha;
		this.idMod = idMod;
	}

	public int getIdGrupo() {
		return this.idGrupo;
	}

	public void setIdGrupo( int idGrupo ) {
		this.idGrupo = idGrupo;
	}

	public String getNombreGrupo() {
		return this.nombreGrupo;
	}

	public void setNombreGrupo( String nombreGrupo ) {
		this.nombreGrupo = nombreGrupo;
	}

	public JbEstado getEstado() {
		return this.estado;
	}

	public void setEstado( JbEstado estado ) {
		this.estado = estado;
	}

	public String getEmp() {
		return this.emp;
	}

	public void setEmp( String emp ) {
		this.emp = emp;
	}

	public Date getFechaPromesa() {
		return this.fechaPromesa;
	}

	public void setFechaPromesa( Date fechaPromesa ) {
		this.fechaPromesa = fechaPromesa;
	}

	public Date getVolverLlamar() {
		return this.volverLlamar;
	}

	public void setVolverLlamar( Date volverLlamar ) {
		this.volverLlamar = volverLlamar;
	}

	public Integer getNumLlamada() {
		return this.numLlamada;
	}

	public void setNumLlamada( Integer numLlamada ) {
		this.numLlamada = numLlamada;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha( Timestamp fecha ) {
		this.fecha = fecha;
	}

	public String getIdMod() {
		return this.idMod;
	}

	public void setIdMod( String idMod ) {
		this.idMod = idMod;
	}

}

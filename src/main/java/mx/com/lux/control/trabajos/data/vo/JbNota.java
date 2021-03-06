package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

// Generated 21/07/2011 02:01:22 PM by Hibernate Tools 3.4.0.CR1

/**
 * JbNota generated by hbm2java
 */
public class JbNota implements Serializable {

	private static final long serialVersionUID = 8233160480743076788L;

	private int idNota;
	private String idCliente;
	private String cliente;
	private String dejo;
	private String instruccion;
	private String emp;
	private String servicio;
	private String condicion;
	private Date fechaProm;
	private Timestamp fechaOrden;
	private Timestamp fechaMod;
	private String tipoServ;
	private String idMod;

	public JbNota() {
	}

	public JbNota( int idNota, Timestamp fechaOrden, Timestamp fechaMod, String idMod ) {
		this.idNota = idNota;
		this.fechaOrden = fechaOrden;
		this.fechaMod = fechaMod;
		this.idMod = idMod;
	}

	public JbNota( int idNota, String idCliente, String cliente, String dejo, String instruccion, String emp, String servicio, String condicion, Date fechaProm, Timestamp fechaOrden, Timestamp fechaMod, String tipoServ, String idMod ) {
		this.idNota = idNota;
		this.idCliente = idCliente;
		this.cliente = cliente;
		this.dejo = dejo;
		this.instruccion = instruccion;
		this.emp = emp;
		this.servicio = servicio;
		this.condicion = condicion;
		this.fechaProm = fechaProm;
		this.fechaOrden = fechaOrden;
		this.fechaMod = fechaMod;
		this.tipoServ = tipoServ;
		this.idMod = idMod;
	}

	public int getIdNota() {
		return this.idNota;
	}

	public void setIdNota( int idNota ) {
		this.idNota = idNota;
	}

	public String getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente( String idCliente ) {
		this.idCliente = idCliente;
	}

	public String getCliente() {
		return this.cliente;
	}

	public void setCliente( String cliente ) {
		this.cliente = cliente;
	}

	public String getDejo() {
		return this.dejo;
	}

	public void setDejo( String dejo ) {
		this.dejo = dejo;
	}

	public String getInstruccion() {
		return this.instruccion;
	}

	public void setInstruccion( String instruccion ) {
		this.instruccion = instruccion;
	}

	public String getEmp() {
		return this.emp;
	}

	public void setEmp( String emp ) {
		this.emp = emp;
	}

	public String getServicio() {
		return this.servicio;
	}

	public void setServicio( String servicio ) {
		this.servicio = servicio;
	}

	public String getCondicion() {
		return this.condicion;
	}

	public void setCondicion( String condicion ) {
		this.condicion = condicion;
	}

	public Date getFechaProm() {
		return this.fechaProm;
	}

	public void setFechaProm( Date fechaProm ) {
		this.fechaProm = fechaProm;
	}

	public Timestamp getFechaOrden() {
		return this.fechaOrden;
	}

	public void setFechaOrden( Timestamp fechaOrden ) {
		this.fechaOrden = fechaOrden;
	}

	public Timestamp getFechaMod() {
		return this.fechaMod;
	}

	public void setFechaMod( Timestamp fechaMod ) {
		this.fechaMod = fechaMod;
	}

	public String getTipoServ() {
		return this.tipoServ;
	}

	public void setTipoServ( String tipoServ ) {
		this.tipoServ = tipoServ;
	}

	public String getIdMod() {
		return this.idMod;
	}

	public void setIdMod( String idMod ) {
		this.idMod = idMod;
	}

	public JbNota setValuesJbNota( String idCliente, String cliente, String dejo, String instruccion, String emp, String servicio, String condicion, Date fechaProm, Timestamp fechaOrden, Timestamp fechaMod, String tipoServ, String idMod ) {
		JbNota jbn = new JbNota();
		jbn.setIdCliente( idCliente );
		jbn.setCliente( cliente );
		jbn.setDejo( dejo );
		jbn.setInstruccion( instruccion );
		jbn.setEmp( emp );
		jbn.setServicio( servicio );
		jbn.setCondicion( condicion );
		jbn.setFechaProm( fechaProm );
		jbn.setFechaOrden( fechaOrden );
		jbn.setFechaMod( fechaMod );
		jbn.setTipoServ( tipoServ );
		jbn.setIdMod( idMod );
		return jbn;
	}
}

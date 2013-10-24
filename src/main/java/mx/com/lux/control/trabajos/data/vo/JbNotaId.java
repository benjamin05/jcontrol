package mx.com.lux.control.trabajos.data.vo;

// Generated 21/07/2011 02:01:22 PM by Hibernate Tools 3.4.0.CR1

import java.sql.Timestamp;
import java.util.Date;

/**
 * JbNotaId generated by hbm2java
 */
public class JbNotaId implements java.io.Serializable {

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

	public JbNotaId() {
	}

	public JbNotaId( int idNota, Timestamp fechaOrden, Timestamp fechaMod, String idMod ) {
		this.idNota = idNota;
		this.fechaOrden = fechaOrden;
		this.fechaMod = fechaMod;
		this.idMod = idMod;
	}

	public JbNotaId( int idNota, String idCliente, String cliente, String dejo, String instruccion, String emp, String servicio, String condicion, Date fechaProm, Timestamp fechaOrden, Timestamp fechaMod, String tipoServ, String idMod ) {
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

	public boolean equals( Object other ) {
		if ( ( this == other ) )
			return true;
		if ( ( other == null ) )
			return false;
		if ( !( other instanceof JbNotaId ) )
			return false;
		JbNotaId castOther = (JbNotaId) other;

		return ( this.getIdNota() == castOther.getIdNota() ) && ( ( this.getIdCliente() == castOther.getIdCliente() ) || ( this.getIdCliente() != null && castOther.getIdCliente() != null && this.getIdCliente().equals( castOther.getIdCliente() ) ) ) && ( ( this.getCliente() == castOther.getCliente() ) || ( this.getCliente() != null && castOther.getCliente() != null && this.getCliente().equals( castOther.getCliente() ) ) ) && ( ( this.getDejo() == castOther.getDejo() ) || ( this.getDejo() != null && castOther.getDejo() != null && this.getDejo().equals( castOther.getDejo() ) ) ) && ( ( this.getInstruccion() == castOther.getInstruccion() ) || ( this.getInstruccion() != null && castOther.getInstruccion() != null && this.getInstruccion().equals( castOther.getInstruccion() ) ) ) && ( ( this.getEmp() == castOther.getEmp() ) || ( this.getEmp() != null && castOther.getEmp() != null && this.getEmp().equals( castOther.getEmp() ) ) ) && ( ( this.getServicio() == castOther.getServicio() ) || ( this.getServicio() != null && castOther.getServicio() != null && this.getServicio().equals( castOther.getServicio() ) ) ) && ( ( this.getCondicion() == castOther.getCondicion() ) || ( this.getCondicion() != null && castOther.getCondicion() != null && this.getCondicion().equals( castOther.getCondicion() ) ) ) && ( ( this.getFechaProm() == castOther.getFechaProm() ) || ( this.getFechaProm() != null && castOther.getFechaProm() != null && this.getFechaProm().equals( castOther.getFechaProm() ) ) ) && ( ( this.getFechaOrden() == castOther.getFechaOrden() ) || ( this.getFechaOrden() != null && castOther.getFechaOrden() != null && this.getFechaOrden().equals( castOther.getFechaOrden() ) ) ) && ( ( this.getFechaMod() == castOther.getFechaMod() ) || ( this.getFechaMod() != null && castOther.getFechaMod() != null && this.getFechaMod().equals( castOther.getFechaMod() ) ) ) && ( ( this.getTipoServ() == castOther.getTipoServ() ) || ( this.getTipoServ() != null && castOther.getTipoServ() != null && this.getTipoServ().equals( castOther.getTipoServ() ) ) ) && ( ( this.getIdMod() == castOther.getIdMod() ) || ( this.getIdMod() != null && castOther.getIdMod() != null && this.getIdMod().equals( castOther.getIdMod() ) ) );
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdNota();
		result = 37 * result + ( getIdCliente() == null ? 0 : this.getIdCliente().hashCode() );
		result = 37 * result + ( getCliente() == null ? 0 : this.getCliente().hashCode() );
		result = 37 * result + ( getDejo() == null ? 0 : this.getDejo().hashCode() );
		result = 37 * result + ( getInstruccion() == null ? 0 : this.getInstruccion().hashCode() );
		result = 37 * result + ( getEmp() == null ? 0 : this.getEmp().hashCode() );
		result = 37 * result + ( getServicio() == null ? 0 : this.getServicio().hashCode() );
		result = 37 * result + ( getCondicion() == null ? 0 : this.getCondicion().hashCode() );
		result = 37 * result + ( getFechaProm() == null ? 0 : this.getFechaProm().hashCode() );
		result = 37 * result + ( getFechaOrden() == null ? 0 : this.getFechaOrden().hashCode() );
		result = 37 * result + ( getFechaMod() == null ? 0 : this.getFechaMod().hashCode() );
		result = 37 * result + ( getTipoServ() == null ? 0 : this.getTipoServ().hashCode() );
		result = 37 * result + ( getIdMod() == null ? 0 : this.getIdMod().hashCode() );
		return result;
	}

}

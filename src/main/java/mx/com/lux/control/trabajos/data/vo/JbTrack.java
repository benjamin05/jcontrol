package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.util.Date;

public class JbTrack implements Serializable {

	private static final long serialVersionUID = 7409918252064768947L;

	private String rx;
	private Date fecha;
	private String estado;
	private String observaciones;
	private String empleado;
	private String idViaje;
	private String idMod;

	public JbTrack() {
		fecha = new Date();
	}

	public String getRx() {
		return rx;
	}

	public void setRx( String rx ) {
		this.rx = rx;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha( Date fecha ) {
		this.fecha = fecha;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones( String observaciones ) {
		this.observaciones = observaciones;
	}

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado( String empleado ) {
		this.empleado = empleado;
	}

	public String getIdViaje() {
		return idViaje;
	}

	public void setIdViaje( String idViaje ) {
		this.idViaje = idViaje;
	}

	public String getIdMod() {
		return idMod;
	}

	public void setIdMod( String idMod ) {
		this.idMod = idMod;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado( String estado ) {
		this.estado = estado;
	}

}

package mx.com.lux.control.trabajos.data.vo;

import java.sql.Timestamp;

public class TrackViewId implements java.io.Serializable {
	private Timestamp fecha;
	private String estado;
	private String obs;
	private String nombre_empleado;
	private String ap_pat_empleado;
	private String ap_mat_empleado;
	private String rx;

	public TrackViewId() {
	}

	public TrackViewId( Timestamp fecha, String estado, String obs, String nombre_empleado, String ap_pat_empleado, String ap_mat_empleado, String rx ) {
		super();
		this.fecha = fecha;
		this.estado = estado;
		this.obs = obs;
		this.nombre_empleado = nombre_empleado;
		this.ap_pat_empleado = ap_pat_empleado;
		this.ap_mat_empleado = ap_mat_empleado;
		this.rx = rx;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha( Timestamp fecha ) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado( String estado ) {
		this.estado = estado;
	}

	public String getObs() {
		return obs;
	}

	public void setObs( String obs ) {
		this.obs = obs;
	}

	public String getNombre_empleado() {
		return nombre_empleado;
	}

	public void setNombre_empleado( String nombre_empleado ) {
		this.nombre_empleado = nombre_empleado;
	}

	public String getAp_pat_empleado() {
		return ap_pat_empleado;
	}

	public void setAp_pat_empleado( String ap_pat_empleado ) {
		this.ap_pat_empleado = ap_pat_empleado;
	}

	public String getAp_mat_empleado() {
		return ap_mat_empleado;
	}

	public void setAp_mat_empleado( String ap_mat_empleado ) {
		this.ap_mat_empleado = ap_mat_empleado;
	}

	public String getRx() {
		return rx;
	}

	public void setRx( String rx ) {
		this.rx = rx;
	}
}

package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.util.Date;

public class Empleado implements Serializable {

	private static final long serialVersionUID = 270348220170143061L;

	private String idEmpleado;
	private String nombreEmpleado;
	private String apPatEmpleado;
	private String apMatEmpleado;
	private Integer idPuesto;
	private String passwd;
	private char idSync;
	private Date fechaMod;
	private String idMod;
	private Sucursal sucursal;
	private Integer idEmpresa;

	public String getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado( String idEmpleado ) {
		this.idEmpleado = idEmpleado;
	}

	public String getNombreEmpleado() {
		return this.nombreEmpleado;
	}

	public void setNombreEmpleado( String nombreEmpleado ) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public String getApPatEmpleado() {
		return apPatEmpleado;
	}

	public void setApPatEmpleado( String apPatEmpleado ) {
		this.apPatEmpleado = apPatEmpleado;
	}

	public String getApMatEmpleado() {
		return this.apMatEmpleado;
	}

	public void setApMatEmpleado( String apMatEmpleado ) {
		this.apMatEmpleado = apMatEmpleado;
	}

	public Integer getIdPuesto() {
		return this.idPuesto;
	}

	public void setIdPuesto( Integer idPuesto ) {
		this.idPuesto = idPuesto;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd( String passwd ) {
		this.passwd = passwd;
	}

	public char getIdSync() {
		return this.idSync;
	}

	public void setIdSync( char idSync ) {
		this.idSync = idSync;
	}

	public Date getFechaMod() {
		return this.fechaMod;
	}

	public void setFechaMod( Date fechaMod ) {
		this.fechaMod = fechaMod;
	}

	public String getIdMod() {
		return this.idMod;
	}

	public void setIdMod( String idMod ) {
		this.idMod = idMod;
	}

	public Integer getIdEmpresa() {
		return this.idEmpresa;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal( Sucursal sucursal ) {
		this.sucursal = sucursal;
	}

	public void setIdEmpresa( Integer idEmpresa ) {
		this.idEmpresa = idEmpresa;
	}

	public String getNombreApellidos() {
		return new StringBuilder( nombreEmpleado ).append( " " ).append( apPatEmpleado ).append( " " ).append( apMatEmpleado ).toString();
	}
}

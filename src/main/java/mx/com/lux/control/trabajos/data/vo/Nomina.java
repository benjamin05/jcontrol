package mx.com.lux.control.trabajos.data.vo;

import java.util.Date;
// Generated 5/09/2011 02:50:16 PM by Hibernate Tools 3.4.0.CR1

/**
 * Nomina generated by hbm2java
 */
public class Nomina implements java.io.Serializable {

	private int idPedido;
	private String idFactura;
	private String factura;
	private String idEmpleado;
	private Acuses acuse;
	private String idEmp;
	private String empleado;
	private String idEmpresa;
	private String idParentesco;
	private String pagos;
	private String solicitud;
	private String autorizacion;
	private Date fechaSolicitud;
	private Date fechaAutorizacion;
	private String observaciones;
	private Date fechaMod;

	public Nomina() {
	}

	public Nomina( int idPedido ) {
		this.idPedido = idPedido;
	}

	public Nomina( int idPedido, String idFactura, String factura, String idEmpleado, Acuses acuse, String idEmp, String empleado, String idEmpresa, String idParentesco, String pagos, String solicitud, String autorizacion, Date fechaSolicitud, Date fechaAutorizacion, String observaciones, Date fechaMod ) {
		this.idPedido = idPedido;
		this.idFactura = idFactura;
		this.factura = factura;
		this.idEmpleado = idEmpleado;
		this.acuse = acuse;
		this.idEmp = idEmp;
		this.empleado = empleado;
		this.idEmpresa = idEmpresa;
		this.idParentesco = idParentesco;
		this.pagos = pagos;
		this.solicitud = solicitud;
		this.autorizacion = autorizacion;
		this.fechaSolicitud = fechaSolicitud;
		this.fechaAutorizacion = fechaAutorizacion;
		this.observaciones = observaciones;
		this.fechaMod = fechaMod;
	}

	public int getIdPedido() {
		return this.idPedido;
	}

	public void setIdPedido( int idPedido ) {
		this.idPedido = idPedido;
	}

	public String getIdFactura() {
		return this.idFactura;
	}

	public void setIdFactura( String idFactura ) {
		this.idFactura = idFactura;
	}

	public String getFactura() {
		return this.factura;
	}

	public void setFactura( String factura ) {
		this.factura = factura;
	}

	public String getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado( String idEmpleado ) {
		this.idEmpleado = idEmpleado;
	}

	public Acuses getAcuse() {
		return acuse;
	}

	public void setAcuse( Acuses acuse ) {
		this.acuse = acuse;
	}

	public String getIdEmp() {
		return this.idEmp;
	}

	public void setIdEmp( String idEmp ) {
		this.idEmp = idEmp;
	}

	public String getEmpleado() {
		return this.empleado;
	}

	public void setEmpleado( String empleado ) {
		this.empleado = empleado;
	}

	public String getIdEmpresa() {
		return this.idEmpresa;
	}

	public void setIdEmpresa( String idEmpresa ) {
		this.idEmpresa = idEmpresa;
	}

	public String getIdParentesco() {
		return this.idParentesco;
	}

	public void setIdParentesco( String idParentesco ) {
		this.idParentesco = idParentesco;
	}

	public String getPagos() {
		return this.pagos;
	}

	public void setPagos( String pagos ) {
		this.pagos = pagos;
	}

	public String getSolicitud() {
		return this.solicitud;
	}

	public void setSolicitud( String solicitud ) {
		this.solicitud = solicitud;
	}

	public String getAutorizacion() {
		return this.autorizacion;
	}

	public void setAutorizacion( String autorizacion ) {
		this.autorizacion = autorizacion;
	}

	public Date getFechaSolicitud() {
		return this.fechaSolicitud;
	}

	public void setFechaSolicitud( Date fechaSolicitud ) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaAutorizacion() {
		return this.fechaAutorizacion;
	}

	public void setFechaAutorizacion( Date fechaAutorizacion ) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones( String observaciones ) {
		this.observaciones = observaciones;
	}

	public Date getFechaMod() {
		return this.fechaMod;
	}

	public void setFechaMod( Date fechaMod ) {
		this.fechaMod = fechaMod;
	}

}

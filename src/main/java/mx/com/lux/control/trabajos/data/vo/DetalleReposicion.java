package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.util.Date;

public class DetalleReposicion implements Serializable {

	private static final long serialVersionUID = -4998239326765253993L;

	private String factura;
	private Integer numeroOrden;
	private String idSucursal;
	private String ojo;
	private String tipo;
	private String valorAnterior;
	private String valorNuevo;
	private String campo;
	private Date fecha;

	public DetalleReposicion() {
		this.fecha = new Date();
	}

	public DetalleReposicion( String factura, Integer numeroOrden, String idSucursal, String ojo, String tipo, String valorAnterior, String valorNuevo, String campo ) {
		this.factura = factura;
		this.numeroOrden = numeroOrden;
		this.idSucursal = idSucursal;
		this.ojo = ojo;
		this.tipo = tipo;
		this.valorAnterior = valorAnterior;
		this.valorNuevo = valorNuevo;
		this.campo = campo;
		this.fecha = new Date();
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura( String factura ) {
		this.factura = factura;
	}

	public Integer getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden( Integer numeroOrden ) {
		this.numeroOrden = numeroOrden;
	}

	public String getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal( String sucursal ) {
		this.idSucursal = sucursal;
	}

	public String getOjo() {
		return ojo;
	}

	public void setOjo( String ojo ) {
		this.ojo = ojo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo( String tipo ) {
		this.tipo = tipo;
	}

	public String getValorAnterior() {
		return valorAnterior;
	}

	public void setValorAnterior( String valorAnterior ) {
		this.valorAnterior = valorAnterior;
	}

	public String getValorNuevo() {
		return valorNuevo;
	}

	public void setValorNuevo( String valorNuevo ) {
		this.valorNuevo = valorNuevo;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo( String campo ) {
		this.campo = campo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha( Date fecha ) {
		if( fecha != null ) {
			this.fecha = new Date( fecha.getTime() );
		}
	}
}

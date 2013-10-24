package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.util.Date;

public class DetalleNotaVenta implements Serializable {

	private static final long serialVersionUID = 5411134221636123006L;

	private String idFactura;
	private Integer idArticulo;
	private String idTipoDetalle;
	private Double cantidadFac;
	private String precioUnitLista;
	private String precioUnitFinal;
	private char idSync;
	private Date fechaMod;
	private String idMod;
	private int idSucursal;
	private Character surte;
	private String idRepVenta;
	private String precioCalcLista;
	private String precioCalcOferta;
	private String precioFactura;
	private String precioConv;

	public String getIdFactura() {
		return idFactura;
	}

	public void setIdFactura( String idFactura ) {
		this.idFactura = idFactura;
	}

	public Integer getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo( Integer idArticulo ) {
		this.idArticulo = idArticulo;
	}

	public String getIdTipoDetalle() {
		return this.idTipoDetalle;
	}

	public void setIdTipoDetalle( String idTipoDetalle ) {
		this.idTipoDetalle = idTipoDetalle;
	}

	public Double getCantidadFac() {
		return this.cantidadFac;
	}

	public void setCantidadFac( Double cantidadFac ) {
		this.cantidadFac = cantidadFac;
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

	public int getIdSucursal() {
		return this.idSucursal;
	}

	public void setIdSucursal( int idSucursal ) {
		this.idSucursal = idSucursal;
	}

	public Character getSurte() {
		return this.surte;
	}

	public void setSurte( Character surte ) {
		this.surte = surte;
	}

	public String getIdRepVenta() {
		return this.idRepVenta;
	}

	public void setIdRepVenta( String idRepVenta ) {
		this.idRepVenta = idRepVenta;
	}

	public String getPrecioUnitLista() {
		return precioUnitLista;
	}

	public void setPrecioUnitLista( String precioUnitLista ) {
		this.precioUnitLista = precioUnitLista;
	}

	public String getPrecioUnitFinal() {
		return precioUnitFinal;
	}

	public void setPrecioUnitFinal( String precioUnitFinal ) {
		this.precioUnitFinal = precioUnitFinal;
	}

	public String getPrecioCalcLista() {
		return precioCalcLista;
	}

	public void setPrecioCalcLista( String precioCalcLista ) {
		this.precioCalcLista = precioCalcLista;
	}

	public String getPrecioCalcOferta() {
		return precioCalcOferta;
	}

	public void setPrecioCalcOferta( String precioCalcOferta ) {
		this.precioCalcOferta = precioCalcOferta;
	}

	public String getPrecioFactura() {
		return precioFactura;
	}

	public void setPrecioFactura( String precioFactura ) {
		this.precioFactura = precioFactura;
	}

	public String getPrecioConv() {
		return precioConv;
	}

	public void setPrecioConv( String precioConv ) {
		this.precioConv = precioConv;
	}
}

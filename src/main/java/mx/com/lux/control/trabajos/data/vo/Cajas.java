package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;

public class Cajas implements Serializable {

	private static final long serialVersionUID = 7308243681292196640L;

	private int idCaja;
	private int idSucursal;
	private String descr;
	private String impFactura;
	private String impTicket;
	private String impReporte;
	private int letraAscii;
	private int numFactura;
	private String cajero;

	public int getIdCaja() {
		return this.idCaja;
	}

	public void setIdCaja( int idCaja ) {
		this.idCaja = idCaja;
	}

	public int getIdSucursal() {
		return this.idSucursal;
	}

	public void setIdSucursal( int idSucursal ) {
		this.idSucursal = idSucursal;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr( String descr ) {
		this.descr = descr;
	}

	public String getImpFactura() {
		return this.impFactura;
	}

	public void setImpFactura( String impFactura ) {
		this.impFactura = impFactura;
	}

	public String getImpTicket() {
		return this.impTicket;
	}

	public void setImpTicket( String impTicket ) {
		this.impTicket = impTicket;
	}

	public String getImpReporte() {
		return this.impReporte;
	}

	public void setImpReporte( String impReporte ) {
		this.impReporte = impReporte;
	}

	public int getLetraAscii() {
		return this.letraAscii;
	}

	public void setLetraAscii( int letraAscii ) {
		this.letraAscii = letraAscii;
	}

	public int getNumFactura() {
		return this.numFactura;
	}

	public void setNumFactura( int numFactura ) {
		this.numFactura = numFactura;
	}

	public String getCajero() {
		return this.cajero;
	}

	public void setCajero( String cajero ) {
		this.cajero = cajero;
	}

}

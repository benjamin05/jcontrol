package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;

public class Terminal implements Serializable {

	private static final long serialVersionUID = 8741876688000142047L;

	private Integer idSucursal;
	private String idTerminal;
	private Integer idBancoDep;
	private String descripcion;
	private String afiliacion;
	private boolean promocion;
	private String numero;

	Terminal() {
	}

	public Integer getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal( Integer id ) {
		this.idSucursal = id;
	}

	public String getIdTerminal() {
		return idTerminal;
	}

	public void setIdTerminal( String idTerminal ) {
		this.idTerminal = idTerminal;
	}

	public Integer getIdBancoDep() {
		return idBancoDep;
	}

	public void setIdBancoDep( Integer idBancoDep ) {
		this.idBancoDep = idBancoDep;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion( String descripcion ) {
		this.descripcion = descripcion;
	}

	public String getAfiliacion() {
		return afiliacion;
	}

	public void setAfiliacion( String afiliacion ) {
		this.afiliacion = afiliacion;
	}

	public boolean getPromocion() {
		return promocion;
	}

	public void setPromocion( boolean promocion ) {
		this.promocion = promocion;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero( String numero ) {
		this.numero = numero;
	}


}

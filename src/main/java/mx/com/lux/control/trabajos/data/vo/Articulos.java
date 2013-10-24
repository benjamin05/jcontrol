package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.util.Date;

public class Articulos implements Serializable {

	private static final long serialVersionUID = 2789003895112365658L;

	private Integer idArticulo;
	private String articulo;
	private String colorCode;
	private String descArticulo;
	private Character idGenerico;
	private String idGenTipo;
	private String idGenSubtipo;
	private String precio;
	private String precioO;
	private Character sArticulo;
	private Character idSync;
	private Date fechaMod;
	private String idMod;
	private Integer idSucursal;
	private String colorDesc;
	private String idCb;
	private Character idDisenoLente;

	public Integer getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo( Integer idArticulo ) {
		this.idArticulo = idArticulo;
	}

	public String getArticulo() {
		return articulo;
	}

	public void setArticulo( String articulo ) {
		this.articulo = articulo;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode( String colorCode ) {
		this.colorCode = colorCode;
	}

	public String getDescArticulo() {
		return descArticulo;
	}

	public void setDescArticulo( String descArticulo ) {
		this.descArticulo = descArticulo;
	}

	public Character getIdGenerico() {
		return idGenerico;
	}

	public void setIdGenerico( Character idGenerico ) {
		this.idGenerico = idGenerico;
	}

	public String getIdGenTipo() {
		return idGenTipo;
	}

	public void setIdGenTipo( String idGenTipo ) {
		this.idGenTipo = idGenTipo;
	}

	public String getIdGenSubtipo() {
		return idGenSubtipo;
	}

	public void setIdGenSubtipo( String idGenSubtipo ) {
		this.idGenSubtipo = idGenSubtipo;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio( String precio ) {
		this.precio = precio;
	}

	public String getPrecioO() {
		return precioO;
	}

	public void setPrecioO( String precioO ) {
		this.precioO = precioO;
	}

	public Character getsArticulo() {
		return sArticulo;
	}

	public void setsArticulo( Character sArticulo ) {
		this.sArticulo = sArticulo;
	}

	public Character getIdSync() {
		return idSync;
	}

	public void setIdSync( Character idSync ) {
		this.idSync = idSync;
	}

	public Date getFechaMod() {
		return fechaMod;
	}

	public void setFechaMod( Date fechaMod ) {
		this.fechaMod = fechaMod;
	}

	public String getIdMod() {
		return idMod;
	}

	public void setIdMod( String idMod ) {
		this.idMod = idMod;
	}

	public Integer getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal( Integer idSucursal ) {
		this.idSucursal = idSucursal;
	}

	public String getColorDesc() {
		return colorDesc;
	}

	public void setColorDesc( String colorDesc ) {
		this.colorDesc = colorDesc;
	}

	public String getIdCb() {
		return idCb;
	}

	public void setIdCb( String idCb ) {
		this.idCb = idCb;
	}

	public Character getIdDisenoLente() {
		return idDisenoLente;
	}

	public void setIdDisenoLente( Character idDisenoLente ) {
		this.idDisenoLente = idDisenoLente;
	}

}

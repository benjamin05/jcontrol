package mx.com.lux.control.trabajos.data.vo;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class JbDev implements Serializable {

	private Integer id;
	private String factura;
	private String sucursal;
	private String apartado;
	private String idViaje;
	private String documento;
	private String armazon;
	private String color;
	private Date fechaEnvio;
	private Timestamp fecha;
	private String idMod;
	private Boolean rx;

	public Integer getId() {
		return id;
	}

	public void setId( Integer idDev ) {
		this.id = idDev;
	}

	public String getFactura() {
		return factura;
	}

	public String factura() {
		return StringUtils.isNotBlank( factura ) ? factura.trim() : "";
	}

	public void setFactura( String factura ) {
		this.factura = factura;
	}

	public String getSucursal() {
		return sucursal;
	}

	public String sucursal() {
		return StringUtils.isNotBlank( sucursal ) ? sucursal.trim() : "";
	}

	public void setSucursal( String sucursal ) {
		this.sucursal = sucursal;
	}

	public String getApartado() {
		return apartado;
	}

	public String apartado() {
		return StringUtils.isNotBlank( apartado ) ? apartado.trim() : "";
	}

	public void setApartado( String apartado ) {
		this.apartado = apartado;
	}

	public String getIdViaje() {
		return idViaje;
	}

	public String idViaje() {
		return StringUtils.isNotBlank( idViaje ) ? idViaje.trim() : "";
	}

	public void setIdViaje( String idViaje ) {
		this.idViaje = idViaje;
	}

	public String getDocumento() {
		return documento;
	}

	public String documento() {
		return StringUtils.isNotBlank( documento ) ? documento.trim() : "";
	}

	public void setDocumento( String documento ) {
		this.documento = documento;
	}

	public String getArmazon() {
		return armazon;
	}

	public String armazon() {
		return StringUtils.isNotBlank( armazon ) ? armazon.trim() : "";
	}

	public void setArmazon( String armazon ) {
		this.armazon = armazon;
	}

	public String getColor() {
		return color;
	}

	public String color() {
		return StringUtils.isNotBlank( color ) ? color.trim() : "";
	}

	public void setColor( String color ) {
		this.color = color;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio( Date fechaEnvio ) {
		if ( fechaEnvio != null ) {
			this.fechaEnvio = new Date( fechaEnvio.getTime() );
		}
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha( Timestamp fecha ) {
		if ( fecha != null ) {
			this.fecha = new Timestamp( fecha.getTime() );
		}
	}

	public String getIdMod() {
		return idMod;
	}

	public void setIdMod( String idMod ) {
		this.idMod = idMod;
	}

	public Boolean isRx() {
		return rx;
	}

	public Boolean getRx() {
		return rx;
	}

	public void setRx( Boolean rx ) {
		this.rx = rx;
	}

	public boolean equals( Object other ) {
		if ( this == other ) {
			return true;
		}
		if ( other == null ) {
			return false;
		}
		if ( !( other instanceof JbDevId ) ) {
			return false;
		}
		JbDevId castOther = (JbDevId) other;
		return ( this.getId() == castOther.getIdDev() ) && ( ( this.getFactura() == castOther.getFactura() ) || ( this.getFactura() != null && castOther.getFactura() != null && this.getFactura().equals( castOther.getFactura() ) ) ) && ( ( this.getSucursal() == castOther.getSucursal() ) || ( this.getSucursal() != null && castOther.getSucursal() != null && this.getSucursal().equals( castOther.getSucursal() ) ) ) && ( ( this.getApartado() == castOther.getApartado() ) || ( this.getApartado() != null && castOther.getApartado() != null && this.getApartado().equals( castOther.getApartado() ) ) ) && ( ( this.getIdViaje() == castOther.getIdViaje() ) || ( this.getIdViaje() != null && castOther.getIdViaje() != null && this.getIdViaje().equals( castOther.getIdViaje() ) ) ) && ( ( this.getDocumento() == castOther.getDocumento() ) || ( this.getDocumento() != null && castOther.getDocumento() != null && this.getDocumento().equals( castOther.getDocumento() ) ) ) && ( ( this.getArmazon() == castOther.getArm() ) || ( this.getArmazon() != null && castOther.getArm() != null && this.getArmazon().equals( castOther.getArm() ) ) ) && ( ( this.getColor() == castOther.getCol() ) || ( this.getColor() != null && castOther.getCol() != null && this.getColor().equals( castOther.getCol() ) ) ) && ( ( this.getFechaEnvio() == castOther.getFechaEnvio() ) || ( this.getFechaEnvio() != null && castOther.getFechaEnvio() != null && this.getFechaEnvio().equals( castOther.getFechaEnvio() ) ) ) && ( ( this.getFecha() == castOther.getFecha() ) || ( this.getFecha() != null && castOther.getFecha() != null && this.getFecha().equals( castOther.getFecha() ) ) ) && ( ( this.getIdMod() == castOther.getIdMod() ) || ( this.getIdMod() != null && castOther.getIdMod() != null && this.getIdMod().equals( castOther.getIdMod() ) ) ) && ( this.isRx() == castOther.isRx() );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + id;
		result = 37 * result + ( factura == null ? 0 : factura.hashCode() );
		result = 37 * result + ( sucursal == null ? 0 : sucursal.hashCode() );
		result = 37 * result + ( apartado == null ? 0 : apartado.hashCode() );
		result = 37 * result + ( idViaje == null ? 0 : idViaje.hashCode() );
		result = 37 * result + ( documento == null ? 0 : documento.hashCode() );
		result = 37 * result + ( armazon == null ? 0 : armazon.hashCode() );
		result = 37 * result + ( color == null ? 0 : color.hashCode() );
		result = 37 * result + ( fechaEnvio == null ? 0 : fechaEnvio.hashCode() );
		result = 37 * result + ( fecha == null ? 0 : fecha.hashCode() );
		result = 37 * result + ( idMod == null ? 0 : idMod.hashCode() );
		result = 37 * result + ( rx ? 1 : 0 );
		return result;
	}
}

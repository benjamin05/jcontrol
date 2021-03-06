package mx.com.lux.control.trabajos.data.vo;

// Generated 21/07/2011 02:01:22 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * JbViajeId generated by hbm2java
 */
public class JbViajeId implements java.io.Serializable {

	private String idViaje;
	private Date fecha;

	public JbViajeId() {
	}

	public JbViajeId( String idViaje, Date fecha ) {
		this.idViaje = idViaje;
		this.fecha = fecha;
	}

	public String getIdViaje() {
		return this.idViaje;
	}

	public void setIdViaje( String idViaje ) {
		this.idViaje = idViaje;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha( Date fecha ) {
		this.fecha = fecha;
	}

	public boolean equals( Object other ) {
		if ( ( this == other ) )
			return true;
		if ( ( other == null ) )
			return false;
		if ( !( other instanceof JbViajeId ) )
			return false;
		JbViajeId castOther = (JbViajeId) other;

		return ( ( this.getIdViaje() == castOther.getIdViaje() ) || ( this.getIdViaje() != null && castOther.getIdViaje() != null && this.getIdViaje().equals( castOther.getIdViaje() ) ) ) && ( ( this.getFecha() == castOther.getFecha() ) || ( this.getFecha() != null && castOther.getFecha() != null && this.getFecha().equals( castOther.getFecha() ) ) );
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + ( getIdViaje() == null ? 0 : this.getIdViaje().hashCode() );
		result = 37 * result + ( getFecha() == null ? 0 : this.getFecha().hashCode() );
		return result;
	}

}

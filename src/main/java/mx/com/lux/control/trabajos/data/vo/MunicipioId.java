package mx.com.lux.control.trabajos.data.vo;

public class MunicipioId implements java.io.Serializable {

	private static final long serialVersionUID = -3732957056631651246L;

	private String idEstado;
	private String idLocalidad;

	public String getIdEstado() {
		return this.idEstado;
	}

	public void setIdEstado( String idEstado ) {
		this.idEstado = idEstado;
	}

	public String getIdLocalidad() {
		return this.idLocalidad;
	}

	public void setIdLocalidad( String idLocalidad ) {
		this.idLocalidad = idLocalidad;
	}

	public boolean equals( Object other ) {
		if ( ( this == other ) )
			return true;
		if ( ( other == null ) )
			return false;
		if ( !( other instanceof MunicipioId ) )
			return false;
		MunicipioId castOther = (MunicipioId) other;

		return ( ( this.getIdEstado() == castOther.getIdEstado() ) || ( this.getIdEstado() != null && castOther.getIdEstado() != null && this.getIdEstado().equals( castOther.getIdEstado() ) ) ) && ( ( this.getIdLocalidad() == castOther.getIdLocalidad() ) || ( this.getIdLocalidad() != null && castOther.getIdLocalidad() != null && this.getIdLocalidad().equals( castOther.getIdLocalidad() ) ) );
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + ( getIdEstado() == null ? 0 : this.getIdEstado().hashCode() );
		result = 37 * result + ( getIdLocalidad() == null ? 0 : this.getIdLocalidad().hashCode() );
		return result;
	}

}

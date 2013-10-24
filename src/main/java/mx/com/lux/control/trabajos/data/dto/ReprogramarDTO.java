package mx.com.lux.control.trabajos.data.dto;

public class ReprogramarDTO {

	public String contactoFormaContacto;
	public String descripcionTipoContacto;

	public ReprogramarDTO() {
		super();
	}

	public ReprogramarDTO( String contactoFormaContacto, String descripcionTipoContacto ) {
		super();
		this.contactoFormaContacto = contactoFormaContacto;
		this.descripcionTipoContacto = descripcionTipoContacto;
	}

	/**
	 * @return the contactoFormaContacto
	 */
	public String getContactoFormaContacto() {
		return contactoFormaContacto;
	}

	/**
	 * @param contactoFormaContacto the contactoFormaContacto to set
	 */
	public void setContactoFormaContacto( String contactoFormaContacto ) {
		this.contactoFormaContacto = contactoFormaContacto;
	}

	/**
	 * @return the descripcionTipoContacto
	 */
	public String getDescripcionTipoContacto() {
		return descripcionTipoContacto;
	}

	/**
	 * @param descripcionTipoContacto the descripcionTipoContacto to set
	 */
	public void setDescripcionTipoContacto( String descripcionTipoContacto ) {
		this.descripcionTipoContacto = descripcionTipoContacto;
	}


}

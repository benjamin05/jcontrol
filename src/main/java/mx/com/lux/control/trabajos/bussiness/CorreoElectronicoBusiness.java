package mx.com.lux.control.trabajos.bussiness;

import mx.com.lux.control.trabajos.exception.ApplicationException;

public interface CorreoElectronicoBusiness {

	void enviarCorreoContacto( String rx ) throws ApplicationException;

}

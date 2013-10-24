package mx.com.lux.control.trabajos.bussiness;

import mx.com.lux.control.trabajos.exception.ServiceException;

public interface RecepcionBusiness {

	void insertarAcuseRecepcionSatisfactoria( final String rx ) throws ServiceException;

}

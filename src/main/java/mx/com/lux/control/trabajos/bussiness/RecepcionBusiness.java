package mx.com.lux.control.trabajos.bussiness;

import mx.com.lux.control.trabajos.data.vo.JbLlamada;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.exception.ServiceException;

public interface RecepcionBusiness {

	void insertarAcuseRecepcionSatisfactoria( final String rx ) throws ServiceException;

    JbLlamada obtenerLlamadaPorRx( String rx ) throws DAOException;

}

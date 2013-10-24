package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.Reimpresion;
import mx.com.lux.control.trabajos.exception.DAOException;

public interface ReimpresionDAO extends BasicsDAO {

	void guarda( Reimpresion reimpresion ) throws DAOException;

	void guardaActualiza( Reimpresion reimpresion ) throws DAOException;

	Integer obtenConteoReimpresiones( String idFactura ) throws DAOException;

}

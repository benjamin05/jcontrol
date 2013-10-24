package mx.com.lux.control.trabajos.data.dao.trabajo;

import mx.com.lux.control.trabajos.data.dao.BasicsDAO;
import mx.com.lux.control.trabajos.data.vo.GParametro;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.io.Serializable;

public interface GParametroDAO extends BasicsDAO {

	/**
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Object findByPrimaryKey( Class<?> clazz, Serializable id ) throws DAOException;

	/**
	 * @param id
	 * @return
	 * @throws ApplicationException
	 */
	public GParametro findById( String id ) throws DAOException;
}

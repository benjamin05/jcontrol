package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.FormaContacto;
import mx.com.lux.control.trabajos.exception.DAOException;

public interface FormaContactoDAO extends BasicsDAO {

	FormaContacto obtenPorRx( String rx ) throws DAOException;

}

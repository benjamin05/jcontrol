package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.TipoContacto;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.List;

public interface TipoFormaContactoDAO extends BasicsDAO {

	List<TipoContacto> obtenerTodos() throws DAOException;
}

package mx.com.lux.control.trabajos.data.dao.trabajo;

import mx.com.lux.control.trabajos.data.dao.BasicsDAO;
import mx.com.lux.control.trabajos.data.vo.Receta;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.io.Serializable;

public interface RecetaDAO extends BasicsDAO {

	Object findByPrimaryKey( Class<?> clazz, Serializable id ) throws DAOException;

	Receta findById( Integer id ) throws DAOException;

	Receta obtenerRecetaPorTrabajo( String rx ) throws DAOException;

	Receta obtenerRecetaPorTipoOpt( String tipoOpt ) throws DAOException;

}

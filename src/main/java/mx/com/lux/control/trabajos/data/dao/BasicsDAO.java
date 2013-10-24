package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.exception.DAOException;

import java.io.Serializable;
import java.util.List;

/**
 * @author DGCM42
 */
public interface BasicsDAO {

	/**
	 * Metodo Generico para insertar/actualizar en base de datos con hibernate
	 *
	 * @param object
	 * @throws DAOException
	 */
	public void save( Object object ) throws DAOException;


    /**
     * Metodo Generico para insertar/actualizar en base de datos con hibernate
     *
     * @param object
     * @throws DAOException
     */
    public void mergeAndSave( Object object ) throws DAOException;



	/**
	 * Metodo Generico para insertar/actualizar en base de datos con hibernate
	 *
	 * @param object
	 * @throws DAOException
	 */
	public void saveOnly( Object object ) throws DAOException;

	/**
	 * Metodo Generico para eliminar en base de datos con hibernate
	 *
	 * @param object
	 * @throws DAOException
	 */
	public void delete( Object object ) throws DAOException;

	/**
	 * Metodo Generico para buscar un objeto por primary key
	 *
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Object findByPrimaryKey( Class<?> clazz, Serializable id ) throws DAOException;

	/**
	 * Metodo Generico para consultar todos los registros.
	 *
	 * @return
	 * @throws DAOException
	 */
	public List<?> findAll() throws DAOException;

}

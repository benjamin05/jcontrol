package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.Examen;
import mx.com.lux.control.trabajos.exception.DAOException;

public interface ExamenDAO extends BasicsDAO {

	Examen obtenExamen( Integer idExamen ) throws DAOException;

	Examen obtenExamenPorTrabajo( String rx ) throws DAOException;

}

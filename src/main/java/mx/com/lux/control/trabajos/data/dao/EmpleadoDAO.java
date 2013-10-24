package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.exception.DAOException;

public interface EmpleadoDAO extends BasicsDAO {

	Empleado obtenEmpleado( String idEmpleado ) throws DAOException;

}

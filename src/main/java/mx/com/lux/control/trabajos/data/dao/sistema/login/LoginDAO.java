package mx.com.lux.control.trabajos.data.dao.sistema.login;

import mx.com.lux.control.trabajos.data.dao.BasicsDAO;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.List;

public interface LoginDAO extends BasicsDAO {

	public List<Empleado> findUserPassword( String idEmpleado, String password ) throws DAOException;

	public Empleado findUserById( String idEmpleado ) throws DAOException;

}

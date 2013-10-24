package mx.com.lux.control.trabajos.bussiness.service.sistema.login;

import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.exception.ApplicationException;

import java.util.List;

public interface LoginService {

	public List<Empleado> findUserPassword( String idEmpleado, String password ) throws ApplicationException;

	public Empleado findUserById( String idEmpleado ) throws ApplicationException;

}

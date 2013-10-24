package mx.com.lux.control.trabajos.bussiness.service.sistema.login;

import mx.com.lux.control.trabajos.data.dao.sistema.login.LoginDAO;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service( "loginService" )
public class LoginServiceImpl implements LoginService {

	@Resource
	private LoginDAO loginDAO;

	public List<Empleado> findUserPassword( String idEmpleado, String password ) throws ApplicationException {
		return loginDAO.findUserPassword( idEmpleado, password );
	}

	public Empleado findUserById( String idEmpleado ) throws ApplicationException {
		return loginDAO.findUserById( idEmpleado );
	}
}

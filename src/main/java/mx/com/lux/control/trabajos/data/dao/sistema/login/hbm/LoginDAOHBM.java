package mx.com.lux.control.trabajos.data.dao.sistema.login.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.sistema.login.LoginDAO;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "loginDAO" )
public class LoginDAOHBM extends DAOSupport implements LoginDAO {

	private static final String QUERY_FIND_USER_PASSWORD = "findUserPassword";
	private static final String QUERY_FIND_USER = "findUserById";
	private static final String QUERY_ID_EMPLEADO_PARAM = "idEmpleadoParam";
	private static final String QUERY_PASSWORD_PARAM = "passwordParam";

	@Autowired
	public LoginDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<?> findAll() throws DAOException {
		return null;
	}

	@SuppressWarnings( "unchecked" )
	public List<Empleado> findUserPassword( String idEmpleado, String password ) throws DAOException {
		return (List<Empleado>) findByNamedQuery( QUERY_FIND_USER_PASSWORD, new String[]{ QUERY_ID_EMPLEADO_PARAM, QUERY_PASSWORD_PARAM }, new Object[]{ idEmpleado, password } );
	}

	@SuppressWarnings( "unchecked" )
	public Empleado findUserById( String idEmpleado ) throws DAOException {
		return (Empleado) getUniqueResult( QUERY_FIND_USER, new String[]{ QUERY_ID_EMPLEADO_PARAM }, new Object[]{ idEmpleado } );
	}

}

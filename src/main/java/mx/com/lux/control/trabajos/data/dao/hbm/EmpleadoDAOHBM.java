package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.EmpleadoDAO;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "empleadoDAO" )
public class EmpleadoDAOHBM extends DAOSupport implements EmpleadoDAO {

	@Autowired
	public EmpleadoDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<?> findAll() throws DAOException {
		return findAll( Empleado.class );
	}

	@Override
	public Empleado obtenEmpleado( String idEmpleado ) throws DAOException {
		if ( StringUtils.isNotBlank( idEmpleado ) ) {
			return (Empleado) getSession().get( Empleado.class, idEmpleado );
		}
		return null;
	}
}

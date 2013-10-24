package mx.com.lux.control.trabajos.data.dao.ordenservicio.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.ordenservicio.TipoContactoDAO;
import mx.com.lux.control.trabajos.data.vo.TipoContacto;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "tipoContactoDAO" )
public class TipoContactoDAOHBM extends DAOSupport implements TipoContactoDAO {

	public static final String QUERY_FIND_TIPO_CONTACTO = "From TipoContacto tc";

	@Autowired
	public TipoContactoDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<TipoContacto> findAll() throws DAOException {
		return (List<TipoContacto>) getSession().createQuery( QUERY_FIND_TIPO_CONTACTO ).list();
	}

}

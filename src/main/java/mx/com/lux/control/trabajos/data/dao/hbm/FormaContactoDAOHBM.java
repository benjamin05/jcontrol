package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.FormaContactoDAO;
import mx.com.lux.control.trabajos.data.vo.FormaContacto;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "formaContactoDAO" )
public class FormaContactoDAOHBM extends DAOSupport implements FormaContactoDAO {

	@Autowired
	public FormaContactoDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<?> findAll() throws DAOException {
		return findAll( FormaContacto.class );
	}

	@Override
	public FormaContacto obtenPorRx( String rx ) throws DAOException {
		Criteria criteria = getSession().createCriteria( FormaContacto.class );
		criteria.add( Restrictions.eq( "rx", rx ) );
		return (FormaContacto) criteria.uniqueResult();
	}
}

/**
 *
 */
package mx.com.lux.control.trabajos.data.dao.trabajo.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.trabajo.GParametroDAO;
import mx.com.lux.control.trabajos.data.vo.GParametro;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Oscar Vazquez
 */
@Repository( "gParametroDAO" )
public class GParametroDAOHBM extends DAOSupport implements GParametroDAO {

	public static final String QUERY_FIND_BY_ID = "findGParametroById";
	public static final String PARAM_ID = "gpParam";

	@Autowired
	public GParametroDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<?> findAll() throws DAOException {
		return findAll( GParametro.class );
	}

	@Override
	public GParametro findById( String id ) throws DAOException {
		Criteria criteria = getSession().createCriteria( GParametro.class );
		criteria.add( Restrictions.eq( "idParametro", id ) );
		return (GParametro) criteria.uniqueResult();
		// return ( GParametro ) getUniqueResult( QUERY_FIND_BY_ID, new String[] { PARAM_ID }, new Object[] { id } );
	}

}

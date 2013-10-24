package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.CondicionIcGenericoDAO;
import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.vo.CondicionIcGenerico;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "condicionIcGenericoDAO" )
public class CondicionIcGenericoDAOHBM extends DAOSupport implements CondicionIcGenericoDAO {

	@Autowired
	public CondicionIcGenericoDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<CondicionIcGenerico> findAll() throws DAOException {
		Criteria criteria = getSession().createCriteria( CondicionIcGenerico.class );
		return criteria.list();
	}

	@Override
	public CondicionIcGenerico obtenerPorGenericoPorConvenio( final String idGenerico, final String idConvenio ) {
		Criteria criteria = getSession().createCriteria( CondicionIcGenerico.class );
		criteria.add( Restrictions.eq( "idGenerico", idGenerico ) );
		criteria.add( Restrictions.eq( "idConvenio", idConvenio ) );
		return (CondicionIcGenerico) criteria.uniqueResult();
	}
}

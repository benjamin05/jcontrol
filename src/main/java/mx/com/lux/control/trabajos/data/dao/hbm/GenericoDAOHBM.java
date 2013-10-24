package mx.com.lux.control.trabajos.data.dao.hbm;


import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.GenericoDAO;
import mx.com.lux.control.trabajos.data.vo.Generico;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "genericoDAO" )
public class GenericoDAOHBM extends DAOSupport implements GenericoDAO {

	@Autowired
	public GenericoDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<Generico> findAll() throws DAOException {
		Criteria criteria = getSession().createCriteria( Generico.class );
		criteria.addOrder( Order.asc( "id" ) );
		return criteria.list();
	}

	public List<Generico> obtenerGenericosGarantias() {
		Criteria criteria = getSession().createCriteria( Generico.class );
		//criteria.add( Restrictions.in( "id", new Character[]{ 'A', 'B', 'D' } ) );
		criteria.add( Restrictions.in( "id", new Character[]{ 'A', 'B' } ) );
		criteria.addOrder( Order.asc( "id" ) );
		return criteria.list();
	}
}

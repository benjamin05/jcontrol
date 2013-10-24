package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.TipoPagoDAO;
import mx.com.lux.control.trabajos.data.vo.TipoPago;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "tipoPagoDAO" )
public class TipoPagoDAOHBM extends DAOSupport implements TipoPagoDAO {

	@Autowired
	public TipoPagoDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<TipoPago> findAll() throws DAOException {
		Criteria criteria = getSession().createCriteria( TipoPago.class );
		return criteria.list();
	}

	@Override
	public TipoPago obtenerPorId( final String id ) {
		Criteria criteria = getSession().createCriteria( TipoPago.class );
		criteria.add( Restrictions.eq( "id", id ) );
		return (TipoPago) criteria.uniqueResult();
	}

}

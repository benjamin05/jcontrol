package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.ExternoDAO;
import mx.com.lux.control.trabajos.data.vo.JbExterno;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "externoDAO" )
public class ExternoDAOHBM extends DAOSupport implements ExternoDAO {

	@Autowired
	public ExternoDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<?> findAll() throws DAOException {
		return null;
	}

	@Override
	public JbExterno obtenerPorRx( final String rx ) {
		Criteria criteria = getSession().createCriteria( JbExterno.class );
		criteria.add( Restrictions.eq( "factura", rx ) );
		return (JbExterno) criteria.uniqueResult();
	}

	@Override
	public void actualizarFechaEntrega( final String rx ) {
		if ( rx != null ) {
			SQLQuery query = getSession().createSQLQuery( "update public.jb_externos set fecha_entrega = current_timestamp where factura = ?" );
			query.setParameter( 0, rx );
			query.executeUpdate();
		}
	}

}

package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.JbGarantiaDAO;
import mx.com.lux.control.trabajos.data.vo.JbGarantia;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "jbGarantiaDAO" )
public class JbGarantiaDAOHBM extends DAOSupport implements JbGarantiaDAO {

	@Autowired
	public JbGarantiaDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		setSessionFactory( sessionFactory );
	}

	@Override
	public List<JbGarantia> findAll() throws DAOException {
		Criteria criteria = getSession().createCriteria( JbGarantia.class );
		return criteria.list();
	}

	@Override
	public JbGarantia obtenerPorId( Integer id ) {
		Criteria criteria = getSession().createCriteria( JbGarantia.class );
		criteria.add( Restrictions.eq( "id", id ) );
		return (JbGarantia) criteria.uniqueResult();
	}

	@Override
	public JbGarantia obtenerPorFactura( String factura ) {
		Criteria criteria = getSession().createCriteria( JbGarantia.class );
		criteria.add( Restrictions.eq( "id", Integer.parseInt( factura ) ) );
		return (JbGarantia) criteria.uniqueResult();
	}


}

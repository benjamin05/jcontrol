package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.JbDevDAO;
import mx.com.lux.control.trabajos.data.vo.JbDev;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "jbDevDAO" )
public class JbDevDAOHBM extends DAOSupport implements JbDevDAO {

	@Autowired
	public JbDevDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public Integer contarJbDevsEnvios() throws DAOException {
		Criteria criteria = getSession().createCriteria( JbDev.class );
		criteria.add( Restrictions.isNull( "fechaEnvio" ) );
		criteria.setProjection( Projections.rowCount() );
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<JbDev> obtenerJbDevsEnviosPaginado( Integer primerResultado, Integer numeroResultados ) throws DAOException {
		Criteria criteria = getSession().createCriteria( JbDev.class );
		criteria.add( Restrictions.isNull( "fechaEnvio" ) );
		criteria.setFirstResult( primerResultado );
		criteria.setMaxResults( numeroResultados );
		return (List<JbDev>) criteria.list();
	}

	@Override
	public List<JbDev> findAll() throws DAOException {
		Criteria criteria = getSession().createCriteria( JbDev.class );
		return (List<JbDev>) criteria.list();
	}
}

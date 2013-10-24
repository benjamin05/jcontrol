package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.TipoAcuseDAO;
import mx.com.lux.control.trabajos.data.vo.TipoAcuse;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "tipoAcuseDAO" )
public class TipoAcuseDAOHBM extends DAOSupport implements TipoAcuseDAO {

	@Autowired
	public TipoAcuseDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public TipoAcuse obtenerPorId( String id ) {
		Criteria criteria = getSession().createCriteria( TipoAcuse.class );
		criteria.add( Restrictions.eq( "id", id ) );
		return (TipoAcuse) criteria.uniqueResult();
	}

	@Override
	public List<TipoAcuse> findAll() throws DAOException {
		Criteria criteria = getSession().createCriteria( TipoAcuse.class );
		return criteria.list();
	}

}

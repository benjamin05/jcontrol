package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.BancoDAO;
import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.vo.Banco;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "bancoDAO" )
public class BancoDAOHBM extends DAOSupport implements BancoDAO {

	@Autowired
	public BancoDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<Banco> findAll() throws DAOException {
		Criteria criteria = getSession().createCriteria( Banco.class );
		return criteria.list();
	}

	@Override
	public Banco obtenerPorId( final String id ) {
		if ( StringUtils.isNotBlank( id ) ) {
			Criteria criteria = getSession().createCriteria( Banco.class );
			criteria.add( Restrictions.eq( "id", Integer.parseInt( id.trim() ) ) );
			return (Banco) criteria.uniqueResult();
		}
		return null;
	}

}

package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.ReimpresionDAO;
import mx.com.lux.control.trabajos.data.vo.NotaReimpresion;
import mx.com.lux.control.trabajos.data.vo.Reimpresion;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "reimpresionDAO" )
public class ReimpresionDAOHBM extends DAOSupport implements ReimpresionDAO {

	@Autowired
	public ReimpresionDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<?> findAll() throws DAOException {
		return findAll( Reimpresion.class );
	}

	@Override
	public void guarda( Reimpresion reimpresion ) throws DAOException {
		getSession().save( reimpresion );
	}

	@Override
	public void guardaActualiza( Reimpresion reimpresion ) throws DAOException {
		getSession().saveOrUpdate( reimpresion );
	}

	@Override
	public Integer obtenConteoReimpresiones( String idFactura ) throws DAOException {
		if ( StringUtils.isNotBlank( idFactura ) ) {
			Criteria criteria = getSession().createCriteria( Reimpresion.class );
			criteria.setProjection( Projections.rowCount() );
			criteria.add( Restrictions.eq( "nota", NotaReimpresion.RX.nota ) );
			criteria.add( Restrictions.eq( "idNota", idFactura ) );
			return (Integer) criteria.uniqueResult();
		}
		return null;
	}
}

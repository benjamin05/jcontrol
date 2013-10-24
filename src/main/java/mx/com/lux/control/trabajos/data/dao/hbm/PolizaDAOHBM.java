package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.PolizaDAO;
import mx.com.lux.control.trabajos.data.vo.Polizas;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository( "polizaDAO" )
public class PolizaDAOHBM extends DAOSupport implements PolizaDAO {

	@Autowired
	public PolizaDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<?> findAll() throws DAOException {
		return findAll( Polizas.class );
	}

	@Override
	public Polizas obtenPoliza( Integer idPoliza ) throws DAOException {
		if ( idPoliza != null ) {
			return (Polizas) getSession().get( Polizas.class, idPoliza );
		}
		return null;
	}

	@Override
	public Polizas obtenPolizaPorFactura( String rx ) throws DAOException {
		if ( StringUtils.isNotBlank( rx ) ) {
			Criteria criteria = getSession().createCriteria( Polizas.class );
			criteria.add( Restrictions.eq( "facturaVenta", rx ) );
			criteria.add( Restrictions.ne( "status", "C" ) );
			List<?> lista = criteria.list();
			if ( lista != null && !lista.isEmpty() ) {
				return (Polizas) lista.get( 0 );
			}
		}
		return null;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<Polizas> buscarPorRxYPorSucursal( final String rx, final Integer idSucursalOrigen ) {
		Criteria criteria = getSession().createCriteria( Polizas.class );
		criteria.add( Restrictions.eq( "facturaVenta", rx ) );
		criteria.add( Restrictions.eq( "idSucursal", idSucursalOrigen ) );
		return Collections.unmodifiableList( (List<Polizas>) criteria.list() );
	}
}

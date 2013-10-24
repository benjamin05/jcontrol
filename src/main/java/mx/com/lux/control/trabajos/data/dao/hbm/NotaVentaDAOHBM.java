package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.NotaVentaDAO;
import mx.com.lux.control.trabajos.data.vo.DetalleNotaVenta;
import mx.com.lux.control.trabajos.data.vo.NotaVenta;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository( "notaVentaDAO" )
public class NotaVentaDAOHBM extends DAOSupport implements NotaVentaDAO {

	@Autowired
	public NotaVentaDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<?> findAll() throws DAOException {
		return findAll( NotaVenta.class );
	}

	@Override
	public void guarda( NotaVenta notaVenta ) throws DAOException {
		getSession().save( notaVenta );
	}

	@Override
	public void guardaActualiza( NotaVenta notaVenta ) throws DAOException {
		getSession().saveOrUpdate( notaVenta );
	}

	@Override
	public NotaVenta obtenNotaVenta( String idFactura ) throws DAOException {
		if ( StringUtils.isNotBlank( idFactura ) ) {
			return (NotaVenta) getSession().get( NotaVenta.class, idFactura );
		}
		return null;
	}

	@Override
	public NotaVenta obtenNotaVentaPorTrabajo( final String rx ) throws DAOException {
		if ( StringUtils.isNotBlank( rx ) ) {
			Criteria criteria = getSession().createCriteria( NotaVenta.class );
			criteria.add( Restrictions.eq( "factura", rx ) );
			criteria.setMaxResults( 1 );
			return (NotaVenta) criteria.uniqueResult();
		}
		return null;
	}

	@Override
	public List<DetalleNotaVenta> obtenListaDetalleNotaVenta( String idFactura ) throws DAOException {
		if ( StringUtils.isNotBlank( idFactura ) ) {
			Criteria criteria = getSession().createCriteria( DetalleNotaVenta.class );
			criteria.add( Restrictions.eq( "idFactura", idFactura ) );
			return criteria.list();
		}
		return new ArrayList<DetalleNotaVenta>();
	}

	@Override
	public List<DetalleNotaVenta> obtenListaDetalleNotaVentaPorTrabajo( String rx ) throws DAOException {
		if ( StringUtils.isNotBlank( rx ) ) {
			DetachedCriteria notaVentaCriteria = DetachedCriteria.forClass( NotaVenta.class );
			notaVentaCriteria.setProjection( Property.forName( "idFactura" ) );
			notaVentaCriteria.add( Restrictions.eq( "factura", rx ) );
			Criteria criteria = getSession().createCriteria( DetalleNotaVenta.class );
			criteria.add( Property.forName( "idFactura" ).eq( notaVentaCriteria ) );
			return criteria.list();
		}
		return new ArrayList<DetalleNotaVenta>();
	}
}

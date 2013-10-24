package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.ArticuloDAO;
import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.vo.Articulos;
import mx.com.lux.control.trabajos.data.vo.DetalleNotaVenta;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.NotaVenta;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository( "articuloDAO" )
public class ArticuloDAOHBM extends DAOSupport implements ArticuloDAO {

	@Autowired
	public ArticuloDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<?> findAll() throws DAOException {
		return findAll( Articulos.class );
	}

	@Override
	public Articulos obtenArticulo( Integer idArticulo ) throws DAOException {
		if ( idArticulo != null ) {
			return (Articulos) getSession().get( Articulos.class, idArticulo );
		}
		return null;
	}

	@Override
	public List<Articulos> obtenListaArticulosPorTrabajo( String rx ) throws DAOException {
		if ( StringUtils.isNotBlank( rx ) ) {
			DetachedCriteria trabajoCriteria = DetachedCriteria.forClass( Jb.class );
			trabajoCriteria.setProjection( Property.forName( "rx" ) );
			trabajoCriteria.add( Restrictions.eq( "rx", rx ) );
			DetachedCriteria notaVentaCriteria = DetachedCriteria.forClass( NotaVenta.class );
			notaVentaCriteria.setProjection( Property.forName( "idFactura" ) );
			notaVentaCriteria.add( Property.forName( "factura" ).eq( trabajoCriteria ) );
			DetachedCriteria detalleNotaVentaCriteria = DetachedCriteria.forClass( DetalleNotaVenta.class );
			detalleNotaVentaCriteria.setProjection( Property.forName( "idArticulo" ) );
			detalleNotaVentaCriteria.add( Property.forName( "idFactura" ).eq( notaVentaCriteria ) );
			Criteria criteria = getSession().createCriteria( Articulos.class );
			criteria.add( Property.forName( "idArticulo" ).in( detalleNotaVentaCriteria ) );
			return criteria.list();
		}
		return new ArrayList<Articulos>();
	}

	@Override
	public Articulos obtenArticuloGenericoAPorNotaVenta( String idFactura ) throws DAOException {
		if ( StringUtils.isNotBlank( idFactura ) ) {
			StringBuilder sb = new StringBuilder();
			sb.append( "SELECT a FROM Articulos a, DetalleNotaVenta d " );
			sb.append( "WHERE a.idGenerico=:idGenerico " );
			sb.append( "AND a.idArticulo=d.idArticulo " );
			sb.append( "AND d.idFactura=:idFactura" );
			Query query = getSession().createQuery( sb.toString() );
			query.setCharacter( "idGenerico", 'A' );
			query.setString( "idFactura", idFactura );
			List<?> lista = query.list();
			if ( lista != null && !lista.isEmpty() ) {
				return (Articulos) lista.get( 0 );
			}
		}
		return null;
	}

	@Override
	public Articulos obtenArticuloGenericoBPorNotaVenta( String idFactura ) throws DAOException {
		if ( StringUtils.isNotBlank( idFactura ) ) {
			StringBuilder sb = new StringBuilder();
			sb.append( "SELECT a FROM Articulos a, DetalleNotaVenta d " );
			sb.append( "WHERE a.idGenerico=:idGenerico " );
			sb.append( "AND a.idArticulo=d.idArticulo " );
			sb.append( "AND d.idFactura=:idFactura" );
			Query query = getSession().createQuery( sb.toString() );
			query.setCharacter( "idGenerico", 'B' );
			query.setString( "idFactura", idFactura );
			List<?> lista = query.list();
			if ( lista != null && !lista.isEmpty() ) {
				return (Articulos) lista.get( 0 );
			}
		}
		return null;
	}
}

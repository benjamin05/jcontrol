package mx.com.lux.control.trabajos.data.dao.trabajo.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.trabajo.RecetaDAO;
import mx.com.lux.control.trabajos.data.vo.Receta;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "recetaDAO" )
public class RecetaDAOHBM extends DAOSupport implements RecetaDAO {

	@Autowired
	public RecetaDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<?> findAll() throws DAOException {
		return findAll( Receta.class );
	}

	@Override
	public Receta findById( Integer id ) throws DAOException {
		if ( id != null ) {
			return (Receta) getSession().get( Receta.class, id );
		}
		return null;
	}

	@Override
	public Receta obtenerRecetaPorTrabajo( String rx ) throws DAOException {
		if ( StringUtils.isNotBlank( rx ) ) {
			StringBuilder sb = new StringBuilder();
			sb.append( "SELECT r FROM Receta r, NotaVenta n, Jb j " );
			sb.append( "WHERE j.rx=:rx " );
			sb.append( "AND j.rx=n.factura " );
			sb.append( "AND n.receta=r.idReceta" );
			Query query = getSession().createQuery( sb.toString() );
			query.setString( "rx", rx );
			List<?> lista = query.list();
			if ( lista != null && !lista.isEmpty() ) {
				return (Receta) lista.get( 0 );
			}
		}
		return null;
	}

	@Override
	public Receta obtenerRecetaPorTipoOpt( String tipoOpt ) throws DAOException {
		if( StringUtils.isNotBlank( tipoOpt ) ) {
			Criteria criteria = getSession().createCriteria( Receta.class );
			criteria.add( Restrictions.eq( "tipoOpt", tipoOpt ) );
			return (Receta) criteria.uniqueResult();
		}
		return null;
	}
}

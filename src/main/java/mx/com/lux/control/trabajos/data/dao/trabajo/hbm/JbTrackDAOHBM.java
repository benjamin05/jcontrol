package mx.com.lux.control.trabajos.data.dao.trabajo.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.trabajo.JbTrackDAO;
import mx.com.lux.control.trabajos.data.vo.JbEstado;
import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "trackDAO" )
public class JbTrackDAOHBM extends DAOSupport implements JbTrackDAO {

	private static final String QUERY_FIND_JBTRACK_BY_RX_AND_ESTADO = "findJbTrackByRxAndEstado";
	private static final String QUERY_FIND_JBTRACK_BY_RX = "findJbTrackByRx";

	@Autowired
	public JbTrackDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<JbTrack> findJbTrackByRxAndEstado( String rx, String estado ) throws DAOException {
		return (List<JbTrack>) findByNamedQuery( QUERY_FIND_JBTRACK_BY_RX_AND_ESTADO, new Object[]{ rx, estado } );
	}

	@Override
	public List<?> findAll() throws DAOException {
		return null;
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public List<JbTrack> findJbTrackByRx( String rx ) throws DAOException {
		Criteria criteria = getSession().createCriteria( JbTrack.class );
		criteria.add( Restrictions.eq( "rx", rx ) );
		criteria.addOrder( Order.desc( "fecha" ) );
		return criteria.list();
	}

	@Override
	public JbTrack obtenerUltimoJbTrackX1PorRx( String rx ) throws DAOException {
		Criteria criteria = getSession().createCriteria( JbTrack.class );
		criteria.add( Restrictions.eq( "rx", rx ) );
		criteria.add( Restrictions.eq( "estado", "X1" ) );
		criteria.addOrder( Order.desc( "fecha" ) );
		criteria.setMaxResults( 1 );
		return (JbTrack) criteria.uniqueResult();
	}

	@Override
	public void saveOrUpdate( JbTrack track ) throws DAOException {
		getSession().saveOrUpdate( track );
	}

	@Override
	public JbTrack obtenerUltimoTrackParaDesentregarGarantia( final String rx ) {
		Criteria criteria = getSession().createCriteria( JbTrack.class );
		criteria.add( Restrictions.eq( "rx", rx ) );
		criteria.add( Restrictions.ne( "estado", "TE" ) );
		criteria.add( Restrictions.ne( "estado", "AE" ) );
		criteria.add( Restrictions.ne( "estado", "AR" ) );
		criteria.addOrder( Order.desc( "fecha" ) );
		List<JbTrack> tracks = (List<JbTrack>) criteria.list();

		for ( JbTrack track : tracks ) {
			Criteria criteria2 = getSession().createCriteria( JbEstado.class );
			criteria2.add( Restrictions.eq( "idEdo", track.getEstado() ) );
			JbEstado estado = (JbEstado) criteria2.uniqueResult();
			String llamada = StringUtils.isNotBlank( estado.getLlamada() ) ? estado.getLlamada().trim() : "";
			if( !"NA".equals( llamada ) ) {
				return track;
			}
		}
		return null;
	}
}

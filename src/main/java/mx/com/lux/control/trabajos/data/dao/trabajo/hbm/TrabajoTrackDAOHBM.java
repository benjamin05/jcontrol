package mx.com.lux.control.trabajos.data.dao.trabajo.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoTrackDAO;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.TrackView;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "trabajoTrackDAO" )
public class TrabajoTrackDAOHBM extends DAOSupport implements TrabajoTrackDAO {

	private static final String QUERY_FINDALL_Table_jb_BY_FILTERS_PARAM_RX = "rxParam";
	private static final String QUERY_FIND_ALL_TRACKVIEW = "findAllTrackViewByRx";
	private static final String QUERY_COUNT_ALL_TRACKVIEW = "countAllTrackViewByRx";

	@Autowired
	public TrabajoTrackDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<?> findAll() throws DAOException {
		return (List<Jb>) findByNamedQuery( "findAll" );
	}

	@SuppressWarnings( "unchecked" )
	public List<TrackView> findAllTrackView( String rx, int firstResult, int resultSize ) throws DAOException {
		return (List<TrackView>) findByNamedQueryPaging( QUERY_FIND_ALL_TRACKVIEW, new String[]{ QUERY_FINDALL_Table_jb_BY_FILTERS_PARAM_RX }, new Object[]{ rx }, firstResult, resultSize );
	}

	public int countAllTrackView( String rx ) throws DAOException {
		return countByNamedQuery( QUERY_COUNT_ALL_TRACKVIEW, new String[]{ QUERY_FINDALL_Table_jb_BY_FILTERS_PARAM_RX }, new Object[]{ rx } );
	}

}

package mx.com.lux.control.trabajos.bussiness.service.trabajo;

import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoTrackDAO;
import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.data.vo.TrackView;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service( "trabajoTrackService" )
public class TrabajoTrackServiceImpl implements TrabajoTrackService {

	@Resource
	private TrabajoTrackDAO trabajoTrackDAO;

	@Override
	public List<TrackView> findAllTrackView( String rx, int firstResult, int resultSize ) throws ApplicationException {
		return trabajoTrackDAO.findAllTrackView( rx, firstResult, resultSize );
	}

	@Override
	public int countAllTrackView( String rx ) throws ApplicationException {
		return trabajoTrackDAO.countAllTrackView( rx );
	}

	@Override
	public void save( JbTrack jbTrack ) throws ApplicationException {
		trabajoTrackDAO.save( jbTrack );
	}

}

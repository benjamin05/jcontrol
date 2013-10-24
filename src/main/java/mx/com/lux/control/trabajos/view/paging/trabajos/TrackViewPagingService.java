package mx.com.lux.control.trabajos.view.paging.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoTrackService;
import mx.com.lux.control.trabajos.data.vo.TrackView;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.view.paging.PagingService;

import java.util.List;

public class TrackViewPagingService implements PagingService<TrackView> {

	private TrabajoTrackService service;
	private String rx;

	public TrackViewPagingService( TrabajoTrackService service, String rx ) {
		super();
		this.service = service;
		this.rx = rx;
	}

	@Override
	public List<TrackView> getPage( int firstResult, int resultSize ) throws ApplicationException {
		return service.findAllTrackView( rx, firstResult, resultSize );
	}

	@Override
	public int count() throws ApplicationException {
		return service.countAllTrackView( rx );
	}

}

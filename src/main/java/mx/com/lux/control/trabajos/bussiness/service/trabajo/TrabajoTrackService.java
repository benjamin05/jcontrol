package mx.com.lux.control.trabajos.bussiness.service.trabajo;

import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.data.vo.TrackView;
import mx.com.lux.control.trabajos.exception.ApplicationException;

import java.util.List;

public interface TrabajoTrackService {

	public void save( JbTrack jbTrack ) throws ApplicationException;

	public List<TrackView> findAllTrackView( String rx, int firstResult, int resultSize ) throws ApplicationException;

	public int countAllTrackView( String rx ) throws ApplicationException;

    public List<JbTrack> findAllJbTracks(String rx) throws ApplicationException;

}

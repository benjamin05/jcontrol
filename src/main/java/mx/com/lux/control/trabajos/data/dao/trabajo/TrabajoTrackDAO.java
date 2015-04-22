package mx.com.lux.control.trabajos.data.dao.trabajo;

import mx.com.lux.control.trabajos.data.dao.BasicsDAO;
import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.data.vo.TrackView;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.List;

public interface TrabajoTrackDAO extends BasicsDAO {

	public List<TrackView> findAllTrackView( String rx, int firstResult, int resultSize ) throws DAOException;

	public int countAllTrackView( String rx ) throws DAOException;

    public List<JbTrack> findAllJbTrack(String rx) throws DAOException;
}

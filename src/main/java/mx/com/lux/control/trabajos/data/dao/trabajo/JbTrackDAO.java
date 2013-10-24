package mx.com.lux.control.trabajos.data.dao.trabajo;

import mx.com.lux.control.trabajos.data.dao.BasicsDAO;
import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.List;

public interface JbTrackDAO extends BasicsDAO {

	List<JbTrack> findJbTrackByRxAndEstado( String rx, String estado ) throws DAOException;

	List<JbTrack> findJbTrackByRx( String rx ) throws DAOException;

	void saveOrUpdate( JbTrack track ) throws DAOException;

	JbTrack obtenerUltimoJbTrackX1PorRx( String rx ) throws DAOException;

	JbTrack obtenerUltimoTrackParaDesentregarGarantia( String rx );

}

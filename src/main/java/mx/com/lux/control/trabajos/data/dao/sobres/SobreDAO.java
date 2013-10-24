package mx.com.lux.control.trabajos.data.dao.sobres;

import mx.com.lux.control.trabajos.data.dao.BasicsDAO;
import mx.com.lux.control.trabajos.data.vo.JbSobre;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.Date;
import java.util.List;

public interface SobreDAO extends BasicsDAO {

	public List<JbSobre> findAllSobres( int firstResult, int resultSize ) throws DAOException;

	public int countAllSobres() throws DAOException;

	public void deleteSobre( JbSobre jbSobre ) throws DAOException;

	public void saveSobre( JbSobre jbSobre ) throws DAOException;

	public List<JbSobre> findAllSobresByFechaEnvioNullAndRxEmpty() throws DAOException;

	public List<JbSobre> findAllSobresByFechaEnvioNullAndRxNotEmpty() throws DAOException;

	public List<JbSobre> findAllSobresByRx( String rx ) throws DAOException;

    public List<JbSobre> findAllSobresVaciosByViajeAndFechaEnvio( String idViaje, Date fechaEnvio) throws DAOException;

    public List<JbSobre> findAllSobresNoVaciosByViajeAndFechaEnvio( String idViaje, Date fechaEnvio) throws DAOException;

    public JbSobre findAllSobresByFechaEnvioNullAndNotTodayAndRxEmpty( String rx, Date fechaEnvio ) throws DAOException;

}

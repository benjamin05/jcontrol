package mx.com.lux.control.trabajos.data.dao.envio;

import mx.com.lux.control.trabajos.data.dao.BasicsDAO;
import mx.com.lux.control.trabajos.data.vo.JbViaje;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.Date;

public interface ViajeDAO extends BasicsDAO {

	public int findNextIdViaje() throws DAOException;

    public JbViaje findFolioViaje( String folioId, Date fech ) throws DAOException;

    public JbViaje findUltimoViaje( );

}

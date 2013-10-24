package mx.com.lux.control.trabajos.data.dao.trabajo;

import mx.com.lux.control.trabajos.data.dao.BasicsDAO;
import mx.com.lux.control.trabajos.data.vo.Cajas;
import mx.com.lux.control.trabajos.exception.DAOException;

public interface CajasDAO extends BasicsDAO {

	public Cajas findCajasById( Integer idCaja ) throws DAOException;

}

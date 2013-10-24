package mx.com.lux.control.trabajos.data.dao.envio;

import mx.com.lux.control.trabajos.data.dao.BasicsDAO;
import mx.com.lux.control.trabajos.data.vo.Sucursal;
import mx.com.lux.control.trabajos.exception.DAOException;

public interface SucursalDAO extends BasicsDAO {

	Sucursal findById( int id ) throws DAOException;

	Sucursal obtenerPorNombre( String nombre );

	Sucursal obtenerPorCentroCostos( String centroCostos );

}

package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.Polizas;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.List;

public interface PolizaDAO extends BasicsDAO {

	Polizas obtenPoliza( Integer idPoliza ) throws DAOException;

	Polizas obtenPolizaPorFactura( String rx ) throws DAOException;

	List<Polizas> buscarPorRxYPorSucursal( final String rx, final Integer idSucursalOrigen );

}

package mx.com.lux.control.trabajos.bussiness;

import mx.com.lux.control.trabajos.data.vo.Polizas;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.exception.ServiceException;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;

import java.util.List;

public interface EntregaExternoBusiness {

	void actualizarEstadoTrabajo( String rx, EstadoTrabajo estado ) throws ServiceException;

	void insertarTrack( String rx, EstadoTrabajo estado ) throws ServiceException;

	void actualizarFechaEntrega( String rx ) throws ServiceException;

	void insertarAcuses( String rx, EstadoTrabajo estado ) throws ServiceException;

	void insertarAcusesConSaldo( String rx, EstadoTrabajo estado ) throws ServiceException;

	void crearArchivoPagoExternos( String rx ) throws ServiceException;

	void crearArchivoPagoExternosConSaldo( String rx ) throws ServiceException;

	void crearArchivoEntregaExternos( String rx ) throws ServiceException;

	void procesarPoliza( String idPoliza ) throws ServiceException;

	List<Polizas> obtenerPolizas( String rx ) throws ServiceException;

	void actualizarPoliza( Polizas poliza ) throws DAOException;

	void insertarAcusePoliza( Polizas poliza ) throws ServiceException;

	void eliminarJbLlamada( String rx ) throws ServiceException;

	void actualizarPagosExterno( String rx, String idEmpleadoAtendio ) throws ServiceException;

}

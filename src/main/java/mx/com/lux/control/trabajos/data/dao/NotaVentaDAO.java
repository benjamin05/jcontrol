package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.DetalleNotaVenta;
import mx.com.lux.control.trabajos.data.vo.NotaVenta;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.List;

public interface NotaVentaDAO extends BasicsDAO {

	void guarda( NotaVenta notaVenta ) throws DAOException;

	void guardaActualiza( NotaVenta notaVenta ) throws DAOException;

	NotaVenta obtenNotaVenta( String idFactura ) throws DAOException;

	NotaVenta obtenNotaVentaPorTrabajo( String rx ) throws DAOException;

	List<DetalleNotaVenta> obtenListaDetalleNotaVenta( String idFactura ) throws DAOException;

	List<DetalleNotaVenta> obtenListaDetalleNotaVentaPorTrabajo( String rx ) throws DAOException;

}

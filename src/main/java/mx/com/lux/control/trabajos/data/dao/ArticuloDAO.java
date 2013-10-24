package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.Articulos;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.List;

public interface ArticuloDAO extends BasicsDAO {

	Articulos obtenArticulo( Integer idArticulo ) throws DAOException;

	List<Articulos> obtenListaArticulosPorTrabajo( String rx ) throws DAOException;

	Articulos obtenArticuloGenericoAPorNotaVenta( String idFactura ) throws DAOException;

	Articulos obtenArticuloGenericoBPorNotaVenta( String idFactura ) throws DAOException;

}

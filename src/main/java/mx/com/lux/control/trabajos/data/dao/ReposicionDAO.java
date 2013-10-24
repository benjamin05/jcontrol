package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.*;

import java.util.List;

public interface ReposicionDAO extends BasicsDAO {

	List<String> obtenerTodosRxConReposicion();

	List<Reposicion> buscarPorRx( String rx );

	Reposicion obtenerPorRxYNumeroOrden( String rx, Integer numeroOrden );

	List<ResponsableReposicion> obtenerTodosResponsableReposicion();

	List<Prisma> obtenerTodosPrisma();

	List<CausaReposicion> obtenerTodosCausaReposicion();

	List<DetalleReposicion> obtenerDetalleReposicionPorRx( String rx );

	List<DetalleReposicion> obtenerDetalleReposicionPorRxYNumeroOrden( String rx, Integer numeroOrden );

    Reposicion obtenerReposicionPorFactura( String factura );

}

package mx.com.lux.control.trabajos.bussiness.service;


import mx.com.lux.control.trabajos.data.vo.CausaReposicion;
import mx.com.lux.control.trabajos.data.vo.DetalleReposicion;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.Prisma;
import mx.com.lux.control.trabajos.data.vo.Receta;
import mx.com.lux.control.trabajos.data.vo.Reposicion;
import mx.com.lux.control.trabajos.data.vo.ResponsableReposicion;
import mx.com.lux.control.trabajos.data.vo.Sucursal;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.ServiceException;

import java.util.List;

public interface ReposicionService {

	List<Jb> buscarTrabajosReposicionesPorClientePorRx( String rx, String cliente );

	List<Reposicion> buscarReposicionesAsociadasAlTrabajo( String rx );

	Reposicion obtenerReposicionPorRxYNumeroOrden( String rx, Integer numeroOrden );

	Empleado buscarEmpleadoPorId( String idEmpleado );

	List<ResponsableReposicion> obtenerTodosResponsableReposicion();

	List<Prisma> obtenerTodosPrisma();

	List<CausaReposicion> obtenerTodosCausaReposicion();

	Receta obtenerRecetaReposicion( String rx );

	List<DetalleReposicion> buscarDetalleReposicionAsociadasAlTrabajo( String rx );

	List<DetalleReposicion> buscarDetalleReposicionAsociadasAReposicion( String rx, Integer numeroOrden );

	Reposicion procesarReposicion( Receta recetaAnt, Receta receta, Reposicion reposicion, String rx, Boolean ojoIzquierdoSeleccionado, Boolean ojoDerechoSeleccionado ) throws ApplicationException;

	void procesarReposicionSegundaParte( String rx, Integer numeroOrden ) throws ApplicationException;

	Sucursal obtenerSucursalReposicion( String rx );

	void imprimirTicketReposicion( final String factura, final Integer numeroOrden ) throws ServiceException;

    Reposicion obtenerReposicionPorFactura( String factura );

}

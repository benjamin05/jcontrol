package mx.com.lux.control.trabajos.view.paging.ordenservicio;

import mx.com.lux.control.trabajos.bussiness.service.ordenservicio.OrdenServicioService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.view.paging.PagingService;

import java.util.List;

public class OrdenServicioPagingService implements PagingService<Jb> {

	private OrdenServicioService service;
	private String orden;
	private String cliente;

	/**
	 * @param service
	 * @param rx
	 * @param estado
	 * @param atendio
	 * @param cliente
	 */
	public OrdenServicioPagingService( OrdenServicioService service, String orden, String cliente ) {
		super();
		this.service = service;
		this.orden = orden;
		this.cliente = cliente;
	}

	@Override
	public List<Jb> getPage( int firstResult, int resultSize ) throws ApplicationException {
		return service.findAllJbByOrdenAndCliente( orden, cliente, firstResult, resultSize );
	}

	@Override
	public int count() throws ApplicationException {
		return service.countAllJbByOrdenAndCliente( orden, cliente );
	}

}

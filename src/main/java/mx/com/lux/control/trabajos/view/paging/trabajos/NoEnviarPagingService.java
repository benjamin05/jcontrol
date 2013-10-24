package mx.com.lux.control.trabajos.view.paging.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.view.paging.PagingService;

import java.util.List;

public class NoEnviarPagingService implements PagingService<Jb> {

	private TrabajoService service;

	public NoEnviarPagingService( final TrabajoService service ) {
		super();
		this.service = service;
	}

	@Override
	public List<Jb> getPage( final int firstResult, final int resultSize ) throws ApplicationException {
		return service.findNoEnviar( firstResult, resultSize );
	}

	@Override
	public int count() throws ApplicationException {
		return service.countNoEnviar();
	}

}

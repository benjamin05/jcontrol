package mx.com.lux.control.trabajos.view.paging.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.view.paging.PagingService;

import java.util.List;

public class PorEnviarPagingService implements PagingService<Jb> {

	private TrabajoService service;

	public PorEnviarPagingService( TrabajoService service ) {
		super();
		this.service = service;
	}

	@Override
	public List<Jb> getPage( int firstResult, int resultSize ) throws ApplicationException {
		return service.findPorEnviar( firstResult, resultSize );
	}

	@Override
	public int count() throws ApplicationException {
		return service.countPorEnviar();
	}

}

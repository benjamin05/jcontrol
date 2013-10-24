package mx.com.lux.control.trabajos.view.paging.sobres;

import mx.com.lux.control.trabajos.bussiness.service.sobres.SobreService;
import mx.com.lux.control.trabajos.data.vo.JbSobre;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.view.paging.PagingService;

import java.util.List;

public class SobrePagingService implements PagingService<JbSobre> {

	private SobreService service;

	public SobrePagingService( SobreService service ) {
		super();
		this.service = service;
	}

	@Override
	public List<JbSobre> getPage( int firstResult, int resultSize ) throws ApplicationException {
		return service.findAllSobres( firstResult, resultSize );
	}

	@Override
	public int count() throws ApplicationException {
		return service.countAllSobres();
	}

}

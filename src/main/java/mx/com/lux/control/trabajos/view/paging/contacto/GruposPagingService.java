package mx.com.lux.control.trabajos.view.paging.contacto;

import mx.com.lux.control.trabajos.bussiness.service.contacto.ContactoViewService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.view.paging.PagingService;

import java.util.List;

public class GruposPagingService implements PagingService<Jb> {

	private ContactoViewService service;
	private String nombreGpo;
	private String rx;

	public GruposPagingService( ContactoViewService service, String nombreGpo, String rx ) {
		super();
		this.service = service;
		this.nombreGpo = nombreGpo;
		this.rx = rx;
	}

	@Override
	public List<Jb> getPage( int firstResult, int resultSize ) throws ApplicationException {
		return service.findAllGrupos( firstResult, resultSize, nombreGpo, rx );
	}

	@Override
	public int count() throws ApplicationException {
		return service.countAllGrupos( nombreGpo, rx );
	}

}

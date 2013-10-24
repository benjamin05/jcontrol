package mx.com.lux.control.trabajos.view.paging.contacto;

import mx.com.lux.control.trabajos.bussiness.service.contacto.ContactoViewService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.view.paging.PagingService;

import java.util.List;

public class GrupoPagingService implements PagingService<Jb> {

	private ContactoViewService service;
	private String idGrupo;

	public GrupoPagingService( ContactoViewService service, String idGrupo ) {
		super();
		this.service = service;
		this.idGrupo = idGrupo;
	}

	@Override
	public List<Jb> getPage( int firstResult, int resultSize ) throws ApplicationException {
		return service.findJbByGrupo( firstResult, resultSize, idGrupo );
	}

	@Override
	public int count() throws ApplicationException {
		return service.countJbByGrupo( idGrupo );
	}

}

package mx.com.lux.control.trabajos.view.paging.contacto;

import mx.com.lux.control.trabajos.bussiness.service.contacto.ContactoViewService;
import mx.com.lux.control.trabajos.data.vo.ContactoView;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.view.paging.PagingService;

import java.util.List;

public class ContactoViewPagingService implements PagingService<ContactoView> {

	private ContactoViewService service;
	private String atendio;

	/**
	 * @param service
	 * @param rx
	 * @param estado
	 * @param atendio
	 * @param cliente
	 */
	public ContactoViewPagingService( ContactoViewService service, String atendio ) {
		super();
		this.service = service;
		this.atendio = atendio;
	}

	@Override
	public List<ContactoView> getPage( int firstResult, int resultSize ) throws ApplicationException {
		return service.findAllLlamadasViewByFilters( atendio, firstResult, resultSize );
	}

	@Override
	public int count() throws ApplicationException {
		return service.countAllLlamadasViewByFilters( atendio );
	}

}

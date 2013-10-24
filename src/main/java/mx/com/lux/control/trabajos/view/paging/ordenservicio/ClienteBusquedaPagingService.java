package mx.com.lux.control.trabajos.view.paging.ordenservicio;

import mx.com.lux.control.trabajos.bussiness.service.ordenservicio.OrdenServicioService;
import mx.com.lux.control.trabajos.data.vo.Cliente;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.view.paging.PagingService;

import java.util.List;

public class ClienteBusquedaPagingService implements PagingService<Cliente> {

	private OrdenServicioService service;
	private String apPaterno;
	private String apMaterno;
	private String nombre;

	/**
	 * @param service
	 * @param rx
	 * @param estado
	 * @param atendio
	 * @param cliente
	 */
	public ClienteBusquedaPagingService( OrdenServicioService service, String apPaterno, String apMaterno, String nombre ) {
		super();
		this.service = service;
		this.apPaterno = apPaterno;
		this.apMaterno = apMaterno;
		this.nombre = nombre;
	}

	@Override
	public List<Cliente> getPage( int firstResult, int resultSize ) throws ApplicationException {
		return service.findAllClienteByApellidosAndNombre( apPaterno, apMaterno, nombre, firstResult, resultSize );
	}

	@Override
	public int count() throws ApplicationException {
		return service.countAllClienteByApellidosAndNombre( apPaterno, apMaterno, nombre );
	}

}

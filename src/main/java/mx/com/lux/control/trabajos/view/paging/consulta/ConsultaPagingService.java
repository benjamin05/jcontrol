package mx.com.lux.control.trabajos.view.paging.consulta;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.view.paging.PagingService;

import java.util.List;

public class ConsultaPagingService implements PagingService<Jb> {

	private TrabajoService service;
	private String rx;
	private Integer idEdoGrupo;
	private String atendio;
	private String cliente;

	/**
	 * @param service
	 * @param rx
	 * @param estado
	 * @param atendio
	 * @param cliente
	 */
	public ConsultaPagingService( TrabajoService service, String rx, Integer idEdoGrupo, String atendio, String cliente ) {
		super();
		this.service = service;
		this.rx = rx;
		this.idEdoGrupo = idEdoGrupo;
		this.atendio = atendio;
		this.cliente = cliente;
	}

	@Override
	public List<Jb> getPage( int firstResult, int resultSize ) throws ApplicationException {
		return service.findAlljbByFilters( rx, idEdoGrupo, atendio, cliente, firstResult, resultSize );
	}

	@Override
	public int count() throws ApplicationException {
		return service.countAlljbByFilters( rx, idEdoGrupo, atendio, cliente );
	}

}

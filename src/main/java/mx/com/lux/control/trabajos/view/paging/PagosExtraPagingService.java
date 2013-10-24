package mx.com.lux.control.trabajos.view.paging;

import mx.com.lux.control.trabajos.bussiness.service.ExternoService;
import mx.com.lux.control.trabajos.data.vo.JbPagoExtra;
import mx.com.lux.control.trabajos.exception.ApplicationException;

import java.util.List;

public class PagosExtraPagingService implements PagingService<JbPagoExtra> {

	private ExternoService externoService;
	private String rx;

	public PagosExtraPagingService( ExternoService externoService, String rx ) {
		super();
		this.externoService = externoService;
		this.rx = rx;
	}

	@Override
	public List<JbPagoExtra> getPage( int firstResult, int resultSize ) throws ApplicationException {
		return externoService.obtenerPagosExtraPorRxPaginado( rx, firstResult, resultSize );
	}

	@Override
	public int count() throws ApplicationException {
		return externoService.contarPagosExtraPorRx( rx );
	}

}

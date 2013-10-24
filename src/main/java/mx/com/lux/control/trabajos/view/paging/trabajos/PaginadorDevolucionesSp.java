package mx.com.lux.control.trabajos.view.paging.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.envio.EnvioService;
import mx.com.lux.control.trabajos.data.vo.JbDev;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.view.paging.PagingService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component( "paginadorDevolucionesSp" )
public class PaginadorDevolucionesSp implements PagingService<JbDev> {

	@Resource
	private EnvioService envioService;

	@Override
	public List<JbDev> getPage( final int primerResultado, final int numeroResultados ) throws ApplicationException {
		return envioService.obtenerJbDevsEnviosPaginado( primerResultado, numeroResultados );
	}

	@Override
	public int count() throws ApplicationException {
		return envioService.contarJbDevsEnvios();
	}
}

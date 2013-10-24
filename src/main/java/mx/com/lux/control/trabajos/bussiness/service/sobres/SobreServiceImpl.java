package mx.com.lux.control.trabajos.bussiness.service.sobres;

import mx.com.lux.control.trabajos.bussiness.service.impresora.TrabajoImpresion;
import mx.com.lux.control.trabajos.data.dao.sobres.SobreDAO;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.JbSobre;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service( "sobreService" )
public class SobreServiceImpl implements SobreService {

	@Resource
	private SobreDAO sobreDAO;

	@Resource
	private TrabajoImpresion trabajoImpresion;

	@Override
	public List<JbSobre> findAllSobres( int firstResult, int resultSize ) throws ApplicationException {
		return sobreDAO.findAllSobres( firstResult, resultSize );
	}

	@Override
	public int countAllSobres() throws ApplicationException {
		return sobreDAO.countAllSobres();
	}

	@Override
	public void deleteSobre( JbSobre jbSobre ) throws ApplicationException {
		sobreDAO.deleteSobre( jbSobre );
	}

	@Override
	public void saveSobre( JbSobre jbSobre ) throws ApplicationException {
		sobreDAO.saveSobre( jbSobre );
	}

	@Override
	public List<JbSobre> findAllSobresByFechaEnvioNullAndRxEmpty() throws ApplicationException {
		return sobreDAO.findAllSobresByFechaEnvioNullAndRxEmpty();
	}

	@Override
	public List<JbSobre> findAllSobresByFechaEnvioNullAndRxNotEmpty() throws ApplicationException {
		return sobreDAO.findAllSobresByFechaEnvioNullAndRxNotEmpty();
	}

	@Override
	public void imprimirSobre( Empleado empleado, JbSobre sobre ) throws ApplicationException {
		trabajoImpresion.imprimirSobre( empleado, sobre );
	}

	@Override
	public List<JbSobre> findAllSobresByRx( String rx ) throws ApplicationException {
		return sobreDAO.findAllSobresByRx( rx );
	}
}

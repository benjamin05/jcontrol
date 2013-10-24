package mx.com.lux.control.trabajos.bussiness.service.ordenservicio;

import mx.com.lux.control.trabajos.data.dao.ordenservicio.OrdenServicioDAO;
import mx.com.lux.control.trabajos.data.vo.Cliente;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbNota;
import mx.com.lux.control.trabajos.data.vo.JbServicio;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.constants.EstadoConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service( "ordenServicioService" )
public class OrdenServicioServiceImpl implements OrdenServicioService {

	@Resource
	private OrdenServicioDAO ordenServicioDAO;

	@Override
	public List<Jb> findAllJbByOrdenAndCliente( String orden, String cliente, int firstResult, int resultSize ) throws ApplicationException {
		return ordenServicioDAO.findAllJbByOrdenAndCliente( orden, cliente, firstResult, resultSize );
	}

	@Override
	public int countAllJbByOrdenAndCliente( String orden, String cliente ) throws ApplicationException {
		return ordenServicioDAO.countJbOrdenServicio( orden, cliente );
	}

	@Override
	public List<Jb> findJbByOrden( String orden, String cliente, int firstResult, int resultSize ) throws ApplicationException {
		return ordenServicioDAO.findJbByOrden( orden, cliente, firstResult, resultSize );
	}

	@Override
	public int countJbByOrden( String orden, String cliente ) throws ApplicationException {
		return ordenServicioDAO.countJbByOrden( orden, cliente );
	}

	@Override
	public List<Cliente> findAllClienteByApellidosAndNombre( String apPaterno, String apMaterno, String nombre, int firstResult, int resultSize ) throws ApplicationException {
		return ordenServicioDAO.findAllClienteByApellidosAndNombre( apPaterno, apMaterno, nombre, firstResult, resultSize );
	}

	@Override
	public int countAllClienteByApellidosAndNombre( String apPaterno, String apMaterno, String nombre ) throws ApplicationException {
		return ordenServicioDAO.countAllClienteByApellidosAndNombre( apPaterno, apMaterno, nombre );
	}

	@Override
	public boolean validarDesentregar( Jb jb ) {
		String status = jb.getEstado().getIdEdo().trim();

		if ( StringUtils.isNotBlank( status ) ) {
			return EstadoConstants.ESTADO_TRABAJO_ENTREGADO.equalsIgnoreCase( status );
		}
		return false;
	}

	@Override
	public boolean validarBodega( Jb jb ) {
		if ( jb.getEstado().getIdEdo().trim().equals( EstadoConstants.ESTADO_RECIBE_SUCURSAL ) || jb.getEstado().getIdEdo().trim().equals( EstadoConstants.ESTADO_RETENIDO ) )
			return true;
		return false;
	}

	@Override
	public List<JbServicio> findAllJbServicios() throws ApplicationException {
		return ordenServicioDAO.findAllJbServicios();
	}

}

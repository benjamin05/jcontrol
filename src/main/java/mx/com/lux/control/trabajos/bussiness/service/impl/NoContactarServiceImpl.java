package mx.com.lux.control.trabajos.bussiness.service.impl;

import mx.com.lux.control.trabajos.bussiness.service.NoContactarService;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoTrackDAO;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbLlamada;
import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.ServiceException;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;
import mx.com.lux.control.trabajos.view.Session;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service( "noContactarService" )
public class NoContactarServiceImpl implements NoContactarService {

	@Resource
	private TrabajoTrackDAO trabajoTrackDAO;

	@Resource
	private TrabajoDAO trabajoDAO;

	@Override
	public void procesarNoContactar( final String rx, final String observaciones ) throws ApplicationException {
		guardarTrack( rx, observaciones );
		actualizarTrabajo( rx );
		eliminarLlamada( rx );
	}

	private void guardarTrack( String rx, String observaciones ) throws ApplicationException {
		Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
		JbTrack jbTrack = new JbTrack();
		jbTrack.setRx( rx );
		jbTrack.setEstado( EstadoTrabajo.NO_CONTACTAR.codigo() );
		jbTrack.setEmpleado( empleado.getIdEmpleado() );
		jbTrack.setFecha( new Date() );
		jbTrack.setIdMod( "0" );
		jbTrack.setIdViaje( "" );
		jbTrack.setObservaciones( observaciones );
		trabajoTrackDAO.save( jbTrack );
	}

	private void actualizarTrabajo( String rx ) throws ApplicationException {
		Jb trabajo = trabajoDAO.findById( rx );
		trabajo.setNoLlamar( true );
		trabajoDAO.save( trabajo );
	}

	private void eliminarLlamada( String rx ) throws ApplicationException {
		try {
			JbLlamada jbLlamada = trabajoDAO.findJbLlamadaById( rx );
			if ( jbLlamada != null ) {
				trabajoDAO.delete( jbLlamada );
			}
		} catch ( Exception e ) {
			throw new ServiceException( "Error al eliminar JbLlamada", e );
		}
	}

}

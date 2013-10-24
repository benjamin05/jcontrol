package mx.com.lux.control.trabajos.bussiness.service.impl;

import mx.com.lux.control.trabajos.bussiness.service.ReprogramarService;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

@Service( "reprogramarService" )
public class ReprogramarServiceImpl implements ReprogramarService {

	@Resource
	private TrabajoDAO trabajoDAO;

	@Override
	public void reprogramarContacto( final String rx, final Date fechaVolverContactar ) throws ApplicationException {
		actualizarTrabajo( rx, fechaVolverContactar );
		insertarTrack( fechaVolverContactar );
		borrarLlamada( rx );
	}

	@Override
	public void reprogramarContactoGrupo( final String rx, final Date fechaVolverContactar, final JbGrupo grupo ) throws ApplicationException {
		actualizarTrabajo( rx, fechaVolverContactar );
		Jb trabajo = trabajoDAO.findById( rx );
		trabajoDAO.saveJbAndJbGrupo( trabajo, grupo );
		borrarLlamada( rx );
	}

	private void borrarLlamada( String rx ) throws DAOException {
		JbLlamada llamada = trabajoDAO.findJbLlamadaById( rx );
		if ( llamada != null ) {
			trabajoDAO.delete( llamada );
		}
	}

	private void actualizarTrabajo( final String rx, final Date fecha ) throws DAOException {
		Jb trabajo = trabajoDAO.findById( rx );
		trabajo.setVolverLlamar( fecha );
		trabajoDAO.save( trabajo );
	}

	private void insertarTrack( final Date fecha ) {
		JbTrack jbTrack = new JbTrack();
		jbTrack.setRx( (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX ) );
		jbTrack.setEstado( EstadoTrabajo.CONTACTO_REPROGRAMADO.codigo() );
		Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
		jbTrack.setEmpleado( empleado.getIdEmpleado() );
		jbTrack.setFecha( new Timestamp( System.currentTimeMillis() ) );
		jbTrack.setIdMod( String.valueOf( 0 ) );
		jbTrack.setIdViaje( Constants.CADENA_VACIA );
		jbTrack.setObservaciones( fecha.toString() );
	}
}

package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.EstadoConstants;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.widgets.Shell;

import java.sql.Timestamp;

public class Desretener {

	private final static TrabajoService trabajoService;

	private JbTrack jbTrack;
	private Shell shell;

	static {
		trabajoService = ServiceLocator.getBean( TrabajoService.class );
	}

	public Desretener() {
	}

	public Desretener( Shell shell ) {
		this.shell = shell;
		jbTrack = new JbTrack();
	}

	public void action() {
		if ( MessageDialogWithToggle.openQuestion( shell, MessagesPropertyHelper.getProperty( "enviar.title" ), MessagesPropertyHelper.getProperty( "enviar.message" ) ) ) {
			try {
				String jbRx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
				Jb jb = trabajoService.findById( jbRx );
				Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
				jbTrack.setRx( jbRx );
				jbTrack.setObservaciones( jb.getMaterial() );
				jbTrack.setEmpleado( empleado.getIdEmpleado() );
				jbTrack.setIdViaje( Constants.CADENA_VACIA );
				jbTrack.setFecha( new Timestamp( System.currentTimeMillis() ) );
				jbTrack.setIdMod( Constants.CERO_STRING );

				if ( jb.getRoto() != null && jb.getRoto() > 0 ) {
					jb.getEstado().setIdEdo( EstadoConstants.ESTADO_ROTO_POR_ENVIAR );
					jbTrack.setEstado( EstadoTrabajo.ROTO_POR_ENVIAR.codigo() );
				} else {
					jb.getEstado().setIdEdo( EstadoConstants.ESTADO_POR_ENVIAR );
					jbTrack.setEstado( EstadoTrabajo.POR_ENVIAR.codigo() );
				}

				trabajoService.saveJbAndTrack( jb, jbTrack );
				ApplicationUtils.recargarDatosPestanyaVisible();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
}

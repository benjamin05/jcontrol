package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.widgets.Shell;

public class Desvincular {

	private static TrabajoService trabajoService;

	private Shell shell;

	static {
		trabajoService = ServiceLocator.getBean( TrabajoService.class );
	}

	public Desvincular() {
	}

	public Desvincular( Shell shell ) {
		this.shell = shell;
	}

	public void action() {
		if ( MessageDialogWithToggle.openQuestion( shell, MessagesPropertyHelper.getProperty( "desvincular.title" ), MessagesPropertyHelper.getProperty( "desvincular.message" ) ) ) {
			try {
				String jbRx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX2 );
				if ( jbRx == null ) {
					jbRx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
				}
				trabajoService.desvincular( jbRx );
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX2 );
				ApplicationUtils.recargarDatosPestanyaVisible();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
}

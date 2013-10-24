package mx.com.lux.control.trabajos.view.container.reposicion;

import mx.com.lux.control.trabajos.bussiness.service.ReposicionService;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class MenuImprimirReposicion {

	private final Logger log = Logger.getLogger( MenuImprimirReposicion.class );

	private final Menu menu;
	private final Shell shell;
	private final ReposicionService reposicionService;

	public MenuImprimirReposicion( Shell parentShell, Control control ) {
		this.shell = parentShell;
		menu = new Menu( control );
		createMenu();
		reposicionService = ServiceLocator.getBean( ReposicionService.class );
	}

	public Menu getMenu() {
		return menu;
	}

	private void createMenu() {
		crearOpcionImprimirReposicion();
	}

	private void crearOpcionImprimirReposicion() {
		MenuItem menuItem = new MenuItem( menu, SWT.NONE );
		menuItem.setText( "Imprimir Reposición" );
		menuItem.setImage( ControlTrabajosWindowElements.popupWindowRecetaIcon );
		menuItem.setEnabled( true );

		menuItem.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				log.debug( "Imprimir Reposición" );
				String rx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
				Integer numeroOrden = (Integer) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_NUMERO_ORDEN );
				try {
					reposicionService.imprimirTicketReposicion( rx, numeroOrden );
				} catch( Exception e ) {
					log.error( "Error al imprimir ticket de reposicion" );
					MessageDialog.openError( shell, "Error", "Error al imprimir Ticket de la reposicion: Rx:" + rx + " - Numero Orden:" + numeroOrden );
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );
	}

}

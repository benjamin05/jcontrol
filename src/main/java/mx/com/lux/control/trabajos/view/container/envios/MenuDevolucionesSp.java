package mx.com.lux.control.trabajos.view.container.envios;

import mx.com.lux.control.trabajos.bussiness.service.envio.EnvioService;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class MenuDevolucionesSp {

	private final Logger log = Logger.getLogger( MenuDevolucionesSp.class );

	private final Menu menu;
	private final Shell shell;

	private final EnvioService envioService;

	public static String ITEM_SELECTED_JB_DEV = "ITEM_SELECTED_JB_DEV";

	public MenuDevolucionesSp( Shell parentShell, Control control ) {
		this.shell = parentShell;
		menu = new Menu( control );
		envioService = (EnvioService) ServiceLocator.getBean( "envioService" );
		createMenu();
	}

	public Menu getMenu() {
		return menu;
	}

	private void createMenu() {

		MenuItem menuItemImprimirTickets = new MenuItem( menu, SWT.NONE );
		menuItemImprimirTickets.setText( "Imprimir Tickets" );
		menuItemImprimirTickets.setImage( ControlTrabajosWindowElements.popupWindowRecetaIcon );
		menuItemImprimirTickets.setEnabled( true );

		menuItemImprimirTickets.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				try {
					Integer jbDevId = (Integer) Session.getAttribute( MenuDevolucionesSp.ITEM_SELECTED_JB_DEV );
					if ( jbDevId != null && jbDevId > -1 ) {
						log.debug( "Imprimiendo tickets Devolucion SP. Id: " + jbDevId );
						Integer idSucursal = (Integer) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_SUCURSAL );
						envioService.imprimirTicketsDevolucionSp( jbDevId, idSucursal );
					}
				} catch ( Exception e ) {
					log.error( "Error al imprimir tickets Devolución SP", e );
					/*
					MessageBox messageBox = new MessageBox( shell, SWT.ERROR );
					messageBox.setMessage( "Error al imprimir tickets Devolución SP" );
					messageBox.open();
					*/
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );

	}

}

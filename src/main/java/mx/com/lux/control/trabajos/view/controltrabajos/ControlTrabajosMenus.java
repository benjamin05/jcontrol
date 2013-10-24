package mx.com.lux.control.trabajos.view.controltrabajos;

import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.view.ControlTrabajosPrincipal;
import mx.com.lux.control.trabajos.view.container.contacto.GruposDataDialog;
import mx.com.lux.control.trabajos.view.container.envios.EnviarEFaxDialog;
import mx.com.lux.control.trabajos.view.container.sistema.login.LoginContainer;
import mx.com.lux.control.trabajos.view.container.sobres.SobresDialog;
import mx.com.lux.control.trabajos.view.container.trabajos.BuscarTrabajoRotoDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class ControlTrabajosMenus {

	private ApplicationWindow applicationWindow;
	private ControlTrabajosPrincipal controlTrabajosPrincipal;
	private Shell shell;
	private static Menu menuBarTools;
	private static Menu menuDropDown;

	public ControlTrabajosMenus() {
	}

	public ControlTrabajosMenus( Composite parent, ControlTrabajosPrincipal controlTrabajosPrincipal ) {
		this.controlTrabajosPrincipal = controlTrabajosPrincipal;
		menuBarTools = new Menu( parent.getShell(), SWT.BAR );
		menuDropDown = new Menu( parent.getShell(), SWT.DROP_DOWN );
		createMenuHerramientas();
	}

	public Menu getMenuHerramientas() {
		return menuBarTools;
	}

	private void createMenuHerramientas() {
		MenuItem itemMenuTools = new MenuItem( menuBarTools, SWT.CASCADE );
		itemMenuTools.setMenu( menuDropDown );
		itemMenuTools.setText( ApplicationPropertyHelper.getProperty( "options.herramientas" ) );
		addMenuSobres( menuDropDown );
		addEnviarEFax( menuDropDown );
		addGrupo( menuDropDown );
		anyadirOpcionMenuRotos( menuDropDown );
		anyadirOpcionCerrarSesion( menuDropDown );
		addMenuSalir( menuDropDown );
	}

	private void addMenuSobres( Menu menu ) {
		MenuItem itemMenuSobres = new MenuItem( menu, SWT.PUSH );
		itemMenuSobres.setText( ApplicationPropertyHelper.getProperty( "button.sobres.label" ) );
		itemMenuSobres.setImage( ControlTrabajosWindowElements.sobresIcon );
		itemMenuSobres.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				SobresDialog dialog = new SobresDialog( shell );
				dialog.open();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	private void addEnviarEFax( Menu menu ) {
		MenuItem itemMenu = new MenuItem( menu, SWT.PUSH );
		itemMenu.setText( ApplicationPropertyHelper.getProperty( "button.efax.label" ) );
		itemMenu.setImage( ControlTrabajosWindowElements.popupWindowEfaxIcon );
		itemMenu.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				EnviarEFaxDialog dialog = new EnviarEFaxDialog( shell );
				dialog.open();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	private void addGrupo( Menu menu ) {
		MenuItem itemMenu = new MenuItem( menu, SWT.PUSH );
		itemMenu.setText( ApplicationPropertyHelper.getProperty( "button.grupo.label" ) );
		itemMenu.setImage( ControlTrabajosWindowElements.popupWindowCreateGroupIcon );
		itemMenu.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				GruposDataDialog gruposDataDialog = new GruposDataDialog( shell );
				gruposDataDialog.open();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	private void addMenuSalir( Menu menu ) {
		MenuItem itemMenuSalir = new MenuItem( menu, SWT.PUSH );
		itemMenuSalir.setText( ApplicationPropertyHelper.getProperty( "button.salir.label" ) );
		itemMenuSalir.setImage( ControlTrabajosWindowElements.exitIcon );
		itemMenuSalir.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				System.exit( 0 );
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	private void anyadirOpcionMenuRotos( Menu menu ) {
		MenuItem menuItem = new MenuItem( menu, SWT.PUSH );
		menuItem.setText( "Rotos" );
		menuItem.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				BuscarTrabajoRotoDialog dialogo = new BuscarTrabajoRotoDialog( shell );
				dialogo.open();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );
	}

	private void anyadirOpcionCerrarSesion( Menu menu ) {
		MenuItem menuItem = new MenuItem( menu, SWT.PUSH );
		menuItem.setText( "Cerrar sesión" );
		menuItem.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				controlTrabajosPrincipal.close();
				LoginContainer login = new LoginContainer();
				login.open();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );
	}
}

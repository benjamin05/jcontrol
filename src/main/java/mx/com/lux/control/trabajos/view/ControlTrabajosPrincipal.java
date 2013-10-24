package mx.com.lux.control.trabajos.view;

import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.view.container.consulta.ConsultaContainer;
import mx.com.lux.control.trabajos.view.container.contacto.ContactoContainer;
import mx.com.lux.control.trabajos.view.container.envios.EnviosContainer;
import mx.com.lux.control.trabajos.view.container.garantia.GarantiaContainer;
import mx.com.lux.control.trabajos.view.container.ordenservicio.OrdenServicioContainer;
import mx.com.lux.control.trabajos.view.container.recepcion.RecepcionContainer;
import mx.com.lux.control.trabajos.view.container.reposicion.ReposicionContainer;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosMenus;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.ResourceBundle;

public class ControlTrabajosPrincipal extends ApplicationWindow {

	protected Shell shell;
	private final ContactoContainer contactoContainer;
	private final EnviosContainer enviosContainer;
	private final ConsultaContainer consultaContainer;
	private final RecepcionContainer recepcionContainer;
	private final OrdenServicioContainer ordenServicioContainer;
	private final GarantiaContainer garantiaContainer;
	private final ReposicionContainer reposicionContainer;
	private TabFolder tabFolder;

	public ControlTrabajosPrincipal() {
		super( null );
		contactoContainer = new ContactoContainer( this );
		enviosContainer = new EnviosContainer( this );
		consultaContainer = new ConsultaContainer( this );
		recepcionContainer = new RecepcionContainer( this );
		ordenServicioContainer = new OrdenServicioContainer( this );
		garantiaContainer = new GarantiaContainer( this );
		reposicionContainer = new ReposicionContainer( this );
		Session.setAttribute( "applicationWindow", this );
	}

	protected Control createContents( Composite parent ) {
		Empleado empleado = (Empleado) Session.atributes.get( Constants.PARAM_USER_LOGGED );

		ControlTrabajosMenus controlTrabajosMenus = new ControlTrabajosMenus( parent, this );
		parent.getShell().setMenuBar( controlTrabajosMenus.getMenuHerramientas() );

		Composite container = new Composite( parent, SWT.NONE );
		container.setLayout( new GridLayout( 1, false ) );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImage );

		Group group = new Group( container, SWT.NONE );
		group.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );
		group.setLayout( new GridLayout( 3, false ) );

		Label lblStoreName = new Label( group, SWT.NONE );
		lblStoreName.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		lblStoreName.setText( "Sucursal: " + empleado.getSucursal().getNombre() );

		Label lblDate = new Label( group, SWT.NONE );
		lblDate.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		lblDate.setText( "Fecha: " + ApplicationUtils.getCurrentDate() );

		Label lblUsuario = new Label( group, SWT.NONE );
		lblUsuario.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		lblUsuario.setText( "Usuario: " + empleado.getNombreApellidos() );

		tabFolder = new TabFolder( container, SWT.NONE );
		tabFolder.setBackgroundImage( ControlTrabajosWindowElements.backgroundImage );
        GridData gd = new GridData( SWT.FILL, SWT.FILL, true, true );
		gd.widthHint = 780;
		gd.heightHint = 432;
		tabFolder.setLayoutData( gd );

		TabItem tabContacto = new TabItem( tabFolder, SWT.NONE );
		tabContacto.setText( ApplicationPropertyHelper.getProperty( "options.contacto" ) );
		tabContacto.setImage( new Image( Display.getCurrent(), getClass().getResourceAsStream( ApplicationPropertyHelper.getProperty( "options.contacto.image" ) ) ) );
		tabContacto.setControl( contactoContainer.createContents( tabFolder ) );

		TabItem tabEnvios = new TabItem( tabFolder, SWT.NONE );
		tabEnvios.setText( ApplicationPropertyHelper.getProperty( "options.envios" ) );
		tabEnvios.setImage( new Image( Display.getCurrent(), getClass().getResourceAsStream( ApplicationPropertyHelper.getProperty( "options.envios.image" ) ) ) );
		tabEnvios.setControl( enviosContainer.createContents( tabFolder ) );

		TabItem tabRecepcion = new TabItem( tabFolder, SWT.NONE );
		tabRecepcion.setText( ApplicationPropertyHelper.getProperty( "options.recepcion" ) );
		tabRecepcion.setImage( new Image( Display.getCurrent(), getClass().getResourceAsStream( ApplicationPropertyHelper.getProperty( "options.recepcion.image" ) ) ) );
		tabRecepcion.setControl( recepcionContainer.createContents( tabFolder ) );

		TabItem tabConsulta = new TabItem( tabFolder, SWT.NONE );
		tabConsulta.setText( ApplicationPropertyHelper.getProperty( "options.consultas" ) );
		tabConsulta.setImage( new Image( Display.getCurrent(), getClass().getResourceAsStream( ApplicationPropertyHelper.getProperty( "options.consultas.image" ) ) ) );
		tabConsulta.setControl( consultaContainer.createContents( tabFolder ) );

		TabItem pestanyaReposiciones = new TabItem( tabFolder, SWT.NONE );
		pestanyaReposiciones.setText( "Reposiciones" );
		pestanyaReposiciones.setImage( new Image( Display.getCurrent(), getClass().getResourceAsStream( ApplicationPropertyHelper.getProperty( "options.rotos.image" ) ) ) );
		pestanyaReposiciones.setControl( reposicionContainer.createContents( tabFolder ) );

		TabItem tabOrdenServicio = new TabItem( tabFolder, SWT.NONE );
		tabOrdenServicio.setText( ApplicationPropertyHelper.getProperty( "options.orden.servicio" ) );
		tabOrdenServicio.setImage( new Image( Display.getCurrent(), getClass().getResourceAsStream( ApplicationPropertyHelper.getProperty( "options.orden.servicio.image" ) ) ) );
		tabOrdenServicio.setControl( ordenServicioContainer.createContents( tabFolder ) );

		TabItem tabGarantias = new TabItem( tabFolder, SWT.NONE );
		tabGarantias.setText( ApplicationPropertyHelper.getProperty( "options.garantias" ) );
		tabGarantias.setImage( new Image( Display.getCurrent(), getClass().getResourceAsStream( ApplicationPropertyHelper.getProperty( "options.garantias.image" ) ) ) );
		tabGarantias.setControl( garantiaContainer.createContents( tabFolder ) );

		tabFolder.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				int index = tabFolder.getSelectionIndex();
				setValuesVisibleModule( index );
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );

		setValuesVisibleModule( Constants.DEFAULT_TAB_POSITION );

        StringBuilder sb = new StringBuilder( "ver: " );
        sb.append( ResourceBundle.getBundle( "release" ).getString( "version" ) ).append( "-" );
        sb.append( ResourceBundle.getBundle( "release" ).getString( "revision" ) );
        sb.append( " fecha: " ).append( ResourceBundle.getBundle( "release" ).getString( "date" ) );
        Label release = new Label( container, SWT.NONE );
        release.setLayoutData( new GridData( SWT.CENTER, SWT.FILL, true, false ) );
        release.setText( sb.toString() );

		return container;
	}

	private void setValuesVisibleModule( Integer index ) {
		Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_TAB );
		if ( Constants.CONTACTO_TAB_POSITION.equals( index ) ) {
			Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_TAB, contactoContainer );
			contactoContainer.loadContacto();
		} else if ( Constants.CONSULTA_TAB_POSITION.equals( index ) ) {
			Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_TAB, consultaContainer );
		} else if ( Constants.ENVIOS_TAB_POSITION.equals( index ) ) {
			Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_TAB, enviosContainer );
			enviosContainer.cargarDatos();
		} else if ( Constants.ORDENSERVICIO_TAB_POSITION.equals( index ) ) {
			Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_TAB, ordenServicioContainer );
			ordenServicioContainer.llenarTabla();
		} else if ( Constants.RECEPCION_TAB_POSITION.equals( index ) ) {
			Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_TAB, recepcionContainer );
			recepcionContainer.llenarTabla();
		} else if( Constants.GARANTIAS_TAB_POSITION.equals( index ) ) {
			Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_TAB, garantiaContainer );
			garantiaContainer.cargarDatosTabla();
		}
	}

	@Override
	protected MenuManager createMenuManager() {
		return new MenuManager( "menu" );
	}

	@Override
	protected StatusLineManager createStatusLineManager() {
		return new StatusLineManager();
	}

	@Override
	protected void configureShell( final Shell newShell ) {
		super.configureShell( newShell );
		newShell.setImage( ControlTrabajosWindowElements.applicationIcon );
		newShell.setText( ApplicationPropertyHelper.getProperty( "sistema.name.label.dos" ) );
		newShell.setBounds( 1, 1, 800, 580 );
		newShell.setMinimumSize( 800, 580 );
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 800, 560 );
	}

}

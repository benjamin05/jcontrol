package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.ordenservicio.OrdenServicioService;
import mx.com.lux.control.trabajos.data.vo.Cliente;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.RecepcionPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.TableListener;
import mx.com.lux.control.trabajos.view.paging.PagingListener;
import mx.com.lux.control.trabajos.view.paging.ordenservicio.ClienteBusquedaPagingService;
import mx.com.lux.control.trabajos.view.render.ClienteBusquedaRender;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;

public class ClienteBusquedaDialog extends Dialog {

	private final static OrdenServicioService ordenServicioService;

	private Shell shell;
	private Text txtApPaterno;
	private Text txtApMaterno;
	private Table table;
	private Button btnAgregar;
	private PagingListener<Cliente> lastPagingListener;
	private Text txtNombre;

	private Integer[] menuOptionDesactive = {
			Constants.MENU_INDEX_DATOS_RECETA,
			Constants.MENU_INDEX_CONSULTA_TRABAJO,
			Constants.MENU_INDEX_INFO_LAB,
			Constants.MENU_INDEX_REPROGRAMAR,
			Constants.MENU_INDEX_NO_CONTACTAR,
			Constants.MENU_INDEX_RETENER,
			Constants.MENU_INDEX_DESRETENER,
			Constants.MENU_INDEX_ENVIAR,
			Constants.MENU_INDEX_NOENVIAR,
			Constants.MENU_INDEX_EFAX,
			Constants.MENU_INDEX_AUTORIZACION,
			Constants.MENU_INDEX_SOBRE,
			Constants.MENU_INDEX_DATOS_FACTURA,
			Constants.MENU_INDEX_DESVINCULAR,
			Constants.MENU_INDEX_CREAR_GRUPO,
			Constants.MENU_INDEX_AGREGAR_RX,
			Constants.MENU_INDEX_AGREGAR_GRUPO,
			Constants.MENU_INDEX_NUEVA_ORDEN,
			Constants.MENU_INDEX_ENTREGAR,
			Constants.MENU_INDEX_DESENTREGAR,
			Constants.MENU_INDEX_IMPRIMIR,
			Constants.MENU_INDEX_BODEGA,
			Constants.MENU_INDEX_GARANTIA_NUEVA,
			Constants.MENU_INDEX_GARANTIA_ENTREGAR,
			Constants.MENU_INDEX_GARANTIA_DESENTREGAR,
			Constants.MENU_INDEX_GARANTIA_IMPRIMIR,
			Constants.MENU_INDEX_GARANTIA_BODEGA
	};

	static {
		ordenServicioService = ServiceLocator.getBean( OrdenServicioService.class );
	}

	public ClienteBusquedaDialog( Shell parentShell ) {
		super( parentShell );
		shell = parentShell;
	}

	/**
	 * Create contents of the dialog.
	 *
	 * @param parent
	 */
	@Override
	public Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group grpBusqueda = new Group( container, SWT.NONE );
		grpBusqueda.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.busqueda" ) );
		grpBusqueda.setBounds( 10, 10, 592, 108 );
		grpBusqueda.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblApPaterno = new Label( grpBusqueda, SWT.NONE );
		lblApPaterno.setBounds( 10, 26, 100, 15 );
		lblApPaterno.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.apellido.paterno" ) );
		lblApPaterno.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtApPaterno = new Text( grpBusqueda, SWT.BORDER );

		txtApPaterno.addKeyListener( new KeyAdapter() {
			@Override
			public void keyReleased( KeyEvent e ) {
				if ( e.character == SWT.CR )
					llenarTabla();
			}
		} );

		txtApPaterno.setBounds( 116, 23, 300, 21 );

		Label lblApMaterno = new Label( grpBusqueda, SWT.NONE );
		lblApMaterno.setBounds( 10, 53, 100, 15 );
		lblApMaterno.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.apellido.materno" ) );
		lblApMaterno.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtApMaterno = new Text( grpBusqueda, SWT.BORDER );
		txtApMaterno.setBounds( 116, 50, 300, 21 );

		txtApMaterno.addKeyListener( new KeyAdapter() {
			@Override
			public void keyReleased( KeyEvent e ) {
				if ( e.character == SWT.CR )
					llenarTabla();
			}
		} );

		Label lblNombre = new Label( grpBusqueda, SWT.NONE );
		lblNombre.setBounds( 10, 83, 57, 15 );
		lblNombre.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.nombre" ) );
		lblNombre.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtNombre = new Text( grpBusqueda, SWT.BORDER );
		txtNombre.setBounds( 116, 77, 300, 21 );

		txtNombre.addKeyListener( new KeyAdapter() {
			@Override
			public void keyReleased( KeyEvent e ) {
				if ( e.character == SWT.CR )
					llenarTabla();
			}
		} );

		VerifyListener upperTxt = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		txtApPaterno.addVerifyListener( upperTxt );
		txtApMaterno.addVerifyListener( upperTxt );
		txtNombre.addVerifyListener( upperTxt );

		Button btnBuscar = new Button( grpBusqueda, SWT.NONE );

		btnBuscar.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				llenarTabla();
			}
		} );

		btnBuscar.setBounds( 446, 26, 114, 33 );
		btnBuscar.setImage( ControlTrabajosWindowElements.buscarIcon );
		btnBuscar.setText( RecepcionPropertyHelper.getProperty( "recepcion.label.buscar" ) );

		btnAgregar = new Button( container, SWT.NONE );
		btnAgregar.setBounds( 10, 124, 114, 33 );
		btnAgregar.setEnabled( false );
		btnAgregar.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.agregar" ) );

		btnAgregar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				close();
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE );
				Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE, null );
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_EDIT_CLIENTE );
				Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_EDIT_CLIENTE, "true" );
				DatosClienteDialog datosClienteDialog = new DatosClienteDialog( shell );
				datosClienteDialog.open();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );

		table = new Table( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL );

		table.addMouseListener( new MouseAdapter() {
			public void mouseDoubleClick( MouseEvent arg0 ) {
				close();
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_EDIT_CLIENTE );
				Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_EDIT_CLIENTE, "true" );
				DatosClienteDialog datosClienteDialog = new DatosClienteDialog( shell );
				datosClienteDialog.open();
			}
		} );

		table.addSelectionListener( new SelectionAdapter() {

			public void widgetSelected( SelectionEvent arg0 ) {
				int index = table.getSelectionIndex();
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE );
				if ( index >= 0 ) {
					Cliente cliente = (Cliente) lastPagingListener.getItem( index );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE, String.valueOf( cliente.getIdCliente() ) );
					table.getMenu().getItem( 0 ).setEnabled( true );
				}
			}
		} );

		table.setBounds( 10, 163, 592, 179 );
		table.setHeaderVisible( true );
		table.setLinesVisible( true );

		// se agregan los menus
		MenuInfoTrabajos menuInfoTrabajosPorEnviar = new MenuInfoTrabajos( shell, table, ApplicationUtils.opcionesMenu( menuOptionDesactive ) );
		table.setMenu( menuInfoTrabajosPorEnviar.getMenuInfoTrabajos() );
		// se pintan las celdas
		table.addListener( SWT.PaintItem, new TableListener() );

		TableColumn tblclmCliente = new TableColumn( table, SWT.NONE );
		tblclmCliente.setWidth( 76 );
		tblclmCliente.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente" ) );

		TableColumn tblclmNombre = new TableColumn( table, SWT.NONE );
		tblclmNombre.setWidth( 260 );
		tblclmNombre.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.nombre" ) );

		TableColumn tblclmDir = new TableColumn( table, SWT.NONE );
		tblclmDir.setWidth( 100 );
		tblclmDir.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.direccion" ) );

		return container;
	}

	public void llenarTabla() {
		table.removeAll();
		if ( lastPagingListener != null )
			table.removeListener( SWT.SetData, lastPagingListener );
		lastPagingListener = new PagingListener<Cliente>( 100, new ClienteBusquedaPagingService( ordenServicioService, txtApPaterno.getText(), txtApMaterno.getText(), txtNombre.getText() ), new ClienteBusquedaRender(), "error servicio ClienteBusqueda" );
		table.addListener( SWT.SetData, lastPagingListener );
		table.setItemCount( lastPagingListener.size() );
		// habilita boton agregar al hacer una busqueda
		btnAgregar.setEnabled( true );
		table.redraw();
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 614, 430 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.busqueda.cliente" ) );
	}

	/**
	 * Create contents of the button bar.
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Button buttonCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		buttonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		buttonCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
	}
}

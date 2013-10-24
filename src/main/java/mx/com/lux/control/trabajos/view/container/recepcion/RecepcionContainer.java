package mx.com.lux.control.trabajos.view.container.recepcion;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.DateUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.RecepcionPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

import java.util.List;

public class RecepcionContainer {

	private final static TrabajoService trabajoService;

	private ApplicationWindow applicationWindow;
	private Shell shell;
	private Text txtRx;
	private Text txtViaje;
	private Table table;
	private Integer[] menuOptionDesactive = { Constants.MENU_INDEX_REPROGRAMAR, Constants.MENU_INDEX_NO_CONTACTAR, Constants.MENU_INDEX_AUTORIZACION, Constants.MENU_INDEX_AGREGAR_RX, Constants.MENU_INDEX_ENVIAR, Constants.MENU_INDEX_NOENVIAR, Constants.MENU_INDEX_AGREGAR_GRUPO, Constants.MENU_INDEX_EFAX, Constants.MENU_INDEX_SOBRE, Constants.MENU_INDEX_DESRETENER, Constants.MENU_INDEX_ENTREGAR, Constants.MENU_INDEX_DESENTREGAR, Constants.MENU_INDEX_IMPRIMIR, Constants.MENU_INDEX_BODEGA, Constants.MENU_INDEX_NUEVA_ORDEN,
			Constants.MENU_INDEX_NUEVA_ORDEN,
			Constants.MENU_INDEX_GARANTIA_NUEVA,
			Constants.MENU_INDEX_GARANTIA_ENTREGAR,
			Constants.MENU_INDEX_GARANTIA_DESENTREGAR,
			Constants.MENU_INDEX_GARANTIA_IMPRIMIR,
			Constants.MENU_INDEX_GARANTIA_BODEGA
	};

	static {
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
	}

	public RecepcionContainer( ApplicationWindow applicationWindow ) {
		this.applicationWindow = applicationWindow;
		this.shell = applicationWindow.getShell();
	}

	/**
	 * Create contents of the dialog.
	 *
	 * @param parent
	 * @wbp.parser.entryPoint
	 */
	public Control createContents( TabFolder tabFolder ) {
		Composite container = new Composite( tabFolder, SWT.NONE );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group grpBusqueda = new Group( container, SWT.NONE );
		grpBusqueda.setText( "Busqueda" );
		grpBusqueda.setBounds( 10, 10, 256, 156 );
		grpBusqueda.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblRx = new Label( grpBusqueda, SWT.NONE );
		lblRx.setBounds( 10, 26, 55, 15 );
		lblRx.setText( RecepcionPropertyHelper.getProperty( "recepcion.label.rx" ) );
		lblRx.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtRx = new Text( grpBusqueda, SWT.BORDER );
		txtRx.setBounds( 95, 23, 147, 25 );

		txtRx.addKeyListener( new KeyAdapter() {
			@Override
			public void keyReleased( KeyEvent e ) {
				if ( e.character == SWT.CR )
					buscar();
			}
		} );

		txtViaje = new Text( grpBusqueda, SWT.BORDER );
		txtViaje.setBounds( 95, 50, 147, 25 );

		VerifyListener upperTxt = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		txtRx.addVerifyListener( upperTxt );
		txtViaje.addVerifyListener( upperTxt );

		Label lblViaje = new Label( grpBusqueda, SWT.NONE );
		lblViaje.setBounds( 10, 53, 55, 15 );
		lblViaje.setText( RecepcionPropertyHelper.getProperty( "recepcion.label.viaje" ) );
		lblViaje.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Button btnBuscar = new Button( grpBusqueda, SWT.NONE );

		btnBuscar.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				buscar();
			}
		} );

		btnBuscar.setBounds( 10, 113, 114, 33 );
		btnBuscar.setImage( ControlTrabajosWindowElements.buscarIcon );
		btnBuscar.setText( RecepcionPropertyHelper.getProperty( "recepcion.label.buscar" ) );

		table = new Table( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL );

		table.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				TableItem item = table.getSelection()[0];
				Object[] obj = (Object[]) item.getData();
				Jb jb = (Jb) obj[0];
				if ( jb != null ) {
					try {
						Integer recetaId = trabajoService.findIdRecetaByFactura( jb.getRx() );
						// Si existe la receta, activa el boton de Datos Receta
						// y pone en sesi�n el Id de la receta
						if ( recetaId != null && recetaId > 0 ) {
							Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RECETA, recetaId );
							table.getMenu().getItem( 0 ).setEnabled( true );
						}
						Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE, jb.getIdCliente() );
						Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX, jb.getRx() );
						// Activa Consultar Trabajo
						table.getMenu().getItem( 1 ).setEnabled( true );
						// Activa Info Laboratorio
						table.getMenu().getItem( 2 ).setEnabled( true );
						// Activa Datos Cliente
						table.getMenu().getItem( 3 ).setEnabled( true );
						// Activa Retener
						if ( trabajoService.validateRetenerTrabajo( jb.estado() ) )
							table.getMenu().getItem( 4 ).setEnabled( true );
						else
							table.getMenu().getItem( 4 ).setEnabled( false );
						// Activa Datos Factura
						table.getMenu().getItem( 5 ).setEnabled( true );
						// Activa agruopar
						if ( trabajoService.validarAgrupar( jb ) )
							table.getMenu().getItem( 6 ).setEnabled( true );
						else
							table.getMenu().getItem( 6 ).setEnabled( false );
						// Activa desagrupar
						if ( trabajoService.validarDesagrupar( jb ) )
							table.getMenu().getItem( 7 ).setEnabled( true );
						else
							table.getMenu().getItem( 7 ).setEnabled( false );
					} catch ( ApplicationException e1 ) {
						e1.printStackTrace();
					}
				}
			}
		} );

		table.setBounds( 10, 188, 741, 218 );
		table.setHeaderVisible( true );
		table.setLinesVisible( true );

		MenuInfoTrabajos menuInfoTrabajosPorEnviar = new MenuInfoTrabajos( applicationWindow.getShell(), table, ApplicationUtils.opcionesMenu( menuOptionDesactive ) );
		table.setMenu( menuInfoTrabajosPorEnviar.getMenuInfoTrabajos() );

		TableColumn tblclmnHora = new TableColumn( table, SWT.NONE );
		tblclmnHora.setWidth( 100 );
		tblclmnHora.setText( RecepcionPropertyHelper.getProperty( "recepcion.table.hora.header" ) );

		TableColumn tblclmnRx = new TableColumn( table, SWT.NONE );
		tblclmnRx.setWidth( 80 );
		tblclmnRx.setText( RecepcionPropertyHelper.getProperty( "recepcion.table.rx.header" ) );

		TableColumn tblclmnEstado = new TableColumn( table, SWT.NONE );
		tblclmnEstado.setWidth( 100 );
		tblclmnEstado.setText( RecepcionPropertyHelper.getProperty( "recepcion.table.estado.header" ) );

		TableColumn tblclmnCliente = new TableColumn( table, SWT.NONE );
		tblclmnCliente.setWidth( 250 );
		tblclmnCliente.setText( RecepcionPropertyHelper.getProperty( "recepcion.table.cliente.header" ) );

		TableColumn tblclmnMaterial = new TableColumn( table, SWT.NONE );
		tblclmnMaterial.setWidth( 200 );
		tblclmnMaterial.setText( RecepcionPropertyHelper.getProperty( "recepcion.table.material.header" ) );

		llenarTabla();
		return container;
	}

	public void llenarTabla() {
		table.removeAll();
		try {
			List<Object[]> jbs = trabajoService.findAllJbByEdosAndFecha();
			for ( Object[] obj : jbs ) {
				Jb jb = (Jb) obj[0];
				JbTrack track = (JbTrack) obj[1];
				TableItem item = new TableItem( table, SWT.NONE );
				item.setData( obj );
				item.setText( 0, DateUtils.formatDate( track.getFecha(), "HH:mm" ) );
				item.setText( 1, jb.getRx() );
				item.setText( 2, track.getEstado() );
				item.setText( 3, jb.getCliente() );
				item.setText( 4, jb.getMaterial() );
			}
		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
		table.redraw();
	}

	private void buscar() {
		try {
			Jb jb = trabajoService.findById( txtRx.getText() );
			trabajoService.validarRecepcion( jb );
			Session.setAttribute( Constants.ITEM_SELECTED_JB, jb );
			Session.setAttribute( Constants.ITEM_SELECTED_ID_VIAJE, Integer.valueOf( txtViaje.getText() ) );

			if ( trabajoService.isNoEnviado( jb ) ) {
				String[] etiquetasBotones = new String[]{ MessagesPropertyHelper.getProperty( "generic.si" ), MessagesPropertyHelper.getProperty( "generic.no" ) };
				MessageDialog confirm = new MessageDialog( shell, MessagesPropertyHelper.getProperty( "recepcion.enviar.title" ), null, MessagesPropertyHelper.getProperty( "recepcion.enviar.message" ), MessageDialog.INFORMATION, etiquetasBotones, 0 );
				if ( confirm.open() != 0 ) {
					return;
				}
			}

			Jb grupo = trabajoService.isGrupo( jb );
			if ( grupo != null ) {
				MessageBox mb = new MessageBox( new Shell( Display.getCurrent() ), SWT.OK );
				mb.setText( MessagesPropertyHelper.getProperty( "recepcion.grupo.title" ) );
				mb.setMessage( MessagesPropertyHelper.getProperty( "recepcion.grupo.message" ) + " " + grupo.getCliente() );
				mb.open();
			}

			new RecepcionDialog( shell ).open();
		} catch ( ApplicationException e1 ) {
			e1.printStackTrace();
		} catch ( NumberFormatException nfe ) {
			MessageDialog.openError( shell, "Error", MessagesPropertyHelper.getProperty( "recepcion.viaje.invalido" ) );
		} catch ( IllegalStateException iae ) {
			MessageDialog.openError( shell, "Error", iae.getMessage() );
		}
	}
}

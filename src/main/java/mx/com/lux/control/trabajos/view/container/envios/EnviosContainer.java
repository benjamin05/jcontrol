package mx.com.lux.control.trabajos.view.container.envios;

import mx.com.lux.control.trabajos.bussiness.service.envio.EnvioService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbDev;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.PrinterException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.DatosRecetaDialog;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.container.trabajos.ReimpresionPackingDialog;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.TableListener;
import mx.com.lux.control.trabajos.view.paging.PagingListener;
import mx.com.lux.control.trabajos.view.paging.trabajos.NoEnviarPagingService;
import mx.com.lux.control.trabajos.view.paging.trabajos.PaginadorDevolucionesSp;
import mx.com.lux.control.trabajos.view.paging.trabajos.PorEnviarPagingService;
import mx.com.lux.control.trabajos.view.render.DevolucionSpRender;
import mx.com.lux.control.trabajos.view.render.NoEnviarRender;
import mx.com.lux.control.trabajos.view.render.PorEnviarRender;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

public class EnviosContainer {

	private final Logger log = Logger.getLogger( EnviosContainer.class );

	private TrabajoService trabajoService;
	private EnvioService envioService;

	private PagingListener<Jb> lastPagingListenerPorEnviar;
	private PagingListener<Jb> lastPagingListenerNoEnviar;
	private PagingListener<JbDev> pagingListenerDevolucionesSp;
	private PaginadorDevolucionesSp paginadorDevolucionesSp;
	private Table tablaPorEnviar;
	private Table tablaNoEnviar;
	private Table tablaDevolucionesSp;
	private Text textoViaje;
	private Text textoFolio;
	private final Shell shell;

	private final Integer[] menuOptionDesactiveEnviar = { Constants.MENU_INDEX_REPROGRAMAR, Constants.MENU_INDEX_NO_CONTACTAR, Constants.MENU_INDEX_INFO_LAB, Constants.MENU_INDEX_AUTORIZACION, Constants.MENU_INDEX_CREAR_GRUPO, Constants.MENU_INDEX_AGREGAR_GRUPO, Constants.MENU_INDEX_AGREGAR_RX, Constants.MENU_INDEX_DESVINCULAR, Constants.MENU_INDEX_ENTREGAR, Constants.MENU_INDEX_DESENTREGAR, Constants.MENU_INDEX_IMPRIMIR, Constants.MENU_INDEX_BODEGA, Constants.MENU_INDEX_NUEVA_ORDEN,
			Constants.MENU_INDEX_NUEVA_ORDEN,
			Constants.MENU_INDEX_GARANTIA_NUEVA,
			Constants.MENU_INDEX_GARANTIA_ENTREGAR,
			Constants.MENU_INDEX_GARANTIA_DESENTREGAR,
			Constants.MENU_INDEX_GARANTIA_IMPRIMIR,
			Constants.MENU_INDEX_GARANTIA_BODEGA
	};
	private final Integer[] menuOptionDesactiveNoEnviar = { Constants.MENU_INDEX_REPROGRAMAR, Constants.MENU_INDEX_NO_CONTACTAR, Constants.MENU_INDEX_INFO_LAB, Constants.MENU_INDEX_EFAX, Constants.MENU_INDEX_AUTORIZACION, Constants.MENU_INDEX_CREAR_GRUPO, Constants.MENU_INDEX_AGREGAR_GRUPO, Constants.MENU_INDEX_AGREGAR_RX, Constants.MENU_INDEX_DESVINCULAR, Constants.MENU_INDEX_ENTREGAR, Constants.MENU_INDEX_DESENTREGAR, Constants.MENU_INDEX_IMPRIMIR, Constants.MENU_INDEX_BODEGA, Constants.MENU_INDEX_NUEVA_ORDEN,
			Constants.MENU_INDEX_NUEVA_ORDEN,
			Constants.MENU_INDEX_GARANTIA_NUEVA,
			Constants.MENU_INDEX_GARANTIA_ENTREGAR,
			Constants.MENU_INDEX_GARANTIA_DESENTREGAR,
			Constants.MENU_INDEX_GARANTIA_IMPRIMIR,
			Constants.MENU_INDEX_GARANTIA_BODEGA
	};

	public EnviosContainer( ApplicationWindow applicationWindow ) {
		shell = applicationWindow.getShell();
		trabajoService = ServiceLocator.getBean( TrabajoService.class );
		envioService = ServiceLocator.getBean( EnvioService.class );
		paginadorDevolucionesSp = ServiceLocator.getBean( PaginadorDevolucionesSp.class );
	}

	public Control createContents( TabFolder tabFolder ) {

		Composite container = new Composite( tabFolder, SWT.NONE );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		crearCamposDatos( container );
		crearTablaDevolucionesSp( container );
		crearTablaNoEnviar( container );
		crearTablaPorEnviar( container );
		crearBotonesAcciones( container );

		return container;
	}

	private void crearTablaNoEnviar( Composite container ) {
		tablaNoEnviar = new Table( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL );
		tablaNoEnviar.setBounds( 270, 81, 217, 260 );
		tablaNoEnviar.setHeaderVisible( true );
		tablaNoEnviar.setLinesVisible( true );
		tablaNoEnviar.addListener( SWT.PaintItem, new TableListener() );

		TableColumn clmNoEnv = new TableColumn( tablaNoEnviar, SWT.CENTER );
		clmNoEnv.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.no.enviar" ) );
		clmNoEnv.setWidth( clmNoEnv.getParent().getBounds().width - 5 );

		MenuInfoTrabajos menuInfoTrabajosNoEnviar = new MenuInfoTrabajos( shell, tablaNoEnviar, ApplicationUtils.opcionesMenu( menuOptionDesactiveNoEnviar ) );
		tablaNoEnviar.setMenu( menuInfoTrabajosNoEnviar.getMenuInfoTrabajos() );

		// Eventos de la tabla No Enviar
		tablaNoEnviar.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				int index = tablaNoEnviar.getSelectionIndex();
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE );
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RECETA );

				if ( index >= 0 ) {
					Jb jb = lastPagingListenerNoEnviar.getItem( index );
					Integer recetaId = null;
					try {
						// Obtiene la receta del Rx
						recetaId = trabajoService.findIdRecetaByFactura( jb.getRx() );
						// Si existe la receta, activa el boton de Datos Receta y pone en sesion el Id de la receta
						if ( recetaId != null && recetaId > 0 ) {
							Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RECETA, recetaId );
							tablaNoEnviar.getMenu().getItem( 0 ).setEnabled( true );
						}
					} catch ( ApplicationException e1 ) {
						e1.printStackTrace();
					}
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE, jb.getIdCliente() );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX, jb.getRx() );

					// Activa Consultar Trabajo
					tablaNoEnviar.getMenu().getItem( 1 ).setEnabled( true );
					// Activa Datos Cliente
					tablaNoEnviar.getMenu().getItem( 2 ).setEnabled( true );
					// Activa Enviar
					tablaNoEnviar.getMenu().getItem( 5 ).setEnabled( true );
					// Activa Retener
					if ( trabajoService.validateRetenerTrabajo( jb.estado() ) ) {
						tablaNoEnviar.getMenu().getItem( 3 ).setEnabled( true );
					} else {
						tablaNoEnviar.getMenu().getItem( 3 ).setEnabled( false );
					}
					// Activa Desretener
					tablaNoEnviar.getMenu().getItem( 4 ).setEnabled( trabajoService.validateDesretenerTrabajo( jb ) );
					tablaNoEnviar.getMenu().getItem( 4 ).setEnabled( false );
					// Activa Datos Factura
					tablaNoEnviar.getMenu().getItem( 7 ).setEnabled( true );
				} else {
					tablaNoEnviar.getMenu().getItem( 0 ).setEnabled( false );
					tablaNoEnviar.getMenu().getItem( 1 ).setEnabled( false );
					tablaNoEnviar.getMenu().getItem( 2 ).setEnabled( false );
					tablaNoEnviar.getMenu().getItem( 3 ).setEnabled( false );
					tablaNoEnviar.getMenu().getItem( 4 ).setEnabled( false );
					tablaNoEnviar.getMenu().getItem( 5 ).setEnabled( false );
					tablaNoEnviar.getMenu().getItem( 6 ).setEnabled( false );
					tablaNoEnviar.getMenu().getItem( 7 ).setEnabled( false );
				}
			}
		} );
	}

	private void crearTablaPorEnviar( Composite container ) {
		tablaPorEnviar = new Table( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL );
		tablaPorEnviar.setLinesVisible( true );
		tablaPorEnviar.setHeaderVisible( true );
		tablaPorEnviar.setBounds( 520, 81, 217, 260 );
		tablaPorEnviar.addListener( SWT.PaintItem, new TableListener() );

		TableColumn clmPorEnv = new TableColumn( tablaPorEnviar, SWT.CENTER );
		clmPorEnv.setWidth( clmPorEnv.getParent().getBounds().width - 22 );
		clmPorEnv.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.por.enviar" ) );

		MenuInfoTrabajos menuInfoTrabajosPorEnviar = new MenuInfoTrabajos( shell, tablaPorEnviar, ApplicationUtils.opcionesMenu( menuOptionDesactiveEnviar ) );
		tablaPorEnviar.setMenu( menuInfoTrabajosPorEnviar.getMenuInfoTrabajos() );


		// Eventos de la tabla Por Enviar
		tablaPorEnviar.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				int index = tablaPorEnviar.getSelectionIndex();
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE );
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RECETA );
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_CLIENTE_NOMBRE );

				if ( index >= 0 ) {
					Jb jb = lastPagingListenerPorEnviar.getItem( index );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE, jb.getIdCliente() );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX, jb.getRx() );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_CLIENTE_NOMBRE, jb.getCliente() );

					Integer recetaId = null;
					try {
						// Obtiene la receta del Rx
						recetaId = trabajoService.findIdRecetaByFactura( jb.getRx() );
						// Si existe la receta, activa el boton de Datos Receta y pone en sesion el Id de la receta
						if ( recetaId != null && Integer.valueOf( 0 ).compareTo( recetaId ) < 0 ) {
							Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RECETA, recetaId );
							tablaPorEnviar.getMenu().getItem( 0 ).setEnabled( true );
						}
					} catch ( ApplicationException e1 ) {
						e1.printStackTrace();
					}

					// Activa Consultar Trabajo
					tablaPorEnviar.getMenu().getItem( 1 ).setEnabled( true );
					// Activa Datos Cliente
					tablaPorEnviar.getMenu().getItem( 2 ).setEnabled( true );
					// Activa No Enviar
					tablaPorEnviar.getMenu().getItem( 6 ).setEnabled( true );
					// Activa Retener
					if ( trabajoService.validateRetenerTrabajo( jb.estado() ) ) {
						tablaPorEnviar.getMenu().getItem( 3 ).setEnabled( true );
					} else {
						tablaPorEnviar.getMenu().getItem( 3 ).setEnabled( false );
					}
					// Activa Efax
					tablaPorEnviar.getMenu().getItem( 7 ).setEnabled( trabajoService.validateEnviarEFax( jb ) );
					// Activa Desretener
					tablaNoEnviar.getMenu().getItem( 4 ).setEnabled( trabajoService.validateDesretenerTrabajo( jb ) );
					// Activa Sobre
					tablaPorEnviar.getMenu().getItem( 8 ).setEnabled( true );
					// Activa Datos Factura
					tablaPorEnviar.getMenu().getItem( 9 ).setEnabled( true );
					tablaPorEnviar.getMenu().getItem( 10 ).setEnabled( trabajoService.puedeEnviarExterno( jb ) );
					tablaPorEnviar.getMenu().getItem( 11 ).setEnabled( trabajoService.puedeEliminarExterno( jb ) );
				} else {
					tablaPorEnviar.getMenu().getItem( 0 ).setEnabled( false );
					tablaPorEnviar.getMenu().getItem( 1 ).setEnabled( false );
					tablaPorEnviar.getMenu().getItem( 2 ).setEnabled( false );
					tablaPorEnviar.getMenu().getItem( 3 ).setEnabled( false );
					tablaPorEnviar.getMenu().getItem( 4 ).setEnabled( false );
					tablaPorEnviar.getMenu().getItem( 5 ).setEnabled( false );
					tablaPorEnviar.getMenu().getItem( 6 ).setEnabled( false );
					tablaPorEnviar.getMenu().getItem( 7 ).setEnabled( false );
					tablaPorEnviar.getMenu().getItem( 8 ).setEnabled( false );
					tablaPorEnviar.getMenu().getItem( 9 ).setEnabled( false );
				}
			}
		} );
	}

	private void crearTablaDevolucionesSp( Composite contenedor ) {
		tablaDevolucionesSp = new Table( contenedor, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL );
		tablaDevolucionesSp.setLinesVisible( true );
		tablaDevolucionesSp.setHeaderVisible( true );
		tablaDevolucionesSp.setBounds( 20, 81, 217, 260 );
		tablaDevolucionesSp.addListener( SWT.PaintItem, new TableListener() );

		TableColumn columnaDevolucionesSp = new TableColumn( tablaDevolucionesSp, SWT.CENTER );
		columnaDevolucionesSp.setWidth( columnaDevolucionesSp.getParent().getBounds().width - 22 );
		columnaDevolucionesSp.setText( "Devoluciones SP" );

		MenuDevolucionesSp menuDevolucionesSp = new MenuDevolucionesSp( shell, tablaDevolucionesSp );
		tablaDevolucionesSp.setMenu( menuDevolucionesSp.getMenu() );

		tablaDevolucionesSp.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				int index = tablaDevolucionesSp.getSelectionIndex();
				if ( index > -1 ) {
					JbDev jbDev = pagingListenerDevolucionesSp.getItem( index );
					Session.setAttribute( MenuDevolucionesSp.ITEM_SELECTED_JB_DEV, jbDev != null ? jbDev.getId() : null );
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
				super.widgetDefaultSelected( event );
			}
		} );
	}

	private void crearCamposDatos( Composite container ) {

		VerifyListener upperTxt = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		DateTime dtFecha = new DateTime( container, SWT.BORDER );
		dtFecha.setBounds( 114, 22, 110, 29 );
		dtFecha.setEnabled( false );
		dtFecha.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		Label lblFecha = new Label( container, SWT.NONE );
		lblFecha.setBounds( 36, 29, 57, 15 );
		lblFecha.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.fecha" ) );
		lblFecha.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblViaje = new Label( container, SWT.NONE );
		lblViaje.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.viaje" ) );
		lblViaje.setBounds( 341, 29, 57, 15 );
		lblViaje.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		textoViaje = new Text( container, SWT.BORDER );
		textoViaje.setBounds( 404, 22, 75, 25 );
		textoViaje.setEnabled( false );

		textoFolio = new Text( container, SWT.BORDER );
		textoFolio.setBounds( 672, 22, 75, 25 );

		Label lblFolio = new Label( container, SWT.NONE );
		lblFolio.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.folio" ) );
		lblFolio.setBounds( 609, 29, 57, 15 );
		lblFolio.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		textoViaje.addVerifyListener( upperTxt );
		textoFolio.addVerifyListener( upperTxt );
	}

	private void crearBotonesAcciones( Composite container ) {

		Button btnCerrarViaje = new Button( container, SWT.NONE );
		btnCerrarViaje.setBounds( 81, 376, 79, 27 );
		btnCerrarViaje.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cerrar.viaje" ) );

		btnCerrarViaje.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				Boolean viajeVacio = Boolean.FALSE;
				if ( textoFolio.getText() != null && !textoFolio.getText().isEmpty() ) {
					if ( MessageDialog.openQuestion( shell, MessagesPropertyHelper.getProperty( "cerrar.viaje.title" ), MessagesPropertyHelper.getProperty( "cerrar.viaje.message" ) ) ) {
						try {
							envioService.imprimirPackingCerrado( textoFolio.getText() );
							envioService.imprimirPackingCerrado( textoFolio.getText() );
						} catch ( PrinterException ex ) {
							MessageDialog.openError( shell, "Error", "La impresora no est\u00E1 lista." );
							ex.printStackTrace();
						} catch ( ApplicationException ex ) {
							MessageDialog.openError( shell, "Error", "Ocurri\u00F3 un error." );
							ex.printStackTrace();
						}
						viajeVacio = envioService.saveValuesCerrarViaje( textoFolio.getText() );
                        if ( viajeVacio ) {
							MessageDialog.openWarning( shell, MessagesPropertyHelper.getProperty( "cerrar.viaje.vacio.title" ), MessagesPropertyHelper.getProperty( "cerrar.viaje.vacio.message" ) );
						}
						textoFolio.setText( "" );
						cargarDatos();
					}
				} else {
					MessageDialog.openError( shell, MessagesPropertyHelper.getProperty( "cerrar.viaje.error.title" ), MessagesPropertyHelper.getProperty( "cerrar.viaje.error.message" ) );
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );

        Button btnReImprimir = new Button( container, SWT.NONE );
        btnReImprimir.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.reimpresion" ) );
        btnReImprimir.setBounds( 240, 376, 99, 27 );
        btnReImprimir.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent arg0 ) {
                ReimpresionPackingDialog reimpresionPackingDialog = new ReimpresionPackingDialog( shell );
                reimpresionPackingDialog.open();
            }
        });

		Button btnPrevio = new Button( container, SWT.NONE );
		btnPrevio.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.previo" ) );
		btnPrevio.setBounds( 409, 376, 79, 27 );

		btnPrevio.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				try {
					envioService.imprimirPackingPrevio();
				} catch ( PrinterException e1 ) {
					MessageDialog.openError( shell, "Error", "La impresora no est\u00E1 lista" );
					log.error( "La impresora no está lista", e1 );
				} catch ( ApplicationException e2 ) {
					MessageDialog.openError( shell, "Error", "Error al imprimir el Packing previo" );
					log.error( "Error al imprimir el Packing previo", e2 );
				}
			}
		} );

		Button btnActualizar = new Button( container, SWT.NONE );
		btnActualizar.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.actualizar" ) );
		btnActualizar.setBounds( 567, 376, 79, 27 );

		btnActualizar.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				ApplicationUtils.recargarDatosPestanyaVisible();
			}
		} );
	}

	public void cargarDatos() {
		cargarDatosViaje();
		cargarDatosPorEnviar();
		cargarDatosNoEnviar();
		cargarDatosDevolucionesSp();
	}

	public void cargarDatosViaje() {
		try {
			textoViaje.setText( Integer.toString( envioService.findNextIdViaje() ) );
		} catch ( ApplicationException ae ) {
			ae.printStackTrace();
		}
	}

	public void cargarDatosPorEnviar() {
		tablaPorEnviar.removeAll();
		if ( lastPagingListenerPorEnviar != null ) {
			tablaPorEnviar.removeListener( SWT.SetData, lastPagingListenerPorEnviar );
		}
		lastPagingListenerPorEnviar = new PagingListener<Jb>( 10, new PorEnviarPagingService( trabajoService ), new PorEnviarRender(), "Error del servicio de trabajos" );
		tablaPorEnviar.addListener( SWT.SetData, lastPagingListenerPorEnviar );
		tablaPorEnviar.setItemCount( lastPagingListenerPorEnviar.size() );
		tablaPorEnviar.redraw();
	}

	public void cargarDatosNoEnviar() {
		tablaNoEnviar.removeAll();
		if ( lastPagingListenerNoEnviar != null ) {
			tablaNoEnviar.removeListener( SWT.SetData, lastPagingListenerNoEnviar );
		}
		lastPagingListenerNoEnviar = new PagingListener<Jb>( 10, new NoEnviarPagingService( trabajoService ), new NoEnviarRender(), "Error del servicio de trabajos" );
		tablaNoEnviar.addListener( SWT.SetData, lastPagingListenerNoEnviar );
		tablaNoEnviar.setItemCount( lastPagingListenerNoEnviar.size() );
		tablaNoEnviar.redraw();
	}

	public void cargarDatosDevolucionesSp() {
		tablaDevolucionesSp.removeAll();
		if ( pagingListenerDevolucionesSp != null ) {
			tablaDevolucionesSp.removeListener( SWT.SetData, pagingListenerDevolucionesSp );
		}
		pagingListenerDevolucionesSp = new PagingListener<JbDev>( 10, paginadorDevolucionesSp, new DevolucionSpRender(), "Error del paginador de Devoluciones SP" );
		tablaDevolucionesSp.addListener( SWT.SetData, pagingListenerDevolucionesSp );
		tablaDevolucionesSp.setItemCount( pagingListenerDevolucionesSp.size() );
		tablaDevolucionesSp.redraw();
	}


}

package mx.com.lux.control.trabajos.view.container.contacto;

import mx.com.lux.control.trabajos.bussiness.service.contacto.ContactoViewService;
import mx.com.lux.control.trabajos.bussiness.service.sistema.login.LoginService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.ContactoView;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.NotaVenta;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.*;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.ContactoPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.TableListener;
import mx.com.lux.control.trabajos.view.paging.PagingListener;
import mx.com.lux.control.trabajos.view.paging.contacto.ContactoViewPagingService;
import mx.com.lux.control.trabajos.view.render.ContactoViewRender;
import mx.com.lux.control.trabajos.view.util.TableUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

public class ContactoContainer {

	private final ContactoViewService contactoViewService;
	private final LoginService loginService;
	private final TrabajoService trabajoService;

	private ApplicationWindow applicationWindow;
	private PagingListener<ContactoView> lastPagingListener;
	private Group grpFiltros;
	private Text txtPendientes;
	private Text txtNoRealizados;
	private Text txtAtendio;
	private Table table;
	private Integer[] menuOptionDesactive = {
			// Constants.MENU_INDEX_REPROGRAMAR,
			// Constants.MENU_INDEX_NO_CONTACTAR,
			Constants.MENU_INDEX_ENVIAR,
			Constants.MENU_INDEX_NOENVIAR,
			Constants.MENU_INDEX_EFAX,
			Constants.MENU_INDEX_SOBRE,
			Constants.MENU_INDEX_AGREGAR_GRUPO,
			Constants.MENU_INDEX_AGREGAR_RX,
			Constants.MENU_INDEX_ENTREGAR,
			Constants.MENU_INDEX_DESENTREGAR,
			Constants.MENU_INDEX_IMPRIMIR,
			Constants.MENU_INDEX_BODEGA,
			Constants.MENU_INDEX_NUEVA_ORDEN,
			Constants.MENU_INDEX_GARANTIA_NUEVA,
			Constants.MENU_INDEX_GARANTIA_ENTREGAR,
			Constants.MENU_INDEX_GARANTIA_DESENTREGAR,
			Constants.MENU_INDEX_GARANTIA_IMPRIMIR,
			Constants.MENU_INDEX_GARANTIA_BODEGA
	};

	public ContactoContainer( ApplicationWindow applicationWindow ) {
		super();
		this.applicationWindow = applicationWindow;
		contactoViewService = ServiceLocator.getBean( ContactoViewService.class );
		loginService = ServiceLocator.getBean( LoginService.class );
		trabajoService = ServiceLocator.getBean( TrabajoService.class );
	}

	public Control createContents( TabFolder tabFolder ) {
		Composite container = new Composite( tabFolder, SWT.NONE );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group grpResume = new Group( container, SWT.NONE );
		grpResume.setText( Constants.CADENA_VACIA );
		grpResume.setBounds( 25, 34, 520, 40 );
		grpResume.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblPendientes = new Label( grpResume, SWT.NONE );
		lblPendientes.setText( ContactoPropertyHelper.getProperty( "contacto.resume.pendientes" ) + TrabajosPropertyHelper.getProperty( "symbol.diagonal" ) + ContactoPropertyHelper.getProperty( "contacto.resume.retrasados" ) );
		lblPendientes.setBounds( 5, 13, 140, EnvironmentConstants.LABEL_NORMAL_HEIGHT );
		lblPendientes.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtPendientes = new Text( grpResume, SWT.BORDER | SWT.CENTER );
		txtPendientes.setBounds( 280, 10, 200, EnvironmentConstants.TEXTBOX_NUMBER_HEIGHT );
		txtPendientes.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtPendientes.setEditable( false );

		Label lblNoContestadas = new Label( grpResume, SWT.NONE );
		lblNoContestadas.setBounds( 210, 13, 90, EnvironmentConstants.LABEL_NORMAL_HEIGHT );
		lblNoContestadas.setText( ContactoPropertyHelper.getProperty( "contacto.resume.contestadas.no" ) );
		lblNoContestadas.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblNoContestadas.setVisible( false );

		txtNoRealizados = new Text( grpResume, SWT.BORDER );
		txtNoRealizados.setBounds( 310, 10, EnvironmentConstants.TEXTBOX_NUMBER_WIDTH, EnvironmentConstants.TEXTBOX_NUMBER_HEIGHT );
		txtNoRealizados.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtNoRealizados.setEditable( false );
		txtNoRealizados.setVisible( false );

		grpFiltros = new Group( container, SWT.NONE );
		grpFiltros.setText( Constants.CADENA_VACIA );
		grpFiltros.setBounds( 550, 34, 200, 40 );
		grpFiltros.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblAtendio = new Label( grpFiltros, SWT.NONE );
		lblAtendio.setBounds( 10, 13, 55, EnvironmentConstants.LABEL_NORMAL_HEIGHT );
		lblAtendio.setText( ContactoPropertyHelper.getProperty( "contacto.criterio.atendio" ) );
		lblAtendio.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtAtendio = new Text( grpFiltros, SWT.BORDER );
		txtAtendio.setBounds( 65, 6, 90, 25 );
		txtAtendio.setTextLimit( Constants.LONGITUD_ID_EMPLEADO );

		Button btnFiltrar = new Button( grpFiltros, SWT.NONE );
		btnFiltrar.setBounds( 160, 6, 35, 27 );
		btnFiltrar.setImage( ControlTrabajosWindowElements.buscarIcon );
		btnFiltrar.setToolTipText( ApplicationPropertyHelper.getProperty( "button.filtrar.label" ) );

		table = new Table( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL );
		table.setLinesVisible( true );
		table.setBounds( 25, 80, 725, 330 );
		table.setHeaderVisible( true );

		TableUtils.createTableColumn( table, SWT.NONE, 75, TrabajosPropertyHelper.getProperty( "trabajos.label.rx" ) );
		TableUtils.createTableColumn( table, SWT.NONE, 125, TrabajosPropertyHelper.getProperty( "trabajos.label.cliente" ) );
		TableUtils.createTableColumn( table, SWT.NONE, 80, TrabajosPropertyHelper.getProperty( "trabajos.label.venta" ) );
		TableUtils.createTableColumn( table, SWT.NONE, 55, TrabajosPropertyHelper.getProperty( "trabajos.label.personal.atendio" ) );
		TableUtils.createTableColumn( table, SWT.NONE, 100, TrabajosPropertyHelper.getProperty( "trabajos.label.contacto.tipo" ) );
		TableUtils.createTableColumn( table, SWT.NONE, 100, TrabajosPropertyHelper.getProperty( "trabajos.label.contacto" ) );
		TableUtils.createTableColumn( table, SWT.NONE, 100, TrabajosPropertyHelper.getProperty( "trabajos.label.contacto.estado" ) );
		TableUtils.createTableColumn( table, SWT.NONE, 100, TrabajosPropertyHelper.getProperty( "trabajos.label.promesa" ) );

		MenuInfoTrabajos menuInfoTrabajos = new MenuInfoTrabajos( applicationWindow.getShell(), table, ApplicationUtils.opcionesMenu( menuOptionDesactive ) );
		table.setMenu( menuInfoTrabajos.getMenuInfoTrabajos() );

		VerifyListener upperTxt = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		txtAtendio.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				if ( arg0.character == SWT.CR ) {
					validateIdEmpleado();
				}
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		txtAtendio.addVerifyListener( upperTxt );

		btnFiltrar.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				validateIdEmpleado();
			}
		} );

		table.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				int index = table.getSelectionIndex();
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE );
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_CLIENTE_NOMBRE );
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RECETA );

				if ( index >= 0 ) {
					ContactoView contactoView = lastPagingListener.getItem( index );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE, contactoView.getIdClienteJb() );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX, contactoView.getRx() );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_CLIENTE_NOMBRE, contactoView.getCliente() );
					try {
						if ( contactoView.getRx().startsWith( RxConstants.TIPO_GRUPO_F ) ) {
							table.getMenu().getItem( 0 ).setEnabled( false );
							table.getMenu().getItem( 1 ).setEnabled( false );
							table.getMenu().getItem( 2 ).setEnabled( false );
							table.getMenu().getItem( 3 ).setEnabled( false );
							table.getMenu().getItem( 4 ).setEnabled( false );
							table.getMenu().getItem( 5 ).setEnabled( false );
							table.getMenu().getItem( 6 ).setEnabled( false );
							table.getMenu().getItem( 7 ).setEnabled( false );
							table.getMenu().getItem( 8 ).setEnabled( false );
							table.getMenu().getItem( 9 ).setEnabled( false );
							table.getMenu().getItem( 10 ).setEnabled( true );
							table.getMenu().getItem( 11 ).setEnabled( false );
						} else {
							NotaVenta nv = trabajoService.findNotaVentaByFactura( contactoView.getRx() );
							Jb jb = trabajoService.findById( contactoView.getRx() );

							if ( nv != null && nv.getReceta() != null && nv.getReceta() > 0 ) {
								table.getMenu().getItem( 0 ).setEnabled( true );
								Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RECETA, nv.getReceta() );
							} else {
								table.getMenu().getItem( 0 ).setEnabled( false );
							}

							table.getMenu().getItem( 1 ).setEnabled( true );
							table.getMenu().getItem( 2 ).setEnabled( true );
							table.getMenu().getItem( 3 ).setEnabled( true );

							table.getMenu().getItem( 4 ).setEnabled( true );
							table.getMenu().getItem( 5 ).setEnabled( true );

							table.getMenu().getItem( 6 ).setEnabled( trabajoService.validateRetenerTrabajo( EstadoTrabajo.parse( contactoView.getJbEstado() ) ) );
							table.getMenu().getItem( 7 ).setEnabled( trabajoService.validateDesretenerTrabajo( jb ) );
							table.getMenu().getItem( 8 ).setEnabled( trabajoService.validateAutorizacion( jb ) );
							// Activa Datos Factura
							table.getMenu().getItem( 9 ).setEnabled( true );
							// Activa agrupar
							table.getMenu().getItem( 10 ).setEnabled( trabajoService.validarAgrupar( jb ) );
							// Activa desagrupar
							table.getMenu().getItem( 11 ).setEnabled( trabajoService.validarDesagrupar( jb ) );
						}
					} catch ( Exception e ) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );

		table.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseDoubleClick( MouseEvent e ) {
				int index = table.getSelectionIndex();
				if ( index >= 0 ) {
					ContactoView contactoView = lastPagingListener.getItem( index );
					Session.setAttribute( Constants.PARAM_CONTACTO_VIEW, contactoView );
					ContactoViewDataDialog contactoViewDataDialog = new ContactoViewDataDialog( applicationWindow.getShell() );
					contactoViewDataDialog.open();
					getAllContacto();
					totalEstadoContacto();
				}
			}
		} );

		table.addListener( SWT.PaintItem, new TableListener( 7, 6 ) );

		loadContacto();
		totalEstadoContacto();
		return container;
	}

	private void validateIdEmpleado() {
		try {
			if ( !txtAtendio.getText().isEmpty() ) {
				Empleado e = loginService.findUserById( txtAtendio.getText() );
				if ( e != null ) {
					txtAtendio.setBackground( SWTResourceManager.getColor( SWT.COLOR_WHITE ) );
					getAllContacto();
					totalEstadoContacto();
				} else if ( e == null ) {
					MessageDialog.openError( applicationWindow.getShell(), ContactoPropertyHelper.getProperty( "contacto.search.error.title" ), ContactoPropertyHelper.getProperty( "contacto.search.error.message" ) );
					txtAtendio.setBackground( GraphicConstants.FIELD_COLOR_INVALID );
				}
			} else {
				getAllContacto();
				totalEstadoContacto();
			}
		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
	}

	public void getAllContacto() {
		table.removeAll();
		if ( lastPagingListener != null )
			table.removeListener( SWT.SetData, lastPagingListener );
		lastPagingListener = new PagingListener<ContactoView>( 10, new ContactoViewPagingService( contactoViewService, txtAtendio.getText() ), new ContactoViewRender(), "error servicio ContactoContainer" );
		table.addListener( SWT.SetData, lastPagingListener );
		table.setItemCount( lastPagingListener.size() );
		table.redraw();
	}

	public void loadContacto() {
		table.removeAll();
		if ( lastPagingListener != null )
			table.removeListener( SWT.SetData, lastPagingListener );
		lastPagingListener = new PagingListener<ContactoView>( 100, new ContactoViewPagingService( contactoViewService, Constants.CADENA_VACIA ), new ContactoViewRender(), "error de servicio contacto view" );
		table.addListener( SWT.SetData, lastPagingListener );
		table.setItemCount( lastPagingListener.size() );
		table.redraw();
	}

	private void totalEstadoContacto() {
		int pendientes = getTotalEstadoContacto( EstadoConstants.ESTADO_PENDIENTE, txtAtendio.getText() );
		int no_realizados = getTotalRetrasadoContacto( JbTipoConstants.JB_TIPO_RETRASADO, txtAtendio.getText() );
		txtPendientes.setText( String.valueOf( pendientes ) + TrabajosPropertyHelper.getProperty( "symbol.diagonal" ) + String.valueOf( no_realizados ) );
	}

	private int getTotalEstadoContacto( String estado, String atendio ) {
		int i = 0;
		try {
			i = contactoViewService.countEstado( estado, atendio );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return i;
	}

	private int getTotalRetrasadoContacto( String tipo, String atendio ) {
		int i = 0;
		try {
			i = contactoViewService.countTipo( tipo, atendio );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return i;
	}

}

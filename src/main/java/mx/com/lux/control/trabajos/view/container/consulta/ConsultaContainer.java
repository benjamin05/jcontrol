package mx.com.lux.control.trabajos.view.container.consulta;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.EdoGrupo;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.NotaVenta;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.RxConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.ConsultaTrabajoDialog;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.TableListener;
import mx.com.lux.control.trabajos.view.paging.PagingListener;
import mx.com.lux.control.trabajos.view.paging.consulta.ConsultaPagingService;
import mx.com.lux.control.trabajos.view.render.ConsultaRender;
import mx.com.lux.control.trabajos.view.render.EstadosRender;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

public class ConsultaContainer {

	private TrabajoService trabajoService;

	private final ApplicationWindow applicationWindow;
	private final Shell shell;
	private PagingListener<Jb> lastPagingListener;
	private Text txtCliente;
	private Text txtRx;
	private Text txtAtendio;
	private ComboViewer cmbEstado;
	private Table table;
	private Label lblSaldoValue;
	private final int[] opcionesMenu = new int[]{
			Constants.MENU_INDEX_DATOS_RECETA, Constants.MENU_INDEX_CONSULTA_TRABAJO, Constants.MENU_INDEX_INFO_LAB, Constants.MENU_INDEX_DATOS_CLIENTE, Constants.MENU_INDEX_REPROGRAMAR, Constants.MENU_INDEX_NO_CONTACTAR, Constants.MENU_INDEX_RETENER, Constants.MENU_INDEX_DESRETENER, Constants.MENU_INDEX_AUTORIZACION, Constants.MENU_INDEX_DATOS_FACTURA, Constants.MENU_INDEX_DESVINCULAR, Constants.MENU_INDEX_CREAR_GRUPO, Constants.MENU_INDEX_EXTERNO_ENVIAR, Constants.MENU_INDEX_EXTERNO_ELIMINAR,
			Constants.MENU_INDEX_EXTERNO_ENTREGAR,
			Constants.MENU_INDEX_NUEVA_ORDEN
	};

	public ConsultaContainer( ApplicationWindow applicationWindow ) {
		this.applicationWindow = applicationWindow;
		this.shell = applicationWindow.getShell();
	}

	public Control createContents( TabFolder tabFolder ) {
		trabajoService = ServiceLocator.getBean( TrabajoService.class );

		Composite container = new Composite( tabFolder, SWT.NONE );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group grpBsqueda = new Group( container, SWT.NONE );
		grpBsqueda.setText( ApplicationPropertyHelper.getProperty( "consultas.trabajo.grupo.busqueda.title" ) );
		grpBsqueda.setBounds( 15, 10, 375, 135 );
		grpBsqueda.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblRx = new Label( grpBsqueda, SWT.NONE );
		lblRx.setBounds( 15, 15, 50, 15 );
		lblRx.setText( ApplicationPropertyHelper.getProperty( "consultas.criterio.rx" ) );
		lblRx.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtRx = new Text( grpBsqueda, SWT.BORDER );
		txtRx.setBounds( 100, 15, 100, 25 );
		txtRx.setTextLimit( 10 );

		Label lblNombreCliente = new Label( grpBsqueda, SWT.NONE );
		lblNombreCliente.setText( "Cliente" );
		lblNombreCliente.setBounds( 15, 45, 50, 15 );
		lblNombreCliente.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtCliente = new Text( grpBsqueda, SWT.BORDER );
		txtCliente.setBounds( 100, 45, 250, 25 );
		txtCliente.setTextLimit( 50 );

		Label lblEstado = new Label( grpBsqueda, SWT.NONE );
		lblEstado.setBounds( 15, 75, 50, 15 );
		lblEstado.setText( ApplicationPropertyHelper.getProperty( "consultas.criterio.estado" ) );
		lblEstado.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		cmbEstado = new ComboViewer( grpBsqueda, SWT.READ_ONLY );
		cmbEstado.setContentProvider( new ArrayContentProvider() );
		cmbEstado.setLabelProvider( new EstadosRender() );
		cmbEstado.getCombo().setVisibleItemCount( 10 );
		cmbEstado.getCombo().setBounds( 100, 75, 250, 25 );
		cmbEstado.setInput( trabajoService.getEstados() );

		Label lblAtendi = new Label( grpBsqueda, SWT.NONE );
		lblAtendi.setBounds( 15, 105, 50, 15 );
		lblAtendi.setText( ApplicationPropertyHelper.getProperty( "consultas.criterio.atendio" ) );
		lblAtendi.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtAtendio = new Text( grpBsqueda, SWT.BORDER );
		txtAtendio.setBounds( 100, 105, 250, 25 );
		txtAtendio.setTextLimit( Constants.LONGITUD_ID_EMPLEADO );

		VerifyListener upperTxt = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		Button btnBuscar = new Button( container, SWT.NONE );
		btnBuscar.setBounds( 485, 101, 86, 28 );
		btnBuscar.setImage( ControlTrabajosWindowElements.buscarIcon );
		btnBuscar.setText( ApplicationPropertyHelper.getProperty( "button.buscar.label" ) );

		Button btnLimpiar = new Button( container, SWT.NONE );
		btnLimpiar.setBounds( 620, 101, 85, 28 );
		btnLimpiar.setImage( ControlTrabajosWindowElements.limpiarIcon );
		btnLimpiar.setText( ApplicationPropertyHelper.getProperty( "button.limpiar.label" ) );

		Group grpSaldo = new Group( container, SWT.NONE );
		grpSaldo.setText( "Saldo" );
		grpSaldo.setBounds( 431, 10, 330, 85 );
		grpSaldo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		lblSaldoValue = new Label( grpSaldo, SWT.NONE );
		lblSaldoValue.setBounds( 10, 20, 315, 55 );
		lblSaldoValue.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblSaldoValue.setFont( SWTResourceManager.getFont( "Tahoma", 35, SWT.BOLD ) );
		lblSaldoValue.setAlignment( SWT.CENTER );

		table = new Table( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL );
		table.setHeaderVisible( true );
		table.setLinesVisible( true );
		table.setBounds( 15, 145, 745, 270 );
		final Menu menu = MenuInfoTrabajos.generaMenu( applicationWindow.getShell(), table, opcionesMenu );
		table.setMenu( menu );

		TableColumn colRx = new TableColumn( table, SWT.NONE );
		colRx.setWidth( 75 );
		colRx.setText( ApplicationPropertyHelper.getProperty( "consultas.grid.rx" ) );

		TableColumn colCliente = new TableColumn( table, SWT.NONE );
		colCliente.setWidth( 300 );
		colCliente.setText( ApplicationPropertyHelper.getProperty( "consultas.grid.cliente" ) );

		TableColumn colEstado = new TableColumn( table, SWT.NONE );
		colEstado.setWidth( 120 );
		colEstado.setText( ApplicationPropertyHelper.getProperty( "consultas.grid.estado" ) );

		TableColumn colFecha = new TableColumn( table, SWT.NONE );
		colFecha.setWidth( 90 );
		colFecha.setText( ApplicationPropertyHelper.getProperty( "consultas.grid.fecha" ) );

		TableColumn colAtendio = new TableColumn( table, SWT.NONE );
		colAtendio.setWidth( 70 );
		colAtendio.setText( ApplicationPropertyHelper.getProperty( "consultas.grid.atendio" ) );

		TableColumn colPromesa = new TableColumn( table, SWT.NONE );
		colPromesa.setWidth( 80 );
		colPromesa.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.promesa" ) );

		txtRx.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				if ( arg0.character == SWT.CR ) {
					searchFilter();
				}
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		txtCliente.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				if ( arg0.character == SWT.CR ) {
					searchFilter();
				}
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		txtAtendio.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				if ( arg0.character == SWT.CR ) {
					searchFilter();
				}
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		btnBuscar.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				searchFilter();
			}
		} );

		btnLimpiar.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				txtRx.setText( "" );
				txtRx.redraw();
				cmbEstado.getCombo().clearSelection();
				cmbEstado.setSelection( null );
				txtAtendio.setText( "" );
				txtAtendio.redraw();
				txtCliente.setText( "" );
				txtCliente.redraw();
				table.removeAll();
				table.redraw();
				lblSaldoValue.setText( "" );
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
					Jb trabajo = lastPagingListener.getItem( index );
					String rx = trabajo.getRx();
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE, trabajo.getIdCliente() );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX, rx );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_CLIENTE_NOMBRE, trabajo.getCliente() );
					lblSaldoValue.setText( trabajoService.obtenSaldoEnTexto( rx ) );
					NotaVenta notaVenta = trabajoService.findNotaVentaByFactura( rx );

					if ( rx.startsWith( RxConstants.TIPO_GRUPO_F ) ) {
						menu.getItem( 0 ).setEnabled( false );
						menu.getItem( 1 ).setEnabled( false );
						menu.getItem( 2 ).setEnabled( false );
						menu.getItem( 3 ).setEnabled( false );
						menu.getItem( 4 ).setEnabled( false );
						menu.getItem( 5 ).setEnabled( false );
						menu.getItem( 6 ).setEnabled( false );
						menu.getItem( 7 ).setEnabled( false );
						menu.getItem( 8 ).setEnabled( false );
						menu.getItem( 9 ).setEnabled( false );
						menu.getItem( 10 ).setEnabled( true );
						menu.getItem( 11 ).setEnabled( false );
						menu.getItem( 12 ).setEnabled( trabajoService.puedeEnviarExterno( trabajo ) );
						menu.getItem( 13 ).setEnabled( trabajoService.puedeEliminarExterno( trabajo ) );
					} else {
						if ( notaVenta != null && notaVenta.getReceta() != null && notaVenta.getReceta() > 0 ) {
							table.getMenu().getItem( 0 ).setEnabled( true );
							Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RECETA, notaVenta.getReceta() );
						} else {
							menu.getItem( 0 ).setEnabled( true );
						}
						menu.getItem( 1 ).setEnabled( true );
						menu.getItem( 2 ).setEnabled( true );
						menu.getItem( 3 ).setEnabled( true );
						menu.getItem( 4 ).setEnabled( true );
						menu.getItem( 5 ).setEnabled( true );
						menu.getItem( 6 ).setEnabled( trabajoService.validateRetenerTrabajo( trabajo.estado() ) );
						menu.getItem( 7 ).setEnabled( trabajoService.validateDesretenerTrabajo( trabajo ) );
						menu.getItem( 8 ).setEnabled( trabajoService.validateAutorizacion( trabajo ) );
						menu.getItem( 9 ).setEnabled( true );
						menu.getItem( 10 ).setEnabled( trabajoService.validarAgrupar( trabajo ) );
						menu.getItem( 11 ).setEnabled( trabajoService.validarDesagrupar( trabajo ) );
						menu.getItem( 13 ).setEnabled( trabajoService.puedeEnviarExterno( trabajo ) );
						menu.getItem( 14 ).setEnabled( trabajoService.puedeEliminarExterno( trabajo ) );
						menu.getItem( 15 ).setEnabled( trabajoService.puedeEntregarExterno( rx ) );
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
					Jb tj = lastPagingListener.getItem( index );
					Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX, tj.getRx() );
					ConsultaTrabajoDialog consultaTrabajoDialog = new ConsultaTrabajoDialog( shell );
					consultaTrabajoDialog.open();
				}
			}
		} );

		table.addListener( SWT.PaintItem, new TableListener( ConsultaRender.PROMESA_CELL, ConsultaRender.ESTADO_CELL ) );
		txtRx.addVerifyListener( upperTxt );
		txtCliente.addVerifyListener( upperTxt );
		txtAtendio.addVerifyListener( upperTxt );

		return container;
	}

	public void searchFilter() {
		table.removeAll();
		if ( lastPagingListener != null ) {
			table.removeListener( SWT.SetData, lastPagingListener );
		}
		ConsultaPagingService pagingService = new ConsultaPagingService( trabajoService, txtRx.getText(), obtenSeleccionGrupoEstados(), txtAtendio.getText(), txtCliente.getText() );
		lastPagingListener = new PagingListener<Jb>( 20, pagingService, new ConsultaRender(), "error de servicio jb" );
		table.addListener( SWT.SetData, lastPagingListener );
		table.setItemCount( lastPagingListener.size() );
		table.redraw();
	}

	private Integer obtenSeleccionGrupoEstados() {
		StructuredSelection selection = (StructuredSelection) cmbEstado.getSelection();
		EdoGrupo estado = (EdoGrupo) selection.getFirstElement();
		if ( estado != null ) {
			return estado.getId();
		}
		return -1;
	}
}

package mx.com.lux.control.trabajos.view.container.reposicion;

import mx.com.lux.control.trabajos.bussiness.service.ReposicionService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.Reposicion;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.ConsultaTrabajoDialog;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReposicionContainer {

	private final Logger log = LoggerFactory.getLogger( ReposicionContainer.class );

	private final Shell shell;

	private Text textoGarantia;
	private Text textoCliente;
	private Table tablaTrabajos;
	private Table tablaReposiciones;

	private ReposicionService reposicionService;
	private TrabajoService trabajoService;

	public ReposicionContainer( final ApplicationWindow applicationWindow ) {
		shell = applicationWindow.getShell();
		reposicionService = ServiceLocator.getBean( ReposicionService.class );
		trabajoService = ServiceLocator.getBean( TrabajoService.class );
	}

	public Control createContents( final TabFolder pestanya ) {
		Composite contenedor = crearContenedorPrincipal( pestanya );
		crearBusqueda( contenedor );
		crearTablaTrabajos( contenedor );
		crearTablaReposiciones( contenedor );
		cargarTrabajos();
		return contenedor;
	}

	private Composite crearContenedorPrincipal( final TabFolder pestanya ) {
		Composite contenedor = new Composite( pestanya, SWT.NONE );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		RowLayout layout = new RowLayout( SWT.HORIZONTAL );
		layout.marginWidth = 10;
		layout.marginHeight = 10;
		contenedor.setLayout( layout );
		RowData rd = new RowData();
		rd.width = 750;
		contenedor.setLayoutData( rd );
		return contenedor;
	}

	private void crearBusqueda( final Composite contenedor ) {
		Group grupoBusqueda = new Group( contenedor, SWT.NONE );
		grupoBusqueda.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		grupoBusqueda.setText( "Búsqueda" );
		GridLayout layout = new GridLayout( 5, false );
		layout.marginWidth = 10;
		layout.marginHeight = 10;
		layout.horizontalSpacing = 20;
		grupoBusqueda.setLayout( layout );
		RowData rd = new RowData();
		rd.width = 750;
		rd.height = 45;
		grupoBusqueda.setLayoutData( rd );

		VerifyListener textoEnMayusculas = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		Label etiquetaGarantia = new Label( grupoBusqueda, SWT.NONE );
		etiquetaGarantia.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		etiquetaGarantia.setText( "Rx" );
		GridData gd0 = new GridData();
		gd0.minimumWidth = 50;
		etiquetaGarantia.setLayoutData( gd0 );

		textoGarantia = new Text( grupoBusqueda, SWT.BORDER );
		GridData gd1 = new GridData();
		gd1.widthHint = 140;
		textoGarantia.setLayoutData( gd1 );

		Label etiquetaCliente = new Label( grupoBusqueda, SWT.NONE );
		etiquetaCliente.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		etiquetaCliente.setText( "Cliente" );
		GridData gd2 = new GridData();
		gd2.minimumWidth = 50;
		etiquetaCliente.setLayoutData( gd2 );

		textoCliente = new Text( grupoBusqueda, SWT.BORDER );
		GridData gd3 = new GridData();
		gd3.widthHint = 250;
		textoCliente.setLayoutData( gd3 );
		textoCliente.addVerifyListener( textoEnMayusculas );

		Button botonBuscar = new Button( grupoBusqueda, SWT.NONE );
		botonBuscar.setText( "Buscar" );
		botonBuscar.setImage( ControlTrabajosWindowElements.buscarIcon );
		GridData gd4 = new GridData();
		gd4.widthHint = 100;
		botonBuscar.setLayoutData( gd4 );

		botonBuscar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				cargarTrabajos();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );

	}

	private void crearTablaTrabajos( Composite contenedor ) {
		tablaTrabajos = new Table( contenedor, SWT.BORDER | SWT.VIRTUAL );
		tablaTrabajos.setHeaderVisible( true );
		tablaTrabajos.setLinesVisible( true );
		RowData rd = new RowData();
		rd.width = 735;
		rd.height = 160;
		tablaTrabajos.setLayoutData( rd );

		TableColumn col1 = new TableColumn( tablaTrabajos, SWT.NONE );
		col1.setWidth( 100 );
		col1.setText( "Rx" );

		TableColumn col2 = new TableColumn( tablaTrabajos, SWT.NONE );
		col2.setWidth( 200 );
		col2.setText( "Material" );

		TableColumn col3 = new TableColumn( tablaTrabajos, SWT.NONE );
		col3.setWidth( 430 );
		col3.setText( "Cliente" );

		tablaTrabajos.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseDoubleClick( MouseEvent e ) {
				int index = tablaTrabajos.getSelectionIndex();
				TableItem item = tablaTrabajos.getItem( index );
				log.info( "Seleccionado trabajo " + item.getText( 0 ) + " : " + item.getText( 1 ) + " : " + item.getText( 2 ) );
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
				Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX, item.getText( 0 ) );
				ConsultaTrabajoDialog consultaTrabajoDialog = new ConsultaTrabajoDialog( shell );
				consultaTrabajoDialog.open();
			}
		} );

		tablaTrabajos.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				int index = tablaTrabajos.getSelectionIndex();
				TableItem item = tablaTrabajos.getItem( index );
				log.info( "Seleccionado trabajo " + item.getText( 0 ) + " : " + item.getText( 1 ) + " : " + item.getText( 2 ) );
				cargarReposiciones( item.getText( 0 ) );

				String rx = item.getText( 0 );
				Jb trabajo = trabajoService.findById( rx );
				Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX, rx );
				Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE, trabajo.getIdCliente() );
				Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ORDEN_SERVICIO, trabajo );

				MenuReposiciones.calcularOpcionesActivas( rx );
			}
		} );

		tablaTrabajos.setMenu( MenuReposiciones.construir( shell, tablaTrabajos ) );
	}

	private void crearTablaReposiciones( Composite contenedor ) {
		tablaReposiciones = new Table( contenedor, SWT.BORDER | SWT.VIRTUAL );
		tablaReposiciones.setHeaderVisible( true );
		tablaReposiciones.setLinesVisible( true );
		RowData rd = new RowData();
		rd.width = 735;
		rd.height = 130;
		tablaReposiciones.setLayoutData( rd );

		TableColumn col1 = new TableColumn( tablaReposiciones, SWT.NONE );
		col1.setWidth( 30 );
		col1.setText( "No." );

		TableColumn col2 = new TableColumn( tablaReposiciones, SWT.NONE );
		col2.setWidth( 100 );
		col2.setText( "Fecha" );

		TableColumn col3 = new TableColumn( tablaReposiciones, SWT.NONE );
		col3.setWidth( 200 );
		col3.setText( "Causa" );

		TableColumn col4 = new TableColumn( tablaReposiciones, SWT.NONE );
		col4.setWidth( 300 );
		col4.setText( "Elaboró" );

		TableColumn col5 = new TableColumn( tablaReposiciones, SWT.NONE );
		col5.setWidth( 50 );
		col5.setText( "Tipo" );

		tablaReposiciones.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseDoubleClick( MouseEvent e ) {
				int index = tablaReposiciones.getSelectionIndex();
				TableItem item = tablaReposiciones.getItem( index );
				Integer numeroOrden = Integer.parseInt( item.getText( 0 ) );
				String rxReposicion = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
				log.debug( "Reposicion: Rx: " + rxReposicion + " : Numero Orden: " + numeroOrden );
				DetalleReposicionDialog dialogo = new DetalleReposicionDialog( shell, rxReposicion, numeroOrden );
				dialogo.open();
			}
		} );

		tablaReposiciones.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				int index = tablaReposiciones.getSelectionIndex();
				TableItem item = tablaReposiciones.getItem( index );
				Integer numeroOrden = Integer.parseInt( item.getText( 0 ) );
				Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_NUMERO_ORDEN, numeroOrden );
			}
		} );

		MenuImprimirReposicion menu = new MenuImprimirReposicion( shell, tablaReposiciones );
		tablaReposiciones.setMenu( menu.getMenu() );
	}

	public void cargarTrabajos() {
		tablaTrabajos.clearAll();
		tablaTrabajos.removeAll();
		for ( Jb trabajo : reposicionService.buscarTrabajosReposicionesPorClientePorRx( textoGarantia.getText(), textoCliente.getText() ) ) {
			TableItem item = new TableItem( tablaTrabajos, SWT.NONE );
			item.setText( 0, StringUtils.defaultIfBlank( trabajo.getRx(), "" ) );
			item.setText( 1, StringUtils.defaultIfBlank( trabajo.getMaterial(), "" ) );
			item.setText( 2, StringUtils.defaultIfBlank( trabajo.getCliente(), "" ) );
		}
	}

	public void cargarReposiciones( String rx ) {
		tablaReposiciones.clearAll();
		tablaReposiciones.removeAll();
		for( Reposicion reposicion : reposicionService.buscarReposicionesAsociadasAlTrabajo( rx ) ) {
			TableItem item = new TableItem( tablaReposiciones, SWT.NONE );
			item.setText( 0, reposicion.getNumeroOrden().toString() );
			item.setText( 1, ApplicationUtils.formatearFecha( reposicion.getFecha(), "dd/MM/yyyy" ) );
			item.setText( 2, StringUtils.defaultIfBlank( reposicion.getCausa().getDescripcion(), "" ) );
			item.setText( 3, StringUtils.defaultIfBlank( reposicion.getEmpleado().getNombreApellidos(), "" ) );
			item.setText( 4, StringUtils.defaultIfBlank( reposicion.getTipo().toString(), "" ) );
		}
	}

}

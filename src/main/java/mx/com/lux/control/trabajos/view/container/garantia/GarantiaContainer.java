package mx.com.lux.control.trabajos.view.container.garantia;

import mx.com.lux.control.trabajos.bussiness.service.GarantiaService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

public class GarantiaContainer {

	private final Shell shell;
	// Servicios
	private final GarantiaService garantiaService;
	private final TrabajoService trabajoService;
	// Elementos graficos
	private Table tablaGarantias;
	private Text textoGarantia;
	private Text textoCliente;

	private final Integer[] opcionesDesactivadas = {
		// Las lineas comentadas son las opciones activas que aparecen en el menu al pulsar boton derecho del raton
		Constants.MENU_INDEX_DATOS_RECETA,
		// Constants.MENU_INDEX_CONSULTA_TRABAJO,
		// Constants.MENU_INDEX_INFO_LAB,
		// Constants.MENU_INDEX_DATOS_CLIENTE,
		// Constants.MENU_INDEX_REPROGRAMAR,
		// Constants.MENU_INDEX_NO_CONTACTAR,
		// Constants.MENU_INDEX_RETENER,
		// Constants.MENU_INDEX_DESRETENER,
		Constants.MENU_INDEX_ENVIAR,
		Constants.MENU_INDEX_NOENVIAR,
		Constants.MENU_INDEX_EFAX,
		Constants.MENU_INDEX_AUTORIZACION,
		Constants.MENU_INDEX_SOBRE,
		Constants.MENU_INDEX_DATOS_FACTURA,
		// Constants.MENU_INDEX_DESVINCULAR,
		// Constants.MENU_INDEX_CREAR_GRUPO,
		Constants.MENU_INDEX_AGREGAR_GRUPO,
		Constants.MENU_INDEX_AGREGAR_RX,
		Constants.MENU_INDEX_NUEVA_ORDEN,
		Constants.MENU_INDEX_ENTREGAR,
		Constants.MENU_INDEX_DESENTREGAR,
		Constants.MENU_INDEX_IMPRIMIR,
		Constants.MENU_INDEX_BODEGA,
		Constants.MENU_INDEX_EXTERNO_ENVIAR,
		Constants.MENU_INDEX_EXTERNO_ELIMINAR,
		Constants.MENU_INDEX_EXTERNO_ENTREGAR,
		Constants.MENU_INDEX_FORMACONTACTO_MODIFICAR,
		// Constants.MENU_INDEX_GARANTIA_NUEVA,
		// Constants.MENU_INDEX_GARANTIA_ENTREGAR,
		// Constants.MENU_INDEX_GARANTIA_DESENTREGAR,
		// Constants.MENU_INDEX_GARANTIA_IMPRIMIR,
		// Constants.MENU_INDEX_GARANTIA_BODEGA,
		// Constants.MENU_INDEX_GARANTIA_REPOSICION,
	};

	public GarantiaContainer( final ApplicationWindow applicationWindow ) {
		shell = applicationWindow.getShell();
		garantiaService = ServiceLocator.getBean( GarantiaService.class );
		trabajoService = ServiceLocator.getBean( TrabajoService.class );
	}

	public Control createContents( final TabFolder pestanya ) {

		Composite contenedor = crearContenedorPrincipal( pestanya );
		crearBusqueda( contenedor );
		crearResultadosBusqueda( contenedor );
		cargarDatosTabla();
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
		etiquetaGarantia.setText( "Garantía" );
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
				cargarDatosTabla();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );

	}

	private void crearResultadosBusqueda( final Composite contenedor ) {

		tablaGarantias = new Table( contenedor, SWT.BORDER | SWT.VIRTUAL );
		tablaGarantias.setHeaderVisible( true );
		tablaGarantias.setLinesVisible( true );
		RowData rd = new RowData();
		rd.width = 735;
		rd.height = 320;
		tablaGarantias.setLayoutData( rd );

		MenuInfoTrabajos menu = new MenuInfoTrabajos( shell, tablaGarantias, ApplicationUtils.opcionesMenu( opcionesDesactivadas ) );
		// MenuGarantia menuGarantia = new MenuGarantia( shell, tablaGarantias );
		tablaGarantias.setMenu( menu.getMenuInfoTrabajos() );

		TableColumn col1 = new TableColumn( tablaGarantias, SWT.NONE );
		col1.setWidth( 100 );
		col1.setText( "Garantia" );

		TableColumn col2 = new TableColumn( tablaGarantias, SWT.NONE );
		col2.setWidth( 300 );
		col2.setText( "Cliente" );

		TableColumn col3 = new TableColumn( tablaGarantias, SWT.NONE );
		col3.setWidth( 130 );
		col3.setText( "Articulo" );

		TableColumn col4 = new TableColumn( tablaGarantias, SWT.NONE );
		col4.setWidth( 100 );
		col4.setText( "Fecha" );

		TableColumn col5 = new TableColumn( tablaGarantias, SWT.NONE );
		col5.setWidth( 100 );
		col5.setText( "Estado" );

		tablaGarantias.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseDoubleClick( MouseEvent e ) {
				int index = tablaGarantias.getSelectionIndex();
				if ( index >= 0 ) {
					TableItem item = tablaGarantias.getItem( index );
					String rx = item.getText( 0 );
					Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX, rx );

					DetalleGarantiaDialog dialogo = new DetalleGarantiaDialog( shell, rx );
					dialogo.open();
				}
			}
		} );

		tablaGarantias.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				int index = tablaGarantias.getSelectionIndex();
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ORDEN_SERVICIO );
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE );

				if ( index >= 0 ) {
					TableItem item = tablaGarantias.getItem( index );
					String rx = item.getText( 0 );
					Jb trabajo = trabajoService.findById( rx );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ORDEN_SERVICIO, trabajo );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX, rx );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE, trabajo.getIdCliente() );
					tablaGarantias.getMenu().getItem( 0 ).setEnabled( true );
					tablaGarantias.getMenu().getItem( 1 ).setEnabled( true );
					tablaGarantias.getMenu().getItem( 2 ).setEnabled( true );
					tablaGarantias.getMenu().getItem( 3 ).setEnabled( true );
					tablaGarantias.getMenu().getItem( 4 ).setEnabled( true );
					tablaGarantias.getMenu().getItem( 5 ).setEnabled( trabajoService.validateRetenerTrabajo( trabajo.estado() ) ); // Retener
					tablaGarantias.getMenu().getItem( 6 ).setEnabled( trabajoService.validateDesretenerTrabajo( trabajo ) ); // Desretener
					tablaGarantias.getMenu().getItem( 7 ).setEnabled( trabajoService.validarAgrupar( trabajo ) );
					tablaGarantias.getMenu().getItem( 8 ).setEnabled( trabajoService.validarDesagrupar( trabajo ) );
					tablaGarantias.getMenu().getItem( 10 ).setEnabled( garantiaService.sePuedeEntregar( trabajo ) );
					tablaGarantias.getMenu().getItem( 11 ).setEnabled( garantiaService.sePuedeDesentregar( trabajo ) );
					tablaGarantias.getMenu().getItem( 13 ).setEnabled( garantiaService.sePuedePonerEnBodega( trabajo ) );
					tablaGarantias.getMenu().getItem( 14 ).setEnabled( true );
				}
			}
		} );
	}

	public void cargarDatosTabla() {
		tablaGarantias.clearAll();
		tablaGarantias.removeAll();
		for ( Jb trabajo : garantiaService.buscarTrabajosGarantiaPorGarantiaPorCliente( textoGarantia.getText(), textoCliente.getText() ) ) {
			TableItem item = new TableItem( tablaGarantias, SWT.NONE );
			item.setText( 0, StringUtils.defaultIfBlank( trabajo.getRx(), "" ) );
			item.setText( 1, StringUtils.defaultIfBlank( trabajo.getCliente(), "" ) );
			item.setText( 2, StringUtils.defaultIfBlank( trabajo.getMaterial(), "" ) );
			item.setText( 3, StringUtils.defaultIfBlank( ApplicationUtils.formatearFecha( trabajo.getFechaVenta(), "dd-MM-yyyy" ), "" ) );
			item.setText( 4, StringUtils.defaultIfBlank( trabajo.estado().codigo(), "" ) );
		}
	}

}

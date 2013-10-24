package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.ExternoService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.TableListener;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import java.util.Date;

public class DatosFacturaExternoDialog extends Dialog {

	private final Logger log = Logger.getLogger( DatosFacturaExternoDialog.class );

	private final String rx;
	private Shell shell;

	private static final TrabajoService trabajoService;
	private static final ExternoService externoService;

	static {
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
		externoService = (ExternoService) ServiceLocator.getBean( "externoService" );
	}

	public DatosFacturaExternoDialog( Shell parentShell ) {
		super( parentShell );
		shell = parentShell;
		rx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 720, 370 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( "Datos Factura trabajo externo RX: " + rx );
		shell = newShell;
	}

	@Override
	protected Control createDialogArea( Composite parent ) {

		shell = parent.getShell();

		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Composite contenedor = crearContenedorPrincipal( parent );
		crearDatosFactura( contenedor );
		crearPestanyas( contenedor );
		return contenedor;
	}

	private Composite crearContenedorPrincipal( Composite parent ) {
		Composite contenedor = (Composite) super.createDialogArea( parent );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		RowLayout layout = new RowLayout( SWT.HORIZONTAL );
		layout.marginWidth = 15;
		layout.marginHeight = 10;
		contenedor.setLayout( layout );
		return contenedor;
	}

	private void crearDatosFactura( Composite contenedor ) {

		Jb trabajo = trabajoService.findById( rx );
		JbExterno externo = externoService.obtenerExternoPorRx( rx );

		Composite grupo = new Composite( contenedor, SWT.NONE );
		grupo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout gl = new GridLayout( 4, false );
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		gl.horizontalSpacing = 15;
		grupo.setLayout( gl );
		RowData rd = new RowData();
		rd.width = 700;
		grupo.setLayoutData( rd );

		// int anchoLabel = 100;
		int anchoTexto = 240;

		Label labelSucursalOrigen = new Label( grupo, SWT.NONE );
		labelSucursalOrigen.setText( "Suc. Origen" );
		labelSucursalOrigen.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text textoSucursalOrigen = new Text( grupo, SWT.BORDER );
		textoSucursalOrigen.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		textoSucursalOrigen.setEditable( false );
		GridData gd1 = new GridData();
		gd1.widthHint = anchoTexto;
		textoSucursalOrigen.setLayoutData( gd1 );
		textoSucursalOrigen.setText( externo != null ? externo.getOrigen() : "" );

		Label labelCliente = new Label( grupo, SWT.NONE );
		labelCliente.setText( "Cliente" );
		labelCliente.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text textoCliente = new Text( grupo, SWT.BORDER );
		textoCliente.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		textoCliente.setEditable( false );
		GridData gd2 = new GridData();
		gd2.widthHint = anchoTexto;
		textoCliente.setLayoutData( gd2 );
		textoCliente.setText( trabajo != null ? trabajo.getCliente() : "" );

		Label labelFechaVenta = new Label( grupo, SWT.NONE );
		labelFechaVenta.setText( "Fecha venta" );
		labelFechaVenta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text textoFechaVenta = new Text( grupo, SWT.BORDER );
		textoFechaVenta.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		textoFechaVenta.setEditable( false );
		GridData gd3 = new GridData();
		gd3.widthHint = 100;
		textoFechaVenta.setLayoutData( gd3 );
		textoFechaVenta.setText( ApplicationUtils.formatearFecha( externo != null ? externo.getFechaFactura() : new Date() ) );

		Label labelArticulos = new Label( grupo, SWT.NONE );
		labelArticulos.setText( "Artículos" );
		labelArticulos.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text textoArticulos = new Text( grupo, SWT.BORDER );
		textoArticulos.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		textoArticulos.setEditable( false );
		GridData gd4 = new GridData();
		gd4.widthHint = anchoTexto;
		textoArticulos.setLayoutData( gd4 );
		textoArticulos.setText( trabajo != null ? trabajo.getMaterial() : "" );

		Label labelFactura = new Label( grupo, SWT.NONE );
		labelFactura.setText( "Factura" );
		labelFactura.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text textoFactura = new Text( grupo, SWT.BORDER );
		textoFactura.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		textoFactura.setEditable( false );
		GridData gd5 = new GridData();
		gd5.widthHint = 100;
		textoFactura.setLayoutData( gd5 );
		textoFactura.setText( trabajo != null ? trabajo.getRx() : "" );

		Label labelSaldo = new Label( grupo, SWT.NONE );
		labelSaldo.setText( "Saldo" );
		labelSaldo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text textoSaldo = new Text( grupo, SWT.BORDER );
		textoSaldo.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		textoSaldo.setEditable( false );
		GridData gd6 = new GridData();
		gd6.widthHint = 100;
		textoSaldo.setLayoutData( gd6 );
		textoSaldo.setText( trabajo != null ? trabajo.getSaldoFormateado() : "" );
	}

	private void crearPestanyas( final Composite contenedor ) {
		TabFolder pestanyas = new TabFolder( contenedor, SWT.NONE );
		pestanyas.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		RowData rd2 = new RowData();
		rd2.width = 680;
		pestanyas.setLayoutData( rd2 );

		crearPestanyaPagos( pestanyas );
		crearPestanyaSeguroConfianza( pestanyas );
	}

	private void crearPestanyaPagos( final TabFolder pestanyas ) {
		TabItem pestanyaPagos = new TabItem( pestanyas, SWT.NONE );
		pestanyaPagos.setText( "Pagos" );

		Composite c1 = new Composite( pestanyas, SWT.NONE );
		pestanyaPagos.setControl( c1 );
		// c1.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout gl = new GridLayout( 1, false );
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		gl.horizontalSpacing = 15;
		c1.setLayout( gl );
		RowData rd = new RowData();
		rd.width = 670;
		c1.setLayoutData( rd );

		Table tablaPagos = new Table( c1, SWT.BORDER | SWT.FULL_SELECTION );
		tablaPagos.setHeaderVisible( true );
		tablaPagos.setLinesVisible( true );
		tablaPagos.addListener( SWT.PaintItem, new TableListener() );
		GridData gd = new GridData();
		gd.widthHint = 670;
		gd.heightHint = 100;
		tablaPagos.setLayoutData( gd );

		TableColumn col1 = new TableColumn( tablaPagos, SWT.NONE );
		col1.setText( "Forma Pago" );
		col1.setWidth( 100 );

		TableColumn col2 = new TableColumn( tablaPagos, SWT.NONE );
		col2.setText( "Banco emisor" );
		col2.setWidth( 200 );

		TableColumn col3 = new TableColumn( tablaPagos, SWT.NONE );
		col3.setText( "Terminal" );
		col3.setWidth( 100 );

		TableColumn col4 = new TableColumn( tablaPagos, SWT.NONE );
		col4.setText( "Plan" );
		col4.setWidth( 50 );

		TableColumn col5 = new TableColumn( tablaPagos, SWT.NONE );
		col5.setText( "Monto" );
		col5.setWidth( 100 );

		for ( JbPagoExtra pagoExterno : externoService.obtenerPagosExtraPorRx( rx ) ) {
			Banco banco = externoService.obtenerBancoPorId( pagoExterno.getIdBancoEmi() );
			Terminal terminal = externoService.obtenerTerminalPorId( pagoExterno.getIdTerm() );
			TableItem item = new TableItem( tablaPagos, SWT.NONE );
			item.setText( 0, StringUtils.defaultIfBlank( pagoExterno.getIdFormaPago(), "" ) );
			item.setText( 1, StringUtils.defaultIfBlank( banco != null ? banco.getDescripcion() : "", "" ) );
			item.setText( 2, StringUtils.defaultIfBlank( terminal != null ? terminal.getDescripcion() : "", "" ) );
			item.setText( 3, StringUtils.defaultIfBlank( pagoExterno.getIdPlan(), "" ) );
			item.setText( 4, StringUtils.defaultIfBlank( pagoExterno.getMontoFormateado(), "" ) );
		}
	}

	private void crearPestanyaSeguroConfianza( final TabFolder pestanyas ) {

		TabItem pestanyaSeguroConfianza = new TabItem( pestanyas, SWT.NONE );
		pestanyaSeguroConfianza.setText( "Seguro confianza" );

		Composite c2 = new Composite( pestanyas, SWT.NONE );
		pestanyaSeguroConfianza.setControl( c2 );
		// c2.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout gl = new GridLayout( 2, false );
		gl.marginWidth = 10;
		gl.marginHeight = 10;
		gl.horizontalSpacing = 15;
		c2.setLayout( gl );
		RowData rd = new RowData();
		rd.width = 580;
		c2.setLayoutData( rd );

		Label labelPoliza = new Label( c2, SWT.NONE );
		labelPoliza.setText( "Poliza" );
		// labelPoliza.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = 150;
		labelPoliza.setLayoutData( gd1 );

		Text textoPoliza = new Text( c2, SWT.BORDER );
		textoPoliza.setEditable( false );
		textoPoliza.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = 250;
		textoPoliza.setLayoutData( gd2 );

		Label labelFacturaSeguro = new Label( c2, SWT.NONE );
		labelFacturaSeguro.setText( "Factura Seguro" );
		// labelFacturaSeguro.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text textoFacturaSeguro = new Text( c2, SWT.BORDER );
		textoFacturaSeguro.setEditable( false );
		textoFacturaSeguro.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		GridData gd3 = new GridData();
		gd3.widthHint = 250;
		textoFacturaSeguro.setLayoutData( gd3 );

		Label labelFechaSeguro = new Label( c2, SWT.NONE );
		labelFechaSeguro.setText( "Fecha" );

		Text textoFechaSeguro = new Text( c2, SWT.BORDER );
		textoFechaSeguro.setEditable( false );
		textoFechaSeguro.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		GridData gd4 = new GridData();
		gd4.widthHint = 100;
		textoFechaSeguro.setLayoutData( gd4 );

		try {
			JbExterno externo = externoService.obtenerExternoPorRx( rx );
			Sucursal sucursal = trabajoService.obtenerSucursalPorNombre( externo.getOrigen() );

			java.util.List<Polizas> polizas = trabajoService.findPolizasByFacturaVenta( externo.getFactura().replace( "X", "" ) );
			for ( Polizas poliza : polizas ) {
				if ( poliza.getIdSucursal().equals( sucursal.getIdSucursal() ) ) {
					textoPoliza.setText( poliza.getNoPoliza().toString() );
					textoFacturaSeguro.setText( poliza.getFactura() );
					textoFechaSeguro.setText( ApplicationUtils.formatearFecha( poliza.getFechaVenta() ) );
					break;
				}
			}

		} catch ( ApplicationException e ) {
			log.error( "Error al cargar seguro confianza" );
		}
	}

	@Override
	protected void createButtonsForButtonBar( final Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Button buttonRegresar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		Rectangle rec1 = buttonRegresar.getBounds();
		buttonRegresar.setBounds( rec1.x, rec1.y, rec1.height, rec1.y + 40 );
		buttonRegresar.setImage( ControlTrabajosWindowElements.aceptarIcon );
		buttonRegresar.setText( "Regresar" );
		buttonRegresar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		buttonRegresar.addSelectionListener( new BotonRegresarListener( shell ) );

		Button buttonCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		Rectangle rec2 = buttonCerrar.getBounds();
		buttonCerrar.setBounds( rec2.x, rec2.y, rec2.height, rec2.y + 40 );
		buttonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		buttonCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
		buttonCerrar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
	}

	class BotonRegresarListener implements SelectionListener {

		private Shell shell;
		
		BotonRegresarListener( Shell shell ) {
			super();
			this.shell = shell;
		}
		
		@Override
		public void widgetSelected( SelectionEvent arg0 ) {
			String[] etiquetasBotones = new String[]{ MessagesPropertyHelper.getProperty( "generic.si" ), MessagesPropertyHelper.getProperty( "generic.no" ) };
			Boolean ok = MessageDialog.openConfirm( shell, "Regresar", "¿ Confirma que desea regresar el trabajo a la Sucursal Origen ?" );
			if ( ok ) {
				try {
					externoService.regresarExternoSucursalOrigen( rx );
				} catch ( Exception e ) {
					log.error( "Error al regresar el trabajo externo: " + e.getMessage() );
					MessageDialog.openError( shell, "Error", "Se ha producido un error al regresar el trabajo a la Sucursal de Origen" );
				}
			}
		}

		@Override
		public void widgetDefaultSelected( SelectionEvent arg0 ) {
		}
	}
}

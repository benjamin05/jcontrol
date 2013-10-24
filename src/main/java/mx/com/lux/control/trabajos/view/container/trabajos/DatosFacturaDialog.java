package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.TableListener;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import java.text.SimpleDateFormat;
import java.util.List;

public class DatosFacturaDialog extends Dialog {
	private Text txtFecha;
	private Text txtTicket;
	private Text txtSOI;
	private Text txtConvenio;
	private Text txtObservaciones;
	private Text txtTotal;
	private Text txtFacturaFiscal;
	private Text txtDescuento;
	private Text txtSaldo;
	private Text txtPoliza;
	private Text txtFacturaSeguro;
	private Text txtFechaSeguro;
	private Shell shell;

	private static final TrabajoService trabajoService;
	private Table tablePagos;
	private Table tableDetalle;

	static {
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
	}

	public DatosFacturaDialog( Shell parentShell ) {
		super( parentShell );
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		this.shell = container.getShell();
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group group = new Group( container, SWT.SHADOW_ETCHED_IN );
		group.setLayout( null );
		GridData gd_group = new GridData( SWT.LEFT, SWT.CENTER, false, false, 1, 1 );
		gd_group.heightHint = 193;
		gd_group.widthHint = 589;
		group.setLayoutData( gd_group );
		group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblFecha = new Label( group, SWT.NONE );
		lblFecha.setBounds( 8, 23, 31, 15 );
		lblFecha.setText( "Fecha" );
		lblFecha.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtFecha = new Text( group, SWT.BORDER );
		txtFecha.setBounds( 113, 20, 161, 21 );
		txtFecha.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtFecha.setEditable( false );

		Label lblNewLabel = new Label( group, SWT.NONE );
		lblNewLabel.setBounds( 306, 23, 55, 15 );
		lblNewLabel.setText( "Ticket" );
		lblNewLabel.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblNewLabel_1 = new Label( group, SWT.NONE );
		lblNewLabel_1.setBounds( 10, 58, 55, 15 );
		lblNewLabel_1.setText( "SOI" );
		lblNewLabel_1.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblFacturaFiscal = new Label( group, SWT.NONE );
		lblFacturaFiscal.setBounds( 306, 58, 77, 15 );
		lblFacturaFiscal.setText( "Factura Fiscal" );
		lblFacturaFiscal.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblConvenio = new Label( group, SWT.NONE );
		lblConvenio.setBounds( 8, 95, 55, 15 );
		lblConvenio.setText( "Convenio" );
		lblConvenio.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblDescuento = new Label( group, SWT.NONE );
		lblDescuento.setBounds( 306, 95, 77, 15 );
		lblDescuento.setText( "% Descuento" );
		lblDescuento.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblObservaciones = new Label( group, SWT.NONE );
		lblObservaciones.setBounds( 8, 133, 87, 15 );
		lblObservaciones.setText( "Observaciones" );
		lblObservaciones.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblSaldo = new Label( group, SWT.NONE );
		lblSaldo.setBounds( 306, 133, 55, 15 );
		lblSaldo.setText( "Saldo" );
		lblSaldo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblTotal = new Label( group, SWT.NONE );
		lblTotal.setBounds( 10, 172, 55, 15 );
		lblTotal.setText( "Total" );
		lblTotal.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtTicket = new Text( group, SWT.BORDER );
		txtTicket.setBounds( 408, 23, 161, 21 );
		txtTicket.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtTicket.setEditable( false );

		txtSOI = new Text( group, SWT.BORDER );
		txtSOI.setBounds( 113, 58, 161, 21 );
		txtSOI.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtSOI.setEditable( false );

		txtConvenio = new Text( group, SWT.BORDER );
		txtConvenio.setBounds( 113, 95, 161, 21 );
		txtConvenio.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtConvenio.setEditable( false );

		txtObservaciones = new Text( group, SWT.BORDER );
		txtObservaciones.setBounds( 113, 133, 161, 21 );
		txtObservaciones.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtObservaciones.setEditable( false );

		txtTotal = new Text( group, SWT.BORDER );
		txtTotal.setBounds( 113, 172, 161, 21 );
		txtTotal.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtTotal.setEditable( false );

		txtFacturaFiscal = new Text( group, SWT.BORDER );
		txtFacturaFiscal.setBounds( 408, 58, 161, 21 );
		txtFacturaFiscal.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtFacturaFiscal.setEditable( false );

		txtDescuento = new Text( group, SWT.BORDER );
		txtDescuento.setBounds( 408, 95, 161, 21 );
		txtDescuento.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtDescuento.setEditable( false );

		txtSaldo = new Text( group, SWT.BORDER );
		txtSaldo.setBounds( 408, 133, 161, 21 );
		txtSaldo.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtSaldo.setEditable( false );
		new Label( container, SWT.NONE );

		TabFolder tabFolder = new TabFolder( container, SWT.NONE );

		GridData gd_tabFolder = new GridData( SWT.LEFT, SWT.CENTER, false, false, 1, 1 );
		gd_tabFolder.heightHint = 140;
		gd_tabFolder.widthHint = 596;
		tabFolder.setLayoutData( gd_tabFolder );
		tabFolder.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		TabItem tbtmDetalle = new TabItem( tabFolder, SWT.NONE );
		tbtmDetalle.setText( "Detalle" );

		Composite composite = new Composite( tabFolder, SWT.NONE );
		tbtmDetalle.setControl( composite );
		composite.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		tableDetalle = new Table( composite, SWT.BORDER | SWT.FULL_SELECTION );
		tableDetalle.setBounds( 24, 26, 546, 92 );
		tableDetalle.setHeaderVisible( true );
		tableDetalle.setLinesVisible( true );
		tableDetalle.addListener( SWT.PaintItem, new TableListener() );

		TableColumn tblclmnNewColumn_2 = new TableColumn( tableDetalle, SWT.NONE );
		tblclmnNewColumn_2.setWidth( 141 );
		tblclmnNewColumn_2.setText( "Articulo" );

		TableColumn tblclmnTipo = new TableColumn( tableDetalle, SWT.NONE );
		tblclmnTipo.setWidth( 100 );
		tblclmnTipo.setText( "Tipo" );

		TableColumn tblclmnSurte = new TableColumn( tableDetalle, SWT.NONE );
		tblclmnSurte.setWidth( 100 );
		tblclmnSurte.setText( "Surte" );

		TableColumn tblclmnCantidad = new TableColumn( tableDetalle, SWT.NONE );
		tblclmnCantidad.setWidth( 100 );
		tblclmnCantidad.setText( "Cantidad" );

		TableColumn tblclmnTotal = new TableColumn( tableDetalle, SWT.NONE );
		tblclmnTotal.setWidth( 100 );
		tblclmnTotal.setText( "Total" );

		TabItem tbtmPagos = new TabItem( tabFolder, SWT.NONE );
		tbtmPagos.setText( "Pagos" );

		Composite composite_1 = new Composite( tabFolder, SWT.NONE );
		tbtmPagos.setControl( composite_1 );
		composite_1.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		tablePagos = new Table( composite_1, SWT.BORDER | SWT.FULL_SELECTION );
		tablePagos.setBounds( 22, 24, 546, 91 );
		tablePagos.setHeaderVisible( true );
		tablePagos.setLinesVisible( true );
		tablePagos.addListener( SWT.PaintItem, new TableListener() );

		TableColumn tblclmnNewColumn = new TableColumn( tablePagos, SWT.NONE );
		tblclmnNewColumn.setWidth( 274 );
		tblclmnNewColumn.setText( "Forma Pago" );

		TableColumn tblclmnNewColumn_1 = new TableColumn( tablePagos, SWT.NONE );
		tblclmnNewColumn_1.setWidth( 268 );
		tblclmnNewColumn_1.setText( "Importe" );

		TabItem tbtmSeguroConfianza = new TabItem( tabFolder, SWT.NONE );
		tbtmSeguroConfianza.setText( "Seguro Confianza" );

		Composite composite_2 = new Composite( tabFolder, SWT.NONE );
		tbtmSeguroConfianza.setControl( composite_2 );
		composite_2.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblPoliza = new Label( composite_2, SWT.NONE );
		lblPoliza.setText( "Poliza" );
		lblPoliza.setBounds( 10, 24, 55, 15 );
		lblPoliza.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtPoliza = new Text( composite_2, SWT.BORDER );
		txtPoliza.setEditable( false );
		txtPoliza.setBackground( SWTResourceManager.getColor( 255, 255, 204 ) );
		txtPoliza.setBounds( 110, 21, 160, 21 );

		Label lblFacturaSeguro = new Label( composite_2, SWT.NONE );
		lblFacturaSeguro.setText( "Factura Seguro" );
		lblFacturaSeguro.setBounds( 10, 65, 94, 15 );
		lblFacturaSeguro.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtFacturaSeguro = new Text( composite_2, SWT.BORDER );
		txtFacturaSeguro.setEditable( false );
		txtFacturaSeguro.setBackground( SWTResourceManager.getColor( 255, 255, 204 ) );
		txtFacturaSeguro.setBounds( 110, 62, 160, 21 );

		Label lblFechaSeguro = new Label( composite_2, SWT.NONE );
		lblFechaSeguro.setBounds( 10, 104, 55, 15 );
		lblFechaSeguro.setText( "Fecha" );
		lblFechaSeguro.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtFechaSeguro = new Text( composite_2, SWT.BORDER );
		txtFechaSeguro.setEditable( false );
		txtFechaSeguro.setBackground( SWTResourceManager.getColor( 255, 255, 204 ) );
		txtFechaSeguro.setBounds( 110, 101, 160, 21 );

		String rx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );

		if ( rx != null ) {
			try {
				// Encabezado
				NotaVenta nv = trabajoService.findNotaVentaByFactura( rx );
				Sucursal sucursal = ( (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED ) ).getSucursal();
				if ( nv != null ) {
					SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
					txtFecha.setText( sdf.format( nv.getFechaHoraFactura() ) );
					txtTicket.setText( sucursal.getCentroCostos() + " - " + nv.getFactura() );
					txtSOI.setText( nv.getIdFactura() );
					InstitucionIc institucion = trabajoService.findInstitucionIcByIdConvenio( nv.getIdConvenio().trim() );
					if ( institucion != null )
						txtConvenio.setText( institucion.getInicialesIc() );
					NotaFactura nf = trabajoService.findMaxNotaFacturaByFactura( nv.getFactura() );
					if ( nf != null )
						txtFacturaFiscal.setText( nf.getIdFiscal() );
					txtDescuento.setText( nv.getPor100Descuento().toString() );
					txtObservaciones.setText( nv.getObservacionesNv() );
					txtSaldo.setText( nv.getVentaNeta().subtract( nv.getSumaPagos() ).toString() );
					txtTotal.setText( nv.getVentaNeta().toString() );
					List<DetalleNotaVenta> detalles = trabajoService.findAllDetalleNotaVenByIdArt( nv.getIdFactura() );
					for ( DetalleNotaVenta detalle : detalles ) {
						Articulos articulos = trabajoService.findArticulosByArt( detalle.getIdArticulo() );
						TableItem item = new TableItem( tableDetalle, SWT.NONE );
						if ( articulos != null ) {
							String s = articulos.getArticulo().trim() + " - ";
							if ( articulos.getColorCode() != null )
								s += articulos.getColorCode();
							item.setText( 0, s );
						}
						item.setText( 1, detalle.getIdTipoDetalle() );
						item.setText( 2, detalle.getSurte() != null ? detalle.getSurte().toString() : "" );
						item.setText( 3, detalle.getCantidadFac() != null ? detalle.getCantidadFac().toString() : "" );
						Double precioUnit = ApplicationUtils.moneyToDouble( detalle.getPrecioUnitFinal() );
						item.setText( 4, Double.toString( precioUnit * detalle.getCantidadFac() ) );
					}
					// Pagos
					List<Pagos> pagos = trabajoService.findPagosByIdFactura( nv.getIdFactura() );
					for ( Pagos pago : pagos ) {
						TableItem item = new TableItem( tablePagos, SWT.NONE );
						item.setText( 0, pago.getIdFPago() );
						item.setText( 1, pago.getMontoPago() );
					}
					// Seguro Confianza
					List<Polizas> polizas = trabajoService.findPolizasByFacturaVenta( nv.getFactura() );
					for ( Polizas poliza : polizas ) {
						txtPoliza.setText( poliza.getNoPoliza() != null ? poliza.getNoPoliza().toString() : "" );
						txtFacturaSeguro.setText( poliza.getFactura() );
						txtFechaSeguro.setText( sdf.format( poliza.getFechaVenta() ) );
					}
				}
			} catch ( ApplicationException ae ) {
				ae.printStackTrace();
			} catch ( NumberFormatException nfe ) {
				MessageDialog.openError( shell, "Error", "Formato n\u00famerico incorrecto" );
			}
		}

		return container;
	}

	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Button buttonCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		Rectangle rec = buttonCerrar.getBounds();
		buttonCerrar.setBounds( rec.x, rec.y, rec.height, rec.y + 40 );
		buttonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		buttonCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
		buttonCerrar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 629, 515 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.datosfactura" ) );
	}
}

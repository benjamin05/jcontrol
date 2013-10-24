package mx.com.lux.control.trabajos.view.container.garantia;

import mx.com.lux.control.trabajos.bussiness.service.GarantiaService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.Generico;
import mx.com.lux.control.trabajos.data.vo.NotaVenta;
import mx.com.lux.control.trabajos.data.vo.Sucursal;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.render.GenericoRender;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.Map;

public class NuevaGarantiaDialog extends Dialog {

	private Shell shell;

	private final GarantiaService garantiaService;

	private final TrabajoService trabajoService;

	private ComboViewer comboTipoGarantiaViewer;
	private Combo comboTipoGarantia;
	private Text textoTicket1;
	private Text textoTicket2;
	private Text textoProducto;
	private Text textoColor;

	public NuevaGarantiaDialog( Shell parentShell ) {
		super( parentShell );
		shell = parentShell;
		garantiaService = ServiceLocator.getBean( "garantiaService", GarantiaService.class );
		trabajoService = ServiceLocator.getBean( "trabajoService", TrabajoService.class );
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 370, 230 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( "Nueva Garantía" );
		shell = newShell;
	}

	@Override
	protected Control createContents( Composite parent ) {
		Composite contenedor = crearContenedorPrincipal( parent );
		crearCampos( contenedor );
		return contenedor;
	}

	private Composite crearContenedorPrincipal( final Composite parent ) {
		Composite contenedor = new Composite( parent, SWT.NONE );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout layout = new GridLayout( 2, false );
		layout.marginWidth = 10;
		layout.marginHeight = 10;
		layout.horizontalSpacing = 15;
		contenedor.setLayout( layout );
		GridData gd = new GridData();
		gd.widthHint = this.getInitialSize().x;
		gd.heightHint = this.getInitialSize().y;
		contenedor.setLayoutData( gd );
		return contenedor;
	}

	private void crearCampos( Composite contenedor ) {

		VerifyListener textoNumerico = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = StringUtils.isNumeric( e.text ) ? e.text : "";
			}
		};

		VerifyListener textoMayusculas = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		String rx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
		Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
		// Jb trabajo = trabajoService.findById( rx );
		NotaVenta notaVenta = trabajoService.findNotaVentaByFactura( rx );
		Sucursal sucursal = empleado.getSucursal();
		Integer centroCostos = StringUtils.isNotBlank( sucursal.getCentroCostos() ) ? Integer.parseInt( sucursal.getCentroCostos().trim() ) : 0;

		crearTipoGarantia( contenedor );
		crearTicket( contenedor, textoNumerico, centroCostos, notaVenta );
		crearCampoProducto( contenedor, textoMayusculas );
		crearColor( contenedor, textoMayusculas );
		crearBotones( contenedor );
	}

	private void crearTipoGarantia( Composite contenedor ) {
		// Tipo Garantia
		Label etiquetaTipoGarantia = new Label( contenedor, SWT.NONE );
		etiquetaTipoGarantia.setText( "Tipo Garantía" );
		etiquetaTipoGarantia.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = 90;
		etiquetaTipoGarantia.setLayoutData( gd1 );

		comboTipoGarantiaViewer = new ComboViewer( contenedor, SWT.READ_ONLY );
		comboTipoGarantiaViewer.setContentProvider( new ArrayContentProvider() );
		comboTipoGarantiaViewer.setLabelProvider( new GenericoRender() );
		comboTipoGarantiaViewer.setInput( garantiaService.obtenerGenericosGarantias() );
		comboTipoGarantia = comboTipoGarantiaViewer.getCombo();
		comboTipoGarantia.setEnabled( true );
		GridData gd2 = new GridData();
		gd2.widthHint = 240;
		comboTipoGarantia.setLayoutData( gd2 );

		comboTipoGarantia.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				int indiceTipoGarantia = comboTipoGarantia.getSelectionIndex();
				Generico generico = (Generico) comboTipoGarantiaViewer.getElementAt( indiceTipoGarantia );
				if ( generico.getId().equals( 'A' ) ) {
					textoProducto.setEnabled( true );
					textoColor.setEnabled( true );
				} else {
					textoProducto.setEnabled( false );
					textoProducto.setText( "" );
					textoColor.setEnabled( false );
					textoColor.setText( "" );
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {

			}
		} );
	}

	private void crearTicket( Composite contenedor, VerifyListener textoNumerico, Integer centroCostos, NotaVenta notaVenta ) {
		Label etiquetaTicket = new Label( contenedor, SWT.NONE );
		etiquetaTicket.setText( "Ticket" );
		etiquetaTicket.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd3 = new GridData();
		gd3.widthHint = 40;
		etiquetaTicket.setLayoutData( gd3 );

		Composite ticket = new Composite( contenedor, SWT.NONE );
		ticket.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd4 = new GridData();
		gd4.widthHint = 180;
		ticket.setLayoutData( gd4 );
		GridLayout layout = new GridLayout( 3, false );
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.horizontalSpacing = 0;
		ticket.setLayout( layout );

		textoTicket1 = new Text( ticket, SWT.BORDER | SWT.CENTER );
		textoTicket1.setTextLimit( 4 );
		GridData gd5 = new GridData();
		gd5.widthHint = 50;
		textoTicket1.setLayoutData( gd5 );
		textoTicket1.addVerifyListener( textoNumerico );
		textoTicket1.setText( String.format( "%04d", centroCostos ) );

		Label etiquetaGuion = new Label( ticket, SWT.NONE );
		etiquetaGuion.setText( " - " );
		etiquetaGuion.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd7 = new GridData();
		gd7.widthHint = 15;
		etiquetaGuion.setLayoutData( gd7 );

		textoTicket2 = new Text( ticket, SWT.BORDER | SWT.CENTER );
		textoTicket2.setTextLimit( 8 );
		GridData gd6 = new GridData();
		gd6.widthHint = 90;
		textoTicket2.setLayoutData( gd6 );
		textoTicket2.addVerifyListener( textoNumerico );
		textoTicket2.setText( notaVenta != null ? notaVenta.getFactura() : "" );
	}

	private void crearCampoProducto( final Composite contenedor, final VerifyListener textoMayusculas ) {

		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Producto" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = 90;
		etiqueta.setLayoutData( gd1 );

		textoProducto = new Text( contenedor, SWT.BORDER );
		GridData gd2 = new GridData();
		gd2.widthHint = 230;
		textoProducto.setLayoutData( gd2 );
		textoProducto.addVerifyListener( textoMayusculas );
		textoProducto.setEnabled( false );
	}

	private void crearColor( final Composite contenedor, final VerifyListener textoMayusculas ) {

		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Color" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd13 = new GridData();
		gd13.widthHint = 90;
		etiqueta.setLayoutData( gd13 );

		textoColor = new Text( contenedor, SWT.BORDER );
		GridData gd14 = new GridData();
		gd14.widthHint = 230;
		textoColor.setLayoutData( gd14 );
		textoColor.addVerifyListener( textoMayusculas );
		textoColor.setEnabled( false );
	}

	private void crearBotones( Composite contenedor ) {

		Composite botones = new Composite( contenedor, SWT.RIGHT );
		botones.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd10 = new GridData();
		gd10.widthHint = 200;
		gd10.horizontalSpan = 2;
		gd10.horizontalIndent = 155;
		botones.setLayoutData( gd10 );
		GridLayout layout2 = new GridLayout( 2, false );
		layout2.marginWidth = 0;
		layout2.marginHeight = 10;
		layout2.horizontalSpacing = 10;
		botones.setLayout( layout2 );

		Button botonCerrar = new Button( botones, SWT.NONE );
		botonCerrar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		botonCerrar.setText( "Cerrar" );
		botonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		GridData gd9 = new GridData();
		gd9.widthHint = 90;
		botonCerrar.setLayoutData( gd9 );
		botonCerrar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				shell.close();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );

		Button botonValidar = new Button( botones, SWT.NONE );
		botonValidar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		botonValidar.setText( "Validar" );
		botonValidar.setImage( ControlTrabajosWindowElements.aceptarIcon );
		GridData gd8 = new GridData();
		gd8.widthHint = 90;
		botonValidar.setLayoutData( gd8 );
		botonValidar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				if ( sonDatosValidos() ) {
					Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
					int indiceTipoGarantia = comboTipoGarantia.getSelectionIndex();
					Generico generico = (Generico) comboTipoGarantiaViewer.getElementAt( indiceTipoGarantia );
					Map<GarantiaService.ResultadoValidacion, String> resultadoValidacion = garantiaService.validarGarantia( generico, textoTicket2.getText(), textoTicket1.getText(), textoProducto.getText(), textoColor.getText(), Integer.toString( empleado.getSucursal().getIdSucursal() ), empleado.getIdEmpleado().trim() );
					if ( resultadoValidacion.containsKey( GarantiaService.ResultadoValidacion.RESULTADO_OK ) ) {
						String rx = textoTicket2.getText();
                        System.out.println( rx );
						Sucursal sucursal = garantiaService.obtenerSucursalPorCentroCostos( textoTicket1.getText() );
						AltaGarantiaDialog dialogo = new AltaGarantiaDialog( shell, rx, generico, resultadoValidacion.get( GarantiaService.ResultadoValidacion.PRODUCTO ), resultadoValidacion.get( GarantiaService.ResultadoValidacion.COLOR ), resultadoValidacion.get( GarantiaService.ResultadoValidacion.ID_CLIENTE ), textoTicket2.getText(), sucursal.idSucursal(), empleado.getSucursal().idSucursal() );
						dialogo.open();
					} else {
						MessageDialog.openError( shell, "Error", resultadoValidacion.get( GarantiaService.ResultadoValidacion.ERROR ) );
					}
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );
	}

	private Boolean sonDatosValidos() {
		if ( comboTipoGarantia.getSelectionIndex() < 0 ) {
			comboTipoGarantia.setFocus();
			MessageDialog.openError( shell, "Error", "Debe seleccionar un tipo de Garantía" );
			return Boolean.FALSE;
		}
		if ( StringUtils.isBlank( textoTicket1.getText() ) ) {
			textoTicket1.setFocus();
			MessageDialog.openError( shell, "Error", "Debe rellenar el primer campo del Ticket ( Cento de Costos ) correctamente" );
			return Boolean.FALSE;
		} else if ( !garantiaService.existeCentroCostos( textoTicket1.getText().trim() ) ) {
			textoTicket1.setFocus();
			MessageDialog.openError( shell, "Error", "No existe el Centro de Costos " + textoTicket1.getText().trim() );
			return Boolean.FALSE;
		}
		if ( StringUtils.isBlank( textoTicket2.getText() ) ) {
			textoTicket2.setFocus();
			MessageDialog.openError( shell, "Error", "Debe rellenar el segundo campo Ticket ( Factura ) correctamente" );
			return Boolean.FALSE;
		}
		/*
		else if ( !garantiaService.existeFactura( textoTicket2.getText().trim() ) ) {
			textoTicket2.setFocus();
			MessageDialog.openError( shell, "Error", "No existe el trabajo con Factura " + textoTicket2.getText().trim() + " o no se encuentra en el estado Entregado" );
			return Boolean.FALSE;
		}
		*/
		if ( textoProducto.getEnabled() && StringUtils.isBlank( textoProducto.getText() ) ) {
			textoProducto.setFocus();
			MessageDialog.openError( shell, "Error", "Debe rellenar el campo Producto correctamente" );
			return Boolean.FALSE;
		}
		if ( textoColor.getEnabled() && StringUtils.isBlank( textoColor.getText() ) ) {
			textoColor.setFocus();
			MessageDialog.openError( shell, "Error", "Debe rellenar el campo Color correctamente" );
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

}

package mx.com.lux.control.trabajos.view.container.garantia;

import mx.com.lux.control.trabajos.bussiness.service.GarantiaService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.ClienteService;
import mx.com.lux.control.trabajos.data.vo.Cliente;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.Generico;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class AltaGarantiaDialog extends Dialog {

	private final Logger log = LoggerFactory.getLogger( AltaGarantiaDialog.class );

	private Shell shell;
	private final String rx;
	private final Generico generico;
	private final String producto;
	private final String color;
	private final String factura;
	private final String idSucursal;
	private final String idSucursalLocal;
	private Cliente cliente;
	private final Empleado empleado;

	private Text textoDejo;
	private Text textoDanyo;
	private Text textoCondiciones;
	private DateTime fechaPromesa;

	private final GarantiaService garantiaService;
	private final ClienteService clienteService;

	private final VerifyListener textoMayusculasSinAcentos;

	protected AltaGarantiaDialog( final Shell parentShell, final String rx, final Generico generico, final String producto, final String color, final String idCliente, final String factura, final String idSucursal, final String idSucursalLocal ) {
		super( parentShell );
		shell = parentShell;
		this.rx = rx;
		this.generico = generico;
		this.producto = producto;
		this.color = color;
		this.factura = factura;
		this.idSucursal = idSucursal;
		this.idSucursalLocal = idSucursalLocal;
		empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );

		garantiaService = ServiceLocator.getBean( GarantiaService.class );
		clienteService = ServiceLocator.getBean( ClienteService.class );

		try {
			if( idSucursal.equals( idSucursalLocal ) ) {
				cliente = clienteService.findClienteById( Integer.parseInt( idCliente ) );
			} else {
				cliente = clienteService.importarClienteExterno( idSucursal, idCliente );
			}
		} catch( Exception e ) {
			log.error( "Error al obtener los datos del Cliente: " + idCliente );
			log.error( "Excepcion: " + e.getMessage() );
			if( idSucursal.equals( idSucursalLocal ) ) {
				MessageDialog.openError( shell, "Error", "Error al cargar los datos de cliente: " + idCliente );
			} else {
				MessageDialog.openError( shell, "Error", "Error al cargar los datos de cliente externo: " + idCliente );
			}
			close();
		}

		textoMayusculasSinAcentos = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
				e.text = ApplicationUtils.quitarAcentos( e.text );
			}
		};
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 400, 350 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( "Alta de Garantía" );
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

	private void crearCampos( final Composite contenedor ) {

		VerifyListener textoNumerico = new VerifyListener() {
			public void verifyText( final VerifyEvent e ) {
				e.text = StringUtils.isNumeric( e.text ) ? e.text : "";
			}
		};

		VerifyListener textoMayusculas = new VerifyListener() {
			public void verifyText( final VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		crearCampoProducto( contenedor );
		crearCampoCliente( contenedor );
		crearCampoDejo( contenedor, textoMayusculas );
		crearCampoDanyo( contenedor, textoMayusculas );
		crearCampoCondiciones( contenedor, textoMayusculas );
		crearCampoFechaPromesa( contenedor );
		crearBotones( contenedor );
	}

	private void crearCampoProducto( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Producto" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = 90;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER | SWT.READ_ONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = 250;
		texto.setLayoutData( gd2 );
		texto.setText( producto );
		texto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
	}

	private void crearCampoCliente( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Cliente" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = 90;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER | SWT.READ_ONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = 250;
		texto.setLayoutData( gd2 );
		texto.setText( cliente != null ? cliente.getNombreCompleto( false ) : "ERROR AL CARGAR CLIENTE" );
		texto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
	}

	private void crearCampoDejo( final Composite contenedor, final VerifyListener textoMayusculas ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Dejó" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = 90;
		etiqueta.setLayoutData( gd1 );

		textoDejo = new Text( contenedor, SWT.BORDER );
		GridData gd2 = new GridData();
		gd2.widthHint = 250;
		textoDejo.setLayoutData( gd2 );
		textoDejo.addVerifyListener( textoMayusculasSinAcentos );
	}

	private void crearCampoDanyo( final Composite contenedor, final VerifyListener textoMayusculas ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Daño" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = 90;
		etiqueta.setLayoutData( gd1 );

		textoDanyo = new Text( contenedor, SWT.BORDER );
		GridData gd2 = new GridData();
		gd2.widthHint = 250;
		textoDanyo.setLayoutData( gd2 );
		textoDanyo.addVerifyListener( textoMayusculasSinAcentos );
	}

	private void crearCampoCondiciones( final Composite contenedor, final VerifyListener textoMayusculas ) {
		Label etiqueta = new Label( contenedor, SWT.WRAP );
		etiqueta.setText( "Condiciones generales" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = 90;
		etiqueta.setLayoutData( gd1 );

		textoCondiciones = new Text( contenedor, SWT.BORDER | SWT.V_SCROLL | SWT.WRAP );
		GridData gd2 = new GridData();
		gd2.widthHint = 235;
		gd2.heightHint = 80;
		textoCondiciones.setLayoutData( gd2 );
		textoCondiciones.addVerifyListener( textoMayusculasSinAcentos );
	}

	private void crearCampoFechaPromesa( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Fecha promesa" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = 100;
		etiqueta.setLayoutData( gd1 );

		fechaPromesa = new DateTime( contenedor, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN );
		GridData gd3 = new GridData();
		gd3.widthHint = 110;
		fechaPromesa.setLayoutData( gd3 );
	}

	private void crearBotones( Composite contenedor ) {

		Composite botones = new Composite( contenedor, SWT.NONE );
		botones.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd10 = new GridData();
		gd10.widthHint = 220;
		gd10.horizontalSpan = 2;
		gd10.horizontalIndent = 160;
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
		gd9.widthHint = 100;
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

		Button botonGuardar = new Button( botones, SWT.NONE );
		botonGuardar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		botonGuardar.setText( "Guardar" );
		botonGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		GridData gd8 = new GridData();
		gd8.widthHint = 100;
		botonGuardar.setLayoutData( gd8 );
		botonGuardar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
                Map<GarantiaService.ResultadoAlta, String> resultadoAlta = new LinkedHashMap<GarantiaService.ResultadoAlta, String>();
					if( sonDatosValidos() ) {
                        try {
						resultadoAlta = garantiaService.altaGarantia( generico, factura, idSucursal, idSucursalLocal, empleado.getIdEmpleado(), producto, color, textoDejo.getText(), textoDanyo.getText(), textoCondiciones.getText(), dateTime2Date( fechaPromesa ) );
                        } catch ( Exception e ) {
                            log.error( "Error al procesar el Alta de Garantia: " + e.getMessage() );
                            MessageDialog.openError( shell, "Error", "Error al procesar el Alta de Garantia" );
                        }
                        if( resultadoAlta.containsKey( GarantiaService.ResultadoAlta.ALTA_OK ) ) {
                            ApplicationUtils.recargarDatosPestanyaVisible();
							String idGarantia = resultadoAlta.get( GarantiaService.ResultadoAlta.ID_GARANTIA );
							DatosClienteGarantiaDialog dialogo = new DatosClienteGarantiaDialog( shell, "G" + idGarantia );
                            dialogo.open();
						} else {
							MessageDialog.openError( shell, "Error", resultadoAlta.get( GarantiaService.ResultadoAlta.ERROR ) );
						}
					}

			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );
	}

	private Boolean sonDatosValidos() {
		if ( textoDejo.getEnabled() && StringUtils.isBlank( textoDejo.getText() ) ) {
			textoDejo.setFocus();
			MessageDialog.openError( shell, "Error", "Debe rellenar el campo Dejo correctamente" );
			return Boolean.FALSE;
		}
		if ( textoDanyo.getEnabled() && StringUtils.isBlank( textoDanyo.getText() ) ) {
			textoDanyo.setFocus();
			MessageDialog.openError( shell, "Error", "Debe rellenar el campo Daño correctamente" );
			return Boolean.FALSE;
		}
		if ( textoCondiciones.getEnabled() && StringUtils.isBlank( textoCondiciones.getText() ) ) {
			textoCondiciones.setFocus();
			MessageDialog.openError( shell, "Error", "Debe rellenar el campo Condiciones correctamente" );
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	private Date dateTime2Date( DateTime fecha ) {
		Calendar calendar = Calendar.getInstance();
		calendar.set( Calendar.YEAR, fecha.getYear() );
		calendar.set( Calendar.MONTH, fecha.getMonth() );
		calendar.set( Calendar.DAY_OF_MONTH, fecha.getDay() );
		return calendar.getTime();
	}
}

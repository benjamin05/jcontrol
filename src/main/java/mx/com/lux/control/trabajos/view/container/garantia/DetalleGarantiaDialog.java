package mx.com.lux.control.trabajos.view.container.garantia;

import mx.com.lux.control.trabajos.bussiness.service.GarantiaService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.ClienteService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.SucursalService;
import mx.com.lux.control.trabajos.data.vo.Cliente;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.JbGarantia;
import mx.com.lux.control.trabajos.data.vo.Sucursal;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class DetalleGarantiaDialog extends Dialog {

	private final Logger log = LoggerFactory.getLogger( DetalleGarantiaDialog.class );

	private Shell shell;
	private final String rx;
	private final Empleado empleado;
	private JbGarantia garantia;
	private Cliente cliente;
	private Sucursal sucursal;

	private final int anchoMuyCorto = 50;
	private final int anchoCorto = 110;
	private final int anchoMedio = 150;
	private final int anchoLargo = 200;
	private final int anchoMuyLargo = 290;

	private final GarantiaService garantiaService;
	private final ClienteService clienteService;
	private final SucursalService sucursalService;

	protected DetalleGarantiaDialog( final Shell parentShell, final String rx ) {
		super( parentShell );
		shell = parentShell;
		this.rx = rx;
		empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );

		garantiaService = ServiceLocator.getBean( GarantiaService.class );
		clienteService = ServiceLocator.getBean( ClienteService.class );
		sucursalService = ServiceLocator.getBean( SucursalService.class );
		try {
			garantia = garantiaService.obtenerGarantiaPorId( Integer.parseInt( StringUtils.remove( rx, "G" ) ) );
			sucursal = sucursalService.findById( Integer.parseInt( garantia.getSucursal().trim() ) );
			cliente = clienteService.findClienteById( Integer.parseInt( garantia.getIdCliente() ) );
		} catch ( Exception e ) {
			log.error( "Error al cargar los datos iniciales: " + e.getMessage() );
		}

	}

	@Override
	protected Point getInitialSize() {
		return new Point( 750, 320 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( "Detalle de la Garantía " + garantia.getId() );
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
		GridLayout layout = new GridLayout( 4, false );
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

		crearCampoFactura( contenedor );

		crearCampoId( contenedor );
		crearCampoCliente( contenedor );

		crearCampoTipo( contenedor );
		crearCampoSucursal( contenedor );

		crearCampoProducto( contenedor );
		crearCampoDejo( contenedor );

		crearCampoColor( contenedor );
		crearCampoDanyo( contenedor );

		crearCampoCondiciones( contenedor );

		crearCampoFecha( contenedor );
		crearCampoFechaPromesa( contenedor );

		crearBotones( contenedor );
	}

	private void crearCampoFactura( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Factura" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = anchoMuyCorto;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER | SWT.READ_ONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = anchoCorto;
		texto.setLayoutData( gd2 );
		texto.setText( garantia.getFactura() );
		texto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		Label espacioBlanco = new Label( contenedor, SWT.NONE );
		espacioBlanco.setText( "" );
		espacioBlanco.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd3 = new GridData();
		gd3.widthHint = 580;
		gd3.horizontalSpan = 2;
		espacioBlanco.setLayoutData( gd3 );
	}

	private void crearCampoId( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Id." );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = anchoMuyCorto;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER | SWT.READ_ONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = anchoCorto;
		texto.setLayoutData( gd2 );
		texto.setText( garantia.getId().toString() );
		texto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
	}

	private void crearCampoCliente( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Cliente" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = anchoCorto;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER | SWT.READ_ONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = anchoMuyLargo;
		texto.setLayoutData( gd2 );
		texto.setText( cliente.getNombreCompleto( false ) );
		texto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
	}

	private void crearCampoTipo( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Tipo" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = anchoCorto;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER | SWT.READ_ONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = anchoMedio;
		texto.setLayoutData( gd2 );
		texto.setText( garantia.getTipoGarantia() );
		texto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
	}

	private void crearCampoSucursal( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Sucursal" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = anchoCorto;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER | SWT.READ_ONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = anchoMuyLargo;
		texto.setLayoutData( gd2 );
		texto.setText( "[" + garantia.getSucursal().trim() + "] " + sucursal.getNombre() );
		texto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
	}

	private void crearCampoFecha( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Fecha" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = anchoCorto;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER | SWT.READ_ONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = anchoCorto;
		texto.setLayoutData( gd2 );
		texto.setText( ApplicationUtils.formatearFecha( garantia.getFecha() ) );
		texto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
	}

	private void crearCampoFechaPromesa( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Fecha promesa" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = anchoCorto;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER | SWT.READ_ONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = anchoCorto;
		texto.setLayoutData( gd2 );
		texto.setText( ApplicationUtils.formatearFecha( garantia.getFechaPromesa() ) );
		Date ahora = new Date();
		if ( ahora.after( garantia.getFechaPromesa() ) ) {
			texto.setBackground( GraphicConstants.FIELD_COLOR_ALERT );
		} else {
			texto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		}

	}

	private void crearCampoCondiciones( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Condiciones" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = anchoCorto;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER | SWT.READ_ONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = 590;
		gd2.horizontalSpan = 3;
		texto.setLayoutData( gd2 );
		texto.setText( garantia.getCondicion() );
		texto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
	}

	private void crearCampoProducto( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Producto" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = anchoCorto;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER | SWT.READ_ONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = anchoMedio;
		texto.setLayoutData( gd2 );
		texto.setText( garantia.getArmazon() );
		texto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
	}

	private void crearCampoDejo( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Dejó" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = anchoMuyCorto;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER | SWT.READ_ONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = anchoMuyLargo;
		texto.setLayoutData( gd2 );
		texto.setText( garantia.getDejo() );
		texto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
	}

	private void crearCampoColor( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Color" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = anchoCorto;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER | SWT.READ_ONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = anchoMedio;
		texto.setLayoutData( gd2 );
		texto.setText( garantia.getColor() );
		texto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
	}

	private void crearCampoDanyo( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Daño" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = anchoMuyCorto;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER | SWT.READ_ONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = anchoMuyLargo;
		texto.setLayoutData( gd2 );
		texto.setText( garantia.getRazon() );
		texto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
	}


	private void crearBotones( Composite contenedor ) {

		Composite botones = new Composite( contenedor, SWT.NONE );
		botones.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd10 = new GridData();
		gd10.widthHint = 220;
		gd10.horizontalSpan = 4;
		gd10.horizontalIndent = 620;
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
	}

}

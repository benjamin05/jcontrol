package mx.com.lux.control.trabajos.view.container.garantia;

import mx.com.lux.control.trabajos.bussiness.service.GarantiaService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
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

public class EntregarGarantiaDialog extends Dialog {

	private final Logger log = LoggerFactory.getLogger( EntregarGarantiaDialog.class );

	private Shell shell;

	private final GarantiaService garantiaService;

	private final TrabajoService trabajoService;

	private final String rx;

	private Text textoObservaciones;

	private final VerifyListener textoMayusculas;

	public EntregarGarantiaDialog( final Shell parentShell, final String rx ) {
		super( parentShell );
		shell = parentShell;
		garantiaService = ServiceLocator.getBean( GarantiaService.class );
		trabajoService = ServiceLocator.getBean( TrabajoService.class );
		this.rx = rx;
		textoMayusculas = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 370, 215 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( "Entregar Garantía" );
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
		crearCampoRx( contenedor );
		crearCampoObservaciones( contenedor );
		crearBotones( contenedor );
	}

	private void crearCampoRx( final Composite contenedor ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Rx" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = 90;
		etiqueta.setLayoutData( gd1 );

		Text textoRx = new Text( contenedor, SWT.BORDER | SWT.READ_ONLY );
		GridData gd2 = new GridData();
		gd2.widthHint = 210;
		textoRx.setLayoutData( gd2 );
		textoRx.setText( rx );
	}

	private void crearCampoObservaciones( final Composite contenedor ) {

		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "Observaciones" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = 100;
		etiqueta.setLayoutData( gd1 );

		textoObservaciones = new Text( contenedor, SWT.BORDER | SWT.V_SCROLL );
		GridData gd2 = new GridData();
		gd2.widthHint = 200;
		gd2.heightHint = 80;
		textoObservaciones.setLayoutData( gd2 );
		textoObservaciones.addVerifyListener( textoMayusculas );
	}

	private void crearBotones( Composite contenedor ) {

		Composite botones = new Composite( contenedor, SWT.NONE );
		botones.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd10 = new GridData();
		gd10.widthHint = 200;
		gd10.horizontalSpan = 2;
		gd10.horizontalIndent = 150;
		botones.setLayoutData( gd10 );
		GridLayout layout2 = new GridLayout( 2, false );
		layout2.marginWidth = 0;
		layout2.marginHeight = 10;
		layout2.horizontalSpacing = 10;
		botones.setLayout( layout2 );

		Button botonGuardar = new Button( botones, SWT.NONE );
		botonGuardar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		botonGuardar.setText( "Guardar" );
		botonGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		GridData gd8 = new GridData();
		gd8.widthHint = 90;
		botonGuardar.setLayoutData( gd8 );
		botonGuardar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				if ( sonDatosValidos() ) {
					Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
					try {
						if( garantiaService.entregarGarantia( rx, textoObservaciones.getText(), empleado.getIdEmpleado() ) ) {
							MessageDialog.openInformation( shell, "Entrega Garantia", "Se ha procesado la Garantía correctamente" );
							ApplicationUtils.recargarDatosPestanyaVisible();
							shell.close();
						} else {
							MessageDialog.openError( shell, "Error", "No se ha podido procesar la Garantía" );
						}
					} catch( ApplicationException e ) {
						log.error( "Error al procesar la Garantia: " + e.getMessage() );
						MessageDialog.openError( shell, "Error", "No se ha podido procesar la Garantía" );
					}

				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );

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
	}

	private Boolean sonDatosValidos() {
		if ( StringUtils.isBlank( textoObservaciones.getText() ) ) {
			textoObservaciones.setFocus();
			MessageDialog.openError( shell, "Error", "Debe rellenar el campo Observaciones correctamente" );
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

}

package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.recepcion.NoSatisfactorioDialog;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
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

public class BuscarTrabajoRotoDialog extends Dialog {

	private final Logger log = LoggerFactory.getLogger( BuscarTrabajoRotoDialog.class );
	private Shell shell;
	private Text textoRx;
	private final int numeroColumnas = 6;
	private final int anchoColumna = 50;
	private final int anchoEntreColumnas = 10;
	private final TrabajoService trabajoService;

	public BuscarTrabajoRotoDialog( Shell shell ) {
		super( shell );
		this.shell = shell;
		trabajoService = ServiceLocator.getBean( TrabajoService.class );
	}

	@Override
	protected Point getInitialSize() {
		return new Point( ancho( 4 ) + 40, 130 );
	}

	@Override
	protected void configureShell( Shell shell ) {
		super.configureShell( shell );
		shell.setText( "Buscar trabajo" );
		this.shell = shell;
	}

	@Override
	protected Control createContents( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite contenedor = crearContenedorPrincipal( parent );
		crearCampos( contenedor );
		crearBotones( contenedor );
		return parent;
	}

	private Composite crearContenedorPrincipal( final Composite parent ) {
		Composite contenedor = new Composite( parent, SWT.NONE );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout layout = new GridLayout( numeroColumnas, false );
		layout.marginWidth = 10;
		layout.marginHeight = 10;
		layout.horizontalSpacing = 0;
		contenedor.setLayout( layout );
		GridData gd = new GridData();
		gd.widthHint = this.getInitialSize().x;
		gd.heightHint = this.getInitialSize().y;
		contenedor.setLayoutData( gd );
		return contenedor;
	}

	private void crearCampos( Composite contenedor ) {
		textoRx = crearCampoTexto( contenedor, "Rx", "", 1, 3 );
	}

	private void crearBotones( Composite contenedor ) {
		Composite botones = new Composite( contenedor, SWT.NONE );
		botones.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd10 = new GridData();
		gd10.widthHint = numeroColumnas * anchoColumna;
		gd10.horizontalSpan = numeroColumnas;
		botones.setLayoutData( gd10 );
		GridLayout layout2 = new GridLayout( 2, false );
		layout2.marginWidth = 0;
		layout2.marginHeight = 10;
		layout2.horizontalSpacing = 10;
		botones.setLayout( layout2 );

		Button botonGuardar = new Button( botones, SWT.NONE );
		botonGuardar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		botonGuardar.setText( "Buscar" );
		botonGuardar.setImage( ControlTrabajosWindowElements.aceptarIcon );
		GridData gd1 = new GridData();
		gd1.widthHint = 100;
		botonGuardar.setLayoutData( gd1 );
		botonGuardar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				if( esTrabajoValido( textoRx.getText() ) ) {
					Session.setAttribute( Constants.ITEM_SELECTED_JB, trabajoService.findById( textoRx.getText() ) );
					NoSatisfactorioDialog dialogo = new NoSatisfactorioDialog( shell );
					dialogo.open();
					cerrar();
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );

		Button botonCerrar = new Button( botones, SWT.NONE );
		botonCerrar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		botonCerrar.setText( "Cerrar" );
		botonCerrar.setImage( ControlTrabajosWindowElements.cancelarIcon );
		GridData gd2 = new GridData();
		gd2.widthHint = 100;
		botonCerrar.setLayoutData( gd2 );
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

	private Text crearCampoTexto( Composite contenedor, String textoEtiqueta, String textoContenido, int columnasEtiqueta, int columnasTexto ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "  " + textoEtiqueta );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = ancho( columnasEtiqueta );
		gd1.horizontalSpan = columnasEtiqueta;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER );
		texto.setToolTipText( textoEtiqueta );
		GridData gd2 = new GridData();
		gd2.widthHint = ancho( columnasTexto );
		gd2.horizontalSpan = columnasTexto;
		texto.setLayoutData( gd2 );
		texto.setText( textoContenido );
		texto.addVerifyListener( new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
				e.text = ApplicationUtils.quitarAcentos( e.text );
			}
		} );
		return texto;
	}

	private int ancho( int numeroColumnas ) {
		return ( numeroColumnas * anchoColumna ) + ( ( numeroColumnas - 1 ) * anchoEntreColumnas );
	}

	private boolean esTrabajoValido( String rx ) {
		if( StringUtils.isNotBlank( rx ) ) {
			Jb jb = trabajoService.findById( rx );
			EstadoTrabajo estado = jb.estado();
			if( EstadoTrabajo.CANCELADO.equals( estado ) || EstadoTrabajo.ENTREGADO.equals( estado ) ) {
				return false;
			}
			String tipo = jb.getJbTipo();
			if( "GAR".equals( tipo ) || "OS".equals( tipo ) ) {
				return false;
			}
			return true;
		}
		return false;
	}

	private void cerrar() {
		this.close();
	}

}

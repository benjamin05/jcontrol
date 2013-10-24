package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.EstadoConstants;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import java.sql.Timestamp;

public class RetenidosDialog extends Dialog {

	public static TrabajoService trabajoService;

	private JbTrack jbTrack;
	private Text txtRazon;
	private Button buttonGuardar;
	private String razon;

	static {
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
	}

	public RetenidosDialog( Shell parentShell ) {
		super( parentShell );
		jbTrack = new JbTrack();
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		final Composite container = (Composite) super.createDialogArea( parent );
		container.setBounds( 5, 5, 495, 555 );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		VerifyListener upperTxt = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		Group group = new Group( container, SWT.SHADOW_OUT );
		group.setBounds( 10, 10, 486, 39 );
		group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblRazon = new Label( group, SWT.NONE );
		lblRazon.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.razon" ) );
		lblRazon.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblRazon.setBounds( 10, 10, 90, 20 );
		lblRazon.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtRazon = new Text( group, SWT.BORDER );
		txtRazon.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtRazon.setBounds( 109, 10, 370, 20 );
		txtRazon.addKeyListener( new KeyListener() {

			@Override
			public void keyReleased( KeyEvent arg0 ) {
				buttonGuardar.setEnabled( !txtRazon.getText().isEmpty() );
				razon = txtRazon.getText();
				if ( arg0.character == SWT.CR ) {
					if ( buttonGuardar.getEnabled() ) {
						saveRetener();
						container.getParent().getShell().close();
					}
				}
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		txtRazon.addVerifyListener( upperTxt );

		return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 507, 145 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( Constants.RX_DOS_PUNTOS + " " + Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX ) );
	}

	/**
	 * Create contents of the button bar.
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Button buttonCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		buttonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		buttonCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );

		buttonGuardar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.OK_LABEL, false );
		buttonGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		buttonGuardar.setText( ApplicationPropertyHelper.getProperty( "button.guardar.label" ) );
		buttonGuardar.setEnabled( false );
		buttonGuardar.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				saveRetener();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {

			}
		} );
	}

	private void saveRetener() {
		try {
			String jbRx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
			Jb jb = trabajoService.findById( jbRx );
			Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
			jbTrack.setRx( jbRx );
			jbTrack.setObservaciones( razon );
			jbTrack.setEstado( EstadoTrabajo.RETENIDO.codigo() );
			jbTrack.setEmpleado( empleado.getIdEmpleado() );
			jbTrack.setFecha( new Timestamp( System.currentTimeMillis() ) );
			jbTrack.setIdMod( Constants.CERO_STRING );
			jbTrack.setIdViaje( Constants.CADENA_VACIA );

			jb.getEstado().setIdEdo( EstadoConstants.ESTADO_RETENIDO );

			razon = razon.trim();
			razon = razon.toUpperCase();

			if ( razon.equals( TrabajosPropertyHelper.getProperty( "razon.sa" ) ) ) {
				jb.setRetAuto( razon );
				jbTrack.setObservaciones( razon );
			}

			trabajoService.saveJbAndTrack( jb, jbTrack );

			ApplicationUtils.recargarDatosPestanyaVisible();

		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
	}
}

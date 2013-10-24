package mx.com.lux.control.trabajos.view.container.envios;

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
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import java.sql.Timestamp;

public class NoEnviarDialog extends Dialog {

	public static TrabajoService trabajoService;

	private JbTrack jbTrack;
	private Text txtRazon;
	private Button buttonGuardar;
	private String razon;

	static {
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
	}

	public NoEnviarDialog( Shell parentShell ) {
		super( parentShell );
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
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
		// muestra RX: en la barra de titulo
		newShell.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.rx.dos.puntos" ) + " " + Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX ) );
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
				try {
					String jbRx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
					Jb jb = trabajoService.findById( jbRx );
					// JB.estado = si PE -> NE, si RPE -> RNE
					if ( jb.getEstado().getIdEdo().trim().equals( EstadoConstants.ESTADO_POR_ENVIAR ) )
						jb.getEstado().setIdEdo( EstadoConstants.ESTADO_NO_ENVIAR );
					else if ( jb.getEstado().getIdEdo().trim().equals( EstadoConstants.ESTADO_ROTO_POR_ENVIAR ) )
						jb.getEstado().setIdEdo( EstadoConstants.ESTADO_ROTO_NO_ENVIAR );
					else
						throw new IllegalArgumentException( "Estado no valido " + jb.getEstado().getIdEdo() );

					// Empleado firmado
					Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );

					// Se crea el JbTrack
					jbTrack = new JbTrack();
					jbTrack.setRx( jbRx );
					jbTrack.setEstado( jb.estado().codigo() );
					jbTrack.setObservaciones( razon.trim().toUpperCase() );
					jbTrack.setEmpleado( empleado.getIdEmpleado() );
					jbTrack.setFecha( new Timestamp( System.currentTimeMillis() ) );
					jbTrack.setIdMod( empleado.getIdEmpleado() );

					trabajoService.saveJbAndTrack( jb, jbTrack );

					ApplicationUtils.recargarDatosPestanyaVisible();

				} catch ( ApplicationException e ) {
					e.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {

			}
		} );
	}
}

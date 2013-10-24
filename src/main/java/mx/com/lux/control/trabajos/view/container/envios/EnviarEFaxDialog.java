package mx.com.lux.control.trabajos.view.container.envios;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

public class EnviarEFaxDialog extends Dialog {

	public static TrabajoService trabajoService;

	static {
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
	}

    private final Logger log = Logger.getLogger( EnviarEFaxDialog.class );

	private Text txtRx;
	private String rx;
	private Shell shell;

	private Button buttonGuardar;

	public EnviarEFaxDialog( Shell parentShell ) {
		super( parentShell );
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		container.setBounds( 5, 5, 495, 555 );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group group = new Group( container, SWT.SHADOW_OUT );
		group.setBounds( 10, 10, 486, 39 );
		group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		VerifyListener upperTxt = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		Label lblRazon = new Label( group, SWT.NONE );
		lblRazon.setText( "Rx" );
		lblRazon.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblRazon.setBounds( 10, 10, 90, 20 );
		lblRazon.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtRx = new Text( group, SWT.BORDER );
		txtRx.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtRx.setBounds( 109, 10, 370, 20 );

		txtRx.addKeyListener( new KeyListener() {

			@Override
			public void keyReleased( KeyEvent arg0 ) {
				buttonGuardar.setEnabled( !txtRx.getText().isEmpty() );
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		txtRx.addModifyListener( new ModifyListener() {
			public void modifyText( ModifyEvent arg0 ) {
				rx = txtRx.getText();
			}
		} );

		txtRx.addVerifyListener( upperTxt );

		return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 507, 145 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( ApplicationPropertyHelper.getProperty( "button.efax.label" ) );
		this.shell = newShell;
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
		buttonGuardar = createButton( parent, IDialogConstants.YES_ID, IDialogConstants.OK_LABEL, false );
		buttonGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		buttonGuardar.setText( ApplicationPropertyHelper.getProperty( "button.guardar.label" ) );
		buttonGuardar.setEnabled( false );
		buttonGuardar.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				try {
					trabajoService.validarEnviarEFax( rx );
					Empleado empleadoFirmado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
					MessageDialog confirm = new MessageDialog( shell, MessagesPropertyHelper.getProperty( "efax.title" ), null, MessagesPropertyHelper.getProperty( "efax.message" ), MessageDialog.INFORMATION, new String[]{ MessagesPropertyHelper.getProperty( "generic.si" ), MessagesPropertyHelper.getProperty( "generic.no" ) }, 0 );
					int result = confirm.open();
					if ( result == 0 ) {
						boolean completeTask = trabajoService.enviarEFax( rx, empleadoFirmado );
                        if( !completeTask ){
                            log.debug( "warning dialog" );
                            MessageDialog warningDialog = new MessageDialog( shell, MessagesPropertyHelper.getProperty( "advertencia.title" ),
                                    null, MessagesPropertyHelper.getProperty( "efaxfail.message" ),
                                    MessageDialog.ERROR, new String[]{ MessagesPropertyHelper.getProperty( "generic.ok" ) }, 0 );
                            warningDialog.open();
                        }
						shell.dispose();
					}
				} catch ( ApplicationException e ) {
					e.printStackTrace();
				} catch ( Exception e ) {
					MessageDialog.openError( shell, "Error", e.getMessage() );

				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {

			}
		} );
	}
}

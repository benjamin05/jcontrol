package mx.com.lux.control.trabajos.view.container.contacto;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.ContactoPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;

public class AgregarRxDialog extends Dialog {

	private static final TrabajoService trabajoService;

	private Jb jb;
	private Text txtNombreRx;
	private Button buttonGuardar;
	private Shell shell;
	private String rx;
	private String rxSelected;

	static {
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
	}

	public AgregarRxDialog( Shell parentShell ) {
		super( parentShell );
		rxSelected = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		shell = container.getShell();
		container.setBounds( 15, 25, 495, 555 );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group group = new Group( container, SWT.SHADOW_OUT );
		group.setBounds( 10, 10, 255, 39 );
		group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblNombre = new Label( group, SWT.NONE );
		lblNombre.setText( ContactoPropertyHelper.getProperty( "contacto.grupo.rx" ) );
		lblNombre.setBounds( 10, 10, 19, 20 );
		lblNombre.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtNombreRx = new Text( group, SWT.BORDER );
		txtNombreRx.setBounds( 35, 10, 186, 20 );
		txtNombreRx.setTextLimit( 10 );

		VerifyListener upperTxt = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		if ( rxSelected != null ) {
			rx = rxSelected;
			txtNombreRx.setText( rxSelected );
		}

		txtNombreRx.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				buttonGuardar.setEnabled( !txtNombreRx.getText().isEmpty() );
				rx = txtNombreRx.getText();
				if ( arg0.character == SWT.CR ) {
					if ( buttonGuardar.getEnabled() && validateRx() ) {
						save();
						shell.close();
					}
				}
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		txtNombreRx.addVerifyListener( upperTxt );

		return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 287, 145 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( ContactoPropertyHelper.getProperty( "contacto.grupo.agregar.rx.grupo" ) );
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
		buttonGuardar.setEnabled( !txtNombreRx.getText().isEmpty() );
		buttonGuardar.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				if ( validateRx() ) {
					save();
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	private Boolean validateRx() {
		boolean exist = false;

		try {
			jb = trabajoService.findById( rx );

			if ( jb == null ) {
				MessageDialog.openError( shell, MessagesPropertyHelper.getProperty( "contacto.grupo.agregar.rx.invalida.title" ), MessagesPropertyHelper.getProperty( "contacto.grupo.agregar.rx.invalida.message" ) );
			} else {
				exist = true;
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return exist;
	}

	private void save() {
		try {
			String idGrupo = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_GRUPO );
			trabajoService.agregarRxAGrupo( jb, idGrupo );
			ApplicationUtils.recargarDatosPestanyaVisible();
		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
	}
}

package mx.com.lux.control.trabajos.view.container.sistema.login;

import mx.com.lux.control.trabajos.bussiness.service.sistema.login.LoginService;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.view.ControlTrabajosPrincipal;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.List;

public class LoginContainer {

	private static final LoginService loginService;

	private Text txtEmpleado;
	private Text txtPassword;
	private Button btnAceptar;
	protected Shell shell;
	private ControlTrabajosPrincipal controlTrabajosPrincipal;

	static {
		loginService = ServiceLocator.getBean( LoginService.class );
	}

	protected void createContents() {
		shell = new Shell();
		shell.setBounds( 1, 1, 420, 300 );
		shell.setBackgroundImage( ControlTrabajosWindowElements.backgroundImage );
		shell.setText( ApplicationPropertyHelper.getProperty( "sistema.name.label.tres" ) );
		shell.setLayout( new FillLayout() );

		Group grupo = new Group( shell, SWT.NONE );
		grupo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImage );
		GridLayout grupoLayout = new GridLayout( 2, false );
		grupoLayout.marginHeight = 60;
		grupoLayout.marginWidth = 60;
		grupoLayout.verticalSpacing = 12;
		grupo.setLayout( grupoLayout );

		Label lblEmpresa = new Label( grupo, SWT.NONE );
		lblEmpresa.setBackgroundImage( ControlTrabajosWindowElements.backgroundImage );
		lblEmpresa.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		lblEmpresa.setText( ApplicationPropertyHelper.getProperty( "sistema.label.empresa" ) );

		Label lblIdEmpresa = new Label( grupo, SWT.NONE );
		lblIdEmpresa.setBackgroundImage( ControlTrabajosWindowElements.backgroundImage );
		lblEmpresa.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		lblIdEmpresa.setText( "PRECISION OPTICA" );

		Label lblEmpleado = new Label( grupo, SWT.NONE );
		lblEmpleado.setBackgroundImage( ControlTrabajosWindowElements.backgroundImage );
		lblEmpleado.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		lblEmpleado.setText( ApplicationPropertyHelper.getProperty( "sistema.label.empleado" ) );

		txtEmpleado = new Text( grupo, SWT.BORDER | SWT.CENTER );
		GridData txtEmpleadoGridData = new GridData( SWT.FILL, SWT.FILL, true, false );
		txtEmpleadoGridData.heightHint = 20;
		txtEmpleado.setLayoutData( txtEmpleadoGridData );
		//txtEmpleado.setTextLimit( 4 );

		Label lblPassword = new Label( grupo, SWT.NONE );
		lblPassword.setBackgroundImage( ControlTrabajosWindowElements.backgroundImage );
		lblPassword.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		lblPassword.setText( ApplicationPropertyHelper.getProperty( "sistema.label.password" ) );

		txtPassword = new Text( grupo, SWT.BORDER | SWT.CENTER | SWT.PASSWORD );
		GridData txtPasswordGridData = new GridData( SWT.FILL, SWT.FILL, true, false );
		txtPasswordGridData.heightHint = 20;
		txtPassword.setLayoutData( txtPasswordGridData );
		//txtPassword.setTextLimit( 8 );

		btnAceptar = new Button( grupo, SWT.NONE );
		btnAceptar.setImage( ControlTrabajosWindowElements.logInIcon );
		btnAceptar.setText( ApplicationPropertyHelper.getProperty( "sistema.label.aceptar" ) );
		GridData btnAceptarGridData = new GridData( SWT.FILL, SWT.FILL, true, false );
		btnAceptarGridData.horizontalSpan = 2;
		btnAceptarGridData.horizontalAlignment = SWT.CENTER;
		btnAceptarGridData.widthHint = 120;
		btnAceptar.setLayoutData( btnAceptarGridData );
		btnAceptar.setEnabled( false );

		txtEmpleado.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				btnAceptar.setEnabled( StringUtils.isNotBlank( txtEmpleado.getText() ) && StringUtils.isNotBlank( txtPassword.getText() ) );
				if ( arg0.character == SWT.CR ) {
					if ( btnAceptar.getEnabled() ) {
						validateLogin();
					}
				}
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		txtEmpleado.addVerifyListener( new VerifyListener() {
			@Override
			public void verifyText( VerifyEvent event ) {
				switch ( event.keyCode ) {
					case SWT.BS: // Backspace
					case SWT.DEL: // Delete
					case SWT.HOME: // Home
					case SWT.END: // End
					case SWT.ARROW_LEFT: // Left arrow
					case SWT.ARROW_RIGHT: // Right arrow
						return;
				}

				if ( !Character.isDigit( event.character ) ) {
					event.doit = false; // disallow the action
				}
			}
		} );

		txtPassword.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				btnAceptar.setEnabled( !txtEmpleado.getText().isEmpty() && !txtPassword.getText().isEmpty() );
				if ( arg0.character == SWT.CR ) {
					if ( btnAceptar.getEnabled() ) {
						validateLogin();
					}
				}
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		btnAceptar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				validateLogin();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	private void validateLogin() {
		List<Empleado> listaUsuarios = null;
		try {
			listaUsuarios = loginService.findUserPassword( txtEmpleado.getText(), txtPassword.getText() );
			if ( listaUsuarios != null && !listaUsuarios.isEmpty() ) {
				Empleado usuario = listaUsuarios.get( 0 );
				Session.setAttribute( Constants.PARAM_USER_LOGGED, usuario );
				Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_SUCURSAL, usuario.getSucursal().getIdSucursal() );
				openApplication();
			} else {
				MessageDialog.openError( shell, MessagesPropertyHelper.getProperty( "login.error.title" ), MessagesPropertyHelper.getProperty( "login.error.message" ) );
			}
		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
	}

	private void openApplication() {
		shell.close();
		controlTrabajosPrincipal.setBlockOnOpen( true );
		controlTrabajosPrincipal.open();
		Display.getCurrent().dispose();
	}

	public void open() {
		Display display = Display.getDefault();

		controlTrabajosPrincipal = new ControlTrabajosPrincipal();
		ControlTrabajosWindowElements.loadEnvironment( controlTrabajosPrincipal );

		createContents();
		shell.open();
		shell.setImage( ControlTrabajosWindowElements.applicationIcon );
		shell.layout();

		while ( !shell.isDisposed() ) {
			if ( !display.readAndDispatch() ) {
				display.sleep();
			}
		}
	}

}

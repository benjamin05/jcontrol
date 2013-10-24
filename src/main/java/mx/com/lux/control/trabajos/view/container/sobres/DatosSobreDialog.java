package mx.com.lux.control.trabajos.view.container.sobres;

import mx.com.lux.control.trabajos.bussiness.service.sobres.SobreService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbSobre;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.PrinterException;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.SobresPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import java.sql.Timestamp;
import java.util.Date;

public class DatosSobreDialog extends Dialog {
	private Text txtSobre;
	private Text txtDestinatario;
	private Text txtArea;
	private Text txtContenido;
	private JbSobre jbSobre;
	private Jb jb;
	private Shell shell;

	public static SobreService sobreService;
	public static TrabajoService trabajoService;

	static {
		sobreService = (SobreService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_SOBRE_SERVICE );
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
	}


	public DatosSobreDialog( Shell parentShell, TipoSobre tipoSobre ) {
		super( parentShell );
		if ( tipoSobre == TipoSobre.MODIFICADO ) {
			jbSobre = (JbSobre) Session.getAttribute( SobresDialog.ITEM_SELECTED_JBSOBRE );
		} else if ( tipoSobre == TipoSobre.ENVIO ) {
			String rx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
			try {
				jb = trabajoService.findById( rx );
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		this.shell = newShell;
		newShell.setText( SobresPropertyHelper.getProperty( "sobres.nuevo.dialog.title" ) );
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		Composite container = (Composite) super.createDialogArea( parent );
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		VerifyListener upperTxt = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		Group group = new Group( container, SWT.NONE );
		group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd_group = new GridData( SWT.LEFT, SWT.CENTER, false, false, 1, 1 );
		gd_group.heightHint = 191;
		gd_group.widthHint = 257;
		group.setLayoutData( gd_group );

		Label lblSobre = new Label( group, SWT.NONE );
		lblSobre.setBounds( 10, 22, 55, 15 );
		lblSobre.setText( SobresPropertyHelper.getProperty( "sobres.label.sobre" ) );
		lblSobre.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtSobre = new Text( group, SWT.BORDER );
		txtSobre.setBounds( 105, 16, 139, 21 );

		txtDestinatario = new Text( group, SWT.BORDER );
		txtDestinatario.setBounds( 105, 53, 139, 21 );

		txtArea = new Text( group, SWT.BORDER );
		txtArea.setBounds( 105, 91, 139, 21 );

		txtContenido = new Text( group, SWT.BORDER );
		txtContenido.setBounds( 105, 132, 139, 21 );

		Label lblDestinatario = new Label( group, SWT.NONE );
		lblDestinatario.setBounds( 10, 53, 69, 15 );
		lblDestinatario.setText( SobresPropertyHelper.getProperty( "sobres.label.destinatario" ) );
		lblDestinatario.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblArea = new Label( group, SWT.NONE );
		lblArea.setBounds( 10, 91, 55, 15 );
		lblArea.setText( SobresPropertyHelper.getProperty( "sobres.label.area" ) );
		lblArea.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblContenido = new Label( group, SWT.NONE );
		lblContenido.setBounds( 10, 132, 55, 15 );
		lblContenido.setText( SobresPropertyHelper.getProperty( "sobres.label.contenido" ) );
		lblContenido.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		if ( jbSobre != null ) {
			txtSobre.setText( jbSobre.getFolioSobre() );
			txtDestinatario.setText( jbSobre.getDest() );
			txtArea.setText( jbSobre.getArea() );
			txtContenido.setText( jbSobre.getContenido() );
		}

		if ( jb != null ) {
			txtContenido.setText( jb.getMaterial() );
		}

		txtSobre.addVerifyListener( upperTxt );
		txtDestinatario.addVerifyListener( upperTxt );
		txtArea.addVerifyListener( upperTxt );
		txtContenido.addVerifyListener( upperTxt );

		return container;
	}

	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		parent.setBackground( SWTResourceManager.getColor( SWT.COLOR_BLACK ) );
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Button btnGuardar = createButton( parent, IDialogConstants.YES_ID, "Guardar", true );
		btnGuardar.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				if ( jbSobre == null ) {
					jbSobre = new JbSobre();
				}
				if ( jb != null ) {
					jbSobre.setRx( jb.getRx() );
				}
				jbSobre.setArea( txtArea.getText() );
				jbSobre.setFolioSobre( txtSobre.getText() );
				jbSobre.setContenido( txtContenido.getText() );
				jbSobre.setDest( txtDestinatario.getText() );
				jbSobre.setFecha( new Timestamp( new Date().getTime() ) );
				Empleado empleadoFirmado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
				jbSobre.setEmp( empleadoFirmado.getIdEmpleado() );
				jbSobre.setIdMod( "0" );
				try {
					sobreService.saveSobre( jbSobre );
					SobresDialog dialog = (SobresDialog) Session.getAttribute( SobresDialog.SOBRES_DIALOG );
					if ( dialog != null ) {
						dialog.loadSobres();
					}
					sobreService.imprimirSobre( empleadoFirmado, jbSobre );
				} catch ( PrinterException ex ) {
					MessageDialog.openError( shell, "Error", "La impresora no est\u00e1 lista." );
					ex.printStackTrace();
				} catch ( ApplicationException e1 ) {
					MessageDialog.openError( shell, "Error", "Ocurri\u00f3 un error." );
					e1.printStackTrace();
				}
				shell.dispose();
			}
		} );
		btnGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		btnGuardar.setText( ApplicationPropertyHelper.getProperty( "button.guardar.label" ) );
		Button btnCerrar = createButton( parent, IDialogConstants.CANCEL_ID, "Cancelar", false );
		btnCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
		btnCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 293, 321 );
	}
}

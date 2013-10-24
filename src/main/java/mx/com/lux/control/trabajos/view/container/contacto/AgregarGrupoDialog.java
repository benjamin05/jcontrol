package mx.com.lux.control.trabajos.view.container.contacto;

import mx.com.lux.control.trabajos.bussiness.service.contacto.ContactoViewService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbEstado;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.EstadoConstants;
import mx.com.lux.control.trabajos.util.constants.RxConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.ContactoPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;

import java.sql.Timestamp;

public class AgregarGrupoDialog extends Dialog {

	public static ContactoViewService contactoViewService;
	public static TrabajoService trabajoService;

	private Text txtNombreGrupo;
	private Button buttonGuardar;
	private String nombre;

	static {
		contactoViewService = (ContactoViewService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_CONTACTO_SERVICE );
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
	}

	public AgregarGrupoDialog( Shell parentShell ) {
		super( parentShell );
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		final Composite container = (Composite) super.createDialogArea( parent );
		container.setBounds( 15, 25, 495, 555 );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group group = new Group( container, SWT.SHADOW_OUT );
		group.setBounds( 10, 10, 508, 39 );
		group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblNombre = new Label( group, SWT.NONE );
		lblNombre.setText( ContactoPropertyHelper.getProperty( "contacto.grupo.nombre.grupo" ) );
		lblNombre.setBounds( 10, 10, 112, 20 );
		lblNombre.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtNombreGrupo = new Text( group, SWT.BORDER );
		txtNombreGrupo.setBounds( 128, 10, 370, 20 );

		txtNombreGrupo.addFocusListener( new FocusListener() {
			@Override
			public void focusLost( FocusEvent e ) {
				txtNombreGrupo.getText().toUpperCase();
			}

			@Override
			public void focusGained( FocusEvent e ) {
			}
		} );

		txtNombreGrupo.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				buttonGuardar.setEnabled( StringUtils.isNotBlank( txtNombreGrupo.getText() ) );
				nombre = txtNombreGrupo.getText().toUpperCase();
				if ( arg0.character == SWT.CR ) {
					if ( buttonGuardar.getEnabled() ) {
						save();
						container.getParent().getShell().close();
					}
				}
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		VerifyListener upperTxt = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		txtNombreGrupo.addVerifyListener( upperTxt );
		return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 530, 145 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( ContactoPropertyHelper.getProperty( "contacto.grupo.agregar.grupo" ) );
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
				save();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	private void save() {
		try {
			Jb jb = new Jb();
			String newIdGroup = contactoViewService.getLastIdGroup( RxConstants.TIPO_GRUPO_F );
			Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
			jb.setRx( newIdGroup );
			jb.setEstado( new JbEstado() );
			jb.getEstado().setIdEdo( EstadoConstants.ESTADO_PENDIENTE );
			jb.setCliente( nombre );
			jb.setEmpAtendio( empleado.getIdEmpleado() );
			jb.setMaterial( "VARIAS FACTURAS" );
			jb.setJbTipo( "GRUPO" );
			jb.setTipoVenta( "GRUPO" );
			jb.setFechaMod( new Timestamp( System.currentTimeMillis() ) );
			jb.setIdMod( Constants.CERO_STRING );
			trabajoService.save( jb );
			ApplicationUtils.recargarDatosPestanyaVisible();
		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
	}
}

package mx.com.lux.control.trabajos.view.container.ordenservicio;

import mx.com.lux.control.trabajos.bussiness.service.ordenservicio.TipoContactoService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.dao.trabajo.ClienteDAO;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.ContactoPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

public class FormaContactoDialog extends Dialog {

	private static final TrabajoService trabajoService;
	private static final TipoContactoService tipoContactoService;

	private Shell shell;
	private Text txtContacto;
	private Text txtObservaciones;
	private List<TipoContacto> formasContacto;
	private Cliente cliente;
	private Button buttonGuardar;
	private Button radioSi;
	private Combo comboContacto;
	private Jb jb;
	private Integer idFormaContacto;

	static {
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
		tipoContactoService = (TipoContactoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TIPO_CONTACTO_SERVICE );
	}

	public FormaContactoDialog( Shell parentShell ) {
		super( parentShell );
	}

	@Override
	protected Control createDialogArea( final Composite parent ) {
		Composite container = (Composite) super.createDialogArea( parent );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		container.setLayout( null );

		Group grpDatos = new Group( container, SWT.NONE );
		grpDatos.setText( ContactoPropertyHelper.getProperty( "forma.contacto.datos" ) );
		grpDatos.setBounds( 10, 10, 364, 82 );

		Label lblFactura = new Label( grpDatos, SWT.NONE );
		lblFactura.setBounds( 10, 24, 55, 15 );
		lblFactura.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblFactura.setText( ContactoPropertyHelper.getProperty( "forma.contacto.factura" ) );

		Text txtFactura = new Text( grpDatos, SWT.BORDER );
		txtFactura.setBounds( 137, 21, 217, 25 );
		txtFactura.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtFactura.setEditable( false );

		Label lblNombre = new Label( grpDatos, SWT.NONE );
		lblNombre.setBounds( 10, 54, 55, 15 );
		lblNombre.setText( ContactoPropertyHelper.getProperty( "forma.contacto.nombre" ) );
		lblNombre.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtNombre = new Text( grpDatos, SWT.BORDER );
		txtNombre.setBounds( 137, 51, 217, 25 );
		txtNombre.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtNombre.setEditable( false );

		Group grpContactar = new Group( container, SWT.NONE );
		grpContactar.setText( ContactoPropertyHelper.getProperty( "forma.contacto.contacto" ) );
		grpContactar.setBounds( 10, 98, 364, 166 );

		Label lblContactar = new Label( grpContactar, SWT.NONE );
		lblContactar.setBounds( 10, 23, 108, 15 );
		lblContactar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblContactar.setText( ContactoPropertyHelper.getProperty( "forma.contacto.contactar" ) );

		Label lblContacto = new Label( grpContactar, SWT.NONE );
		lblContacto.setBounds( 10, 47, 55, 15 );
		lblContacto.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblContacto.setText( ContactoPropertyHelper.getProperty( "forma.contacto.contacto" ) );

		comboContacto = new Combo( grpContactar, SWT.NONE );
		comboContacto.setBounds( 141, 44, 150, 23 );
		comboContacto.setEnabled( false );

		txtContacto = new Text( grpContactar, SWT.BORDER );
		txtContacto.setBounds( 141, 73, 215, 25 );
		txtContacto.setEnabled( false );

		txtContacto.addKeyListener( new KeyAdapter() {
			@Override
			public void keyReleased( final KeyEvent e ) {
				enableButttonGuardar();
			}
		} );

		Label lblObservaciones = new Label( grpContactar, SWT.NONE );
		lblObservaciones.setBounds( 10, 100, 108, 15 );
		lblObservaciones.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblObservaciones.setText( ContactoPropertyHelper.getProperty( "forma.contacto.observaciones" ) );

		txtObservaciones = new Text( grpContactar, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL );
		txtObservaciones.setBounds( 141, 100, 215, 50 );
		txtObservaciones.setEnabled( false );

		final Button radioNo = new Button( grpContactar, SWT.RADIO );
		radioNo.setBounds( 182, 22, 45, 16 );
		radioNo.setText( ContactoPropertyHelper.getProperty( "forma.contacto.no" ) );
		radioNo.setSelection( true );

		radioSi = new Button( grpContactar, SWT.RADIO );
		radioSi.setBounds( 141, 22, 40, 16 );
		radioSi.setText( ContactoPropertyHelper.getProperty( "forma.contacto.si" ) );
		radioSi.setSelection( false );

		radioSi.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( final SelectionEvent e ) {
				radioNo.setSelection( false );
				radioSi.setSelection( true );
				comboContacto.setEnabled( true );
				txtContacto.setEnabled( true );
				txtObservaciones.setEnabled( true );
				enableButttonGuardar();
			}
		} );

		radioNo.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( final SelectionEvent e ) {
				radioSi.setSelection( false );
				radioNo.setSelection( true );
				comboContacto.setEnabled( false );
				txtContacto.setEnabled( false );
				txtObservaciones.setEnabled( false );
				enableButttonGuardar();
			}
		} );

		comboContacto.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( final SelectionEvent e ) {
				int indice = comboContacto.getSelectionIndex();
				idFormaContacto = formasContacto.get( indice ).getIdTipoContacto();
				switch ( indice ) {
					case 0:
						txtContacto.setText( StringUtils.isNotBlank( cliente.getEmailCli() ) ? cliente.getEmailCli().trim() : "" );
						break;
					case 1:
						txtContacto.setText( StringUtils.isNotBlank( cliente.getTelAdiCli() ) ? cliente.getTelAdiCli().trim() : "" );
						break;
					case 2:
						txtContacto.setText( StringUtils.isNotBlank( cliente.getTelCasaCli() ) ? cliente.getTelCasaCli().trim() : "" );
						break;
					case 3:
						txtContacto.setText( StringUtils.isNotBlank( cliente.getTelAdiCli() ) ? cliente.getTelAdiCli().trim() : "" );
						break;
				}
				enableButttonGuardar();
			}
		} );

		try {
			formasContacto = tipoContactoService.findAll();
			String[] formas = new String[formasContacto.size()];
			int i = 0;
			for ( TipoContacto tc : formasContacto ) {
				formas[i++] = tc.getDescripcion();
			}
			comboContacto.setItems( formas );

			this.cliente = (Cliente) Session.getAttribute( Constants.ITEM_SELECTED_CLIENTE );
		} catch ( ApplicationException ae ) {
			ae.printStackTrace();
		}

		jb = (Jb) Session.getAttribute( Constants.ITEM_NEW_ORDEN_SERVICIO );
		txtFactura.setText( jb.getRx() );
		txtNombre.setText( jb.getCliente() );

		return container;
	}

	@Override
	protected void createButtonsForButtonBar( final Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		buttonGuardar = createButton( parent, IDialogConstants.YES_ID, IDialogConstants.CANCEL_LABEL, false );

		buttonGuardar.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( final SelectionEvent e ) {

				try {
                    Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
                    Sucursal sucursal = empleado.getSucursal();

					if ( radioSi.getSelection() ) {
						FormaContacto fc = new FormaContacto();
						fc.setRx( jb.getRx() );
						fc.setIdCliente( Integer.parseInt( jb.getIdCliente() ) );
						fc.setObservaciones( txtObservaciones.getText() );
						TipoContacto tc = new TipoContacto();
						tc.setIdTipoContacto( idFormaContacto );
						fc.setTipoContacto( tc );
						fc.setContacto( StringUtils.isNotBlank( txtContacto.getText() ) ? txtContacto.getText() : "" );
                        fc.setIdSucursal( sucursal.getIdSucursal() );
                        fc.setFechaMod( new Date() );
						trabajoService.save( fc );
					} else {
						jb = trabajoService.findById( jb.getRx() );
						jb.setNoLlamar( true );
						trabajoService.save( jb );
					}
					ApplicationUtils.recargarDatosPestanyaVisible();
					shell.dispose();
				} catch ( ApplicationException ae ) {
					MessageBox box = new MessageBox( shell, SWT.ERROR );
					box.setText( ae.getMessage() );
					box.open();
				}
			}
		} );

		buttonGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		buttonGuardar.setText( ApplicationPropertyHelper.getProperty( "button.guardar.label" ) );
		buttonGuardar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Button buttonCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		Rectangle rec = buttonCerrar.getBounds();
		buttonCerrar.setBounds( rec.x, rec.y, rec.height, rec.y + 40 );
		buttonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		buttonCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
		buttonCerrar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 390, 364 );
	}

	@Override
	protected void configureShell( final Shell newShell ) {
		super.configureShell( newShell );
		this.shell = newShell;
		newShell.setText( ContactoPropertyHelper.getProperty( "detalles.os.title" ) );
	}

	private void enableButttonGuardar() {
		if ( radioSi.getSelection() ) {
			buttonGuardar.setEnabled( ( txtContacto.getText().length() == 10 || ApplicationUtils.isEmail( txtContacto.getText() ) ) && comboContacto.getSelectionIndex() > -1 );
		} else {
			buttonGuardar.setEnabled( true );
		}
	}
}

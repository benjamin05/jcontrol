package mx.com.lux.control.trabajos.view.container.ordenservicio;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.ClienteService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Cliente;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbNota;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.ContactoPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DetallesOrdenServicioDialog extends Dialog {

	private static final ClienteService clienteService;
	private static final TrabajoService trabajoService;

	private Shell shell;
	private Text txtApellidoMaterno;
	private Text txtApellidoPaterno;
	private Text txtNombre;
	private Text txtServicio;
	private Text txtFechaPromesa;
	private Text txtDejo;
	private Text txtCondicionesGenerales;
	private Text txtInstruccion;

	static {
		clienteService = (ClienteService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_CLIENTE_SERVICE );
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
	}

	/**
	 * Create the dialog.
	 *
	 * @param parentShell
	 */
	public DetallesOrdenServicioDialog( Shell parentShell ) {
		super( parentShell );
		this.shell = parentShell;
	}

	/**
	 * Create contents of the dialog.
	 *
	 * @param parent
	 */
	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		container.setLayout( new GridLayout( 2, false ) );
		new Label( container, SWT.NONE );
		new Label( container, SWT.NONE );

		Label lblApellidoPaterno = new Label( container, SWT.NONE );
		lblApellidoPaterno.setText( ContactoPropertyHelper.getProperty( "detalles.os.apellidoPaterno" ) );
		lblApellidoPaterno.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblApellidoPaterno.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );

		txtApellidoPaterno = new Text( container, SWT.BORDER );
		txtApellidoPaterno.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 1, 1 ) );
		txtApellidoPaterno.setEditable( false );
		txtApellidoPaterno.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		Label lblApellidoMaterno = new Label( container, SWT.NONE );
		lblApellidoMaterno.setText( ContactoPropertyHelper.getProperty( "detalles.os.apellidoMaterno" ) );
		lblApellidoMaterno.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblApellidoMaterno.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );

		txtApellidoMaterno = new Text( container, SWT.BORDER );
		txtApellidoMaterno.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 1, 1 ) );
		txtApellidoMaterno.setEditable( false );
		txtApellidoMaterno.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		Label lblNombre = new Label( container, SWT.NONE );
		lblNombre.setText( ContactoPropertyHelper.getProperty( "detalles.os.nombre" ) );
		lblNombre.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblNombre.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );

		txtNombre = new Text( container, SWT.BORDER );
		txtNombre.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 1, 1 ) );
		txtNombre.setEditable( false );
		txtNombre.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		Label lblServicio = new Label( container, SWT.NONE );
		lblServicio.setText( ContactoPropertyHelper.getProperty( "detalles.os.servicio" ) );
		lblServicio.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblServicio.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );

		txtServicio = new Text( container, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL );
		txtServicio.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 1, 1 ) );
		txtServicio.setEditable( false );
		txtServicio.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		Label lblFechaPromesa = new Label( container, SWT.NONE );
		lblFechaPromesa.setText( ContactoPropertyHelper.getProperty( "detalles.os.fechapromesa" ) );
		lblFechaPromesa.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblFechaPromesa.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );

		txtFechaPromesa = new Text( container, SWT.BORDER );
		txtFechaPromesa.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 1, 1 ) );
		txtFechaPromesa.setEditable( false );
		txtFechaPromesa.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		Label lblDejo = new Label( container, SWT.NONE );
		lblDejo.setText( ContactoPropertyHelper.getProperty( "detalles.os.dejo" ) );
		lblDejo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblDejo.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );

		txtDejo = new Text( container, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL );
		txtDejo.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 1, 1 ) );
		txtDejo.setEditable( false );
		txtDejo.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		Label lblCondicionesGenerales = new Label( container, SWT.NONE );
		lblCondicionesGenerales.setText( ContactoPropertyHelper.getProperty( "detalles.os.condicionesgenerales" ) );
		lblCondicionesGenerales.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblCondicionesGenerales.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );

		txtCondicionesGenerales = new Text( container, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL );
		txtCondicionesGenerales.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 1, 1 ) );
		txtCondicionesGenerales.setEditable( false );
		txtCondicionesGenerales.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		Label lblInstruccion = new Label( container, SWT.NONE );
		lblInstruccion.setText( ContactoPropertyHelper.getProperty( "detalles.os.instruccion" ) );
		lblInstruccion.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblInstruccion.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );

		txtInstruccion = new Text( container, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL );
		txtInstruccion.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 1, 1 ) );
		txtInstruccion.setEditable( false );
		txtInstruccion.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		try {
			Jb os = (Jb) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ORDEN_SERVICIO );
			JbNota jbNota = trabajoService.findJbNota( Integer.parseInt( os.getRx().substring( 1 ) ) );
            if( jbNota == null ){
                jbNota = trabajoService.findJbNota( Integer.parseInt( os.getRx() ) );
            }
			Cliente cliente = clienteService.findClienteById( Integer.valueOf( os.getIdCliente() ) );
			txtApellidoPaterno.setText( cliente.getApellidoPatCli() );
			txtApellidoMaterno.setText( cliente.getApellidoMatCli() );
			txtNombre.setText( cliente.getNombreCli() );
			txtDejo.setText( jbNota.getDejo() );
			txtServicio.setText( jbNota.getServicio() );
			if ( jbNota.getFechaProm() != null )
				txtFechaPromesa.setText( new SimpleDateFormat( "dd/MM/yyyy" ).format( jbNota.getFechaProm() ) );
			txtCondicionesGenerales.setText( jbNota.getCondicion() );
			txtInstruccion.setText( jbNota.getInstruccion() );
		} catch ( ApplicationException ae ) {
			ae.printStackTrace();
		}

		return container;
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
		Rectangle rec = buttonCerrar.getBounds();
		buttonCerrar.setBounds( rec.x, rec.y, rec.height, rec.y + 40 );
		buttonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		buttonCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
		buttonCerrar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point( 450, 400 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( ContactoPropertyHelper.getProperty( "detalles.os.title" ) );
	}
}

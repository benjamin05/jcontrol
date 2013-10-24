package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.*;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.OnlyDigit;
import mx.com.lux.control.trabajos.view.render.EmpresaFichaRender;
import mx.com.lux.control.trabajos.view.render.ParentescoFichasRender;
import mx.com.lux.control.trabajos.view.render.PlazosPagoFichasRender;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import java.sql.Timestamp;

public class AutorizacionDialog extends Dialog {

	public static TrabajoService trabajoService;

	static {
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
	}

	private EmpresaFicha empresaFicha = new EmpresaFicha();
	private Jb jb = new Jb();
	private ParentescoFichas parentescoFichas = new ParentescoFichas();
	private PlazosPagoFichas plazosPagoFichas = new PlazosPagoFichas();
	private final Empleado empleado;

	private final Shell shell;
	private Text txtNumEmp;
	private Button buttonGuardar;

	private ComboViewer comboViewEmpresa;
	private ComboViewer comboViewParen;
	private ComboViewer comboViewPlazo;
	private Combo comboEmpresa;
	private Combo comboParen;
	private Combo comboPlazo;

	private Integer indexEmpresa;
	private Integer indexParen;
	private Integer indexPlazo;
	private String numEmp;
	private String nombreEmpleadoResponseWeb = "";
	private String rx;

	public AutorizacionDialog( Shell parentShell ) {
		super( parentShell );
		this.shell = parentShell;
		rx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
		empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		container.setBounds( 5, 5, 495, 555 );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group gpoAutoriza = new Group( container, SWT.NONE );
		gpoAutoriza.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		gpoAutoriza.setBounds( 10, 10, 348, 166 );
		gpoAutoriza.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblFactura = new Label( gpoAutoriza, SWT.NONE );
		lblFactura.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.factura" ) );
		lblFactura.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblFactura.setBounds( 10, 10, 110, 20 );
		lblFactura.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtFactura = new Text( gpoAutoriza, SWT.BORDER );
		txtFactura.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtFactura.setEditable( false );
		txtFactura.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtFactura.setBounds( 147, 10, 187, 20 );
		txtFactura.setText( rx );

		Label lblNumEmp = new Label( gpoAutoriza, SWT.NONE );
		lblNumEmp.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.num.empleado" ) );
		lblNumEmp.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblNumEmp.setBounds( 10, 36, 135, 20 );
		lblNumEmp.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtNumEmp = new Text( gpoAutoriza, SWT.BORDER );
		txtNumEmp.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtNumEmp.setBounds( 147, 36, 187, 20 );
		txtNumEmp.setTextLimit( Constants.LONGITUD_ID_EMPLEADO );

		comboViewEmpresa = new ComboViewer( gpoAutoriza, SWT.READ_ONLY );
		comboEmpresa = comboViewEmpresa.getCombo();
		comboEmpresa.setBounds( 147, 62, 187, 27 );
		comboViewEmpresa.setContentProvider( new ArrayContentProvider() );
		comboViewEmpresa.setLabelProvider( new EmpresaFichaRender() );

		Label lblEmpresa = new Label( gpoAutoriza, SWT.NONE );
		lblEmpresa.setBounds( 10, 65, 110, 20 );
		lblEmpresa.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblEmpresa.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.empresa" ) );
		lblEmpresa.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		comboViewParen = new ComboViewer( gpoAutoriza, SWT.READ_ONLY );
		comboParen = comboViewParen.getCombo();
		comboParen.setBounds( 147, 95, 187, 27 );
		comboViewParen.setContentProvider( new ArrayContentProvider() );
		comboViewParen.setLabelProvider( new ParentescoFichasRender() );

		comboViewPlazo = new ComboViewer( gpoAutoriza, SWT.READ_ONLY );
		comboPlazo = comboViewPlazo.getCombo();
		comboPlazo.setBounds( 147, 128, 187, 27 );
		comboViewPlazo.setContentProvider( new ArrayContentProvider() );
		comboViewPlazo.setLabelProvider( new PlazosPagoFichasRender() );

		Label lblParentesco = new Label( gpoAutoriza, SWT.NONE );
		lblParentesco.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblParentesco.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.parentesco" ) );
		lblParentesco.setBounds( 10, 98, 110, 20 );
		lblParentesco.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblPlazo = new Label( gpoAutoriza, SWT.NONE );
		lblPlazo.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblPlazo.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.plazo" ) );
		lblPlazo.setBounds( 10, 131, 110, 20 );
		lblPlazo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		try {
			comboViewEmpresa.setInput( trabajoService.getEmpresaFicha() );
			comboViewParen.setInput( trabajoService.getParentescoFichas() );

			jb = trabajoService.findById( rx );
			if ( jb.getTipoVenta().equals( TipoVentaConstants.TIPO_VENTA_EMPLEADO_AUDITIVO ) ) {
				comboViewPlazo.setInput( trabajoService.getAllPlazosPagoFichas() );
			} else {
				comboViewPlazo.setInput( trabajoService.getPlazosPagoFichasByTipoVentaNull() );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}

		// EVENTOS

		txtNumEmp.addKeyListener( new KeyListener() {

			@Override
			public void keyReleased( KeyEvent arg0 ) {
				enableButtonGuardar();
				numEmp = txtNumEmp.getText();
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
				// TODO Auto-generated method stub

			}
		} );

		txtNumEmp.addVerifyListener( new OnlyDigit() );

		comboViewEmpresa.addSelectionChangedListener( new ISelectionChangedListener() {

			@Override
			public void selectionChanged( SelectionChangedEvent arg0 ) {
				enableButtonGuardar();
				indexEmpresa = comboEmpresa.getSelectionIndex();
			}
		} );

		comboViewParen.addSelectionChangedListener( new ISelectionChangedListener() {

			@Override
			public void selectionChanged( SelectionChangedEvent arg0 ) {
				enableButtonGuardar();
				indexParen = comboParen.getSelectionIndex();
			}
		} );

		comboViewPlazo.addSelectionChangedListener( new ISelectionChangedListener() {

			@Override
			public void selectionChanged( SelectionChangedEvent arg0 ) {
				enableButtonGuardar();
				indexPlazo = comboPlazo.getSelectionIndex();
			}
		} );

		return container;
	}

	private void enableButtonGuardar() {
		if ( !txtNumEmp.getText().isEmpty() && !comboViewEmpresa.getSelection().isEmpty() &&
				!comboViewParen.getSelection().isEmpty() && !comboViewPlazo.getSelection().isEmpty() ) {
			buttonGuardar.setEnabled( true );
		} else {
			buttonGuardar.setEnabled( false );
		}
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 370, 276 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( Constants.RX_DOS_PUNTOS + " " +
				rx );
	}

	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Button buttonCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		buttonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		buttonCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
		buttonGuardar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.OK_LABEL, false );
		buttonGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		buttonGuardar.setText( ApplicationPropertyHelper.getProperty( "button.solicitar.label" ) );
		buttonGuardar.setEnabled( false );
		buttonGuardar.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				empresaFicha = (EmpresaFicha) comboViewEmpresa.getElementAt( indexEmpresa );
				nombreEmpleadoResponseWeb = trabajoService.empleadoEmpresaWebResponse( numEmp, empresaFicha.getIdEmpresa() );
				if ( !nombreEmpleadoResponseWeb.startsWith( Constants.ERROR_RESPONSE_NO_EXISTE_EMPLEADO ) ) {
					try {
						NotaVenta notaVenta = trabajoService.findNotaVentaByFactura( rx );
						if ( notaVenta != null ) {
							parentescoFichas = (ParentescoFichas) comboViewParen.getElementAt( indexParen );
							plazosPagoFichas = (PlazosPagoFichas) comboViewPlazo.getElementAt( indexPlazo );

							String contenido = trabajoService.createContenidoAcuses( notaVenta, empresaFicha.getIdEmpresa(), numEmp, rx, parentescoFichas.getIdParentesco(), jb.getCliente(), String.valueOf( plazosPagoFichas.getPlazo() ), String.valueOf( empleado.getSucursal().getIdSucursal() ) );

							Acuses acuses = new Acuses();
							acuses.setContenido( contenido );
							acuses.setIdTipo( AcusesConstants.AUTO_FICHAS );
							acuses.setIntentos( Constants.CERO_INTEGER );
							acuses.setFechaCarga( new Timestamp( System.currentTimeMillis() ) );

							Nomina nomina = new Nomina();
							nomina.setIdFactura( notaVenta.getIdFactura() );
							nomina.setFactura( notaVenta.getFactura() );
							nomina.setIdEmpleado( empleado.getIdEmpleado() );
							nomina.setAcuse( acuses );
							nomina.setIdEmp( numEmp );
							nomina.setEmpleado( nombreEmpleadoResponseWeb );
							nomina.setIdEmpresa( empresaFicha.getIdEmpresa() );
							nomina.setIdParentesco( parentescoFichas.getIdParentesco() );
							nomina.setPagos( plazosPagoFichas.getPlazo().toString() );
							nomina.setFechaSolicitud( new Timestamp( System.currentTimeMillis() ) );
							nomina.setFechaMod( new Timestamp( System.currentTimeMillis() ) );

							JbTrack jbTrack = new JbTrack();
							jbTrack.setRx( rx );
							jbTrack.setEstado( EstadoTrabajo.RETENIDO.codigo() );
							jbTrack.setObservaciones( Constants.TRABAJO_PENDIENTE_AUTORIZAR );
							jbTrack.setEmpleado( empleado.getIdEmpleado() );
							jbTrack.setIdViaje( "" );
							jbTrack.setFecha( new Timestamp( System.currentTimeMillis() ) );
							jbTrack.setIdMod( "0" );

							trabajoService.saveAcusesNominaJbtrack( acuses, nomina, jbTrack );

							ApplicationUtils.recargarDatosPestanyaVisible();
						}

					} catch ( ApplicationException e ) {
						e.printStackTrace();
					}
				} else {
					MessageDialog.openError( shell, MessagesPropertyHelper.getProperty( "error.no.existe.empleado.title" ), MessagesPropertyHelper.getProperty( "error.no.existe.empleado.message" ) );
					AutorizacionDialog ad = new AutorizacionDialog( shell );
					ad.open();
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {

			}
		} );
	}

}

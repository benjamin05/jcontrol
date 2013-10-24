package mx.com.lux.control.trabajos.view.container.ordenservicio;

import mx.com.lux.control.trabajos.bussiness.service.impresora.TrabajoImpresion;
import mx.com.lux.control.trabajos.bussiness.service.ordenservicio.OrdenServicioService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.PrinterException;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.*;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.render.JbServiciosRender;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrdenServicioDialog extends Dialog {

	private final static OrdenServicioService ordenServicioService;
	private final static TrabajoService trabajoService;
	private final static TrabajoImpresion trabajoImpresion;

	private JbServicio servicio;
	private List<JbServicio> servicios;
	private Empleado empleado;
	private Cliente cliente;
	private Text txtApPaterno;
	private Text txtApMaterno;
	private Text txtNombre;
	private Text txtDejo;
	private Text txtInst;
	private Text txtCond;
	private ComboViewer cmbVwServ;
	private Combo cmbServ;
	private DateTime dtFeProm;
	private Button btnGuardar;
	private Shell shell;
	private Date fechaPromesa = new Date();
	private Calendar calendar;

	static {
		ordenServicioService = ServiceLocator.getBean( OrdenServicioService.class );
		trabajoService = ServiceLocator.getBean( TrabajoService.class );
		trabajoImpresion = ServiceLocator.getBean( TrabajoImpresion.class );
	}

	public OrdenServicioDialog( Shell parentShell ) {
		super( parentShell );
		this.shell = parentShell;
		empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
		cliente = (Cliente) Session.getAttribute( Constants.ITEM_SELECTED_CLIENTE );
		try {
			servicios = ordenServicioService.findAllJbServicios();
		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
	}

	/**
	 * Create contents of the dialog.
	 *
	 * @param parent
	 */
	@Override
	public Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		VerifyListener upperTxt = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		Group grpOrdServ = new Group( container, SWT.NONE );
		grpOrdServ.setBounds( 10, 10, 430, 360 );
		grpOrdServ.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblApPaterno = new Label( grpOrdServ, SWT.NONE );
		lblApPaterno.setBounds( 10, 13, 100, 15 );
		lblApPaterno.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.apellido.paterno" ) );
		lblApPaterno.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtApPaterno = new Text( grpOrdServ, SWT.BORDER );
		txtApPaterno.setBounds( 146, 10, 270, 21 );
		txtApPaterno.setEnabled( false );
		txtApPaterno.setText( cliente.getApellidoPatCli() );

		Label lblApMaterno = new Label( grpOrdServ, SWT.NONE );
		lblApMaterno.setBounds( 10, 40, 100, 15 );
		lblApMaterno.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.apellido.materno" ) );
		lblApMaterno.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtApMaterno = new Text( grpOrdServ, SWT.BORDER );
		txtApMaterno.setBounds( 146, 37, 270, 21 );
		txtApMaterno.setEnabled( false );
		txtApMaterno.setText( cliente.getApellidoMatCli() );

		Label lblNombre = new Label( grpOrdServ, SWT.NONE );
		lblNombre.setBounds( 10, 67, 57, 15 );
		lblNombre.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.nombre" ) );
		lblNombre.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtNombre = new Text( grpOrdServ, SWT.BORDER );
		txtNombre.setBounds( 146, 64, 270, 21 );
		txtNombre.setEnabled( false );
		txtNombre.setText( cliente.getNombreCli() );

		Label lblDejo = new Label( grpOrdServ, SWT.NONE );
		lblDejo.setBounds( 10, 90, 57, 25 );
		lblDejo.setText( ApplicationPropertyHelper.getProperty( "orden.servicio.dejo" ) );
		lblDejo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtDejo = new Text( grpOrdServ, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL );
		txtDejo.setBounds( 146, 90, 270, 65 );

		txtDejo.addVerifyListener( upperTxt );

		Label lblServicio = new Label( grpOrdServ, SWT.NONE );
		lblServicio.setBounds( 10, 160, 57, 25 );
		lblServicio.setText( ApplicationPropertyHelper.getProperty( "orden.servicio.servicio" ) );
		lblServicio.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		cmbVwServ = new ComboViewer( grpOrdServ, SWT.NONE | SWT.READ_ONLY );
		cmbServ = cmbVwServ.getCombo();
		cmbServ.setBounds( 146, 160, 270, 25 );
		cmbVwServ.setContentProvider( new ArrayContentProvider() );
		cmbVwServ.setLabelProvider( new JbServiciosRender() );
		cmbVwServ.setInput( servicios );

		cmbVwServ.addSelectionChangedListener( new ISelectionChangedListener() {
			@Override
			public void selectionChanged( SelectionChangedEvent arg0 ) {
				int index = cmbServ.getSelectionIndex();
				servicio = (JbServicio) cmbVwServ.getElementAt( index );
				enableButtonGuardar();
			}
		} );

		Label lblInstruccion = new Label( grpOrdServ, SWT.NONE );
		lblInstruccion.setBounds( 10, 190, 86, 25 );
		lblInstruccion.setText( ApplicationPropertyHelper.getProperty( "orden.servicio.instruccion" ) );
		lblInstruccion.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtInst = new Text( grpOrdServ, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL );
		txtInst.setBounds( 146, 190, 270, 65 );

		txtInst.addVerifyListener( upperTxt );

		Label lblFechaPromesa = new Label( grpOrdServ, SWT.NONE );
		lblFechaPromesa.setBounds( 10, 260, 100, 25 );
		lblFechaPromesa.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.fecha.promesa" ) );
		lblFechaPromesa.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		dtFeProm = new DateTime( grpOrdServ, SWT.BORDER | SWT.DROP_DOWN );
		dtFeProm.setBounds( 146, 260, 270, 25 );
		dtFeProm.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		// carga la fecha del dia
		org.joda.time.DateTime dt = new org.joda.time.DateTime( new Date() );
		dtFeProm.setDate( dt.getYear(), dt.getMonthOfYear() - 1, dt.getDayOfMonth() );

		dtFeProm.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				if ( calendar == null )
					calendar = Calendar.getInstance();
				calendar.set( dtFeProm.getYear(), dtFeProm.getMonth(), dtFeProm.getDay() );
				fechaPromesa = calendar.getTime();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );

		Label lblCond = new Label( grpOrdServ, SWT.NONE );
		lblCond.setBounds( 10, 290, 130, 25 );
		lblCond.setText( ApplicationPropertyHelper.getProperty( "orden.servicio.condiciones.generales" ) );
		lblCond.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtCond = new Text( grpOrdServ, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL );
		txtCond.setBounds( 146, 290, 270, 65 );

		txtCond.addVerifyListener( upperTxt );

		txtDejo.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				enableButtonGuardar();
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		txtInst.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				enableButtonGuardar();
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		txtCond.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				enableButtonGuardar();
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 450, 460 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( ApplicationPropertyHelper.getProperty( "options.orden.servicio" ) );
	}

	/**
	 * Create contents of the button bar.
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		btnGuardar = createButton( parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, false );
		btnGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		btnGuardar.setText( ApplicationPropertyHelper.getProperty( "button.guardar.label" ) );
		btnGuardar.setEnabled( false );
		Button btnCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		btnCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		btnCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );

		btnGuardar.addMouseListener( new MouseListener() {
			@Override
			public void mouseUp( MouseEvent arg0 ) {
				try {
					// if(MessageDialog)
					JbNota jbNota = new JbNota();
					jbNota = jbNota.setValuesJbNota( String.valueOf( cliente.getIdCliente() ), cliente.getNombreCompleto( false ), txtDejo.getText(), txtInst.getText(), empleado.getIdEmpleado(), servicio.getServicio(), txtCond.getText(), fechaPromesa, new Timestamp( System.currentTimeMillis() ), new Timestamp( System.currentTimeMillis() ), Constants.JBNOTA_TIPO_SERVICIO, Constants.CERO_STRING );

					trabajoService.save( jbNota );

					Jb jb = new Jb();
					jb.setRx( Constants.LETRA_S + jbNota.getIdNota() );
					jb.setEstado( new JbEstado( EstadoConstants.ESTADO_POR_ENVIAR ) );
					jb.setIdCliente( jbNota.getIdCliente() );
					jb.setEmpAtendio( empleado.getIdEmpleado() );
					jb.setNumLlamada( Constants.CERO_INTEGER );
					jb.setMaterial( jbNota.getDejo() );
					jb.setJbTipo( JbTipoConstants.JB_TIPO_OS );
					jb.setFechaPromesa( jbNota.getFechaProm() );
					jb.setCliente( jbNota.getCliente() );
					jb.setNoLlamar( false );
					jb.setFechaVenta( new Timestamp( System.currentTimeMillis() ) );
					jb.setFechaMod( new Timestamp( System.currentTimeMillis() ) );
					jb.setIdMod( Constants.CERO_STRING );

					Session.setAttribute( Constants.ITEM_NEW_ORDEN_SERVICIO, jb );

					// TODO la especificacion dice JB.no_enviar:f pero el campo no existe

					JbTrack track = new JbTrack();
					track.setRx( jb.getRx() );
					track.setEstado( EstadoTrabajo.POR_ENVIAR.codigo() );
					track.setObservaciones( jb.getMaterial() );
					track.setEmpleado( empleado.getIdEmpleado() );
					track.setFecha( new Date() );
					track.setIdMod( "0" );
					track.setIdViaje( null );

					Object[] objectSave = new Object[3];
					objectSave[0] = jbNota;
					objectSave[1] = jb;
					objectSave[2] = track;
					Object[] objectsDelete = new Object[0];

					trabajoService.saveOrUpdateDeleteObjectList( objectSave, objectsDelete );
					trabajoService.updateSaldo( jb.getRx(), BigDecimal.ZERO );
					trabajoImpresion.imprimirOrdenServicio( jbNota, empleado, jb, cliente, empleado.getSucursal(), true );
					trabajoImpresion.imprimirOrdenServicio( jbNota, empleado, jb, cliente, empleado.getSucursal(), true );
				} catch ( PrinterException pe ) {
					MessageBox mb = new MessageBox( shell, SWT.ERROR );
					System.out.println( pe.getMessage() );
					mb.setMessage( pe.getMessage() );
					mb.open();
				} catch ( ApplicationException e ) {
					e.printStackTrace();
				}

				FormaContactoDialog dialog = new FormaContactoDialog( shell );
				dialog.open();

			}

			@Override
			public void mouseDown( MouseEvent arg0 ) {
			}

			@Override
			public void mouseDoubleClick( MouseEvent arg0 ) {
			}
		} );
	}

	private void enableButtonGuardar() {
		btnGuardar.setEnabled( !txtDejo.getText().isEmpty() && !txtInst.getText().isEmpty() && !txtCond.getText().isEmpty() && servicio != null );
	}
}

package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.GParametroService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.JbGrupoService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.dto.ReprogramarDTO;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.DateUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import java.sql.Timestamp;
import java.util.Date;

public class ViajeDialog extends Dialog {

	public static TrabajoService trabajoService;
	public static GParametroService gParametroService;
	public static JbGrupoService jbGrupoService;

	static {
		jbGrupoService = (JbGrupoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_JB_GRUPO );
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
		gParametroService = (GParametroService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_GPAREMETRO_SERVICE );
	}

	private Jb jb;
	private JbGrupo jbGrupo;
	private JbTrack jbTrack;
	private GParametro parametroVolverContactar;
	private ContactoView contactoView;

	private ReprogramarDTO reprogramarDTO;
	;
	private Boolean grupo;
	private Shell shell;
	private Text txtNumViaje;
	private DateTime dtFecha;
	private Button btnImprimir;
	private Date today = new Date();


	public ViajeDialog( Shell parentShell ) {
		super( parentShell );
		shell = parentShell;
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		container.setBounds( 5, 5, 495, 555 );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group group_1 = new Group( container, SWT.NONE );
		group_1.setBounds( 10, 10, 255, 66 );
		group_1.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblNumViaje = new Label( group_1, SWT.NONE );
		lblNumViaje.setText( "Número de Viaje" );
		lblNumViaje.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblNumViaje.setBounds( 10, 36, 115, 20 );
		lblNumViaje.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		dtFecha = new DateTime( group_1, SWT.BORDER | SWT.DROP_DOWN );
		dtFecha.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		dtFecha.setBounds( 126, 10, 119, 20 );

		dtFecha.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				String fecha = DateUtils.builToTimestampFormat( dtFecha.getYear(), dtFecha.getMonth(), dtFecha.getDay(), dtFecha.getHours(), dtFecha.getMinutes(), dtFecha.getSeconds() );
				Timestamp timestamp = Timestamp.valueOf( fecha );

				Integer daysBetween = DateUtils.daysBetweenCeilDayHours( new Date(), timestamp );

				if ( daysBetween < 0 ) {
					btnImprimir.setEnabled( false );
				} else {
					btnImprimir.setEnabled( true );
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {

			}
		} );

		txtNumViaje = new Text( group_1, SWT.BORDER );
		txtNumViaje.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtNumViaje.setBounds( 126, 36, 119, 20 );
		txtNumViaje.setTextLimit( 4 );
		//		txtNumViaje.setText(parametroVolverContactar.getValor());


		txtNumViaje.addKeyListener( new KeyListener() {

			@Override
			public void keyReleased( KeyEvent arg0 ) {
				if ( !txtNumViaje.getText().isEmpty() && StringUtils.isNumeric( txtNumViaje.getText() ) ) {
					btnImprimir.setEnabled( true );
				} else {
					btnImprimir.setEnabled( false );
				}
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {

			}
		} );
		Label lblFecha = new Label( group_1, SWT.NONE );
		lblFecha.setText( "Fecha" );
		lblFecha.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblFecha.setBounds( 10, 10, 57, 20 );
		lblFecha.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );


		//		org.joda.time.DateTime today = DateUtils.addDays(new Date(), txtNumViaje.getText());
		//		dtFecha.setDate(today.getYear(), today.getMonthOfYear()-1, today.getDayOfMonth());

		//		txtNumViaje.addKeyListener(new DateTimeTextUpdaterKeyListener(dtFecha, txtNumViaje, btnImprimir));
		//		dtFecha.addSelectionListener(new DateTimeTextUpdaterSelectionListener(dtFecha, txtNumViaje, btnImprimir));

		return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 287, 174 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		//		newShell.setText(TrabajosPropertyHelper.getProperty("trabajos.window.dialog.title.reprogramar"));
		/*newShell.setText(
				grupo?
						TrabajosPropertyHelper.getProperty("trabajos.label.grupo.dos.puntos")
						+ " " +
						RxConstants.TIPO_GRUPO+jbGrupo.getIdGrupo()
						:
							TrabajosPropertyHelper.getProperty("trabajos.label.rx.dos.puntos")
							+ " " +
							jb.getRx());*/
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
		btnImprimir = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.OK_LABEL, false );
		btnImprimir.setImage( ControlTrabajosWindowElements.printIcon );
		btnImprimir.setText( ApplicationPropertyHelper.getProperty( "button.imprimir.label" ) );
		btnImprimir.setEnabled( true );
		btnImprimir.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				if ( MessageDialog.openConfirm( shell, MessagesPropertyHelper.getProperty( "guardar.title" ), MessagesPropertyHelper.getProperty( "guardar.message.01" ) ) ) {

					jbTrack.setRx( (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX ) );
					jbTrack.setEstado( EstadoTrabajo.NO_CONTACTAR.codigo() );
					Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
					jbTrack.setEmpleado( empleado.getIdEmpleado() );
					jbTrack.setFecha( new Timestamp( System.currentTimeMillis() ) );
					jbTrack.setIdMod( empleado.getIdEmpleado() );
					jbTrack.setIdViaje( Constants.CADENA_VACIA );

					try {
						if ( grupo ) {
							trabajoService.saveJbAndJbGrupo( jb, jbGrupo );
						} else {
							trabajoService.saveJbAndTrack( jb, jbTrack );
						}


					} catch ( ApplicationException e ) {
						e.printStackTrace();
					}
				}

			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {

			}
		} );
	}
}

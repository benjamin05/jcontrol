package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.ReprogramarService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.GParametroService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.JbGrupoService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.dto.ReprogramarDTO;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.DateUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.*;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.DateTimeTextUpdaterKeyListener;
import mx.com.lux.control.trabajos.view.listener.DateTimeTextUpdaterSelectionListener;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class ReprogramarDialog extends Dialog {

	public static TrabajoService trabajoService;
	public static GParametroService gParametroService;
	public static JbGrupoService jbGrupoService;
	public static ReprogramarService reprogramarService;

	static {
		jbGrupoService = (JbGrupoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_JB_GRUPO );
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
		gParametroService = (GParametroService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_GPAREMETRO_SERVICE );
		reprogramarService = (ReprogramarService) ServiceLocator.getBean( "reprogramarService" );
	}

	private Calendar fechaVolverContactar = Calendar.getInstance();

	private Jb jb;
	private JbGrupo jbGrupo;
	private JbTrack jbTrack;
	private GParametro parametroVolverContactar;
	private ContactoView contactoView;

	private ReprogramarDTO reprogramarDTO;

	private Boolean grupo;
	private Shell shell;
	private Text txtRx;
	private Text txtCliente;
	private Text txtTipoContacto;
	private Text txtContacto;
	private Text txtDias;
	private DateTime dtVolvercontactar;
	private Button buttonGuardar;

	private Date today = new Date();
	private Calendar calendar;
	private String fecha;
    private Date fechaVolverLlamar;

    private Integer dias;

	public ReprogramarDialog( Shell parentShell ) {
		super( parentShell );
		shell = parentShell;
		jbTrack = new JbTrack();
		try {
			reprogramarDTO = (ReprogramarDTO) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_REPROGRAMAR_DTO );
			String rx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
			parametroVolverContactar = gParametroService.findById( GParametroConstants.PARAMETRO_VOLVER_CONTACTAR );

			grupo = false;
			jb = trabajoService.findById( rx );

            fechaVolverLlamar = jb.getVolverLlamar();

		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		container.setBounds( 5, 5, 495, 555 );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group group = new Group( container, SWT.NONE );
		group.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		group.setBounds( 10, 10, 505, 121 );
		group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblRx = new Label( group, SWT.NONE );
		lblRx.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.rx" ) );
		lblRx.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblRx.setBounds( 10, 10, 110, 20 );
		lblRx.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtRx = new Text( group, SWT.BORDER );
		txtRx.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtRx.setEditable( false );
		txtRx.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtRx.setBounds( 126, 10, 116, 20 );
		txtRx.setText( grupo ? RxConstants.TIPO_GRUPO_G + jbGrupo.getIdGrupo() : jb.getRx() );

		Label lblCliente = new Label( group, SWT.NONE );
		lblCliente.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente" ) );
		lblCliente.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblCliente.setBounds( 10, 36, 110, 20 );
		lblCliente.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtCliente = new Text( group, SWT.BORDER );
		txtCliente.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtCliente.setEditable( false );
		txtCliente.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtCliente.setBounds( 126, 36, 370, 20 );
		txtCliente.setText( grupo ? jbGrupo.getNombreGrupo() : jb.getCliente() );

		Label lblTipoDeContacto = new Label( group, SWT.NONE );
		lblTipoDeContacto.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.contacto.tipo" ) );
		lblTipoDeContacto.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblTipoDeContacto.setBounds( 10, 62, 110, 20 );
		lblTipoDeContacto.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		FormaContacto formaContacto = trabajoService.obtenerFormaTrabajoPorRx( jb.getRx() );

		txtTipoContacto = new Text( group, SWT.BORDER );
		txtTipoContacto.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtTipoContacto.setEditable( false );
		txtTipoContacto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtTipoContacto.setBounds( 126, 62, 370, 20 );
		txtTipoContacto.setText( formaContacto != null && formaContacto.getTipoContacto() != null ? formaContacto.getTipoContacto().getDescripcion() : "" );

		Label lblContacto = new Label( group, SWT.NONE );
		lblContacto.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.contacto" ) );
		lblContacto.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblContacto.setBounds( 10, 88, 110, 20 );
		lblContacto.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtContacto = new Text( group, SWT.BORDER );
		txtContacto.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtContacto.setEditable( false );
		txtContacto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtContacto.setBounds( 126, 88, 370, 20 );
		txtContacto.setText( formaContacto != null ? formaContacto.getContacto() : "" );

		Group group_1 = new Group( container, SWT.NONE );
		group_1.setBounds( 10, 137, 505, 39 );
		group_1.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblVolvercontactar = new Label( group_1, SWT.NONE );
		lblVolvercontactar.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.contactar.volver" ) );
		lblVolvercontactar.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblVolvercontactar.setBounds( 10, 10, 115, 20 );
		lblVolvercontactar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		dtVolvercontactar = new DateTime( group_1, SWT.BORDER | SWT.DROP_DOWN );
		dtVolvercontactar.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		dtVolvercontactar.setBounds( 126, 10, 119, 20 );

		dtVolvercontactar.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				fecha = getDateTimeFromPicker();
				Timestamp timestamp = Timestamp.valueOf( fecha );
				Integer daysBetween = DateUtils.daysBetweenCeilDayHours( new Date(), timestamp );
				if ( daysBetween < 0 ) {
					buttonGuardar.setEnabled( false );
				} else {
					buttonGuardar.setEnabled( true );
                    txtDias.setText( daysBetween.toString() );
				}
                dias = Integer.parseInt( txtDias.getText() );
                txtDias.setText( dias.toString() );
				fechaVolverContactar.set( Calendar.YEAR, dtVolvercontactar.getYear() );
				fechaVolverContactar.set( Calendar.MONTH, dtVolvercontactar.getMonth() );
				fechaVolverContactar.set( Calendar.DAY_OF_MONTH, dtVolvercontactar.getDay() );
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {

			}
		} );

		Label lblO = new Label( group_1, SWT.NONE );
		lblO.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.o" ) );
		lblO.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblO.setAlignment( SWT.CENTER );
		lblO.setBounds( 254, 10, 30, 20 );
		lblO.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtDias = new Text( group_1, SWT.BORDER );
		txtDias.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtDias.setBounds( 297, 10, 57, 20 );
		txtDias.setTextLimit( 4 );

        if ( fechaVolverLlamar == null ) {
            txtDias.setText(parametroVolverContactar.getValor());
        }else {
            Integer diasDif = DateUtils.daysBetweenCeilDayHours(new Date(), fechaVolverLlamar);

            if (diasDif <= 0) {
                txtDias.setText(parametroVolverContactar.getValor());
            } else {
                txtDias.setText(diasDif.toString());
            }
        }


		txtDias.addKeyListener( new KeyListener() {

			@Override
			public void keyReleased( KeyEvent arg0 ) {
				if ( !txtDias.getText().isEmpty() && StringUtils.isNumeric( txtDias.getText() ) ) {
					fecha = getDateTimeFromPicker();
                    dias = Integer.parseInt( txtDias.getText() );
                    txtDias.setText( dias.toString() );
					buttonGuardar.setEnabled( true );
				} else {
					buttonGuardar.setEnabled( false );
				}
				/*fechaVolverContactar.set( Calendar.YEAR, dtVolvercontactar.getYear() );
				fechaVolverContactar.set( Calendar.MONTH, dtVolvercontactar.getMonth() );
				fechaVolverContactar.set( Calendar.DAY_OF_MONTH, dtVolvercontactar.getDay() );*/
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {

			}
		} );
		Label lblDias = new Label( group_1, SWT.NONE );
		lblDias.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.dias" ) );
		lblDias.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblDias.setBounds( 367, 10, 57, 20 );
		lblDias.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		org.joda.time.DateTime today = DateUtils.addDays( new Date(), txtDias.getText() );
		dtVolvercontactar.setDate( today.getYear(), today.getMonthOfYear() - 1, today.getDayOfMonth() );

		txtDias.addKeyListener( new DateTimeTextUpdaterKeyListener( dtVolvercontactar, txtDias, buttonGuardar ) );
		dtVolvercontactar.addSelectionListener( new DateTimeTextUpdaterSelectionListener( dtVolvercontactar, txtDias, buttonGuardar ) );

        fechaVolverContactar.set( Calendar.YEAR, dtVolvercontactar.getYear() );
		fechaVolverContactar.set( Calendar.MONTH, dtVolvercontactar.getMonth() );
		fechaVolverContactar.set( Calendar.DAY_OF_MONTH, dtVolvercontactar.getDay() );


		return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 530, 270 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( grupo ? Constants.GRUPO_DOS_PUNTOS + " " + RxConstants.TIPO_GRUPO_G + jbGrupo.getIdGrupo() : Constants.RX_DOS_PUNTOS + " " + jb.getRx() );
	}

	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Button buttonCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		buttonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		buttonCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );

        //final Integer dayRep = Integer.parseInt( txtDias.getText() );
        //final Integer day = dtVolvercontactar.getDay();
        buttonGuardar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.OK_LABEL, false );
		buttonGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		buttonGuardar.setText( ApplicationPropertyHelper.getProperty( "button.guardar.label" ) );
		buttonGuardar.setEnabled( true );
		buttonGuardar.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
                try {
                    fechaVolverContactar.setTime( new Date() );
                    fechaVolverContactar.add( Calendar.DAY_OF_MONTH, dias );
                    //reprogramarService.reprogramarContactoGrupo( jb.getRx(), fechaVolverContactar.getTime(), jbGrupo );
                    reprogramarService.reprogramarContactoGrupo( jb.getRx(), fechaVolverContactar.getTime(), jbGrupo );
                    if ( grupo ) {
					} else {
						//reprogramarService.reprogramarContacto( jb.getRx(), fechaVolverContactar.getTime() );
                        reprogramarService.reprogramarContacto( jb.getRx(), fechaVolverContactar.getTime() );
					}
				} catch ( ApplicationException e ) {
					e.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	private String getDateTimeFromPicker() {
		String dateFormatTimestamp = DateUtils.builToTimestampFormat( dtVolvercontactar.getYear(), dtVolvercontactar.getMonth(), dtVolvercontactar.getDay(), dtVolvercontactar.getHours(), dtVolvercontactar.getMinutes(), dtVolvercontactar.getSeconds() );
		return dateFormatTimestamp;
	}
}

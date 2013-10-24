package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.envio.EnvioService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.RecetaService;
import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.data.vo.Receta;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.DateUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

public class ReimpresionPackingDialog extends Dialog {

    private final Logger log = Logger.getLogger( EnvioService.class );
    public static EnvioService envioService;

    static{
        envioService = (EnvioService) ServiceLocator.getBean( EnvioService.class );
    }

	private Group group;
	private Label lblViaje;
	private Text txtViaje;
    private Label lblFecha;
    private Text txtFecha;
    private Label lblMensaje;
    private String fecha;
    private String viaje;
    private static final String MSJ_BUSQUEDA_INCORRECTA = "No se encontro el packing";

    private Shell shell;
    private JbTrack jbTrack;

	public ReimpresionPackingDialog( Shell parentShell ) {
        super( parentShell );
        shell = parentShell;
        jbTrack = new JbTrack();
        this.fecha = DateUtils.formatDate( envioService.obtenerUltimoPackingCerrado().getId().getFecha(), "dd/MM/yyyy" );
        this.viaje = envioService.obtenerUltimoPackingCerrado().getId().getIdViaje();
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		container.setBounds( 5, 5, 520, 610 );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

        group = new Group( container, SWT.SHADOW_OUT );
        group.setBounds( 10, 10, 305, 88 );
        group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

        lblViaje = new Label( group, SWT.NONE );
        lblViaje.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.viaje" ) );
        lblViaje.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
        lblViaje.setBounds( 10, 10, 40, 20 );
        lblViaje.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

        txtViaje = new Text( group, SWT.BORDER );
        txtViaje.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
        txtViaje.setEditable( true );
        txtViaje.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        txtViaje.setBounds( 109, 10, 80, 20 );
        txtViaje.setText( this.viaje );

        lblFecha = new Label( group, SWT.NONE );
        lblFecha.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.fecha" ) );
        lblFecha.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
        lblFecha.setBounds( 10, 40, 40, 20 );
        lblFecha.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

        txtFecha = new Text( group, SWT.BORDER );
        txtFecha.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
        txtFecha.setEditable( true );
        txtFecha.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        txtFecha.setBounds( 109, 40, 116, 20 );
        txtFecha.setText( this.fecha );

        lblMensaje = new Label( group, SWT.NONE );
        lblMensaje.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.ERROR ) );
        lblMensaje.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        lblMensaje.setBounds( 10, 60, 293, 20 );

		return container;
	}

	@Override
	protected Point getInitialSize() {
        return new Point( 330, 200 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( Constants.REIMPRIMIR_PACKING_DOS_PUNTOS );
	}

	/**
	 * Create contents of the button bar.
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar( Composite parent ) {

        Button buttonImprimir = createButton( parent, IDialogConstants.STOP_ID, IDialogConstants.NO_LABEL, false );
        buttonImprimir.setImage( ControlTrabajosWindowElements.printIcon );
        buttonImprimir.setText( ApplicationPropertyHelper.getProperty( "button.imprimir.label" ) );
        buttonImprimir.addSelectionListener( new SelectionListener() {

            @Override
            public void widgetSelected( SelectionEvent arg0 ) {
                lblMensaje.setText( "" );
                log.debug( "widgetSelected( SelectionEvent arg0 )" );
                String viaje = txtViaje.getText();
                String fecha = txtFecha.getText();
                try{
                    if( StringUtils.trimToEmpty(viaje).length() > 0 && StringUtils.trimToEmpty(fecha).length() > 0 ){
                        String print = envioService.imprimirPackingCerrado( viaje, fecha );
                        if( print == null ){
                            lblMensaje.setText( MSJ_BUSQUEDA_INCORRECTA );
                        }
                    }
                } catch ( ApplicationException ap ){
                    System.out.println( ap );
                }
            }

            @Override
            public void widgetDefaultSelected( SelectionEvent selectionEvent ) {
            }
        });

		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Button buttonCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		buttonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		buttonCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
	}
}

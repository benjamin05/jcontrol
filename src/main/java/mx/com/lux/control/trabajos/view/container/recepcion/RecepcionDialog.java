package mx.com.lux.control.trabajos.view.container.recepcion;

import mx.com.lux.control.trabajos.bussiness.RecepcionBusiness;
import mx.com.lux.control.trabajos.bussiness.service.contacto.ContactoViewService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.JbGrupoService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.RecetaService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.SucursalService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.*;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.ContactoPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.RecepcionPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class RecepcionDialog extends Dialog {

	private static final TrabajoService trabajoService;
	private static final RecetaService recetaService;
	private static final SucursalService sucursalService;
    private static final ContactoViewService contactoViewService;
    private static final JbGrupoService jbGrupoService;

    @Resource
    private RecepcionBusiness recepcionBusiness;

    private ContactoView currentContactoView = new ContactoView();
    private Jb currentJb = new Jb();
    private boolean realizado = true;
	private Integer idViaje;
	private Jb jb;
	private Shell shell;
	private RecepcionDialog self;
    private String rx;
    private boolean grupo = false;
    private List<Jb> listaTrabajosEnGrupo = new ArrayList<Jb>();
    private JbLlamada currentJbLlamada = new JbLlamada();
    private Empleado empleado = new Empleado();
    private String strInfo;

	static {
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
		recetaService = (RecetaService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_RECETA_SERVICE );
		sucursalService = (SucursalService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_SUCURSAL_SERVICE );
        contactoViewService = (ContactoViewService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_CONTACTO_SERVICE );
        jbGrupoService = (JbGrupoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_JB_GRUPO );
	}

	public RecepcionDialog( Shell parentShell, String rx ) {
		super( parentShell );
        this.rx = StringUtils.trimToEmpty(rx);
		shell = parentShell;
		self = this;
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		container.setLayout( new FillLayout() );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group grupo = new Group( container, SWT.NONE );
		grupo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout layout = new GridLayout( 2, false );
		layout.marginHeight = 10;
		layout.marginWidth = 20;
		grupo.setLayout( layout );

		Label lblCliente = new Label( grupo, SWT.NONE );
		lblCliente.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblCliente.setText( "Cliente" );

		Text txtCliente = new Text( grupo, SWT.BORDER );
		txtCliente.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		txtCliente.setEnabled( false );
		txtCliente.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		Label lblMaterial = new Label( grupo, SWT.NONE );
		lblMaterial.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblMaterial.setText( "Material" );

		Text txtMaterial = new Text( grupo, SWT.BORDER );
		txtMaterial.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		txtMaterial.setEnabled( false );
		txtMaterial.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		Label lblFechaVenta = new Label( grupo, SWT.NONE );
		lblFechaVenta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblFechaVenta.setText( "Fecha Venta" );

		Text txtFechaVenta = new Text( grupo, SWT.BORDER );
		txtFechaVenta.setEnabled( false );
		txtFechaVenta.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		txtFechaVenta.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		Label lblFechaPromesa = new Label( grupo, SWT.NONE );
		lblFechaPromesa.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblFechaPromesa.setText( "Fecha Promesa" );

		Text txtFechaPromesa = new Text( grupo, SWT.BORDER );
		txtFechaPromesa.setEnabled( false );
		txtFechaPromesa.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		txtFechaPromesa.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		Label lblObservacionesRx = new Label( grupo, SWT.NONE );
		lblObservacionesRx.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblObservacionesRx.setText( "Observaciones Rx" );

		Text txtObservacionesRx = new Text( grupo, SWT.BORDER | SWT.WRAP | SWT.MULTI );
		txtObservacionesRx.setEnabled( false );
		GridData txtObservacionesRxGridData = new GridData( SWT.FILL, SWT.FILL, true, false );
		txtObservacionesRxGridData.heightHint = 60;
		txtObservacionesRx.setLayoutData( txtObservacionesRxGridData );
		txtObservacionesRx.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		Label lblObservacionesFactura = new Label( grupo, SWT.NONE );
		lblObservacionesFactura.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblObservacionesFactura.setText( "Observaciones Factura" );

		Text txtObservacionesFactura = new Text( grupo, SWT.BORDER | SWT.WRAP | SWT.MULTI );
		txtObservacionesFactura.setEnabled( false );
		GridData txtObservacionesFacturaGridData = new GridData( SWT.FILL, SWT.FILL, true, false );
		txtObservacionesFacturaGridData.heightHint = 60;
		txtObservacionesFactura.setLayoutData( txtObservacionesFacturaGridData );
		txtObservacionesFactura.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		Label lblExterno = new Label( grupo, SWT.NONE );
		lblExterno.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblExterno.setText( "Externo" );

		Button chkExterno = new Button( grupo, SWT.CHECK );
		chkExterno.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		chkExterno.setEnabled( false );

		Label lblSucursalDestino = new Label( grupo, SWT.NONE );
		lblSucursalDestino.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		lblSucursalDestino.setText( "Sucursal Destino" );

		ComboViewer comboViewer = new ComboViewer( grupo, SWT.NONE );
		Combo sucursalesCombo = comboViewer.getCombo();
		sucursalesCombo.setEnabled( false );
		sucursalesCombo.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		sucursalesCombo.setBackground( GraphicConstants.FIELD_COLOR_READONLY );

		try {
			SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );
			jb = (Jb) Session.getAttribute( Constants.ITEM_SELECTED_JB );
			idViaje = (Integer) Session.getAttribute( Constants.ITEM_SELECTED_ID_VIAJE );
			txtCliente.setText( jb.getCliente() );
			txtMaterial.setText( StringUtils.trimToEmpty(jb.getMaterial()) );
			txtFechaPromesa.setText( df.format( jb.getFechaPromesa() ) );

			if ( jb.getFechaVenta() != null ) {
				txtFechaVenta.setText( df.format( jb.getFechaVenta() ) );
			}

			if ( StringUtils.isNotBlank( jb.getExterno() ) ) {
				chkExterno.setSelection( true );
				Sucursal sucursal = sucursalService.findById( Integer.parseInt( jb.getExterno() ) );
				sucursalesCombo.setText( sucursal.getNombre() );
			}

			NotaVenta nv = trabajoService.findNotaVentaByFactura( jb.getRx() );
			if ( nv != null ) {
				txtObservacionesFactura.setText( nv.getObservacionesNv() );
				Receta receta = recetaService.findById( nv.getReceta() );
				if ( receta != null ) {
					txtObservacionesRx.setText( StringUtils.trimToEmpty( receta.getObservacionesR() ) );
				}
			}
		} catch ( ApplicationException ae ) {
			ae.printStackTrace();
		}

		return container;
	}

	@Override
	protected void createButtonsForButtonBar( final Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Button btnSatisfactorio = createButton( parent, IDialogConstants.YES_TO_ALL_ID, IDialogConstants.YES_TO_ALL_LABEL, false );
		btnSatisfactorio.setText( RecepcionPropertyHelper.getProperty( "recepcion.dialog.button.satisfactorio" ) );
		btnSatisfactorio.setImage( ControlTrabajosWindowElements.aceptarIcon );
		btnSatisfactorio.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );

		Button btnNoSatisfactorio = createButton( parent, IDialogConstants.YES_TO_ALL_ID, IDialogConstants.YES_TO_ALL_LABEL, false );
		btnNoSatisfactorio.setText( RecepcionPropertyHelper.getProperty( "recepcion.dialog.button.no.satisfactorio" ) );
		btnNoSatisfactorio.setImage( ControlTrabajosWindowElements.cancelarIcon );
		btnNoSatisfactorio.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );

		Button buttonCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true );
		buttonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		buttonCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );

		btnSatisfactorio.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				try {
                    trabajoService.trabajoSatisfactorio( jb, idViaje );
                    enviaSms();
					parent.getShell().dispose();
					ApplicationUtils.recargarDatosPestanyaVisible();
				} catch ( ApplicationException e1 ) {
					e1.printStackTrace();
				}
			}
		} );

		btnNoSatisfactorio.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				NoSatisfactorioDialog noSatisfactorioDialog = new NoSatisfactorioDialog( shell );
				noSatisfactorioDialog.open();
				ApplicationUtils.recargarDatosPestanyaVisible();
				self.close();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 480, 460 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( RecepcionPropertyHelper.getProperty( "recepcion.dialog.title" ) );
	}


    public void enviaSms() throws DAOException {
        String estado = "";
        if ( realizado ) {
            boolean esContactoSMS = false;
            if ( esTipoContactoSMS( rx ) ) {
                esContactoSMS = true;
                strInfo = TrabajosPropertyHelper.getProperty("trabajos.msg.sms.envio");
                estado = ContactoPropertyHelper.getProperty( "contacto.estado.entregar.ae" );
            }

            empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
            currentJb = trabajoService.findById( rx );
            currentJb.setNumLlamada( currentJb.getNumLlamada() != null ? ( currentJb.getNumLlamada() + 1 ) : 1 );
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date()); // Configuramos la fecha que se recibe
            calendar.add(Calendar.DAY_OF_YEAR, 8);
            currentJb.setVolverLlamar(calendar.getTime());

            if ( StringUtils.trimToEmpty(currentJb.getIdGrupo()).startsWith(RxConstants.TIPO_GRUPO_F) ) {
                try{
                listaTrabajosEnGrupo = jbGrupoService.obtenTrabajosEnGrupo( StringUtils.trimToEmpty(currentJb.getIdGrupo()) );
                grupo = true;
                } catch ( ApplicationException e1 ) {
                    e1.printStackTrace();
                }
            }
            Boolean grupoEntregado = false;
            Jb jbGrupo = trabajoService.findById( StringUtils.trimToEmpty(currentJb.getIdGrupo()) );
            if( jbGrupo != null && StringUtils.trimToEmpty(jbGrupo.getEstado().getIdEdo()).equalsIgnoreCase("RS") ){
                grupoEntregado = true;
            }
            try{
                currentJbLlamada = trabajoService.findJbLlamadaById( StringUtils.trimToEmpty(currentJb.getIdGrupo()) );
            } catch ( ApplicationException e ) { System.out.println( e );}
            if ( grupo && grupoEntregado ) {
                currentJb = trabajoService.findById( StringUtils.trimToEmpty(currentJb.getIdGrupo()) );
                calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_YEAR, 8);
                currentJb.setVolverLlamar(calendar.getTime());
                if ( listaTrabajosEnGrupo != null && !listaTrabajosEnGrupo.isEmpty() ) {
                    Object[] objRealizado = new Object[listaTrabajosEnGrupo.size() + 2];
                    objRealizado[0] = currentJb;
                    objRealizado[1] = currentJbLlamada;

                    if ( esContactoSMS ) {
                        java.util.List<Jb> listaTrabajosContacto = new ArrayList<Jb>();
                        listaTrabajosContacto.add( currentJb );
                        listaTrabajosContacto.addAll( listaTrabajosEnGrupo );
                        for ( Jb trabajoContacto : listaTrabajosContacto ) {
                            String rx = trabajoContacto.getRx();
                            if ( rx.startsWith( "F" ) ) {
                                // Solo generamos el acuse para el RX del grupo
                                try {
                                    contactoViewService.registraContactoSMS( rx, true );
                                } catch ( ApplicationException ex ) {
                                    Status status = new Status( IStatus.ERROR, "JSOI", 0, "Error al registrar contacto SMS", null );
                                    ErrorDialog.openError(shell, "Error", "Error", status);
                                }

                            }
                        }
                        MessageDialog.openInformation(getShell(), StringUtils.defaultIfBlank(strInfo, ""), TrabajosPropertyHelper.getProperty("trabajos.msg.sms.info"));
                    }

                    int i = 2;
                    for ( Jb jb : listaTrabajosEnGrupo ) {
                        JbTrack jbTrack = new JbTrack();
                        jbTrack.setRx( jb.getRx() );
                        jbTrack.setEstado( estado );
                        jbTrack.setObservaciones( StringUtils.defaultIfBlank( strInfo, "" ) );
                        jbTrack.setEmpleado( empleado.getIdEmpleado() );
                        jbTrack.setIdViaje( null );
                        jbTrack.setFecha( new Date() );
                        jbTrack.setIdMod( "0" );
                        objRealizado[i++] = jbTrack;
                    }
                    try {
                        jbGrupoService.saveGrupoTrackContactoRealizado(objRealizado);
                    } catch ( ApplicationException e ) {
                        System.out.println("Error al guardar Grupo Track Contacto realizado: " + e.getMessage());
                    }
                }
            } else if( !grupo ) {
                if ( esContactoSMS ) {
                    try {
                        contactoViewService.registraContactoSMS( rx, true );
                        MessageDialog.openInformation( getShell(), strInfo, TrabajosPropertyHelper.getProperty( "trabajos.msg.sms.info" ) );
                    } catch ( ApplicationException ex ) {
                        Status status = new Status( IStatus.ERROR, "JSOI", 0, "Error al registrar contacto SMS", null );
                        ErrorDialog.openError( shell, "Error", "Error", status );
                    }

                    JbTrack currentJbt = new JbTrack();
                    currentJbt.setRx( rx );
                    currentJbt.setEstado( estado );
                    currentJbt.setObservaciones( StringUtils.defaultIfBlank( strInfo, "" ) );
                    currentJbt.setEmpleado( empleado.getIdEmpleado() );
                    currentJbt.setIdViaje( null );
                    currentJbt.setFecha( new Date() );
                    currentJbt.setIdMod( "0" );
                    Object[] objRealizado = { currentJbLlamada, currentJbt, currentJb };
                    saveContactoTrackJb(objRealizado);
                }

            }
        }
    }

    private boolean esTipoContactoSMS( final String rx ) {
        FormaContacto formaContacto = contactoViewService.obtenFormaContactoDeRx( rx );
        return ( formaContacto != null ) && ( formaContacto.getTipoContacto().getIdTipoContacto() == 3 );
    }

    private void saveContactoTrackJb( Object[] o ) {
        try {
            trabajoService.deleteInsertUpdate( o );
        } catch ( ApplicationException e ) {
            e.printStackTrace();
        }
    }

    private void updateJbVolverLlamar( Object[] o ) {
        try {
            trabajoService.deleteInsertUpdate( o );
        } catch ( ApplicationException e ) {
            e.printStackTrace();
        }
    }
}

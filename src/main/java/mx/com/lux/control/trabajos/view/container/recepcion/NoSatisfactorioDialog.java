package mx.com.lux.control.trabajos.view.container.recepcion;

import mx.com.lux.control.trabajos.bussiness.service.impresora.TrabajoImpresion;
import mx.com.lux.control.trabajos.bussiness.service.sistema.login.LoginService;
import mx.com.lux.control.trabajos.bussiness.service.sobres.SobreService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.DateUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.*;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import java.sql.Timestamp;
import java.util.*;
import java.util.List;

public class NoSatisfactorioDialog extends Dialog {

	private static final TrabajoService trabajoService;
	private static final TrabajoImpresion trabajoImpresion;
	private static final LoginService loginService;
    private static final SobreService sobreService;

	private Jb jb = new Jb();
	private JbRoto jbRoto = new JbRoto();
    private JbSobre jbSobre;
	private JbRoto jbRotoTemp = new JbRoto();
	private JbLlamada jbLlamadaExist = new JbLlamada();
	private JbLlamada jbLlamada = new JbLlamada();
	private JbTrack jbTrackRecibeSuc = new JbTrack();
	private JbTrack jbTrackRotoEnviar = new JbTrack();
	private Empleado empleado;
	private NotaVenta notaVenta = new NotaVenta();
	private Shell shell;
	private Text txtCausaRoto;
	private Text txtResponsable;
	private Text txtMaterial;
	private Button btnArmazon;
	private Button btnLente;
	private Button btnSi;
	private Button btnNo;
	private Button buttonGuardar;
	private DateTime dtFePromesa;
	private String rx;
	private String fecha;
	private Integer numViaje;
	private Calendar calendar;
	private Object[] saveUpdateObjs = new Object[6];
	private Object[] delObjs;
	private Label labelNombreResponsable;

	static {
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
		trabajoImpresion = (TrabajoImpresion) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_IMPRESION );
		loginService = (LoginService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_LOGIN_SERVICE );
        sobreService = (SobreService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_SOBRE_SERVICE );
	}


	public NoSatisfactorioDialog( Shell parentShell ) {
		super( parentShell );
		shell = parentShell;
		jb = (Jb) Session.getAttribute( Constants.ITEM_SELECTED_JB );
		rx = jb.getRx();
		empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
		numViaje = (Integer) Session.getAttribute( Constants.ITEM_SELECTED_ID_VIAJE );
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		container.setBounds( 5, 5, 495, 555 );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group group = new Group( container, SWT.SHADOW_OUT );
		group.setForeground( SWTResourceManager.getColor( SWT.COLOR_BLUE ) );
		group.setBounds( 10, 10, 656, 161 );
		group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblTipRoto = new Label( group, SWT.NONE );
		lblTipRoto.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.tipo.roto" ) );
		lblTipRoto.setBounds( 21, 21, 76, 15 );
		lblTipRoto.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		btnArmazon = new Button( group, SWT.RADIO );
		btnArmazon.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.armazon" ) );
		btnArmazon.setBounds( 116, 21, 86, 22 );
		btnArmazon.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		btnLente = new Button( group, SWT.RADIO );
		btnLente.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.lente" ) );
		btnLente.setBounds( 208, 21, 70, 22 );
		btnLente.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblCausaRoto = new Label( group, SWT.NONE );
		lblCausaRoto.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.causa.roto" ) );
		lblCausaRoto.setBounds( 21, 56, 76, 15 );
		lblCausaRoto.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtCausaRoto = new Text( group, SWT.BORDER );
		txtCausaRoto.setBounds( 116, 53, 510, 20 );
		txtCausaRoto.setFocus();

		Label lblResponsable = new Label( group, SWT.NONE );
		lblResponsable.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.responsable" ) );
		lblResponsable.setBounds( 21, 94, 89, 15 );
		lblResponsable.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtResponsable = new Text( group, SWT.BORDER );
		txtResponsable.setBounds( 116, 91, 50, 20 );
		txtResponsable.setTextLimit( Constants.LONGITUD_ID_EMPLEADO );

		labelNombreResponsable = new Label( group, SWT.NONE );
		labelNombreResponsable.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		labelNombreResponsable.setBounds( 180, 91, 450, 20 );

		Label lblMaterial = new Label( group, SWT.NONE );
		lblMaterial.setBounds( 21, 129, 57, 15 );
		lblMaterial.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.material" ) );
		lblMaterial.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtMaterial = new Text( group, SWT.BORDER );
		// txtMaterial.setEditable(false);
		// txtMaterial.setBackground(SWTResourceManager.getColor(255, 255,
		// 204));
		txtMaterial.setBounds( 116, 126, 510, 20 );

		Group gpoLlamFe = new Group( container, SWT.NONE );
		gpoLlamFe.setBounds( 10, 178, 656, 66 );
		gpoLlamFe.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblContactar = new Label( gpoLlamFe, SWT.NONE );
		lblContactar.setBounds( 21, 17, 57, 15 );
		lblContactar.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.contactar" ) );
		lblContactar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		btnSi = new Button( gpoLlamFe, SWT.RADIO );
		btnSi.setBounds( 116, 10, 50, 22 );
		btnSi.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.radio.si" ) );
		btnSi.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		btnSi.setSelection( true );

		btnNo = new Button( gpoLlamFe, SWT.RADIO );
		btnNo.setBounds( 165, 10, 50, 22 );
		btnNo.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.radio.no" ) );
		btnNo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblFechaPromesa = new Label( gpoLlamFe, SWT.NONE );
		lblFechaPromesa.setBounds( 21, 38, 96, 15 );
		lblFechaPromesa.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.fecha.promesa" ) );
		lblFechaPromesa.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		dtFePromesa = new DateTime( gpoLlamFe, SWT.BORDER | SWT.DROP_DOWN );
		dtFePromesa.setBounds( 119, 38, 127, 20 );
		org.joda.time.DateTime today = new org.joda.time.DateTime( new Date() );
		dtFePromesa.setDate( today.getYear(), today.getMonthOfYear() - 1, today.getDayOfMonth() );

		dtFePromesa.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				fecha = getDateTimeFromPicker();
				Timestamp timestamp = Timestamp.valueOf( fecha );
				Integer daysBetween = DateUtils.isMayor( new Date(), timestamp );
				buttonGuardar.setEnabled( daysBetween >= 0 );
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );

		btnArmazon.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				loadMaterial( Constants.ARTICULOS_ID_GENERICO_ARMAZON.charAt( 0 ) );
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );

		btnLente.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				loadMaterial( Constants.ARTICULOS_ID_GENERICO_LENTE.charAt( 0 ) );
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );

		txtCausaRoto.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				enableBtnGuardar();
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		txtResponsable.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				enableBtnGuardar();
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		txtResponsable.addFocusListener( new FocusListener() {
			@Override
			public void focusLost( FocusEvent e ) {
				String responsableId = txtResponsable.getText().trim();

				if ( StringUtils.isNotBlank( responsableId ) ) {
					try {
						Empleado responsable = loginService.findUserById( responsableId );
						labelNombreResponsable.setText( responsable.getNombreApellidos().trim() );
					} catch ( Exception ex ) {
						labelNombreResponsable.setText( MessagesPropertyHelper.getProperty( "recepcion.no.satisfactorio.responsable.error.message" ) );
					}
				}
			}

			@Override
			public void focusGained( FocusEvent e ) {
			}
		} );

		txtMaterial.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				enableBtnGuardar();
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		return container;
	}

	public String loadMaterial( char generico ) {
		String str = "";
		Articulos art = null;
		try {
			NotaVenta nv = trabajoService.findNotaVentaByFactura( rx );
			if ( nv != null && nv.getIdFactura() != null ) {
				List<DetalleNotaVenta> lst = new ArrayList<DetalleNotaVenta>();
				lst = trabajoService.findAllDetalleNotaVenByIdArt( nv.getIdFactura() );
				if ( lst != null && !lst.isEmpty() ) {
					for ( DetalleNotaVenta stg : lst ) {
						art = trabajoService.findArticuloByIdArtAndIdGeneric( stg.getIdArticulo(), generico );
						if ( art != null ) {
							str = art.getArticulo().trim();
						}
					}
				}
			}
			txtMaterial.setText( str );
			enableBtnGuardar();
		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}

		return str;
	}

	private String getDateTimeFromPicker() {
		String dateFormatTimestamp = DateUtils.builToTimestampFormat( dtFePromesa.getYear(), dtFePromesa.getMonth(), dtFePromesa.getDay(), dtFePromesa.getHours(), dtFePromesa.getMinutes(), dtFePromesa.getSeconds() );
		return dateFormatTimestamp;
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 700, 360 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( Constants.RX_DOS_PUNTOS + " " + rx );
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
		buttonCerrar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		buttonGuardar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.OK_LABEL, false );
		buttonGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		buttonGuardar.setText( ApplicationPropertyHelper.getProperty( "button.guardar.label" ) );
		buttonGuardar.setEnabled( false );

		buttonGuardar.addMouseListener( new MouseListener() {
			@Override
			public void mouseUp( MouseEvent arg0 ) {

				try {
					Empleado e = loginService.findUserById( txtResponsable.getText() );

					if ( e != null ) {
						setValuesJbRoto();
						updateJbAndNotaVenta();
						jbLlamadaExist = trabajoService.findJbLlamadaById( rx );
						setValuesJbTrackRecibeSuc();
						setValuesJbTrackRotoPorEnviar();
						if ( jbLlamadaExist != null ) {
							saveUpdateObjs = new Object[0];
							delObjs = new Object[1];
							delObjs[0] = jbLlamadaExist;
							trabajoService.saveOrUpdateDeleteObjectList( saveUpdateObjs, delObjs );
							delObjs = new Object[0];
							saveUpdateObjs = new Object[6];
						}

						saveUpdateObjs[0] = jbRoto;
						saveUpdateObjs[1] = jb;
						saveUpdateObjs[2] = notaVenta;
						saveUpdateObjs[4] = jbTrackRecibeSuc;
						saveUpdateObjs[3] = jbTrackRotoEnviar;

                        if ( jbRoto.getLlamada() ) {
							setValuesJbLlamada();
							saveUpdateObjs[5] = jbLlamada;
						}

                        jbSobre = null;

                        trabajoService.saveOrUpdateDeleteObjectList( saveUpdateObjs, delObjs );

                        if ( jbRoto.getTipo().equals(Constants.ARTICULOS_ID_GENERICO_ARMAZON) ) {

                            jbSobre = new JbSobre();
                            Integer idRoto = jbRoto.getIdRoto();

                            jbSobre.setFolioSobre( idRoto.toString());
                            jbSobre.setArea("ROTO ARMAZON");
                            jbSobre.setContenido(jbRoto.getMaterial());
                            jbSobre.setDest("ROTO ARMAZON");
                            jbSobre.setFecha( new Timestamp( new Date().getTime() ) );
                            jbSobre.setIdMod( "0" );
                            jbSobre.setEmp("ROTO");

                            sobreService.saveSobre( jbSobre );
                        }


                        if ( Constants.ARTICULOS_ID_GENERICO_ARMAZON.equalsIgnoreCase( jbRoto.getTipo().trim() ) ) {
							trabajoImpresion.imprimirNoSatisfactorio( jb, jbRoto, e, empleado.getSucursal(), jbSobre );
						}


					} else {
						MessageDialog.openError( shell, MessagesPropertyHelper.getProperty( "recepcion.no.satisfactorio.responsable.error.title" ), MessagesPropertyHelper.getProperty( "recepcion.no.satisfactorio.responsable.error.message" ) );
						NoSatisfactorioDialog noSatisfactorioDialog = new NoSatisfactorioDialog( shell );
						noSatisfactorioDialog.open();
					}

				} catch ( ApplicationException e ) {
					e.printStackTrace();
				}
			}

			@Override
			public void mouseDown( MouseEvent arg0 ) {
			}

			@Override
			public void mouseDoubleClick( MouseEvent arg0 ) {
			}
		} );
	}

	private void setValuesJbRoto() {
		try {
			// busca rx en jb_rotos
			jbRotoTemp = trabajoService.getLastJbRotoByRx( rx );
			if ( jbRotoTemp != null ) {
				jbRoto.setNumRoto( jbRotoTemp.getNumRoto() + Constants.UNO_INTEGER );
			} else {
				jbRoto.setNumRoto( Constants.UNO_INTEGER );
			}
			jbRoto.setTipo( btnArmazon.getSelection() ? Constants.ARTICULOS_ID_GENERICO_ARMAZON : Constants.ARTICULOS_ID_GENERICO_LENTE );
			jbRoto.setLlamada( btnSi.getSelection() );
			jbRoto.setRx( rx );
			jbRoto.setMaterial( txtMaterial.getText() );
			jbRoto.setCausa( txtCausaRoto.getText() );
			jbRoto.setEmp( txtResponsable.getText() );
			jbRoto.setAlta( true );
			jbRoto.setFechaProm( getDateFromPicker() );
			jbRoto.setFecha( new Timestamp( System.currentTimeMillis() ) );
			jbRoto.setIdMod( Constants.CERO_STRING );

		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
	}

	private Date getDateFromPicker() {
		if ( calendar == null ) {
			calendar = Calendar.getInstance();
		}
		calendar.set( dtFePromesa.getYear(), dtFePromesa.getMonth(), dtFePromesa.getDay() );
		Date date = calendar.getTime();
		return date;
	}

	private void updateJbAndNotaVenta() {
		if ( jb.getVolverLlamar() != null ) {
			jb.setVolverLlamar( null );
		}

		if ( jb.getRoto() != null ) {
			jb.setRoto( jb.getRoto() + Constants.UNO_INTEGER );
		} else {
			jb.setRoto( Constants.UNO_INTEGER );
		}

		jb.setFechaPromesa( jbRoto.getFechaProm() );
		jb.getEstado().setIdEdo( EstadoConstants.ESTADO_ROTO_POR_ENVIAR );
		notaVenta = trabajoService.findNotaVentaByFactura( rx );

		if ( notaVenta != null ) {
			notaVenta.setFechaPrometida( jbRoto.getFechaProm() );
		}
	}

	@SuppressWarnings( "static-access" )
	private void setValuesJbTrackRecibeSuc() {
		jbTrackRecibeSuc.setRx( rx );
		jbTrackRecibeSuc.setEstado( EstadoTrabajo.RECIBE_SUCURSAL.codigo() );
		jbTrackRecibeSuc.setObservaciones( Constants.OBS_VIAJE + " " + numViaje );
		jbTrackRecibeSuc.setEmpleado( empleado.getIdEmpleado() );
		jbTrackRecibeSuc.setIdViaje( "" );
		jbTrackRecibeSuc.setFecha( new Date() );
		jbTrackRecibeSuc.setIdMod( "0" );
	}

	@SuppressWarnings( "static-access" )
	private void setValuesJbTrackRotoPorEnviar() {
		jbTrackRotoEnviar.setRx( rx );
		jbTrackRotoEnviar.setEstado( EstadoTrabajo.ROTO_POR_ENVIAR.codigo() );
		jbTrackRotoEnviar.setObservaciones( txtCausaRoto.getText() );
		jbTrackRotoEnviar.setEmpleado( empleado.getIdEmpleado() );
		jbTrackRotoEnviar.setIdViaje( "" );
		jbTrackRotoEnviar.setFecha( new Date() );
		jbTrackRotoEnviar.setIdMod( "0" );
	}

	private void setValuesJbLlamada() {
		Jb jb = null;
		try {
			jb = trabajoService.findById( rx );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		jbLlamada.setNumLlamada( jb.getNumLlamada() );
		jbLlamada.setRx( jb.getRx() );
		jbLlamada.setEmpAtendio( jb.getEmpAtendio() );
		jbLlamada.setFecha( new Timestamp( System.currentTimeMillis() ) );
		jbLlamada.setEstado( EstadoConstants.ESTADO_PENDIENTE );
		jbLlamada.setContesto( null );
		jbLlamada.setEmpLlamo( null );
		jbLlamada.setTipo( JbTipoConstants.JB_TIPO_RETRASADO );
		jbLlamada.setObs( null );
		jbLlamada.setGrupo( false );
		jbLlamada.setIdMod( Constants.CADENA_VACIA );
	}

	private void enableBtnGuardar() {
		buttonGuardar.setEnabled( !txtCausaRoto.getText().isEmpty() && !txtResponsable.getText().isEmpty() && !txtMaterial.getText().isEmpty() );
	}
}

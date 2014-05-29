package mx.com.lux.control.trabajos.view.container.contacto;

import mx.com.lux.control.trabajos.bussiness.service.contacto.ContactoViewService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.GParametroService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.JbGrupoService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.DateUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.*;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.ContactoPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.DateTimeTextUpdaterKeyListener;
import mx.com.lux.control.trabajos.view.listener.DateTimeTextUpdaterSelectionListener;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ContactoViewDataDialog extends Dialog {

	private final Logger log = LoggerFactory.getLogger( ContactoViewDataDialog.class );

	private final TrabajoService trabajoService;
	private final JbGrupoService jbGrupoService;
	private final ContactoViewService contactoViewService;

	private ContactoView currentContactoView = new ContactoView();
	private Empleado empleado = new Empleado();
	private GParametro parametroVolverContactar;
	private String rx;
	private Jb currentJb = new Jb();
	private JbLlamada currentJbLlamada = new JbLlamada();
	private List<Jb> listaTrabajosEnGrupo = new ArrayList<Jb>();
	private Text txtInfo;
	private Text txtDias;
	private DateTime dtVolvCont;
	private Button btnGuardar;
	private boolean realizado = false;
	private boolean grupo = false;
	private Calendar calendar;
	private String strInfo;
	private Date dateFromDatePicker;
	private final Shell shell;

	public ContactoViewDataDialog( Shell parentShell ) {
		super( parentShell );
		log.debug( "Opening Dialog: " + this.getClass().getSimpleName() );
		this.shell = parentShell;
		trabajoService = ServiceLocator.getBean( TrabajoService.class );
		GParametroService gParametroService = ServiceLocator.getBean( GParametroService.class );
		jbGrupoService = ServiceLocator.getBean( JbGrupoService.class );
		contactoViewService = ServiceLocator.getBean( ContactoViewService.class );
		try {
			parametroVolverContactar = gParametroService.findById( GParametroConstants.PARAMETRO_VOLVER_CONTACTAR );
		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
		this.strInfo = "";
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
		currentContactoView = (ContactoView) Session.getAttribute( Constants.PARAM_CONTACTO_VIEW );
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Composite container = (Composite) super.createDialogArea( parent );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group group_1 = new Group( container, SWT.NONE );
		group_1.setBounds( 10, 10, 578, 195 );
		group_1.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		VerifyListener upperTxt = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		Label lblCliente = new Label( group_1, SWT.NONE );
		lblCliente.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente" ) );
		lblCliente.setBounds( 10, 10, 100, 20 );
		lblCliente.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtCliente = new Text( group_1, SWT.BORDER );
		txtCliente.setEditable( false );
		txtCliente.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtCliente.setBounds( 146, 10, 413, 20 );

		Label lblMaterial = new Label( group_1, SWT.NONE );
		lblMaterial.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.material" ) );
		lblMaterial.setBounds( 10, 36, 100, 20 );
		lblMaterial.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtMaterial = new Text( group_1, SWT.BORDER );
		txtMaterial.setEditable( false );
		txtMaterial.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtMaterial.setBounds( 146, 36, 413, 20 );

		Label lblSaldo = new Label( group_1, SWT.NONE );
		lblSaldo.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.saldo" ) );
		lblSaldo.setBounds( 10, 62, 100, 20 );
		lblSaldo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtSaldo = new Text( group_1, SWT.BORDER );
		txtSaldo.setEditable( false );
		txtSaldo.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtSaldo.setBounds( 146, 62, 217, 20 );

		Label lblTipCon = new Label( group_1, SWT.NONE );
		lblTipCon.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.contacto.tipo.contacto" ) );
		lblTipCon.setBounds( 10, 88, 100, 20 );
		lblTipCon.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtTipCon = new Text( group_1, SWT.BORDER );
		txtTipCon.setEditable( false );
		txtTipCon.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtTipCon.setBounds( 146, 88, 217, 20 );

		Label lblCon = new Label( group_1, SWT.NONE );
		lblCon.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.contacto" ) );
		lblCon.setBounds( 10, 114, 132, 20 );
		lblCon.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtCon = new Text( group_1, SWT.BORDER );
		txtCon.setEditable( false );
		txtCon.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtCon.setBounds( 146, 114, 413, 20 );

		Label lblContactos = new Label( group_1, SWT.NONE );
		lblContactos.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.contactos" ) );
		lblContactos.setBounds( 10, 140, 132, 20 );
		lblContactos.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtContactos = new Text( group_1, SWT.BORDER );
		txtContactos.setEditable( false );
		txtContactos.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtContactos.setBounds( 146, 140, 413, 20 );

		Label lblObs = new Label( group_1, SWT.NONE );
		lblObs.setBounds( 10, 166, 132, 20 );
		lblObs.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.observaciones" ) );
		lblObs.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtObs = new Text( group_1, SWT.BORDER );
		txtObs.setBounds( 146, 166, 413, 20 );
		txtObs.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtObs.setEditable( false );

		Group group = new Group( container, SWT.SHADOW_OUT );
		group.setBounds( 10, 211, 578, 104 );
		group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblInfo = new Label( group, SWT.NONE );
		lblInfo.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.info" ) );
		lblInfo.setBounds( 8, 10, 132, 20 );
		lblInfo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtInfo = new Text( group, SWT.BORDER );
		txtInfo.setEnabled( false );
		txtInfo.setBounds( 146, 10, 417, 20 );

		Label lblVolvCont = new Label( group, SWT.NONE );
		lblVolvCont.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.contactar.volver" ) );
		lblVolvCont.setBounds( 10, 38, 132, 20 );
		lblVolvCont.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		dtVolvCont = new DateTime( group, SWT.BORDER | SWT.DROP_DOWN );
		dtVolvCont.setBounds( 146, 39, 217, 20 );
		dtVolvCont.setEnabled( false );

		StringBuilder sbLabel = new StringBuilder( TrabajosPropertyHelper.getProperty( "trabajos.label.o" ) );
		sbLabel.append( " " );
		sbLabel.append( TrabajosPropertyHelper.getProperty( "trabajos.label.dias" ) );

		Label lblDias = new Label( group, SWT.NONE );
		lblDias.setText( sbLabel.toString() );
		lblDias.setBounds( 395, 39, 59, 20 );
		lblDias.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtDias = new Text( group, SWT.BORDER );
		txtDias.setEnabled( false );
		txtDias.setBounds( 513, 39, 50, 20 );
		txtDias.setTextLimit( 3 );
		txtDias.setText( parametroVolverContactar.getValor() );

		Button btnRealizado = new Button( group, SWT.NONE );
		btnRealizado.setBounds( 146, 65, 120, 35 );
		btnRealizado.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.realizado" ) );
		btnRealizado.setImage( ControlTrabajosWindowElements.aceptarIcon );

		Button btnNoRealizado = new Button( group, SWT.NONE );
		btnNoRealizado.setBounds( 443, 65, 120, 35 );
		btnNoRealizado.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.no.realizado" ) );
		btnNoRealizado.setImage( ControlTrabajosWindowElements.cancelarIcon );

		org.joda.time.DateTime today = DateUtils.addDays( new Date(), txtDias.getText() );
		dtVolvCont.setDate( today.getYear(), today.getMonthOfYear() - 1, today.getDayOfMonth() );

		txtDias.addKeyListener( new DateTimeTextUpdaterKeyListener( dtVolvCont, txtDias, btnGuardar ) );
		dtVolvCont.addSelectionListener( new DateTimeTextUpdaterSelectionListener( dtVolvCont, txtDias, btnGuardar ) );
		dateFromDatePicker = getDateTimeFromPicker();

		if ( currentContactoView != null && StringUtils.isNotBlank( currentContactoView.getRx() ) ) {
			rx = currentContactoView.getRx().trim();
			parent.getShell().setText( "Rx: " + rx );
			try {
				currentJb = trabajoService.findById( rx );
				currentJbLlamada = trabajoService.findJbLlamadaById( rx );
				txtCliente.setText( currentContactoView.getTitulo() + " " + currentContactoView.getCliente() );
				txtTipCon.setText( currentContactoView.getDescripcionTipoContacto() );
				txtCon.setText( currentContactoView.getContactoForma() );
				txtObs.setText( currentContactoView.getObsForma().trim() );
				txtContactos.setText( currentContactoView.getNumLlamadaJbLlamada().toString() );
				txtMaterial.setText( currentContactoView.getMaterial() );
				txtSaldo.setText( currentContactoView.getSaldo() );

				// si es grupo, se obtienen sus elementos
				if ( rx.startsWith( RxConstants.TIPO_GRUPO_F ) ) {
					listaTrabajosEnGrupo = jbGrupoService.obtenTrabajosEnGrupo( rx );
					grupo = true;
				}

				if ( esTipoContactoSMS( rx ) ) {
					// modifica texto boton realizado por enviar cuando tipo
					// contacto SMS
					btnRealizado.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.sms" ) );
				}

			} catch ( ApplicationException e1 ) {
				e1.printStackTrace();
			}
		}

		txtInfo.addFocusListener( new FocusListener() {
			@Override
			public void focusLost( FocusEvent arg0 ) {
				if ( StringUtils.isNotBlank( txtInfo.getText() ) ) {
					strInfo = txtInfo.getText().trim().toUpperCase();
					txtInfo.setText( strInfo );
				}
			}

			@Override
			public void focusGained( FocusEvent arg0 ) {
			}
		} );

		dtVolvCont.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				String fecha = DateUtils.builToTimestampFormat( dtVolvCont.getYear(), dtVolvCont.getMonth(), dtVolvCont.getDay(), dtVolvCont.getHours(), dtVolvCont.getMinutes(), dtVolvCont.getSeconds() );
				Timestamp timestamp = Timestamp.valueOf( fecha );
				Integer daysBetween = DateUtils.daysBetweenCeilDayHours( new Date(), timestamp );
				btnGuardar.setEnabled( daysBetween >= 0 );
				dateFromDatePicker = getDateTimeFromPicker();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );

		txtDias.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				if ( !txtDias.getText().isEmpty() && StringUtils.isNumeric( txtDias.getText() ) ) {
                    dateFromDatePicker = getDateTimeFromPicker();
                    btnGuardar.setEnabled( true );
				} else {
					btnGuardar.setEnabled( false );
				}
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		btnRealizado.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {

				if ( esTipoContactoCorreoElectronico( rx ) ) {
					try {
						contactoViewService.enviarNotificacionCorreoElectronico( rx );
						btnGuardar.setEnabled( true );
						txtInfo.setEnabled( true );
						dtVolvCont.setEnabled( true );
						txtDias.setEnabled( true );
						realizado = true;
					} catch ( ApplicationException e ) {
						Status status = new Status( IStatus.ERROR, "JSOI", 0, "Error al enviar el Correo Electronico", null );
						ErrorDialog.openError( shell, "Error", "Error", status );
					}
				} else {
					btnGuardar.setEnabled( true );
					txtInfo.setEnabled( true );
					dtVolvCont.setEnabled( true );
					txtDias.setEnabled( true );
					realizado = true;
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );

		btnNoRealizado.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				btnGuardar.setEnabled( true );
				txtInfo.setEnabled( true );
				dtVolvCont.setEnabled( false );
				txtDias.setEnabled( false );
				realizado = false;
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );

		txtInfo.addVerifyListener( upperTxt );

		return container;
	}

	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Button btnCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		btnCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		btnCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
		btnGuardar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.OK_LABEL, false );
		btnGuardar.setText( ApplicationPropertyHelper.getProperty( "button.guardar.label" ) );
		btnGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		btnGuardar.setEnabled( false );

		btnGuardar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				String estado = currentContactoView.getTipoLlamada();
				if ( realizado ) {

					boolean esContactoSMS = false;
					if ( esTipoContactoSMS( rx ) ) {
						esContactoSMS = true;
						strInfo = TrabajosPropertyHelper.getProperty( "trabajos.msg.sms.envio" );
					}

					if ( currentContactoView.getTipoLlamada().trim().equals( ContactoPropertyHelper.getProperty( "contacto.estado.entregar" ) ) ) {
						estado = ContactoPropertyHelper.getProperty( "contacto.estado.entregar.ae" );
					} else if ( currentContactoView.getTipoLlamada().trim().equals( JbTipoConstants.JB_TIPO_RETRASADO ) ) {
						estado = ContactoPropertyHelper.getProperty( "contacto.estado.retrasado.ar" );
					}

					currentJb.setNumLlamada( currentJb.getNumLlamada() != null ? ( currentJb.getNumLlamada() + 1 ) : 1 );
					currentJb.setVolverLlamar( dateFromDatePicker );

					if ( grupo ) {
						if ( listaTrabajosEnGrupo != null && !listaTrabajosEnGrupo.isEmpty() ) {
							Object[] objRealizado = new Object[listaTrabajosEnGrupo.size() + 2];
							objRealizado[0] = currentJb;
							objRealizado[1] = currentJbLlamada;

							if ( esContactoSMS ) {
								List<Jb> listaTrabajosContacto = new ArrayList<Jb>();
								listaTrabajosContacto.add( currentJb );
								listaTrabajosContacto.addAll( listaTrabajosEnGrupo );
								for ( Jb trabajoContacto : listaTrabajosContacto ) {
									String rx = trabajoContacto.getRx();
									if ( rx.startsWith( "F" ) ) {
										// Solo generamos el acuse para el RX del grupo
										try {
											contactoViewService.registraContactoSMS( rx, false );
										} catch ( ApplicationException ex ) {
											Status status = new Status( IStatus.ERROR, "JSOI", 0, "Error al registrar contacto SMS", null );
											ErrorDialog.openError( shell, "Error", "Error", status );
										}

									}
								}
								MessageDialog.openInformation( getShell(), StringUtils.defaultIfBlank( strInfo, "" ), TrabajosPropertyHelper.getProperty( "trabajos.msg.sms.info" ) );
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
								jbGrupoService.saveGrupoTrackContactoRealizado( objRealizado );
							} catch ( ApplicationException e ) {
								log.error( "Error al guardar Grupo Track Contacto realizado: " + e.getMessage() );
							}
						}
					} else {
						if ( esContactoSMS ) {
							try {
								contactoViewService.registraContactoSMS( currentJb.getRx(), false );
								MessageDialog.openInformation( getShell(), strInfo, TrabajosPropertyHelper.getProperty( "trabajos.msg.sms.info" ) );
							} catch ( ApplicationException ex ) {
								Status status = new Status( IStatus.ERROR, "JSOI", 0, "Error al registrar contacto SMS", null );
								ErrorDialog.openError( shell, "Error", "Error", status );
							}
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
						saveContactoTrackJb( objRealizado );
					}
				} else {
					estado = EstadoConstants.ESTADO_NO_CONTESTO;
					currentJbLlamada.setEstado( EstadoConstants.ESTADO_NO_CONTESTO );

					if ( grupo ) {
						if ( listaTrabajosEnGrupo != null && !listaTrabajosEnGrupo.isEmpty() ) {
							Object[] objRealizado = new Object[listaTrabajosEnGrupo.size() + 1];
							objRealizado[0] = currentJbLlamada;
							int i = 1;
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
								jbGrupoService.saveGrupoTrackContactoNoRealizado( objRealizado );
							} catch ( ApplicationException e ) {
								e.printStackTrace();
							}
						}
					} else {
						JbTrack currentJbt = new JbTrack();
						currentJbt.setRx( rx );
						currentJbt.setEstado( estado );
						currentJbt.setObservaciones( StringUtils.defaultIfBlank( strInfo, "" ) );
						currentJbt.setEmpleado( empleado.getIdEmpleado() );
						currentJbt.setIdViaje( null );
						currentJbt.setFecha( new Date() );
						currentJbt.setIdMod( "0" );
						Object[] objNoRealizado = { currentJbLlamada, currentJbt };
						saveJbLlamadaAndTrack( objNoRealizado );
					}
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 610, 420 );
	}

	private Date getDateTimeFromPicker() {
		if ( calendar == null ) {
			calendar = Calendar.getInstance();
		}
		calendar.set( dtVolvCont.getYear(), dtVolvCont.getMonth(), dtVolvCont.getDay() );
		return calendar.getTime();
	}

	private void saveContactoTrackJb( Object[] o ) {
		try {
			trabajoService.deleteInsertUpdate( o );
		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
	}

	private void saveJbLlamadaAndTrack( Object[] o ) {
		try {
			trabajoService.updateInsert( o );
		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
	}

	private boolean esTipoContactoSMS( final String rx ) {
		FormaContacto formaContacto = contactoViewService.obtenFormaContactoDeRx( rx );
		return ( formaContacto != null ) && ( formaContacto.getTipoContacto().getIdTipoContacto() == 3 );
	}

	private boolean esTipoContactoCorreoElectronico( final String rx ) {
		FormaContacto formaContacto = contactoViewService.obtenFormaContactoDeRx( rx );
		return ( formaContacto != null ) && ( formaContacto.getTipoContacto().getIdTipoContacto() == 1 );
	}

}

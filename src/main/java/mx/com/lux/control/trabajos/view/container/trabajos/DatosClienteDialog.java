package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.ClienteService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.ordenservicio.OrdenServicioDialog;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.OnlyDigit;
import mx.com.lux.control.trabajos.view.render.CodigoRender;
import mx.com.lux.control.trabajos.view.render.DominiosRender;
import mx.com.lux.control.trabajos.view.render.EstadoRepublicaRender;
import mx.com.lux.control.trabajos.view.render.MunicipioRender;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class DatosClienteDialog extends Dialog {

	private final Logger log = Logger.getLogger( this.getClass() );

	private static final ClienteService clienteService;
	private static final TrabajoService trabajoService;

	private OrdenServicioDialog ordenServicioDialog;
	private Empleado empleado;
	private Cliente cliente;
	private Cliente newCliente = new Cliente();
	private EstadoRepublica currentEstadoRepublica;
	private List<EstadoRepublica> estados;
	private Municipio currentMunicipio;
	private List<Municipio> municipios;
	private Codigo currentCodigo;
	private List<Codigo> colonias;
	private Dominios currentDominios;
	private List<Dominios> listaDominios;
	private String winTitle;
	private String editar;
	private String strCodPost;
	private String strEmail;
	private boolean edit;
	private Integer indexEstados = -1;
	private Integer indexDelMun = -1;
	private Integer indexColonia;
	private Integer indexDominios;
	private Date feDatePicker = new Date();
	private Calendar calendar;
	private Shell shell;
	private Label lblArroba;
	private Text tbDCapaterno;
	private Text tbDCamaterno;
	private Text tbDCcalle;
	private Text tbDCcolonia;
	private Text tbDCestado;
	private Text tbDCobservaciones;
	private Text tbDCemail;
	private Text tbDCtelcelular;
	private Text tbDCteltrabajo;
	private Text tbDCtelcasa;
	private Text tbDCdelmnpo;
	private Text tbDCcp;
	private Text tbDCnombre;
	private Text fechaNacimientoTxt;
	private Text txtEmail;
	private Text txtOtroDominio;
	private String strOtroDominio;
	private Button rdBtnMasculino;
	private Button rdBtnFemenino;
	private Button btnGuardar;
	private Button btnMostrarDominios;
	private ComboViewer cmbVwEdos;
	private Combo cmbEdos;
	private ComboViewer cmbVwDelMun;
	private Combo cmbDelMun;
	private ComboViewer cmbVwColonia;
	private Combo cmbColonia;
	private ComboViewer cmbVwEmail;
	private Combo cmbEmail;
	private DateTime dtFeNac;

	static {
		clienteService = (ClienteService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_CLIENTE_SERVICE );
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
	}

	public DatosClienteDialog( Shell parentShell ) {
		super( parentShell );
		shell = parentShell;

		try {
			// se extrae empleado logueado
			empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
			// variable habilita campos para edicion
			editar = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_EDIT_CLIENTE );
			edit = Boolean.valueOf( editar );
			// se obtiene el cliente
			String idCliente = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE );
			winTitle = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );

			if ( idCliente != null ) {
                cliente = clienteService.findClienteById( Integer.parseInt( idCliente.trim() ) );
            } else {
				cliente = null;
			}

			if ( edit ) {
				// carga lista de estados y de dominios
				estados = trabajoService.getAllEstadoRepublica();
				listaDominios = trabajoService.getAllDominios();
			}
		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
	}

	@Override
	protected Control createDialogArea( Composite parent ) {

		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Composite container = (Composite) super.createDialogArea( parent );
		container.setBounds( 10, 10, 485, 480 );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		/*
		Group group = new Group( container, SWT.SHADOW_OUT );
		group.setForeground( SWTResourceManager.getColor( SWT.COLOR_BLUE ) );
		group.setBounds( 10, 10, 656, 410 );
		group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		*/

		// ETIQUETAS QUE SE VAN A MOSTRAR SIEMPRE
		Label lblNombre = new Label( container, SWT.NONE );
		lblNombre.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.nombre" ) );
		lblNombre.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblNombre.setBounds( 10, 7, 99, 20 );
		lblNombre.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblApellidoPaterno = new Label( container, SWT.NONE );
		lblApellidoPaterno.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.apellido.paterno" ) );
		lblApellidoPaterno.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblApellidoPaterno.setBounds( 10, 34, 112, 20 );
		lblApellidoPaterno.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblApellidoMaterno = new Label( container, SWT.NONE );
		lblApellidoMaterno.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.apellido.materno" ) );
		lblApellidoMaterno.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblApellidoMaterno.setBounds( 10, 61, 112, 20 );
		lblApellidoMaterno.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblEmail = new Label( container, SWT.NONE );
		lblEmail.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.email" ) );
		lblEmail.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblEmail.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblDelmnpo = new Label( container, SWT.NONE );
		lblDelmnpo.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.direccion.municipio" ) );
		lblDelmnpo.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblDelmnpo.setBounds( 10, 192, 62, 20 );
		lblDelmnpo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblCp = new Label( container, SWT.NONE );
		lblCp.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.direccion.cp" ) );
		lblCp.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblCp.setBounds( 10, 166, 112, 20 );
		lblCp.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblFechaNacimiento = new Label( container, SWT.NONE );
		lblFechaNacimiento.setBounds( 10, 87, 112, 15 );
		lblFechaNacimiento.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.fecha.nacimiento" ) );
		lblFechaNacimiento.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblFechaNacimiento.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblCalleYNumero = new Label( container, SWT.NONE );
		lblCalleYNumero.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.direccion.calle.numero" ) );
		lblCalleYNumero.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblCalleYNumero.setBounds( 10, 141, 112, 20 );
		lblCalleYNumero.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblColonia = new Label( container, SWT.NONE );
		lblColonia.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.direccion.colonia" ) );
		lblColonia.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblColonia.setBounds( 348, 209, 55, 20 );
		lblColonia.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblEstado = new Label( container, SWT.NONE );
		lblEstado.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.direccion.estado" ) );
		lblEstado.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblEstado.setBounds( 348, 172, 55, 20 );
		lblEstado.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblTelefonos = new Label( container, SWT.NONE );
		lblTelefonos.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.tel" ) );
		lblTelefonos.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblTelefonos.setBounds( 10, 222, 112, 20 );
		lblTelefonos.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblCasa = new Label( container, SWT.NONE );
		lblCasa.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.tel.casa" ) );
		lblCasa.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblCasa.setBounds( 10, 249, 112, 20 );
		lblCasa.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		// VALIDACION PARA MOSTRAR ALGUNAS ETIQUETAS
		if ( edit ) {
			Label lblSexo = new Label( container, SWT.NONE );
			lblSexo.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.sexo" ) );
			lblSexo.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
			lblSexo.setBounds( 10, 114, 112, 20 );
			lblSexo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
			lblEmail.setBounds( 10, 275, 112, 20 );
		} else {
			Label lblDir = new Label( container, SWT.NONE );
			lblDir.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.direccion" ) );
			lblDir.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
			lblDir.setBounds( 10, 110, 112, 20 );
			lblDir.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

			Label lblTrabajo = new Label( container, SWT.NONE );
			lblTrabajo.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.tel.trabajo" ) );
			lblTrabajo.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
			lblTrabajo.setBounds( 10, 272, 112, 20 );
			lblTrabajo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

			Label lblCelular = new Label( container, SWT.NONE );
			lblCelular.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.tel.adicional" ) );
			lblCelular.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
			lblCelular.setBounds( 10, 299, 112, 20 );
			lblCelular.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

			lblEmail.setBounds( 10, 326, 112, 20 );

			Label lblObservaciones = new Label( container, SWT.NONE );
			lblObservaciones.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.observaciones" ) );
			lblObservaciones.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
			lblObservaciones.setBounds( 10, 353, 112, 20 );
			lblObservaciones.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		}

		VerifyListener upperTxt = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		// CAMPOS QUE SE VAN A MOSTRAR SIEMPRE
		tbDCnombre = new Text( container, SWT.BORDER );
		tbDCnombre.setEditable( edit );
		tbDCnombre.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		tbDCnombre.setBounds( 127, 7, 510, 20 );

		tbDCapaterno = new Text( container, SWT.BORDER );
		tbDCapaterno.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		tbDCapaterno.setEditable( edit );
		tbDCapaterno.setBounds( 127, 33, 510, 20 );

		tbDCamaterno = new Text( container, SWT.BORDER );
		tbDCamaterno.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		tbDCamaterno.setEditable( edit );
		tbDCamaterno.setBounds( 127, 61, 510, 20 );

		if ( !edit ) {
			fechaNacimientoTxt = new Text( container, SWT.BORDER );
			fechaNacimientoTxt.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
			fechaNacimientoTxt.setEditable( false );
			fechaNacimientoTxt.setBounds( 127, 87, 127, 20 );
			if ( cliente.getFechaNac() != null ) {
				fechaNacimientoTxt.setText( new SimpleDateFormat( ApplicationPropertyHelper.getProperty( "app.format.date.date" ) ).format( cliente.getFechaNac() ) );
			}
		} else {
			// DATE-PICKER PARA MODIFICAR LA FECHA DE NACIMIENTO
			dtFeNac = new DateTime( container, SWT.BORDER | SWT.DROP_DOWN );
			dtFeNac.setBounds( 128, 87, 196, 20 );
			dtFeNac.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
			// evento para guardar fecha cada vez que se modifica

			dtFeNac.addSelectionListener( new SelectionListener() {
				@Override
				public void widgetSelected( SelectionEvent arg0 ) {
					if ( calendar == null )
						calendar = Calendar.getInstance();
					calendar.set( dtFeNac.getYear(), dtFeNac.getMonth(), dtFeNac.getDay() );
					feDatePicker = calendar.getTime();
				}

				@Override
				public void widgetDefaultSelected( SelectionEvent arg0 ) {
				}
			} );

			// RADIO BUTTONS PARA SEXO
			rdBtnMasculino = new Button( container, SWT.RADIO );
			rdBtnMasculino.setBounds( 128, 114, 97, 22 );
			rdBtnMasculino.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.sexo.masculino" ) );
			rdBtnMasculino.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

			// verifica boton guardar en la seleccion
			rdBtnMasculino.addSelectionListener( new SelectionListener() {
				@Override
				public void widgetSelected( SelectionEvent arg0 ) {
					enableButttonGuardar();
				}

				@Override
				public void widgetDefaultSelected( SelectionEvent arg0 ) {
				}
			} );

			rdBtnFemenino = new Button( container, SWT.RADIO );
			rdBtnFemenino.setBounds( 241, 114, 97, 22 );
			rdBtnFemenino.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.sexo.femenino" ) );
			rdBtnFemenino.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

			rdBtnFemenino.addSelectionListener( new SelectionListener() {
				@Override
				public void widgetSelected( SelectionEvent arg0 ) {
					enableButttonGuardar();
				}

				@Override
				public void widgetDefaultSelected( SelectionEvent arg0 ) {
				}
			} );
		}

		tbDCcalle = new Text( container, SWT.BORDER );
		tbDCcalle.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		tbDCcalle.setEditable( edit );
		tbDCcalle.setBounds( 127, 140, 510, 20 );

		tbDCcp = new Text( container, SWT.BORDER );
		tbDCcp.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		tbDCcp.setEditable( edit );
		tbDCcp.setTextLimit( Constants.LONGITUD_CODIGO_POSTAL );
		tbDCcp.setBounds( 127, 166, 200, 20 );

		tbDCcp.addFocusListener( new FocusListener() {
			@Override
			public void focusLost( FocusEvent arg0 ) {
				if ( edit && strCodPost != null && !strCodPost.isEmpty() ) {
					loadCombosByCodigo();
					enableButttonGuardar();
				}
			}

			@Override
			public void focusGained( FocusEvent arg0 ) {
			}
		} );

		if ( edit ) {
			// COMBO ESTADOS
			cmbVwEdos = new ComboViewer( container, SWT.NONE | SWT.READ_ONLY );
			cmbEdos = cmbVwEdos.getCombo();
			cmbEdos.setBounds( 438, 172, 199, 27 );
			cmbVwEdos.setContentProvider( new ArrayContentProvider() );
			cmbVwEdos.setLabelProvider( new EstadoRepublicaRender() );
			cmbVwEdos.setInput( estados );

			// COMBO DELEGACION/MUNICIPIO
			cmbVwDelMun = new ComboViewer( container, SWT.NONE | SWT.READ_ONLY );
			cmbDelMun = cmbVwDelMun.getCombo();
			cmbDelMun.setBounds( 127, 192, 197, 27 );
			cmbDelMun.setEnabled( false );
			cmbVwDelMun.setContentProvider( new ArrayContentProvider() );
			cmbVwDelMun.setLabelProvider( new MunicipioRender() );

			// EVENTO PARA CARGAR COMBO DELEGACION/MUNICIPIO
			cmbVwEdos.addSelectionChangedListener( new ISelectionChangedListener() {
				@Override
				public void selectionChanged( SelectionChangedEvent arg0 ) {
					int i = cmbEdos.getSelectionIndex();
					if ( indexEstados != i ) {
						indexEstados = i;
						// guarda el objeto EstadoRepublica seleccionado
						currentEstadoRepublica = (EstadoRepublica) cmbVwEdos.getElementAt( indexEstados );

						if ( currentEstadoRepublica != null ) {
							cmbVwDelMun.setInput( trabajoService.findAllDelMunByIdEstadoAndLocalidad( currentEstadoRepublica.getIdEstado(), Constants.CADENA_VACIA ) );
							cmbDelMun.setEnabled( true );
							cmbColonia.setEnabled( false );
							currentEstadoRepublica = null;
							currentCodigo = null;
							enableButttonGuardar();
						}
					}
				}
			} );

			// COMBO COLONIA
			cmbVwColonia = new ComboViewer( container, SWT.NONE | SWT.READ_ONLY );
			cmbColonia = cmbVwColonia.getCombo();
			cmbColonia.setBounds( 438, 209, 199, 27 );
			cmbColonia.setEnabled( false );
			cmbVwColonia.setContentProvider( new ArrayContentProvider() );
			cmbVwColonia.setLabelProvider( new CodigoRender() );

			// EVENTO PARA COMBO COLONIA
			cmbVwColonia.addSelectionChangedListener( new ISelectionChangedListener() {
				@Override
				public void selectionChanged( SelectionChangedEvent arg0 ) {
					indexColonia = cmbColonia.getSelectionIndex();
					// guarda el objeto Codigo seleccionado
					currentCodigo = (Codigo) cmbVwColonia.getElementAt( indexColonia );
					enableButttonGuardar();
				}
			} );

			// EVENTO PARA CARGAR COMBO COLONIA
			cmbVwDelMun.addSelectionChangedListener( new ISelectionChangedListener() {
				@Override
				public void selectionChanged( SelectionChangedEvent arg0 ) {
					int i = cmbDelMun.getSelectionIndex();
					if ( indexDelMun != i ) {
						indexDelMun = i;
						// guarda el objeto Municipio seleccionado
						currentMunicipio = (Municipio) cmbVwDelMun.getElementAt( indexDelMun );

						if ( currentMunicipio != null ) {
							cmbVwColonia.setInput( trabajoService.findAllCodigoByIdEstadoAndLocalidad( currentMunicipio.getId().getIdEstado(), currentMunicipio.getId().getIdLocalidad() ) );
							cmbColonia.setEnabled( true );
							currentCodigo = null;
							enableButttonGuardar();
						}
					}
				}
			} );

		} else {
			tbDCestado = new Text( container, SWT.BORDER );
			tbDCestado.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
			tbDCestado.setEditable( edit );
			tbDCestado.setBounds( 409, 168, 228, 20 );
            tbDCestado.setText( cliente.getMunicipio() != null ? cliente.getMunicipio().getEstadoRepublica().getNombre() : "" );

			tbDCdelmnpo = new Text( container, SWT.BORDER );
			tbDCdelmnpo.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
			tbDCdelmnpo.setEditable( edit );
			tbDCdelmnpo.setBounds( 127, 192, 200, 20 );
            tbDCdelmnpo.setText( cliente.getMunicipio() != null ? cliente.getMunicipio().getNombre() : "" );

			tbDCcolonia = new Text( container, SWT.BORDER );
			tbDCcolonia.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
			tbDCcolonia.setEditable( edit );
			tbDCcolonia.setBounds( 409, 205, 228, 20 );
			tbDCcolonia.setText( cliente.getColoniaCli() );

			tbDCestado.addVerifyListener( upperTxt );
			tbDCdelmnpo.addVerifyListener( upperTxt );
			tbDCcolonia.addVerifyListener( upperTxt );
		}

		tbDCtelcasa = new Text( container, SWT.BORDER );
		tbDCtelcasa.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		tbDCtelcasa.setEditable( edit );
		tbDCtelcasa.setTextLimit( Constants.LONGITUD_TELEFONO_CASA );
		tbDCtelcasa.setBounds( 127, 248, 200, 20 );

		if ( edit ) {
			// EDITAR EMAIL
			txtEmail = new Text( container, SWT.BORDER );
			txtEmail.setBounds( 128, 275, 199, 25 );

			// VALIDACION PARA CAMPOS
			lblArroba = new Label( container, SWT.NONE );
			lblArroba.setBounds( 337, 279, 20, 15 );
			lblArroba.setText( Constants.SYMBOL_ARROBA );
			lblArroba.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

			// COMBO DOMINIOS
			cmbVwEmail = new ComboViewer( container, SWT.NONE | SWT.READ_ONLY );
			cmbEmail = cmbVwEmail.getCombo();
			cmbEmail.setBounds( 359, 275, 187, 27 );
			cmbVwEmail.setContentProvider( new ArrayContentProvider() );
			cmbVwEmail.setLabelProvider( new DominiosRender() );

			txtOtroDominio = new Text( container, SWT.BORDER );
			txtOtroDominio.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
			txtOtroDominio.setBounds( 359, 275, 187, 27 );
			txtOtroDominio.setVisible( false );

			// EVENTO PARA COMBO DOMINIOS
			cmbVwEmail.addSelectionChangedListener( new ISelectionChangedListener() {
				@Override
				public void selectionChanged( SelectionChangedEvent arg0 ) {
					indexDominios = cmbEmail.getSelectionIndex();
					// guarda el objeto Dominios seleccionado
					currentDominios = (Dominios) cmbVwEmail.getElementAt( indexDominios );
					if ( currentDominios.getNombre().equals( TrabajosPropertyHelper.getProperty( "trabajos.label.otro" ) ) ) {
						cmbEmail.setVisible( false );
						btnMostrarDominios.setVisible( true );
						txtOtroDominio.setVisible( true );
					}
				}
			} );

			// se agrega la opcion "OTRO" a la lista de dominios
			Dominios dominio = new Dominios();
			dominio.setNombre( TrabajosPropertyHelper.getProperty( "trabajos.label.otro" ) );
			listaDominios.add( dominio );
			cmbVwEmail.setInput( listaDominios );

			// Boton para mostrar los dominios existentes
			btnMostrarDominios = new Button( container, SWT.NONE );
			btnMostrarDominios.setBounds( 359, 305, 129, 27 );
			btnMostrarDominios.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.mostrar.dominios" ) );
			btnMostrarDominios.setVisible( false );

			btnMostrarDominios.addSelectionListener( new SelectionListener() {
				@Override
				public void widgetSelected( SelectionEvent arg0 ) {
					btnMostrarDominios.setVisible( false );
					lblArroba.setVisible( true );
					cmbEmail.setVisible( true );
					cmbVwEmail.setInput( listaDominios );
					txtOtroDominio.setVisible( false );
					strOtroDominio = null;
				}

				@Override
				public void widgetDefaultSelected( SelectionEvent arg0 ) {
				}
			} );

		} else {
			tbDCteltrabajo = new Text( container, SWT.BORDER );
			tbDCteltrabajo.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
			tbDCteltrabajo.setEditable( edit );
			tbDCteltrabajo.setBounds( 127, 271, 200, 20 );
			tbDCteltrabajo.setText( StringUtils.isNotBlank( cliente.getTelTrabCli() ) ? cliente.getTelTrabCli().trim() : Constants.CADENA_VACIA );

			tbDCtelcelular = new Text( container, SWT.BORDER );
			tbDCtelcelular.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
			tbDCtelcelular.setEditable( edit );
			tbDCtelcelular.setBounds( 127, 298, 200, 20 );
			tbDCtelcelular.setText( StringUtils.isNotBlank( cliente.getTelAdiCli() ) ? cliente.getTelAdiCli().trim() : Constants.CADENA_VACIA );

			tbDCemail = new Text( container, SWT.BORDER );
			tbDCemail.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
			tbDCemail.setEditable( edit );
			tbDCemail.setBounds( 127, 325, 510, 20 );
			tbDCemail.setText( StringUtils.isNotBlank( cliente.getEmailCli() ) ? cliente.getEmailCli().trim() : Constants.CADENA_VACIA );

			tbDCobservaciones = new Text( container, SWT.BORDER );
			tbDCobservaciones.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
			tbDCobservaciones.setEditable( edit );
			tbDCobservaciones.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
			tbDCobservaciones.setBounds( 127, 352, 510, 20 );
			tbDCobservaciones.setText( ( cliente.getObs() == null ) ? Constants.CADENA_VACIA : cliente.getObs() );

			tbDCobservaciones.addVerifyListener( upperTxt );

			tbDCemail.addVerifyListener( new VerifyListener() {
				public void verifyText( VerifyEvent e ) {
					e.text = e.text.toLowerCase();
				}
			} );
		}

		// EVENTOS PARA HABILITAR/DESHABILITAR BOTON GUARDAR

		tbDCnombre.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				enableButttonGuardar();
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		tbDCapaterno.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				enableButttonGuardar();
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		tbDCamaterno.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				enableButttonGuardar();
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		tbDCcalle.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				enableButttonGuardar();
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		tbDCcp.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				// obtiene codigo capturado
				strCodPost = tbDCcp.getText();
				// si oprime ENTER ejecuta busqueda
				if ( arg0.character == SWT.CR )
					if ( edit && strCodPost != null && !strCodPost.isEmpty() )
						loadCombosByCodigo();
				enableButttonGuardar();
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		tbDCnombre.addVerifyListener( upperTxt );
		tbDCapaterno.addVerifyListener( upperTxt );
		tbDCamaterno.addVerifyListener( upperTxt );
		tbDCcalle.addVerifyListener( upperTxt );

		// CARGA DATOS SI EXISTE EL CLIENTE
		validateClienteExist();
		// para que acepte solo numeros
		// en esta posicion permite que se cargue el codigo
		tbDCcp.addVerifyListener( new OnlyDigit() );
		tbDCtelcasa.addVerifyListener( new OnlyDigit() );
		// CAMBIA CAMPOS A READONLY
		enableFields();
		return container;
	}

	private void loadCombosByCodigo() {
		Cliente clienteTmp = new Cliente();
		clienteTmp.setMunicipio( new Municipio() );
		clienteTmp.getMunicipio().setId( new MunicipioId() );
		// se carga lista para combo colonia
		colonias = trabajoService.findAllCodigoByCodigo( strCodPost );
		for ( Codigo codigo : colonias ) {
			if ( codigo.getIdEstado() != null && codigo.getIdLocalidad() != null ) {
				// se cargan las colonias en combo
				cmbVwColonia.setInput( colonias );
				cmbColonia.setEnabled( true );
				currentCodigo = null;
				// asignacion de valores cliente
				clienteTmp.getMunicipio().getId().setIdEstado( codigo.getIdEstado() );
				clienteTmp.getMunicipio().getId().setIdLocalidad( codigo.getIdLocalidad() );
				// seleccion de registros en combos
				selectComboEstadoMunicipio( clienteTmp );
				break;
			}
		}
	}

	private void selectComboEstadoMunicipio( Cliente cliente ) {
		// ESTADO
		indexEstados = selectEstadoCombo( estados, cliente );
		if ( indexEstados != null ) {
			cmbEdos.select( indexEstados );
			currentEstadoRepublica = (EstadoRepublica) cmbVwEdos.getElementAt( indexEstados );
		}

		// DELEGACION/MUNICIPIO
		municipios = trabajoService.findAllDelMunByIdEstadoAndLocalidad( cliente.getMunicipio().getId().getIdEstado(), Constants.CADENA_VACIA );

		cmbVwDelMun.setInput( municipios );
		indexDelMun = selectDelMunCombo( municipios, cliente );
		if ( indexDelMun != null ) {
			cmbDelMun.setEnabled( true );
			cmbDelMun.select( indexDelMun );
			currentMunicipio = (Municipio) cmbVwDelMun.getElementAt( indexDelMun );
		}
	}

	private void validateClienteExist() {
		org.joda.time.DateTime feNaCliente;

		if ( cliente != null ) {
			tbDCnombre.setText( cliente.getNombreCli() );
			tbDCapaterno.setText( cliente.getApellidoPatCli() );
			if( cliente.getApellidoMatCli() != null && !cliente.getApellidoMatCli().isEmpty() ){
                tbDCamaterno.setText( cliente.getApellidoMatCli() );
            }
			tbDCcalle.setText( cliente.getDireccionCli() );
			tbDCcp.setText( cliente.getCodigo() );
			tbDCtelcasa.setText( StringUtils.isNotBlank( cliente.getTelCasaCli() ) ? cliente.getTelCasaCli().trim() : Constants.CADENA_VACIA );

			if ( edit ) {

				// se selecciona radioButton dependiendo del sexo
				rdBtnMasculino.setSelection( cliente.getSexoCli() );
				rdBtnFemenino.setSelection( !cliente.getSexoCli() );

				// selecciona Estado y Municipio
				selectComboEstadoMunicipio( cliente );

				// COLONIA
				colonias = trabajoService.findAllCodigoByIdEstadoAndLocalidad( cliente.getMunicipio().getId().getIdEstado(), cliente.getMunicipio().getId().getIdLocalidad() );
				cmbVwColonia.setInput( colonias );
				indexColonia = selectColoniaCombo( colonias, cliente );
				if ( indexColonia != null ) {
					cmbColonia.setEnabled( true );
					cmbColonia.select( indexColonia );
					currentCodigo = (Codigo) cmbVwColonia.getElementAt( indexColonia );
				}

				// DOMINIOS
				despliegaEmailCliente( cliente.getEmailCli() );

				// se agrega fecha de nacimiento del cliente al date-picker
				feNaCliente = new org.joda.time.DateTime( cliente.getFechaNac() );
				dtFeNac.setDate( feNaCliente.getYear(), feNaCliente.getMonthOfYear() - 1, feNaCliente.getDayOfMonth() );
				// se agrega fecahNacimiento a variable
				feDatePicker = cliente.getFechaNac();
			}

		} else {
			if ( edit ) {
				feNaCliente = new org.joda.time.DateTime( new Date() );
				dtFeNac.setDate( feNaCliente.getYear(), feNaCliente.getMonthOfYear() - 1, feNaCliente.getDayOfMonth() );
			}
		}
		tbDCnombre.setFocus();
	}

	private void enableFields() {
		if ( !edit ) {
			tbDCnombre.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
			tbDCapaterno.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
			tbDCamaterno.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
			fechaNacimientoTxt.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
			tbDCcalle.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
			tbDCcolonia.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
			tbDCcp.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
			tbDCestado.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
			tbDCdelmnpo.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
			tbDCtelcasa.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
			tbDCteltrabajo.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
			tbDCtelcelular.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
			tbDCemail.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		}
	}

	private Integer selectEstadoCombo( List<EstadoRepublica> lst, Cliente cliente ) {
		Integer index = null;

		for ( EstadoRepublica er : lst ) {
			if ( er.getIdEstado().equals( cliente.getMunicipio().getId().getIdEstado() ) ) {
				index = lst.indexOf( er );
				break;
			}
		}

		return index;
	}

	private Integer selectDelMunCombo( List<Municipio> lst, Cliente cliente ) {
		Integer index = null;

		for ( Municipio m : lst ) {
			if ( m.getId().getIdEstado().equals( cliente.getMunicipio().getId().getIdEstado() ) && m.getId().getIdLocalidad().equals( cliente.getMunicipio().getId().getIdLocalidad() ) ) {
				index = lst.indexOf( m );
				break;
			}
		}

		return index;
	}

	private Integer selectColoniaCombo( List<Codigo> lst, Cliente cliente ) {
		Integer index = null;

		for ( Codigo c : lst ) {
			if ( c.getIdEstado().equals( cliente.getMunicipio().getId().getIdEstado() ) && c.getIdLocalidad().equals( cliente.getMunicipio().getId().getIdLocalidad() ) ) {
				if ( c.getUsuario().trim().equals( cliente.getColoniaCli().trim() ) ) {
					index = lst.indexOf( c );
					break;
				}
			}
		}

		return index;
	}

	private void despliegaEmailCliente( String clienteEmail ) {
		log.debug( "Email del Cliente: " + clienteEmail );
		Pattern p = Pattern.compile( ".+@.+\\.[a-z]+" );
		if ( StringUtils.contains( clienteEmail, "@" ) && p.matcher( clienteEmail ).matches() ) {
			// se extrae el usuario y dominio del email como un arreglo de dos strings
			String[] emailItems = StringUtils.split( clienteEmail, "@" );
			String usuarioEmail = "";
			String dominioEmail = "";
			if ( emailItems.length > 1 ) {
				usuarioEmail = emailItems[0].trim();
				dominioEmail = emailItems[1].trim();
			}
			txtEmail.setText( usuarioEmail );

			for ( Dominios dominio : listaDominios ) {
				if ( StringUtils.equalsIgnoreCase( dominio.getNombre(), dominioEmail ) ) {
					// se obtiene la posicion del nombre del dominio del email, en caso que corresponda con alguno registrado
					indexDominios = listaDominios.indexOf( dominio );
				}
			}

			if ( indexDominios != null ) {
				cmbEmail.select( indexDominios );
				currentDominios = (Dominios) cmbVwEmail.getElementAt( indexDominios );
			} else {
				// en caso de no obtener la posicion del dominio, se muestra el campo abierto y se le asigna el dominio obtenido
				btnMostrarDominios.setVisible( true );
				cmbEmail.setVisible( false );
				txtOtroDominio.setVisible( true );
				txtOtroDominio.setText( dominioEmail );
			}
		}
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 682, 518 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.datos.cliente" ) );
		if ( winTitle != null ) {
			newShell.setText( Constants.RX_DOS_PUNTOS + " " + winTitle );
		}
	}

	/**
	 * Create contents of the button bar.
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		if ( !edit ) {
			Button botonEditarFormaContacto = createButton( parent, IDialogConstants.DETAILS_ID, IDialogConstants.CANCEL_LABEL, false );
			botonEditarFormaContacto.setImage( ControlTrabajosWindowElements.iconoEditar );
			botonEditarFormaContacto.setText( "Contacto" );
			botonEditarFormaContacto.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
			botonEditarFormaContacto.addMouseListener( new MouseListener() {
				@Override
				public void mouseDown( MouseEvent arg0 ) {
					String rx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
					EditarFormaContactoDialog dialogo = new EditarFormaContactoDialog( shell, rx );
					dialogo.open();
				}

				@Override
				public void mouseUp( MouseEvent arg0 ) {
				}

				@Override
				public void mouseDoubleClick( MouseEvent arg0 ) {
				}
			} );
		}


		btnGuardar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		btnGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		btnGuardar.setText( ApplicationPropertyHelper.getProperty( "button.guardar.label" ) );
		btnGuardar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		btnGuardar.setEnabled( false );
		btnGuardar.setVisible( edit );
		if ( edit ) {
			enableButttonGuardar();
		}
		Button buttonCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		// Rectangle rec = buttonCerrar.getBounds();
		// buttonCerrar.setBounds(rec.x, rec.y, rec.height, rec.y+40);
		buttonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		buttonCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
		buttonCerrar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		// EVENTOS
		btnGuardar.addMouseListener( new MouseListener() {
			@Override
			public void mouseUp( MouseEvent arg0 ) {
			}

			@Override
			public void mouseDown( MouseEvent arg0 ) {
				strEmail = getEmail();

				if ( StringUtils.isNotBlank( tbDCtelcasa.getText() ) && tbDCtelcasa.getText().length() < 10 ) {
					MessageDialog.openError( shell, MessagesPropertyHelper.getProperty( "numero.telefono.error.title" ), MessagesPropertyHelper.getProperty( "numero.telefono.error.message" ) );
				} else if ( StringUtils.isNotBlank( strEmail ) && !ApplicationUtils.isEmail( strEmail ) ) {
					MessageDialog.openError( shell, MessagesPropertyHelper.getProperty( "email.error.title" ), MessagesPropertyHelper.getProperty( "email.error.message" ) );
				} else {
					saveCliente();
				}

			}

			@Override
			public void mouseDoubleClick( MouseEvent arg0 ) {
			}
		} );
	}

	private void saveCliente() {
		Session.removeAttribute( Constants.ITEM_SELECTED_CLIENTE );

		try {
			if ( cliente != null ) {
				setValuesCliente( cliente );
				trabajoService.save( cliente );
				Session.setAttribute( Constants.ITEM_SELECTED_CLIENTE, cliente );
			} else {
				newCliente.setMunicipio( new Municipio() );
				setValuesCliente( newCliente );
				setOtherValuesCliente( newCliente );
				trabajoService.save( newCliente );
				Session.setAttribute( Constants.ITEM_SELECTED_CLIENTE, newCliente );
			}

			this.createShell().close();
			ordenServicioDialog = new OrdenServicioDialog( shell );
			ordenServicioDialog.open();
		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
	}

	private void setValuesCliente( Cliente cliente ) {
		cliente.setNombreCli( tbDCnombre.getText() );
		cliente.setApellidoPatCli( tbDCapaterno.getText() );
		cliente.setApellidoMatCli( tbDCamaterno.getText() );
		cliente.setSexoCli( rdBtnMasculino.getSelection() ? true : false );
		cliente.setFechaNac( feDatePicker );
		cliente.setDireccionCli( tbDCcalle.getText() );
		cliente.setCodigo( tbDCcp.getText() );
		cliente.getMunicipio().setEstadoRepublica( currentEstadoRepublica );
		cliente.setMunicipio( currentMunicipio );
		cliente.setColoniaCli( currentCodigo.getUsuario() );
		cliente.setTelCasaCli( tbDCtelcasa.getText().trim() );
		cliente.setIdAtendio( empleado.getIdEmpleado() );
		if ( StringUtils.isNotEmpty( strEmail ) ) {
			cliente.setEmailCli( strEmail );
		}
	}

	private void setOtherValuesCliente( Cliente cliente ) {
		cliente.setIdOftalmologo( Constants.CERO_INTEGER );
		cliente.setFechaAltaCli( new Date() );
		cliente.setTipoCli( Constants.CLIENTE_TIPO_NUEVO.charAt( 0 ) );
		cliente.setFCasadaCli( false );
		cliente.setAvisar( false );
		cliente.setIdSync( Constants.UNO_STRING.charAt( 0 ) );
		cliente.setIdSucursal( empleado.getSucursal().getIdSucursal() );
		cliente.setSUsaAnteojos( Constants.ESPACIO.charAt( 0 ) );
		cliente.setFechaMod( new Date() );
		cliente.setIdMod( Constants.CERO_STRING );
		cliente.setReceta( Constants.CERO_INTEGER );
		cliente.setHoraAlta( new Date() );
		cliente.setFinado( false );
		cliente.setModImp( false );
		cliente.setCalif( Constants.CERO_INTEGER );
	}

	private String getEmail() {
		if ( StringUtils.isNotEmpty( txtEmail.getText() ) ) {
			String username = txtEmail.getText().trim();
			StringBuilder sb = new StringBuilder( username );
			sb.append( Constants.SYMBOL_ARROBA );
			if ( btnMostrarDominios.getVisible() && StringUtils.isNotEmpty( txtOtroDominio.getText() ) ) {
				strOtroDominio = txtOtroDominio.getText().trim();
				sb.append( strOtroDominio );
				return sb.toString();
			} else if ( StringUtils.isNotEmpty( currentDominios.getNombre() ) ) {
				sb.append( currentDominios.getNombre().trim() );
				return sb.toString();
			}
		}
		return null;
	}

	private void enableButttonGuardar() {
		if ( edit ) {
			btnGuardar.setEnabled( !tbDCnombre.getText().isEmpty() && !tbDCapaterno.getText().isEmpty() && !tbDCamaterno.getText().isEmpty() && !tbDCcalle.getText().isEmpty() && !tbDCcp.getText().isEmpty() && ( rdBtnMasculino.getSelection() || rdBtnFemenino.getSelection() ) && currentMunicipio != null && currentCodigo != null && currentCodigo.getUsuario() != null && !currentCodigo.getUsuario().isEmpty() );
		}
	}
}

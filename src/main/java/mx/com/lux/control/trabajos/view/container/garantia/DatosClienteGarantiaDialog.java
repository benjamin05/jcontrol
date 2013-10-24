package mx.com.lux.control.trabajos.view.container.garantia;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.ClienteService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.render.CodigoRender;
import mx.com.lux.control.trabajos.view.render.DominiosRender;
import mx.com.lux.control.trabajos.view.render.EstadoRepublicaRender;
import mx.com.lux.control.trabajos.view.render.MunicipioRender;
import mx.com.lux.control.trabajos.view.render.SexoRender;
import mx.com.lux.control.trabajos.view.render.TipoFormaContactoRender;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

public class DatosClienteGarantiaDialog extends Dialog {

	private final Logger log = LoggerFactory.getLogger( DatosClienteGarantiaDialog.class );

	private Shell shell;
	private Cliente cliente;
	private String rx;

	private final ClienteService clienteService;
	private final TrabajoService trabajoService;

	// Cliente
	private Text textoNombre;
	private ComboViewer comboViewerSexo;
	private Combo comboSexo;
	private Text textoApellidoPaterno;
	private Text textoApellidoMaterno;
	private DateTime textoFechaNacimiento;
	private Text textoCalle;
	private Text textoCodigoPostal;
	private ComboViewer comboViewerEstado;
	private Combo comboEstado;
	private ComboViewer comboViewerDelegacionMunicipio;
	private Combo comboDelegacionMunicipio;
	private ComboViewer comboViewerColonia;
	private Combo comboColonia;
	private Text textoTelefono;
	private Text textoEmail;
	private ComboViewer comboViewerDominios;
	private Combo comboDominios;

	// Forma de contacto
	private Button botonSi;
	private Button botonNo;
	private ComboViewer comboViewerTipoContacto;
	private Combo comboTipoContacto;
	private Text textoContacto;
	private ComboViewer comboViewerDominios2;
	private Combo comboDominios2;
	private Text textoObservaciones;

	protected DatosClienteGarantiaDialog( final Shell parentShell, final String rx ) {
		super( parentShell );
		shell = parentShell;
		this.rx = rx;
		clienteService = ServiceLocator.getBean( ClienteService.class );
		trabajoService = ServiceLocator.getBean( TrabajoService.class );
		try {
			Jb trabajo = trabajoService.findById( rx );
			cliente = clienteService.findClienteById( Integer.parseInt( trabajo.getIdCliente() ) );
		} catch ( Exception e ) {
			log.error( "Error al cargar los datos iniciales: " + e.getMessage() );
		}
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 720, 530 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( "Datos del Cliente" );
		shell = newShell;
	}

	@Override
	protected Control createContents( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		crearCampos( crearContenedorDatosCliente( parent ) );
		crearCamposFormaContacto( crearContenedorFormaContacto( parent ) );
		crearBotones( crearContenedorBotones( parent ) );
		return parent;
	}

	private Composite crearContenedorDatosCliente( final Composite parent ) {
		Group contenedor = new Group( parent, SWT.NONE );
		contenedor.setText( "Datos de Cliente" );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout layout = new GridLayout( 12, false );
		layout.marginWidth = 10;
		layout.marginHeight = 10;
		layout.horizontalSpacing = 0;
		contenedor.setLayout( layout );
		GridData gd = new GridData();
		gd.widthHint = this.getInitialSize().x - 30;
		gd.heightHint = 240;
		gd.verticalIndent = 10;
		gd.horizontalIndent = 10;
		contenedor.setLayoutData( gd );
		return contenedor;
	}

	private Composite crearContenedorFormaContacto( final Composite parent ) {
		Group contenedor = new Group( parent, SWT.NONE );
		contenedor.setText( "Forma de Contacto" );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout layout = new GridLayout( 12, false );
		layout.marginWidth = 10;
		layout.marginHeight = 10;
		layout.horizontalSpacing = 0;
		contenedor.setLayout( layout );
		GridData gd = new GridData();
		gd.widthHint = this.getInitialSize().x - 30;
		gd.heightHint = 140 ;
		gd.verticalIndent = 0;
		gd.horizontalIndent = 10;
		contenedor.setLayoutData( gd );
		return contenedor;
	}

	private Composite crearContenedorBotones( final Composite parent ) {
		Composite contenedor = new Composite( parent, SWT.NONE );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout layout = new GridLayout( 12, false );
		layout.marginWidth = 10;
		layout.marginHeight = 0;
		layout.horizontalSpacing = 0;
		contenedor.setLayout( layout );
		GridData gd = new GridData();
		gd.widthHint = this.getInitialSize().x - 30;
		gd.heightHint = 50 ;
		gd.horizontalIndent = 10;
		contenedor.setLayoutData( gd );
		return contenedor;
	}

	private void crearCampos( final Composite contenedor ) {
		textoNombre = crearCampoTexto( contenedor, "Nombre", cliente.getNombreCli(), 2, 4 );
		comboSexo = crearComboSexo( contenedor, 2, 4 );
		textoApellidoPaterno = crearCampoTexto( contenedor, "Ap. paterno", cliente.getApellidoPatCli(), 2, 4 );
		textoFechaNacimiento = crearFechaNacimiento( contenedor, 2, 4 );
		textoApellidoMaterno = crearCampoTexto( contenedor, "Ap. materno", cliente.getApellidoMatCli(), 2, 4 );
		crearElementoVacio( contenedor, 6 );
		textoCalle = crearCampoTexto( contenedor, "Calle", cliente.getDireccionCli(), 2, 7 );
		textoCodigoPostal = crearCampoTexto( contenedor, "C.P.", cliente.getCodigo(), 1, 2 );
		comboColonia = crearComboColonias( contenedor, 2, 4 );
		comboDelegacionMunicipio = crearComboMunicipios( contenedor, 2, 4 );
		comboEstado = crearComboEstado( contenedor, 2, 4 );
		crearElementoVacio( contenedor, 6 );
		textoTelefono = crearCampoTexto( contenedor, "Teléfono", cliente.getTelCasaCli(), 2, 2 );
		textoEmail = crearCampoTexto( contenedor, "e-Mail", cliente.getEmailCli(), 2, 4 );
		comboDominios = crearComboDominios( contenedor, 2 );

		cargarCamposCodigoPostal();

		textoCodigoPostal.addModifyListener( new CodigoPostalListener() );
		comboEstado.addSelectionListener( new EstadoListener() );
		comboDelegacionMunicipio.addSelectionListener( new MunicipioListener() );
		comboDominios.addSelectionListener( new DominioListener() );
	}

	private DateTime crearFechaNacimiento( Composite contenedor, int columnasEtiqueta, int columnasFecha ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "  " + "Fecha Nac." );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = ancho( columnasEtiqueta );
		gd1.horizontalSpan = columnasEtiqueta;
		etiqueta.setLayoutData( gd1 );

		DateTime fechaNacimiento = new DateTime( contenedor, SWT.BORDER | SWT.DROP_DOWN );
		GridData gd2 = new GridData();
		gd2.widthHint = ancho( columnasFecha ) + 20;
		gd2.horizontalSpan = columnasFecha;
		fechaNacimiento.setLayoutData( gd2 );
		Calendar fecha = Calendar.getInstance();
		fecha.setTime( cliente.getFechaNac() );
		fechaNacimiento.setYear( fecha.get( Calendar.YEAR ) );
		fechaNacimiento.setMonth( fecha.get( Calendar.MONTH ) );
		fechaNacimiento.setDay( fecha.get( Calendar.DAY_OF_MONTH ) );

		return fechaNacimiento;
	}

	private void crearCamposFormaContacto( final Composite contenedor ) {
		crearBotonesSiNo( contenedor, 3, 1 );
		crearElementoVacio( contenedor, 7 );
		comboTipoContacto = crearComboTipoContacto( contenedor, 3, 4 );
		crearElementoVacio( contenedor, 5 );
		textoContacto  = crearCampoTexto( contenedor, "Contacto", "", 3, 5 );
		comboDominios2 = crearComboDominios2( contenedor, 4 );
		textoObservaciones = crearCampoTexto( contenedor, "Observaciones", "", 3, 9 );
		activarDesactivarFormaContacto( true );
	}

	private Text crearCampoTexto( Composite contenedor, String textoEtiqueta, String textoContenido, int columnasEtiqueta, int columnasTexto ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "  " + textoEtiqueta );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = ancho( columnasEtiqueta );
		gd1.horizontalSpan = columnasEtiqueta;
		etiqueta.setLayoutData( gd1 );

		Text texto = new Text( contenedor, SWT.BORDER );
		texto.setToolTipText( textoEtiqueta );
		GridData gd2 = new GridData();
		gd2.widthHint = ancho( columnasTexto );
		gd2.horizontalSpan = columnasTexto;
		texto.setLayoutData( gd2 );
		texto.setText( textoContenido );
		texto.addVerifyListener( new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
				e.text = ApplicationUtils.quitarAcentos( e.text );
			}
		} );

		return texto;
	}

	private Combo crearComboSexo( Composite contenedor, int columnasEtiqueta, int columnasCombo ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "  " + "Sexo" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = ancho( columnasEtiqueta );
		gd1.horizontalSpan = columnasEtiqueta;
		etiqueta.setLayoutData( gd1 );

		comboViewerSexo = new ComboViewer( contenedor, SWT.READ_ONLY );
		comboViewerSexo.setContentProvider( new ArrayContentProvider() );
		comboViewerSexo.setLabelProvider( new SexoRender() );
		comboViewerSexo.setInput( new Boolean[] { Boolean.TRUE, Boolean.FALSE } );
		Combo combo = comboViewerSexo.getCombo();
		combo.setVisibleItemCount( 2 );
		GridData gd0 = new GridData();
		gd0.widthHint = ancho( columnasCombo ) + 20;
		gd0.horizontalSpan = columnasCombo;
		combo.setLayoutData( gd0 );

		for( int i = 0; i < combo.getItems().length; i++ ) {
			Boolean sexo = (Boolean) comboViewerSexo.getElementAt( i );
			if( sexo.equals( cliente.getSexoCli() ) ) {
				combo.select( i );
			}
		}

		return combo;
	}

	private Combo crearComboEstado( Composite contenedor, int columnasEtiqueta, int columnasCombo ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "  " + "Estado" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = ancho( columnasEtiqueta );
		gd1.horizontalSpan = columnasEtiqueta;
		etiqueta.setLayoutData( gd1 );

		comboViewerEstado = new ComboViewer( contenedor, SWT.READ_ONLY );
		comboViewerEstado.setContentProvider( new ArrayContentProvider() );
		comboViewerEstado.setLabelProvider( new EstadoRepublicaRender() );
		comboViewerEstado.setInput( trabajoService.getAllEstadoRepublica() );
		Combo combo = comboViewerEstado.getCombo();
		GridData gd0 = new GridData();
		gd0.widthHint = ancho( columnasCombo ) + 10;
		gd0.horizontalSpan = columnasCombo;
		combo.setLayoutData( gd0 );

		return combo;
	}

	private Combo crearComboMunicipios( Composite contenedor, int columnasEtiqueta, int columnasCombo ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "  " + "Deleg./Mnpo" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = ancho( columnasEtiqueta );
		gd1.horizontalSpan = columnasEtiqueta;
		etiqueta.setLayoutData( gd1 );

		comboViewerDelegacionMunicipio = new ComboViewer( contenedor, SWT.READ_ONLY );
		comboViewerDelegacionMunicipio.setContentProvider( new ArrayContentProvider() );
		comboViewerDelegacionMunicipio.setLabelProvider( new MunicipioRender() );
		// comboViewer.setInput( null );
		Combo combo = comboViewerDelegacionMunicipio.getCombo();
		GridData gd0 = new GridData();
		gd0.widthHint = ancho( columnasCombo ) + 20;
		gd0.horizontalSpan = columnasCombo;
		combo.setLayoutData( gd0 );

		return combo;
	}

	private Combo crearComboColonias( Composite contenedor, int columnasEtiqueta, int columnasCombo ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "  " + "Colonia" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = ancho( columnasEtiqueta );
		gd1.horizontalSpan = columnasEtiqueta;
		etiqueta.setLayoutData( gd1 );

		comboViewerColonia = new ComboViewer( contenedor, SWT.SIMPLE );
		comboViewerColonia.setContentProvider( new ArrayContentProvider() );
		comboViewerColonia.setLabelProvider( new CodigoRender() );
		Combo combo = comboViewerColonia.getCombo();
		GridData gd0 = new GridData();
		gd0.widthHint = ancho( columnasCombo ) + 10;
		gd0.horizontalSpan = columnasCombo;
		combo.setLayoutData( gd0 );

		return combo;
	}

	private Combo crearComboDominios( Composite contenedor, int columnasCombo ) {
		comboViewerDominios = new ComboViewer( contenedor, SWT.READ_ONLY );
		comboViewerDominios.setContentProvider( new ArrayContentProvider() );
		comboViewerDominios.setLabelProvider( new DominiosRender() );
		java.util.List<Dominios> dominios = trabajoService.getAllDominios();
		Dominios dominio = new Dominios();
		dominio.setNombre( "@" );
		dominios.add( 0, dominio );
		comboViewerDominios.setInput( dominios );
		Combo combo = comboViewerDominios.getCombo();
		GridData gd0 = new GridData();
		gd0.widthHint = ancho( columnasCombo ) + 10;
		gd0.horizontalSpan = columnasCombo;
		combo.setLayoutData( gd0 );
		combo.setToolTipText( "Selecciona un dominio de email" );
		return combo;
	}

	private void crearBotonesSiNo( Composite contenedor, int columnasEtiqueta, int columnasRadio ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "  " + "¿Contactar al Cliente? " );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = ancho( columnasEtiqueta );
		gd1.horizontalSpan = columnasEtiqueta;
		etiqueta.setLayoutData( gd1 );


		botonNo = new Button( contenedor, SWT.RADIO );
		botonNo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		botonNo.setText( "No" );
		GridData gd2 = new GridData();
		gd2.widthHint = ancho( columnasRadio );
		gd2.horizontalSpan = columnasRadio;
		botonNo.setLayoutData( gd2 );
		botonNo.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				activarDesactivarFormaContacto( false );
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );

		botonSi = new Button( contenedor, SWT.RADIO );
		botonSi.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		botonSi.setText( "Si" );
		botonSi.setSelection( true );
		GridData gd0 = new GridData();
		gd0.widthHint = ancho( columnasRadio );
		gd0.horizontalSpan = columnasRadio;
		botonSi.setLayoutData( gd0 );
		botonSi.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				activarDesactivarFormaContacto( true );
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );
	}

	private void activarDesactivarFormaContacto( boolean activo ) {
		comboTipoContacto.setEnabled( activo );
		textoContacto.setEnabled( activo );
		comboDominios2.setEnabled( activo );
		textoObservaciones.setEnabled( activo );
		if( activo ) {
			TipoContacto seleccion = (TipoContacto) comboViewerTipoContacto.getElementAt( comboTipoContacto.getSelectionIndex() );
			if ( seleccion != null && seleccion.getIdTipoContacto() == 1 ) {
				comboDominios2.setEnabled( true );
			} else {
				comboDominios2.setEnabled( false );
			}
		}
	}

	private Combo crearComboTipoContacto( Composite contenedor, int columnasEtiqueta, int columnasCombo ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "  " + "Tipo Contacto" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = ancho( columnasEtiqueta );
		gd1.horizontalSpan = columnasEtiqueta;
		etiqueta.setLayoutData( gd1 );

		comboViewerTipoContacto = new ComboViewer( contenedor, SWT.READ_ONLY );
		comboViewerTipoContacto.setContentProvider( new ArrayContentProvider() );
		comboViewerTipoContacto.setLabelProvider( new TipoFormaContactoRender() );
		comboViewerTipoContacto.setInput( trabajoService.obtenerTiposFormaContacto() );
		Combo combo = comboViewerTipoContacto.getCombo();
		GridData gd0 = new GridData();
		gd0.widthHint = ancho( columnasCombo ) + 10;
		gd0.horizontalSpan = columnasCombo;
		combo.setLayoutData( gd0 );
		combo.setToolTipText( "Selecciona un tipo de contacto" );

		combo.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				haCambiadoSeleccionTipoContacto();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );

		return combo;
	}

	private Combo crearComboDominios2( Composite contenedor, int columnasCombo ) {
		comboViewerDominios2 = new ComboViewer( contenedor, SWT.READ_ONLY );
		comboViewerDominios2.setContentProvider( new ArrayContentProvider() );
		comboViewerDominios2.setLabelProvider( new DominiosRender() );
		java.util.List<Dominios> dominios = trabajoService.getAllDominios();
		Dominios dominio = new Dominios();
		dominio.setNombre( "@" );
		dominios.add( 0, dominio );
		comboViewerDominios2.setInput( dominios );
		Combo combo = comboViewerDominios2.getCombo();
		GridData gd0 = new GridData();
		gd0.widthHint = ancho( columnasCombo ) + 10;
		gd0.horizontalSpan = columnasCombo;
		combo.setLayoutData( gd0 );
		combo.setToolTipText( "Selecciona un dominio de email" );
		combo.setEnabled( false );
		combo.addSelectionListener( new DominioListener2() );
		return combo;
	}

	private void crearBotones( Composite contenedor ) {
		Composite botones = new Composite( contenedor, SWT.NONE );
		botones.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd10 = new GridData();
		gd10.widthHint = 220;
		gd10.horizontalSpan = 12;
		gd10.horizontalIndent = 470;
		botones.setLayoutData( gd10 );
		GridLayout layout2 = new GridLayout( 2, false );
		layout2.marginWidth = 0;
		layout2.marginHeight = 10;
		layout2.horizontalSpacing = 10;
		botones.setLayout( layout2 );

		Button botonCerrar = new Button( botones, SWT.NONE );
		botonCerrar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		botonCerrar.setText( "Cerrar" );
		botonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		GridData gd11 = new GridData();
		gd11.widthHint = 100;
		botonCerrar.setLayoutData( gd11 );
		botonCerrar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				shell.close();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );

		Button botonGuardar = new Button( botones, SWT.NONE );
		botonGuardar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		botonGuardar.setText( "Guardar" );
		botonGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		GridData gd9 = new GridData();
		gd9.widthHint = 100;
		botonGuardar.setLayoutData( gd9 );
		botonGuardar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				if( datosValidos( true, true ) ) {
					procesarDatosCliente();
					procesarFormaContacto();
					MessageDialog.openInformation( shell, "Datos de Cliente", "Se han guardado los datos correctamente" );
					shell.getParent().getParent().dispose();
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );
	}

	private int ancho( int numeroColumnas ) {
		int anchoColumna = 50;
		int anchoEntreColumnas = 5;
		return ( numeroColumnas * anchoColumna ) + ( ( numeroColumnas - 1 ) * anchoEntreColumnas );
	}

	private class CodigoPostalListener implements ModifyListener {
		@Override
		public void modifyText( ModifyEvent modifyEvent ) {
			String codigoPostal = textoCodigoPostal.getText();
			if ( codigoPostal != null && codigoPostal.length() == 5 ) {
				cargarCamposCodigoPostal();
			}
		}
	}

	private class EstadoListener implements SelectionListener {
		@Override
		public void widgetSelected( SelectionEvent event ) {
			EstadoRepublica estadoRepublica = (EstadoRepublica) comboViewerEstado.getElementAt( comboEstado.getSelectionIndex() );
			comboViewerDelegacionMunicipio.setInput( trabajoService.findAllDelMunByIdEstadoAndLocalidad( estadoRepublica.getIdEstado(), "" ) );
			comboColonia.removeAll();
		}

		@Override
		public void widgetDefaultSelected( SelectionEvent event ) {
		}
	}

	private class MunicipioListener implements SelectionListener {
		@Override
		public void widgetSelected( SelectionEvent event ) {
			EstadoRepublica estadoRepublica = (EstadoRepublica) comboViewerEstado.getElementAt( comboEstado.getSelectionIndex() );
			Municipio municipio = (Municipio) comboViewerDelegacionMunicipio.getElementAt( comboDelegacionMunicipio.getSelectionIndex() );
			comboViewerColonia.setInput( trabajoService.findAllCodigoByIdEstadoAndLocalidad( estadoRepublica.getIdEstado(), municipio.getId().getIdLocalidad() ) );
		}

		@Override
		public void widgetDefaultSelected( SelectionEvent event ) {
		}
	}

	private class DominioListener implements SelectionListener {
		@Override
		public void widgetSelected( SelectionEvent event ) {
			String dominio = comboDominios.getText();
			if( StringUtils.isNotBlank( dominio ) ) {
				dominio = dominio.toLowerCase().trim();
				String email = textoEmail.getText();
				if( StringUtils.isBlank( email ) ) {
					textoEmail.setText( dominio.contains( "@" ) ? dominio : "@" + dominio );
				} else {
					String[] partes = StringUtils.split( email, "@" );
					textoEmail.setText( partes[0] + ( dominio.contains( "@" ) ? dominio : "@" + dominio ) );
				}
				textoEmail.setFocus();
			}
		}

		@Override
		public void widgetDefaultSelected( SelectionEvent event ) {
		}
	}

	private class DominioListener2 implements SelectionListener {
		@Override
		public void widgetSelected( SelectionEvent event ) {
			String dominio = comboDominios2.getText();
			if( StringUtils.isNotBlank( dominio ) ) {
				dominio = dominio.toLowerCase().trim();
				String email = textoContacto.getText();
				if( StringUtils.isBlank( email ) ) {
					textoContacto.setText( dominio.contains( "@" ) ? dominio : "@" + dominio );
				} else {
					String[] partes = StringUtils.split( email, "@" );
					textoContacto.setText( partes[0] + ( dominio.contains( "@" ) ? dominio : "@" + dominio ) );
				}
				textoContacto.setFocus();
			}
		}

		@Override
		public void widgetDefaultSelected( SelectionEvent event ) {
		}
	}

	private void cargarCamposCodigoPostal() {
		String codigoPostal = textoCodigoPostal.getText();
		if ( StringUtils.isNotBlank( codigoPostal ) && codigoPostal.length() == 5 ) {
			seleccionarComboEstado( codigoPostal );
			cargarComboMunicipios( codigoPostal );
			seleccionarComboMunicipios( codigoPostal );
			cargarComboColonias( codigoPostal );
			seleccionarComboColonias();
		}
	}

	private void seleccionarComboEstado( String codigoPostal ) {
		if ( StringUtils.isNotBlank( codigoPostal ) ) {
			String idEstado = trabajoService.obtenerIdEstadoPorCodigoPostal( codigoPostal );
			int numeroEstados = comboEstado.getItems().length;
			for ( int i = 0; i < numeroEstados; i++ ) {
				EstadoRepublica estadoRepublica = (EstadoRepublica) comboViewerEstado.getElementAt( i );
				if ( idEstado.equals( estadoRepublica.getIdEstado() ) ) {
					comboEstado.select( i );
					break;
				}
			}
		}
	}

	private void cargarComboMunicipios( String codigoPostal ) {
		if( StringUtils.isNotBlank( codigoPostal ) ) {
			String idEstado = trabajoService.obtenerIdEstadoPorCodigoPostal( codigoPostal );
			comboViewerDelegacionMunicipio.setInput( trabajoService.findAllDelMunByIdEstadoAndLocalidad( idEstado, "" ) );
		}
	}

	private void seleccionarComboMunicipios( String codigoPostal ) {
		if( StringUtils.isNotBlank( codigoPostal ) ) {
			String idLocalidad = trabajoService.obtenerIdLocalidadPorCodigoPostal( codigoPostal );
			int numeroLocalidades = comboDelegacionMunicipio.getItems().length;
			for ( int i = 0; i < numeroLocalidades; i++ ) {
				Municipio municipio = (Municipio) comboViewerDelegacionMunicipio.getElementAt( i );
				if ( idLocalidad.equals( municipio.getId().getIdLocalidad() ) ) {
					comboDelegacionMunicipio.select( i );
					break;
				}
			}
		}
	}

	private void cargarComboColonias( String codigoPostal ) {
		if( StringUtils.isNotBlank( codigoPostal ) ) {
			comboViewerColonia.setInput( trabajoService.findAllCodigoByCodigo( codigoPostal ) );
		}
	}

	private void seleccionarComboColonias() {
		if( cliente != null && StringUtils.isNotBlank( cliente.getColoniaCli() ) ) {
			String colonia = cliente.getColoniaCli().trim();
			int numeroColonias = comboColonia.getItems().length;
			for( int i = 0; i < numeroColonias; i++ ) {
				Codigo codigo = (Codigo) comboViewerColonia.getElementAt( i );
				String nombreColonia = codigo.getUsuario().trim();
				if( nombreColonia.equalsIgnoreCase( colonia ) ) {
					comboColonia.select( i );
					break;
				}
			}
		}
	}

	private boolean datosValidos( boolean validarDatosCliente, boolean validarFormaContacto ) {
		if( validarDatosCliente ) {
			if( textoNombre.isEnabled() && !StringUtils.isNotBlank( textoNombre.getText() ) ) {
				MessageDialog.openError( shell, "Error", "Debe rellenar correctamente el campo Nombre" );
				textoNombre.setFocus();
				return false;
			}
			if( comboSexo.isEnabled() && comboSexo.getSelectionIndex() < 0 ) {
				MessageDialog.openError( shell, "Error", "Debe seleccionar una opción del campo Sexo" );
				comboSexo.setFocus();
				return false;
			}
			if( textoApellidoPaterno.isEnabled() && !StringUtils.isNotBlank( textoApellidoPaterno.getText() ) ) {
				MessageDialog.openError( shell, "Error", "Debe rellenar correctamente el campo Apellido paterno" );
				textoApellidoPaterno.setFocus();
				return false;
			}
			if( textoApellidoMaterno.isEnabled() && !StringUtils.isNotBlank( textoApellidoMaterno.getText() ) ) {
				MessageDialog.openError( shell, "Error", "Debe rellenar correctamente el campo Apellido materno" );
				textoApellidoMaterno.setFocus();
				return false;
			}
			if( textoCalle.isEnabled() && !StringUtils.isNotBlank( textoCalle.getText() ) ) {
				MessageDialog.openError( shell, "Error", "Debe rellenar correctamente el campo Calle" );
				textoCalle.setFocus();
				return false;
			}
			if( textoCodigoPostal.isEnabled() && !StringUtils.isNotBlank( textoCodigoPostal.getText() ) ) {
				MessageDialog.openError( shell, "Error", "Debe rellenar correctamente el campo Codigo Postal" );
				textoCodigoPostal.setFocus();
				return false;
			} else if( textoCodigoPostal.isEnabled() && textoCodigoPostal.getText().length() != 5 ) {
				MessageDialog.openError( shell, "Error", textoCodigoPostal.getText() + " no es un Codigo Postal correcto ( 5 digitos )" );
				textoCodigoPostal.setFocus();
				return false;
			}
			if( comboEstado.isEnabled() && comboEstado.getSelectionIndex() < 0 ) {
				MessageDialog.openError( shell, "Error", "Debe seleccionar una opción del campo Estado" );
				comboEstado.setFocus();
				return false;
			}
			if( comboDelegacionMunicipio.isEnabled() && comboDelegacionMunicipio.getSelectionIndex() < 0 ) {
				MessageDialog.openError( shell, "Error", "Debe seleccionar una opción del campo Municipio" );
				comboDelegacionMunicipio.setFocus();
				return false;
			}
			if( comboColonia.isEnabled() && comboColonia.getSelectionIndex() < 0 ) {
				MessageDialog.openError( shell, "Error", "Debe seleccionar una opción del campo Colonia" );
				comboColonia.setFocus();
				return false;
			}
			if( textoEmail.isEnabled() && StringUtils.isNotBlank( textoEmail.getText() ) && !ApplicationUtils.isEmail( textoEmail.getText() ) ) {
				MessageDialog.openError( shell, "Error", textoEmail.getText() + " no es un email correcto" );
				textoEmail.setFocus();
				return false;
			}
		}
		if( validarFormaContacto && botonSi.getSelection() ) {
			return esFormaContactoValida();
		}
		return true;
	}

	private void crearElementoVacio( Composite contenedor, int columnas ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd = new GridData();
		gd.widthHint = ancho( columnas );
		gd.horizontalSpan = columnas;
		etiqueta.setLayoutData( gd );
	}

	private void haCambiadoSeleccionTipoContacto() {
		textoContacto.setFocus();
		TipoContacto seleccion = (TipoContacto) comboViewerTipoContacto.getElementAt( comboTipoContacto.getSelectionIndex() );
		if ( seleccion != null && seleccion.getIdTipoContacto() == 1 ) {
			// Correo electronico
			String email = cliente.getEmailCli();
			if( StringUtils.isNotBlank( textoEmail.getText() ) && ApplicationUtils.isEmail( textoEmail.getText() ) ) {
				textoContacto.setText( textoEmail.getText() );
			} else if ( StringUtils.isNotBlank( email ) && ApplicationUtils.isEmail( email ) ) {
				textoContacto.setText( email );
			} else {
				textoContacto.setText( "" );
			}
			comboDominios2.setEnabled( true );
		} else if( seleccion != null && ( seleccion.getIdTipoContacto() == 2 || seleccion.getIdTipoContacto() == 3 ) ) {
			// SMS
			if( StringUtils.isNotBlank( cliente.getTelAdiCli() ) ) {
				textoContacto.setText( cliente.getTelAdiCli() );
			} else {
				textoContacto.setText( "" );
			}
			comboDominios2.setEnabled( false );
		} else if( seleccion != null && seleccion.getIdTipoContacto() == 4 ) {
			// Telefono
			if( StringUtils.isNotBlank( textoTelefono.getText() ) ) {
				textoContacto.setText( textoTelefono.getText() );
			} else if( StringUtils.isNotBlank( cliente.getTelCasaCli() ) ) {
				textoContacto.setText( cliente.getTelCasaCli() );
			} else if( StringUtils.isNotBlank( cliente.getTelTrabCli() ) ) {
				textoContacto.setText( cliente.getTelTrabCli() );
			} else {
				textoContacto.setText( "" );
			}
			comboDominios2.setEnabled( false );
		}
	}

	private Boolean esFormaContactoValida() {
		if ( comboTipoContacto.getSelectionIndex() < 0 ) {
			comboTipoContacto.setFocus();
			MessageDialog.openError( shell, "Error", "Debe seleccionar un Tipo de Contacto" );
			return false;
		}
		if ( StringUtils.isBlank( textoContacto.getText() ) ) {
			textoContacto.setFocus();
			MessageDialog.openError( shell, "Error", "Debe introducir un dato de contacto" );
			return false;
		}
		TipoContacto seleccion = (TipoContacto) comboViewerTipoContacto.getElementAt( comboTipoContacto.getSelectionIndex() );
		if ( seleccion != null && seleccion.getIdTipoContacto() == 1 ) {
			String email = textoContacto.getText();
			if ( StringUtils.isNotBlank( email ) && !ApplicationUtils.isEmail( email ) ) {
				textoContacto.setFocus();
				MessageDialog.openError( shell, "Error", "El campo contacto no es un email correcto" );
				return false;
			}
		} else if ( seleccion != null && ( seleccion.getIdTipoContacto() == 4 || seleccion.getIdTipoContacto() == 3 || seleccion.getIdTipoContacto() == 2 ) ) {
			if ( !StringUtils.isNumericSpace( textoContacto.getText() ) ) {
				textoContacto.setFocus();
				MessageDialog.openError( shell, "Error", "El campo contacto no es un número de teléfono correcto" );
				return false;
			}
		}
		return true;
	}

	private void procesarDatosCliente() {
		try {
			Jb trabajo = trabajoService.findById( rx );
			Cliente cliente = clienteService.findClienteById( Integer.parseInt( trabajo.getIdCliente() ) );
			cliente.setNombreCli( textoNombre.getText() );
			cliente.setApellidoPatCli( textoApellidoPaterno.getText() );
			cliente.setApellidoMatCli( textoApellidoMaterno.getText() );
			cliente.setSexoCli( (Boolean) comboViewerSexo.getElementAt( comboSexo.getSelectionIndex() ) );
			cliente.setDireccionCli( textoCalle.getText() );
			cliente.setCodigo( textoCodigoPostal.getText() );
			EstadoRepublica estadoRepublica = (EstadoRepublica) comboViewerEstado.getElementAt( comboEstado.getSelectionIndex() );
			cliente.setIdEstado( estadoRepublica.getIdEstado() );
			Municipio municipio = (Municipio) comboViewerDelegacionMunicipio.getElementAt( comboDelegacionMunicipio.getSelectionIndex() );
			cliente.setIdLocalidad( municipio.getId().getIdLocalidad() );
			cliente.setColoniaCli( comboColonia.getText() );
			cliente.setTelCasaCli( textoTelefono.getText() );
			cliente.setEmailCli( textoEmail.getText() );
			Calendar fechaNacimiento = Calendar.getInstance();
			fechaNacimiento.set( Calendar.YEAR, textoFechaNacimiento.getYear() );
			fechaNacimiento.set( Calendar.MONTH, textoFechaNacimiento.getMonth() );
			fechaNacimiento.set( Calendar.DAY_OF_MONTH, textoFechaNacimiento.getDay() );
			cliente.setFechaNac( fechaNacimiento.getTime() );
			trabajoService.save( cliente );
		} catch( Exception e ) {
			log.error( "Error al actualizar Datos de Cliente" );
			MessageDialog.openError( shell, "Error", "Error al actualizar Datos de Cliente" );
		}

	}

	private void procesarFormaContacto() {
		try {
			if( botonSi.getSelection() ) {
                Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
                Sucursal sucursal = empleado.getSucursal();
				TipoContacto tipoContacto = (TipoContacto) comboViewerTipoContacto.getElementAt( comboTipoContacto.getSelectionIndex() );
				FormaContacto formaContacto = new FormaContacto();
				formaContacto.setRx( rx );
				formaContacto.setIdCliente( cliente.getIdCliente() );
				formaContacto.setObservaciones( textoObservaciones.getText() );
				formaContacto.setTipoContacto( tipoContacto );
				formaContacto.setContacto( textoContacto.getText() );
                formaContacto.setIdSucursal( sucursal.getIdSucursal() );
                formaContacto.setFechaMod( new Date() );
				trabajoService.save( formaContacto );
			} else if( botonNo.getSelection() ) {
				Jb trabajo = trabajoService.findById( rx );
				trabajo.setNoLlamar( true );
				trabajoService.save( trabajo );
			} else {
				log.error( "Error ???" );
			}
		} catch( Exception e ) {
			log.error( "Error al guardar la Forma de Contacto" );
			MessageDialog.openError( shell, "Error", "Error al guardar la Forma de Contacto" );
		}
	}
}

package mx.com.lux.control.trabajos.view.container.externos;

import mx.com.lux.control.trabajos.bussiness.service.ExternoService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Banco;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbPagoExtra;
import mx.com.lux.control.trabajos.data.vo.Terminal;
import mx.com.lux.control.trabajos.data.vo.TipoPago;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.render.BancoRender;
import mx.com.lux.control.trabajos.view.render.FormaPagoRender;
import mx.com.lux.control.trabajos.view.render.PagoExtraRender;
import mx.com.lux.control.trabajos.view.render.TerminalRender;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EntregaExternoDialog extends Dialog {

	private Button botonOk;

	private final ExternoService externoService;
	private final String rx;
	private Jb trabajo;

	private final Shell shell;

	private ComboViewer formaPago;
	private Combo formaPagoCombo;
	private Text claveP;
	private Text referenciaClave;
	private ComboViewer banco;
	private Combo bancoCombo;
	private ComboViewer terminal;
	private Combo terminalCombo;
	private Text monto;
	private Label etiquetaFormaPago;
	private Label etiquetaClaveP;
	private Label etiquetaReferencia;
	private Label etiquetaBanco;
	private Label etiquetaTerminal;
	private Label etiquetaMonto;

	private Button botonEditar;
	private Button botonGuardar;
	private Button botonEliminar;

	private Text pagado;
	private Text porPagar;

	private Table tablaPagos;
	private List<JbPagoExtra> pagosExternos;

	private int idPagoExtraSeleccionado = 0;

	public EntregaExternoDialog( Shell parentShell ) {
		super( parentShell );
		shell = parentShell;
		externoService = ServiceLocator.getBean( ExternoService.class );
		TrabajoService trabajoService = ServiceLocator.getBean( TrabajoService.class );
		rx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
		try {
			trabajo = trabajoService.findById( rx );
		} catch ( Exception e ) {
			//
		}
	}

	@Override
	protected Point getInitialSize() {
		recalcularSaldos();
		return new Point( 660, 380 );
	}

	@Override
	protected Control createDialogArea( Composite parent ) {

		parent.getShell().setText( rx + "  -  " + trabajo.getCliente() + "  -  " + trabajo.getSaldo() );
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Composite contenedor = (Composite) super.createDialogArea( parent );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		RowLayout layout = new RowLayout( SWT.HORIZONTAL );
		layout.marginWidth = 5;
		layout.marginHeight = 10;
		contenedor.setLayout( layout );

		crearBotonesAcciones( contenedor );
		crearPago( contenedor );
		crearPagos( contenedor );
		crearSaldo( contenedor );

		recalcularSaldos();

		return contenedor;
	}

	private void crearBotonesAcciones( Composite contenedor ) {
		Composite grupo = new Composite( contenedor, SWT.NONE );
		grupo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		RowLayout layout = new RowLayout( SWT.HORIZONTAL );
		layout.marginWidth = 10;
		layout.marginHeight = 10;
		layout.spacing = 5;
		layout.fill = true;
		grupo.setLayout( layout );
		RowData rd = new RowData();
		rd.width = 360;
		rd.height = 45;
		grupo.setLayoutData( rd );

		Button botonAgregar = new Button( grupo, SWT.NONE );
		botonAgregar.setBounds( 485, 101, 86, 28 );
		botonAgregar.setImage( ControlTrabajosWindowElements.iconoAgregar );
		botonAgregar.setText( "Nuevo" );
		botonAgregar.addSelectionListener( new BotonNuevoListener() );

		botonEditar = new Button( grupo, SWT.NONE );
		botonEditar.setBounds( 485, 101, 86, 28 );
		botonEditar.setImage( ControlTrabajosWindowElements.iconoEditar );
		botonEditar.setText( "Editar" );
		botonEditar.setEnabled( false );
		botonEditar.addSelectionListener( new BotonEditarListener() );

		botonGuardar = new Button( grupo, SWT.NONE );
		botonGuardar.setBounds( 485, 101, 86, 28 );
		botonGuardar.setImage( ControlTrabajosWindowElements.iconoGuardar );
		botonGuardar.setText( "Guardar" );
		botonGuardar.setEnabled( false );
		botonGuardar.addSelectionListener( new BotonGuardarListener() );

		botonEliminar = new Button( grupo, SWT.NONE );
		botonEliminar.setBounds( 485, 101, 86, 28 );
		botonEliminar.setImage( ControlTrabajosWindowElements.iconoCancelar );
		botonEliminar.setText( "Eliminar" );
		botonEliminar.setEnabled( false );
		botonEliminar.addSelectionListener( new BotonEliminarListener() );

		Composite grupo2 = new Composite( contenedor, SWT.NONE );
		grupo2.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		RowLayout layout2 = new RowLayout( SWT.HORIZONTAL );
		layout2.marginHeight = 20;
		layout2.marginLeft = 40;
		layout2.justify = false;
		grupo2.setLayout( layout );
		RowData rd2 = new RowData();
		rd2.width = 283;
		rd2.height = 43;
		grupo2.setLayoutData( rd2 );

		Label etiquetaPorPagar = new Label( grupo2, SWT.NONE );
		etiquetaPorPagar.setFont( new Font( null, "Arial", 14, SWT.NORMAL ) );
		etiquetaPorPagar.setText( " Por pagar: " );
		etiquetaPorPagar.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		porPagar = new Text( grupo2, SWT.BORDER | SWT.RIGHT );
		porPagar.setEnabled( true );
		porPagar.setEditable( false );
		porPagar.setFont( new Font( null, "Arial", 14, SWT.NORMAL ) );
		porPagar.setText( trabajo.getSaldo().toString() );
		RowData rd3 = new RowData();
		rd3.width = 155;
		porPagar.setLayoutData( rd3 );
	}

	private void crearPago( Composite contenedor ) {
		Composite grupoPago = new Composite( contenedor, SWT.NONE );
		grupoPago.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		// grupoPago.setText( "Datos de pago" );
		GridLayout layout = new GridLayout( 6, false );
		layout.marginWidth = 10;
		layout.marginHeight = 5;
		grupoPago.setLayout( layout );
		RowData rd = new RowData();
		rd.width = 640;
		grupoPago.setLayoutData( rd );

		etiquetaFormaPago = new Label( grupoPago, SWT.NONE );
		etiquetaFormaPago.setText( "Forma de pago" );
		etiquetaFormaPago.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData rd1 = new GridData();
		rd1.widthHint = 100;
		etiquetaFormaPago.setLayoutData( rd1 );

		etiquetaClaveP = new Label( grupoPago, SWT.NONE );
		etiquetaClaveP.setText( "                    " );
		etiquetaClaveP.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData rd2 = new GridData();
		rd2.widthHint = 90;
		etiquetaClaveP.setLayoutData( rd2 );

		etiquetaReferencia = new Label( grupoPago, SWT.NONE );
		etiquetaReferencia.setText( "                    " );
		etiquetaReferencia.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData rd3 = new GridData();
		rd3.widthHint = 100;
		etiquetaReferencia.setLayoutData( rd3 );

		etiquetaBanco = new Label( grupoPago, SWT.NONE );
		etiquetaBanco.setText( "                    " );
		etiquetaBanco.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData rd4 = new GridData();
		rd4.widthHint = 100;
		etiquetaBanco.setLayoutData( rd4 );

		etiquetaTerminal = new Label( grupoPago, SWT.NONE );
		etiquetaTerminal.setText( "                    " );
		etiquetaTerminal.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData rd5 = new GridData();
		rd5.widthHint = 70;
		etiquetaTerminal.setLayoutData( rd5 );

		etiquetaMonto = new Label( grupoPago, SWT.NONE );
		etiquetaMonto.setText( "Monto" );
		etiquetaMonto.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData rd7 = new GridData();
		rd7.widthHint = 100;
		etiquetaMonto.setLayoutData( rd7 );

		formaPago = new ComboViewer( grupoPago, SWT.READ_ONLY );
		formaPago.setContentProvider( new ArrayContentProvider() );
		formaPago.setLabelProvider( new FormaPagoRender() );
		formaPago.setInput( externoService.obtenerTiposPago() );
		formaPagoCombo = formaPago.getCombo();
		formaPagoCombo.setVisibleItemCount( 5 );
		GridData rd8 = new GridData();
		rd8.widthHint = 100;
		formaPagoCombo.setLayoutData( rd8 );
		formaPagoCombo.setEnabled( false );

		formaPago.addSelectionChangedListener( new ISelectionChangedListener() {
			@Override
			public void selectionChanged( SelectionChangedEvent arg0 ) {
				if ( !arg0.getSelection().isEmpty() ) {
					haCambiadoSeleccionFormaPago();
				}
			}
		} );

		claveP = new Text( grupoPago, SWT.BORDER | SWT.CENTER );
		claveP.setEnabled( false );
		claveP.setTextLimit( 16 );
		GridData rd9 = new GridData();
		rd9.widthHint = 90;
		claveP.setLayoutData( rd9 );

		referenciaClave = new Text( grupoPago, SWT.BORDER | SWT.CENTER );
		referenciaClave.setEnabled( false );
		GridData rd10 = new GridData();
		rd10.widthHint = 100;
		referenciaClave.setLayoutData( rd10 );

		banco = new ComboViewer( grupoPago, SWT.READ_ONLY );
		bancoCombo = banco.getCombo();
		bancoCombo.setEnabled( false );
		banco.setContentProvider( new ArrayContentProvider() );
		banco.setLabelProvider( new BancoRender() );
		banco.getCombo().setVisibleItemCount( 10 );
		banco.setInput( externoService.obtenerBancos() );
		GridData rd11 = new GridData();
		rd11.widthHint = 100;
		banco.getCombo().setLayoutData( rd11 );

		terminal = new ComboViewer( grupoPago, SWT.READ_ONLY );
		terminalCombo = terminal.getCombo();
		terminalCombo.setEnabled( false );
		terminal.setContentProvider( new ArrayContentProvider() );
		terminal.setLabelProvider( new TerminalRender() );
		terminal.getCombo().setVisibleItemCount( 10 );
		terminal.setInput( externoService.obtenerTerminales() );
		GridData rd12 = new GridData();
		rd12.widthHint = 70;
		terminalCombo.setLayoutData( rd12 );

		monto = new Text( grupoPago, SWT.BORDER | SWT.RIGHT );
		monto.setEnabled( false );
		GridData rd14 = new GridData();
		rd14.widthHint = 113;
		monto.setLayoutData( rd14 );

	}

	private void crearPagos( Composite contenedor ) {

		tablaPagos = new Table( contenedor, SWT.BORDER | SWT.VIRTUAL );
		tablaPagos.setHeaderVisible( true );
		tablaPagos.setLinesVisible( true );
		RowData rd = new RowData();
		rd.width = 620;
		rd.height = 100;
		tablaPagos.setLayoutData( rd );

		TableColumn col1 = new TableColumn( tablaPagos, SWT.NONE );
		col1.setWidth( 90 );
		col1.setText( "Fecha" );

		TableColumn col2 = new TableColumn( tablaPagos, SWT.CENTER );
		col2.setWidth( 45 );
		col2.setText( "Pago" );

		TableColumn col3 = new TableColumn( tablaPagos, SWT.NONE );
		col3.setWidth( 70 );
		col3.setText( "Numero" );

		TableColumn col4 = new TableColumn( tablaPagos, SWT.NONE );
		col4.setWidth( 65 );
		col4.setText( "Term" );

		TableColumn col5 = new TableColumn( tablaPagos, SWT.NONE );
		col5.setWidth( 55 );
		col5.setText( "Promo" );

		TableColumn col6 = new TableColumn( tablaPagos, SWT.NONE );
		col6.setWidth( 95 );
		col6.setText( "Refer" );

		TableColumn col7 = new TableColumn( tablaPagos, SWT.NONE );
		col7.setWidth( 60 );
		col7.setText( "Banco" );

		TableColumn col8 = new TableColumn( tablaPagos, SWT.RIGHT );
		col8.setWidth( 75 );
		col8.setText( "Importe" );

		TableColumn col9 = new TableColumn( tablaPagos, SWT.NONE );
		col9.setWidth( 0 );
		col9.setText( "Id" );

		// tablaPagos.setSortColumn( col1 );

		cargarSaldos();

		tablaPagos.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				int index = tablaPagos.getSelectionIndex();
				if ( index >= 0 ) {
					JbPagoExtra pagoExtra = pagosExternos.get( index );
					// JbPagoExtra pagoExtra = pagingListener.getItem( index );
					idPagoExtraSeleccionado = pagoExtra.getIdPago();
					botonEditar.setEnabled( true );
					botonEliminar.setEnabled( true );
				} else {
					idPagoExtraSeleccionado = 0;
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );

		tablaPagos.addListener( SWT.SetData, new Listener() {
			public void handleEvent( Event e ) {
				TableItem item = (TableItem) e.item;
				int index = tablaPagos.indexOf( item );
				PagoExtraRender render = new PagoExtraRender();
				JbPagoExtra pagoExterno = pagosExternos.get( index );
				item.setText( render.getText( pagoExterno ) );
			}
		} );
	}

	private void crearSaldo( Composite contenedor ) {

		Double saldoPagadoD = 0d;

		Composite grupoSaldo = new Composite( contenedor, SWT.NONE );
		grupoSaldo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		RowLayout layout = new RowLayout( SWT.HORIZONTAL );
		layout.marginWidth = 10;
		layout.marginLeft = 395;
		layout.justify = false;
		grupoSaldo.setLayout( layout );
		RowData rd = new RowData();
		rd.width = 645;
		grupoSaldo.setLayoutData( rd );

		Label etiquetaPagado = new Label( grupoSaldo, SWT.NONE );
		etiquetaPagado.setText( "Pagado:" );
		etiquetaPagado.setFont( new Font( null, "Arial", 14, SWT.NORMAL ) );
		etiquetaPagado.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		pagado = new Text( grupoSaldo, SWT.BORDER | SWT.RIGHT );
		pagado.setEnabled( true );
		pagado.setEditable( false );
		pagado.setFont( new Font( null, "Arial", 14, SWT.NORMAL ) );
		pagado.setText( saldoPagadoD.toString() );
		RowData rd2 = new RowData();
		rd2.width = 155;
		pagado.setLayoutData( rd2 );


	}

	// Listeners de los botones de acciones

	class BotonNuevoListener implements SelectionListener {

		@Override
		public void widgetSelected( SelectionEvent arg0 ) {
			botonEditar.setEnabled( false );
			botonEliminar.setEnabled( false );
			botonGuardar.setEnabled( true );

			formaPagoCombo.deselectAll();
			claveP.setText( "" );
			referenciaClave.setText( "" );
			bancoCombo.deselectAll();
			terminalCombo.deselectAll();
			monto.setText( "" );

			formaPagoCombo.setEnabled( true );
			formaPagoCombo.redraw();
			claveP.setEnabled( false );
			referenciaClave.setEnabled( false );
			bancoCombo.setEnabled( false );
			terminalCombo.setEnabled( false );
			idPagoExtraSeleccionado = -1;
		}

		@Override
		public void widgetDefaultSelected( SelectionEvent arg0 ) {
		}
	}

	class BotonEditarListener implements SelectionListener {

		@Override
		public void widgetSelected( SelectionEvent arg0 ) {
			cargarPagoDesdeTabla();
			botonEditar.setEnabled( false );
			botonEliminar.setEnabled( false );
			botonGuardar.setEnabled( true );
		}

		@Override
		public void widgetDefaultSelected( SelectionEvent arg0 ) {
		}
	}

	class BotonGuardarListener implements SelectionListener {

		@Override
		public void widgetSelected( SelectionEvent arg0 ) {
			if ( idPagoExtraSeleccionado > 0 ) {
				actualizarPago();
			} else {
				guardarPago();
			}
		}

		@Override
		public void widgetDefaultSelected( SelectionEvent arg0 ) {
		}
	}

	class BotonEliminarListener implements SelectionListener {

		@Override
		public void widgetSelected( SelectionEvent arg0 ) {
			if ( MessageDialog.openConfirm( shell, "Confirmar eliminar", "¿ Eliminar el pago externo seleccionado ?" ) ) {
				externoService.eliminarPagoExtraPorId( idPagoExtraSeleccionado );
				botonEditar.setEnabled( false );
				botonEliminar.setEnabled( false );
				botonGuardar.setEnabled( true );
				idPagoExtraSeleccionado = 0;
				cargarSaldos();
				recalcularSaldos();
				idPagoExtraSeleccionado = -1;
			}
		}

		@Override
		public void widgetDefaultSelected( SelectionEvent arg0 ) {
		}
	}

	private void guardarPago() {
		if ( validarPago() ) {
			JbPagoExtra pagoExtra = new JbPagoExtra();
			pagoExtra.setFactura( rx );
			pagoExtra.setMonto( new BigDecimal( Double.parseDouble( monto.getText() ) ) );
			pagoExtra.setIdFPago( formaPagoCombo.getSelectionIndex() > -1 ? ( (TipoPago) formaPago.getElementAt( formaPagoCombo.getSelectionIndex() ) ).getId().trim() : "" );
			pagoExtra.setClaveP( claveP.getText() );
			pagoExtra.setRefClave( referenciaClave.getText() );
			pagoExtra.setIdBancoEmi( bancoCombo.getSelectionIndex() > -1 ? ( (Banco) banco.getElementAt( bancoCombo.getSelectionIndex() ) ).getId().toString().trim() : "" );
			pagoExtra.setIdTerm( terminalCombo.getSelectionIndex() > -1 ? ( (Terminal) terminal.getElementAt( terminalCombo.getSelectionIndex() ) ).getIdTerminal().trim() : "" );
			if ( "TC".equals( pagoExtra.getIdFPago() ) ) {
				pagoExtra.setIdPlan( "N" );
			}
			pagoExtra.setIdFormaPago( formaPagoCombo.getSelectionIndex() > -1 ? ( (TipoPago) formaPago.getElementAt( formaPagoCombo.getSelectionIndex() ) ).getId().trim() : "" );

			String formaPagoId = formaPagoCombo.getSelectionIndex() > -1 ? ( (TipoPago) formaPago.getElementAt( formaPagoCombo.getSelectionIndex() ) ).getId().trim() : "";
			if ( "TC".equals( formaPagoId ) || "TD".equals( formaPagoId ) ) {
				String cp = claveP.getText();
				cp = StringUtils.remove( cp, " " );
				pagoExtra.setRefer( cp.length() > 4 ? cp.substring( cp.length() - 4 ) : cp );
			}
			pagoExtra.setConfirm( false );
			pagoExtra.setFecha( new Timestamp( new Date().getTime() ) );

			try {
				externoService.guardarPagoExtra( pagoExtra );
			} catch ( Exception e ) {
				Status status = new Status( IStatus.ERROR, "JSOI", 0, "Error al guardar el pago", null );
				ErrorDialog.openError( shell, "Error", "Error", status );
			}
			recalcularSaldos();
			recargarSaldos();
			limpiarCamposPago();
		}
	}

	private void actualizarPago() {
		if ( validarPago() && idPagoExtraSeleccionado > 0 ) {
			JbPagoExtra pagoExtra = new JbPagoExtra();
			pagoExtra.setFactura( rx );
			pagoExtra.setMonto( new BigDecimal( Double.parseDouble( monto.getText() ) ) );
			pagoExtra.setIdFPago( formaPagoCombo.getSelectionIndex() > -1 ? ( (TipoPago) formaPago.getElementAt( formaPagoCombo.getSelectionIndex() ) ).getId().trim() : "" );
			pagoExtra.setClaveP( claveP.isEnabled() ? claveP.getText() : "" );
			pagoExtra.setRefClave( referenciaClave.isEnabled() ? referenciaClave.getText() : "" );
			pagoExtra.setIdBancoEmi( ( bancoCombo.isEnabled() && bancoCombo.getSelectionIndex() > -1 ) ? ( (Banco) banco.getElementAt( bancoCombo.getSelectionIndex() ) ).getId().toString().trim() : "" );
			pagoExtra.setIdTerm( ( terminalCombo.isEnabled() && terminalCombo.getSelectionIndex() > -1 ) ? ( (Terminal) terminal.getElementAt( terminalCombo.getSelectionIndex() ) ).getIdTerminal().trim() : "" );
			pagoExtra.setIdFormaPago( ( formaPagoCombo.isEnabled() && formaPagoCombo.getSelectionIndex() > -1 ) ? ( (TipoPago) formaPago.getElementAt( formaPagoCombo.getSelectionIndex() ) ).getId().trim() : "" );

			String formaPagoId = formaPagoCombo.getSelectionIndex() > -1 ? ( (TipoPago) formaPago.getElementAt( formaPagoCombo.getSelectionIndex() ) ).getId().trim() : "";
			if ( "TC".equals( formaPagoId ) || "TD".equals( formaPagoId ) ) {
				String cp = claveP.getText();
				cp = StringUtils.remove( cp, " " );
				pagoExtra.setRefer( cp.length() > 4 ? cp.substring( cp.length() - 4 ) : cp );
			}
			if ( "TC".equals( formaPagoId ) ) {
				pagoExtra.setIdPlan( "N" );
			}
			pagoExtra.setConfirm( false );
			pagoExtra.setFecha( new Timestamp( new Date().getTime() ) );

			try {
				externoService.actualizarPagoExtra( idPagoExtraSeleccionado, pagoExtra );
			} catch ( Exception e ) {
				Status status = new Status( IStatus.ERROR, "JSOI", 0, "Error al guardar el pago", null );
				ErrorDialog.openError( shell, "Error", "Error", status );
			}
			recalcularSaldos();
			recargarSaldos();
			limpiarCamposPago();
			idPagoExtraSeleccionado = -1;
		}
	}

	private boolean validarPago() {
		String errorPago = "Datos de Pago incorrecto";
		if ( formaPagoCombo.isEnabled() && formaPagoCombo.getSelectionIndex() < 0 ) {
			formaPagoCombo.setFocus();
			Status status = new Status( IStatus.ERROR, "JSOI", 0, "Debe seleccionar una opcion del campo \"" + etiquetaFormaPago.getText().trim() + "\"", null );
			ErrorDialog.openError( shell, "Error", errorPago, status );
			return false;
		}
		if ( claveP.isEnabled() && StringUtils.isBlank( claveP.getText() ) ) {
			claveP.setFocus();
			Status status = new Status( IStatus.ERROR, "JSOI", 0, "El campo \"" + etiquetaClaveP.getText().trim() + "\" no puede estar vacio", null );
			ErrorDialog.openError( shell, "Error", errorPago, status );
			return false;
		} else if ( claveP.isEnabled() && !StringUtils.isNumeric( claveP.getText() ) ) {
			claveP.setFocus();
			Status status = new Status( IStatus.ERROR, "JSOI", 0, "El campo \"" + etiquetaClaveP.getText().trim() + "\" solo puede ser numerico", null );
			ErrorDialog.openError( shell, "Error", errorPago, status );
			return false;
		}
		if ( referenciaClave.isEnabled() && StringUtils.isBlank( referenciaClave.getText() ) ) {
			referenciaClave.setFocus();
			Status status = new Status( IStatus.ERROR, "JSOI", 0, "El campo \"" + etiquetaReferencia.getText().trim() + "\" no puede estar vacio", null );
			ErrorDialog.openError( shell, "Error", errorPago, status );
			return false;
		} else if ( referenciaClave.isEnabled() && !StringUtils.isNumeric( referenciaClave.getText() ) ) {
			referenciaClave.setFocus();
			Status status = new Status( IStatus.ERROR, "JSOI", 0, "El campo \"" + etiquetaReferencia.getText().trim() + "\" solo puede ser numerico", null );
			ErrorDialog.openError( shell, "Error", errorPago, status );
			return false;
		}
		if ( bancoCombo.isEnabled() && bancoCombo.getSelectionIndex() < 0 ) {
			bancoCombo.setFocus();
			Status status = new Status( IStatus.ERROR, "JSOI", 0, "Debe seleccionar una opcion del campo \"" + etiquetaBanco.getText().trim() + "\"", null );
			ErrorDialog.openError( shell, "Error", errorPago, status );
			return false;
		}
		if ( terminalCombo.isEnabled() && terminalCombo.getSelectionIndex() < 0 ) {
			terminalCombo.setFocus();
			Status status = new Status( IStatus.ERROR, "JSOI", 0, "Debe seleccionar una opcion del campo \"" + etiquetaTerminal.getText().trim() + "\"", null );
			ErrorDialog.openError( shell, "Error", errorPago, status );
			return false;
		}
		if ( monto.isEnabled() && StringUtils.isBlank( monto.getText() ) ) {
			monto.setFocus();
			Status status = new Status( IStatus.ERROR, "JSOI", 0, "El campo \"" + etiquetaMonto.getText().trim() + "\" no puede estar vacio", null );
			ErrorDialog.openError( shell, "Error", errorPago, status );
			return false;
		} else if ( monto.isEnabled() ) {
			String montoTmp = monto.getText();
			if ( montoTmp.contains( "-" ) ) {
				Status status = new Status( IStatus.ERROR, "JSOI", 0, "El monto no puede ser negativo", null );
				ErrorDialog.openError( shell, "Error", errorPago, status );
				monto.setFocus();
				return false;
			}
			try {
				// si produce una excepcion, no es un numero correcto
				Double.parseDouble( montoTmp );
			} catch ( Exception e ) {
				monto.setFocus();
				Status status = new Status( IStatus.ERROR, "JSOI", 0, "El campo \"" + etiquetaMonto.getText().trim() + "\" debe ser un importe correcto ( sin separador de miles )", null );
				ErrorDialog.openError( shell, "Error", errorPago, status );
				return false;
			}
		}
		return true;
	}

	private void cargarSaldos() {
		pagosExternos = externoService.obtenerPagosExtraPorRx( rx );
		tablaPagos.setItemCount( pagosExternos.size() );
		tablaPagos.clearAll();
		tablaPagos.redraw();
	}

	private void recargarSaldos() {
		pagosExternos = externoService.obtenerPagosExtraPorRx( rx );
		tablaPagos.setItemCount( pagosExternos.size() );
		tablaPagos.clearAll();
		tablaPagos.redraw();
	}

	private void recalcularSaldos() {
		List<JbPagoExtra> pagosExtra = externoService.obtenerPagosExtraPorRxPaginado( rx, 0, 9999 );
		BigDecimal saldo = trabajo.getSaldo();
		BigDecimal tmp = BigDecimal.ZERO;
		for ( JbPagoExtra pe : pagosExtra ) {
			tmp = tmp.add( pe.getMonto() );
		}
		BigDecimal pendiente = saldo.subtract( tmp );
		NumberFormat nf = NumberFormat.getCurrencyInstance( Locale.US );

		this.pagado.setText( nf.format( tmp.doubleValue() ) );
		this.porPagar.setText( nf.format( pendiente.doubleValue() ) );
		if ( pendiente.compareTo( BigDecimal.ZERO ) < 0 ) {
			this.porPagar.setText( nf.format( 0d ) );
			this.pagado.setForeground( new Color( null, 255, 100, 100 ) );
			this.porPagar.setForeground( new Color( null, 0, 0, 0 ) );
			if ( botonOk != null ) {
				botonOk.setEnabled( false );
			}
		} else if ( pendiente.compareTo( BigDecimal.ZERO ) == 0 ) {
			this.pagado.setForeground( new Color( null, 0, 0, 0 ) );
			this.porPagar.setForeground( new Color( null, 50, 200, 50 ) );
			if ( botonOk != null ) {
				botonOk.setEnabled( true );
			}
		} else {
			this.pagado.setForeground( new Color( null, 0, 0, 0 ) );
			this.porPagar.setForeground( new Color( null, 0, 0, 0 ) );
			if ( botonOk != null ) {
				botonOk.setEnabled( false );
			}
		}

	}

	private void limpiarCamposPago() {
		botonEditar.setEnabled( false );
		botonEliminar.setEnabled( false );
		botonGuardar.setEnabled( false );

		formaPagoCombo.deselectAll();
		formaPagoCombo.setEnabled( false );
		claveP.setText( "" );
		referenciaClave.setText( "" );
		bancoCombo.deselectAll();
		terminalCombo.deselectAll();
		monto.setText( "" );

		formaPagoCombo.setEnabled( false );
		claveP.setEnabled( false );
		referenciaClave.setEnabled( false );
		bancoCombo.setEnabled( false );
		terminalCombo.setEnabled( false );
		monto.setEnabled( false );
	}

	@Override
	protected void cancelPressed() {
		super.cancelPressed();
	}

	@Override
	protected void okPressed() {
		super.okPressed();
		try {
			Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
			externoService.entregarExternoConSaldo( rx, empleado.getIdEmpleado().trim() );
		} catch ( Exception e ) {
			Status status = new Status( IStatus.ERROR, "JSOI", 0, "Error al procesar el trabajo", null );
			ErrorDialog.openError( shell, "Error", "", status );
		}
	}

	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		super.createButtonsForButtonBar( parent );

		botonOk = getButton( IDialogConstants.OK_ID );
		botonOk.setImage( ControlTrabajosWindowElements.saveIcon );
		botonOk.setText( ApplicationPropertyHelper.getProperty( "button.guardar.label" ) );
		botonOk.setEnabled( false );
		setButtonLayoutData( botonOk );

		Button botonCancel = getButton( IDialogConstants.CANCEL_ID );
		botonCancel.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
		botonCancel.setImage( ControlTrabajosWindowElements.closeIcon );
		setButtonLayoutData( botonCancel );

		botonOk.getParent().setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
	}

	private void cargarPagoDesdeTabla() {
		int index = tablaPagos.getSelectionIndex();
		if ( index >= 0 ) {
			// JbPagoExtra pagoExtra = pagingListener.getItem( index );
			JbPagoExtra pagoExtra = pagosExternos.get( index );
			idPagoExtraSeleccionado = pagoExtra.getIdPago();
			if ( StringUtils.isNotBlank( pagoExtra.getIdFormaPago() ) ) {
				String[] items = formaPagoCombo.getItems();
				TipoPago tipoPago = externoService.obtenerTipoPagoPorId( pagoExtra.getIdFormaPago() );
				for ( int i = 0; i < formaPagoCombo.getItems().length; i++ ) {
					if ( tipoPago.getId().trim().equalsIgnoreCase( items[i].trim() ) ) {
						formaPagoCombo.select( i );
						formaPagoCombo.setEnabled( true );
						break;
					}
				}
				haCambiadoSeleccionFormaPago();
			}
			if ( StringUtils.isNotBlank( pagoExtra.getClaveP() ) ) {
				claveP.setText( pagoExtra.getClaveP().trim() );
			}
			if ( StringUtils.isNotBlank( pagoExtra.getRefClave() ) ) {
				referenciaClave.setText( pagoExtra.getRefClave().trim() );
			}
			if ( StringUtils.isNotBlank( pagoExtra.getIdBancoEmi() ) ) {
				String[] items = bancoCombo.getItems();
				Banco banco = externoService.obtenerBancoPorId( pagoExtra.getIdBancoEmi() );
				for ( int i = 0; i < bancoCombo.getItems().length; i++ ) {
					if ( banco.getDescripcion().trim().equalsIgnoreCase( items[i].trim() ) ) {
						bancoCombo.select( i );
						break;
					}
				}
			}
			if ( StringUtils.isNotBlank( pagoExtra.getIdTerm() ) ) {
				String[] items = terminalCombo.getItems();
				Terminal terminal = externoService.obtenerTerminalPorId( pagoExtra.getIdTerm() );
				for ( int i = 0; i < terminalCombo.getItems().length; i++ ) {
					if ( terminal.getDescripcion().trim().equalsIgnoreCase( items[i].trim() ) ) {
						terminalCombo.select( i );
						break;
					}
				}
			}
			if ( pagoExtra.getMonto() != null ) {
				NumberFormat nf = NumberFormat.getInstance();
				nf.setGroupingUsed( false );
				monto.setText( nf.format( pagoExtra.getMonto() ) );
			}
		}
	}

	private void haCambiadoSeleccionFormaPago() {
		TipoPago tipoPago = (TipoPago) formaPago.getElementAt( formaPagoCombo.getSelectionIndex() );
		if ( tipoPago != null ) {
			etiquetaClaveP.setText( tipoPago.getF1() != null ? tipoPago.getF1() : "" );
			etiquetaClaveP.setVisible( true );
			etiquetaClaveP.redraw();

			etiquetaReferencia.setText( tipoPago.getF2() != null ? tipoPago.getF2() : "" );
			etiquetaReferencia.setVisible( true );
			etiquetaReferencia.redraw();

			etiquetaBanco.setText( tipoPago.getF3() != null ? tipoPago.getF3() : "" );
			etiquetaBanco.setVisible( true );
			etiquetaBanco.redraw();

			etiquetaTerminal.setText( tipoPago.getF4() != null ? tipoPago.getF4() : "" );
			etiquetaTerminal.setVisible( true );
			etiquetaTerminal.redraw();

			claveP.setEnabled( StringUtils.isNotBlank( tipoPago.getF1() ) );
			referenciaClave.setEnabled( StringUtils.isNotBlank( tipoPago.getF2() ) );
			bancoCombo.setEnabled( StringUtils.isNotBlank( tipoPago.getF3() ) );
			terminalCombo.setEnabled( StringUtils.isNotBlank( tipoPago.getF4() ) );
			monto.setEnabled( true );
		}
	}

}

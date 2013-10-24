package mx.com.lux.control.trabajos.view.container.reposicion;

import mx.com.lux.control.trabajos.bussiness.service.ReposicionService;
import mx.com.lux.control.trabajos.data.vo.DetalleReposicion;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.Reposicion;
import mx.com.lux.control.trabajos.data.vo.Sucursal;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.view.container.DialogoBase;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DetalleReposicionDialog extends DialogoBase {

	private final Logger log = LoggerFactory.getLogger( DetalleReposicionDialog.class );

	private final String rx;
	private final Integer numeroOrden;

	private final ReposicionService reposicionService;

	private Text textoRx;
	private Text textoArea;
	private Text textoIdResponsable;
	private Text textoResponsable;
	private Text textoCausa;
	private Text textoProblema;
	private Text textoDiagnostico;
	private Text textoSucursal;

	// Receta
	private Text textoOjoDerechoEsfera;
	private Text textoOjoDerechoCilindro;
	private Text textoOjoDerechoEje;
	private Text textoOjoDerechoAdc;
	private Text textoOjoDerechoAdi;
	private Text textoOjoDerechoAv;
	private Text textoOjoDerechoDi;
	private Text textoOjoDerechoPrisma;
	private Text textoOjoDerechoUbicacion;

	private Text textoOjoIzquierdoEsfera;
	private Text textoOjoIzquierdoCilindro;
	private Text textoOjoIzquierdoEje;
	private Text textoOjoIzquierdoAdc;
	private Text textoOjoIzquierdoAdi;
	private Text textoOjoIzquierdoAv;
	private Text textoOjoIzquierdoDi;
	private Text textoOjoIzquierdoPrisma;
	private Text textoOjoIzquierdoUbicacion;

	private Button checkboxOjoDerecho;
	private Button checkboxOjoIzquierdo;

	private Text textoDiLejos;
	private Text textoDiCerca;
	private Text textoAltOblea;
	private Text textoUsoAnteojos;
	private Text textoObservaciones;

	private Table tablaCambios;

	public DetalleReposicionDialog( final Shell parentShell, final String rx, final Integer numeroOrden ) {
		super( parentShell, "Detalle Reposicion", 14, 45, 5, 570 );
		this.rx = rx;
		this.numeroOrden = numeroOrden;
		reposicionService = ServiceLocator.getBean( ReposicionService.class );
	}

	@Override
	protected Control createContents( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite contenedorPrincipal = crearContenedorPrincipal( parent );
		crearCampos( contenedorPrincipal );
		configurarCampos();
		Composite contenedorReceta = crearContenedorReceta( contenedorPrincipal );
		crearCamposReceta( contenedorReceta );
		cargarDatos();
		Composite contenedorCambios = crearContenedorCambios( contenedorPrincipal );
		crearCamposCambios( contenedorCambios );
		anchoColumna = 45;
		anchoEntreColumnas = 3;
		Composite contenedorBotones = crearContenedorBotones( contenedorPrincipal );
		crearBotones( contenedorBotones );
		return parent;
	}

	private Composite crearContenedorPrincipal( final Composite parent ) {
		Composite contenedor = new Composite( parent, SWT.NONE );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout layout = new GridLayout( numeroColumnas, false );
		layout.marginWidth = 0;
		layout.marginHeight = 5;
		layout.horizontalSpacing = 0;
		contenedor.setLayout( layout );
		GridData gd = new GridData();
		gd.widthHint = getInitialSize().x;
		gd.heightHint = getInitialSize().y;
		gd.verticalIndent = 10;
		gd.horizontalIndent = 10;
		contenedor.setLayoutData( gd );
		return contenedor;
	}

	private Composite crearContenedorReceta( Composite parent ) {
		Composite contenedor = new Composite( parent, SWT.NONE );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout layout = new GridLayout( numeroColumnas, false );
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		contenedor.setLayout( layout );
		GridData gd = new GridData();
		gd.widthHint = ancho( numeroColumnas ) + 35;
		gd.heightHint = 195;
		gd.verticalIndent = 10;
		gd.horizontalIndent = 0;
		gd.horizontalSpan = numeroColumnas;
		contenedor.setLayoutData( gd );
		return contenedor;
	}

	private Composite crearContenedorCambios( Composite parent ) {
		Composite contenedor = new Composite( parent, SWT.NONE );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout layout = new GridLayout( numeroColumnas, false );
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		contenedor.setLayout( layout );
		GridData gd = new GridData();
		gd.widthHint = 740;
		gd.heightHint = 100;
		gd.verticalIndent = 0;
		gd.horizontalIndent = 0;
		gd.horizontalSpan = numeroColumnas;
		contenedor.setLayoutData( gd );
		return contenedor;
	}

	private void crearCampos( final Composite contenedor ) {
		crearElementoEtiqueta( contenedor, "Rx", 2 );
		textoRx = crearElementoTexto( contenedor, rx, 2, TipoElemento.SOLO_LECTURA );

		crearElementoVacio( contenedor, 1 );
		crearElementoEtiqueta( contenedor, " Sucursal", 2 );
		textoSucursal = crearElementoTexto( contenedor, "", 7, TipoElemento.SOLO_LECTURA );

		crearElementoEtiqueta( contenedor, "Area", 2 );
		textoArea = crearElementoTexto( contenedor, "", 3, TipoElemento.SOLO_LECTURA );
		crearElementoEtiqueta( contenedor, " Responsable", 2 );
		textoIdResponsable = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		textoResponsable = crearElementoTexto( contenedor, "", 6, TipoElemento.SOLO_LECTURA );

		crearElementoEtiqueta( contenedor, "Causa", 2 );
		textoCausa = crearElementoTexto( contenedor, "", 5, TipoElemento.SOLO_LECTURA );
		crearElementoVacio( contenedor, 6 );

		crearElementoEtiqueta( contenedor, "Problema", 2 );
		textoProblema = crearElementoTexto( contenedor, "", 12, TipoElemento.SOLO_LECTURA );
		crearElementoEtiqueta( contenedor, "Diagnóstico", 2 );
		textoDiagnostico = crearElementoTexto( contenedor, "", 12, TipoElemento.SOLO_LECTURA );
	}

	private void crearCamposReceta( final Composite contenedor ) {
		anchoEntreColumnas = 0;
		anchoColumna = 39;

		crearElementoEtiqueta( contenedor, "Receta", 2 );
		crearElementoEtiqueta( contenedor, "Uso", 1 );
		textoUsoAnteojos = crearElementoTexto( contenedor, "", 3, TipoElemento.SOLO_LECTURA );
		crearElementoVacio( contenedor, 8 );

		crearElementoVacio( contenedor, 1 );
		crearElementoEtiqueta( contenedor, "Esfera", 1, 8 );
		crearElementoEtiqueta( contenedor, "Cil", 1, 8 );
		crearElementoEtiqueta( contenedor, "Eje", 1, 8 );
		crearElementoEtiqueta( contenedor, "Ad.C.", 1, 8 );
		crearElementoEtiqueta( contenedor, "Ad.Int.", 1, 8 );
		crearElementoVacio( contenedor, 1 );
		crearElementoEtiqueta( contenedor, "AV", 1, 8 );
		crearElementoEtiqueta( contenedor, "D.M.", 1, 8 );
		crearElementoEtiqueta( contenedor, "Prisma", 1, 8 );
		crearElementoEtiqueta( contenedor, "Ubic.", 2, 8 );
		crearElementoEtiqueta( contenedor, "D.I. lejos", 1, 6 );
		textoDiLejos = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );

		checkboxOjoDerecho = crearElementoCheckbox( contenedor, "OD", 1 );
		checkboxOjoDerecho.setEnabled( false );
		textoOjoDerechoEsfera = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		textoOjoDerechoCilindro = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		textoOjoDerechoEje = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		textoOjoDerechoAdc = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		textoOjoDerechoAdi = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		crearElementoEtiqueta( contenedor, "20/", 1 );
		textoOjoDerechoAv = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		textoOjoDerechoDi = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		textoOjoDerechoPrisma = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		textoOjoDerechoUbicacion = crearElementoTexto( contenedor, "", 2, TipoElemento.SOLO_LECTURA );
		crearElementoEtiqueta( contenedor, "D.I. Cerca", 1, 6 );
		textoDiCerca = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );

		checkboxOjoIzquierdo = crearElementoCheckbox( contenedor, "OI", 1 );
		checkboxOjoIzquierdo.setEnabled( false );
		textoOjoIzquierdoEsfera = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		textoOjoIzquierdoCilindro = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		textoOjoIzquierdoEje = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		textoOjoIzquierdoAdc = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		textoOjoIzquierdoAdi = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		crearElementoEtiqueta( contenedor, "20/", 1 );
		textoOjoIzquierdoAv = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		textoOjoIzquierdoDi = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		textoOjoIzquierdoPrisma = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
		textoOjoIzquierdoUbicacion = crearElementoTexto( contenedor, "", 2, TipoElemento.SOLO_LECTURA );
		crearElementoEtiqueta( contenedor, "Alt. oblea", 1, 6 );
		textoAltOblea = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );

		crearElementoEtiqueta( contenedor, "Obs.", 1 );
		anchoEntreColumnas = 5;
		textoObservaciones = crearElementoTexto( contenedor, "", 13, TipoElemento.SOLO_LECTURA );
	}

	private void crearCamposCambios( Composite contenedor ) {
		crearElementoEtiqueta( contenedor, "Cambios", 2, 0 );
		tablaCambios = new Table( contenedor, SWT.BORDER | SWT.VIRTUAL );
		tablaCambios.setHeaderVisible( true );
		tablaCambios.setLinesVisible( true );
		GridData rd = new GridData();
		rd.widthHint = 615;
		rd.heightHint = 80;
		tablaCambios.setLayoutData( rd );

		TableColumn col1 = new TableColumn( tablaCambios, SWT.NONE );
		col1.setWidth( 150 );
		col1.setText( "Ojo" );

		TableColumn col2 = new TableColumn( tablaCambios, SWT.NONE );
		col2.setWidth( 150 );
		col2.setText( "Tipo" );

		TableColumn col3 = new TableColumn( tablaCambios, SWT.NONE );
		col3.setWidth( 150 );
		col3.setText( "Anterior" );

		TableColumn col4 = new TableColumn( tablaCambios, SWT.NONE );
		col4.setWidth( 150 );
		col4.setText( "Nuevo" );

		cargarTablaDetalleReposicion();

	}

	private void configurarCampos() {
		textoRx.setTextLimit( 8 );
		Sucursal sucursal = reposicionService.obtenerSucursalReposicion( textoRx.getText() );
		textoSucursal.setText( sucursal.getNombre() );

		textoIdResponsable.setTextLimit( 6 );
		textoIdResponsable.addModifyListener( new ModifyListener() {
			@Override
			public void modifyText( ModifyEvent modifyEvent ) {
				if ( StringUtils.isNotBlank( textoIdResponsable.getText() ) ) {
					Empleado empleado = reposicionService.buscarEmpleadoPorId( textoIdResponsable.getText() );
					if ( empleado != null ) {
						textoResponsable.setText( empleado.getNombreApellidos() );
					} else {
						textoResponsable.setText( "" );
					}
				}
			}
		} );
	}

	private void cargarDatos() {
		if ( StringUtils.isNotBlank( textoRx.getText() ) ) {
			Reposicion reposicion = reposicionService.obtenerReposicionPorRxYNumeroOrden( rx, numeroOrden );
			if ( reposicion != null ) {
				textoArea.setText( reposicion.getArea() );
				textoIdResponsable.setText( reposicion.getIdResponsable() );
				textoCausa.setText( reposicion.getCausa().getDescripcion() );
				textoProblema.setText( reposicion.getProblema() );
				textoDiagnostico.setText( reposicion.getDx() );

				if( reposicion.getOjo().compareTo( 'L' ) == 0 ) {
					checkboxOjoIzquierdo.setSelection( true );
				} else if( reposicion.getOjo().compareTo( 'R' ) == 0 ) {
					checkboxOjoDerecho.setSelection( true );
				} else {
					checkboxOjoIzquierdo.setSelection( true );
					checkboxOjoDerecho.setSelection( true );
				}

				switch ( reposicion.getUsoAnteojos() ) {
					case 'I':
					case 'i':
						textoUsoAnteojos.setText( "INTERMEDIO" );
						break;
					case 'C':
					case 'c':
						textoUsoAnteojos.setText( "CERCA" );
						break;
					case 'L':
					case 'l':
						textoUsoAnteojos.setText( "LEJOS" );
						break;
					case 'B':
					case 'b':
						textoUsoAnteojos.setText( "BIFOCAL" );
						break;
					case 'P':
					case 'p':
						textoUsoAnteojos.setText( "PROGRESIVO" );
						break;
					case 'T':
					case 't':
						textoUsoAnteojos.setText( "BIFOCAL INTERMEDIO" );
						break;
				}

				textoDiLejos.setText( reposicion.getDiLejos() );
				textoOjoDerechoEsfera.setText( reposicion.getOdEsf() );
				textoOjoDerechoCilindro.setText( reposicion.getOdCil() );
				textoOjoDerechoEje.setText( reposicion.getOdEje() );
				textoOjoDerechoAdc.setText( reposicion.getOdAdc() );
				textoOjoDerechoAdi.setText( reposicion.getOdAdi() );
				textoOjoDerechoAv.setText( reposicion.getOdAv() );
				textoOjoDerechoDi.setText( reposicion.getOdDi() );
				textoOjoDerechoPrisma.setText( reposicion.getOdPrisma() );
				textoOjoDerechoUbicacion.setText( reposicion.getOdPrismaV() != null ? reposicion.getOdPrismaV() : "" );

				textoDiCerca.setText( reposicion.getDiCerca() );
				textoOjoIzquierdoEsfera.setText( reposicion.getOiEsf() );
				textoOjoIzquierdoCilindro.setText( reposicion.getOiCil() );
				textoOjoIzquierdoEje.setText( reposicion.getOiEje() );
				textoOjoIzquierdoAdc.setText( reposicion.getOiAdc() );
				textoOjoIzquierdoAdi.setText( reposicion.getOiAdi() );
				textoOjoIzquierdoAv.setText( reposicion.getOiAv() );
				textoOjoIzquierdoDi.setText( reposicion.getOiDi() );
				textoOjoIzquierdoPrisma.setText( reposicion.getOiPrisma() );
				textoOjoIzquierdoUbicacion.setText( reposicion.getOiPrismaV() != null ? reposicion.getOiPrismaV() : "" );

				textoAltOblea.setText( reposicion.getAltObl() );
				textoObservaciones.setText( reposicion.getObservaciones() );
			}
		}
	}

	private void cargarTablaDetalleReposicion() {
		tablaCambios.clearAll();
		tablaCambios.removeAll();
		if ( StringUtils.isNotBlank( textoRx.getText() ) ) {
			for ( DetalleReposicion detalleReposicion : reposicionService.buscarDetalleReposicionAsociadasAReposicion( textoRx.getText(), numeroOrden ) ) {
				TableItem item = new TableItem( tablaCambios, SWT.NONE );
				item.setText( 0, StringUtils.defaultIfBlank( detalleReposicion.getOjo(), "" ) );
				item.setText( 1, StringUtils.defaultIfBlank( detalleReposicion.getTipo(), "" ) );
				item.setText( 2, StringUtils.defaultIfBlank( detalleReposicion.getValorAnterior(), "" ) );
				item.setText( 3, StringUtils.defaultIfBlank( detalleReposicion.getValorNuevo(), "" ) );
			}
		}
	}

	private Composite crearContenedorBotones( Composite parent ) {
		Composite contenedor = new Composite( parent, SWT.NONE );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout layout = new GridLayout( numeroColumnas, false );
		layout.marginWidth = 10;
		layout.marginHeight = 5;
		contenedor.setLayout( layout );
		GridData gd = new GridData();
		gd.widthHint = 740;
		gd.heightHint = 50;
		gd.verticalIndent = 0;
		gd.horizontalIndent = 0;
		gd.horizontalSpan = numeroColumnas;
		contenedor.setLayoutData( gd );
		return contenedor;
	}

	private void crearBotones( Composite contenedor ) {
		crearElementoVacio( contenedor, 12 );
		Button botonCerrar = crearElementoBoton( contenedor, "Cerrar", 2, ControlTrabajosWindowElements.closeIcon );
		botonCerrar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				shell.close();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );

	}

}

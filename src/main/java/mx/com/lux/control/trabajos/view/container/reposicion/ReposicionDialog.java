package mx.com.lux.control.trabajos.view.container.reposicion;

import mx.com.lux.control.trabajos.bussiness.service.ExternoService;
import mx.com.lux.control.trabajos.bussiness.service.GarantiaService;
import mx.com.lux.control.trabajos.bussiness.service.ReposicionService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.util.constants.TipoTrabajo;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.DialogoBase;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.render.CausaReposicionRender;
import mx.com.lux.control.trabajos.view.render.PrismaRender;
import mx.com.lux.control.trabajos.view.render.ResponsableReposicionRender;
import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.graphics.Color;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Date;

public class ReposicionDialog extends DialogoBase {

    private final Logger log = LoggerFactory.getLogger( ReposicionDialog.class );

    private final String rx;

    private final TrabajoService trabajoService;
    private final ReposicionService reposicionService;
    private final ExternoService externoService;
    private final GarantiaService garantiaService;

    private Text textoRx;
    private ComboViewer comboViewerArea;
    private Text textoIdResponsable;
    private Text textoResponsable;
    private ComboViewer comboViewerCausa;
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
    private ComboViewer comboViewerOjoDerechoUbicacion;

    private Text textoOjoIzquierdoEsfera;
    private Text textoOjoIzquierdoCilindro;
    private Text textoOjoIzquierdoEje;
    private Text textoOjoIzquierdoAdc;
    private Text textoOjoIzquierdoAdi;
    private Text textoOjoIzquierdoAv;
    private Text textoOjoIzquierdoDi;
    private Text textoOjoIzquierdoPrisma;
    private ComboViewer comboViewerOjoIzquierdoUbicacion;

    private Button checkboxOjoDerecho;
    private Button checkboxOjoIzquierdo;

    private Text textoDiLejos;
    private Text textoDiCerca;
    private Text textoAltOblea;
    private Text textoUsoAnteojos;
    private Text textoObservaciones;

    private Button botonForma;
    private Button botonModificar;
    private Button botonRestablecer;

    private Table tablaCambios;

    private final double VALOR_MULTIPLO = 0.25;

    public ReposicionDialog( final Shell parentShell, final String rx ) {
        super( parentShell, "Reposicion", 14, 45, 5, 570 );
        this.rx = rx;
        trabajoService = ServiceLocator.getBean( TrabajoService.class );
        reposicionService = ServiceLocator.getBean( ReposicionService.class );
        externoService = ServiceLocator.getBean( ExternoService.class );
        garantiaService = ServiceLocator.getBean( GarantiaService.class );
    }

    @Override
    protected Control createContents( Composite parent ) {
        parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
        Composite contenedorPrincipal = crearContenedorPrincipal( parent );
        crearCampos( contenedorPrincipal );
        configurarCampos();
        Composite contenedorReceta = crearContenedorReceta( contenedorPrincipal );
        crearCamposReceta( contenedorReceta );
        cargarDatosReceta();
        configurarCamposReceta();
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

    private void crearCampos( final Composite contenedor ) {
        crearElementoEtiqueta( contenedor, "Rx", 2 );
        if ( StringUtils.isNotBlank( rx ) ) {
            textoRx = crearElementoTexto( contenedor, rx, 2, TipoElemento.SOLO_LECTURA );
        } else {
            textoRx = crearElementoTexto( contenedor, "", 2, TipoElemento.NORMAL );
        }
        crearElementoVacio( contenedor, 1 );
        crearElementoEtiqueta( contenedor, " Sucursal", 2 );
        textoSucursal = crearElementoTexto( contenedor, "", 7, TipoElemento.SOLO_LECTURA );

        crearElementoEtiqueta( contenedor, "Area", 2 );
        comboViewerArea = crearElementoCombo( contenedor, new ResponsableReposicionRender(), reposicionService.obtenerTodosResponsableReposicion(), 3, TipoElemento.NORMAL );
        crearElementoEtiqueta( contenedor, " Responsable", 2 );
        textoIdResponsable = crearElementoTexto( contenedor, "", 1, TipoElemento.NORMAL );
        textoResponsable = crearElementoTexto( contenedor, "", 6, TipoElemento.SOLO_LECTURA );

        crearElementoEtiqueta( contenedor, "Causa", 2 );
        comboViewerCausa = crearElementoCombo( contenedor, new CausaReposicionRender(), reposicionService.obtenerTodosCausaReposicion(), 5, TipoElemento.NORMAL );
        crearElementoVacio( contenedor, 6 );

        crearElementoEtiqueta( contenedor, "Problema", 2 );
        textoProblema = crearElementoTexto( contenedor, "", 12, TipoElemento.NORMAL );
        crearElementoEtiqueta( contenedor, "Diagnóstico", 2 );
        textoDiagnostico = crearElementoTexto( contenedor, "", 12, TipoElemento.NORMAL );
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
        textoOjoDerechoEsfera = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        textoOjoDerechoEsfera.addFocusListener( new FocusListener() {
            @Override
            public void focusGained( FocusEvent focusEvent ) {

            }

            @Override
            public void focusLost( FocusEvent focusEvent ) {
                if ( textoOjoDerechoEsfera.getEditable() ) {
                    if ( textoOjoDerechoEsfera.getText().trim().length() > 0 || textoOjoDerechoEsfera.getText() == "0" ) {
                        String esfera = textoOjoDerechoEsfera.getText().trim();
                        Double multiplo = 0.0;
                        if ( esfera.length() > 0 ) {
                            double number = Double.parseDouble( esfera );
                            multiplo = number % VALOR_MULTIPLO;
                        }
                        if ( multiplo == 0 ) {
                            textoOjoDerechoEsfera.setText( "" );
                            esfera = esfera.replace( "+", "" );
                            esfera = esfera.replace( "-", "" );
                            if( esfera.length() == 1 ){
                                esfera = esfera+".00";
                            } else if( esfera.length() == 2 ){
                                esfera = esfera+"00";
                            } else if( esfera.length() == 3 ){
                                esfera = esfera+"0";
                            }
                            textoOjoDerechoEsfera.setText( "+"+esfera );
                        } else {
                            textoOjoDerechoEsfera.setText( "" );
                       }
                    } else {
                        textoOjoDerechoEsfera.setText( "" );
                    }
                }
            }
        } );

        textoOjoDerechoCilindro = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        textoOjoDerechoCilindro.addFocusListener( new FocusListener() {
            @Override
            public void focusGained( FocusEvent focusEvent ) {
            }

            @Override
            public void focusLost( FocusEvent focusEvent ) {
                if ( textoOjoDerechoCilindro.getEditable() ) {
                    if ( textoOjoDerechoCilindro.getText().trim().length() > 0 || textoOjoDerechoCilindro.getText() == "0" ) {
                        String esfera = textoOjoDerechoCilindro.getText().trim();
                        Double multiplo = 0.0;
                        if ( esfera.length() > 0 ) {
                            double number = Double.parseDouble( esfera );
                            multiplo = number % VALOR_MULTIPLO;
                        }
                        if ( multiplo == 0 ) {
                            textoOjoDerechoCilindro.setText( "" );
                            esfera = esfera.replace( "-", "" );
                            esfera = esfera.replace( "+", "" );
                            if( esfera.length() == 1 ){
                                esfera = esfera+".00";
                            } else if( esfera.length() == 2 ){
                                esfera = esfera+"00";
                            }else if( esfera.length() == 3 ){
                                esfera = esfera+"0";
                            }
                            textoOjoDerechoCilindro.setText( "-"+esfera );
                        } else {
                            textoOjoDerechoCilindro.setText( "" );
                        }
                    } else {
                        textoOjoDerechoCilindro.setText( "" );
                    }
                }
            }
        } );


        textoOjoDerechoEje = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        textoOjoDerechoAdc = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        textoOjoDerechoAdi = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        crearElementoEtiqueta( contenedor, "20/", 1 );
        textoOjoDerechoAv = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        textoOjoDerechoDi = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        textoOjoDerechoPrisma = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        comboViewerOjoDerechoUbicacion = crearElementoCombo( contenedor, new PrismaRender(), reposicionService.obtenerTodosPrisma(), 2, TipoElemento.DESACTIVADO );
        crearElementoEtiqueta( contenedor, "D.I. Cerca", 1, 6 );
        textoDiCerca = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );

        checkboxOjoIzquierdo = crearElementoCheckbox( contenedor, "OI", 1 );
        textoOjoIzquierdoEsfera = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        textoOjoIzquierdoEsfera.addFocusListener( new FocusListener() {
            @Override
            public void focusGained( FocusEvent focusEvent ) {
            }

            @Override
            public void focusLost( FocusEvent focusEvent ) {
                if ( textoOjoIzquierdoEsfera.getEditable() ) {
                    if ( textoOjoIzquierdoEsfera.getText().trim().length() > 0 || textoOjoIzquierdoEsfera.getText() == "0" ) {
                        String esfera = textoOjoIzquierdoEsfera.getText().trim();
                        Double multiplo = 0.0;
                        if ( esfera.length() > 0 ) {
                            double number = Double.parseDouble( esfera );
                            multiplo = number % VALOR_MULTIPLO;
                        }
                        if ( multiplo == 0 ) {
                            textoOjoIzquierdoEsfera.setText( "" );
                            esfera = esfera.replace( "+", "" );
                            esfera = esfera.replace( "-", "" );
                            if( esfera.length() == 1 ){
                                esfera = esfera+".00";
                            } else if( esfera.length() == 2 ){
                                esfera = esfera+"00";
                            }else if( esfera.length() == 3 ){
                                esfera = esfera+"0";
                            }
                            textoOjoIzquierdoEsfera.setText( "+"+esfera );
                        } else {
                            textoOjoIzquierdoEsfera.setText( "" );
                        }
                    } else {
                        textoOjoIzquierdoEsfera.setText( "" );
                    }
                }
            }
        } );

        textoOjoIzquierdoCilindro = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        textoOjoIzquierdoCilindro.addFocusListener( new FocusListener() {
            @Override
            public void focusGained( FocusEvent focusEvent ) {
            }

            @Override
            public void focusLost( FocusEvent focusEvent ) {
                if ( textoOjoIzquierdoCilindro.getEditable() ) {
                    if ( textoOjoIzquierdoCilindro.getText().trim().length() > 0 || textoOjoIzquierdoCilindro.getText() == "0" ) {
                        String esfera = textoOjoIzquierdoCilindro.getText().trim();
                        Double multiplo = 0.0;
                        if ( esfera.length() > 0 ) {
                            double number = Double.parseDouble( esfera );
                            multiplo = number % VALOR_MULTIPLO;
                        }
                        if ( multiplo == 0 ) {
                            textoOjoIzquierdoCilindro.setText( "" );
                            esfera = esfera.replace( "-", "" );
                            esfera = esfera.replace( "+", "" );
                            if( esfera.length() == 1 ){
                                esfera = esfera+".00";
                            } else if( esfera.length() == 2 ){
                                esfera = esfera+"00";
                            }else if( esfera.length() == 3 ){
                                esfera = esfera+"0";
                            }
                            textoOjoIzquierdoCilindro.setText( "-"+esfera );
                        } else {
                            textoOjoIzquierdoCilindro.setText( "" );
                        }
                    } else {
                        textoOjoIzquierdoCilindro.setText( "" );
                    }
                }
            }
        } );


        textoOjoIzquierdoEje = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        textoOjoIzquierdoAdc = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        textoOjoIzquierdoAdi = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        crearElementoEtiqueta( contenedor, "20/", 1 );
        textoOjoIzquierdoAv = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        textoOjoIzquierdoDi = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        textoOjoIzquierdoPrisma = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );
        comboViewerOjoIzquierdoUbicacion = crearElementoCombo( contenedor, new PrismaRender(), reposicionService.obtenerTodosPrisma(), 2, TipoElemento.DESACTIVADO );
        crearElementoEtiqueta( contenedor, "Alt. oblea", 1, 6 );
        textoAltOblea = crearElementoTexto( contenedor, "", 1, TipoElemento.SOLO_LECTURA );

        crearElementoEtiqueta( contenedor, "Obs.", 1 );
        anchoEntreColumnas = 5;
        textoObservaciones = crearElementoTexto( contenedor, "", 13, TipoElemento.SOLO_LECTURA );

        crearElementoVacio( contenedor, 2 );
        botonForma = crearElementoBoton( contenedor, "Forma", 3, ControlTrabajosWindowElements.iconoAgregar );
        botonForma.addSelectionListener( new SelectionListener() {
            @Override
            public void widgetSelected( SelectionEvent event ) {
                MaterialAcabadoReposicionDialog dialogo = new MaterialAcabadoReposicionDialog( shell, textoRx.getText() );
                dialogo.open();
            }

            @Override
            public void widgetDefaultSelected( SelectionEvent event ) {

            }
        } );
        crearElementoVacio( contenedor, 3 );
        botonModificar = crearElementoBoton( contenedor, "Modificar", 3, ControlTrabajosWindowElements.iconoEditar );
        botonModificar.setEnabled( false );
        botonModificar.addSelectionListener( new SelectionListener() {
            @Override
            public void widgetSelected( SelectionEvent event ) {
                habilitarCamposReceta();
            }

            @Override
            public void widgetDefaultSelected( SelectionEvent event ) {
            }
        } );
        botonRestablecer = crearElementoBoton( contenedor, "Restablecer", 3, ControlTrabajosWindowElements.iconoCancelar );
        botonRestablecer.setEnabled( false );
        botonRestablecer.addSelectionListener( new SelectionListener() {
            @Override
            public void widgetSelected( SelectionEvent event ) {
                deshabilitarCamposReceta();
                cargarDatosReceta();
            }

            @Override
            public void widgetDefaultSelected( SelectionEvent event ) {
            }
        } );
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

    private void crearBotones( Composite contenedor ) {
        crearElementoVacio( contenedor, 10 );
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

        Button botonGuardar = crearElementoBoton( contenedor, "Guardar", 2, ControlTrabajosWindowElements.saveIcon );
        botonGuardar.addSelectionListener( new SelectionListener() {
            @Override
            public void widgetSelected( SelectionEvent event ) {
                if ( datosValidos() ) {
                    try {
                        if( ( textoOjoDerechoCilindro.getText().trim().length() > 0 && textoOjoDerechoEje.getText().trim().length() <= 0 )
                                || ( textoOjoIzquierdoCilindro.getText().trim().length() > 0 && textoOjoIzquierdoEje.getText().trim().length() <= 0 ) ){

                            textoOjoDerechoEje.setBackground( GraphicConstants.FIELD_COLOR_ALERT );
                            textoOjoIzquierdoEje.setBackground( GraphicConstants.FIELD_COLOR_ALERT );
                        } else {
                            procesarReposicion();
                            //MessageDialog.openError( shell, "Error", "El eje debe ser mayor a cero" );
                        }
                    } catch ( Exception e ) {
                        log.error( "Error al procesar la reposicion: " + e.getMessage() );
                        MessageDialog.openError( shell, "Error", "Error al procesar la reposicion" );
                    }
                }
            }

            @Override
            public void widgetDefaultSelected( SelectionEvent event ) {
            }
        } );
    }

    private void configurarCampos() {
        textoRx.setTextLimit( 8 );
        if ( !esRxCorrecto( textoRx.getText() ) ) {
            textoRx.setBackground( GraphicConstants.FIELD_COLOR_INVALID );
        }
        textoRx.addModifyListener( new ModifyListener() {
            @Override
            public void modifyText( ModifyEvent modifyEvent ) {
                if ( StringUtils.isNotBlank( textoRx.getText() ) ) {
                    if ( esRxCorrecto( textoRx.getText() ) ) {
                        textoRx.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
                        textoRx.setToolTipText( "Numero de Factura correcto" );
                        Sucursal sucursal = reposicionService.obtenerSucursalReposicion( textoRx.getText() );
                        textoSucursal.setText( sucursal.getNombre() );
                        cargarDatosReceta();
                        botonModificar.setEnabled( true );
                        botonRestablecer.setEnabled( true );
                    } else {
                        textoRx.setBackground( GraphicConstants.FIELD_COLOR_INVALID );
                        textoRx.setToolTipText( "Numero de Factura incorrecto" );
                        botonModificar.setEnabled( false );
                        botonRestablecer.setEnabled( false );
                        limpiarDatosReceta();
                    }
                    cargarTablaDetalleReposicion();
                }
            }
        } );

        if ( StringUtils.isNotBlank( textoRx.getText() ) ) {
            Jb trabajo = trabajoService.findById( textoRx.getText() );
            if ( trabajo != null ) {
                textoRx.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
                textoRx.setToolTipText( "Numero de Factura correcto" );
                Sucursal sucursal = reposicionService.obtenerSucursalReposicion( textoRx.getText() );
                textoSucursal.setText( sucursal.getNombre() );
            } else {
                textoRx.setBackground( GraphicConstants.FIELD_COLOR_INVALID );
                textoRx.setToolTipText( "Numero de Factura incorrecto" );
            }
        } else {
            Empleado empleado = ( Empleado ) Session.getAttribute( Constants.PARAM_USER_LOGGED );
            textoSucursal.setText( empleado.getSucursal().getNombre() );
        }

        comboViewerArea.addSelectionChangedListener( new ISelectionChangedListener() {
            @Override
            public void selectionChanged( SelectionChangedEvent selectionChangedEvent ) {
                String area = comboViewerArea.getCombo().getText();
                if ( StringUtils.isNotBlank( area ) && "SUC".equalsIgnoreCase( area ) ) {
                    textoIdResponsable.setEnabled( true );
                    textoResponsable.setEnabled( true );
                    textoIdResponsable.setBackground( GraphicConstants.FIELD_COLOR_INVALID );
                } else {
                    textoIdResponsable.setEnabled( false );
                    textoResponsable.setEnabled( false );
                    textoIdResponsable.setText( "" );
                    textoResponsable.setText( "" );
                    textoIdResponsable.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
                }
            }
        } );

        textoIdResponsable.setEnabled( false );
        textoResponsable.setEnabled( false );
        textoIdResponsable.setTextLimit( 6 );
        textoIdResponsable.addModifyListener( new ModifyListener() {
            @Override
            public void modifyText( ModifyEvent modifyEvent ) {
                if ( StringUtils.isNotBlank( textoIdResponsable.getText() ) ) {
                    Empleado empleado = reposicionService.buscarEmpleadoPorId( textoIdResponsable.getText() );
                    if ( empleado != null ) {
                        textoResponsable.setText( empleado.getNombreApellidos() );
                        textoIdResponsable.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
                    } else {
                        textoResponsable.setText( "" );
                        textoIdResponsable.setBackground( GraphicConstants.FIELD_COLOR_INVALID );
                    }
                }
            }
        } );
    }

    private void configurarCamposReceta() {
        Session.removeAttribute( "MATERIAL_ACABADO" );
        if ( esRxCorrecto( textoRx.getText() ) ) {
            // Cargamos datos de la receta
            Receta receta = reposicionService.obtenerRecetaReposicion( textoRx.getText() );
            if ( receta != null ) {
                botonModificar.setEnabled( true );
                botonRestablecer.setEnabled( true );
                cargarDatosReceta();
            } else {
                botonModificar.setEnabled( false );
                botonRestablecer.setEnabled( false );
            }
            // Scamos los datos de forma, material, acabado
            Jb trabajo = trabajoService.findById( textoRx.getText() );
            String factura = trabajo.getRx().replace( "X", "" );
            JbGarantia garantia = garantiaService.obtenerGarantiaPorFactura( factura );
            Empleado empleado = ( Empleado ) Session.getAttribute( Constants.PARAM_USER_LOGGED );
            Sucursal sucursal = empleado.getSucursal();
            if ( trabajo.esLaboratorio() || ( trabajo.esGarantia() && sucursal.getIdSucursal() == Integer.parseInt( garantia.getSucursal().trim() ) ) ) {
                NotaVenta notaVenta = trabajoService.findNotaVentaByFactura( textoRx.getText() );
                if ( notaVenta != null ) {
                    Session.setAttribute( "MATERIAL_ACABADO", new MaterialAcabado( notaVenta.getUdf2(), "" ) );
                }
            } else if ( trabajo.esExterno() ) {
                JbExterno externo = externoService.obtenerExternoPorRx( textoRx.getText() );
                if ( externo != null ) {
                    Session.setAttribute( "MATERIAL_ACABADO", new MaterialAcabado( externo.getMaterial(), externo.getForma() ) );
                }
            } else {
                Session.removeAttribute( "MATERIAL_ACABADO" );
            }
        }
    }

    private void cargarDatosReceta() {
        if ( StringUtils.isNotBlank( textoRx.getText() ) ) {
            Receta receta = reposicionService.obtenerRecetaReposicion( textoRx.getText() );
            if ( receta != null ) {
                switch ( receta.getsUsoAnteojos() ) {
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

                textoDiLejos.setText( receta.getDiLejosR() );
                textoOjoDerechoEsfera.setText( receta.getOdEsfR() );
                textoOjoDerechoCilindro.setText( receta.getOdCilR() );
                textoOjoDerechoEje.setText( receta.getOdEjeR() );
                textoOjoDerechoAdc.setText( receta.getOdAdcR() );
                textoOjoDerechoAdc.addFocusListener( new FocusListener() {
                    @Override
                    public void focusGained( FocusEvent focusEvent ) {
                    }

                    @Override
                    public void focusLost( FocusEvent focusEvent ) {
                        if ( textoOjoDerechoAdc.getEditable() ) {
                            if ( textoOjoDerechoAdc.getText().trim().length() > 0 || textoOjoDerechoAdc.getText() == "0" ) {
                                String esfera = textoOjoDerechoAdc.getText().trim();
                                Double multiplo = 0.0;
                                if ( esfera.length() > 0 ) {
                                    double number = Double.parseDouble( esfera );
                                    multiplo = number % VALOR_MULTIPLO;
                                }
                                if ( multiplo == 0 ) {
                                    textoOjoDerechoAdc.setText( "" );
                                    esfera = esfera.replace( "+", "" );
                                    esfera = esfera.replace( "-", "" );
                                    if( esfera.length() == 1 ){
                                        esfera = esfera+".00";
                                    } else if( esfera.length() == 2 ){
                                        esfera = esfera+"00";
                                    } else if( esfera.length() == 3 ){
                                            esfera = esfera+"0";
                                        }
                                    textoOjoDerechoAdc.setText( "+"+esfera );
                                } else {
                                    textoOjoDerechoAdc.setText( "" );
                                }
                            } else {
                                textoOjoDerechoAdc.setText( "" );
                            }
                        }
                    }
                } );


                textoOjoDerechoAdi.setText( receta.getOdAdiR() );
                textoOjoDerechoAdi.addFocusListener( new FocusListener() {
                    @Override
                    public void focusGained( FocusEvent focusEvent ) {
                    }

                    @Override
                    public void focusLost( FocusEvent focusEvent ) {
                        if ( textoOjoDerechoAdi.getEditable() ) {
                            if ( textoOjoDerechoAdi.getText().trim().length() > 0 || textoOjoDerechoAdi.getText() == "0" ) {
                                String esfera = textoOjoDerechoAdi.getText().trim();
                                Double multiplo = 0.0;
                                if ( esfera.length() > 0 ) {
                                    double number = Double.parseDouble( esfera );
                                    multiplo = number % VALOR_MULTIPLO;
                                }
                                if ( multiplo == 0 ) {
                                    textoOjoDerechoAdi.setText( "" );
                                    esfera = esfera.replace( "+", "" );
                                    esfera = esfera.replace( "-", "" );
                                    if( esfera.length() == 1 ){
                                        esfera = esfera+".00";
                                    } else if( esfera.length() == 2 ){
                                        esfera = esfera+"00";
                                    } else if( esfera.length() == 3 ){
                                        esfera = esfera+"0";
                                    }
                                    textoOjoDerechoAdi.setText( "+"+esfera );
                                } else {
                                    textoOjoDerechoAdi.setText( "" );
                                }
                            } else {
                                textoOjoDerechoAdi.setText( "" );
                            }
                        }
                    }
                } );


                textoOjoDerechoAv.setText( receta.getOdAvR() );
                textoOjoDerechoDi.setText( receta.getDiOd() );
                textoOjoDerechoPrisma.setText( receta.getOdPrismaH() );
                if ( StringUtils.isNotBlank( receta.getOdPrismaV() ) ) {
                    String[] items = comboViewerOjoDerechoUbicacion.getCombo().getItems();
                    for ( int i = 0; i < items.length; i++ ) {
                        if ( items[ i ].trim().equalsIgnoreCase( receta.getOdPrismaV().trim() ) ) {
                            comboViewerOjoDerechoUbicacion.getCombo().select( i );
                            break;
                        }
                    }
                }
                textoDiCerca.setText( receta.getDiCercaR() );
                textoOjoIzquierdoEsfera.setText( receta.getOiEsfR() );
                textoOjoIzquierdoCilindro.setText( receta.getOiCilR() );
                textoOjoIzquierdoEje.setText( receta.getOiEjeR() );
                textoOjoIzquierdoAdc.setText( receta.getOiAdcR() );
                textoOjoIzquierdoAdc.addFocusListener( new FocusListener() {
                    @Override
                    public void focusGained( FocusEvent focusEvent ) {
                    }

                    @Override
                    public void focusLost( FocusEvent focusEvent ) {
                        if ( textoOjoIzquierdoAdc.getEditable() ) {
                            if ( textoOjoIzquierdoAdc.getText().trim().length() > 0 || textoOjoIzquierdoAdc.getText() == "0" ) {
                                String esfera = textoOjoIzquierdoAdc.getText().trim();
                                Double multiplo = 0.0;
                                if ( esfera.length() > 0 ) {
                                    double number = Double.parseDouble( esfera );
                                    multiplo = number % VALOR_MULTIPLO;
                                }
                                if ( multiplo == 0 ) {
                                    textoOjoIzquierdoAdc.setText( "" );
                                    esfera = esfera.replace( "+", "" );
                                    esfera = esfera.replace( "-", "" );
                                    if( esfera.length() == 1 ){
                                        esfera = esfera+".00";
                                    } else if( esfera.length() == 2 ){
                                        esfera = esfera+"00";
                                    } else if( esfera.length() == 3 ){
                                        esfera = esfera+"0";
                                    }
                                    textoOjoIzquierdoAdc.setText( "+"+esfera );
                                } else {
                                    textoOjoIzquierdoAdc.setText( "" );
                                }
                            } else {
                                textoOjoIzquierdoAdc.setText( "" );
                            }
                        }
                    }
                } );


                textoOjoIzquierdoAdi.setText( receta.getOiAdiR() );
                textoOjoIzquierdoAdi.addFocusListener( new FocusListener() {

                    @Override
                    public void focusGained( FocusEvent focusEvent ) {
                    }

                    @Override
                    public void focusLost( FocusEvent focusEvent ) {
                        if ( textoOjoIzquierdoAdi.getEditable() ) {
                            if ( textoOjoIzquierdoAdi.getText().trim().length() > 0 || textoOjoIzquierdoAdi.getText() == "0" ) {
                                String esfera = textoOjoIzquierdoAdi.getText().trim();
                                Double multiplo = 0.0;
                                if ( esfera.length() > 0 ) {
                                    double number = Double.parseDouble( esfera );
                                    multiplo = number % VALOR_MULTIPLO;
                                }
                                if ( multiplo == 0 ) {
                                    textoOjoIzquierdoAdi.setText( "" );
                                    esfera = esfera.replace( "+", "" );
                                    esfera = esfera.replace( "-", "" );
                                    if( esfera.length() == 1 ){
                                        esfera = esfera+".00";
                                    } else if( esfera.length() == 2 ){
                                        esfera = esfera+"00";
                                    } else if( esfera.length() == 3 ){
                                        esfera = esfera+"0";
                                    }
                                    textoOjoIzquierdoAdi.setText( "+"+esfera );
                                } else {
                                    textoOjoIzquierdoAdi.setText( "" );
                                }
                            } else {
                                textoOjoIzquierdoAdi.setText( "" );
                            }
                        }
                    }
                } );


                textoOjoIzquierdoAv.setText( receta.getOiAvR() );
                textoOjoIzquierdoDi.setText( receta.getDiOi() );
                textoOjoIzquierdoPrisma.setText( receta.getOiPrismaH() );
                if ( StringUtils.isNotBlank( receta.getOiPrismaV() ) ) {
                    String[] items = comboViewerOjoIzquierdoUbicacion.getCombo().getItems();
                    for ( int i = 0; i < items.length; i++ ) {
                        if ( items[ i ].trim().equalsIgnoreCase( receta.getOdPrismaV().trim() ) ) {
                            comboViewerOjoIzquierdoUbicacion.getCombo().select( i );
                            break;
                        }
                    }
                }
                textoAltOblea.setText( receta.getAltOblR() );
                textoObservaciones.setText( receta.getObservacionesR() );
            } else {
                habilitarCamposReceta();
            }
        }
    }

    private void limpiarDatosReceta() {
        textoUsoAnteojos.setText( "" );
        textoDiLejos.setText( "" );
        textoOjoDerechoEsfera.setText( "" );
        textoOjoDerechoCilindro.setText( "" );
        textoOjoDerechoEje.setText( "" );
        textoOjoDerechoAdc.setText( "" );
        textoOjoDerechoAdi.setText( "" );
        textoOjoDerechoAv.setText( "" );
        textoOjoDerechoDi.setText( "" );
        textoOjoDerechoPrisma.setText( "" );
        comboViewerOjoDerechoUbicacion.getCombo().setEnabled( false );
        textoDiCerca.setText( "" );
        textoOjoIzquierdoEsfera.setText( "" );
        textoOjoIzquierdoCilindro.setText( "" );
        textoOjoIzquierdoEje.setText( "" );
        textoOjoIzquierdoAdc.setText( "" );
        textoOjoIzquierdoAdi.setText( "" );
        textoOjoIzquierdoAv.setText( "" );
        textoOjoIzquierdoDi.setText( "" );
        textoOjoIzquierdoPrisma.setText( "" );
        comboViewerOjoIzquierdoUbicacion.getCombo().setEnabled( false );
        textoAltOblea.setText( "" );
        textoObservaciones.setText( "" );
    }

    private void habilitarCamposReceta() {
        textoDiLejos.setEditable( true );
        textoOjoDerechoEsfera.setEditable( true );
        textoOjoDerechoCilindro.setEditable( true );
        textoOjoDerechoEje.setEditable( true );
        textoOjoDerechoAdc.setEditable( true );
        textoOjoDerechoAdi.setEditable( true );
        textoOjoDerechoAv.setEditable( true );
        textoOjoDerechoDi.setEditable( true );
        textoOjoDerechoPrisma.setEditable( true );
        comboViewerOjoDerechoUbicacion.getCombo().setEnabled( true );
        textoDiCerca.setEditable( true );
        textoOjoIzquierdoEsfera.setEditable( true );
        textoOjoIzquierdoCilindro.setEditable( true );
        textoOjoIzquierdoEje.setEditable( true );
        textoOjoIzquierdoAdc.setEditable( true );
        textoOjoIzquierdoAdi.setEditable( true );
        textoOjoIzquierdoAv.setEditable( true );
        textoOjoIzquierdoDi.setEditable( true );
        textoOjoIzquierdoPrisma.setEditable( true );
        comboViewerOjoIzquierdoUbicacion.getCombo().setEnabled( true );
        textoAltOblea.setEditable( true );
        textoObservaciones.setEditable( true );

        textoDiLejos.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoDerechoEsfera.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoDerechoCilindro.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoDerechoEje.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoDerechoAdc.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoDerechoAdi.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoDerechoAv.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoDerechoDi.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoDerechoPrisma.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoDiCerca.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoIzquierdoEsfera.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoIzquierdoCilindro.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoIzquierdoEje.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoIzquierdoAdc.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoIzquierdoAdi.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoIzquierdoAv.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoIzquierdoDi.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoOjoIzquierdoPrisma.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoAltOblea.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
        textoObservaciones.setBackground( GraphicConstants.FIELD_COLOR_NORMAL );
    }

    private void deshabilitarCamposReceta() {
        textoDiLejos.setEditable( false );
        textoOjoDerechoEsfera.setEditable( false );
        textoOjoDerechoCilindro.setEditable( false );
        textoOjoDerechoEje.setEditable( false );
        textoOjoDerechoAdc.setEditable( false );
        textoOjoDerechoAdi.setEditable( false );
        textoOjoDerechoAv.setEditable( false );
        textoOjoDerechoDi.setEditable( false );
        textoOjoDerechoPrisma.setEditable( false );
        comboViewerOjoDerechoUbicacion.getCombo().setEnabled( false );
        textoDiCerca.setEditable( false );
        textoOjoIzquierdoEsfera.setEditable( false );
        textoOjoIzquierdoCilindro.setEditable( false );
        textoOjoIzquierdoEje.setEditable( false );
        textoOjoIzquierdoAdc.setEditable( false );
        textoOjoIzquierdoAdi.setEditable( false );
        textoOjoIzquierdoAv.setEditable( false );
        textoOjoIzquierdoDi.setEditable( false );
        textoOjoIzquierdoPrisma.setEditable( false );
        comboViewerOjoIzquierdoUbicacion.getCombo().setEnabled( false );
        textoAltOblea.setEditable( false );
        textoObservaciones.setEditable( false );

        textoDiLejos.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoDerechoEsfera.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoDerechoCilindro.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoDerechoEje.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoDerechoAdc.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoDerechoAdi.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoDerechoAv.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoDerechoDi.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoDerechoPrisma.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoDiCerca.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoIzquierdoEsfera.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoIzquierdoCilindro.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoIzquierdoEje.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoIzquierdoAdc.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoIzquierdoAdi.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoIzquierdoAv.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoIzquierdoDi.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoOjoIzquierdoPrisma.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoAltOblea.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
        textoObservaciones.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
    }

    private void cargarTablaDetalleReposicion() {
        tablaCambios.clearAll();
        tablaCambios.removeAll();
        if ( StringUtils.isNotBlank( textoRx.getText() ) ) {
            for ( DetalleReposicion detalleReposicion : reposicionService.buscarDetalleReposicionAsociadasAlTrabajo( textoRx.getText() ) ) {
                TableItem item = new TableItem( tablaCambios, SWT.NONE );
                item.setText( 0, StringUtils.defaultIfBlank( detalleReposicion.getOjo(), "" ) );
                item.setText( 1, StringUtils.defaultIfBlank( detalleReposicion.getTipo(), "" ) );
                item.setText( 2, StringUtils.defaultIfBlank( detalleReposicion.getValorAnterior(), "" ) );
                item.setText( 3, StringUtils.defaultIfBlank( detalleReposicion.getValorNuevo(), "" ) );
            }
        }
    }


    private boolean datosValidos() {
        if ( StringUtils.isBlank( textoRx.getText() ) ) {
            MessageDialog.openError( shell, "Error", "Debe rellenar el campo RX" );
            textoRx.setFocus();
            return false;
        } else if ( !esRxCorrecto( textoRx.getText() ) ) {
            MessageDialog.openError( shell, "Error", "No es un trabajo válido. RX: " + textoRx.getText() );
            textoRx.setFocus();
            return false;
        }
        if ( comboViewerArea.getCombo().getSelectionIndex() < 0 ) {
            MessageDialog.openError( shell, "Error", "Debe seleccionar un Area" );
            comboViewerArea.getCombo().setFocus();
            return false;
        } else {
            String area = comboViewerArea.getCombo().getText().trim();
            if ( "SUC".equalsIgnoreCase( area ) && StringUtils.isBlank( textoIdResponsable.getText() ) ) {
                MessageDialog.openError( shell, "Error", "Debe indicar un Responsable" );
                textoIdResponsable.setFocus();
                return false;
            }
        }
        if ( StringUtils.isBlank( textoProblema.getText() ) ) {
            MessageDialog.openError( shell, "Error", "Debe rellenar el campo Problema" );
            textoProblema.setFocus();
            return false;
        }
        if ( Session.getAttribute( "MATERIAL_ACABADO" ) == null || !( Session.getAttribute( "MATERIAL_ACABADO" ) instanceof MaterialAcabado ) ) {
            MessageDialog.openError( shell, "Error", "Debe introducir datos de Forma" );
            botonForma.setFocus();
            return false;
        }
        if ( !checkboxOjoDerecho.getSelection() && !checkboxOjoIzquierdo.getSelection() ) {
            MessageDialog.openError( shell, "Error", "Debe seleccionar al menos un Ojo" );
            checkboxOjoDerecho.setFocus();
            return false;
        }
        // Validaciones de los valores de la Receta

        return true;
    }

    private boolean esRxCorrecto( final String rx ) {
        Jb trabajo = trabajoService.findById( rx );
        boolean tipoOk = false;
        boolean estadoOk = false;
        if ( trabajo != null ) {
            String tipo = trabajo.getJbTipo().trim().toUpperCase();
            if ( "GAR".equals( tipo ) || "EXT".equals( tipo ) || "LAB".equals( tipo ) ) {
                tipoOk = true;
            }
            EstadoTrabajo estado = trabajo.estado();
            if ( EstadoTrabajo.CANCELADO != estado && EstadoTrabajo.ENTREGADO != estado ) {
                estadoOk = true;
            }
        }
        return tipoOk && estadoOk;
    }

    private void procesarReposicion() {
        try {
            Empleado empleado = ( Empleado ) Session.getAttribute( Constants.PARAM_USER_LOGGED );
            Jb trabajo = trabajoService.findById( textoRx.getText() );

            Reposicion reposicion = new Reposicion();
            Receta receta = reposicionService.obtenerRecetaReposicion( textoRx.getText() );
            Receta rec = new Receta();

            reposicion.setFactura( textoRx.getText() );
            reposicion.setEmpleado( empleado );
            reposicion.setIdResponsable( textoIdResponsable.getText() );
            reposicion.setFecha( new Date() );
            reposicion.setTipo( calcularTipoReposicionTrabajo( textoRx.getText() ) );
            reposicion.setIdCliente( trabajo.idCliente() );
            reposicion.setCausa( ( CausaReposicion ) comboViewerCausa.getElementAt( comboViewerCausa.getCombo().getSelectionIndex() ) );
            reposicion.setProblema( textoProblema.getText() );
            reposicion.setDx( textoDiagnostico.getText() );
            reposicion.setInstrucciones( "" );

            if ( "INTERMEDIO".equals( textoUsoAnteojos.getText() ) ) {
                reposicion.setUsoAnteojos( 'i' );
            } else if ( "CERCA".equals( textoUsoAnteojos.getText() ) ) {
                reposicion.setUsoAnteojos( 'c' );
            } else if ( "LEJOS".equals( textoUsoAnteojos.getText() ) ) {
                reposicion.setUsoAnteojos( 'l' );
            } else if ( "BIFOCAL".equals( textoUsoAnteojos.getText() ) ) {
                reposicion.setUsoAnteojos( 'b' );
            } else if ( "PROGRESIVO".equals( textoUsoAnteojos.getText() ) ) {
                reposicion.setUsoAnteojos( 'p' );
            } else if ( "BIFOCAL INTERMEDIO".equals( textoUsoAnteojos.getText() ) ) {
                reposicion.setUsoAnteojos( 't' );
            }

            reposicion.setOdEsf( textoOjoDerechoEsfera.getText().trim() );
            reposicion.setOdCil( textoOjoDerechoCilindro.getText().trim() );
            reposicion.setOdEje( textoOjoDerechoEje.getText().trim() );
            reposicion.setOdAdc( textoOjoDerechoAdc.getText().trim() );
            reposicion.setOdAdi( textoOjoDerechoAdi.getText().trim() );
            reposicion.setOdAv( textoOjoDerechoAv.getText().trim() );
            reposicion.setOdDi( textoOjoDerechoDi.getText().trim() );
            reposicion.setOdPrisma( textoOjoDerechoPrisma.getText().trim() );
            Prisma prismaOjoDerecho = ( Prisma ) comboViewerOjoDerechoUbicacion.getElementAt( comboViewerOjoDerechoUbicacion.getCombo().getSelectionIndex() );
            reposicion.setOdPrismaV( prismaOjoDerecho != null ? prismaOjoDerecho.getDescripcion() : null );

            reposicion.setOiEsf( textoOjoIzquierdoEsfera.getText().trim() );
            reposicion.setOiCil( textoOjoIzquierdoCilindro.getText().trim() );
            reposicion.setOiEje( textoOjoIzquierdoEje.getText().trim() );
            reposicion.setOiAdc( textoOjoIzquierdoAdc.getText().trim() );
            reposicion.setOiAdi( textoOjoIzquierdoAdi.getText().trim() );
            reposicion.setOiAv( textoOjoIzquierdoAv.getText().trim() );
            reposicion.setOiDi( textoOjoIzquierdoDi.getText().trim() );
            reposicion.setOiPrisma( textoOjoIzquierdoPrisma.getText().trim() );
            Prisma prismaOjoIzquierdo = ( Prisma ) comboViewerOjoIzquierdoUbicacion.getElementAt( comboViewerOjoIzquierdoUbicacion.getCombo().getSelectionIndex() );
            reposicion.setOiPrismaV( prismaOjoIzquierdo != null ? prismaOjoIzquierdo.getDescripcion() : null );

            reposicion.setDiCerca( textoDiCerca.getText().trim() );
            reposicion.setDiLejos( textoDiLejos.getText().trim() );
            reposicion.setAltObl( textoAltOblea.getText().trim() );
            reposicion.setObservaciones( textoObservaciones.getText().trim() );

            reposicion.setArea( comboViewerArea.getCombo().getText() );


            reposicion.setCliente( trabajo.getCliente().trim() );

            MaterialAcabado materialAcabado = ( MaterialAcabado ) Session.getAttribute( "MATERIAL_ACABADO" );
            if ( materialAcabado != null ) {
                String material = MaterialAcabado.Material.NINGUNO != materialAcabado.getMaterial() ? materialAcabado.getMaterial().name() : "";
                material += MaterialAcabado.Acabado.NINGUNO != materialAcabado.getAcabado() ? "," + materialAcabado.getAcabado().name() : "";
                reposicion.setMaterial( material );
                reposicion.setTratamientos( materialAcabado.getForma() );
                receta.setTratamientos( materialAcabado.getForma() );
            }

            receta.setOdEsfR( textoOjoDerechoEsfera.getText().trim() );
            receta.setOdCilR( textoOjoDerechoCilindro.getText().trim() );
            receta.setOdEjeR( textoOjoDerechoEje.getText().trim() );
            receta.setOdAdcR( textoOjoDerechoAdc.getText().trim() );
            receta.setOdAdiR( textoOjoDerechoAdi.getText().trim() );
            receta.setOdPrismaH( textoOjoDerechoPrisma.getText().trim() );
            receta.setOiEsfR( textoOjoIzquierdoEsfera.getText().trim() );
            receta.setOiCilR( textoOjoIzquierdoCilindro.getText().trim() );
            receta.setOiEjeR( textoOjoIzquierdoEje.getText().trim() );
            receta.setOiAdcR( textoOjoIzquierdoAdc.getText().trim() );
            receta.setOiAdiR( textoOjoIzquierdoAdi.getText().trim() );
            receta.setOiPrismaV( textoOjoIzquierdoPrisma.getText().trim() );
            receta.setDiLejosR( textoDiLejos.getText().trim() );
            receta.setDiCercaR( textoDiCerca.getText().trim() );
            receta.setOdAvR( textoOjoDerechoAv.getText().trim() );
            receta.setOiAvR( textoOjoIzquierdoAv.getText().trim() );
            receta.setAltOblR( textoAltOblea.getText().trim() );
            receta.setObservacionesR( textoObservaciones.getText().trim() );
            receta.setFechaMod( new Date() );
            receta.setOdPrismaV( prismaOjoDerecho != null ? prismaOjoDerecho.getDescripcion() : null );
            receta.setOiPrismaV( prismaOjoIzquierdo != null ? prismaOjoIzquierdo.getDescripcion() : null );


            // estos datos se calculan en el servicio
            // reposicion.setFolio(  );
            // reposicion.setIdSucursal(  );
            // reposicion.setOjo(  );

            reposicion = reposicionService.procesarReposicion( rec, receta, reposicion, textoRx.getText(), checkboxOjoIzquierdo.getSelection(), checkboxOjoDerecho.getSelection() );
            reposicionService.procesarReposicionSegundaParte( reposicion.getFactura(), reposicion.getNumeroOrden() );

            log.debug( "Se ha procesado correctamente la Reposicion " + reposicion.getNumeroOrden() + " para la fatura " + reposicion.getFactura() );
            ApplicationUtils.recargarDatosPestanyaVisible();
            close();
        } catch ( Exception e ) {
            log.error( "Error al procesar Reposicion: " + e.getMessage() );
            MessageDialog.openError( shell, "Error", "Error al procesar reposicion" );
        }
    }

    private Character calcularTipoReposicionTrabajo( final String rx ) {
        Jb trabajo = trabajoService.findById( rx );
        if ( TipoTrabajo.GARANTIA == trabajo.tipo() ) {
            return 'G';
        } else if ( TipoTrabajo.EXTERNO == trabajo.tipo() ) {
            return 'N';
        } else if ( TipoTrabajo.LABORATORIO == trabajo.tipo() ) {
            return 'N';
        }
        return null;
    }

}

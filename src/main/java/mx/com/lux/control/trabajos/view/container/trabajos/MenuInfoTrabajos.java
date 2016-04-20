package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.ExternoService;
import mx.com.lux.control.trabajos.bussiness.service.GarantiaService;
import mx.com.lux.control.trabajos.bussiness.service.TicketService;
import mx.com.lux.control.trabajos.bussiness.service.impresora.TrabajoImpresion;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.ClienteService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.GParametroService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.PrinterException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.EstadoConstants;
import mx.com.lux.control.trabajos.util.constants.RxConstants;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.contacto.AgregarGrupoDialog;
import mx.com.lux.control.trabajos.view.container.contacto.AgregarRxDialog;
import mx.com.lux.control.trabajos.view.container.contacto.GrupoDataDialog;
import mx.com.lux.control.trabajos.view.container.contacto.GruposDataDialog;
import mx.com.lux.control.trabajos.view.container.envios.NoEnviarDialog;
import mx.com.lux.control.trabajos.view.container.externos.EntregaExternoDialog;
import mx.com.lux.control.trabajos.view.container.externos.EnvioExternoDialog;
import mx.com.lux.control.trabajos.view.container.garantia.DesentregarGarantiaDialog;
import mx.com.lux.control.trabajos.view.container.garantia.EntregarGarantiaDialog;
import mx.com.lux.control.trabajos.view.container.garantia.NuevaGarantiaDialog;
import mx.com.lux.control.trabajos.view.container.reposicion.ReposicionDialog;
import mx.com.lux.control.trabajos.view.container.sobres.DatosSobreDialog;
import mx.com.lux.control.trabajos.view.container.sobres.TipoSobre;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MenuInfoTrabajos {

    private final Logger log = Logger.getLogger( MenuInfoTrabajos.class );

    private static final TrabajoService trabajoService;
    private static final TrabajoImpresion trabajoImpresion;
    private static final ClienteService clienteService;
    private static final ExternoService externoService;
    private static final GarantiaService garantiaService;
    private static final TicketService ticketService;
    private static final GParametroService gparametroService;

    public static String ITEM_SELECTED_ID_CLIENTE = "currentIdCliente";
    public static String ITEM_SELECTED_ID_RX = "currentIdRx";
    public static String ITEM_SELECTED_ID_RX2 = "currentIdRx2";
    public static String ITEM_SELECTED_ID_RECETA = "currentIdReceta";
    public static String ITEM_SELECTED_CLIENTE_NOMBRE = "currentClienteNombre";
    public static String ITEM_SELECTED_ID_SUCURSAL = "currentIdSucursal";
    public static String ITEM_SELECTED_REPROGRAMAR_DTO = "currentReprogramarDTO";
    public static String ITEM_SELECTED_TAB = "currentSelectedTab";
    public static String ITEM_SELECTED_SECOND_POPUP = "currentSecondPopup";
    public static String ITEM_SELECTED_THIRD_POPUP = "currentThirdPopup";
    public static String ITEM_SELECTED_GRUPO = "currentGrupo";
    public static String ITEM_SELECTED_EDIT_CLIENTE = "editCliente";
    public static String ITEM_SELECTED_ORDEN_SERVICIO = "currentOS";
	public static String ITEM_SELECTED_NUMERO_ORDEN = "NUMERO_ORDEN";

    private final Menu menuInfoTrabajos;
    private final Shell shell;
    private List<Boolean> menuOptionActive = new ArrayList<Boolean>();

    static {
        trabajoService = ServiceLocator.getBean( TrabajoService.class );
        trabajoImpresion = ServiceLocator.getBean( TrabajoImpresion.class );
        clienteService = ServiceLocator.getBean( ClienteService.class );
        externoService = ServiceLocator.getBean( ExternoService.class );
        garantiaService = ServiceLocator.getBean( GarantiaService.class );
        ticketService = ServiceLocator.getBean( TicketService.class );
        gparametroService = ServiceLocator.getBean( GParametroService.class );

    }

    public MenuInfoTrabajos( Shell shell, Control control, List<Boolean> opcionesMenu ) {
        this.shell = shell;
        this.menuOptionActive = opcionesMenu;
        menuInfoTrabajos = new Menu( control );
        createMenu();
    }

    public static Menu generaMenu( Shell shell, Control control, int[] opciones ) {
        List<Boolean> listaActivos = new ArrayList<Boolean>();
        for ( int i = 0; i < Constants.MENU_TOTAL; i++ ) {
            listaActivos.add( i, ArrayUtils.contains( opciones, i ) );
        }
        MenuInfoTrabajos menu = new MenuInfoTrabajos( shell, control, listaActivos );
        return menu.getMenuInfoTrabajos();
    }

    public Menu getMenuInfoTrabajos() {
        return menuInfoTrabajos;
    }

    private void createMenu() {

        if ( menuOptionActive.get( Constants.MENU_INDEX_DATOS_RECETA ) ) {
            MenuItem mnItemDatosReceta = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemDatosReceta.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.datos.receta" ) );
            mnItemDatosReceta.setImage( ControlTrabajosWindowElements.popupWindowRecetaIcon );
            mnItemDatosReceta.setEnabled( false );

            mnItemDatosReceta.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    DatosRecetaDialog datosRecetaDialog = new DatosRecetaDialog( shell );
                    datosRecetaDialog.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_CONSULTA_TRABAJO ) ) {
            MenuItem mnItemConsultaTrabajo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemConsultaTrabajo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.consultar.trabajo" ) );
            mnItemConsultaTrabajo.setImage( ControlTrabajosWindowElements.popupWindowDatosTrabajoIcon );
            mnItemConsultaTrabajo.setEnabled( false );

            mnItemConsultaTrabajo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    ConsultaTrabajoDialog consultaTrabajoDialog = new ConsultaTrabajoDialog( shell );
                    consultaTrabajoDialog.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_INFO_LAB ) ) {
            MenuItem mnItemInfoLab = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemInfoLab.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.info.laboratorio" ) );
            mnItemInfoLab.setImage( ControlTrabajosWindowElements.popupWindowInfoPinoIcon );
            mnItemInfoLab.setEnabled( false );

            mnItemInfoLab.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    InfoPinoDialog consultaRxDialog = new InfoPinoDialog( shell );
                    consultaRxDialog.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_DATOS_CLIENTE ) ) {
            MenuItem mnItemDatosCliente = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemDatosCliente.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.datos.cliente" ) );
            mnItemDatosCliente.setImage( ControlTrabajosWindowElements.popupWindowDatosClienteIcon );
            mnItemDatosCliente.setEnabled( false );

            mnItemDatosCliente.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_EDIT_CLIENTE );
                    Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_EDIT_CLIENTE, "false" );
                    DatosClienteDialog datosClienteDialog = new DatosClienteDialog( shell );
                    datosClienteDialog.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_REPROGRAMAR ) ) {
            MenuItem mnItemReprogramar = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemReprogramar.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.reprogramar" ) );
            mnItemReprogramar.setImage( ControlTrabajosWindowElements.popupWindowReprogramarIcon );
            mnItemReprogramar.setEnabled( false );

            mnItemReprogramar.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    ReprogramarDialog reprogramarDialog = new ReprogramarDialog( shell );
                    reprogramarDialog.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_NO_CONTACTAR ) ) {
            MenuItem mnItemNoContactar = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNoContactar.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.contactar.no" ) );
            mnItemNoContactar.setImage( ControlTrabajosWindowElements.popupWindowNoContactarIcon );
            mnItemNoContactar.setEnabled( false );

            mnItemNoContactar.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    if ( MessageDialog.openQuestion( shell, MessagesPropertyHelper.getProperty( "contactar.no.title" ), MessagesPropertyHelper.getProperty( "contactar.no.message.01" ) ) ) {
                        NoContactarDialog noContactarDialog = new NoContactarDialog( shell );
                        noContactarDialog.open();
                    }
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_RETENER ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.retenido" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowRetenerIcon );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    RetenidosDialog retenidosDataDialog = new RetenidosDialog( shell );
                    retenidosDataDialog.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_DESRETENER ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.desretener" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowDesretenerIcon );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    Desretener desretener = new Desretener( shell );
                    desretener.action();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_AUTORIZACION ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.autorizacion" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowAutorizacionIcon );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    AutorizacionDialog autorizacionDialog = new AutorizacionDialog( shell );
                    autorizacionDialog.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_ENVIAR ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.enviar" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowEnviarIcon );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    try {
                        String jbRx = ( String ) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
                        Jb jb = trabajoService.findById( jbRx );
                        System.out.println( jb.getRx() );

                        // JB.estado = si NE -> PE, si RNE -> RPE
                        if ( jb.getEstado().getIdEdo().trim().equals( EstadoConstants.ESTADO_NO_ENVIAR ) ) {
                            jb.getEstado().setIdEdo( EstadoConstants.ESTADO_POR_ENVIAR );
                        } else if ( jb.getEstado().getIdEdo().trim().equals( EstadoConstants.ESTADO_ROTO_NO_ENVIAR ) ) {
                            jb.getEstado().setIdEdo( EstadoConstants.ESTADO_ROTO_POR_ENVIAR );
                        } else {
                            throw new IllegalArgumentException( "Estado no valido " + jb.getEstado().getIdEdo() );
                        }

                        Empleado empleado = ( Empleado ) Session.getAttribute( Constants.PARAM_USER_LOGGED );
                        JbTrack jbTrack = new JbTrack();
                        jbTrack.setRx( jbRx );
                        jbTrack.setEstado( jb.estado().codigo() );
                        jbTrack.setObservaciones( jb.getMaterial() );
                        jbTrack.setEmpleado( empleado.getIdEmpleado() );
                        jbTrack.setFecha( new Timestamp( System.currentTimeMillis() ) );
                        jbTrack.setIdMod( empleado.getIdEmpleado() );
                        trabajoService.saveJbAndTrack( jb, jbTrack );
                    } catch ( ApplicationException e ) {
                        e.printStackTrace();
                    }
                    ApplicationUtils.recargarDatosPestanyaVisible();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_NOENVIAR ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.noenviar" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowNoEnviarIcon );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    NoEnviarDialog ned = new NoEnviarDialog( shell );
                    ned.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_EFAX ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.efax" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowEfaxIcon );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {

                    String jbRx = (String) Session.getAttribute(MenuInfoTrabajos.ITEM_SELECTED_ID_RX);
                    Empleado empleado = (Empleado) Session.getAttribute(Constants.PARAM_USER_LOGGED);
                    Jb jb = null;

                    // Mensaje para Fax sucursales Foraneas
                    GParametro parametro = null;
                    try {
                        jb = trabajoService.findById(jbRx);
                        parametro = gparametroService.findById("fax_surte_suc");
                    } catch (ApplicationException e) {
                        e.printStackTrace();
                    }

                    Boolean enviarFax = false;

                    if (jb != null) {
                        if (jb.getSurte().trim().equals("S")) {
                            if (parametro != null) {
                                if (parametro.getValor().trim().toUpperCase().equals("SI")) {
                                    enviarFax = true;
                                }
                            }
                        } else if (jb.getSurte().trim().equals("P")) {
                            enviarFax = true;
                        } else if ( jb.getSurte() == null ) {
                            enviarFax = true;
                        } else if ( jb.getSurte().trim().equals("") ) {
                            enviarFax = true;
                        }
                    }

                    if ( enviarFax == false ) {
                        MessageDialog.openWarning(shell, "Acción invalida", "Acción invalida, Trabajo no se puede enviar por Fax");
                        return;
                    }

                    String[] buttonsLabels = new String[]{ MessagesPropertyHelper.getProperty( "generic.si" ),
                            MessagesPropertyHelper.getProperty( "generic.no" ) };
                    MessageDialog confirm = new MessageDialog( shell, MessagesPropertyHelper.getProperty( "efax.title" ), null, MessagesPropertyHelper.getProperty( "efax.message" ), MessageDialog.INFORMATION, buttonsLabels, 0 );
                    int result = confirm.open();
                    if ( result == 0 ) {
                        try {

                                boolean completeTask = trabajoService.enviarEFax(jbRx, empleado);
                                if (!completeTask) {
                                    if (!completeTask) {
                                        log.debug("warning dialog");
                                        MessageDialog warningDialog = new MessageDialog(shell, MessagesPropertyHelper.getProperty("advertencia.title"),
                                                null, MessagesPropertyHelper.getProperty("efaxfail.message"),
                                                MessageDialog.ERROR, new String[]{MessagesPropertyHelper.getProperty("generic.ok")}, 0);
                                        warningDialog.open();
                                    }
                                }


                            ApplicationUtils.recargarDatosPestanyaVisible();
                        } catch ( ApplicationException e ) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_SOBRE ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.sobre" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.sobresIcon );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    DatosSobreDialog dialog = new DatosSobreDialog( shell, TipoSobre.ENVIO );
                    dialog.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_DATOS_FACTURA ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.datosfactura" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowFacturaIcon );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    String rx = ( String ) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
                    try {
                        Jb trabajo = trabajoService.findById( rx );
                        log.info( "Cargando dialogo de Datos de Factura. RX: " + rx );
                        if ( "EXT".equalsIgnoreCase( trabajo.getJbTipo().trim() ) ) {
                            log.info( "Es un trabajo Externo" );
                            DatosFacturaExternoDialog dialog = new DatosFacturaExternoDialog( shell );
                            dialog.open();
                        } else {
                            DatosFacturaDialog dialog = new DatosFacturaDialog( shell );
                            dialog.open();
                        }
                    } catch ( Exception e ) {
                        Status status = new Status( IStatus.ERROR, "JSOI", 0, "Error al cargar datos de Factura", null );
                        ErrorDialog.openError( shell, "Error", "Error", status );
                    }
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_CREAR_GRUPO ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.grupo" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowCreateGroupIcon );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    String rxId = ( String ) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );

                    if ( rxId.startsWith( RxConstants.TIPO_GRUPO_F ) ) {
                        Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_GRUPO, rxId );
                        GrupoDataDialog grupoDataDialog = new GrupoDataDialog( shell );
                        grupoDataDialog.open();
                    } else {
                        Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_SECOND_POPUP );
                        GruposDataDialog gruposDataDialog = new GruposDataDialog( shell );
                        Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_SECOND_POPUP, gruposDataDialog );
                        gruposDataDialog.open();
                    }

                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_DESVINCULAR ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.desvincular" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowDesvincularIcon );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    Desvincular desvincular = new Desvincular();
                    desvincular.action();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_AGREGAR_GRUPO ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.agrear.grupo" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowAddGroupIcon );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    AgregarGrupoDialog agregarGrupoDialog = new AgregarGrupoDialog( shell );
                    agregarGrupoDialog.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_AGREGAR_RX ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.agrear.rx" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowAddRx );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    AgregarRxDialog agregarRxDialog = new AgregarRxDialog( shell );
                    agregarRxDialog.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_NUEVA_ORDEN ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.nueva.orden" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowNuevaOrden );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    ClienteBusquedaDialog clienteBusquedaDialog = new ClienteBusquedaDialog( shell );
                    clienteBusquedaDialog.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_ENTREGAR ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.entregar" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowEntregar );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    new EntregasDialog( shell, true ).open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_DESENTREGAR ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.desentregar" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowDesentregar );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    new EntregasDialog( shell, false ).open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_IMPRIMIR ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.imprimir.os" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowImprimir );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    try {
                        Jb os = ( Jb ) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ORDEN_SERVICIO );
                        Empleado empleado = ( Empleado ) Session.getAttribute( Constants.PARAM_USER_LOGGED );
                        Cliente cliente = clienteService.findClienteById( Integer.valueOf( os.getIdCliente() ) );
                        JbNota jbNota = new JbNota();
                        Boolean OS = true;
                        System.out.println( os.getJbTipo() );
                        if( os.getJbTipo() != null ){
                            jbNota = trabajoService.findJbNota( Integer.parseInt( os.getRx().substring( 1 ) ) );
                        } else {
                            jbNota = trabajoService.findJbNota( Integer.parseInt( os.getRx() ) );
                            OS = false;
                        }
                        System.out.println( OS );
                        trabajoImpresion.imprimirOrdenServicio( jbNota, empleado, os, cliente, empleado.getSucursal(), OS );
                    } catch ( NumberFormatException e ) {
                        e.printStackTrace();
                    } catch ( PrinterException pe ) {
                        MessageBox mb = new MessageBox( shell, SWT.ERROR );
                        mb.setMessage( pe.getMessage() );
                        mb.open();
                    } catch ( ApplicationException e ) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_BODEGA ) ) {
            MenuItem mnItemNuevo = new MenuItem( menuInfoTrabajos, SWT.NONE );
            mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.bodega" ) );
            mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowBodega );
            mnItemNuevo.setEnabled( false );

            mnItemNuevo.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    String[] buttonsLabels = new String[]{ MessagesPropertyHelper.getProperty( "generic.si" ),
                            MessagesPropertyHelper.getProperty( "generic.no" ) };
                    MessageDialog confirm = new MessageDialog( shell, MessagesPropertyHelper.getProperty( "bodega.title" ), null, MessagesPropertyHelper.getProperty( "bodega.message" ), MessageDialog.INFORMATION, buttonsLabels, 0 );
                    int result = confirm.open();
                    if ( result == 0 ) {
                        try {
                            Jb os = ( Jb ) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ORDEN_SERVICIO );
                            Empleado empleado = ( Empleado ) Session.getAttribute( Constants.PARAM_USER_LOGGED );
                            trabajoService.enviarOrdenDeServicioABodega( os, empleado );
                            ApplicationUtils.recargarDatosPestanyaVisible();
                        } catch ( ApplicationException e ) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_EXTERNO_ENVIAR ) ) {
            MenuItem menuItem = new MenuItem( menuInfoTrabajos, SWT.NONE );
            menuItem.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.externo.enviar" ) );
            menuItem.setImage( ControlTrabajosWindowElements.iconoEnviarExterno );
            menuItem.setEnabled( false );

            menuItem.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent ev ) {
                    EnvioExternoDialog envioExternoDialog = new EnvioExternoDialog( shell, true );
                    envioExternoDialog.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent ev ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_EXTERNO_ELIMINAR ) ) {
            MenuItem menuItem = new MenuItem( menuInfoTrabajos, SWT.NONE );
            menuItem.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.externo.eliminar" ) );
            menuItem.setImage( ControlTrabajosWindowElements.iconoEliminarExterno );
            menuItem.setEnabled( false );

            menuItem.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent ev ) {
                    String rx = ( String ) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
                    String titulo = "Eliminar Externo";
                    String mensaje = "Eliminar externo: " + rx;
                    boolean confirma = MessageDialog.openConfirm( shell, titulo, mensaje );
                    if ( confirma ) {
                        try {
                            externoService.eliminarExterno( rx );
                        } catch ( ApplicationException e ) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent ev ) {
                }
            } );
        }

        if ( menuOptionActive.get( Constants.MENU_INDEX_EXTERNO_ENTREGAR ) ) {
            MenuItem menuItem = new MenuItem( menuInfoTrabajos, SWT.NONE );
            menuItem.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.externo.entregar" ) );
            menuItem.setImage( ControlTrabajosWindowElements.iconoEntregarExterno );
            menuItem.setEnabled( false );

            menuItem.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    String rx = ( String ) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
                    try {
                        Jb trabajo = trabajoService.findById( rx );
                        BigDecimal saldo = trabajo.getSaldo();
                        if ( BigDecimal.ZERO.compareTo( saldo ) == 0 ) {
                            procesarEntregaTrabajo( rx );
                        } else {
                            eliminarPagosExtNoConfirmados( rx );
                            abrirDialogoSaldos();
                        }
                    } catch ( Exception ex ) {
                        Status status = new Status( IStatus.ERROR, "JSOI", 0, MessagesPropertyHelper.getProperty( "trabajo.error" ), null );
                        ErrorDialog.openError( shell, "Error", MessagesPropertyHelper.getProperty( "trabajo.error" ), status );
                    }
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }

        crearOpcionGarantiaNueva();
        crearOpcionGarantiaEntregar();
        crearOpcionGarantiaDesentregar();
        crearOpcionGarantiaImprimir();
        crearOpcionGarantiaBodega();
		crearOpcionGarantiaReposicion();
    }

    private void crearOpcionGarantiaNueva() {
        if ( menuOptionActive.get( Constants.MENU_INDEX_GARANTIA_NUEVA ) ) {
            MenuItem menuItem = new MenuItem( menuInfoTrabajos, SWT.NONE );
            menuItem.setText( "Nueva Garantía" );
            menuItem.setEnabled( true );

            menuItem.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    log.debug( "Nueva Garantia" );
                    NuevaGarantiaDialog dialogo = new NuevaGarantiaDialog( shell );
                    dialogo.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }
    }

    private void crearOpcionGarantiaEntregar() {
        if ( menuOptionActive.get( Constants.MENU_INDEX_GARANTIA_ENTREGAR ) ) {
            MenuItem menuItem = new MenuItem( menuInfoTrabajos, SWT.NONE );
            menuItem.setText( "Entregar Garantía" );
            menuItem.setEnabled( true );

            menuItem.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    String rx = ( String ) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
                    EntregarGarantiaDialog dialogo = new EntregarGarantiaDialog( shell, rx );
                    dialogo.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }
    }

    private void crearOpcionGarantiaDesentregar() {
        if ( menuOptionActive.get( Constants.MENU_INDEX_GARANTIA_DESENTREGAR ) ) {
            MenuItem menuItem = new MenuItem( menuInfoTrabajos, SWT.NONE );
            menuItem.setText( "Desentregar Garantía" );
            menuItem.setEnabled( true );

            menuItem.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    String rx = ( String ) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
                    DesentregarGarantiaDialog dialogo = new DesentregarGarantiaDialog( shell, rx );
                    dialogo.open();
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }
    }

    private void crearOpcionGarantiaImprimir() {
        if ( menuOptionActive.get( Constants.MENU_INDEX_GARANTIA_IMPRIMIR ) ) {
            MenuItem menuItem = new MenuItem( menuInfoTrabajos, SWT.NONE );
            menuItem.setText( "Imprimir Garantía" );
            menuItem.setEnabled( true );

            menuItem.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    String rx = ( String ) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
                    if ( ticketService.imprimeTicketGarantia( rx ) ) {
                        MessageDialog.openInformation( shell, "Imprimir Garantia", "Se ha impreso correctamente la Garantía: " + rx );
                    } else {
                        MessageDialog.openError( shell, "Imprimir Garantia", "No se ha podido imprimir la Garantía: " + rx );
                    }
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }
    }

    private void crearOpcionGarantiaBodega() {

        if ( menuOptionActive.get( Constants.MENU_INDEX_GARANTIA_BODEGA ) ) {
            MenuItem menuItem = new MenuItem( menuInfoTrabajos, SWT.NONE );
            menuItem.setText( "Bodega Garantía" );
            menuItem.setEnabled( false );

            menuItem.addSelectionListener( new SelectionListener() {
                @Override
                public void widgetSelected( SelectionEvent arg0 ) {
                    if ( MessageDialog.openConfirm( shell, "Enviar a Bodega", "¿ Está seguro de que desea mandar la Orden de Garantía a Bodega ?" ) ) {
                        String rx = ( String ) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
                        Empleado empleado = ( Empleado ) Session.getAttribute( Constants.PARAM_USER_LOGGED );
                        try {
                            if ( garantiaService.mandarABodega( rx, empleado.getIdEmpleado() ) ) {
                                MessageDialog.openInformation( shell, "Envio a Bodega", "Se ha mandado la Garantía a Bodega: " + rx );
                                ApplicationUtils.recargarDatosPestanyaVisible();
                            } else {
                                MessageDialog.openError( shell, "Envio a Bodega", "No se ha podido mandar la Garantía Bodega: " + rx );
                            }
                        } catch ( Exception e ) {
                            log.error( "Error al enviar a Bodega: " + rx + " : " + e.getMessage() );
                            MessageDialog.openError( shell, "Envio a Bodega", "Se ha producido un error al enviar la Garantia a Bodega: " + rx );
                        }

                    }
                }

                @Override
                public void widgetDefaultSelected( SelectionEvent arg0 ) {
                }
            } );
        }
    }

	private void crearOpcionGarantiaReposicion() {

		if ( menuOptionActive.get( Constants.MENU_INDEX_GARANTIA_REPOSICION ) ) {
			MenuItem menuItem = new MenuItem( menuInfoTrabajos, SWT.NONE );
			menuItem.setText( "Reposición" );
			menuItem.setEnabled( false );

			menuItem.addSelectionListener( new SelectionListener() {
				@Override
				public void widgetSelected( SelectionEvent arg0 ) {
					String rx = ( String ) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
					ReposicionDialog dialogo = new ReposicionDialog( shell, rx );
					dialogo.open();
				}

				@Override
				public void widgetDefaultSelected( SelectionEvent arg0 ) {
				}
			} );
		}
	}

    private void procesarEntregaTrabajo( String rx ) {
        if ( trabajoService.puedeEntregarExterno( rx ) ) {
            String[] buttonsLabels = new String[]{ MessagesPropertyHelper.getProperty( "generic.si" ),
                    MessagesPropertyHelper.getProperty( "generic.no" ) };
            MessageDialog confirm = new MessageDialog( shell, MessagesPropertyHelper.getProperty( "externo.entrega.titulo" ), null, MessagesPropertyHelper.getProperty( "externo.entrega.mensaje" ), MessageDialog.INFORMATION, buttonsLabels, 0 );
            if ( confirm.open() == 0 ) {
                try {
                    Empleado empleado = ( Empleado ) Session.getAttribute( Constants.PARAM_USER_LOGGED );
                    externoService.entregarExterno( rx, empleado.getIdEmpleado().trim() );
                } catch ( Exception e ) {
                    Status status = new Status( IStatus.ERROR, "JSOI", 0, e.getMessage(), null );
                    ErrorDialog.openError( shell, "Error", MessagesPropertyHelper.getProperty( "trabajo.error" ), status );
                }
            }
        } else {
            Status status = new Status( IStatus.ERROR, "JSOI", 0, MessagesPropertyHelper.getProperty( "trabajo.error" ), null );
            ErrorDialog.openError( shell, "Error", MessagesPropertyHelper.getProperty( "externo.entrega.error.19" ), status );
        }
    }

    private void abrirDialogoSaldos() {
        EntregaExternoDialog dialogo = new EntregaExternoDialog( shell );
        dialogo.open();
    }

    private void eliminarPagosExtNoConfirmados( final String rx ) {
        try {
            externoService.eliminarPagosExtNoConfirmados( rx );
        } catch ( Exception e ) {
            Status status = new Status( IStatus.ERROR, "JSOI", 0, e.getMessage(), null );
            ErrorDialog.openError( shell, "Error", MessagesPropertyHelper.getProperty( "trabajo.error" ), status );
        }
    }
}

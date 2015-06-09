package mx.com.lux.control.trabajos.bussiness.service.envio;

import mx.com.lux.control.trabajos.bussiness.service.GarantiaService;
import mx.com.lux.control.trabajos.bussiness.service.TicketService;
import mx.com.lux.control.trabajos.bussiness.service.impresora.TrabajoImpresion;
import mx.com.lux.control.trabajos.data.dao.EmpleadoDAO;
import mx.com.lux.control.trabajos.data.dao.JbDevDAO;
import mx.com.lux.control.trabajos.data.dao.JbViajeDetDAO;
import mx.com.lux.control.trabajos.data.dao.envio.ViajeDAO;
import mx.com.lux.control.trabajos.data.dao.ordenservicio.OrdenServicioDAO;
import mx.com.lux.control.trabajos.data.dao.sobres.SobreDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.JbTrackDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.DateUtils;
import org.apache.commons.lang.time.*;
import mx.com.lux.control.trabajos.util.constants.*;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service( "envioService" )
public class EnvioServiceImpl implements EnvioService {

    private final Logger log = Logger.getLogger( EnvioService.class );

    @Resource
    private TrabajoDAO trabajoDAO;

    @Resource
    private JbTrackDAO trackDAO;

    @Resource
    private ViajeDAO viajeDAO;

    @Resource
    private SobreDAO sobreDAO;

    @Resource
    private EmpleadoDAO empleadoDAO;

    @Resource
    private JbDevDAO jbDevDAO;

    @Resource
    private JbViajeDetDAO jbViajeDetDAO;

    @Resource
    private TicketService ticketService;

    @Resource
    private TrabajoImpresion trabajoImpresion;

    @Resource
    private GarantiaService garantiaService;

    @Override
    public int findNextIdViaje() throws ApplicationException {
        return viajeDAO.findNextIdViaje();
    }

    public void imprimirPackingPrevio() throws ApplicationException {
        Empleado emp = ( Empleado ) Session.getAttribute( Constants.PARAM_USER_LOGGED );
        Sucursal sucursal = emp.getSucursal();
        trabajoImpresion.imprimirPackingPrevio( new Date(), viajeDAO.findNextIdViaje(), emp, sucursal,
                trabajoDAO.buscarPorEnviarLaboratorio(),
                trabajoDAO.findByJbTipo( JbTipoConstants.JB_TIPO_EXT ),
                trabajoDAO.findByJbTipo( JbTipoConstants.JB_TIPO_REF ),
                trabajoDAO.findByJbTipo( JbTipoConstants.JB_TIPO_GAR ),
                trabajoDAO.findByJbTipo( JbTipoConstants.JB_TIPO_OS ),
                trabajoDAO.findByJbEstado( JbTipoConstants.JB_TIPO_X1 ),
                sobreDAO.findAllSobresByFechaEnvioNullAndRxEmpty(),
                sobreDAO.findAllSobresByFechaEnvioNullAndRxNotEmpty(),
                trabajoDAO.findJbDevByFechaEnvioNull(),
                trabajoDAO.findAllDoctoInvByTipoAndEstado( Constants.DEVOLUCIONES_TIPO_DA, Constants.DEVOLUCIONES_ESTADO_PENDIENTE ) );
    }

    @Override
    public void imprimirPackingCerrado( String folioViaje ) throws ApplicationException {
        Empleado emp = ( Empleado ) Session.getAttribute( Constants.PARAM_USER_LOGGED );
        Sucursal sucursal = emp.getSucursal();
        trabajoImpresion.imprimirPackingCerrado( new Date(),
                viajeDAO.findNextIdViaje(),
                folioViaje,
                emp,
                sucursal,
                trabajoDAO.buscarPorEnviarLaboratorio(),
                trabajoDAO.findByJbTipo( JbTipoConstants.JB_TIPO_EXT ),
                trabajoDAO.findByJbTipo( JbTipoConstants.JB_TIPO_REF ),
                trabajoDAO.findByJbTipo( JbTipoConstants.JB_TIPO_GAR ),
                trabajoDAO.findByJbTipo( JbTipoConstants.JB_TIPO_OS ),
                trabajoDAO.findByJbEstado( JbTipoConstants.JB_TIPO_X1 ),
                sobreDAO.findAllSobresByFechaEnvioNullAndRxEmpty(),
                sobreDAO.findAllSobresByFechaEnvioNullAndRxNotEmpty(),
                trabajoDAO.findJbDevByFechaEnvioNull(),
                trabajoDAO.findAllDoctoInvByTipoAndEstado( Constants.DEVOLUCIONES_TIPO_DA, Constants.DEVOLUCIONES_ESTADO_PENDIENTE ) );
    }

    public Boolean  saveValuesCerrarViaje( String viajeFolio ) {
        Empleado empleado = ( Empleado ) Session.getAttribute( Constants.PARAM_USER_LOGGED );
        List<Jb> jbList = new ArrayList<Jb>();
        List<Jb> jbListNoEnviar = new ArrayList<Jb>();
        List<Jb> jbListTmp = new ArrayList<Jb>();
        List<String> listJbTipo = new ArrayList<String>();
        listJbTipo.add( JbTipoConstants.JB_TIPO_LAB );
        listJbTipo.add( JbTipoConstants.JB_TIPO_EXT );
        listJbTipo.add( JbTipoConstants.JB_TIPO_REF );
        listJbTipo.add( JbTipoConstants.JB_TIPO_GAR );
        listJbTipo.add( JbTipoConstants.JB_TIPO_OS );
        List<JbSobre> sobres = new ArrayList<JbSobre>();
        List<JbSobre> sobresVacios = new ArrayList<JbSobre>();
        List<JbSobre> sobresNoVacios = new ArrayList<JbSobre>();
        List<Jb> ordenesServicio = new ArrayList<Jb>();


        List<JbDev> jbDevList = new ArrayList<JbDev>();
        List<DoctoInv> doctoInvList = new ArrayList<DoctoInv>();
        String contenido = "";
        String equal = Constants.SYMBOL_EQUAL;
        String coma = Constants.SYMBOL_COMA;
        String pipe = Constants.SYMBOL_PIPE;
        // inicializa en 2 para guardar JbViaje y Acuse
        int count = 2;
        Date date = new Date();
        boolean viajeVacio = false;
        // Arreglo vacio de objetos para borrar
        Object[] delObjects = new Object[ 0 ];

        try {

            JbViaje jbViaje = new JbViaje();
            jbViaje.setId( new JbViajeId() );
            List<JbTrack> jbTrackList = new ArrayList<JbTrack>();
            Acuses acuse = new Acuses();

            // se extrae la lista de trabajos del detalle
            jbList = getListJbByTipo( listJbTipo );

            // se busca jb.estado = 'X1' y se agrega a trabajos detalle
            jbListTmp = trabajoDAO.findByJbEstado( EstadoConstants.ESTADO_ENVIAR_EXTERNO );
            for ( Jb j : ApplicationUtils.compruebaLista( jbListTmp ) ) {
                jbList.add( j );
            }
            jbListTmp.clear();

            // se busca jb.estado = 'NE/RNE'
            jbListNoEnviar = trabajoDAO.findByJbEstado( EstadoConstants.ESTADO_NO_ENVIAR );
            jbListTmp = trabajoDAO.findByJbEstado( EstadoConstants.ESTADO_ROTO_NO_ENVIAR );
            if ( jbListNoEnviar == null ) {
                jbListNoEnviar = new ArrayList<Jb>();
            } else if ( !jbListNoEnviar.isEmpty() ) {
                for ( Jb j : ApplicationUtils.compruebaLista( jbListTmp ) ) {
                    jbListNoEnviar.add( j );
                }
            } else {
                jbListNoEnviar = jbListTmp;
            }

            // se extrae la lista de sobres
            sobresVacios = sobreDAO.findAllSobresByFechaEnvioNullAndRxEmpty();
            sobresNoVacios = sobreDAO.findAllSobresByFechaEnvioNullAndRxNotEmpty();
            if ( sobresVacios != null && !sobresVacios.isEmpty() ) {
                sobres.addAll( sobresVacios );
            }
            if ( sobresNoVacios != null && !sobresNoVacios.isEmpty() ) {
                sobres.addAll( sobresNoVacios );
            }

            // se extrae la lista de devoluciones armazones sp
            jbDevList = trabajoDAO.findJbDevByFechaEnvioNull();

            // se extrae la lista de devoluciones
            doctoInvList = trabajoDAO.findAllDoctoInvByTipoAndEstado( Constants.DEVOLUCIONES_TIPO_DA, Constants.DEVOLUCIONES_ESTADO_PENDIENTE );

            // se extrae lista de ordenes de servicios
            ordenesServicio = trabajoDAO.findByJbTipo( JbTipoConstants.JB_TIPO_OS );

            // set valores para JbViaje
            String nextIdViaje = String.valueOf( findNextIdViaje() );
            jbViaje.getId().setIdViaje( nextIdViaje );
            jbViaje.setFolio( viajeFolio );
            jbViaje.setAbierto( false );
            jbViaje.setEmp( empleado.getIdEmpleado() );
            jbViaje.getId().setFecha( new Timestamp( System.currentTimeMillis() ) );
            jbViaje.setHora( new Date( date.getTime() ) );

            List<JbViajeDet> lstViajeDet = new ArrayList<JbViajeDet>();;


            // set valores para Acuses
            acuse.setIdTipo( AcusesConstants.ENV );
            acuse.setFechaCarga( new Timestamp( System.currentTimeMillis() ) );
            acuse.setIntentos( Constants.CERO_INTEGER );

            // se crea contenido para Acuse.contenido
            contenido = Constants.RX_VAL + equal;

            // se recorre lista trabajos detalle
            if ( jbList != null && !jbList.isEmpty() ) {
                for ( Jb job : jbList ) {
                    count++;
                    JbEstado estado = job.getEstado();
                    if ( estado.getIdEdo().startsWith( EstadoConstants.ESTADO_POR_ENVIAR ) ) {
                        job.setEstado( new JbEstado( EstadoConstants.ESTADO_EN_PINO ) );
                        jbTrackList.add( setValuesJbTrack( job.getRx(), nextIdViaje, empleado, EstadoConstants.ESTADO_EN_PINO ) );
                    } else if ( estado.getIdEdo().startsWith( EstadoConstants.ESTADO_ROTO_POR_ENVIAR ) ) {
                        job.setEstado( new JbEstado( EstadoConstants.ESTADO_ROTO_EN_PINO ) );
                        jbTrackList.add( setValuesJbTrack( job.getRx(), nextIdViaje, empleado, EstadoConstants.ESTADO_ROTO_EN_PINO ) );
                    } else if ( estado.getIdEdo().startsWith( RxConstants.TIPO_EXTERNO ) ) {
                        job.setEstado( new JbEstado( EstadoConstants.ESTADO_ENVIADO_EXTERNO ) );
                        jbTrackList.add( setValuesJbTrack( job.getRx(), nextIdViaje, empleado, EstadoConstants.ESTADO_ENVIADO_EXTERNO ) );
                    } else {
                        jbTrackList.add( setValuesJbTrack( job.getRx(), nextIdViaje, empleado, estado.getIdEdo() ) );
                    }
                    count++;
                    job.setIdViaje( nextIdViaje );

                    JbViajeDet viaje = new JbViajeDet();
                    viaje.setIdViaje( nextIdViaje.trim() );
                    viaje.setFecha( new Timestamp( System.currentTimeMillis() ) );
                    viaje.setRx( job.getRx().trim() );
                    viaje.setMaterial( job.getMaterial().trim() );
                    viaje.setJbTipo( job.getJbTipo().trim() );
                    viaje.setSurte( StringUtils.trimToEmpty( job.getSurte() ) );
                    viaje.setRoto( job.getRoto() );
                    lstViajeDet.add( viaje );

                    String rx = job.getRx();

                    if ( job.getJbTipo().startsWith("GAR") ) {
                        Integer idGarantia = Integer.parseInt(rx.substring(1));
                        JbGarantia jbGarantia = garantiaService.obtenerGarantiaPorId(idGarantia);

                        if ( jbGarantia.getNumOrden() == null )
                            rx = job.getRx();
                        else
                            rx = job.getRx() + jbGarantia.getNumOrden().toString();
                    }

                    if ( job.getEstado().getIdEdo().startsWith( EstadoConstants.ESTADO_ROTO_POR_ENVIAR ) ) {

                        Integer numOrden = trabajoDAO.getLastNumOrdenRepo(job.getRx());

                        rx = "R" + job.getRx() + numOrden.toString();
                    }

                    if ( job.getEstado().getIdEdo().startsWith( EstadoConstants.ESTADO_ROTO_EN_PINO ) ) {

                        Integer numOrden = trabajoDAO.getLastNumOrdenRepo(job.getRx());
                        JbRoto jbRoto = trabajoDAO.getLastJbRotoByRx( job.getRx() );

                        Boolean rotoArmazon = false;

                        if ( jbRoto == null )
                            rotoArmazon = false;
                        else {
                            if ( jbRoto.getTipo().trim().equals("A") ) {
                                rotoArmazon = true;
                            }else{
                                rotoArmazon = false;
                            }
                        }

                        if ( rotoArmazon ) {
                            JbSobre jbSobreTmp = trabajoDAO.getSobreFolioSobreAndEmp( Integer.toString( jbRoto.getIdRoto() ), "ROTO" );

                            if ( jbSobreTmp != null ) {
                                rx = "P" + jbSobreTmp.getId().toString();
                            }
                        }else {

                            rx = "R" + job.getRx();

                            if (numOrden != null)
                                rx = rx + numOrden.toString();
                        }

                    }

                    List<JbTrack> tracks = trackDAO.findJbTrackByRxAndEstado( job.getRx(), "FAX" );
                    String colSurte = "";

                    if ( tracks.isEmpty() ) {
                        if (job.getSurte() != null) {
                            colSurte = job.getSurte().trim();
                        }
                    }else {
                        colSurte = "F" + job.getSurte().trim();
                    }

                    // se crea contenido para acuse
                    if ( colSurte.trim().equals("FP") ) {
                        contenido = contenido;
                    }else {
                        contenido = contenido + rx + coma + " ";
                    }
                }
            }

            // actualizar JbSobre
            for ( JbSobre sobre : ApplicationUtils.compruebaLista( sobres ) ) {
                sobre.setFechaEnvio( new Timestamp( System.currentTimeMillis() ) );
                sobre.setIdViaje( nextIdViaje );
                if ( StringUtils.isNotBlank( sobre.getRx() ) ) {
                    Jb trabajo = trabajoDAO.findById( sobre.getRx() );
                    if ( trabajo != null ) {
                        JbEstado estado = trabajo.getEstado();
                        if ( EstadoTrabajo.POR_ENVIAR.equals( trabajo.estado() ) ) {
                            trabajo.setEstado( new JbEstado( EstadoConstants.ESTADO_EN_PINO ) );
                            jbTrackList.add( setValuesJbTrack( trabajo.getRx(), nextIdViaje, empleado, EstadoConstants.ESTADO_EN_PINO ) );
                        } else if ( EstadoTrabajo.ROTO_POR_ENVIAR.equals( trabajo.estado() ) ) {
                            trabajo.setEstado( new JbEstado( EstadoConstants.ESTADO_ROTO_EN_PINO ) );
                            jbTrackList.add( setValuesJbTrack( trabajo.getRx(), nextIdViaje, empleado, EstadoConstants.ESTADO_ROTO_EN_PINO ) );
                        } else {
                            jbTrackList.add( setValuesJbTrack( trabajo.getRx(), nextIdViaje, empleado, estado.getIdEdo() ) );
                        }
                    }
                    JbViajeDet viaje = new JbViajeDet();
                    viaje.setIdViaje( nextIdViaje.trim() );
                    viaje.setFecha( new Timestamp( System.currentTimeMillis() ) );
                    viaje.setRx( trabajo.getRx().trim() );
                    viaje.setMaterial( trabajo.getMaterial().trim() );
                    viaje.setJbTipo( trabajo.getJbTipo().trim() );
                    viaje.setSurte( StringUtils.trimToEmpty( trabajo.getSurte() ) );
                    viaje.setRoto( trabajo.getRoto() );
                    lstViajeDet.add( viaje );
                    jbList.add( trabajo );
                    count = count + 3;
                } else {
                    count++;
                }

                contenido =  contenido + "P" + sobre.getId() + coma + " ";
            }

            // actualizar JbDev
            for ( JbDev jbd : ApplicationUtils.compruebaLista( jbDevList ) ) {
                jbd.setFechaEnvio( new Timestamp( System.currentTimeMillis() ) );
                jbd.setIdViaje( nextIdViaje );
                contenido =  contenido + "P" + jbd.getIdSobre() + coma + " ";
                count++;
            }

            // Se termina campo rxVal=
            if ( contenido.endsWith(coma + " ") ) {
                contenido = contenido.substring(0, contenido.length()-2);
                contenido = contenido + pipe;
            }else{
                contenido = contenido + pipe;
            }

            // actualizar DoctoInv
            for ( DoctoInv di : ApplicationUtils.compruebaLista( doctoInvList ) ) {
                di.setEstado( Constants.DEVOLUCIONES_ESTADO_ENVIADO );
                count++;
            }

            SimpleDateFormat dateCodeBar = new SimpleDateFormat( "ddMM" );
            String idSuc = ApplicationUtils.shiftStringRight(Integer.toString(empleado.getSucursal().getIdSucursal()), 5, "0");
            String strViaje = ApplicationUtils.shiftStringRight(jbViaje.getId().getIdViaje(), 2, "0");

            String barCode = strViaje + dateCodeBar.format(new Timestamp( System.currentTimeMillis())) + idSuc;

            // se agrega más contenido para acuse
            contenido = contenido + Constants.ID_SUCURSAL_VAL + equal + empleado.getSucursal().getIdSucursal() + pipe
                    + Constants.FECHA_VAL + equal
                    + DateUtils.formatDate( new Timestamp( System.currentTimeMillis() ),
                                            ApplicationPropertyHelper.getProperty( "app.format.date.date" ) )
                    + pipe + Constants.HORA_VAL + equal
                    + DateUtils.formatDate( new Timestamp( System.currentTimeMillis() ),
                                            ApplicationPropertyHelper.getProperty( "app.format.date.time" ) ) + pipe;

            // se agregan objetos al arreglo
            Object[] obj = new Object[ count ];
            obj[ 0 ] = jbViaje;
            acuse.setContenido( contenido );
            obj[ 1 ] = acuse;
            count = 2;

            // se agregan objetos de listas al arreglo

            if ( jbList != null && !jbList.isEmpty() ) {
                for ( Jb aJbList : jbList ) {
                    obj[ count ] = aJbList;
                    count++;
                }
                for ( JbTrack aJbTrackList : jbTrackList ) {
                    obj[ count ] = aJbTrackList;
                    count++;
                }
            }

            for ( JbSobre sobre : ApplicationUtils.compruebaLista( sobres ) ) {
                obj[ count ] = sobre;
                count++;
            }

            if ( jbDevList != null && !jbDevList.isEmpty() ) {
                for ( JbDev aJbDevList : jbDevList ) {
                    obj[ count ] = aJbDevList;
                    count++;
                }
            }

            for ( DoctoInv aDoctoInvList : ApplicationUtils.compruebaLista( doctoInvList ) ) {
                obj[ count ] = aDoctoInvList;
                count++;
            }

            // actualiza en BD jb.estado = NE/RNE
            if ( jbListNoEnviar != null && !jbListNoEnviar.isEmpty() ) {
                int c = 0;
                for ( Jb job : jbListNoEnviar ) {
                    c++;
                    JbEstado estado = job.getEstado();
                    if ( estado.getIdEdo().startsWith( EstadoConstants.ESTADO_NO_ENVIAR ) ) {
                        job.setEstado( new JbEstado( EstadoConstants.ESTADO_POR_ENVIAR ) );
                    } else if ( job.getEstado().getIdEdo().startsWith( EstadoConstants.ESTADO_ROTO_NO_ENVIAR ) ) {
                        job.setEstado( new JbEstado( EstadoConstants.ESTADO_ROTO_POR_ENVIAR ) );
                    }
                }

                // se agregan objetos al arreglo
                Object[] objs = new Object[ c ];
                for ( int i = 0; i < jbListNoEnviar.size(); i++ ) {
                    objs[ i ] = jbListNoEnviar.get( i );
                }

                trabajoDAO.saveOrUpdateDeleteObjectList( objs, delObjects );
            }

            // valida si guarda en BD
            if ( count > 2 ) {
                // se guardan todos los objetos
                trabajoDAO.saveOrUpdateDeleteObjectList( obj, delObjects );

                // se agrega contenido faltante con idAcuse guardado previamente
                contenido = acuse.getContenido() + Constants.ID_ACUSE_VAL + equal + acuse.getIdAcuse() + pipe + Constants.ENVIO_VAL + equal + barCode + pipe;
                acuse.setContenido( contenido );

                trabajoDAO.save( acuse );
                //for( JbViajeDet trip : lstViajeDet ){
                    jbViajeDetDAO.saveJbViajeDet( lstViajeDet );
                //}
            } else {
                viajeVacio = true;
            }
        } catch ( ApplicationException e ) {
            e.printStackTrace();
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return viajeVacio;
    }


    private List<Jb> getListJbByTipo( final List<String> tipos ) {
        List<Jb> trabajos = new ArrayList<Jb>();
        try {
            for ( String tipo : tipos ) {
                List<Jb> trabajosTmp = trabajoDAO.findByJbTipo( tipo );
                log.debug( trabajosTmp.size() );
                trabajos.addAll( ApplicationUtils.compruebaLista( trabajosTmp ) );
            }
        } catch ( ApplicationException e ) {
            log.error( "Error al Obtener Trabajos por tipos: " + e.getMessage() );
        }
        return trabajos;
    }

    private JbTrack setValuesJbTrack( final String rx, final String idViaje, final Empleado empleado, final String estado ) {
        JbTrack jbTrack = new JbTrack();
        try {
            jbTrack.setRx( rx );
            jbTrack.setIdViaje( idViaje );
            jbTrack.setEmpleado( empleado.getIdEmpleado() );
            jbTrack.setEstado( estado );
            jbTrack.setFecha( new Timestamp( System.currentTimeMillis() ) );
            jbTrack.setIdMod( "0" );
            if ( CollectionUtils.isNotEmpty( sobreDAO.findAllSobresByRx( rx ) ) ) {
                jbTrack.setObservaciones( Constants.OBS_VIAJE + " " + idViaje + " " + Constants.OBS_RX_SOBRE );
            } else {
                jbTrack.setObservaciones( Constants.OBS_VIAJE + " " + idViaje );
            }
        } catch ( ApplicationException ex ) {
            log.error( "Error al guardar Track: " + ex.getMessage() );
        }
        return jbTrack;
    }

    @Override
    public List<JbDev> obtenerJbDevsEnviosPaginado( final Integer primerResultado, final Integer numeroResultados ) throws ApplicationException {
        return jbDevDAO.obtenerJbDevsEnviosPaginado( primerResultado, numeroResultados );
    }

    @Override
    public Integer contarJbDevsEnvios() throws ApplicationException {
        return jbDevDAO.contarJbDevsEnvios();
    }

    @Override
    public void imprimirTicketsDevolucionSp( final Integer idJbDev, final Integer idSucursal ) throws ApplicationException {
        ticketService.imprimeTicketSucursalDevolucionSp( idJbDev );
        ticketService.imprimeTicketSobreDevolucionSp( idJbDev, idSucursal );
    }


    @Override
    public String imprimirPackingCerrado( String folioViaje, String fecha ) throws ApplicationException {
        String printOk = null;
        Empleado emp = null;
        Sucursal sucursal = null;
        SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );
        Date date = null;
        JbViaje viaje = null;
        Integer idViaje;
        try {
            Date fech = df.parse( fecha );
            date = org.apache.commons.lang.time.DateUtils.truncate( fech, Calendar.DAY_OF_MONTH );
            viaje = viajeDAO.findFolioViaje( folioViaje, date );
            emp = empleadoDAO.obtenEmpleado( viaje.getEmp() );
            sucursal = emp.getSucursal();
        } catch ( ParseException pe ) {
            log.debug( pe );
        }
        if ( date != null && viaje != null ) {
            idViaje = Integer.parseInt( viaje.getId().getIdViaje().trim() );
            trabajoImpresion.imprimirPackingCerradoHist( viaje.getId().getFecha(),
                    idViaje,
                    viaje.getFolio(),
                    emp,
                    sucursal,
                    jbViajeDetDAO.buscarPorEnviarLaboratorio( folioViaje, date ),
                    jbViajeDetDAO.buscarRotosExternos( folioViaje, date ),
                    jbViajeDetDAO.buscarRefRepSP( folioViaje, date ),
                    jbViajeDetDAO.buscarGar( folioViaje, date ),
                    jbViajeDetDAO.buscarOS( folioViaje, date ),
                    jbViajeDetDAO.buscarExternos( folioViaje, date ),
                    sobreDAO.findAllSobresVaciosByViajeAndFechaEnvio( folioViaje, date ),
                    sobreDAO.findAllSobresNoVaciosByViajeAndFechaEnvio( folioViaje, date ),
                    trabajoDAO.findJbDevByViajeAndFechaEnvio( folioViaje, date ),
                    trabajoDAO.findAllDoctoInvByTipoAndEstadoAndFecha( Constants.DEVOLUCIONES_TIPO_DA, Constants.DEVOLUCIONES_ESTADO_ENVIADO, date ) );
            printOk = "ok";
        }
        return printOk;
    }


    public JbViaje obtenerUltimoPackingCerrado( ) {
        JbViaje lastPacking;

        lastPacking = viajeDAO.findUltimoViaje();
        return lastPacking;
    }

}

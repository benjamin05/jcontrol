package mx.com.lux.control.trabajos.bussiness.service.impl;

import mx.com.lux.control.trabajos.bussiness.EntregaExternoBusiness;
import mx.com.lux.control.trabajos.bussiness.EnvioExternoBusiness;
import mx.com.lux.control.trabajos.bussiness.service.ExternoService;
import mx.com.lux.control.trabajos.bussiness.service.TicketService;
import mx.com.lux.control.trabajos.data.dao.AcuseDAO;
import mx.com.lux.control.trabajos.data.dao.BancoDAO;
import mx.com.lux.control.trabajos.data.dao.EmpleadoDAO;
import mx.com.lux.control.trabajos.data.dao.ExternoDAO;
import mx.com.lux.control.trabajos.data.dao.NotaVentaDAO;
import mx.com.lux.control.trabajos.data.dao.PagoExtraDAO;
import mx.com.lux.control.trabajos.data.dao.PlanDAO;
import mx.com.lux.control.trabajos.data.dao.ReimpresionDAO;
import mx.com.lux.control.trabajos.data.dao.TerminalDAO;
import mx.com.lux.control.trabajos.data.dao.TipoPagoDAO;
import mx.com.lux.control.trabajos.data.dao.envio.SucursalDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.JbTrackDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO;
import mx.com.lux.control.trabajos.data.vo.Acuses;
import mx.com.lux.control.trabajos.data.vo.Banco;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbExterno;
import mx.com.lux.control.trabajos.data.vo.JbLlamada;
import mx.com.lux.control.trabajos.data.vo.JbPagoExtra;
import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.data.vo.NotaReimpresion;
import mx.com.lux.control.trabajos.data.vo.NotaVenta;
import mx.com.lux.control.trabajos.data.vo.Plan;
import mx.com.lux.control.trabajos.data.vo.Polizas;
import mx.com.lux.control.trabajos.data.vo.Reimpresion;
import mx.com.lux.control.trabajos.data.vo.Sucursal;
import mx.com.lux.control.trabajos.data.vo.Terminal;
import mx.com.lux.control.trabajos.data.vo.TipoPago;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.exception.ServiceException;
import mx.com.lux.control.trabajos.util.constants.AcusesConstants;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service( "externoService" )
public class ExternoServiceImpl implements ExternoService {

    private final Logger log = LoggerFactory.getLogger( ExternoServiceImpl.class );

    @Resource
    private TrabajoDAO trabajoDAO;

    @Resource
    private NotaVentaDAO notaVentaDAO;

    @Resource
    private EmpleadoDAO empleadoDAO;

    @Resource
    private SucursalDAO sucursalDAO;

    @Resource
    private ReimpresionDAO reimpresionDAO;

    @Resource
    private TipoPagoDAO tipoPagoDAO;

    @Resource
    private BancoDAO bancoDAO;

    @Resource
    private ExternoDAO externoDAO;

    @Resource
    private TerminalDAO terminalDAO;

    @Resource
    private PlanDAO planDAO;

    @Resource
    private PagoExtraDAO pagoExtraDAO;

    @Resource
    private JbTrackDAO trackDAO;

    @Resource
    private AcuseDAO acuseDAO;

    @Resource
    private EntregaExternoBusiness entregaExternoBusiness;

    @Resource
    private EnvioExternoBusiness envioExternoBusiness;

    @Resource
    private TicketService ticketService;

    @Override
    @Transactional
    public void enviarExterno( String rx, Integer idSucursalOrigen, Integer idSucursalDestino, String sobre, String observaciones, String idEmpleado ) throws ApplicationException {
        log.info( "inicio enviar externo" );
        if ( StringUtils.isNotBlank( rx ) && StringUtils.isNotBlank( sobre ) && StringUtils.isNotBlank( idEmpleado ) && idSucursalOrigen > 0 && idSucursalDestino > 0 ) {
            Object[] logData = new Object[]{ rx, idSucursalOrigen, idSucursalDestino, sobre, observaciones,
                    idEmpleado };
            log.debug( "recibe rx: {}, origen: {}, destino: {}, sobre: {}, obs: {}, empleado: {}", logData );
            Jb trabajo = trabajoDAO.findById( rx );
            trabajo.setCaja( sobre );
            trabajo.setObsExt( observaciones );
            trabajo.setExterno( String.valueOf( idSucursalDestino ) );
            EstadoTrabajo ultimoEstado = trabajo.estado();
            if ( !EstadoTrabajo.EN_PINO.equals( ultimoEstado ) ) {
                trabajo.estado( EstadoTrabajo.POR_ENVIAR_EXTERNO );
            }
            trabajoDAO.save( trabajo );

            Sucursal sucursal = sucursalDAO.findById( idSucursalDestino );
            JbTrack track = new JbTrack();
            track.setRx( rx );
            track.setEstado( EstadoTrabajo.POR_ENVIAR_EXTERNO.codigo() );
            track.setEmpleado( idEmpleado );
            track.setObservaciones( sucursal.getNombre() );
            track.setFecha( new Timestamp( System.currentTimeMillis() ) );
            track.setIdMod( "0" );
            trackDAO.save( track );

            Acuses acuse = new Acuses();
            acuse.setIdTipo( AcusesConstants.EXTERNO_ENVIO );
            acuse.setFechaCarga( new Date() );
            acuse.setContenido( envioExternoBusiness.generaContenidoAcuseEnvio( trabajo, idSucursalOrigen ) );
            acuseDAO.save( acuse );

            NotaVenta notaVenta = notaVentaDAO.obtenNotaVentaPorTrabajo( rx );
            if( notaVenta != null ){
                notaVenta.setSucDest( trabajo.getExterno() );
                notaVentaDAO.guarda( notaVenta );
            }

            envioExternoBusiness.generaArchivoEnvio( trabajo, idSucursalOrigen );
            envioExternoBusiness.imprimeTicketEnvio( trabajo, idSucursalOrigen, idEmpleado );

            if ( EstadoTrabajo.POR_ENVIAR.equals( ultimoEstado ) ) {
                Acuses acuseRx = new Acuses();
                acuseRx.setIdTipo( AcusesConstants.RX );
                acuseRx.setFechaCarga( new Date() );
                acuseRx.setContenido( envioExternoBusiness.generaContenidoRecetaAcuseEnvio( trabajo, notaVenta ) );
                acuseDAO.save( acuseRx );
                Reimpresion reimpresion = new Reimpresion();
                reimpresion.setNota( NotaReimpresion.RX.nota );
                reimpresion.setIdNota( notaVenta != null ? notaVenta.getIdFactura() : "" );
                reimpresion.setEmpleado( idEmpleado );
                reimpresion.setFecha( new Date() );
                reimpresionDAO.guarda( reimpresion );
                envioExternoBusiness.generaArchivoRecetaEnvio( trabajo, notaVenta );
                envioExternoBusiness.imprimeTicketRecetaEnvio( trabajo, notaVenta, idSucursalOrigen, idEmpleado );
            } else {
                JbLlamada llamada = trabajoDAO.findJbLlamadaById( rx );
                if ( llamada != null ) {
                    trabajoDAO.delete( llamada );
                }
            }
        }
        log.info( "fin enviar externo" );
    }

    @Override
    @Transactional
    public void eliminarExterno( String rx ) throws ApplicationException {
        log.info( "inicio eliminar externo" );
        if ( StringUtils.isNotBlank( rx ) ) {
            log.debug( "rx externo: {}", rx );
            EstadoTrabajo ultimoEstadoValido = trabajoDAO.obtenUltimoEstadoValidoEnTrack( rx, EstadoTrabajo.POR_ENVIAR_EXTERNO );
            log.debug( "ultimo estado valido: {}", ultimoEstadoValido );
            Jb trabajo = trabajoDAO.findById( rx );
            log.debug( "estado actual: {}", trabajo.estado() );
            trabajo.estado( ultimoEstadoValido );
            trabajoDAO.save( trabajo );
            log.info( "nuevo estado: {}", trabajo.estado() );
        }
        log.info( "fin eliminar externo" );
    }

    @Override
    public void entregarExterno( final String rx, final String idEmpleadoAtendio ) throws ApplicationException {
        entregaExternoBusiness.actualizarEstadoTrabajo( rx, EstadoTrabajo.ENTREGADO );
        entregaExternoBusiness.insertarTrack( rx, EstadoTrabajo.ENTREGADO );
        entregaExternoBusiness.actualizarFechaEntrega( rx );
        entregaExternoBusiness.insertarAcuses( rx, EstadoTrabajo.ENTREGADO );
        entregaExternoBusiness.crearArchivoPagoExternos( rx );
        entregaExternoBusiness.crearArchivoEntregaExternos( rx );
        entregaExternoBusiness.eliminarJbLlamada( rx );
        entregaExternoBusiness.actualizarPagosExterno( rx, idEmpleadoAtendio );
        ticketService.imprimeTicketEntregaExterno( rx );
        procesarPolizas( rx );
    }

    @Override
    public void entregarExternoConSaldo( final String rx, final String idEmpleadoAtendio ) throws ApplicationException {
        entregaExternoBusiness.actualizarEstadoTrabajo( rx, EstadoTrabajo.ENTREGADO );
        entregaExternoBusiness.insertarTrack( rx, EstadoTrabajo.ENTREGADO );
        entregaExternoBusiness.actualizarFechaEntrega( rx );
        entregaExternoBusiness.insertarAcusesConSaldo( rx, EstadoTrabajo.ENTREGADO );
        entregaExternoBusiness.crearArchivoPagoExternosConSaldo( rx );
        entregaExternoBusiness.crearArchivoEntregaExternos( rx );
        entregaExternoBusiness.eliminarJbLlamada( rx );
        entregaExternoBusiness.actualizarPagosExterno( rx, idEmpleadoAtendio );
        ticketService.imprimeTicketEntregaExterno( rx );
        procesarPolizas( rx );
    }

    private void procesarPolizas( final String rx ) throws ServiceException {
        List<Polizas> polizas = entregaExternoBusiness.obtenerPolizas( rx );
        if ( CollectionUtils.size( polizas ) > 0 ) {
            for ( Polizas poliza : polizas ) {
                entregaExternoBusiness.procesarPoliza( Integer.toString( poliza.getIdPoliza() ) );
            }
        }
    }

    @Override
    public List<TipoPago> obtenerTiposPago() {
        try {
            return ( List<TipoPago> ) tipoPagoDAO.findAll();
        } catch ( Exception e ) {
            log.error( "Error al obtener tipos de pago", e );
        }
        return new ArrayList<TipoPago>();
    }

    @Override
    public TipoPago obtenerTipoPagoPorId( String id ) {
        return tipoPagoDAO.obtenerPorId( id );
    }

    @Override
    public List<Banco> obtenerBancos() {
        try {
            return ( List<Banco> ) bancoDAO.findAll();
        } catch ( Exception e ) {
            log.error( "Error al obtener bancos", e );
        }
        return new ArrayList<Banco>();
    }

    @Override
    public Banco obtenerBancoPorId( final String id ) {
        return bancoDAO.obtenerPorId( id );
    }

    @Override
    public List<Terminal> obtenerTerminales() {
        try {
            return ( List<Terminal> ) terminalDAO.findAll();
        } catch ( Exception e ) {
            log.error( "Error al obtener terminales", e );
        }
        return new ArrayList<Terminal>();
    }

    @Override
    public Terminal obtenerTerminalPorId( String id ) {
        try {
            if ( StringUtils.isNotBlank( id ) ) {
                List<Terminal> terminales = ( List<Terminal> ) terminalDAO.findAll();
                for ( Terminal terminal : terminales ) {
                    if ( id.trim().equalsIgnoreCase( terminal.getIdTerminal().trim() ) ) {
                        return terminal;
                    }
                }
            }
        } catch ( Exception e ) {
            log.error( "Error al obtener terminales", e );
        }
        return null;
    }

    @Override
    public List<Plan> obtenerPlanes() {
        try {
            return ( List<Plan> ) planDAO.findAll();
        } catch ( Exception e ) {
            log.error( "Error al obtener planes", e );
        }
        return new ArrayList<Plan>();
    }

    @Override
    public JbPagoExtra guardarPagoExtra( JbPagoExtra pagoExtra ) throws ApplicationException {
        try {
            pagoExtraDAO.save( pagoExtra );
        } catch ( Exception e ) {
            throw new ServiceException( "Error al guardar pago Extra: " + e.getMessage(), e );
        }
        return pagoExtra;
    }

    @Override
    public JbPagoExtra actualizarPagoExtra( Integer id, JbPagoExtra pagoExtra ) throws ApplicationException {
        try {
            pagoExtraDAO.eliminarPorId( id );
            pagoExtraDAO.save( pagoExtra );
        } catch ( Exception e ) {
            throw new ServiceException( "Error al actualizar pago Extra: " + e.getMessage(), e );
        }
        return pagoExtra;
    }

    @Override
    public List<JbPagoExtra> obtenerPagosExtraPorRxPaginado( final String rx, final int primerResultado, final int numeroResultados ) {
        return pagoExtraDAO.buscarPorRxPaginado( rx, primerResultado, numeroResultados );
    }

    @Override
    public List<JbPagoExtra> obtenerPagosExtraPorRx( final String rx ) {
        return pagoExtraDAO.buscarPorRx( rx );
    }

    @Override
    public Integer contarPagosExtraPorRx( final String rx ) {
        return pagoExtraDAO.buscarPorRx( rx ).size();
    }

    @Override
    public void eliminarPagoExtraPorId( final Integer id ) {
        pagoExtraDAO.eliminarPorId( id );
    }

    @Override
    public void eliminarPagosExtNoConfirmados( final String rx ) throws ApplicationException {
        List<JbPagoExtra> pagosExtra = pagoExtraDAO.buscarPorRx( rx );
        for ( JbPagoExtra pagoExtra : pagosExtra ) {
            if ( !pagoExtra.isConfirm() ) {
                try {
                    pagoExtraDAO.delete( pagoExtra );
                } catch ( DAOException e ) {
                    throw new ServiceException( "Error al borrar un Pago Extra no Confirmado", e );
                }
            }
        }
    }

    @Override
    public JbExterno obtenerExternoPorRx( final String rx ) {
		try {
			return externoDAO.obtenerPorRx( rx );
		} catch( Exception e ) {
			log.error( "Error al obtener Externo por RX: " + rx );
		}
        return null;
    }

    @Override
    public void regresarExternoSucursalOrigen( final String rx ) throws ApplicationException {
        Jb trabajo = trabajoDAO.findById( rx );
        trabajo.estado( EstadoTrabajo.ENTREGADO );
        trabajoDAO.save( trabajo );

        JbTrack track = new JbTrack();
        track.setRx( rx );
        track.setEstado( EstadoTrabajo.ENTREGADO.codigo() );
        track.setObservaciones( "Se regresó a Sucursal Origen" );
        track.setEmpleado( "EXT" );
        track.setFecha( new Date() );
        trackDAO.saveOrUpdate( track );

        entregaExternoBusiness.eliminarJbLlamada( rx );
    }
}

package mx.com.lux.control.trabajos.bussiness.service.contacto;

import mx.com.lux.control.trabajos.bussiness.CorreoElectronicoBusiness;
import mx.com.lux.control.trabajos.data.dao.contacto.ContactoViewDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.GParametroDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.JbGrupoDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.exception.ServiceException;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.RxConstants;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service( "contactoViewService" )
public class ContactoViewServiceImpl implements ContactoViewService {

    private final Logger log = LoggerFactory.getLogger( ContactoViewService.class );

    private static final String SHELL_CMD = TrabajosPropertyHelper.getProperty( "trabajos.msg.sms.cmd" );

    @Resource
    private JbGrupoDAO jbGrupoDAO;

    @Resource
    private ContactoViewDAO contactoViewDAO;

    @Resource
    private TrabajoDAO trabajoDAO;

    @Resource
    private GParametroDAO gParametroDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private CorreoElectronicoBusiness correoElectronicoBusiness;

    @Override
    public List<ContactoView> findAllLlamadasViewByFilters( String atendio, int firstResult, int resultSize ) throws ApplicationException {
        return contactoViewDAO.findAllLlamadasViewByFilters( atendio, firstResult, resultSize );
    }

    @Override
    public int countAllLlamadasViewByFilters( String atendio ) throws ApplicationException {
        return contactoViewDAO.countAllLlamadasViewByFilters( atendio );
    }

    @Override
    public List<ContactoView> findAllLlamadasView( int firstResult, int resultSize ) throws ApplicationException {
        return contactoViewDAO.findAllLlamadasView( firstResult, resultSize );
    }

    @Override
    public int countEstado( String estado, String atendio ) throws ApplicationException {
        return contactoViewDAO.countAllContactosByEstadoByEmpleadoNumero( estado, atendio );
    }

    @Override
    public int countTipo( String tipo, String atendio ) throws ApplicationException {
        return contactoViewDAO.countAllContactosByTipoByEmpleadoNumero( tipo, atendio );
    }

    @Override
    public List<Jb> findAllGrupos( int firstResult, int resultSize, String nombreGpo, String rx ) throws ApplicationException {
        return contactoViewDAO.findAllGrupos( firstResult, resultSize, nombreGpo, rx );
    }

    @Override
    public int countAllGrupos( String nombreGpo, String rx ) throws ApplicationException {
        return contactoViewDAO.countAllGrupos( nombreGpo, rx );
    }

    @Override
    public List<Jb> findJbByGrupo( int firstResult, int resultSize, String idGrupo ) throws ApplicationException {
        return contactoViewDAO.findJbByGrupo( firstResult, resultSize, idGrupo );
    }

    @Override
    public int countJbByGrupo( String idGrupo ) throws ApplicationException {
        return contactoViewDAO.countJbByGrupo( idGrupo );
    }

    @Override
    public String getLastIdGroup( String rx ) throws ApplicationException {
        int idGroup = Integer.valueOf( contactoViewDAO.getLastIdGroup( rx ) );
        idGroup++;
        String idGpo = RxConstants.TIPO_GRUPO_F + idGroup;
        return idGpo;
    }

    @Override
    public void actualizarGrupo( String idGrupo ) throws ApplicationException {
        int tamanio = countJbByGrupo( idGrupo );
        Jb grupo = trabajoDAO.findById( idGrupo );
        List<Jb> jbs = findJbByGrupo( 0, tamanio, idGrupo );

        Date fechaMaxima = !jbs.isEmpty() ? jbs.get( 0 ).getFechaPromesa() : null;
        if ( fechaMaxima != null ) {
            for ( Jb jb : jbs ) {
                if ( fechaMaxima.before( jb.getFechaPromesa() ) )
                    fechaMaxima = jb.getFechaPromesa();
            }
        }
        grupo.setFechaPromesa( fechaMaxima );
        // TODO para que se hace esto de sumar los saldos si no se guarda en ningun lado ???
        BigDecimal saldo = BigDecimal.ZERO;
        for ( Jb tmp : jbs ) {
            saldo = saldo.add( tmp.getSaldo() );
        }
        trabajoDAO.save( grupo );
    }

    @Override
    public void actualizarFechaPromesa( String idGrupo ) throws ApplicationException {
        int tamanio = countJbByGrupo( idGrupo );
        Jb grupo = trabajoDAO.findById( idGrupo );
        List<Jb> jbs = findJbByGrupo( 0, tamanio, idGrupo );
        Date fechaMaxima = !jbs.isEmpty() ? jbs.get( 0 ).getFechaPromesa() : null;
        if ( fechaMaxima != null ) {
            for ( Jb jb : jbs ) {
                if ( fechaMaxima.before( jb.getFechaPromesa() ) )
                    fechaMaxima = jb.getFechaPromesa();
            }
        }
        grupo.setFechaPromesa( fechaMaxima );
        trabajoDAO.save( grupo );
    }

    @Override
    public FormaContacto obtenFormaContactoDeRx( String rx ) {
        try {
            return contactoViewDAO.obtenFormaContacto( rx );
        } catch ( DAOException e ) {
            return null;
        }
    }

    @Override
    public void registraContactoSMS( final String rx, Boolean recepcion ) throws ApplicationException {
        try {
            Acuses acuse = new Acuses();
            acuse.setIdTipo( "sms" );
            acuse.setFechaCarga( new Date() );
            acuse.setContenido( crearContenidoAcuseSms( rx, recepcion ) );
            contactoViewDAO.save( acuse );
            //Runtime.getRuntime().exec( SHELL_CMD );
        } catch ( DAOException e ) {
            throw new ServiceException( "Error al registrar contacto SMS", e );
        } //catch ( IOException e ) {
            //throw new ServiceException( "Error al registrar contacto SMS", e );
        //}
    }

    private String crearContenidoAcuseSms( final String rx, Boolean recepcion ) throws DAOException {

        String grupo = null;
        String Rx = rx;

        if ( rx.startsWith("F") ) {

            List<Jb> listaTrabajosEnGrupo = new ArrayList<Jb>();
            listaTrabajosEnGrupo = jbGrupoDAO.listaTrabajosEnGrupo( StringUtils.trimToEmpty( rx ) );

            for ( Jb jb : listaTrabajosEnGrupo ) {
                FormaContacto fc = obtenFormaContactoDeRx( jb.getRx() );
                if ( fc.getTipoContacto().getIdTipoContacto() == 3 ) { // Si forma contacto SMS
                    Rx = jb.getRx().toString();
                }
            }
        }




        Empleado empleado = ( Empleado ) Session.getAttribute( Constants.PARAM_USER_LOGGED );
        FormaContacto formaContacto = obtenFormaContactoDeRx( Rx );
        Jb trabajo = trabajoDAO.findById( Rx );
        JbLlamada llamada = trabajoDAO.findJbLlamadaById( rx );
        if(recepcion ){
          llamada = new JbLlamada();
          llamada.setTipo("ENTREGAR");
        }

        GParametro gParametro = null;
        if ( "ENTREGAR".equalsIgnoreCase( llamada.getTipo().trim() ) && !trabajo.esGrupo() ) {
            gParametro = gParametroDAO.findById( "sms_entregar" );
        } else if ( "RETRASADO".equalsIgnoreCase( llamada.getTipo().trim() ) && !trabajo.esGrupo() ) {
            gParametro = gParametroDAO.findById( "sms_retrasado" );
        } else if ( "ENTREGAR".equalsIgnoreCase( llamada.getTipo().trim() ) && trabajo.esGrupo() ) {
            gParametro = gParametroDAO.findById( "sms_grupo_entregar" );
        } else if ( "RETRASADO".equalsIgnoreCase( llamada.getTipo().trim() ) && trabajo.esGrupo() ) {
            gParametro = gParametroDAO.findById( "sms_grupo_retrasado" );
        }
        String textoIncompleto = gParametro != null ? gParametro.getValor() : "";
        String textoCompleto = reemplazarVariables( textoIncompleto, rx );

        Map<String, Object> datos = new HashMap<String, Object>();
        datos.put( "telefono", StringUtils.deleteWhitespace( formaContacto.getContacto() ) );
        datos.put( "idSucursalOrigen", empleado.getSucursal().getIdSucursal() );
        datos.put( "nombre", trabajo.getCliente() );
        datos.put( "rx", rx );
        datos.put( "texto", textoCompleto );
        String acuse = VelocityEngineUtils.mergeTemplateIntoString( velocityEngine, "acuse-contacto-sms.vm", "UTF-8", datos );
        log.debug( "Contenido acuse: " + acuse );
        return acuse;
    }

    private String reemplazarVariables( final String texto, final String rx ) throws DAOException {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );
        Empleado empleado = ( Empleado ) Session.getAttribute( Constants.PARAM_USER_LOGGED );
        Sucursal sucursal = empleado.getSucursal();
        Jb trabajo = trabajoDAO.findById( rx );
        String tmp = texto;
        tmp = StringUtils.replace( tmp, "{rx}", trabajo.getRx() );
        tmp = StringUtils.replace( tmp, "{id_suc}", Integer.toString( sucursal.getIdSucursal() ) );
        tmp = StringUtils.replace( tmp, "{id_sucursal}", String.format( "%04d", sucursal.getIdSucursal() ) );
        tmp = StringUtils.replace( tmp, "{sucursal}", sucursal.getNombre() );
        tmp = StringUtils.replace( tmp, "{cliente}", trabajo.getCliente() );
        tmp = StringUtils.replace( tmp, "{estado}", trabajo.estado().codigo() );
        tmp = StringUtils.replace( tmp, "{id_viaje}", trabajo.getIdViaje() );
        tmp = StringUtils.replace( tmp, "{caja}", trabajo.getCaja() );
        tmp = StringUtils.replace( tmp, "{id_cliente}", trabajo.getIdCliente() );
        tmp = StringUtils.replace( tmp, "{roto}", trabajo.getRoto() != null ? trabajo.getRoto().toString() : "" );
        tmp = StringUtils.replace( tmp, "{emp_atendio}", trabajo.getEmpAtendio() );
        tmp = StringUtils.replace( tmp, "{num_llamada}", trabajo.getNumLlamada() != null ? trabajo.getNumLlamada().toString() : "" );
        tmp = StringUtils.replace( tmp, "{material}", trabajo.getMaterial() );
        tmp = StringUtils.replace( tmp, "{surte}", trabajo.getSurte() );
        tmp = StringUtils.replace( tmp, "{saldo}", trabajo.getSaldoFormateado().replace( "$", "" ) );
        tmp = StringUtils.replace( tmp, "{jb_tipo}", trabajo.getJbTipo() );
        tmp = StringUtils.replace( tmp, "{volver_llamar}", trabajo.getVolverLlamar() != null ? sdf.format( trabajo.getVolverLlamar() ) : "" );
        tmp = StringUtils.replace( tmp, "{fecha_promesa}", trabajo.getFechaPromesa() != null ? sdf.format( trabajo.getFechaPromesa() ) : "" );
        tmp = StringUtils.replace( tmp, "{fecha_mod}", trabajo.getFechaMod() != null ? sdf.format( trabajo.getFechaMod() ) : "" );
        tmp = StringUtils.replace( tmp, "{obs_ext}", trabajo.getObsExt() );
        tmp = StringUtils.replace( tmp, "{ret_auto}", trabajo.getRetAuto() );
        tmp = StringUtils.replace( tmp, "{no_llamar}", trabajo.getNoLlamar() != null ? trabajo.getNoLlamar().toString() : "" );
        tmp = StringUtils.replace( tmp, "{tipo_venta}", trabajo.getTipoVenta() );
        tmp = StringUtils.replace( tmp, "{fecha_venta}", trabajo.getFechaVenta() != null ? sdf.format( trabajo.getFechaVenta() ) : "" );
        tmp = StringUtils.replace( tmp, "{id_grupo}", trabajo.getIdGrupo() );
        tmp = StringUtils.replace( tmp, "{no_enviar}", "" ); // TODO ?
        tmp = StringUtils.replace( tmp, "{externo}", trabajo.getExterno() );
        return tmp;
    }

    @Override
    public void enviarNotificacionCorreoElectronico( final String rx ) throws ApplicationException {
        correoElectronicoBusiness.enviarCorreoContacto( rx );
    }

}

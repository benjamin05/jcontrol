package mx.com.lux.control.trabajos.bussiness.impl;

import mx.com.lux.control.trabajos.bussiness.EnvioExternoBusiness;
import mx.com.lux.control.trabajos.bussiness.service.TicketService;
import mx.com.lux.control.trabajos.data.dao.*;
import mx.com.lux.control.trabajos.data.dao.envio.SucursalDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.ClienteDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.RecetaDAO;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.exception.ServiceException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.DateUtils;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component( "envioExternoBusiness" )
public class EnvioExternoBusinessImpl implements EnvioExternoBusiness {

    private final Logger log = LoggerFactory.getLogger( EnvioExternoBusiness.class );

    @Resource
    private NotaVentaDAO notaVentaDAO;

    @Resource
    private ClienteDAO clienteDAO;

    @Resource
    private ExamenDAO examenDAO;

    @Resource
    private RecetaDAO recetaDAO;

    @Resource
    private ArticuloDAO articuloDAO;

    @Resource
    private PolizaDAO polizaDAO;

    @Resource
    private SucursalDAO sucursalDAO;

    @Resource
    private ReimpresionDAO reimpresionDAO;

    @Resource
    private EmpleadoDAO empleadoDAO;

    @Resource
    private FormaContactoDAO formaContactoDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private TicketService ticketService;

    @Override
    public String generaContenidoAcuseEnvio( Jb trabajo, Integer idSucursalOrigen ) throws ServiceException {
        log.info( "generando contenido del acuse de envio de externo" );
        if ( trabajo != null && idSucursalOrigen > 0 ) {
            try {
                return generaCadenaEnvio( trabajo, idSucursalOrigen, "acuse-envio-externo.vm" );
            } catch ( Exception e ) {
                throw new ServiceException( "Error al generar contenido del acuse de envio externo", e );
            }
        }
        return null;
    }

    @Override
    public void generaArchivoEnvio( Jb trabajo, Integer idSucursalOrigen ) throws ServiceException {
        log.info( "generando archivo de envio de externo" );
        if ( trabajo != null && idSucursalOrigen > 0 ) {
            StringBuilder nombre = new StringBuilder( TrabajosPropertyHelper.getProperty( "trabajos.ruta.externo.envio" ) );
            nombre.append( "/" );
            nombre.append( String.format( "%04d", Integer.valueOf( trabajo.getExterno() ) ) );
            nombre.append( "-" );
            nombre.append( trabajo.getRx() );
            nombre.append( "-X2.EXT" );
            try {
                File archivo = new File( nombre.toString() );
                BufferedWriter out = new BufferedWriter( new FileWriter( archivo ) );
                String contenido = generaCadenaEnvio( trabajo, idSucursalOrigen, "archivo-envio-externo.vm" );
                out.write( contenido );
                out.close();
            } catch ( Exception e ) {
                throw new ServiceException( "Error al generar archivo de envio externo", e );
            }
        }
    }

    private String generaCadenaEnvio( Jb trabajo, Integer idSucursalOrigen, String plantilla ) throws DAOException {
        log.info( "generando cadena para envio de externo" );
        if ( trabajo != null && idSucursalOrigen > 0 && StringUtils.isNotBlank( plantilla ) ) {
            NotaVenta notaVenta = notaVentaDAO.obtenNotaVentaPorTrabajo( trabajo.getRx() );
            Cliente cliente = clienteDAO.obtenCliente( Integer.valueOf( trabajo.getIdCliente() ) );
            FormaContacto formaContacto = formaContactoDAO.obtenPorRx( trabajo.getRx() );
            Examen examen = examenDAO.obtenExamenPorTrabajo( trabajo.getRx() );
            Receta receta = recetaDAO.obtenerRecetaPorTrabajo( trabajo.getRx() );
            Articulos articuloA = articuloDAO.obtenArticuloGenericoAPorNotaVenta( notaVenta != null ? notaVenta.getIdFactura() : "");
            Articulos articuloB = articuloDAO.obtenArticuloGenericoBPorNotaVenta( notaVenta != null ? notaVenta.getIdFactura() : "");
            Polizas poliza = polizaDAO.obtenPolizaPorFactura( trabajo.getRx() );
            Map<String, Object> items = new HashMap<String, Object>();
            items.put( "dateFormat", new SimpleDateFormat( "dd-MM-yyyy" ) );
            items.put( "trabajo", trabajo );
            items.put( "idSucursalOrigen", String.format( "%04d", idSucursalOrigen ) );
            items.put( "cliente", cliente );
            items.put( "examen", examen );
            items.put( "receta", receta );
            items.put( "uso", receta != null ? receta.getUsoAnteojos().nombre : null );
            items.put( "notaVenta", notaVenta );
            items.put( "articuloA", articuloA );
            items.put( "articuloB", articuloB );
            items.put( "poliza", poliza );
            if ( poliza != null && !poliza.getDetalles().isEmpty() ) {
                items.put( "polizaDetalles", poliza.getDetalles() );
                items.put( "cantDetalles", poliza.getDetalles().size() );
            }
            items.put( "formaContacto", formaContacto );
            if ( formaContacto != null ) {
                items.put( "formaContactoObservaciones", StringUtils.replace( formaContacto.getObservaciones(), "\n", "~" ) );
            }
            return VelocityEngineUtils.mergeTemplateIntoString( velocityEngine, plantilla, "UTF-8", items );
        }
        return null;
    }

    @Override
    public void imprimeTicketEnvio( Jb trabajo, Integer idSucursalOrigen, String idEmpleado ) throws ServiceException {
        log.info( "generando ticket para envio de externo" );
        if ( trabajo != null && idSucursalOrigen > 0 && StringUtils.isNotBlank( idEmpleado ) ) {
            try {
                Sucursal sucursalOrigen = sucursalDAO.findById( idSucursalOrigen );
                Sucursal sucursalDestino = sucursalDAO.findById( Integer.valueOf( trabajo.getExterno() ) );
                Empleado usuario = empleadoDAO.obtenEmpleado( idEmpleado );
                Cliente cliente = clienteDAO.obtenCliente( Integer.valueOf( trabajo.getIdCliente() ) );
                Examen examen = examenDAO.obtenExamenPorTrabajo( trabajo.getRx() );
                Empleado optometrista = empleadoDAO.obtenEmpleado( examen != null ? String.valueOf( examen.getIdAtendio() ) : "" );
                Map<String, Object> elementos = new HashMap<String, Object>();
                elementos.put( "sobre", trabajo.getCaja() );
                elementos.put( "cliente", trabajo.getCliente() );
                elementos.put( "factura", trabajo.getRx() );
                elementos.put( "material", trabajo.getMaterial() );
                elementos.put( "saldo", trabajo.getSaldoFormateado() );
                elementos.put( "observaciones", trabajo.getObsExt() );
                elementos.put( "sucursal_origen", sucursalOrigen != null ? sucursalOrigen.getNombre() : null );
                elementos.put( "id_sucursal_origen", sucursalOrigen != null ? sucursalOrigen.getIdSucursal() : null );
                elementos.put( "sucursal_destino", sucursalDestino != null ? sucursalDestino.getNombre() : null );
                elementos.put( "id_sucursal_destino", sucursalDestino != null ? sucursalDestino.getIdSucursal() : null );
                elementos.put( "fecha", DateFormatUtils.format( new Date(), "dd-MM-yyyy" ) );
                elementos.put( "envio", usuario != null ? usuario.getNombreApellidos() : null );
                elementos.put( "optometrista", optometrista != null ? optometrista.getNombreApellidos() : null );
                if ( cliente != null ) {
                    elementos.put( "telefono_casa", cliente.getTelCasaCli() );
                    elementos.put( "telefono_trabajo", cliente.getTelTrabCli() );
                    elementos.put( "telefono_trabajo_ext", cliente.getExtTrabCli() );
                    elementos.put( "telefono_adicional", cliente.getTelAdiCli() );
                    elementos.put( "telefono_adicional_ext", cliente.getExtAdiCli() );
                }
                ticketService.imprimeEnvioExterno( elementos );
            } catch ( DAOException e ) {
                throw new ServiceException( "Error al generar ticket de envio externo", e );
            }
        }
    }

    @Override
    public String generaContenidoRecetaAcuseEnvio( Jb trabajo, NotaVenta notaVenta ) throws ServiceException {
        if ( trabajo != null && notaVenta != null ) {
            try {
                String nombre = generaNombreRecetaEnvio( trabajo, notaVenta );
                return generaCadenaRecetaEnvio( trabajo, notaVenta, "acuse-receta-envio-externo.vm", nombre );
            } catch ( DAOException e ) {
                throw new ServiceException( "Error al generar el contenido del acuse de receta de envio externo", e );
            }
        }
        return null;
    }

    @Override
    public void generaArchivoRecetaEnvio( Jb trabajo, NotaVenta notaVenta ) throws ServiceException {
        if ( trabajo != null && notaVenta != null ) {
            try {
                StringBuilder nombre = new StringBuilder();
                nombre.append( TrabajosPropertyHelper.getProperty( "trabajos.ruta.externo.envio" ) );
                nombre.append( "/" );
                nombre.append( generaNombreRecetaEnvio( trabajo, notaVenta ) );
                File archivo = new File( nombre.toString() );
                String contenido = generaCadenaRecetaEnvio( trabajo, notaVenta, "archivo-receta-envio-externo.vm", null );
                if ( StringUtils.isNotBlank( contenido ) ) {
                    BufferedWriter out = new BufferedWriter( new FileWriter( archivo ) );
                    out.write( contenido );
                    out.close();
                }
            } catch ( Exception e ) {
                throw new ServiceException( "Error al generarel contenido del archivo de receta de envio externo", e );
            }
        }
    }

    private String generaNombreRecetaEnvio( Jb trabajo, NotaVenta notaVenta ) throws DAOException {
        if ( trabajo != null && notaVenta != null ) {
            int impresiones = reimpresionDAO.obtenConteoReimpresiones( notaVenta.getIdFactura() );
            StringBuilder nombre = new StringBuilder();
            nombre.append( String.format( "%04d", Integer.valueOf( trabajo.getExterno() ) ) );
            nombre.append( notaVenta.getFactura() );
            nombre.append( impresiones > 0 ? impresiones : "" );
            nombre.append( "RX" );
            return nombre.toString();
        }
        return null;
    }

    private String generaCadenaRecetaEnvio( Jb trabajo, NotaVenta notaVenta, String plantilla, String nombre ) throws DAOException {
        if ( trabajo != null && notaVenta != null && StringUtils.isNotBlank( plantilla ) ) {
            Articulos articuloA = articuloDAO.obtenArticuloGenericoAPorNotaVenta( notaVenta.getIdFactura() );
            Articulos articuloB = articuloDAO.obtenArticuloGenericoBPorNotaVenta( notaVenta.getIdFactura() );
            Receta receta = recetaDAO.obtenerRecetaPorTrabajo( trabajo.getRx() );
            StringBuilder tratamientos = new StringBuilder();
            tratamientos.append( notaVenta.getUdf2() );
            if ( receta != null && StringUtils.isNotBlank( receta.getTratamientos() ) ) {
                tratamientos.append( ", " ).append( receta.getTratamientos() );
            }
            Map<String, Object> items = new HashMap<String, Object>();
            items.put( "sucursal", String.format( "%04d", Integer.valueOf( trabajo.getExterno() ) ) );
            items.put( "notaVenta", notaVenta );
            items.put( "articuloA", articuloA );
            items.put( "articuloB", articuloB );
            items.put( "receta", receta );
            items.put( "nombre", nombre );
            items.put( "adicion_d", obtenAdicion( notaVenta.getCantLente(), receta, false ) );
            items.put( "adicion_i", obtenAdicion( notaVenta.getCantLente(), receta, true ) );
            items.put( "prisma_dh", obtenPrisma( receta, false, false ) );
            items.put( "prisma_ih", obtenPrisma( receta, true, false ) );
            items.put( "prisma_dv", obtenPrisma( receta, false, true ) );
            items.put( "prisma_iv", obtenPrisma( receta, true, true ) );
            items.put( "cantidad_lente", notaVenta.getCantidadLente().tipo );
            items.put( "tratamientos", tratamientos.toString() );
            return VelocityEngineUtils.mergeTemplateIntoString( velocityEngine, plantilla, "UTF-8", items );
        }
        return null;
    }

    @Override
    public void imprimeTicketRecetaEnvio( Jb trabajo, NotaVenta notaVenta, Integer idSucursalOrigen, String idEmpleado ) throws ServiceException {
        if ( trabajo != null && notaVenta != null && idSucursalOrigen > 0 && StringUtils.isNotBlank( idEmpleado ) ) {
            try {
                Map<String, Object> elementos = new HashMap<String, Object>();
                elementos.put( "nombre_archivo", generaNombreRecetaEnvio( trabajo, notaVenta ) );
                elementos.put( "factura", notaVenta.getFactura() );
                elementos.put( "fecha_hora_factura", DateUtils.formatDate( notaVenta.getFechaHoraFactura(), "dd-MM-yyyy" ) );
                elementos.put( "fecha_prometida", DateUtils.formatDate( notaVenta.getFechaPrometida(), "dd-MM-yyyy" ) );
                elementos.put( "material", notaVenta.getUdf2() );
                elementos.put( "forma_lente", notaVenta.getUdf3() );
                elementos.put( "obs_factura", notaVenta.getObservacionesNv() );

                Sucursal sucursalOrigen = sucursalDAO.findById( idSucursalOrigen );
                elementos.put( "sucursal_origen", sucursalOrigen != null ? sucursalOrigen.getNombre() : null );
                elementos.put( "id_sucursal_origen", sucursalOrigen != null ? sucursalOrigen.getIdSucursal() : null );

                Sucursal sucursalDestino = sucursalDAO.findById( Integer.valueOf( trabajo.getExterno() ) );
                elementos.put( "sucursal_destino", sucursalDestino != null ? sucursalDestino.getNombre() : null );
                elementos.put( "id_sucursal_destino", sucursalDestino != null ? sucursalDestino.getIdSucursal() : null );

                Empleado usuario = empleadoDAO.obtenEmpleado( idEmpleado );
                elementos.put( "usuario", usuario != null ? usuario.getNombreApellidos() : null );

                String regresoClases = null;//REGRESO A CLASES
                Cliente cliente = clienteDAO.obtenCliente( Integer.valueOf( trabajo.getIdCliente() ) );
                if ( cliente != null ) {
                    elementos.put( "cliente", cliente.getNombreCompleto( true ) );
                    elementos.put( "telefono_casa", cliente.getTelCasaCli() );
                    elementos.put( "telefono_trabajo", cliente.getTelTrabCli() );
                    elementos.put( "telefono_trabajo_ext", cliente.getExtTrabCli() );
                    elementos.put( "telefono_adicional", cliente.getTelAdiCli() );
                    elementos.put( "telefono_adicional_ext", cliente.getExtAdiCli() );
                    regresoClases = cliente.getUdf1();
                    elementos.put( "regreso_clases", regresoClases );
                }

                Receta receta = recetaDAO.obtenerRecetaPorTrabajo( trabajo.getRx() );
                if ( receta != null ) {
                    elementos.put( "esf_d", receta.getOdEsfR() );
                    elementos.put( "cil_d", receta.getOdCilR() );
                    elementos.put( "eje_d", receta.getOdEjeR() );
                    elementos.put( "adc_d", obtenAdicion( notaVenta.getCantLente(), receta, false ) );
                    elementos.put( "pri_d", obtenPrisma( receta, false, false ) );
                    elementos.put( "esf_i", receta.getOiEsfR() );
                    elementos.put( "cil_i", receta.getOiCilR() );
                    elementos.put( "eje_i", receta.getOiEjeR() );
                    elementos.put( "adc_i", obtenAdicion( notaVenta.getCantLente(), receta, true ) );
                    elementos.put( "pri_i", obtenPrisma( receta, true, false ) );
                    elementos.put( "dil", receta.getDiLejosR() );
                    elementos.put( "dic", receta.getDiCercaR() );
                    elementos.put( "dmd", receta.getDiOd() );
                    elementos.put( "dmi", receta.getDiOi() );
                    elementos.put( "altura_oblea", receta.getAltOblR() );
                    elementos.put( "uso", receta.getUsoAnteojos().nombre );
                    elementos.put( "obs_receta", receta.getObservacionesR() );
                }

                Examen examen = examenDAO.obtenExamenPorTrabajo( trabajo.getRx() );
                Empleado optometrista = empleadoDAO.obtenEmpleado( examen != null ? String.valueOf( examen.getIdAtendio() ) : "" );
                elementos.put( "optometrista", optometrista != null ? optometrista.getNombreApellidos() : null );

                BigDecimal ventaNeta = notaVenta.getVentaNeta();
                BigDecimal sumaPagos = notaVenta.getSumaPagos();
                Double saldo = ventaNeta.subtract( sumaPagos ).doubleValue();
                elementos.put( "saldo", ApplicationUtils.doubleToMoney( saldo ) );
                elementos.put( "con_saldo", saldo > 0 ? "CON SALDO" : null );

                List<Articulos> listaArticulos = articuloDAO.obtenListaArticulosPorTrabajo( trabajo.getRx() );
                StringBuilder strb = new StringBuilder();
                int i = 0;
                for ( Articulos item : listaArticulos ) {
                    if ( "B".equalsIgnoreCase( String.valueOf( item.getIdGenerico() ) ) ) {
                        if ( ++i > 1 ) {
                            strb.append( ", " );
                        }
                        strb.append( item.getArticulo() ).append( "-" ).append( item.getDescArticulo() );
                    }
                }
                elementos.put( "articulos", strb.toString() );

                String armazon = null;
                elementos.put( "armazon", armazon );

                String tratamiento = null;
                elementos.put( "trat", tratamiento );

                String convenioNomina = null; //VENTA PINO
                elementos.put( "convenio_nomina", convenioNomina );

                ticketService.imprimeRecetaEnvioExterno( elementos );
            } catch ( DAOException e ) {
                throw new ServiceException( "Error al generar contenido del ticket de receta para envio externo", e );
            }
        }
    }

    private String obtenAdicion( String cantidad, Receta receta, boolean esIzquierdo ) {
        if ( StringUtils.isNotBlank( cantidad ) && receta != null ) {
            StringBuilder adicion = new StringBuilder();
            switch ( receta.getUsoAnteojos() ) {
                case C:
                    adicion.append( "0" );
                    break;
                case L:
                    adicion.append( "" );
                    break;
                case I:
                    adicion.append( "0" );
                    break;
                case P:
                    adicion.append( esIzquierdo ? receta.getOiAdcR() : receta.getOdAdcR() );
                    break;
                case B:
                    adicion.append( esIzquierdo ? receta.getOiAdcR() : receta.getOdAdcR() );
                    break;
                case T:
                    adicion.append( esIzquierdo ? receta.getOiAdiR() : receta.getOdAdiR() );
                    break;
            }
            if ( esIzquierdo && StringUtils.equalsIgnoreCase( "od", cantidad ) ) {
                return "";
            } else if ( !esIzquierdo && StringUtils.equalsIgnoreCase( "oi", cantidad ) ) {
                return "";
            }
            return adicion.toString();
        }
        return null;
    }

    private String obtenPrisma( Receta receta, boolean esIzquierdo, boolean esVertical ) {
        if ( receta != null ) {
            String prismaH = esIzquierdo ? receta.getOiPrismaH() : receta.getOdPrismaH();
            if ( StringUtils.isNotBlank( prismaH ) ) {
                String prismaV = esIzquierdo ? receta.getOiPrismaV() : receta.getOdPrismaV();
                if ( esVertical ) {
                    if ( "ARRIBA".equalsIgnoreCase( prismaV ) ) {
                        return "+" + prismaH;
                    } else if ( "ABAJO".equalsIgnoreCase( prismaV ) ) {
                        return "-" + prismaH;
                    }
                } else {
                    if ( "ADENTRO".equalsIgnoreCase( prismaV ) ) {
                        return "+" + prismaH;
                    } else if ( "AFUERA".equalsIgnoreCase( prismaV ) ) {
                        return "-" + prismaH;
                    }
                }
                return "";
            }
        }
        return null;
    }
}

package mx.com.lux.control.trabajos.bussiness.service.impl;

import mx.com.lux.control.trabajos.bussiness.service.TicketService;
import mx.com.lux.control.trabajos.data.dao.*;
import mx.com.lux.control.trabajos.data.dao.envio.SucursalDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.CajasDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.ClienteDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.properties.ImpresionPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service( "ticketService" )
public class TicketServiceImpl implements TicketService {

	private final Logger log = LoggerFactory.getLogger( TicketServiceImpl.class );

	private final Integer ID_CAJAS = Integer.valueOf( ImpresionPropertyHelper.getProperty( "id.cajas" ) );

	@Resource
	private CajasDAO cajasDAO;

	@Resource
	private PagoExtraDAO pagoExtraDAO;

	@Resource
	private ExternoDAO externoDAO;

	@Resource
	private ClienteDAO clienteDAO;

	@Resource
	private TrabajoDAO trabajoDAO;

	@Resource
	private SucursalDAO sucursalDAO;

	@Resource
	private BancoDAO bancoDAO;

	@Resource
	private TipoPagoDAO tipoPagoDAO;

	@Resource
	private JbDevDAO jbDevDAO;

	@Resource
	private JbGarantiaDAO jbGarantiaDAO;

	@Resource
	private VelocityEngine velocityEngine;

	private String generaTicket( String template, Map<String, Object> items ) {
		if ( StringUtils.isNotBlank( template ) && items != null ) {
			try {
				String fileName = items.containsKey( "nombre_ticket" ) ? (String) items.get( "nombre_ticket" ) : template;
				File file = File.createTempFile( fileName, null );
				Writer writer = new FileWriter( file );
				items.put( "writer", writer );
				VelocityEngineUtils.mergeTemplate( velocityEngine, template, "ASCII", items, writer );
				writer.close();
				return file.getAbsolutePath();
			} catch ( IOException e ) {
				log.error( "error al generar archivo para impresion", e );
			}
		}
		return null;
	}

	private void imprimeTicket( String template, Map<String, Object> items ) {
		String ticket = generaTicket( template, items );
		if ( StringUtils.isNotBlank( ticket ) ) {
			try {
				Cajas cajas = cajasDAO.findCajasById( ID_CAJAS );
				StringBuilder cmd = new StringBuilder();
				cmd.append( cajas.getImpTicket() );
				cmd.append( " " );
				cmd.append( ticket );
				log.info( "ejecuta: {}", cmd );
				Runtime run = Runtime.getRuntime();
				Process pr = run.exec( cmd.toString() );
				pr.waitFor();
			} catch ( DAOException e ) {
				log.error( "error al obtener comando impresion ticket", e );
			} catch ( InterruptedException e ) {
				log.error( "error durante la ejecucion del comando de impresion", e );
			} catch ( IOException e ) {
				log.error( "error al ejecutar comando impresion", e );
			}
		}
	}

	@Override
	public void imprimeEnvioExterno( Map<String, Object> elementos ) {
		imprimeTicket( "ticket-envio-externo.vm", elementos );
	}

	@Override
	public void imprimeRecetaEnvioExterno( Map<String, Object> elementos ) {
		imprimeTicket( "ticket-receta.vm", elementos );
	}

	@Override
	public void imprimeTicketEntregaExterno( String rx ) {

		try {
			Jb trabajo = trabajoDAO.findById( rx );
			Cliente cliente = clienteDAO.obtenCliente( Integer.parseInt( trabajo.getIdCliente() ) );
			List<JbPagoExtra> pagosExtra = pagoExtraDAO.buscarPorRx( rx );
			JbExterno externo = externoDAO.obtenerPorRx( rx );
			Sucursal sucursalOrigen = sucursalDAO.obtenerPorNombre( externo.getOrigen() );
			Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );

			SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );
			NumberFormat nf = NumberFormat.getCurrencyInstance( Locale.US );

			StringBuffer telefonos = new StringBuffer();
			telefonos.append( ( cliente != null && StringUtils.isNotBlank( cliente.getTelCasaCli() ) ) ? " Casa: " + cliente.getTelCasaCli() : "" );
			telefonos.append( ( cliente != null && StringUtils.isNotBlank( cliente.getTelTrabCli() ) ) ? " Trabajo: " + cliente.getTelTrabCli() : "" );
			telefonos.append( ( cliente != null && StringUtils.isNotBlank( cliente.getExtTrabCli() ) ) ? " ext. " + cliente.getExtTrabCli() : "" );
			telefonos.append( ( cliente != null && StringUtils.isNotBlank( cliente.getTelAdiCli() ) ) ? " Adicional: " + cliente.getTelAdiCli() : "" );
			telefonos.append( ( cliente != null && StringUtils.isNotBlank( cliente.getExtAdiCli() ) ) ? " ext. " + cliente.getExtAdiCli() : "" );

			List<String[]> pagos = new ArrayList<String[]>();
			for ( JbPagoExtra pagoExtra : pagosExtra ) {
				Banco banco = StringUtils.isNotBlank( pagoExtra.getIdBancoEmi() ) ? bancoDAO.obtenerPorId( pagoExtra.getIdBancoEmi() ) : null;
				String descripcionBanco = banco != null ? banco.getDescripcion() : "--";
				if ( descripcionBanco.length() > 20 ) {
					descripcionBanco = descripcionBanco.substring( 0, 20 );
				}
				TipoPago tipoPago = tipoPagoDAO.obtenerPorId( pagoExtra.getIdFormaPago() );
				String descripcionTipoPago = tipoPago.getDescripcion().trim().toUpperCase().replace( "TARJETA", "T." ).replace( "TRANSFERENCIA", "TRANSF." );
				pagos.add( new String[]{ descripcionTipoPago, descripcionBanco, pagoExtra.getRefClave(), nf.format( pagoExtra.getMonto() ) } );
			}

			Map<String, Object> items = new HashMap<String, Object>();
			items.put( "sucursalOrigen", externo.getOrigen() );
			items.put( "sucursalDestino", empleado.getSucursal().getNombre() );
			items.put( "fecha", sdf.format( new Date() ) );
			items.put( "factura", externo.getFactura() );
			items.put( "material", trabajo.getMaterial() );
			items.put( "cliente", trabajo.getCliente() );
			items.put( "domicilio", cliente != null ? cliente.getDireccionCli() : "" );
			items.put( "telefonos", telefonos.toString() );
			items.put( "saldo", trabajo.getSaldoFormateado() );
			items.put( "pagos", pagos );
			imprimeTicket( "ticket-entrega-externo-sin-saldo.vm", items );
		} catch ( Exception e ) {
			log.error( "ERROR al imprimir ticket: " + e.getMessage() );
		}
	}

	@Override
	public void imprimeTicketSucursalDevolucionSp( final Integer idJbDev ) {
		try {
			log.info( "Imprimiendo Ticket de Sucursal para Devolucion SP. Id: " + idJbDev );
			JbDev jbDev = (JbDev) jbDevDAO.findByPrimaryKey( JbDev.class, idJbDev );
			Map<String, Object> datos = new HashMap<String, Object>();
			datos.put( "fecha", ApplicationUtils.formatearFecha( jbDev.getFecha(), "dd-MM-yyyy" ) );
			datos.put( "factura", StringUtils.defaultIfBlank( jbDev.factura(), "--" ) );
			datos.put( "armazon", StringUtils.defaultIfBlank( jbDev.armazon(), "--" ) );
			datos.put( "color", StringUtils.defaultIfBlank( jbDev.color(), "--" ) );
			datos.put( "apartado", StringUtils.defaultIfBlank( jbDev.apartado(), "--" ) );
			datos.put( "documento", StringUtils.defaultIfBlank( jbDev.documento(), "--" ) );
			imprimeTicket( "ticket-sucursal-devolucion-sp.vm", datos );
		} catch ( Exception e ) {
			log.error( "Error al imprimir el Ticket de Sucursal para Devolucion SP. Id: " + idJbDev );
		}

	}

	@Override
	public void imprimeTicketSobreDevolucionSp( final Integer idJbDev, final Integer idSucursal ) {
		try {
			log.info( "Imprimiendo Ticket de Sobre para Devolucion SP. Id: " + idJbDev );
			JbDev jbDev = (JbDev) jbDevDAO.findByPrimaryKey( JbDev.class, idJbDev );
			Sucursal sucursalSolicita = sucursalDAO.findById( Integer.parseInt( jbDev.getSucursal().trim() ) );
			Sucursal sucursalDevuelve = sucursalDAO.findById( idSucursal );
			Map<String, Object> datos = new HashMap<String, Object>();
			datos.put( "fecha", ApplicationUtils.formatearFecha( jbDev.getFecha(), "dd-MM-yyyy" ) );
			datos.put( "factura", StringUtils.defaultIfBlank( jbDev.factura(), "--" ) );
			datos.put( "armazon", StringUtils.defaultIfBlank( jbDev.armazon(), "--" ) );
			datos.put( "color", StringUtils.defaultIfBlank( jbDev.color(), "--" ) );
			datos.put( "apartado", StringUtils.defaultIfBlank( jbDev.apartado(), "--" ) );
			datos.put( "documento", StringUtils.defaultIfBlank( jbDev.documento(), "--" ) );
			datos.put( "sucursalSolicita", StringUtils.defaultIfBlank( sucursalSolicita.getNombre(), "--" ) );
			datos.put( "idSucursalSolicita", StringUtils.defaultIfBlank( jbDev.getSucursal().trim(), "--" ) );
			datos.put( "sucursalDevuelve", StringUtils.defaultIfBlank( sucursalDevuelve.getNombre(), "--" ) );
			datos.put( "idSucursalDevuelve", StringUtils.defaultIfBlank( idSucursal.toString(), "--" ) );
			datos.put( "conReceta", jbDev.getRx() );
			imprimeTicket( "ticket-sobre-devolucion-sp.vm", datos );
		} catch ( Exception e ) {
			log.error( "Error al imprimir el Ticket de Sobre para Devolucion SP. Id: " + idJbDev );
		}
	}

	@Override
	public Boolean imprimeTicketGarantia( final String rx ) {
		log.info( "Imprimiendo Ticket de Garantía: " + rx );
		try {
			Integer idGarantia = Integer.parseInt( StringUtils.replace( rx, "G", "" ) );
			Jb trabajo = trabajoDAO.findById( rx );
			JbGarantia garantia = jbGarantiaDAO.obtenerPorId( idGarantia );
			//Sucursal sucursal = ( garantia != null ) ? sucursalDAO.findById( garantia.idSucursal() ) : null;
			Cliente cliente = ( garantia != null ) ? clienteDAO.obtenCliente( garantia.idCliente() ) : null;
			Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
            Sucursal sucursal = empleado.getSucursal();
			if( garantia != null && sucursal != null && cliente != null ) {
				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put( "trabajo", trabajo );
				datos.put( "garantia", garantia );
				datos.put( "sucursal", sucursal );
				datos.put( "cliente", cliente );
				datos.put( "empleado", empleado );
				datos.put( "fechaHoy", ApplicationUtils.formatearFecha( new Date(), "dd-MM-yyyy" ) );
				imprimeTicket( "ticket-garantia.vm", datos );
				return Boolean.TRUE;
			}
		} catch( Exception e ) {
			log.error( "Error al imprimir Garantia: " + e.getMessage() );
		}
		log.error( "No se ha podido imprimir la Garantia " + rx );
		return Boolean.FALSE;
	}
}


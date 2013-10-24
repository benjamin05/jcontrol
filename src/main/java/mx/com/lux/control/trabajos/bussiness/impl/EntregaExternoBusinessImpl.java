package mx.com.lux.control.trabajos.bussiness.impl;

import com.google.common.base.Joiner;
import mx.com.lux.control.trabajos.bussiness.EntregaExternoBusiness;
import mx.com.lux.control.trabajos.data.dao.AcuseDAO;
import mx.com.lux.control.trabajos.data.dao.ExternoDAO;
import mx.com.lux.control.trabajos.data.dao.PagoExtraDAO;
import mx.com.lux.control.trabajos.data.dao.PolizaDAO;
import mx.com.lux.control.trabajos.data.dao.envio.SucursalDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.JbTrackDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO;
import mx.com.lux.control.trabajos.data.vo.Acuses;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbExterno;
import mx.com.lux.control.trabajos.data.vo.JbLlamada;
import mx.com.lux.control.trabajos.data.vo.JbPagoExtra;
import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.data.vo.Polizas;
import mx.com.lux.control.trabajos.data.vo.Sucursal;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.exception.ServiceException;
import mx.com.lux.control.trabajos.util.constants.AcusesConstants;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component( "entregaExternoBusiness" )
public class EntregaExternoBusinessImpl implements EntregaExternoBusiness {

	private final Logger log = Logger.getLogger( EntregaExternoBusiness.class );

	@Resource
	private TrabajoDAO trabajoDAO;

	@Resource
	private JbTrackDAO trackDAO;

	@Resource
	private AcuseDAO acuseDAO;

	@Resource
	private SucursalDAO sucursalDAO;

	@Resource
	private PagoExtraDAO pagoExtraDAO;

	@Resource
	private ExternoDAO externoDAO;

	@Resource
	private PolizaDAO polizaDAO;

	@Resource
	private VelocityEngine velocityEngine;

	@Override
	public void actualizarEstadoTrabajo( final String rx, final EstadoTrabajo estado ) throws ServiceException {
		try {
			Jb trabajo = trabajoDAO.findById( rx );
			trabajo.estado( estado );
			trabajoDAO.save( trabajo );
		} catch ( Exception e ) {
			throw new ServiceException( "Error al cambiar el estado del trabajo: " + estado, e );
		}
	}

	@Override
	public void insertarTrack( final String rx, final EstadoTrabajo estado ) throws ServiceException {
		try {
			Empleado usuario = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
			JbTrack track = new JbTrack();
			track.setRx( rx );
			track.setEstado( estado.codigo() );
			track.setEmpleado( usuario.getIdEmpleado() );
			track.setObservaciones( "NOTA EXTERNA" );
			track.setFecha( new Timestamp( System.currentTimeMillis() ) );
			track.setIdMod( "0" );
			trackDAO.save( track );
		} catch ( Exception e ) {
			throw new ServiceException( "Error al insertar JbTrack", e );
		}
	}

	@Override
	public void actualizarFechaEntrega( final String rx ) throws ServiceException {
		try {
			externoDAO.actualizarFechaEntrega( rx );
		} catch ( Exception e ) {
			throw new ServiceException( "Error al actualizar fecha entrega del trabajo: " + rx, e );
		}
	}

	@Override
	public void insertarAcuses( final String rx, final EstadoTrabajo estado ) throws ServiceException {
		try {
			Acuses acuse = new Acuses();
			acuse.setIdTipo( AcusesConstants.EXTERNO_ENTREGA );
			acuse.setFechaCarga( new Date() );
			acuse.setContenido( "" );
			acuse.setIntentos( 0 );
			acuseDAO.save( acuse );

			acuse.setContenido( generarTextoAcuseEntregaExterno( rx, acuse.getIdAcuse() ) );
			acuseDAO.save( acuse );
		} catch ( Exception e ) {
			throw new ServiceException( "Error al insertar Acuses", e );
		}
	}

	@Override
	public void insertarAcusesConSaldo( final String rx, final EstadoTrabajo estado ) throws ServiceException {
		try {
			Acuses acuse = new Acuses();
			acuse.setIdTipo( AcusesConstants.EXTERNO_ENTREGA );
			acuse.setFechaCarga( new Date() );
			acuse.setContenido( "" );
			acuse.setIntentos( 0 );
			acuseDAO.save( acuse );

			acuse.setContenido( generarTextoAcuseEntregaExternoConSaldo( rx, acuse.getIdAcuse() ) );
			acuseDAO.save( acuse );
		} catch ( Exception e ) {
			throw new ServiceException( "Error al insertar Acuses", e );
		}
	}

	private String generarTextoAcuseEntregaExterno( final String rx, final Integer idAcuse ) {
		List<JbPagoExtra> pagosExtra = pagoExtraDAO.buscarPorRx( rx );
		JbExterno externo = externoDAO.obtenerPorRx( rx );
		Assert.notNull( externo, "No existe externo para la RX " + rx );
		Sucursal sucursalOrigen = sucursalDAO.obtenerPorNombre( externo.getOrigen() );
		Assert.notNull( sucursalOrigen, "No existe la Sucursal " + externo.getOrigen() );
		Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );

		NumberFormat nf = NumberFormat.getInstance( Locale.US );
		nf.setMinimumFractionDigits( 2 );
		nf.setMaximumFractionDigits( 2 );
		nf.setGroupingUsed( false );
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
		StringBuilder montos = new StringBuilder();
		for ( JbPagoExtra pagoExtra : pagosExtra ) {
			if ( pagoExtra != null && pagoExtra.getMonto() != null ) {
				montos.append( nf.format( pagoExtra.getMonto() ) ).append( "*" );
			}
		}
		log.debug( "Monto: " + montos );

		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put( "rx", StringUtils.remove( rx.toUpperCase(), 'X' ) );
		datos.put( "monto", StringUtils.isEmpty( montos.toString() ) ? "0.00" : montos.toString() );
		datos.put( "externo", externo );
		datos.put( "fechaEntrega", sdf.format( externo.getFechaEntrega() ) );
		datos.put( "empleado", empleado );
		datos.put( "idSucursalOrigen", String.format( "%04d", sucursalOrigen != null ? sucursalOrigen.getIdSucursal() : 0 ) );
		datos.put( "idSucursalDestino", empleado.getSucursal().getIdSucursal() );
		datos.put( "idAcuse", idAcuse );

		String acuse = VelocityEngineUtils.mergeTemplateIntoString( velocityEngine, "acuse-entrega-externo.vm", "UTF-8", datos );
		log.debug( "Acuse entrega externo: " + acuse );
		return acuse;
	}

	private String generarTextoAcuseEntregaExternoConSaldo( final String rx, final Integer idAcuse ) {
		List<JbPagoExtra> pagosExtra = pagoExtraDAO.buscarPorRx( rx );
		JbExterno externo = externoDAO.obtenerPorRx( rx );
		Assert.notNull( externo, "No existe externo para la RX " + rx );
		Sucursal sucursalOrigen = sucursalDAO.obtenerPorNombre( externo.getOrigen() );
		Assert.notNull( sucursalOrigen, "No existe la Sucursal " + externo.getOrigen() );
		Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );

		String formasPago = null;
		String bancos = null;
		String referencias = null;
		String montos = null;
		String clavesP = null;
		String terminales = null;
		String planes = null;

		NumberFormat nf = NumberFormat.getInstance( Locale.US );
		nf.setMinimumFractionDigits( 2 );
		nf.setMaximumFractionDigits( 2 );
		nf.setGroupingUsed( false );
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );

		Joiner joiner = Joiner.on( '*' ).skipNulls();
		for ( JbPagoExtra pagoExtra : pagosExtra ) {
			formasPago = joiner.join( formasPago, pagoExtra.getIdFPago() != null ? pagoExtra.getIdFPago().trim() : "" );
			bancos = joiner.join( bancos, pagoExtra.getIdBancoEmi() != null ? pagoExtra.getIdBancoEmi().trim() : "" );
			referencias = joiner.join( referencias, pagoExtra.getRefClave() != null ? pagoExtra.getRefClave().trim() : "" );
			montos = joiner.join( montos, pagoExtra.getMonto() != null ? nf.format( pagoExtra.getMonto() ) : "" );
			clavesP = joiner.join( clavesP, pagoExtra.getClaveP() != null ? pagoExtra.getClaveP().trim() : "" );
			terminales = joiner.join( terminales, pagoExtra.getIdTerm() != null ? pagoExtra.getIdTerm().trim() : "" );
			planes = joiner.join( planes, pagoExtra.getIdPlan() != null ? pagoExtra.getIdPlan().trim() : "" );
		}
		formasPago = formasPago + "*";
		bancos = bancos + "*";
		referencias = referencias + "*";
		montos = montos + "*";
		clavesP = clavesP + "*";
		terminales = terminales + "*";
		planes = planes + "*";

		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put( "rx", StringUtils.remove( rx.toUpperCase(), 'X' ) );
		datos.put( "fecha", sdf.format( new Date() ) );
		datos.put( "empleado", empleado );
		datos.put( "id_acuseVal", idAcuse );
		datos.put( "fpVal", formasPago );
		datos.put( "bancoVal", bancos );
		datos.put( "refVal", referencias );
		datos.put( "montoVal", montos );
		datos.put( "clave_pVal", clavesP );
		datos.put( "termVal", terminales );
		datos.put( "planVal", planes );
		datos.put( "id_suc_oriVal", String.format( "%04d", sucursalOrigen.getIdSucursal() ) );
		datos.put( "id_suc_desVal", empleado.getSucursal().getIdSucursal() );

		String acuse = VelocityEngineUtils.mergeTemplateIntoString( velocityEngine, "acuse-entrega-externo-con-saldo.vm", "UTF-8", datos );
		log.debug( "Acuse entrega externo: " + acuse );
		return acuse;
	}

	@Override
	public void crearArchivoPagoExternos( final String rx ) throws ServiceException {
		JbExterno externo = externoDAO.obtenerPorRx( rx );
		Assert.notNull( externo, "No existe externo para la RX " + rx );
		Sucursal sucursalOrigen = sucursalDAO.obtenerPorNombre( externo.getOrigen() );
		Assert.notNull( sucursalOrigen, "No existe la Sucursal " + externo.getOrigen() );
		Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );

		String nombreArchivo = String.format( "%04d", sucursalOrigen.getIdSucursal() ) + "-" + externo.getFactura() + "-pag_ext.EXT";
		String rutaArchivo = TrabajosPropertyHelper.getProperty( "trabajos.ruta.externo.envio" );
		log.debug( "Archivo pago externos: " + rutaArchivo + "/" + nombreArchivo );

		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );

		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put( "rx", rx );
		datos.put( "timestamp", sdf.format( new Date() ) );
		datos.put( "empleado", empleado );
		String texto = VelocityEngineUtils.mergeTemplateIntoString( velocityEngine, "archivo-pago-externo.vm", "UTF-8", datos );
		log.debug( texto );

		try {
			File directorio = crearDirectorioSiNoExiste( rutaArchivo );
			if ( directorio.exists() ) {
				File archivo = new File( rutaArchivo + "/" + nombreArchivo );
				if ( archivo.exists() ) {
					archivo.delete();
				}
				BufferedWriter bw = new BufferedWriter( new FileWriter( archivo ) );
				bw.write( texto );
				bw.flush();
				bw.close();
			}
		} catch ( IOException e ) {
			throw new ServiceException( "No se pudo guardar el archivo de pago externo: " + e.getMessage(), e );
		}
	}

	@Override
	public void crearArchivoPagoExternosConSaldo( final String rx ) throws ServiceException {
		List<JbPagoExtra> pagosExtra = pagoExtraDAO.buscarPorRx( rx );
		JbExterno externo = externoDAO.obtenerPorRx( rx );
		Assert.notNull( externo, "No existe externo para la RX " + rx );
		Sucursal sucursalOrigen = sucursalDAO.obtenerPorNombre( externo.getOrigen() );
		Assert.notNull( sucursalOrigen, "No existe la Sucursal " + externo.getOrigen() );
		Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );

		String nombreArchivo = String.format( "%04d", sucursalOrigen.getIdSucursal() ) + "-" + externo.getFactura() + "-pag_ext.EXT";
		String rutaArchivo = TrabajosPropertyHelper.getProperty( "trabajos.ruta.externo.envio" );
		log.debug( "Archivo pago externos: " + rutaArchivo + "/" + nombreArchivo );

		String formasPago = null;
		String bancos = null;
		String referencias = null;
		String montos = null;

		NumberFormat nf = NumberFormat.getInstance( Locale.US );
		nf.setMinimumFractionDigits( 2 );
		nf.setMaximumFractionDigits( 2 );
		nf.setGroupingUsed( false );
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );

		Joiner joiner = Joiner.on( '*' ).skipNulls();
		for ( JbPagoExtra pagoExtra : pagosExtra ) {
			formasPago = joiner.join( formasPago, pagoExtra.getIdFPago() != null ? pagoExtra.getIdFPago().trim() : "" );
			bancos = joiner.join( bancos, pagoExtra.getIdBancoEmi() != null ? pagoExtra.getIdBancoEmi().trim() : "" );
			referencias = joiner.join( referencias, pagoExtra.getRefClave() != null ? pagoExtra.getRefClave().trim() : "" );
			montos = joiner.join( montos, pagoExtra.getMonto() != null ? nf.format( pagoExtra.getMonto() ) : "" );
		}
		formasPago = formasPago + "*";
		bancos = bancos + "*";
		referencias = referencias + "*";
		montos = montos + "*";

		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put( "FACTURA", StringUtils.remove( rx.toUpperCase(), 'X' ) );
		datos.put( "FORMA_PAGOS", formasPago );
		datos.put( "BANCOS", bancos );
		datos.put( "REF_CLAVE", referencias );
		datos.put( "MONTO", montos );
		datos.put( "FECHA", sdf.format( new Date() ) );
		datos.put( "EMPLEADO", empleado.getIdEmpleado().trim() );
		String texto = VelocityEngineUtils.mergeTemplateIntoString( velocityEngine, "archivo-pago-externo-con-saldo.vm", "UTF-8", datos );
		log.debug( texto );

		try {
			File directorio = crearDirectorioSiNoExiste( rutaArchivo );
			if ( directorio.exists() ) {
				File archivo = new File( rutaArchivo + "/" + nombreArchivo );
				if ( archivo.exists() ) {
					archivo.delete();
				}
				BufferedWriter bw = new BufferedWriter( new FileWriter( archivo ) );
				bw.write( texto );
				bw.flush();
				bw.close();
			}
		} catch ( IOException e ) {
			throw new ServiceException( "No se pudo guardar el archivo de pago externo: " + e.getMessage(), e );
		}
	}

	@Override
	public void crearArchivoEntregaExternos( String rx ) throws ServiceException {
		JbExterno externo = externoDAO.obtenerPorRx( rx );
		Assert.notNull( externo, "No existe externo para la RX " + rx );
		Sucursal sucursalOrigen = sucursalDAO.obtenerPorNombre( externo.getOrigen() );
		Assert.notNull( sucursalOrigen, "No existe la Sucursal " + externo.getOrigen() );
		Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );

		String nombreArchivo = String.format( "%04d", sucursalOrigen.getIdSucursal() ) + "-TE" + StringUtils.remove( externo.getFactura(), "X" ) + "-STS.EXT";
		String rutaArchivo = TrabajosPropertyHelper.getProperty( "trabajos.ruta.externo.envio" );
		log.debug( "Archivo pago externos: " + rutaArchivo + "/" + nombreArchivo );

		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );

		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put( "rx", StringUtils.remove( rx.toUpperCase(), "X" ) );
		datos.put( "timestamp", sdf.format( new Date() ) );
		datos.put( "empleado", empleado );
		String texto = VelocityEngineUtils.mergeTemplateIntoString( velocityEngine, "archivo-entrega-externo.vm", "UTF-8", datos );
		log.debug( texto );

		try {
			File directorio = crearDirectorioSiNoExiste( rutaArchivo );
			if ( directorio.exists() ) {
				File archivo = new File( rutaArchivo + "/" + nombreArchivo );
				if ( archivo.exists() ) {
					archivo.delete();
				}
				BufferedWriter bw = new BufferedWriter( new FileWriter( archivo ) );
				bw.write( texto );
				bw.flush();
				bw.close();
			}
		} catch ( IOException e ) {
			throw new ServiceException( "No se pudo guardar el archivo de pago externo: " + e.getMessage(), e );
		}
	}

	private File crearDirectorioSiNoExiste( String ruta ) throws ServiceException {
		File directorio = new File( ruta );
		if ( !directorio.exists() ) {
			log.warn( "No existe el directorio: " + ruta );
			if ( directorio.mkdirs() ) {
				log.debug( "Se ha creado el directorio: " + ruta );
			} else {
				log.error( "Error: no se ha podido crear el directorio: " + ruta );
				throw new ServiceException( "Error: no se ha podido crear el directorio: " + ruta );
			}
		}
		return directorio;
	}

	@Override
	public List<Polizas> obtenerPolizas( String rx ) throws ServiceException {
		JbExterno externo = externoDAO.obtenerPorRx( rx );
		Sucursal sucursalOrigen = sucursalDAO.obtenerPorNombre( externo.getOrigen() );
		return polizaDAO.buscarPorRxYPorSucursal( StringUtils.remove( externo.getFactura(), "X" ), sucursalOrigen.getIdSucursal() );
	}

	@Override
	public void actualizarPoliza( Polizas poliza ) throws DAOException {
		// 365 * 24 * 60 * 60 * 1000l
		// dias * horas * minutos * segundos * milisegundos
		Long anyoEnMilisegundos = 31536000000l;
		Long ahoraMilisegundos = new Date().getTime();
		Long vigenciaMilisegundos = ahoraMilisegundos + anyoEnMilisegundos;
		poliza.setStatus( "E" );
		poliza.setFechaEntrega( new Date() );
		poliza.setVigencia( new Date( vigenciaMilisegundos ) );
		polizaDAO.save( poliza );
	}

	@Override
	public void insertarAcusePoliza( final Polizas poliza ) throws ServiceException {
		try {
			Acuses acuse = new Acuses();
			acuse.setIdTipo( AcusesConstants.POLIZA_ENTREGA );
			acuse.setFechaCarga( new Date() );
			acuse.setContenido( "" );
			acuseDAO.save( acuse );

			acuse.setContenido( generarTextoAcusePoliza( poliza, acuse.getIdAcuse() ) );
			acuseDAO.save( acuse );
		} catch ( Exception e ) {
			throw new ServiceException( "Error al insertar Acuse de Poliza", e );
		}
	}

	private String generarTextoAcusePoliza( final Polizas poliza, final Integer idAcuse ) {
		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put( "idAcuse", idAcuse );
		datos.put( "poliza", poliza );
		String acuse = VelocityEngineUtils.mergeTemplateIntoString( velocityEngine, "acuse-poliza.vm", "UTF-8", datos );
		log.debug( "Acuse poliza: " + acuse );
		return acuse;
	}

	@Override
	public void procesarPoliza( final String idPoliza ) throws ServiceException {
		try {
			String cmd = "./entregar_poliza.sh -a " + idPoliza;
			Runtime run = Runtime.getRuntime();
			Process pr = run.exec( cmd );
			pr.waitFor();
		} catch ( Exception e ) {
			throw new ServiceException( "Error al procesar la poliza: " + e.getMessage(), e );
		}
	}

	@Override
	public void eliminarJbLlamada( String rx ) throws ServiceException {
		try {
			JbLlamada jbLlamada = trabajoDAO.findJbLlamadaById( rx );
			if ( jbLlamada != null ) {
				trabajoDAO.delete( jbLlamada );
			}
		} catch ( Exception e ) {
			throw new ServiceException( "Error al eliminar JbLlamada", e );
		}
	}

	@Override
	public void actualizarPagosExterno( String rx, String idEmpleadoAtendio ) throws ServiceException {
		List<JbPagoExtra> pagosExterno = pagoExtraDAO.buscarPorRx( rx );
		for ( JbPagoExtra pagoExterno : pagosExterno ) {
			try {
				pagoExterno.setConfirm( true );
				pagoExterno.setIdEmpleado( idEmpleadoAtendio );
				pagoExtraDAO.save( pagoExterno );
			} catch ( Exception e ) {
				throw new ServiceException( "Error al actualizar Pago Externo: " + pagoExterno, e );
			}

		}
	}
}

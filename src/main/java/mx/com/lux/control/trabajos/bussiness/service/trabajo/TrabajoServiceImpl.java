package mx.com.lux.control.trabajos.bussiness.service.trabajo;

import mx.com.lux.control.trabajos.bussiness.RecepcionBusiness;
import mx.com.lux.control.trabajos.data.dao.CondicionIcGenericoDAO;
import mx.com.lux.control.trabajos.data.dao.FormaContactoDAO;
import mx.com.lux.control.trabajos.data.dao.NotaVentaDAO;
import mx.com.lux.control.trabajos.data.dao.TipoFormaContactoDAO;
import mx.com.lux.control.trabajos.data.dao.contacto.ContactoViewDAO;
import mx.com.lux.control.trabajos.data.dao.envio.SucursalDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.JbGrupoDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO;
import mx.com.lux.control.trabajos.data.vo.Acuses;
import mx.com.lux.control.trabajos.data.vo.Articulos;
import mx.com.lux.control.trabajos.data.vo.Cajas;
import mx.com.lux.control.trabajos.data.vo.Codigo;
import mx.com.lux.control.trabajos.data.vo.CondicionIcGenerico;
import mx.com.lux.control.trabajos.data.vo.DetalleNotaVenta;
import mx.com.lux.control.trabajos.data.vo.Dominios;
import mx.com.lux.control.trabajos.data.vo.EdoGrupo;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.EmpresaFicha;
import mx.com.lux.control.trabajos.data.vo.EstadoRepublica;
import mx.com.lux.control.trabajos.data.vo.FormaContacto;
import mx.com.lux.control.trabajos.data.vo.InstitucionIc;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbDev;
import mx.com.lux.control.trabajos.data.vo.JbEstado;
import mx.com.lux.control.trabajos.data.vo.JbGrupo;
import mx.com.lux.control.trabajos.data.vo.JbLlamada;
import mx.com.lux.control.trabajos.data.vo.JbNota;
import mx.com.lux.control.trabajos.data.vo.JbRoto;
import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.data.vo.Municipio;
import mx.com.lux.control.trabajos.data.vo.Nomina;
import mx.com.lux.control.trabajos.data.vo.NotaFactura;
import mx.com.lux.control.trabajos.data.vo.NotaVenta;
import mx.com.lux.control.trabajos.data.vo.Pagos;
import mx.com.lux.control.trabajos.data.vo.ParentescoFichas;
import mx.com.lux.control.trabajos.data.vo.PlazosPagoFichas;
import mx.com.lux.control.trabajos.data.vo.Polizas;
import mx.com.lux.control.trabajos.data.vo.Sucursal;
import mx.com.lux.control.trabajos.data.vo.TipoContacto;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.InfoPinoUtil;
import mx.com.lux.control.trabajos.util.constants.AcusesConstants;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.EstadoConstants;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;
import mx.com.lux.control.trabajos.util.constants.JbTipoConstants;
import mx.com.lux.control.trabajos.util.constants.TipoTrabajo;
import mx.com.lux.control.trabajos.util.constants.TipoVentaConstants;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service( "trabajoService" )
public class TrabajoServiceImpl implements TrabajoService {

	private final Logger log = LoggerFactory.getLogger( TrabajoService.class );

	private static final String RX_TIPO_BASE = "[0-9]+";

	@Resource
	private TrabajoDAO trabajoDAO;

	@Resource
	private ContactoViewDAO contactoViewDAO;

	@Resource
	private JbGrupoDAO jbGrupoDAO;

	@Resource
	private NotaVentaDAO notaVentaDAO;

	@Resource
	private FormaContactoDAO formaContactoDAO;

	@Resource
	private TipoFormaContactoDAO tipoFormaContactoDAO;

	@Resource
	private SucursalDAO sucursalDAO;

	@Resource
	private CondicionIcGenericoDAO condicionIcGenericoDAO;

	@Resource
	private RecepcionBusiness recepcionBusiness;

	@Override
	public List<Jb> findAlljbByFilters( String rx, Integer idEdoGrupo, String atendio, String cliente, int firstResult, int resultSize ) throws ApplicationException {
		return trabajoDAO.findAlljbByFilters( rx, idEdoGrupo, atendio, cliente, firstResult, resultSize );
	}

	@Override
	public int countAlljbByFilters( String rx, Integer idEdoGrupo, String atendio, String cliente ) throws ApplicationException {
		return trabajoDAO.countAlljbByFilters( rx, idEdoGrupo, atendio, cliente );
	}

	@Override
	public List<EdoGrupo> getEstados() {
		try {
			return trabajoDAO.getEstados();
		} catch ( DAOException e ) {
			return new ArrayList<EdoGrupo>();
		}
	}

	@Override
	public Jb findById( final String rx ) {
		try {
			return trabajoDAO.findById( rx );
		} catch ( Exception e ) {
			return null;
		}

	}

	@Override
	public JbLlamada findJbLlamadaById( String rxString ) throws ApplicationException {
		return trabajoDAO.findJbLlamadaById( rxString );
	}

	@Override
	public void delete( Object o ) throws ApplicationException {
		trabajoDAO.delete( o );
	}

	@Override
	public void saveJbAndTrack( Jb jb, JbTrack jbTrack ) throws ApplicationException {
		trabajoDAO.saveJbAndTrack( jb, jbTrack );
	}

	public void deleteInsertUpdate( Object[] o ) throws ApplicationException {
		trabajoDAO.deleteInsertUpdate( o );
	}

	public void updateInsert( Object[] o ) throws ApplicationException {
		trabajoDAO.updateInsert( o );
	}

	@Override
	public void saveJbAndJbGrupo( Jb jb, JbGrupo jbGrupo ) throws ApplicationException {
		trabajoDAO.saveJbAndJbGrupo( jb, jbGrupo );
	}

	public Nomina findNominaByFactura( String rx ) throws ApplicationException {
		return trabajoDAO.findNominaByFactura( rx );
	}

	public List<EmpresaFicha> getEmpresaFicha() throws ApplicationException {
		return trabajoDAO.getEmpresaFicha();
	}

	public List<ParentescoFichas> getParentescoFichas() throws ApplicationException {
		return trabajoDAO.getParentescoFichas();
	}

	public List<PlazosPagoFichas> getPlazosPagoFichasByTipoVentaNull() throws ApplicationException {
		return trabajoDAO.getPlazosPagoFichasByTipoVentaNull();
	}

	public List<PlazosPagoFichas> getAllPlazosPagoFichas() throws ApplicationException {
		return trabajoDAO.getAllPlazosPagoFichas();
	}

	public void saveAcusesNominaJbtrack( Acuses acuses, Nomina nomina, JbTrack jbTrack ) throws ApplicationException {
		trabajoDAO.saveAcusesNominaJbtrack( acuses, nomina, jbTrack );
	}

	public NotaVenta findNotaVentaByFactura( String rx ) {
		try {
			return notaVentaDAO.obtenNotaVentaPorTrabajo( rx );
		} catch ( DAOException e ) {
			return null;
		}
	}

	public DetalleNotaVenta findDetalleNotaVenByIdArt( String IdArt ) throws ApplicationException {
		return trabajoDAO.findDetalleNotaVenByIdArt( IdArt );
	}

	public DetalleNotaVenta findDetalleNotaVenByIdArtSurteAndGenerico( String idFactura, char surte, char generico ) throws ApplicationException {
		return trabajoDAO.findDetalleNotaVenByIdArtSurteAndGenerico( idFactura, surte, generico );
	}

	public List<DetalleNotaVenta> findAllDetalleNotaVenByIdArt( String idArt ) throws ApplicationException {
		return trabajoDAO.findAllDetalleNotaVenByIdArt( idArt );
	}

	public Articulos findArticulosByArt( Integer art ) throws ApplicationException {
		return trabajoDAO.findArticulosByArt( art );
	}

	@Override
	public Integer findIdRecetaByFactura( String rx ) throws ApplicationException {
		return trabajoDAO.findIdRecetaByFactura( rx );
	}

	@Override
	public List<Jb> findPorEnviar( int firstResult, int resultSize ) throws ApplicationException {
		return trabajoDAO.findPorEnviar( firstResult, resultSize );
	}

	@Override
	public List<Jb> findNoEnviar( int firstResult, int resultSize ) throws ApplicationException {
		return trabajoDAO.findNoEnviar( firstResult, resultSize );
	}

	@Override
	public int countPorEnviar() throws ApplicationException {
		return trabajoDAO.countPorEnviar();
	}

	@Override
	public int countNoEnviar() throws ApplicationException {
		return trabajoDAO.countNoEnviar();
	}

	@Override
	public List<Jb> findByJbTipo( String tipo ) throws ApplicationException {
		return trabajoDAO.findByJbTipo( tipo );
	}

	@Override
	public List<Jb> findByJbEstado( String estado ) throws ApplicationException {
		return trabajoDAO.findByJbEstado( estado );
	}

	@Override
	public List<JbDev> findJbDevByFechaEnvioNull() throws ApplicationException {
		return trabajoDAO.findJbDevByFechaEnvioNull();
	}

	@Override
	public boolean validateEnviarEFax( Jb jb ) {
		if ( EstadoConstants.ESTADO_POR_ENVIAR.equals( jb.getEstado().getIdEdo().trim() ) && JbTipoConstants.JB_TIPO_LAB.equals( StringUtils.trimToEmpty(jb.getJbTipo()).trim() ) )
			return true;
		else
			return false;
	}

	@Override
	public void validarEnviarEFax( String rx ) throws Exception {
		Jb jb = findById( rx );
		// Existe jb?
		if ( jb == null )
			throw new Exception( TrabajosPropertyHelper.getProperty( "envios.validacion.rx.noexiste" ) );
			// El sistema verifica que el trabajo seleccionado se encuentre en
			// estado de Por Enviar: JB.estado = "PE"
		else if ( !jb.getEstado().getIdEdo().trim().equals( EstadoConstants.ESTADO_POR_ENVIAR ) )
			throw new Exception( TrabajosPropertyHelper.getProperty( "envios.validacion.estado.invalido" ) );
			// El sistema verifica que el trabajo seleccionado sea de tipo
			// Laboratorio: JB.jb_tipo = �LAB�.
		else if ( !jb.getJbTipo().trim().equals( JbTipoConstants.JB_TIPO_LAB ) )
			throw new Exception( TrabajosPropertyHelper.getProperty( "envios.validacion.tipo.invalido" ) );
	}

	@Override
	public boolean enviarEFax( String rx, Empleado empleado ) throws ApplicationException {
        boolean bandera;
		JbTrack jbTrack = new JbTrack();
		jbTrack.setRx( rx );
		jbTrack.setEstado( EstadoTrabajo.FAX.codigo() );
		jbTrack.setObservaciones( TrabajosPropertyHelper.getProperty( "trabajos.msg.fax.obs" ) );
		jbTrack.setEmpleado( empleado.getIdEmpleado() );
		jbTrack.setFecha( new Date() );
		jbTrack.setIdMod( empleado.getIdEmpleado() );

		NotaVenta notaVenta = findNotaVentaByFactura( rx );
        if( notaVenta == null ){
            while ( rx.length() < 6 )
            {
                rx = "0"+rx;
            }
            notaVenta = findNotaVentaByFactura( rx );
        }
        if( notaVenta != null ){
		DetalleNotaVenta detalleNotaVen = findDetalleNotaVenByIdArtSurteAndGenerico( notaVenta.getIdFactura(), 'P', 'A' );
		Articulos articulos = null;
		if ( detalleNotaVen != null ) {
			articulos = findArticulosByArt( detalleNotaVen.getIdArticulo() );
		}

		// Crea Acuses
		Acuses acuses = new Acuses();
		acuses.setIdTipo( AcusesConstants.FAX );
		acuses.setFechaCarga( new Timestamp( System.currentTimeMillis() ) );

		trabajoDAO.saveAcusesAndJbtrack( acuses, jbTrack );

		String contenido = crearContenidoEFax( rx, detalleNotaVen, articulos, empleado, acuses );
		acuses.setContenido( contenido );

		trabajoDAO.save( acuses );
            return true;
        } else {
            return false;
        }

	}

	private String crearContenidoEFax( String rx, DetalleNotaVenta detalleNotaVen, Articulos articulos, Empleado empleado, Acuses acuse ) {
		String pipe = Constants.SYMBOL_PIPE;
		String equal = Constants.SYMBOL_EQUAL;
		StringBuilder sb = new StringBuilder( "" );
		// id_articuloVal
		sb.append( Constants.ID_ARTICULO_VAL ).append( equal );
		if ( detalleNotaVen != null ) {
			sb.append( articulos.getArticulo().trim() );
		}
		sb.append( pipe );
		// id_sucursalVal
		sb.append( Constants.ID_SUCURSAL_VAL ).append( equal );
		sb.append( String.format( "%04d", empleado.getSucursal().getIdSucursal() ) );
		sb.append( pipe );
		// rxVal
		sb.append( Constants.RX_VAL ).append( equal );
		sb.append( rx );
		sb.append( pipe );
		// id_colorVal
		sb.append( Constants.ID_COLOR_VAL ).append( equal );
		if ( detalleNotaVen != null && articulos.getColorCode() != null ) {
			sb.append( articulos.getColorCode().trim() );
		}
		sb.append( pipe );
		// id_acuseVal
		sb.append( Constants.ID_ACUSE_VAL ).append( equal );
		sb.append( acuse.getIdAcuse() );
		sb.append( pipe );

		// skuVal
		sb.append( Constants.SKU_VAL ).append( equal );
		sb.append( articulos.getIdArticulo() );
		sb.append( pipe );

		return sb.toString();
	}

	public String empleadoEmpresaWebResponse( String idEmpleado, String idEmpresa ) {
		String nameEmpleado = "";
		List<String> list = new ArrayList<String>();
		String answer = "";
		String[] data;

		// se crea la direccion web con los parametros
		String urlString = Constants.AUTORIZACION_URL + idEmpleado + Constants.SYMBOL_AMPERSAND + Constants.AUTORIZACION_ID_EMPRESA + idEmpresa;

		// se obtiene la respuesta web
		list = InfoPinoUtil.getReponseFromURL( urlString );

		// se extrae la respuesta para la validacion
		if ( !list.isEmpty() ) {
			answer = list.get( 1 );
			data = answer.split( Constants.REG_EXP_PIPE );
			nameEmpleado = data[2];
		}

		return nameEmpleado;
	}

	@Override
	public Boolean validateDesretenerTrabajo( final Jb trabajo ) {
		boolean desretener = false;
		try {
			if ( EstadoTrabajo.RETENIDO.equals( trabajo.estado() ) ) {
				desretener = true;
				if ( trabajo.getTipoVenta() != null && ( trabajo.getTipoVenta().trim().equals( TipoVentaConstants.TIPO_VENTA_EMPLEADO ) || trabajo.getTipoVenta().trim().equals( TipoVentaConstants.TIPO_VENTA_EMPLEADO_AUDITIVO ) ) ) {
					Nomina nomina = trabajoDAO.findNominaByFactura( trabajo.getRx() );
					if ( nomina != null && nomina.getAutorizacion() != null && StringUtils.isNumeric( String.valueOf( nomina.getAutorizacion() ) ) ) {
						int a = Integer.parseInt( nomina.getAutorizacion() );
						if ( a <= 0 ) {
							desretener = false;
						}
					} else {
						desretener = false;
					}
				}
			}
		} catch ( ApplicationException e ) {
			log.error( "Error: " + e.getMessage() );
		}
		return desretener;
	}

	@Override
	public Boolean validateRetenerTrabajo( EstadoTrabajo estado ) {
		if ( EstadoTrabajo.ENTREGADO.equals( estado ) || EstadoTrabajo.CANCELADO.equals( estado ) || EstadoTrabajo.BODEGA.equals( estado ) || EstadoTrabajo.RETENIDO.equals( estado ) || EstadoTrabajo.POR_ENVIAR_EXTERNO.equals( estado ) || EstadoTrabajo.ENVIADO_EXTERNO.equals( estado ) ) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean validateAutorizacion( Jb jb ) {
		boolean autoriza = false;
		try {
			if ( EstadoTrabajo.RETENIDO.equals( jb.estado() ) ) {
				String tipoVenta = jb.getTipoVenta() != null ? jb.getTipoVenta().trim() : "";
				if ( TipoVentaConstants.TIPO_VENTA_EMPLEADO.equals( tipoVenta ) || TipoVentaConstants.TIPO_VENTA_EMPLEADO_AUDITIVO.equals( tipoVenta ) ) {
					Nomina nomina = trabajoDAO.findNominaByFactura( jb.getRx() );
					if ( ( nomina != null && nomina.getFechaSolicitud() == null ) || nomina == null ) {
						autoriza = true;
					}
				}
			}
		} catch ( ApplicationException e ) {
			log.error( "Error al validar Autorizacion", e );
		}
		return autoriza;
	}

	public String createContenidoAcuses( NotaVenta notaVenta, String idEmpresa, String numEmpleado, String rx, String idParentesco, String cliente, String plazo, String sucursal ) {
		final String pipe = Constants.SYMBOL_PIPE;
		final String equal = Constants.SYMBOL_EQUAL;
		final String coma = Constants.SYMBOL_COMA;
		Double ventaNeta = notaVenta.getVentaNeta().doubleValue();
		String idConvenio = ( notaVenta != null && notaVenta.getIdConvenio() != null ) ? notaVenta.getIdConvenio().trim() : "";
		try {
			String contenido = Constants.EMPRESA_VAL + equal + idEmpresa + pipe + Constants.EMPLEADO_VAL + equal + numEmpleado + pipe + Constants.FACTURA_VAL + equal + rx + pipe + Constants.PARIENTE_VAL + equal + idParentesco + pipe + Constants.NOMBRE_PTE_VAL + equal + cliente + pipe;
			List<DetalleNotaVenta> detallesNotaVenta = trabajoDAO.findAllDetalleNotaVenByIdArt( notaVenta.getIdFactura() );
			BigDecimal porcentajeDescuento = BigDecimal.ZERO;
			if ( CollectionUtils.isNotEmpty( detallesNotaVenta ) ) {
				contenido = contenido + Constants.ARTICULO_VAL + equal;
				for ( DetalleNotaVenta detalleNotaVenta : detallesNotaVenta ) {
					Articulos articulo = trabajoDAO.findArticulosByArt( detalleNotaVenta.getIdArticulo() );
					contenido = contenido + articulo.getArticulo().trim() + coma;
					String idGenerico = ( articulo != null && articulo.getIdGenerico() != null ) ? articulo.getIdGenerico().toString() : "";
					CondicionIcGenerico condicionIcGenerico = condicionIcGenericoDAO.obtenerPorGenericoPorConvenio( idGenerico, idConvenio );
					BigDecimal porcentajeDescuentoTmp = ( condicionIcGenerico != null && condicionIcGenerico.getPorcentajeDescuento() != null ) ? condicionIcGenerico.getPorcentajeDescuento() : BigDecimal.ZERO;
					if ( porcentajeDescuentoTmp.compareTo( porcentajeDescuento ) > 0 ) {
						porcentajeDescuento = porcentajeDescuentoTmp;
					}
				}
				contenido = contenido + pipe;
			}
			contenido = contenido + Constants.TIPO_COMPRA_VAL + equal + Constants.CERO_UNO_STRING + pipe + Constants.IMPORTE_VAL + equal + String.valueOf( ventaNeta ) + pipe + Constants.DESCUENTO_VAL + equal + porcentajeDescuento + pipe + Constants.QUINCENAS_VAL + equal + plazo + pipe + Constants.SUCURSAL_VAL + equal + sucursal + pipe + Constants.CONVENIO_VAL + equal + notaVenta.getIdConvenio() + pipe;
			return contenido;
		} catch ( ApplicationException e ) {
			log.error( "Error al crear el contenido de acuses", e );
		}
		return "";
	}

	@Override
	public List<Pagos> findPagosByIdFactura( String idFactura ) throws ApplicationException {
		return trabajoDAO.findPagosByIdFactura( idFactura );
	}

	@Override
	public List<Polizas> findPolizasByFacturaVenta( String facturaVenta ) throws ApplicationException {
		return trabajoDAO.findPolizasByFacturaVenta( facturaVenta );
	}

	@Override
	public NotaFactura findNotaFacturaByIdFiscal( String idFiscal ) throws ApplicationException {
		return (NotaFactura) trabajoDAO.findByPrimaryKey( NotaFactura.class, idFiscal );
	}

	@Override
	public InstitucionIc findInstitucionIcByIdConvenio( String idConvenio ) throws ApplicationException {
        InstitucionIc institucion = null;
        if( idConvenio.trim().length() > 0 ){
             institucion = (InstitucionIc) trabajoDAO.findByPrimaryKey( InstitucionIc.class, idConvenio );
        }
        return institucion;
	}

	@Override
	public NotaFactura findMaxNotaFacturaByFactura( String factura ) throws ApplicationException {
		return trabajoDAO.findMaxNotaFacturaByFactura( factura );
	}

	@Override
	public Cajas findCajasById( Integer idCaja ) throws ApplicationException {
		return trabajoDAO.findCajasById( idCaja );
	}

	@Override
	public void save( Object object ) throws ApplicationException {
		trabajoDAO.save( object );
	}

	@Override
	public boolean validarAgrupar( final Jb trabajo ) {
		EstadoTrabajo estado = trabajo.estado();
		return !( EstadoTrabajo.BODEGA.equals( estado ) || EstadoTrabajo.ENTREGADO.equals( estado ) ||
				EstadoTrabajo.POR_ENVIAR_EXTERNO.equals( estado ) || EstadoTrabajo.ENVIADO_EXTERNO.equals( estado ) ||
				EstadoTrabajo.RECIBIDO_DESTINO_EXTERNO.equals( estado ) || EstadoTrabajo.CANCELADO.equals( estado ) );
	}

	@Override
	public boolean validarDesagrupar( Jb trabajo ) {
		return trabajo.getIdGrupo() != null;
	}

	@Override
	/**
	 * Este metodo valida si el jb:
	 * el trabajo capturado se encuentra en estado de Cancelado, Entregado o Recibe Sucursal. JB.estado = 'CN'/'TE'/'RS'.
	 */
	public void validarRecepcion( Jb jb ) {
		if ( jb != null ) {
			if ( EstadoConstants.ESTADO_CANCELADO.equalsIgnoreCase( jb.getEstado().getIdEdo().trim() ) ) {
				throw new IllegalStateException( "No se puede recibir un trabajo Cancelado." );
			} else if ( EstadoConstants.ESTADO_TRABAJO_ENTREGADO.equalsIgnoreCase( jb.getEstado().getIdEdo().trim() ) ) {
				throw new IllegalStateException( "No se puede recibir un trabajo Entregado." );
			} else if ( EstadoConstants.ESTADO_RECIBE_SUCURSAL.equalsIgnoreCase( jb.getEstado().getIdEdo().trim() ) ) {
				throw new IllegalStateException( "El trabajo ya fue recibido." );
			}
		} else {
			throw new IllegalStateException( "La Rx no existe." );
		}
	}

	@Override
	public boolean isNoEnviado( Jb jb ) throws ApplicationException {
		String status = jb.getEstado().getIdEdo().trim();
		if ( StringUtils.isNotBlank( status ) ) {
			return EstadoConstants.ESTADO_POR_ENVIAR.equalsIgnoreCase( status ) || EstadoConstants.ESTADO_NO_ENVIAR.equalsIgnoreCase( status );
		}
		return false;
	}

	@Override
	public Jb isGrupo( Jb jb ) throws ApplicationException {
		if ( StringUtils.isNotBlank( jb.getIdGrupo() ) ) {
			return findById( jb.getIdGrupo() );
		}
		return null;
	}

	@Override
	public Articulos findArticuloByIdArtAndIdGeneric( Integer art, char generico ) throws ApplicationException {
		return trabajoDAO.findArticuloByIdArtAndIdGeneric( art, generico );
	}

	@Override
	public JbRoto getLastJbRotoByRx( String rx ) throws ApplicationException {
		return trabajoDAO.getLastJbRotoByRx( rx );
	}

	@Override
	public List<Object[]> findAllJbByEdosAndFecha() throws ApplicationException {
		return trabajoDAO.findJbJbTrackByEdosFecha();
	}

	@Override
	public void trabajoSatisfactorio( Jb jb, Integer idViaje ) throws ApplicationException {
		String rx = jb.getRx();
		String atendio = jb.getEmpAtendio();
		jb.estado( EstadoTrabajo.RECIBE_SUCURSAL );
		jb.setVolverLlamar( null );
		trabajoDAO.save( jb );
		Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
		JbTrack track = new JbTrack();
		track.setEstado( EstadoTrabajo.RECIBE_SUCURSAL.codigo() );
		track.setEmpleado( empleado.getIdEmpleado() );
		track.setFecha( new Timestamp( System.currentTimeMillis() ) );
		track.setIdViaje( "" );
		track.setIdMod( "" );
		track.setObservaciones( "Viaje " + idViaje );
		track.setRx( rx );
		trabajoDAO.save( track );

		if ( jb.getIdGrupo() != null ) {
			String idGrupo = jb.getIdGrupo();
			List<Jb> jobs = jbGrupoDAO.listaTrabajosEnGrupo( idGrupo );
			Jb grupo = trabajoDAO.findById( idGrupo );
			boolean visitados = false;
			boolean todosRecibidos = true;
			JbLlamada llamadaGrupo = findJbLlamadaById( idGrupo );
			if ( llamadaGrupo != null ) {
				trabajoDAO.delete( llamadaGrupo );
			}
			for ( Jb job : jobs ) {
				visitados = true;
				JbLlamada llamadaJob = findJbLlamadaById( job.getRx() );
				if ( llamadaJob != null ) {
					trabajoDAO.delete( llamadaJob );
				}
				todosRecibidos &= job.estado().equals( EstadoTrabajo.RECIBE_SUCURSAL );
			}
			if ( visitados && todosRecibidos ) {
				grupo.estado( EstadoTrabajo.RECIBE_SUCURSAL );
				trabajoDAO.save( grupo );
				llamadaGrupo = new JbLlamada();
				llamadaGrupo.setNumLlamada( jb.getNumLlamada() );
				llamadaGrupo.setRx( idGrupo );
				llamadaGrupo.setFecha( new Timestamp( System.currentTimeMillis() ) );
				llamadaGrupo.setEstado( EstadoConstants.ESTADO_PENDIENTE );
				llamadaGrupo.setContesto( null );
				llamadaGrupo.setEmpAtendio( atendio );
				llamadaGrupo.setEmpLlamo( null );
				llamadaGrupo.setTipo( Constants.ENTREGAR );
				llamadaGrupo.setObs( null );
				llamadaGrupo.setGrupo( true );
				llamadaGrupo.setIdMod( empleado.getIdEmpleado() );
				trabajoDAO.save( llamadaGrupo );
			}
		} else {
            generaLlamada(jb, empleado);
        }

		if ( "EXT".equalsIgnoreCase( jb.getEmpAtendio().trim() ) ) {
			recepcionBusiness.insertarAcuseRecepcionSatisfactoria( rx );
		}
	}

    public Boolean generaLlamada(Jb jb, Empleado empleado) throws ApplicationException {

        JbLlamada llamada = findJbLlamadaById( jb.getRx() );
        FormaContacto formaContacto = formaContactoDAO.obtenPorRx( jb.getRx() );

        if ( formaContacto != null ) {
            if ( formaContacto.getTipoContacto().getIdTipoContacto() == 3 ) {

                if ( llamada != null ) {
                    trabajoDAO.delete(llamada);
                    llamada = null;
                }

                return false;
            }
        }

        if ( jb.getNoLlamar() == null ) {
            if (llamada != null) {
//                trabajoDAO.delete(llamada);
//                llamada = null;
            }
        }else{
            if ( jb.getNoLlamar() == true  ) {
                if (llamada != null) {
                    trabajoDAO.delete(llamada);
                    llamada = null;
                }
                return false;
            }
        }

        if ( llamada == null )
            llamada = new JbLlamada();

        llamada.setRx(jb.getRx());
        llamada.setNumLlamada(jb.getNumLlamada());
        llamada.setFecha(new Timestamp(System.currentTimeMillis()));
        llamada.setEstado(EstadoTrabajo.PENDIENTE.codigo());
        llamada.setContesto(null);
        llamada.setEmpAtendio(jb.getEmpAtendio());
        llamada.setEmpLlamo(null);
        llamada.setTipo(Constants.ENTREGAR);
        llamada.setObs(null);
        llamada.setGrupo(false);
        llamada.setIdMod(empleado.getIdEmpleado());
        trabajoDAO.save(llamada);

        return true;
    }

	@Override
	public void desvincular( String rx ) throws ApplicationException {
		if ( StringUtils.isNotBlank( rx ) ) {
			Jb job = trabajoDAO.findById( rx );
			if ( job != null && StringUtils.isNotBlank( job.getIdGrupo() ) ) {
				Jb grupo = trabajoDAO.findById( job.getIdGrupo() );
				Empleado emp = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
				JbTrack jbTrack = new JbTrack();
				jbTrack.setEmpleado( emp.getIdEmpleado() );
				jbTrack.setEstado( EstadoTrabajo.DESAGRUPADO.codigo() );
				jbTrack.setFecha( new Timestamp( System.currentTimeMillis() ) );
				jbTrack.setIdMod( Constants.CERO_STRING );
				jbTrack.setRx( job.getRx() );
				jbTrack.setObservaciones( grupo.getRx() + " - " + grupo.getCliente() );
				job.setIdGrupo( null );
				trabajoDAO.saveJbAndTrack( job, jbTrack );
				actualizarGrupo( grupo );
			}
		}
	}

	private void actualizarGrupo( Jb grupo ) throws ApplicationException {
		int tamanio = contactoViewDAO.countJbByGrupo( grupo.getRx() );
		List<Jb> jbs = contactoViewDAO.findJbByGrupo( 0, tamanio, grupo.getRx() );

		if ( jbs.isEmpty() ) {
			JbLlamada llamada = findJbLlamadaById( grupo.getRx() );
			if ( llamada != null ) {
				trabajoDAO.delete( llamada );
			}
			trabajoDAO.delete( grupo );
			return;
		}

		BigDecimal saldo = BigDecimal.ZERO;
		boolean todosRecibidos = true;
		Date fechaPromesaMaxima = null;
		Date fechaVolverLlamarMaxima = null;

		for ( Jb tmp : jbs ) {
			// Obtiene si todos los trabajos han sido recibidos
			if ( !tmp.getEstado().getIdEdo().trim().equals( EstadoConstants.ESTADO_RECIBE_SUCURSAL ) ) {
				todosRecibidos = false;
			}
			// Suma los saldos
			saldo = saldo.add( tmp.getSaldo() );
			// Obtiene la fecha promesa maxima
			if ( fechaPromesaMaxima == null && tmp.getFechaPromesa() != null ) {
				fechaPromesaMaxima = tmp.getFechaPromesa();
			} else if ( fechaPromesaMaxima != null && tmp.getFechaPromesa() != null && fechaPromesaMaxima.before( tmp.getFechaPromesa() ) ) {
				fechaPromesaMaxima = tmp.getFechaPromesa();
			}
			// Obtiene la fecha volver llamar maxima
			if ( fechaVolverLlamarMaxima == null && tmp.getVolverLlamar() != null ) {
				fechaVolverLlamarMaxima = tmp.getVolverLlamar();
			} else if ( fechaVolverLlamarMaxima != null && tmp.getVolverLlamar() != null && fechaVolverLlamarMaxima.before( tmp.getVolverLlamar() ) ) {
				fechaVolverLlamarMaxima = tmp.getVolverLlamar();
			}
		}

		if ( todosRecibidos ) {
			grupo.setEstado( new JbEstado( EstadoConstants.ESTADO_RECIBE_SUCURSAL ) );
		} else if ( grupo.getEstado().getIdEdo().trim().equals( EstadoConstants.ESTADO_RECIBE_SUCURSAL ) ) {
			grupo.setEstado( new JbEstado( EstadoConstants.ESTADO_PENDIENTE ) );
		}

		grupo.setFechaPromesa( fechaPromesaMaxima );
		grupo.setVolverLlamar( fechaVolverLlamarMaxima );
		grupo.setFechaVenta( jbs.get( 0 ).getFechaVenta() );
		save( grupo );
		// El saldo debe de tratarse de forma diferente por que la columna es de
		// tipo money
		trabajoDAO.updateSaldo( grupo.getRx(), saldo );
	}

	@Override
	public void agregarRxAGrupo( Jb jb, String idGrupo ) throws ApplicationException {
		if ( jb != null && StringUtils.isNotBlank( idGrupo ) ) {
			jb.setIdGrupo( idGrupo );
			String rx = jb.getRx();
			Empleado emp = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
			Jb grupo = trabajoDAO.findById( idGrupo );
			JbLlamada llamada = trabajoDAO.findJbLlamadaById( rx );
			JbTrack jbTrack = new JbTrack();
			jbTrack.setEmpleado( emp.getIdEmpleado() );
			jbTrack.setEstado( EstadoTrabajo.AGRUPADO.codigo() );
			jbTrack.setFecha( new Timestamp( System.currentTimeMillis() ) );
			jbTrack.setIdMod( Constants.CERO_STRING );
			jbTrack.setRx( rx );
			jbTrack.setObservaciones( idGrupo + " - " + grupo.getCliente() );

			trabajoDAO.saveJbAndTrack( jb, jbTrack );
			actualizarGrupo( grupo );
			FormaContacto formaContactoGrupo = contactoViewDAO.obtenFormaContacto( idGrupo );
			FormaContacto formaContactoTrabajo = contactoViewDAO.obtenFormaContacto( rx );
			if ( formaContactoGrupo == null && formaContactoTrabajo != null ) {
				formaContactoGrupo = new FormaContacto();
				formaContactoGrupo.setRx( idGrupo );
				formaContactoGrupo.setIdCliente( formaContactoTrabajo.getIdCliente() );
				formaContactoGrupo.setTipoContacto( formaContactoTrabajo.getTipoContacto() );
				formaContactoGrupo.setContacto( formaContactoTrabajo.getContacto() );
				formaContactoGrupo.setObservaciones( formaContactoTrabajo.getObservaciones() );
                formaContactoGrupo.setFechaMod( formaContactoTrabajo.getFechaMod() );
                formaContactoGrupo.setIdSucursal( formaContactoTrabajo.getIdSucursal() );
				trabajoDAO.save( formaContactoGrupo );
			}
			if ( llamada != null ) {
				trabajoDAO.delete( llamada );
			}
		}
	}

	@Override
	public void saveOrUpdateDeleteObjectList( Object[] saveUpdateObjects, Object[] deleteObjects ) throws ApplicationException {
		trabajoDAO.saveOrUpdateDeleteObjectList( saveUpdateObjects, deleteObjects );
	}

	@Override
	public List<EstadoRepublica> getAllEstadoRepublica() {
		try {
			return trabajoDAO.getAllEstadoRepublica();
		} catch( ApplicationException e ) {
			log.error( "Error al obtener los Estados de la Republica" );
		}
		return new ArrayList<EstadoRepublica>();
	}

	@Override
	public List<Municipio> findAllDelMunByIdEstadoAndLocalidad( String estado, String localidad ) {
		try {
			return trabajoDAO.findAllDelMunByIdEstadoAndLocalidad( estado, localidad );
		} catch ( DAOException e ) {
			log.error( "Error al obtener municipios a partir del estado" );
		}
		return new ArrayList<Municipio>();
	}

	@Override
	public List<Codigo> findAllCodigoByIdEstadoAndLocalidad( String estado, String localidad ) {
		try {
			return trabajoDAO.findAllCodigoByIdEstadoAndLocalidad( estado, localidad );
		} catch ( DAOException e ) {
			log.error( "Error al obtener Codigos por estado y localidad" );
		}
		return new ArrayList<Codigo>();
	}

	@Override
	public List<Dominios> getAllDominios() {
		try {
			return trabajoDAO.getAllDominios();
		} catch ( DAOException e ) {
			log.error( "Error al obtener los Dominios" );
		}
		return new ArrayList<Dominios>();
	}

	@Override
	public String findLastEstadoFromJbTrackWhereLlamadaNotNA( String rx ) throws ApplicationException {
		return trabajoDAO.findLastEstadoFromJbTrackWhereLlamadaNotNA( rx );
	}

	@Override
	public void enviarOrdenDeServicioABodega( Jb os, Empleado empleado ) throws ApplicationException {
		os.setEstado( new JbEstado( EstadoConstants.ESTADO_BODEGA ) );
		JbTrack track = new JbTrack();
		track.setIdMod( empleado.getIdEmpleado() );
		track.setEmpleado( empleado.getIdEmpleado() );
		track.setEstado( EstadoTrabajo.BODEGA.codigo() );
		track.setObservaciones( "ENVIADO A BODEGA" );
		track.setRx( os.getRx() );
		track.setFecha( new Timestamp( System.currentTimeMillis() ) );
		saveJbAndTrack( os, track );
		// Si existe registro en JB_LLAMADA para esa RX, se elimina el registro.
		JbLlamada llamada = findJbLlamadaById( os.getRx() );
		if ( llamada != null )
			trabajoDAO.delete( llamada );
	}

	@Override
	public List<Codigo> findAllCodigoByCodigo( String codigo ) {
		try {
			return trabajoDAO.findAllCodigoByCodigo( codigo );
		} catch ( DAOException e ) {
			log.error( "Error al obtener los codigos a partir del codigo postal" );
		}
		return new ArrayList<Codigo>();
	}

	@Override
	public void updateSaldo( final String rx, final BigDecimal saldo ) throws ApplicationException {
		trabajoDAO.updateSaldo( rx, saldo );
	}

	@Override
	public JbNota findJbNota( int id ) throws ApplicationException {
		return (JbNota) trabajoDAO.findByPrimaryKey( JbNota.class, id );
	}

	public String obtenSaldoEnTexto( String rx ) {
		if ( StringUtils.isNotBlank( rx ) ) {
			try {
				Jb job = trabajoDAO.findById( rx );
				NotaVenta notaVenta = notaVentaDAO.obtenNotaVentaPorTrabajo( rx );
				if ( Pattern.matches( RX_TIPO_BASE, rx ) && notaVenta != null ) {
					BigDecimal ventaNeta = notaVenta.getVentaNeta();
					BigDecimal sumaPagos = notaVenta.getSumaPagos();
					Double saldo = ventaNeta.subtract( sumaPagos ).doubleValue();
					return ApplicationUtils.doubleToMoney( saldo );
				} else if ( job != null && job.getSaldo() != null ) {
					return job.getSaldoFormateado();
				}
			} catch ( DAOException e ) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Override
	public boolean puedeEnviarExterno( Jb trabajo ) {
		if ( trabajo != null ) {
			EstadoTrabajo estado = trabajo.estado();
			TipoTrabajo tipo = trabajo.tipo();
			boolean enEstadoValido = EstadoTrabajo.RECIBE_SUCURSAL.equals( estado ) || EstadoTrabajo.POR_ENVIAR.equals( estado ) || EstadoTrabajo.EN_PINO.equals( estado );
			boolean deTipoValido = TipoTrabajo.ORDEN_SERVICIO.equals( tipo ) || TipoTrabajo.GARANTIA.equals( tipo );
			return enEstadoValido && !deTipoValido;
		}
		return false;
	}

	@Override
	public boolean puedeEliminarExterno( Jb trabajo ) {
		if ( trabajo != null ) {
			EstadoTrabajo estado = trabajo.estado();
			return EstadoTrabajo.POR_ENVIAR_EXTERNO.equals( estado );
		}
		return false;
	}

	@Override
	public boolean puedeEntregarExterno( String rx ) {
		if ( rx != null ) {
			try {
				Jb trabajo = trabajoDAO.findById( rx );
				EstadoTrabajo estado = trabajo.estado();
				TipoTrabajo tipo = trabajo.tipo();
				return ( EstadoTrabajo.RECIBE_SUCURSAL.equals( estado ) && TipoTrabajo.EXTERNO.equals( tipo ) );
			} catch ( DAOException e ) {
				// TODO codigo para procesar la excepcion
			}
		}
		return false;
	}

	@Override
	public FormaContacto obtenerFormaTrabajoPorRx( String rx ) {
		try {
			return formaContactoDAO.obtenPorRx( rx );
		} catch ( Exception e ) {
			return null;
		}
	}

	@Override
	public List<TipoContacto> obtenerTiposFormaContacto() {
		try {
			return tipoFormaContactoDAO.obtenerTodos();
		} catch ( Exception e ) {
			log.error( "Error al obtener los tipos de forma de contacto" );
			return new ArrayList<TipoContacto>();
		}
	}

	@Override
	public Sucursal obtenerSucursalPorNombre( String nombre ) {
		return sucursalDAO.obtenerPorNombre( nombre );
	}

	@Override
	public String obtenerIdLocalidadPorCodigoPostal( String codigoPostal ) {
		return trabajoDAO.obtenerIdLocalidadPorCodigoPostal( codigoPostal );
	}

	@Override
	public String obtenerIdEstadoPorCodigoPostal( String codigoPostal ) {
		return trabajoDAO.obtenerIdEstadoPorCodigoPostal( codigoPostal );
	}
}

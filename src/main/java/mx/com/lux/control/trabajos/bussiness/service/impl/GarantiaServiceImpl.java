package mx.com.lux.control.trabajos.bussiness.service.impl;

import mx.com.lux.control.trabajos.bussiness.service.GarantiaService;
import mx.com.lux.control.trabajos.bussiness.service.TicketService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.ClienteService;
import mx.com.lux.control.trabajos.data.dao.GenericoDAO;
import mx.com.lux.control.trabajos.data.dao.JbGarantiaDAO;
import mx.com.lux.control.trabajos.data.dao.NotaVentaDAO;
import mx.com.lux.control.trabajos.data.dao.TipoAcuseDAO;
import mx.com.lux.control.trabajos.data.dao.envio.SucursalDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.ClienteDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.JbTrackDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.exception.ServiceException;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;
import mx.com.lux.control.trabajos.view.Session;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service( "garantiaService" )
public class GarantiaServiceImpl implements GarantiaService {

	private final Logger log = LoggerFactory.getLogger( GarantiaService.class );

	private final String GARANTIA_VALIDA_LENTE = "garantia_alta_len";
	private final String GARANTIA_ALTA_LENTE = "garantia_alta_len";
	private final String GARANTIA_VALIDA_ARMAZON = "garantia_valida_arm";
	private final String GARANTIA_ALTA_ARMAZON = "garantia_alta_arm";

	@Resource
	private TrabajoDAO trabajoDAO;

	@Resource
	private JbTrackDAO jbTrackDAO;

	@Resource
	private JbGarantiaDAO jbGarantiaDAO;

	@Resource
	private GenericoDAO genericoDAO;

	@Resource
	private TipoAcuseDAO tipoAcuseDAO;

	@Resource
	private SucursalDAO sucursalDAO;

	@Resource
	private ClienteDAO clienteDAO;

	@Resource
	private NotaVentaDAO notaVentaDAO;

	@Resource
	private TicketService ticketService;

    @Resource
    private ClienteService clienteService;

	@Override
	public List<Jb> buscarTrabajosGarantiaPorGarantiaPorCliente( final String garantia, final String cliente ) {
		return trabajoDAO.buscarTrabajoGarantiaPorRxPorCliente( garantia, cliente );
	}

	@Override
	public Integer contarTrabajosGarantiaPorGarantiaPorCliente( final String garantia, final String cliente ) {
		return trabajoDAO.contarPorRxPorCliente( garantia, cliente );
	}

	@Override
	public List<Generico> obtenerGenericosGarantias() {
		return genericoDAO.obtenerGenericosGarantias();
	}

	@Override
	public Map<ResultadoValidacion, String> validarGarantia( Generico generico, String factura, String idSucursal, String producto, String color, String idSucursalLocal, String idEmpleado ) {
		switch ( generico.getId() ) {
			case 'A':
				log.debug( "Validar Garantia de Armazon" );
				return validarGarantiaArmazon( factura, idSucursal, producto, color );
			case 'B':
				log.debug( "Validar Garantia de Lentes" );
				return validarGarantiaLente( factura, idSucursal, idSucursalLocal, idEmpleado );
			case 'D':
				log.debug( "Validar Garantia de Aparatos auditivos" );
				return null;
			default:
				// Este caso no se va a dar si funcionan las validaciones de la vista
				log.debug( "No se ha seleccionado un Tipo de Garantia válida" );
				Map<ResultadoValidacion, String> resultado = new HashMap<ResultadoValidacion, String>();
				resultado.put( ResultadoValidacion.RESULTADO_NO_OK, "" );
				resultado.put( ResultadoValidacion.ERROR, "No se ha seleccionado un Tipo de Garantia válida" );
				return resultado;
		}
	}

	@Override
	public Map<ResultadoAlta, String> altaGarantia( Generico generico, String factura, String idSucursal, String idSucursalLocal, String idEmpleado, String producto, String color, String dejo, String danyo, String condiciones, Date fechaPromesa ) throws ServiceException {
		try {
			switch ( generico.getId() ) {
				case 'A':
					log.debug( "Alta Garantia de Armazon" );
					Map<ResultadoAlta, String> resultadoWebArmazon = altaWebGarantiaArmazon( factura, idSucursal, idSucursalLocal, idEmpleado, producto, color, danyo );
					if ( resultadoWebArmazon.containsKey( ResultadoAlta.ALTA_OK ) ) {
						String idGarantia = resultadoWebArmazon.get( ResultadoAlta.ID_GARANTIA );
                        String idCliente = actualizarCliente( factura, idEmpleado, resultadoWebArmazon );
                        insertarGarantia( resultadoWebArmazon, idCliente, danyo, condiciones, producto, color, idSucursal, factura, idEmpleado, fechaPromesa, generico, dejo );
						insertarJb( resultadoWebArmazon, idCliente, factura, idEmpleado, producto, color, fechaPromesa );
						insertarTrack( idGarantia, idEmpleado, producto, color );

						ticketService.imprimeTicketGarantia( "G" + idGarantia );
                        ticketService.imprimeTicketGarantia( "G" + idGarantia );
					}
					return resultadoWebArmazon;
				case 'B':
					log.debug( "Alta Garantia de Lentes" );
					Map<ResultadoAlta, String> resultadoWeb = altaWebGarantiaLente( factura, idSucursal, idSucursalLocal, idEmpleado, producto, color, danyo );
					if ( resultadoWeb.containsKey( ResultadoAlta.ALTA_OK ) ) {
						String idGarantia = resultadoWeb.get( ResultadoAlta.ID_GARANTIA );
                        String idCliente = actualizarCliente( factura, idEmpleado, resultadoWeb );
                        insertarGarantia( resultadoWeb, idCliente, danyo, condiciones, producto, color, idSucursal, factura, idEmpleado, fechaPromesa, generico, dejo );
                        insertarJb( resultadoWeb, idCliente, factura, idEmpleado, producto, color, fechaPromesa );
						insertarTrack( idGarantia, idEmpleado, producto, color );

                        ticketService.imprimeTicketGarantia( "G" + idGarantia );
                        ticketService.imprimeTicketGarantia( "G" + idGarantia );
					}
					return resultadoWeb;
				case 'D':
					log.debug( "Alta Garantia de Aparatos auditivos" );
					return null;
				default:
					// Este caso no se va a dar si funcionan las validaciones de la vista
					log.debug( "No se ha seleccionado un Tipo de Garantia válida" );
					Map<ResultadoAlta, String> resultado = new HashMap<ResultadoAlta, String>();
					resultado.put( ResultadoAlta.ALTA_NO_OK, "" );
					resultado.put( ResultadoAlta.ERROR, "No se ha seleccionado un Tipo de Garantia válida" );
					return resultado;
			}
		} catch ( Exception e ) {
			throw new ServiceException( "Error al procesar el Alta de Garantia", e );
		}
	}

	private Map<ResultadoValidacion, String> validarGarantiaLente( final String factura, final String idSucursal, final String idSucursalLocal, final String idEmpleado ) {
		String url = tipoAcuseDAO.obtenerPorId( GARANTIA_VALIDA_LENTE ).getPagina();
		Map<ResultadoValidacion, String> resultado = new HashMap<ResultadoValidacion, String>();
		try {
			if ( StringUtils.isNotBlank( url ) ) {
				url = url + ensamblarArgumentoValidarLente( factura, idSucursal, idSucursalLocal, idEmpleado );
				log.debug( "Peticion Web Validar Garantia: " + url );
				SAXBuilder saxBuilder = new SAXBuilder();
				Document document = saxBuilder.build( new URL( url ) );
				Element xx = document.getRootElement();
				if ( xx.getName().equals( "XX" ) ) {
					String respuesta = StringUtils.deleteWhitespace( xx.getValue() );
					String[] datos = StringUtils.split( respuesta, "|" );
					if ( datos[0].equals( "0" ) ) {
						log.debug( "Error al validar Garantia de lente: " + datos[1] );
						resultado.put( ResultadoValidacion.RESULTADO_NO_OK, "" );
						resultado.put( ResultadoValidacion.ERROR, datos[1] );
					} else if ( datos[0].equals( "1" ) ) {
						log.debug( "Garantia Lente valida" );
						log.debug( "Producto: " + datos[1] );
						log.debug( "Nombre cliente: " + datos[2] );
						log.debug( "Id Cliente externo: " + datos[3] );
						log.debug( "Id Sucursal externo: " + datos[4] );
						log.debug( "Fecha factura: " + datos[5] );
						resultado.put( ResultadoValidacion.RESULTADO_OK, "" );
						resultado.put( ResultadoValidacion.PRODUCTO, datos[1] );
						resultado.put( ResultadoValidacion.COLOR, "" );
						resultado.put( ResultadoValidacion.CLIENTE, datos[2] );
						resultado.put( ResultadoValidacion.ID_CLIENTE, datos[3] );
						resultado.put( ResultadoValidacion.ID_SUCURSAL, datos[4] );
						resultado.put( ResultadoValidacion.FECHA_FACTURA, datos[5] );
					}
				} else {
					log.error( "Error en la respuesta del Servidor" );
					resultado.put( ResultadoValidacion.RESULTADO_NO_OK, "" );
					resultado.put( ResultadoValidacion.ERROR, "Error en la respuesta del Servidor" );
				}
			}
		} catch ( MalformedURLException e ) {
			log.error( "URL incorrecta: " + url );
			resultado.put( ResultadoValidacion.RESULTADO_NO_OK, "" );
			resultado.put( ResultadoValidacion.ERROR, "URL incorrecta: " + url );
		} catch ( IOException e ) {
			log.error( "Error al leer la respuesta web" );
			resultado.put( ResultadoValidacion.RESULTADO_NO_OK, "" );
			resultado.put( ResultadoValidacion.ERROR, "Error al leer la respuesta web" );
		} catch ( JDOMException e ) {
			log.error( "Error al procesar la respuesta del Servidor" );
			resultado.put( ResultadoValidacion.RESULTADO_NO_OK, "" );
			resultado.put( ResultadoValidacion.ERROR, "Error al procesar la respuesta del Servidor" );
		}
		return resultado;
	}

	private Map<ResultadoValidacion, String> validarGarantiaArmazon( String factura, String idSucursal, String producto, String color ) {
		String url = tipoAcuseDAO.obtenerPorId( GARANTIA_VALIDA_ARMAZON ).getPagina();
		Map<ResultadoValidacion, String> resultado = new HashMap<ResultadoValidacion, String>();
		try {
			if ( StringUtils.isNotBlank( url ) ) {
				url = url + ensamblarArgumentoValidarArmazon( factura, idSucursal, producto, color );
				log.debug( "Peticion Web Validar Armazon: " + url );

				URL urlUrl = new URL( url );
				BufferedReader in = new BufferedReader( new InputStreamReader( urlUrl.openStream(), "ISO-8859-1" ) );
				SAXBuilder saxBuilder = new SAXBuilder();

				Document document = saxBuilder.build( in );

				Element xx = document.getRootElement();
				if ( xx.getName().equals( "XX" ) ) {
					String respuesta = StringUtils.deleteWhitespace( xx.getValue() );
					String[] datos = StringUtils.split( respuesta, "|" );
					if ( datos[0].equals( "0" ) ) {
						log.debug( "Error al validar Garantia de armazon: " + datos[1] );
						resultado.put( ResultadoValidacion.RESULTADO_NO_OK, "" );
						resultado.put( ResultadoValidacion.ERROR, datos[1] );
					} else if ( datos[0].equals( "1" ) ) {
						log.debug( "Garantia Armazon valida" );
						log.debug( "Nombre cliente: " + datos[1] );
						log.debug( "Id Cliente externo: " + datos[2] );
						log.debug( "Id Sucursal externo: " + datos[3] );
						log.debug( "Fecha factura: " + datos[4] );
						resultado.put( ResultadoValidacion.RESULTADO_OK, "" );
						resultado.put( ResultadoValidacion.CLIENTE, datos[1] );
						resultado.put( ResultadoValidacion.ID_CLIENTE, datos[2] );
						resultado.put( ResultadoValidacion.ID_SUCURSAL, datos[3] );
						resultado.put( ResultadoValidacion.FECHA_FACTURA, datos[4] );
						resultado.put( ResultadoValidacion.PRODUCTO, producto );
						resultado.put( ResultadoValidacion.COLOR, color );
					}
				} else {
					log.error( "Error en la respuesta del Servidor" );
					resultado.put( ResultadoValidacion.RESULTADO_NO_OK, "" );
					resultado.put( ResultadoValidacion.ERROR, "Error en la respuesta del Servidor" );
				}
			}
		} catch ( MalformedURLException e ) {
			log.error( "URL incorrecta: " + url );
			resultado.put( ResultadoValidacion.RESULTADO_NO_OK, "" );
			resultado.put( ResultadoValidacion.ERROR, "URL incorrecta: " + url );
		} catch ( IOException e ) {
			log.error( "Error al leer la respuesta web: " + e.getMessage() );
			resultado.put( ResultadoValidacion.RESULTADO_NO_OK, "" );
			resultado.put( ResultadoValidacion.ERROR, "Error al leer la respuesta web" );
		} catch ( JDOMException e ) {
			log.error( "Error al procesar la respuesta del Servidor: " + e.getMessage() );
			resultado.put( ResultadoValidacion.RESULTADO_NO_OK, "" );
			resultado.put( ResultadoValidacion.ERROR, "Error al procesar la respuesta del Servidor" );
		} catch ( Exception e ) {
			resultado.put( ResultadoValidacion.RESULTADO_NO_OK, "" );
			resultado.put( ResultadoValidacion.ERROR, "Error al procesar la petición" );
		}
		return resultado;
	}

	private Map<ResultadoAlta, String> altaWebGarantiaLente( String factura, String idSucursal, String idSucursalLocal, String idEmpleado, String producto, String color, String danyo ) {
		String url = tipoAcuseDAO.obtenerPorId( GARANTIA_ALTA_LENTE ).getPagina();
		Map<ResultadoAlta, String> resultado = new HashMap<ResultadoAlta, String>();
		if ( StringUtils.isNotBlank( url ) ) {
			url = url + ensamblarArgumentoAltaLente( factura, idSucursal, idSucursalLocal, idEmpleado, producto, color, danyo );
			log.debug( "Peticion Web Alta Garantia: " + url );
			try {
				SAXBuilder saxBuilder = new SAXBuilder();
				Document document = saxBuilder.build( new URL( url ) );
				Element xx = document.getRootElement();
				if ( xx.getName().equals( "XX" ) ) {
					String respuesta = StringUtils.deleteWhitespace( xx.getValue() );
					String[] datos = StringUtils.split( respuesta, "|" );
					if ( datos[0].equals( "0" ) || datos[0].equals( "1" ) ) {
						log.error( "Error al dar de alta Garantia de lente: " );
						if ( datos.length == 2 ) {
							log.error( "Error: " + datos[1] );
							resultado.put( ResultadoAlta.ALTA_NO_OK, "" );
							resultado.put( ResultadoAlta.ERROR, datos[1] );
						} else {
							log.error( "Nombre: " + datos[1] );
							log.error( "Id Cliente externo: " + datos[2] );
							log.error( "Id Sucursal externo: " + datos[3] );
							log.error( "Fecha factura: " + datos[4] );
							resultado.put( ResultadoAlta.ALTA_NO_OK, "" );
							resultado.put( ResultadoAlta.ERROR, "Error al dar de alta Garantia de lente" );
							resultado.put( ResultadoAlta.CLIENTE, datos[1] );
							resultado.put( ResultadoAlta.ID_CLIENTE, datos[2] );
							resultado.put( ResultadoAlta.ID_SUCURSAL, datos[3] );
							resultado.put( ResultadoAlta.FECHA_FACTURA, datos[4] );
						}
					} else {
						log.debug( "Id Garantia: " + datos[0] );
						resultado.put( ResultadoAlta.ALTA_OK, "" );
						resultado.put( ResultadoAlta.ID_GARANTIA, datos[0] );
                        resultado.put( ResultadoAlta.CLIENTE, datos[1] );
                        resultado.put( ResultadoAlta.ID_CLIENTE, datos[2] );
                        resultado.put( ResultadoAlta.ID_SUCURSAL, datos[3] );
                        resultado.put( ResultadoAlta.FECHA_FACTURA, datos[4] );
					}
				} else {
					log.error( "Error en la respuesta del Servidor" );
					resultado.put( ResultadoAlta.ALTA_NO_OK, "" );
					resultado.put( ResultadoAlta.ERROR, "Error en la respuesta del Servidor" );
				}
			} catch ( MalformedURLException e ) {
				log.error( "URL incorrecta: " + url + " : " + e.getMessage() );
				resultado.put( ResultadoAlta.ALTA_NO_OK, "" );
				resultado.put( ResultadoAlta.ERROR, "URL incorrecta" );
			} catch ( IOException e ) {
				log.error( "Error al leer la respuesta web: " + e.getMessage() );
				resultado.put( ResultadoAlta.ALTA_NO_OK, "" );
				resultado.put( ResultadoAlta.ERROR, "Error al procesar la respuesta del Servidor" );
			} catch ( JDOMException e ) {
				log.error( "Error al procesar la respuesta del Servidor " );
				resultado.put( ResultadoAlta.ALTA_NO_OK, "" );
				resultado.put( ResultadoAlta.ERROR, "Error al procesar la respuesta del Servidor" );
			}
		}
		return resultado;
	}

	private Map<ResultadoAlta, String> altaWebGarantiaArmazon( String factura, String idSucursal, String idSucursalLocal, String idEmpleado, String producto, String color, String danyo ) {
		String url = tipoAcuseDAO.obtenerPorId( GARANTIA_ALTA_ARMAZON ).getPagina();
		Map<ResultadoAlta, String> resultado = new HashMap<ResultadoAlta, String>();
		if ( StringUtils.isNotBlank( url ) ) {
			url = url + ensamblarArgumentoAltaArmazon( factura, idSucursal, idSucursalLocal, idEmpleado, producto, color, danyo );
			log.debug( "Peticion Web Alta Garantia: " + url );
			try {
				SAXBuilder saxBuilder = new SAXBuilder();
				Document document = saxBuilder.build( new URL( url ) );
				Element xx = document.getRootElement();
				if ( xx.getName().equals( "XX" ) ) {
					String respuesta = StringUtils.deleteWhitespace( xx.getValue() );
					String[] datos = StringUtils.split( respuesta, "|" );
					if ( datos[0].equals( "0" ) || datos[0].equals( "1" ) ) {
						log.error( "Error al dar de alta Garantia de armazon: " );
						log.error( "Nombre: " + datos[1] );
						log.error( "Id Cliente externo: " + datos[2] );
						log.error( "Id Sucursal externo: " + datos[3] );
						log.error( "Fecha factura: " + datos[4] );
						resultado.put( ResultadoAlta.ALTA_NO_OK, "" );
						resultado.put( ResultadoAlta.ERROR, "Error al dar de alta Garantia de armazon" );
						resultado.put( ResultadoAlta.CLIENTE, datos[1] );
						resultado.put( ResultadoAlta.ID_CLIENTE, datos[2] );
						resultado.put( ResultadoAlta.ID_SUCURSAL, datos[3] );
						resultado.put( ResultadoAlta.FECHA_FACTURA, datos[4] );
					} else {
						log.debug( "Id Garantia: " + datos[0] );
						resultado.put( ResultadoAlta.ALTA_OK, "" );
						resultado.put( ResultadoAlta.ID_GARANTIA, datos[0] );
                        resultado.put( ResultadoAlta.CLIENTE, datos[1] );
                        resultado.put( ResultadoAlta.ID_CLIENTE, datos[2] );
                        resultado.put( ResultadoAlta.ID_SUCURSAL, datos[3] );
                        resultado.put( ResultadoAlta.FECHA_FACTURA, datos[4] );
					}
				} else {
					log.error( "Error en la respuesta del Servidor" );
					resultado.put( ResultadoAlta.ALTA_NO_OK, "" );
					resultado.put( ResultadoAlta.ERROR, "Error en la respuesta del Servidor" );
				}
			} catch ( MalformedURLException e ) {
				log.error( "URL incorrecta: " + url + " : " + e.getMessage() );
				resultado.put( ResultadoAlta.ALTA_NO_OK, "" );
				resultado.put( ResultadoAlta.ERROR, "URL incorrecta" );
			} catch ( IOException e ) {
				log.error( "Error al leer la respuesta web: " + e.getMessage() );
				resultado.put( ResultadoAlta.ALTA_NO_OK, "" );
				resultado.put( ResultadoAlta.ERROR, "Error al procesar la respuesta del Servidor" );
			} catch ( JDOMException e ) {
				log.error( "Error al procesar la respuesta del Servidor " );
				resultado.put( ResultadoAlta.ALTA_NO_OK, "" );
				resultado.put( ResultadoAlta.ERROR, "Error al procesar la respuesta del Servidor" );
			}
		}
		return resultado;
	}

	private String ensamblarArgumentoValidarLente( final String factura, final String idSucursal, final String idSucursalLocal, final String idEmpleado ) {
		StringBuilder arg = new StringBuilder( "?arg=" );
		arg.append( factura ).append( "|" );
		arg.append( idSucursal ).append( "||" );
		arg.append( idSucursalLocal ).append( "|" );
		arg.append( StringUtils.isNotBlank( idEmpleado ) ? idEmpleado.trim() : "" ).append( "|" );
		arg.append( "2|v" );
		return arg.toString();
	}

	private String ensamblarArgumentoAltaLente( final String factura, final String idSucursal, final String idSucursalLocal, final String idEmpleado, final String producto, final String color, final String danyo ) {
		StringBuilder arg = new StringBuilder( "?arg=" );
		arg.append( factura ).append( "|" );
		arg.append( idSucursal ).append( "|" );
		arg.append( producto ).append( "|" );
		arg.append( color ).append( "|" );
		arg.append( danyo ).append( "|" );
		arg.append( idSucursalLocal ).append( "|" );
		arg.append( StringUtils.isNotBlank( idEmpleado ) ? idEmpleado.trim() : "" ).append( "|" );
		arg.append( "2|c" );
		return arg.toString();
	}

	private String ensamblarArgumentoValidarArmazon( String factura, String idSucursal, String producto, String color ) {
		StringBuilder arg = new StringBuilder( "?arg=" );
		arg.append( factura ).append( "|" );
		arg.append( idSucursal ).append( "|" );
		arg.append( producto ).append( "|" );
		arg.append( color );
		return arg.toString();
	}

	private String ensamblarArgumentoAltaArmazon( String factura, String idSucursal, String idSucursalLocal, String idEmpleado, String producto, String color, final String danyo ) {
		StringBuilder arg = new StringBuilder( "?arg=" );
		arg.append( factura ).append( "|" );
		arg.append( idSucursal ).append( "|" );
		arg.append( producto ).append( "|" );
		arg.append( color ).append( "|" );
		arg.append( danyo ).append( "|" );
		arg.append( idSucursalLocal ).append( "|" );
		arg.append( StringUtils.isNotBlank( idEmpleado ) ? idEmpleado.trim() : "" ).append( "|" );
		arg.append( "2|" );
		return arg.toString();
	}

    private void insertarGarantia( Map<ResultadoAlta, String> garant, String idCliente, String danyo, String condiciones, String producto, String color, String idSucursal, String factura, String idEmpleado, Date fechaPromesa, Generico generico, String dejo ) throws DAOException {
		NotaVenta notaVenta = notaVentaDAO.obtenNotaVentaPorTrabajo( factura );
		Cliente cliente = clienteDAO.obtenCliente( Integer.parseInt( idCliente ) );

		String tipoGarantia = "";
		switch ( generico.getId() ) {
			case 'A':
				tipoGarantia = "ARMAZON";
				break;
			case 'B':
				tipoGarantia = "LENTE";
				break;
		}

        DateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );
        Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
        Sucursal sucursal = empleado.getSucursal();

		JbGarantia garantia = new JbGarantia();
		garantia.setId( Integer.parseInt( garant.get( ResultadoAlta.ID_GARANTIA ) ) );
        if( garant.get( ResultadoAlta.ID_SUCURSAL ).equals( sucursal.getCentroCostos() ) ){
            garantia.setFechaFactura( notaVenta.getFechaHoraFactura() );
        } else {
            try{
                garantia.setFechaFactura( df.parse( garant.get( ResultadoAlta.FECHA_FACTURA ) ) );
            } catch ( ParseException ex ){
                log.error( ex.toString() );
            }
        }
        garantia.setIdCliente( Integer.toString( cliente.getIdCliente() ) );
        garantia.setCliente( cliente.getNombreCompleto( false ) );
		garantia.setRazon( danyo );
		garantia.setCondicion( condiciones );
		garantia.setArmazon( producto );
		garantia.setColor( color );
		garantia.setSucursal( idSucursal );
		garantia.setFactura( factura );
        garantia.setEmpleado( idEmpleado );
		garantia.setFechaPromesa( fechaPromesa );
		garantia.setFecha( new Timestamp( new Date().getTime() ) );
		garantia.setTipoGarantia( tipoGarantia );
		garantia.setDejo( dejo );
		garantia.setIdMod( "0" );
		jbGarantiaDAO.save( garantia );
	}

	private void insertarJb( Map<ResultadoAlta, String> garant, String idCliente, String factura, String idEmpleado, String producto, String color, Date fechaPromesa ) throws DAOException {
		NotaVenta notaVenta = notaVentaDAO.obtenNotaVentaPorTrabajo( factura );
        Cliente cliente = clienteDAO.obtenCliente( Integer.parseInt( idCliente ) );

        Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
        Sucursal sucursal = empleado.getSucursal();

		Jb jb = new Jb();
		jb.setRx( "G" + garant.get( ResultadoAlta.ID_GARANTIA ) );
		jb.estado( EstadoTrabajo.POR_ENVIAR );
        jb.setIdCliente( Integer.toString( cliente.getIdCliente() ) );
        jb.setCliente( cliente.getNombreCompleto( false ) );
		jb.setEmpAtendio( idEmpleado );
		jb.setNumLlamada( 0 );
		jb.setMaterial( producto + " " + StringUtils.defaultIfBlank( color, "" ) );
		jb.setSurte( "" );
		jb.setSaldo( BigDecimal.ZERO );
		jb.setJbTipo( "GAR" );
		jb.setFechaPromesa( fechaPromesa );
		jb.setNoLlamar( false );
		jb.setFechaVenta( new Timestamp( new Date().getTime() ) );
		jb.setFechaMod( new Timestamp( new Date().getTime() ) );
		jb.setIdMod( "0" );
        trabajoDAO.save( jb );
	}

	private void insertarTrack( String factura, String idEmpleado, String producto, String color ) throws DAOException {
		JbTrack track = new JbTrack();
		track.setRx( "G" + factura );
		track.setEstado( EstadoTrabajo.POR_ENVIAR.codigo() );
		track.setObservaciones( producto + " " + StringUtils.defaultIfBlank( color, "" ) );
		track.setEmpleado( idEmpleado );
		jbTrackDAO.save( track );
	}

	private String actualizarCliente( String factura, String idEmpleado, Map<ResultadoAlta, String> garant ) throws DAOException {
		NotaVenta notaVenta = notaVentaDAO.obtenNotaVentaPorTrabajo( factura );
        Cliente cliente = new Cliente();
		//Assert.notNull( notaVenta, "No existe Nota venta para la factura " + factura );

        Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
        Sucursal sucursal = empleado.getSucursal();

        if( sucursal.getCentroCostos().equals( garant.get( ResultadoAlta.ID_SUCURSAL ) ) ){
            cliente = clienteDAO.obtenCliente( notaVenta.getIdCliente() );
        } else {
            String idCliente = garant.get( ResultadoAlta.ID_CLIENTE );
            String idSucursal = garant.get( ResultadoAlta.ID_SUCURSAL );
            try{
                Cliente client = clienteService.importarClienteExterno( idSucursal, idCliente );
                //Integer idClient = clienteDAO.generarIdCliente();
                //cliente.setIdCliente( idClient );
                cliente.setIdConvenio( client.getIdConvenio() );
                cliente.setTitulo( client.getTitulo() );
                cliente.setIdOftalmologo( client.getIdOftalmologo() );
                cliente.setTipoOft( client.getTipoOft() );
                cliente.setIdLocalidad( client.getIdLocalidad() );
                cliente.setIdEstado( client.getIdEstado() );
                cliente.setFechaAltaCli( client.getFechaAltaCli() );
                cliente.setTipoCli( client.getTipoCli() );
                cliente.setSexoCli( client.getSexoCli() );
                cliente.setApellidoPatCli( client.getApellidoPatCli() );
                cliente.setApellidoMatCli( client.getApellidoMatCli() );
                cliente.setFCasadaCli( client.getFCasadaCli() );
                cliente.setNombreCli( client.getNombreCli() );
                cliente.setRfcCli( client.getRfcCli() );
                cliente.setDireccionCli( client.getDireccionCli() );
                cliente.setColoniaCli( client.getColoniaCli() );
                cliente.setCodigo( client.getCodigo() );
                cliente.setTelCasaCli( client.getTelCasaCli() );
                cliente.setTelTrabCli( client.getTelTrabCli() );
                cliente.setExtTrabCli( client.getExtTrabCli() );
                cliente.setTelAdiCli( client.getTelAdiCli() );
                cliente.setExtAdiCli( client.getExtAdiCli() );
                cliente.setEmailCli( client.getEmailCli() );
                cliente.setSUsaAnteojos( client.getSUsaAnteojos() );
                cliente.setAvisar( client.getAvisar() );
                cliente.setIdSync( client.getIdSync() );
                cliente.setFechaMod( new Date() );
                cliente.setIdMod( "0" );
                cliente.setIdSucursal( sucursal.getIdSucursal() );
                cliente.setUdf1( client.getUdf1() );
                cliente.setUdf2( client.getUdf2() );
                cliente.setCliOri( client.getCliOri() );
                cliente.setUdf4( client.getUdf4() );
                cliente.setUdf5( client.getUdf5() );
                cliente.setUdf6( client.getUdf6() );
                cliente.setReceta( client.getReceta() );
                cliente.setObs( client.getObs() );
                cliente.setFechaNac( client.getFechaNac() );
                cliente.setCuc( client.getCuc() );
                cliente.setHoraAlta( client.getHoraAlta() );
                cliente.setFinado( client.getFinado() );
                cliente.setFechaImp( client.getFechaImp() );
                cliente.setModImp( client.isModImp() );
                cliente.setCalif( client.getCalif() );
                cliente.setFechaImpUpdate( client.getFechaImpUpdate() );
                cliente.setTipoImp( client.getTipoImp() );
                cliente.setHistCuc( client.getHistCuc() );
                cliente.setHistCli( client.getHistCli() );
            } catch( ApplicationException  e ) {
                log.error( "Error al obtener los datos del Cliente: " + idCliente );
                log.error( "Excepcion: " + e.getMessage() );
            }
        }
        cliente.setIdAtendio( idEmpleado );
        clienteDAO.save( cliente );

        return String.valueOf( cliente.getIdCliente() );
	}

	@Override
	public Boolean entregarGarantia( final String rx, final String observaciones, final String idEmpleado ) throws ApplicationException {
		log.info( "Entregar Garantia: " + rx );

		Jb trabajo = trabajoDAO.findById( rx );
		trabajo.estado( EstadoTrabajo.ENTREGADO );
		trabajoDAO.save( trabajo );

		JbTrack track = new JbTrack();
		track.setEstado( EstadoTrabajo.ENTREGADO.codigo() );
		track.setRx( rx );
		track.setObservaciones( observaciones );
		track.setEmpleado( idEmpleado );
		jbTrackDAO.save( track );

		JbLlamada llamada = trabajoDAO.findJbLlamadaById( rx );
		if ( llamada != null ) {
			trabajoDAO.delete( llamada );
		}

		return Boolean.TRUE;
	}

	@Override
	public Boolean desentregarGarantia( final String rx, final String observaciones, final String idEmpleado ) throws ApplicationException {
		log.info( "Desentregar Garantia: " + rx );

		JbTrack ultimoTrack = jbTrackDAO.obtenerUltimoTrackParaDesentregarGarantia( rx );

		Jb trabajo = trabajoDAO.findById( rx );
		trabajo.estado( EstadoTrabajo.parse( ultimoTrack.getEstado() ) );
		trabajoDAO.save( trabajo );

		JbTrack track = new JbTrack();
		track.setEstado( EstadoTrabajo.DESENTREGADO.codigo() );
		track.setRx( rx );
		track.setObservaciones( observaciones );
		track.setEmpleado( idEmpleado );
		track.setIdViaje( ultimoTrack.getIdViaje() );
		track.setIdMod( ultimoTrack.getIdMod() );
		jbTrackDAO.save( track );

		return Boolean.TRUE;
	}

	@Override
	public Boolean sePuedePonerEnBodega( final String rx ) {
		try {
			Jb trabajo = trabajoDAO.findById( rx );
			return EstadoTrabajo.RECIBE_SUCURSAL.equals( trabajo.estado() ) || EstadoTrabajo.RETENIDO.equals( trabajo.estado() );
		} catch ( DAOException e ) {
			log.error( "Error al comprobar si se puede colocar en Bodega" );
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean sePuedePonerEnBodega( final Jb trabajo ) {
		try {
			return EstadoTrabajo.RECIBE_SUCURSAL.equals( trabajo.estado() ) || EstadoTrabajo.RETENIDO.equals( trabajo.estado() );
		} catch ( Exception e ) {
			log.error( "Error al comprobar si se puede colocar en Bodega" );
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean sePuedeEntregar( final Jb trabajo ) {
		try {
			return !EstadoTrabajo.ENTREGADO.equals( trabajo.estado() );
		} catch ( Exception e ) {
			log.error( "Error al comprobar si se puede entregar" );
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean sePuedeDesentregar( final Jb trabajo ) {
		try {
			return EstadoTrabajo.ENTREGADO.equals( trabajo.estado() );
		} catch ( Exception e ) {
			log.error( "Error al comprobar si se puede desentregar" );
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean mandarABodega( final String rx, final String idEmpleado ) throws ApplicationException {
		log.info( "Envio a Bodega: " + rx );

		Jb trabajo = trabajoDAO.findById( rx );
		trabajo.estado( EstadoTrabajo.BODEGA );
		trabajoDAO.save( trabajo );

		JbTrack track = new JbTrack();
		track.setEstado( EstadoTrabajo.BODEGA.codigo() );
		track.setRx( rx );
		track.setObservaciones( "ENVIADO A BODEGA" );
		track.setEmpleado( idEmpleado );
		jbTrackDAO.save( track );

		JbLlamada llamada = trabajoDAO.findJbLlamadaById( rx );
		if ( llamada != null ) {
			trabajoDAO.delete( llamada );
		}

		return Boolean.TRUE;
	}


	@Override
	public Boolean existeCentroCostos( final String idCentroCostos ) {
		try {
			Sucursal sucursal = sucursalDAO.obtenerPorCentroCostos( idCentroCostos );
			return ( sucursal != null );
		} catch ( Exception e ) {
			log.error( "Error al comprobar si existe Centro de Costos: " + idCentroCostos );
		}
		return false;
	}

	@Override
	public Sucursal obtenerSucursalPorCentroCostos( final String idCentroCostos ) {
		try {
			return sucursalDAO.obtenerPorCentroCostos( idCentroCostos );
		} catch ( Exception e ) {
			log.error( "Error al comprobar si existe Centro de Costos: " + idCentroCostos );
		}
		return null;
	}

	@Override
	public JbGarantia obtenerGarantiaPorFactura( String rx ) {
		return jbGarantiaDAO.obtenerPorFactura( StringUtils.remove( rx, "G" ) );
	}

	@Override
	public JbGarantia obtenerGarantiaPorId( Integer idGarantia ) {
		return jbGarantiaDAO.obtenerPorId( idGarantia );
	}

	@Override
	public Boolean existeFactura( final String rx ) {
		try {
			Jb trabajo = trabajoDAO.findById( rx );
			return ( trabajo != null && EstadoTrabajo.ENTREGADO.equals( trabajo.estado() ) );
		} catch ( Exception e ) {
			log.error( "Error al comprobar si existe Factura: " + rx );
		}
		return false;
	}
}

package mx.com.lux.control.trabajos.bussiness.impl;

import mx.com.lux.control.trabajos.bussiness.EntregaExternoBusiness;
import mx.com.lux.control.trabajos.bussiness.RecepcionBusiness;
import mx.com.lux.control.trabajos.data.dao.AcuseDAO;
import mx.com.lux.control.trabajos.data.dao.ExternoDAO;
import mx.com.lux.control.trabajos.data.dao.PagoExtraDAO;
import mx.com.lux.control.trabajos.data.dao.PolizaDAO;
import mx.com.lux.control.trabajos.data.dao.envio.SucursalDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.JbTrackDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO;
import mx.com.lux.control.trabajos.data.vo.Acuses;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.JbExterno;
import mx.com.lux.control.trabajos.data.vo.Sucursal;
import mx.com.lux.control.trabajos.exception.ServiceException;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.view.Session;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component( "recepcionBusiness" )
public class RecepcionBusinessImpl implements RecepcionBusiness {

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
	public void insertarAcuseRecepcionSatisfactoria( final String rx ) throws ServiceException {
		try {
			Acuses acuse = new Acuses();
			acuse.setIdTipo( "X3" );
			acuse.setFechaCarga( new Date() );
			acuse.setContenido( "" );
			acuse.setIntentos( 0 );
			acuseDAO.save( acuse );

			acuse.setContenido( generarTextoAcuse( rx, acuse.getIdAcuse() ) );
			acuseDAO.save( acuse );
		} catch ( Exception e ) {
			throw new ServiceException( "Error al insertar Acuse", e );
		}
	}

	private String generarTextoAcuse( final String rx, final Integer idAcuse ) {
		JbExterno externo = externoDAO.obtenerPorRx( rx );
		Assert.notNull( externo, "No existe externo para la RX " + rx );
		Sucursal sucursalOrigen = sucursalDAO.obtenerPorNombre( externo.getOrigen() );
		Assert.notNull( sucursalOrigen, "No existe la Sucursal " + externo.getOrigen() );
		Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );

		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );

		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put( "rx", StringUtils.remove( rx.toUpperCase(), 'X' ) );
		datos.put( "fecha", sdf.format( new Date() ) );
		datos.put( "idSucursalOrigen", String.format( "%04d", sucursalOrigen != null ? sucursalOrigen.getIdSucursal() : 0 ) );
		datos.put( "idSucursalDestino", String.format( "%04d", empleado.getSucursal() != null ? empleado.getSucursal().getIdSucursal() : 0 ) );
		datos.put( "idAcuse", idAcuse );

		String acuse = VelocityEngineUtils.mergeTemplateIntoString( velocityEngine, "acuse-recepcion-satisfactoria.vm", "UTF-8", datos );
		log.debug( "Acuse recepcion externo: " + acuse );
		return acuse;
	}

}

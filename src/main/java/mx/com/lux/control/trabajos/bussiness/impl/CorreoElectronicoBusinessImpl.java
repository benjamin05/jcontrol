package mx.com.lux.control.trabajos.bussiness.impl;

import mx.com.lux.control.trabajos.bussiness.CorreoElectronicoBusiness;
import mx.com.lux.control.trabajos.data.dao.FormaContactoDAO;
import mx.com.lux.control.trabajos.data.dao.NotaVentaDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.view.Session;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component( "correoElectronicoBusiness" )
public class CorreoElectronicoBusinessImpl implements CorreoElectronicoBusiness {

	private final Logger log = Logger.getLogger( CorreoElectronicoBusiness.class );

	@Resource
	private JavaMailSender mailSender;

	@Resource
	FormaContactoDAO formaContactoDAO;

	@Resource
	TrabajoDAO trabajoDAO;

	@Resource
	NotaVentaDAO notaVentaDAO;

	@Resource
	private VelocityEngine velocityEngine;

	@Override
	public void enviarCorreoContacto( final String rx ) throws ApplicationException {
		FormaContacto formaContacto = formaContactoDAO.obtenPorRx( rx );
		if ( formaContacto != null && formaContacto.getTipoContacto().getIdTipoContacto() == 1 ) {
			// 1 es tipo contacto correo
			JbLlamada llamada = trabajoDAO.findJbLlamadaById( rx );
			Boolean esEntregar = llamada.getTipo().trim().equalsIgnoreCase( "ENTREGAR" ) ? true : false;
			Boolean esRetrasado = llamada.getTipo().trim().equalsIgnoreCase( "RETRASADO" ) ? true : false;

			String direccionPara = formaContacto.getContacto();
			String direccionDesde = "contacto@lux.mx";
			String asunto = "";
			if ( esEntregar ) {
				asunto = "Opticas Lux - Orden de trabajo lista";
			} else if ( esRetrasado ) {
				asunto = "Opticas Lux - Orden de trabajo retrasada";
			}
			enviarCorreoElectronico( direccionPara, direccionDesde, asunto, crearContenidoDelCorreo( rx, esEntregar, esRetrasado ) );
		} else {
			log.error( "No se ha enviado el correo para el trabajo " + rx + " porque la forma de contacto es otra." );
		}
	}

	private String crearContenidoDelCorreo( final String rx, final Boolean esEntregar, final Boolean esRetrasado ) throws DAOException {
		Jb trabajo = trabajoDAO.findById( rx );
		NotaVenta notaVenta = notaVentaDAO.obtenNotaVentaPorTrabajo( rx );
		String poliza = ( notaVenta != null && notaVenta.getPoliza() != null && BigDecimal.ZERO.compareTo( notaVenta.getPoliza() ) < 0 ) ? notaVenta.getPolizaFormateado() : null;
		if( poliza != null ) {
			log.debug( "La Nota Venta viene con una Poliza de " + poliza );
		}
		Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put( "rx", rx );
		datos.put( "cliente", trabajo.getCliente() );
		datos.put( "esGrupo", trabajo.esGrupo() );
		datos.put( "sucursal", empleado.getSucursal() );
		datos.put( "poliza", poliza );
		if ( esEntregar ) {
			return VelocityEngineUtils.mergeTemplateIntoString( velocityEngine, "notificacion-correo-electronico-entregar.vm", "UTF-8", datos );
		}
		if ( esRetrasado ) {
			return VelocityEngineUtils.mergeTemplateIntoString( velocityEngine, "notificacion-correo-electronico-retrasado.vm", "UTF-8", datos );
		}
		return "";
	}

	private void enviarCorreoElectronico( final String para, final String de, final String asunto, final String mensaje ) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare( MimeMessage mimeMessage ) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper( mimeMessage, true, "UTF-8" );
				message.setTo( new String[]{ para } );
				message.setSubject( asunto );
				message.setFrom( de );
				message.setText( mensaje, false );
			}
		};
		mailSender.send( preparator );
	}
}

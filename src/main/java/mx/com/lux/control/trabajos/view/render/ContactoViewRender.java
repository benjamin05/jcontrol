package mx.com.lux.control.trabajos.view.render;

import mx.com.lux.control.trabajos.data.vo.ContactoView;
import mx.com.lux.control.trabajos.util.DateUtils;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.EstadoConstants;
import mx.com.lux.control.trabajos.util.constants.RxConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.ContactoPropertyHelper;
import org.apache.commons.lang.StringUtils;

public class ContactoViewRender implements TableItemRender<ContactoView> {

	private static final String DATE_PATTERN = ApplicationPropertyHelper.getProperty( "app.format.date.date" );

	@Override
	public String[] getText( ContactoView contacto ) {
		String[] textoColumna = new String[8];
		textoColumna[0] = StringUtils.defaultIfEmpty( contacto.getRx(), Constants.CADENA_VACIA );
		textoColumna[1] = contacto.getCliente();
		textoColumna[2] = StringUtils.defaultIfEmpty( DateUtils.formatDate( contacto.getFechaHoraFactura(), DATE_PATTERN ), Constants.CADENA_VACIA );

		if ( contacto.getAtendio().startsWith( RxConstants.TIPO_EXTERNO ) ) {
			textoColumna[3] = ContactoPropertyHelper.getProperty( "contacto.constant.ext" );
		} else {
			textoColumna[3] = contacto.getAtendio();
		}

		textoColumna[4] = contacto.getTipoLlamada();
		textoColumna[5] = contacto.getDescripcionTipoContacto();

		if ( contacto.getEstadoLlamada().trim().equals( EstadoConstants.ESTADO_PENDIENTE ) ) {
			textoColumna[6] = ContactoPropertyHelper.getProperty( "contacto.estado.pendiente" );
		} else if ( contacto.getEstadoLlamada().trim().equals( EstadoConstants.ESTADO_NO_CONTESTO ) ) {
			textoColumna[6] = ContactoPropertyHelper.getProperty( "contacto.estado.no.contesto" );
		} else {
			textoColumna[6] = contacto.getEstadoLlamada();
		}

		textoColumna[7] = DateUtils.formatDate( contacto.getFechaPromesa(), DATE_PATTERN );

		return textoColumna;
	}
}

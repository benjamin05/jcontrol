package mx.com.lux.control.trabajos.view.render;

import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.util.DateUtils;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;

public class OrdenServicioRender implements TableItemRender<Jb> {

	@Override
	public String[] getText( Jb arg ) {
		String[] text = new String[4];
		text[0] = arg.getRx();
		text[1] = arg.getCliente();
		if ( arg.getFechaVenta() != null )
			text[2] = DateUtils.formatDate( arg.getFechaVenta(), ApplicationPropertyHelper.getProperty( "app.format.date.date" ) );
		else
			text[2] = Constants.CADENA_VACIA;
		text[3] = DateUtils.formatDate( arg.getFechaPromesa(), ApplicationPropertyHelper.getProperty( "app.format.date.date" ) );
		return text;
	}

}

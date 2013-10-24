package mx.com.lux.control.trabajos.view.render;

import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.util.DateUtils;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;

public class GruposRender implements TableItemRender<Jb> {

	@Override
	public String[] getText( Jb arg ) {
		return new String[]{ arg.getRx(), arg.getEstado().getIdEdo(), arg.getCliente(), arg.getFechaPromesa() != null ? DateUtils.formatDate( arg.getFechaPromesa(), ApplicationPropertyHelper.getProperty( "app.format.date.date" ) ) : "" };
	}

}

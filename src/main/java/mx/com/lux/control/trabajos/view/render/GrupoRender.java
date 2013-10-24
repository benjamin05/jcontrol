package mx.com.lux.control.trabajos.view.render;

import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;

import java.text.SimpleDateFormat;

public class GrupoRender implements TableItemRender<Jb> {

	@Override
	public String[] getText( Jb trabajo ) {
		SimpleDateFormat sdf = new SimpleDateFormat( ApplicationPropertyHelper.getProperty( "app.format.date.date" ) );
		String[] text = new String[6];
		text[0] = trabajo.getRx();
		text[1] = trabajo.getEstado().getIdEdo();
		text[2] = trabajo.getCliente();
		text[3] = trabajo.getMaterial();
		text[4] = trabajo.getSaldoFormateado();
		text[5] = trabajo.getFechaVenta() != null ? sdf.format( trabajo.getFechaVenta() ) : "";
		return text;
	}

	;
}

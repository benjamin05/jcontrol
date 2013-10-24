package mx.com.lux.control.trabajos.view.render;

import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.util.constants.EstadoConstants;

public class PorEnviarRender implements TableItemRender<Jb> {

	@Override
	public String[] getText( Jb arg ) {
		return new String[]{ arg.getEstado().getIdEdo().trim().equals( EstadoConstants.ESTADO_ROTO_POR_ENVIAR ) ? "R" + arg.getRx() : arg.getRx() };
	}

	;
}

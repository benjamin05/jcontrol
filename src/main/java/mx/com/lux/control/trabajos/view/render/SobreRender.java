package mx.com.lux.control.trabajos.view.render;

import mx.com.lux.control.trabajos.data.vo.JbSobre;

public class SobreRender implements TableItemRender<JbSobre> {

	@Override
	public String[] getText( JbSobre arg ) {
		return new String[]{ arg.getFolioSobre(), arg.getDest(), arg.getArea(), arg.getContenido() };
	}

}

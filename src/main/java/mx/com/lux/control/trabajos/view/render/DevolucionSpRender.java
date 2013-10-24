package mx.com.lux.control.trabajos.view.render;

import mx.com.lux.control.trabajos.data.vo.JbDev;

public class DevolucionSpRender implements TableItemRender<JbDev> {

	@Override
	public String[] getText( final JbDev jbDev ) {
		return new String[]{ jbDev.getDocumento() };
	}

}

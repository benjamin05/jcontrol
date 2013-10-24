package mx.com.lux.control.trabajos.view.render;

import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.util.DateUtils;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;

public class ConsultaRender implements TableItemRender<Jb> {

	public static final int RX_CELL = 0;
	public static final int CLIENTE_CELL = 1;
	public static final int ESTADO_CELL = 2;
	public static final int MODIFICACION_CELL = 3;
	public static final int ATENDIO_CELL = 4;
	public static final int PROMESA_CELL = 5;

	@Override
	public String[] getText( Jb job ) {
		String[] text = new String[6];
		text[RX_CELL] = job.getRx();
		text[CLIENTE_CELL] = job.getCliente();
		text[ESTADO_CELL] = job.getEstado().getDescr();
		text[MODIFICACION_CELL] = DateUtils.formatDate( job.getFechaMod(), ApplicationPropertyHelper.getProperty( "app.format.date.date" ) );
		text[ATENDIO_CELL] = job.getEmpAtendio();
		text[PROMESA_CELL] = DateUtils.formatDate( job.getFechaPromesa(), ApplicationPropertyHelper.getProperty( "app.format.date.date" ) );
		return text;
	}
}

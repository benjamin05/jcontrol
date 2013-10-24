package mx.com.lux.control.trabajos.view.render;

import mx.com.lux.control.trabajos.data.vo.TrackView;
import mx.com.lux.control.trabajos.util.DateUtils;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;

public class TrackViewRender implements TableItemRender<TrackView> {

	@Override
	public String[] getText( TrackView arg ) {
		return new String[]{ DateUtils.formatDate( arg.getId().getFecha(), ApplicationPropertyHelper.getProperty( "app.format.date.d.m" ) ), DateUtils.formatDate( arg.getId().getFecha(), ApplicationPropertyHelper.getProperty( "app.format.date.time" ) ), arg.getId().getEstado(), arg.getId().getObs(), arg.getId().getNombre_empleado() + " " + arg.getId().getAp_pat_empleado() + " " + arg.getId().getAp_mat_empleado() };
	}

}

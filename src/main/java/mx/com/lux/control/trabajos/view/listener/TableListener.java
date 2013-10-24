package mx.com.lux.control.trabajos.view.listener;

import mx.com.lux.control.trabajos.util.constants.EstadoConstants;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import org.apache.commons.lang.time.DateUtils;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;

import java.util.Date;

public class TableListener implements Listener {

	private static final String EDO_DESCR_EN_PINO = TrabajosPropertyHelper.getProperty( "jb.llamada.estado.en.pino" );
	private static final String EDO_DESCR_X1 = TrabajosPropertyHelper.getProperty( "estado.descr.x1" );
	private static final String EDO_DESCR_X2 = TrabajosPropertyHelper.getProperty( "estado.descr.x2" );
	private static final String EDO_DESCR_X3 = TrabajosPropertyHelper.getProperty( "estado.descr.x3" );
	private static final String EDO_DESCR_X4 = TrabajosPropertyHelper.getProperty( "estado.descr.x4" );
	private int columnaPromesa = -1;
	private int columnaEstadoDescr = -1;

	public TableListener() {
	}

	public TableListener( int columnaPromesa, int columnaEstadoDescr ) {
		this.columnaPromesa = columnaPromesa;
		this.columnaEstadoDescr = columnaEstadoDescr;
	}

	@Override
	public void handleEvent( Event ev ) {
		TableItem item = (TableItem) ev.item;
		if ( columnaEstadoDescr >= 0 && columnaPromesa >= 0 ) {
			String estadoDescr = item.getText( columnaEstadoDescr ).trim();
			String promesa = item.getText( columnaPromesa ).trim();
			try {
				Date date = DateUtils.parseDate( promesa, new String[]{ ApplicationPropertyHelper.getProperty( "app.format.date.date" ) } );
				if ( EstadoConstants.ESTADO_EN_PINO.equalsIgnoreCase( estadoDescr ) || ( EDO_DESCR_EN_PINO.equalsIgnoreCase( estadoDescr ) && new Date().compareTo( date ) > 0 ) ) {
					item.setBackground( GraphicConstants.FIELD_COLOR_ALERT );
				} else if ( EDO_DESCR_X1.equalsIgnoreCase( estadoDescr ) ) {
					item.setBackground( GraphicConstants.FILA_AZUL );
				} else if ( EDO_DESCR_X2.equalsIgnoreCase( estadoDescr ) ) {
					item.setBackground( GraphicConstants.FILA_AZUL );
				} else if ( EDO_DESCR_X3.equalsIgnoreCase( estadoDescr ) ) {
					item.setBackground( GraphicConstants.FILA_AZUL );
				} else if ( EDO_DESCR_X4.equalsIgnoreCase( estadoDescr ) ) {
					item.setBackground( GraphicConstants.FILA_AZUL );
				}
			} catch ( Exception ex ) {
				ex.printStackTrace();
			}
		}
	}
}

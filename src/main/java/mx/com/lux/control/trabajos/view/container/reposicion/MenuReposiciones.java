package mx.com.lux.control.trabajos.view.container.reposicion;

import mx.com.lux.control.trabajos.view.container.menu.OpcionesMenu;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import java.util.HashMap;
import java.util.Map;

public class
        MenuReposiciones {

	private static Map<OpcionesMenu.Opciones, MenuItem> opciones = new HashMap<OpcionesMenu.Opciones, MenuItem>();

	public static Menu construir( final Shell shell, final Control control ) {
		Menu menu = new Menu( control );
		OpcionesMenu.crearOpcionNuevaReposicion( shell, menu );
		OpcionesMenu.crearOpcionAgregarReposicion( shell, menu );
		OpcionesMenu.crearOpcionConsultarTrabajo( shell, menu );
		OpcionesMenu.crearOpcionDatosCliente( shell, menu );
		OpcionesMenu.crearOpcionDatosFactura( shell, menu );
		OpcionesMenu.crearOpcionInformacionLaboratorio( shell, menu );
		opciones.put( OpcionesMenu.Opciones.RETENER_TRABAJO, OpcionesMenu.crearOpcionRetener( shell, menu ) );
		opciones.put( OpcionesMenu.Opciones.DESRETENER_TRABAJO, OpcionesMenu.crearOpcionDesretener( shell, menu ) );
		OpcionesMenu.crearOpcionReprogramar( shell, menu );
		OpcionesMenu.crearOpcionNoContactar( shell, menu );
		opciones.put( OpcionesMenu.Opciones.AGRUPAR_TRABAJO, OpcionesMenu.crearOpcionCrearGrupo( shell, menu ) );
		opciones.put( OpcionesMenu.Opciones.DESAGRUPAR_TRABAJO, OpcionesMenu.crearOpcionDesvincular( shell, menu ) );
		return menu;
	}

	public static void calcularOpcionesActivas( final String rx ) {
		OpcionesMenu.validarOpcionRetener( opciones.get( OpcionesMenu.Opciones.RETENER_TRABAJO ), rx );
		OpcionesMenu.validarOpcionDesretener( opciones.get( OpcionesMenu.Opciones.DESRETENER_TRABAJO ), rx );
		OpcionesMenu.validarOpcionCrearGrupo( opciones.get( OpcionesMenu.Opciones.AGRUPAR_TRABAJO ), rx );
		OpcionesMenu.validarOpcionDesvincular( opciones.get( OpcionesMenu.Opciones.DESAGRUPAR_TRABAJO ), rx );
	}

}

package mx.com.lux.control.trabajos.util.constants;

import mx.com.lux.control.trabajos.util.properties.EnvironmentPropertyHelper;
import org.eclipse.swt.graphics.Color;

public class GraphicConstants {

	public static final Color FIELD_COLOR_NORMAL = new Color( null, 255, 255, 255 );
	public static final Color FIELD_COLOR_READONLY = new Color( null, Integer.valueOf( EnvironmentPropertyHelper.getProperty( "color.campo.readonly.rojo" ) ), Integer.valueOf( EnvironmentPropertyHelper.getProperty( "color.campo.readonly.verde" ) ), Integer.valueOf( EnvironmentPropertyHelper.getProperty( "color.campo.readonly.azul" ) ) );
	public static final Color FIELD_COLOR_INVALID = new Color( null, Integer.valueOf( EnvironmentPropertyHelper.getProperty( "color.campo.invalido.rojo" ) ), Integer.valueOf( EnvironmentPropertyHelper.getProperty( "color.campo.invalido.verde" ) ), Integer.valueOf( EnvironmentPropertyHelper.getProperty( "color.campo.invalido.azul" ) ) );
	public static final Color FIELD_COLOR_ALERT = new Color( null, Integer.valueOf( EnvironmentPropertyHelper.getProperty( "color.campo.alerta.rojo" ) ), Integer.valueOf( EnvironmentPropertyHelper.getProperty( "color.campo.alerta.verde" ) ), Integer.valueOf( EnvironmentPropertyHelper.getProperty( "color.campo.alerta.azul" ) ) );
	public static final Color filaParColor = new Color( null, Integer.valueOf( EnvironmentPropertyHelper.getProperty( "color.fila.par.rojo" ) ), Integer.valueOf( EnvironmentPropertyHelper.getProperty( "color.fila.par.verde" ) ), Integer.valueOf( EnvironmentPropertyHelper.getProperty( "color.fila.par.azul" ) ) );
	public static final Color filaImparColor = new Color( null, Integer.valueOf( EnvironmentPropertyHelper.getProperty( "color.fila.impar.rojo" ) ), Integer.valueOf( EnvironmentPropertyHelper.getProperty( "color.fila.impar.verde" ) ), Integer.valueOf( EnvironmentPropertyHelper.getProperty( "color.fila.impar.azul" ) ) );
	public static final Color FILA_AZUL = new Color( null, 153, 204, 255 );

}

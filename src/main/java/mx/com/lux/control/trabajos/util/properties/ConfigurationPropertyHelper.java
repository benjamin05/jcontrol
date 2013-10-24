/**
 *
 */
package mx.com.lux.control.trabajos.util.properties;

import java.util.ResourceBundle;

/**
 * @author saul fragoso
 */
public class ConfigurationPropertyHelper {

	private static ResourceBundle resourceBundle = ResourceBundle.getBundle( "mx.com.lux.control.trabajos.resources.Config" );
	;


	private static final String EMPTY_KEY = "";

	/**
	 * Metodo que devuelve el directorio
	 *
	 * @param key
	 * @return
	 */
	public static String getProperty( String key ) {
		try {
			String s = resourceBundle.getString( key );
			return s;
		} catch ( Exception e ) {
			e.printStackTrace();
			return EMPTY_KEY;
		}
	}

}

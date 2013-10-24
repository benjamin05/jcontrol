/**
 *
 */
package mx.com.lux.control.trabajos.util.properties;

import java.util.ResourceBundle;

/**
 * @author DGCM42
 */
public class RecepcionPropertyHelper {

	private static ResourceBundle resourceBundle = ResourceBundle.getBundle( "mx.com.lux.control.trabajos.resources.Recepcion" );
	private static final String EMPTY_KEY = "";

	static {

	}


	/**
	 * Metodo que devuelve el mensaje
	 *
	 * @param key
	 * @return
	 */
	public static String getProperty( String key ) {
		try {
			return resourceBundle.getString( key );
		} catch ( Exception e ) {
			e.printStackTrace();
			return EMPTY_KEY;
		}
	}

}

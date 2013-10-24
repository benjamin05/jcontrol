package mx.com.lux.control.trabajos.util.properties;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.ResourceBundle;

public class EnvironmentPropertyHelper {

	private static ResourceBundle defaultDesign = ResourceBundle.getBundle( "mx.com.lux.control.trabajos.resources.EnvironmentDefault" );
	;
	private static final Properties proper = new Properties();
	private static final String EMPTY_KEY = "";
	private static final String externalDesign = ConfigurationPropertyHelper.getProperty( "directory" );

	public static String getProperty( String key ) {
		try {
			String str = defaultDesign.getString( key );
			if ( loadFile( externalDesign ) ) {
				str = proper.getProperty( key );
			}

			return str;
		} catch ( Exception e ) {
			e.printStackTrace();
			return EMPTY_KEY;
		}
	}

	public static boolean loadFile( String archivoPropiedades ) {
		try {
			proper.load( new FileInputStream( archivoPropiedades ) );
			return true;
		} catch ( Exception e ) {
			return false;
		}
	}

}

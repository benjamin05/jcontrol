package mx.com.lux.control.trabajos.view;

import java.util.HashMap;
import java.util.Map;

public class Session {

	public static Map<String, Object> atributes = new HashMap<String, Object>();

	public static void setAttribute( String name, Object attribute ) {
		if ( atributes == null ) {
			atributes = new HashMap<String, Object>();
		}
		atributes.put( name, attribute );
	}

	public static synchronized Object getAttribute( String name ) {
		if ( atributes == null ) {
			atributes = new HashMap<String, Object>();
		}
		return atributes.get( name );
	}

	public static synchronized Object removeAttribute( String name ) {
		if ( atributes == null ) {
			atributes = new HashMap<String, Object>();
		}
		return atributes.remove( name );
	}

}

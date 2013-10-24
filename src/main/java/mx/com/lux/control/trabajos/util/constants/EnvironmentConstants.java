package mx.com.lux.control.trabajos.util.constants;

import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;

public class EnvironmentConstants {

	public static int WINDOW_PRINCIPAL_WIDTH = Integer.parseInt( ApplicationPropertyHelper.getProperty( "window.principal.width" ) );
	public static int WINDOW_PRINCIPAL_HEIGHT = Integer.parseInt( ApplicationPropertyHelper.getProperty( "window.principal.height" ) );
	public static int TABLE_WIDTH = Integer.parseInt( ApplicationPropertyHelper.getProperty( "table.width" ) );
	public static int TABLE_HEIGHT = Integer.parseInt( ApplicationPropertyHelper.getProperty( "table.height" ) );
	public static int BUTTON_WIDTH = Integer.parseInt( ApplicationPropertyHelper.getProperty( "button.width" ) );
	public static int BUTTON_HEIGHT = Integer.parseInt( ApplicationPropertyHelper.getProperty( "button.height" ) );

	public static int TEXTBOX_NUMBER_WIDTH = Integer.parseInt( ApplicationPropertyHelper.getProperty( "button.width" ) );
	public static int TEXTBOX_NUMBER_HEIGHT = Integer.parseInt( ApplicationPropertyHelper.getProperty( "button.height" ) );

	public static int TEXTBOX_TEXT_WIDTH = Integer.parseInt( ApplicationPropertyHelper.getProperty( "button.width" ) );
	public static int TEXTBOX_TEXT_HEIGHT = Integer.parseInt( ApplicationPropertyHelper.getProperty( "button.height" ) );

	public static int LABEL_NORMAL_HEIGHT = Integer.parseInt( ApplicationPropertyHelper.getProperty( "label.normal.height" ) );
	public static int LABEL_NORMAL_WIDTH = Integer.parseInt( ApplicationPropertyHelper.getProperty( "label.normal.width" ) );


}

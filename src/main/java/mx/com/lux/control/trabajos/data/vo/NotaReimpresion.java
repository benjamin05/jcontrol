package mx.com.lux.control.trabajos.data.vo;

import org.apache.commons.lang.StringUtils;

public enum NotaReimpresion {
	FA( "Fa" ),
	RB( "Rb" ),
	RX( "Rx" ),
	TV( "Tv" ),
	NA( "" );

	public final String nota;

	private NotaReimpresion( String nota ) {
		this.nota = nota;
	}

	public static NotaReimpresion parse( String nota ) {
		if ( StringUtils.isNotBlank( nota ) ) {
			String valor = nota.trim();
			for ( NotaReimpresion item : NotaReimpresion.values() ) {
				if ( item.nota.equalsIgnoreCase( valor ) ) {
					return item;
				}
			}
		}
		return NA;
	}
}

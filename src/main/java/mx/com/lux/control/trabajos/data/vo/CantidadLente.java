package mx.com.lux.control.trabajos.data.vo;

import org.apache.commons.lang.StringUtils;

public enum CantidadLente {
	OD( "od", "R" ),
	PAR( "par", "P" ),
	OI( "oi", "L" ),
	NA( "", "" );

	public final String cantidad;
	public final String tipo;

	private CantidadLente( String cantidad, String tipo ) {
		this.cantidad = cantidad;
		this.tipo = tipo;
	}

	public static CantidadLente parse( String cantidad ) {
		if ( StringUtils.isNotBlank( cantidad ) ) {
			String valor = cantidad.trim();
			for ( CantidadLente item : CantidadLente.values() ) {
				if ( item.cantidad.equalsIgnoreCase( valor ) ) {
					return item;
				}
			}
		}
		return NA;
	}
}

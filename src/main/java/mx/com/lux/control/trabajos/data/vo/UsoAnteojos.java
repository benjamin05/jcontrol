package mx.com.lux.control.trabajos.data.vo;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

public enum UsoAnteojos {
	B( 'b', "BIFOCAL" ),
	C( 'c', "CERCA" ),
	I( 'i', "INTERMEDIO" ),
	L( 'l', "LEJOS" ),
	P( 'p', "PROGRESIVO" ),
	T( 't', "BIFOCAL INTERMEDIO" ),
	NA( ' ', "INDEFINIDO" );

	public final char uso;
	public final String nombre;

	private UsoAnteojos( char uso, String nombre ) {
		this.uso = uso;
		this.nombre = nombre;
	}

	public static UsoAnteojos parse( char uso ) {
		if ( StringUtils.isNotBlank( CharUtils.toString( uso ) ) ) {
			for ( UsoAnteojos item : UsoAnteojos.values() ) {
				if ( item.uso == uso ) {
					return item;
				}
			}
		}
		return NA;
	}
}

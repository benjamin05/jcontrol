package mx.com.lux.control.trabajos.util.constants;

public enum TipoTrabajo {

	LABORATORIO( "LAB" ),
	EXTERNO( "EXT" ),
	REF( "REF" ),
	GARANTIA( "GAR" ),
	ORDEN_SERVICIO( "OS" ),
	X1( "X1" ),
	RETRASADO( "RESTRASADO" );

	private String codigo;

	TipoTrabajo( String codigo ) {
		this.codigo = codigo;
	}

	public String codigo() {
		return codigo;
	}

	public static TipoTrabajo parse( String codigo ) {
		if ( codigo != null ) {
			String codigoTmp = codigo.trim().toUpperCase();
			for ( TipoTrabajo tipo : TipoTrabajo.values() ) {
				if ( tipo.codigo().equals( codigoTmp ) ) {
					return tipo;
				}
			}
		}
		return null;
	}

}

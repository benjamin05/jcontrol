package mx.com.lux.control.trabajos.util.constants;

import mx.com.lux.control.trabajos.data.vo.JbEstado;

public enum EstadoTrabajo {

	NO_CONTACTAR( "NT" ),
	CONTACTO_REPROGRAMADO( "CR" ),
	ENTREGADO( "TE" ),
	CANCELADO( "CN" ),
	BODEGA( "BD" ),
	RETENIDO( "RTN" ),
	POR_ENVIAR_EXTERNO( "X1" ),
	ENVIADO_EXTERNO( "X2" ),
	RECIBIDO_DESTINO_EXTERNO( "X3" ),
	ENTREGADO_DESTINO_EXTERNO( "X4" ),
	PENDIENTE( "PN" ),
	NO_CONTESTO( "NC" ),
	EN_PINO( "EP" ),
	POR_ENVIAR( "PE" ),
	ROTO_POR_ENVIAR( "RPE" ),
	NO_ENVIAR( "NE" ),
	ROTO_NO_ENVIAR( "RNE" ),
	FAX( "FAX" ),
	ROTO_EN_PINO( "REP" ),
	AGRUPADO( "AGR" ),
	DESAGRUPADO( "DGR" ),
	DESENTREGADO( "DE" ),
	RECIBE_SUCURSAL( "RS" );

	private String codigo;

	EstadoTrabajo( String codigo ) {
		this.codigo = codigo;
	}

	public String codigo() {
		return codigo;
	}

	public JbEstado entity() {
		return new JbEstado( this.codigo );
	}

	public static EstadoTrabajo parse( final String codigo ) {
		if ( codigo != null ) {
			String codigoTmp = codigo.trim().toUpperCase();
			for ( EstadoTrabajo estadoTrabajo : EstadoTrabajo.values() ) {
				if ( estadoTrabajo.codigo().equals( codigoTmp ) ) {
					return estadoTrabajo;
				}
			}
		}
		return null;
	}

	public static EstadoTrabajo parse( final JbEstado estado ) {
		if ( estado != null && estado.getIdEdo() != null ) {
			String codigo = estado.getIdEdo().trim().toUpperCase();
			for ( EstadoTrabajo estadoTrabajo : EstadoTrabajo.values() ) {
				if ( estadoTrabajo.codigo().equals( codigo ) ) {
					return estadoTrabajo;
				}
			}
		}
		return null;
	}

	public boolean equals( final EstadoTrabajo estado ) {
		return this.codigo.equals( estado.codigo );
	}
}

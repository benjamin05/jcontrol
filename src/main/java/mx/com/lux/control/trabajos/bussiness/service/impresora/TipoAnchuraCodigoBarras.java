package mx.com.lux.control.trabajos.bussiness.service.impresora;

public enum TipoAnchuraCodigoBarras {
	NA( 0 ), N2( 2 ), N3( 3 ), N4( 4 ), N5( 5 ), N6( 6 );

	public final byte n;

	private TipoAnchuraCodigoBarras( int n ) {
		this.n = (byte) n;
	}
}

package mx.com.lux.control.trabajos.bussiness.service.impresora;

public enum TipoAlineacion {
	IZQUIERDA( 0 ), CENTRO( 1 ), DERECHA( 2 );

	public final byte n;

	private TipoAlineacion( int n ) {
		this.n = (byte) n;
	}
}

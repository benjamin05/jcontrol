package mx.com.lux.control.trabajos.bussiness.service.impresora;

public enum TipoCorte {

	CORTE_PARCIAL( "1" );

	String nombre;

	private TipoCorte( String nombre ) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return nombre;
	}
}

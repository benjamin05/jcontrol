package mx.com.lux.control.trabajos.data.dto;

public class InfoPinoDTO {

	public static String TIPO_SEGUIMIENTO = "S";
	public static String TIPO_ESTACION = "E";
	public static String SEPARATOR = "\\|";

	private String tipo;
	private String descSeguimiento;
	private String fechaEstacion;
	private String horaEstacion;
	private String estacion;


	public InfoPinoDTO() {
		super();
	}

	public InfoPinoDTO( String tipo, String descSeguimiento ) {
		super();
		this.tipo = tipo;
		this.descSeguimiento = descSeguimiento;
	}


	public InfoPinoDTO( String tipo, String horaEstacion, String fechaEstacion, String estacion ) {
		super();
		this.tipo = tipo;
		this.fechaEstacion = fechaEstacion;
		this.horaEstacion = horaEstacion;
		this.estacion = estacion;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo( String tipo ) {
		this.tipo = tipo;
	}

	/**
	 * @return the descSeguimiento
	 */
	public String getDescSeguimiento() {
		return descSeguimiento;
	}

	/**
	 * @param descSeguimiento the descSeguimiento to set
	 */
	public void setDescSeguimiento( String descSeguimiento ) {
		this.descSeguimiento = descSeguimiento;
	}

	/**
	 * @return the fechaEstacion
	 */
	public String getFechaEstacion() {
		return fechaEstacion;
	}

	/**
	 * @param fechaEstacion the fechaEstacion to set
	 */
	public void setFechaEstacion( String fechaEstacion ) {
		this.fechaEstacion = fechaEstacion;
	}

	/**
	 * @return the horaEstacion
	 */
	public String getHoraEstacion() {
		return horaEstacion;
	}

	/**
	 * @param horaEstacion the horaEstacion to set
	 */
	public void setHoraEstacion( String horaEstacion ) {
		this.horaEstacion = horaEstacion;
	}

	/**
	 * @return the estacion
	 */
	public String getEstacion() {
		return estacion;
	}

	/**
	 * @param estacion the estacion to set
	 */
	public void setEstacion( String estacion ) {
		this.estacion = estacion;
	}

	public String[] toEstacionArray() {
		return new String[]{ horaEstacion, fechaEstacion, estacion };
	}

}	

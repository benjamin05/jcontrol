package mx.com.lux.control.trabajos.data.vo;

// Generated 21/07/2011 02:01:22 PM by Hibernate Tools 3.4.0.CR1

/**
 * GParametro generated by hbm2java
 */
public class GParametro implements java.io.Serializable {

	private String idParametro;
	private String descr;
	private String valor;

	public GParametro() {
	}

	public GParametro( String idParametro ) {
		this.idParametro = idParametro;
	}

	public GParametro( String idParametro, String descr, String valor ) {
		this.idParametro = idParametro;
		this.descr = descr;
		this.valor = valor;
	}

	public String getIdParametro() {
		return this.idParametro;
	}

	public void setIdParametro( String idParametro ) {
		this.idParametro = idParametro;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr( String descr ) {
		this.descr = descr;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor( String valor ) {
		this.valor = valor;
	}

}

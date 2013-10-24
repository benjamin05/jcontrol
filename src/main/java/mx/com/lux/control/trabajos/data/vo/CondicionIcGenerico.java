package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class CondicionIcGenerico implements Serializable {

	private static final long serialVersionUID = 3971581734776047466L;

	private String idConvenio;
	private String idGenerico;
	private BigDecimal porcentajeDescuento;

	public String getIdConvenio() {
		return idConvenio;
	}

	public void setIdConvenio( String idConvenio ) {
		this.idConvenio = idConvenio;
	}

	public String getIdGenerico() {
		return idGenerico;
	}

	public void setIdGenerico( String idGenerico ) {
		this.idGenerico = idGenerico;
	}

	public BigDecimal getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento( BigDecimal porcentajeDescuento ) {
		this.porcentajeDescuento = porcentajeDescuento;
	}
}

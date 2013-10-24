package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.util.Date;

public class Reimpresion implements Serializable {

	private static final long serialVersionUID = 3505474137215788243L;

	private String idNota;
	private Date fecha;
	private String nota;
	private String empleado;
	private String factura;

	public String getIdNota() {
		return idNota;
	}

	public void setIdNota( String idNota ) {
		this.idNota = idNota;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha( Date fecha ) {
		this.fecha = fecha;
	}

	public String getNota() {
		return nota;
	}

	public void setNota( String nota ) {
		this.nota = nota;
	}

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado( String empleado ) {
		this.empleado = empleado;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura( String factura ) {
		this.factura = factura;
	}
}

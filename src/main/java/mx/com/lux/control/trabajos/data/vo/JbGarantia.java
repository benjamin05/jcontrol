package mx.com.lux.control.trabajos.data.vo;

import mx.com.lux.control.trabajos.util.ApplicationUtils;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.Date;

public class JbGarantia implements java.io.Serializable {

	private static final long serialVersionUID = 4351142062418691480L;

	private Integer id;
	private String idCliente;
	private String cliente;
	private String razon;
	private String condicion;
	private String armazon;
	private String color;
	private String sucursal;
	private String factura;
	private Date fechaFactura;
	private String empleado;
	private Date fechaPromesa;
	private Timestamp fecha;
	private String tipoGarantia;
	private String dejo;
	private String idMod;

	public Integer getId() {
		return id;
	}

	public void setId( Integer id ) {
		this.id = id;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public Integer idCliente() {
		if( StringUtils.isNotBlank( idCliente ) && StringUtils.isNumericSpace( idCliente ) ) {
			return Integer.parseInt( idCliente.trim() );
		}
		return -1;
	}

	public void setIdCliente( String idCliente ) {
		this.idCliente = idCliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente( String cliente ) {
		this.cliente = cliente;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon( String razon ) {
		this.razon = razon;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion( String condicion ) {
		this.condicion = condicion;
	}

	public String getArmazon() {
		return armazon;
	}

	public void setArmazon( String armazon ) {
		this.armazon = armazon;
	}

	public String getColor() {
		return color;
	}

	public void setColor( String color ) {
		this.color = color;
	}

	public String getSucursal() {
		return sucursal;
	}

	public Integer idSucursal() {
		if( StringUtils.isNotBlank( sucursal ) && StringUtils.isNumericSpace( sucursal ) ) {
			return Integer.parseInt( sucursal.trim() );
		}
		return -1;
	}

	public void setSucursal( String sucursal ) {
		this.sucursal = sucursal;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura( String factura ) {
		this.factura = factura;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public String fechaFacturaFormateada() {
		if( fechaFactura != null ) {
			return ApplicationUtils.formatearFecha( fechaFactura, "dd-MM-yyyy" ) ;
		}
		return "";
	}

	public void setFechaFactura( Date fechaFactura ) {
		if ( fechaFactura != null ) {
			this.fechaFactura = new Date( fechaFactura.getTime() );
		}
	}

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado( String empleado ) {
		this.empleado = empleado;
	}

	public Date getFechaPromesa() {
		return fechaPromesa;
	}

	public String fechaPromesaFormateada() {
		if( fechaPromesa != null ) {
			return ApplicationUtils.formatearFecha( fechaPromesa, "dd-MM-yyyy" ) ;
		}
		return "";
	}

	public void setFechaPromesa( Date fechaPromesa ) {
		if ( fechaPromesa != null ) {
			this.fechaPromesa = new Date( fechaPromesa.getTime() );
		}
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha( Timestamp fecha ) {
		if ( fecha != null ) {
			this.fecha = new Timestamp( fecha.getTime() );
		}
	}

	public String getTipoGarantia() {
		return tipoGarantia;
	}

	public void setTipoGarantia( String tipoGarantia ) {
		this.tipoGarantia = tipoGarantia;
	}

	public String getDejo() {
		return dejo;
	}

	public void setDejo( String dejo ) {
		this.dejo = dejo;
	}

	public String getIdMod() {
		return idMod;
	}

	public void setIdMod( String idMod ) {
		this.idMod = idMod;
	}

}

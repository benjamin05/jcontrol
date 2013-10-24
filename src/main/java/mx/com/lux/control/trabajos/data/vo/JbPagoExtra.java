package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Locale;

public class JbPagoExtra implements Serializable {

	private static final long serialVersionUID = -2050536838191775253L;

	private int idPago;
	private String factura;
	private String idFormaPago;
	private String idBanco;
	private String refer;
	private BigDecimal monto;
	private Timestamp fecha;
	private String idEmpleado;
	private String idFPago;
	private String claveP;
	private String refClave;
	private String idBancoEmi;
	private String idTerm;
	private String idPlan;
	private boolean confirm;
	private String idMod;

	public JbPagoExtra() {
	}

	public JbPagoExtra( int idPago, String factura, Timestamp fecha, boolean confirm ) {
		this.idPago = idPago;
		this.factura = factura;
		this.fecha = fecha;
		this.confirm = confirm;
	}

	public JbPagoExtra( int idPago, String factura, String idFormaPago, String idBanco, String refer, BigDecimal monto, Timestamp fecha, String idEmpleado, String idFPago, String claveP, String refClave, String idBancoEmi, String idTerm, String idPlan, boolean confirm, String idMod ) {
		this.idPago = idPago;
		this.factura = factura;
		this.idFormaPago = idFormaPago;
		this.idBanco = idBanco;
		this.refer = refer;
		this.monto = monto;
		this.fecha = fecha;
		this.idEmpleado = idEmpleado;
		this.idFPago = idFPago;
		this.claveP = claveP;
		this.refClave = refClave;
		this.idBancoEmi = idBancoEmi;
		this.idTerm = idTerm;
		this.idPlan = idPlan;
		this.confirm = confirm;
		this.idMod = idMod;
	}

	public int getIdPago() {
		return this.idPago;
	}

	public void setIdPago( int idPago ) {
		this.idPago = idPago;
	}

	public String getFactura() {
		return this.factura;
	}

	public void setFactura( String factura ) {
		this.factura = factura;
	}

	public String getIdFormaPago() {
		return this.idFormaPago;
	}

	public void setIdFormaPago( String idFormaPago ) {
		this.idFormaPago = idFormaPago;
	}

	public String getIdBanco() {
		return this.idBanco;
	}

	public void setIdBanco( String idBanco ) {
		this.idBanco = idBanco;
	}

	public String getRefer() {
		return this.refer;
	}

	public void setRefer( String refer ) {
		this.refer = refer;
	}

	public BigDecimal getMonto() {
		return this.monto;
	}

	public String getMontoFormateado() {
		NumberFormat nf = NumberFormat.getCurrencyInstance( Locale.US );
		return nf.format( monto );
	}

	public void setMonto( BigDecimal monto ) {
		this.monto = monto;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha( Timestamp fecha ) {
		this.fecha = fecha;
	}

	public String getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado( String idEmpleado ) {
		this.idEmpleado = idEmpleado;
	}

	public String getIdFPago() {
		return this.idFPago;
	}

	public void setIdFPago( String idFPago ) {
		this.idFPago = idFPago;
	}

	public String getClaveP() {
		return this.claveP;
	}

	public void setClaveP( String claveP ) {
		this.claveP = claveP;
	}

	public String getRefClave() {
		return this.refClave;
	}

	public void setRefClave( String refClave ) {
		this.refClave = refClave;
	}

	public String getIdBancoEmi() {
		return this.idBancoEmi;
	}

	public void setIdBancoEmi( String idBancoEmi ) {
		this.idBancoEmi = idBancoEmi;
	}

	public String getIdTerm() {
		return this.idTerm;
	}

	public void setIdTerm( String idTerm ) {
		this.idTerm = idTerm;
	}

	public String getIdPlan() {
		return this.idPlan;
	}

	public void setIdPlan( String idPlan ) {
		this.idPlan = idPlan;
	}

	public boolean isConfirm() {
		return this.confirm;
	}

	public void setConfirm( boolean confirm ) {
		this.confirm = confirm;
	}

	public String getIdMod() {
		return this.idMod;
	}

	public void setIdMod( String idMod ) {
		this.idMod = idMod;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( "Factura: " ).append( factura ).append( "\n" );
		sb.append( "Forma de pago: " ).append( idFPago ).append( "\n" );
		sb.append( "Monto: " ).append( monto ).append( "\n" );
		sb.append( "Confirmado: " ).append( confirm ).append( "\n" );
		return sb.toString();
	}
}

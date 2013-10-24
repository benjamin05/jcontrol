package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class NotaVenta implements Serializable {

	private static final long serialVersionUID = -8491645592979567043L;

	private String idFactura;
	private String idEmpleado;
	private Integer idCliente;
	private String idConvenio;
	private Integer idRepVenta;
	private Character tipoNotaVenta;
	private Date fechaRecOrd;
	private char tipoCli;
	private Boolean FExpideFactura;
	private BigDecimal ventaTotal;
	private BigDecimal ventaNeta;
	private BigDecimal sumaPagos;
	private Date fechaHoraFactura;
	private Date fechaPrometida;
	private Date fechaEntrega;
	private Boolean FArmazonCli;
	private Integer por100Descuento;
	private BigDecimal montoDescuento;
	private Character tipoDescuento;
	private String idEmpleadoDescto;
	private Boolean FResumenNotasMo;
	private char SFactura;
	private Integer numeroOrden;
	private Character tipoEntrega;
	private String observacionesNv;
	private char idSync;
	private Date fechaMod;
	private String idMod;
	private int idSucursal;
	private String factura;
	private String cantLente;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String sucDest;
	private String TDeduc;
	private Integer receta;
	private String empEntrego;
	private String lc;
	private Date horaEntrega;
	private boolean descuento;
	private boolean polEnt;
	private String tipoVenta;
	private BigDecimal poliza;

	public String getIdFactura() {
		return this.idFactura;
	}

	public void setIdFactura( String idFactura ) {
		this.idFactura = idFactura;
	}

	public String getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado( String idEmpleado ) {
		this.idEmpleado = idEmpleado;
	}

	public Integer getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente( Integer idCliente ) {
		this.idCliente = idCliente;
	}

	public String getIdConvenio() {
		return this.idConvenio;
	}

	public void setIdConvenio( String idConvenio ) {
		this.idConvenio = idConvenio;
	}

	public Integer getIdRepVenta() {
		return this.idRepVenta;
	}

	public void setIdRepVenta( Integer idRepVenta ) {
		this.idRepVenta = idRepVenta;
	}

	public Character getTipoNotaVenta() {
		return this.tipoNotaVenta;
	}

	public void setTipoNotaVenta( Character tipoNotaVenta ) {
		this.tipoNotaVenta = tipoNotaVenta;
	}

	public Date getFechaRecOrd() {
		return this.fechaRecOrd;
	}

	public void setFechaRecOrd( Date fechaRecOrd ) {
		this.fechaRecOrd = fechaRecOrd;
	}

	public char getTipoCli() {
		return this.tipoCli;
	}

	public void setTipoCli( char tipoCli ) {
		this.tipoCli = tipoCli;
	}

	public Boolean getFExpideFactura() {
		return this.FExpideFactura;
	}

	public void setFExpideFactura( Boolean FExpideFactura ) {
		this.FExpideFactura = FExpideFactura;
	}

	public BigDecimal getVentaTotal() {
		return ventaTotal;
	}

	public String getVentaTotalFormateado() {
		NumberFormat nf = NumberFormat.getCurrencyInstance( Locale.US );
		return nf.format( ventaTotal );
	}

	public void setVentaTotal( BigDecimal ventaTotal ) {
		this.ventaTotal = ventaTotal;
	}

	public BigDecimal getVentaNeta() {
		return ventaNeta;
	}

	public String getVentaNetaFormateado() {
		NumberFormat nf = NumberFormat.getCurrencyInstance( Locale.US );
		return nf.format( ventaNeta );
	}

	public void setVentaNeta( BigDecimal ventaNeta ) {
		this.ventaNeta = ventaNeta;
	}

	public BigDecimal getSumaPagos() {
		return sumaPagos;
	}

	public String getSumaPagosFormateado() {
		NumberFormat nf = NumberFormat.getCurrencyInstance( Locale.US );
		return nf.format( sumaPagos );
	}

	public void setSumaPagos( BigDecimal sumaPagos ) {
		this.sumaPagos = sumaPagos;
	}

	public Date getFechaHoraFactura() {
		return this.fechaHoraFactura;
	}

	public void setFechaHoraFactura( Date fechaHoraFactura ) {
		this.fechaHoraFactura = fechaHoraFactura;
	}

	public Date getFechaPrometida() {
		return this.fechaPrometida;
	}

	public void setFechaPrometida( Date fechaPrometida ) {
		this.fechaPrometida = fechaPrometida;
	}

	public Date getFechaEntrega() {
		return this.fechaEntrega;
	}

	public void setFechaEntrega( Date fechaEntrega ) {
		this.fechaEntrega = fechaEntrega;
	}

	public Boolean getFArmazonCli() {
		return this.FArmazonCli;
	}

	public void setFArmazonCli( Boolean FArmazonCli ) {
		this.FArmazonCli = FArmazonCli;
	}

	public Integer getPor100Descuento() {
		return this.por100Descuento;
	}

	public void setPor100Descuento( Integer por100Descuento ) {
		this.por100Descuento = por100Descuento;
	}

	public BigDecimal getMontoDescuento() {
		return this.montoDescuento;
	}

	public String getMontoDescuentoFormateado() {
		NumberFormat nf = NumberFormat.getCurrencyInstance( Locale.US );
		return nf.format( montoDescuento );
	}

	public void setMontoDescuento( BigDecimal montoDescuento ) {
		this.montoDescuento = montoDescuento;
	}

	public Character getTipoDescuento() {
		return this.tipoDescuento;
	}

	public void setTipoDescuento( Character tipoDescuento ) {
		this.tipoDescuento = tipoDescuento;
	}

	public String getIdEmpleadoDescto() {
		return this.idEmpleadoDescto;
	}

	public void setIdEmpleadoDescto( String idEmpleadoDescto ) {
		this.idEmpleadoDescto = idEmpleadoDescto;
	}

	public Boolean getFResumenNotasMo() {
		return this.FResumenNotasMo;
	}

	public void setFResumenNotasMo( Boolean FResumenNotasMo ) {
		this.FResumenNotasMo = FResumenNotasMo;
	}

	public char getSFactura() {
		return this.SFactura;
	}

	public void setSFactura( char SFactura ) {
		this.SFactura = SFactura;
	}

	public Integer getNumeroOrden() {
		return this.numeroOrden;
	}

	public void setNumeroOrden( Integer numeroOrden ) {
		this.numeroOrden = numeroOrden;
	}

	public Character getTipoEntrega() {
		return this.tipoEntrega;
	}

	public void setTipoEntrega( Character tipoEntrega ) {
		this.tipoEntrega = tipoEntrega;
	}

	public String getObservacionesNv() {
		return this.observacionesNv;
	}

	public void setObservacionesNv( String observacionesNv ) {
		this.observacionesNv = observacionesNv;
	}

	public char getIdSync() {
		return this.idSync;
	}

	public void setIdSync( char idSync ) {
		this.idSync = idSync;
	}

	public Date getFechaMod() {
		return this.fechaMod;
	}

	public void setFechaMod( Date fechaMod ) {
		this.fechaMod = fechaMod;
	}

	public String getIdMod() {
		return this.idMod;
	}

	public void setIdMod( String idMod ) {
		this.idMod = idMod;
	}

	public int getIdSucursal() {
		return this.idSucursal;
	}

	public void setIdSucursal( int idSucursal ) {
		this.idSucursal = idSucursal;
	}

	public String getFactura() {
		return this.factura;
	}

	public void setFactura( String factura ) {
		this.factura = factura;
	}

	public String getCantLente() {
		return this.cantLente;
	}

	public void setCantLente( String cantLente ) {
		this.cantLente = cantLente;
	}

	public String getUdf2() {
		return this.udf2;
	}

	public void setUdf2( String udf2 ) {
		this.udf2 = udf2;
	}

	public String getUdf3() {
		return this.udf3;
	}

	public void setUdf3( String udf3 ) {
		this.udf3 = udf3;
	}

	public String getUdf4() {
		return this.udf4;
	}

	public void setUdf4( String udf4 ) {
		this.udf4 = udf4;
	}

	public String getUdf5() {
		return this.udf5;
	}

	public void setUdf5( String udf5 ) {
		this.udf5 = udf5;
	}

	public String getSucDest() {
		return this.sucDest;
	}

	public void setSucDest( String sucDest ) {
		this.sucDest = sucDest;
	}

	public String getTDeduc() {
		return this.TDeduc;
	}

	public void setTDeduc( String TDeduc ) {
		this.TDeduc = TDeduc;
	}

	public Integer getReceta() {
		return this.receta;
	}

	public void setReceta( Integer receta ) {
		this.receta = receta;
	}

	public String getEmpEntrego() {
		return this.empEntrego;
	}

	public void setEmpEntrego( String empEntrego ) {
		this.empEntrego = empEntrego;
	}

	public String getLc() {
		return this.lc;
	}

	public void setLc( String lc ) {
		this.lc = lc;
	}

	public Date getHoraEntrega() {
		return this.horaEntrega;
	}

	public void setHoraEntrega( Date horaEntrega ) {
		this.horaEntrega = horaEntrega;
	}

	public boolean isDescuento() {
		return this.descuento;
	}

	public void setDescuento( boolean descuento ) {
		this.descuento = descuento;
	}

	public boolean isPolEnt() {
		return this.polEnt;
	}

	public void setPolEnt( boolean polEnt ) {
		this.polEnt = polEnt;
	}

	public String getTipoVenta() {
		return this.tipoVenta;
	}

	public void setTipoVenta( String tipoVenta ) {
		this.tipoVenta = tipoVenta;
	}

	public CantidadLente getCantidadLente() {
		return CantidadLente.parse( cantLente );
	}

	public BigDecimal getPoliza() {
		return poliza;
	}

	public void setPoliza( BigDecimal poliza ) {
		this.poliza = poliza;
	}

	public String getPolizaFormateado() {
		NumberFormat nf = NumberFormat.getCurrencyInstance( Locale.US );
		return nf.format( poliza );
	}
}

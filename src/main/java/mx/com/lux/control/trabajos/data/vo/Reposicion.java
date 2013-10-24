package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.util.Date;

public class Reposicion implements Serializable {

	private static final long serialVersionUID = -3170549031264977383L;

	private String factura;
	private Integer numeroOrden;
	private Empleado empleado;
	private String idResponsable;
	private Date fecha;
	private Character tipo;
	private Integer idCliente;
	private CausaReposicion causa;
	private String problema;
	private String dx;
	private String instrucciones;
	private Character usoAnteojos;

	private String diLejos;
	private String diCerca;
	private String altObl;
	private String observaciones;
	private String area;
	private String folio;
	private String cliente;
	private String material;
	private String tratamientos;
	private String idSucursal;
	private Character ojo;

	// Ojo derecho
	private String odEsf;
	private String odCil;
	private String odEje;
	private String odAdc;
	private String odAdi;
	private String odAv;
	private String odDi;
	private String odPrisma;
	private String odPrismaV;
	// Ojo izquierdo
	private String oiEsf;
	private String oiCil;
	private String oiEje;
	private String oiAdc;
	private String oiAdi;
	private String oiAv;
	private String oiDi;
	private String oiPrisma;
	private String oiPrismaV;

	public String getFactura() {
		return factura;
	}

	public void setFactura( String factura ) {
		this.factura = factura;
	}

	public Integer getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden( Integer numeroOrden ) {
		this.numeroOrden = numeroOrden;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado( Empleado empleado ) {
		this.empleado = empleado;
	}

	public String getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable( String idResponsable ) {
		this.idResponsable = idResponsable;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha( Date fecha ) {
		if( fecha != null ) {
			this.fecha = new Date( fecha.getTime() );
		}
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo( Character tipo ) {
		this.tipo = tipo;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente( Integer idCliente ) {
		this.idCliente = idCliente;
	}

	public CausaReposicion getCausa() {
		return causa;
	}

	public void setCausa( CausaReposicion causa ) {
		this.causa = causa;
	}

	public String getProblema() {
		return problema;
	}

	public void setProblema( String problema ) {
		this.problema = problema;
	}

	public String getDx() {
		return dx;
	}

	public void setDx( String dx ) {
		this.dx = dx;
	}

	public String getInstrucciones() {
		return instrucciones;
	}

	public void setInstrucciones( String instrucciones ) {
		this.instrucciones = instrucciones;
	}

	public Character getUsoAnteojos() {
		return usoAnteojos;
	}

	public void setUsoAnteojos( Character usoAnteojos ) {
		this.usoAnteojos = usoAnteojos;
	}

	public String getDiLejos() {
		return diLejos;
	}

	public void setDiLejos( String diLejos ) {
		this.diLejos = diLejos;
	}

	public String getDiCerca() {
		return diCerca;
	}

	public void setDiCerca( String diCerca ) {
		this.diCerca = diCerca;
	}

	public String getAltObl() {
		return altObl;
	}

	public void setAltObl( String altObl ) {
		this.altObl = altObl;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones( String observaciones ) {
		this.observaciones = observaciones;
	}

	public String getArea() {
		return area;
	}

	public void setArea( String area ) {
		this.area = area;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio( String folio ) {
		this.folio = folio;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente( String cliente ) {
		this.cliente = cliente;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial( String material ) {
		this.material = material;
	}

	public String getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos( String tratamientos ) {
		this.tratamientos = tratamientos;
	}

	public String getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal( String idSucursal ) {
		this.idSucursal = idSucursal;
	}

	public Character getOjo() {
		return ojo;
	}

	public void setOjo( Character ojo ) {
		this.ojo = ojo;
	}

	public String getOdEsf() {
		return odEsf;
	}

	public void setOdEsf( String odEsf ) {
		this.odEsf = odEsf;
	}

	public String getOdCil() {
		return odCil;
	}

	public void setOdCil( String odCil ) {
		this.odCil = odCil;
	}

	public String getOdEje() {
		return odEje;
	}

	public void setOdEje( String odEje ) {
		this.odEje = odEje;
	}

	public String getOdAdc() {
		return odAdc;
	}

	public void setOdAdc( String odAdc ) {
		this.odAdc = odAdc;
	}

	public String getOdAdi() {
		return odAdi;
	}

	public void setOdAdi( String odAdi ) {
		this.odAdi = odAdi;
	}

	public String getOdAv() {
		return odAv;
	}

	public void setOdAv( String odAv ) {
		this.odAv = odAv;
	}

	public String getOdDi() {
		return odDi;
	}

	public void setOdDi( String odDi ) {
		this.odDi = odDi;
	}

	public String getOdPrisma() {
		return odPrisma;
	}

	public void setOdPrisma( String odPrisma ) {
		this.odPrisma = odPrisma;
	}

	public String getOdPrismaV() {
		return odPrismaV;
	}

	public void setOdPrismaV( String odPrismaV ) {
		this.odPrismaV = odPrismaV;
	}

	public String getOiEsf() {
		return oiEsf;
	}

	public void setOiEsf( String oiEsf ) {
		this.oiEsf = oiEsf;
	}

	public String getOiCil() {
		return oiCil;
	}

	public void setOiCil( String oiCil ) {
		this.oiCil = oiCil;
	}

	public String getOiEje() {
		return oiEje;
	}

	public void setOiEje( String oiEje ) {
		this.oiEje = oiEje;
	}

	public String getOiAdc() {
		return oiAdc;
	}

	public void setOiAdc( String oiAdc ) {
		this.oiAdc = oiAdc;
	}

	public String getOiAdi() {
		return oiAdi;
	}

	public void setOiAdi( String oiAdi ) {
		this.oiAdi = oiAdi;
	}

	public String getOiAv() {
		return oiAv;
	}

	public void setOiAv( String oiAv ) {
		this.oiAv = oiAv;
	}

	public String getOiDi() {
		return oiDi;
	}

	public void setOiDi( String oiDi ) {
		this.oiDi = oiDi;
	}

	public String getOiPrisma() {
		return oiPrisma;
	}

	public void setOiPrisma( String oiPrisma ) {
		this.oiPrisma = oiPrisma;
	}

	public String getOiPrismaV() {
		return oiPrismaV;
	}

	public void setOiPrismaV( String oiPrismaV ) {
		this.oiPrismaV = oiPrismaV;
	}
}

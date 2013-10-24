package mx.com.lux.control.trabajos.data.vo;

import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;
import mx.com.lux.control.trabajos.util.constants.TipoTrabajo;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class Jb implements Serializable {

	private static final long serialVersionUID = -8828035501954726549L;

	private String rx;
	private JbEstado estado;
	private String idViaje;
	private String caja;
	private String idCliente;
	private Integer roto;
	private String empAtendio;
	private Integer numLlamada;
	private String material;
	private String surte;
	private BigDecimal saldo;
	private String jbTipo;
	private Date volverLlamar;
	private Date fechaPromesa;
	private Timestamp fechaMod;
	private String cliente;
	private String idMod;
	private String obsExt;
	private String retAuto;
	private Boolean noLlamar;
	private String tipoVenta;
	private String idGrupo;
	private Timestamp fechaVenta;
	private String externo;

	public Jb() {
	}

	public Jb( String rx, Timestamp fechaMod, String idMod ) {
		this.rx = rx;
		this.fechaMod = fechaMod;
		this.idMod = idMod;
	}

	public Jb( String rx, JbEstado estado, String idViaje, String caja, String idCliente, Integer roto, String empAtendio, Integer numLlamada, String material, String surte, BigDecimal saldo, String jbTipo, Date volverLlamar, Date fechaPromesa, Timestamp fechaMod, String cliente, String idMod, String obsExt, String retAuto, Boolean noLlamar, String tipoVenta, String idGrupo, Timestamp fechaVenta ) {
		this.rx = rx;
		this.estado = estado;
		this.idViaje = idViaje;
		this.caja = caja;
		this.idCliente = idCliente;
		this.roto = roto;
		this.empAtendio = empAtendio;
		this.numLlamada = numLlamada;
		this.material = material;
		this.surte = surte;
		this.saldo = saldo;
		this.jbTipo = jbTipo;
		this.volverLlamar = volverLlamar;
		this.fechaPromesa = fechaPromesa;
		this.fechaMod = fechaMod;
		this.cliente = cliente;
		this.idMod = idMod;
		this.obsExt = obsExt;
		this.retAuto = retAuto;
		this.noLlamar = noLlamar;
		this.tipoVenta = tipoVenta;
		this.idGrupo = idGrupo;
		this.fechaVenta = fechaVenta;
	}

	public String getExterno() {
		return externo;
	}

	public void setExterno( String externo ) {
		this.externo = externo;
	}

	public String getRx() {
		return this.rx;
	}

	public void setRx( String rx ) {
		this.rx = rx;
	}

	public JbEstado getEstado() {
		return estado;
	}

	public void setEstado( JbEstado estado ) {
		this.estado = estado;
	}

	public String getIdViaje() {
		return this.idViaje;
	}

	public void setIdViaje( String idViaje ) {
		this.idViaje = idViaje;
	}

	public String getCaja() {
		return this.caja;
	}

	public void setCaja( String caja ) {
		this.caja = caja;
	}

	public String getIdCliente() {
		return this.idCliente;
	}

	public Integer idCliente() {
		if( StringUtils.isNotBlank( idCliente ) ) {
			return Integer.parseInt( idCliente );
		}
		return null;
	}

	public void setIdCliente( String idCliente ) {
		this.idCliente = idCliente;
	}

	public Integer getRoto() {
		return this.roto;
	}

	public void setRoto( Integer roto ) {
		this.roto = roto;
	}

	public String getEmpAtendio() {
		return this.empAtendio;
	}

	public void setEmpAtendio( String empAtendio ) {
		this.empAtendio = empAtendio;
	}

	public Integer getNumLlamada() {
		return this.numLlamada;
	}

	public void setNumLlamada( Integer numLlamada ) {
		this.numLlamada = numLlamada;
	}

	public String getMaterial() {
		return this.material;
	}

	public void setMaterial( String material ) {
		this.material = material;
	}

	public String getSurte() {
		return this.surte;
	}

	public void setSurte( String surte ) {
		this.surte = surte;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public String getSaldoFormateado() {
		NumberFormat nf = NumberFormat.getCurrencyInstance( Locale.US );
		return nf.format( saldo );
	}

	public void setSaldo( BigDecimal saldo ) {
		this.saldo = saldo;
	}

	public String getJbTipo() {
		return jbTipo;
	}

	public void setJbTipo( String jbTipo ) {
		this.jbTipo = jbTipo;
	}

	public Date getVolverLlamar() {
		return volverLlamar;
	}

	public void setVolverLlamar( Date volverLlamar ) {
		if( volverLlamar != null ) {
			this.volverLlamar = new Date( volverLlamar.getTime() );
		}
	}

	public Date getFechaPromesa() {
		return this.fechaPromesa;
	}

	public void setFechaPromesa( Date fechaPromesa ) {
		if( fechaPromesa != null ) {
			this.fechaPromesa = new Date( fechaPromesa.getTime() );
		}
	}

	public Timestamp getFechaMod() {
		return this.fechaMod;
	}

	public void setFechaMod( Timestamp fechaMod ) {
		if( fechaMod != null ) {
			this.fechaMod = new Timestamp( fechaMod.getTime() );
		}
	}

	public String getCliente() {
		return this.cliente;
	}

	public void setCliente( String cliente ) {
		this.cliente = cliente;
	}

	public String getIdMod() {
		return this.idMod;
	}

	public void setIdMod( String idMod ) {
		this.idMod = idMod;
	}

	public String getObsExt() {
		return this.obsExt;
	}

	public void setObsExt( String obsExt ) {
		this.obsExt = obsExt;
	}

	public String getRetAuto() {
		return this.retAuto;
	}

	public void setRetAuto( String retAuto ) {
		this.retAuto = retAuto;
	}

	public Boolean getNoLlamar() {
		return this.noLlamar;
	}

	public void setNoLlamar( Boolean noLlamar ) {
		this.noLlamar = noLlamar;
	}

	public String getTipoVenta() {
		return tipoVenta;
	}

	public void setTipoVenta( String tipoVenta ) {
		this.tipoVenta = tipoVenta;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo( String idGrupo ) {
		this.idGrupo = idGrupo;
	}

	public Timestamp getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta( Timestamp fechaVenta ) {
		if( fechaVenta != null ) {
			this.fechaVenta = new Timestamp( fechaVenta.getTime() );
		}
	}

	public EstadoTrabajo estado() {
		return EstadoTrabajo.parse( estado );
	}

	public void estado( EstadoTrabajo estado ) {
		this.estado = new JbEstado( estado.codigo() );
	}

	public TipoTrabajo tipo() {
		return TipoTrabajo.parse( jbTipo );
	}

	public void tipo( TipoTrabajo tipo ) {
		this.jbTipo = tipo.codigo();
	}

	public boolean esGrupo() {
		return ( StringUtils.isNotBlank( rx ) && rx.toUpperCase().startsWith( "F" ) );
	}

	public boolean esGarantia() {
		return TipoTrabajo.GARANTIA == tipo();
	}

	public boolean esExterno() {
		return TipoTrabajo.EXTERNO == tipo();
	}

	public boolean esLaboratorio() {
		return TipoTrabajo.LABORATORIO == tipo();
	}
}

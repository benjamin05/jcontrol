package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class ContactoView implements Serializable {

	private static final long serialVersionUID = -8550224467977808379L;

	private String rx;
	private String titulo;
	private String cliente;
	private String atendio;
	private String tipoLlamada;
	private String estadoLlamada;
	private String jbEstado;
	private String descripcionTipoContacto;
	private String idClienteJb;
	private String saldo;
	private String contactoForma;
	private String obsForma;
	private String material;
	private Timestamp fechaHoraFactura;
	private Timestamp fechaPromesa;
	private Integer numLlamadaJbLlamada;

	public ContactoView() {
	}

	public ContactoView( String rx, String titulo, String cliente, Timestamp fechaHoraFactura, String atendio, String tipoLlamada, String estadoLlamada, String jbEstado, String descripcionTipoContacto, Timestamp fechaPromesa, String idClienteJb, String saldo, String contactoForma, String obsForma, String material, Integer numLlamadaJbLlamada ) {
		super();
		this.rx = rx;
		this.titulo = titulo;
		this.cliente = cliente;
		this.fechaHoraFactura = fechaHoraFactura;
		this.atendio = atendio;
		this.tipoLlamada = tipoLlamada;
		this.descripcionTipoContacto = descripcionTipoContacto;
		this.estadoLlamada = estadoLlamada;
		this.jbEstado = jbEstado;
		this.fechaPromesa = fechaPromesa;
		this.idClienteJb = idClienteJb;
		this.saldo = saldo;
		this.contactoForma = contactoForma;
		this.obsForma = obsForma;
		this.material = material;
		this.numLlamadaJbLlamada = numLlamadaJbLlamada;
	}

	public String getRx() {
		return rx;
	}

	public void setRx( String rx ) {
		this.rx = rx;
	}

	public String getTitulo() {
		return titulo != null ? titulo.trim() : "";
	}

	public void setTitulo( String titulo ) {
		this.titulo = titulo;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente( String cliente ) {
		this.cliente = cliente;
	}

	public Timestamp getFechaHoraFactura() {
		return fechaHoraFactura;
	}

	public void setFechaHoraFactura( Timestamp fechaHoraFactura ) {
		this.fechaHoraFactura = fechaHoraFactura;
	}

	public String getAtendio() {
		return atendio;
	}

	public void setAtendio( String atendio ) {
		this.atendio = atendio;
	}

	public String getTipoLlamada() {
		return tipoLlamada;
	}

	public void setTipoLlamada( String tipoLlamada ) {
		this.tipoLlamada = tipoLlamada;
	}

	public String getDescripcionTipoContacto() {
		return descripcionTipoContacto;
	}

	public void setDescripcionTipoContacto( String descripcionTipoContacto ) {
		this.descripcionTipoContacto = descripcionTipoContacto;
	}

	public String getEstadoLlamada() {
		return estadoLlamada;
	}

	public void setEstadoLlamada( String estadoLlamada ) {
		this.estadoLlamada = estadoLlamada;
	}

	public String getJbEstado() {
		return jbEstado;
	}

	public void setJbEstado( String jbEstado ) {
		this.jbEstado = jbEstado;
	}

	public Timestamp getFechaPromesa() {
		return fechaPromesa;
	}

	public void setFechaPromesa( Timestamp fechaPromesa ) {
		this.fechaPromesa = fechaPromesa;
	}

	public String getIdClienteJb() {
		return idClienteJb;
	}

	public void setIdClienteJb( String idClienteJb ) {
		this.idClienteJb = idClienteJb;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo( String saldo ) {
		this.saldo = saldo;
	}

	public String getContactoForma() {
		return contactoForma;
	}

	public void setContactoForma( String contactoForma ) {
		this.contactoForma = contactoForma;
	}

	public String getObsForma() {
		return obsForma;
	}

	public void setObsForma( String obsForma ) {
		this.obsForma = obsForma;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial( String material ) {
		this.material = material;
	}

	public Integer getNumLlamadaJbLlamada() {
		return numLlamadaJbLlamada;
	}

	public void setNumLlamadaJbLlamada( Integer numLlamadaJbLlamada ) {
		this.numLlamadaJbLlamada = numLlamadaJbLlamada;
	}

	@Override
	public String toString() {
		return "ContactoView [rx=" + rx + ", cliente=" + cliente + ", fechaHoraFactura=" + fechaHoraFactura + ", atendio=" + atendio + ", tipoLlamada=" + tipoLlamada + ", estadoLlamada=" + estadoLlamada + ", jbEstado=" + jbEstado + ", descripcionTipoContacto=" + descripcionTipoContacto + ", fechaPromesa=" + fechaPromesa + ", idClienteJb=" + idClienteJb + ", saldo=" + saldo + ", contactoForma=" + contactoForma + ", obsForma=" + obsForma + ", material=" + material + ", numLlamadaJbLlamada=" + numLlamadaJbLlamada + "]";
	}

}

package mx.com.lux.control.trabajos.data.vo;

public class Codigo implements java.io.Serializable {


	private String codigo;
	private String idEstado;
	private String idLocalidad;
	private String usuario;

	private String clase;
	private String ciudad;
	private Character reparto;
	private Character servicios;
	private String oficina;
	private String asentamien;
	private Integer actualiza;
	private String cor;

	public Codigo() {
	}

	public String getClase() {
		return this.clase;
	}

	public void setClase( String clase ) {
		this.clase = clase;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad( String ciudad ) {
		this.ciudad = ciudad;
	}

	public Character getReparto() {
		return this.reparto;
	}

	public void setReparto( Character reparto ) {
		this.reparto = reparto;
	}

	public Character getServicios() {
		return this.servicios;
	}

	public void setServicios( Character servicios ) {
		this.servicios = servicios;
	}

	public String getOficina() {
		return this.oficina;
	}

	public void setOficina( String oficina ) {
		this.oficina = oficina;
	}

	public String getAsentamien() {
		return this.asentamien;
	}

	public void setAsentamien( String asentamien ) {
		this.asentamien = asentamien;
	}

	public Integer getActualiza() {
		return this.actualiza;
	}

	public void setActualiza( Integer actualiza ) {
		this.actualiza = actualiza;
	}

	public String getCor() {
		return this.cor;
	}

	public void setCor( String cor ) {
		this.cor = cor;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo( String codigo ) {
		this.codigo = codigo;
	}

	public String getIdEstado() {
		return idEstado;
	}

	public void setIdEstado( String idEstado ) {
		this.idEstado = idEstado;
	}

	public String getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad( String idLocalidad ) {
		this.idLocalidad = idLocalidad;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario( String usuario ) {
		this.usuario = usuario;
	}
}

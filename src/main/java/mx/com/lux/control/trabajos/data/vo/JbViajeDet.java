package mx.com.lux.control.trabajos.data.vo;

import java.util.Date;

public class JbViajeDet implements java.io.Serializable {

	private Integer id;
	private Date fecha;
    private String idViaje;
    private String rx;
    private String material;
    private String surte;
    private String jbTipo;
    private Integer roto;

	public JbViajeDet() {
	}

    public Date getFecha() {
        return fecha;
    }

    public void setFecha( Date fecha ) {
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getIdViaje() {
        return idViaje;
    }

    public void setIdViaje( String idViaje ) {
        this.idViaje = idViaje;
    }

    public String getJbTipo() {
        return jbTipo;
    }

    public void setJbTipo( String jbTipo ) {
        this.jbTipo = jbTipo;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial( String material ) {
        this.material = material;
    }

    public String getRx() {
        return rx;
    }

    public void setRx( String rx ) {
        this.rx = rx;
    }

    public String getSurte() {
        return surte;
    }

    public void setSurte( String surte ) {
        this.surte = surte;
    }

    public Integer getRoto() {
        return roto;
    }

    public void setRoto( Integer roto ) {
        this.roto = roto;
    }
}

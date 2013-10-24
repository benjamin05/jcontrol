package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.util.Date;

public class FormaContacto implements Serializable {

    private static final long serialVersionUID = -393073799394149586L;

    private String rx;
    private String contacto;
    private String observaciones;
    private Integer idCliente;
    private TipoContacto tipoContacto;
    private Date fechaMod;
    private Integer idSucursal;

    public TipoContacto getTipoContacto() {
        return tipoContacto;
    }

    public void setTipoContacto( TipoContacto tipoContacto ) {
        this.tipoContacto = tipoContacto;
    }

    public String getRx() {
        return rx;
    }

    public void setRx( String rx ) {
        this.rx = rx;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto( String contacto ) {
        this.contacto = contacto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones( String observaciones ) {
        this.observaciones = observaciones;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente( Integer idCliente ) {
        this.idCliente = idCliente;
    }

    public Date getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod( Date fechaMod ) {
        this.fechaMod = fechaMod;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal( Integer idSucursal ) {
        this.idSucursal = idSucursal;
    }
}

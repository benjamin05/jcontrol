package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;

public class TipoContacto implements Serializable {

    private static final long serialVersionUID = -5423943647325001854L;

    private int idTipoContacto;
    private String descripcion;

    public int getIdTipoContacto() {
        return this.idTipoContacto;
    }

    public void setIdTipoContacto( int idTipoContacto ) {
        this.idTipoContacto = idTipoContacto;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion( String descripcion ) {
        this.descripcion = descripcion;
    }
}

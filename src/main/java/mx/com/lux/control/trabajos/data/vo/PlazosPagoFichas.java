package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PlazosPagoFichas implements Serializable {

    private static final long serialVersionUID = -1941477523548469323L;

    private int idPlazo;
    private BigDecimal plazo;
    private String tipoVenta;

    public int getIdPlazo() {
        return this.idPlazo;
    }

    public void setIdPlazo( int idPlazo ) {
        this.idPlazo = idPlazo;
    }

    public BigDecimal getPlazo() {
        return this.plazo;
    }

    public void setPlazo( BigDecimal plazo ) {
        this.plazo = plazo;
    }

    public String getTipoVenta() {
        return this.tipoVenta;
    }

    public void setTipoVenta( String tipoVenta ) {
        this.tipoVenta = tipoVenta;
    }
}

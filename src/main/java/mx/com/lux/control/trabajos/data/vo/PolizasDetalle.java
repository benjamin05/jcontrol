package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class PolizasDetalle implements Serializable {

    private static final long serialVersionUID = 183474796906667777L;

    private Integer idPoliza;
    private String idArticulo;
    private String articulo;
    private String idColor;
    private String idGenerico;
    private Integer cantidad;
    private BigDecimal precioLista;
    private BigDecimal precioVenta;
    private Date fechaMod;
    private String idMod;

    public Integer getIdPoliza() {
        return idPoliza;
    }

    public void setIdPoliza( Integer idPoliza ) {
        this.idPoliza = idPoliza;
    }

    public String getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo( String idArticulo ) {
        this.idArticulo = idArticulo;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo( String articulo ) {
        this.articulo = articulo;
    }

    public String getIdColor() {
        return idColor;
    }

    public void setIdColor( String idColor ) {
        this.idColor = idColor;
    }

    public String getIdGenerico() {
        return idGenerico;
    }

    public void setIdGenerico( String idGenerico ) {
        this.idGenerico = idGenerico;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad( Integer cantidad ) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioLista() {
        return precioLista;
    }

    public void setPrecioLista( BigDecimal precioLista ) {
        this.precioLista = precioLista;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta( BigDecimal precioVenta ) {
        this.precioVenta = precioVenta;
    }

    public Date getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod( Date fechaMod ) {
        this.fechaMod = fechaMod;
    }

    public String getIdMod() {
        return idMod;
    }

    public void setIdMod( String idMod ) {
        this.idMod = idMod;
    }

    public String getPrecioListaFormateado() {
        NumberFormat nf = NumberFormat.getCurrencyInstance( Locale.US );
        return nf.format( precioLista );
    }

    public String getPrecioVentaFormateado() {
        NumberFormat nf = NumberFormat.getCurrencyInstance( Locale.US );
        return nf.format( precioVenta );
    }
}

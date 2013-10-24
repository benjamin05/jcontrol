package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Polizas implements Serializable {

    private static final long serialVersionUID = 6190024178684861202L;

    private Integer idPoliza;
    private String idFactura;
    private Integer idCliente;
    private Integer noPoliza;
    private String factura;
    private Date fechaMod;
    private String facturaVenta;
    private Date fechaVenta;
    private Date fechaEntrega;
    private Date vigencia;
    private String status;
    private Date fechaCan;
    private Integer idSucursal;
    private String idMod;
    private List<PolizasDetalle> detalles = new ArrayList<PolizasDetalle>();

    public Integer getIdPoliza() {
        return this.idPoliza;
    }

    public void setIdPoliza( Integer idPoliza ) {
        this.idPoliza = idPoliza;
    }

    public String getIdFactura() {
        return this.idFactura;
    }

    public void setIdFactura( String idFactura ) {
        this.idFactura = idFactura;
    }

    public Integer getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente( Integer idCliente ) {
        this.idCliente = idCliente;
    }

    public Integer getNoPoliza() {
        return this.noPoliza;
    }

    public void setNoPoliza( Integer noPoliza ) {
        this.noPoliza = noPoliza;
    }

    public String getFactura() {
        return this.factura;
    }

    public void setFactura( String factura ) {
        this.factura = factura;
    }

    public Date getFechaMod() {
        return this.fechaMod;
    }

    public void setFechaMod( Date fechaMod ) {
        this.fechaMod = fechaMod;
    }

    public String getFacturaVenta() {
        return this.facturaVenta;
    }

    public void setFacturaVenta( String facturaVenta ) {
        this.facturaVenta = facturaVenta;
    }

    public Date getFechaVenta() {
        return this.fechaVenta;
    }

    public void setFechaVenta( Date fechaVenta ) {
        this.fechaVenta = fechaVenta;
    }

    public Date getFechaEntrega() {
        return this.fechaEntrega;
    }

    public void setFechaEntrega( Date fechaEntrega ) {
        this.fechaEntrega = fechaEntrega;
    }

    public Date getVigencia() {
        return this.vigencia;
    }

    public void setVigencia( Date vigencia ) {
        this.vigencia = vigencia;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    public Date getFechaCan() {
        return this.fechaCan;
    }

    public void setFechaCan( Date fechaCan ) {
        this.fechaCan = fechaCan;
    }

    public Integer getIdSucursal() {
        return this.idSucursal;
    }

    public void setIdSucursal( Integer idSucursal ) {
        this.idSucursal = idSucursal;
    }

    public String getIdMod() {
        return this.idMod;
    }

    public void setIdMod( String idMod ) {
        this.idMod = idMod;
    }

    public List<PolizasDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles( List<PolizasDetalle> detalles ) {
        this.detalles = detalles;
    }
}

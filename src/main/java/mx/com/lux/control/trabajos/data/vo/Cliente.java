package mx.com.lux.control.trabajos.data.vo;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class Cliente implements Serializable {

    private static final long serialVersionUID = 7296281706835309039L;

    private int idCliente;
    private String idConvenio;
    private String titulo;
    private Integer idOftalmologo;
    private String tipoOft;
    private String idLocalidad;
    private String idEstado;
    private Municipio municipio;
    private Date fechaAltaCli;
    private char tipoCli;
    private Boolean sexoCli;
    private String apellidoPatCli;
    private String apellidoMatCli;
    private Boolean FCasadaCli;
    private String nombreCli;
    private String rfcCli;
    private String direccionCli;
    private String coloniaCli;
    private String codigo;
    private String telCasaCli;
    private String telTrabCli;
    private String extTrabCli;
    private String telAdiCli;
    private String extAdiCli;
    private String emailCli;
    private char SUsaAnteojos;
    private Boolean avisar;
    private String idAtendio;
    private char idSync;
    private Date fechaMod;
    private String idMod;
    private int idSucursal;
    private String udf1;
    private String udf2;
    private String cliOri;
    private String udf4;
    private String udf5;
    private String udf6;
    private Integer receta;
    private String obs;
    private Date fechaNac;
    private String cuc;
    private Date horaAlta;
    private Boolean finado;
    private Date fechaImp;
    private boolean modImp;
    private int calif;
    private Date fechaImpUpdate;
    private String tipoImp;
    private String histCuc;
    private String histCli;

    public int getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente( int idCliente ) {
        this.idCliente = idCliente;
    }

    public String getIdConvenio() {
        return this.idConvenio;
    }

    public void setIdConvenio( String idConvenio ) {
        this.idConvenio = idConvenio;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo( String titulo ) {
        this.titulo = titulo;
    }

    public Integer getIdOftalmologo() {
        return this.idOftalmologo;
    }

    public void setIdOftalmologo( Integer idOftalmologo ) {
        this.idOftalmologo = idOftalmologo;
    }

    public String getTipoOft() {
        return this.tipoOft;
    }

    public void setTipoOft( String tipoOft ) {
        this.tipoOft = tipoOft;
    }

    public String getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad( String idLocalidad ) {
        this.idLocalidad = idLocalidad;
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado( String idEstado ) {
        this.idEstado = idEstado;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio( Municipio municipio ) {
        this.municipio = municipio;
    }

    public Date getFechaAltaCli() {
        return this.fechaAltaCli;
    }

    public void setFechaAltaCli( Date fechaAltaCli ) {
        this.fechaAltaCli = fechaAltaCli;
    }

    public char getTipoCli() {
        return this.tipoCli;
    }

    public void setTipoCli( char tipoCli ) {
        this.tipoCli = tipoCli;
    }

    public Boolean getSexoCli() {
        return this.sexoCli;
    }

    public void setSexoCli( Boolean sexoCli ) {
        this.sexoCli = sexoCli;
    }

    public String getApellidoPatCli() {
        return this.apellidoPatCli;
    }

    public void setApellidoPatCli( String apellidoPatCli ) {
        this.apellidoPatCli = apellidoPatCli;
    }

    public String getApellidoMatCli() {
        return this.apellidoMatCli;
    }

    public void setApellidoMatCli( String apellidoMatCli ) {
        this.apellidoMatCli = apellidoMatCli;
    }

    public Boolean getFCasadaCli() {
        return this.FCasadaCli;
    }

    public void setFCasadaCli( Boolean FCasadaCli ) {
        this.FCasadaCli = FCasadaCli;
    }

    public String getNombreCli() {
        return this.nombreCli;
    }

    public void setNombreCli( String nombreCli ) {
        this.nombreCli = nombreCli;
    }

    public String getRfcCli() {
        return this.rfcCli;
    }

    public void setRfcCli( String rfcCli ) {
        this.rfcCli = rfcCli;
    }

    public String getDireccionCli() {
        return this.direccionCli;
    }

    public void setDireccionCli( String direccionCli ) {
        this.direccionCli = direccionCli;
    }

    public String getColoniaCli() {
        return this.coloniaCli;
    }

    public void setColoniaCli( String coloniaCli ) {
        this.coloniaCli = coloniaCli;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo( String codigo ) {
        this.codigo = codigo;
    }

    public String getTelCasaCli() {
        return this.telCasaCli;
    }

    public void setTelCasaCli( String telCasaCli ) {
        this.telCasaCli = telCasaCli;
    }

    public String getTelTrabCli() {
        return this.telTrabCli;
    }

    public void setTelTrabCli( String telTrabCli ) {
        this.telTrabCli = telTrabCli;
    }

    public String getExtTrabCli() {
        return this.extTrabCli;
    }

    public void setExtTrabCli( String extTrabCli ) {
        this.extTrabCli = extTrabCli;
    }

    public String getTelAdiCli() {
        return this.telAdiCli;
    }

    public void setTelAdiCli( String telAdiCli ) {
        this.telAdiCli = telAdiCli;
    }

    public String getExtAdiCli() {
        return this.extAdiCli;
    }

    public void setExtAdiCli( String extAdiCli ) {
        this.extAdiCli = extAdiCli;
    }

    public String getEmailCli() {
        return this.emailCli;
    }

    public void setEmailCli( String emailCli ) {
        this.emailCli = emailCli;
    }

    public char getSUsaAnteojos() {
        return this.SUsaAnteojos;
    }

    public void setSUsaAnteojos( char SUsaAnteojos ) {
        this.SUsaAnteojos = SUsaAnteojos;
    }

    public Boolean getAvisar() {
        return this.avisar;
    }

    public void setAvisar( Boolean avisar ) {
        this.avisar = avisar;
    }

    public String getIdAtendio() {
        return this.idAtendio;
    }

    public void setIdAtendio( String idAtendio ) {
        this.idAtendio = idAtendio;
    }

    public char getIdSync() {
        return this.idSync;
    }

    public void setIdSync( char idSync ) {
        this.idSync = idSync;
    }

    public Date getFechaMod() {
        return this.fechaMod;
    }

    public void setFechaMod( Date fechaMod ) {
        this.fechaMod = fechaMod;
    }

    public String getIdMod() {
        return this.idMod;
    }

    public void setIdMod( String idMod ) {
        this.idMod = idMod;
    }

    public int getIdSucursal() {
        return this.idSucursal;
    }

    public void setIdSucursal( int idSucursal ) {
        this.idSucursal = idSucursal;
    }

    public String getUdf1() {
        return this.udf1;
    }

    public void setUdf1( String udf1 ) {
        this.udf1 = udf1;
    }

    public String getUdf2() {
        return this.udf2;
    }

    public void setUdf2( String udf2 ) {
        this.udf2 = udf2;
    }

    public String getCliOri() {
        return this.cliOri;
    }

    public void setCliOri( String cliOri ) {
        this.cliOri = cliOri;
    }

    public String getUdf4() {
        return this.udf4;
    }

    public void setUdf4( String udf4 ) {
        this.udf4 = udf4;
    }

    public String getUdf5() {
        return this.udf5;
    }

    public void setUdf5( String udf5 ) {
        this.udf5 = udf5;
    }

    public String getUdf6() {
        return this.udf6;
    }

    public void setUdf6( String udf6 ) {
        this.udf6 = udf6;
    }

    public Integer getReceta() {
        return this.receta;
    }

    public void setReceta( Integer receta ) {
        this.receta = receta;
    }

    public String getObs() {
        return this.obs;
    }

    public void setObs( String obs ) {
        this.obs = obs;
    }

    public Date getFechaNac() {
        return this.fechaNac;
    }

    public void setFechaNac( Date fechaNac ) {
        this.fechaNac = fechaNac;
    }

    public String getCuc() {
        return this.cuc;
    }

    public void setCuc( String cuc ) {
        this.cuc = cuc;
    }

    public Date getHoraAlta() {
        return this.horaAlta;
    }

    public void setHoraAlta( Date horaAlta ) {
        this.horaAlta = horaAlta;
    }

    public Boolean getFinado() {
        return this.finado;
    }

    public void setFinado( Boolean finado ) {
        this.finado = finado;
    }

    public Date getFechaImp() {
        return this.fechaImp;
    }

    public void setFechaImp( Date fechaImp ) {
        this.fechaImp = fechaImp;
    }

    public boolean isModImp() {
        return this.modImp;
    }

    public void setModImp( boolean modImp ) {
        this.modImp = modImp;
    }

    public int getCalif() {
        return this.calif;
    }

    public void setCalif( int calif ) {
        this.calif = calif;
    }

    public Date getFechaImpUpdate() {
        return this.fechaImpUpdate;
    }

    public void setFechaImpUpdate( Date fechaImpUpdate ) {
        this.fechaImpUpdate = fechaImpUpdate;
    }

    public String getTipoImp() {
        return this.tipoImp;
    }

    public void setTipoImp( String tipoImp ) {
        this.tipoImp = tipoImp;
    }

    public String getHistCuc() {
        return this.histCuc;
    }

    public void setHistCuc( String histCuc ) {
        this.histCuc = histCuc;
    }

    public String getHistCli() {
        return this.histCli;
    }

    public void setHistCli( String histCli ) {
        this.histCli = histCli;
    }

    public String getNombreCompleto( boolean conTitulo ) {
        StringBuilder sb = new StringBuilder();
        if ( conTitulo ) {
            sb.append( StringUtils.isNotBlank( titulo ) ? titulo.trim().toUpperCase() + " " : "" );
        }
        sb.append( StringUtils.isNotBlank( nombreCli ) ? nombreCli.trim().toUpperCase() + " " : "" );
        sb.append( StringUtils.isNotBlank( apellidoPatCli ) ? apellidoPatCli.trim().toUpperCase() + " " : "" );
        sb.append( StringUtils.isNotBlank( apellidoMatCli ) ? apellidoMatCli.trim().toUpperCase() : "" );
        return sb.toString();
    }
}

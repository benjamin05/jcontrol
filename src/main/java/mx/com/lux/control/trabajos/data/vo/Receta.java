package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.util.Date;

public class Receta implements Serializable {

	private static final long serialVersionUID = 4479193093866250191L;

	private int idReceta;
	private int examen;
	private int idCliente;
	private Date fechaReceta;
	private char sUsoAnteojos;
	private String idOptometrista;
	private String tipoOpt;
	private String odEsfR;
	private String odCilR;
	private String odEjeR;
	private String odAdcR;
	private String odAdiR;
	private String odPrismaH;
	private String oiEsfR;
	private String oiCilR;
	private String oiEjeR;
	private String oiAdcR;
	private String oiAdiR;
	private String oiPrismaH;
	private String diLejosR;
	private String diCercaR;
	private String odAvR;
	private String oiAvR;
	private String altOblR;
	private String observacionesR;
	private boolean FImpresa;
	private char idSync;
	private Date fechaMod;
	private String idMod;
	private int idSucursal;
	private String diOd;
	private String diOi;
	private String materialArm;
	private String odPrismaV;
	private String oiPrismaV;
	private String tratamientos;
	private String udf5;
	private String udf6;
	private String idRxOri;

	public int getIdReceta() {
		return this.idReceta;
	}

	public void setIdReceta( int idReceta ) {
		this.idReceta = idReceta;
	}

	public int getExamen() {
		return this.examen;
	}

	public void setExamen( int examen ) {
		this.examen = examen;
	}

	public int getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente( int idCliente ) {
		this.idCliente = idCliente;
	}

	public Date getFechaReceta() {
		return this.fechaReceta;
	}

	public void setFechaReceta( Date fechaReceta ) {
		this.fechaReceta = fechaReceta;
	}

	public char getsUsoAnteojos() {
		return sUsoAnteojos;
	}

	public void setsUsoAnteojos( char sUsoAnteojos ) {
		this.sUsoAnteojos = sUsoAnteojos;
	}

	public String getIdOptometrista() {
		return this.idOptometrista;
	}

	public void setIdOptometrista( String idOptometrista ) {
		this.idOptometrista = idOptometrista;
	}

	public String getTipoOpt() {
		return this.tipoOpt;
	}

	public void setTipoOpt( String tipoOpt ) {
		this.tipoOpt = tipoOpt;
	}

	public String getOdEsfR() {
		return this.odEsfR;
	}

	public void setOdEsfR( String odEsfR ) {
		this.odEsfR = odEsfR;
	}

	public String getOdCilR() {
		return this.odCilR;
	}

	public void setOdCilR( String odCilR ) {
		this.odCilR = odCilR;
	}

	public String getOdEjeR() {
		return this.odEjeR;
	}

	public void setOdEjeR( String odEjeR ) {
		this.odEjeR = odEjeR;
	}

	public String getOdAdcR() {
		return this.odAdcR;
	}

	public void setOdAdcR( String odAdcR ) {
		this.odAdcR = odAdcR;
	}

	public String getOdAdiR() {
		return this.odAdiR;
	}

	public void setOdAdiR( String odAdiR ) {
		this.odAdiR = odAdiR;
	}

	public String getOdPrismaH() {
		return this.odPrismaH;
	}

	public void setOdPrismaH( String odPrismaH ) {
		this.odPrismaH = odPrismaH;
	}

	public String getOiEsfR() {
		return this.oiEsfR;
	}

	public void setOiEsfR( String oiEsfR ) {
		this.oiEsfR = oiEsfR;
	}

	public String getOiCilR() {
		return this.oiCilR;
	}

	public void setOiCilR( String oiCilR ) {
		this.oiCilR = oiCilR;
	}

	public String getOiEjeR() {
		return this.oiEjeR;
	}

	public void setOiEjeR( String oiEjeR ) {
		this.oiEjeR = oiEjeR;
	}

	public String getOiAdcR() {
		return this.oiAdcR;
	}

	public void setOiAdcR( String oiAdcR ) {
		this.oiAdcR = oiAdcR;
	}

	public String getOiAdiR() {
		return this.oiAdiR;
	}

	public void setOiAdiR( String oiAdiR ) {
		this.oiAdiR = oiAdiR;
	}

	public String getOiPrismaH() {
		return this.oiPrismaH;
	}

	public void setOiPrismaH( String oiPrismaH ) {
		this.oiPrismaH = oiPrismaH;
	}

	public String getDiLejosR() {
		return this.diLejosR;
	}

	public void setDiLejosR( String diLejosR ) {
		this.diLejosR = diLejosR;
	}

	public String getDiCercaR() {
		return this.diCercaR;
	}

	public void setDiCercaR( String diCercaR ) {
		this.diCercaR = diCercaR;
	}

	public String getOdAvR() {
		return this.odAvR;
	}

	public void setOdAvR( String odAvR ) {
		this.odAvR = odAvR;
	}

	public String getOiAvR() {
		return this.oiAvR;
	}

	public void setOiAvR( String oiAvR ) {
		this.oiAvR = oiAvR;
	}

	public String getAltOblR() {
		return this.altOblR;
	}

	public void setAltOblR( String altOblR ) {
		this.altOblR = altOblR;
	}

	public String getObservacionesR() {
		return this.observacionesR;
	}

	public void setObservacionesR( String observacionesR ) {
		this.observacionesR = observacionesR;
	}

	public boolean isFImpresa() {
		return this.FImpresa;
	}

	public void setFImpresa( boolean FImpresa ) {
		this.FImpresa = FImpresa;
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

	public String getDiOd() {
		return this.diOd;
	}

	public void setDiOd( String diOd ) {
		this.diOd = diOd;
	}

	public String getDiOi() {
		return this.diOi;
	}

	public void setDiOi( String diOi ) {
		this.diOi = diOi;
	}

	public String getMaterialArm() {
		return this.materialArm;
	}

	public void setMaterialArm( String materialArm ) {
		this.materialArm = materialArm;
	}

	public String getOdPrismaV() {
		return this.odPrismaV;
	}

	public void setOdPrismaV( String odPrismaV ) {
		this.odPrismaV = odPrismaV;
	}

	public String getOiPrismaV() {
		return this.oiPrismaV;
	}

	public void setOiPrismaV( String oiPrismaV ) {
		this.oiPrismaV = oiPrismaV;
	}

	public String getTratamientos() {
		return this.tratamientos;
	}

	public void setTratamientos( String tratamientos ) {
		this.tratamientos = tratamientos;
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

	public String getIdRxOri() {
		return this.idRxOri;
	}

	public void setIdRxOri( String idRxOri ) {
		this.idRxOri = idRxOri;
	}

	public UsoAnteojos getUsoAnteojos() {
		return UsoAnteojos.parse( sUsoAnteojos );
	}
}

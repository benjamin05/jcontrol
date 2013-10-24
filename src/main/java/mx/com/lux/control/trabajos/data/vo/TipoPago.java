package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;

public class TipoPago implements Serializable {

	private static final long serialVersionUID = -2191003627342055104L;

	private String id;
	private String descripcion;
	private String tipoSoi;
	private String tipoCon;
	private String f1;
	private String f2;
	private String f3;
	private String f4;
	private String f5;

	TipoPago() {
	}

	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion( String descripcion ) {
		this.descripcion = descripcion;
	}

	public String getTipoSoi() {
		return tipoSoi;
	}

	public void setTipoSoi( String tipoSoi ) {
		this.tipoSoi = tipoSoi;
	}

	public String getTipoCon() {
		return tipoCon;
	}

	public void setTipoCon( String tipoCon ) {
		this.tipoCon = tipoCon;
	}

	public String getF1() {
		return f1;
	}

	public void setF1( String f1 ) {
		this.f1 = f1;
	}

	public String getF2() {
		return f2;
	}

	public void setF2( String f2 ) {
		this.f2 = f2;
	}

	public String getF3() {
		return f3;
	}

	public void setF3( String f3 ) {
		this.f3 = f3;
	}

	public String getF4() {
		return f4;
	}

	public void setF4( String f4 ) {
		this.f4 = f4;
	}

	public String getF5() {
		return f5;
	}

	public void setF5( String f5 ) {
		this.f5 = f5;
	}

}

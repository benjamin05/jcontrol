package mx.com.lux.control.trabajos.data.vo;

import java.sql.Timestamp;
import java.util.Date;

public class JbSobre implements java.io.Serializable {

	private static final long serialVersionUID = -8668272146458087059L;

	private Integer id;
	private String folioSobre;
	private String dest;
	private String emp;
	private String area;
	private String contenido;
	private String idViaje;
	private Date fechaEnvio;
	private Timestamp fecha;
	private String idMod;
	private String rx;
    private Integer idSobre;

	public JbSobre() {
	}

	public String getFolioSobre() {
		return this.folioSobre;
	}

	public void setFolioSobre( String folioSobre ) {
		this.folioSobre = folioSobre;
	}

	public String getDest() {
		return this.dest;
	}

	public void setDest( String dest ) {
		this.dest = dest;
	}

	public String getEmp() {
		return this.emp;
	}

	public void setEmp( String emp ) {
		this.emp = emp;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea( String area ) {
		this.area = area;
	}

	public String getContenido() {
		return this.contenido;
	}

	public void setContenido( String contenido ) {
		this.contenido = contenido;
	}

	public String getIdViaje() {
		return this.idViaje;
	}

	public void setIdViaje( String idViaje ) {
		this.idViaje = idViaje;
	}

	public Date getFechaEnvio() {
		return this.fechaEnvio;
	}

	public void setFechaEnvio( Date fechaEnvio ) {
		if ( fechaEnvio != null ) {
			this.fechaEnvio = new Date( fechaEnvio.getTime() );
		}
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha( Timestamp fecha ) {
		if ( fecha != null ) {
			this.fecha = new Timestamp( fecha.getTime() );
		}
	}

	public String getIdMod() {
		return this.idMod;
	}

	public void setIdMod( String idMod ) {
		this.idMod = idMod;
	}

	public String getRx() {
		return this.rx;
	}

	public void setRx( String rx ) {
		this.rx = rx;
	}

	public boolean equals( Object other ) {
		if ( ( this == other ) ) {
			return true;
		}
		if ( ( other == null ) ) {
			return false;
		}
		if ( !( other instanceof JbSobreId ) ) {
			return false;
		}
		JbSobreId castOther = (JbSobreId) other;

		return ( ( this.getFolioSobre() == castOther.getFolioSobre() ) || ( this.getFolioSobre() != null && castOther.getFolioSobre() != null && this.getFolioSobre().equals( castOther.getFolioSobre() ) ) ) && ( ( this.getDest() == castOther.getDest() ) || ( this.getDest() != null && castOther.getDest() != null && this.getDest().equals( castOther.getDest() ) ) ) && ( ( this.getEmp() == castOther.getEmp() ) || ( this.getEmp() != null && castOther.getEmp() != null && this.getEmp().equals( castOther.getEmp() ) ) ) && ( ( this.getArea() == castOther.getArea() ) || ( this.getArea() != null && castOther.getArea() != null && this.getArea().equals( castOther.getArea() ) ) ) && ( ( this.getContenido() == castOther.getContenido() ) || ( this.getContenido() != null && castOther.getContenido() != null && this.getContenido().equals( castOther.getContenido() ) ) ) && ( ( this.getIdViaje() == castOther.getIdViaje() ) || ( this.getIdViaje() != null && castOther.getIdViaje() != null && this.getIdViaje().equals( castOther.getIdViaje() ) ) ) && ( ( this.getFechaEnvio() == castOther.getFechaEnvio() ) || ( this.getFechaEnvio() != null && castOther.getFechaEnvio() != null && this.getFechaEnvio().equals( castOther.getFechaEnvio() ) ) ) && ( ( this.getFecha() == castOther.getFecha() ) || ( this.getFecha() != null && castOther.getFecha() != null && this.getFecha().equals( castOther.getFecha() ) ) ) && ( ( this.getIdMod() == castOther.getIdMod() ) || ( this.getIdMod() != null && castOther.getIdMod() != null && this.getIdMod().equals( castOther.getIdMod() ) ) ) && ( ( this.getRx() == castOther.getRx() ) || ( this.getRx() != null && castOther.getRx() != null && this.getRx().equals( castOther.getRx() ) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getFolioSobre() == null ? 0 : this.getFolioSobre().hashCode() );
		result = 37 * result + ( getDest() == null ? 0 : this.getDest().hashCode() );
		result = 37 * result + ( getEmp() == null ? 0 : this.getEmp().hashCode() );
		result = 37 * result + ( getArea() == null ? 0 : this.getArea().hashCode() );
		result = 37 * result + ( getContenido() == null ? 0 : this.getContenido().hashCode() );
		result = 37 * result + ( getIdViaje() == null ? 0 : this.getIdViaje().hashCode() );
		result = 37 * result + ( getFechaEnvio() == null ? 0 : this.getFechaEnvio().hashCode() );
		result = 37 * result + ( getFecha() == null ? 0 : this.getFecha().hashCode() );
		result = 37 * result + ( getIdMod() == null ? 0 : this.getIdMod().hashCode() );
		result = 37 * result + ( getRx() == null ? 0 : this.getRx().hashCode() );
		return result;
	}

	public Integer getId() {
		return id;
	}

	public void setId( Integer id ) {
		this.id = id;
	}

/*
    public Integer getIdSobre() {
        return idSobre;
    }

    public void setIdSobre(Integer idSobre) {
        this.idSobre = idSobre;
    }
*/

}

package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class JbLlamada implements Serializable {

	private static final long serialVersionUID = -5828371826594076411L;

	private Integer numLlamada;
	private String rx;
	private Timestamp fecha;
	private String estado;
	private String contesto;
	private String empAtendio;
	private String empLlamo;
	private String tipo;
	private String obs;
	private Boolean grupo;
	private String idMod;

	public Integer getNumLlamada() {
		return this.numLlamada;
	}

	public void setNumLlamada( Integer numLlamada ) {
		this.numLlamada = numLlamada;
	}

	public String getRx() {
		return this.rx;
	}

	public void setRx( String rx ) {
		this.rx = rx;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha( Timestamp fecha ) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado( String estado ) {
		this.estado = estado;
	}

	public String getContesto() {
		return this.contesto;
	}

	public void setContesto( String contesto ) {
		this.contesto = contesto;
	}

	public String getEmpAtendio() {
		return this.empAtendio;
	}

	public void setEmpAtendio( String empAtendio ) {
		this.empAtendio = empAtendio;
	}

	public String getEmpLlamo() {
		return this.empLlamo;
	}

	public void setEmpLlamo( String empLlamo ) {
		this.empLlamo = empLlamo;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo( String tipo ) {
		this.tipo = tipo;
	}

	public String getObs() {
		return this.obs;
	}

	public void setObs( String obs ) {
		this.obs = obs;
	}

	public Boolean getGrupo() {
		return this.grupo;
	}

	public void setGrupo( Boolean grupo ) {
		this.grupo = grupo;
	}

	public String getIdMod() {
		return this.idMod;
	}

	public void setIdMod( String idMod ) {
		this.idMod = idMod;
	}

	public boolean equals( Object other ) {
		if ( ( this == other ) )
			return true;
		if ( ( other == null ) )
			return false;
		if ( !( other instanceof JbLlamadaId ) )
			return false;
		JbLlamadaId castOther = (JbLlamadaId) other;

		return ( ( this.getNumLlamada() == castOther.getNumLlamada() ) || ( this.getNumLlamada() != null && castOther.getNumLlamada() != null && this.getNumLlamada().equals( castOther.getNumLlamada() ) ) ) && ( ( this.getRx() == castOther.getRx() ) || ( this.getRx() != null && castOther.getRx() != null && this.getRx().equals( castOther.getRx() ) ) ) && ( ( this.getFecha() == castOther.getFecha() ) || ( this.getFecha() != null && castOther.getFecha() != null && this.getFecha().equals( castOther.getFecha() ) ) ) && ( ( this.getEstado() == castOther.getEstado() ) || ( this.getEstado() != null && castOther.getEstado() != null && this.getEstado().equals( castOther.getEstado() ) ) ) && ( ( this.getContesto() == castOther.getContesto() ) || ( this.getContesto() != null && castOther.getContesto() != null && this.getContesto().equals( castOther.getContesto() ) ) ) && ( ( this.getEmpAtendio() == castOther.getEmpAtendio() ) || ( this.getEmpAtendio() != null && castOther.getEmpAtendio() != null && this.getEmpAtendio().equals( castOther.getEmpAtendio() ) ) ) && ( ( this.getEmpLlamo() == castOther.getEmpLlamo() ) || ( this.getEmpLlamo() != null && castOther.getEmpLlamo() != null && this.getEmpLlamo().equals( castOther.getEmpLlamo() ) ) ) && ( ( this.getTipo() == castOther.getTipo() ) || ( this.getTipo() != null && castOther.getTipo() != null && this.getTipo().equals( castOther.getTipo() ) ) ) && ( ( this.getObs() == castOther.getObs() ) || ( this.getObs() != null && castOther.getObs() != null && this.getObs().equals( castOther.getObs() ) ) ) && ( ( this.getGrupo() == castOther.getGrupo() ) || ( this.getGrupo() != null && castOther.getGrupo() != null && this.getGrupo().equals( castOther.getGrupo() ) ) ) && ( ( this.getIdMod() == castOther.getIdMod() ) || ( this.getIdMod() != null && castOther.getIdMod() != null && this.getIdMod().equals( castOther.getIdMod() ) ) );
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + ( getNumLlamada() == null ? 0 : this.getNumLlamada().hashCode() );
		result = 37 * result + ( getRx() == null ? 0 : this.getRx().hashCode() );
		result = 37 * result + ( getFecha() == null ? 0 : this.getFecha().hashCode() );
		result = 37 * result + ( getEstado() == null ? 0 : this.getEstado().hashCode() );
		result = 37 * result + ( getContesto() == null ? 0 : this.getContesto().hashCode() );
		result = 37 * result + ( getEmpAtendio() == null ? 0 : this.getEmpAtendio().hashCode() );
		result = 37 * result + ( getEmpLlamo() == null ? 0 : this.getEmpLlamo().hashCode() );
		result = 37 * result + ( getTipo() == null ? 0 : this.getTipo().hashCode() );
		result = 37 * result + ( getObs() == null ? 0 : this.getObs().hashCode() );
		result = 37 * result + ( getGrupo() == null ? 0 : this.getGrupo().hashCode() );
		result = 37 * result + ( getIdMod() == null ? 0 : this.getIdMod().hashCode() );
		return result;
	}

}

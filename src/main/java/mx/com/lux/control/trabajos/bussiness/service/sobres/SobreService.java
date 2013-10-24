package mx.com.lux.control.trabajos.bussiness.service.sobres;

import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.JbSobre;
import mx.com.lux.control.trabajos.exception.ApplicationException;

import java.util.List;

public interface SobreService {

	public void deleteSobre( JbSobre jbSobre ) throws ApplicationException;

	public void saveSobre( JbSobre jbSobre ) throws ApplicationException;

	public List<JbSobre> findAllSobres( int firstResult, int resultSize ) throws ApplicationException;

	public int countAllSobres() throws ApplicationException;

	public List<JbSobre> findAllSobresByFechaEnvioNullAndRxEmpty() throws ApplicationException;

	public List<JbSobre> findAllSobresByFechaEnvioNullAndRxNotEmpty() throws ApplicationException;

	public List<JbSobre> findAllSobresByRx( String rx ) throws ApplicationException;

	public void imprimirSobre( Empleado empleado, JbSobre sobre ) throws ApplicationException;

}

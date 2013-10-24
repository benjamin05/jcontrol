package mx.com.lux.control.trabajos.bussiness.service.envio;

import mx.com.lux.control.trabajos.data.vo.JbDev;
import mx.com.lux.control.trabajos.data.vo.JbViaje;
import mx.com.lux.control.trabajos.exception.ApplicationException;

import java.util.Date;
import java.util.List;

public interface EnvioService {

	int findNextIdViaje() throws ApplicationException;

	void imprimirPackingPrevio() throws ApplicationException;

	Boolean saveValuesCerrarViaje( String viajeFolio );

	void imprimirPackingCerrado( String folioViaje ) throws ApplicationException;

	List<JbDev> obtenerJbDevsEnviosPaginado( Integer primerResultado, Integer numeroResultados ) throws ApplicationException;

	Integer contarJbDevsEnvios() throws ApplicationException;

	void imprimirTicketsDevolucionSp( Integer idJbDev, Integer idSucursal ) throws ApplicationException;

    public String imprimirPackingCerrado( String folioViaje, String fecha ) throws ApplicationException;

    public JbViaje obtenerUltimoPackingCerrado( );

}

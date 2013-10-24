package mx.com.lux.control.trabajos.bussiness.service.trabajo;

import mx.com.lux.control.trabajos.data.vo.GParametro;
import mx.com.lux.control.trabajos.exception.ApplicationException;

public interface GParametroService {

	/**
	 * @param id
	 * @return
	 */
	public GParametro findByPrimaryKey( Integer id ) throws ApplicationException;

	/**
	 * @param pARAMETRO_VOLVER_CONTACTAR
	 * @return
	 * @throws ApplicationException
	 */
	public GParametro findById( String pARAMETRO_VOLVER_CONTACTAR ) throws ApplicationException;

}

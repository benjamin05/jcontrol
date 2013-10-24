package mx.com.lux.control.trabajos.bussiness.service.trabajo;

import mx.com.lux.control.trabajos.data.vo.Receta;
import mx.com.lux.control.trabajos.exception.ApplicationException;

public interface RecetaService {

	/**
	 * Devuelve la receta por llave primaria
	 *
	 * @param id
	 * @return
	 */
	public Receta findByPrimaryKey( Integer id ) throws ApplicationException;

	/**
	 * Devuelve la receta por llave primaria
	 *
	 * @param id
	 * @return
	 * @throws ApplicationException
	 */
	public Receta findById( Integer id ) throws ApplicationException;

}

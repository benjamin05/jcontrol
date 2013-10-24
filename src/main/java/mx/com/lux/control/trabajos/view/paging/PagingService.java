package mx.com.lux.control.trabajos.view.paging;

import mx.com.lux.control.trabajos.exception.ApplicationException;

import java.util.List;

public interface PagingService<T> {

	/**
	 * Obtiene un intervalo de registros de una tabla
	 *
	 * @param firstResult
	 * @param resultSize
	 * @return
	 * @throws ApplicationException
	 */
	public List<T> getPage( int firstResult, int resultSize ) throws ApplicationException;

	/**
	 * Obtiene el numero de registros en una tabla
	 *
	 * @return
	 * @throws ApplicationException
	 */
	public int count() throws ApplicationException;
}
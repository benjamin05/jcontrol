package mx.com.lux.control.trabajos.exception;

import org.apache.log4j.Logger;

/**
 * @author Oscar Vazquez Vazquez
 * @version 1.0
 */
public abstract class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger( ApplicationException.class );

	private String errorCode;

	/**
	 * Constructor por parametros para asignar un codigo de error a una excepcion.
	 *
	 * @param errorCode Codigo de Error
	 * @param throwable Excepcion a envolver.
	 */
	protected ApplicationException( String errorCode ) {
		super( errorCode );
		this.errorCode = errorCode;

	}

	/**
	 * Constructor por parametros para asignar un codigo de error a una excepcion.
	 *
	 * @param errorCode Codigo de Error
	 * @param throwable Excepcion a envolver.
	 */
	protected ApplicationException( String errorCode, Throwable throwable ) {
		super( errorCode, throwable );
		this.errorCode = errorCode;
		log.error( errorCode, throwable );
	}

	/**
	 * Devuelve el codigo de error
	 *
	 * @return Codigo de error
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Asigna el codigo de error
	 *
	 * @param string codigo de error
	 */
	public void setErrorCode( String string ) {
		errorCode = string;
	}

}

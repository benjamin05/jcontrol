package mx.com.lux.control.trabajos.exception;

/**
 * @author Oscar Vazquez Vazquez
 * @version 1.0
 */
public class SystemException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor heredado
	 *
	 * @param errorCode Codigo de Error
	 * @param throwable Excepcion a envolver.
	 */
	public SystemException( String errorCode, Throwable throwable ) {
		super( errorCode, throwable );
	}
}

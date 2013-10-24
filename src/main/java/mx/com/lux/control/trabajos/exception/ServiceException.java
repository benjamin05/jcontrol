package mx.com.lux.control.trabajos.exception;

import org.apache.log4j.Logger;

public class ServiceException extends ApplicationException {

	private static final long serialVersionUID = -5595619507052477044L;

	private static final Logger log = Logger.getLogger( ServiceException.class );

	public ServiceException( String errorCode ) {
		super( errorCode );
		log.error( errorCode );
	}

	public ServiceException( String errorCode, Throwable throwable ) {
		super( errorCode, throwable );
		log.error( errorCode, throwable );
	}

}

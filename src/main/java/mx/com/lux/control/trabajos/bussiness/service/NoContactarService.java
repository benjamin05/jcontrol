package mx.com.lux.control.trabajos.bussiness.service;

import mx.com.lux.control.trabajos.exception.ApplicationException;

public interface NoContactarService {

	void procesarNoContactar( String rx, String observaciones ) throws ApplicationException;

}

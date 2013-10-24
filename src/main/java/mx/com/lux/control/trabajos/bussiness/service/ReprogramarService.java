package mx.com.lux.control.trabajos.bussiness.service;

import mx.com.lux.control.trabajos.data.vo.JbGrupo;
import mx.com.lux.control.trabajos.exception.ApplicationException;

import java.util.Date;

public interface ReprogramarService {

	void reprogramarContacto( String rx, Date fechaVolverContactar ) throws ApplicationException;

	void reprogramarContactoGrupo( String rx, Date fechaVolverContactar, JbGrupo grupo ) throws ApplicationException;

}

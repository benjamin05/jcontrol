package mx.com.lux.control.trabajos.bussiness.service.ordenservicio;

import mx.com.lux.control.trabajos.data.vo.TipoContacto;
import mx.com.lux.control.trabajos.exception.ApplicationException;

import java.util.List;

public interface TipoContactoService {

	public List<TipoContacto> findAll() throws ApplicationException;

	public void save( TipoContacto tipoContacto ) throws ApplicationException;

}

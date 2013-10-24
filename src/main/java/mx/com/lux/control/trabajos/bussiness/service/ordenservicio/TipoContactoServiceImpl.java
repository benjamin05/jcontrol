package mx.com.lux.control.trabajos.bussiness.service.ordenservicio;

import mx.com.lux.control.trabajos.data.dao.ordenservicio.TipoContactoDAO;
import mx.com.lux.control.trabajos.data.vo.TipoContacto;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service( "tipoContactoService" )
public class TipoContactoServiceImpl implements TipoContactoService {

	@Resource
	private TipoContactoDAO tipoContactoDAO;

	@Override
	public List<TipoContacto> findAll() throws ApplicationException {
		return (List<TipoContacto>) tipoContactoDAO.findAll();
	}

	@Override
	public void save( TipoContacto tipoContacto ) throws ApplicationException {
		save( tipoContacto );
	}

}

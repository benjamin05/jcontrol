/**
 *
 */
package mx.com.lux.control.trabajos.bussiness.service.trabajo;

import mx.com.lux.control.trabajos.data.dao.trabajo.GParametroDAO;
import mx.com.lux.control.trabajos.data.vo.Cliente;
import mx.com.lux.control.trabajos.data.vo.GParametro;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Oscar Vazquez
 */
@Service( "gParametroService" )
public class GParametroServiceImpl implements GParametroService {

	@Resource
	private GParametroDAO gParametroDAO;

	@Override
	public GParametro findByPrimaryKey( Integer id ) throws ApplicationException {
		return (GParametro) gParametroDAO.findByPrimaryKey( Cliente.class, id );
	}

	@Override
	public GParametro findById( String id ) throws ApplicationException {
		return gParametroDAO.findById( id );
	}

}

/**
 *
 */
package mx.com.lux.control.trabajos.bussiness.service.trabajo;

import mx.com.lux.control.trabajos.data.dao.trabajo.RecetaDAO;
import mx.com.lux.control.trabajos.data.vo.Receta;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Oscar Vazquez
 */
@Service( "recetaService" )
public class RecetaServiceImpl implements RecetaService {

	@Resource
	private RecetaDAO recetaDAO;

	/*
		 * (non-Javadoc)
		 *
		 * @see
		 * mx.com.lux.control.trabajos.bussiness.service.trabajo.RecetaService#findById(java.lang.Integer
		 * )
		 */
	@Override
	public Receta findByPrimaryKey( Integer id ) throws ApplicationException {
		return (Receta) recetaDAO.findByPrimaryKey( Receta.class, id );
	}

	@Override
	public Receta findById( Integer id ) throws ApplicationException {
		return recetaDAO.findById( id );
	}

}

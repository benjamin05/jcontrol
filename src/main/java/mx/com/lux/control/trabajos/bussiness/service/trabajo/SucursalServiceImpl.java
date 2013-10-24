package mx.com.lux.control.trabajos.bussiness.service.trabajo;

import mx.com.lux.control.trabajos.data.dao.envio.SucursalDAO;
import mx.com.lux.control.trabajos.data.vo.Sucursal;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service( "sucursalService" )
public class SucursalServiceImpl implements SucursalService {

	@Resource
	private SucursalDAO sucursalDAO;

	@Override
	public Sucursal findById( int idSucursal ) throws ApplicationException {
		return sucursalDAO.findById( idSucursal );
	}

	@Override
	public List<Sucursal> obtenListaSucursales() {
		try {
			return (List<Sucursal>) sucursalDAO.findAll();
		} catch ( DAOException e ) {
			return new ArrayList<Sucursal>();
		}
	}

}

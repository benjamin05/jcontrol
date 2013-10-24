package mx.com.lux.control.trabajos.bussiness.service.trabajo;

import mx.com.lux.control.trabajos.data.vo.Sucursal;
import mx.com.lux.control.trabajos.exception.ApplicationException;

import java.util.List;

public interface SucursalService {

	public Sucursal findById( int idSucursal ) throws ApplicationException;

	List<Sucursal> obtenListaSucursales();

}
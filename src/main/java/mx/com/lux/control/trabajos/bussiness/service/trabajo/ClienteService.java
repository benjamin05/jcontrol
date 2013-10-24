package mx.com.lux.control.trabajos.bussiness.service.trabajo;

import mx.com.lux.control.trabajos.data.vo.Cliente;
import mx.com.lux.control.trabajos.exception.ApplicationException;

public interface ClienteService {

	Cliente findByPrimaryKey( Integer id ) throws ApplicationException;

	Cliente findClienteById( Integer idCliente ) throws ApplicationException;

	Cliente importarClienteExterno( String idSucursal, String idCliente ) throws ApplicationException;

}

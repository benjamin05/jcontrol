package mx.com.lux.control.trabajos.bussiness.service.trabajo;

import mx.com.lux.control.trabajos.data.dao.trabajo.ClienteDAO;
import mx.com.lux.control.trabajos.data.vo.Cliente;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Service( "clienteService" )
public class ClienteServiceImpl implements ClienteService {

	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	@Resource
	private ClienteDAO clienteDAO;

	@Override
	public Cliente findByPrimaryKey( final Integer id ) throws ApplicationException {
		return (Cliente) clienteDAO.findByPrimaryKey( Cliente.class, id );
	}

	@Override
	public Cliente findClienteById( final Integer id ) throws ApplicationException {
		return clienteDAO.obtenCliente( id );
	}

	@Override
	public Cliente importarClienteExterno( final String idSucursal, final String idCliente ) throws ApplicationException {
		ejecutarShellImportarClienteExterno( idSucursal, idCliente );
		String cliOri = idSucursal.trim() + ":" + idCliente.trim();
		Cliente cliente = clienteDAO.obtenerPorCliOri( cliOri );
		if( cliente == null ) {
			// Cuando no se encuentra el cliente por cliOri hay que buscar en el campo histCli por si el cliente ha ido cambiando de sucursales
			List<Cliente> clientes = clienteDAO.buscarPorHistCli( cliOri );
			for( Cliente clienteTmp : clientes ) {
				String histCli = clienteTmp.getHistCli();
				String[] cliOris = StringUtils.split( histCli, "," );
				for( String cliOriTmp : cliOris ) {
					if( cliOriTmp.equals( cliOri ) ) {
						return clienteTmp;
					}
				}
			}
		}
		return cliente;
	}

	private void ejecutarShellImportarClienteExterno( final String idSucursal, final String idCliente ) throws ServiceException {
		Assert.hasText( idSucursal );
		Assert.hasText( idCliente );
		try {
			String cmd = "./importar_cliente.sh -s " + idSucursal + " -c " + idCliente;
			Runtime run = Runtime.getRuntime();
			Process pr = run.exec( cmd );
			pr.waitFor();
		} catch ( Exception e ) {
			log.error( "Error al ejecutar shell para importar cliente externo: " + e.getMessage() );
			throw new ServiceException( "Error al ejecutar shell para importar cliente externo: " + idCliente );
		}
	}
}

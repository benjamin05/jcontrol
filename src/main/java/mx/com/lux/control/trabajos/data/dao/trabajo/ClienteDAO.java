package mx.com.lux.control.trabajos.data.dao.trabajo;

import mx.com.lux.control.trabajos.data.dao.BasicsDAO;
import mx.com.lux.control.trabajos.data.vo.Cliente;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.List;

public interface ClienteDAO extends BasicsDAO {

	Cliente obtenCliente( Integer id ) throws DAOException;

	Cliente obtenerPorCliOri( String cliOri );

	List<Cliente> buscarPorHistCli( String cliOri );

    int generarIdCliente();

}

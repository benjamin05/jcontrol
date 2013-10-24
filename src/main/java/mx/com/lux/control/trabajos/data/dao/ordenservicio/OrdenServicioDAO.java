package mx.com.lux.control.trabajos.data.dao.ordenservicio;

import mx.com.lux.control.trabajos.data.dao.BasicsDAO;
import mx.com.lux.control.trabajos.data.vo.Cliente;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbServicio;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.List;

public interface OrdenServicioDAO extends BasicsDAO {

	public List<Jb> findAllJbByOrdenAndCliente( String orden, String cliente, int firstResult, int resultSize ) throws DAOException;

	public int countJbOrdenServicio( String orden, String cliente ) throws DAOException;

	public List<Cliente> findAllClienteByApellidosAndNombre( String apPaterno, String apMaterno, String nombre, int firstResult, int resultSize ) throws DAOException;

	public int countAllClienteByApellidosAndNombre( String apPaterno, String apMaterno, String nombre ) throws DAOException;

	List<Jb> findJbByOrden( String orden, String cliente, int firstResult, int resultSize ) throws DAOException;

	int countJbByOrden( String orden, String cliente ) throws DAOException;

	public List<JbServicio> findAllJbServicios() throws DAOException;
}

package mx.com.lux.control.trabajos.data.dao.contacto;

import mx.com.lux.control.trabajos.data.dao.BasicsDAO;
import mx.com.lux.control.trabajos.data.vo.ContactoView;
import mx.com.lux.control.trabajos.data.vo.FormaContacto;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.List;

public interface ContactoViewDAO extends BasicsDAO {

	public List<ContactoView> findAllLlamadasViewByFilters( String atendio, int firstResult, int resultSize ) throws DAOException;

	public int countAllLlamadasViewByFilters( String atendio ) throws DAOException;

	public List<ContactoView> findAllLlamadasView( int firstResult, int resultSize ) throws DAOException;

	public int countAllContactosByEstadoByEmpleadoNumero( String estado, String atendio ) throws DAOException;

	public int countAllContactosByTipoByEmpleadoNumero( String tipo, String atendio ) throws DAOException;

	public List<Jb> findAllGrupos( int firstResult, int resultSize, String nombreGpo, String rx ) throws DAOException;

	public int countAllGrupos( String nombreGpo, String rx ) throws DAOException;

	public String getLastIdGroup( String rx ) throws DAOException;

	public List<Jb> findJbByGrupo( int firstResult, int resultSize, String idGrupo ) throws DAOException;

	public int countJbByGrupo( String idGrupo ) throws DAOException;

	FormaContacto obtenFormaContacto( String rx ) throws DAOException;

}

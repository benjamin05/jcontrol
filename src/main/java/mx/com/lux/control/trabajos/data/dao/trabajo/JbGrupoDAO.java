package mx.com.lux.control.trabajos.data.dao.trabajo;

import mx.com.lux.control.trabajos.data.dao.BasicsDAO;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbGrupo;
import mx.com.lux.control.trabajos.data.vo.JbGrupoDetalle;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.List;

public interface JbGrupoDAO extends BasicsDAO {

	/**
	 * @param id
	 * @return
	 * @throws ApplicationException
	 */
	public JbGrupo findById( Integer id ) throws DAOException;

	public List<JbGrupoDetalle> findJbGrupoDetalleByIdGrupo( Integer id, int firstResult, int resultSize ) throws DAOException;

	public int countJbGrupoDetalleByIdGrupo( Integer idGpo ) throws DAOException;

	public List<Jb> findJbFromJbGrupoDetalleByIdGrupo( Integer idGrupo ) throws DAOException;

	public void saveGrupoTrack( Integer[] operaciones, Object[] object ) throws DAOException;

	List<Jb> listaTrabajosEnGrupo( String idGrupo ) throws DAOException;

}

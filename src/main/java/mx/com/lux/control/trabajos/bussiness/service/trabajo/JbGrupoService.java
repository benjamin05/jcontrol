package mx.com.lux.control.trabajos.bussiness.service.trabajo;

import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbGrupo;
import mx.com.lux.control.trabajos.data.vo.JbGrupoDetalle;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.List;

public interface JbGrupoService {

	public void save( JbGrupo jbGrupo ) throws ApplicationException;

	public JbGrupo findById( Integer id ) throws ApplicationException;

	public List<JbGrupoDetalle> findJbGrupoDetalleByIdGrupo( Integer id, int firstResult, int resultSize ) throws DAOException;

	public int countJbGrupoDetalleByIdGrupo( Integer idGpo ) throws ApplicationException;

	public List<Jb> findJbFromJbGrupoDetalleByIdGrupo( Integer idGrupo ) throws ApplicationException;

	public void saveGrupoTrackContactoRealizado( Object[] object ) throws ApplicationException;

	public void saveGrupoTrackContactoNoRealizado( Object[] object ) throws ApplicationException;

	List<Jb> obtenTrabajosEnGrupo( String rx ) throws ApplicationException;

}

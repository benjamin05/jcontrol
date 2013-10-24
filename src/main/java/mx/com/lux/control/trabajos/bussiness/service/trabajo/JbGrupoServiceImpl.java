package mx.com.lux.control.trabajos.bussiness.service.trabajo;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.trabajo.JbGrupoDAO;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbGrupo;
import mx.com.lux.control.trabajos.data.vo.JbGrupoDetalle;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service( "jbGrupoService" )
public class JbGrupoServiceImpl implements JbGrupoService {

	@Resource
	private JbGrupoDAO jbGrupoDAO;

	@Override
	public void save( JbGrupo jbGrupo ) throws ApplicationException {
		jbGrupoDAO.save( jbGrupo );
	}

	@Override
	public JbGrupo findById( Integer id ) throws ApplicationException {
		return jbGrupoDAO.findById( id );
	}

	@Override
	public List<JbGrupoDetalle> findJbGrupoDetalleByIdGrupo( Integer id, int firstResult, int resultSize ) throws DAOException {
		return jbGrupoDAO.findJbGrupoDetalleByIdGrupo( id, firstResult, resultSize );
	}

	public int countJbGrupoDetalleByIdGrupo( Integer idGpo ) throws ApplicationException {
		return jbGrupoDAO.countJbGrupoDetalleByIdGrupo( idGpo );
	}

	@Override
	public List<Jb> findJbFromJbGrupoDetalleByIdGrupo( Integer idGrupo ) throws ApplicationException {
		return jbGrupoDAO.findJbFromJbGrupoDetalleByIdGrupo( idGrupo );
	}

	@Override
	public void saveGrupoTrackContactoRealizado( Object[] object ) throws ApplicationException {
		Integer[] operaciones = new Integer[object.length];
		operaciones[0] = DAOSupport.INSERT_OR_UPDATE;
		operaciones[1] = DAOSupport.DELETE;
		for ( int i = 2; i < operaciones.length; i++ ) {
			operaciones[i] = DAOSupport.INSERT;
		}
		jbGrupoDAO.saveGrupoTrack( operaciones, object );
	}

	@Override
	public void saveGrupoTrackContactoNoRealizado( Object[] object ) throws ApplicationException {
		Integer[] operaciones = new Integer[object.length];
		operaciones[0] = DAOSupport.INSERT_OR_UPDATE;
		for ( int i = 1; i < operaciones.length; i++ ) {
			operaciones[i] = DAOSupport.INSERT;
		}
		jbGrupoDAO.saveGrupoTrack( operaciones, object );
	}

	@Transactional( readOnly = true )
	public List<Jb> obtenTrabajosEnGrupo( String rx ) throws ApplicationException {
		return jbGrupoDAO.listaTrabajosEnGrupo( rx );
	}
}

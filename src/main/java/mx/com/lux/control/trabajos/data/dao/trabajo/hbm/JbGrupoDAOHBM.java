package mx.com.lux.control.trabajos.data.dao.trabajo.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.trabajo.JbGrupoDAO;
import mx.com.lux.control.trabajos.data.vo.Cliente;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbGrupo;
import mx.com.lux.control.trabajos.data.vo.JbGrupoDetalle;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository( "jbGrupoDAO" )
public class JbGrupoDAOHBM extends DAOSupport implements JbGrupoDAO {

	public static final String QUERY_FIND_BY_ID = "findJbGrupoById";
	public static final String QUERY_FIND_GPO_DETALLE_BY_ID = "findJbGrupoDetalleByIdGrupo";
	public static final String QUERY_COUNT_GPO_DETALLE_BY_ID = "countJbGrupoDetalleByIdGrupo";
	public static final String QUERY_FIND_JB_FROM_GPO_DETALLE_BY_ID = "findJbFromJbGrupoDetalleByIdGrupo";
	public static final String PARAM_ID = "idParam";

	@Autowired
	public JbGrupoDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	/*
		 * (non-Javadoc)
		 *
		 * @see mx.com.lux.control.trabajos.data.dao.BasicsDAO#findAll()
		 */
	@Override
	public List<?> findAll() throws DAOException {
		return findAll( Cliente.class );
	}

	@Override
	public JbGrupo findById( Integer id ) throws DAOException {
		return (JbGrupo) getUniqueResult( QUERY_FIND_BY_ID, new String[]{ PARAM_ID }, new Object[]{ id } );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<JbGrupoDetalle> findJbGrupoDetalleByIdGrupo( Integer id, int firstResult, int resultSize ) throws DAOException {
		return (List<JbGrupoDetalle>) findByNamedQueryPaging( QUERY_FIND_GPO_DETALLE_BY_ID, new String[]{ PARAM_ID }, new Object[]{ id }, firstResult, resultSize );
	}

	public int countJbGrupoDetalleByIdGrupo( Integer idGpo ) throws DAOException {
		return countByNamedQuery( QUERY_COUNT_GPO_DETALLE_BY_ID, new String[]{ PARAM_ID }, new Object[]{ idGpo } );
	}

	@Override
	public List<Jb> findJbFromJbGrupoDetalleByIdGrupo( Integer idGrupo ) throws DAOException {
		return (List<Jb>) findByNamedQuery( QUERY_FIND_JB_FROM_GPO_DETALLE_BY_ID, new String[]{ PARAM_ID }, new Object[]{ idGrupo } );
	}

	@Override
	public void saveGrupoTrack( Integer[] operaciones, Object[] object ) throws DAOException {
		insertUpdateDelete( operaciones, object );
	}

	@Override
	public List<Jb> listaTrabajosEnGrupo( String idGrupo ) throws DAOException {
		if ( StringUtils.isNotEmpty( idGrupo ) ) {
			Criteria criteria = getSession().createCriteria( Jb.class );
			criteria.add( Restrictions.like( "idGrupo", idGrupo ) );
			return criteria.list();
		}
		return new ArrayList<Jb>();
	}
}

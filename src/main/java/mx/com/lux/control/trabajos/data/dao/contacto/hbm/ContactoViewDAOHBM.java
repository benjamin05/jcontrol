package mx.com.lux.control.trabajos.data.dao.contacto.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.contacto.ContactoViewDAO;
import mx.com.lux.control.trabajos.data.vo.ContactoView;
import mx.com.lux.control.trabajos.data.vo.FormaContacto;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "contactoViewDAO" )
public class ContactoViewDAOHBM extends DAOSupport implements ContactoViewDAO {

	private static final String QUERY_SELECT_NEXTVAL_ID_GRUPO = "SELECT nextval('jb_grupo_id_grupo_seq')";
	private static final String QUERY_FINDALL_ENTREGA_RETRASO_ATENDIO = "findAllEntregaRetrasoByAtendio";
	private static final String QUERY_COUNT_ENTREGA_RETRASO_ATENDIO = "countAllEntregaRetrasoByAtendio";
	private static final String QUERY_COUNT_ESTADO_EMPLEADO = "countAllContactosByEstadoByEmpleadoNumero";
	private static final String QUERY_FINDALL_GRUPOS = "findAllGrupos";
	private static final String QUERY_COUNTALL_GRUPOS = "countAllGrupos";
	private static final String QUERY_FIND_JB_BY_GRUPO = "findJbByGrupo";
	private static final String COUNT_JB_BY_GRUPO = "countJbByGrupo";
	private static final String QUERY_COUNT_TIPO_EMPLEADO = "countAllContactosByTipoByEmpleadoNumero";
	private static final String PARAM_ATENDIO = "atendioParam";
	private static final String PARAM_ESTADO = "estadoParam";
	private static final String PARAM_TIPO = "tipoParam";
	private static final String PARAM_ID_GRUPO = "idGrupoParam";
	private static final String PARAM_RX = "rxParam";

	@Autowired
	public ContactoViewDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<?> findAll() throws DAOException {
		return (List<Jb>) findByNamedQuery( "findAll" );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<ContactoView> findAllLlamadasViewByFilters( String atendio, int firstResult, int resultSize ) throws DAOException {
		return (List<ContactoView>) findByNamedQueryPaging( QUERY_FINDALL_ENTREGA_RETRASO_ATENDIO, new String[]{ PARAM_ATENDIO }, new Object[]{ '%' + atendio + '%' }, firstResult, resultSize );
	}

	@Override
	public int countAllLlamadasViewByFilters( String atendio ) throws DAOException {
		return countByNamedQuery( QUERY_COUNT_ENTREGA_RETRASO_ATENDIO, new String[]{ PARAM_ATENDIO }, new Object[]{ '%' + atendio + '%' } );
	}

	@SuppressWarnings( "unchecked" )
	public List<ContactoView> findAllLlamadasView( int firstResult, int resultSize ) throws DAOException {

		List<ContactoView> list = (List<ContactoView>) findByNamedQuery( "findAllEntregaRetraso", firstResult, resultSize );
		return list;
	}

	public int countAllContactosByEstadoByEmpleadoNumero( String estado, String atendio ) throws DAOException {
		return countByNamedQuery( QUERY_COUNT_ESTADO_EMPLEADO, new String[]{ PARAM_ESTADO, PARAM_ATENDIO }, new Object[]{ estado, '%' + atendio + '%' } );
	}

	public int countAllContactosByTipoByEmpleadoNumero( String tipo, String atendio ) throws DAOException {
		return countByNamedQuery( QUERY_COUNT_TIPO_EMPLEADO, new String[]{ PARAM_TIPO, PARAM_ATENDIO }, new Object[]{ tipo, '%' + atendio + '%' } );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<Jb> findAllGrupos( int firstResult, int resultSize, String nombreGpo, String rx ) throws DAOException {
		return (List<Jb>) findByNamedQueryPaging( QUERY_FINDALL_GRUPOS, new String[]{ PARAM_ID_GRUPO, PARAM_RX }, new Object[]{ '%' + nombreGpo + '%', '%' + rx + '%' }, firstResult, resultSize );
	}

	@Override
	public int countAllGrupos( String nombreGpo, String rx ) throws DAOException {
		return countByNamedQuery( QUERY_COUNTALL_GRUPOS, new String[]{ PARAM_ID_GRUPO, PARAM_RX }, new Object[]{ '%' + nombreGpo + '%', '%' + rx + '%' } );
	}

	@Override
	public String getLastIdGroup( String rx ) throws DAOException {
		return getSession().createSQLQuery( QUERY_SELECT_NEXTVAL_ID_GRUPO ).uniqueResult().toString();
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<Jb> findJbByGrupo( int firstResult, int resultSize, String idGrupo ) throws DAOException {
		return (List<Jb>) findByNamedQueryPaging( QUERY_FIND_JB_BY_GRUPO, new String[]{ PARAM_ID_GRUPO }, new Object[]{ idGrupo }, firstResult, resultSize );
	}

	@Override
	public int countJbByGrupo( String idGrupo ) throws DAOException {
		return countByNamedQuery( COUNT_JB_BY_GRUPO, new String[]{ PARAM_ID_GRUPO }, new Object[]{ idGrupo } );
	}

	@Override
	public FormaContacto obtenFormaContacto( String rx ) throws DAOException {
		if ( StringUtils.isNotBlank( rx ) ) {
			Criteria criteria = getSession().createCriteria( FormaContacto.class );
			criteria.add( Restrictions.eq( "rx", rx ) );
			List<FormaContacto> lista = criteria.list();
			if ( !lista.isEmpty() ) {
				return lista.get( 0 );
			}
		}
		return null;
	}
}

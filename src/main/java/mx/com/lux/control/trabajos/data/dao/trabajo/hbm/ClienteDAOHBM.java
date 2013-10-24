package mx.com.lux.control.trabajos.data.dao.trabajo.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.trabajo.ClienteDAO;
import mx.com.lux.control.trabajos.data.vo.Cliente;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.List;

@Repository( "clienteDAO" )
public class ClienteDAOHBM extends DAOSupport implements ClienteDAO {

	@Autowired
	public ClienteDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<?> findAll() throws DAOException {
		return findAll( Cliente.class );
	}

	@Override
	public Cliente obtenCliente( Integer id ) throws DAOException {
		if ( id != null ) {
			return (Cliente) getSession().get( Cliente.class, id );
		}
		return null;
	}

	@Override
	public Cliente obtenerPorCliOri( String cliOri ) {
		Assert.hasText( cliOri );
		Criteria criteria = getSession().createCriteria( Cliente.class );
		criteria.add( Restrictions.eq( "cliOri", cliOri ) );
		return (Cliente) criteria.uniqueResult();
	}

	@Override
	public List<Cliente> buscarPorHistCli( String cliOri ) {
		Assert.hasText( cliOri );
		Criteria criteria = getSession().createCriteria( Cliente.class );
		criteria.add( Restrictions.ilike( "histCli", cliOri, MatchMode.ANYWHERE ) );
		return criteria.list();
	}

    public int generarIdCliente(){

        StringBuilder sb = new StringBuilder();
        //sb.append( " SELECT nextval ( 'clientes_id_cliente_seq' ) as INTEGER" );
        Query query = getSession().createSQLQuery( "select nextval ( 'clientes_id_cliente_seq' ) " );
        int idCliente = Integer.parseInt( query.uniqueResult().toString() );

        return idCliente;
    }
}

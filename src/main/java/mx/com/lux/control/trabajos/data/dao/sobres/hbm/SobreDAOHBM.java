package mx.com.lux.control.trabajos.data.dao.sobres.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.sobres.SobreDAO;
import mx.com.lux.control.trabajos.data.vo.JbSobre;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository( "sobreDAO" )
public class SobreDAOHBM extends DAOSupport implements SobreDAO {

	private static final String QUERY_FINDALL_SOBRES = "findAllSobres";
	private static final String QUERY_COUNTALL_SOBRES = "countAllSobres";
	private static final String QUERY_FINDBY_FECHAENVIONULL_AND_RXEMPTY_SOBRES = "findAllSobresByFechaEnvioNullAndRxEmpty";
	private static final String QUERY_FINDBY_FECHAENVIONULL_AND_RXNOTEMPTY_SOBRES = "findAllSobresByFechaEnvioNullAndRxNotEmpty";
	private static final String QUERY_FINDALL_SOBRES_BY_RX = "findAllSobresByRx";
	private static final String QUERY_RX_PARAM = "rxParam";

	@Autowired
	public SobreDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<JbSobre> findAll() throws DAOException {
		return (List<JbSobre>) findByNamedQuery( QUERY_FINDALL_SOBRES );
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public List<JbSobre> findAllSobres( int firstResult, int resultSize ) throws DAOException {
		return (List<JbSobre>) findByNamedQuery( QUERY_FINDALL_SOBRES, firstResult, resultSize );
	}

	@Override
	public int countAllSobres() throws DAOException {
		return countByNamedQuery( QUERY_COUNTALL_SOBRES );
	}

	@Override
	public void deleteSobre( JbSobre jbSobre ) throws DAOException {
		delete( jbSobre );
	}

	@Override
	public void saveSobre( JbSobre jbSobre ) throws DAOException {
		save( jbSobre );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<JbSobre> findAllSobresByFechaEnvioNullAndRxEmpty() throws DAOException {
		return (List<JbSobre>) findByNamedQuery( QUERY_FINDBY_FECHAENVIONULL_AND_RXEMPTY_SOBRES );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<JbSobre> findAllSobresByFechaEnvioNullAndRxNotEmpty() throws DAOException {
		return (List<JbSobre>) findByNamedQuery( QUERY_FINDBY_FECHAENVIONULL_AND_RXNOTEMPTY_SOBRES );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<JbSobre> findAllSobresByRx( String rx ) throws DAOException {
		return (List<JbSobre>) findByNamedQuery( QUERY_FINDALL_SOBRES_BY_RX, new String[]{ QUERY_RX_PARAM }, new Object[]{ rx } );
	}


    public List<JbSobre> findAllSobresVaciosByViajeAndFechaEnvio( String idViaje, Date fechaEnvio) throws DAOException {

        StringBuilder sb = new StringBuilder();
        sb.append( "FROM JbSobre js " );
        sb.append( "WHERE js.idViaje=:idTrip " );
        sb.append( "AND js.fechaEnvio=:fech " );
        sb.append( "AND (js.rx = '' OR js.rx IS NULL) " );
        Query query = getSession().createQuery( sb.toString() );
        query.setText( "idTrip", idViaje.trim() );
        query.setDate( "fech", fechaEnvio );
        List<JbSobre>lista = query.list();

        return lista;
    }


    public List<JbSobre> findAllSobresNoVaciosByViajeAndFechaEnvio( String idViaje, Date fechaEnvio) throws DAOException{


        StringBuilder sb = new StringBuilder();
        sb.append( "FROM JbSobre js " );
        sb.append( "WHERE js.idViaje=:idTrip " );
        sb.append( "AND js.fechaEnvio=:fech " );
        sb.append( "AND js.rx IS NOT NULL AND js.rx != '' " );
        Query query = getSession().createQuery( sb.toString() );
        query.setText( "idTrip", idViaje.trim() );
        query.setDate( "fech", fechaEnvio );
        List<JbSobre>lista = query.list();

        return lista;
    }


    @SuppressWarnings( "unchecked" )
    @Override
    public JbSobre findAllSobresByFechaEnvioNullAndNotTodayAndRxEmpty( String rx, Date fechaEnvio ) throws DAOException {

        StringBuilder sb = new StringBuilder();
        sb.append( "FROM JbSobre js " );
        sb.append( "WHERE js.rx=:rx " );
        sb.append( "AND js.fechaEnvio!=:fecha" );
        Query query = getSession().createQuery( sb.toString() );
        query.setString( "rx", rx );
        query.setDate( "fecha", fechaEnvio );
        List<JbSobre> lista = query.list();
        if( lista.size() > 0 ){
            JbSobre sobre = lista.get(0);
            return sobre;
        } else {
            return  null;
        }
    }

}

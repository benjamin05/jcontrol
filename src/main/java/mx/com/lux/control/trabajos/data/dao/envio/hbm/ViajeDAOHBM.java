package mx.com.lux.control.trabajos.data.dao.envio.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.envio.ViajeDAO;
import mx.com.lux.control.trabajos.data.vo.JbGarantia;
import mx.com.lux.control.trabajos.data.vo.JbViaje;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository( "viajeDAO" )
public class ViajeDAOHBM extends DAOSupport implements ViajeDAO {

    private static final String QUERY_FINDNEXTIDVIAJE_VIAJE = "findNextIdViaje";

    @Autowired
    public ViajeDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
        this.setSessionFactory( sessionFactory );
    }

    @Override
    public List<?> findAll() throws DAOException {
        return null;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public int findNextIdViaje() throws DAOException {
        List<String> lista = ( List<String> ) findByNamedQuery( QUERY_FINDNEXTIDVIAJE_VIAJE );
        if ( lista.isEmpty() )
            return 1;
        else
            return Integer.parseInt( lista.get( lista.size() - 1 ).trim() ) + 1;

    }

    @SuppressWarnings( "unchecked" )
    @Override
    public JbViaje findFolioViaje( String folioId, Date fech ) throws DAOException {

        JbViaje jbViaje = null;
        Criteria criteria = getSession().createCriteria( JbViaje.class );
        criteria.add( Restrictions.eq( "id.idViaje", folioId ) );
        criteria.add( Restrictions.eq( "id.fecha", fech ) );

        jbViaje = (JbViaje)criteria.uniqueResult();
        if ( jbViaje != null ) {
            return jbViaje;
        } else {
            return null;
        }
    }


    @SuppressWarnings( "unchecked" )
    @Override
    public JbViaje findUltimoViaje( ) {

        JbViaje jbViaje = new JbViaje();
        Criteria criteria = getSession().createCriteria( JbViaje.class );
        criteria.addOrder( Order.asc( "id.fecha" ) );
        List list = criteria.list();
        jbViaje = (JbViaje)list.get( list.size()-1 );

        return jbViaje;
    }

}
package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.JbViajeDetDAO;
import mx.com.lux.control.trabajos.data.vo.JbViaje;
import mx.com.lux.control.trabajos.data.vo.JbViajeDet;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository( "jbViajeDetDAO" )
public class JbViajeDetDAOHBM extends DAOSupport implements JbViajeDetDAO {

    @Autowired
    public JbViajeDetDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
        this.setSessionFactory( sessionFactory );
    }

    @Override
    public List<?> findAll() throws DAOException {
        return null;
    }

    public List<JbViajeDet> buscarPorEnviarLaboratorio( String idViaje, Date fecha ) throws DAOException{

        StringBuilder sb = new StringBuilder();
        sb.append( "FROM JbViajeDet vd " );
        sb.append( "WHERE vd.idViaje=:idTrip " );
        sb.append( "AND vd.fecha=:fech " );
        sb.append( "AND vd.jbTipo = 'LAB' ");
        Query query = getSession().createQuery( sb.toString() );
        query.setText( "idTrip", idViaje.trim() );
        query.setDate( "fech", fecha );
        List<JbViajeDet>lista = query.list();

        return lista;
    }

    public List<JbViajeDet> buscarRotosExternos( String idViaje, Date fecha ) throws DAOException{

        StringBuilder sb = new StringBuilder();
        sb.append( "FROM JbViajeDet vd " );
        sb.append( "WHERE vd.idViaje=:idTrip " );
        sb.append( "AND vd.fecha=:fech " );
        sb.append( "AND vd.jbTipo = 'EXT' ");
        Query query = getSession().createQuery( sb.toString() );
        query.setText( "idTrip", idViaje.trim() );
        query.setDate( "fech", fecha );
        List<JbViajeDet>lista = query.list();

        return lista;
    }

    public List<JbViajeDet> buscarRefRepSP( String idViaje, Date fecha ) throws DAOException{

        StringBuilder sb = new StringBuilder();
        sb.append( "FROM JbViajeDet vd " );
        sb.append( "WHERE vd.idViaje=:idTrip " );
        sb.append( "AND vd.fecha=:fech " );
        sb.append( "AND vd.jbTipo = 'REF' ");
        Query query = getSession().createQuery( sb.toString() );
        query.setText( "idTrip", idViaje.trim() );
        query.setDate( "fech", fecha );
        List<JbViajeDet>lista = query.list();

        return lista;
    }

    public List<JbViajeDet> buscarGar( String idViaje, Date fecha ) throws DAOException{

        StringBuilder sb = new StringBuilder();
        sb.append( "FROM JbViajeDet vd " );
        sb.append( "WHERE vd.idViaje=:idTrip " );
        sb.append( "AND vd.fecha=:fech " );
        sb.append( "AND vd.jbTipo = 'GAR' ");
        Query query = getSession().createQuery( sb.toString() );
        query.setText( "idTrip", idViaje.trim() );
        query.setDate( "fech", fecha );
        List<JbViajeDet>lista = query.list();

        return lista;
    }


    public List<JbViajeDet> buscarOS( String idViaje, Date fecha ) throws DAOException{

        StringBuilder sb = new StringBuilder();
        sb.append( "FROM JbViajeDet vd " );
        sb.append( "WHERE vd.idViaje=:idTrip " );
        sb.append( "AND vd.fecha=:fech " );
        sb.append( "AND vd.jbTipo = 'OS' ");
        Query query = getSession().createQuery( sb.toString() );
        query.setText( "idTrip", idViaje.trim() );
        query.setDate( "fech", fecha );
        List<JbViajeDet>lista = query.list();

        return lista;
    }


    public List<JbViajeDet> buscarExternos( String idViaje, Date fecha ) throws DAOException{

        StringBuilder sb = new StringBuilder();
        sb.append( "FROM JbViajeDet vd " );
        sb.append( "WHERE vd.idViaje=:idTrip " );
        sb.append( "AND vd.fecha=:fech " );
        sb.append( "AND vd.jbTipo = 'X1' ");
        Query query = getSession().createQuery( sb.toString() );
        query.setText( "idTrip", idViaje.trim() );
        query.setDate( "fech", fecha );
        List<JbViajeDet>lista = query.list();

        return lista;
    }


    public void saveJbViajeDet( List<JbViajeDet> viajeDet ) throws DAOException{

            for(JbViajeDet viaje : viajeDet){
                getSession().save( viaje );
            }
    }
}

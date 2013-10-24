package mx.com.lux.control.trabajos.data.dao.ordenservicio.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.ordenservicio.OrdenServicioDAO;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.util.constants.EstadoConstants;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository( "ordenServicioDAO" )
public class OrdenServicioDAOHBM extends DAOSupport implements OrdenServicioDAO {

    private static final String QUERY_FINDALL_JB_BY_ORDEN_CLIENTE = "findJbByOrdenAndCliente";
    private static final String QUERY_COUNT_JB_BY_ORDEN_CLIENTE = "countJbByOrdenAndCliente";
    private static final String QUERY_FINDALL_CLIENTE_BY_APELLIDOS_AND_NOMBRE = "findAllClienteByApellidosAndNombre";
    private static final String QUERY_COUNT_CLIENTE_BY_APELLIDOS_AND_NOMBRE = "countAllClienteByApellidosAndNombre";
    private static final String QUERY_FIND_JB_BY_ORDEN = "findJbByOrden";
    private static final String QUERY_COUNT_JB_BY_ORDEN = "countJbByOrden";

    private static final String QUERY_COUNT_JBNOTA_BY_ORDEN = "countJbNotaByOrden";
    private static final String QUERY_FIND_JBNOTA_BY_ORDEN = "findJbNotaByOrden";

    private static final String QUERY_FINDALL_JBSERVICIOS = "findAllJbServicios";
    private static final String ORDEN_PARAM = "ordenParam";
    private static final String CLIENTE_PARAM = "clienteParam";
    private static final String APELLIDO_PATERNO_PARAM = "apPaternoParam";
    private static final String APELLIDO_MATERNO_PARAM = "apMaternoParam";
    private static final String NOMBRE_PARAM = "nombreParam";

    @Autowired
    public OrdenServicioDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
        this.setSessionFactory( sessionFactory );
    }

    @Override
    public List<Jb> findAllJbByOrdenAndCliente( String orden, String cliente, int firstResult, int resultSize ) throws DAOException {
        String[] names = new String[]{ ORDEN_PARAM, CLIENTE_PARAM };
        Object[] params = new Object[]{ '%' + orden + '%', '%' + cliente + '%' };
        return ( List<Jb> ) findByNamedQueryPaging( QUERY_FINDALL_JB_BY_ORDEN_CLIENTE, names, params, firstResult, resultSize );
    }

    @Override
    public int countJbOrdenServicio( String orden, String cliente ) throws DAOException {
        return countByNamedQuery( QUERY_COUNT_JB_BY_ORDEN_CLIENTE, new String[]{ ORDEN_PARAM,
                CLIENTE_PARAM }, new Object[]{ '%' + orden + '%', '%' + cliente + '%' } );
    }

    @Override
    public List<?> findAll() throws DAOException {
        return null;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<Cliente> findAllClienteByApellidosAndNombre( String apPaterno, String apMaterno, String nombre, int firstResult, int resultSize ) throws DAOException {
        return ( List<Cliente> ) findByNamedQueryPaging( QUERY_FINDALL_CLIENTE_BY_APELLIDOS_AND_NOMBRE, new String[]{
                APELLIDO_PATERNO_PARAM, APELLIDO_MATERNO_PARAM, NOMBRE_PARAM }, new Object[]{ '%' + apPaterno + '%',
                '%' + apMaterno + '%', '%' + nombre + '%' }, firstResult, resultSize );
    }

    @Override
    public int countAllClienteByApellidosAndNombre( String apPaterno, String apMaterno, String nombre ) throws DAOException {
        return countByNamedQuery( QUERY_COUNT_CLIENTE_BY_APELLIDOS_AND_NOMBRE, new String[]{ APELLIDO_PATERNO_PARAM,
                APELLIDO_MATERNO_PARAM, NOMBRE_PARAM }, new Object[]{ '%' + apPaterno + '%', '%' + apMaterno + '%',
                '%' + nombre + '%' } );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<Jb> findJbByOrden( String orden, String cliente, int firstResult, int resultSize ) throws DAOException {
        List<Jb> lstJb = new ArrayList<Jb>();
        lstJb = ( List<Jb> ) findByNamedQueryPaging( QUERY_FIND_JB_BY_ORDEN, new String[]{ ORDEN_PARAM,
                CLIENTE_PARAM }, new Object[]{ '%' + orden + '%', '%' + cliente + '%' }, firstResult, resultSize );
        if ( lstJb.size() <= 0 && StringUtils.isNumeric( orden ) ) {
            List<Jb> lstJbNota = ( List<Jb> ) findByNamedQueryPaging( QUERY_FIND_JBNOTA_BY_ORDEN, new String[]{
                    ORDEN_PARAM, CLIENTE_PARAM }, new Object[]{ '%' + orden + '%' ,
                    '%' + cliente + '%' }, firstResult, resultSize );
            for ( Jb jNota : lstJbNota ) {
                jNota.setJbTipo( null );
                lstJb.add( jNota );
            }
        }
        return lstJb;
    }

    @Override
    public int countJbByOrden( String orden, String cliente ) throws DAOException {
        int count = 0;

        count = countByNamedQuery( QUERY_COUNT_JB_BY_ORDEN, new String[]{ ORDEN_PARAM, CLIENTE_PARAM }, new Object[]{
                '%' + orden + '%', '%' + cliente + '%' } );
        if( count <= 0 && StringUtils.isNumeric( orden ) ){
            count = countByNamedQuery( QUERY_COUNT_JBNOTA_BY_ORDEN, new String[]{ ORDEN_PARAM, CLIENTE_PARAM }, new Object[]{
                    '%' + orden + '%', '%' + cliente + '%' } );
        }

        return count;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<JbServicio> findAllJbServicios() throws DAOException {
        return ( List<JbServicio> ) findByNamedQuery( QUERY_FINDALL_JBSERVICIOS );
    }

}

package mx.com.lux.control.trabajos.data.dao.trabajo.hbm;

import mx.com.lux.control.trabajos.bussiness.service.envio.EnvioService;
import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository( "trabajoDAO" )
public class TrabajoDAOHBM extends DAOSupport implements TrabajoDAO {

    private final Logger log = Logger.getLogger( EnvioService.class );

    private static final String QUERY_FINDALL_Table_jb_BY_FILTERS_PARAM_RX = "rxParam";
    private static final String QUERY_FINDALL_Table_jb_BY_FILTERS_PARAM_ESTADO = "estadoParam";
    private static final String QUERY_FINDALL_Table_jb_BY_FILTERS_PARAM_CLIENTE = "clienteParam";
    private static final String QUERY_FINDALL_ID_FACTURA_PARAM = "idFacturaParam";
    private static final String QUERY_FINDALL_ID_ARTICULO_PARAM = "idArticuloParam";
    private static final String QUERY_GENERICO_PARAM = "genericoParam";
    private static final String QUERY_SURTE_PARAM = "surteParam";
    private static final String QUERY_ID_FACTURA_PARAM = "idFacturaParam";
    private static final String QUERY_FACTURA_VENTA_PARAM = "facturaVentaParam";
    private static final String QUERY_TIPO_PARAM = "tipoParam";
    private static final String QUERY_ID_PARAM = "idParam";
    private static final String QUERY_LOCALIDAD_PARAM = "localidadParam";
    private static final String QUERY_CODIGO_PARAM = "codigoParam";
    private static final String QUERY_FIND_LAST_ESTADO_BY_LLAMADANA = "findLastEstadoByLlamadaNA";
    private static final String QUERY_FINDALL_EMPRESA_FICHA = "findAllEmpresaFicha";
    private static final String QUERY_FINDALL_PARENTESCO_FICHA = "findAllParentescoFichas";
    private static final String QUERY_FIND_PAGOS_BY_ID_FACTURA = "findPagosByIdFactura";
    private static final String QUERY_FINDALL_JB_BY_EDOS_FECHA = "findAllJbByEdosAndFecha";
    private static final String QUERY_FINDALL_MUNICI_BY_EDO_AND_LOCALIDAD = "findAllDelMunByIdEstadoAndLocalidad";
    private static final String QUERY_FINDALL_CODIGO_BY_EDO_AND_LOCALIDAD = "findAllCodigoByIdEstadoAndLocalidad";
    private static final String QUERY_FINDALL_CODIGO_BY_CODIGO = "findAllCodigoByCodigo";
    private static final String QUERY_FIND_JB_BY_ID = "findJbById";
    private static final String QUERY_FIND_NOMINA_BY_FACTURA = "findNominaByFactura";
    private static final String QUERY_FIND_RECETA_BY_FACTURA = "findRecetaIdByFactura";
    private static final String QUERY_FIND_DET_NOTA_VEN_BY_ID_ARTICULO = "findDetNotaVenByIdArt";
    private static final String QUERY_FIND_DET_NOTA_VEN_BY_ID_ARTICULO_SURTE_AND_GENERICO = "findDetNotaVenByIdArtSurteAndGenerico";
    private static final String QUERY_FIND_ARTICULOS_BY_ARTICULO = "findArticulosByArt";
    private static final String QUERY_FIND_JB_PORENVIAR = "findJbPorEnviar";
    private static final String QUERY_COUNT_JB_PORENVIAR = "countJbPorEnviar";
    private static final String QUERY_FIND_JB_NOENVIAR = "findJbNoEnviar";
    private static final String QUERY_COUNT_JB_NOENVIAR = "countJbNoEnviar";
    private static final String QUERY_FIND_JB_BYTIPO = "findByJbTipo";
    private static final String QUERY_FIND_JBDEV_BYFECHAENVIONULL = "findJbDevByFechaEnvioNull";
    private static final String QUERY_FIND_POLIZAS_BY_FACTURA_VENTA = "findPolizasByFacturaVenta";
    private static final String QUERY_FIND_MAX_NOTAFACTURA_BY_FACTURA = "findMaxNotaFacturaByFactura";
    private static final String QUERY_FIND_DOCTOINV_BY_TIPO_AND_ESTADO = "findAllDoctoInvByTipoAndEstado";
    private static final String QUERY_FIND_CAJA_BY_ID_CAJA = "findCajaByIdCaja";
    private static final String QUERY_FIND_ARTICULO_BY_IDARTICULO_IDGENERICO = "findArticuloByIdArtAndIdGeneric";
    private static final String QUERY_FIND_LAST_JBROTO_BY_RX = "findLastJbRotoByRx";
    private static final String QUERY_FIND_JB_BY_IDCLIENTE = "findJbByIdCliente";
    private static final String QUERY_GETALL_ESTADOREPUBLICA = "getAllEstadoRepublica";
    private static final String QUERY_GETALL_DOMINIOS = "getAllDominios";

    @Autowired
    public TrabajoDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
        this.setSessionFactory( sessionFactory );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<?> findAll() throws DAOException {
        return ( List<Jb> ) findByNamedQuery( "findAll" );
    }

    @Override
    public List<EdoGrupo> getEstados() throws DAOException {
        return getSession().createCriteria( EdoGrupo.class ).list();
    }

    @Override
    public List<Jb> findAlljbByFilters( String rx, Integer idEdoGrupo, String atendio, String cliente, int firstResult, int resultSize ) throws DAOException {
        Criteria criteria = getSession().createCriteria( Jb.class );
        if ( StringUtils.isNotBlank( rx ) ) {
            criteria.add( Restrictions.like( "rx", rx, MatchMode.ANYWHERE ) );
        }
        if ( idEdoGrupo != null && idEdoGrupo > 0 ) {
            EdoGrupo edoGrupo = ( EdoGrupo ) getSession().get( EdoGrupo.class, idEdoGrupo );
            if ( edoGrupo != null ) {
                Set<JbEstado> estadoSet = edoGrupo.getEstados();
                List<String> estados = new ArrayList<String>();
                for ( JbEstado estado : estadoSet ) {
                    estados.add( estado.getIdEdo() );
                }
                criteria.add( Property.forName( "estado.idEdo" ).in( estados ) );
            }
        }
        if ( StringUtils.isNotBlank( atendio ) ) {
            criteria.add( Restrictions.like( "empAtendio", atendio, MatchMode.ANYWHERE ) );
        }
        if ( StringUtils.isNotBlank( cliente ) ) {
            criteria.add( Restrictions.like( "cliente", cliente, MatchMode.ANYWHERE ) );
        }
        criteria.addOrder( Order.asc( "rx" ) );
        criteria.addOrder( Order.asc( "cliente" ) );
        criteria.addOrder( Order.asc( "fechaPromesa" ) );
        criteria.addOrder( Order.asc( "empAtendio" ) );
        if ( firstResult >= 0 ) {
            criteria.setFirstResult( firstResult );
        }
        if ( resultSize > 0 ) {
            criteria.setMaxResults( resultSize );
        }
        return criteria.list();
    }

    @Override
    public int countAlljbByFilters( String rx, Integer idEdoGrupo, String atendio, String cliente ) throws DAOException {
        List<Jb> items = findAlljbByFilters( rx, idEdoGrupo, atendio, cliente, 0, 0 );
        return items.size();
    }

    @Override
    public Jb findById( final String rx ) throws DAOException {
        if ( rx != null ) {
            logger.debug( "findById: " + rx );
            return ( Jb ) getUniqueResult( QUERY_FIND_JB_BY_ID, new String[]{
                    QUERY_FINDALL_Table_jb_BY_FILTERS_PARAM_RX }, new Object[]{ rx } );
        }
        logger.error( "findById. rx es null" );
        return null;
    }

    @Override
    public JbLlamada findJbLlamadaById( final String rx ) throws DAOException {
        if ( rx != null ) {
            logger.debug( "findJbLlamadaById: " + rx );
            Criteria criteria = getSession().createCriteria( JbLlamada.class );
            criteria.add( Restrictions.eq( "rx", rx ) );
            return ( JbLlamada ) criteria.uniqueResult();
        }
        logger.error( "findById. rx es null" );
        return null;
    }

    @Override
    public void saveJbAndTrack( Jb jb, JbTrack jbTrack ) throws DAOException {
        try {
            getSession().saveOrUpdate( jb );
            getSession().saveOrUpdate( jbTrack );
        } catch ( Exception e ) {
            System.out.println( e );
        }
    }

    @Override
    public void deleteInsertUpdate( Object[] o ) throws DAOException {
        insertUpdateDelete( new Integer[]{ DELETE, INSERT, INSERT_OR_UPDATE }, o );
    }

    @Override
    public void updateInsert( Object[] o ) throws DAOException {
        insertUpdateDelete( new Integer[]{ INSERT_OR_UPDATE, INSERT }, o );
    }

    @Override
    public void saveJbAndJbGrupo( Jb jb, JbGrupo jbGrupo ) throws DAOException {
        insertUpdateDelete( new Integer[]{ UPDATE, INSERT }, new Object[]{ jb, jbGrupo } );

    }

    @SuppressWarnings( "unchecked" )
    @Override
    public Nomina findNominaByFactura( String rx ) throws DAOException {
        List<Nomina> lista = ( List<Nomina> ) findByNamedQuery( QUERY_FIND_NOMINA_BY_FACTURA, new String[]{
                QUERY_FINDALL_Table_jb_BY_FILTERS_PARAM_RX }, new Object[]{ rx } );
        return lista.isEmpty() ? null : ( Nomina ) lista.get( lista.size() - 1 );
    }

    @Override
    public Integer findIdRecetaByFactura( String rx ) throws DAOException {
        return ( Integer ) getUniqueResult( QUERY_FIND_RECETA_BY_FACTURA, new String[]{
                QUERY_FINDALL_Table_jb_BY_FILTERS_PARAM_RX }, new Object[]{ rx } );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<Jb> findPorEnviar( int firstResult, int resultSize ) throws DAOException {
        return ( List<Jb> ) findByNamedQuery( QUERY_FIND_JB_PORENVIAR, firstResult, resultSize );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<Jb> findNoEnviar( int firstResult, int resultSize ) throws DAOException {
        return ( List<Jb> ) findByNamedQuery( QUERY_FIND_JB_NOENVIAR, firstResult, resultSize );
    }

    @Override
    public int countPorEnviar() throws DAOException {
        return countByNamedQuery( QUERY_COUNT_JB_PORENVIAR );
    }

    @Override
    public int countNoEnviar() throws DAOException {
        return countByNamedQuery( QUERY_COUNT_JB_NOENVIAR );
    }

    @Override
    public List<Jb> findByJbTipo( String tipo ) throws DAOException {
        return ( List<Jb> ) findByNamedQuery( QUERY_FIND_JB_BYTIPO, new Object[]{ tipo } );
    }

    public List<Jb> buscarPorEnviarLaboratorio() throws DAOException {
        return ( List<Jb> ) findByNamedQuery( "buscarPorEnviarLaboratorio" );
    }

    @Override
    public List<Jb> findByJbEstado( String estado ) throws DAOException {
        Criteria criteria = getSession().createCriteria( JbEstado.class );
        criteria.add( Restrictions.eq( "idEdo", estado ) );
        List<JbEstado> estados = criteria.list();
        JbEstado estadoTmp = estados.get( 0 );

        Criteria criteria2 = getSession().createCriteria( Jb.class );
        criteria2.add( Restrictions.eq( "estado", estadoTmp ) );
        criteria2.addOrder( Order.asc( "rx" ) );
        return criteria2.list();
    }

    @Override
    public List<JbDev> findJbDevByFechaEnvioNull() throws DAOException {
        return ( List<JbDev> ) findByNamedQuery( QUERY_FIND_JBDEV_BYFECHAENVIONULL );
    }

    @SuppressWarnings( "unchecked" )
    public List<EmpresaFicha> getEmpresaFicha() throws DAOException {
        return ( List<EmpresaFicha> ) findByNamedQuery( QUERY_FINDALL_EMPRESA_FICHA );
    }

    @SuppressWarnings( "unchecked" )
    public List<ParentescoFichas> getParentescoFichas() throws DAOException {
        return ( List<ParentescoFichas> ) findByNamedQuery( QUERY_FINDALL_PARENTESCO_FICHA );
    }

    @SuppressWarnings( "unchecked" )
    public List<PlazosPagoFichas> getPlazosPagoFichasByTipoVentaNull() throws DAOException {
        DetachedCriteria criteriaEma = DetachedCriteria.forClass( PlazosPagoFichas.class );
        criteriaEma.setProjection( Property.forName( "idPlazo" ) );
        criteriaEma.add( Restrictions.like( "tipoVenta", "EMA", MatchMode.ANYWHERE ) );
        Criteria criteria = getSession().createCriteria( PlazosPagoFichas.class );
        criteria.add( Property.forName( "idPlazo" ).notIn( criteriaEma ) );
        criteria.addOrder( Order.asc( "plazo" ) );
        return criteria.list();
    }

    @SuppressWarnings( "unchecked" )
    public List<PlazosPagoFichas> getAllPlazosPagoFichas() throws DAOException {
        Criteria criteria = getSession().createCriteria( PlazosPagoFichas.class );
        criteria.addOrder( Order.asc( "plazo" ) );
        return criteria.list();
    }

    @Override
    public void saveAcusesNominaJbtrack( Acuses acuses, Nomina nomina, JbTrack jbTrack ) throws DAOException {
        insertUpdateDelete( new Integer[]{ INSERT, INSERT, INSERT }, new Object[]{ acuses, nomina, jbTrack } );
    }

    public void saveAcusesAndJbtrack( Acuses acuses, JbTrack jbTrack ) throws DAOException {
        insertUpdateDelete( new Integer[]{ INSERT, INSERT }, new Object[]{ acuses, jbTrack } );
    }

    public DetalleNotaVenta findDetalleNotaVenByIdArt( String idArt ) throws DAOException {
        return ( DetalleNotaVenta ) getUniqueResult( QUERY_FIND_DET_NOTA_VEN_BY_ID_ARTICULO, new String[]{
                QUERY_FINDALL_ID_FACTURA_PARAM }, new Object[]{ idArt } );
    }

    @SuppressWarnings( "unchecked" )
    public List<DetalleNotaVenta> findAllDetalleNotaVenByIdArt( String idArt ) throws DAOException {
        return ( List<DetalleNotaVenta> ) findByNamedQuery( QUERY_FIND_DET_NOTA_VEN_BY_ID_ARTICULO, new String[]{
                QUERY_FINDALL_ID_FACTURA_PARAM }, new Object[]{ idArt } );
    }

    public DetalleNotaVenta findDetalleNotaVenByIdArtSurteAndGenerico( String idFactura, char surte, char generico ) throws DAOException {
        return ( DetalleNotaVenta ) getUniqueResult( QUERY_FIND_DET_NOTA_VEN_BY_ID_ARTICULO_SURTE_AND_GENERICO, new String[]{
                QUERY_FINDALL_ID_FACTURA_PARAM, QUERY_SURTE_PARAM, QUERY_GENERICO_PARAM }, new Object[]{ idFactura,
                surte, generico } );
    }

    public Articulos findArticulosByArt( Integer art ) throws DAOException {
        return ( Articulos ) getUniqueResult( QUERY_FIND_ARTICULOS_BY_ARTICULO, new String[]{
                QUERY_FINDALL_ID_ARTICULO_PARAM }, new Object[]{ art } );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<Pagos> findPagosByIdFactura( String idFactura ) throws DAOException {
        return ( List<Pagos> ) findByNamedQuery( QUERY_FIND_PAGOS_BY_ID_FACTURA, new String[]{
                QUERY_ID_FACTURA_PARAM }, new Object[]{ idFactura } );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<Polizas> findPolizasByFacturaVenta( String facturaVenta ) throws DAOException {
        return ( List<Polizas> ) findByNamedQuery( QUERY_FIND_POLIZAS_BY_FACTURA_VENTA, new String[]{
                QUERY_FACTURA_VENTA_PARAM }, new Object[]{ facturaVenta } );
    }

    @Override
    public NotaFactura findMaxNotaFacturaByFactura( String factura ) throws DAOException {
        List<NotaFactura> lista = ( List<NotaFactura> ) findByNamedQuery( QUERY_FIND_MAX_NOTAFACTURA_BY_FACTURA, new Object[]{
                factura } );
        if ( lista.size() > 0 )
            return lista.get( 0 );
        else
            return null;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<DoctoInv> findAllDoctoInvByTipoAndEstado( String tipo, String estado ) throws DAOException {
        return ( List<DoctoInv> ) findByNamedQuery( QUERY_FIND_DOCTOINV_BY_TIPO_AND_ESTADO, new String[]{
                QUERY_TIPO_PARAM, QUERY_FINDALL_Table_jb_BY_FILTERS_PARAM_ESTADO }, new Object[]{ tipo, estado } );
    }

    @Override
    public Cajas findCajasById( Integer idCaja ) throws DAOException {
        return ( Cajas ) getUniqueResult( QUERY_FIND_CAJA_BY_ID_CAJA, new String[]{ QUERY_ID_PARAM }, new Object[]{
                idCaja } );
    }

    @Override
    public Articulos findArticuloByIdArtAndIdGeneric( Integer art, char generico ) throws DAOException {
        return ( Articulos ) getUniqueResult( QUERY_FIND_ARTICULO_BY_IDARTICULO_IDGENERICO, new String[]{
                QUERY_FINDALL_ID_ARTICULO_PARAM, QUERY_GENERICO_PARAM }, new Object[]{ art, generico } );
    }

    @Override
    public JbRoto getLastJbRotoByRx( String rx ) throws DAOException {
        @SuppressWarnings( "unchecked" ) List<JbRoto> lst = ( List<JbRoto> ) findByNamedQuery( QUERY_FIND_LAST_JBROTO_BY_RX, new String[]{
                QUERY_FINDALL_Table_jb_BY_FILTERS_PARAM_RX }, new Object[]{ rx } );
        if ( lst.size() > 0 ) {
            JbRoto jbr = lst.get( 0 );
            return jbr;
        } else
            return null;
    }

    @Override
    public void saveOrUpdateDeleteObjectList( Object[] saveUpdateObjects, Object[] deleteObjects ) throws DAOException {
        int total = 0;
        if ( saveUpdateObjects != null )
            if ( saveUpdateObjects.length > 0 )
                total = saveUpdateObjects.length;
        if ( deleteObjects != null )
            if ( deleteObjects.length > 0 )
                total = total + deleteObjects.length;
        Integer[] intList = new Integer[ total ];
        Object[] objList = new Object[ total ];
        int c = 0;

        if ( saveUpdateObjects != null )
            for ( int i = 0; i < saveUpdateObjects.length; i++ ) {
                if ( saveUpdateObjects[ i ] != null ) {
                    objList[ i ] = saveUpdateObjects[ i ];
                    intList[ i ] = INSERT_OR_UPDATE;
                    c++;
                }
            }
        if ( deleteObjects != null )
            for ( int i = 0; i < deleteObjects.length; i++ ) {
                if ( deleteObjects[ i ] != null ) {
                    objList[ c ] = deleteObjects[ i ];
                    intList[ c ] = DELETE;
                    c++;
                }
            }

        insertUpdateDelete( intList, objList );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<Object[]> findJbJbTrackByEdosFecha() throws DAOException {
        return ( List<Object[]> ) findByNamedQuery( QUERY_FINDALL_JB_BY_EDOS_FECHA );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public void updateSaldo( final String rx, final BigDecimal saldo ) throws DAOException {
        Criteria criteria = getSession().createCriteria( Jb.class );
        criteria.add( Restrictions.eq( "rx", rx ) );
        Jb trabajo = ( Jb ) criteria.uniqueResult();
        trabajo.setSaldo( saldo );
        save( trabajo );
    }

    @Override
    public Jb getJbByIdCliente( String idCliente ) throws DAOException {
        // TODO metodo pendiente para ClienteBusquedaDialog
        return ( Jb ) getUniqueResult( QUERY_FIND_JB_BY_IDCLIENTE, new String[]{
                QUERY_FINDALL_Table_jb_BY_FILTERS_PARAM_CLIENTE }, new Object[]{ idCliente } );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<EstadoRepublica> getAllEstadoRepublica() throws DAOException {
        return ( List<EstadoRepublica> ) findByNamedQuery( QUERY_GETALL_ESTADOREPUBLICA );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<Municipio> findAllDelMunByIdEstadoAndLocalidad( String estado, String localidad ) throws DAOException {
        return ( List<Municipio> ) findByNamedQuery( QUERY_FINDALL_MUNICI_BY_EDO_AND_LOCALIDAD, new String[]{
                QUERY_FINDALL_Table_jb_BY_FILTERS_PARAM_ESTADO, QUERY_LOCALIDAD_PARAM }, new Object[]{
                '%' + estado + '%', '%' + localidad + '%' } );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<Codigo> findAllCodigoByIdEstadoAndLocalidad( String estado, String localidad ) throws DAOException {
        return ( List<Codigo> ) findByNamedQuery( QUERY_FINDALL_CODIGO_BY_EDO_AND_LOCALIDAD, new String[]{
                QUERY_FINDALL_Table_jb_BY_FILTERS_PARAM_ESTADO, QUERY_LOCALIDAD_PARAM }, new Object[]{
                '%' + estado + '%', '%' + localidad + '%' } );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<Dominios> getAllDominios() throws DAOException {
        return ( List<Dominios> ) findByNamedQuery( QUERY_GETALL_DOMINIOS );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<Codigo> findAllCodigoByCodigo( String codigo ) throws DAOException {
        return ( List<Codigo> ) findByNamedQuery( QUERY_FINDALL_CODIGO_BY_CODIGO, new String[]{
                QUERY_CODIGO_PARAM }, new Object[]{ codigo } );
    }

    @Override
    public String findLastEstadoFromJbTrackWhereLlamadaNotNA( String rx ) throws DAOException {
        List<String> estados = ( List<String> ) findByNamedQuery( QUERY_FIND_LAST_ESTADO_BY_LLAMADANA, new Object[]{
                rx } );
        return estados.get( 0 );
    }

    @Override
    public EstadoTrabajo obtenUltimoEstadoValidoEnTrack( String rx, EstadoTrabajo estadoPorDescartar ) throws DAOException {
        if ( StringUtils.isNotBlank( rx ) ) {
            DetachedCriteria criteriaEstados = DetachedCriteria.forClass( JbEstado.class );
            criteriaEstados.setProjection( Property.forName( "idEdo" ) );
            criteriaEstados.add( Restrictions.or( Restrictions.eq( "llamada", "NA" ), Restrictions.eq( "idEdo", estadoPorDescartar.codigo() ) ) );
            Criteria criteria = getSession().createCriteria( JbTrack.class );
            criteria.add( Restrictions.eq( "rx", rx ) );
            criteria.add( Property.forName( "estado" ).notIn( criteriaEstados ) );
            criteria.addOrder( Order.desc( "fecha" ) );
            criteria.setMaxResults( 1 );
            JbTrack track = ( JbTrack ) criteria.uniqueResult();
            return EstadoTrabajo.parse( track.getEstado() );
        }
        return null;
    }

    @Override
    public void actualizarFechaEntregaTrabajoExterno( final String rx ) {
        if ( rx != null ) {
            SQLQuery query = getSession().createSQLQuery( "update public.jb_externos set fecha_entrega = current_timestamp where rx = ?" );
            query.setParameter( 0, rx );
            query.executeUpdate();
        }
    }

    @Override
    public List<Jb> buscarTrabajoGarantiaPorRxPorCliente( final String rx, final String cliente ) {
        Criteria criteria = getSession().createCriteria( Jb.class );
        criteria.add( Restrictions.eq( "jbTipo", "GAR" ) );
        if ( StringUtils.isBlank( rx ) && StringUtils.isBlank( cliente ) ) {
            criteria.add( Restrictions.and( Restrictions.ne( "estado.idEdo", "TE" ), Restrictions.and( Restrictions.ne( "estado.idEdo", "CN" ), Restrictions.ne( "estado.idEdo", "BD" ) ) ) );
        }
        if ( StringUtils.isNotBlank( rx ) ) {
            criteria.add( Restrictions.ilike( "rx", rx, MatchMode.ANYWHERE ) );
        }
        if ( StringUtils.isNotBlank( cliente ) ) {
            criteria.add( Restrictions.ilike( "cliente", cliente, MatchMode.ANYWHERE ) );
        }
        return criteria.list();
    }

    @Override
    public Integer contarPorRxPorCliente( final String rx, final String cliente ) {
        Criteria criteria = getSession().createCriteria( Jb.class );
        criteria.add( Restrictions.eq( "jbTipo", "GAR" ) );
        criteria.add( Restrictions.and( Restrictions.ne( "estado.idEdo", "TE" ), Restrictions.and( Restrictions.ne( "estado.idEdo", "CN" ), Restrictions.ne( "estado.idEdo", "BD" ) ) ) );
        if ( StringUtils.isNotBlank( rx ) ) {
            criteria.add( Restrictions.ilike( "rx", rx, MatchMode.ANYWHERE ) );
        }
        if ( StringUtils.isNotBlank( cliente ) ) {
            criteria.add( Restrictions.ilike( "cliente", cliente, MatchMode.ANYWHERE ) );
        }
        criteria.setProjection( Projections.rowCount() );
        return ( Integer ) criteria.uniqueResult();
    }

    @Override
    public String obtenerIdEstadoPorCodigoPostal( String codigoPostal ) {
        Criteria criteria = getSession().createCriteria( Codigo.class );
        criteria.add( Restrictions.eq( "codigo", codigoPostal ) );
        criteria.setProjection( Projections.distinct( Projections.property( "idEstado" ) ) );
        criteria.setMaxResults( 1 );
        return ( String ) criteria.uniqueResult();
    }

    @Override
    public String obtenerIdLocalidadPorCodigoPostal( String codigoPostal ) {
        Criteria criteria = getSession().createCriteria( Codigo.class );
        criteria.add( Restrictions.eq( "codigo", codigoPostal ) );
        criteria.setProjection( Projections.distinct( Projections.property( "idLocalidad" ) ) );
        criteria.setMaxResults( 1 );
        return ( String ) criteria.uniqueResult();
    }

    public List<Jb> obtenerDetalleTrabajosPorRxsPorCliente( List<String> rxs, String cliente, EstadoTrabajo[] estadosOmitir ) {
        Criteria criteria = getSession().createCriteria( Jb.class );
        if ( CollectionUtils.isNotEmpty( rxs ) ) {
            criteria.add( Restrictions.in( "rx", rxs ) );
            for ( EstadoTrabajo estado : estadosOmitir ) {
                criteria.add( Restrictions.ne( "estado.idEdo", estado.codigo() ) );
            }
            if ( StringUtils.isNotBlank( cliente ) ) {
                criteria.add( Restrictions.ilike( "cliente", cliente, MatchMode.ANYWHERE ) );
            }
            return criteria.list();
        } else {
            // Si no hay rxs ... no devolvemos resultados
            return new ArrayList<Jb>();
        }
    }


    public List<JbDev> findJbDevByViajeAndFechaEnvio( String idViaje, Date fechaEnvio ) throws DAOException{

        StringBuilder sb = new StringBuilder();
        sb.append( "FROM JbDev dev " );
        sb.append( "WHERE dev.idViaje=:idTrip " );
        sb.append( "AND dev.fechaEnvio=:fech " );
        Query query = getSession().createQuery( sb.toString() );
        query.setText( "idTrip", idViaje.trim() );
        query.setDate( "fech", fechaEnvio );
        List<JbDev>lista = query.list();

        return lista;
    }


    public List<DoctoInv> findAllDoctoInvByTipoAndEstadoAndFecha( String tipo, String estado, Date fecha ) throws DAOException{

        StringBuilder sb = new StringBuilder();
        sb.append( "FROM DoctoInv dInv " );
        sb.append( "WHERE dInv.idTipoDocto=:tipo " );
        sb.append( "AND dInv.estado=:estado " );
        sb.append( "AND dInv.fechaMod=:fech " );
        Query query = getSession().createQuery( sb.toString() );
        query.setText( "tipo", tipo.trim() );
        query.setText( "estado", estado.trim() );
        query.setDate( "fech", fecha );
        List<DoctoInv>lista = query.list();

        return lista;
    }

    public void saveJb( Jb jb ) throws DAOException{
        getSession().save( jb );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public Integer getLastNumOrdenRepo( String rx ) throws DAOException {

        StringBuilder sb = new StringBuilder();
        sb.append( "FROM Reposicion r " );
        sb.append( "WHERE r.factura=:rxParam " );
        sb.append( "ORDER BY r.numeroOrden" );
        Query query = getSession().createQuery( sb.toString() );
        query.setText( "rxParam", rx.trim() );

        List<Reposicion> lista = query.list();

        Integer numOrden = null;

        if ( lista != null ) {
            for ( Reposicion repo : lista ) {
                numOrden = repo.getNumeroOrden();
            }
        }

        //(List<Reposicion>) findByNamedQuery( "QUERY_FIND_LAST_NUM_ORDEN_REPO", new String[]{ rx }, new Object[]{ rx } );

        return numOrden;
    }

}

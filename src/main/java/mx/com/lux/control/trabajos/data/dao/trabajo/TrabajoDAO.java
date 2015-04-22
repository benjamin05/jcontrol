package mx.com.lux.control.trabajos.data.dao.trabajo;

import mx.com.lux.control.trabajos.data.dao.BasicsDAO;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TrabajoDAO extends BasicsDAO {

	public List<Jb> findAlljbByFilters( String rx, Integer idEdoGrupo, String atendio, String cliente, int firstResult, int resultSize ) throws DAOException;

	public List<EdoGrupo> getEstados() throws DAOException;

	public int countAlljbByFilters( String rx, Integer idEdoGrupo, String atendio, String cliente ) throws DAOException;

	public Jb findById( String rxString ) throws DAOException;

	public JbLlamada findJbLlamadaById( String rxString ) throws DAOException;

	public void saveJbAndTrack( Jb jb, JbTrack jbTrack ) throws DAOException;

	public void deleteInsertUpdate( Object[] o ) throws DAOException;

	public void updateInsert( Object[] o ) throws DAOException;

	public void saveJbAndJbGrupo( Jb jb, JbGrupo jbGrupo ) throws DAOException;

	public Nomina findNominaByFactura( String rx ) throws DAOException;

	public Integer findIdRecetaByFactura( String rx ) throws DAOException;

	public List<Jb> findPorEnviar( int firstResult, int resultSize ) throws DAOException;

	public List<Jb> findNoEnviar( int firstResult, int resultSize ) throws DAOException;

	public int countPorEnviar() throws DAOException;

	public List<EmpresaFicha> getEmpresaFicha() throws DAOException;

	public List<ParentescoFichas> getParentescoFichas() throws DAOException;

	public List<PlazosPagoFichas> getPlazosPagoFichasByTipoVentaNull() throws DAOException;

	public List<PlazosPagoFichas> getAllPlazosPagoFichas() throws DAOException;

	public int countNoEnviar() throws DAOException;

	public List<Jb> findByJbTipo( String tipo ) throws DAOException;

	public List<Jb> buscarPorEnviarLaboratorio() throws DAOException;

	public List<Jb> findByJbEstado( String estado ) throws DAOException;

	public void saveAcusesNominaJbtrack( Acuses acuses, Nomina nomina, JbTrack jbTrack ) throws DAOException;

	public void saveAcusesAndJbtrack( Acuses acuses, JbTrack jbTrack ) throws DAOException;

	public List<JbDev> findJbDevByFechaEnvioNull() throws DAOException;

	public DetalleNotaVenta findDetalleNotaVenByIdArt( String idArt ) throws DAOException;

	public List<DetalleNotaVenta> findAllDetalleNotaVenByIdArt( String idArt ) throws DAOException;

	public DetalleNotaVenta findDetalleNotaVenByIdArtSurteAndGenerico( String IdArt, char surte, char generico ) throws DAOException;

	public Articulos findArticulosByArt( Integer art ) throws DAOException;

	public List<Pagos> findPagosByIdFactura( String idFactura ) throws DAOException;

	public List<Polizas> findPolizasByFacturaVenta( String facturaVenta ) throws DAOException;

	public NotaFactura findMaxNotaFacturaByFactura( String factura ) throws DAOException;

	public List<DoctoInv> findAllDoctoInvByTipoAndEstado( String tipo, String estado ) throws DAOException;

	public Cajas findCajasById( Integer idCaja ) throws DAOException;

	public Articulos findArticuloByIdArtAndIdGeneric( Integer art, char generico ) throws DAOException;

	public JbRoto getLastJbRotoByRx( String rx ) throws DAOException;

	public void saveOrUpdateDeleteObjectList( Object[] saveUpdateObjects, Object[] deleteObjects ) throws DAOException;

	public List<Object[]> findJbJbTrackByEdosFecha() throws DAOException;

	public void updateSaldo( String rx, BigDecimal saldo ) throws DAOException;

	public Jb getJbByIdCliente( String idCliente ) throws DAOException;

	public List<EstadoRepublica> getAllEstadoRepublica() throws DAOException;

	public List<Municipio> findAllDelMunByIdEstadoAndLocalidad( String estado, String localidad ) throws DAOException;

	List<Codigo> findAllCodigoByIdEstadoAndLocalidad( String estado, String localidad ) throws DAOException;

	List<Dominios> getAllDominios() throws DAOException;

	List<Codigo> findAllCodigoByCodigo( String codigo ) throws DAOException;

	String obtenerIdEstadoPorCodigoPostal( String codigoPostal );

	String obtenerIdLocalidadPorCodigoPostal( String codigoPostal );

	String findLastEstadoFromJbTrackWhereLlamadaNotNA( String rx ) throws DAOException;

	EstadoTrabajo obtenUltimoEstadoValidoEnTrack( String rx, EstadoTrabajo estadoPorDescartar ) throws DAOException;

	void actualizarFechaEntregaTrabajoExterno( String rx );

	List<Jb> buscarTrabajoGarantiaPorRxPorCliente( String rx, String cliente );

	Integer contarPorRxPorCliente( String rx, String cliente );

	List<Jb> obtenerDetalleTrabajosPorRxsPorCliente( List<String> rxs, String cliente, EstadoTrabajo[] estadosOmitir );

    public List<JbDev> findJbDevByViajeAndFechaEnvio( String idViaje, Date fechaEnvio ) throws DAOException;

    public List<DoctoInv> findAllDoctoInvByTipoAndEstadoAndFecha( String tipo, String estado, Date fecha ) throws DAOException;

    public void saveJb( Jb jb ) throws DAOException;

    public Integer getLastNumOrdenRepo( String rx ) throws DAOException;

}

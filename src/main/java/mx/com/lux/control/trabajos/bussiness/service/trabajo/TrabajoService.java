package mx.com.lux.control.trabajos.bussiness.service.trabajo;

import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;

import java.math.BigDecimal;
import java.util.List;

public interface TrabajoService {

	List<Jb> findAlljbByFilters( String rx, Integer idEdoGrupo, String atendio, String cliente, int firstResult, int resultSize ) throws ApplicationException;

	int countAlljbByFilters( String rx, Integer idEdoGrupo, String atendio, String cliente ) throws ApplicationException;

	List<EdoGrupo> getEstados();

	Jb findById( String rx );

	JbLlamada findJbLlamadaById( String rxString ) throws ApplicationException;

	void saveJbAndTrack( Jb jb, JbTrack jbTrack ) throws ApplicationException;

	void deleteInsertUpdate( Object[] o ) throws ApplicationException;

	void updateInsert( Object[] o ) throws ApplicationException;

	void saveJbAndJbGrupo( Jb jb, JbGrupo jbGrupo ) throws ApplicationException;

	Nomina findNominaByFactura( String rx ) throws ApplicationException;

	Integer findIdRecetaByFactura( String rx ) throws ApplicationException;

	List<Jb> findPorEnviar( int firstResult, int resultSize ) throws ApplicationException;

	List<Jb> findNoEnviar( int firstResult, int resultSize ) throws ApplicationException;

	List<EmpresaFicha> getEmpresaFicha() throws ApplicationException;

	List<ParentescoFichas> getParentescoFichas() throws ApplicationException;

	List<PlazosPagoFichas> getPlazosPagoFichasByTipoVentaNull() throws ApplicationException;

	List<PlazosPagoFichas> getAllPlazosPagoFichas() throws ApplicationException;

	void saveAcusesNominaJbtrack( Acuses acuses, Nomina nomina, JbTrack jbTrack ) throws ApplicationException;

	int countPorEnviar() throws ApplicationException;

	int countNoEnviar() throws ApplicationException;

	List<Jb> findByJbTipo( String tipo ) throws ApplicationException;

	List<Jb> findByJbEstado( String estado ) throws ApplicationException;

	List<JbDev> findJbDevByFechaEnvioNull() throws ApplicationException;

	boolean validateEnviarEFax( Jb jb );

	NotaVenta findNotaVentaByFactura( String rx );

	DetalleNotaVenta findDetalleNotaVenByIdArt( String idArt ) throws ApplicationException;

	DetalleNotaVenta findDetalleNotaVenByIdArtSurteAndGenerico( String IdArt, char surte, char generico ) throws ApplicationException;

	List<DetalleNotaVenta> findAllDetalleNotaVenByIdArt( String idArt ) throws ApplicationException;

	Articulos findArticulosByArt( Integer art ) throws ApplicationException;

	boolean enviarEFax( String rx, Empleado empleado ) throws ApplicationException;

	String empleadoEmpresaWebResponse( String idEmpleado, String idEmpresa );

	Boolean validateDesretenerTrabajo( Jb jb );

	Boolean validateRetenerTrabajo( EstadoTrabajo estado );

	Boolean validateAutorizacion( Jb jb );

	String createContenidoAcuses( NotaVenta notaVenta, String idEmpresa, String numEmpleado, String rx, String idParentesco, String cliente, String plazo, String sucursal );

	void validarEnviarEFax( String rx ) throws Exception;

	List<Pagos> findPagosByIdFactura( String idFactura ) throws ApplicationException;

	List<Polizas> findPolizasByFacturaVenta( String facturaVenta ) throws ApplicationException;

	NotaFactura findNotaFacturaByIdFiscal( String idFiscal ) throws ApplicationException;

	InstitucionIc findInstitucionIcByIdConvenio( String idConvenio ) throws ApplicationException;

	NotaFactura findMaxNotaFacturaByFactura( String factura ) throws ApplicationException;

	Cajas findCajasById( Integer idCaja ) throws ApplicationException;

	void save( Object object ) throws ApplicationException;

	boolean validarDesagrupar( Jb jb );

	boolean validarAgrupar( Jb jb );

	void validarRecepcion( Jb jb );

	boolean isNoEnviado( Jb jb ) throws ApplicationException;

	Jb isGrupo( Jb jb ) throws ApplicationException;

	Articulos findArticuloByIdArtAndIdGeneric( Integer art, char generico ) throws ApplicationException;

	JbRoto getLastJbRotoByRx( String rx ) throws ApplicationException;

	void trabajoSatisfactorio( Jb jb, Integer idViaje ) throws ApplicationException;

	List<Object[]> findAllJbByEdosAndFecha() throws ApplicationException;

	void agregarRxAGrupo( Jb jb, String idGrupo ) throws ApplicationException;

	void desvincular( String rx ) throws ApplicationException;

	void saveOrUpdateDeleteObjectList( Object[] saveUpdateObjects, Object[] deleteObjects ) throws ApplicationException;

	List<EstadoRepublica> getAllEstadoRepublica();

	List<Municipio> findAllDelMunByIdEstadoAndLocalidad( String estado, String localidad );

	List<Codigo> findAllCodigoByIdEstadoAndLocalidad( String estado, String localidad );

	void delete( Object o ) throws ApplicationException;

	List<Dominios> getAllDominios();

	List<Codigo> findAllCodigoByCodigo( String codigo );

	String obtenerIdEstadoPorCodigoPostal( String codigoPostal );

	String obtenerIdLocalidadPorCodigoPostal( String codigoPostal );

	String findLastEstadoFromJbTrackWhereLlamadaNotNA( String rx ) throws ApplicationException;

	void enviarOrdenDeServicioABodega( Jb os, Empleado empleado ) throws ApplicationException;

	void updateSaldo( final String rx, final BigDecimal saldo ) throws ApplicationException;

	JbNota findJbNota( int id ) throws ApplicationException;

	String obtenSaldoEnTexto( String rx );

	boolean puedeEnviarExterno( Jb job );

	boolean puedeEliminarExterno( Jb job );

	boolean puedeEntregarExterno( String rx );

	FormaContacto obtenerFormaTrabajoPorRx( String rx );

	List<TipoContacto> obtenerTiposFormaContacto();

	Sucursal obtenerSucursalPorNombre( String nombre );

}
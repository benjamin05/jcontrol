package mx.com.lux.control.trabajos.bussiness.service;

import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;

import java.util.List;

public interface ExternoService {

    void enviarExterno( String rx, Integer idSucursalOrigen, Integer idSucursalDestino, String sobre, String observaciones, String idEmpleado ) throws ApplicationException;

    void eliminarExterno( String rx ) throws ApplicationException;

    void entregarExterno( String rx, String idEmpleadoAtendio ) throws ApplicationException;

    void entregarExternoConSaldo( String rx, String idEmpleadoAtendio ) throws ApplicationException;

    List<TipoPago> obtenerTiposPago();

    TipoPago obtenerTipoPagoPorId( String id );

    List<Banco> obtenerBancos();

    Banco obtenerBancoPorId( String id );

    List<Terminal> obtenerTerminales();

    Terminal obtenerTerminalPorId( String id );

    List<Plan> obtenerPlanes();

    JbPagoExtra guardarPagoExtra( JbPagoExtra pagoExtra ) throws ApplicationException;

    JbPagoExtra actualizarPagoExtra( Integer id, JbPagoExtra pagoExtra ) throws ApplicationException;

    List<JbPagoExtra> obtenerPagosExtraPorRxPaginado( String rx, int primerResultado, int numeroResultados );

    List<JbPagoExtra> obtenerPagosExtraPorRx( String rx );

    Integer contarPagosExtraPorRx( String rx );

    void eliminarPagoExtraPorId( Integer id );

    void eliminarPagosExtNoConfirmados( String rx ) throws ApplicationException;

    JbExterno obtenerExternoPorRx( String rx );

    void regresarExternoSucursalOrigen( String rx ) throws ApplicationException;

}

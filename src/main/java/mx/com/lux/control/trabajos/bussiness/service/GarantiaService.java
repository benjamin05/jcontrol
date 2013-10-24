package mx.com.lux.control.trabajos.bussiness.service;

import mx.com.lux.control.trabajos.data.vo.Generico;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbGarantia;
import mx.com.lux.control.trabajos.data.vo.Sucursal;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.ServiceException;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GarantiaService {

	List<Jb> buscarTrabajosGarantiaPorGarantiaPorCliente( String garantia, String cliente );

	Integer contarTrabajosGarantiaPorGarantiaPorCliente( String garantia, String cliente );

	List<Generico> obtenerGenericosGarantias();

	Map<ResultadoValidacion, String> validarGarantia( Generico generico, String factura, String idSucursal, String producto, String color, String idSucursalLocal, String idEmpleado );

	Map<ResultadoAlta, String> altaGarantia( Generico generico, String factura, String idSucursal, String idSucursalLocal, String idEmpleado, String producto, String color, String dejo, String danyo, String condiciones, Date fechaPromesa ) throws ServiceException;

	Boolean entregarGarantia( String rx, String observaciones, String idEmpleado ) throws ApplicationException;

	Boolean desentregarGarantia( String rx, String observaciones, String idEmpleado ) throws ApplicationException;

	Boolean sePuedePonerEnBodega( String rx );

	Boolean sePuedePonerEnBodega( Jb trabajo );

	Boolean sePuedeEntregar( Jb trabajo );

	Boolean sePuedeDesentregar( Jb trabajo );

	Boolean mandarABodega( String rx, String idEmpleado ) throws ApplicationException;

	Boolean existeCentroCostos( String idCentroCostos );

	Sucursal obtenerSucursalPorCentroCostos( String idCentroCostos );

	JbGarantia obtenerGarantiaPorFactura( String rx );

	JbGarantia obtenerGarantiaPorId( Integer idGarantia );

	Boolean existeFactura( String rx );

	public enum ResultadoValidacion {
		RESULTADO_OK,
		RESULTADO_NO_OK,
		PRODUCTO,
		CLIENTE,
		ID_CLIENTE,
		ID_SUCURSAL,
		FECHA_FACTURA,
		COLOR,
		ERROR
	}

	public enum ResultadoAlta {
		ALTA_OK,
		ALTA_NO_OK,
		PRODUCTO,
		CLIENTE,
		ID_CLIENTE,
		ID_SUCURSAL,
		ID_GARANTIA,
		FECHA_FACTURA,
		ERROR
	}

}

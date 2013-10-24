package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.JbGarantia;

public interface JbGarantiaDAO extends BasicsDAO {

	JbGarantia obtenerPorId( Integer id );

	JbGarantia obtenerPorFactura( String factura );

}

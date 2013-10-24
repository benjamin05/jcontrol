package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.TipoPago;

public interface TipoPagoDAO extends BasicsDAO {

	TipoPago obtenerPorId( String id );

}

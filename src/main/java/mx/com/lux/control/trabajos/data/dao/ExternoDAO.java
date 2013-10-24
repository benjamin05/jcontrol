package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.JbExterno;

public interface ExternoDAO extends BasicsDAO {

	JbExterno obtenerPorRx( String rx );

	void actualizarFechaEntrega( String rx );

}

package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.Banco;

public interface BancoDAO extends BasicsDAO {

	Banco obtenerPorId( final String id );
}

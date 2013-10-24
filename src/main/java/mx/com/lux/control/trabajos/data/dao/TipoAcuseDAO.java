package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.TipoAcuse;

public interface TipoAcuseDAO extends BasicsDAO {

	TipoAcuse obtenerPorId( String id );

}

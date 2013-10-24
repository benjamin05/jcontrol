package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.Generico;

import java.util.List;

public interface GenericoDAO extends BasicsDAO {

	List<Generico> obtenerGenericosGarantias();

}

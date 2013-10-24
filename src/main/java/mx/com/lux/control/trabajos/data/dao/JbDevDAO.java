package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.JbDev;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.List;

public interface JbDevDAO extends BasicsDAO {

	List<JbDev> obtenerJbDevsEnviosPaginado( Integer primerResultado, Integer numeroResultados ) throws DAOException;

	Integer contarJbDevsEnvios() throws DAOException;

}

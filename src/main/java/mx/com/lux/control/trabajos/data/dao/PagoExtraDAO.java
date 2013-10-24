package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.JbPagoExtra;

import java.util.List;


public interface PagoExtraDAO extends BasicsDAO {

	List<JbPagoExtra> buscarPorRx( String rx );

	List<JbPagoExtra> buscarPorRxPaginado( String rx, int primerResultado, int numeroResultados );

	void eliminarPorId( Integer id );

}

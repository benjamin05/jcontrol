package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.CondicionIcGenerico;

public interface CondicionIcGenericoDAO extends BasicsDAO {

	CondicionIcGenerico obtenerPorGenericoPorConvenio( String idGenerico, String idConvenio );

}

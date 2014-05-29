package mx.com.lux.control.trabajos.bussiness.service.contacto;

import mx.com.lux.control.trabajos.data.vo.ContactoView;
import mx.com.lux.control.trabajos.data.vo.FormaContacto;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.exception.ApplicationException;

import java.util.List;

public interface ContactoViewService {

	List<ContactoView> findAllLlamadasViewByFilters( String atendio, int firstResult, int resultSize ) throws ApplicationException;

	int countAllLlamadasViewByFilters( String atendio ) throws ApplicationException;

	List<ContactoView> findAllLlamadasView( int firstResult, int resultSize ) throws ApplicationException;

	int countEstado( String estado, String atendio ) throws ApplicationException;

	int countTipo( String tipo, String atendio ) throws ApplicationException;

	List<Jb> findAllGrupos( int firstResult, int resultSize, String nombreGpo, String rx ) throws ApplicationException;

	int countAllGrupos( String nombreGpo, String rx ) throws ApplicationException;

	String getLastIdGroup( String rx ) throws ApplicationException;

	List<Jb> findJbByGrupo( int firstResult, int resultSize, String idGrupo ) throws ApplicationException;

	int countJbByGrupo( String idGrupo ) throws ApplicationException;

	void actualizarFechaPromesa( String idGrupo ) throws ApplicationException;

	void actualizarGrupo( String idGrupo ) throws ApplicationException;

	FormaContacto obtenFormaContactoDeRx( String rx );

	void registraContactoSMS( String rx, Boolean recepcion ) throws ApplicationException;

	void enviarNotificacionCorreoElectronico( final String rx ) throws ApplicationException;
}

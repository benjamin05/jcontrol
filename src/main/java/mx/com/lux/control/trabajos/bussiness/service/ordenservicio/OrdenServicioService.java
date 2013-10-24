package mx.com.lux.control.trabajos.bussiness.service.ordenservicio;

import mx.com.lux.control.trabajos.data.vo.Cliente;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbNota;
import mx.com.lux.control.trabajos.data.vo.JbServicio;
import mx.com.lux.control.trabajos.exception.ApplicationException;

import java.util.List;

public interface OrdenServicioService {

	public List<Jb> findAllJbByOrdenAndCliente( String orden, String cliente, int firstResult, int resultSize ) throws ApplicationException;

	public int countAllJbByOrdenAndCliente( String orden, String cliente ) throws ApplicationException;

	public List<Cliente> findAllClienteByApellidosAndNombre( String apPaterno, String apMaterno, String nombre, int firstResult, int resultSize ) throws ApplicationException;

	public int countAllClienteByApellidosAndNombre( String apPaterno, String apMaterno, String nombre ) throws ApplicationException;

	boolean validarDesentregar( Jb jb );

	int countJbByOrden( String orden, String cliente ) throws ApplicationException;

	List<Jb> findJbByOrden( String orden, String cliente, int firstResult, int resultSize ) throws ApplicationException;

	boolean validarBodega( Jb jb );

	public List<JbServicio> findAllJbServicios() throws ApplicationException;


}

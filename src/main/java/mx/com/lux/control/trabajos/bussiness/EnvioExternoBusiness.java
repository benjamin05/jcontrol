package mx.com.lux.control.trabajos.bussiness;

import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.NotaVenta;
import mx.com.lux.control.trabajos.exception.ServiceException;

public interface EnvioExternoBusiness {

    String generaContenidoAcuseEnvio( Jb trabajo, Integer idSucursalOrigen ) throws ServiceException;

    void generaArchivoEnvio( Jb trabajo, Integer idSucursalOrigen ) throws ServiceException;

    void imprimeTicketEnvio( Jb trabajo, Integer idSucursalOrigen, String idEmpleado ) throws ServiceException;

    String generaContenidoRecetaAcuseEnvio( Jb trabajo, NotaVenta notaVenta ) throws ServiceException;

    void generaArchivoRecetaEnvio( Jb trabajo, NotaVenta notaVenta ) throws ServiceException;

    void imprimeTicketRecetaEnvio( Jb trabajo, NotaVenta notaVenta, Integer idSucursalOrigen, String idEmpleado ) throws ServiceException;

}

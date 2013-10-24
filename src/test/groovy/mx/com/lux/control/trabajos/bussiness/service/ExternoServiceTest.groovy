package mx.com.lux.control.trabajos.bussiness.service

import mx.com.lux.control.trabajos.bussiness.EnvioExternoBusiness
import mx.com.lux.control.trabajos.bussiness.service.impl.ExternoServiceImpl
import mx.com.lux.control.trabajos.data.dao.AcuseDAO
import mx.com.lux.control.trabajos.data.dao.NotaVentaDAO
import mx.com.lux.control.trabajos.data.dao.envio.SucursalDAO
import mx.com.lux.control.trabajos.data.dao.trabajo.JbTrackDAO
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO
import mx.com.lux.control.trabajos.util.constants.AcusesConstants
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo
import spock.lang.Specification
import mx.com.lux.control.trabajos.data.vo.*

class ExternoServiceTest extends Specification {

  def 'enviar externo en concepto'( ) {
    given:
    def trabajoDAO = Mock( TrabajoDAO )
    def sucursalDAO = Mock( SucursalDAO )
    def notaVentaDAO = Mock( NotaVentaDAO )
    def trackDAO = Mock( JbTrackDAO )
    def acuseDAO = Mock( AcuseDAO )
    def envioExternoBusiness = Mock( EnvioExternoBusiness )
    def service = new ExternoServiceImpl(
        trabajoDAO: trabajoDAO,
        sucursalDAO: sucursalDAO,
        notaVentaDAO: notaVentaDAO,
        trackDAO: trackDAO,
        acuseDAO: acuseDAO,
        envioExternoBusiness: envioExternoBusiness
    )
    def trabajo = new Jb(
        rx: 207864,
        estado: EstadoTrabajo.RECIBE_SUCURSAL.entity()
    )
    def idSucursalOrigen = 10
    def sobre = 'PRUEBA'
    def observaciones = 'TEST'
    def idEmpleado = '9999'
    def sucursalDestino = new Sucursal(
        idSucursal: 1,
        nombre: 'LUX SUR'
    )
    def notaVenta = new NotaVenta(
        sucDest: sucursalDestino.idSucursal
    )
    def track = new JbTrack(
        rx: trabajo.rx,
        estado: EstadoTrabajo.POR_ENVIAR_EXTERNO.codigo(),
        empleado: idEmpleado,
        observaciones: sucursalDestino.nombre,
        idMod: 0
    )
    def acuse = new Acuses(
        idTipo: AcusesConstants.EXTERNO_ENVIO
    )

    when:
    service.enviarExterno( trabajo.rx, idSucursalOrigen, sucursalDestino.idSucursal, sobre, observaciones, idEmpleado )

    then:
    1 * trabajoDAO.findById( _ ) >> trabajo
    trabajo.caja == sobre
    trabajo.obsExt == observaciones
    trabajo.externo == sucursalDestino.idSucursal as String
    trabajo.estado() == EstadoTrabajo.POR_ENVIAR_EXTERNO
    1 * trabajoDAO.save( _ )
    1 * sucursalDAO.findById( _ ) >> sucursalDestino
    1 * trackDAO.save( {
      assert it.rx == track.rx
      assert it.estado == track.estado
      assert it.empleado == track.empleado
      assert it.observaciones == track.observaciones
      assert it.idMod == track.idMod
      it
    } )
    1 * envioExternoBusiness.generaContenidoAcuseEnvio( _, _ ) >> acuse.contenido
    1 * acuseDAO.save( {
      assert it.idTipo == acuse.idTipo
      assert it.contenido == acuse.contenido
      it
    } )
    1 * notaVentaDAO.obtenNotaVentaPorTrabajo( _ ) >> notaVenta
    notaVenta.sucDest == trabajo.externo
    1 * notaVentaDAO.guarda( notaVenta )
    1 * envioExternoBusiness.generaArchivoEnvio( trabajo, idSucursalOrigen )
    1 * envioExternoBusiness.imprimeTicketEnvio( trabajo, idSucursalOrigen, idEmpleado )
    1 * trabajoDAO.findJbLlamadaById( trabajo.rx ) >> null
  }

  def 'eliminar externo en concepto'( ) {
    given:
    def trabajoDAO = Mock( TrabajoDAO )
    def service = new ExternoServiceImpl( trabajoDAO: trabajoDAO )
    def estado = EstadoTrabajo.EN_PINO
    def trabajo = new Jb(
        rx: '1',
        estado: EstadoTrabajo.RECIBE_SUCURSAL.entity()
    )

    when:
    service.eliminarExterno( trabajo.rx )

    then:
    1 * trabajoDAO.obtenUltimoEstadoValidoEnTrack( _, _ ) >> estado
    1 * trabajoDAO.findById( _ ) >> trabajo
    trabajo.estado() == EstadoTrabajo.EN_PINO
    1 * trabajoDAO.save( _ )
  }
}

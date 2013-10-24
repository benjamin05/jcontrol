package mx.com.lux.control.trabajos.bussiness.service

import mx.com.lux.control.trabajos.bussiness.service.impl.TicketServiceImpl
import mx.com.lux.control.trabajos.data.dao.trabajo.CajasDAO
import mx.com.lux.control.trabajos.data.vo.Cajas
import org.apache.velocity.app.VelocityEngine
import spock.lang.Specification

class TicketServiceTest extends Specification {

  def engine = new VelocityEngine()

  def setup( ) {
    engine.addProperty( 'resource.loader', 'class' )
    engine.addProperty( 'class.resource.loader.class', 'org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader' )
  }

  def 'imprime ticket de envio externo'( ) {
    given:
    def cajasDAO = Mock( CajasDAO )
    def ticketService = new TicketServiceImpl(
        velocityEngine: engine,
        cajasDAO: cajasDAO
    )
    def elementos = [
        sucursal_origen: 'LUX COYOACAN',
        id_sucursal_origen: 5,
        sucursal_destino: 'LUX SUR',
        id_sucursal_destino: 10,
        fecha: '23-03-2012',
        envio: 'JUAN LOPEZ PEREZ',
        sobre: 'PRUEBA',
        cliente: 'FRANCISCO JAVIER VILLA VARGAS',
        telefono_casa: '5544332211',
        telefono_trabajo: '5544332211',
        telefono_trabajo_ext: '123',
        telefono_adicional: '5544332211',
        telefono_adicional_ext: '123',
        factura: '123456',
        material: 'X1X MONOFOCAL',
        saldo: '\$199.00',
        optometrista: 'JOSE LUIS NAJERA VAZQUEZ',
        observaciones: 'TEST'
    ]
    def cajas = new Cajas( impTicket: 'lpr -Plp0' )

    when:
    ticketService.imprimeEnvioExterno( elementos )

    then:
    1 * cajasDAO.findCajasById( _ ) >> cajas
  }

  def 'imprime ticket de receta de envio externo'( ) {
    given:
    def cajasDAO = Mock( CajasDAO )
    def ticketService = new TicketServiceImpl(
        cajasDAO: cajasDAO,
        velocityEngine: engine
    )
    def elementos = [
        nombre_archivo: '00100351820RX',
        sucursal_origen: 'SEARS INSURGENTES',
        id_sucursal_origen: 51,
        sucursal_destino: 'LUX COYOACAN',
        id_sucursal_destino: 5,
        factura: '035182',
        fecha_hora_factura: '01-06-2007',
        fecha_prometida: '01-06-2007',
        optometrista: 'JOSE LUIS NAJERA VAZQUEZ',
        usuario: 'JUAN PEREZ LOPEZ',
        copia: '1',
        cliente: 'SR. FRANCISCO JAVIER VILLA VARGAS',
        telefono_casa: '5544332211',
        telefono_trabajo: '5544332211',
        telefono_trabajo_ext: '1234',
        telefono_adicional: '5544332211',
        saldo: '\$0.00',
        articulos: 'X1X-MONOFOCAL 1, X2X-MONOFOCAL 2',
        esf_d: '-99.99',
        cil_d: '-99.99',
        eje_d: '-99.99',
        adc_d: '-99.99',
        adi_d: '-99.99',
        pri_d: '-99.99',
        esf_i: '-99.99',
        cil_i: '-99.99',
        eje_i: '-99.99',
        adc_i: '-99.99',
        adi_i: '-99.99',
        pri_i: '-99.99',
        dil: '-99.99',
        dic: '-99.99',
        dmd: '-99.99',
        dmi: '-99.99',
        armazon: 'AT2101 10 [S]',
        uso: 'LEJOS',
        material: 'AIRE',
        forma_lente: 'AT2101'
    ]
    def cajas = new Cajas( impTicket: 'lpr -Plp0' )

    when:
    ticketService.imprimeRecetaEnvioExterno( elementos )

    then:
    1 * cajasDAO.findCajasById( _ ) >> cajas
  }
}

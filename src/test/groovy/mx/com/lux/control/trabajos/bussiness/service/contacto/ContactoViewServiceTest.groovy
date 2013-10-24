package mx.com.lux.control.trabajos.bussiness.service.contacto

import mx.com.lux.control.trabajos.data.dao.contacto.ContactoViewDAO
import mx.com.lux.control.trabajos.data.dao.trabajo.GParametroDAO
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO
import mx.com.lux.control.trabajos.util.constants.Constants
import mx.com.lux.control.trabajos.view.Session
import org.apache.velocity.app.VelocityEngine
import spock.lang.Specification
import mx.com.lux.control.trabajos.data.vo.*

class ContactoViewServiceTest extends Specification {

  def velocityEngine = new VelocityEngine()

  def setup( ) {
    velocityEngine.addProperty( 'resource.loader', 'class' )
    velocityEngine.addProperty( 'class.resource.loader.class', 'org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader' )
  }

  def 'registra contacto por sms con entrega'( ) {
    given:
    def expected = 'destinoVal=5533992661|id_suc_oriVal=9999|nombreVal=PRUEBA PRUEBA SISTEMAS|rxVal=216875|' +
        'textoVal=Opticas Lux se complace en informarle que su orden de trabajo 216875 esta lista para ser ' +
        'recogida en su sucursal LUX PRUEBA|'
    def rx = '216875'
    def contactoViewDAO = Mock( ContactoViewDAO )
    def trabajoDAO = Mock( TrabajoDAO )
    def gParametroDAO = Mock( GParametroDAO )
    def service = new ContactoViewServiceImpl(
        contactoViewDAO: contactoViewDAO,
        trabajoDAO: trabajoDAO,
        gParametroDAO: gParametroDAO,
        velocityEngine: velocityEngine
    )
    def trabajo = new Jb(
        rx: rx,
        saldo: new BigDecimal( 0.00 ),
        cliente: 'PRUEBA PRUEBA SISTEMAS',
        estado: new JbEstado(
            idEdo: 'RS'
        )
    )
    def empleado = new Empleado(
        sucursal: new Sucursal(
            idSucursal: 9999,
            nombre: 'LUX PRUEBA'
        )
    )
    Session.setAttribute( Constants.PARAM_USER_LOGGED, empleado )
    def formaContacto = new FormaContacto(
        contacto: ' 55 33 99 26 61 '
    )
    def llamada = new JbLlamada(
        tipo: 'ENTREGAR'
    )
    def gParametro = new GParametro(
        valor: 'Opticas Lux se complace en informarle que su orden de trabajo {rx} esta lista para ser recogida ' +
            'en su sucursal {sucursal}'
    )
    def acuse = new Acuses(
        idTipo: 'sms',
        contenido: expected
    )
    contactoViewDAO.obtenFormaContacto( _ ) >> formaContacto
    trabajoDAO.findById( _ ) >> trabajo
    trabajoDAO.findJbLlamadaById( _ ) >> llamada
    gParametroDAO.findById( _ ) >> gParametro

    when:
    service.registraContactoSMS( rx )

    then:
    1 * contactoViewDAO.save( {
      assert it.idTipo == acuse.idTipo
      assert it.contenido == acuse.contenido
      it
    } )
  }

  def 'registra contacto por sms con retraso'( ) {
    given:
    def expected = 'destinoVal=5533992661|id_suc_oriVal=16|nombreVal=MARENTES MARTINEZ PABLO|rxVal=210585|' +
        'textoVal=Opticas Lux le notifica que su orden de trabajo 210585 no podra ser entregada en la fecha ' +
        'comprometida. Le agradecemos su comprension. Atte. LUX MOLIERE|'
    def rx = '210585'
    def contactoViewDAO = Mock( ContactoViewDAO )
    def trabajoDAO = Mock( TrabajoDAO )
    def gParametroDAO = Mock( GParametroDAO )
    def service = new ContactoViewServiceImpl(
        contactoViewDAO: contactoViewDAO,
        trabajoDAO: trabajoDAO,
        gParametroDAO: gParametroDAO,
        velocityEngine: velocityEngine
    )
    def trabajo = new Jb(
        rx: rx,
        saldo: new BigDecimal( 10.00 ),
        cliente: 'MARENTES MARTINEZ PABLO',
        estado: new JbEstado(
            idEdo: 'RS'
        )
    )
    def empleado = new Empleado(
        sucursal: new Sucursal(
            idSucursal: 16,
            nombre: 'LUX MOLIERE'
        )
    )
    Session.setAttribute( Constants.PARAM_USER_LOGGED, empleado )
    def formaContacto = new FormaContacto(
        contacto: '5533992661'
    )
    def llamada = new JbLlamada(
        tipo: 'RETRASADO'
    )
    def gParametro = new GParametro(
        valor: 'Opticas Lux le notifica que su orden de trabajo {rx} no podra ser entregada en la fecha ' +
            'comprometida. Le agradecemos su comprension. Atte. {sucursal}'
    )
    def acuse = new Acuses(
        idTipo: 'sms',
        contenido: expected
    )
    contactoViewDAO.obtenFormaContacto( _ ) >> formaContacto
    trabajoDAO.findById( _ ) >> trabajo
    trabajoDAO.findJbLlamadaById( _ ) >> llamada
    gParametroDAO.findById( _ ) >> gParametro

    when:
    service.registraContactoSMS( rx )

    then:
    1 * contactoViewDAO.save( {
      assert it.idTipo == acuse.idTipo
      assert it.contenido == acuse.contenido
      it
    } )
  }
}

package mx.com.lux.control.trabajos.data.dao

import mx.com.lux.control.trabajos.data.dao.hbm.ExamenDAOHBM
import org.hibernate.SessionFactory
import spock.lang.Specification

class ExamenDAOTest extends Specification {

  def 'obten examen por rx de trabajo'( ) {
    given:
    def sessionFactory = Mock( SessionFactory )
    def dao = new ExamenDAOHBM( sessionFactory )

    when:
    def actual = dao.obtenExamenPorTrabajo( value )

    then:
    actual == expected

    where:
    expected | value
    null     | null
    null     | ''
    null     | 'a'
  }
}

package mx.com.lux.control.trabajos.util

import spock.lang.Specification

class DateUtilsTest extends Specification {

  def 'obtener fecha como texto con formato'( ) {
    when:
    def actual = DateUtils.formatDate( date, format )

    then:
    expected == actual

    where:
    expected     | date                                     | format
    ''           | null                                     | null
    ''           | new Date()                               | null
    ''           | null                                     | ''
    ''           | null                                     | 'null'
    '01/01/2012' | Date.parse( 'dd/MM/yyyy', '01/01/2012' ) | 'dd/MM/yyyy'
    '01-01-2012' | Date.parse( 'dd/MM/yyyy', '01/01/2012' ) | 'dd-MM-yyyy'
  }
}

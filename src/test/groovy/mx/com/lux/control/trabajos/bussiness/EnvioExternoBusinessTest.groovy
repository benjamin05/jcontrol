package mx.com.lux.control.trabajos.bussiness

import mx.com.lux.control.trabajos.bussiness.impl.EnvioExternoBusinessImpl
import mx.com.lux.control.trabajos.bussiness.service.TicketService
import mx.com.lux.control.trabajos.data.dao.ArticuloDAO
import mx.com.lux.control.trabajos.data.dao.EmpleadoDAO
import mx.com.lux.control.trabajos.data.dao.ExamenDAO
import mx.com.lux.control.trabajos.data.dao.ReimpresionDAO
import mx.com.lux.control.trabajos.data.dao.envio.SucursalDAO
import mx.com.lux.control.trabajos.data.dao.trabajo.ClienteDAO
import mx.com.lux.control.trabajos.data.dao.trabajo.RecetaDAO
import org.apache.commons.lang.time.DateFormatUtils
import spock.lang.Specification
import mx.com.lux.control.trabajos.data.vo.*

class EnvioExternoBusinessTest extends Specification {

  def 'imprime ticket de envio'( ) {
    given:
    def sucursalDAO = Mock( SucursalDAO )
    def empleadoDAO = Mock( EmpleadoDAO )
    def clienteDAO = Mock( ClienteDAO )
    def examenDAO = Mock( ExamenDAO )
    def ticketService = Mock( TicketService )
    def business = new EnvioExternoBusinessImpl(
        sucursalDAO: sucursalDAO,
        empleadoDAO: empleadoDAO,
        clienteDAO: clienteDAO,
        examenDAO: examenDAO,
        ticketService: ticketService
    )
    def trabajo = new Jb(
        rx: '210914',
        externo: '10',
        idCliente: 99,
        caja: 'PRUEBA',
        cliente: 'FRANCISCO JAVIER VILLA VARGAS',
        material: 'BV3013, X3',
        saldo: new BigDecimal( 1784.00 ),
        obsExt: 'TEST'
    )
    def sucursalOrigen = new Sucursal(
        idSucursal: 1,
        nombre: 'LUX SUR'
    )
    def sucursalDestino = new Sucursal(
        idSucursal: 10,
        nombre: 'LUX SATELITE PB'
    )
    def usuario = new Empleado(
        idEmpleado: 9999,
        nombreEmpleado: 'JUAN',
        apMatEmpleado: 'PEREZ',
        apPatEmpleado: 'LOPEZ'
    )
    def cliente = new Cliente(
        idCliente: 99,
        telCasaCli: '5544332211',
        telTrabCli: '5544332211',
        extTrabCli: '1234',
        telAdiCli: '5544332211'
    )
    def examen = new Examen( idAtendio: 10 )
    def optometrista = new Empleado(
        idEmpleado: 10,
        nombreEmpleado: 'JOSE LUIS',
        apPatEmpleado: 'NAJERA',
        apMatEmpleado: 'VAZQUEZ'
    )
    sucursalDAO.findById( sucursalOrigen.idSucursal ) >> sucursalOrigen
    sucursalDAO.findById( sucursalDestino.idSucursal ) >> sucursalDestino
    empleadoDAO.obtenEmpleado( usuario.idEmpleado ) >> usuario
    clienteDAO.obtenCliente( _ ) >> cliente
    examenDAO.obtenExamenPorTrabajo( _ ) >> examen
    empleadoDAO.obtenEmpleado( optometrista.idEmpleado ) >> optometrista
    def elementos = [
        sucursal_origen: 'LUX SUR',
        id_sucursal_origen: 1,
        sucursal_destino: 'LUX SATELITE PB',
        id_sucursal_destino: 10,
        fecha: DateFormatUtils.format( new Date(), 'dd-MM-yyyy' ),
        envio: 'JUAN LOPEZ PEREZ',
        sobre: 'PRUEBA',
        cliente: 'FRANCISCO JAVIER VILLA VARGAS',
        telefono_casa: '5544332211',
        telefono_trabajo: '5544332211',
        telefono_trabajo_ext: '1234',
        telefono_adicional: '5544332211',
        factura: '210914',
        material: 'BV3013, X3',
        saldo: '\$1,784.00',
        optometrista: 'JOSE LUIS NAJERA VAZQUEZ',
        observaciones: 'TEST'
    ]

    when:
    business.imprimeTicketEnvio( trabajo, sucursalOrigen.idSucursal, usuario.idEmpleado )

    then:
    1 * ticketService.imprimeEnvioExterno( {
      assert it.sucursal_origen == elementos.sucursal_origen
      assert it.id_sucursal_origen == elementos.id_sucursal_origen
      assert it.sucursal_destino == elementos.sucursal_destino
      assert it.id_sucursal_destino == elementos.id_sucursal_destino
      assert it.fecha == elementos.fecha
      assert it.sobre == elementos.sobre
      assert it.cliente == elementos.cliente
      assert it.telefono_casa == elementos.telefono_casa
      assert it.telefono_trabajo == elementos.telefono_trabajo
      assert it.telefono_trabajo_ext == elementos.telefono_trabajo_ext
      assert it.telefono_adicional == elementos.telefono_adicional
      assert it.telefono_adicional_ext == elementos.telefono_adicional_ext
      assert it.factura == elementos.factura
      assert it.material == elementos.material
      assert it.saldo == elementos.saldo
      assert it.optometrista == elementos.optometrista
      assert it.observaciones == elementos.observaciones
      it
    } )
  }

  def 'imprime ticket de receta de envio'( ) {
    given:
    def sucursalDAO = Mock( SucursalDAO )
    def clienteDAO = Mock( ClienteDAO )
    def articuloDAO = Mock( ArticuloDAO )
    def examenDAO = Mock( ExamenDAO )
    def empleadoDAO = Mock( EmpleadoDAO )
    def recetaDAO = Mock( RecetaDAO )
    def reimpresionDAO = Mock( ReimpresionDAO )
    def ticketService = Mock( TicketService )
    def business = new EnvioExternoBusinessImpl(
        sucursalDAO: sucursalDAO,
        clienteDAO: clienteDAO,
        articuloDAO: articuloDAO,
        examenDAO: examenDAO,
        empleadoDAO: empleadoDAO,
        recetaDAO: recetaDAO,
        reimpresionDAO: reimpresionDAO,
        ticketService: ticketService
    )
    def trabajo = new Jb(
        rx: '123456',
        externo: '5',
        idCliente: 10,
        material: 'BV3013, X3',
        obsExt: 'TEST'
    )
    def notaVenta = new NotaVenta(
        factura: '035182',
        fechaHoraFactura: Date.parse( 'dd-MM-yyyy', '01-06-2007' ),
        fechaPrometida: Date.parse( 'dd-MM-yyyy', '01-06-2007' ),
        ventaNeta: 29.99,
        sumaPagos: 19.99,
        udf2: 'Aire',
        udf3: 'AT2101',
        observacionesNv: 'TEST'
    )
    def sucursalOrigen = new Sucursal(
        idSucursal: 51,
        nombre: 'SEARS INSURGENTES'
    )
    def sucursalDestino = new Sucursal(
        idSucursal: 5,
        nombre: 'LUX COYOACAN'
    )
    def usuario = new Empleado(
        idEmpleado: 9999,
        nombreEmpleado: 'JUAN',
        apPatEmpleado: 'PEREZ',
        apMatEmpleado: 'LOPEZ'
    )
    def cliente = new Cliente(
        idCliente: 10,
        nombreCli: 'FRANCISCO JAVIER',
        apellidoPatCli: 'VILLA',
        apellidoMatCli: 'VARGAS',
        titulo: 'SR.',
        telCasaCli: '5544332211',
        telTrabCli: '5544332211',
        extTrabCli: '1234',
        telAdiCli: '5544332211'
    )
    def articuloB1 = new Articulos(
        idGenerico: 'B',
        articulo: 'X1X',
        descArticulo: 'MONOFOCAL 1'
    )
    def articuloB2 = new Articulos(
        idGenerico: 'B',
        articulo: 'X2X',
        descArticulo: 'MONOFOCAL 2'
    )
    def examen = new Examen(
        idAtendio: 10
    )
    def receta = new Receta(
        sUsoAnteojos: 'l' as char,
        odEsfR: '-99.99',
        odCilR: '-99.99',
        odEjeR: '-99.99',
        odPrismaH: '4.00',
        odPrismaV: 'AFUERA',
        oiPrismaH: '4.00',
        oiPrismaV: 'AFUERA',
        oiEsfR: '-99.99',
        oiCilR: '-99.99',
        oiEjeR: '-99.99',
        diLejosR: '-99.99',
        diCercaR: '-99.99',
        diOd: '-99.99',
        diOi: '-99.99',
        observacionesR: 'NINGUNA',
        altOblR: 'A1'
    )
    def optometrista = new Empleado(
        idEmpleado: 10,
        nombreEmpleado: 'JOSE LUIS',
        apPatEmpleado: 'NAJERA',
        apMatEmpleado: 'VAZQUEZ'
    )
    sucursalDAO.findById( sucursalOrigen.idSucursal ) >> sucursalOrigen
    sucursalDAO.findById( sucursalDestino.idSucursal ) >> sucursalDestino
    empleadoDAO.obtenEmpleado( usuario.idEmpleado ) >> usuario
    clienteDAO.obtenCliente( _ ) >> cliente
    examenDAO.obtenExamenPorTrabajo( _ ) >> examen
    recetaDAO.obtenerRecetaPorTrabajo( _ ) >> receta
    empleadoDAO.obtenEmpleado( optometrista.idEmpleado ) >> optometrista
    reimpresionDAO.obtenConteoReimpresiones( _ ) >> 0
    articuloDAO.obtenListaArticulosPorTrabajo( _ ) >> [ articuloB1, articuloB2 ]
    def elementos = [
        nombre_archivo: '0005035182RX',
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
        saldo: '\$10.00',
        articulos: 'X1X-MONOFOCAL 1, X2X-MONOFOCAL 2',
        esf_d: '-99.99',
        cil_d: '-99.99',
        eje_d: '-99.99',
        pri_d: '-4.00',
        esf_i: '-99.99',
        cil_i: '-99.99',
        eje_i: '-99.99',
        pri_i: '-4.00',
        dil: '-99.99',
        dic: '-99.99',
        dmd: '-99.99',
        dmi: '-99.99',
        altura_oblea: 'A1',
        armazon: null,
        uso: 'LEJOS',
        trat: null,
        material: 'Aire',
        forma_lente: 'AT2101',
        obs_receta: 'NINGUNA',
        obs_factura: 'TEST',
        con_saldo: 'CON SALDO',
        regreso_clases: null,
        convenio_nomina: null
    ]

    when:
    business.imprimeTicketRecetaEnvio( trabajo, notaVenta, sucursalOrigen.idSucursal, usuario.idEmpleado )

    then:
    1 * ticketService.imprimeRecetaEnvioExterno( {
      assert it.nombre_archivo == elementos.nombre_archivo
      assert it.sucursal_origen == elementos.sucursal_origen
      assert it.id_sucursal_origen == elementos.id_sucursal_origen
      assert it.sucursal_destino == elementos.sucursal_destino
      assert it.id_sucursal_destino == elementos.id_sucursal_destino
      assert it.factura == elementos.factura
      assert it.fecha_hora_factura == elementos.fecha_hora_factura
      assert it.fecha_prometida == elementos.fecha_prometida
      assert it.optometrista == elementos.optometrista
      assert it.usuario == elementos.usuario
      assert it.cliente == elementos.cliente
      assert it.telefono_casa == elementos.telefono_casa
      assert it.telefono_trabajo == elementos.telefono_trabajo
      assert it.telefono_trabajo_ext == elementos.telefono_trabajo_ext
      assert it.telefono_adicional == elementos.telefono_adicional
      assert it.telefono_adicional_ext == elementos.telefono_adicional_ext
      assert it.saldo == elementos.saldo
      assert it.articulos == elementos.articulos
      assert it.esf_d == elementos.esf_d
      assert it.cil_d == elementos.cil_d
      assert it.eje_d == elementos.eje_d
      assert it.adc_d == elementos.adc_d
      assert it.adi_d == elementos.adi_d
      assert it.pri_d == elementos.pri_d
      assert it.esf_i == elementos.esf_i
      assert it.cil_i == elementos.cil_i
      assert it.eje_i == elementos.eje_i
      assert it.adc_i == elementos.adc_i
      assert it.adi_i == elementos.adi_i
      assert it.pri_i == elementos.pri_i
      assert it.dil == elementos.dil
      assert it.dic == elementos.dic
      assert it.dmd == elementos.dmd
      assert it.dmi == elementos.dmi
      assert it.altura_oblea == elementos.altura_oblea
      assert it.armazon == elementos.armazon
      assert it.uso == elementos.uso
      assert it.trat == elementos.trat
      assert it.material == elementos.material
      assert it.forma_lente == elementos.forma_lente
      assert it.obs_receta == elementos.obs_receta
      assert it.obs_factura == elementos.obs_factura
      assert it.con_saldo == elementos.con_saldo
      assert it.regreso_clases == elementos.regreso_clases
      assert it.convenio_nomina == elementos.convenio_nomina
      it
    } )
  }

  def 'imprime ticket de receta de envio sin optometrista'( ) {
    given:
    def sucursalDAO = Mock( SucursalDAO )
    def clienteDAO = Mock( ClienteDAO )
    def articuloDAO = Mock( ArticuloDAO )
    def examenDAO = Mock( ExamenDAO )
    def empleadoDAO = Mock( EmpleadoDAO )
    def recetaDAO = Mock( RecetaDAO )
    def reimpresionDAO = Mock( ReimpresionDAO )
    def ticketService = Mock( TicketService )
    def business = new EnvioExternoBusinessImpl(
        sucursalDAO: sucursalDAO,
        clienteDAO: clienteDAO,
        articuloDAO: articuloDAO,
        examenDAO: examenDAO,
        empleadoDAO: empleadoDAO,
        recetaDAO: recetaDAO,
        reimpresionDAO: reimpresionDAO,
        ticketService: ticketService
    )
    def trabajo = new Jb(
        rx: '123456',
        externo: '5',
        idCliente: 10,
        material: 'BV3013, X3',
        obsExt: 'TEST'
    )
    def notaVenta = new NotaVenta(
        factura: '035182',
        fechaHoraFactura: Date.parse( 'dd-MM-yyyy', '01-06-2007' ),
        fechaPrometida: Date.parse( 'dd-MM-yyyy', '01-06-2007' ),
        ventaNeta: 29.99,
        sumaPagos: 19.99,
        udf2: 'Aire',
        udf3: 'AT2101',
        observacionesNv: 'TEST'
    )
    def sucursalOrigen = new Sucursal(
        idSucursal: 51,
        nombre: 'SEARS INSURGENTES'
    )
    def sucursalDestino = new Sucursal(
        idSucursal: 5,
        nombre: 'LUX COYOACAN'
    )
    def usuario = new Empleado(
        idEmpleado: 9999,
        nombreEmpleado: 'JUAN',
        apPatEmpleado: 'PEREZ',
        apMatEmpleado: 'LOPEZ'
    )
    def cliente = new Cliente(
        idCliente: 10,
        nombreCli: 'FRANCISCO JAVIER',
        apellidoPatCli: 'VILLA',
        apellidoMatCli: 'VARGAS',
        titulo: 'SR.',
        telCasaCli: '5544332211',
        telTrabCli: '5544332211',
        extTrabCli: '1234',
        telAdiCli: '5544332211'
    )
    def articuloB1 = new Articulos(
        idGenerico: 'B',
        articulo: 'X1X',
        descArticulo: 'MONOFOCAL 1'
    )
    def articuloB2 = new Articulos(
        idGenerico: 'B',
        articulo: 'X2X',
        descArticulo: 'MONOFOCAL 2'
    )
    def examen = new Examen(
        idAtendio: 10
    )
    def receta = new Receta(
        sUsoAnteojos: 'l' as char,
        odEsfR: '-99.99',
        odCilR: '-99.99',
        odEjeR: '-99.99',
        odPrismaH: '4.00',
        odPrismaV: 'AFUERA',
        oiPrismaH: '4.00',
        oiPrismaV: 'AFUERA',
        oiEsfR: '-99.99',
        oiCilR: '-99.99',
        oiEjeR: '-99.99',
        diLejosR: '-99.99',
        diCercaR: '-99.99',
        diOd: '-99.99',
        diOi: '-99.99',
        observacionesR: 'NINGUNA',
        altOblR: 'A1'
    )
    sucursalDAO.findById( sucursalOrigen.idSucursal ) >> sucursalOrigen
    sucursalDAO.findById( sucursalDestino.idSucursal ) >> sucursalDestino
    empleadoDAO.obtenEmpleado( usuario.idEmpleado ) >> usuario
    clienteDAO.obtenCliente( _ ) >> cliente
    examenDAO.obtenExamenPorTrabajo( _ ) >> examen
    recetaDAO.obtenerRecetaPorTrabajo( _ ) >> receta
    reimpresionDAO.obtenConteoReimpresiones( _ ) >> 0
    articuloDAO.obtenListaArticulosPorTrabajo( _ ) >> [ articuloB1, articuloB2 ]
    def elementos = [
        nombre_archivo: '0005035182RX',
        sucursal_origen: 'SEARS INSURGENTES',
        id_sucursal_origen: 51,
        sucursal_destino: 'LUX COYOACAN',
        id_sucursal_destino: 5,
        factura: '035182',
        fecha_hora_factura: '01-06-2007',
        fecha_prometida: '01-06-2007',
        usuario: 'JUAN PEREZ LOPEZ',
        copia: '1',
        cliente: 'SR. FRANCISCO JAVIER VILLA VARGAS',
        telefono_casa: '5544332211',
        telefono_trabajo: '5544332211',
        telefono_trabajo_ext: '1234',
        telefono_adicional: '5544332211',
        saldo: '\$10.00',
        articulos: 'X1X-MONOFOCAL 1, X2X-MONOFOCAL 2',
        esf_d: '-99.99',
        cil_d: '-99.99',
        eje_d: '-99.99',
        pri_d: '-4.00',
        esf_i: '-99.99',
        cil_i: '-99.99',
        eje_i: '-99.99',
        pri_i: '-4.00',
        dil: '-99.99',
        dic: '-99.99',
        dmd: '-99.99',
        dmi: '-99.99',
        altura_oblea: 'A1',
        armazon: null,
        uso: 'LEJOS',
        trat: null,
        material: 'Aire',
        forma_lente: 'AT2101',
        obs_receta: 'NINGUNA',
        obs_factura: 'TEST',
        con_saldo: 'CON SALDO',
        regreso_clases: null,
        convenio_nomina: null
    ]

    when:
    business.imprimeTicketRecetaEnvio( trabajo, notaVenta, sucursalOrigen.idSucursal, usuario.idEmpleado )

    then:
    1 * ticketService.imprimeRecetaEnvioExterno( {
      assert it.nombre_archivo == elementos.nombre_archivo
      assert it.sucursal_origen == elementos.sucursal_origen
      assert it.id_sucursal_origen == elementos.id_sucursal_origen
      assert it.sucursal_destino == elementos.sucursal_destino
      assert it.id_sucursal_destino == elementos.id_sucursal_destino
      assert it.factura == elementos.factura
      assert it.fecha_hora_factura == elementos.fecha_hora_factura
      assert it.fecha_prometida == elementos.fecha_prometida
      assert it.optometrista == elementos.optometrista
      assert it.usuario == elementos.usuario
      assert it.cliente == elementos.cliente
      assert it.telefono_casa == elementos.telefono_casa
      assert it.telefono_trabajo == elementos.telefono_trabajo
      assert it.telefono_trabajo_ext == elementos.telefono_trabajo_ext
      assert it.telefono_adicional == elementos.telefono_adicional
      assert it.telefono_adicional_ext == elementos.telefono_adicional_ext
      assert it.saldo == elementos.saldo
      assert it.articulos == elementos.articulos
      assert it.esf_d == elementos.esf_d
      assert it.cil_d == elementos.cil_d
      assert it.eje_d == elementos.eje_d
      assert it.adc_d == elementos.adc_d
      assert it.adi_d == elementos.adi_d
      assert it.pri_d == elementos.pri_d
      assert it.esf_i == elementos.esf_i
      assert it.cil_i == elementos.cil_i
      assert it.eje_i == elementos.eje_i
      assert it.adc_i == elementos.adc_i
      assert it.adi_i == elementos.adi_i
      assert it.pri_i == elementos.pri_i
      assert it.dil == elementos.dil
      assert it.dic == elementos.dic
      assert it.dmd == elementos.dmd
      assert it.dmi == elementos.dmi
      assert it.altura_oblea == elementos.altura_oblea
      assert it.armazon == elementos.armazon
      assert it.uso == elementos.uso
      assert it.trat == elementos.trat
      assert it.material == elementos.material
      assert it.forma_lente == elementos.forma_lente
      assert it.obs_receta == elementos.obs_receta
      assert it.obs_factura == elementos.obs_factura
      assert it.con_saldo == elementos.con_saldo
      assert it.regreso_clases == elementos.regreso_clases
      assert it.convenio_nomina == elementos.convenio_nomina
      it
    } )
  }
}

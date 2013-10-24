package mx.com.lux.control.trabajos.bussiness

import mx.com.lux.control.trabajos.bussiness.impl.EnvioExternoBusinessImpl
import mx.com.lux.control.trabajos.data.dao.trabajo.ClienteDAO
import mx.com.lux.control.trabajos.data.dao.trabajo.RecetaDAO
import org.apache.velocity.app.VelocityEngine
import spock.lang.Specification
import mx.com.lux.control.trabajos.data.dao.*
import mx.com.lux.control.trabajos.data.vo.*

class ArchivoEnvioExternoTest extends Specification {

  def engine = new VelocityEngine()

  def setup( ) {
    engine.addProperty( 'resource.loader', 'class' )
    engine.addProperty( 'class.resource.loader.class', 'org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader' )
  }

  def 'genera contenido archivo por envio de externo'( ) {
    given:
    def expected = '''1|210459|
2|0016|
3|10|
4|MANUEL|TRON|PEREZ|SR.|0||011|09|N|1|PRADO SUR N 555|LOMAS DE CHAPULTEPEC|11000|5552824162|||||no tiene|TOPM600927KQ7|0|S|||27-09-1960|19605|||||
5|0549|70|70|-0.75|-0.50|41|-1.00|-0.50|139|69|-1.00|-0.50|55|+1.50||20|-1.25|-0.50|135|+1.50||||20||11-12-2011|N|||
6|-1.00|-0.50|55|+1.50||||20||-1.25|-0.50|135|+1.50||||20||69|||l|LEJOS|28-02-2012|XZ13 EN SYO OKMOD CROSSHAIR|0549||
7|XZ13|$2,380.00|XZ13||Metal|||09-03-2012||13-03-2012|28-02-2012|'''
    def clienteDAO = Mock( ClienteDAO )
    def examenDAO = Mock( ExamenDAO )
    def recetaDAO = Mock( RecetaDAO )
    def notaVentaDAO = Mock( NotaVentaDAO )
    def articuloDAO = Mock( ArticuloDAO )
    def polizaDAO = Mock( PolizaDAO )
    def formaContactoDAO = Mock( FormaContactoDAO )
    def business = new EnvioExternoBusinessImpl(
        clienteDAO: clienteDAO,
        examenDAO: examenDAO,
        recetaDAO: recetaDAO,
        notaVentaDAO: notaVentaDAO,
        articuloDAO: articuloDAO,
        polizaDAO: polizaDAO,
        formaContactoDAO: formaContactoDAO,
        velocityEngine: engine
    )
    def idSucursalOrigen = 16
    def idAtendio = '0549'
    def cliente = new Cliente(
        idCliente: 19605,
        nombreCli: 'MANUEL',
        apellidoPatCli: 'TRON',
        apellidoMatCli: 'PEREZ',
        titulo: 'SR.',
        rfcCli: 'TOPM600927KQ7',
        idOftalmologo: 0,
        idLocalidad: '011',
        idEstado: '09',
        tipoCli: 'N',
        sexoCli: true,
        direccionCli: 'PRADO SUR N 555',
        coloniaCli: 'LOMAS DE CHAPULTEPEC',
        codigo: '11000',
        telCasaCli: '5552824162',
        emailCli: 'no tiene',
        FCasadaCli: false,
        SUsaAnteojos: 'S' as char,
        fechaNac: Date.parse( 'dd-MM-yyyy', '27-09-1960' )
    )
    def trabajo = new Jb(
        rx: 210459,
        idCliente: cliente.idCliente,
        externo: 10,
        material: 'XZ13',
        saldo: new BigDecimal( 2380.00 ),
        volverLlamar: Date.parse( 'dd-MM-yyyy', '13-03-2012' )
    )
    def examen = new Examen(
        idAtendio: idAtendio,
        avSaOdLejosEx: 70,
        avSaOiLejosEx: 70,
        objOdEsfEx: '-0.75',
        objOdCilEx: '-0.50',
        objOdEjeEx: 41,
        objOiEsfEx: '-1.00',
        objOiCilEx: '-0.50',
        objOiEjeEx: 139,
        objDiEx: 69,
        subOdEsfEx: '-1.00',
        subOdCilEx: '-0.50',
        subOdEjeEx: 55,
        subOdAdcEx: '+1.50',
        subOdAvEx: 20,
        subOiEsfEx: '-1.25',
        subOiCilEx: '-0.50',
        subOiEjeEx: 135,
        subOiAdcEx: '+1.50',
        subOiAvEx: 20,
        fechaAlta: Date.parse( 'dd-MM-yyyy', '11-12-2011' ),
        tipoCli: 'N'
    )
    def receta = new Receta(
        odEsfR: '-1.00',
        odCilR: '-0.50',
        odEjeR: 55,
        odAdcR: '+1.50',
        odAvR: 20,
        oiEsfR: '-1.25',
        oiCilR: '-0.50',
        oiEjeR: 135,
        oiAdcR: '+1.50',
        oiAvR: 20,
        diLejosR: 69,
        sUsoAnteojos: 'l' as char,
        fechaReceta: Date.parse( 'dd-MM-yyyy', '28-02-2012' ),
        observacionesR: 'XZ13 EN SYO OKMOD CROSSHAIR',
        idOptometrista: idAtendio
    )
    def notaVenta = new NotaVenta(
        udf2: 'Metal',
        fechaPrometida: Date.parse( 'dd-MM-yyyy', '09-03-2012' ),
        fechaHoraFactura: Date.parse( 'dd-MM-yyyy', '28-02-2012' )
    )
    def articuloB = new Articulos( articulo: 'XZ13' )
    clienteDAO.obtenCliente( _ ) >> cliente
    examenDAO.obtenExamenPorTrabajo( _ ) >> examen
    recetaDAO.obtenerRecetaPorTrabajo( _ ) >> receta
    notaVentaDAO.obtenNotaVentaPorTrabajo( _ ) >> notaVenta
    articuloDAO.obtenArticuloGenericoBPorNotaVenta( _ ) >> articuloB
    def file = new File( '/tmp/0010-210459-X2.EXT' )

    when:
    business.generaArchivoEnvio( trabajo, idSucursalOrigen )

    then:
    file?.exists()
    file?.canRead()
    expected == file.text?.trim()

    cleanup:
    file.delete()
  }

  def 'genera contenido archivo por receta de envio externo'( ) {
    given:
    def expected = '0001|215636|X1|-3.00|-1.00|80||-4.00||-2.50|-1.75|93||-4.00||60|58||||Pasta|' +
        'DR LEOPOLDO DE VELASCO RX SIN GARANTIA|P||AT2101|AFUERA|AFUERA|'
    def articuloDAO = Mock( ArticuloDAO )
    def recetaDAO = Mock( RecetaDAO )
    def reimpresionDAO = Mock( ReimpresionDAO )
    def business = new EnvioExternoBusinessImpl(
        articuloDAO: articuloDAO,
        recetaDAO: recetaDAO,
        reimpresionDAO: reimpresionDAO,
        velocityEngine: engine
    )
    def trabajo = new Jb(
        externo: '1'
    )
    def notaVenta = new NotaVenta(
        factura: '215636',
        cantLente: 'par',
        udf2: 'Pasta'
    )
    def articuloA = new Articulos( articulo: 'AT2101' )
    def articuloB = new Articulos( articulo: 'X1' )
    def receta = new Receta(
        odEsfR: '-3.00',
        odCilR: '-1.00',
        odEjeR: '80',
        odPrismaH: '4.00',
        oiEsfR: '-2.50',
        oiCilR: '-1.75',
        oiEjeR: '93',
        oiPrismaH: '4.00',
        diLejosR: '60',
        diCercaR: '58',
        observacionesR: 'DR LEOPOLDO DE VELASCO RX SIN GARANTIA',
        odPrismaV: 'AFUERA',
        oiPrismaV: 'AFUERA',
        sUsoAnteojos: 'l' as char
    )
    articuloDAO.obtenArticuloGenericoAPorNotaVenta( _ ) >> articuloA
    articuloDAO.obtenArticuloGenericoBPorNotaVenta( _ ) >> articuloB
    recetaDAO.obtenerRecetaPorTrabajo( _ ) >> receta
    reimpresionDAO.obtenConteoReimpresiones( _ ) >> 0
    def file = new File( '/tmp/0001215636RX' )

    when:
    business.generaArchivoRecetaEnvio( trabajo, notaVenta )

    then:
    file?.exists()
    file?.canRead()
    expected == file.text?.trim()

    cleanup:
    file.delete()
  }

  def 'genera contenido archivo por envio de externo con forma de contacto'( ) {
    given:
    def expected = '''1|210459|
2|0016|
3|10|
4|MANUEL|TRON|PEREZ|SR.|0||011|09|N|1|PRADO SUR N 555|LOMAS DE CHAPULTEPEC|11000|5552824162|||||no tiene|TOPM600927KQ7|0|S|||27-09-1960|19605|||||
5|0549|70|70|-0.75|-0.50|41|-1.00|-0.50|139|69|-1.00|-0.50|55|+1.50||20|-1.25|-0.50|135|+1.50||||20||11-12-2011|N|||
6|-1.00|-0.50|55|+1.50||||20||-1.25|-0.50|135|+1.50||||20||69|||l|LEJOS|28-02-2012|XZ13 EN SYO OKMOD CROSSHAIR|0549||
7|XZ13|$2,380.00|XZ13||Metal|||09-03-2012||13-03-2012|28-02-2012|
11|210459|19605|4|5557961490|~|12-03-2012|16|'''
    def clienteDAO = Mock( ClienteDAO )
    def examenDAO = Mock( ExamenDAO )
    def recetaDAO = Mock( RecetaDAO )
    def notaVentaDAO = Mock( NotaVentaDAO )
    def articuloDAO = Mock( ArticuloDAO )
    def polizaDAO = Mock( PolizaDAO )
    def formaContactoDAO = Mock( FormaContactoDAO )
    def business = new EnvioExternoBusinessImpl(
        clienteDAO: clienteDAO,
        examenDAO: examenDAO,
        recetaDAO: recetaDAO,
        notaVentaDAO: notaVentaDAO,
        articuloDAO: articuloDAO,
        polizaDAO: polizaDAO,
        formaContactoDAO: formaContactoDAO,
        velocityEngine: engine
    )
    def idSucursalOrigen = 16
    def idAtendio = '0549'
    def cliente = new Cliente(
        idCliente: 19605,
        nombreCli: 'MANUEL',
        apellidoPatCli: 'TRON',
        apellidoMatCli: 'PEREZ',
        titulo: 'SR.',
        rfcCli: 'TOPM600927KQ7',
        idOftalmologo: 0,
        idLocalidad: '011',
        idEstado: '09',
        tipoCli: 'N',
        sexoCli: true,
        direccionCli: 'PRADO SUR N 555',
        coloniaCli: 'LOMAS DE CHAPULTEPEC',
        codigo: '11000',
        telCasaCli: '5552824162',
        emailCli: 'no tiene',
        FCasadaCli: false,
        SUsaAnteojos: 'S' as char,
        fechaNac: Date.parse( 'dd-MM-yyyy', '27-09-1960' )
    )
    def trabajo = new Jb(
        rx: 210459,
        idCliente: cliente.idCliente,
        externo: 10,
        material: 'XZ13',
        saldo: new BigDecimal( 2380.00 ),
        volverLlamar: Date.parse( 'dd-MM-yyyy', '13-03-2012' )
    )
    def examen = new Examen(
        idAtendio: idAtendio,
        avSaOdLejosEx: 70,
        avSaOiLejosEx: 70,
        objOdEsfEx: '-0.75',
        objOdCilEx: '-0.50',
        objOdEjeEx: 41,
        objOiEsfEx: '-1.00',
        objOiCilEx: '-0.50',
        objOiEjeEx: 139,
        objDiEx: 69,
        subOdEsfEx: '-1.00',
        subOdCilEx: '-0.50',
        subOdEjeEx: 55,
        subOdAdcEx: '+1.50',
        subOdAvEx: 20,
        subOiEsfEx: '-1.25',
        subOiCilEx: '-0.50',
        subOiEjeEx: 135,
        subOiAdcEx: '+1.50',
        subOiAvEx: 20,
        fechaAlta: Date.parse( 'dd-MM-yyyy', '11-12-2011' ),
        tipoCli: 'N'
    )
    def receta = new Receta(
        odEsfR: '-1.00',
        odCilR: '-0.50',
        odEjeR: 55,
        odAdcR: '+1.50',
        odAvR: 20,
        oiEsfR: '-1.25',
        oiCilR: '-0.50',
        oiEjeR: 135,
        oiAdcR: '+1.50',
        oiAvR: 20,
        diLejosR: 69,
        sUsoAnteojos: 'l' as char,
        fechaReceta: Date.parse( 'dd-MM-yyyy', '28-02-2012' ),
        observacionesR: 'XZ13 EN SYO OKMOD CROSSHAIR',
        idOptometrista: idAtendio
    )
    def notaVenta = new NotaVenta(
        udf2: 'Metal',
        fechaPrometida: Date.parse( 'dd-MM-yyyy', '09-03-2012' ),
        fechaHoraFactura: Date.parse( 'dd-MM-yyyy', '28-02-2012' )
    )
    def articuloB = new Articulos( articulo: 'XZ13' )
    def formaContacto = new FormaContacto(
        rx: trabajo.rx,
        idCliente: cliente.idCliente,
        contacto: '5557961490',
        observaciones: '\n',
        tipoContacto: new TipoContacto( idTipoContacto: 4 ),
        fechaMod: Date.parse( 'dd-MM-yyyy', '12-03-2012' ),
        idSucursal: idSucursalOrigen
    )
    clienteDAO.obtenCliente( _ ) >> cliente
    examenDAO.obtenExamenPorTrabajo( _ ) >> examen
    recetaDAO.obtenerRecetaPorTrabajo( _ ) >> receta
    notaVentaDAO.obtenNotaVentaPorTrabajo( _ ) >> notaVenta
    articuloDAO.obtenArticuloGenericoBPorNotaVenta( _ ) >> articuloB
    formaContactoDAO.obtenPorRx( _ ) >> formaContacto
    def file = new File( '/tmp/0010-210459-X2.EXT' )

    when:
    business.generaArchivoEnvio( trabajo, idSucursalOrigen )

    then:
    file?.exists()
    file?.canRead()
    expected == file.text?.trim()

    cleanup:
    file.delete()
  }

  def 'genera contenido archivo por envio de externo con poliza y forma de contacto'( ) {
    given:
    def expected = '''1|210459|
2|0016|
3|10|
4|MANUEL|TRON|PEREZ|SR.|0||011|09|N|1|PRADO SUR N 555|LOMAS DE CHAPULTEPEC|11000|5552824162|||||no tiene|TOPM600927KQ7|0|S|||27-09-1960|19605|||||
5|0549|70|70|-0.75|-0.50|41|-1.00|-0.50|139|69|-1.00|-0.50|55|+1.50||20|-1.25|-0.50|135|+1.50||||20||11-12-2011|N|||
6|-1.00|-0.50|55|+1.50||||20||-1.25|-0.50|135|+1.50||||20||69|||l|LEJOS|28-02-2012|XZ13 EN SYO OKMOD CROSSHAIR|0549||
7|XZ13|$2,380.00|XZ13||Metal|||09-03-2012||13-03-2012|28-02-2012|
8|A44259|22591|104880|211271|07-03-2012|211267|07-03-2012|||A||16|
9|2420|3455|PM1W||B|1|$3,350.00|$3,015.00|10-03-2012|
10|2420|167708|GA0892|YVE|A|1|$3,990.00|$3,591.00|10-03-2012|
11|210459|19605|4|5557961490|~|12-03-2012|16|'''
    def clienteDAO = Mock( ClienteDAO )
    def examenDAO = Mock( ExamenDAO )
    def recetaDAO = Mock( RecetaDAO )
    def notaVentaDAO = Mock( NotaVentaDAO )
    def articuloDAO = Mock( ArticuloDAO )
    def polizaDAO = Mock( PolizaDAO )
    def formaContactoDAO = Mock( FormaContactoDAO )
    def business = new EnvioExternoBusinessImpl(
        clienteDAO: clienteDAO,
        examenDAO: examenDAO,
        recetaDAO: recetaDAO,
        notaVentaDAO: notaVentaDAO,
        articuloDAO: articuloDAO,
        polizaDAO: polizaDAO,
        formaContactoDAO: formaContactoDAO,
        velocityEngine: engine
    )
    def idSucursalOrigen = 16
    def idAtendio = '0549'
    def cliente = new Cliente(
        idCliente: 19605,
        nombreCli: 'MANUEL',
        apellidoPatCli: 'TRON',
        apellidoMatCli: 'PEREZ',
        titulo: 'SR.',
        rfcCli: 'TOPM600927KQ7',
        idOftalmologo: 0,
        idLocalidad: '011',
        idEstado: '09',
        tipoCli: 'N',
        sexoCli: true,
        direccionCli: 'PRADO SUR N 555',
        coloniaCli: 'LOMAS DE CHAPULTEPEC',
        codigo: '11000',
        telCasaCli: '5552824162',
        emailCli: 'no tiene',
        FCasadaCli: false,
        SUsaAnteojos: 'S' as char,
        fechaNac: Date.parse( 'dd-MM-yyyy', '27-09-1960' )
    )
    def trabajo = new Jb(
        rx: 210459,
        idCliente: cliente.idCliente,
        externo: 10,
        material: 'XZ13',
        saldo: new BigDecimal( 2380.00 ),
        volverLlamar: Date.parse( 'dd-MM-yyyy', '13-03-2012' )
    )
    def examen = new Examen(
        idAtendio: idAtendio,
        avSaOdLejosEx: 70,
        avSaOiLejosEx: 70,
        objOdEsfEx: '-0.75',
        objOdCilEx: '-0.50',
        objOdEjeEx: 41,
        objOiEsfEx: '-1.00',
        objOiCilEx: '-0.50',
        objOiEjeEx: 139,
        objDiEx: 69,
        subOdEsfEx: '-1.00',
        subOdCilEx: '-0.50',
        subOdEjeEx: 55,
        subOdAdcEx: '+1.50',
        subOdAvEx: 20,
        subOiEsfEx: '-1.25',
        subOiCilEx: '-0.50',
        subOiEjeEx: 135,
        subOiAdcEx: '+1.50',
        subOiAvEx: 20,
        fechaAlta: Date.parse( 'dd-MM-yyyy', '11-12-2011' ),
        tipoCli: 'N'
    )
    def receta = new Receta(
        odEsfR: '-1.00',
        odCilR: '-0.50',
        odEjeR: 55,
        odAdcR: '+1.50',
        odAvR: 20,
        oiEsfR: '-1.25',
        oiCilR: '-0.50',
        oiEjeR: 135,
        oiAdcR: '+1.50',
        oiAvR: 20,
        diLejosR: 69,
        sUsoAnteojos: 'l' as char,
        fechaReceta: Date.parse( 'dd-MM-yyyy', '28-02-2012' ),
        observacionesR: 'XZ13 EN SYO OKMOD CROSSHAIR',
        idOptometrista: idAtendio
    )
    def notaVenta = new NotaVenta(
        udf2: 'Metal',
        fechaPrometida: Date.parse( 'dd-MM-yyyy', '09-03-2012' ),
        fechaHoraFactura: Date.parse( 'dd-MM-yyyy', '28-02-2012' )
    )
    def articuloB = new Articulos( articulo: 'XZ13' )
    def poliza = new Polizas(
        idFactura: 'A44259',
        idCliente: 22591,
        noPoliza: 104880,
        factura: 211271,
        fechaMod: Date.parse( 'dd-MM-yyyy', '07-03-2012' ),
        facturaVenta: 211267,
        fechaVenta: Date.parse( 'dd-MM-yyyy', '07-03-2012' ),
        status: 'A',
        idSucursal: idSucursalOrigen,
        detalles: [
            new PolizasDetalle(
                idPoliza: 2420,
                idArticulo: '3455',
                articulo: 'PM1W',
                idGenerico: 'B',
                cantidad: 1,
                precioLista: 3350.00,
                precioVenta: 3015.00,
                fechaMod: Date.parse( 'dd-MM-yyyy', '10-03-2012' )
            ),
            new PolizasDetalle(
                idPoliza: 2420,
                idArticulo: '167708',
                articulo: 'GA0892',
                idColor: 'YVE',
                idGenerico: 'A',
                cantidad: 1,
                precioLista: 3990.00,
                precioVenta: 3591.00,
                fechaMod: Date.parse( 'dd-MM-yyyy', '10-03-2012' )
            )
        ]
    )
    def formaContacto = new FormaContacto(
        rx: trabajo.rx,
        idCliente: cliente.idCliente,
        contacto: '5557961490',
        observaciones: '\n',
        tipoContacto: new TipoContacto( idTipoContacto: 4 ),
        fechaMod: Date.parse( 'dd-MM-yyyy', '12-03-2012' ),
        idSucursal: idSucursalOrigen
    )
    clienteDAO.obtenCliente( _ ) >> cliente
    examenDAO.obtenExamenPorTrabajo( _ ) >> examen
    recetaDAO.obtenerRecetaPorTrabajo( _ ) >> receta
    notaVentaDAO.obtenNotaVentaPorTrabajo( _ ) >> notaVenta
    articuloDAO.obtenArticuloGenericoBPorNotaVenta( _ ) >> articuloB
    polizaDAO.obtenPolizaPorFactura( _ ) >> poliza
    formaContactoDAO.obtenPorRx( _ ) >> formaContacto
    def file = new File( '/tmp/0010-210459-X2.EXT' )

    when:
    business.generaArchivoEnvio( trabajo, idSucursalOrigen )

    then:
    file?.exists()
    file?.canRead()
    expected == file.text?.trim()

    cleanup:
    file.delete()
  }
}

package mx.com.lux.control.trabajos.bussiness

import mx.com.lux.control.trabajos.bussiness.impl.EnvioExternoBusinessImpl
import mx.com.lux.control.trabajos.data.dao.trabajo.ClienteDAO
import mx.com.lux.control.trabajos.data.dao.trabajo.RecetaDAO
import org.apache.velocity.app.VelocityEngine
import spock.lang.Specification
import mx.com.lux.control.trabajos.data.dao.*
import mx.com.lux.control.trabajos.data.vo.*

class AcuseEnvioExternoTest extends Specification {

  def engine = new VelocityEngine()

  def setup( ) {
    engine.addProperty( 'resource.loader', 'class' )
    engine.addProperty( 'class.resource.loader.class', 'org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader' )
  }

  def 'genera contenido acuse por envio de externo'( ) {
    given:
    def expected = 'C1Val=1%207864%|C2Val=2%0131%|C3Val=3%10%|C4Val=4%FERNANDO%ARELLANO%CASTILLO%SR.%0%' +
        '%007%17%N%1%RTN DEL ARMOR N 24 C%SANTA MARIA AHUACATITLAN%62100%7773237091%%%%%%%0%N%0616%%01-01-1970%' +
        '20377%%%%%|C5Val=5%0616%200%200%-0.25%-0.50%35%+0.50%-0.75%20%62%-0.25%-0.50%35%+1.00%%20%+0.00%-0.25%' +
        '20%+1.00%%%%20%OBJETIOCA ES SOBREREFRACCION DE SUS LC%10-10-2011%N%%%|C6Val=6%-0.00%%%+1.00%%%%20%' +
        '%+0.00%%%+1.00%%%%20%%62%60%%c%CERCA%10-10-2011%ARMAZON RANURADO AT2214 U31P10 DH53 PTE17 DE55 DV27%' +
        '0616%%|C7Val=7%P1W, AT2214%$4,370.00%P1W%AT2214%Nylon%%%20-10-2011%%23-03-2012%10-10-2011%|C8Val=8' +
        '%A44259%22591%104880%211271%07-03-2012%211267%07-03-2012%%%A%%131%|'
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
    def idSucursalOrigen = 131
    def idAtendio = '0616'
    def cliente = new Cliente(
        idCliente: 20377,
        nombreCli: 'FERNANDO        ',
        apellidoPatCli: 'ARELLANO   ',
        apellidoMatCli: 'CASTILLO   ',
        titulo: 'SR.                ',
        idOftalmologo: 0,
        idLocalidad: '007',
        idEstado: '17',
        tipoCli: 'N',
        sexoCli: true,
        direccionCli: 'RTN DEL ARMOR N 24 C',
        coloniaCli: 'SANTA MARIA AHUACATITLAN',
        codigo: '62100',
        telCasaCli: '7773237091',
        FCasadaCli: false,
        SUsaAnteojos: 'N' as char,
        idAtendio: idAtendio,
        fechaNac: Date.parse( 'dd-MM-yyyy', '01-01-1970' )
    )
    def trabajo = new Jb(
        rx: 207864,
        idCliente: cliente.idCliente,
        externo: 10,
        material: 'P1W, AT2214',
        saldo: new BigDecimal( 4370.00 ),
        volverLlamar: Date.parse( 'dd-MM-yyyy', '23-03-2012' )
    )
    def examen = new Examen(
        idAtendio: idAtendio,
        avSaOdLejosEx: 200,
        avSaOiLejosEx: 200,
        objOdEsfEx: '-0.25',
        objOdCilEx: '-0.50',
        objOdEjeEx: '35',
        objOiEsfEx: '+0.50',
        objOiCilEx: '-0.75',
        objOiEjeEx: 20,
        objDiEx: 62,
        subOdEsfEx: '-0.25',
        subOdCilEx: '-0.50',
        subOdEjeEx: 35,
        subOdAdcEx: '+1.00',
        subOdAvEx: 20,
        subOiEsfEx: '+0.00',
        subOiCilEx: '-0.25',
        subOiEjeEx: 20,
        subOiAdcEx: '+1.00',
        subOiAvEx: 20,
        observacionesEx: 'OBJETIOCA ES SOBREREFRACCION DE SUS LC',
        fechaAlta: Date.parse( 'dd-MM-yyyy', '10-10-2011' ),
        tipoCli: 'N'
    )
    def receta = new Receta(
        odEsfR: '-0.00',
        odAdcR: '+1.00',
        odAvR: 20,
        oiEsfR: '+0.00',
        oiAdcR: '+1.00',
        oiAvR: 20,
        diLejosR: 62,
        diCercaR: 60,
        sUsoAnteojos: 'c' as char,
        fechaReceta: Date.parse( 'dd-MM-yyyy', '10-10-2011' ),
        observacionesR: 'ARMAZON RANURADO AT2214 U31P10 DH53 PTE17 DE55 DV27',
        idOptometrista: idAtendio
    )
    def notaVenta = new NotaVenta(
        udf2: 'Nylon',
        fechaPrometida: Date.parse( 'dd-MM-yyyy', '20-10-2011' ),
        fechaHoraFactura: Date.parse( 'dd-MM-yyyy', '10-10-2011' )
    )
    def articuloA = new Articulos( articulo: 'AT2214' )
    def articuloB = new Articulos( articulo: 'P1W' )
    def poliza = new Polizas(
        idFactura: 'A44259',
        idCliente: 22591,
        noPoliza: 104880,
        factura: 211271,
        fechaMod: Date.parse( 'dd-MM-yyyy', '07-03-2012' ),
        facturaVenta: 211267,
        fechaVenta: Date.parse( 'dd-MM-yyyy', '07-03-2012' ),
        status: 'A',
        idSucursal: idSucursalOrigen
    )
    clienteDAO.obtenCliente( _ ) >> cliente
    examenDAO.obtenExamenPorTrabajo( _ ) >> examen
    recetaDAO.obtenerRecetaPorTrabajo( _ ) >> receta
    notaVentaDAO.obtenNotaVentaPorTrabajo( _ ) >> notaVenta
    articuloDAO.obtenArticuloGenericoAPorNotaVenta( _ ) >> articuloA
    articuloDAO.obtenArticuloGenericoBPorNotaVenta( _ ) >> articuloB
    polizaDAO.obtenPolizaPorFactura( _ ) >> poliza

    when:
    def actual = business.generaContenidoAcuseEnvio( trabajo, idSucursalOrigen )

    then:
    expected == actual
  }

  def 'genera contenido acuse por receta de envio externo'( ) {
    given:
    def expected = 'sucursalVal=0001|recetaVal=211267|codigoVal=X281|esfera_dVal=+3.25|cilindro_dVal=-1.00|eje_dVal=165|' +
        'adicion_dVal=+2.00|prisma_d_hVal=|prisma_d_vVal=|esfera_iVal=+3.50|cilindro_iVal=-1.25|eje_iVal=175|adicion_iVal=+2.00|' +
        'prisma_i_hVal=|prisma_i_vVal=|distancia_lVal=68|distancia_cVal=66|distancia_m_dVal=|distancia_m_iVal=|alturaVal=11.0|' +
        'tratamientosVal=Aire, Pulido|observacionesVal=CRED.726175. AT2103 AZUL C2L RAYADOS O:0008 uso=b|parVal=P|' +
        'formaVal=AT2102202754|armazonVal=|archivoVal=0001211267RX|'
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
        rx: '',
        externo: '1'
    )
    def notaVenta = new NotaVenta(
        factura: '211267',
        cantLente: 'par',
        udf2: 'Aire, Pulido',
        udf3: 'AT2102202754'
    )
    def articuloB = new Articulos( articulo: 'X281' )
    def receta = new Receta(
        odEsfR: '+3.25',
        odCilR: '-1.00',
        odEjeR: '165',
        odAdcR: '+2.00',
        oiEsfR: '+3.50',
        oiCilR: '-1.25',
        oiEjeR: '175',
        oiAdcR: '+2.00',
        diLejosR: '68',
        diCercaR: '66',
        odAvR: '25',
        oiAvR: '20',
        altOblR: '11.0',
        sUsoAnteojos: 'b' as char,
        observacionesR: 'CRED.726175. AT2103 AZUL C2L RAYADOS O:0008 uso=b'
    )
    reimpresionDAO.obtenConteoReimpresiones( _ ) >> 0
    articuloDAO.obtenArticuloGenericoAPorNotaVenta( _ ) >> null
    articuloDAO.obtenArticuloGenericoBPorNotaVenta( _ ) >> articuloB
    recetaDAO.obtenerRecetaPorTrabajo( _ ) >> receta

    when:
    def actual = business.generaContenidoRecetaAcuseEnvio( trabajo, notaVenta )

    then:
    expected == actual?.trim()
  }

  def 'genera contenido acuse por envio de externo con forma de contacto'( ) {
    given:
    def expected = 'C1Val=1%210726%|C2Val=2%0016%|C3Val=3%1201%|C4Val=4%MARIA EUGENIA%SALCEDO%SANCHEZ%SRA.%0%%007%09%' +
        'C%0%AV. TEXCOCO # 188%CUCHILLA DEL TESORO%07900%57961490%%%%%%MAJR6407176P7%0%S%2207%%11-01-1966%33325%%%%%|' +
        'C5Val=5%0160%400%400%+1.75%-6.00%8%-2.50%-3.25%174%60%+1.50%-4.50%10%%%25%-1.25%-4.00%165%%%%%40%%' +
        '06-12-2003%N%%0%|C6Val=6%-0.25%-4.50%15%%%%%25%%-2.50%-4.00%165%%%%%40%%60%%%l%LEJOS%06-12-2003' +
        '%SE DEJA MISMA GRAD.%0160%%|C7Val=7%X1%$0.00%X1%%Pasta%%%22-03-2012%%%12-03-2012%|C11Val=11%210726%33325%4%' +
        '5557961490%~%12-03-2012%16%|'
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
    def idOptometrista = '0160'
    def cliente = new Cliente(
        idCliente: 33325,
        nombreCli: 'MARIA EUGENIA',
        apellidoPatCli: 'SALCEDO',
        apellidoMatCli: 'SANCHEZ',
        titulo: 'SRA.',
        rfcCli: 'MAJR6407176P7',
        idOftalmologo: 0,
        idLocalidad: '007',
        idEstado: '09',
        tipoCli: 'C',
        sexoCli: false,
        direccionCli: 'AV. TEXCOCO # 188',
        coloniaCli: 'CUCHILLA DEL TESORO',
        codigo: '07900',
        telCasaCli: '57961490',
        FCasadaCli: false,
        SUsaAnteojos: 'S' as char,
        idAtendio: '2207',
        fechaNac: Date.parse( 'dd-MM-yyyy', '11-01-1966' )
    )
    def trabajo = new Jb(
        rx: 210726,
        idCliente: cliente.idCliente,
        externo: 1201,
        material: 'X1',
        saldo: new BigDecimal( 0.00 )
    )
    def examen = new Examen(
        idAtendio: idOptometrista,
        avSaOdLejosEx: 400,
        avSaOiLejosEx: 400,
        objOdEsfEx: '+1.75',
        objOdCilEx: '-6.00',
        objOdEjeEx: '8',
        objOiEsfEx: '-2.50',
        objOiCilEx: '-3.25',
        objOiEjeEx: 174,
        objDiEx: 60,
        subOdEsfEx: '+1.50',
        subOdCilEx: '-4.50',
        subOdEjeEx: 10,
        subOdAvEx: 25,
        subOiEsfEx: '-1.25',
        subOiCilEx: '-4.00',
        subOiEjeEx: 165,
        subOiAvEx: 40,
        fechaAlta: Date.parse( 'dd-MM-yyyy', '06-12-2003' ),
        tipoCli: 'N',
        idOftalmologo: 0
    )
    def receta = new Receta(
        odEsfR: '-0.25',
        odCilR: '-4.50',
        odEjeR: '15',
        odAvR: 25,
        oiEsfR: '-2.50',
        oiCilR: '-4.00',
        oiEjeR: 165,
        oiAvR: 40,
        diLejosR: 60,
        sUsoAnteojos: 'l' as char,
        fechaReceta: Date.parse( 'dd-MM-yyyy', '06-12-2003' ),
        observacionesR: 'SE DEJA MISMA GRAD.',
        idOptometrista: idOptometrista
    )
    def notaVenta = new NotaVenta(
        udf2: 'Pasta',
        fechaPrometida: Date.parse( 'dd-MM-yyyy', '22-03-2012' ),
        fechaHoraFactura: Date.parse( 'dd-MM-yyyy', '12-03-2012' )
    )
    def articuloB = new Articulos( articulo: 'X1' )
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

    when:
    def actual = business.generaContenidoAcuseEnvio( trabajo, idSucursalOrigen )

    then:
    expected == actual
  }

  def 'genera contenido acuse por envio de externo con poliza y forma de contacto'( ) {
    given:
    def expected = 'C1Val=1%207864%|C2Val=2%0131%|C3Val=3%10%|' +
        'C4Val=4%FERNANDO%ARELLANO%CASTILLO%SR.%0%%007%17%N%1%RTN DEL ARMOR N 24 C%SANTA MARIA AHUACATITLAN%62100' +
        '%7773237091%%%%%%%0%N%0616%%01-01-1970%20377%%%%%|' +
        'C5Val=5%0616%200%200%-0.25%-0.50%35%+0.50%-0.75%20%62%-0.25%-0.50%35%+1.00%%20%+0.00%-0.25%20%+1.00%%%%20' +
        '%OBJETIOCA ES SOBREREFRACCION DE SUS LC%10-10-2011%N%%%|' + 'C6Val=6%-0.00%%%+1.00%%%%20%%+0.00%%%+1.00%%' +
        '%%20%%62%60%%c%CERCA%10-10-2011%ARMAZON RANURADO AT2214 U31P10 DH53 PTE17 DE55 DV27%0616%%|' +
        'C7Val=7%P1W, AT2214%$4,370.00%P1W%AT2214%Nylon%%%20-10-2011%%23-03-2012%10-10-2011%|' +
        'C8Val=8%A44259%22591%104880%211271%07-03-2012%211267%07-03-2012%%%A%%131%|' +
        'C9Val=9%2420%3455%PM1W%%B%1%$3,350.00%$3,015.00%10-03-2012%|' +
        'C10Val=10%2420%167708%GA0892%YVE%A%1%$3,990.00%$3,591.00%10-03-2012%|' +
        'C11Val=11%210656%28918%3%5554069570%~%10-03-2012%16%|'
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
    def idSucursalOrigen = 131
    def idAtendio = '0616'
    def cliente = new Cliente(
        idCliente: 20377,
        nombreCli: 'FERNANDO',
        apellidoPatCli: 'ARELLANO',
        apellidoMatCli: 'CASTILLO',
        titulo: 'SR.',
        idOftalmologo: 0,
        idLocalidad: '007',
        idEstado: '17',
        tipoCli: 'N',
        sexoCli: true,
        direccionCli: 'RTN DEL ARMOR N 24 C',
        coloniaCli: 'SANTA MARIA AHUACATITLAN',
        codigo: '62100',
        telCasaCli: '7773237091',
        FCasadaCli: false,
        SUsaAnteojos: 'N' as char,
        idAtendio: idAtendio,
        fechaNac: Date.parse( 'dd-MM-yyyy', '01-01-1970' )
    )
    def trabajo = new Jb(
        rx: 207864,
        idCliente: cliente.idCliente,
        externo: 10,
        material: 'P1W, AT2214',
        saldo: new BigDecimal( 4370.00 ),
        volverLlamar: Date.parse( 'dd-MM-yyyy', '23-03-2012' )
    )
    def examen = new Examen(
        idAtendio: idAtendio,
        avSaOdLejosEx: 200,
        avSaOiLejosEx: 200,
        objOdEsfEx: '-0.25',
        objOdCilEx: '-0.50',
        objOdEjeEx: '35',
        objOiEsfEx: '+0.50',
        objOiCilEx: '-0.75',
        objOiEjeEx: 20,
        objDiEx: 62,
        subOdEsfEx: '-0.25',
        subOdCilEx: '-0.50',
        subOdEjeEx: 35,
        subOdAdcEx: '+1.00',
        subOdAvEx: 20,
        subOiEsfEx: '+0.00',
        subOiCilEx: '-0.25',
        subOiEjeEx: 20,
        subOiAdcEx: '+1.00',
        subOiAvEx: 20,
        observacionesEx: 'OBJETIOCA ES SOBREREFRACCION DE SUS LC',
        fechaAlta: Date.parse( 'dd-MM-yyyy', '10-10-2011' ),
        tipoCli: 'N'
    )
    def receta = new Receta(
        odEsfR: '-0.00',
        odAdcR: '+1.00',
        odAvR: 20,
        oiEsfR: '+0.00',
        oiAdcR: '+1.00',
        oiAvR: 20,
        diLejosR: 62,
        diCercaR: 60,
        sUsoAnteojos: 'c' as char,
        fechaReceta: Date.parse( 'dd-MM-yyyy', '10-10-2011' ),
        observacionesR: 'ARMAZON RANURADO AT2214 U31P10 DH53 PTE17 DE55 DV27',
        idOptometrista: idAtendio
    )
    def notaVenta = new NotaVenta(
        udf2: 'Nylon',
        fechaPrometida: Date.parse( 'dd-MM-yyyy', '20-10-2011' ),
        fechaHoraFactura: Date.parse( 'dd-MM-yyyy', '10-10-2011' )
    )
    def articuloA = new Articulos( articulo: 'AT2214' )
    def articuloB = new Articulos( articulo: 'P1W' )
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
        rx: '210656',
        idCliente: 28918,
        contacto: '5554069570',
        observaciones: '\n',
        tipoContacto: new TipoContacto( idTipoContacto: 3 ),
        fechaMod: Date.parse( 'dd-MM-yyyy', '10-03-2012' ),
        idSucursal: 16
    )
    clienteDAO.obtenCliente( _ ) >> cliente
    examenDAO.obtenExamenPorTrabajo( _ ) >> examen
    recetaDAO.obtenerRecetaPorTrabajo( _ ) >> receta
    notaVentaDAO.obtenNotaVentaPorTrabajo( _ ) >> notaVenta
    articuloDAO.obtenArticuloGenericoAPorNotaVenta( _ ) >> articuloA
    articuloDAO.obtenArticuloGenericoBPorNotaVenta( _ ) >> articuloB
    polizaDAO.obtenPolizaPorFactura( _ ) >> poliza
    formaContactoDAO.obtenPorRx( _ ) >> formaContacto

    when:
    def actual = business.generaContenidoAcuseEnvio( trabajo, idSucursalOrigen )

    then:
    expected == actual
  }
}

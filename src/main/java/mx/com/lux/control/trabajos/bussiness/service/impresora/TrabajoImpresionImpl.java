package mx.com.lux.control.trabajos.bussiness.service.impresora;

import mx.com.lux.control.trabajos.data.dao.ExternoDAO;
import mx.com.lux.control.trabajos.data.dao.PagoExtraDAO;
import mx.com.lux.control.trabajos.data.dao.envio.SucursalDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.CajasDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.ClienteDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.JbTrackDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.exception.PrinterException;
import mx.com.lux.control.trabajos.exception.SystemException;
import mx.com.lux.control.trabajos.util.properties.ImpresionPropertyHelper;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



@Service( "trabajoImpresion" )
public class TrabajoImpresionImpl implements TrabajoImpresion {

	private final Logger log = Logger.getLogger( TrabajoImpresion.class );

	private static final int TAM_COL_RX = 15;
	private static final int TAM_COL_CODIGO = 30;
	private static final int TAM_COL_SURTE = 5;
	private static final int TAM_COL_ID_DOC = 15;
	private static final int TAM_COL_CANTIDAD = 15;
	private static final String ACEPTA_CLIENTE_LABEL = ImpresionPropertyHelper.getProperty( "orden-servicio.label.acepta-cliente" );
	private static final String PREFIJO_ORDEN_SERVICIO = "S";
	private static final String sobreLabel = "SOBRE:";
	private static final String deLabel = "DE:";
	private static final String paraLabel = "PARA:";
	private static final String areaLabeL = "AREA:";
	private static final String contenidoLabel = "CONTENIDO:";
	private static final String sobreLabel2 = "SOBRE:";
	private static final String paraLabel2 = "PARA:";
	private static final String areaLabeL2 = "AREA:";
	private static final String rxLabel2 = "RX:";
	private static final String folioLabel = "FOLIO:";
	private static final String doctoLabel2 = "DOCTO:";
	private static final String sucursalSolicitaLabel2 = "SUCURSAL QUE SOLICITA:";
	private static final String apartadoLabel2 = "APARTADO:";
    private static final String folioSobreLabel = "FOLIO P.:";
	private static final String contenidoLabel2 = "CONTENIDO:";
	private static final String sucursalLabel = "SUCURSAL:";
	private static final String sucursalDestinoLabel = "SUC. DESTINO:";
	private static final String facturaLabel = "FACTURA:";
	private static final String viajeLabel = "VIAJE:";
	private static final String empleadoLabel = "EMPLEADO:";
	private static final String tituloSobre = "ENVIO DE SOBRE";
	private static final String recibido = "RECIBIDO";
	private static final String fechaVentaLabel = "FECHA VENTA:";
	private static final String materialLabel = "MATERIAL:";
	private static final String numRotoLabel = "NUM ROTO:";
	private static final String causaLabel = "CAUSA:";
	private static final String responsableLabel = "RESPONSABLE:";
	private static final String voboGerenteLabel = "Vo. Bo. PARA GERENTE";
	private static final String telefonoLabel = "TELEFONO:";
	private static final String clienteLabel = "CLIENTE:";
	private static final String domicilioLabel = "DOMICILIO:";
	private static final String coloniaLabel = "COLONIA:";
	private static final String cpLabel = "C.P.:";
	private static final String casaLabel = "Casa:";
	private static final String trabajoLabel = "Trabajo:";
	private static final String adicionalLabel = "Adicional:";
	private static final String dejoLabel = "DEJO:";
	private static final String fechaEntregaLabel = "FECHA ENTREGA:";
	private static final String servicioLabel = "SERVICIO:";
	private static final String instruccionesLabel = "INSTRUCCIONES:";
	private static final String notaLabel = "NOTA:";
	private static final String condicionesLabel = "CONDICIONES GENERALES:";
	private static final String notaContent = "Todos estos trabajos se hacen si el cliente acepta el riesgo de deterioro que puede sufrir su anteojo por lo cual pedimos su ACEPTACION.";
    private static final String notaContent1 = "Acepto condiciones en las que dejo mi producto y me doy por enterado del riesgo o deterioro que pueda sufrir el mismo a causa de la manipulacion para su habilitacion o reparacion";
	private static final String titleColumnaRx = "RX";
	private static final String titleColumnaCodigo = "CODIGO";
	private static final String titleColumnaSurte = "SURTE";
	private static final String titleColumnaIdDocto = "DOCUMENTO";
	private static final String titleColumnaCantidad = "CANTIDAD";
	private static final String tituloPackingPrevio = "PACKING PREVIO";
	private static final String tituloPackingCerrado = "PACKING CERRADO";
	private static final String tituloRoto = "ROTO ARM.";
	private static final String tituloOrdenServicio = "ORDEN DE SERVICIO";
    private static final String tituloMaterialCliente = "MATERIAL DEL CLIENTE";
	private static final String trabajosTitle = "TRABAJOS";
	private static final String rotosExternosTitle = "ROTOS EXTERNOS";
	private static final String refRepSpTitle = "REFACCIONES/REPARACIONES/SP";
	private static final String garantiasTitle = "GARANTIAS";
	private static final String ordenServicioTitle = "ORDEN DE SERVICIO";
	private static final String trabajosExternosTitle = "TRABAJOS EXTERNOS";
	private static final String devolucionesTitle = "DEVOLUCIONES";
	private static final String sobresTitle = "SOBRES";
	private static final String rxSobresTitle = "RX SOBRES";
	private static final String devArmazonesTitle = "DEV ARMAZONES SP";
	private static final String printerExceptionMessage = "La impresora no esta lista.";
	private static final Integer idCajas = Integer.valueOf( ImpresionPropertyHelper.getProperty( "id.cajas" ) );

	@Resource
	private TrabajoDAO trabajoDAO;

	@Resource
	private JbTrackDAO trackDAO;

	@Resource
	private SucursalDAO sucursalDAO;

	@Resource
	private CajasDAO cajasDAO;

	@Resource
	private PagoExtraDAO pagoExtraDAO;

	@Resource
	private ExternoDAO externoDAO;

	@Resource
	private ClienteDAO clienteDAO;

	@Override
	public void imprimirPackingPrevio( Date fecha, int idViaje, Empleado empleado, Sucursal sucursal, List<Jb> trabajosLab, List<Jb> trabajosRotosExternos, List<Jb> trabajosRefRepSp, List<Jb> trabajosGarantias, List<Jb> trabajosOrdenesServicio, List<Jb> trabajosExternos, List<JbSobre> sobresVacios, List<JbSobre> sobresNoVacios, List<JbDev> trabajosDevoluciones, List<DoctoInv> doctoInvList ) throws ApplicationException {
		String nombreArchivo = "";
		try {
			nombreArchivo = Long.toString( fecha.getTime() ) + Double.toString( Math.random() );
			imprimirPackingPrevioArchivo( fecha, idViaje, empleado, sucursal, trabajosLab, trabajosRotosExternos, trabajosRefRepSp, trabajosGarantias, trabajosOrdenesServicio, trabajosExternos, sobresVacios, sobresNoVacios, trabajosDevoluciones, doctoInvList, nombreArchivo );
			Cajas cajas = cajasDAO.findCajasById( idCajas );
			String cmd = cajas.getImpTicket() + " " + nombreArchivo;
			Runtime run = Runtime.getRuntime();
			Process pr = run.exec( cmd );
			pr.waitFor();
		} catch ( IOException ioe ) {
			throw new PrinterException( printerExceptionMessage );
		} catch ( InterruptedException e ) {
			throw new SystemException( e.getMessage(), e );
		} finally {
			File f = new File( nombreArchivo );
			if ( f.exists() && f.delete() ) {
				log.debug( "Fichero borrado corectamente" );
			}
		}
	}

	private void imprimirPackingPrevioArchivo( Date fecha, int idViaje, Empleado empleado, Sucursal sucursal, List<Jb> trabajosLab, List<Jb> trabajosRotosExternos, List<Jb> trabajosRefRepSp, List<Jb> trabajosGarantias, List<Jb> trabajosOrdenesServicio, List<Jb> trabajosExternos, List<JbSobre> sobresVacios, List<JbSobre> sobresNoVacios, List<JbDev> trabajosDevoluciones, List<DoctoInv> doctoInvList, String nombreArchivo ) throws IOException, DAOException {
		SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );
		ImpresoraTM88 ti = new ImpresoraTM88( nombreArchivo );
		// Inicializar
		// Cabecera
		ti.imprimirSeparacion();
		ti.alimentarPapel( 40 );
		ti.alinear( TipoAlineacion.CENTRO );
		ti.establecerTipoImpresion( TipoImpresion.DOBLEALTURA_ENFATIZADO, TipoFuente.A, false );
		ti.imprimirString( tituloPackingPrevio );
		ti.saltoLinea();
		ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.A, false );
		ti.alinear( TipoAlineacion.IZQUIERDA );
		ti.imprimirSeparacion();
		// Encabezado
		ti.alimentarPapel( 10 );
		ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
		// Fecha
		ti.alinear( TipoAlineacion.DERECHA );
		ti.imprimirString( sdf.format( fecha ) );
		ti.imprimirString( "    " );
		ti.saltoLinea();
		// Sucursal
		ti.alinear( TipoAlineacion.IZQUIERDA );
		ti.imprimirString( sucursalLabel );
		ti.imprimirString( "  " );
		ti.imprimirString( sucursal.getNombre() );
		ti.saltoLinea();
		// Viaje
		ti.imprimirString( viajeLabel );
		ti.imprimirString( "  " );
		ti.imprimirString( Integer.toString( idViaje ) );
		ti.saltoLinea();
		// Empleado
		ti.imprimirString( empleadoLabel );
		ti.imprimirString( "  " );
		ti.imprimirString( empleado.getNombreApellidos() );
		ti.saltoLinea();

		// Trabajos
		if ( !trabajosLab.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( trabajosTitle );
			// Encabezados
			ti.alinear( TipoAlineacion.IZQUIERDA );
			ti.imprimirEncabezado( TAM_COL_RX, titleColumnaRx );
			ti.imprimirEncabezado( TAM_COL_CODIGO, titleColumnaCodigo );
			ti.imprimirEncabezado( TAM_COL_SURTE, titleColumnaSurte );
			ti.saltoLinea();
			// Contenido
			ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
			for ( Jb jb : trabajosLab ) {
				imprimirTrabajoConRxCodigoSurte( jb, ti );
//				ti.saltoLinea();
			}
			ti.saltoLinea();
		}

		// Rotos Externos
		if ( !trabajosRotosExternos.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( rotosExternosTitle );
			// Encabezados
			ti.alinear( TipoAlineacion.IZQUIERDA );
			ti.imprimirEncabezado( TAM_COL_RX, titleColumnaRx );
			ti.imprimirEncabezado( TAM_COL_CODIGO, titleColumnaCodigo );
			ti.saltoLinea();
			// Contenido
			ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
			for ( Jb jb : trabajosRotosExternos ) {
				imprimirTrabajoConRxCodigo( jb, ti );
				ti.saltoLinea();
			}
			ti.saltoLinea();
		}

		// REFACCIONES/REPARACIONES/SP
		if ( !trabajosRefRepSp.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( refRepSpTitle );
			// Encabezados
			ti.alinear( TipoAlineacion.IZQUIERDA );
			ti.imprimirEncabezado( TAM_COL_RX, titleColumnaRx );
			ti.imprimirEncabezado( TAM_COL_CODIGO, titleColumnaCodigo );
			ti.saltoLinea();
			// Contenido
			ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
			for ( Jb jb : trabajosRefRepSp ) {
				imprimirTrabajoConRxCodigo( jb, ti );
				ti.saltoLinea();
			}
			ti.saltoLinea();
		}

		// Garantias
		if ( !trabajosGarantias.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( garantiasTitle );
			// Encabezados
			ti.alinear( TipoAlineacion.IZQUIERDA );
			ti.imprimirEncabezado( TAM_COL_RX, titleColumnaRx );
			ti.imprimirEncabezado( TAM_COL_CODIGO, titleColumnaCodigo );
			ti.saltoLinea();
			// Contenido
			ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
			for ( Jb jb : trabajosGarantias ) {
				imprimirTrabajoConRxCodigo( jb, ti );
				ti.saltoLinea();
			}
			ti.saltoLinea();
		}

		// Orden de Servicio
		if ( !trabajosOrdenesServicio.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( ordenServicioTitle );
			// Encabezados
			ti.alinear( TipoAlineacion.IZQUIERDA );
			ti.imprimirEncabezado( TAM_COL_RX, titleColumnaRx );
			ti.imprimirEncabezado( TAM_COL_CODIGO, titleColumnaCodigo );
			ti.saltoLinea();
			// Contenido
			ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
			for ( Jb jb : trabajosOrdenesServicio ) {
				imprimirTrabajoConRxCodigo( jb, ti );
				ti.saltoLinea();
			}
			ti.saltoLinea();
		}

		// Trabajos Externos
		if ( !trabajosExternos.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( trabajosExternosTitle );
			// Contenido
			ti.alinear( TipoAlineacion.IZQUIERDA );
			for ( Jb jb : trabajosExternos ) {
				imprimirTrabajoExterno( jb, ti );
				ti.saltoLinea();
			}
		}

		// Devoluciones
		if ( !doctoInvList.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( devolucionesTitle );
			// Encabezados
			ti.alinear( TipoAlineacion.IZQUIERDA );
			ti.imprimirEncabezado( TAM_COL_ID_DOC, titleColumnaIdDocto );
			ti.imprimirEncabezado( TAM_COL_CANTIDAD, titleColumnaCantidad );
			ti.saltoLinea();
			// Contenido
			ti.alinear( TipoAlineacion.IZQUIERDA );
			for ( DoctoInv doctoInv : doctoInvList ) {
				imprimirDevolucion( doctoInv, ti );
				ti.saltoLinea();
			}
		}

		// Sobres
		if ( !sobresVacios.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( sobresTitle );
			// Contenido
			ti.alinear( TipoAlineacion.IZQUIERDA );
			for ( JbSobre sobre : sobresVacios ) {
				imprimirSobreSinRx( sobre, ti );
				ti.saltoLinea();
			}
		}

		// Sobres Rx
		if ( !sobresNoVacios.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( rxSobresTitle );
			// Contenido
			ti.alinear( TipoAlineacion.IZQUIERDA );
			for ( JbSobre sobre : sobresNoVacios ) {
				imprimirSobreConRx( sobre, ti );
				ti.saltoLinea();
			}
		}

		// Dev Armazones SP
		if ( !trabajosDevoluciones.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( devArmazonesTitle );
			// Contenido
			ti.alinear( TipoAlineacion.IZQUIERDA );
			for ( JbDev jbDev : trabajosDevoluciones ) {
				imprimirJbDev( jbDev, ti );
				ti.saltoLinea();
			}
		}

		// Finalizar
		ti.alimentarPapel( 200 );
		ti.alimentarPapel( 200 );
		ti.cortarPapel( TipoCorte.CORTE_PARCIAL );
		ti.finalizarImpresion();
	}

	@Override
	public void imprimirPackingCerrado( Date fecha, int idViaje, String folioViaje, Empleado empleado, Sucursal sucursal, List<Jb> trabajosLab, List<Jb> trabajosRotosExternos, List<Jb> trabajosRefRepSp, List<Jb> trabajosGarantias, List<Jb> trabajosOrdenesServicio, List<Jb> trabajosExternos, List<JbSobre> sobresVacios, List<JbSobre> sobresNoVacios, List<JbDev> trabajosDevoluciones, List<DoctoInv> doctoInvList ) throws ApplicationException {
        String nombreArchivo = "";
		try {

            //nombreArchivo = Long.toString( new Date().getTime() ) + Double.toString( Math.random() );
            File file = createTempFile("ticketSobre");
            nombreArchivo = file.getAbsolutePath().toString();
//            nombreArchivo = Long.toString( fecha.getTime() ) + Double.toString( Math.random() );

			imprimirPackingCerradoArchivo( fecha, idViaje, folioViaje, empleado, sucursal, trabajosLab, trabajosRotosExternos, trabajosRefRepSp, trabajosGarantias, trabajosOrdenesServicio, trabajosExternos, sobresVacios, sobresNoVacios, trabajosDevoluciones, doctoInvList, nombreArchivo );
			Cajas cajas = cajasDAO.findCajasById( idCajas );
			String cmd = cajas.getImpTicket() + " " + nombreArchivo;
            System.out.println( cmd );
			Runtime run = Runtime.getRuntime();
			Process pr = run.exec( cmd );
			pr.waitFor();
		} catch ( IOException ioe ) {
			throw new PrinterException( printerExceptionMessage );
		} catch ( InterruptedException e ) {
			throw new SystemException( e.getMessage(), e );
		}
        /*finally {
			File f = new File( nombreArchivo );
			if ( f.exists() && f.delete() ) {
				log.debug( "Fichero borrado correctamente" );
			}
		}*/
	}


    @Override
    public void imprimirPackingCerradoHist( Date fecha, int idViaje, String folioViaje, Empleado empleado, Sucursal sucursal, List<JbViajeDet> trabajosLab, List<JbViajeDet> trabajosRotosExternos, List<JbViajeDet> trabajosRefRepSp, List<JbViajeDet> trabajosGarantias, List<JbViajeDet> trabajosOrdenesServicio, List<JbViajeDet> trabajosExternos, List<JbSobre> sobresVacios, List<JbSobre> sobresNoVacios, List<JbDev> trabajosDevoluciones, List<DoctoInv> doctoInvList ) throws ApplicationException{
        String nombreArchivo = "";
        try {
            nombreArchivo = Long.toString( fecha.getTime() ) + Double.toString( Math.random() );
            imprimirPackingCerradoArchivoHist( fecha, idViaje, folioViaje, empleado, sucursal, trabajosLab, trabajosRotosExternos, trabajosRefRepSp, trabajosGarantias, trabajosOrdenesServicio, trabajosExternos, sobresVacios, sobresNoVacios, trabajosDevoluciones, doctoInvList, nombreArchivo );
            Cajas cajas = cajasDAO.findCajasById( idCajas );
            String cmd = cajas.getImpTicket() + " " + nombreArchivo;
            System.out.println( cmd );
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec( cmd );
            pr.waitFor();
        } catch ( IOException ioe ) {
            throw new PrinterException( printerExceptionMessage );
        } catch ( InterruptedException e ) {
            throw new SystemException( e.getMessage(), e );
        } finally {
            File f = new File( nombreArchivo );
            if ( f.exists() && f.delete() ) {
                log.debug( "Fichero borrado correctamente" );
            }
        }
    }

	private void imprimirPackingCerradoArchivo( Date fecha, int idViaje, String folioViaje, Empleado empleado, Sucursal sucursal, List<Jb> trabajosLab, List<Jb> trabajosRotosExternos, List<Jb> trabajosRefRepSp, List<Jb> trabajosGarantias, List<Jb> trabajosOrdenesServicio, List<Jb> trabajosExternos, List<JbSobre> sobresVacios, List<JbSobre> sobresNoVacios, List<JbDev> trabajosDevoluciones, List<DoctoInv> doctoInvList, String nombreArchivo ) throws IOException, DAOException {
		SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );
		ImpresoraTM88 ti = new ImpresoraTM88( nombreArchivo );

		// Inicializar
        // Codigo barra inicio escaneo
        SimpleDateFormat dateCodeBar = new SimpleDateFormat( "ddMM" );
        String idSuc = ApplicationUtils.shiftStringRight(sucursal.idSucursal(), 5, "0");
        String strViaje = ApplicationUtils.shiftStringRight(Integer.toString(idViaje), 2, "0");
        String barCode = strViaje + dateCodeBar.format(fecha) + idSuc;

        ti.alinear( TipoAlineacion.CENTRO );
        ti.imprimeCodigoBarras(barCode, TipoAnchuraCodigoBarras.N2, true);
        ti.alimentarPapel( 40 );

		// Cabecera
		ti.imprimirSeparacion();
		ti.alimentarPapel( 40 );
		ti.alinear( TipoAlineacion.CENTRO );
		ti.establecerTipoImpresion( TipoImpresion.DOBLEALTURA_ENFATIZADO, TipoFuente.A, false );
		ti.imprimirString( tituloPackingCerrado );
		ti.saltoLinea();
		ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.A, false );
		ti.alinear( TipoAlineacion.IZQUIERDA );
		ti.imprimirSeparacion();

		// Encabezado
		ti.alimentarPapel( 10 );
		ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );

        // Fecha
		ti.alinear( TipoAlineacion.DERECHA );
		ti.imprimirString( sdf.format( fecha ) );
		ti.imprimirString( "    " );
		ti.saltoLinea();
		// Sucursal
		ti.alinear( TipoAlineacion.IZQUIERDA );
		ti.imprimirString( sucursalLabel );
		ti.imprimirString( "  " );
		ti.imprimirString( sucursal.getNombre() );
		ti.saltoLinea();
		// Viaje
		ti.imprimirString( viajeLabel );
		ti.imprimirString( "  " );
		ti.imprimirString( Integer.toString( idViaje ) );
		ti.saltoLinea();
		// Folio Viaje
		ti.imprimirString( folioLabel );
		ti.imprimirString( "  " );
		ti.imprimirString( folioViaje );
		ti.saltoLinea();
		// Empleado
		ti.imprimirString( empleadoLabel );
		ti.imprimirString( "  " );
		ti.imprimirString( empleado.getNombreApellidos() );
		ti.saltoLinea();

		// Trabajos
		if ( !trabajosLab.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( trabajosTitle );
			// Encabezados
			ti.alinear( TipoAlineacion.IZQUIERDA );
			ti.imprimirEncabezado( TAM_COL_RX, titleColumnaRx );
			ti.imprimirEncabezado( TAM_COL_CODIGO, titleColumnaCodigo );
			ti.imprimirEncabezado( TAM_COL_SURTE, titleColumnaSurte );
			ti.saltoLinea();
			// Contenido
			ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
			for ( Jb jb : trabajosLab ) {
				imprimirTrabajoConRxCodigoSurte( jb, ti );
				//ti.saltoLinea();
			}
			ti.saltoLinea();
		}

		// Rotos Externos
		if ( !trabajosRotosExternos.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( rotosExternosTitle );
			// Encabezados
			ti.alinear( TipoAlineacion.IZQUIERDA );
			ti.imprimirEncabezado( TAM_COL_RX, titleColumnaRx );
			ti.imprimirEncabezado( TAM_COL_CODIGO, titleColumnaCodigo );
			ti.saltoLinea();
			// Contenido
			ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
			for ( Jb jb : trabajosRotosExternos ) {
				imprimirTrabajoConRxCodigo( jb, ti );
				ti.saltoLinea();
			}
			ti.saltoLinea();
		}

		// REFACCIONES/REPARACIONES/SP
		if ( !trabajosRefRepSp.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( refRepSpTitle );
			// Encabezados
			ti.alinear( TipoAlineacion.IZQUIERDA );
			ti.imprimirEncabezado( TAM_COL_RX, titleColumnaRx );
			ti.imprimirEncabezado( TAM_COL_CODIGO, titleColumnaCodigo );
			ti.saltoLinea();
			// Contenido
			ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
			for ( Jb jb : trabajosRefRepSp ) {
				imprimirTrabajoConRxCodigo( jb, ti );
				ti.saltoLinea();
			}
			ti.saltoLinea();
		}

		// Garantias
		if ( !trabajosGarantias.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( garantiasTitle );
			// Encabezados
			ti.alinear( TipoAlineacion.IZQUIERDA );
			ti.imprimirEncabezado( TAM_COL_RX, titleColumnaRx );
			ti.imprimirEncabezado( TAM_COL_CODIGO, titleColumnaCodigo );
			ti.saltoLinea();
			// Contenido
			ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
			for ( Jb jb : trabajosGarantias ) {
				imprimirTrabajoConRxCodigo( jb, ti );
				ti.saltoLinea();
			}
			ti.saltoLinea();
		}

		// Orden de Servicio
		if ( !trabajosOrdenesServicio.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( ordenServicioTitle );
			// Encabezados
			ti.alinear( TipoAlineacion.IZQUIERDA );
			ti.imprimirEncabezado( TAM_COL_RX, titleColumnaRx );
			ti.imprimirEncabezado( TAM_COL_CODIGO, titleColumnaCodigo );
			ti.saltoLinea();
			// Contenido
			ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
			for ( Jb jb : trabajosOrdenesServicio ) {
				imprimirTrabajoConRxCodigo( jb, ti );
				ti.saltoLinea();
			}
			ti.saltoLinea();
		}

		// Trabajos Externos
		if ( !trabajosExternos.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( trabajosExternosTitle );
			// Contenido
			ti.alinear( TipoAlineacion.IZQUIERDA );
			for ( Jb jb : trabajosExternos ) {
				imprimirTrabajoExterno( jb, ti );
				ti.saltoLinea();
			}
		}

		// Devoluciones
		if ( !doctoInvList.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( devolucionesTitle );
			// Encabezados
			ti.alinear( TipoAlineacion.IZQUIERDA );
			ti.imprimirEncabezado( TAM_COL_ID_DOC, titleColumnaIdDocto );
			ti.imprimirEncabezado( TAM_COL_CANTIDAD, titleColumnaCantidad );
			ti.saltoLinea();
			// Contenido
			ti.alinear( TipoAlineacion.IZQUIERDA );
			for ( DoctoInv doctoInv : doctoInvList ) {
				imprimirDevolucion( doctoInv, ti );
				ti.saltoLinea();
			}
		}

		// Sobres
		if ( !sobresVacios.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( sobresTitle );
			// Contenido
			ti.alinear( TipoAlineacion.IZQUIERDA );
			for ( JbSobre sobre : sobresVacios ) {
				imprimirSobreSinRx( sobre, ti );
				ti.saltoLinea();
			}
		}

		// Sobres Rx
		if ( !sobresNoVacios.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( rxSobresTitle );
			// Contenido
			ti.alinear( TipoAlineacion.IZQUIERDA );
			for ( JbSobre sobre : sobresNoVacios ) {
				imprimirSobreConRx( sobre, ti );
				ti.saltoLinea();
			}
		}

		// Dev Armazones SP
		if ( !trabajosDevoluciones.isEmpty() ) {
			ti.alimentarPapel( 40 );
			// Subtitulo
			ti.imprimirSubtitulo( devArmazonesTitle );
			// Contenido
			ti.alinear( TipoAlineacion.IZQUIERDA );
			for ( JbDev jbDev : trabajosDevoluciones ) {
				imprimirJbDev( jbDev, ti );
				ti.saltoLinea();
			}
		}

        // Codigo barra finalizar captura
        ti.alinear( TipoAlineacion.CENTRO );
        ti.alimentarPapel( 40 );
        ti.imprimeCodigoBarras("100001", TipoAnchuraCodigoBarras.N3, true);

        // Finalizar
		ti.alimentarPapel( 200 );
		ti.alimentarPapel( 200 );
		ti.cortarPapel( TipoCorte.CORTE_PARCIAL );
		ti.finalizarImpresion();
	}

	@Override
	public void imprimirSobre( Empleado empleado, JbSobre sobre ) throws ApplicationException {
		String nombreArchivo = null;

        try {
			//nombreArchivo = Long.toString( new Date().getTime() ) + Double.toString( Math.random() );
            File file = createTempFile("ticketSobre");
            nombreArchivo = file.getAbsolutePath().toString();

            imprimirSobreArchivo( new Date(), empleado, sobre, nombreArchivo );
			Cajas cajas = cajasDAO.findCajasById( idCajas );
			String cmd = cajas.getImpTicket() + " " + nombreArchivo;
			System.out.println( cmd );
			Runtime run = Runtime.getRuntime();
			Process pr = run.exec( cmd );
			pr.waitFor();
		} catch ( IOException ioe ) {
			throw new PrinterException( printerExceptionMessage );
		} catch ( InterruptedException e ) {
			throw new SystemException( e.getMessage(), e );
		} //finally {
//			File f = new File( nombreArchivo );
//			if ( f.exists() && f.delete() ) {
//				log.debug( "Fichero borrado correctamente" );
//			}
//		}
	}

	private void imprimirSobreArchivo( Date fecha, Empleado empleado, JbSobre sobre, String nombreArchivo ) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );

		ImpresoraTM88 ti = new ImpresoraTM88( nombreArchivo );
		// Inicializar
        ti.alimentarPapel( 40 );

        // Codigo Barras
        String idSuc = ApplicationUtils.shiftStringRight(empleado.getSucursal().idSucursal(), 5, "0");
        String numSobre = ApplicationUtils.shiftStringRight(sobre.getId().toString(), 5, "0");
        String codeBar = "P" + idSuc + numSobre;

        ti.alinear( TipoAlineacion.CENTRO );
        ti.imprimeCodigoBarras( codeBar, TipoAnchuraCodigoBarras.N2, true );

		// Cabecera
        ti.imprimirSeparacion();
		ti.establecerTipoImpresion( TipoImpresion.DOBLEALTURA_ENFATIZADO, TipoFuente.A, false );
		ti.imprimirString( tituloSobre );
		ti.saltoLinea();
		ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.A, false );
		ti.alinear( TipoAlineacion.IZQUIERDA );
		ti.imprimirSeparacion();

		// Fecha
		ti.alimentarPapel( 10 );
		ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
		ti.alinear( TipoAlineacion.DERECHA );
		ti.imprimirString( sdf.format( fecha ) );
		ti.imprimirString( "    " );
		ti.saltoLinea();

		// Cuerpo
		ti.alinear( TipoAlineacion.IZQUIERDA );
		imprimirSobre( sobre, empleado, ti );

		// Pie
		ti.alimentarPapel( 150 );
		ti.alinear( TipoAlineacion.CENTRO );
		ti.establecerTipoImpresion( TipoImpresion.ENFATIZADO, TipoFuente.B, false );
		ti.imprimirLineaFirma();
		ti.imprimirString( recibido );
		// Finalizar
		ti.alimentarPapel( 200 );
		ti.alimentarPapel( 200 );
		ti.cortarPapel( TipoCorte.CORTE_PARCIAL );
		ti.finalizarImpresion();
	}

	private void imprimirTrabajoConRxCodigoSurte( Jb jb, ImpresoraTM88 ti ) throws IOException, DAOException {

        List<JbTrack> tracks = trackDAO.findJbTrackByRxAndEstado( jb.getRx(), "FAX" );

        String colSurte = "";

        if ( !tracks.isEmpty() ) {
            // Si existe en JB_TRACK.estado = FAX, agregar una "F" en JB.surte
//            if ( jb.getEstado().getIdEdo().trim().equals("RPE") ) {
            if ( jb.getEstado().getIdEdo().trim().equals("RPE") )
                colSurte = jb.getSurte();
            else
                colSurte = "F" + jb.getSurte();
        } else {
            colSurte = jb.getSurte();
        }

        //No incluir en ticket si es Fax con surte Pino
        if ( colSurte.trim().equals("FP") )
            return;

        // Columna de RX
		// - Si JB.roto > 0, anteponer una "R" a JB.rx
		if ( jb.getRoto() != null && jb.getRoto() > 0 ) {
			ti.imprimirString( TAM_COL_RX, "R" + jb.getRx() );
		} else {
			ti.imprimirString( TAM_COL_RX, jb.getRx() );
		}

        // Columna de Codigo (Material)
		ti.imprimirString( TAM_COL_CODIGO, jb.getMaterial() );

        // Columna de surte
        ti.imprimirString(TAM_COL_SURTE, colSurte);
        ti.saltoLinea();

	}


    private void imprimirTrabajoConRxCodigoSurteHist( JbViajeDet jb, ImpresoraTM88 ti ) throws IOException, DAOException {
        // - Si JB.roto > 0, anteponer una "R" a JB.rx

        List<JbTrack> tracks = trackDAO.findJbTrackByRxAndEstado( jb.getRx(), "FAX" );

        String colSurte = "";

        if ( !tracks.isEmpty() ) {
            colSurte = jb.getSurte();
        }else {
            // Si existe en JB_TRACK.estado = FAX, agregar una "F" en JB.surte
            colSurte = "F" + jb.getSurte();
        }

        //No incluir en ticket si es Fax con surte Pino
        if ( colSurte.trim().equals("FP") )
            return;


        if ( jb.getRoto() != null && jb.getRoto() > 0 ) {
            ti.imprimirString( TAM_COL_RX, "R" + jb.getRx() );
        } else {
            ti.imprimirString( TAM_COL_RX, jb.getRx() );
        }

        ti.imprimirString( TAM_COL_CODIGO, jb.getMaterial() );

        // Si existe en JB_TRACK.estado = FAX, agregar una "F" en
        // JB.surte
        ti.imprimirString( TAM_COL_SURTE, colSurte );

        ti.saltoLinea();
    }

	private void imprimirDevolucion( DoctoInv doctoInv, ImpresoraTM88 ti ) throws IOException, DAOException {
		ti.imprimirString( TAM_COL_ID_DOC, doctoInv.getIdDocto() );
		ti.imprimirString( TAM_COL_CANTIDAD, doctoInv.getCantidad() );
	}

	private void imprimirJbDev( JbDev jbDev, ImpresoraTM88 ti ) throws IOException, DAOException {
		ti.imprimirString( doctoLabel2, jbDev.getDocumento() );
		String idSucursal = jbDev.getSucursal();
		Sucursal sucursalSolicitante = sucursalDAO.findById( Integer.parseInt( idSucursal.trim() ) );

        if ( sucursalSolicitante != null ) {
			ti.imprimirString( sucursalSolicitaLabel2, sucursalSolicitante.getNombre() );
		} else {
			ti.imprimirString( sucursalSolicitaLabel2, "" );
		}

        ti.imprimirString( apartadoLabel2, jbDev.getApartado() );

        if ( jbDev.getIdSobre() != null )
            ti.imprimirString( folioSobreLabel, jbDev.getIdSobre().toString() );
        else
            ti.imprimirString( folioSobreLabel, "" );
	}

	private void imprimirTrabajoConRxCodigo( Jb jb, ImpresoraTM88 ti ) throws IOException, DAOException {
		// - Si JB.roto > 0, anteponer una "R" a JB.rx
		if ( jb.getRoto() != null && jb.getRoto() > 0 ) {
			ti.imprimirString( TAM_COL_RX, "R" + jb.getRx() );
		} else {
			ti.imprimirString( TAM_COL_RX, jb.getRx() );
		}

		ti.imprimirString( TAM_COL_CODIGO, jb.getMaterial() );
	}


    private void imprimirTrabajoConRxCodigoHist( JbViajeDet jb, ImpresoraTM88 ti ) throws IOException, DAOException {
        // - Si JB.roto > 0, anteponer una "R" a JB.rx
        if ( jb.getRoto() != null && jb.getRoto() > 0 ) {
            ti.imprimirString( TAM_COL_RX, "R" + jb.getRx() );
        } else {
            ti.imprimirString( TAM_COL_RX, jb.getRx() );
        }

        ti.imprimirString( TAM_COL_CODIGO, jb.getMaterial() );
    }

	private void imprimirTrabajoExterno( Jb jb, ImpresoraTM88 ti ) throws IOException, DAOException {

		List<JbTrack> tracks = trackDAO.findJbTrackByRxAndEstado( jb.getRx(), "X1" );
		JbTrack tmp = null;
		for ( JbTrack track : tracks ) {
			if ( tmp == null ) {
				tmp = track;
			} else if ( track.getFecha().after( tmp.getFecha() ) ) {
				tmp = track;
			}
		}

		if ( tmp != null ) {
			ti.imprimirString( sucursalDestinoLabel, tmp.getObservaciones() );
		} else {
			ti.imprimirString( sucursalDestinoLabel, "--" );
		}
		ti.imprimirString( sobreLabel2, jb.getCaja() );
		ti.imprimirString( facturaLabel, jb.getRx() );
	}


    private void imprimirTrabajoExternoHist( JbViajeDet jb, ImpresoraTM88 ti ) throws IOException, DAOException {

        List<JbTrack> tracks = trackDAO.findJbTrackByRxAndEstado( jb.getRx(), "X1" );
        JbTrack tmp = null;
        for ( JbTrack track : tracks ) {
            if ( tmp == null ) {
                tmp = track;
            } else if ( track.getFecha().after( tmp.getFecha() ) ) {
                tmp = track;
            }
        }

        if ( tmp != null ) {
            ti.imprimirString( sucursalDestinoLabel, tmp.getObservaciones() );
        } else {
            ti.imprimirString( sucursalDestinoLabel, "--" );
        }
        ti.imprimirString( sobreLabel2, trabajoDAO.findById( jb.getRx() ).getCaja() );
        ti.imprimirString( facturaLabel, jb.getRx() );
    }

	private void imprimirSobre( JbSobre sobre, Empleado empleado, ImpresoraTM88 ti ) throws IOException {
		ti.imprimirString( sobreLabel, sobre.getFolioSobre() );
		ti.alimentarPapel( 15 );
		ti.imprimirString( deLabel, empleado.getNombreApellidos() + " [" + empleado.getSucursal().getNombre() + "]" );
		ti.alimentarPapel( 15 );
		ti.imprimirString( paraLabel, sobre.getDest() );
		ti.alimentarPapel( 15 );
		ti.imprimirString( areaLabeL, sobre.getArea() );
		ti.alimentarPapel( 15 );
		ti.imprimirString( contenidoLabel, sobre.getContenido() );
	}

	private void imprimirSobreSinRx( JbSobre sobre, ImpresoraTM88 ti ) throws IOException {
		ti.imprimirString( sobreLabel2, sobre.getFolioSobre() );
		ti.imprimirString( paraLabel2, sobre.getDest() );
		ti.imprimirString( areaLabeL2, sobre.getArea() );
		ti.imprimirString( contenidoLabel2, sobre.getContenido() );
        ti.imprimirString( folioSobreLabel, sobre.getId().toString() );
	}

	private void imprimirSobreConRx( JbSobre sobre, ImpresoraTM88 ti ) throws IOException, DAOException {
		// - Si JB.roto > 0, anteponer una "R" a JB.rx
		Jb jb = trabajoDAO.findById( sobre.getRx() );
		if ( jb.getRoto() != null && jb.getRoto() > 0 ) {
			ti.imprimirString( rxLabel2, "R" + sobre.getRx() );
		} else {
			ti.imprimirString( rxLabel2, sobre.getRx() );
		}
		ti.imprimirString( sobreLabel2, sobre.getFolioSobre() );
		ti.imprimirString( paraLabel2, sobre.getDest() );
		ti.imprimirString( areaLabeL2, sobre.getArea() );
		ti.imprimirString( contenidoLabel2, sobre.getContenido() );
        ti.imprimirString( folioSobreLabel, sobre.getId().toString() );
	}

	@Override
	public void imprimirNoSatisfactorio( Jb jb, JbRoto jbRoto, Empleado empleado, Sucursal sucursal, JbSobre jbSobre ) throws ApplicationException {
		Date fecha = new Date();
		String nombreArchivo = "";
		try {
//			nombreArchivo = Long.toString( fecha.getTime() ) + Double.toString( Math.random() );

            File file = createTempFile("ticketSobre");
            nombreArchivo = file.getAbsolutePath().toString();

			imprimirNoSatisfactorioArchivo( fecha, empleado, sucursal, jb, jbRoto, nombreArchivo, jbSobre );
			Cajas cajas = cajasDAO.findCajasById( idCajas );
			String cmd = cajas.getImpTicket() + " " + nombreArchivo;
            log.debug( "Impresion: " + cmd );
			Runtime run = Runtime.getRuntime();
			Process pr = run.exec( cmd );
			pr.waitFor();
		} catch ( IOException ioe ) {
			throw new PrinterException( printerExceptionMessage );
		} catch ( InterruptedException e ) {
			throw new SystemException( e.getMessage(), e );
		}/* finally {
			File f = new File( nombreArchivo );
			if ( f.exists() && f.delete() ) {
				log.debug( "Fichero borrado correctamente" );
			}
		}*/
	}

	public void imprimirNoSatisfactorioArchivo( Date fecha, Empleado empleado, Sucursal sucursal, Jb jb, JbRoto jbRoto, String nombreArchivo, JbSobre jbSobre ) throws ApplicationException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );
		ImpresoraTM88 ti = new ImpresoraTM88( nombreArchivo );

		// Inicializar

        // Codigo Barras
        if ( jbSobre != null ) {

            String idSuc = ApplicationUtils.shiftStringRight(sucursal.idSucursal(), 5, "0");
            String numSobre = ApplicationUtils.shiftStringRight(jbSobre.getId() .toString(), 5, "0");
            String codeBar = "P" + idSuc + numSobre;

            ti.alinear(TipoAlineacion.CENTRO);
            ti.imprimeCodigoBarras(codeBar , TipoAnchuraCodigoBarras.N2, true);
        }

		// Cabecera
        ti.imprimirSeparacion();
		ti.alimentarPapel( 40 );

        ti.alinear( TipoAlineacion.IZQUIERDA );
		ti.establecerTipoImpresion( TipoImpresion.DOBLEALTURA_DOBLEANCHURA_ENFATIZADO, TipoFuente.A, false );
		ti.imprimirString( tituloRoto );
		ti.imprimirString( "      " );
		//ti.imprimirString( Integer.toString(  ) );
        ti.imprimirString( jbRoto.getRx() );
		ti.saltoLinea();
		ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.A, false );
		ti.alinear( TipoAlineacion.IZQUIERDA );
		ti.imprimirSeparacion();

		// Encabezado
		ti.alimentarPapel( 10 );
		ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );

		// Fecha
		ti.alinear( TipoAlineacion.DERECHA );
		ti.imprimirString( sdf.format( fecha ) );
		ti.imprimirString( "    " );
		ti.saltoLinea();

		// Sucursal
		ti.alinear( TipoAlineacion.IZQUIERDA );
		ti.imprimirString( sucursalLabel, sucursal.getNombre() );

		// Fecha Venta
		if ( jb.getFechaVenta() != null ) {
			ti.imprimirString( fechaVentaLabel, sdf.format( jb.getFechaVenta() ) );
		}
		ti.alinear( TipoAlineacion.CENTRO );
		ti.imprimirSeparacionInterna();
		ti.alinear( TipoAlineacion.IZQUIERDA );
		// Material
		ti.alimentarPapel( 20 );
		ti.imprimirString( materialLabel, jbRoto.getMaterial() );
		ti.alinear( TipoAlineacion.CENTRO );
		ti.imprimirSeparacionInterna();
		ti.alinear( TipoAlineacion.IZQUIERDA );
		// Num Roto
		ti.alimentarPapel( 20 );
		ti.imprimirString( numRotoLabel, Integer.toString( jbRoto.getNumRoto() ) );
		// Causa
		ti.imprimirString( causaLabel, jbRoto.getCausa() );
		// Responsable
		ti.imprimirString( responsableLabel, empleado.getNombreApellidos() );
		ti.saltoLinea();
		// Pie
		ti.alimentarPapel( 180 );
		ti.alinear( TipoAlineacion.CENTRO );
		ti.establecerTipoImpresion( TipoImpresion.ENFATIZADO, TipoFuente.B, false );
		ti.imprimirLineaFirma();
		ti.imprimirString( voboGerenteLabel );
		// Finalizar
		ti.alimentarPapel( 200 );
		ti.alimentarPapel( 200 );
		ti.cortarPapel( TipoCorte.CORTE_PARCIAL );
		ti.finalizarImpresion();
	}

	public void imprimirOrdenServicioArchivo( final JbNota jbNota, final Empleado empleado, final Cliente cliente, final Sucursal sucursal, final Date fecha, final String nombreArchivo, final Boolean flag ) throws ApplicationException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );
		ImpresoraTM88 ti = new ImpresoraTM88( nombreArchivo );

        // Codigo Barras
        String idSuc = ApplicationUtils.shiftStringRight(empleado.getSucursal().idSucursal(), 5, "0");
        String numServicio = ApplicationUtils.shiftStringRight( Integer.toString(jbNota.getIdNota()), 5, "0");
        String codeBar = "S" + idSuc + numServicio;

        ti.alinear( TipoAlineacion.CENTRO );
        ti.imprimeCodigoBarras( codeBar, TipoAnchuraCodigoBarras.N2, true );

//        ti.alinear( TipoAlineacion.IZQUIERDA );
        ti.imprimeCodigoBarras( trabajoDAO.getJbServicioServicio(jbNota.getServicio()).getIdServicio().toString(), TipoAnchuraCodigoBarras.N2, true );
        ti.alinear( TipoAlineacion.IZQUIERDA );

        ti.imprimirSeparacion();
		ti.alimentarPapel( 40 );
		ti.alinear( TipoAlineacion.IZQUIERDA );
		ti.establecerTipoImpresion( TipoImpresion.DOBLEALTURA_ENFATIZADO, TipoFuente.A, false );
        if( flag ){
            ti.imprimirString( tituloOrdenServicio + "\t" + PREFIJO_ORDEN_SERVICIO + jbNota.getIdNota() );
        } else {
            ti.imprimirString( tituloMaterialCliente + "\t" + jbNota.getIdNota() );
        }
		ti.saltoLinea();
		ti.alinear( TipoAlineacion.IZQUIERDA );
		ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.A, false );
		ti.imprimirSeparacion();
		ti.alimentarPapel( 10 );
		ti.alinear( TipoAlineacion.DERECHA );
		ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
		ti.imprimirString( "\t" + sdf.format( fecha ) );
		ti.saltoLinea();

		// Sucursal
		ti.alinear( TipoAlineacion.IZQUIERDA );
		ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
		ti.imprimirString( sucursalLabel, sucursal.getNombre() );
		ti.imprimirString( telefonoLabel, sucursal.getTelefonos() );

		// Empleado
		ti.imprimirString( empleadoLabel, empleado.getNombreApellidos() );
		ti.alinear( TipoAlineacion.IZQUIERDA );
		ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.A, false );
		ti.imprimirSeparacion();

		// Cliente
		ti.imprimirString( clienteLabel, cliente.getNombreCompleto( false ) );
		ti.imprimirString( domicilioLabel, cliente.getDireccionCli() );
		ti.imprimirString( coloniaLabel, cliente.getColoniaCli() );
		ti.imprimirString( cpLabel, cliente.getCodigo() );
		ti.imprimirString( telefonoLabel, casaLabel + " " + cliente.getTelCasaCli() + " " + trabajoLabel + " " + cliente.getTelTrabCli() + " " + adicionalLabel + " " + cliente.getTelAdiCli() );

		// Separacion
		ti.alinear( TipoAlineacion.IZQUIERDA );
		ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.A, false );
		ti.imprimirSeparacion();
        if( !flag ){
            ti.imprimirString( facturaLabel, String.valueOf( jbNota.getIdNota() ) );
        }
		ti.imprimirString( dejoLabel, jbNota.getDejo() );
		ti.imprimirString( fechaEntregaLabel, sdf.format( jbNota.getFechaProm() ) );
		ti.imprimirString( servicioLabel, jbNota.getServicio() );
		ti.imprimirString( instruccionesLabel, jbNota.getInstruccion() );

		// Separacion
		ti.alinear( TipoAlineacion.CENTRO );
		ti.imprimirSeparacionInterna();
		ti.alinear( TipoAlineacion.IZQUIERDA );
		ti.alimentarPapel( 20 );

		// Condiciones Generales
		ti.imprimirString( condicionesLabel, jbNota.getCondicion() );

		// Firma Cliente
		ti.alimentarPapel( 180 );
		ti.alinear( TipoAlineacion.CENTRO );
		ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
		ti.imprimirLineaFirma();
		ti.imprimirString( cliente.getNombreCompleto( false ) );
		ti.saltoLinea();

		// Separacion
		ti.alinear( TipoAlineacion.CENTRO );
		ti.imprimirSeparacionInterna();
		ti.alinear( TipoAlineacion.IZQUIERDA );
		ti.alimentarPapel( 20 );

		// Nota
		ti.imprimirString( notaLabel, notaContent );
        ti.alimentarPapel( 15 );
        if( !flag ){
            ti.imprimirString( notaContent1 );
        }
		ti.alimentarPapel( 180 );
		ti.alinear( TipoAlineacion.CENTRO );
		ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
		ti.imprimirLineaFirma();
		ti.imprimirString( cliente.getNombreCompleto( false ) );
		ti.saltoLinea();
		ti.establecerTipoImpresion( TipoImpresion.ENFATIZADO, TipoFuente.B, false );
		ti.imprimirString( ACEPTA_CLIENTE_LABEL );
		ti.saltoLinea();

		// Firma Gerente
		ti.alimentarPapel( 200 );
		ti.alinear( TipoAlineacion.CENTRO );
		ti.establecerTipoImpresion( TipoImpresion.ENFATIZADO, TipoFuente.B, false );
		ti.imprimirLineaFirma();
		ti.imprimirString( voboGerenteLabel );

		// Finalizar
		ti.alimentarPapel( 200 );
		ti.alimentarPapel( 200 );
		ti.cortarPapel( TipoCorte.CORTE_PARCIAL );
		ti.finalizarImpresion();
	}

	@Override
	public void imprimirOrdenServicio( JbNota jbNota, Empleado empleado, Jb jb, Cliente cliente, Sucursal sucursal, Boolean flag ) throws ApplicationException {
		Date fecha = new Date();
		String nombreArchivo = "";
		try {
			nombreArchivo = Long.toString( fecha.getTime() ) + Double.toString( Math.random() );
            imprimirOrdenServicioArchivo( jbNota, empleado, cliente, sucursal, fecha, nombreArchivo, flag );
			Cajas cajas = cajasDAO.findCajasById( idCajas );
			String cmd = cajas.getImpTicket() + " " + nombreArchivo;
			Runtime run = Runtime.getRuntime();
			Process pr = run.exec( cmd );
			pr.waitFor();
		} catch ( IOException ioe ) {
			throw new PrinterException( printerExceptionMessage );
		} catch ( InterruptedException e ) {
			throw new SystemException( e.getMessage(), e );
		} finally {
			File f = new File( nombreArchivo );
			if ( f.exists() && f.delete() ) {
				log.debug( "Fichero borrado correctamente" );
			}
		}
	}



    private void imprimirPackingCerradoArchivoHist( Date fecha, int idViaje, String folioViaje, Empleado empleado, Sucursal sucursal, List<JbViajeDet> trabajosLab, List<JbViajeDet> trabajosRotosExternos, List<JbViajeDet> trabajosRefRepSp, List<JbViajeDet> trabajosGarantias, List<JbViajeDet> trabajosOrdenesServicio, List<JbViajeDet> trabajosExternos, List<JbSobre> sobresVacios, List<JbSobre> sobresNoVacios, List<JbDev> trabajosDevoluciones, List<DoctoInv> doctoInvList, String nombreArchivo ) throws IOException, DAOException {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );
        ImpresoraTM88 ti = new ImpresoraTM88( nombreArchivo );
        // Inicializar
        // Cabecera
        ti.imprimirSeparacion();
        ti.alimentarPapel( 40 );
        ti.alinear( TipoAlineacion.CENTRO );
        ti.establecerTipoImpresion( TipoImpresion.DOBLEALTURA_ENFATIZADO, TipoFuente.A, false );
        ti.imprimirString( tituloPackingCerrado );
        ti.saltoLinea();
        ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.A, false );
        ti.alinear( TipoAlineacion.IZQUIERDA );
        ti.imprimirSeparacion();

        // Encabezado
        ti.alimentarPapel( 10 );
        ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );

        // Fecha
        ti.alinear( TipoAlineacion.DERECHA );
        ti.imprimirString( sdf.format( fecha ) );
        ti.imprimirString( "    " );
        ti.saltoLinea();
        // Sucursal
        ti.alinear( TipoAlineacion.IZQUIERDA );
        ti.imprimirString( sucursalLabel );
        ti.imprimirString( "  " );
        ti.imprimirString( sucursal.getNombre() );
        ti.saltoLinea();
        // Viaje
        ti.imprimirString( viajeLabel );
        ti.imprimirString( "  " );
        ti.imprimirString( Integer.toString( idViaje ) );
        ti.saltoLinea();
        // Folio Viaje
        ti.imprimirString( folioLabel );
        ti.imprimirString( "  " );
        ti.imprimirString( folioViaje );
        ti.saltoLinea();
        // Empleado
        ti.imprimirString( empleadoLabel );
        ti.imprimirString( "  " );
        ti.imprimirString( empleado.getNombreApellidos() );
        ti.saltoLinea();

        // Trabajos
        if ( !trabajosLab.isEmpty() ) {
            ti.alimentarPapel( 40 );
            // Subtitulo
            ti.imprimirSubtitulo( trabajosTitle );
            // Encabezados
            ti.alinear( TipoAlineacion.IZQUIERDA );
            ti.imprimirEncabezado( TAM_COL_RX, titleColumnaRx );
            ti.imprimirEncabezado( TAM_COL_CODIGO, titleColumnaCodigo );
            ti.imprimirEncabezado( TAM_COL_SURTE, titleColumnaSurte );
            ti.saltoLinea();
            // Contenido
            ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
            for ( JbViajeDet jb : trabajosLab ) {
                imprimirTrabajoConRxCodigoSurteHist( jb, ti );
                //ti.saltoLinea();
            }
            ti.saltoLinea();
        }

        // Rotos Externos
        if ( !trabajosRotosExternos.isEmpty() ) {
            ti.alimentarPapel( 40 );
            // Subtitulo
            ti.imprimirSubtitulo( rotosExternosTitle );
            // Encabezados
            ti.alinear( TipoAlineacion.IZQUIERDA );
            ti.imprimirEncabezado( TAM_COL_RX, titleColumnaRx );
            ti.imprimirEncabezado( TAM_COL_CODIGO, titleColumnaCodigo );
            ti.saltoLinea();
            // Contenido
            ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
            for ( JbViajeDet jb : trabajosRotosExternos ) {
                imprimirTrabajoConRxCodigoHist( jb, ti );
                ti.saltoLinea();
            }
            ti.saltoLinea();
        }

        // REFACCIONES/REPARACIONES/SP
        if ( !trabajosRefRepSp.isEmpty() ) {
            ti.alimentarPapel( 40 );
            // Subtitulo
            ti.imprimirSubtitulo( refRepSpTitle );
            // Encabezados
            ti.alinear( TipoAlineacion.IZQUIERDA );
            ti.imprimirEncabezado( TAM_COL_RX, titleColumnaRx );
            ti.imprimirEncabezado( TAM_COL_CODIGO, titleColumnaCodigo );
            ti.saltoLinea();
            // Contenido
            ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
            for ( JbViajeDet jb : trabajosRefRepSp ) {
                imprimirTrabajoConRxCodigoHist( jb, ti );
                ti.saltoLinea();
            }
            ti.saltoLinea();
        }

        // Garantias
        if ( !trabajosGarantias.isEmpty() ) {
            ti.alimentarPapel( 40 );
            // Subtitulo
            ti.imprimirSubtitulo( garantiasTitle );
            // Encabezados
            ti.alinear( TipoAlineacion.IZQUIERDA );
            ti.imprimirEncabezado( TAM_COL_RX, titleColumnaRx );
            ti.imprimirEncabezado( TAM_COL_CODIGO, titleColumnaCodigo );
            ti.saltoLinea();
            // Contenido
            ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
            for ( JbViajeDet jb : trabajosGarantias ) {
                imprimirTrabajoConRxCodigoHist( jb, ti );
                ti.saltoLinea();
            }
            ti.saltoLinea();
        }

        // Orden de Servicio
        if ( !trabajosOrdenesServicio.isEmpty() ) {
            ti.alimentarPapel( 40 );
            // Subtitulo
            ti.imprimirSubtitulo( ordenServicioTitle );
            // Encabezados
            ti.alinear( TipoAlineacion.IZQUIERDA );
            ti.imprimirEncabezado( TAM_COL_RX, titleColumnaRx );
            ti.imprimirEncabezado( TAM_COL_CODIGO, titleColumnaCodigo );
            ti.saltoLinea();
            // Contenido
            ti.establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
            for ( JbViajeDet jb : trabajosOrdenesServicio ) {
                imprimirTrabajoConRxCodigoHist( jb, ti );
                ti.saltoLinea();
            }
            ti.saltoLinea();
        }

        // Trabajos Externos
        if ( !trabajosExternos.isEmpty() ) {
            ti.alimentarPapel( 40 );
            // Subtitulo
            ti.imprimirSubtitulo( trabajosExternosTitle );
            // Contenido
            ti.alinear( TipoAlineacion.IZQUIERDA );
            for ( JbViajeDet jb : trabajosExternos ) {
                imprimirTrabajoExternoHist( jb, ti );
                ti.saltoLinea();
            }
        }

        // Devoluciones
        if ( !doctoInvList.isEmpty() ) {
            ti.alimentarPapel( 40 );
            // Subtitulo
            ti.imprimirSubtitulo( devolucionesTitle );
            // Encabezados
            ti.alinear( TipoAlineacion.IZQUIERDA );
            ti.imprimirEncabezado( TAM_COL_ID_DOC, titleColumnaIdDocto );
            ti.imprimirEncabezado( TAM_COL_CANTIDAD, titleColumnaCantidad );
            ti.saltoLinea();
            // Contenido
            ti.alinear( TipoAlineacion.IZQUIERDA );
            for ( DoctoInv doctoInv : doctoInvList ) {
                imprimirDevolucion( doctoInv, ti );
                ti.saltoLinea();
            }
        }

        // Sobres
        if ( !sobresVacios.isEmpty() ) {
            ti.alimentarPapel( 40 );
            // Subtitulo
            ti.imprimirSubtitulo( sobresTitle );
            // Contenido
            ti.alinear( TipoAlineacion.IZQUIERDA );
            for ( JbSobre sobre : sobresVacios ) {
                imprimirSobreSinRx( sobre, ti );
                ti.saltoLinea();
            }
        }

        // Sobres Rx
        if ( !sobresNoVacios.isEmpty() ) {
            ti.alimentarPapel( 40 );
            // Subtitulo
            ti.imprimirSubtitulo( rxSobresTitle );
            // Contenido
            ti.alinear( TipoAlineacion.IZQUIERDA );
            for ( JbSobre sobre : sobresNoVacios ) {
                imprimirSobreConRx( sobre, ti );
                ti.saltoLinea();
            }
        }

        // Dev Armazones SP
        if ( !trabajosDevoluciones.isEmpty() ) {
            ti.alimentarPapel( 40 );
            // Subtitulo
            ti.imprimirSubtitulo( devArmazonesTitle );
            // Contenido
            ti.alinear( TipoAlineacion.IZQUIERDA );
            for ( JbDev jbDev : trabajosDevoluciones ) {
                imprimirJbDev( jbDev, ti );
                ti.saltoLinea();
            }
        }

        // Finalizar
        ti.alimentarPapel( 200 );
        ti.alimentarPapel( 200 );
        ti.cortarPapel( TipoCorte.CORTE_PARCIAL );
        ti.finalizarImpresion();
    }

    private File createTempFile (String tipo){

        File file = null;

        try {
            String nombreArchivo = tipo + Long.toString( new Date().getTime() ) + Double.toString( Math.random() );
            file = File.createTempFile( nombreArchivo, null );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getAbsoluteFile();
    }
}

package mx.com.lux.control.trabajos.bussiness.service.impl;

import mx.com.lux.control.trabajos.bussiness.service.ReposicionService;
import mx.com.lux.control.trabajos.data.dao.EmpleadoDAO;
import mx.com.lux.control.trabajos.data.dao.ExternoDAO;
import mx.com.lux.control.trabajos.data.dao.JbGarantiaDAO;
import mx.com.lux.control.trabajos.data.dao.NotaVentaDAO;
import mx.com.lux.control.trabajos.data.dao.ReposicionDAO;
import mx.com.lux.control.trabajos.data.dao.envio.SucursalDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.RecetaDAO;
import mx.com.lux.control.trabajos.data.dao.trabajo.TrabajoDAO;
import mx.com.lux.control.trabajos.data.vo.CausaReposicion;
import mx.com.lux.control.trabajos.data.vo.DetalleReposicion;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbExterno;
import mx.com.lux.control.trabajos.data.vo.JbGarantia;
import mx.com.lux.control.trabajos.data.vo.MaterialAcabado;
import mx.com.lux.control.trabajos.data.vo.NotaVenta;
import mx.com.lux.control.trabajos.data.vo.Prisma;
import mx.com.lux.control.trabajos.data.vo.Receta;
import mx.com.lux.control.trabajos.data.vo.Reposicion;
import mx.com.lux.control.trabajos.data.vo.ResponsableReposicion;
import mx.com.lux.control.trabajos.data.vo.Sucursal;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.exception.DAOException;
import mx.com.lux.control.trabajos.exception.ServiceException;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.EstadoTrabajo;
import mx.com.lux.control.trabajos.view.Session;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service( "reposicionService" )
public class ReposicionServiceImpl implements ReposicionService {

	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	@Resource
	private ReposicionDAO reposicionDAO;

	@Resource
	private TrabajoDAO trabajoDAO;

	@Resource
	private EmpleadoDAO empleadoDAO;

	@Resource
	private RecetaDAO recetaDAO;

	@Resource
	private JbGarantiaDAO jbGarantiaDAO;

	@Resource
	private ExternoDAO externoDAO;

	@Resource
	private SucursalDAO sucursalDAO;

	@Resource
	private NotaVentaDAO notaVentaDAO;

	@Override
	public List<Jb> buscarTrabajosReposicionesPorClientePorRx( String rx, String cliente ) {
		try {
			List<String> rxs = new ArrayList<String>();
			if ( StringUtils.isNotBlank( rx ) ) {
				rxs.add( rx );
			} else {
				rxs.addAll( reposicionDAO.obtenerTodosRxConReposicion() );
			}
			return trabajoDAO.obtenerDetalleTrabajosPorRxsPorCliente( rxs, cliente, new EstadoTrabajo[]{ EstadoTrabajo.CANCELADO, EstadoTrabajo.ENTREGADO } );
		} catch ( Exception e ) {
			log.error( "Error al obtener Reposiciones: Rx " + rx );
			log.error( "Error al obtener Reposiciones: Cliente " + cliente );
			log.error( "Error al obtener Reposiciones: " + e.getMessage() );
		}
		return new ArrayList<Jb>();
	}

	@Override
	public List<Reposicion> buscarReposicionesAsociadasAlTrabajo( String rx ) {
		if ( StringUtils.isNotBlank( rx ) ) {
			return reposicionDAO.buscarPorRx( rx );
		}
		return new ArrayList<Reposicion>();
	}

	@Override
	public Reposicion obtenerReposicionPorRxYNumeroOrden( String rx, Integer numeroOrden ) {
		if ( StringUtils.isNotBlank( rx ) && numeroOrden != null ) {
			return reposicionDAO.obtenerPorRxYNumeroOrden( rx, numeroOrden );
		}
		return null;
	}

	@Override
	public Empleado buscarEmpleadoPorId( String idEmpleado ) {
		try {
			if( StringUtils.isNotBlank( idEmpleado ) ) {
				return empleadoDAO.obtenEmpleado( idEmpleado );
			}
		} catch ( Exception e ) {
			log.error( "Error al obtener Empleado: id: " + idEmpleado );
		}
		return null;
	}

	@Override
	public List<ResponsableReposicion> obtenerTodosResponsableReposicion() {
		return reposicionDAO.obtenerTodosResponsableReposicion();
	}

	@Override
	public List<Prisma> obtenerTodosPrisma() {
		return reposicionDAO.obtenerTodosPrisma();
	}

	@Override
	public List<CausaReposicion> obtenerTodosCausaReposicion() {
		return reposicionDAO.obtenerTodosCausaReposicion();
	}

	@Override
	public List<DetalleReposicion> buscarDetalleReposicionAsociadasAlTrabajo( String rx ) {
		try {
			if( StringUtils.isNotBlank( rx ) ) {
				return reposicionDAO.obtenerDetalleReposicionPorRx( rx );
			}
		} catch( Exception e ) {
			log.error( "Error al obtener detalle de reposicion : rx : " + rx + " : " + e.getMessage() );
		}
		return new ArrayList<DetalleReposicion>();
	}

	@Override
	public List<DetalleReposicion> buscarDetalleReposicionAsociadasAReposicion( String rx, Integer numeroOrden ) {
		try {
			if( StringUtils.isNotBlank( rx ) && numeroOrden != null ) {
				return reposicionDAO.obtenerDetalleReposicionPorRxYNumeroOrden( rx, numeroOrden );
			}
		} catch( Exception e ) {
			log.error( "Error al obtener detalle de reposicion : rx : " + rx + " : " + e.getMessage() );
		}
		return new ArrayList<DetalleReposicion>();
	}

	@Override
	public Reposicion procesarReposicion( Receta recetaAnt, Receta receta, Reposicion reposicion, String rx, Boolean ojoIzquierdoSeleccionado, Boolean ojoDerechoSeleccionado ) throws ApplicationException {
	    Jb trabajo = trabajoDAO.findById( rx );
		// Calculamos el numero de reposicion
		List<Reposicion> reposiciones = reposicionDAO.buscarPorRx( rx );
		int numeroReposicion = 1;
		for( Reposicion reposicionTmp : reposiciones ) {
			if( reposicionTmp.getNumeroOrden() > numeroReposicion ) {
				numeroReposicion = reposicionTmp.getNumeroOrden();
			}
		}
        if( reposiciones.size() > 0 ){
            reposicion.setNumeroOrden( numeroReposicion + 1 );
        } else {
            reposicion.setNumeroOrden( numeroReposicion);
        }

		// Calculamos el folio
		if( trabajo.esExterno() ) {
			reposicion.setFolio( StringUtils.remove( trabajo.getRx(), "X" ) + numeroReposicion );
		} else if( trabajo.esGarantia() ) {
			reposicion.setFolio( "G" + trabajo.getRx() + numeroReposicion );
		} else {
			reposicion.setFolio( "R" + trabajo.getRx() + numeroReposicion );
		}
		// Calculamos la sucursal
		Sucursal sucursal = obtenerSucursalReposicion( rx );
		reposicion.setIdSucursal( "" + sucursal.getIdSucursal() );
		// Calculamos el Ojo
		reposicion.setOjo( calcularOjo( rx, reposicion, ojoIzquierdoSeleccionado, ojoDerechoSeleccionado ) );

		reposicionDAO.save( reposicion );
        //recetaDAO.delete( recetaAnt );
        recetaDAO.mergeAndSave( receta );


		// calculamos las diferencias entre la receta y la nueva reposicion
		List<DetalleReposicion> detalleReposiciones = calcularDetalleReposicion( rx, reposicion );
		for( DetalleReposicion detalleReposicion : detalleReposiciones ) {
			reposicionDAO.save( detalleReposicion );
		}

		return reposicion;
	}

	@Override
	public void procesarReposicionSegundaParte( String rx, Integer numeroOrden ) throws ApplicationException {
		ejecutarProcesoShellReposicion( rx, numeroOrden );
	}

	private void ejecutarProcesoShellReposicion( final String factura, final Integer numeroOrden ) throws ServiceException {
		try {
			String cmd = "./reposicion.sh -f " + factura + " -o " + numeroOrden;
			Runtime run = Runtime.getRuntime();
			Process pr = run.exec( cmd );
			pr.waitFor();
		} catch ( Exception e ) {
			log.error( "Error al ejecutar shell de la reposicion: " + e.getMessage() );
			throw new ServiceException( "Error al procesar la reposicion: " + e.getMessage(), e );
		}
	}

	@Override
	public void imprimirTicketReposicion( final String factura, final Integer numeroOrden ) throws ServiceException {
		ejecutarShellImprimirReposicion( factura, numeroOrden );
	}

	private void ejecutarShellImprimirReposicion( final String factura, final Integer numeroOrden ) throws ServiceException {
		try {
			String cmd = "./reposicion.sh -f " + factura + " -o " + numeroOrden + " -r 1";
			Runtime run = Runtime.getRuntime();
			Process pr = run.exec( cmd );
			pr.waitFor();
		} catch ( Exception e ) {
			log.error( "Error al ejecutar shell de la reposicion: " + e.getMessage() );
			throw new ServiceException( "Error al procesar la reposicion: " + e.getMessage(), e );
		}
	}

	private Character calcularOjo( String rx, Reposicion reposicion, Boolean ojoIzquierdoSeleccionado, Boolean ojoDerechoSeleccionado ) {
		Receta receta = obtenerRecetaReposicion( rx );
		if( receta != null ) {
			if( haCambiadoAlgoQueObligueParOjos( receta, reposicion ) ) {
				return 'P';
			} else if( ojoIzquierdoSeleccionado && ojoDerechoSeleccionado ) {
				return 'P';
			} else if( ojoIzquierdoSeleccionado ) {
				return 'L';
			} else if( ojoDerechoSeleccionado ) {
				return 'R';
			}
		}
		return null;
	}

	private List<DetalleReposicion> calcularDetalleReposicion( String factura, Reposicion reposicion ) {
		List<DetalleReposicion> detalles = new ArrayList<DetalleReposicion>();
		Receta receta = obtenerRecetaReposicion( factura );
		Integer numeroOrden = reposicion.getNumeroOrden();
		String idSucursal = reposicion.getIdSucursal();
		if( receta != null ) {
			if( sonDiferentes( reposicion.getOdEsf(), receta.getOdEsfR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "D", "ESF", receta.getOdEsfR(), reposicion.getOdEsf(), "od_esf" ) );
			}
			if( sonDiferentes( reposicion.getOdCil(), receta.getOdCilR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "D", "CIL", receta.getOdCilR(), reposicion.getOdCil(), "od_cil" ) );
			}
			if(sonDiferentes( reposicion.getOdEje(), receta.getOdEjeR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "D", "EJE", receta.getOdEjeR(), reposicion.getOdEje(), "od_eje" ) );
			}
			if( sonDiferentes( reposicion.getOdAdc(), receta.getOdAdcR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "D", "ADC", receta.getOdAdcR(), reposicion.getOdAdc(), "od_adc" ) );
			}
			if( sonDiferentes( reposicion.getOdAdi(), receta.getOdAdiR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "D", "ADI", receta.getOdAdiR(), reposicion.getOdAdi(), "od_adi" ) );
			}
			if( sonDiferentes( reposicion.getOdAv(), receta.getOdAvR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "D", "AV", receta.getOdAvR(), reposicion.getOdAv(), "od_av" ) );
			}
			if( sonDiferentes( reposicion.getOdPrisma(), receta.getOdPrismaH() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "D", "PRISMA_H", receta.getOdPrismaH(), reposicion.getOdPrisma(), "od_prisma_h" ) );
			}
			if( sonDiferentes( reposicion.getOdPrismaV(), receta.getOdPrismaV() ) && ( reposicion.getOdPrismaV() != null && !receta.getOdPrismaV().equals( "" ) ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "D", "PRISMA_V", receta.getOdPrismaV(), reposicion.getOdPrismaV(), "od_prisma_v" ) );
			}

			if( sonDiferentes( reposicion.getOiEsf(), receta.getOiEsfR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "I", "ESF", receta.getOiEsfR(), reposicion.getOiEsf(), "oi_esf" ) );
			}
			if( sonDiferentes( reposicion.getOiCil(), receta.getOiCilR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "I", "CIL", receta.getOiCilR(), reposicion.getOiCil(), "oi_cil" ) );
			}
			if( sonDiferentes( reposicion.getOiEje(), receta.getOiEjeR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "I", "EJE", receta.getOiEjeR(), reposicion.getOiEje(), "oi_eje" ) );
			}
			if( sonDiferentes( reposicion.getOiAdc(), receta.getOiAdcR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "I", "ADC", receta.getOiAdcR(), reposicion.getOiAdc(), "oi_adc" ) );
			}
			if( sonDiferentes( reposicion.getOiAdi(), receta.getOiAdiR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "I", "ADI", receta.getOiAdiR(), reposicion.getOiAdi(), "oi_adi" ) );
			}
			if( sonDiferentes( reposicion.getOiAv(), receta.getOiAvR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "I", "AV", receta.getOiAvR(), reposicion.getOiAv(), "oi_av" ) );
			}
			if( sonDiferentes( reposicion.getOiPrisma(), receta.getOiPrismaH() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "I", "PRISMA_H", receta.getOiPrismaH(), reposicion.getOiPrisma(), "oi_prisma_h" ) );
			}
			if( sonDiferentes( reposicion.getOiPrismaV(), receta.getOiPrismaV() ) && ( reposicion.getOdPrismaV() != null && !receta.getOdPrismaV().equals( "" ) ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "I", "PRISMA_V", receta.getOiPrismaV(), reposicion.getOiPrismaV(), "oi_prisma_v" ) );
			}

			if( sonDiferentes( reposicion.getDiLejos(), receta.getDiLejosR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "", "DI_LEJOS", receta.getDiLejosR(), reposicion.getDiLejos(), "di_lejos" ) );
			}
			if( sonDiferentes( reposicion.getDiCerca(), receta.getDiCercaR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "", "DI_CERCA", receta.getDiCercaR(), reposicion.getDiCerca(), "di_cerca" ) );
			}
			if( sonDiferentes( reposicion.getAltObl(), receta.getAltOblR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "", "ALT_OBL", receta.getAltOblR(), reposicion.getAltObl(), "alt_obl" ) );
			}
			if( !StringUtils.equalsIgnoreCase( reposicion.getObservaciones(), receta.getObservacionesR() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "", "OBS", receta.getObservacionesR(), reposicion.getObservaciones(), "observaciones" ) );
			}

			MaterialAcabado materialAcabado = new MaterialAcabado( reposicion.getMaterial(), reposicion.getTratamientos() );
			MaterialAcabado materialAcabadoAnterior = new MaterialAcabado( receta.getMaterialArm(), receta.getTratamientos() );
			if( materialAcabado.getMaterial() != materialAcabadoAnterior.getMaterial() ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "", "material", materialAcabadoAnterior.getMaterial().name(), materialAcabado.getMaterial().name(), "" ) );
			}
			if( materialAcabado.getAcabado() != materialAcabadoAnterior.getAcabado() ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "", "acabado", materialAcabadoAnterior.getAcabado().name(), materialAcabado.getAcabado().name(), "" ) );
			}
			if( sonDiferentes( materialAcabado.getForma(), materialAcabadoAnterior.getForma() ) ) {
				detalles.add( new DetalleReposicion( factura, numeroOrden, idSucursal, "", "forma", materialAcabadoAnterior.getForma(), materialAcabado.getForma(), "" ) );
			}
			return detalles;
		}
		return new ArrayList<DetalleReposicion>();
	}

	private boolean sonDiferentes( final String a, final String b ) {
		String uno = StringUtils.isBlank( a ) ? "" : a;
		String dos = StringUtils.isBlank( b ) ? "" : b;

		Character signoUno = uno.contains( "-" ) ? '-' : '+';
		Character signoDos = dos.contains( "-" ) ? '-' : '+';

		if( !signoUno.equals( signoDos ) ) {
			return true;
		} else {
			uno = StringUtils.remove( uno, signoUno ).trim();
			dos = StringUtils.remove( dos, signoDos ).trim();
			return !uno.equals( dos );
		}
	}

	private boolean haCambiadoAlgoQueObligueParOjos( Receta receta, Reposicion reposicion ) {
		if( !receta.getAltOblR().trim().equalsIgnoreCase( reposicion.getAltObl().trim() ) ) {
			return true;
		} else if( !receta.getOdAdiR().equalsIgnoreCase( reposicion.getOdAdi().trim() ) ) {
			return true;
		} else if( !receta.getOdAdcR().equalsIgnoreCase( reposicion.getOdAdc().trim() ) ) {
			return true;
		} else if( !receta.getOiAdiR().equalsIgnoreCase( reposicion.getOiAdi().trim() ) ) {
			return true;
		} else if( !receta.getOiAdcR().equalsIgnoreCase( reposicion.getOiAdc().trim() ) ) {
			return true;
		}
		return false;
	}

	@Override
	public Receta obtenerRecetaReposicion( String rx ) {
		try {
			if( StringUtils.isNotBlank( rx ) ) {
				Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
				Jb trabajo = trabajoDAO.findById( rx );
				if( trabajo.esExterno() ) {
					JbExterno externo = externoDAO.obtenerPorRx( rx );
					Sucursal sucursal = sucursalDAO.obtenerPorNombre( externo.getOrigen() );
					String tipoOpt = sucursal.getIdSucursal() + ":" +  StringUtils.remove( externo.getFactura(), "X" ) ;
					return recetaDAO.obtenerRecetaPorTipoOpt( tipoOpt );
				} else if( trabajo.esLaboratorio() ) {
					return recetaDAO.obtenerRecetaPorTrabajo( rx );
				} else if( trabajo.esGarantia() ) {
					// JbGarantia garantia = jbGarantiaDAO.obtenerPorFactura( rx );
					JbGarantia garantia = jbGarantiaDAO.obtenerPorId( Integer.parseInt( StringUtils.remove( rx, "G" ) ) );
					String idSucursal = garantia.getSucursal().trim();
					String idSucursalLocal = "" + empleado.getSucursal().getIdSucursal();
					if( idSucursal.equals( idSucursalLocal ) ) {
						NotaVenta notaVenta = notaVentaDAO.obtenNotaVentaPorTrabajo( garantia.getFactura() );
						return recetaDAO.findById( notaVenta.getReceta() );
					} else {
						String tipoOpt = idSucursal + ":" + garantia.getFactura();
						return recetaDAO.obtenerRecetaPorTipoOpt( tipoOpt );
					}
				}
			}
		} catch( Exception e ) {
			log.error( "Error al obtener la Receta Rx: " + rx );
		}
		return null;
	}

	@Override
	public Sucursal obtenerSucursalReposicion( String rx ) {
		try {
			if( StringUtils.isNotBlank( rx ) ) {
				Jb trabajo = trabajoDAO.findById( rx );
				if( trabajo.esExterno() ) {
					JbExterno externo = externoDAO.obtenerPorRx( rx );
					return sucursalDAO.obtenerPorNombre( externo.getOrigen() );
				} else if( trabajo.esLaboratorio() ) {
					Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
					return empleado.getSucursal();
				} else if( trabajo.esGarantia() ) {
					JbGarantia garantia = jbGarantiaDAO.obtenerPorId( Integer.parseInt( StringUtils.remove( rx, "G" ) ) );
					return sucursalDAO.findById( Integer.parseInt( garantia.getSucursal().trim() ) );
				}
			}
		} catch( Exception e ) {
			log.error( "Error al obtener la Sucursal Rx: " + rx );
		}
		return null;
	}


    @Override
    public Reposicion obtenerReposicionPorFactura( String factura ){

        Reposicion repo = reposicionDAO.obtenerReposicionPorFactura( factura );

        return repo;
    }

}


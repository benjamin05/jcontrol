package mx.com.lux.control.trabajos.view.container.menu;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.ClienteService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.RxConstants;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.contacto.GrupoDataDialog;
import mx.com.lux.control.trabajos.view.container.contacto.GruposDataDialog;
import mx.com.lux.control.trabajos.view.container.reposicion.ReposicionDialog;
import mx.com.lux.control.trabajos.view.container.trabajos.ConsultaTrabajoDialog;
import mx.com.lux.control.trabajos.view.container.trabajos.DatosClienteDialog;
import mx.com.lux.control.trabajos.view.container.trabajos.DatosFacturaDialog;
import mx.com.lux.control.trabajos.view.container.trabajos.DatosFacturaExternoDialog;
import mx.com.lux.control.trabajos.view.container.trabajos.Desretener;
import mx.com.lux.control.trabajos.view.container.trabajos.Desvincular;
import mx.com.lux.control.trabajos.view.container.trabajos.InfoPinoDialog;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.container.trabajos.NoContactarDialog;
import mx.com.lux.control.trabajos.view.container.trabajos.ReprogramarDialog;
import mx.com.lux.control.trabajos.view.container.trabajos.RetenidosDialog;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class OpcionesMenu {

	private static final TrabajoService trabajoService;
	private static final ClienteService clienteService;

	static {
		trabajoService = ServiceLocator.getBean( TrabajoService.class );
		clienteService = ServiceLocator.getBean( ClienteService.class );
	}

	public static void crearOpcionConsultarTrabajo( final Shell shell, final Menu menu ) {
		MenuItem mnItemConsultaTrabajo = new MenuItem( menu, SWT.NONE );
		mnItemConsultaTrabajo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.consultar.trabajo" ) );
		mnItemConsultaTrabajo.setImage( ControlTrabajosWindowElements.popupWindowDatosTrabajoIcon );
		mnItemConsultaTrabajo.setEnabled( true );
		mnItemConsultaTrabajo.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				ConsultaTrabajoDialog consultaTrabajoDialog = new ConsultaTrabajoDialog( shell );
				consultaTrabajoDialog.open();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	public static void crearOpcionInformacionLaboratorio( final Shell shell, final Menu menu ) {
		MenuItem mnItemInfoLab = new MenuItem( menu, SWT.NONE );
		mnItemInfoLab.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.info.laboratorio" ) );
		mnItemInfoLab.setImage( ControlTrabajosWindowElements.popupWindowInfoPinoIcon );
		mnItemInfoLab.setEnabled( true );
		mnItemInfoLab.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				InfoPinoDialog consultaRxDialog = new InfoPinoDialog( shell );
				consultaRxDialog.open();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	public static void crearOpcionDatosCliente( final Shell shell, final Menu menu ) {
		MenuItem mnItemDatosCliente = new MenuItem( menu, SWT.NONE );
		mnItemDatosCliente.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.datos.cliente" ) );
		mnItemDatosCliente.setImage( ControlTrabajosWindowElements.popupWindowDatosClienteIcon );
		mnItemDatosCliente.setEnabled( true );

		mnItemDatosCliente.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_EDIT_CLIENTE );
				Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_EDIT_CLIENTE, "false" );
				DatosClienteDialog datosClienteDialog = new DatosClienteDialog( shell );
				datosClienteDialog.open();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	public static void crearOpcionDatosFactura( final Shell shell, final Menu menu ) {
		MenuItem mnItemNuevo = new MenuItem( menu, SWT.NONE );
		mnItemNuevo.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.datosfactura" ) );
		mnItemNuevo.setImage( ControlTrabajosWindowElements.popupWindowFacturaIcon );
		mnItemNuevo.setEnabled( true );

		mnItemNuevo.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				String rx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
				try {
					Jb trabajo = trabajoService.findById( rx );
					if ( "EXT".equalsIgnoreCase( trabajo.getJbTipo().trim() ) ) {
						DatosFacturaExternoDialog dialog = new DatosFacturaExternoDialog( shell );
						dialog.open();
					} else {
						DatosFacturaDialog dialog = new DatosFacturaDialog( shell );
						dialog.open();
					}
				} catch ( Exception e ) {
					Status status = new Status( IStatus.ERROR, "JSOI", 0, "Error al cargar datos de Factura", null );
					ErrorDialog.openError( shell, "Error", "Error", status );
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	public static MenuItem crearOpcionRetener( final Shell shell, final Menu menu ) {
		MenuItem opcion = new MenuItem( menu, SWT.NONE );
		opcion.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.retenido" ) );
		opcion.setImage( ControlTrabajosWindowElements.popupWindowRetenerIcon );
		opcion.setEnabled( true );

		opcion.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				RetenidosDialog retenidosDataDialog = new RetenidosDialog( shell );
				retenidosDataDialog.open();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );

		return opcion;
	}

	public static void validarOpcionRetener( MenuItem opcion, String rx ) {
		Jb trabajo = trabajoService.findById( rx );
		opcion.setEnabled( trabajoService.validateRetenerTrabajo( trabajo.estado() ) );
	}

	public static MenuItem crearOpcionDesretener( final Shell shell, final Menu menu ) {
		MenuItem opcion = new MenuItem( menu, SWT.NONE );
		opcion.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.desretener" ) );
		opcion.setImage( ControlTrabajosWindowElements.popupWindowDesretenerIcon );
		opcion.setEnabled( true );
		opcion.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				Desretener desretener = new Desretener( shell );
				desretener.action();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
		return opcion;
	}

	public static void validarOpcionDesretener( MenuItem opcion, String rx ) {
		Jb trabajo = trabajoService.findById( rx );
		opcion.setEnabled( trabajoService.validateDesretenerTrabajo( trabajo ) );
	}

	public static void crearOpcionReprogramar( final Shell shell, final Menu menu ) {
		MenuItem mnItemReprogramar = new MenuItem( menu, SWT.NONE );
		mnItemReprogramar.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.reprogramar" ) );
		mnItemReprogramar.setImage( ControlTrabajosWindowElements.popupWindowReprogramarIcon );
		mnItemReprogramar.setEnabled( true );
		mnItemReprogramar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				ReprogramarDialog reprogramarDialog = new ReprogramarDialog( shell );
				reprogramarDialog.open();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	public static void crearOpcionNoContactar( final Shell shell, final Menu menu ) {
		MenuItem mnItemNoContactar = new MenuItem( menu, SWT.NONE );
		mnItemNoContactar.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.contactar.no" ) );
		mnItemNoContactar.setImage( ControlTrabajosWindowElements.popupWindowNoContactarIcon );
		mnItemNoContactar.setEnabled( true );
		mnItemNoContactar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				if ( MessageDialog.openQuestion( shell, MessagesPropertyHelper.getProperty( "contactar.no.title" ), MessagesPropertyHelper.getProperty( "contactar.no.message.01" ) ) ) {
					NoContactarDialog noContactarDialog = new NoContactarDialog( shell );
					noContactarDialog.open();
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	public static MenuItem crearOpcionCrearGrupo( final Shell shell, final Menu menu ) {
		MenuItem opcion = new MenuItem( menu, SWT.NONE );
		opcion.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.grupo" ) );
		opcion.setImage( ControlTrabajosWindowElements.popupWindowCreateGroupIcon );
		opcion.setEnabled( true );
		opcion.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				String rxId = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
				if ( rxId.startsWith( RxConstants.TIPO_GRUPO_F ) ) {
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_GRUPO, rxId );
					GrupoDataDialog grupoDataDialog = new GrupoDataDialog( shell );
					grupoDataDialog.open();
				} else {
					Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_SECOND_POPUP );
					GruposDataDialog gruposDataDialog = new GruposDataDialog( shell );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_SECOND_POPUP, gruposDataDialog );
					gruposDataDialog.open();
				}

			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
		return opcion;
	}

	public static void validarOpcionCrearGrupo( MenuItem opcion, String rx ) {
		Jb trabajo = trabajoService.findById( rx );
		opcion.setEnabled( trabajoService.validarAgrupar( trabajo ) );
	}

	public static MenuItem crearOpcionDesvincular( final Shell shell, final Menu menu ) {
		MenuItem opcion = new MenuItem( menu, SWT.NONE );
		opcion.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.desvincular" ) );
		opcion.setImage( ControlTrabajosWindowElements.popupWindowDesvincularIcon );
		opcion.setEnabled( true );

		opcion.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				Desvincular desvincular = new Desvincular();
				desvincular.action();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
		return opcion;
	}

	public static void validarOpcionDesvincular( MenuItem opcion, String rx ) {
		Jb trabajo = trabajoService.findById( rx );
		opcion.setEnabled( trabajoService.validarDesagrupar( trabajo ) );
	}

	public static void crearOpcionNuevaReposicion( final Shell shell, final Menu menu ) {
		MenuItem menuItem = new MenuItem( menu, SWT.NONE );
		menuItem.setText( "Nueva Reposición" );
		menuItem.setImage( ControlTrabajosWindowElements.popupWindowRecetaIcon );
		menuItem.setEnabled( true );

		menuItem.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				ReposicionDialog dialogo = new ReposicionDialog( shell, null );
				dialogo.open();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );
	}

	public static void crearOpcionAgregarReposicion( final Shell shell, final Menu menu ) {
		MenuItem menuItem = new MenuItem( menu, SWT.NONE );
		menuItem.setText( "Agregar Reposición" );
		menuItem.setImage( ControlTrabajosWindowElements.popupWindowRecetaIcon );
		menuItem.setEnabled( true );

		menuItem.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				String rx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
				ReposicionDialog dialogo = new ReposicionDialog( shell, rx );
				dialogo.open();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );
	}

	public enum Opciones {
		RETENER_TRABAJO,
		DESRETENER_TRABAJO,
		AGRUPAR_TRABAJO,
		DESAGRUPAR_TRABAJO
	}

}

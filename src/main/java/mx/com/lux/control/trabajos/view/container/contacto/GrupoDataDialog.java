package mx.com.lux.control.trabajos.view.container.contacto;

import mx.com.lux.control.trabajos.bussiness.service.contacto.ContactoViewService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.ContactoPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.TableListener;
import mx.com.lux.control.trabajos.view.paging.PagingListener;
import mx.com.lux.control.trabajos.view.paging.contacto.GrupoPagingService;
import mx.com.lux.control.trabajos.view.render.GrupoRender;
import mx.com.lux.control.trabajos.view.util.TableUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class GrupoDataDialog extends Dialog {

	private static ContactoViewService contactoViewService;

	private PagingListener<Jb> lastPagingListener;
	private Table tblGpo;
	private List<Boolean> menuGrupos = new ArrayList<Boolean>();
	private String idGrupo;
	private Integer[] menuOptionDesactive = { Constants.MENU_INDEX_DATOS_RECETA, Constants.MENU_INDEX_CONSULTA_TRABAJO, Constants.MENU_INDEX_INFO_LAB, Constants.MENU_INDEX_DATOS_CLIENTE, Constants.MENU_INDEX_REPROGRAMAR, Constants.MENU_INDEX_NO_CONTACTAR, Constants.MENU_INDEX_RETENER, Constants.MENU_INDEX_DESRETENER, Constants.MENU_INDEX_ENVIAR, Constants.MENU_INDEX_NOENVIAR, Constants.MENU_INDEX_EFAX, Constants.MENU_INDEX_AUTORIZACION, Constants.MENU_INDEX_SOBRE, Constants.MENU_INDEX_DATOS_FACTURA, Constants.MENU_INDEX_CREAR_GRUPO, Constants.MENU_INDEX_AGREGAR_GRUPO, Constants.MENU_INDEX_AGREGAR_RX, Constants.MENU_INDEX_ENTREGAR, Constants.MENU_INDEX_DESENTREGAR, Constants.MENU_INDEX_IMPRIMIR, Constants.MENU_INDEX_BODEGA, Constants.MENU_INDEX_NUEVA_ORDEN };

	static {
		contactoViewService = ServiceLocator.getBean( ContactoViewService.class );
	}

	/**
	 * Create the dialog.
	 *
	 * @param parentShell
	 */
	public GrupoDataDialog( Shell parentShell ) {
		super( parentShell );
	}

	// methods

	/**
	 * Create contents of the dialog.
	 *
	 * @param parent
	 */
	@Override
	protected Control createDialogArea( Composite parent ) {
		idGrupo = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_GRUPO );
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Composite container = (Composite) super.createDialogArea( parent );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImage );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		tblGpo = new Table( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL );
		tblGpo.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				int index = tblGpo.getSelectionIndex();
				if ( index >= 0 ) {
					// Activa Desvincular
					tblGpo.getMenu().getItem( 0 ).setEnabled( true );
					Jb jb = lastPagingListener.getItem( index );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX2, jb.getRx() );
				} else {
					// Desactiva Desvincular
					tblGpo.getMenu().getItem( 0 ).setEnabled( false );
				}
			}
		} );

		tblGpo.setLinesVisible( true );
		tblGpo.setItemCount( 0 );
		tblGpo.setHeaderVisible( true );
		tblGpo.setBounds( 10, 10, 669, 146 );
		tblGpo.addListener( SWT.PaintItem, new TableListener() );

		TableColumn clmRx = new TableColumn( tblGpo, SWT.NONE );
		clmRx.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.rx" ) );
		clmRx.setWidth( 70 );

		TableColumn clmEdo = new TableColumn( tblGpo, SWT.NONE );
		TableUtils.configTableColumn( clmEdo, 80, TrabajosPropertyHelper.getProperty( "trabajos.label.rx.estado" ) );

		TableColumn clmSaldo = new TableColumn( tblGpo, SWT.NONE );
		TableUtils.configTableColumn( clmSaldo, 200, TrabajosPropertyHelper.getProperty( "trabajos.label.cliente" ) );

		TableColumn clmMaterial = new TableColumn( tblGpo, SWT.NONE );
		TableUtils.configTableColumn( clmMaterial, 150, TrabajosPropertyHelper.getProperty( "trabajos.label.material" ) );

		TableColumn clmCliente = new TableColumn( tblGpo, SWT.NONE );
		TableUtils.configTableColumn( clmCliente, 70, TrabajosPropertyHelper.getProperty( "trabajos.label.saldo" ) );

		TableColumn clmFechaVenta = new TableColumn( tblGpo, SWT.NONE );
		TableUtils.configTableColumn( clmFechaVenta, 80, TrabajosPropertyHelper.getProperty( "trabajos.label.fecha.venta" ) );

		menuGrupos = ApplicationUtils.opcionesMenu( menuOptionDesactive );
		MenuInfoTrabajos menuInfoTrabajos = new MenuInfoTrabajos( parent.getShell(), tblGpo, menuGrupos );
		tblGpo.setMenu( menuInfoTrabajos.getMenuInfoTrabajos() );
		loadTable( idGrupo );
		return container;
	}

	public void loadTable() {
		tblGpo.getMenu().getItem( 0 ).setEnabled( false );
		loadTable( idGrupo );
	}

	private void loadTable( String idGrupo ) {
		tblGpo.removeAll();
		if ( lastPagingListener != null )
			tblGpo.removeListener( SWT.SetData, lastPagingListener );
		lastPagingListener = new PagingListener<Jb>( 20, new GrupoPagingService( contactoViewService, idGrupo ), new GrupoRender(), "error servicio from GrupoDataDialog" );
		tblGpo.addListener( SWT.SetData, lastPagingListener );
		tblGpo.setItemCount( lastPagingListener.size() );
		tblGpo.redraw();
	}

	/**
	 * Create contents of the button bar.
	 *
	 * @param parent
	 */

	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Button btnCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		btnCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		btnCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( ContactoPropertyHelper.getProperty( "contacto.grupo.trabajos" ) );
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point( 695, 250 );
	}
}

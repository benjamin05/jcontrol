package mx.com.lux.control.trabajos.view.container.contacto;

import mx.com.lux.control.trabajos.bussiness.service.contacto.ContactoViewService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.RxConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.ContactoPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.TableListener;
import mx.com.lux.control.trabajos.view.paging.PagingListener;
import mx.com.lux.control.trabajos.view.paging.contacto.GruposPagingService;
import mx.com.lux.control.trabajos.view.render.GruposRender;
import mx.com.lux.control.trabajos.view.util.TableUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class GruposDataDialog extends Dialog {

	private final static ContactoViewService contactoViewService;

	private PagingListener<Jb> lastPagingListener;
	private Shell shell;
	private Table tblGpo;
	private Text txtNombreGpo;
	private List<Boolean> menuGrupos = new ArrayList<Boolean>();
	private Integer[] menuOptionDesactive = { Constants.MENU_INDEX_DATOS_RECETA, Constants.MENU_INDEX_CONSULTA_TRABAJO, Constants.MENU_INDEX_INFO_LAB,
            Constants.MENU_INDEX_DATOS_CLIENTE, Constants.MENU_INDEX_REPROGRAMAR, Constants.MENU_INDEX_NO_CONTACTAR, Constants.MENU_INDEX_RETENER,
            Constants.MENU_INDEX_DESRETENER, Constants.MENU_INDEX_ENVIAR, Constants.MENU_INDEX_NOENVIAR, Constants.MENU_INDEX_EFAX, Constants.MENU_INDEX_AUTORIZACION,
            Constants.MENU_INDEX_SOBRE, Constants.MENU_INDEX_DATOS_FACTURA, Constants.MENU_INDEX_DESVINCULAR, Constants.MENU_INDEX_CREAR_GRUPO,
            Constants.MENU_INDEX_NUEVA_ORDEN, Constants.MENU_INDEX_ENTREGAR, Constants.MENU_INDEX_DESENTREGAR, Constants.MENU_INDEX_IMPRIMIR, Constants.MENU_INDEX_BODEGA,
            Constants.MENU_INDEX_GARANTIA_NUEVA, Constants.MENU_INDEX_GARANTIA_ENTREGAR, Constants.MENU_INDEX_GARANTIA_DESENTREGAR, Constants.MENU_INDEX_GARANTIA_IMPRIMIR };

	static {
		contactoViewService = ServiceLocator.getBean( ContactoViewService.class );
	}

	/**
	 * Create the dialog.
	 *
	 * @param parentShell
	 */
	public GruposDataDialog( Shell parentShell ) {
		super( parentShell );
	}

	/**
	 * Create contents of the dialog.
	 *
	 * @param parent
	 */
	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		shell = container.getShell();
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImage );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		VerifyListener upperTxt = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		tblGpo = new Table( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL );
		tblGpo.setLinesVisible( true );
		tblGpo.setItemCount( 0 );
		tblGpo.setHeaderVisible( true );
		tblGpo.setBounds( 10, 72, 674, 184 );
		tblGpo.addListener( SWT.PaintItem, new TableListener() );

		TableColumn clmGpoId = new TableColumn( tblGpo, SWT.NONE );
		TableUtils.configTableColumn( clmGpoId, 60, ContactoPropertyHelper.getProperty( "contacto.grupo.id" ) );

		TableColumn clmEstado = new TableColumn( tblGpo, SWT.NONE );
		TableUtils.configTableColumn( clmEstado, 60, ContactoPropertyHelper.getProperty( "contacto.grupo.estado" ) );

		TableColumn clmGpo = new TableColumn( tblGpo, SWT.NONE );
		TableUtils.configTableColumn( clmGpo, 420, ContactoPropertyHelper.getProperty( "contacto.grupo.nombre" ) );

		TableColumn clmPromesa = new TableColumn( tblGpo, SWT.NONE );
		TableUtils.configTableColumn( clmPromesa, 80, ContactoPropertyHelper.getProperty( "contacto.grupo.fecha.promesa" ) );

		Group grpSearchGpo = new Group( container, SWT.NONE );
		grpSearchGpo.setText( ContactoPropertyHelper.getProperty( "contacto.grupo.buscar.grupo" ) );
		grpSearchGpo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		grpSearchGpo.setBounds( 10, 10, 674, 56 );

		Label lblNombreDelGrupo = new Label( grpSearchGpo, SWT.NONE );
		lblNombreDelGrupo.setBounds( 10, 25, 109, 15 );
		lblNombreDelGrupo.setText( ContactoPropertyHelper.getProperty( "contacto.grupo.nombre.grupo" ) );
		lblNombreDelGrupo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtNombreGpo = new Text( grpSearchGpo, SWT.BORDER );
		txtNombreGpo.setBounds( 135, 20, 427, 25 );

		Button btnBuscar = new Button( grpSearchGpo, SWT.NONE );
		btnBuscar.setBounds( 585, 18, 79, 27 );
		btnBuscar.setText( ContactoPropertyHelper.getProperty( "contacto.grupo.buscar" ) );

		// se agrega menu desplegable
		menuGrupos = ApplicationUtils.opcionesMenu( menuOptionDesactive );
		MenuInfoTrabajos menuInfoTrabajos = new MenuInfoTrabajos( shell, tblGpo, menuGrupos );
		tblGpo.setMenu( menuInfoTrabajos.getMenuInfoTrabajos() );

		tblGpo.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				int index = tblGpo.getSelectionIndex();
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_GRUPO );
				if ( index >= 0 ) {
					Jb jb = lastPagingListener.getItem( index );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_GRUPO, jb.getRx() );
					tblGpo.getMenu().getItem( 1 ).setEnabled( true );
				} else {
					tblGpo.getMenu().getItem( 1 ).setEnabled( false );
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );

		tblGpo.addMouseListener( new MouseListener() {
			@Override
			public void mouseUp( MouseEvent arg0 ) {
			}

			@Override
			public void mouseDown( MouseEvent arg0 ) {
			}

			@Override
			public void mouseDoubleClick( MouseEvent arg0 ) {
				int index = tblGpo.getSelectionIndex();
				if ( index >= 0 ) {
					GrupoDataDialog grupoDataDialog = new GrupoDataDialog( shell );
					Jb jb = lastPagingListener.getItem( index );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_GRUPO, jb.getRx() );
					Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_THIRD_POPUP, grupoDataDialog );
					grupoDataDialog.open();
				}
			}
		} );

		txtNombreGpo.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				if ( arg0.character == SWT.CR ) {
					loadTable();
				}
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		btnBuscar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				if ( StringUtils.isNotBlank( txtNombreGpo.getText() ) ) {
					loadTable();
				} else {
					txtNombreGpo.setText( "" );
					loadTable();
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );

		txtNombreGpo.addVerifyListener( upperTxt );

		loadTable();
		return container;
	}

	// METODOS EXTRA
	public void loadTable() {
		tblGpo.removeAll();
		if ( lastPagingListener != null )
			tblGpo.removeListener( SWT.SetData, lastPagingListener );
		lastPagingListener = new PagingListener<Jb>( 50, new GruposPagingService( contactoViewService, txtNombreGpo.getText().toUpperCase(), RxConstants.TIPO_GRUPO_F ), new GruposRender(), "error servicio GruposDataDialog" );
		tblGpo.addListener( SWT.SetData, lastPagingListener );
		tblGpo.setHeaderVisible( true );
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
		newShell.addDisposeListener( new DisposeListener() {
			public void widgetDisposed( DisposeEvent arg0 ) {
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX2 );
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_THIRD_POPUP );
				Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_SECOND_POPUP );
			}
		} );
		super.configureShell( newShell );
		newShell.setText( ContactoPropertyHelper.getProperty( "contacto.grupo.title" ) );
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point( 702, 350 );
	}
}

package mx.com.lux.control.trabajos.view.container.sobres;

import mx.com.lux.control.trabajos.bussiness.service.sobres.SobreService;
import mx.com.lux.control.trabajos.data.vo.JbSobre;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.SobresPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.TableListener;
import mx.com.lux.control.trabajos.view.paging.PagingListener;
import mx.com.lux.control.trabajos.view.paging.sobres.SobrePagingService;
import mx.com.lux.control.trabajos.view.render.SobreRender;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

public class SobresDialog extends Dialog {
	private Table tblSobres;
	private PagingListener<JbSobre> lastPagingListener;

	public static String SOBRES_DIALOG = "sobresDialog";
	private final static SobreService sobreService;

	public static String ITEM_SELECTED_JBSOBRE = "currentJbSobre";

	static {
		sobreService = ServiceLocator.getBean( SobreService.class );
	}

	public SobresDialog( Shell parentShell ) {
		super( parentShell );
		Session.setAttribute( SobresDialog.SOBRES_DIALOG, this );
	}

	@Override
	protected Control createDialogArea( final Composite parent ) {
		parent.getShell().setText( ApplicationPropertyHelper.getProperty( "button.sobres.label" ) );
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		parent.addDisposeListener( new DisposeListener() {
			public void widgetDisposed( DisposeEvent arg0 ) {
				Session.removeAttribute( SobresDialog.SOBRES_DIALOG );
				Session.removeAttribute( SobresDialog.ITEM_SELECTED_JBSOBRE );
			}
		} );

		Composite container = (Composite) super.createDialogArea( parent );
		container.setBounds( 5, 5, 495, 555 );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		tblSobres = new Table( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL );
		tblSobres.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 1, 1 ) );
		tblSobres.setHeaderVisible( true );
		tblSobres.setLinesVisible( true );
		tblSobres.addListener( SWT.PaintItem, new TableListener() );

		TableColumn tblclmnSobre = new TableColumn( tblSobres, SWT.NONE );
		tblclmnSobre.setWidth( 100 );
		tblclmnSobre.setText( SobresPropertyHelper.getProperty( "sobres.header.sobre" ) );

		TableColumn tblclmnDestinatario = new TableColumn( tblSobres, SWT.NONE );
		tblclmnDestinatario.setWidth( 100 );
		tblclmnDestinatario.setText( SobresPropertyHelper.getProperty( "sobres.header.destinatario" ) );

		TableColumn tblclmnArea = new TableColumn( tblSobres, SWT.NONE );
		tblclmnArea.setWidth( 100 );
		tblclmnArea.setText( SobresPropertyHelper.getProperty( "sobres.header.area" ) );

		TableColumn tblclmnContenido = new TableColumn( tblSobres, SWT.NONE );
		tblclmnContenido.setWidth( 100 );
		tblclmnContenido.setText( SobresPropertyHelper.getProperty( "sobres.header.contenido" ) );

		tblSobres.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}

			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				int index = tblSobres.getSelectionIndex();
				if ( index >= 0 ) {
					JbSobre jbSobre = lastPagingListener.getItem( index );
					Session.setAttribute( SobresDialog.ITEM_SELECTED_JBSOBRE, jbSobre );
					tblSobres.getMenu().getItem( Constants.MENU_SOBRES_INDEX_ELIMINAR ).setEnabled( true );
				}
			}
		} );

		tblSobres.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseDoubleClick( MouseEvent e ) {
				JbSobre sobreSeleccionado = (JbSobre) Session.getAttribute( SobresDialog.ITEM_SELECTED_JBSOBRE );
				if ( sobreSeleccionado != null ) {
					DatosSobreDialog nuevoSobreDialog = new DatosSobreDialog( parent.getShell(), TipoSobre.MODIFICADO );
					nuevoSobreDialog.open();
				}
			}
		} );

		MenuSobres menuSobres = new MenuSobres( parent.getShell(), tblSobres );
		tblSobres.setMenu( menuSobres.getMenuSobres() );

		loadSobres();

		return container;
	}

	protected void loadSobres() {
		tblSobres.removeAll();
		if ( lastPagingListener != null ) {
			tblSobres.removeListener( SWT.SetData, lastPagingListener );
		}
		lastPagingListener = new PagingListener<JbSobre>( 20, new SobrePagingService( sobreService ), new SobreRender(), "error de servicio JbSobre" );
		tblSobres.addListener( SWT.SetData, lastPagingListener );
		tblSobres.setHeaderVisible( true );
		tblSobres.setItemCount( lastPagingListener.size() );
		tblSobres.redraw();
	}

	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Button button = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		button.setImage( ControlTrabajosWindowElements.closeIcon );
		button.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 450, 300 );
	}
}

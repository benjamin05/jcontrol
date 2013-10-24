package mx.com.lux.control.trabajos.view.container.consulta;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoTrackService;
import mx.com.lux.control.trabajos.data.vo.TrackView;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.view.paging.PagingListener;
import mx.com.lux.control.trabajos.view.paging.trabajos.TrackViewPagingService;
import mx.com.lux.control.trabajos.view.render.TrackViewRender;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class ViewDataDialog extends Dialog {

	private static TrabajoTrackService trabajoTrackService;

	private PagingListener<TrackView> lastPagingListener;
	private TrackView trackView;
	private Table table;
	private String rx;

	static {
		trabajoTrackService = ServiceLocator.getBean( TrabajoTrackService.class );
	}

	public ViewDataDialog( Shell parentShell ) {
		super( parentShell );
		setShellStyle( SWT.TITLE );
	}

	public TrackView getTrackView() {
		return trackView;
	}

	public void setTrackView( TrackView trackView ) {
		this.trackView = trackView;
	}

	public String getRx() {
		return rx;
	}

	public void setRx( String rx ) {
		this.rx = rx;
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		Composite container = (Composite) super.createDialogArea( parent );
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 2;

		GridData gridTable = new GridData( SWT.LEFT, SWT.CENTER, false, false, 2, 1 );
		gridTable.heightHint = 180;
		gridTable.widthHint = 588;
		table = new Table( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL );
		table.setLayoutData( gridTable );
		table.setHeaderVisible( true );

		createTableColumn( table, SWT.NONE, 100, "track.grid.rx" );
		createTableColumn( table, SWT.NONE, 100, "track.grid.fecha" );
		createTableColumn( table, SWT.NONE, 100, "track.grid.hora" );
		createTableColumn( table, SWT.NONE, 100, "track.grid.estado" );
		createTableColumn( table, SWT.NONE, 100, "track.grid.observaciones" );
		createTableColumn( table, SWT.NONE, 100, "track.grid.empleado" );

		lastPagingListener = new PagingListener<TrackView>( 20, new TrackViewPagingService( trabajoTrackService, rx ), new TrackViewRender(), "error de servicio jb" );

		table.addListener( SWT.SetData, lastPagingListener );
		table.setItemCount( lastPagingListener.size() );

		table.addListener( SWT.PaintItem, new Listener() {
			@Override
			public void handleEvent( Event event ) {
				int itemIndex = table.indexOf( (TableItem) event.item );
				TableItem tableItem = (TableItem) table.getItem( itemIndex );
				if ( itemIndex % 2 == 0 ) {
					tableItem.setBackground( GraphicConstants.filaParColor );
				} else {
					tableItem.setBackground( GraphicConstants.filaImparColor );
				}
			}
		} );

		return container;
	}

	/**
	 * Create contents of the button bar.
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		Button button = createButton( parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true );
		button.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
			}
		} );
		createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point( 623, 301 );
	}

	private void createTableColumn( Table table, int style, int width, String label ) {
		TableColumn tblclmnNombre = new TableColumn( table, style );
		tblclmnNombre.setWidth( width );
		tblclmnNombre.setText( ApplicationPropertyHelper.getProperty( label ) );
	}
}

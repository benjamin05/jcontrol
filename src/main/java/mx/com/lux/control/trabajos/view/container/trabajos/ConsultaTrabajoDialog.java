package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoTrackService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.TrackView;
import mx.com.lux.control.trabajos.util.DateUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.TableListener;
import mx.com.lux.control.trabajos.view.paging.PagingListener;
import mx.com.lux.control.trabajos.view.paging.trabajos.TrackViewPagingService;
import mx.com.lux.control.trabajos.view.render.TrackViewRender;
import mx.com.lux.control.trabajos.view.util.TableUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class ConsultaTrabajoDialog extends Dialog {

	public static TrabajoTrackService trabajoTrackService;
	public static TrabajoService trabajoService;

	private Jb jb;
	private PagingListener<TrackView> lastPagingListener;
	private Table tblTrack;

	static {
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
		trabajoTrackService = (TrabajoTrackService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_TRACK_SERVICE );
	}

	public ConsultaTrabajoDialog( Shell parentShell ) {
		super( parentShell );
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		String rx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
		try {
			jb = trabajoService.findById( rx );
			if ( jb == null ) {
				this.close();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}

		Composite container = (Composite) super.createDialogArea( parent );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		container.setLayout( new FillLayout() );

		Group grupo = new Group( container, SWT.NONE );
		grupo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout grupoLayout = new GridLayout( 1, false );
		grupoLayout.marginHeight = 10;
		grupoLayout.marginWidth = 10;
		grupo.setLayout( grupoLayout );

		Group datosGrupo = new Group( grupo, SWT.BORDER | SWT.SHADOW_OUT );
		datosGrupo.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		datosGrupo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout datosGrupoLayout = new GridLayout( 4, false );
		datosGrupoLayout.marginWidth = 10;
		datosGrupo.setLayout( datosGrupoLayout );

		Label lblRx = new Label( datosGrupo, SWT.NONE );
		lblRx.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		lblRx.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.rx" ) );
		lblRx.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtRx = new Text( datosGrupo, SWT.BORDER );
		txtRx.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		txtRx.setEditable( false );
		txtRx.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtRx.setText( jb.getRx() );

		Label lblCliente = new Label( datosGrupo, SWT.NONE );
		lblCliente.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		lblCliente.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente" ) );
		lblCliente.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtCliente = new Text( datosGrupo, SWT.BORDER );
		txtCliente.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		txtCliente.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtCliente.setEditable( false );
		txtCliente.setText( ( jb != null && jb.getCliente() != null ) ? jb.getCliente() : Constants.CADENA_VACIA );

		Label lblEstadoRx = new Label( datosGrupo, SWT.NONE );
		lblEstadoRx.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		lblEstadoRx.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.rx.estado" ) );
		lblEstadoRx.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtEstadoRx = new Text( datosGrupo, SWT.BORDER );
		txtEstadoRx.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		txtEstadoRx.setEditable( false );
		txtEstadoRx.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtEstadoRx.setText( jb.getEstado().getDescr() );

		Label lblAtendio = new Label( datosGrupo, SWT.NONE );
		lblAtendio.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		lblAtendio.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.personal.atendio" ) );
		lblAtendio.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtAtendio = new Text( datosGrupo, SWT.BORDER );
		txtAtendio.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		txtAtendio.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtAtendio.setEditable( false );
		txtAtendio.setText( jb.getEmpAtendio() );

		Label lblFechaEstado = new Label( datosGrupo, SWT.NONE );
		lblFechaEstado.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		lblFechaEstado.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.fecha.estado" ) );
		lblFechaEstado.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtFechaEstado = new Text( datosGrupo, SWT.BORDER );
		txtFechaEstado.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		txtFechaEstado.setEditable( false );
		txtFechaEstado.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtFechaEstado.setText( DateUtils.formatDate( jb.getFechaMod(), ApplicationPropertyHelper.getProperty( "app.format.date.date" ) ) );

		Label lblSaldo = new Label( datosGrupo, SWT.NONE );
		lblSaldo.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		lblSaldo.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.saldo" ) );
		lblSaldo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtSaldo = new Text( datosGrupo, SWT.BORDER );
		txtSaldo.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
		txtSaldo.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtSaldo.setEditable( false );
		txtSaldo.setText( trabajoService.obtenSaldoEnTexto( jb.getRx() ) );

		tblTrack = new Table( grupo, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL );
		tblTrack.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
		tblTrack.setLinesVisible( true );
		tblTrack.setHeaderVisible( true );
		tblTrack.addListener( SWT.PaintItem, new TableListener() );

		TableColumn tblClmFechaEstado = new TableColumn( tblTrack, SWT.NONE );
		TableUtils.configTableColumn( tblClmFechaEstado, 50, TrabajosPropertyHelper.getProperty( "trabajos.label.fecha" ) );

		TableColumn tblClmHora = new TableColumn( tblTrack, SWT.NONE );
		TableUtils.configTableColumn( tblClmHora, 50, TrabajosPropertyHelper.getProperty( "trabajos.label.rx.estado.hora" ) );

		TableColumn tblClmEstadoRx = new TableColumn( tblTrack, SWT.NONE );
		TableUtils.configTableColumn( tblClmEstadoRx, 250, TrabajosPropertyHelper.getProperty( "trabajos.label.rx.estado" ) );

		TableColumn tblClmObsEstado = new TableColumn( tblTrack, SWT.NONE );
		TableUtils.configTableColumn( tblClmObsEstado, 250, TrabajosPropertyHelper.getProperty( "trabajos.label.rx.estado.observaciones" ) );

		TableColumn tblClmEmpleado = new TableColumn( tblTrack, SWT.NONE );
		TableUtils.configTableColumn( tblClmEmpleado, 100, TrabajosPropertyHelper.getProperty( "trabajos.label.empleado" ) );

		fillTrackTable( rx );
		return container;
	}

	private void fillTrackTable( String rx ) {
		tblTrack.clearAll();
		if ( lastPagingListener != null ) {
			tblTrack.removeListener( SWT.SetData, lastPagingListener );
		}
		lastPagingListener = new PagingListener<TrackView>( 20, new TrackViewPagingService( trabajoTrackService, rx ), new TrackViewRender(), "error de servicio jb_track" );
		tblTrack.addListener( SWT.SetData, lastPagingListener );
		tblTrack.setItemCount( lastPagingListener.size() );
		tblTrack.redraw();
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 780, 420 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( Constants.RX_DOS_PUNTOS + " " + ( ( jb != null ) ? jb.getRx() : Constants.CADENA_VACIA ) );
	}

	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Button buttonCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		buttonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		buttonCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
	}
}

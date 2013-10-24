package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.data.dto.InfoPinoDTO;
import mx.com.lux.control.trabajos.util.InfoPinoUtil;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.TableListener;
import mx.com.lux.control.trabajos.view.util.TableUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import java.util.ArrayList;
import java.util.List;

public class InfoPinoDialog extends Dialog {

	private List<InfoPinoDTO> infoPinoDTOList;
	private List<InfoPinoDTO> infoPinoDTOSeguimientoList;
	private List<InfoPinoDTO> infoPinoDTOEstacionList;

	private Text txtSeguimiento;
	private Text txtUltimaEstacion;
	private Text txtFechaUltimaEstacion;
	private Text txtRx;
	private Table tblTrack;

	public InfoPinoDialog( Shell parentShell ) {
		super( parentShell );
		String urlString = Constants.INFO_PINO_URL +
				Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX ) +
				Constants.SYMBOL_PIPE +
				Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_SUCURSAL );
		//		infoPinoDTOList = InfoPinoUtil.getInfoPino(urlString);
		infoPinoDTOEstacionList = new ArrayList<InfoPinoDTO>();
		infoPinoDTOSeguimientoList = new ArrayList<InfoPinoDTO>();
		InfoPinoUtil.setInfoPino( urlString, infoPinoDTOSeguimientoList, infoPinoDTOEstacionList );
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		container.setBounds( 5, 5, 495, 555 );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group group = new Group( container, SWT.SHADOW_OUT );
		group.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		group.setBounds( 10, 10, 473, 120 );
		group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblRx = new Label( group, SWT.NONE );
		lblRx.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblRx.setBounds( 10, 10, 120, 20 );
		lblRx.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.rx" ) );
		lblRx.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtSeguimiento = new Text( group, SWT.BORDER );
		txtSeguimiento.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtSeguimiento.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtSeguimiento.setEditable( false );
		txtSeguimiento.setBounds( 160, 36, 300, 20 );
		if ( infoPinoDTOSeguimientoList != null && infoPinoDTOSeguimientoList.size() >= 1 ) {
			InfoPinoDTO dto = infoPinoDTOSeguimientoList.get( 0 );
			txtSeguimiento.setText( dto.getDescSeguimiento() );
		}

		Label lblSeguimiento = new Label( group, SWT.NONE );
		lblSeguimiento.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblSeguimiento.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.track.seguimiento" ) );
		lblSeguimiento.setBounds( 10, 36, 120, 20 );
		lblSeguimiento.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblUltimaEstacion = new Label( group, SWT.NONE );
		lblUltimaEstacion.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblUltimaEstacion.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.track.estacion.ultima" ) );
		lblUltimaEstacion.setBounds( 10, 62, 120, 20 );
		lblUltimaEstacion.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblFechaUltEstacion = new Label( group, SWT.NONE );
		lblFechaUltEstacion.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblFechaUltEstacion.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.track.estacion.ultima.fecha" ) );
		lblFechaUltEstacion.setBounds( 10, 88, 120, 20 );
		lblFechaUltEstacion.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtUltimaEstacion = new Text( group, SWT.BORDER );
		txtUltimaEstacion.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtUltimaEstacion.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtUltimaEstacion.setEditable( false );
		txtUltimaEstacion.setBounds( 160, 62, 300, 20 );

		txtFechaUltimaEstacion = new Text( group, SWT.BORDER );
		txtFechaUltimaEstacion.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtFechaUltimaEstacion.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtFechaUltimaEstacion.setEditable( false );
		txtFechaUltimaEstacion.setBounds( 160, 88, 300, 20 );

		if ( infoPinoDTOEstacionList != null && infoPinoDTOEstacionList.size() >= 1 ) {
			InfoPinoDTO dto = infoPinoDTOEstacionList.get( infoPinoDTOEstacionList.size() - 1 );
			txtUltimaEstacion.setText( dto.getEstacion() );
			txtFechaUltimaEstacion.setText( dto.getFechaEstacion() );
		}
		txtRx = new Text( group, SWT.BORDER );
		txtRx.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtRx.setEditable( false );
		txtRx.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtRx.setBounds( 160, 10, 116, 20 );
		txtRx.setText( (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX ) );

		tblTrack = new Table( container, SWT.BORDER | SWT.FULL_SELECTION );
		tblTrack.setLinesVisible( true );
		tblTrack.setHeaderVisible( true );
		tblTrack.setBounds( 10, 136, 473, 195 );

		TableColumn tblClmHoraEstacion = new TableColumn( tblTrack, SWT.NONE );
		TableUtils.configTableColumn( tblClmHoraEstacion, 100, TrabajosPropertyHelper.getProperty( "trabajos.label.track.estacion.hora" ) );
		TableColumn tblClmFechaEstacion = new TableColumn( tblTrack, SWT.NONE );
		TableUtils.configTableColumn( tblClmFechaEstacion, 100, TrabajosPropertyHelper.getProperty( "trabajos.label.track.estacion.fecha" ) );
		TableColumn tblClmEstacion = new TableColumn( tblTrack, SWT.NONE );
		TableUtils.configTableColumn( tblClmEstacion, 100, TrabajosPropertyHelper.getProperty( "trabajos.label.track.estacion" ) );

		for ( InfoPinoDTO infoPinoDTO : infoPinoDTOEstacionList ) {
			TableItem tableItem = new TableItem( tblTrack, SWT.NONE );
			tableItem.setText( infoPinoDTO.toEstacionArray() );
		}
		tblTrack.addListener( SWT.PaintItem, new TableListener() );

		return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 494, 425 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		//		newShell.setText(TrabajosPropertyHelper.getProperty("trabajos.window.dialog.title.info.pino"));
		newShell.setText( Constants.RX_DOS_PUNTOS + " " +
				(String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX ) );
	}

	/**
	 * Create contents of the button bar.
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Button buttonCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		buttonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		buttonCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
	}
}

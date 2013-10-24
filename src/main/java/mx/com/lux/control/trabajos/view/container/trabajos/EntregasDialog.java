package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.JbLlamada;
import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.*;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import java.sql.Timestamp;

public class EntregasDialog extends Dialog {

	private static TrabajoService trabajoService;

	private Text txtObservaciones;
	private Shell shell;
	private Button buttonGuardar;
	private Text txtRx;
	private boolean entregar;

	static {
		trabajoService = (TrabajoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
	}

	public EntregasDialog( Shell parentShell, boolean entregar ) {
		super( parentShell );
		this.entregar = entregar;
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		final Composite container = (Composite) super.createDialogArea( parent );
		container.setBounds( 5, 5, 495, 555 );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group group = new Group( container, SWT.SHADOW_OUT );
		group.setBounds( 10, 10, 486, 110 );
		group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblObs = new Label( group, SWT.NONE );
		lblObs.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.observaciones" ) );
		lblObs.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblObs.setBounds( 7, 48, 126, 20 );
		lblObs.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtObservaciones = new Text( group, SWT.BORDER );
		txtObservaciones.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtObservaciones.setBounds( 139, 48, 337, 20 );

		VerifyListener upperTxt = new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
			}
		};

		txtObservaciones.addVerifyListener( upperTxt );

		txtRx = new Text( group, SWT.BORDER );
		txtRx.setBounds( 139, 20, 85, 21 );
		txtRx.setEditable( false );
		txtRx.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtRx.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtRx.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblRx = new Label( group, SWT.NONE );
		lblRx.setBounds( 10, 20, 55, 15 );
		lblRx.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.rx" ) );
		lblRx.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblRx.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtObservaciones.addKeyListener( new KeyListener() {
			@Override
			public void keyReleased( KeyEvent arg0 ) {
				buttonGuardar.setEnabled( StringUtils.isNotBlank( txtObservaciones.getText() ) );
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );

		Jb os = (Jb) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ORDEN_SERVICIO );
		txtRx.setText( os.getRx() );
		return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 507, 209 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		this.shell = newShell;
		// muestra Rx: en la barra de titulo
		if ( entregar ) {
			newShell.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.entregar" ) );
		} else {
			newShell.setText( TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.desentregar" ) );
		}
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

		buttonGuardar = createButton( parent, IDialogConstants.YES_ID, IDialogConstants.OK_LABEL, false );
		buttonGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		buttonGuardar.setText( ApplicationPropertyHelper.getProperty( "button.guardar.label" ) );
		buttonGuardar.setEnabled( false );

		buttonGuardar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				save();
				shell.dispose();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}

	private void save() {
		try {
			Jb os = (Jb) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ORDEN_SERVICIO );
			Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
			String observaciones = txtObservaciones.getText().toUpperCase();
			JbTrack track = new JbTrack();
			track.setRx( os.getRx() );
			track.setObservaciones( observaciones );
			track.setEmpleado( empleado.getIdEmpleado() );
			track.setFecha( new Timestamp( System.currentTimeMillis() ) );
			track.setIdMod( Constants.CERO_STRING );
			track.setIdViaje( Constants.CADENA_VACIA );

			if ( entregar ) {
				os.getEstado().setIdEdo( EstadoConstants.ESTADO_ENTREGADO );
				track.setEstado( EstadoTrabajo.ENTREGADO.codigo() );
				JbLlamada llamada = trabajoService.findJbLlamadaById( os.getRx() );

				if ( llamada != null ) {
					trabajoService.delete( llamada );
				}
			} else {
				track.setEstado( EstadoTrabajo.DESENTREGADO.codigo() );
				String lastEstado = trabajoService.findLastEstadoFromJbTrackWhereLlamadaNotNA( os.getRx() );
				os.getEstado().setIdEdo( lastEstado.trim() );
			}
			trabajoService.saveJbAndTrack( os, track );
			ApplicationUtils.recargarDatosPestanyaVisible();
		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
	}
}

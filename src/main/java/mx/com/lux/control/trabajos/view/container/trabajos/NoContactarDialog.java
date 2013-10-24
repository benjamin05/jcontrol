package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.NoContactarService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.JbGrupoService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoTrackService;
import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

public class NoContactarDialog extends Dialog {

	public static TrabajoTrackService trabajoTrackService;
	public static JbGrupoService jbGrupoService;
	public static NoContactarService noContactarService;

	static {
		jbGrupoService = (JbGrupoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_JB_GRUPO );
		trabajoTrackService = (TrabajoTrackService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_TRACK_SERVICE );
		noContactarService = (NoContactarService) ServiceLocator.getBean( "noContactarService" );
	}

	private JbTrack jbTrack;

	private Text txtRazon;
	private String observaciones = "";
	private Button buttonGuardar;
	private Shell shell;

	public NoContactarDialog( Shell parentShell ) {
		super( parentShell );
		shell = parentShell;
		jbTrack = new JbTrack();
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		container.setBounds( 5, 5, 495, 555 );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group group = new Group( container, SWT.SHADOW_OUT );
		group.setBounds( 10, 10, 486, 68 );
		group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblRx = new Label( group, SWT.NONE );
		lblRx.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.rx" ) );
		lblRx.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblRx.setBounds( 10, 10, 50, 20 );
		lblRx.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtRx = new Text( group, SWT.BORDER );
		txtRx.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtRx.setEditable( false );
		txtRx.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtRx.setBounds( 109, 10, 116, 20 );
		txtRx.setText( (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX ) );
		Label lblCliente = new Label( group, SWT.NONE );
		lblCliente.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente" ) );
		lblCliente.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblCliente.setBounds( 10, 36, 90, 20 );
		lblCliente.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Text txtCliente = new Text( group, SWT.BORDER );
		txtCliente.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtCliente.setEditable( false );
		txtCliente.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtCliente.setBounds( 109, 36, 370, 20 );
		String cliente = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_CLIENTE_NOMBRE );
		txtCliente.setText( cliente );
		Group group_1 = new Group( container, SWT.NONE );
		group_1.setBounds( 10, 84, 486, 41 );
		group_1.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblRazon = new Label( group_1, SWT.NONE );
		lblRazon.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.razon" ) );
		lblRazon.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblRazon.setBounds( 10, 10, 50, 20 );
		lblRazon.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtRazon = new Text( group_1, SWT.BORDER );
		txtRazon.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtRazon.setBounds( 109, 10, 370, 20 );
		txtRazon.addKeyListener( new KeyListener() {

			@Override
			public void keyReleased( KeyEvent arg0 ) {
				buttonGuardar.setEnabled( !txtRazon.getText().isEmpty() );
				observaciones = txtRazon.getText();
				jbTrack.setObservaciones( txtRazon.getText() );
			}

			@Override
			public void keyPressed( KeyEvent arg0 ) {
			}
		} );
		return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 507, 228 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( Constants.RX_DOS_PUNTOS + " " + Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX ) );
	}

	@Override
	protected void createButtonsForButtonBar( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Button buttonCerrar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
		buttonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
		buttonCerrar.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );

		buttonGuardar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.OK_LABEL, false );
		buttonGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		buttonGuardar.setText( ApplicationPropertyHelper.getProperty( "button.guardar.label" ) );
		buttonGuardar.setEnabled( false );
		buttonGuardar.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetSelected( SelectionEvent arg0 ) {
				String rx = (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
				try {
					noContactarService.procesarNoContactar( rx, observaciones );
				} catch ( ApplicationException e ) {
					Status status = new Status( IStatus.ERROR, "JSOI", 0, e.getMessage(), null );
					ErrorDialog.openError( shell, "Error", "Error al procesar el trabajo", status );
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent arg0 ) {
			}
		} );
	}
}

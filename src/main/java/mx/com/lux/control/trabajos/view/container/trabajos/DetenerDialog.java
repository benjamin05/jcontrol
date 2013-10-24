package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.JbGrupoService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoTrackService;
import mx.com.lux.control.trabajos.data.vo.JbTrack;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

public class DetenerDialog extends Dialog {

	public static TrabajoTrackService trabajoTrackService;
	public static JbGrupoService jbGrupoService;

	static {
		jbGrupoService = (JbGrupoService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_JB_GRUPO );
		trabajoTrackService = (TrabajoTrackService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_TRACK_SERVICE );
	}

	private JbTrack jbTrack;
	private Text txtRazon;
	private Shell shell;
	private Button buttonGuardar;

	public DetenerDialog( Shell parentShell ) {
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
		group.setBounds( 10, 10, 486, 40 );
		group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		//		txtRx.setText((String) Session.getAttribute(MenuInfoTrabajos.ITEM_SELECTED_ID_JB));

		Label lblRazon = new Label( group, SWT.NONE );
		lblRazon.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.razon" ) );
		lblRazon.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblRazon.setBounds( 10, 10, 90, 20 );
		lblRazon.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtRazon = new Text( group, SWT.BORDER );
		txtRazon.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtRazon.setBounds( 109, 10, 370, 20 );
		//		String cliente = (String) Session.getAttribute(MenuInfoTrabajos.ITEM_SELECTED_CLIENTE_NOMBRE);
		//		txtRazon.setText(cliente);

		return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 507, 145 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		//		newShell.setText(TrabajosPropertyHelper.getProperty("trabajos.window.dialog.title.no.contactar"));
		//		newShell.setText(
		//				TrabajosPropertyHelper.getProperty("trabajos.label.rx.dos.puntos")
		//				+ " " +
		//				(String) Session.getAttribute(MenuInfoTrabajos.ITEM_SELECTED_ID_JB));
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

		buttonGuardar = createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.OK_LABEL, false );
		buttonGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
		buttonGuardar.setText( ApplicationPropertyHelper.getProperty( "button.guardar.label" ) );
		/*buttonGuardar.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(MessageDialog.openConfirm(shell, 
						MessagesPropertyHelper.getProperty("guardar.title"), 
						MessagesPropertyHelper.getProperty("guardar.message.01"))){
					jbTrack.getId().setRx((String) Session.getAttribute(MenuInfoTrabajos.ITEM_SELECTED_ID_JB));
					jbTrack.getId().setEstado(EstadoConstants.ESTADO_NO_CONTACTAR);
					Empleado empleado = (Empleado) Session.getAttribute(Constants.PARAM_USER_LOGGED);
					jbTrack.getId().setEmp(empleado.getIdEmpleado());
					jbTrack.getId().setFecha(new Timestamp(System.currentTimeMillis()));
					jbTrack.getId().setIdMod(empleado.getIdEmpleado());
					jbTrack.getId().setIdViaje(Constants.CADENA_VACIA);
					
					try {
						trabajoTrackService.save(jbTrack);
					} catch (ApplicationException e) {
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});*/
	}
}

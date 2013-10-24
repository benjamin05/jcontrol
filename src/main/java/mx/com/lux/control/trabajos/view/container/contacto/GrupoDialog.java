package mx.com.lux.control.trabajos.view.container.contacto;

import mx.com.lux.control.trabajos.data.vo.ContactoView;
import mx.com.lux.control.trabajos.data.vo.ContactoViewId;
import mx.com.lux.control.trabajos.data.vo.TrackView;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.ContactoPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

public class GrupoDialog extends Dialog {

	private TrackView trackView;
	private String rx;
	private Text txtObs;
	private Text txtCliente;
	private Text txtMaterial;
	private Text txtSaldo;
	private Text txtCon;
	private Text txtContactos;
	private ContactoViewId cid = new ContactoViewId();

	public GrupoDialog( Shell parentShell ) {
		super( parentShell );
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
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		// GridLayout gridLayout = (GridLayout) container.getLayout();
		// gridLayout.numColumns = 2;
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImage );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Group group_1 = new Group( container, SWT.NONE );
		group_1.setBounds( 10, 10, 578, 212 );
		group_1.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Label lblCliente = new Label( group_1, SWT.NONE );
		lblCliente.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente" ) );
		lblCliente.setBounds( 10, 10, 100, 20 );

		txtCliente = new Text( group_1, SWT.BORDER );
		txtCliente.setEditable( false );
		txtCliente.setBackground( SWTResourceManager.getColor( 255, 255, 204 ) );
		txtCliente.setBounds( 146, 10, 413, 20 );

		Label lblSaldo = new Label( group_1, SWT.NONE );
		lblSaldo.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.saldo" ) );
		lblSaldo.setBounds( 10, 88, 100, 20 );

		txtSaldo = new Text( group_1, SWT.BORDER );
		txtSaldo.setEditable( false );
		txtSaldo.setBackground( SWTResourceManager.getColor( 255, 255, 204 ) );
		txtSaldo.setBounds( 146, 88, 217, 20 );

		Label lblCon = new Label( group_1, SWT.NONE );
		lblCon.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.contacto" ) );
		lblCon.setBounds( 10, 36, 132, 20 );

		txtCon = new Text( group_1, SWT.BORDER );
		txtCon.setEditable( false );
		txtCon.setBackground( SWTResourceManager.getColor( 255, 255, 204 ) );
		txtCon.setBounds( 146, 36, 413, 20 );

		Label lblContactos = new Label( group_1, SWT.NONE );
		lblContactos.setText( "Contactos" );
		lblContactos.setBounds( 10, 140, 132, 20 );

		txtContactos = new Text( group_1, SWT.BORDER );
		txtContactos.setEditable( false );
		txtContactos.setBackground( SWTResourceManager.getColor( 255, 255, 204 ) );
		txtContactos.setBounds( 146, 140, 413, 20 );

		Label lblObs = new Label( group_1, SWT.NONE );
		lblObs.setBounds( 10, 62, 132, 20 );
		lblObs.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente.observaciones" ) );

		txtObs = new Text( group_1, SWT.BORDER );
		txtObs.setBounds( 146, 62, 413, 20 );
		txtObs.setBackground( SWTResourceManager.getColor( 255, 255, 204 ) );
		txtObs.setEditable( false );

		Label lblMaterial = new Label( group_1, SWT.NONE );
		lblMaterial.setBounds( 10, 114, 100, 20 );
		lblMaterial.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.material" ) );

		txtMaterial = new Text( group_1, SWT.BORDER );
		txtMaterial.setBounds( 146, 114, 413, 20 );
		txtMaterial.setEditable( false );
		txtMaterial.setBackground( SWTResourceManager.getColor( 255, 255, 204 ) );

		Button btnVerGpo = new Button( group_1, SWT.NONE );
		btnVerGpo.setBounds( 243, 168, 120, 35 );

		btnVerGpo.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
			}
		} );

		btnVerGpo.setText( "Ver Grupo" );
		btnVerGpo.setImage( ControlTrabajosWindowElements.aceptarIcon );
		ContactoView currentContactoView = new ContactoView();
		currentContactoView = (ContactoView) Session.getAttribute( "currentContactoView" );

		if ( currentContactoView != null ) {
			// cid = currentContactoView.getId();
			loadData();
		}

		return container;
	}

	private void loadData() {
		txtCliente.setText( cid.getCliente() );
		txtMaterial.setText( cid.getMaterial() );
		txtSaldo.setText( cid.getSaldo() );
		txtCon.setText( cid.getContactoForma() );
		txtObs.setText( cid.getObsForma() );
		txtContactos.setText( cid.getNumLlamadaJbLlamada().toString() );
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
		newShell.setText( ContactoPropertyHelper.getProperty( "contacto.grupo.data.title" ) );
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point( 602, 310 );
	}
}

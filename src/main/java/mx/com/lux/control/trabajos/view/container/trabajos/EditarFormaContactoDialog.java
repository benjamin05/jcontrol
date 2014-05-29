package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.ClienteService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.render.TipoFormaContactoRender;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.Date;

public class EditarFormaContactoDialog extends Dialog {

	private final Logger log = Logger.getLogger( EditarFormaContactoDialog.class );

	private final TrabajoService trabajoService;
	private final ClienteService clienteService;

	private final Shell shell;
	private ComboViewer tipoContactoViewer;
	private Combo tipoContacto;
	private Text contacto;
	private Text observaciones;
	private final String rx;
	private Cliente cliente;

	protected EditarFormaContactoDialog( final Shell parentShell, final String rx ) {
		super( parentShell );
		this.shell = parentShell;
		trabajoService = ServiceLocator.getBean( TrabajoService.class );
		clienteService = ServiceLocator.getBean( ClienteService.class );
		this.rx = rx;
		try {
			Jb trabajo = trabajoService.findById( rx );
			cliente = clienteService.findClienteById( Integer.parseInt( trabajo.getIdCliente() ) );
		} catch ( ApplicationException e ) {
			log.error( "Error al cargar los datos" );
			MessageDialog.openError( shell, "Error", "Error al cargar los datos" );
		}
	}

	@Override
	protected Control createContents( Composite parent ) {
		return super.createContents( parent );
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 370, 290 );
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.getShell().setText( "Editar forma de contacto" );
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Composite contenedor = (Composite) super.createDialogArea( parent );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout layout = new GridLayout( 2, false );
		layout.marginWidth = 20;
		layout.marginHeight = 20;
		contenedor.setLayout( layout );

		dibujarCampos( contenedor );

		cargarFormaContacto();

		return contenedor;
	}

	private void dibujarCampos( Composite contenedor ) {

		Label etiquetaTipoContacto = new Label( contenedor, SWT.NONE );
		etiquetaTipoContacto.setFont( new Font( null, "Arial", 12, SWT.NORMAL ) );
		etiquetaTipoContacto.setText( "Tipo: " );
		etiquetaTipoContacto.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		tipoContactoViewer = new ComboViewer( contenedor, SWT.READ_ONLY );
		tipoContactoViewer.setContentProvider( new ArrayContentProvider() );
		tipoContactoViewer.setLabelProvider( new TipoFormaContactoRender() );
		tipoContactoViewer.setInput( trabajoService.obtenerTiposFormaContacto() );
		tipoContacto = tipoContactoViewer.getCombo();
		tipoContacto.setVisibleItemCount( 5 );
		GridData gd0 = new GridData();
		gd0.widthHint = 200;
		tipoContacto.setLayoutData( gd0 );
		tipoContactoViewer.addSelectionChangedListener( new ISelectionChangedListener() {
			@Override
			public void selectionChanged( SelectionChangedEvent arg0 ) {
				if ( !arg0.getSelection().isEmpty() ) {
					haCambiadoSeleccionTipoContacto();
				}
			}
		} );

		Label etiquetaContacto = new Label( contenedor, SWT.NONE );
		etiquetaContacto.setFont( new Font( null, "Arial", 12, SWT.NORMAL ) );
		etiquetaContacto.setText( "Contacto: " );
		etiquetaContacto.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		contacto = new Text( contenedor, SWT.BORDER );
		GridData gd1 = new GridData();
		gd1.widthHint = 190;
		contacto.setLayoutData( gd1 );

		Label etiquetaObservaciones = new Label( contenedor, SWT.NONE );
		etiquetaObservaciones.setFont( new Font( null, "Arial", 12, SWT.NORMAL ) );
		etiquetaObservaciones.setText( "Observaciones: " );
		etiquetaObservaciones.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		observaciones = new Text( contenedor, SWT.BORDER | SWT.MULTI | SWT.WRAP );
		GridData gd2 = new GridData();
		gd2.widthHint = 195;
		gd2.heightHint = 100;
		observaciones.setLayoutData( gd2 );

	}

	@Override
	protected void createButtonsForButtonBar( final Composite parent ) {
		super.createButtonsForButtonBar( parent );

		Button botonOk = getButton( IDialogConstants.OK_ID );
		botonOk.setImage( ControlTrabajosWindowElements.saveIcon );
		botonOk.setText( ApplicationPropertyHelper.getProperty( "button.guardar.label" ) );
		setButtonLayoutData( botonOk );

		Button botonCancel = getButton( IDialogConstants.CANCEL_ID );
		botonCancel.setText( ApplicationPropertyHelper.getProperty( "button.cerrar.label" ) );
		botonCancel.setImage( ControlTrabajosWindowElements.closeIcon );
		setButtonLayoutData( botonCancel );

		botonOk.getParent().setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
	}

	private void haCambiadoSeleccionTipoContacto() {
		contacto.setFocus();
		TipoContacto seleccion = (TipoContacto) tipoContactoViewer.getElementAt( tipoContacto.getSelectionIndex() );
		if ( seleccion != null && seleccion.getIdTipoContacto() == 1 ) {
			// Correo electronico
			String email = cliente.getEmailCli();
			if ( StringUtils.isNotBlank( email ) && ApplicationUtils.isEmail( email ) ) {
				contacto.setText( email );
			} else {
				contacto.setText( "" );
			}
		} else if( seleccion != null && ( seleccion.getIdTipoContacto() == 2 || seleccion.getIdTipoContacto() == 3 ) ) {
			// SMS
			if( StringUtils.isNotBlank( cliente.getTelAdiCli() ) ) {
				contacto.setText( cliente.getTelAdiCli() );
			} else {
				contacto.setText( "" );
			}

		} else if( seleccion != null && seleccion.getIdTipoContacto() == 4 ) {
			// Telefono
			if( StringUtils.isNotBlank( cliente.getTelCasaCli() ) ) {
				contacto.setText( cliente.getTelCasaCli() );
			} else if( StringUtils.isNotBlank( cliente.getTelTrabCli() ) ) {
				contacto.setText( cliente.getTelTrabCli() );
			} else {
				contacto.setText( "" );
			}
		}
	}

	private void cargarFormaContacto() {
		FormaContacto formaContacto = trabajoService.obtenerFormaTrabajoPorRx( rx );
		if ( formaContacto != null ) {
			// Boolean tieneFormaContacto = true;
			contacto.setText( formaContacto.getContacto() );
			observaciones.setText( StringUtils.trimToEmpty(formaContacto.getObservaciones()) );
			for ( int i = 0; i < tipoContacto.getItems().length; i++ ) {
				String combo = tipoContacto.getItem( i );
				if ( combo.equalsIgnoreCase( formaContacto.getTipoContacto().getDescripcion().trim() ) ) {
					tipoContacto.select( i );
				}
			}
		}
	}

	private Boolean esFormaContactoValida() {
		if ( tipoContacto.getSelectionIndex() < 0 ) {
			tipoContacto.setFocus();
			MessageDialog.openError( shell, "Error", "Debe seleccionar un Tipo de Contacto" );
			return false;
		}
		if ( StringUtils.isBlank( contacto.getText() ) ) {
			contacto.setFocus();
			MessageDialog.openError( shell, "Error", "Debe introducir un dato de contacto" );
			return false;
		}
		TipoContacto seleccion = (TipoContacto) tipoContactoViewer.getElementAt( tipoContacto.getSelectionIndex() );
		if ( seleccion != null && seleccion.getIdTipoContacto() == 1 ) {
			// Tipo de contacto 1
			String email = contacto.getText();
			if ( StringUtils.isNotBlank( email ) && !ApplicationUtils.isEmail( email ) ) {
				contacto.setFocus();
				MessageDialog.openError( shell, "Error", "El campo contacto no es un email correcto" );
				return false;
			}
		} else if ( seleccion != null && ( seleccion.getIdTipoContacto() == 4 || seleccion.getIdTipoContacto() == 3 || seleccion.getIdTipoContacto() == 2 ) ) {
			if ( !StringUtils.isNumericSpace( contacto.getText() ) ) {
				contacto.setFocus();
				MessageDialog.openError( shell, "Error", "El campo contacto no es un número de teléfono correcto" );
				return false;
			}
		}
		return true;
	}

	@Override
	protected void okPressed() {
		try {
			if ( esFormaContactoValida() ) {
				log.info( "Guardando forma de contacto" );
				Jb trabajo = trabajoService.findById( rx );
				TipoContacto tc = (TipoContacto) tipoContactoViewer.getElementAt( tipoContacto.getSelectionIndex() );
                Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
                Sucursal sucursal = empleado.getSucursal();
				FormaContacto fc = new FormaContacto();
				fc.setRx( rx );
				fc.setIdCliente( trabajo.getIdCliente() != null ? Integer.parseInt( trabajo.getIdCliente() ) : null );
				fc.setObservaciones( observaciones.getText() );
				fc.setTipoContacto( tc );
				fc.setContacto( contacto.getText() );
                fc.setIdSucursal( sucursal.getIdSucursal() );
                fc.setFechaMod( new Date() );
				trabajoService.save( fc );
				/*
				FormaContacto formaContacto = new FormaContacto();
				formaContacto.setTipoContacto( (TipoContacto) tipoContactoViewer.getElementAt( tipoContacto.getSelectionIndex() ) );
				FormaContactoId formaContactoId = new FormaContactoId();
				formaContactoId.setContacto( contacto.getText() );
				formaContactoId.setObservaciones( observaciones.getText() );
				formaContactoId.setIdTipoContacto( formaContacto.getTipoContacto().getIdTipoContacto() );
				formaContactoId.setRx( rx );
				formaContactoId.setIdCliente( Integer.parseInt( trabajo.getIdCliente() ) );
				formaContacto.setId( formaContactoId );
				trabajoService.save( formaContacto );
				*/
				ApplicationUtils.recargarDatosPestanyaVisible();
				super.okPressed();
			} else {
				log.info( "Forma de contacto invalida" );
			}

		} catch ( Exception e ) {
			MessageDialog.openError( shell, "Error", "Error al guardar la forma de contacto" );
		}
	}

}

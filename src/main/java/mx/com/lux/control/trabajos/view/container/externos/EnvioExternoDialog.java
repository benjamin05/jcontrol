package mx.com.lux.control.trabajos.view.container.externos;

import mx.com.lux.control.trabajos.bussiness.service.ExternoService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.SucursalService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Empleado;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.data.vo.Sucursal;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.HashMap;
import java.util.Map;

public class EnvioExternoDialog extends Dialog {

    private static final int INPUTS_W = 250;
    private static final int INPUTS_H = 20;

    private final ExternoService externoService;
    private final TrabajoService trabajoService;
    private final SucursalService sucursalService;
    private final boolean esEdicion;

    private String rx;
    private int origen;
    private int destino;
    private String sobre;
    private String observaciones;
    private Button botonGuardar;
    private String idEmpleado;
    private final Map<String, Sucursal> sucursales = new HashMap<String, Sucursal>();

    public EnvioExternoDialog( Shell parentShell, boolean esEdicion ) {
        super( parentShell );
        this.esEdicion = esEdicion;
        externoService = ( ExternoService ) ServiceLocator.getBean( "externoService" );
        trabajoService = ( TrabajoService ) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_TRABAJO_SERVICE );
        sucursalService = ( SucursalService ) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_SUCURSAL_SERVICE );
    }

    @Override
    protected Control createDialogArea( Composite parent ) {

        parent.getShell().setText( "Envío de externo" );

        rx = ( String ) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
        origen = ( Integer ) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_SUCURSAL );
        Empleado usuario = ( Empleado ) Session.getAttribute( Constants.PARAM_USER_LOGGED );
        idEmpleado = usuario.getIdEmpleado();
        String nombreCliente = ( String ) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_CLIENTE_NOMBRE );
        Jb trabajo = null;

        try {
            trabajo = trabajoService.findById( rx );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        for ( Sucursal sucursal : sucursalService.obtenListaSucursales() ) {
            sucursales.put( sucursal.getNombre(), sucursal );
        }

        VerifyListener mayusculasVerifyListener = new VerifyListener() {
            @Override
            public void verifyText( VerifyEvent ev ) {
                ev.text = ev.text.toUpperCase();
            }
        };

        parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

        Composite container = ( Composite ) super.createDialogArea( parent );
        container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
        container.setLayout( new FillLayout() );

        Group grupo = new Group( container, SWT.BORDER | SWT.SHADOW_OUT );
        grupo.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
        GridLayout layout = new GridLayout( 2, false );
        layout.marginHeight = 15;
        layout.marginWidth = 25;
        grupo.setLayout( layout );

        Label rxLabel = new Label( grupo, SWT.NONE );
        rxLabel.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.rx" ) );

        Label rxValorLabel = new Label( grupo, SWT.NONE );
        rxValorLabel.setText( rx );

        Label clienteLabel = new Label( grupo, SWT.NONE );
        clienteLabel.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente" ) );

        Label clienteValorLabel = new Label( grupo, SWT.NONE );
        clienteValorLabel.setText( nombreCliente );

        Label materialLabel = new Label( grupo, SWT.NONE );
        materialLabel.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.material" ) );

        Label materialValorLabel = new Label( grupo, SWT.NONE );
        materialValorLabel.setText( trabajo != null ? trabajo.getMaterial() : "" );

        Label saldoLabel = new Label( grupo, SWT.NONE );
        saldoLabel.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.saldo" ) );

        final Label saldoValorLabel = new Label( grupo, SWT.NONE );
        saldoValorLabel.setText( trabajoService.obtenSaldoEnTexto( rx ) );

        Label sobreLabel = new Label( grupo, SWT.NONE );
        sobreLabel.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.sobre" ) );

        final Text sobreText = new Text( grupo, SWT.BORDER );
        sobreText.setLayoutData( new GridData( INPUTS_W, INPUTS_H ) );
        sobreText.setEnabled( esEdicion );
        sobreText.setEditable( esEdicion );
        sobreText.addVerifyListener( mayusculasVerifyListener );
        sobreText.addModifyListener( new ModifyListener() {
            @Override
            public void modifyText( ModifyEvent ev ) {
                sobre = sobreText.getText().trim();
                botonGuardar.setEnabled( esGuardarHabilitado() );
            }
        } );

        Label destinoLabel = new Label( grupo, SWT.NONE );
        destinoLabel.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.destino" ) );

        final Combo destinoCombo = new Combo( grupo, SWT.BORDER | SWT.READ_ONLY );
        destinoCombo.setLayoutData( new GridData( INPUTS_W, INPUTS_H ) );
        destinoCombo.setVisibleItemCount( 5 );
        destinoCombo.setItems( sucursales.keySet().toArray( new String[]{ } ) );
        destinoCombo.addModifyListener( new ModifyListener() {
            @Override
            public void modifyText( ModifyEvent ev ) {
                Sucursal sucursal = sucursales.get( destinoCombo.getText() );
                double saldo = ApplicationUtils.moneyToDouble( saldoValorLabel.getText() );
                if ( sucursal != null ) {
                    if ( sucursal.getSears() && saldo > 0.0d ) {
                        destinoCombo.deselectAll();
                        destinoCombo.redraw();
                        MessageDialog.openInformation( getShell(), null, MessagesPropertyHelper.getProperty( "error.externo.envio.saldo-pendiente" ) );
                    } else {
                        destino = sucursal.getIdSucursal();
                        botonGuardar.setEnabled( esGuardarHabilitado() );
                    }
                }
            }
        } );

        Label observacionesLabel = new Label( grupo, SWT.NONE );
        observacionesLabel.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.observaciones" ) );

        final Text observacionesText = new Text( grupo, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL );
        observacionesText.setLayoutData( new GridData( INPUTS_W, 60 ) );
        observacionesText.setEnabled( esEdicion );
        observacionesText.setEditable( esEdicion );
        observacionesText.addVerifyListener( mayusculasVerifyListener );
        observacionesText.addModifyListener( new ModifyListener() {
            @Override
            public void modifyText( ModifyEvent ev ) {
                observaciones = observacionesText.getText().trim();
                botonGuardar.setEnabled( esGuardarHabilitado() );
            }
        } );

        return container;
    }

    @Override
    protected void createButtonsForButtonBar( Composite parent ) {
        parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
        Button botonCerrar = createButton( parent, IDialogConstants.CANCEL_ID, ApplicationPropertyHelper.getProperty( "button.cerrar.label" ), false );
        botonCerrar.setImage( ControlTrabajosWindowElements.closeIcon );
        botonGuardar = createButton( parent, IDialogConstants.OK_ID, ApplicationPropertyHelper.getProperty( "button.guardar.label" ), true );
        botonGuardar.setImage( ControlTrabajosWindowElements.saveIcon );
        botonGuardar.setEnabled( false );
        botonGuardar.setVisible( esEdicion );
        botonGuardar.addSelectionListener( new SelectionListener() {
            @Override
            public void widgetSelected( SelectionEvent arg0 ) {
                if ( esGuardarHabilitado() ) {
                    try {
                        externoService.enviarExterno( rx, origen, destino, sobre, observaciones, idEmpleado );
                    } catch ( ApplicationException e ) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void widgetDefaultSelected( SelectionEvent arg0 ) {
            }
        } );
    }

    @Override
    protected Point getInitialSize() {
        return new Point( 450, 350 );
    }

    private boolean esGuardarHabilitado() {
        return StringUtils.isNotBlank( sobre ) && StringUtils.isNotBlank( observaciones ) && destino > 0;
    }
}

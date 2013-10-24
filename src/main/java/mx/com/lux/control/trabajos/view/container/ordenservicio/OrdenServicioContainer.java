package mx.com.lux.control.trabajos.view.container.ordenservicio;

import mx.com.lux.control.trabajos.bussiness.service.ordenservicio.OrdenServicioService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.Jb;
import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.RecepcionPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import mx.com.lux.control.trabajos.view.listener.TableListener;
import mx.com.lux.control.trabajos.view.paging.PagingListener;
import mx.com.lux.control.trabajos.view.paging.ordenservicio.OrdenServicioBusquedaPagingService;
import mx.com.lux.control.trabajos.view.paging.ordenservicio.OrdenServicioPagingService;
import mx.com.lux.control.trabajos.view.render.OrdenServicioRender;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class OrdenServicioContainer {

    private final OrdenServicioService ordenServicioService;
    private final TrabajoService trabajoService;

    private Text txtOrden;
    private Text txtCliente;
    private Table table;
    private PagingListener<Jb> lastPagingListener;
    private final ApplicationWindow applicationWindow;
    private final Integer[] menuOptionDesactive = {
            Constants.MENU_INDEX_DATOS_RECETA, Constants.MENU_INDEX_DATOS_FACTURA, Constants.MENU_INDEX_AUTORIZACION,
            Constants.MENU_INDEX_AGREGAR_RX, Constants.MENU_INDEX_ENVIAR, Constants.MENU_INDEX_NOENVIAR,
            Constants.MENU_INDEX_AGREGAR_GRUPO, Constants.MENU_INDEX_EFAX, Constants.MENU_INDEX_SOBRE,
            Constants.MENU_INDEX_GARANTIA_NUEVA, Constants.MENU_INDEX_GARANTIA_ENTREGAR,
            Constants.MENU_INDEX_GARANTIA_DESENTREGAR, Constants.MENU_INDEX_GARANTIA_IMPRIMIR,
            Constants.MENU_INDEX_GARANTIA_BODEGA
    };

    public OrdenServicioContainer( ApplicationWindow applicationWindow ) {
        this.applicationWindow = applicationWindow;
        ordenServicioService = ServiceLocator.getBean( OrdenServicioService.class );
        trabajoService = ServiceLocator.getBean( TrabajoService.class );
    }

    public Control createContents( TabFolder tabFolder ) {
        Composite container = new Composite( tabFolder, SWT.NONE );
        container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
        container.setLayout( new GridLayout( 1, false ) );

        Group grpBusqueda = new Group( container, SWT.NONE );
        grpBusqueda.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.busqueda" ) );
        grpBusqueda.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
        grpBusqueda.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );
        GridLayout layout = new GridLayout( 5, false );
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        grpBusqueda.setLayout( layout );

        Label lblOrden = new Label( grpBusqueda, SWT.NONE );
        lblOrden.setText( ApplicationPropertyHelper.getProperty( "orden.servicio.orden" ) );
        lblOrden.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

        txtOrden = new Text( grpBusqueda, SWT.BORDER );
        txtOrden.setLayoutData( new GridData() );
        txtOrden.setTextLimit( 10 );

        txtOrden.addKeyListener( new KeyAdapter() {
            @Override
            public void keyReleased( KeyEvent e ) {
                if ( e.character == SWT.CR ) {
                    buscar();
                }
                if ( txtOrden.getText().length() == 0 && txtCliente.getText().length() == 0 ) {
                    llenarTabla();
                }
            }
        } );

        Label lblCliente = new Label( grpBusqueda, SWT.NONE );
        lblCliente.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.cliente" ) );
        lblCliente.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

        txtCliente = new Text( grpBusqueda, SWT.BORDER );
        txtCliente.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );

        txtCliente.addKeyListener( new KeyAdapter() {
            @Override
            public void keyReleased( KeyEvent e ) {
                if ( e.character == SWT.CR ) {
                    buscar();
                }
                if ( txtOrden.getText().length() == 0 && txtCliente.getText().length() == 0 ) {
                    llenarTabla();
                }
            }
        } );

        VerifyListener upperTxt = new VerifyListener() {
            public void verifyText( VerifyEvent e ) {
                e.text = e.text.toUpperCase();
            }
        };

        txtOrden.addVerifyListener( upperTxt );
        txtCliente.addVerifyListener( upperTxt );

        Button btnBuscar = new Button( grpBusqueda, SWT.NONE );
        btnBuscar.setImage( ControlTrabajosWindowElements.buscarIcon );
        btnBuscar.setText( RecepcionPropertyHelper.getProperty( "recepcion.label.buscar" ) );
        btnBuscar.setLayoutData( new GridData( SWT.CENTER, SWT.CENTER, false, false ) );

        btnBuscar.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                buscar();
            }
        } );
        table = new Table( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL );
        table.setLinesVisible( true );
        table.setHeaderVisible( true );
        table.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
        table.addListener( SWT.PaintItem, new TableListener() );

        table.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                int index = table.getSelectionIndex();
                Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ORDEN_SERVICIO );
                Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX );
                Session.removeAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE );
                System.out.println( index );

                if ( index >= 0 ) {
                    Jb os = lastPagingListener.getItem( index );
                    Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ORDEN_SERVICIO, os );
                    Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX, os.getRx() );
                    Session.setAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_CLIENTE, os.getIdCliente() );
                    table.getMenu().getItem( 0 ).setEnabled( true );
                    table.getMenu().getItem( 1 ).setEnabled( true );
                    table.getMenu().getItem( 2 ).setEnabled( true );
                    // Reprogramar
                    table.getMenu().getItem( 3 ).setEnabled( true );
                    // No contactar
                    table.getMenu().getItem( 4 ).setEnabled( true );
                    // Retener
                    table.getMenu().getItem( 5 ).setEnabled( trabajoService.validateRetenerTrabajo( os.estado() ) );
                    // Desretener
                    table.getMenu().getItem( 6 ).setEnabled( trabajoService.validateDesretenerTrabajo( os ) );
                    // Grupo
                    table.getMenu().getItem( 7 ).setEnabled( trabajoService.validarAgrupar( os ) );
                    // Desvincular
                    table.getMenu().getItem( 8 ).setEnabled( trabajoService.validarDesagrupar( os ) );
                    // Nueva Orden
                    table.getMenu().getItem( 9 ).setEnabled( true );
                    // Entregar
                    // TODO se puede entregar de nuevo cuando ya ha sido entregado?
                    table.getMenu().getItem( 10 ).setEnabled( true );
                    // Desentregar
                    table.getMenu().getItem( 11 ).setEnabled( ordenServicioService.validarDesentregar( os ) );
                    // Imprimir
                    table.getMenu().getItem( 12 ).setEnabled( true );
                    // Bodega
                    table.getMenu().getItem( 13 ).setEnabled( ordenServicioService.validarBodega( os ) );
                } else {
                    table.getMenu().getItem( 0 ).setEnabled( false );
                    table.getMenu().getItem( 1 ).setEnabled( false );
                    table.getMenu().getItem( 2 ).setEnabled( false );
                    table.getMenu().getItem( 3 ).setEnabled( false );
                    table.getMenu().getItem( 4 ).setEnabled( false );
                    table.getMenu().getItem( 5 ).setEnabled( false );
                    table.getMenu().getItem( 6 ).setEnabled( false );
                    table.getMenu().getItem( 7 ).setEnabled( false );
                    table.getMenu().getItem( 8 ).setEnabled( false );
                    table.getMenu().getItem( 9 ).setEnabled( false );
                    table.getMenu().getItem( 10 ).setEnabled( false );
                    table.getMenu().getItem( 11 ).setEnabled( false );
                    table.getMenu().getItem( 12 ).setEnabled( false );
                }
            }
        } );

        table.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseDoubleClick( MouseEvent e ) {
                int index = table.getSelectionIndex();
                if ( index >= 0 ) {
                    new DetallesOrdenServicioDialog( applicationWindow.getShell() ).open();
                }
            }

            @Override
            public void mouseDown( MouseEvent e ) {
                if ( e.button == 2 || e.button == 3 ) {
                    // es el boton derecho del raton
                    int index = table.getSelectionIndex();
                    if ( index < 0 ) {
                        table.getMenu().getItem( 9 ).setEnabled( true );
                    }
                }
            }
        } );

        // se agregan los menus
        MenuInfoTrabajos menuInfoTrabajosPorEnviar = new MenuInfoTrabajos( applicationWindow.getShell(), table, ApplicationUtils.opcionesMenu( menuOptionDesactive ) );
        table.setMenu( menuInfoTrabajosPorEnviar.getMenuInfoTrabajos() );

        List<Object[]> titles = new ArrayList<Object[]>();
        titles.add( new Object[]{ ApplicationPropertyHelper.getProperty( "orden.servicio.orden" ), 100 } );
        titles.add( new Object[]{ TrabajosPropertyHelper.getProperty( "trabajos.label.cliente" ), 430 } );
        titles.add( new Object[]{ ApplicationPropertyHelper.getProperty( "orden.servicio.fecha.orden" ), 100 } );
        titles.add( new Object[]{ TrabajosPropertyHelper.getProperty( "trabajos.label.fecha.promesa" ), 100 } );

        for ( Object[] title : titles ) {
            TableColumn column = new TableColumn( table, SWT.NONE );
            column.setText( ( String ) title[ 0 ] );
            column.setWidth( ( Integer ) title[ 1 ] );
        }

        llenarTabla();
        return container;
    }

    public void llenarTabla() {
        table.removeAll();
        if ( lastPagingListener != null ) {
            table.removeListener( SWT.SetData, lastPagingListener );
        }
        lastPagingListener = new PagingListener<Jb>( 100, new OrdenServicioPagingService( ordenServicioService, txtOrden.getText(), txtCliente.getText() ), new OrdenServicioRender(), "error servicio OrdenServicio" );
        table.addListener( SWT.SetData, lastPagingListener );
        table.setItemCount( lastPagingListener.size() );
        table.redraw();
    }

    private void buscar() {
        if ( txtOrden.getText().length() == 0 && txtCliente.getText().length() == 0 ) {
            llenarTabla();
            return;
        }
        table.removeAll();
        if ( lastPagingListener != null ) {
            table.removeListener( SWT.SetData, lastPagingListener );
        }
        lastPagingListener = new PagingListener<Jb>( 100, new OrdenServicioBusquedaPagingService( ordenServicioService, txtOrden.getText(), txtCliente.getText() ), new OrdenServicioRender(), "error servicio OrdenServicio" );
        table.addListener( SWT.SetData, lastPagingListener );
        table.setItemCount( lastPagingListener.size() );
        table.redraw();
    }
}
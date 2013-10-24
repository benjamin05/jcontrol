package mx.com.lux.control.trabajos.view.controltrabajos;


import mx.com.lux.control.trabajos.util.properties.*;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

public class ControlTrabajosWindowElements {

    public static Image applicationIcon;
    public static Image backgroundImage;
    public static Image backgroundImagePlate;
    public static Image logInIcon;
    public static Image buscarIcon;
    public static Image infoIcon;
    public static Image limpiarIcon;
    public static Image exitIcon;
    public static Image saveIcon;
    public static Image closeIcon;
    public static Image aceptarIcon;
    public static Image cancelarIcon;
    public static Image calendarIcon;
    public static Image rightIcon;
    public static Image leftIcon;
    public static Image printIcon;
    public static Image sobresIcon;
    public static Image popupWindowRecetaIcon;
    public static Image popupWindowDatosTrabajoIcon;
    public static Image popupWindowDatosClienteIcon;
    public static Image popupWindowReprogramarIcon;
    public static Image popupWindowReprogramarGrupoIcon;
    public static Image popupWindowInfoPinoIcon;
    public static Image popupWindowNoContactarIcon;
    public static Image popupWindowSolicitarAutorizacionIcon;
    public static Image popupWindowRetenerIcon;
    public static Image popupWindowDesretenerIcon;
    public static Image popupWindowAutorizacionIcon;
    public static Image popupWindowNuevoIcon;
    public static Image popupWindowEliminarIcon;
    public static Image popupWindowEnviarIcon;
    public static Image popupWindowNoEnviarIcon;
    public static Image popupWindowEfaxIcon;
    public static Image popupWindowFacturaIcon;
    public static Image popupWindowDesvincularIcon;
    public static Image popupWindowCreateGroupIcon;
    public static Image popupWindowAddGroupIcon;
    public static Image popupWindowAddRx;
    public static Image popupWindowNuevaOrden;
    public static Image popupWindowEntregar;
    public static Image popupWindowDesentregar;
    public static Image popupWindowImprimir;
    public static Image popupWindowBodega;

    public static Image iconoAgregar;
    public static Image iconoEditar;
    public static Image iconoGuardar;
    public static Image iconoCancelar;
    public static Image iconoEnviarExterno;
    public static Image iconoEliminarExterno;
    public static Image iconoEntregarExterno;

    public static void loadEnvironment( ApplicationWindow applicationWindow ) {
        if ( EnvironmentPropertyHelper.loadFile( ConfigurationPropertyHelper.getProperty( "directory" ) ) ) {
            applicationIcon = new Image( Display.getCurrent(), EnvironmentPropertyHelper.getProperty( "image.logo" ) );
            backgroundImage = new Image( Display.getCurrent(), EnvironmentPropertyHelper.getProperty( "image.background" ) );
            backgroundImagePlate = new Image( Display.getCurrent(), EnvironmentPropertyHelper.getProperty( "image.textura" ) );
        } else {
            applicationIcon = SWTResourceManager.getImage( applicationWindow.getClass(), EnvironmentPropertyHelper.getProperty( "image.logo" ) );
            backgroundImage = SWTResourceManager.getImage( applicationWindow.getClass(), EnvironmentPropertyHelper.getProperty( "image.background" ) );
            backgroundImagePlate = SWTResourceManager.getImage( applicationWindow.getClass(), EnvironmentPropertyHelper.getProperty( "image.textura" ) );
        }

        logInIcon = SWTResourceManager.getImage( applicationWindow.getClass(), ApplicationPropertyHelper.getProperty( "button.login.image" ) );
        exitIcon = SWTResourceManager.getImage( applicationWindow.getClass(), ApplicationPropertyHelper.getProperty( "button.salir.image" ) );
        buscarIcon = SWTResourceManager.getImage( applicationWindow.getClass(), ApplicationPropertyHelper.getProperty( "button.buscar.image" ) );
        infoIcon = SWTResourceManager.getImage( applicationWindow.getClass(), ApplicationPropertyHelper.getProperty( "button.infopino.image" ) );
        limpiarIcon = SWTResourceManager.getImage( applicationWindow.getClass(), ApplicationPropertyHelper.getProperty( "button.limpiar.image" ) );
        saveIcon = SWTResourceManager.getImage( applicationWindow.getClass(), ApplicationPropertyHelper.getProperty( "button.guardar.image" ) );
        closeIcon = SWTResourceManager.getImage( applicationWindow.getClass(), ApplicationPropertyHelper.getProperty( "button.cerrar.image" ) );
        aceptarIcon = SWTResourceManager.getImage( applicationWindow.getClass(), ApplicationPropertyHelper.getProperty( "button.aceptar.image" ) );
        cancelarIcon = SWTResourceManager.getImage( applicationWindow.getClass(), ApplicationPropertyHelper.getProperty( "button.cancelar.image" ) );
        calendarIcon = SWTResourceManager.getImage( applicationWindow.getClass(), ApplicationPropertyHelper.getProperty( "button.fecha.seleccionar.image" ) );
        popupWindowCreateGroupIcon = SWTResourceManager.getImage( applicationWindow.getClass(), ApplicationPropertyHelper.getProperty( "button.family.image" ) );
        rightIcon = SWTResourceManager.getImage( applicationWindow.getClass(), ApplicationPropertyHelper.getProperty( "button.right.image" ) );
        leftIcon = SWTResourceManager.getImage( applicationWindow.getClass(), ApplicationPropertyHelper.getProperty( "button.left.image" ) );
        printIcon = SWTResourceManager.getImage( applicationWindow.getClass(), ApplicationPropertyHelper.getProperty( "button.imprimir.image" ) );
        sobresIcon = SWTResourceManager.getImage( applicationWindow.getClass(), ApplicationPropertyHelper.getProperty( "button.sobres.image" ) );
        popupWindowRecetaIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.datos.receta.img" ) );
        popupWindowDatosTrabajoIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.consultar.trabajo.img" ) );
        popupWindowDatosClienteIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.datos.cliente.img" ) );
        popupWindowReprogramarIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.reprogramar.img" ) );
        popupWindowReprogramarGrupoIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.reprogramar.grupo.img" ) );
        popupWindowInfoPinoIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.info.laboratorio.img" ) );
        popupWindowNoContactarIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.contactar.no.img" ) );
        popupWindowSolicitarAutorizacionIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.solicitud.autorizacion.img" ) );
        popupWindowRetenerIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.retenido.img" ) );
        popupWindowDesretenerIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.desretener.img" ) );
        popupWindowEnviarIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.enviar.img" ) );
        popupWindowNoEnviarIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.noenviar.img" ) );
        popupWindowEfaxIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.efax.img" ) );
        popupWindowAutorizacionIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.autorizacion.img" ) );
        popupWindowNuevoIcon = SWTResourceManager.getImage( applicationWindow.getClass(), SobresPropertyHelper.getProperty( "sobres.menu.popup.item.nuevo.sobre.img" ) );
        popupWindowEliminarIcon = SWTResourceManager.getImage( applicationWindow.getClass(), SobresPropertyHelper.getProperty( "sobres.menu.popup.item.eliminar.sobre.img" ) );
        popupWindowFacturaIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.datosfactura.img" ) );
        popupWindowDesvincularIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.desvincular.img" ) );
        popupWindowAddGroupIcon = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.agrear.grupo.img" ) );
        popupWindowAddRx = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.agregar.rx.img" ) );
        popupWindowNuevaOrden = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.nueva.orden.img" ) );
        popupWindowEntregar = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.entregar.img" ) );
        popupWindowDesentregar = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.desentregar.img" ) );
        popupWindowImprimir = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.imprimir.os.img" ) );
        popupWindowBodega = SWTResourceManager.getImage( applicationWindow.getClass(), TrabajosPropertyHelper.getProperty( "trabajos.menu.popup.item.bodega.img" ) );

        iconoAgregar = SWTResourceManager.getImage( applicationWindow.getClass(), "/mx/com/lux/control/trabajos/images/icons/16x16/add.png" );
        iconoEditar = SWTResourceManager.getImage( applicationWindow.getClass(), "/mx/com/lux/control/trabajos/images/icons/16x16/pencil.png" );
        iconoGuardar = SWTResourceManager.getImage( applicationWindow.getClass(), "/mx/com/lux/control/trabajos/images/icons/16x16/save_as.png" );
        iconoCancelar = SWTResourceManager.getImage( applicationWindow.getClass(), "/mx/com/lux/control/trabajos/images/icons/16x16/delete.png" );
        iconoEnviarExterno = SWTResourceManager.getImage( applicationWindow.getClass(), "/mx/com/lux/control/trabajos/images/icons/32x32/lorry.png" );
        iconoEliminarExterno = SWTResourceManager.getImage( applicationWindow.getClass(), "/mx/com/lux/control/trabajos/images/icons/32x32/lorry_delete.png" );
        iconoEntregarExterno = SWTResourceManager.getImage( applicationWindow.getClass(), "/mx/com/lux/control/trabajos/images/icons/32x32/lorry_go.png" );
    }

}

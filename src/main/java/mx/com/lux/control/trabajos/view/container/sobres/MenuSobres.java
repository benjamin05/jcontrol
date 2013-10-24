package mx.com.lux.control.trabajos.view.container.sobres;

import mx.com.lux.control.trabajos.bussiness.service.sobres.SobreService;
import mx.com.lux.control.trabajos.data.vo.JbSobre;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.properties.MessagesPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.SobresPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import java.util.ArrayList;
import java.util.List;

public class MenuSobres {


	private static DatosSobreDialog nuevoSobreDialog;

	public static SobreService sobreService;

	static {
		sobreService = (SobreService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_SOBRE_SERVICE );
	}


	private Shell shell;
	private Control parentControl;
	private Menu menuSobres;
	private List<Boolean> menuOptionActive = new ArrayList<Boolean>();
	private boolean visible = true;

	public MenuSobres() {
		// TODO Auto-generated constructor stub
	}

	public MenuSobres( Shell shell, Control control ) {
		setMenuComponents( shell, control );
	}

	public MenuSobres( Shell shell, Control control, List<Boolean> menuOptionActive ) {
		setMenuComponentsVisible( shell, control, menuOptionActive );
	}

	private void setMenuComponents( Shell shell, Control control ) {
		this.shell = shell;
		this.parentControl = control;
		if ( menuOptionActive.isEmpty() ) {
			for ( int i = 0; i < Constants.MENU_TOTAL; i++ ) {
				menuOptionActive.add( visible );
			}
		}
		createMenu( control );
	}

	private void setMenuComponentsVisible( Shell shell, Control control, List<Boolean> menuOptionActive ) {
		this.shell = shell;
		this.parentControl = control;
		this.menuOptionActive = menuOptionActive;
		createMenu( control );
	}

	public Menu getMenuSobres() {
		return menuSobres;
	}

	private void createMenu( Control control ) {

		menuSobres = new Menu( control );

		if ( menuOptionActive.get( Constants.MENU_SOBRES_INDEX_NUEVO ).booleanValue() ) {
			MenuItem mnItemDatosReceta = new MenuItem( menuSobres, SWT.NONE );
			mnItemDatosReceta.setText( SobresPropertyHelper.getProperty( "sobres.menu.popup.item.nuevo.sobre" ) );
			mnItemDatosReceta.setImage( ControlTrabajosWindowElements.popupWindowNuevoIcon );
			mnItemDatosReceta.setEnabled( true );
			mnItemDatosReceta.addSelectionListener( new SelectionListener() {

				@Override
				public void widgetSelected( SelectionEvent arg0 ) {
					nuevoSobreDialog = new DatosSobreDialog( shell, TipoSobre.NUEVO );
					nuevoSobreDialog.open();
				}

				@Override
				public void widgetDefaultSelected( SelectionEvent arg0 ) {
					// TODO Auto-generated method stub

				}
			} );
		}

		if ( menuOptionActive.get( Constants.MENU_SOBRES_INDEX_ELIMINAR ).booleanValue() ) {
			MenuItem mnItemDatosReceta = new MenuItem( menuSobres, SWT.NONE );
			mnItemDatosReceta.setText( SobresPropertyHelper.getProperty( "sobres.menu.popup.item.eliminar.sobre" ) );
			mnItemDatosReceta.setImage( ControlTrabajosWindowElements.popupWindowEliminarIcon );
			mnItemDatosReceta.setEnabled( false );
			mnItemDatosReceta.addSelectionListener( new SelectionListener() {

				@Override
				public void widgetSelected( SelectionEvent arg0 ) {
					MessageDialog confirm = new MessageDialog( shell, MessagesPropertyHelper.getProperty( "eliminar.title" ), null, MessagesPropertyHelper.getProperty( "eliminar.message.01" ), MessageDialog.ERROR, new String[]{ MessagesPropertyHelper.getProperty( "generic.si" ), MessagesPropertyHelper.getProperty( "generic.no" ) }, 0 );
					int result = confirm.open();
					if ( result == 0 ) {
						JbSobre jbSobre = (JbSobre) Session.removeAttribute( SobresDialog.ITEM_SELECTED_JBSOBRE );
						try {
							sobreService.deleteSobre( jbSobre );
							menuSobres.getItem( Constants.MENU_SOBRES_INDEX_ELIMINAR ).setEnabled( false );
							SobresDialog dialog = (SobresDialog) Session.getAttribute( SobresDialog.SOBRES_DIALOG );
							dialog.loadSobres();
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
	}
}

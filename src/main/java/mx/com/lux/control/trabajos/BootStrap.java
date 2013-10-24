package mx.com.lux.control.trabajos;


import mx.com.lux.control.trabajos.view.container.sistema.login.LoginContainer;
import org.eclipse.swt.widgets.Display;

public class BootStrap {

	public static void main( String[] args ) {
		LoginContainer login = new LoginContainer();
		login.open();
		Display.getCurrent().dispose();
	}

}

package mx.com.lux.control.trabajos.view.container;

import mx.com.lux.control.trabajos.util.ApplicationUtils;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

public abstract class DialogoBase extends Dialog {

	protected Shell shell;
	protected int numeroColumnas = 12;
	protected int anchoColumna = 50;
	protected int anchoEntreColumnas = 10;
	protected int ancho = ( numeroColumnas * anchoColumna ) + ( ( numeroColumnas - 1 ) * anchoEntreColumnas ) + 40;
	protected int alto = 100;
	protected String tituloDialogo = "";
	protected Font fuente;

	protected DialogoBase( Shell shell ) {
		super( shell );
		this.shell = shell;
	}

	protected DialogoBase( Shell shell, String titulo, int numeroColumnas, int anchoColumna, int anchoEntreColumnas, int alto ) {
		super( shell );
		this.shell = shell;
		tituloDialogo = titulo;
		this.numeroColumnas = numeroColumnas;
		this.anchoColumna = anchoColumna;
		this.anchoEntreColumnas = anchoEntreColumnas;
		this.ancho = ( numeroColumnas * anchoColumna ) + ( ( numeroColumnas - 1 ) * anchoEntreColumnas ) + 60;
		this.alto = alto;
	}

	@Override
	protected Point getInitialSize() {
		return new Point( ancho, alto );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		newShell.setText( tituloDialogo );
		shell = newShell;
	}

	protected Label crearElementoEtiqueta( Composite contenedor, String textoEtiqueta, int columnasEtiqueta ) {
		return crearElementoEtiqueta( contenedor, textoEtiqueta, columnasEtiqueta, 0 );
	}

	protected Label crearElementoEtiqueta( Composite contenedor, String textoEtiqueta, int columnasEtiqueta, int tamanyoFuente ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( textoEtiqueta );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd1 = new GridData();
		gd1.widthHint = ancho( columnasEtiqueta );
		gd1.horizontalSpan = columnasEtiqueta;
		etiqueta.setLayoutData( gd1 );

		if( tamanyoFuente > 0 ) {
			cambiarTamanyoFuente( etiqueta, tamanyoFuente );
		}

		return etiqueta;
	}

	protected Text crearElementoTexto( Composite contenedor, String textoContenido, int columnasTexto, TipoElemento tipo ) {
		Text texto = new Text( contenedor, SWT.BORDER );
		GridData gd2 = new GridData();
		gd2.widthHint = ancho( columnasTexto );
		gd2.horizontalSpan = columnasTexto;
		texto.setLayoutData( gd2 );
		texto.setText( textoContenido );
		texto.addVerifyListener( new VerifyListener() {
			public void verifyText( VerifyEvent e ) {
				e.text = e.text.toUpperCase();
				e.text = ApplicationUtils.quitarAcentos( e.text );
			}
		} );

		switch ( tipo ) {
			case NORMAL:
				break;
			case DESACTIVADO:
				texto.setEnabled( false );
				break;
			case SOLO_LECTURA:
				texto.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
				texto.setEditable( false );
				break;
		}

		return texto;
	}

	protected void crearElementoVacio( Composite contenedor, int columnas ) {
		Label etiqueta = new Label( contenedor, SWT.NONE );
		etiqueta.setText( "" );
		etiqueta.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridData gd = new GridData();
		gd.widthHint = ancho( columnas );
		gd.horizontalSpan = columnas;
		etiqueta.setLayoutData( gd );
	}

	protected Button crearElementoBoton( Composite contenedor, String textoBoton, int columnasBoton, Image icono ) {
		Button boton = new Button( contenedor, SWT.NONE );
		boton.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		boton.setText( textoBoton );
		boton.setImage( icono );
		GridData gd = new GridData();
		gd.widthHint = ancho( columnasBoton ) + 20;
		gd.horizontalSpan = columnasBoton;
		boton.setLayoutData( gd );
		return boton;
	}

	protected Button crearElementoCheckbox( Composite contenedor, String etiqueta, int numeroColumnas ) {
		Button checkbox = new Button( contenedor, SWT.CHECK );
		checkbox.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		checkbox.setText( etiqueta );
		GridData gd = new GridData();
		gd.widthHint = ancho( numeroColumnas ) + 5;
		gd.horizontalSpan = numeroColumnas;
		checkbox.setLayoutData( gd );
		return checkbox;
	}

	protected Button crearElementoRadio( Composite contenedor, String etiqueta, int numeroColumnas ) {
		Button checkbox = new Button( contenedor, SWT.RADIO );
		checkbox.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		checkbox.setText( etiqueta );
		GridData gd = new GridData();
		gd.widthHint = ancho( numeroColumnas ) + 5;
		gd.horizontalSpan = numeroColumnas;
		checkbox.setLayoutData( gd );
		return checkbox;
	}

	protected ComboViewer crearElementoCombo( Composite contenedor, ILabelProvider render, java.util.List input, int columnasCombo, TipoElemento tipo ) {
		ComboViewer comboViewer = new ComboViewer( contenedor, SWT.READ_ONLY );
		comboViewer.setContentProvider( new ArrayContentProvider() );
		comboViewer.setLabelProvider( render );
		comboViewer.setInput( input );

		Combo combo = comboViewer.getCombo();
		GridData gd2 = new GridData();
		gd2.widthHint = ancho( columnasCombo ) + 10;
		gd2.horizontalSpan = columnasCombo;
		combo.setLayoutData( gd2 );

		switch ( tipo ) {
			case NORMAL:
				break;
			case DESACTIVADO:
				combo.setEnabled( false );
				break;
			case SOLO_LECTURA:
				combo.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
				combo.setEnabled( false );
				break;
		}

		return comboViewer;
	}

	protected int ancho( int numeroColumnas ) {
		return ( numeroColumnas * anchoColumna ) + ( ( numeroColumnas - 1 ) * anchoEntreColumnas );
	}

	protected enum TipoElemento {
		NORMAL,
		DESACTIVADO,
		SOLO_LECTURA
	}

	protected void cambiarTamanyoFuente( Control control, int tamanyo ) {
		FontData[] fontData = control.getFont().getFontData();
		for ( FontData aFontData : fontData ) {
			aFontData.setHeight( tamanyo );
		}
		control.setFont( new Font( shell.getDisplay(), fontData ) );
	}

}

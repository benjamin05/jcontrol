package mx.com.lux.control.trabajos.view.container.trabajos;

import mx.com.lux.control.trabajos.bussiness.service.trabajo.RecetaService;
import mx.com.lux.control.trabajos.data.vo.Receta;
import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.BeanNamesContext;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.util.constants.GraphicConstants;
import mx.com.lux.control.trabajos.util.properties.ApplicationPropertyHelper;
import mx.com.lux.control.trabajos.util.properties.TrabajosPropertyHelper;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

public class DatosRecetaDialog extends Dialog {

	public static RecetaService recetaService;
	private Receta receta;

	static {
		recetaService = (RecetaService) ServiceLocator.getBean( BeanNamesContext.BEAN_NAME_RECETA_SERVICE );
	}

	private Group grpRx;
	private Label lblRx;
	private Text txtRx;
	private Group group;
	private Label lblEsf;
	private Label lblCil;
	private Label lblEje;
	private Label lblAd;
	private Label lblAdInt;
	private Label lblDm;
	private Label lblOjoDer;
	private Text txtOjoDerEsf;
	private Text txtOjoDerCil;
	private Text txtOjoDerEje;
	private Text txtOjoDerAd;
	private Text txtOjoDerAdInt;
	private Text txtOjoDerDm;
	private Label lblOjoIzq;
	private Text txtOjoIzqEsf;
	private Text txtOjoIzqCil;
	private Text txtOjoIzqEje;
	private Text txtOjoIzqAd;
	private Text txtOjoIzqAdInt;
	private Text txtOjoIzqDm;
	private Label lblDiLejos;
	private Text txtDiLejos;
	private Label lblDiCerca;
	private Text txtDiCerca;
	private Label lblAltOblea;
	private Text txtAltOblea;
	private Label lblObsRx;
	private Text txtObsRx;

	public DatosRecetaDialog( Shell parentShell ) {
		super( parentShell );
		try {
			Object idReceta = Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RECETA );
			if ( idReceta instanceof Integer ) {
				receta = recetaService.findById( (Integer) idReceta );
			} else {
				String idRecetaStr = (String) idReceta;
				if ( StringUtils.isNotBlank( idRecetaStr ) ) {
					System.out.println( "buscando" + idRecetaStr );
					receta = recetaService.findById( Integer.parseInt( idRecetaStr ) );
				}
			}
			if ( receta == null ) {
				receta = new Receta();
			}
		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
	}

	@Override
	protected Control createDialogArea( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		Composite container = (Composite) super.createDialogArea( parent );
		container.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		container.setBounds( 5, 5, 495, 555 );
		container.setLayout( null );
		container.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		grpRx = new Group( container, SWT.SHADOW_OUT );
		grpRx.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		grpRx.setBounds( 10, 10, 473, 40 );
		grpRx.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		lblRx = new Label( grpRx, SWT.NONE );
		lblRx.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.rx" ) );
		lblRx.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblRx.setBounds( 10, 10, 60, 20 );
		lblRx.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtRx = new Text( grpRx, SWT.BORDER );
		txtRx.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtRx.setEditable( false );
		txtRx.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtRx.setBounds( 76, 10, 116, 20 );
		txtRx.setText( (String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX ) );

		group = new Group( container, SWT.SHADOW_OUT );
		group.setBounds( 10, 56, 473, 185 );
		group.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		lblEsf = new Label( group, SWT.NONE );
		lblEsf.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.receta.esferica" ) );
		lblEsf.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblEsf.setAlignment( SWT.CENTER );
		lblEsf.setBounds( 75, 10, 50, 20 );
		lblEsf.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		lblCil = new Label( group, SWT.NONE );
		lblCil.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.receta.cilindrica" ) );
		lblCil.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblCil.setAlignment( SWT.CENTER );
		lblCil.setBounds( 141, 10, 50, 20 );
		lblCil.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		lblEje = new Label( group, SWT.NONE );
		lblEje.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.receta.eje" ) );
		lblEje.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblEje.setAlignment( SWT.CENTER );
		lblEje.setBounds( 205, 10, 50, 20 );
		lblEje.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		lblAd = new Label( group, SWT.NONE );
		lblAd.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.receta.ad" ) );
		lblAd.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblAd.setAlignment( SWT.CENTER );
		lblAd.setBounds( 271, 10, 50, 20 );
		lblAd.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		lblAdInt = new Label( group, SWT.NONE );
		lblAdInt.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.receta.ad.int" ) );
		lblAdInt.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblAdInt.setAlignment( SWT.CENTER );
		lblAdInt.setBounds( 337, 10, 50, 20 );
		lblAdInt.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		lblDm = new Label( group, SWT.NONE );
		lblDm.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.receta.dm" ) );
		lblDm.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblDm.setAlignment( SWT.CENTER );
		lblDm.setBounds( 403, 10, 50, 20 );
		lblDm.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		lblOjoDer = new Label( group, SWT.NONE );
		lblOjoDer.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.receta.ojo.derecho" ) );
		lblOjoDer.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblOjoDer.setBounds( 10, 36, 60, 20 );
		lblOjoDer.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtOjoDerEsf = new Text( group, SWT.BORDER );
		txtOjoDerEsf.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtOjoDerEsf.setEditable( false );
		txtOjoDerEsf.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtOjoDerEsf.setBounds( 75, 36, 50, 20 );
		txtOjoDerEsf.setText( StringUtils.isNotBlank( receta.getOdEsfR() ) ? receta.getOdEsfR() : Constants.CADENA_VACIA );

		txtOjoDerCil = new Text( group, SWT.BORDER );
		txtOjoDerCil.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtOjoDerCil.setEditable( false );
		txtOjoDerCil.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtOjoDerCil.setBounds( 141, 36, 50, 20 );
		txtOjoDerCil.setText( StringUtils.isNotBlank( receta.getOdCilR() ) ? receta.getOdCilR() : Constants.CADENA_VACIA );

		txtOjoDerEje = new Text( group, SWT.BORDER );
		txtOjoDerEje.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtOjoDerEje.setEditable( false );
		txtOjoDerEje.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtOjoDerEje.setBounds( 205, 36, 50, 20 );
		txtOjoDerEje.setText( StringUtils.isNotBlank( receta.getOdEjeR() ) ? receta.getOdEjeR() : Constants.CADENA_VACIA );

		txtOjoDerAd = new Text( group, SWT.BORDER );
		txtOjoDerAd.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtOjoDerAd.setEditable( false );
		txtOjoDerAd.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtOjoDerAd.setBounds( 271, 36, 50, 20 );
		txtOjoDerAd.setText( StringUtils.isNotBlank( receta.getOdAdcR() ) ? receta.getOdAdcR() : Constants.CADENA_VACIA );

		txtOjoDerAdInt = new Text( group, SWT.BORDER );
		txtOjoDerAdInt.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtOjoDerAdInt.setEditable( false );
		txtOjoDerAdInt.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtOjoDerAdInt.setBounds( 337, 36, 50, 20 );
		txtOjoDerAdInt.setText( StringUtils.isNotBlank( receta.getOdAdiR() ) ? receta.getOdAdiR() : Constants.CADENA_VACIA );

		txtOjoDerDm = new Text( group, SWT.BORDER );
		txtOjoDerDm.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtOjoDerDm.setEditable( false );
		txtOjoDerDm.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtOjoDerDm.setBounds( 403, 36, 50, 20 );
		txtOjoDerDm.setText( StringUtils.isNotBlank( receta.getDiOd() ) ? receta.getDiOd() : Constants.CADENA_VACIA );

		lblOjoIzq = new Label( group, SWT.NONE );
		lblOjoIzq.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.receta.ojo.izquierdo" ) );
		lblOjoIzq.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblOjoIzq.setBounds( 10, 75, 60, 20 );
		lblOjoIzq.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtOjoIzqEsf = new Text( group, SWT.BORDER );
		txtOjoIzqEsf.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtOjoIzqEsf.setEditable( false );
		txtOjoIzqEsf.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtOjoIzqEsf.setBounds( 75, 75, 50, 20 );
		txtOjoIzqEsf.setText( StringUtils.isNotBlank( receta.getOiEsfR() ) ? receta.getOiEsfR() : Constants.CADENA_VACIA );

		txtOjoIzqCil = new Text( group, SWT.BORDER );
		txtOjoIzqCil.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtOjoIzqCil.setEditable( false );
		txtOjoIzqCil.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtOjoIzqCil.setBounds( 141, 75, 50, 20 );
		txtOjoIzqCil.setText( StringUtils.isNotBlank( receta.getOiCilR() ) ? receta.getOiCilR() : Constants.CADENA_VACIA );

		txtOjoIzqEje = new Text( group, SWT.BORDER );
		txtOjoIzqEje.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtOjoIzqEje.setEditable( false );
		txtOjoIzqEje.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtOjoIzqEje.setBounds( 205, 75, 50, 20 );
		txtOjoIzqEje.setText( StringUtils.isNotBlank( receta.getOiEjeR() ) ? receta.getOiEjeR() : Constants.CADENA_VACIA );

		txtOjoIzqAd = new Text( group, SWT.BORDER );
		txtOjoIzqAd.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtOjoIzqAd.setEditable( false );
		txtOjoIzqAd.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtOjoIzqAd.setBounds( 271, 75, 50, 20 );
		txtOjoIzqAd.setText( StringUtils.isNotBlank( receta.getOiAdcR() ) ? receta.getOiAdcR() : Constants.CADENA_VACIA );

		txtOjoIzqAdInt = new Text( group, SWT.BORDER );
		txtOjoIzqAdInt.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtOjoIzqAdInt.setEditable( false );
		txtOjoIzqAdInt.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtOjoIzqAdInt.setBounds( 337, 75, 50, 20 );
		txtOjoIzqAdInt.setText( StringUtils.isNotBlank( receta.getOiAdiR() ) ? receta.getOiAdiR() : Constants.CADENA_VACIA );

		txtOjoIzqDm = new Text( group, SWT.BORDER );
		txtOjoIzqDm.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtOjoIzqDm.setEditable( false );
		txtOjoIzqDm.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtOjoIzqDm.setBounds( 403, 75, 50, 20 );
		txtOjoIzqDm.setText( StringUtils.isNotBlank( receta.getDiOi() ) ? receta.getDiOi() : Constants.CADENA_VACIA );

		lblDiLejos = new Label( group, SWT.NONE );
		lblDiLejos.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.receta.DiLejos" ) );
		lblDiLejos.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblDiLejos.setBounds( 10, 110, 60, 20 );
		lblDiLejos.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtDiLejos = new Text( group, SWT.BORDER );
		txtDiLejos.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtDiLejos.setEditable( false );
		txtDiLejos.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtDiLejos.setBounds( 75, 110, 50, 20 );
		txtDiLejos.setText( StringUtils.isNotBlank( receta.getDiLejosR() ) ? receta.getDiLejosR() : Constants.CADENA_VACIA );

		lblDiCerca = new Label( group, SWT.NONE );
		lblDiCerca.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.receta.DiCerca" ) );
		lblDiCerca.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblDiCerca.setBounds( 141, 110, 60, 20 );
		lblDiCerca.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtDiCerca = new Text( group, SWT.BORDER );
		txtDiCerca.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtDiCerca.setEditable( false );
		txtDiCerca.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtDiCerca.setBounds( 205, 110, 50, 20 );
		txtDiCerca.setText( StringUtils.isNotBlank( receta.getDiCercaR() ) ? receta.getDiCercaR() : Constants.CADENA_VACIA );

		lblAltOblea = new Label( group, SWT.NONE );
		lblAltOblea.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.receta.AltOblea" ) );
		lblAltOblea.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblAltOblea.setBounds( 271, 110, 60, 20 );
		lblAltOblea.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtAltOblea = new Text( group, SWT.BORDER );
		txtAltOblea.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtAltOblea.setEditable( false );
		txtAltOblea.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtAltOblea.setBounds( 337, 110, 50, 20 );
		txtAltOblea.setText( StringUtils.isNotBlank( receta.getAltOblR() ) ? receta.getAltOblR() : Constants.CADENA_VACIA );

		lblObsRx = new Label( group, SWT.NONE );
		lblObsRx.setText( TrabajosPropertyHelper.getProperty( "trabajos.label.receta.ObsRx" ) );
		lblObsRx.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		lblObsRx.setBounds( 10, 148, 60, 20 );
		lblObsRx.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		txtObsRx = new Text( group, SWT.BORDER );
		txtObsRx.setFont( SWTResourceManager.getFont( "Ubuntu", 10, SWT.NORMAL ) );
		txtObsRx.setEditable( false );
		txtObsRx.setBackground( GraphicConstants.FIELD_COLOR_READONLY );
		txtObsRx.setBounds( 75, 148, 378, 20 );
		txtObsRx.setText( StringUtils.isNotBlank( receta.getObservacionesR() ) ? receta.getObservacionesR() : Constants.CADENA_VACIA );
		return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 505, 335 );
	}

	@Override
	protected void configureShell( Shell newShell ) {
		super.configureShell( newShell );
		//		newShell.setText(TrabajosPropertyHelper.getProperty("datos.receta.window.dialog.title"));
		newShell.setText( Constants.RX_DOS_PUNTOS + " " +
				(String) Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_ID_RX ) );
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
	}
}

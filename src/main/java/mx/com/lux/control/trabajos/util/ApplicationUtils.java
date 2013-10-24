package mx.com.lux.control.trabajos.util;

import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.consulta.ConsultaContainer;
import mx.com.lux.control.trabajos.view.container.contacto.ContactoContainer;
import mx.com.lux.control.trabajos.view.container.contacto.GrupoDataDialog;
import mx.com.lux.control.trabajos.view.container.contacto.GruposDataDialog;
import mx.com.lux.control.trabajos.view.container.envios.EnviosContainer;
import mx.com.lux.control.trabajos.view.container.garantia.GarantiaContainer;
import mx.com.lux.control.trabajos.view.container.ordenservicio.OrdenServicioContainer;
import mx.com.lux.control.trabajos.view.container.recepcion.RecepcionContainer;
import mx.com.lux.control.trabajos.view.container.reposicion.ReposicionContainer;
import mx.com.lux.control.trabajos.view.container.trabajos.MenuInfoTrabajos;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class ApplicationUtils {

	// "^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$"
	private static final String EMAIL_REGEX = "^[\\w\\-\\_\\+]+(\\.[\\w\\-\\_]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$";
	private static final String MONEY_FORMAT = "'$'#,###,##0.00";
	private static final String[] MESES = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" };

	public static String getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get( Calendar.DAY_OF_MONTH ) + " de " + MESES[calendar.get( Calendar.MONTH )] + " de " + calendar.get( Calendar.YEAR );
	}

	public static List<Boolean> opcionesMenu( final Integer[] opcionesMenuInactivas ) {
		List<Boolean> opcionesMenu = new ArrayList<Boolean>();
		for ( int i = 0; i < Constants.MENU_TOTAL; i++ ) {
			opcionesMenu.add( i, !ArrayUtils.contains( opcionesMenuInactivas, i ) );
		}
		return Collections.unmodifiableList( opcionesMenu );
	}


	public static void recargarDatosPestanyaVisible() {
		Object pestanya = Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_TAB );
		Object objPopup = Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_SECOND_POPUP );
		Object objThirdPopup = Session.getAttribute( MenuInfoTrabajos.ITEM_SELECTED_THIRD_POPUP );

		if ( pestanya instanceof ContactoContainer ) {
			( (ContactoContainer) pestanya ).getAllContacto();
		}

		if ( pestanya instanceof ConsultaContainer ) {
			( (ConsultaContainer) pestanya ).searchFilter();
		}

		if ( pestanya instanceof RecepcionContainer ) {
			( (RecepcionContainer) pestanya ).llenarTabla();
		}

		if ( pestanya instanceof EnviosContainer ) {
			EnviosContainer enviosContainer = (EnviosContainer) pestanya;
			enviosContainer.cargarDatosNoEnviar();
			enviosContainer.cargarDatosPorEnviar();
			enviosContainer.cargarDatosDevolucionesSp();
		}
		if ( pestanya instanceof OrdenServicioContainer ) {
			OrdenServicioContainer container = OrdenServicioContainer.class.cast( pestanya );
			container.llenarTabla();
		}
		if ( objPopup instanceof GruposDataDialog ) {
			GruposDataDialog gruposDataDialog = (GruposDataDialog) objPopup;
			gruposDataDialog.loadTable();
		}
		if ( objThirdPopup instanceof GrupoDataDialog ) {
			GrupoDataDialog grupoDataDialog = (GrupoDataDialog) objThirdPopup;
			grupoDataDialog.loadTable();
		}
		if ( pestanya instanceof GarantiaContainer ) {
			( (GarantiaContainer) pestanya ).cargarDatosTabla();
		}
		if( pestanya instanceof ReposicionContainer) {
			( (ReposicionContainer) pestanya ).cargarTrabajos();
		}
	}

	public static Double moneyToDouble( final String money ) {
		if ( StringUtils.isNotBlank( money ) ) {
			String value = StringUtils.remove( money, "$" );
			value = StringUtils.remove( value, "," );
			return NumberUtils.toDouble( value, 0.0d );
		}
		return 0.0d;
	}

	public static String doubleToMoney( final Double value ) {
		if ( value != null ) {
			DecimalFormat formatter = new DecimalFormat( MONEY_FORMAT );
			return formatter.format( value );
		}
		return null;
	}

	public static Boolean isEmail( final String email ) {
		return StringUtils.isNotBlank( email ) && Pattern.matches( EMAIL_REGEX, email );
	}

	public static String formatearFecha( final Date fecha, final String pattern ) {
		SimpleDateFormat sdf = new SimpleDateFormat( pattern );
		if ( fecha != null ) {
			return sdf.format( fecha );
		}
		return "";
	}

	public static String formatearFecha( final Date fecha ) {
		String pattern = "dd/MM/yyyy";
		return formatearFecha( fecha, pattern );
	}

	public static String quitarAcentos( final String texto ) {
		String tmp = texto;
		tmp = StringUtils.replace( tmp, "Ñ", "N" );
		tmp = StringUtils.replace( tmp, "ñ", "n" );
		tmp = StringUtils.replace( tmp, "Á", "A" );
		tmp = StringUtils.replace( tmp, "É", "E" );
		tmp = StringUtils.replace( tmp, "Í", "I" );
		tmp = StringUtils.replace( tmp, "Ó", "O" );
		tmp = StringUtils.replace( tmp, "Ú", "U" );
		tmp = StringUtils.replace( tmp, "á", "a" );
		tmp = StringUtils.replace( tmp, "é", "e" );
		tmp = StringUtils.replace( tmp, "í", "i" );
		tmp = StringUtils.replace( tmp, "ó", "o" );
		tmp = StringUtils.replace( tmp, "ú", "u" );
		return tmp;
	}

	public static <T> List<T> compruebaLista( final List<T> lista ) {
		return lista != null ? lista : new ArrayList<T>();
	}
}

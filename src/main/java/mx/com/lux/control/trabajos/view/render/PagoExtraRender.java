package mx.com.lux.control.trabajos.view.render;

import mx.com.lux.control.trabajos.data.vo.JbPagoExtra;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PagoExtraRender implements TableItemRender<JbPagoExtra> {

	public static final int FECHA = 0;
	public static final int PAGO = 1;
	public static final int NUMERO = 2;
	public static final int TERM = 3;
	public static final int PROMO = 4;
	public static final int REFER = 5;
	public static final int BANCO = 6;
	public static final int IMPORTE = 7;
	public static final int ID = 8;

	@Override
	public String[] getText( JbPagoExtra o ) {
		SimpleDateFormat sdf = new SimpleDateFormat( " dd-MM-yyyy" );
		NumberFormat nf = NumberFormat.getCurrencyInstance( Locale.US );
		String[] text = new String[9];
		text[FECHA] = sdf.format( o.getFecha() );
		text[PAGO] = o.getIdFormaPago();
		text[NUMERO] = o.getClaveP();
		text[TERM] = o.getIdTerm();
		text[PROMO] = o.getIdPlan();
		text[REFER] = o.getRefClave();
		text[BANCO] = o.getIdBancoEmi();
		text[IMPORTE] = nf.format( o.getMonto().doubleValue() );
		text[ID] = sdf.format( o.getIdPago() );
		return text;
	}

}

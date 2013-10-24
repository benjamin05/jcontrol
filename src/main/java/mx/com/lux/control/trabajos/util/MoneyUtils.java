package mx.com.lux.control.trabajos.util;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

public class MoneyUtils {

	public static BigDecimal parseMoney( final String money ) {
		if ( money != null ) {
			String tmp = money.trim();
			tmp = StringUtils.remove( tmp, '$' );
			tmp = StringUtils.remove( tmp, ',' );
			return new BigDecimal( tmp );
		}
		return BigDecimal.ZERO;
	}

}

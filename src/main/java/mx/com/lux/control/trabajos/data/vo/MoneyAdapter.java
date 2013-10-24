package mx.com.lux.control.trabajos.data.vo;

import mx.com.lux.control.trabajos.util.ApplicationUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGmoney;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class MoneyAdapter implements UserType {

	@Override
	public Object assemble( Serializable arg0, Object arg1 ) throws HibernateException {
		return arg0;
	}

	@Override
	public Object deepCopy( Object o ) throws HibernateException {
		if ( o != null ) {
			BigDecimal tmp = (BigDecimal) o;
			return BigDecimal.valueOf( tmp.doubleValue() );
		}
		return null;
	}

	@Override
	public Serializable disassemble( Object arg0 ) throws HibernateException {
		return (Serializable) arg0;
	}

	@Override
	public boolean equals( Object o1, Object o2 ) throws HibernateException {
		if ( o1 != null && o2 != null ) {
			return o1.equals( o2 );
		}
		return false;
	}

	@Override
	public int hashCode( Object arg0 ) throws HibernateException {
		return arg0.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object nullSafeGet( ResultSet resultSet, String[] names, Object owner ) throws HibernateException, SQLException {
		String monto = null;
		Object o = Hibernate.STRING.get( resultSet, names[0] );
		if ( o != null && o instanceof String ) {
			monto = (String) o;
		}
		if ( monto != null ) {
			Double montoDouble = ApplicationUtils.moneyToDouble( monto );
			return new BigDecimal( montoDouble );
		}
		return new BigDecimal( 0 );
	}

	@Override
	public void nullSafeSet( PreparedStatement preparedStatement, Object o, int index ) throws HibernateException, SQLException {
		BigDecimal val = (BigDecimal) o;
		if ( val != null ) {
			preparedStatement.setObject( index, new PGmoney( val.doubleValue() ) );
		} else {
			preparedStatement.setString( index, ApplicationUtils.doubleToMoney( 0d ) );
		}
	}

	@Override
	public Object replace( Object arg0, Object arg1, Object arg2 ) throws HibernateException {
		return arg0;
	}

	@SuppressWarnings( "rawtypes" )
	@Override
	public Class returnedClass() {
		return BigDecimal.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[]{ Types.OTHER };
	}

}

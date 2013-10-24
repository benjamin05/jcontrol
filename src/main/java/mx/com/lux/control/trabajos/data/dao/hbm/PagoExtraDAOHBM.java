package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.PagoExtraDAO;
import mx.com.lux.control.trabajos.data.vo.JbPagoExtra;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "pagoExtraDAO" )
public class PagoExtraDAOHBM extends DAOSupport implements PagoExtraDAO {

	@Autowired
	public PagoExtraDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<JbPagoExtra> buscarPorRx( final String rx ) {
		Criteria criteria = getSession().createCriteria( JbPagoExtra.class );
		criteria.add( Restrictions.eq( "factura", rx ) );
		return (List<JbPagoExtra>) criteria.list();
	}

	@Override
	public List<JbPagoExtra> buscarPorRxPaginado( final String rx, final int primerResultado, final int numeroResultados ) {
		Criteria criteria = getSession().createCriteria( JbPagoExtra.class );
		criteria.add( Restrictions.eq( "factura", rx ) );
		criteria.setFirstResult( primerResultado );
		criteria.setMaxResults( numeroResultados );
		return (List<JbPagoExtra>) criteria.list();
	}

	@Override
	public void eliminarPorId( final Integer id ) {
		Criteria criteria = getSession().createCriteria( JbPagoExtra.class );
		criteria.add( Restrictions.eq( "idPago", id ) );
		JbPagoExtra pagoExtra = (JbPagoExtra) criteria.uniqueResult();
		getSession().delete( pagoExtra );
	}

	@Override
	public List<JbPagoExtra> findAll() throws DAOException {
		return getSession().createCriteria( JbPagoExtra.class ).list();
	}

}

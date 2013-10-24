package mx.com.lux.control.trabajos.data.dao.envio.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.envio.SucursalDAO;
import mx.com.lux.control.trabajos.data.vo.Sucursal;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "sucursalDAO" )
public class SucursalDAOHBM extends DAOSupport implements SucursalDAO {

	@Autowired
	public SucursalDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<?> findAll() throws DAOException {
		return getSession().createCriteria( Sucursal.class ).list();
	}

	@Override
	public Sucursal findById( int id ) throws DAOException {
		return (Sucursal) findByPrimaryKey( Sucursal.class, id );
	}

	@Override
	public Sucursal obtenerPorNombre( final String nombre ) {
		Criteria criteria = getSession().createCriteria( Sucursal.class );
		criteria.add( Restrictions.eq( "nombre", nombre ) );
		return (Sucursal) criteria.uniqueResult();
	}

	@Override
	public Sucursal obtenerPorCentroCostos( final String centroCostos ) {
		Criteria criteria = getSession().createCriteria( Sucursal.class );
		criteria.add( Restrictions.eq( "centroCostos", centroCostos ) );
		return (Sucursal) criteria.uniqueResult();
	}
}

package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.ReposicionDAO;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "reposicionDAO" )
public class ReposicionDAOHBM extends DAOSupport implements ReposicionDAO {

	@Autowired
	public ReposicionDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<Reposicion> findAll() throws DAOException {
		Criteria criteria = getSession().createCriteria( Reposicion.class );
		return criteria.list();
	}

	@Override
	public List<String> obtenerTodosRxConReposicion() {
		Criteria criteria = getSession().createCriteria( Reposicion.class );
		criteria.setProjection( Projections.distinct( Projections.property( "factura" ) ) );
		return criteria.list();
	}

	@Override
	public List<Reposicion> buscarPorRx( String rx ) {
		Criteria criteria = getSession().createCriteria( Reposicion.class );
		criteria.add( Restrictions.eq( "factura", rx ) );
		return criteria.list();
	}

	@Override
	public Reposicion obtenerPorRxYNumeroOrden( String rx, Integer numeroOrden ) {
		Criteria criteria = getSession().createCriteria( Reposicion.class );
		criteria.add( Restrictions.eq( "factura", rx ) );
		criteria.add( Restrictions.eq( "numeroOrden", numeroOrden ) );
		return (Reposicion) criteria.uniqueResult();
	}

	@Override
	public List<ResponsableReposicion> obtenerTodosResponsableReposicion() {
		Criteria criteria = getSession().createCriteria( ResponsableReposicion.class );
		criteria.addOrder( Order.asc( "responsable" ) );
		return criteria.list();
	}

	@Override
	public List<Prisma> obtenerTodosPrisma() {
		Criteria criteria = getSession().createCriteria( Prisma.class );
		criteria.addOrder( Order.asc( "descripcion" ) );
		return criteria.list();
	}

	@Override
	public List<CausaReposicion> obtenerTodosCausaReposicion() {
		Criteria criteria = getSession().createCriteria( CausaReposicion.class );
		criteria.addOrder( Order.asc( "descripcion" ) );
		return criteria.list();
	}

	@Override
	public List<DetalleReposicion> obtenerDetalleReposicionPorRx( String rx ) {
		Criteria criteria = getSession().createCriteria( DetalleReposicion.class );
		criteria.add( Restrictions.eq( "factura", rx ) );
		criteria.addOrder( Order.asc( "fecha" ) );
		return criteria.list();
	}

	@Override
	public List<DetalleReposicion> obtenerDetalleReposicionPorRxYNumeroOrden( String rx, Integer numeroOrden ) {
		Criteria criteria = getSession().createCriteria( DetalleReposicion.class );
		criteria.add( Restrictions.eq( "factura", rx ) );
		criteria.add( Restrictions.eq( "numeroOrden", numeroOrden ) );
		criteria.addOrder( Order.asc( "fecha" ) );
		return criteria.list();
	}

    @Override
    public Reposicion obtenerReposicionPorFactura( String factura ){
        Criteria criteria = getSession().createCriteria( Reposicion.class );
        criteria.add( Restrictions.eq("factura", factura ) );

        return (Reposicion)criteria.uniqueResult();
    }
}

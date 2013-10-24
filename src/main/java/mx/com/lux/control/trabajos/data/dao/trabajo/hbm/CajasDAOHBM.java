package mx.com.lux.control.trabajos.data.dao.trabajo.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.trabajo.CajasDAO;
import mx.com.lux.control.trabajos.data.vo.Cajas;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "cajasDAO" )
public class CajasDAOHBM extends DAOSupport implements CajasDAO {

	@Autowired
	public CajasDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public Cajas findCajasById( Integer idCaja ) throws DAOException {
		return (Cajas) findByPrimaryKey( Cajas.class, idCaja );
	}

	@Override
	public List<?> findAll() throws DAOException {
		return null;
	}

}

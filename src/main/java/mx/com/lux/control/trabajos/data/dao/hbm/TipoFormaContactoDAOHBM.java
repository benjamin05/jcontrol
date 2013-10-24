package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.TipoFormaContactoDAO;
import mx.com.lux.control.trabajos.data.vo.TipoContacto;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository( "tipoFormaContactoDAO" )
public class TipoFormaContactoDAOHBM extends DAOSupport implements TipoFormaContactoDAO {

	@Autowired
	public TipoFormaContactoDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<TipoContacto> findAll() throws DAOException {
		return new ArrayList<TipoContacto>();
	}

	@Override
	public List<TipoContacto> obtenerTodos() throws DAOException {
		Criteria criteria = getSession().createCriteria( TipoContacto.class );
		return criteria.list();
	}

}

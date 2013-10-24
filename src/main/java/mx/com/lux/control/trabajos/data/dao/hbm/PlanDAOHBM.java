package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.PlanDAO;
import mx.com.lux.control.trabajos.data.vo.Plan;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "planDAO" )
public class PlanDAOHBM extends DAOSupport implements PlanDAO {

	@Autowired
	public PlanDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<Plan> findAll() throws DAOException {
		Criteria criteria = getSession().createCriteria( Plan.class );
		return criteria.list();
	}

}

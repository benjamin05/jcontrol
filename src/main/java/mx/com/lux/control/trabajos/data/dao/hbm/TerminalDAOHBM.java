package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.TerminalDAO;
import mx.com.lux.control.trabajos.data.vo.Terminal;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "terminalDAO" )
public class TerminalDAOHBM extends DAOSupport implements TerminalDAO {

	@Autowired
	public TerminalDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<Terminal> findAll() throws DAOException {
		Criteria criteria = getSession().createCriteria( Terminal.class );
		return criteria.list();
	}

}

package mx.com.lux.control.trabajos.data.dao.hbm;

import mx.com.lux.control.trabajos.data.dao.DAOSupport;
import mx.com.lux.control.trabajos.data.dao.ExamenDAO;
import mx.com.lux.control.trabajos.data.vo.Examen;
import mx.com.lux.control.trabajos.exception.DAOException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( "examenDAO" )
public class ExamenDAOHBM extends DAOSupport implements ExamenDAO {

	@Autowired
	public ExamenDAOHBM( @Qualifier( "sessionFactory" ) SessionFactory sessionFactory ) {
		this.setSessionFactory( sessionFactory );
	}

	@Override
	public List<?> findAll() throws DAOException {
		return findAll( Examen.class );
	}

	@Override
	public Examen obtenExamen( Integer idExamen ) throws DAOException {
		if ( idExamen != null ) {
			return (Examen) getSession().get( Examen.class, idExamen );
		}
		return null;
	}

	@Override
	public Examen obtenExamenPorTrabajo( String rx ) throws DAOException {
		if ( StringUtils.isNotBlank( rx ) && StringUtils.isNumeric( rx ) ) {
			StringBuilder sb = new StringBuilder();
			sb.append( "SELECT e FROM Examen e, Receta r, NotaVenta n, Jb j " );
			sb.append( "WHERE j.rx=:rx " );
			sb.append( "AND j.rx=n.factura " );
			sb.append( "AND n.receta=r.idReceta " );
			sb.append( "AND r.examen=e.idExamen" );
			Query query = getSession().createQuery( sb.toString() );
			query.setString( "rx", rx );
			List<?> lista = query.list();
			if ( lista != null && !lista.isEmpty() ) {
				return (Examen) lista.get( 0 );
			}
		}
		return null;
	}
}

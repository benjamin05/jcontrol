package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.exception.DAOException;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Clase de soporte para realizar consultas.
 *
 * @author programador
 */
public abstract class DAOSupport extends HibernateDaoSupport {

	private static final Logger log = Logger.getLogger( DAOSupport.class );

	public static final Integer INSERT = 1;
	public static final Integer UPDATE = 2;
	public static final Integer INSERT_OR_UPDATE = 3;
	public static final Integer DELETE = 4;

	/**
	 * Metodo encargado de guardar en base de datos.
	 *
	 * @param object Objeto ha hacer persistente
	 * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
	 */
	public void saveOnly( Object object ) throws DAOException {
		log.debug( "save" );
		try {
			getHibernateTemplate().save( object );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * Metodo encargado de guardar en base de datos.
	 *
	 * @param object Objeto ha hacer persistente
	 * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
	 */
	public void save( Object object ) throws DAOException {
		log.debug( "Save entidad: " + object.getClass().getSimpleName() );
		try {
			getHibernateTemplate().saveOrUpdate( object );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}


    /**
     * Metodo encargado de guardar en base de datos.
     *
     * @param object Objeto ha hacer persistente
     * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
     */
    public void mergeAndSave( Object object ) throws DAOException {
        log.debug( "MergeAndSave entidad: " + object.getClass().getSimpleName() );
        try {
            getHibernateTemplate().merge( object );
            //getHibernateTemplate().saveOrUpdate( object );
        } catch ( DataAccessException e ) {
            log.error( e.getMessage() );
            throw new DAOException( e.getMessage(), e );
        }
    }



	/**
	 * Metodo encargado de realizar una consult por llave primaria
	 *
	 * @param clazz DTO que representa a la entidad sobre la que se realizara la consulta
	 * @param id	Clave del identificador
	 * @return DTO encontrado
	 * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
	 */
	public Object findByPrimaryKey( Class<?> clazz, Serializable id ) throws DAOException {
		log.debug( "Get entidad: " + clazz.getSimpleName() );
		try {
			return getHibernateTemplate().get( clazz, id );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * ejecuta la <i>namedQuery</i> regrensando un unico resultado
	 *
	 * @param namedQuery named query de hibernate
	 * @throws DAOException Excepcion lanzada en caso de alg�n error de Hibernate
	 */
	public Object getUniqueResult( final String namedQuery ) throws DAOException {
		try {
			return getHibernateTemplate().execute( new HibernateCallback() {
				public Object doInHibernate( Session session ) {
					return session.getNamedQuery( namedQuery ).uniqueResult();
				}
			} );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * ejecuta la <i>namedQuery</i> regrensando un unico resultado
	 *
	 * @param namedQuery named query de hibernate
	 * @throws DAOException Excepcion lanzada en caso de alg�n error de Hibernate
	 */
	public Object getUniqueResult( final String namedQuery, final String[] parameters, final Object[] values ) throws DAOException {
		try {
			return getHibernateTemplate().execute( new HibernateCallback() {
				public Object doInHibernate( Session session ) {
					Query query = session.getNamedQuery( namedQuery );
					applyParams( query, parameters, values );
					return query.uniqueResult();
				}
			} );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		} catch( Exception e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * Metodo encargado de eliminar un registro de la base de datos
	 *
	 * @param object Registro a eliminar.
	 * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
	 */
	public void delete( Object object ) throws DAOException {
		log.debug( "Delete entidad: " + object.getClass().getSimpleName() );
		try {
			getHibernateTemplate().delete( object );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * Implementaci�n de metodo para borrado de registros utilizando un named query
	 *
	 * @param namedQuery del named query a ejecutar
	 * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
	 */
	public void delete( final String namedQuery ) throws DAOException {
		try {
			getHibernateTemplate().execute( new HibernateCallback() {
				public Object doInHibernate( Session session ) {
					return session.getNamedQuery( namedQuery ).executeUpdate();
				}

			} );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	public void delete( final String namedQuery, final String[] names, final Object[] parameters ) throws DAOException {
		try {
			getHibernateTemplate().execute( new HibernateCallback() {
				public Object doInHibernate( Session session ) {
					Query query = session.getNamedQuery( namedQuery );
					applyParams( query, names, parameters );
					return query.executeUpdate();
				}
			} );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * Implementacion de metodo para ejecutar un HQL
	 *
	 * @param HQL a ejecutar
	 * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
	 */
	public void executeHQL( final String HQL ) throws DAOException {
		try {
			getHibernateTemplate().execute( new HibernateCallback() {
				public Object doInHibernate( Session session ) {
					return session.createQuery( HQL );
				}

			} );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * Implementaci�n de metodo para actualizar un registro por medio de parametros
	 *
	 * @param namedQuery named query a ejecutar
	 * @param names	  Lista de nombre de parametros
	 * @param parameters Lista de valores de parametros
	 * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
	 */
	public void update( final String namedQuery, final String[] names, final Object[] parameters ) throws DAOException {
		try {
			getHibernateTemplate().execute( new HibernateCallback() {
				public Object doInHibernate( Session session ) {
					Query query = session.getNamedQuery( namedQuery );
					applyParams( query, names, parameters );
					return query.executeUpdate();
				}
			} );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * Implementaci�n de metodo para actualizar un registro por medio de parametros
	 *
	 * @param namedQuery named query a ejecutar
	 * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
	 */
	public void update( final String namedQuery ) throws DAOException {
		try {
			getHibernateTemplate().execute( new HibernateCallback() {
				public Object doInHibernate( Session session ) {
					Query query = session.getNamedQuery( namedQuery );
					return query.executeUpdate();
				}
			} );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * Metodo encargado de devolver todos los registros que se encuentran en la entidad.
	 *
	 * @param clazz DTO que representa a la entidad sobre la que se realizara la consulta
	 * @return Lista con todos los resultados obtenidos
	 * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
	 */
	public List<?> findAll( Class<?> clazz ) throws DAOException {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass( clazz );
			return getHibernateTemplate().findByCriteria( criteria );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * Metodo encargado de realizar la consulta utilizando un DetachedCriteria
	 *
	 * @param detachedCriteria Criteria generado para realizar la consulta
	 * @return Lista con los resultados del criteria.
	 * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
	 */
	public List<?> findByCriteria( DetachedCriteria detachedCriteria ) throws DAOException {
		try {
			return getHibernateTemplate().findByCriteria( detachedCriteria );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * Metodo encargado de realizar la consulta utilizando un DetachedCriteria de manera paginada
	 *
	 * @param detachedCriteria Criteria generado para realizar la consulta
	 * @param first			Numero de registro donde se inicia la consulta
	 * @param max			  Numero maximo de registros a consultar
	 * @return Lista con los resultados del criteria.
	 * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
	 */
	public List<?> findByCriteria( DetachedCriteria detachedCriteria, int first, int max ) throws DAOException {
		try {
			return getHibernateTemplate().findByCriteria( detachedCriteria, first, max );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * @param namedQuery
	 * @return
	 * @throws DAOException
	 */
	public void executeStoreProcedure( String namedQuery ) throws DAOException {
		executeNamedQuery( namedQuery, null );
	}

	/**
	 * @param namedQuery
	 * @param params
	 * @return
	 * @throws DAOException
	 */
	public void executeStoreProcedure( String namedQuery, Object[] params ) throws DAOException {
		executeNamedQuery( namedQuery, params );
	}

	/**
	 * @param namedQuery
	 * @param parameters
	 * @throws DAOException
	 */
	public void executeNamedQuery( final String namedQuery, final Object[] parameters ) throws DAOException {
		log.debug( "execute" );
		try {
			getHibernateTemplate().execute( new HibernateCallback() {
				public Object doInHibernate( Session session ) {
					Query query = session.getNamedQuery( namedQuery );
					for ( int i = 0; i < parameters.length; i++ ) {
						query.setParameter( i, parameters[i] );
					}
					return query.executeUpdate();
				}
			} );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * Metodo encargado de realizar una consulta por medio de una consulta nombrada (NamedQuery)
	 *
	 * @param namedQuery Nombre de la consulta a realizar
	 * @return Resultados de la consulta realizada.
	 * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
	 */
	public List<?> findByNamedQuery( String namedQuery ) throws DAOException {
		return findByNamedQuery( namedQuery, null );
	}

	/**
	 * Metodo encargado de realizar una consulta por medio de una consulta nombrada (NamedQuery) de
	 * manera paginada.
	 *
	 * @param first Numero de registro donde se inicia la consulta
	 * @param max   Numero maximo de registros a consultar
	 * @return Resultados de la consulta realizada.
	 * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
	 */
	public List<?> findByNamedQuery( final String namedQuery, final int first, final int max ) throws DAOException {
		try {
			Query query = getSession().getNamedQuery( namedQuery );
			query.setFirstResult( first );
			query.setMaxResults( max );
			return query.list();
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * Metodo encargado de realizar una consulta por medio de una consulta nombrada (NamedQuery) con
	 * parametros
	 *
	 * @param parameters Lista de valores de parametros.
	 * @return Resultados de la consulta realizada.
	 * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
	 */
	public List<?> findByNamedQuery( String namedQuery, Object[] parameters ) throws DAOException {
		try {
			return getHibernateTemplate().findByNamedQuery( namedQuery, parameters );
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * Metodo encargado de realizar una consulta por medio de una consulta nombrada (NamedQuery) con
	 * parametros nombrados.
	 *
	 * @param names	  Arreglo de nombre de parametros.
	 * @param parameters Arreglo con los valores de los parametros
	 * @return Resultados de la consulta realizada.
	 * @throws DAOException Excepcion lanzada en caso de error al persistir la transaccion.
	 */
	public List<?> findByNamedQuery( String namedQuery, String[] names, Object[] parameters ) throws DAOException {
		try {
			Query query = getSession().getNamedQuery( namedQuery );
			applyParams( query, names, parameters );
			List<?> lista = (List<?>) query.list();
			return lista;
		} catch ( DataAccessException e ) {
			log.error( e.getMessage() );
			throw new DAOException( e.getMessage(), e );
		}
	}

	/**
	 * Ejecuta una consulta por paginacion
	 *
	 * @param namedQuery
	 * @param names
	 * @param parameters
	 * @param firstResult
	 * @param pageSize
	 * @return
	 * @throws DAOException
	 */
	public List<?> findByNamedQueryPaging( final String namedQuery, final String[] names, final Object[] parameters, int firstResult, int pageSize ) throws DAOException {
		Query query = getSession().getNamedQuery( namedQuery );
		applyParams( query, names, parameters );
		query.setFirstResult( firstResult );
		query.setMaxResults( pageSize );
		List<?> lista = (List<?>) query.list();
		return lista;
	}

	/**
	 * Ejecuta una consulta por paginacion
	 *
	 * @param namedQuery
	 * @param names
	 * @param parameters
	 * @return
	 * @throws DAOException
	 */
	public int countByNamedQuery( String namedQuery, String[] names, Object[] parameters ) throws DAOException {
		Query query = getSession().getNamedQuery( namedQuery );
		if ( names != null && parameters != null ) {
			applyParams( query, names, parameters );
		}
		List<?> result = query.list();
		Integer count = Integer.valueOf( result.get( 0 ).toString() );
		return count;
	}

	/**
	 * Ejecuta una consulta por paginacion
	 *
	 * @param namedQuery
	 * @return
	 * @throws DAOException
	 */
	public int countByNamedQuery( String namedQuery ) throws DAOException {
		return countByNamedQuery( namedQuery, null, null );
	}

	private void applyParams( Query query, String[] names, Object[] parameters ) {
		for ( int i = 0; i < names.length; i++ ) {
			Object param = parameters[i];
			String name = names[i];
			if ( param instanceof Collection<?> ) {
				query.setParameterList( name, (Collection<?>) param );
			} else {
				query.setParameter( name, param );
			}
		}
	}

	/**
	 * Ejecuta save = 1 update = 2 saveOrUpdate = 3 delete = 4 en una sola transaccion
	 *
	 * @param in
	 * @param o
	 * @throws DAOException
	 */
	public void insertUpdateDelete( Integer[] in, Object[] o ) throws DAOException {
		try {
			for ( int i = 0; i < in.length; i++ ) {
				if ( in[i].equals( INSERT ) ) {
					getSession().save( o[i] );
				}
				if ( in[i].equals( UPDATE ) ) {
					getSession().update( o[i] );
				}
				if ( in[i].equals( INSERT_OR_UPDATE ) ) {
					getSession().saveOrUpdate( o[i] );
				}
				if ( in[i].equals( DELETE ) ) {
                    if( o[i] != null ){
                        getSession().delete( o[i] );
                    }
				}
			}
		} catch ( Exception e ) {
			log.error( e.getMessage() );
		}
	}
}

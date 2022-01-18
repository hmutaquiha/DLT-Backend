package dlt.dltbackendmaster.repository;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.lang.annotation.Annotation;
import java.util.Iterator;

import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * This class implements the DAO Repository Interface
 * @author derciobucuane
 *
 */
@Repository
public class DAORepositoryImpl implements DAORepository{

	@Autowired
	private SessionFactory sessionFactory;

	protected  Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getAll(Class<T> klass) {
		return getCurrentSession().createQuery("from " + klass.getName())
				.list();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getAllQuery(String s) {
		return getCurrentSession().createQuery(s)
				.list();
	}

	public <T> void update(T klass) {
		getCurrentSession().merge(klass);
	}

	public <T> boolean exist(T klass) {
		return getCurrentSession().contains(klass);
	}

	@SuppressWarnings("rawtypes")
	public <T> int updateQuery(String query, Object... params) {
		NativeQuery q = getCurrentSession().createNativeQuery(query);
		int i = 0;
		for (Object o : params) {
			q.setParameter(i, o);
			i++;
		}
		int r = q.executeUpdate();
		return r;
	}

	public <T> int count(Class<T> klass) {
		Long l = (Long) getCurrentSession().createQuery("select count(c) from " + klass.getName()+" c")
				.uniqueResult();
		return l.intValue();
	}

	@SuppressWarnings("unchecked")
	public <T> T Save(T klass) {
		return (T)getCurrentSession().save(klass);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T GetUniqueEntityByNamedQuery(String query, Object... params) {
		Query q = getCurrentSession().getNamedQuery(query);
		int i = 0;

		for (Object o : params) {
			q.setParameter(i, o);
			i++;//new
		}

		List<T> results = q.list();

		T foundentity = null;
		if (!results.isEmpty()) {
			// ignores multiple results
			foundentity = results.get(0);
		}
		return foundentity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> GetAllEntityByNamedQuery(String query, Object... params) {
		Query q = getCurrentSession().getNamedQuery(query);
		int i = 0;

		for (Object o : params) {
			q.setParameter(i, o);
			i++;//new
		}

		List<T> results = q.list();

		return results;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findByQuery(String hql, Map<String, Object> entidade, Map<String, Object> namedParams) {
		NativeQuery query = getCurrentSession().createSQLQuery(hql);
		if (entidade != null) {
			Entry mapEntry;
			for (Iterator it = entidade.entrySet().iterator(); it
					.hasNext(); query.addEntity(
							(String) mapEntry.getKey(), (Class) mapEntry.getValue())) {
				mapEntry = (Entry) it.next();
			}
		}
		if (namedParams != null) {
			Entry mapEntry;
			for (Iterator it = namedParams.entrySet().iterator(); it
					.hasNext(); query.setParameter(
							(String) mapEntry.getKey(), mapEntry.getValue())) {
				mapEntry = (Entry) it.next();
			}
		}
		List returnList = query.list();

		return returnList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findByQueryFilter(String hql, Map<String, Object> entidade, Map<String, Object> namedParams,
			int f, int m) {
		NativeQuery query = getCurrentSession().createSQLQuery(hql);
		if (entidade != null) {
			Entry mapEntry;
			for (Iterator it = entidade.entrySet().iterator(); it
					.hasNext(); query.addEntity(
							(String) mapEntry.getKey(), (Class) mapEntry.getValue())) {
				mapEntry = (Entry) it.next();
			}
		}
		if (namedParams != null) {
			Entry mapEntry;
			for (Iterator it = namedParams.entrySet().iterator(); it
					.hasNext(); query.setParameter(
							(String) mapEntry.getKey(), mapEntry.getValue())) {
				mapEntry = (Entry) it.next();
			}
		}
		query.setFirstResult(f);
		query.setMaxResults(m);
		List returnList = query.list();

		return returnList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findByJPQuery(String hql, Map<String, Object> namedParams) {
		Query query = getCurrentSession().createQuery(hql);
		if (namedParams != null) {
			Entry mapEntry;
			for (Iterator it = namedParams.entrySet().iterator(); it
					.hasNext(); query.setParameter(
							(String) mapEntry.getKey(), mapEntry.getValue())) {
				mapEntry = (Entry) it.next();
			}
		}
		List returnList = query.list();

		return returnList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findByJPQueryFilter(String hql, Map<String, Object> namedParams, int f, int m) {
		Query query = getCurrentSession().createQuery(hql);
		if (namedParams != null) {
			Entry mapEntry;
			for (Iterator it = namedParams.entrySet().iterator(); it
					.hasNext(); query.setParameter(
							(String) mapEntry.getKey(), mapEntry.getValue())) {
				mapEntry = (Entry) it.next();
			}
		}
		query.setFirstResult(f);
		query.setMaxResults(m);
		List returnList = query.list();

		return returnList;
	}


	public <T> void delete(T klass) {
		getCurrentSession().delete(klass);
	}


	public Class<? extends Annotation> annotationType() {
		// TODO Auto-generated method stub
		return null;
	}

	public String value() {
		// TODO Auto-generated method stub
		return null;
	}

}

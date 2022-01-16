package dlt.dltbackendmaster.service;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dlt.dltbackendmaster.repository.Repository;

/**
 * This class implements the Service interface
 * @author derciobucuane
 *
 */
@Service
public class ServiceImpl implements Service{

	@Autowired
	private Repository repository;

	@Transactional(readOnly = true)
	public <T> List<T> getAll(Class<T> klass) {
		return repository.getAll(klass);
	}

	@Transactional(readOnly = true)
	public <T> List<T> getAllQuery(String s) {
		return repository.getAllQuery(s);
	}

	@Transactional
	public <T> void Save(T klass) {
		repository.Save(klass);
	}

	@Transactional
	public <T> void delete(T klass) {
		repository.delete(klass);
	}

	@Transactional
	public <T> void update(T klass) {
		repository.update(klass);
	}

	@Transactional
	public <T> int updateQuery(String query, Object... params) {
		return repository.updateQuery(query, params);
	}

	@Transactional
	public <T> int count(Class<T> klass) {
		return repository.count(klass);
	}

	@Transactional
	public <T> boolean exist(T klass) {
		return repository.exist(klass);
	}

	@Transactional
	public <T> T GetUniqueEntityByNamedQuery(String query, Object... params) {
		return repository.GetUniqueEntityByNamedQuery(query, params);
	}

	@Transactional
	public <T> List<T> GetAllEntityByNamedQuery(String query, Object... params) {
		return repository.GetAllEntityByNamedQuery(query, params);
	}

	@Transactional
	public <T> List<T> findByQuery(String hql, Map<String, Object> entidade, Map<String, Object> namedParams) {
		return repository.findByQuery(hql, entidade, namedParams);
	}

	@Transactional
	public <T> List<T> findByQueryFilter(String hql, Map<String, Object> entidade, Map<String, Object> namedParams,
			int f, int m) {
		return repository.findByQueryFilter(hql, entidade, namedParams, f, m);
	}

	@Transactional
	public <T> List<T> findByJPQuery(String hql, Map<String, Object> namedParams) {
		return repository.findByJPQuery(hql, namedParams);
	}

	@Transactional
	public <T> List<T> findByJPQueryFilter(String hql, Map<String, Object> namedParams, int f, int m) {
		return repository.findByJPQueryFilter(hql, namedParams, f, m);
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

package dlt.dltbackendmaster.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This interface represents the integrated Repository for all domains 
 * @author derciobucuane
 *
 */
public interface DAORepository {
	<T> List<T> getAll(Class<T> klass);

	<T> List<T> getAllQuery(String s);

	<T> T update(T klass);

	<T> boolean exist(T klass);

	<T> int updateQuery(String query, Object... params);

	<T> int count(Class<T> klass);

	<T> Serializable Save(T klass);

	<T> T GetUniqueEntityByNamedQuery(String query, Object... params);

	<T> T GetUniqueEntityByNamedQuery(String query, String searchNui, String searchName, Integer searchUserCreator, Integer searchDistrict, Object... params);

	<T> T GetUniqueUserEntityByNamedQuery(String query, String searchName, String searchUsername, Integer searchUserCreator, Integer searchDistrict, Object... params);

	<T> T GetUniqueUserEntityByNamedQuery(String query, String searchName, String searchUsername, Integer searchUserCreator, Integer searchDistrict, Integer searchEntryPoint, Object... params);

	<T> T GetUniqueEntityByNamedQuery(String query, String searchNui, Integer searchUserCreator, Integer searchDistrict, Date searchStartDate, Date searchEndDate, Object... params);

	// <T> T GetUniqueEntityByNamedQuery(String query,String searchStartDate, String searchEndDate, Object... params);

	<T> List<T> GetAllEntityByNamedQuery(String query, Object... params);
	
	<T> List<T> GetEntityByNamedQuery(String query, int beneficiaryId, List<Integer> servicesIds );
	
	<T> List<T> GetEntityByNamedQuery(String query, Integer beneficiaryId, Integer ageBand, Integer level);
	
	public <T> List<T> GetEntityByNamedQuery(String query, String name, Date dateOfBirth, int locality);
	
	<T> List<T> GetAllPagedEntityByNamedQuery(String query, int pageIndex, int pageSize,String searchNui, String searchName, Integer searchUserCreator, Integer searchDistrict , Object... params);
	
	<T> List<T> GetAllPagedEntityByNamedQuery(String query, int pageIndex, int pageSize,String searchNui, Integer searchUserCreator, Integer searchDistrict, Date searchStartDate, Date searchEndDate, Object... params);

	<T> List<T> GetAllPagedUserEntityByNamedQuery(String query, int pageIndex, int pageSize, String searchName, String searchUsername, Integer searchUserCreator, Integer searchDistrict, Object... params);

	<T> List<T> GetAllPagedUserEntityByNamedQuery(String query, int pageIndex, int pageSize, String searchName, String searchUsername, Integer searchUserCreator, Integer searchDistrict, Integer searchEntryPoint, Object... params);

    <T> List<T> GetAllPagedEntityByNamedQuery(String query, int pageIndex, int pageSize, Date searchStartDate, Date searchEndDate, Object... params);
	
	<T> List<T> GetByParamsPagedEntityByNamedQuery(String query, int pageIndex, int pageSize, Object... params);
	
	<T> List<T> GetAllEntityByNamedNativeQuery(String query, Object... params);

	<T> List<T> GetByNamedNativeQuery(String query, Integer district, Date startDate, Date endDate, Object... params);

	<T> List<T> GetByNamedNativeQuery(String query, Integer district, String startDate, String endDate, Object... params);
	
	<T> List<T> GetAllPagedEntityByNamedNativeQuery(String query, int pageIndex, int pageSize, Date startDate, Date endDate, List<Integer>districts, Object... params);
	
	<T> List<T> GetAllPagedEntityByNamedNativeQuery(String query, int pageIndex, int pageSize, String startDate, String endDate, List<Integer>districts, Object... params);

	<T> T find(Class<T> klass, Object id);

	<T> List<T> findByQuery(String hql, Map<String, Object> entidade, Map<String, Object> namedParams);

	<T> List<T> findByQueryFilter(String hql, Map<String, Object> entidade, Map<String, Object> namedParams, int f, int m);

	<T> List<T> findByJPQuery(String hql,Map<String, Object> namedParams);

	<T> List<T> findByJPQueryFilter(String hql, Map<String, Object> namedParams, int f, int m);

	<T> void delete(T klass);
	
	<T> int UpdateEntitiesByNamedQuery(String query, Object... params);
}

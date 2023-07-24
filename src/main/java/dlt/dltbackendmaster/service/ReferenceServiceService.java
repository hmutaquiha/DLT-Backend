package dlt.dltbackendmaster.service;

import java.util.List;

import dlt.dltbackendmaster.domain.ReferencesServices;

/**
 * This interface holds the service implementations called by the API controller
 * 
 * @author Francisco da Conceição Alberto Macuácua
 *
 */
public interface ReferenceServiceService {

	List<ReferencesServices> findByReferenceId(Integer referenceId);

}

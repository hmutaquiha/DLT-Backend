package dlt.dltbackendmaster.service;

import java.util.List;

import dlt.dltbackendmaster.domain.BeneficiariesInterventions;
import dlt.dltbackendmaster.domain.ReferencesServicesObject;

/**
 * This interface holds the service implementations called by the API controller
 * 
 * @author Francisco da Conceição Alberto Macuácua
 *
 */
public interface BeneficiariyInterventionService {

	<T> ReferencesServicesObject registerServiceCompletionStatus(BeneficiariesInterventions intervention);

	List<BeneficiariesInterventions> findByBeneficiaryId(Integer beneficiaryId);
}

package dlt.dltbackendmaster.service;

import java.util.Date;
import java.util.List;

import dlt.dltbackendmaster.domain.UsersBeneficiariesCustomSync;

/**
 * This interface holds the service implementations called by the API controller
 * 
 * @author Francisco da Conceição Alberto Macuácua
 *
 */
public interface UsersBeneficiariesCustomSyncService {

	List<UsersBeneficiariesCustomSync> getUserBeneficiariesSync(Integer userId);

	List<UsersBeneficiariesCustomSync> getUserBeneficiariesSyncByDateCreated(Integer userId, Date dateCreated);

	List<UsersBeneficiariesCustomSync> getUserBeneficiariesSyncByDateUpdated(Integer userId, Date dateUpdated);
	
	List<UsersBeneficiariesCustomSync> findByUserIdAndBeneficiaryId(Integer userId, Integer BeneficiaryId);
}

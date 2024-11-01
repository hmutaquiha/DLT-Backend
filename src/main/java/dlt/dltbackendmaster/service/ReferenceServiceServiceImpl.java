package dlt.dltbackendmaster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dlt.dltbackendmaster.domain.ReferencesServices;

/**
 * This class implements the Service interface
 * 
 * @author Francisco da Conceição Alberto Macuácua
 *
 */
@Service
public class ReferenceServiceServiceImpl implements ReferenceServiceService {
	@Autowired
	public DAOService daoService;

	@Override
	public List<ReferencesServices> findByReferenceId(Integer referenceId) {

		List<ReferencesServices> referencesServices = daoService
				.GetAllEntityByNamedQuery("ReferencesServices.findByReference", referenceId);

		return referencesServices;
	}

	@Override
	public ReferencesServices findByReferenceIdAndServiceId(Integer referenceId, Integer serviceId) {

		ReferencesServices referencesServices = daoService
				.GetUniqueEntityByNamedQuery("ReferencesServices.findByReferenceIdAndServiceId", referenceId, serviceId);

		return referencesServices;
	}
}

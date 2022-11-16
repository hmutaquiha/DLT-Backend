package dlt.dltbackendmaster.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Beneficiaries;
import dlt.dltbackendmaster.domain.BeneficiariesInterventions;
import dlt.dltbackendmaster.domain.References;
import dlt.dltbackendmaster.domain.ReferencesServices;
import dlt.dltbackendmaster.domain.ReferencesServicesObject;
import dlt.dltbackendmaster.domain.SubServices;
import dlt.dltbackendmaster.domain.Us;
import dlt.dltbackendmaster.security.utils.ServiceCompletionRules;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/beneficiary-intervention")
public class BeneficiaryInterventionController
{
    private final DAOService service;

    public BeneficiaryInterventionController(DAOService service) {
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<BeneficiariesInterventions>> getAll() {

        try {
            List<BeneficiariesInterventions> interventions = service.getAll(BeneficiariesInterventions.class);
            return new ResponseEntity<>(interventions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ReferencesServicesObject> save(@RequestBody BeneficiariesInterventions intervention) {

        if (intervention.getId() == null || intervention.getId().getDate() == null || intervention.getUs() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
        	Us us = service.find(Us.class, intervention.getUs().getId());
        	intervention.setUs(us);
            intervention.setDateCreated(new Date());
            service.Save(intervention);

            SubServices subService = service.find(SubServices.class, intervention.getId().getSubServiceId());
            intervention.setSubServices(subService);

            // Actualizar o status dos serviços solicitados
            Beneficiaries beneficiary = service.find(Beneficiaries.class, intervention.getBeneficiaries().getId());
            Integer serviceId = subService.getServices().getId();
            List<ReferencesServices> referencesServices = service.GetAllEntityByNamedQuery("ReferencesServices.findByBeneficiaryAndService",
                                                                                           beneficiary.getId(),
                                                                                           serviceId);

            List<References> updatedReferences = new ArrayList<>();

            for (ReferencesServices referenceServices : referencesServices) {

                Integer referenceServiceStatus = ServiceCompletionRules.getReferenceServiceStatus(beneficiary,
                                                                                                  serviceId);

                if (referenceServiceStatus.intValue() != (referenceServices.getStatus())) {
                    referenceServices.setStatus(referenceServiceStatus);
                    referenceServices.setDateUpdated(new Date());
                    referenceServices.setUpdatedBy(subService.getCreatedBy());
                    referenceServices = service.update(referenceServices);

                    // Actualizar o status da referências
                    References reference = referenceServices.getReferences();
                    Integer referenceStatus = ServiceCompletionRules.getReferenceStatus(reference);

                    if (referenceStatus.intValue() != reference.getStatus()) {
                        reference.setStatus(referenceStatus);
                        reference.setDateUpdated(new Date());
                        reference.setUpdatedBy(subService.getCreatedBy());
                        reference = service.update(reference);
                    }
                    updatedReferences.add(reference);
                }
            }
            ReferencesServicesObject referenceServiceObject = new ReferencesServicesObject(intervention,
                                                                                           updatedReferences);
            return new ResponseEntity<>(referenceServiceObject, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("rawtypes")
	@PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity update(@RequestBody BeneficiariesInterventions intervention) {

        if (intervention.getId() == null || intervention.getId().getDate() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {

            BeneficiariesInterventions currentIntervention = service.find(BeneficiariesInterventions.class,
                                                                          intervention.getId());

            if (currentIntervention == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            // if key was updated, entry should be disabled and a new one added
            if (intervention.getId().getBeneficiaryId() != intervention.getBeneficiaries().getId()
                || intervention.getId().getSubServiceId() != intervention.getSubServices().getId()
                || !intervention.getId().getDate().equals(intervention.getDate())) {

                currentIntervention.setStatus(0);
                currentIntervention.setDateUpdated(new Date());
                service.delete(currentIntervention); // Must remove to allow new additions with this key afterwards

                BeneficiariesInterventions newInterv = new BeneficiariesInterventions();
                newInterv.getId().setBeneficiaryId(intervention.getBeneficiaries().getId());
                newInterv.getId().setSubServiceId(intervention.getSubServices().getId());
                newInterv.getId().setDate(intervention.getDate());
                newInterv.setSubServices(intervention.getSubServices());
                newInterv.setBeneficiaries(intervention.getBeneficiaries());
                newInterv.setRemarks(intervention.getRemarks());
                newInterv.setEntryPoint(intervention.getEntryPoint());
                newInterv.setResult(intervention.getResult());
                newInterv.setUs(intervention.getUs());
                newInterv.setProvider(intervention.getProvider());
                newInterv.setStatus(intervention.getStatus());
                newInterv.setActivistId(intervention.getActivistId());
                newInterv.setCreatedBy(currentIntervention.getCreatedBy());
                newInterv.setDateCreated(currentIntervention.getDateCreated());
                newInterv.setDateUpdated(new Date());
                newInterv.setUpdatedBy(intervention.getUpdatedBy());

                service.Save(newInterv);

                SubServices subService = service.find(SubServices.class, newInterv.getId().getSubServiceId());
                newInterv.setSubServices(subService);

                return new ResponseEntity<>(newInterv, HttpStatus.OK);
            }

            // Edit fields

            currentIntervention.setRemarks(intervention.getRemarks());
            currentIntervention.setUs(intervention.getUs());
            currentIntervention.setEntryPoint(intervention.getEntryPoint());
            currentIntervention.setResult(intervention.getResult());
            currentIntervention.setProvider(intervention.getProvider());
            currentIntervention.setStatus(intervention.getStatus());
            currentIntervention.setActivistId(intervention.getActivistId());
            currentIntervention.setDateUpdated(new Date());
            currentIntervention.setUpdatedBy(intervention.getUpdatedBy());

            BeneficiariesInterventions updatedIntervention = service.update(currentIntervention);
            return new ResponseEntity<>(updatedIntervention, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

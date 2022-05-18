package dlt.dltbackendmaster.controller;

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

import dlt.dltbackendmaster.domain.BeneficiaryIntervention;
import dlt.dltbackendmaster.domain.SubService;
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
    public ResponseEntity<List<BeneficiaryIntervention>> getAll() {

        try {
            List<BeneficiaryIntervention> interventions = service.getAll(BeneficiaryIntervention.class);
            return new ResponseEntity<>(interventions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<BeneficiaryIntervention> save(@RequestBody BeneficiaryIntervention intervention) {

        if (intervention == null || intervention.getBeneficiary() == null || intervention.getSubService() == null
            || intervention.getUs_id() == null) {
        	System.out.println(intervention.getBeneficiary());
        	System.out.println(intervention.getSubService());
        	System.out.println(intervention.getUs_id());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            intervention.setDateCreated(new Date());
            BeneficiaryIntervention createdIntervention = (BeneficiaryIntervention) service.Save(intervention);
            SubService subService = service.find(SubService.class, intervention.getSubService().getId());
            createdIntervention.setSubService(subService);
            return new ResponseEntity<>(createdIntervention, HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<BeneficiaryIntervention> update(@RequestBody BeneficiaryIntervention intervention) {

        if (intervention == null || intervention.getBeneficiary() == null || intervention.getSubService() == null
            || intervention.getUs_id() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            intervention.setDateUpdated(new Date());
            BeneficiaryIntervention updatedIntervention = service.update(intervention);
            return new ResponseEntity<>(updatedIntervention, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

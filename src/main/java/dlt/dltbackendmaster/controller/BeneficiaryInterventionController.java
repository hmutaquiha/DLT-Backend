package dlt.dltbackendmaster.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.BeneficiaryIntervention;
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
}

package dlt.dltbackendmaster.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Beneficiary;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController
{
    private final DAOService service;

    public BeneficiaryController(DAOService service) {
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Beneficiary>> getAll() {

        try {
            List<Beneficiary> beneficiaries = service.getAll(Beneficiary.class);
            return new ResponseEntity<>(beneficiaries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Beneficiary> get(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            Beneficiary beneficiary = service.find(Beneficiary.class, id);
            return new ResponseEntity<>(beneficiary, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

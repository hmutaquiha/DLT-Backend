package dlt.dltbackendmaster.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.SubService;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/subservices")
public class SubServiceController
{
    private final DAOService service;

    public SubServiceController(DAOService service) {
        super();
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<SubService>> getAll() {

        try {
            List<SubService> subServices = service.getAll(SubService.class);
            return new ResponseEntity<>(subServices, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{serviceId}", produces = "application/json")
    public ResponseEntity<List<SubService>> get(@PathVariable Integer serviceId) {

        if (serviceId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            List<SubService> subServices = service.GetAllEntityByNamedQuery("SubService.findByService", serviceId);
            return new ResponseEntity<>(subServices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

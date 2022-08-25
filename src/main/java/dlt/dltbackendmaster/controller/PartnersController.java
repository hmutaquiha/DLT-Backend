package dlt.dltbackendmaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Partners;
import dlt.dltbackendmaster.domain.ServiceType;
import dlt.dltbackendmaster.domain.Services;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/partners")
public class PartnersController {

	@Autowired
	private final DAOService service;

	public PartnersController(DAOService service) {
		this.service = service;
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Partners>> getAll() {
		try {
			List<Partners> partners = service.getAll(Partners.class);

			return new ResponseEntity<>(partners, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Partners> get(@PathVariable Integer id){
		if(id == null ) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}	
		
		try {
			Partners partner = service.find(Partners.class, id);

			return new ResponseEntity<>(partner, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 
	@GetMapping(path = "/{partnerType}", produces = "application/json")
    public ResponseEntity<List<Partners>> get(@PathVariable ServiceType partnerType) {

        if (partnerType == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            List<Partners> partners = service.GetAllEntityByNamedQuery("Partners.findByPartnerType", String.valueOf(partnerType.ordinal()+1));
            return new ResponseEntity<>(partners, HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping(path = "/{partnerType}/{district}", produces = "application/json")
    public ResponseEntity<List<Partners>> getByTypeDistrict(@PathVariable ServiceType partnerType, @PathVariable Integer district) {

        if (partnerType == null || district == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            List<Partners> partners = service.GetAllEntityByNamedQuery("Partners.findByTypeDistrict", String.valueOf(partnerType.ordinal()+1), district);
            return new ResponseEntity<>(partners, HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

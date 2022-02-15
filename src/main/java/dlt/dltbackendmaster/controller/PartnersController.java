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
			List<Partners> localities = service.getAll(Partners.class);

			return new ResponseEntity<>(localities, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<Partners> get(@PathVariable Integer id){
		if(id == null ) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}	
		
		try {
			Partners Partners = service.find(Partners.class, id);

			return new ResponseEntity<>(Partners, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

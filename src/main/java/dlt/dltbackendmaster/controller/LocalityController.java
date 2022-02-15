package dlt.dltbackendmaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Locality;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/localities")
public class LocalityController {

	@Autowired
	private final DAOService service;

	public LocalityController(DAOService service) {
		this.service = service;
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Locality>> getAll() {
		try {
			List<Locality> localities = service.getAll(Locality.class);

			return new ResponseEntity<>(localities, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<Locality> get(@PathVariable Integer id){
		if(id == null ) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}	
		
		try {
			Locality locality = service.find(Locality.class, id);

			return new ResponseEntity<>(locality, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

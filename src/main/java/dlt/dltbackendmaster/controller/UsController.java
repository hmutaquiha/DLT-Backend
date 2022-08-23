package dlt.dltbackendmaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.ReferencesServices;
import dlt.dltbackendmaster.domain.Us;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/us")
public class UsController {

	@Autowired
	private final DAOService service;

	public UsController(DAOService service) {
		this.service = service;
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Us>> getAll() {
		try {
			List<Us> uss = service.getAll(Us.class);

			return new ResponseEntity<>(uss, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Us> get(@PathVariable Integer id){
		if(id == null ) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}	
		
		try {
			Us us = service.find(Us.class, id);

			return new ResponseEntity<>(us, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/type/{typeId}/{localityId}", produces = "application/json")
	public ResponseEntity<List<Us>> getByUsType(@PathVariable Integer typeId, @PathVariable Integer localityId){
		
		if(typeId == null || localityId == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}	
		
		try {
			System.out.println("idt: "+typeId);
			System.out.println("idt: "+localityId);
			List<Us> uss = service.GetAllEntityByNamedQuery("Us.findByType", localityId, typeId);
			
			return new ResponseEntity<>(uss, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

package dlt.dltbackendmaster.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.SubServices;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/subservices")
public class SubServiceController {
	private final DAOService service;

	public SubServiceController(DAOService service) {
		super();
		this.service = service;
	}

	@GetMapping(path = "/all", produces = "application/json")
	public ResponseEntity<List<SubServices>> findAll() {
		try {
			List<SubServices> services = service.getAll(SubServices.class);
			return new ResponseEntity<>(services, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<SubServices>> getAll() {

		try {
			List<SubServices> subServices = service.GetAllEntityByNamedQuery("SubService.findAll");
			return new ResponseEntity<>(subServices, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/{serviceId}", produces = "application/json")
	public ResponseEntity<List<SubServices>> get(@PathVariable Integer serviceId) {

		if (serviceId == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			List<SubServices> subServices = service.GetAllEntityByNamedQuery("SubService.findByService", serviceId);
			return new ResponseEntity<>(subServices, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<SubServices> save(@RequestBody SubServices subService) {
		if (subService == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			subService.setDateCreated(new Date());
			subService.setHidden(0);
			subService.setHidden(0);
			subService.setSortOrder(0);
			Integer subServiceId = (Integer) this.service.Save(subService);
			SubServices savedSubService = this.service.find(SubServices.class, subServiceId);

			return new ResponseEntity<>(savedSubService, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<SubServices> update(@RequestBody SubServices subService) {
		if (subService == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			subService.setDateUpdated(new Date());
			SubServices updatedSubService = this.service.update(subService);
			return new ResponseEntity<>(updatedSubService, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}

package dlt.dltbackendmaster.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.ReferencesServices;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.service.ReferenceServiceService;

@RestController
@RequestMapping("/api/reference-service")
public class ReferenceServiceController {
	private final ReferenceServiceService referenceServiceService;
    private final DAOService service;

	public ReferenceServiceController(ReferenceServiceService referenceServiceService, DAOService service) {
		this.referenceServiceService = referenceServiceService;
		this.service = service;
	}

	@GetMapping(path = "/byReferenceId/{referenceId}", produces = "application/json")
	public ResponseEntity<List<ReferencesServices>> getByBeneficiaryId(@PathVariable Integer referenceId) {
		try {
			List<ReferencesServices> interventions = referenceServiceService.findByReferenceId(referenceId);
			return new ResponseEntity<>(interventions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "/decline/{referenceId}/{serviceId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ReferencesServices> refuse(@PathVariable Integer referenceId,
			@PathVariable Integer serviceId) {

		if (referenceId == null || serviceId == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			// setting interventions counts from database before update
			ReferencesServices referencesService = referenceServiceService.findByReferenceIdAndServiceId(referenceId,serviceId);
			referencesService.setStatus(3);

			ReferencesServices referencesServiceUpdated = service.update(referencesService);

			return new ResponseEntity<>(referencesServiceUpdated, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

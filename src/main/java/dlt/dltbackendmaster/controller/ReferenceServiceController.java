package dlt.dltbackendmaster.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.ReferencesServices;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.service.ReferenceServiceService;

@RestController
@RequestMapping("/api/reference-service")
public class ReferenceServiceController {
	private final ReferenceServiceService referenceServiceService;

	public ReferenceServiceController(ReferenceServiceService referenceServiceService) {
		this.referenceServiceService = referenceServiceService;
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
}

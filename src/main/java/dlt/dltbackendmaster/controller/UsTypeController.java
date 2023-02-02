package dlt.dltbackendmaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.UsType;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/ustypes")
public class UsTypeController {

	@Autowired
	private final DAOService service;

	public UsTypeController(DAOService service) {
		this.service = service;
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<UsType>> getAll() {
		try {
			List<UsType> ustype = service.getAll(UsType.class);

			return new ResponseEntity<>( ustype, HttpStatus.OK );
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

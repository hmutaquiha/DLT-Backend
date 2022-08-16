package dlt.dltbackendmaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.District;
import dlt.dltbackendmaster.service.DAOService;

/**
 * API REST Controller Class for Province entity
 * @author gerzeliosaide
 *
 */
@RestController
@RequestMapping("/api")
public class DistrictController {
	
    private final DAOService service;
    
    @Autowired
    public DistrictController(DAOService service) {
    	this.service = service;
    }
		
	@GetMapping("/districts")
	public ResponseEntity<List<District>> getDistricts(){
		try {			
			List<District> districts = service.GetAllEntityByNamedQuery("District.findAll");
			return new ResponseEntity<>(districts, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}

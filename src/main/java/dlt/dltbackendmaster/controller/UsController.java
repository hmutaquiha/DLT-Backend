package dlt.dltbackendmaster.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.District;
import dlt.dltbackendmaster.domain.Locality;
import dlt.dltbackendmaster.domain.Province;
import dlt.dltbackendmaster.domain.ReferencesServices;
import dlt.dltbackendmaster.domain.Us;
import dlt.dltbackendmaster.domain.Users;
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
			List<Us> uss = service.GetAllEntityByNamedQuery("Us.findByType", localityId, typeId);
			
			return new ResponseEntity<>(uss, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/typeUser/{userId}/{typeId}", produces = "application/json")
	public ResponseEntity<List<Us>> getByUsUser(@PathVariable Integer userId, @PathVariable Integer typeId){
		
		if(typeId == null || userId == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}	
		
		try {
			Users user = service.find(Users.class, userId);
			
			if(user.getLocalities().size() > 0) {
				List<Integer> localitiesId = user.getLocalities().stream().map(Locality::getId).collect(Collectors.toList());
				
				List<Us> us = service.GetAllEntityByNamedQuery("Us.findByEPLocalities", localitiesId, typeId);	
				return new ResponseEntity<>(us, HttpStatus.OK);
				
			} else if(user.getDistricts().size() > 0) {
				
				List<Integer> districtsId = user.getDistricts().stream().map(District::getId).collect(Collectors.toList());
				
				List<Us> us = service.GetAllEntityByNamedQuery("Us.findByEPDistrict", districtsId, typeId);	
				return new ResponseEntity<>(us, HttpStatus.OK);
				
			}else if(user.getProvinces().size() > 0) {
				
				List<Integer> provincesId = user.getProvinces().stream().map(Province::getId).collect(Collectors.toList());
				
				List<Us> us = service.GetAllEntityByNamedQuery("Us.findByEPProvince", provincesId, typeId);	
				return new ResponseEntity<>(us, HttpStatus.OK);
				
			}else if(user.getProfiles().getId() == 1) {
				
				List<Us> us = service.getAll(Us.class);
				return new ResponseEntity<>(us, HttpStatus.OK);
			}
						
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

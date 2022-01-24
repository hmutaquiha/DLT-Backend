package dlt.dltbackendmaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final DAOService service;

	@Autowired
	public UserController(DAOService service) {
		this.service = service;
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Users>> getProvinces(){
		List<Users> users = service.getAll(Users.class);
		return ResponseEntity.ok(users);
		
	}
}

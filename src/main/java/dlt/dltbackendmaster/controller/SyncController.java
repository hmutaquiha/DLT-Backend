package dlt.dltbackendmaster.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.serializers.SyncSerializer;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/sync")
public class SyncController {
	
	private final DAOService service;
	
	@Autowired
    public SyncController(DAOService service) {
        this.service = service;
    }
	
	@GetMapping(produces = "application/json")
	public ResponseEntity get(@RequestParam(name = "lastPulledAt") String lastPulledAt) throws ParseException {
		System.out.println("Received Request!" + lastPulledAt);
		Date validatedDate;
		if(lastPulledAt.equals("null") || lastPulledAt == null) {
			lastPulledAt = "2022-03-27";
			validatedDate = new SimpleDateFormat("yyyy-MM-dd").parse(lastPulledAt);
		}else {
			Long t = Long.valueOf(lastPulledAt);
			validatedDate = new Date(t);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = dateFormat.format(validatedDate);
			System.out.println("Date: " + strDate);
		}
		
		Map<String, Object> todo = new HashMap<String, Object>();
        todo.put("lastpulledat", validatedDate);
        
        List<Users> users = service.findByJPQuery("select u from Users u where u.dateCreated > :lastpulledat", todo);
        
        List<Users> usersupdated = service.findByJPQuery("select u from Users u where u.dateUpdated > :lastpulledat", todo);
        try {
        	String object = SyncSerializer.createUsersSyncObject(users, usersupdated, new ArrayList<Integer>());
        	System.out.println(object);
        	return new ResponseEntity<>(object, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity post(@RequestBody String changes) throws ParseException {
		
		System.out.println("pull request received!");
        return new ResponseEntity<>(changes, HttpStatus.OK);
	}
	
	
}

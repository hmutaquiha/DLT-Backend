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
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.domain.watermelondb.SyncObject;
import dlt.dltbackendmaster.domain.watermelondb.UsersSyncModel;
import dlt.dltbackendmaster.serializers.SyncSerializer;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/sync")
public class SyncController {
	private static final String QUERY_FIND_ALL_USERS = "select u from Users u";
	private static final String QUERY_FIND_USERS_BY_CREATED_DATE = "select u from Users u where u.dateCreated > :lastpulledat";
	private static final String QUERY_FIND_USERS_BY_UPDATED_DATE = "select u from Users u where u.dateUpdated > :lastpulledat";
	
	private final DAOService service;
	
	@Autowired
    public SyncController(DAOService service) {
        this.service = service;
    }
	
	@GetMapping(produces = "application/json")
	public ResponseEntity get(@RequestParam(name = "lastPulledAt", required=false) @Nullable String lastPulledAt,
								@RequestParam(name = "username") String username) throws ParseException {

		Date validatedDate;
		Map<String, Object> params = new HashMap<String, Object>();
		List<Users> usersCreated;
		List<Users> usersUpdated;
		
		if(lastPulledAt.equals("null") || lastPulledAt == null ) {

			
			usersCreated = service.findByJPQuery(QUERY_FIND_ALL_USERS, params);
			usersUpdated = new ArrayList<Users>();
			
		}else {

			
			Long t = Long.valueOf(lastPulledAt);
			validatedDate = new Date(t);
			
			params.put("lastpulledat", validatedDate);
			usersCreated = service.findByJPQuery(QUERY_FIND_USERS_BY_CREATED_DATE, params);
			usersUpdated = service.findByJPQuery(QUERY_FIND_USERS_BY_UPDATED_DATE, params);
			
		}
		
		try {
        	String object = SyncSerializer.createUsersSyncObject(usersCreated, usersUpdated, new ArrayList<Integer>());

        	return new ResponseEntity<>(object, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			return new ResponseEntity<>("Parameter not present", HttpStatus.BAD_REQUEST);
		}
        
		
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity post(@RequestBody String changes,
								@RequestParam(name = "username") String username) throws ParseException {
		
		SyncObject users;
		try {
			users = SyncSerializer.readUsersSyncObject(changes);
			
			if(users != null && users.getCreated().size() > 0) {
				
				List<UsersSyncModel> createdList = users.getCreated();
				
				for (UsersSyncModel created : createdList) {
					
					if(created.getOnline_id() == null) {
						Users newUser = new Users(created);
						Integer savedId = (Integer) service.Save(newUser);
						
					}
				}
				
			}
			
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("Json Processing Error!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}

package dlt.dltbackendmaster.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dlt.dltbackendmaster.domain.Locality;
import dlt.dltbackendmaster.domain.Partners;
import dlt.dltbackendmaster.domain.Profiles;
import dlt.dltbackendmaster.domain.Us;
import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.domain.watermelondb.SyncObject;
import dlt.dltbackendmaster.domain.watermelondb.UsersSyncModel;
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
	public ResponseEntity get(@RequestParam(name = "lastPulledAt", required=false) @Nullable String lastPulledAt,
								@RequestParam(name = "username") String username) throws ParseException {

		Date validatedDate;
		List<Users> usersCreated;
		List<Users> usersUpdated;
		List<Integer> listDeleted;
		
		List<Locality> localityCreated;
		List<Locality> localityUpdated;
		List<Partners> partnersCreated;
		List<Partners> partnersUpdated;
		List<Profiles> profilesCreated;
		List<Profiles> profilesUpdated;
		List<Us> usCreated;
		List<Us> usUpdated;
		
		if(lastPulledAt.equals("null") || lastPulledAt == null ) {

			//users
			usersCreated = service.GetAllEntityByNamedQuery("Users.findAll");
			usersUpdated = new ArrayList<Users>();
			listDeleted = new ArrayList<Integer>();
			
			// localities
			localityCreated = service.GetAllEntityByNamedQuery("Locality.findAll");
			localityUpdated = new ArrayList<Locality>();
			
			partnersCreated = service.GetAllEntityByNamedQuery("Partners.findAll");
			partnersUpdated = new ArrayList<Partners>();
			
			profilesCreated = service.GetAllEntityByNamedQuery("Profiles.findAll");
			profilesUpdated = new ArrayList<Profiles>();
			
			usCreated = service.GetAllEntityByNamedQuery("Us.findAll");
			usUpdated = new ArrayList<Us>();
			
		}else {
			Long t = Long.valueOf(lastPulledAt);
			//validatedDate = new Date(t);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(t);
			calendar.set(Calendar.MILLISECOND, 0); // remove milliseconds to avoid wrongly adding entities to created list
			validatedDate = calendar.getTime();
			
			
			// users
			usersCreated = service.GetAllEntityByNamedQuery("Users.findByDateCreated", validatedDate);
			usersUpdated = service.GetAllEntityByNamedQuery("Users.findByDateUpdated", validatedDate);
			listDeleted = new ArrayList<Integer>();

			// localities
			localityCreated = service.GetAllEntityByNamedQuery("Locality.findByDateCreated", validatedDate);
			localityUpdated = service.GetAllEntityByNamedQuery("Locality.findByDateUpdated", validatedDate);
			// partners			
			partnersCreated = service.GetAllEntityByNamedQuery("Partners.findByDateCreated", validatedDate);
			partnersUpdated = service.GetAllEntityByNamedQuery("Partners.findByDateUpdated", validatedDate);
			// profiles
			profilesCreated = service.GetAllEntityByNamedQuery("Profiles.findByDateCreated",validatedDate);
			profilesUpdated = service.GetAllEntityByNamedQuery("Profiles.findByDateUpdated",validatedDate);
			// us			
			usCreated = service.GetAllEntityByNamedQuery("Us.findByDateCreated",validatedDate);
			usUpdated = service.GetAllEntityByNamedQuery("Us.findByDateUpdated",validatedDate);
		}
		
		try {
			SyncObject<Users> usersSO = new SyncObject<Users>(usersCreated, usersUpdated, listDeleted);
			SyncObject<Locality> localitySO = new SyncObject<Locality>(localityCreated, localityUpdated, listDeleted);
			SyncObject<Partners> partnersSO = new SyncObject<Partners>(partnersCreated, partnersUpdated, listDeleted);
			SyncObject<Profiles> profilesSO = new SyncObject<Profiles>(profilesCreated, profilesUpdated, listDeleted);
			SyncObject<Us> usSO = new SyncObject<Us>(usCreated, usUpdated, listDeleted);
			
        	//String object = SyncSerializer.createUsersSyncObject(usersCreated, usersUpdated, new ArrayList<Integer>());
			String object = SyncSerializer.createSyncObject(usersSO, localitySO, profilesSO, partnersSO, usSO, lastPulledAt);
			System.out.println("PULLING " + object);
        	return new ResponseEntity<>(object, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			return new ResponseEntity<>("Parameter not present", HttpStatus.BAD_REQUEST);
		}
        
		
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(consumes = "application/json")
	public ResponseEntity post(@RequestBody String changes,
								@RequestParam(name = "username") String username) throws ParseException, JsonMappingException, JsonProcessingException {
		
		String lastPulledAt = SyncSerializer.readLastPulledAt(changes);
		
		ObjectMapper mapper = new ObjectMapper();
		SyncObject<UsersSyncModel> users;
		try {
			users = SyncSerializer.readUsersSyncObject(changes);
			
			// created entities
			if(users != null && users.getCreated().size() > 0) {
				
				List<UsersSyncModel> createdList = mapper.convertValue(users.getCreated(), new TypeReference<List<UsersSyncModel>>() {});
				
				for (UsersSyncModel created : createdList) {

					if(created.getOnline_id() == null) {
						Users newUser = new Users(created, lastPulledAt);
						Integer savedId = (Integer) service.Save(newUser);
					}
				}
			}

			// updated entities
			if(users != null && users.getUpdated().size() > 0) {
				List<UsersSyncModel> updatedList = mapper.convertValue(users.getUpdated(), new TypeReference<List<UsersSyncModel>>() {});

				for (UsersSyncModel updated : updatedList) {
					
					if(updated.getOnline_id() == null) {
						Users newUser = new Users(updated, lastPulledAt);
						Integer savedId = (Integer) service.Save(newUser);
						
					} else {
						Users updateu = service.find(Users.class, updated.getOnline_id());
						updateu.update(updated, lastPulledAt);
						
						Users savedId = service.update(updateu);
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

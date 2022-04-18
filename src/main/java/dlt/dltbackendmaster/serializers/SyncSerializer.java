package dlt.dltbackendmaster.serializers;

import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dlt.dltbackendmaster.domain.Locality;
import dlt.dltbackendmaster.domain.Partners;
import dlt.dltbackendmaster.domain.Profiles;
import dlt.dltbackendmaster.domain.Us;
import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.domain.watermelondb.SyncObject;
import dlt.dltbackendmaster.domain.watermelondb.UsersSyncModel;

public class SyncSerializer {

	/*public static String createUsersSyncObject(List<Users> created, List<Users> updated, List<Integer> deleted) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<ObjectNode> cretedlist = new ArrayList<ObjectNode>();
		for (Users user : created) {
			cretedlist.add(user.toObjectNode());
		}
		ArrayNode arrayCreated= mapper.createArrayNode();
		arrayCreated.addAll(cretedlist);
		
		List<ObjectNode> updatedlist = new ArrayList<ObjectNode>();
		for (Users user : updated) {
			updatedlist.add(user.toObjectNode());
		}
		
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedlist);
		
		ArrayNode arrayDeleted= mapper.valueToTree(deleted);
		
		ObjectNode userNode = mapper.createObjectNode();
		userNode.set("created", arrayCreated);
		userNode.set("updated", arrayUpdated);
		userNode.set("deleted", arrayDeleted);
		
		ObjectNode changesNode = mapper.createObjectNode();
		changesNode.set("users", userNode);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    
		ObjectNode rootNode = mapper.createObjectNode();
		rootNode.set("changes", changesNode);
		rootNode.put("timestamp",timestamp.getTime());

		return  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
	}*/
	
	public static String createSyncObject(SyncObject<Users> users, 
												SyncObject<Locality> localities, 
												SyncObject<Profiles> profiles,
												SyncObject<Partners> partners,
												SyncObject<Us> us,
												String lastPulledAt) throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode changesNode = mapper.createObjectNode();
		changesNode.set("users", createUserSyncObject(users, lastPulledAt));
		changesNode.set("localities", createLocalitySyncObject(localities));
		changesNode.set("partners", createPartnersSyncObject(partners));
		changesNode.set("profiles", createProfilesSyncObject(profiles));
		changesNode.set("us", createUsSyncObject(us));

		
		ObjectNode rootNode = mapper.createObjectNode();
		rootNode.set("changes", changesNode);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		rootNode.put("timestamp",timestamp.getTime());
		
		return  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
	}
	
	public static ObjectNode createUserSyncObject(SyncObject<Users> users, String lastPulledAt) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		// created users
		List<Users> createdUsers = mapper.convertValue(users.getCreated(), new TypeReference<List<Users>>() {});
		List<ObjectNode> createdlist = createdUsers.stream()
												.map((Users element) -> element.toObjectNode(lastPulledAt))
												.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdlist);
		
		// updated users
		List<Users> updatedUsers = mapper.convertValue(users.getUpdated(), new TypeReference<List<Users>>() {});
		List<ObjectNode> updatedlist = updatedUsers.stream()
												.map((Users element) -> element.toObjectNode(lastPulledAt))
												.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedlist);
		
		// deleted users
		
		//List<Integer> deletedUsers = mapper.convertValue(users.getDeleted(), new TypeReference<List<Integer>>() {});
		ArrayNode arrayDeleted= mapper.createArrayNode();
		//arrayDeleted.addAll(deletedUsers.toArray());
		
		ObjectNode userNode = mapper.createObjectNode();
		userNode.set("created", arrayCreated);
		userNode.set("updated", arrayUpdated);
		userNode.set("deleted", arrayDeleted);
		
		return userNode;
	}
	
	public static ObjectNode createLocalitySyncObject(SyncObject<Locality> locality) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		// created
		List<Locality> createdObjs = mapper.convertValue(locality.getCreated(), new TypeReference<List<Locality>>() {});
		List<ObjectNode> createdlist = createdObjs.stream()
														.map((Locality element) -> element.toObjectNode())
														.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdlist);
				
		// updated
		List<Locality> updatedObjs = mapper.convertValue(locality.getUpdated(), new TypeReference<List<Locality>>() {});
		List<ObjectNode> updatedlist = updatedObjs.stream()
														.map((Locality element) -> element.toObjectNode())
														.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedlist);
				
		// deleted
		List<Integer> deletedObjs = mapper.convertValue(locality.getDeleted(), new TypeReference<List<Integer>>() {});
		ArrayNode arrayDeleted= mapper.valueToTree(deletedObjs);
				
		ObjectNode userNode = mapper.createObjectNode();
		userNode.set("created", arrayCreated);
		userNode.set("updated", arrayUpdated);
		userNode.set("deleted", arrayDeleted);
				
		return userNode;
	}
	
	public static ObjectNode createPartnersSyncObject(SyncObject<Partners> partners) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		// created
		List<Partners> createdObjs = mapper.convertValue(partners.getCreated(), new TypeReference<List<Partners>>() {});
		List<ObjectNode> createdlist = createdObjs.stream()
														.map((Partners element) -> element.toObjectNode())
														.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdlist);
				
		// updated
		List<Partners> updatedObjs = mapper.convertValue(partners.getUpdated(), new TypeReference<List<Partners>>() {});
		List<ObjectNode> updatedlist = updatedObjs.stream()
														.map((Partners element) -> element.toObjectNode())
														.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedlist);
				
		// deleted
		List<Integer> deletedObjs = mapper.convertValue(partners.getDeleted(), new TypeReference<List<Integer>>() {});
		ArrayNode arrayDeleted= mapper.valueToTree(deletedObjs);
				
		ObjectNode userNode = mapper.createObjectNode();
		userNode.set("created", arrayCreated);
		userNode.set("updated", arrayUpdated);
		userNode.set("deleted", arrayDeleted);
				
		return userNode;
	}
	
	public static ObjectNode createProfilesSyncObject(SyncObject<Profiles> profiles) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		// created
		List<Profiles> createdObjs = mapper.convertValue(profiles.getCreated(), new TypeReference<List<Profiles>>() {});
		List<ObjectNode> createdlist = createdObjs.stream()
														.map((Profiles element) -> element.toObjectNode())
														.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdlist);
				
		// updated
		List<Profiles> updatedObjs = mapper.convertValue(profiles.getUpdated(), new TypeReference<List<Profiles>>() {});
		List<ObjectNode> updatedlist = updatedObjs.stream()
														.map((Profiles element) -> element.toObjectNode())
														.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedlist);
				
		// deleted
		List<Integer> deletedObjs = mapper.convertValue(profiles.getDeleted(), new TypeReference<List<Integer>>() {});
		ArrayNode arrayDeleted= mapper.valueToTree(deletedObjs);
				
		ObjectNode userNode = mapper.createObjectNode();
		userNode.set("created", arrayCreated);
		userNode.set("updated", arrayUpdated);
		userNode.set("deleted", arrayDeleted);
				
		return userNode;
	}
	
	public static ObjectNode createUsSyncObject(SyncObject<Us> us) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		// created
		List<Us> createdObjs = mapper.convertValue(us.getCreated(), new TypeReference<List<Us>>() {});
		List<ObjectNode> createdlist = createdObjs.stream()
														.map((Us element) -> element.toObjectNode())
														.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdlist);
				
		// updated
		List<Us> updatedObjs = mapper.convertValue(us.getUpdated(), new TypeReference<List<Us>>() {});
		List<ObjectNode> updatedlist = updatedObjs.stream()
														.map((Us element) -> element.toObjectNode())
														.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedlist);
				
		// deleted
		List<Integer> deletedObjs = mapper.convertValue(us.getDeleted(), new TypeReference<List<Integer>>() {});
		ArrayNode arrayDeleted= mapper.valueToTree(deletedObjs);
				
		ObjectNode userNode = mapper.createObjectNode();
		userNode.set("created", arrayCreated);
		userNode.set("updated", arrayUpdated);
		userNode.set("deleted", arrayDeleted);
				
		return userNode;
	}
	
	public static SyncObject readUsersSyncObject(String changes) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode root = mapper.readTree(changes);
		JsonNode changesNode = root.path("changes");
		
		if(!changesNode.isMissingNode()) {
			JsonNode usersNode = changesNode.path("users");
			@SuppressWarnings("unchecked")
			SyncObject<UsersSyncModel> users = mapper.treeToValue(usersNode, (Class<SyncObject<UsersSyncModel>>)(Object)SyncObject.class);
			
			return users;
		}
		
		return null;
	}
	
	public static String readLastPulledAt(String changes) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode root = mapper.readTree(changes);
		JsonNode lastPulledAtNode = root.path("lastPulledAt");
		
		if(!lastPulledAtNode.isMissingNode()) {
			
			return mapper.treeToValue(lastPulledAtNode, String.class);
		}
		
		return null;
	}
	
	 
}

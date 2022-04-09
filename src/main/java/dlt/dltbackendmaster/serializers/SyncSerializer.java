package dlt.dltbackendmaster.serializers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.domain.watermelondb.SyncObject;
import dlt.dltbackendmaster.domain.watermelondb.UsersSyncModel;

public class SyncSerializer {

	public static String createUsersSyncObject(List<Users> created, List<Users> updated, List<Integer> deleted) throws JsonProcessingException {
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
	}
	
	public static SyncObject readUsersSyncObject(String changes) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode root = mapper.readTree(changes);
		JsonNode changesNode = root.path("changes");
		
		if(!changesNode.isMissingNode()) {
			JsonNode usersNode = changesNode.path("users");
			SyncObject users = mapper.treeToValue(usersNode, SyncObject.class);
			
			return users;
		}
		
		return null;
	}
	
	
	
}

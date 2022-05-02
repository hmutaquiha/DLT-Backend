package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.Users;

public class UsersSerializer extends JsonSerializer<Users> {

	@Override
	public void serialize(Users value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		Users user = new Users();
		user.setId(value.getId());
		user.setName(value.getName());
		user.setEmail(value.getEmail());
		user.setUsername(value.getUsername());
		gen.writeObject(user);
	}

}

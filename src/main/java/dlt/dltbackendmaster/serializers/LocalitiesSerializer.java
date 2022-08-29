package dlt.dltbackendmaster.serializers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.Locality;

public class LocalitiesSerializer extends JsonSerializer<Set<Locality>>{

	@Override
	public void serialize(Set<Locality> localities, JsonGenerator gen, SerializerProvider serializers) throws IOException {
	    Set<Locality> localitiesObject = new HashSet<>();
	    for (Locality value : localitiesObject) {
	        Locality locality = new Locality();
	        locality.setId(value.getId());
	        locality.setName(value.getName());
	        locality.setDescription(value.getDescription());
	        locality.setCreatedBy(value.getCreatedBy());
	        locality.setDateCreated(value.getDateCreated());
	        locality.setDateUpdated(value.getDateUpdated());
	        locality.setDistrict(value.getDistrict());
	        locality.setStatus(value.getStatus());
            localitiesObject.add(locality);
        }
		gen.writeObject(localitiesObject);
		
		
	}

}

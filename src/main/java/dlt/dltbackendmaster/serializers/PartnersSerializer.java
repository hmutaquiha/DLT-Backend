package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.Partner;

public class PartnerSerializer extends JsonSerializer<Partner>{

	@Override
	public void serialize(Partner value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		Partner partner = new Partner();
		partner.setId(partner.getId());
		partner.setName(partner.getName());
		partner.setDescription(partner.getDescription());
		partner.setAbbreviation(partner.getAbbreviation());
		gen.writeObject(partner);
		
	}

}

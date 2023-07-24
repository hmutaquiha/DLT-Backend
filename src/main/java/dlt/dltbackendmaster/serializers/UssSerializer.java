package dlt.dltbackendmaster.serializers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.Us;

public class UssSerializer extends JsonSerializer<Set<Us>>
{

    @Override
    public void serialize(Set<Us> uss, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Set<Us> ussObject = new HashSet<>();

        for (Us value : uss) {
            Us us = new Us();
            us.setId(value.getId());
            us.setCode(value.getCode());
            us.setDescription(value.getDescription());
            us.setName(value.getName());
            us.setLatitude(value.getLatitude());
            us.setLongitude(value.getLongitude());
            us.setStatus(value.getStatus());
            us.setDateCreated(value.getDateCreated());
            us.setDateUpdated(value.getDateUpdated());
            us.setUpdatedBy(value.getUpdatedBy());
            us.setCreatedBy(value.getCreatedBy());

            ussObject.add(us);
        }
        gen.writeObject(ussObject);
    }

}

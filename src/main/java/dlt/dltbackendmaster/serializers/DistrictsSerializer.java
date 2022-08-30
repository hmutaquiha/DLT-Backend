package dlt.dltbackendmaster.serializers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.District;

public class DistrictsSerializer extends JsonSerializer<Set<District>>
{

    @Override
    public void serialize(Set<District> districts, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException {
        Set<District> districtsObjects = new HashSet<District>();

        for (District value : districts) {
            District district = new District();
            district.setId(value.getId());
            district.setProvince(value.getProvince());
            district.setCode(value.getCode());
            district.setName(value.getName());
            districtsObjects.add(district);
        }
        gen.writeObject(districtsObjects);
    }

}

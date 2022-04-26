package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.SubService;

public class SubServiceSerializer extends JsonSerializer<SubService>
{
    @Override
    public void serialize(SubService value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        SubService subService = new SubService();
        subService.setId(value.getId());
        subService.setService(value.getService());
        subService.setName(value.getName());
        subService.setRemarks(value.getRemarks());
        subService.setIsHidden(value.getIsHidden());
        subService.setIsManandatory(value.getIsManandatory());
        subService.setSortOrder(value.getSortOrder());
        subService.setStatus(value.getStatus());
        subService.setCreatedBy(value.getCreatedBy());
        subService.setDateCreated(value.getDateCreated());
        subService.setUpdatedBy(value.getUpdatedBy());
        subService.setDateUpdated(value.getDateUpdated());
        gen.writeObject(subService);
    }
}

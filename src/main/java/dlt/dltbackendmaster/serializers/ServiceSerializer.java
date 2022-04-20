package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.Service;

public class ServiceSerializer extends JsonSerializer<Service>
{
    @Override
    public void serialize(Service value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Service service = new Service();
        service.setId(value.getId());
        service.setName(value.getName());
        service.setDescription(value.getDescription());
        service.setIsHidden(value.getIsHidden());
        service.setIsCoreService(service.getIsCoreService());
        service.setStatus(service.getStatus());
        service.setCreatedBy(value.getCreatedBy());
        service.setDateCreated(value.getDateCreated());
        service.setUpdatedBy(value.getUpdatedBy());
        service.setDateUpdated(value.getDateUpdated());
        gen.writeObject(service);
    }
}

package dlt.dltbackendmaster.serializers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.BeneficiariesInterventions;

public class BeneficiaryInterventionsSerializer extends JsonSerializer<Set<BeneficiariesInterventions>>
{

    @Override
    public void serialize(Set<BeneficiariesInterventions> interventions, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException {
        Set<BeneficiariesInterventions> interventionsObject = new HashSet<>();

        for (BeneficiariesInterventions value : interventions) {
            BeneficiariesInterventions intervention = new BeneficiariesInterventions();
        	intervention.setId(value.getId());
            intervention.setBeneficiaries(value.getBeneficiaries());
            intervention.setSubServices(value.getSubServices());
//            intervention.setDate(value.getId().getDate());
            intervention.setEntryPoint(value.getEntryPoint());
            intervention.setResult(value.getResult());
            intervention.setProvider(value.getProvider());
            intervention.setActivistId(value.getActivistId());
            intervention.setRemarks(value.getRemarks());
            intervention.setUs(value.getUs());
            // intervention.setUs_id(String.valueOf(value.getUs().getId()));
            intervention.setStatus(value.getStatus());
            intervention.setCreatedBy(value.getCreatedBy());
            intervention.setDateCreated(value.getDateCreated());
            intervention.setUpdatedBy(value.getUpdatedBy());
            intervention.setDateUpdated(value.getDateUpdated());
            
            interventionsObject.add(intervention);
        }
        gen.writeObject(interventionsObject);
    }
}

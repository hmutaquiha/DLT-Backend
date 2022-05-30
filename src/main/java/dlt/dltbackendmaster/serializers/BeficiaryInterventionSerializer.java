package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.BeneficiaryIntervention;

public class BeficiaryInterventionSerializer extends JsonSerializer<BeneficiaryIntervention>
{

    @Override
    public void serialize(BeneficiaryIntervention value, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException {
        BeneficiaryIntervention intervention = new BeneficiaryIntervention();
        intervention.setBeneficiary(value.getBeneficiary());
        intervention.setSubService(value.getSubService());
        intervention.setDate(value.getDate());
        intervention.setEntryPoint(value.getEntryPoint());
        intervention.setResult(value.getResult());
        intervention.setProvider(value.getProvider());
        intervention.setActivistId(value.getActivistId());
        intervention.setRemarks(value.getRemarks());
        intervention.setUs_id(value.getUs_id());
        intervention.setStatus(value.getStatus());
        intervention.setCreatedBy(value.getCreatedBy());
        intervention.setDateCreated(value.getDateCreated());
        intervention.setUpdatedBy(value.getUpdatedBy());
        intervention.setDateUpdated(value.getDateUpdated());
    }}

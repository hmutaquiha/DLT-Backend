package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.Beneficiary;

public class BeneficiarySerializer extends JsonSerializer<Beneficiary>
{
    @Override
    public void serialize(Beneficiary value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setId(value.getId());
        beneficiary.setNui(value.getNui());
        beneficiary.setSurname(value.getSurname());
        beneficiary.setName(value.getName());
        beneficiary.setNickName(value.getNickName());
        beneficiary.setGender(value.getGender());
        beneficiary.setDateOfBirth(value.getDateOfBirth());
        beneficiary.setAddress(value.getAddress());
        beneficiary.setEmail(value.getEmail());
        beneficiary.setEntryPoint(value.getEntryPoint());
        beneficiary.setIsOrphan(value.getIsOrphan());
        beneficiary.setIsDeficient(value.getIsDeficient());
        beneficiary.setDeficiencyType(value.getDeficiencyType());
        beneficiary.setIsStudent(value.getIsStudent());
        beneficiary.setNeighborhood(value.getNeighborhood());
        beneficiary.setPartnerId(value.getPartnerId());
        beneficiary.setPhoneNumber(value.getPhoneNumber());
        beneficiary.setVia(value.getVia());
        beneficiary.setUsId(value.getUsId());
        beneficiary.setIsStudent(value.getIsStudent());
        beneficiary.setSchoolName(value.getSchoolName());
        beneficiary.setStatus(value.getStatus());
        beneficiary.setLivesWith(value.getLivesWith());
        beneficiary.setCreatedBy(value.getCreatedBy());
        beneficiary.setDateCreated(value.getDateCreated());
        beneficiary.setUpdatedBy(value.getUpdatedBy());
        beneficiary.setDateUpdated(value.getDateUpdated());
        gen.writeObject(beneficiary);
    }
}

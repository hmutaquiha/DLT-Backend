package dlt.dltbackendmaster.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import dlt.dltbackendmaster.serializers.ServiceSerializer;

@SuppressWarnings("serial")
@Entity
@Table(name = "sub_services", catalog = "dreams_db")
public class SubService extends BasicLifeCycle implements Serializable
{
    private Service service;
    private Boolean isHidden;
    private Boolean isManandatory;
    private String remarks;
    private Integer sortOrder;
    private Set<Beneficiary> beneficiaries;

    public SubService(int id, String name, Integer status, Integer createdBy, Date dateCreated, Integer updatedBy,
                      Date dateUpdated) {
        super(id, name, status, createdBy, dateCreated, updatedBy, dateUpdated);
    }

    public SubService(int id, String name, String description, Integer status, Integer createdBy, Date dateCreated,
                      Integer updatedBy, Date dateUpdated, Service service, Boolean isHidden, Boolean isMandatory,
                      String remarks, Integer sortOrder) {
        this(id, name, status, createdBy, dateCreated, updatedBy, dateUpdated);
        this.service = service;
        this.isHidden = isHidden;
        this.isManandatory = isMandatory;
        this.remarks = remarks;
        this.sortOrder = sortOrder;
    }

    @ManyToOne
    @JoinColumn(name = "service_id")
    @JsonProperty("service")
    @JsonSerialize(using = ServiceSerializer.class)
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Column(name = "hidden")
    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    @Column(name = "mandatory")
    public Boolean getIsManandatory() {
        return isManandatory;
    }

    public void setIsManandatory(Boolean isManandatory) {
        this.isManandatory = isManandatory;
    }

    @Column(name = "remarks")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "sort_order")
    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @ManyToMany(mappedBy = "subServices")
    public Set<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(Set<Beneficiary> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }
}

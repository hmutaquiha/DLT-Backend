package dlt.dltbackendmaster.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dlt.dltbackendmaster.serializers.ServiceSerializer;

@SuppressWarnings("serial")
@Entity
@Table(name = "sub_services", catalog = "dreams_db")
@NamedQueries({ @NamedQuery(name = "SubService.findAll", query = "SELECT c FROM SubService c"),
                @NamedQuery(name = "SubService.findByDateCreated",
                            query = "SELECT c FROM SubService c WHERE c.dateCreated = :lastpulledat"),
                @NamedQuery(name = "SubService.findByDateUpdated",
                            query = "SELECT c FROM SubService c WHERE c.dateUpdated = :lastpulledat") })
public class SubService extends BasicLifeCycle implements Serializable
{
    private Service service;
    private Boolean isHidden;
    private Boolean isManandatory;
    private String remarks;
    private Integer sortOrder;

    public SubService() {}

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

    public SubService(Integer id) {
        this.id = id;
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

    public ObjectNode toObjectNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode subService = mapper.createObjectNode();
        subService.put("id", id);
        subService.put("name", name);
        subService.put("service_id", service.id);
        subService.put("is_mandatory", isManandatory);
        subService.put("is_hidden", isHidden);
        subService.put("remarks", remarks);
        subService.put("sort_order", sortOrder);
        subService.put("status", status);
        subService.put("online_id", id); // flag to control if entity is synchronized with the backend
        return subService;
    }
}

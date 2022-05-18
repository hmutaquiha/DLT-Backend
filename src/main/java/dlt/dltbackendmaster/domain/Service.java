package dlt.dltbackendmaster.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SuppressWarnings("serial")
@Entity
@Table(name = "services", catalog = "dreams_db")
@NamedQueries({ @NamedQuery(name = "Service.findAll", query = "SELECT c FROM Service c"),
                @NamedQuery(name = "Service.findByDateCreated",
                            query = "SELECT c FROM Service c WHERE c.dateCreated = :lastpulledat"),
                @NamedQuery(name = "Service.findByDateUpdated",
                            query = "SELECT c FROM Service c WHERE c.dateUpdated = :lastpulledat"),
                @NamedQuery(name = "Service.findByServiceType",
                            query = "SELECT c FROM Service c WHERE c.serviceType = :serviceType") })
public class Service extends BasicLifeCycle implements Serializable
{
    private Integer serviceType;
    private String description;
    private Boolean isCoreService;
    private Boolean isHidden;

    public Service() {}
    /*
     * public Service(int id, String name, String description, Boolean isCoreService, Boolean isHidden, Integer status,
     * Integer createdBy, Date dateCreated, Integer updatedBy, Date dateUpdated) {
     * super(id, name, status, createdBy, dateCreated, updatedBy, dateUpdated);
     * this.description = description;
     * this.isCoreService = isCoreService;
     * this.isHidden = isHidden;
     * }
     */

    @Column(name = "service_type")
    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "core_service")
    public Boolean getIsCoreService() {
        return isCoreService;
    }

    public void setIsCoreService(Boolean isCoreService) {
        this.isCoreService = isCoreService;
    }

    @Column(name = "hidden")
    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public ObjectNode toObjectNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode service = mapper.createObjectNode();
        service.put("id", id);
        service.put("name", name);
        service.put("description", description);
        service.put("is_core_service", isCoreService);
        service.put("is_hidden", isHidden);
        service.put("status", status);
        service.put("online_id", id); // flag to control if entity is synchronized with the backend
        return service;
    }
}

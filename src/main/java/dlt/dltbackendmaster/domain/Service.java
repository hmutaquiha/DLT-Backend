package dlt.dltbackendmaster.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "services", catalog = "dreams_db")
public class Service extends BasicLifeCycle implements Serializable
{
    private String description;
    private Boolean isCoreService;
    private Boolean isHidden;

    public Service() {}

    public Service(int id, String name, String description, Boolean isCoreService, Boolean isHidden, Integer status,
                   Integer createdBy, Date dateCreated, Integer updatedBy, Date dateUpdated) {
        super(id, name, status, createdBy, dateCreated, updatedBy, dateUpdated);
        this.description = description;
        this.isCoreService = isCoreService;
        this.isHidden = isHidden;
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
}

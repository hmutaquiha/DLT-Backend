package dlt.dltbackendmaster.domain.watermelondb;

public class ServiceSyncModel extends BasicLifeCycleSyncModel
{
    private String description;
    private Boolean is_core_service;
    private Boolean is_hidden;

    public ServiceSyncModel() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIs_core_service() {
        return is_core_service;
    }

    public void setIs_core_service(Boolean isCoreService) {
        this.is_core_service = isCoreService;
    }

    public Boolean getIs_hidden() {
        return is_hidden;
    }

    public void setIs_hidden(Boolean isHidden) {
        this.is_hidden = isHidden;
    }
}

package dlt.dltbackendmaster.domain.watermelondb;

public class ServiceSyncModel extends BasicLifeCycleSyncModel
{
    private String description;
    private Boolean isCoreService;
    private Boolean isHidden;

    public ServiceSyncModel() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsCoreService() {
        return isCoreService;
    }

    public void setIsCoreService(Boolean isCoreService) {
        this.isCoreService = isCoreService;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }
}

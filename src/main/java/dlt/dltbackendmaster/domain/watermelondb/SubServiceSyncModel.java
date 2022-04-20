package dlt.dltbackendmaster.domain.watermelondb;

public class SubServiceSyncModel extends BasicLifeCycleSyncModel
{
    private Integer service_id;
    private Boolean isHidden;
    private Boolean isManandatory;
    private String remarks;
    private Integer sortOrder;

    public SubServiceSyncModel() {}

    public Integer getService_id() {
        return service_id;
    }

    public void setService_id(Integer service_id) {
        this.service_id = service_id;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Boolean getIsManandatory() {
        return isManandatory;
    }

    public void setIsManandatory(Boolean isManandatory) {
        this.isManandatory = isManandatory;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}

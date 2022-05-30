package dlt.dltbackendmaster.domain.watermelondb;

public class SubServiceSyncModel extends BasicLifeCycleSyncModel
{
    private Integer service_id;
    private Boolean is_hidden;
    private Boolean is_manandatory;
    private String remarks;
    private Integer sort_order;

    public SubServiceSyncModel() {}

    public Integer getService_id() {
        return service_id;
    }

    public void setService_id(Integer service_id) {
        this.service_id = service_id;
    }

    public Boolean getIs_hidden() {
        return is_hidden;
    }

    public void setIs_hidden(Boolean isHidden) {
        this.is_hidden = isHidden;
    }

    public Boolean getIs_manandatory() {
        return is_manandatory;
    }

    public void setIs_manandatory(Boolean isManandatory) {
        this.is_manandatory = isManandatory;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getSort_order() {
        return sort_order;
    }

    public void setSort_order(Integer sortOrder) {
        this.sort_order = sortOrder;
    }
}

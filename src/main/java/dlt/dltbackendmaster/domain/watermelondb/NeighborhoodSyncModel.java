package dlt.dltbackendmaster.domain.watermelondb;

public class NeighborhoodSyncModel extends BasicLifeCycleSyncModel
{
    private Integer locality_id;
    private String description;

    public NeighborhoodSyncModel() {}

    public Integer getLocality_id() {
        return locality_id;
    }

    public void setLocality_id(Integer locality_id) {
        this.locality_id = locality_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

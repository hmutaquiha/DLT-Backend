package dlt.dltbackendmaster.domain.watermelondb;

public abstract class BasicLifeCycleSyncModel
{
    private String id;
    private String _status;
    private String _changed;
    private String name;
    private String status;
    private Integer online_id;

    public BasicLifeCycleSyncModel() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public String get_changed() {
        return _changed;
    }

    public void set_changed(String _changed) {
        this._changed = _changed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOnline_id() {
        return online_id;
    }

    public void setOnline_id(Integer online_id) {
        this.online_id = online_id;
    }
}

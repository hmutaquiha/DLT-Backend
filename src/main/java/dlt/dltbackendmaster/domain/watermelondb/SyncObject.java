package dlt.dltbackendmaster.domain.watermelondb;

import java.util.List;

import dlt.dltbackendmaster.domain.watermelondb.*;

public class SyncObject {
	private List<UsersSyncModel> created;
	private List<UsersSyncModel> updated;
	private List<UsersSyncModel> deleted;
	
	public SyncObject() {}
	
	public List<UsersSyncModel> getCreated() {
		return created;
	}
	public void setCreated(List<UsersSyncModel> created) {
		this.created = created;
	}
	public List<UsersSyncModel> getUpdated() {
		return updated;
	}
	public void setUpdated(List<UsersSyncModel> updated) {
		this.updated = updated;
	}
	public List<UsersSyncModel> getDeleted() {
		return deleted;
	}
	public void setDeleted(List<UsersSyncModel> deleted) {
		this.deleted = deleted;
	}
	
	
}

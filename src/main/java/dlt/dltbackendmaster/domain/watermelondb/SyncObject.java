package dlt.dltbackendmaster.domain.watermelondb;

import java.util.List;

public class SyncObject<T> {
	private List<T> created;
	private List<T> updated;
	private List<Integer> deleted;

	
	public SyncObject() {
	}
	
	public SyncObject(List<T> created, List<T> updated, List<Integer> deleted){
		this.created = created;
		this.updated = updated;
		this.deleted = deleted;

	}
	
	public List<T> getCreated() {
		return created;
	}
	public void setCreated(List<T> created) {
		this.created = created;
	}
	public List<T> getUpdated() {
		return updated;
	}
	public void setUpdated(List<T> updated) {
		this.updated = updated;
	}

	public List<Integer> getDeleted() {
		return deleted;
	}
	public void setDeleted(List<Integer> deleted) {
		this.deleted = deleted;
	}
	
	
}

package dlt.dltbackendmaster.domain.watermelondb;

import java.util.List;

import dlt.dltbackendmaster.domain.Locality;
import dlt.dltbackendmaster.domain.Users;

public class SyncObject<T> {
	private List<T> created;
	private List<T> updated;
	private List<T> deleted;
	
	public SyncObject() {
	}
	
	public SyncObject(List<T> created, List<T> updated, List<Integer> deleted){
		this.created = created;
		this.updated = updated;
		this.deleted = updated;
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
	public List<T> getDeleted() {
		return deleted;
	}
	public void setDeleted(List<T> deleted) {
		this.deleted = deleted;
	}
	
	
}

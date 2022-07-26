package dlt.dltbackendmaster.domain.watermelondb;

public class PartnerSyncModel {

	private String id;
	private String _status;
	private String _changed;
	private String name;
	private String abbreviation;
	private String description;
	private String partner_type;
	private int status;
	private Integer online_id;
	
	public PartnerSyncModel() {}

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

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPartner_type() {
		return partner_type;
	}

	public void setPartner_type(String partner_type) {
		this.partner_type = partner_type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getOnline_id() {
		return online_id;
	}

	public void setOnline_id(Integer online_id) {
		this.online_id = online_id;
	}
	
	
}

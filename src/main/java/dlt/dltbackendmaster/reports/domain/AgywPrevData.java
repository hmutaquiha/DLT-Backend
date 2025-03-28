package dlt.dltbackendmaster.reports.domain;

public class AgywPrevData {

	private String province;
	private String district;
	private String layering;
	private String enrollemtTime;
	private String ageBand;
	private Integer value;

	public AgywPrevData() {
		super();
	}

	public AgywPrevData(String province, String district, String layering, String enrollemtTime, String ageBand,
			Integer value) {
		super();
		this.province = province;
		this.district = district;
		this.layering = layering;
		this.enrollemtTime = enrollemtTime;
		this.ageBand = ageBand;
		this.value = value;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getLayering() {
		return layering;
	}

	public void setLayering(String layering) {
		this.layering = layering;
	}

	public String getEnrollemtTime() {
		return enrollemtTime;
	}

	public void setEnrollemtTime(String enrollemtTime) {
		this.enrollemtTime = enrollemtTime;
	}

	public String getAgeBand() {
		return ageBand;
	}

	public void setAgeBand(String ageBand) {
		this.ageBand = ageBand;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}

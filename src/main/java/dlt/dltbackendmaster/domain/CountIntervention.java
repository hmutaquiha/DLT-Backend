package dlt.dltbackendmaster.domain;

import java.util.Objects;

import javax.persistence.Column;

public class CountIntervention implements java.io.Serializable {

	private static final long serialVersionUID = 6101912858410074918L;

	private int beneficiaryId;

	private Long totalInterventions;

	public CountIntervention(int beneficiaryId, Long totalInterventions) {
		this.beneficiaryId = beneficiaryId;
		this.totalInterventions = totalInterventions;
	}

	@Column(name = "beneficiary_id", nullable = false)
	public int getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(int beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	@Column(name = "total_interventions", nullable = false)
	public Long getTotalInterventions() {
		return totalInterventions;
	}

	public void setTotalInterventions(Long totalInterventions) {
		this.totalInterventions = totalInterventions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(beneficiaryId, totalInterventions);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountIntervention other = (CountIntervention) obj;
		return Objects.equals(beneficiaryId, other.beneficiaryId)
				&& Objects.equals(totalInterventions, other.totalInterventions);
	}

	@Override
	public String toString() {
		return "ICountIntervention [beneficiaryId=" + beneficiaryId + ", totalInterventions=" + totalInterventions
				+ "]";
	}

}

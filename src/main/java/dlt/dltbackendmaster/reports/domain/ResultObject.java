package dlt.dltbackendmaster.reports.domain;

import java.util.List;
import java.util.Map;

/**
 * Resultado a ser retornado para compor o relatório
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class ResultObject {
	private Map<String, Map<String, Integer>> totals;
	private Map<String, Map<String, List<Integer>>> beneficiaries;

	public ResultObject() {
		totals = new TotalsAgeBands().getAgeBands();
		beneficiaries = new BeneficiariesAgeBands().getAgeBands();
	}

	public ResultObject(Map<String, Map<String, Integer>> totals,
			Map<String, Map<String, List<Integer>>> beneficiaries) {
		this.totals = totals;
		this.beneficiaries = beneficiaries;
	}

	public Map<String, Map<String, Integer>> getTotals() {
		return totals;
	}

	public void setTotals(Map<String, Map<String, Integer>> totals) {
		this.totals = totals;
	}

	public Map<String, Map<String, List<Integer>>> getBeneficiaries() {
		return beneficiaries;
	}

	public void setBeneficiaries(Map<String, Map<String, List<Integer>>> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}

}

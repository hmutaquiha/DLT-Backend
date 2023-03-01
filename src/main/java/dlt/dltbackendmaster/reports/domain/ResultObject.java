package dlt.dltbackendmaster.reports.domain;

/**
 * Resultado a ser retornado para compor o relatório
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class ResultObject {
	private TotalsReportObject totals;
	private BeneficiariesReportObject beneficiaries;

	public ResultObject(Integer[] districts) {
		totals = new TotalsReportObject(districts);
		beneficiaries = new BeneficiariesReportObject(districts);
	}

	public ResultObject(TotalsReportObject totals, BeneficiariesReportObject beneficiaries) {
		this.totals = totals;
		this.beneficiaries = beneficiaries;
	}

	public TotalsReportObject getTotals() {
		return totals;
	}

	public void setTotals(TotalsReportObject totals) {
		this.totals = totals;
	}

	public BeneficiariesReportObject getBeneficiaries() {
		return beneficiaries;
	}

	public void setBeneficiaries(BeneficiariesReportObject beneficiaries) {
		this.beneficiaries = beneficiaries;
	}

}

package dlt.dltbackendmaster.reports.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Nível do relatório (totais)
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class TotalsReportObject {

	private List<Map<String, List<Map<String, Integer>>>> reportObject = new ArrayList<>();

	public TotalsReportObject() {
		reportObject = new TotalsAgeBands().getAgeBands();
	}

	public List<Map<String, List<Map<String, Integer>>>> getReportObject() {
		return reportObject;
	}

}

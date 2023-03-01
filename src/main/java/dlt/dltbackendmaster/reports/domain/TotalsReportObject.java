package dlt.dltbackendmaster.reports.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Nível do relatório (totais)
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class TotalsReportObject {

	private Map<Integer, Map<String, Map<String, Integer>>> reportObject = new HashMap<>();

	public TotalsReportObject(Integer[] districts) {

		for (Integer district : districts) {
			reportObject.put(district, new TotalsAgeBands().getAgeBands());
		}
	}

	public Map<Integer, Map<String, Map<String, Integer>>> getReportObject() {
		return reportObject;
	}

}

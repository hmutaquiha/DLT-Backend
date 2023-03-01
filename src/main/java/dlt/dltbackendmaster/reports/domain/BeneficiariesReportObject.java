package dlt.dltbackendmaster.reports.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Nível de dados do relatório (beneficiárias)
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class BeneficiariesReportObject {

	private Map<Integer, Map<String, Map<String, List<Integer>>>> reportObject = new HashMap<>();

	public BeneficiariesReportObject(Integer[] districts) {

		for (Integer district : districts) {
			reportObject.put(district, new BeneficiariesAgeBands().getAgeBands());
		}
	}

	public Map<Integer, Map<String, Map<String, List<Integer>>>> getReportObject() {
		return reportObject;
	}

}

package dlt.dltbackendmaster.reports.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Nível de dados do relatório (beneficiárias)
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class BeneficiariesReportObject {

	private List<Map<String, List<Map<String, List<Integer>>>>> reportObject = new ArrayList<>();

	public BeneficiariesReportObject() {

		reportObject = new BeneficiariesAgeBands().getAgeBands();
	}

	public List<Map<String, List<Map<String, List<Integer>>>>> getReportObject() {
		return reportObject;
	}

}

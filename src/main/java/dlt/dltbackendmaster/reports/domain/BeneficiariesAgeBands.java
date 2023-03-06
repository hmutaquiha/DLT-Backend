package dlt.dltbackendmaster.reports.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dlt.dltbackendmaster.reports.utils.ReportsConstants;

/**
 * Nível de faixas etárias (beneficiárias)
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class BeneficiariesAgeBands {

	private List<Map<String, List<Map<String, List<Integer>>>>> ageBands = new ArrayList<>();

	public BeneficiariesAgeBands() {
		for (int i = 0; i < ReportsConstants.AGE_BANDS.length; i++) {
			Map<String, List<Map<String, List<Integer>>>> map = new HashMap<>();
			map.put(ReportsConstants.AGE_BANDS[i], new BeneficiariesEnrollmentTimes().getTime());
			ageBands.add(map);
		}
	}

	public List<Map<String,List<Map<String,List<Integer>>>>> getAgeBands() {
		return ageBands;
	}

}

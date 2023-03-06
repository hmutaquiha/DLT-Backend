package dlt.dltbackendmaster.reports.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dlt.dltbackendmaster.reports.utils.ReportsConstants;

/**
 * Nível de tempo de inscrição no programa (beneficiárias)
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class BeneficiariesEnrollmentTimes {

	private List<Map<String, List<Integer>>> time = new ArrayList<>();

	public BeneficiariesEnrollmentTimes() {
		for (int i = 0; i < ReportsConstants.ENROLLMENT_TIMES.length; i++) {
			Map<String, List<Integer>> map = new HashMap<>();
			map.put(ReportsConstants.ENROLLMENT_TIMES[i], new ArrayList<>());
			time.add(map);
		}
	}

	public List<Map<String, List<Integer>>> getTime() {
		return time;
	}

}

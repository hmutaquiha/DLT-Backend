package dlt.dltbackendmaster.reports.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dlt.dltbackendmaster.reports.utils.ReportsConstants;

/**
 * Nível de tempo de inscrição no programa (totais)
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class TotalsEnrollmentTimes {

	private List<Map<String, Integer>> time = new ArrayList<>();

	public TotalsEnrollmentTimes() {
		for (int i = 0; i < ReportsConstants.ENROLLMENT_TIMES.length; i++) {
			Map<String, Integer> map = new HashMap<>();
			map.put(ReportsConstants.ENROLLMENT_TIMES[i], 0);
			time.add(map);
		}
	}

	public List<Map<String, Integer>> getTime() {
		return time;
	}

}

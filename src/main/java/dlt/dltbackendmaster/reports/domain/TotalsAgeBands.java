package dlt.dltbackendmaster.reports.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dlt.dltbackendmaster.reports.utils.ReportsConstants;

/**
 * Nívele de faixas etárias (totais)
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class TotalsAgeBands {

	private List<Map<String, List<Map<String, Integer>>>> ageBands = new ArrayList<>();

	public TotalsAgeBands() {
		for (int i = 0; i < ReportsConstants.AGE_BANDS.length; i++) {
			Map<String, List<Map<String, Integer>>> map = new HashMap<>();
			map.put(ReportsConstants.AGE_BANDS[i], new TotalsEnrollmentTimes().getTime());
			ageBands.add(map);
		}
	}

	public List<Map<String, List<Map<String, Integer>>>> getAgeBands() {
		return ageBands;
	}

}

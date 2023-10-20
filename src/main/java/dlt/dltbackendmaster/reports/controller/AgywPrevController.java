package dlt.dltbackendmaster.reports.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import dlt.dltbackendmaster.reports.AgywPrevReport;
import dlt.dltbackendmaster.reports.domain.NewlyEnrolledAgywAndServices;
import dlt.dltbackendmaster.reports.domain.ResultObject;
import dlt.dltbackendmaster.service.DAOService;

/**
 * Controller resposável pela comunicação dos dados do relatório
 * 
 * @author Hamilton Mutaquiha
 *
 */
@RestController
@RequestMapping("/api/agyw-prev")
public class AgywPrevController {

	private final DAOService service;

	@Autowired
	public AgywPrevController(DAOService service) {
		this.service = service;
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<Map<Integer, Map<String, ResultObject>>> get(
			@RequestParam(name = "districts") Integer[] districts, @RequestParam(name = "startDate") String startDate,
			@RequestParam(name = "endDate") String endDate) {

		AgywPrevReport report = new AgywPrevReport(service);

		try {
			Map<Integer, Map<String, ResultObject>> reportObject = report.getAgywPrevResultObject(districts, startDate,
					endDate);

			return new ResponseEntity<>(reportObject, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(produces = "application/json", path = "/getNewlyEnrolledAgywAndServicesSummary")
	public ResponseEntity<List<Object>> getNewlyEnrolledAgywAndServicesSummary(
			@RequestParam(name = "districts") Integer[] districts, @RequestParam(name = "startDate") Long startDate,
			@RequestParam(name = "endDate") Long endDate, @RequestParam(name = "pageIndex") int pageIndex,
			@RequestParam(name = "pageSize") int pageSize) {
		AgywPrevReport report = new AgywPrevReport(service);
		try {
			List<Object> reportObject = report.getNewlyEnrolledAgywAndServicesSummary(districts, new Date(startDate),
					new Date(endDate), pageIndex, pageSize);

			return new ResponseEntity<>(reportObject, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getNewlyEnrolledAgywAndServicesJsonGenerated")
	public ResponseEntity<byte[]> getNewlyEnrolledAgywAndServicesJsonGenerated(
			@RequestParam(name = "districts") Integer[] districts, @RequestParam(name = "startDate") Long startDate,
			@RequestParam(name = "endDate") Long endDate) throws JsonProcessingException {
		AgywPrevReport report = new AgywPrevReport(service);

		JSONArray rows = new JSONArray();

		List<Object> reportObjectList = report.getNewlyEnrolledAgywAndServices(districts, new Date(startDate),
				new Date(endDate));
		Object[][] reportObjectArray = reportObjectList.toArray(new Object[0][0]);

		for (Object[] obj : reportObjectArray) {
			rows.add(new NewlyEnrolledAgywAndServices(String.valueOf(obj[0]), String.valueOf(obj[1]),
					String.valueOf(obj[2]), String.valueOf(obj[3]), String.valueOf(obj[4]), String.valueOf(obj[5]),
					String.valueOf(obj[6]), String.valueOf(obj[7]), String.valueOf(obj[8]), String.valueOf(obj[9]),
					String.valueOf(obj[10]), String.valueOf(obj[11]), String.valueOf(obj[12]), String.valueOf(obj[13]),
					String.valueOf(obj[14]), String.valueOf(obj[15]), String.valueOf(obj[16]), String.valueOf(obj[17]),
					String.valueOf(obj[18]), String.valueOf(obj[19]), String.valueOf(obj[20]), String.valueOf(obj[21]),
					String.valueOf(obj[22]), String.valueOf(obj[23]), String.valueOf(obj[24]), String.valueOf(obj[25]),
					String.valueOf(obj[26]), String.valueOf(obj[27]), String.valueOf(obj[28]), String.valueOf(obj[29]),
					String.valueOf(obj[30]), String.valueOf(obj[31]), String.valueOf(obj[32]), String.valueOf(obj[33]),
					String.valueOf(obj[34]), String.valueOf(obj[35]), String.valueOf(obj[36]), String.valueOf(obj[37]),
					String.valueOf(obj[38])));
		}

		JSONArray convertListToJsonArray = convertListToJsonArray(rows);

		try (FileWriter file = new FileWriter("target/reports/new_enrolled.json")) {
			// We can write any JSONArray or JSONObject instance to the file
			file.write(convertListToJsonArray.toJSONString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// File name
		String fileName = "report.xlsx";

		// Export Excel file
//		byte[] excelBytes = excelFileExporter.exportExcelFile(rows, headersRow);

		// Prepare and return the Excel file as a response
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileName);

		return new ResponseEntity<>(null, headers, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	public JSONArray convertListToJsonArray(List<NewlyEnrolledAgywAndServices> rows) {
		JSONArray jsonArray = new JSONArray();

		int chunkSize = 500;
		List<List<NewlyEnrolledAgywAndServices>> sublists = splitList(rows, chunkSize);

		for (List<NewlyEnrolledAgywAndServices> sublist : sublists) {

			for (NewlyEnrolledAgywAndServices data : sublist) {
				JSONObject jsonItem = new JSONObject();
				jsonItem.put("provincia", data.getProvincia());
				jsonItem.put("distrito", data.getDistrito());
				jsonItem.put("onde_mora", data.getOnde_mora());
				jsonItem.put("ponto_entrada", data.getPonto_entrada());
				jsonItem.put("organizacao", data.getOrganizacao());
				jsonItem.put("data_registo", data.getData_registo());
				jsonItem.put("registado_por", data.getRegistado_por());
				jsonItem.put("data_actualizacao", data.getData_actualizacao());
				jsonItem.put("actualizado_por", data.getActualizado_por());
				jsonItem.put("nui", data.getNui());
				jsonItem.put("sexo", data.getSexo());
				jsonItem.put("idade_registo", data.getIdade_registo());
				jsonItem.put("idade_actual", data.getIdade_actual());
				jsonItem.put("faixa_registo", data.getFaixa_registo());
				jsonItem.put("faixa_actual", data.getFaixa_actual());
				jsonItem.put("data_nascimento", data.getData_nascimento());
				jsonItem.put("agyw_prev", data.getAgyw_prev());
				jsonItem.put("com_quem_mora", data.getCom_quem_mora());
				jsonItem.put("sustenta_casa", data.getSustenta_casa());
				jsonItem.put("e_orfa", data.getE_orfa());
				jsonItem.put("vai_escola", data.getVai_escola());
				jsonItem.put("tem_deficiencia", data.getTem_deficiencia());
				jsonItem.put("tipo_deficiencia", data.getTipo_deficiencia());
				jsonItem.put("foi_casada", data.getFoi_casada());
				jsonItem.put("esteve_gravida", data.getEsteve_gravida());
				jsonItem.put("tem_filhos", data.getTem_filhos());
				jsonItem.put("gravida_amamentar", data.getGravida_amamentar());
				jsonItem.put("trabalha", data.getTrabalha());
				jsonItem.put("teste_hiv", data.getTeste_hiv());
				jsonItem.put("area_servico", data.getArea_servico());
				jsonItem.put("a_servico", data.getA_servico());
				jsonItem.put("sub_servico", data.getSub_servico());
				jsonItem.put("pacote_servico", data.getPacote_servico());
				jsonItem.put("ponto_entrada_servico", data.getPonto_entrada_servico());
				jsonItem.put("localizacao", data.getLocalizacao());
				jsonItem.put("data_servico", data.getData_servico());
				jsonItem.put("provedor", data.getProvedor());
				jsonItem.put("observacoes", data.getObservacoes());
				jsonItem.put("servico_status", data.getServico_status());

				jsonArray.add(jsonItem);				
			}
			
		}

		return jsonArray;
	}

	public static <T> List<List<T>> splitList(List<T> originalList, int chunkSize) {
		List<List<T>> sublists = new ArrayList<>();
		for (int i = 0; i < originalList.size(); i += chunkSize) {
			int end = Math.min(originalList.size(), i + chunkSize);
			sublists.add(originalList.subList(i, end));
		}
		return sublists;
	}

}

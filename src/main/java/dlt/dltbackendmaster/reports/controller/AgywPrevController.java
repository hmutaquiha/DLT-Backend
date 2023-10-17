package dlt.dltbackendmaster.reports.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.reports.AgywPrevReport;
import dlt.dltbackendmaster.reports.domain.NewlyEnrolledAgywAndServices;
import dlt.dltbackendmaster.reports.domain.ResultObject;
import dlt.dltbackendmaster.reports.generators.NewlyEnrolledAgywAndServicesExcelFileGenerator;
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
	private NewlyEnrolledAgywAndServicesExcelFileGenerator excelFileExporter;

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

	@GetMapping(path = "/getGeneratedNewlyEnrolledAgywAndServices")
	public ResponseEntity<byte[]> getGeneratedNewlyEnrolledAgywAndServices(
			@RequestParam(name = "districts") Integer[] districts, @RequestParam(name = "startDate") Long startDate,
			@RequestParam(name = "endDate") Long endDate) {
		AgywPrevReport report = new AgywPrevReport(service);

		List<Object> counter = report.countNewlyEnrolledAgywAndServices(districts, new Date(startDate),
				new Date(endDate));
		
		int pageSize = 1000;
		double total = Double.parseDouble(counter.get(0).toString());
		double pagesExactly = total/pageSize;
		int pages =  (int) Math.ceil(pagesExactly);

		List<NewlyEnrolledAgywAndServices> rows = new ArrayList<>();
		
		for (int pageIndex = 0; pageIndex < pages; pageIndex++) {
			List<Object> reportObjectList = report.getNewlyEnrolledAgywAndServices(districts, new Date(startDate),
					new Date(endDate), pageIndex, pageSize );
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
		}

		// Header text
		String[] headersRow = new String[] { "Província", "Distrito", "Onde Mora", "Ponto de Entrada", "Organização",
				"Data de Registo", "Registado Por", "Data da Última Actualização", "Actualizado Por", "NUI", "Sexo",
				"Idade (Registo)", "Idade (Actual)", "Faixa Etária (Registo)", "Faixa Etária (Actual)",
				"Data de Nascimento", "Beneficiaria DREAMS ?", "Com quem Mora", "Sustenta a Casa", "É Orfã",
				"Vai à escola", "Tem Deficiência", "Tipo de Deficiência", "Já foi casada", "Já esteve grávida",
				"Tem filhos", "Está Grávida ou a Amamentar", "Trabalha", "Já fez teste de HIV", "Área de Serviço",
				"Serviço", "Sub-Serviço", "Pacote de Serviço", "Ponto de Entrada de Serviço", "Localização do Serviço",
				"Data do Serviço", "Provedor do Serviço", "Outras Observações", "Status", };

		// File name
		String fileName = "report.xlsx";

		// Export Excel file
		byte[] excelBytes = excelFileExporter.exportExcelFile(rows, headersRow);

		// Prepare and return the Excel file as a response
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileName);

		return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
	}
}

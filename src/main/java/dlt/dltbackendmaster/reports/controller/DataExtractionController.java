package dlt.dltbackendmaster.reports.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.reports.AgywPrevReport;
import dlt.dltbackendmaster.reports.domain.BeneficiaryVulnerability;
import dlt.dltbackendmaster.service.DAOService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller resposável pela comunicação dos dados do relatório
 * 
 * @author Hamilton Mutaquiha
 *
 */
@RestController
@Tag(name = "Data Extractions", description = "DLT Data Extractions APIs")
@RequestMapping("/api/data-extaction")
public class DataExtractionController {

	Logger logger = LoggerFactory.getLogger(DataExtractionController.class);

	private final DAOService service;

	@Autowired
	public DataExtractionController(DAOService service) {
		this.service = service;
	}

	@GetMapping(path = "/beneficiaries-vulnerabilities")
	@Operation(operationId = "beneficiariesVulnerabilities", summary = "Retorna beneficiárias e vulnerabilidades", description = "Retorna a lista de beneficiárias activas e suas respectivas vulnerabilidades")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Executado com sucesso", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = BeneficiaryVulnerability.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content), })
	public ResponseEntity<List<BeneficiaryVulnerability>> getBeneficiariesVulnerabilitiesAndServices(
			@RequestParam(name = "startDate", defaultValue = "yyyy-mm-dd") String startDate, 
			@RequestParam(name = "endDate", defaultValue = "yyyy-mm-dd") String endDate)
			throws IOException {

		AgywPrevReport report = new AgywPrevReport(service);

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.parse(startDate);
			sdf.parse(endDate);
		} catch (ParseException e) {
			logger.warn("Start date: " + startDate + " or End date: "
					+ " has wrong format, please correct to allowed format: YYYY-MM-DD");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			List<BeneficiaryVulnerability> reportObjectList = report.getBeneficiariesVulnerabilities(startDate,
					endDate);

			return new ResponseEntity<>(reportObjectList, HttpStatus.OK);
		} catch (Exception e) {
			logger.warn("System ran into an unknown error while trying to fecth data");
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
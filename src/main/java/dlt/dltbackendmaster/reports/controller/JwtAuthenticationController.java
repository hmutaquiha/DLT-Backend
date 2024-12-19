package dlt.dltbackendmaster.reports.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import dlt.dltbackendmaster.cfgs.AppConfig;
import dlt.dltbackendmaster.reports.domain.JwtRequest;
import dlt.dltbackendmaster.reports.domain.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Authentication", description = "Use this to generate the token to be able to execute other endpoints")
public class JwtAuthenticationController {

	@Autowired
	private AppConfig appConfig;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	@Operation(operationId = "authenticate", summary = "Login to DLT", description = "Fill your credentials on Request body and execute to get the token to authorize execution of other endpoints")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		HttpClient client = HttpClient.newBuilder().build();

		String url = appConfig.getApiHome() + "/api/login?username=" + authenticationRequest.getUsername()
				+ "&password=" + authenticationRequest.getPassword();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString("{}"))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(response.body(), new TypeReference<>() {});

		return ResponseEntity.ok(new JwtResponse((String) map.get("token")));
	}
}

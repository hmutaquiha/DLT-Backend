package dlt.dltbackendmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"dlt.dltbackendmaster.service.Service"})
public class DltBackendMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DltBackendMasterApplication.class, args);
	}

}

package dlt.dltbackendmaster.Initializers;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * @author derciobucuane
 *
 */
@Configuration
@ComponentScan
public class WebConfigurations implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(new CORSInterceptor());
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}

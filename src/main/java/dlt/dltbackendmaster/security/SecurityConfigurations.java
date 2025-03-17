package dlt.dltbackendmaster.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

/**
 * 
 * @author derciobucuane
 *
 */
@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
	
	@Autowired
	private final TokenAuthenticationService tokenAuthenticationService;
	
	public SecurityConfigurations() {
		this.tokenAuthenticationService = new TokenAuthenticationService(userDetailsServiceImpl);
	}

	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsServiceImpl);
	}
	
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		List<Header> httpheaders = new ArrayList<Header>();
		httpheaders.add(new Header("Access-Control-Allow-Origin", "*"));
		httpheaders.add(new Header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE"));
		httpheaders.add(new Header("Access-Control-Allow-Headers", "*"));
		httpheaders.add(new Header("Access-Control-Max-Age", "3600"));
		
		
		http.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(entryPointUnauthorizedHandler).and()
			.anonymous().and()
			.servletApi().and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
			
			.antMatchers("/").permitAll()
				//Verify if backend is oN
			.antMatchers("/ping/**").permitAll()
            .antMatchers("/resources/**").permitAll()
            	//allow anonymous to Sync
            .antMatchers(HttpMethod.POST, "/sync").permitAll()
            .antMatchers(HttpMethod.GET, "/sync").permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/api/login").permitAll()
            //allow anonymous to Confirm Update Password
            .antMatchers("/api/users/username/**").permitAll()
            .antMatchers(HttpMethod.PUT, "/users").permitAll()
            .antMatchers(HttpMethod.PUT, "/api/users/change-password").permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/users").permitAll()
            	//allow anonymous POSTs to login
            .antMatchers(HttpMethod.POST, "/api/login").permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/api/login").permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
            	// allow anonymous POSTs to authenticate
            .antMatchers(HttpMethod.POST, "/authenticate").permitAll()
            .antMatchers(HttpMethod.POST, "/authenticate/**").permitAll()
            	//allow anonymous GETs to API
            .antMatchers(HttpMethod.GET, "/api/**").authenticated()
            .antMatchers(HttpMethod.POST, "/api/**").permitAll()
            .antMatchers(HttpMethod.PUT, "/api/**").authenticated()
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
            .regexMatchers(HttpMethod.OPTIONS, "").permitAll()
            .anyRequest().permitAll().and()
            	//all other request need to be authenticated
            	// custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
            .addFilterBefore(new StatelessLoginFilter("/api/login", tokenAuthenticationService, userDetailsServiceImpl, authenticationManager()), 
            		UsernamePasswordAuthenticationFilter.class)
            	// custom Token based authentication based on the header previously given to the client
            .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class)
            .headers()
    		.referrerPolicy(ReferrerPolicy.SAME_ORIGIN).and()
    		.addHeaderWriter(new StaticHeadersWriter(httpheaders));
		
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	@Override
	protected UserDetailsService userDetailsService() {
		return userDetailsServiceImpl;
	}
	
	@Bean
	public TokenAuthenticationService tokenAuthenticationService() {
		return tokenAuthenticationService;
	}
	
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
	
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        // FIXME: Put valid credentials for e-mail and follow instructions on the link 
        // https://roytuts.com/gmail-smtp-and-security-settings-for-sending-email/
        mailSender.setUsername("change@email.here");
        mailSender.setPassword("changepasswordhere");

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.allow8bitmime", true);
        javaMailProperties.put("mail.smtps.allow8bitmime", true);

        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }

    @Bean
    public MailMessage mailMessage() {
        return new SimpleMailMessage();
    }
}

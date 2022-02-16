package dlt.dltbackendmaster.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * 
 * @author derciobucuane
 *
 */
public class StatelessAuthenticationFilter  extends GenericFilterBean{
	private final TokenAuthenticationService tokenAuthenticationService;
	
	public StatelessAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
		this.tokenAuthenticationService = tokenAuthenticationService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(tokenAuthenticationService.getAuthentication((HttpServletRequest)request));
		HttpServletRequest request1 = (HttpServletRequest) request;
		if(!request1.getMethod().equals("OPTIONS")){
            chain.doFilter(request, response);
        }
		//chain.doFilter(request, response);
	}
}

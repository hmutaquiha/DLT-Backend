package dlt.dltbackendmaster.Initializers;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author derciobucuane
 *
 */
public class CORSFilter implements Filter {
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse)response;
		HttpServletRequest req = (HttpServletRequest)request;
		res.addHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        res.addHeader("Access-Control-Max-Age", "3600");
        res.addHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization, User-Agent, Expires");
        res.addHeader("Access-Control-Expose-Headers", "User-Agent, Expires");
		
        if(!req.getMethod().equals("OPTIONS")){
            chain.doFilter(request, response);
        }
	}
	
	@Override
    public void destroy() {
    }

}

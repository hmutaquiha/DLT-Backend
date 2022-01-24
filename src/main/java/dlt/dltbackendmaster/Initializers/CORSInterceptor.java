package dlt.dltbackendmaster.Initializers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 
 * @author derciobucuane
 *
 */
public class CORSInterceptor implements HandlerInterceptor{

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.addHeader("Content-Type", request.getHeader("Content-Type"));
        response.addHeader("Accept", request.getHeader("Accept"));
        //response.addHeader("X-Auth-Token", request.getHeader("X-Auth-Token"));
        return true; 
	}
}

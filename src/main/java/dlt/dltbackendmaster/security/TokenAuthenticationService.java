package dlt.dltbackendmaster.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import dlt.dltbackendmaster.domain.Account;


/**
 * 
 * @author derciobucuane
 *
 */
public class TokenAuthenticationService {
	private static final String SECRET = "9SyECk96oDsTmXfosdieDI0cD/8FpnojlYSUJT5U9I/FGVmBz5oskmjOR8cbXTvoPjX+Pq/T/b1PqpHX0lYm1oCBjXWICA==";
	private static final String AUTH_HEADER_NAME = "Authorization";

	private final TokenHandler tokenHandler;
	
	@Autowired
	private final UserDetailsServiceImpl serviceImpl;
	
	public TokenAuthenticationService(UserDetailsServiceImpl serviceImpl) {
        this.serviceImpl = serviceImpl;
        tokenHandler = new TokenHandler(SECRET);
    }
	
	@SuppressWarnings("unchecked")
	public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) throws IOException {
        final Account account = (Account) authentication.getDetails();
        //account.setExpires(System.currentTimeMillis() + ONE_HOUR);
        response.addHeader(AUTH_HEADER_NAME, "Bearer " + tokenHandler.createTokenForUser(account));
		List<GrantedAuthority> auths = (List<GrantedAuthority>) account.getAuthorities();
        response.addHeader("User-Agent", auths.get(0).getAuthority()); 
        
    }
    
    public Authentication getAuthentication(HttpServletRequest request) {
        final String authorizationHandler = request.getHeader(AUTH_HEADER_NAME);
        String token = null;
        String username = null;
        
        if (authorizationHandler != null && authorizationHandler.startsWith("Bearer ")) {
        	token = authorizationHandler.substring(7);
        	username = tokenHandler.parseUserFromToken(token);
            final Account account = serviceImpl.loadUserByUsername(username);
            if (account != null) {
                return new UserAuthentication(account);
            }
        }
        return null;
    }

}

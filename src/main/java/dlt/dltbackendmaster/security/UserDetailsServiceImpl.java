package dlt.dltbackendmaster.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.service.DAOService;

/**
 * 
 * @author derciobucuane
 *
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService{
	private static final String QUERY_FIND_USER_BY_USERNAME = "select aa from Users aa where aa.username = :username";
	
	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
	
	@Autowired
	DAOService service;

	@Override
	public Users loadUserByUsername(String username) throws UsernameNotFoundException {
		Map<String, Object> todo = new HashMap<String, Object>();
        todo.put("username", username);
        
		try {
			List<Users> users = service.findByJPQuery(QUERY_FIND_USER_BY_USERNAME, todo);
			if(users != null && !users.isEmpty()) {
				Users account = users.get(0);
				detailsChecker.check(account);
				return account;
			}
			
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
		throw new UsernameNotFoundException("no user found with " + username);
	}
	
	

	
}

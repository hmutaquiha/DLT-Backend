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

import dlt.dltbackendmaster.domain.Account;
import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.service.DAOService;

/**
 * 
 * @author derciobucuane
 *
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService{
	private static final String QUERY_FIND_USER_BY_USERNAME = "select u from Users u where u.username = :username";
	
	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
	
	@Autowired
	DAOService service;

	@Override
	public Account loadUserByUsername(String username) throws UsernameNotFoundException {
		Map<String, Object> todo = new HashMap<String, Object>();
        todo.put("username", username);
 
		try {
			List<Users> users = service.findByJPQuery(QUERY_FIND_USER_BY_USERNAME, todo);
			
			if(users != null && !users.isEmpty()) {
				Account account = new Account(users.get(0));
				detailsChecker.check(account);
				if (account.getStatus() == 0) {
					throw new UsernameNotFoundException("user " + username + " is inactive ");
				}
				return account;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(e.getMessage());
		}
		throw new UsernameNotFoundException("no user found with " + username);
	}
	
	

	
}

package dlt.dltbackendmaster.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import dlt.dltbackendmaster.domain.Account;

/**
 * 
 * @author derciobucuane
 *
 */
public class UserAuthentication implements Authentication{
	
	private static final long serialVersionUID = 1L;
	private final Account account;
	private boolean authenticated = true;

    public UserAuthentication(Account account) {
        this.account = account;
    }

	@Override
	public String getName() {
		return account.getUsername();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return account.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return account.getPassword();
	}

	@Override
	public Object getDetails() {
		return account;
	}

	@Override
	public Object getPrincipal() {
		return account;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated = isAuthenticated;
	}

}

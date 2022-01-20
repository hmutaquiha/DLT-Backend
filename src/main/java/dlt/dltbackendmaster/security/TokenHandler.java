package dlt.dltbackendmaster.security;

import dlt.dltbackendmaster.domain.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author derciobucuane
 *
 */
public class TokenHandler {

	private final String secret;
	
	public TokenHandler(String secret) {
        this.secret = secret;
    }
	
	public String parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username;
    }
    
    public String createTokenForUser(Account account) {
        return Jwts.builder()
                .setSubject(account.getUsername())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}

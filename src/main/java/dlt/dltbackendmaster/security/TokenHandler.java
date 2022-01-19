package dlt.dltbackendmaster.security;

import dlt.dltbackendmaster.domain.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
    
    public String createTokenForUser(Users account) {
        return Jwts.builder()
                .setSubject(account.getUsername())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}

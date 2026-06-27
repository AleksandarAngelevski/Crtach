package org.example.crtachbackend.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class used for generating, parsing and
 * validating JWT tokens
 */
@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private Long jwtExpiration;

    /**
     * Method that extracts the username
     * from the token
     *
     * @param token - the token param
     *              used for extraction
     *
     * @return - returns the extracted
     *          username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    /**
     * Method to extract a specific claim from
     * the token using a claim's resolver.
     *
     * @param <T> - the type of the claim target
     * @param token - the JWT token string
     * @param claimsResolver - the functional interface to resolve
     *                      the target claim
     *
     * @return the resolved claim value
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Method that generates a token
     *
     * @param userDetails - the user details param
     *                    used for generating the
     *                    token for the user
     *
     * @return - returns a token with the user
     *          username
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Method that generates a token
     *
     * @param extraClaims - the extra claims param
     *                    used for injection in the token
     *
     * @param userDetails - the user details param
     *                    used for injecting username
     *                    in the token
     *
     * @return - returns a token with the extra claims,
     *          username, and an expiration time
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Method that gets the expiration time configured
     * in the application properties
     *
     * @return - returns the expiration time
     */
    public long getExpirationTime() {
        return jwtExpiration;
    }

    /**
     * Method that builds the token
     *
     * @param extraClaims - the extra claims param
     *                    used for the first part of
     *                    the token
     *
     * @param userDetails - the user details param
     *                    that provides the username
     *                    of the user for the token
     *
     * @param expiration - the expiration time param
     *                   that provides the expiration
     *                   time of the token
     *
     * @return - returns a built and hashed jwt token
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Method that checks the validity of
     * a token
     *
     * @param token - the token param that's
     *              used for validating
     *
     * @param userDetails - the user details param
     *                    that provides the username
     *                    of a user
     *
     * @return - returns true if token is valid
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Method that checks if a token
     * is expired
     *
     * @param token - the token param used
     *              for validating
     *
     * @return - returns true if a token is
     *          expired
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Method that extracts expiration date
     * from a token
     *
     * @param token - the token used for
     *              extraction
     *
     * @return - returns the extracted
     *          expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Method that extracts all claims from
     * a token
     *
     * @param token - the token param
     *              used for extraction
     *
     * @return - returns the extracted claims
     *          of a jwt token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Method that decodes jwt secret key
     *
     * @return - returns a sha cryptographic
     *          key object
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

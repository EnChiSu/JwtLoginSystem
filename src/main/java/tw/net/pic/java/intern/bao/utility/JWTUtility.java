package tw.net.pic.java.intern.bao.utility;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import tw.net.pic.java.intern.bao.model.User;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtility implements Serializable {

    private static final long serialVersionUID = 234234523523L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private String secretKey = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public int getRoleIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("rid", Integer.class);
    }

    public String getUserIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        JwtParser parser = Jwts.parser();
        parser.setSigningKey(secretKey);
        Jws<Claims> jwtclaims = parser.parseClaimsJws(token);
        Claims body = jwtclaims.getBody();
        return body;
        //return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }


    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }


    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }


    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

//    //generate token for user
//    public String generateJwtToken(String username, long expireTime, String secretKey) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
//                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
//    }

    //generate token for user
    public String generateJwtToken(User user, long expireTime, String secretKey) {
        final long currdate = System.currentTimeMillis();
        return Jwts.builder()
                .setId(user.getId())
                .setSubject(user.getUsername())
                .claim("rid", user.getRole_id())
                .setIssuedAt(new Date(currdate))
                .setExpiration(new Date( currdate + expireTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}



package tw.net.pic.java.intern.bao.service;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import tw.net.pic.java.intern.bao.model.User;
import tw.net.pic.java.intern.bao.model.UserPrincipal;
import tw.net.pic.java.intern.bao.utility.JWTUtility;

public class TokenAuthenticationService {

    //864_000_000
    static final public long _expiretime = 1*60*1000;
    static final public String TOKEN_NAME = "bao_access_token";
    static final String _secret = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";;
    static final String _token_prefix = "Bearer";
    static final String _header_string = "Authorization";

    static public String generateJWT(User principal) throws IOException {
        return (new JWTUtility()).generateJwtToken(principal, _expiretime, _secret);
    }

    public static void addAuthentication(HttpServletResponse res, User principal) throws IOException {
        //String JWT = new JWTUtility().generateJwtToken(username, _expiretime, _secret);
        String JWT = new JWTUtility().generateJwtToken(principal, _expiretime, _secret);

        res.addHeader(_header_string, _token_prefix + " " + JWT);
        res.setContentType("application/json");
        res.setStatus(HttpServletResponse.SC_OK);
        res.getOutputStream().println((new JSONObject(){
            {
                put("status", 200);
                put("message", "Your token has generated successfully!");
                put("result", JWT);
            }
        }).toString());
    }

//    public static Authentication getAuthentication(HttpServletRequest request) {
//        String token = request.getHeader(_header_string);
//        if (token != null) {
//            // parse the token.
//            String user = Jwts.parser().setSigningKey(_secret).parseClaimsJws(token.replace(_token_prefix, "")).getBody()
//                    .getSubject();
//            return user != null ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) : null;
//        }
//        return null;
//    }
}
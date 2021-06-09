package tw.net.pic.java.intern.bao.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UrlPathHelper;
import tw.net.pic.java.intern.bao.model.UserPrincipal;
import tw.net.pic.java.intern.bao.service.TokenAuthenticationService;
import tw.net.pic.java.intern.bao.service.UserDetailsServiceImpl;
import tw.net.pic.java.intern.bao.utility.JWTUtility;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.json.JSONObject;


public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private JWTUtility jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    private static Logger logger = LoggerFactory.getLogger(JwtLoginFilter.class);
    private final static UrlPathHelper urlPathHelper = new UrlPathHelper();

    public JwtLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url,"POST"));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        // post方式取使用者帳密做驗證
        String s = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(s);
        HashMap<String, String> result = new HashMap<String, String>();
        String key = null;
        Iterator<?> keys = jsonObject.keys();
        while(keys.hasNext()) {
            key = (String) keys.next();
            result.put(key, jsonObject.getString(key));
        }

        System.out.printf("JwtLoginFilter.attemptAuthentication: username/password= %s,%s", result.get("username"), result.get("password"));
        System.out.println();

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(result.get("username"), result.get("password"), Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //登入驗證比對的終點(成功), 產生JWT
        System.out.println("JwtLoginFilter.successfulAuthentication, Authentication:"+authResult);
        SecurityContextHolder.getContext().setAuthentication(authResult);

        chain.doFilter(request, response);
    }

    @Override
    @ResponseBody
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //登入驗證比對的終點(失敗)
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().println((new JSONObject(){
            {
            put("status", 500);
            put("message", "Internal Server Error!!!");
            put("result", JSONObject.NULL);
            }
        }).toString());
        response.setHeader("Message", "Could not validate your username and password!!");
        response.setHeader("Location", "http://localhost:8888/index");
        System.out.println("JwtLoginFilter.unsuccessfulAuthentication: Could not validate your username and password!!");
    }
}

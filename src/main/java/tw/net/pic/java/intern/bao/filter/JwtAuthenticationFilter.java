package tw.net.pic.java.intern.bao.filter;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tw.net.pic.java.intern.bao.service.TokenAuthenticationService;
import tw.net.pic.java.intern.bao.service.UserDetailsServiceImpl;
import tw.net.pic.java.intern.bao.utility.JWTUtility;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
// 與 UsernamePasswordAuthenticationFilter 不同，BasicAuthenticationFilter 沒有繼承 AbstractAuthenticationProcessingFilter，
// 而是直接繼承 OncePerRequestFilter。因為它是被使用在請求業務 API 的請求上，而不是進行身份認證流程。
// 無論認證成功與否，BasicAuthenticationFilter 都不會做出重定向的響應。
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //驗證JWT的起點, 當JWT為空將會拋出exception, 進而轉到登入頁面, 若有JWT將進行驗證.
        String requestTokenHeader = null;
        if(httpServletRequest != null && httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (TokenAuthenticationService.TOKEN_NAME.equals(cookie.getName())) {
                    requestTokenHeader = cookie.getValue();
                    break;
                }
            }
        }
        //String requestTokenHeader = httpServletRequest.getHeader("bao_access_token");
        String token = null;
        String userName = null;

        System.out.println("doFilterInternal requestTokenHeader:"+requestTokenHeader);

//        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//            token = requestTokenHeader.substring(7);
//            logger.info(token);
//            try {
//                userName = jwtUtility.getUsernameFromToken(token);
//                System.out.println("filter userName:"+userName);
//                System.out.println("filter user id:"+jwtUtility.getUserIdFromToken(token));
//                System.out.println("filter role id:"+jwtUtility.getRoleIdFromToken(token));
//            } catch (IllegalArgumentException e) {
//                System.out.println("Unable to get username with the JWT Token");
//            } catch (ExpiredJwtException e) {
//                System.out.println("JWT Token has expired");
//            }
//        }

        String userId = null;
        if(requestTokenHeader != null && requestTokenHeader.length() > 0) {
            token = requestTokenHeader;

            try {
                userName = jwtUtility.getUsernameFromToken(token);
                userId = this.jwtUtility.getUserIdFromToken(token);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.out.println("Unable to get username with the JWT Token");
                SecurityContextHolder.getContext().setAuthentication(null);
            } catch (ExpiredJwtException e) {
                e.printStackTrace();
                System.out.println("JWT Token has expired");
                //清除httponly cookie
                SecurityContextHolder.getContext().setAuthentication(null);
                Cookie cookie = new Cookie(TokenAuthenticationService.TOKEN_NAME, null);
                cookie.setMaxAge(0);
                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                httpServletResponse.addCookie(cookie);
            }
        }

        //TODO
        //如果路徑為根路徑 '/', 有Token並且有效, 應該依身分轉到該首頁!

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //登入之後, 這裡將會有資料
        System.out.println("doFilterInternal userName:"+userName+", authentication:"+authentication);
        // Once we get the token, validate it.
        if(null != userId && authentication == null) {
            UserDetails userDetails
                    = userDetailsServiceImpl.loadUserById(userId);
//            UserDetails userDetails
//                    = userDetailsServiceImpl.loadUserByUsername(userName);

            System.out.println("doFilterInternal userDetails by id:"+userDetails);

            // if token is valid configure Spring Security to manually set authentication
            if(jwtUtility.validateToken(token,userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                );

                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
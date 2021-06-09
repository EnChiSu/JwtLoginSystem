package tw.net.pic.java.intern.bao.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.ModelAndView;
import tw.net.pic.java.intern.bao.model.Role;
import tw.net.pic.java.intern.bao.model.User;
import tw.net.pic.java.intern.bao.model.UserInfoVo;
import tw.net.pic.java.intern.bao.model.UserPrincipal;
import tw.net.pic.java.intern.bao.service.TokenAuthenticationService;
import tw.net.pic.java.intern.bao.service.UserDetailsServiceImpl;
import tw.net.pic.java.intern.bao.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Controller
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    private String detectAvailableAccessTokenOnRootOrLogin(HttpServletRequest request) {
        String targetUrl = "/index";
        String jwt = null;
        if (request != null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (TokenAuthenticationService.TOKEN_NAME.equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    System.out.println("Controller token:" + jwt);
                    break;
                }
            }

            boolean needRelogin = false;
            if(jwt == null || jwt.length() == 0) {
                needRelogin = true;
            } else {
                Date date = jwtUtility.getExpirationDateFromToken(jwt);
                if(date.compareTo(new Date(System.currentTimeMillis())) <= 0) {
                    needRelogin = true;
                }
            }

            if(needRelogin) {
                return targetUrl;
            }

            Integer rid = new JWTUtility().getRoleIdFromToken(jwt);
            System.out.println("Controller role id: " + rid);
            //根據使用者的身分將使用者導到下一個頁面
            if (rid == 2) {
                targetUrl = "/customer";
            } else  {
                targetUrl = "/store";
            }
        }
        return targetUrl;
    }

    @GetMapping("/")
    public String loginRoot(HttpServletRequest request) {
        return this.detectAvailableAccessTokenOnRootOrLogin(request);
    }

    @GetMapping("/error")
    public String error(Model model) {
        return "error";
    }

    @GetMapping("/index")
    public String loginIndex(HttpServletRequest request) {
        return this.detectAvailableAccessTokenOnRootOrLogin(request);
    }

//    @PostMapping(path = "/tokenTest")
//    public ResponseEntity<String> tokenFromHeaderTest(@CookieValue(name=TokenAuthenticationService.TOKEN_NAME) String jwtToken) {
//        System.out.println("run tokenFromHeaderTest....jwtToken:"+jwtToken);
//        System.out.println("filter userName:" + jwtUtility.getUsernameFromToken(jwtToken));
//        System.out.println("filter user id:" + jwtUtility.getUserIdFromToken(jwtToken));
//        System.out.println("filter role id:" + jwtUtility.getRoleIdFromToken(jwtToken));
//        return ResponseEntity.ok("reponse from tokenFromHeaderTest!");
//    }

    @RequestMapping(path = "/index", method = RequestMethod.POST)
    @ResponseBody
    public void loginPost(Authentication authResult, HttpServletResponse response) throws ServletException, IOException {
        //authResult來源為UserDetailService.loadUserByusername裡所給的資料
        System.out.println("loginPost Authentication:"+authResult);

        //取這位user的身分
        String role = null;
        if(authResult.getAuthorities()!= null && !authResult.getAuthorities().isEmpty()) {
            role = authResult.getAuthorities().toArray()[0].toString();

        }
        System.out.println("role: "+role);

        //根據使用者的身分將使用者導到下一個頁面
        String targetUrl = "";
        if(Role.ROLE_CUSTOMER.equals(role)) {
            targetUrl = "http://localhost:8888/customer";
        } else if(Role.ROLE_STORE.equals(role)) {
            targetUrl = "http://localhost:8888/store";
        } else {
            throw new ServletException("No Authentication");
        }

        //產生token
        final String jwt = TokenAuthenticationService.generateJWT(((UserPrincipal)authResult.getPrincipal()).getPrincipal());

        ResponseCookie rspCookie = ResponseCookie.from(TokenAuthenticationService.TOKEN_NAME, jwt)
                .domain("localhost")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(TokenAuthenticationService._expiretime)
                .build();

        //將後面需要的資訊塞入response的header
        response.setHeader(HttpHeaders.AUTHORIZATION, jwt);
        response.setHeader(HttpHeaders.SET_COOKIE, rspCookie.toString());
        response.setHeader("Message", "You have successfully logged in!");
        response.setHeader("Location", targetUrl);
        System.out.println(targetUrl);
    }

    @GetMapping("/customer")
    public String welcomeCust(Model model) {
        return "customer";
    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public String customerPost(@RequestBody Model model, @Validated UserInfoVo userInfoVo, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "index";
        }
        model.addAttribute("username", userInfoVo.getUsername());
        return "customer";
    }

    @GetMapping("/store")
    public String welcomeStore(Model model) {
        return "store";
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public String storePost(@RequestBody Model model, @Validated UserInfoVo userInfoVo, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "index";
        }
        model.addAttribute("username", userInfoVo.getUsername());
        return "store";
    }
}
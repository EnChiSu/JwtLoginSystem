package tw.net.pic.java.intern.bao.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tw.net.pic.java.intern.bao.model.User;
import tw.net.pic.java.intern.bao.model.UserInfoVo;
import tw.net.pic.java.intern.bao.model.UserPrincipal;
import tw.net.pic.java.intern.bao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        //username指的是在登入時, 由前端用戶在登入畫面所輸入的帳號資料, 所以這段會在登入驗證時啟動,
        //讀出是否有該帳號資料, 若查到就回傳做後續驗證比對
        final User user = userRepository.findByUsername(username);
        System.out.println("loadUserByUsername , user:"+user);
        if (user == null) throw new UsernameNotFoundException("user name:"+username);
        return new UserPrincipal(user);
    }

    public UserDetails loadUserById(String userId) {
        if (userId == null || userId.length() <0 ) {
            throw new UsernameNotFoundException("no user id:"+userId);
        }

        final User user = userRepository.findById(userId).get();
        if(user == null) {
            throw new UsernameNotFoundException("User not found:"+userId);
        }
        return new UserPrincipal(user);
    }

    public void save(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRole(roleRepository.findById(user.getId()).get()); //可以透過user的id去找他的role，但因為我是在註冊時需要做save，此時db裡還沒有user的資料，所以用不到。
//        之後如果要設定role對權限的對應，可以在DB裡面設計一張role對權限的表，可以根據user輸入的role用這邊的roleRepository去這張表找相對應的權限，在使用者註冊的同時一併將權限存入資料庫
        userRepository.save(user);
    }

}

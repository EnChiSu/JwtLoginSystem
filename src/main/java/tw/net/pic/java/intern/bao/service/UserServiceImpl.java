//package tw.net.pic.java.intern.bao.service;
//
//import tw.net.pic.java.intern.bao.model.Role;
//import tw.net.pic.java.intern.bao.model.User;
//import tw.net.pic.java.intern.bao.repository.RoleRepository;
//import tw.net.pic.java.intern.bao.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.Optional;
//
//@Service
//public class UserServiceImpl implements UserService {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private RoleRepository roleRepository;
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Override
//    public void save(User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
////        user.setRole(roleRepository.findById(user.getId()).get()); //可以透過user的id去找他的role，但因為我是在註冊時需要做save，此時db裡還沒有user的資料，所以用不到。
////        之後如果要設定role對權限的對應，可以在DB裡面設計一張role對權限的表，可以根據user輸入的role用這邊的roleRepository去這張表找相對應的權限，在使用者註冊的同時一併將權限存入資料庫
//        userRepository.save(user);
//    }
//
//    @Override
//    public User findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//}

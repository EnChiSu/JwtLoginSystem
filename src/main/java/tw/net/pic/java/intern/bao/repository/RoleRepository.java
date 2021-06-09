package tw.net.pic.java.intern.bao.repository;

import tw.net.pic.java.intern.bao.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import tw.net.pic.java.intern.bao.model.User;

public interface RoleRepository extends JpaRepository<Role, Long> {
}

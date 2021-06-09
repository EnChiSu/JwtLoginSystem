package tw.net.pic.java.intern.bao.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.net.pic.java.intern.bao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

    @Query(value="select * from user_account data where data.id = :comId",nativeQuery = true)
    User getUserAccountName(String comId);

    @Query(value="select * from user_account data where data.id = :cid",nativeQuery = true)
    User findUserAccountByComId(String cid);

    @Modifying //需要加入@Modifying，如果沒有，則會顯示could not extract ResultSet錯誤
    @Query(value="update user_account set name = :name,modify_date = :modifyDate where id = :comId",nativeQuery = true)
    void updateUserAccountData(String comId, String name, Date modifyDate);
}
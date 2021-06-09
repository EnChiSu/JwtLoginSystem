//package tw.net.pic.java.intern.bao.service;
//import javax.transaction.Transactional;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import tw.net.pic.java.intern.bao.model.User;
//import tw.net.pic.java.intern.bao.repository.IUserInfoDao;
//
//import java.text.ParseException;
//import java.util.List;
//
//
//@Service("userInfoService")
//@Transactional(rollbackOn = Exception.class)
//public class UserInfoServiceImpl implements IUserInfoService {
//    private static final Logger logger =
//            LoggerFactory.getLogger(UserInfoServiceImpl.class);
//    @Autowired
//    private IUserInfoDao userInfoDao;
//    @Override
//    public User get(String id) {
//        return userInfoDao.get(id);
//    }
//    @Override
//    public List<User> findAll() {
//        return userInfoDao.findAll();
//    }
//    @Override
//    public String save(User userInfo) {
//        return userInfoDao.save(userInfo);
//    }
//    public void delete(User userInfo) {
//        userInfoDao.delete(userInfo);
//    }
//    @Override
//    public void update(User userInfo) {
//        userInfoDao.update(userInfo);
//    }
//    @Override
//    public void muiltSave(int age) throws Exception {
////		UserInfo userInfo = new UserInfo();
////		userInfo.setId(UUID.randomUUID().toString());
////		userInfo.setName("ada");
////		userInfo.setSignupDate(new Date());
////		userInfoDao.save(userInfo);
////		if (age != 0) {//預設age=0如果不是0會跳例外處理
////			logger.info("throw new Exception age=" + age);
////			throw new Exception();
////		}
////		UserInfo userInfo2 = new UserInfo();
////		userInfo2.setId(UUID.randomUUID().toString());
////		userInfo2.setName("ada");
////		userInfo2.setSignupDate(new Date());
////		userInfoDao.save(userInfo2);
//    }
//    @Override
//    public void deleteFromName(String name) {
//        userInfoDao.deleteFromName(name);
//    }
//    @Override
//    public List<User> findUserByName(String name) {
//        return userInfoDao.findUserByName(name);
//    }
//    @Override
//    public void delete(String id) {
//        userInfoDao.delete(id);
//    }
//    @Override
//    public void update(String id, String name, String signupDate) throws ParseException {
//        userInfoDao.update(id, name, signupDate);
//    }
//}

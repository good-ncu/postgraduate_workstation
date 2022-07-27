package good.ncu.yjs_bb.back.service.imp;

import good.ncu.yjs_bb.back.dao.UserDao;
import good.ncu.yjs_bb.back.entity.User;
import good.ncu.yjs_bb.back.service.UserService;
import good.ncu.yjs_bb.back.until.SecureDESUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserDao userDao;

    // 不是自动装载，记得要实例化
    SecureDESUtil secureDESUtil = new SecureDESUtil();


    @Override
    public User userLogin(@Param("user_name") String user_name, @Param("user_passwd") String user_passwd) {
        // 加密用户密码
        String ec_passwd = secureDESUtil.encrypt(user_passwd);
        return userDao.userLogin(user_name, ec_passwd);
    }

    @Override
    public List<User> getAllUser(String user_name, int pageStart, int pageSize, String univ_code) {
        return userDao.getAllUser(user_name, pageStart, pageSize, univ_code);
    }

    @Override
    public int getUserCounts(String us) {
//        String user_name = "%" + us + "%";
        return userDao.getUserCounts(us);
    }

    @Override
    public int updateState(Integer user_id, Boolean user_status) {
        return userDao.updateState(user_id, user_status);
    }

    @Override
    public User getUpdateUser(int user_id) {
        User us = userDao.getUpdateUser(user_id);
        us.setUser_passwd("");
        return us;
    }

    @Override
    public int editUser(User user) {
//        // 加密用户密码
//        String ec_passwd = secureDESUtil.encrypt(user.getUser_passwd());
//        user.setUser_passwd(ec_passwd);
        return userDao.editUser(user.getUser_sbdw(), user.getUser_gxphone(), user.getUser_sbdwphone(), user.getUser_id());
    }

    @Override
    public int updatePasswd(String user_passwd, Integer user_id) {
        String ec_passwd = secureDESUtil.encrypt(user_passwd);
        return userDao.updatePasswd(ec_passwd, user_id);
    }

    @Override
    public int searchUserName(String user_name) {
//        System.out.println("s" + user_name);
        return userDao.searchUserName(user_name);
    }

    @Override
    public int addUser(User user) {
        // 加密用户密码
        String ec_passwd = secureDESUtil.encrypt(user.getUser_passwd());
        user.setUser_passwd(ec_passwd);
        return userDao.addUser(user);
    }

    @Override
    public int findUserByID(Integer user_id) {
        return userDao.findUserByID(user_id);
    }

    @Override
    public List<User> getAllUser_gx(String user_name, int pageStart, int pageSize) {
        return userDao.getAllUser_gx(user_name, pageStart, pageSize);
    }

    @Override
    public int getUserCounts_gx(String user_name) {
        return userDao.getUserCounts_gx(user_name);
    }

    @Override
    public List<User> getAllUser_dw(String user_name, int pageStart, int pageSize) {
        return userDao.getAllUser_dw(user_name, pageStart, pageSize);
    }

    @Override
    public int getUserCounts_dw(String user_name) {
        return userDao.getUserCounts_dw(user_name);
    }

}

package good.ncu.yjs_bb.back.service;

import good.ncu.yjs_bb.back.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    public User userLogin(@Param("user_name") String user_name, @Param("user_passwd") String user_passwd);

    //组合拳
    public List<User> getAllUser(@Param("user_name") String user_name, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize, @Param("univ_code") String univ_code);
    public int getUserCounts(@Param("user_name") String user_name);

    public int updateState(@Param("user_id") Integer user_id,@Param("user_status") Boolean user_status);

    public User getUpdateUser(int user_id);

    public int editUser(User user);

    public int updatePasswd(String user_passwd, Integer user_id);

    public int searchUserName(String user_name);

    public int addUser(User user);

    public int findUserByID(Integer user_id);

    public List<User> getAllUser_gx(@Param("user_name") String user_name, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);
    public int getUserCounts_gx(@Param("user_name") String user_name);

    public List<User> getAllUser_dw(@Param("user_name") String user_name, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);
    public int getUserCounts_dw(@Param("user_name") String user_name);
}

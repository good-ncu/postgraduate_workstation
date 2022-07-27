package good.ncu.yjs_bb.back.dao;


import good.ncu.yjs_bb.back.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao {
    public User userLogin(@Param("user_name") String user_name, @Param("user_passwd") String user_passwd);

    //用户列表
    public List<User> getAllUser(@Param("user_name") String user_name, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize, @Param("univ_code") String univ_code);

    public int getUserCounts(@Param("user_name") String user_name);

    public int updateState(@Param("user_id") Integer user_id, @Param("user_status") Boolean user_status);


    public User getUpdateUser(int user_id);

    public int editUser(String user_sbdw, String user_gxphone, String user_sbdwphone,Integer user_id);

    public int updatePasswd(String user_passwd, Integer user_id);

    public int searchUserName(String user_name);

    public int addUser(User user);

    public int findUserByID(Integer user_id);

    // 省厅用户管理高校管理员用户
    public List<User> getAllUser_gx(@Param("user_name") String user_name, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);
    public int getUserCounts_gx(@Param("user_name") String user_name);

    public List<User> getAllUser_dw(@Param("user_name") String user_name, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);
    public int getUserCounts_dw(@Param("user_name") String user_name);
}

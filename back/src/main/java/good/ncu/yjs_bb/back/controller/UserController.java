package good.ncu.yjs_bb.back.controller;


import com.alibaba.fastjson.JSON;
import good.ncu.yjs_bb.back.entity.User;
import good.ncu.yjs_bb.back.service.UserService;
import good.ncu.yjs_bb.back.until.SecureDESUtil;
import good.ncu.yjs_bb.back.until.TokenUtil;
import good.ncu.yjs_bb.back.until.VerificationCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    // 验证码接口
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        HttpSession session = request.getSession(true);
        session.setAttribute("verify_code", text);
        System.out.println(text);
        VerificationCode.output(image, resp.getOutputStream());
    }


    // 登录接口
    @RequestMapping("/userLogin")
    public String userLogin(@RequestBody User user, HttpServletRequest request, HttpServletResponse resp) {
        HashMap<String, Object> res = new HashMap<>();

        if (user == null) {
            res.put("flag", "error");
            res.put("msg", "空对象！");
            return JSON.toJSONString(res);
        }

        // 判定验证码
        if (request.getSession(true).getAttribute("verify_code") == null || !user.getVerifyCode().toUpperCase().equals(request.getSession(true).getAttribute("verify_code").toString().toUpperCase())) {
            res.put("status", 300);
            res.put("msg", "验证码错误");
            res.put("flag", "error");
            System.out.println("用户" + user.getUser_name() + "验证码错误~");
            return JSON.toJSONString(res);
        }

        // flag判断登录情况
        String flag = "error";
        // role判断用户身份，进入对应界面
        String role = "";
        User us = userService.userLogin(user.getUser_name(), user.getUser_passwd());
        if (us != null) {
            flag = "ok";
            role = us.getUser_role();
            System.out.println("用户" + user.getUser_name() + "登录成功~");
            // 生成token
            String token = TokenUtil.sign(user);
            res.put("token", token);
        } else {
            System.out.println("用户" + user.getUser_name() + "登录失败！");
        }


        res.put("flag", flag);
        res.put("user", us);
        res.put("role", role);
        String res_json = JSON.toJSONString(res);

        return res_json;
    }


    //获取报备单位列表
    @RequestMapping("/allUser_dw")
    public String getUserList_st_dw(@RequestParam("query") String query, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        // 获取最大列表数和当前编号
        int number = userService.getUserCounts_dw("%" + query + "%");
        int pageState = (pageNum - 1) * pageSize;

        List<User> users = userService.getAllUser_dw("%" + query + "%", pageState, pageSize);

        SecureDESUtil secureDESUtil = new SecureDESUtil();

        String s = "";

        // 解除加密
        for (User u : users) {
            s = secureDESUtil.decrypt(u.getUser_passwd());
            u.setUser_passwd(s);
        }

        HashMap<String, Object> res = new HashMap<>();
        res.put("number", number);
        res.put("data", users);
        String res_string = JSON.toJSONString(res);

        System.out.println("获取用户列表成功");

        return res_string;
    }

    //获取用户列表
    @RequestMapping("/allUser_gx")
    public String getUserList_st_gx(@RequestParam("query") String query, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        // 获取最大列表数和当前编号
        int number = userService.getUserCounts_gx("%" + query + "%");
        int pageState = (pageNum - 1) * pageSize;

        List<User> users = userService.getAllUser_gx("%" + query + "%", pageState, pageSize);

        SecureDESUtil secureDESUtil = new SecureDESUtil();

        String s = "";

        // 解除加密
        for (User u : users) {
            s = secureDESUtil.decrypt(u.getUser_passwd());
            u.setUser_passwd(s);
        }

        HashMap<String, Object> res = new HashMap<>();
        res.put("number", number);
        res.put("data", users);
        String res_string = JSON.toJSONString(res);

        System.out.println("获取用户列表成功");

        return res_string;
    }





    //获取用户列表
    @RequestMapping("/allUser")
    public String getUserList(@RequestParam("query") String query, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("univ_code") String univ_code) {
        // 获取最大列表数和当前编号
        int number = userService.getUserCounts("%" + query + "%");
        int pageState = (pageNum - 1) * pageSize;

        List<User> users = userService.getAllUser("%" + query + "%", pageState, pageSize, univ_code);

        SecureDESUtil secureDESUtil = new SecureDESUtil();

        String s = "";

        // 解除加密
        for (User u : users) {
            s = secureDESUtil.decrypt(u.getUser_passwd());
            u.setUser_passwd(s);
        }

        HashMap<String, Object> res = new HashMap<>();
        res.put("number", number);
        res.put("data", users);
        String res_string = JSON.toJSONString(res);

        System.out.println("获取用户列表成功");

        return res_string;
    }

    //更新用户账号启用状态
    @RequestMapping("/userState")
    public String updateUserState(@RequestParam("user_id") Integer user_id,
                                  @RequestParam("user_status") Boolean user_status) {
        if (user_status == null || user_status == null) {
            return "error";
        }
        int i = userService.updateState(user_id, user_status);
        System.out.println("用户状态修改接口调用成功");
        return i > 0 ? "success" : "error";
    }

    // 修改用户信息按钮
    @RequestMapping("/getUpdate")
    public String getUpdateUser(Integer user_id) {
        if (user_id == null) {
            return "error";
        }
        User user = userService.getUpdateUser(user_id);
        String string = JSON.toJSONString(user);//注意这里也可以直接返回对象，springboot会把对象直接转换成字符串
        System.out.println("/getUpdate接口调用成功");
        return string;
    }

    // 按下按钮后，完成对用户信息的修改
    @RequestMapping("/editUser")
    public String editUser(@RequestBody User user) {
        if (user == null) {
            return "error";
        }
        int i = userService.editUser(user);
        System.out.println("/editUser接口调用成功");
        return i > 0 ? "success" : "error";
    }

    // 按下按钮后，完成对用户密码的修改
    @RequestMapping("/updatePasswd")
    public String updatePasswd(@RequestParam("user_passwd") String user_passwd, @RequestParam("user_id") Integer user_id) {
        if (user_id == null || user_passwd == null) {
            return "error";
        }
        int i = userService.updatePasswd(user_passwd, user_id);
        System.out.println("/updatePasswd接口调用成功");
        return i > 0 ? "success" : "error";
    }


    // 避免随机生成出已经存在的账号
    @RequestMapping("/searchUserName")
    public String searchUserName(@RequestParam("user_name") String user_name) {
        if (user_name == null || user_name.equals("")) {
            return "error";
        }
        System.out.println("调用生成随机账号接口");
//        System.out.println(user_name);
        int i = userService.searchUserName(user_name);
        return i == 0 ? "success" : "error";
    }

    // 添加用户接口
    @RequestMapping("/addUser")
    public String addUser(@RequestBody User user) {
        if (user == null) {
            return "error";
        }
        int i = userService.addUser(user);
        System.out.println("添加用户接口调用成功");
        return i > 0 ? "success" : "error";
    }

}

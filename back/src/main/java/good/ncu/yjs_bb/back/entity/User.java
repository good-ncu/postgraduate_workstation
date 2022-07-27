package good.ncu.yjs_bb.back.entity;

public class User {
    //用户ID
    private int user_id;
    //用户名
    private String user_name;
    //密码
    private String user_passwd;
    //用户权限
    private String user_role;
    //用户启用状态
    private int user_status;
    //用户所在院校
    private String univ_name;
    //用户院校代码
    private String univ_code;
    //报备工作站名称
    private String user_sbdw;
    //高校联系人手机号
    private String user_gxphone;
    //报备工作站联系人手机号
    private String user_sbdwphone;

    //用于接收验证码
    private String verifyCode;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public User() {

    }

    public User(int user_id, String user_name, String user_passwd, String user_role, int user_status, String univ_name, String univ_code, String user_sbdw, String user_gxphone, String user_sbdwphone) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_passwd = user_passwd;
        this.user_role = user_role;
        this.user_status = user_status;
        this.univ_name = univ_name;
        this.univ_code = univ_code;
        this.user_sbdw = user_sbdw;
        this.user_gxphone = user_gxphone;
        this.user_sbdwphone = user_sbdwphone;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_passwd() {
        return user_passwd;
    }

    public void setUser_passwd(String user_passwd) {
        this.user_passwd = user_passwd;
    }

    public int getUser_status() {
        return user_status;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public String getUniv_name() {
        return univ_name;
    }

    public void setUniv_name(String univ_name) {
        this.univ_name = univ_name;
    }

    public String getUniv_code() {
        return univ_code;
    }

    public void setUniv_code(String univ_code) {
        this.univ_code = univ_code;
    }

    public String getUser_sbdw() {
        return user_sbdw;
    }

    public void setUser_sbdw(String user_sbdw) {
        this.user_sbdw = user_sbdw;
    }

    public String getUser_gxphone() {
        return user_gxphone;
    }

    public void setUser_gxphone(String user_gxphone) {
        this.user_gxphone = user_gxphone;
    }

    public String getUser_sbdwphone() {
        return user_sbdwphone;
    }

    public void setUser_sbdwphone(String user_sbdwphone) {
        this.user_sbdwphone = user_sbdwphone;
    }



    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_passwd='" + user_passwd + '\'' +
                ", user_role='" + user_role + '\'' +
                ", user_status=" + user_status +
                ", univ_name='" + univ_name + '\'' +
                ", univ_code='" + univ_code + '\'' +
                ", user_sbdw='" + user_sbdw + '\'' +
                ", user_gxphone='" + user_gxphone + '\'' +
                ", user_sbdwphone='" + user_sbdwphone + '\'' +
                '}';
    }
}

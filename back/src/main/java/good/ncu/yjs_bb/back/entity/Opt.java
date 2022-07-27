package good.ncu.yjs_bb.back.entity;

public class Opt {
    // 操作ID
    private int opt_id;
    // 产生该操作的用户ID
    private int user_id;
    // 操作名称：登录系统、创建用户、发起申请、通过申请、退回申请
    private String opt_name;
    //操作时间
    private String opt_time;

    public Opt(){

    }

    public Opt(int opt_id, int user_id, String opt_name, String opt_time) {
        this.opt_id = opt_id;
        this.user_id = user_id;
        this.opt_name = opt_name;
        this.opt_time = opt_time;
    }

    public int getOpt_id() {
        return opt_id;
    }

    public void setOpt_id(int opt_id) {
        this.opt_id = opt_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOpt_name() {
        return opt_name;
    }

    public void setOpt_name(String opt_name) {
        this.opt_name = opt_name;
    }

    public String getOpt_time() {
        return opt_time;
    }

    public void setOpt_time(String opt_time) {
        this.opt_time = opt_time;
    }

    @Override
    public String toString() {
        return "Opt{" +
                "opt_id=" + opt_id +
                ", user_id=" + user_id +
                ", opt_name='" + opt_name + '\'' +
                ", opt_time='" + opt_time + '\'' +
                '}';
    }

}

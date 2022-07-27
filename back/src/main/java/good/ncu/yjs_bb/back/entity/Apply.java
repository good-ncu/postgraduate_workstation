package good.ncu.yjs_bb.back.entity;

public class Apply {
    // 请求记录的ID序号
    private int apply_id;
    // 发起报备请求的申请设站单位账号
    private int user_id;
    // 发起报备请求的申请设站单位所在学校
    private String univ_name;
    // 发起报备请求的申请设站单位所在学校的学科代码
    private String univ_code;
    // 申请的工作站名称
    private String apply_dwname;
    // 工作站的单位性质
    private String apply_dwxz;
    // 技术领域
    private String apply_jsly;
    // 社会信用代码
    private String apply_scode;
    // 联合培养学科专业
    private String apply_lhpydis;
    // 学科所在院系
    private String apply_disyx;
    // 高校牵头导师
    private String apply_gxds;
    // 工作站所在城市
    private String apply_city;
    // 工作站类型
    private String apply_worklx;
    // 该请求处于的状态
    private String apply_status;
    // 请求的审核意见
    private String apply_shyj;
    // 请求的删除状态
    private String apply_del;
    // 提交请求的年份
    private String apply_year;
    // 该报备请求最后一次被操作的时间
    private String apply_lastopt;

    private String apply_zstore;

    public Apply() {

    }

    public Apply(int apply_id, int user_id, String univ_name, String univ_code, String apply_dwname, String apply_dwxz, String apply_jsly, String apply_scode, String apply_lhpydis, String apply_disyx, String apply_gxds, String apply_city, String apply_worklx, String apply_status, String apply_shyj, String apply_del, String apply_year, String apply_lastopt, String apply_zstore) {
        this.apply_id = apply_id;
        this.user_id = user_id;
        this.univ_name = univ_name;
        this.univ_code = univ_code;
        this.apply_dwname = apply_dwname;
        this.apply_dwxz = apply_dwxz;
        this.apply_jsly = apply_jsly;
        this.apply_scode = apply_scode;
        this.apply_lhpydis = apply_lhpydis;
        this.apply_disyx = apply_disyx;
        this.apply_gxds = apply_gxds;
        this.apply_city = apply_city;
        this.apply_worklx = apply_worklx;
        this.apply_status = apply_status;
        this.apply_shyj = apply_shyj;
        this.apply_del = apply_del;
        this.apply_year = apply_year;
        this.apply_lastopt = apply_lastopt;
        this.apply_zstore = apply_zstore;
    }

    public int getApply_id() {
        return apply_id;
    }

    public void setApply_id(int apply_id) {
        this.apply_id = apply_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getApply_gxds() {
        return apply_gxds;
    }

    public void setApply_gxds(String apply_gxds) {
        this.apply_gxds = apply_gxds;
    }

    public String getApply_dwname() {
        return apply_dwname;
    }

    public void setApply_dwname(String apply_dwname) {
        this.apply_dwname = apply_dwname;
    }

    public String getApply_dwxz() {
        return apply_dwxz;
    }

    public void setApply_dwxz(String apply_dwxz) {
        this.apply_dwxz = apply_dwxz;
    }

    public String getApply_jsly() {
        return apply_jsly;
    }

    public void setApply_jsly(String apply_jsly) {
        this.apply_jsly = apply_jsly;
    }

    public String getApply_scode() {
        return apply_scode;
    }

    public void setApply_scode(String apply_scode) {
        this.apply_scode = apply_scode;
    }

    public String getApply_lhpydis() {
        return apply_lhpydis;
    }

    public void setApply_lhpydis(String apply_lhpydis) {
        this.apply_lhpydis = apply_lhpydis;
    }

    public String getApply_disyx() {
        return apply_disyx;
    }

    public void setApply_disyx(String apply_disyx) {
        this.apply_disyx = apply_disyx;
    }

    public String getApply_city() {
        return apply_city;
    }

    public void setApply_city(String apply_city) {
        this.apply_city = apply_city;
    }

    public String getApply_worklx() {
        return apply_worklx;
    }

    public void setApply_worklx(String apply_worklx) {
        this.apply_worklx = apply_worklx;
    }

    public String getApply_status() {
        return apply_status;
    }

    public void setApply_status(String apply_status) {
        this.apply_status = apply_status;
    }

    public String getApply_shyj() {
        return apply_shyj;
    }

    public void setApply_shyj(String apply_shyj) {
        this.apply_shyj = apply_shyj;
    }

    public String getApply_del() {
        return apply_del;
    }

    public void setApply_del(String apply_del) {
        this.apply_del = apply_del;
    }

    public String getApply_lastopt() {
        return apply_lastopt;
    }

    public void setApply_lastopt(String apply_lastopt) {
        this.apply_lastopt = apply_lastopt;
    }


    public String getApply_year() {
        return apply_year;
    }

    public void setApply_year(String apply_year) {
        this.apply_year = apply_year;
    }

    public String getApply_zstore() {
        return apply_zstore;
    }

    public void setApply_zstore(String apply_zstore) {
        this.apply_zstore = apply_zstore;
    }

    @Override
    public String toString() {
        return "Apply{" +
                "apply_id=" + apply_id +
                ", user_id=" + user_id +
                ", univ_name='" + univ_name + '\'' +
                ", univ_code='" + univ_code + '\'' +
                ", apply_dwname='" + apply_dwname + '\'' +
                ", apply_dwxz='" + apply_dwxz + '\'' +
                ", apply_jsly='" + apply_jsly + '\'' +
                ", apply_scode='" + apply_scode + '\'' +
                ", apply_lhpydis='" + apply_lhpydis + '\'' +
                ", apply_disyx='" + apply_disyx + '\'' +
                ", apply_gxds='" + apply_gxds + '\'' +
                ", apply_city='" + apply_city + '\'' +
                ", apply_worklx='" + apply_worklx + '\'' +
                ", apply_status='" + apply_status + '\'' +
                ", apply_shyj='" + apply_shyj + '\'' +
                ", apply_del='" + apply_del + '\'' +
                ", apply_year='" + apply_year + '\'' +
                ", apply_lastopt='" + apply_lastopt + '\'' +
                ", apply_zstore='" + apply_zstore + '\'' +
                '}';
    }
}

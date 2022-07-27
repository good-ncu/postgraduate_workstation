package good.ncu.yjs_bb.back.entity;

public class File {
    // 文件ID
    private int file_id;
    // 提交文件的申请设站单位账号的ID
    private int user_id;
    // 申请设站单位所在院校
    private String univ_name;
    // 申请设站单位的院校代码
    private String univ_code;
    // 文件名称
    private String file_name;
    // 文件类型，0PDF 1其他文件
    private String file_type;
    // 文件存放路径
    private String file_path;
    // 文件URL
    private String file_url;


    public File() {

    }

    public File(int file_id, int user_id, String univ_name, String univ_code, String file_name, String file_type, String file_path, String file_url) {
        this.file_id = file_id;
        this.user_id = user_id;
        this.univ_name = univ_name;
        this.univ_code = univ_code;
        this.file_name = file_name;
        this.file_type = file_type;
        this.file_path = file_path;
        this.file_url = file_url;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
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

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    @Override
    public String toString() {
        return "File{" +
                "file_id=" + file_id +
                ", user_id=" + user_id +
                ", univ_name='" + univ_name + '\'' +
                ", univ_code='" + univ_code + '\'' +
                ", file_name='" + file_name + '\'' +
                ", file_type='" + file_type + '\'' +
                ", file_path='" + file_path + '\'' +
                ", file_url='" + file_url + '\'' +
                '}';
    }
}

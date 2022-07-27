package good.ncu.yjs_bb.back.entity;

public class Univ {
    private Integer univ_id;
    private String univ_name;
    private String univ_code;
    private String univ_index;

    public Univ() {
    }

    public Univ(Integer univ_id, String univ_name, String univ_code, String univ_index) {
        this.univ_id = univ_id;
        this.univ_name = univ_name;
        this.univ_code = univ_code;
        this.univ_index = univ_index;
    }

    public Integer getUniv_id() {
        return univ_id;
    }

    public void setUniv_id(Integer univ_id) {
        this.univ_id = univ_id;
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

    public String getUniv_index() {
        return univ_index;
    }

    public void setUniv_index(String univ_index) {
        this.univ_index = univ_index;
    }

    @Override
    public String toString() {
        return "Univ{" +
                "univ_id=" + univ_id +
                ", univ_name='" + univ_name + '\'' +
                ", univ_code='" + univ_code + '\'' +
                ", univ_index='" + univ_index + '\'' +
                '}';
    }
}

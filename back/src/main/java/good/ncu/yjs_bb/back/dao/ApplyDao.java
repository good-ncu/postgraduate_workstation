package good.ncu.yjs_bb.back.dao;


import good.ncu.yjs_bb.back.entity.Apply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ApplyDao {
    public Apply getUserWork(Integer user_id);

    public int addApply(Apply apply);

    public int rev_Apply(Apply apply);

    public List<Apply> getAllApply_gx(String query_lhpy, String query_city, String query_lx, String query_status, String univ_code, Integer pageNum, Integer pageSize);

    public List<Apply> getAllApply_gx_and_year(String query_lhpy, String query_city, String query_lx, String query_status, String univ_code, String query_year, Integer pageNum, Integer pageSize);

    public int getApply_gx_count(@Param("query_lhpy") String query_lhpy, @Param("query_city") String query_city, @Param("query_lx") String query_lx, @Param("query_status") String query_status, @Param("univ_code") String univ_code);

    public int getApply_gx_count_year(@Param("query_lhpy") String query_lhpy, @Param("query_city") String query_city, @Param("query_lx") String query_lx, @Param("query_status") String query_status, @Param("univ_code") String univ_code, @Param("query_year") String query_year);

    public Apply getApplyByID(Integer apply_id);

    public int SH_Apply(Apply apply);

    public Apply getApplyByUserID(Integer user_id);

    public int findUserID(Integer user_id);

    public List<Apply> getAllApply_st_and_year(String query_lhpy, String query_city, String query_lx, String query_status, String univ_code, String query_year,Integer pageNum, Integer pageSize);
    public int getApply_st_count_year(@Param("query_lhpy") String query_lhpy, @Param("query_city") String query_city, @Param("query_lx") String query_lx, @Param("query_status") String query_status, @Param("univ_code") String univ_code, @Param("query_year") String query_year);

}

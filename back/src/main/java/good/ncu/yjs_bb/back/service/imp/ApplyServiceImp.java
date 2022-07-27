package good.ncu.yjs_bb.back.service.imp;

import good.ncu.yjs_bb.back.dao.ApplyDao;
import good.ncu.yjs_bb.back.entity.Apply;
import good.ncu.yjs_bb.back.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyServiceImp implements ApplyService {
    @Autowired
    ApplyDao applyDao;


    @Override
    public Apply getUserWork(Integer user_id) {
        return applyDao.getUserWork(user_id);
    }

    @Override
    public int addApply(Apply apply) {
        return applyDao.addApply(apply);
    }

    @Override
    public int rev_Apply(Apply apply) {
        return applyDao.rev_Apply(apply);
    }

    @Override
    public List<Apply> getAllApply_gx(String query_lhpy, String query_city, String query_lx, String query_status, String univ_code, Integer pageNum, Integer pageSize) {
        return applyDao.getAllApply_gx(query_lhpy, query_city, query_lx, query_status, univ_code, pageNum, pageSize);
    }

    @Override
    public int getApply_gx_count(String query_lhpy, String query_city, String query_lx, String query_status, String univ_code) {
        return applyDao.getApply_gx_count(query_lhpy, query_city, query_lx, query_status, univ_code);
    }

    @Override
    public Apply getApplyByID(Integer apply_id) {
        return applyDao.getApplyByID(apply_id);
    }

    @Override
    public int SH_Apply(Apply apply) {
        return applyDao.SH_Apply(apply);
    }

    @Override
    public Apply getApplyByUserID(Integer user_id) {
        return applyDao.getApplyByUserID(user_id);
    }

    @Override
    public List<Apply> getAllApply_gx_and_year(String query_lhpy, String query_city, String query_lx, String query_status, String univ_code, String query_year, Integer pageNum, Integer pageSize) {
        return applyDao.getAllApply_gx_and_year(query_lhpy, query_city, query_lx, query_status, univ_code, query_year, pageNum, pageSize);
    }

    @Override
    public int getApply_gx_count_year(String query_lhpy, String query_city, String query_lx, String query_status, String univ_code, String query_year) {
        return applyDao.getApply_gx_count_year(query_lhpy, query_city, query_lx, query_status, univ_code, query_year);
    }

    @Override
    public int findUserID(Integer user_id) {
        return applyDao.findUserID(user_id);
    }

    @Override
    public List<Apply> getAllApply_st_and_year(String query_lhpy, String query_city, String query_lx, String query_status, String univ_code, String query_year, Integer pageNum, Integer pageSize) {
        return applyDao.getAllApply_st_and_year(query_lhpy, query_city, query_lx, query_status, univ_code, query_year, pageNum, pageSize);
    }

    @Override
    public int getApply_st_count_year(String query_lhpy, String query_city, String query_lx, String query_status, String univ_code, String query_year) {
        return applyDao.getApply_st_count_year(query_lhpy, query_city, query_lx, query_status, univ_code, query_year);
    }

}

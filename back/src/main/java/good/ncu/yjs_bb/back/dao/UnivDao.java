package good.ncu.yjs_bb.back.dao;

import good.ncu.yjs_bb.back.entity.Univ;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UnivDao {
    public List<Univ> getAllUniv();
    public Univ getUnivByCode(String univ_code);
    public int UpdateIndex(String univ_code);
}

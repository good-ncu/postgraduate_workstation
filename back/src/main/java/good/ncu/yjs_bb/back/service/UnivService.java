package good.ncu.yjs_bb.back.service;

import good.ncu.yjs_bb.back.entity.Univ;

import java.util.List;

public interface UnivService {
    public List<Univ> getAllUniv();
    public Univ getUnivByCode(String univ_code);
    public int UpdateIndex(String univ_code);
}

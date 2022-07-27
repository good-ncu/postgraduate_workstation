package good.ncu.yjs_bb.back.service.imp;

import good.ncu.yjs_bb.back.dao.UnivDao;
import good.ncu.yjs_bb.back.entity.Univ;
import good.ncu.yjs_bb.back.service.UnivService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnivServiceImp implements UnivService {
    @Autowired
    UnivDao univDao;

    @Override
    public List<Univ> getAllUniv() {
        return univDao.getAllUniv();
    }

    @Override
    public Univ getUnivByCode(String univ_code) {
        return univDao.getUnivByCode(univ_code);
    }

    @Override
    public int UpdateIndex(String univ_code) {
        return univDao.UpdateIndex(univ_code);
    }
}

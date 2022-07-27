package good.ncu.yjs_bb.back.service.imp;

import good.ncu.yjs_bb.back.dao.FileDao;
import good.ncu.yjs_bb.back.entity.File;
import good.ncu.yjs_bb.back.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImp implements FileService {
    @Autowired
    FileDao fileDao;

    @Override
    public int addFile(File file) {
        return fileDao.addFile(file);
    }

    @Override
    public int updateFile(File file) {
        return fileDao.updateFile(file);
    }

    @Override
    public File getFileByUserID(Integer user_id, String file_type) {
        return fileDao.getFileByUserID(user_id, file_type);
    }
}

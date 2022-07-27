package good.ncu.yjs_bb.back.service;

import good.ncu.yjs_bb.back.entity.File;

public interface FileService {
    public int addFile(File file);
    public int updateFile(File file);
    public File getFileByUserID(Integer user_id, String file_type);
}

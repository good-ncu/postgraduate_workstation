package good.ncu.yjs_bb.back.dao;


import good.ncu.yjs_bb.back.entity.File;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileDao {
    public int addFile(File file);
    public int updateFile(File file);
    public File getFileByUserID(Integer user_id, String file_type);
}

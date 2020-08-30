package ji.groupcloud.dao;

import ji.groupcloud.bean.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfo, Integer> {
    List<FileInfo> findAllByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    void deleteByFileHash(@Param("fileHash") String fileHash);
}

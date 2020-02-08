package ji.groupcloud.dao;

import ji.groupcloud.dto.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Integer> {
}

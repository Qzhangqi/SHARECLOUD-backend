package ji.groupcloud.bean;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "fileinfo", indexes = {@Index(columnList = "username")})
@Data
public class FileInfo {
    @Id
    private String fileHash;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String fileSize;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private Date expireData;

    public void setExpireData(long time) {
        expireData = new Date(time);
    }
}

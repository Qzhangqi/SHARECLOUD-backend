package ji.groupcloud.dto;

import lombok.Data;

@Data
public class ResUploadFileDTO {

    private String fileHash;

    private String upToken;

    public ResUploadFileDTO(String fileHash, String upToken) {
        this.fileHash = fileHash;
        this.upToken = upToken;
    }
}

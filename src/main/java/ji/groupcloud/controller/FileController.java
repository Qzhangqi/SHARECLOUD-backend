package ji.groupcloud.controller;

import com.qiniu.util.Auth;
import ji.groupcloud.constant.Role;
import ji.groupcloud.bean.FileInfo;
import ji.groupcloud.bean.Result;
import ji.groupcloud.constant.SessionAttr;
import ji.groupcloud.constant.SystemConfig;
import ji.groupcloud.dao.FileInfoRepository;
import ji.groupcloud.dto.ResUploadFileDTO;
import ji.groupcloud.util.HashFun;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class FileController {
    @Autowired
    FileInfoRepository fileInfoRepository;

    @RequiresRoles(Role.USER)
    @GetMapping("/files")
    public Result<List<FileInfo>> getFilesList(String page, HttpSession session) {
        String curruser = (String) session.getAttribute(SessionAttr.USERNAME);
        List<FileInfo> fileInfos = fileInfoRepository.findAllByUsername(curruser);
        return new Result<List<FileInfo>>("0001", "获取成功", fileInfos);
    }

    @RequiresRoles(Role.USER)
    @GetMapping("/search")
    public Result<List<FileInfo>> getSearchFile(String name, String page) {
        return null;
    }

    @RequiresRoles(Role.USER)
    @GetMapping("/getShareUrl")
    public Result<String> getShareUrl() {
        return null;
    }

    @RequiresRoles(Role.USER)
    @GetMapping("/deleteFile")
    public Result<String> deleteFile(String fileHash) {
        fileInfoRepository.deleteByFileHash(fileHash);
        return new Result<String>("0001", "删除成功", "删除成功");
    }

    @RequiresRoles(Role.USER)
    @PostMapping("/uploadFile")
    public Result<ResUploadFileDTO> uploadFile(HttpSession session, @RequestBody FileInfo fileInfo) {
        String curruser = (String) session.getAttribute(SessionAttr.USERNAME);
        String fileHash = HashFun.MD5(curruser + fileInfo.getFileName());
        fileInfo.setUsername(curruser);
        fileInfo.setFileHash(fileHash);
        fileInfo.setExpireData(Calendar.getInstance().getTimeInMillis());
        fileInfoRepository.save(fileInfo);

        Auth auth = Auth.create(SystemConfig.QN_ACCESSKEY, SystemConfig.QN_SECRETKEY);
        String upToken = auth.uploadToken(SystemConfig.QN_BUCKET, fileHash);

        return new Result<ResUploadFileDTO>("0001", "上传成功", new ResUploadFileDTO(fileHash, upToken));
    }
}

package ji.groupcloud.controller;

import ji.groupcloud.constant.Role;
import ji.groupcloud.bean.FileInfo;
import ji.groupcloud.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
public class FileController {
    @RequiresRoles(Role.USER)
    @GetMapping("/files")
    public Result<List<FileInfo>> getFilesList(String path, String page) {
        return null;
    }

    @RequiresRoles(Role.USER)
    @GetMapping("/search")
    public Result<List<FileInfo>> getSearchFile(String name, String page) {
        return null;
    }

    @RequiresRoles(Role.USER)
    @GetMapping("/getShareUrl")
    public Result<String> getShareUrl(String path) {
        return null;
    }

    @RequiresRoles(Role.USER)
    @GetMapping("/fileIsExist")
    public Result<String> getFileIsExist(String path) {
        return null;
    }

    @RequiresRoles(Role.USER)
    @PostMapping("/uploadFile")
    public Result<String> uploadFile(MultipartFile file) {

        try {
            log.info("upload succeed {}", file.getName());
        } catch (Exception e) {
            log.error("/uploadFile 上传出错", e);
            return new Result<String>("0000", "上传出错", "上传出错");
        }

        return new Result<String>("0001", "上传成功", "上传成功");
    }

    @RequiresRoles(Role.USER)
    @GetMapping("/downloadFile")
    public Result<String> downloadFile(String path) {
        return null;
    }
}

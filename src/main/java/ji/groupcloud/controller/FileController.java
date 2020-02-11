package ji.groupcloud.controller;

import ji.groupcloud.config.Role;
import ji.groupcloud.dto.FileInfo;
import ji.groupcloud.dto.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
        return null;
    }

    @RequiresRoles(Role.USER)
    @GetMapping("/downloadFile")
    public Result<String> downloadFile(String path) {
        return null;
    }
}

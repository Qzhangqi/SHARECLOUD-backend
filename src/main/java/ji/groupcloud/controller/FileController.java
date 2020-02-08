package ji.groupcloud.controller;

import ji.groupcloud.dto.FileInfo;
import ji.groupcloud.dto.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
public class FileController {
    @GetMapping("/files")
    public Result<List<FileInfo>> getFilesList(String path, String page) {
        return null;
    }

    @GetMapping("/search")
    public Result<List<FileInfo>> getSearchFile(String name, String page) {
        return null;
    }

    @GetMapping("/getShareUrl")
    public Result<String> getShareUrl(String path) {
        return null;
    }

    @GetMapping("/fileIsExist")
    public Result<String> getFileIsExist(String path) {
        return null;
    }

    @PostMapping("/uploadFile")
    public Result<String> uploadFile(MultipartFile file) {
        return null;
    }

    @GetMapping("/downloadFile")
    public Result<String> downloadFile(String path) {
        return null;
    }
}

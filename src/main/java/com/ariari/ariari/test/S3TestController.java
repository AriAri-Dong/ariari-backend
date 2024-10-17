package com.ariari.ariari.test;

import com.ariari.ariari.commons.manager.S3Manager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3TestController {
    private final S3Manager s3Manager;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = s3Manager.uploadImage(file, "도메인명");
            return "File uploaded successfully! imageUrl: " + imageUrl;
        } catch (Exception e) {
            log.info("★★★★★★★★★★★★★★★★★★★★★★★★★UPLOAD ERROR"); // 예외 처리 수정 예정
            return "File upload failed!";
        }
    }

    @PostMapping("/delete/name/{filename}")
    public String deleteFileByName(@PathVariable(name = "filename") String fileName) {
        try {
            s3Manager.deleteImageByFileName(fileName);
            return "File removed successfully!";
        } catch (Exception e) {
            log.info("★★★★★★★★★★★★★★★★★★★★★★★★★REMOVE ERROR by name"); // 예외 처리 수정 예정
            return "File remove failed!";
        }
    }

    @PostMapping("/delete/path")
    public String deleteFileByPath() {
        try {
            s3Manager.deleteImageByFilePath(""); // 인자에 원하는 경로 추가
            return "File removed successfully!";
        } catch (Exception e) {
            log.info("★★★★★★★★★★★★★★★★★★★★★★★★★REMOVE ERROR by path"); // 예외 처리 수정 예정
            return "File remove failed!";
        }
    }
}
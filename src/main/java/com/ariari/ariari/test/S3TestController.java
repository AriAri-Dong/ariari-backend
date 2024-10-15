package com.ariari.ariari.test;

import com.ariari.ariari.commons.manager.S3Manager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
            String imageUrl = s3Manager.uploadImage(file);
            return "File uploaded successfully! imageUrl: " + imageUrl;
        } catch (Exception e) {
            log.info("★★★★★★★★★★★★★★★★★★★★★★★★★ERROR");
            return "File upload failed!";
        }
    }
}

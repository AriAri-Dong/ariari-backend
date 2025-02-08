package com.ariari.ariari.test;

import com.ariari.ariari.commons.manager.S3Manager;
import com.ariari.ariari.commons.manager.file.FileManager;
import com.ariari.ariari.test.dto.TestModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3TestController {

    private final FileManager fileManager;

    private final S3Manager s3Manager;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@ModelAttribute TestModel testModel) {
        try {
            String imageUrl = fileManager.saveFile(testModel.getImage(), "도메인명");
            return ResponseEntity.ok("File uploaded successfully! imageUrl: " + imageUrl);
        } catch (Exception e) {
            log.error("★★★★★★★★★★★★★★★★★★★★★★★★★UPLOAD ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed!");
        }
    }

//    @PostMapping("/delete/name/{filename}")
//    public String deleteFileByName(@PathVariable(name = "filename") String fileName) {
//        try {
//            fileManager.deleteImageByFileName(fileName);
//            return "File removed successfully!";
//        } catch (Exception e) {
//            log.info("★★★★★★★★★★★★★★★★★★★★★★★★★REMOVE ERROR by name"); // 예외 처리 수정 예정
//            return "File remove failed!";
//        }
//    }

    @PostMapping("/delete/path")
    public String deleteFileByPath() {
        try {
            fileManager.deleteFile("https://d19qg9zwo8is96.cloudfront.net/c1036754-7a2d-4de6-aabe-1ac1c9fd4710_도메인명_화면 캡처 2025-02-08 174140.png"); // 인자에 원하는 경로 추가
            return "File removed successfully!";
        } catch (Exception e) {
            log.info("★★★★★★★★★★★★★★★★★★★★★★★★★REMOVE ERROR by path"); // 예외 처리 수정 예정
            return "File remove failed!";
        }
    }
}
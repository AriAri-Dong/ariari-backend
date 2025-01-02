package com.ariari.ariari.commons.image;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
//@Component
public class TestFileManager implements FileManager {

    @Override
    public String saveFile(MultipartFile file, String domain) {
        log.info("Saving file is complete.");
        return "test image path";
    }

    @Override
    public void deleteFile(String filePath) {
        log.info("Deleting file is complete.");
    }

}
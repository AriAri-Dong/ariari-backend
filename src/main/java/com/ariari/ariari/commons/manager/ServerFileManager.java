package com.ariari.ariari.commons.manager;

import com.ariari.ariari.commons.exception.FileControlException;
import com.ariari.ariari.commons.manager.file.FileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Component
public class ServerFileManager implements FileManager {

    @Value("${file.upload.path:uploads}")
    private String uploadPath;

    private final String baseUrl = "https://ariari-api.winterholic.net/files";

    @Override
    public String saveFile(MultipartFile file, String domain) {
        String fileName = UUID.randomUUID() + "_" + domain + "_" + file.getOriginalFilename();

        try {
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path filePath = uploadDir.resolve(fileName);

            file.transferTo(filePath.toFile());

            return getPublicUrl(fileName);
        } catch (IOException e) {
            throw new FileControlException();
        }
    }

    public void deleteFileByFileName(String fileName) {
        try {
            Path filePath = Paths.get(uploadPath).resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new FileControlException();
        }
    }

    @Override
    public void deleteFile(String filePath) {
        String urlPrefix = baseUrl + "/";
        checkValidFilePath(filePath, urlPrefix);

        String fileKey = filePath.substring(urlPrefix.length());
        deleteFileByFileName(fileKey);
    }

    private void checkValidFilePath(String filePath, String urlPrefix) {
        if (!filePath.startsWith(urlPrefix)) {
            throw new FileControlException();
        }
    }

    private String getPublicUrl(String fileName) {
        return String.format("%s/%s", baseUrl, fileName);
    }
}

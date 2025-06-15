package com.ariari.ariari.commons.manager;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ValidateMultipartFileManager {
    private static final List<String> ALLOWED_CONTENT_TYPES = List.of(
            "application/pdf",
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/webp",
            "image/bmp"
    );

    public static boolean isValidTypeFile(MultipartFile file) {
        if (file == null) return true;

        String contentType = file.getContentType();
        return contentType != null && ALLOWED_CONTENT_TYPES.contains(contentType);
    }

    public static boolean isValidTypeFileList(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) return true;

        return files.stream().allMatch(ValidateMultipartFileManager::isValidTypeFile);
    }

}

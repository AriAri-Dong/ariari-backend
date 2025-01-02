package com.ariari.ariari.commons.image;

import org.springframework.web.multipart.MultipartFile;

public interface FileManager {

    /**
     * @param file
     * @param domain
     * @return filePath
     */
    String saveFile(MultipartFile file, String domain);

    /**
     * @param filePath
     */
    void deleteFile(String filePath);


}

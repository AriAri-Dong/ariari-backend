package com.ariari.ariari.commons.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeletedImageService {

    private final DeletedImageRepository deletedImageRepository;
    private final FileManager fileManager;

    /**
     * delete image logically
     */
    public void deleteImageLogically(String imagePath) {
        DeletedImage deletedImage = new DeletedImage(imagePath);
        deletedImageRepository.save(deletedImage);
    }

    /**
     * delete image physically
     */
    public void deleteDeletedImage(DeletedImage deletedImage) {
        String imagePath = deletedImage.getImagePath();
        deletedImageRepository.delete(deletedImage);
        fileManager.deleteFile(imagePath);
    }

}

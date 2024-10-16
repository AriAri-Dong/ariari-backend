package com.ariari.ariari.commons.image;

import com.ariari.ariari.commons.manager.S3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeletedImageService {

    private final DeletedImageRepository deletedImageRepository;

    public void deleteImageLogically(String imagePath) {
        DeletedImage deletedImage = new DeletedImage(imagePath);
        deletedImageRepository.save(deletedImage);
    }

}

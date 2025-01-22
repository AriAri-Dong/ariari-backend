package com.ariari.ariari.commons.image;

import com.ariari.ariari.commons.manager.file.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 삭제 예정
 */
@Slf4j
//@Service
@Transactional
@RequiredArgsConstructor
public class DeletedImageScheduler {

    private final FileManager fileManager;

    private static final int SAVE_PERIOD = 15;

    /**
     * 매일 04시 처리
     *  -> 서버 시간 설정 필요
     *  -> 현재로부터 15일 이전 논리 삭제 처리된 이미지 물리 삭제
     */
    @Scheduled(cron = "0 0 4 * * *")
    public void deleteImagesPhysically() {
        log.info("delete image scheduler start : {}", LocalDateTime.now());
//        LocalDateTime localDateTime = LocalDateTime.now().minusDays(SAVE_PERIOD);
//
//        for (DeletedImage deletedImage : deletedImages) {
//            fileManager.deleteFile(deletedImage.getImagePath());
//        }

        log.info("delete image scheduler end : {}", LocalDateTime.now());
    }
}

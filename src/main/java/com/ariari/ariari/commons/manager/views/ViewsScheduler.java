package com.ariari.ariari.commons.manager.views;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.recruitment.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 테스트를 위한 로그가 포함
 * 테스트 이후 삭제
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ViewsScheduler {

    private final ClubRepository clubRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ViewsManager viewsManager;

    @Scheduled(cron = "0 0 4 * * *")
    public void updateViews() {
        log.info("===== Updating views start =====");
        List<Club> clubs = clubRepository.findAll();
        List<Recruitment> recruitments = recruitmentRepository.findAll();

        for (Club club : clubs) {
            log.info("clubid {} before : {}", club.getId(), club.getViews());
            viewsManager.subtractViews(club);
            log.info("clubid {} after : {}", club.getId(), club.getViews());

        }

        for (Recruitment recruitment : recruitments) {
            log.info("clubid {} before : {}", recruitment.getId(), recruitment.getViews());
            viewsManager.subtractViews(recruitment);
            log.info("clubid {} after : {}", recruitment.getId(), recruitment.getViews());
        }
        log.info("===== Updating views end =====");
    }

}

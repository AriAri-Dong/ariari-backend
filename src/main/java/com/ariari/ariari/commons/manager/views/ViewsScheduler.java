package com.ariari.ariari.commons.manager.views;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ViewsScheduler {

    private final ClubRepository clubRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ViewsManager viewsManager;

    @Scheduled(cron = "0 0 4 * * *")
    public void updateViews() {
        List<Club> clubs = clubRepository.findAll();
        List<Recruitment> recruitments = recruitmentRepository.findAll();

        for (Club club : clubs) {
            viewsManager.subtractViews(club);
        }

        for (Recruitment recruitment : recruitments) {
            viewsManager.subtractViews(recruitment);
        }
    }

}

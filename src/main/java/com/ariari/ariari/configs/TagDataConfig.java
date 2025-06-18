package com.ariari.ariari.configs;

import com.ariari.ariari.domain.club.review.enums.Icon;
import com.ariari.ariari.domain.club.review.repository.TagRepository;
import com.ariari.ariari.domain.club.review.tag.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagDataConfig {
    private final TagRepository tagRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void tagDataInit(){
        if(tagRepository.count() > 0){
            return;
        }

        tagRepository.save(new Tag("취업준비에 도움이 돼요", Icon.CAREER_PREPARATION));
        tagRepository.save(new Tag("인간관계를 넓힐 수 있어요", Icon.NETWORKING));
        tagRepository.save(new Tag("관심분야를 탐구할 수 있어요", Icon.INTEREST_EXPLORATION));
        tagRepository.save(new Tag("자기 계발에 도움이 돼요", Icon.SELF_DEVELOPMENT));
        tagRepository.save(new Tag("전공 실력이나 성적을 높일 수 있어요", Icon.ACADEMIC_IMPROVEMENT));
        tagRepository.save(new Tag("건강증진에 도움이 돼요", Icon.HEALTH_ENHANCEMENT));
        tagRepository.save(new Tag("다양한 경험을 할 수 있어요", Icon.DIVERSE_EXPERIENCE));

    }
}

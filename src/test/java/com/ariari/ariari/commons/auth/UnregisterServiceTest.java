package com.ariari.ariari.commons.auth;

import com.ariari.ariari.commons.entity.report.ReportRepository;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.activity.ClubActivityRepository;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityCommentRepository;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.domain.club.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.club.enums.ClubRegionType;
import com.ariari.ariari.domain.club.club.enums.ParticipantType;
import com.ariari.ariari.domain.club.notice.ClubNoticeRepository;
import com.ariari.ariari.domain.club.passreview.repository.PassReviewRepository;
import com.ariari.ariari.domain.club.question.ClubQuestionRepository;
import com.ariari.ariari.domain.club.review.repository.ClubReviewRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UnregisterServiceTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private ClubRepository clubRepository;
    @Autowired private PassReviewRepository passReviewRepository;
    @Autowired private ClubReviewRepository clubReviewRepository;
    @Autowired private ReportRepository reportRepository;
    @Autowired private ClubNoticeRepository clubNoticeRepository;
    @Autowired private ClubQuestionRepository clubQuestionRepository;
    @Autowired private ClubActivityRepository clubActivityRepository;
    @Autowired private ClubActivityCommentRepository clubActivityCommentRepository;
    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    void unregister() {
        Member tester = Member.createMember(12314123L, "테스터");
        memberRepository.save(tester);

        Club club = new Club("test club", "body", ClubCategoryType.AMITY, ClubRegionType.CHUNGCHEONG, ParticipantType.GRADUATE_STUDENT, null);
        clubRepository.save(club);




        // clear persistence context
        em.clear();

    }

}
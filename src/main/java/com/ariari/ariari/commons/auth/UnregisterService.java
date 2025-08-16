package com.ariari.ariari.commons.auth;

import com.ariari.ariari.commons.auth.oauth.KakaoAuthManager;
import com.ariari.ariari.commons.commonentity.report.ReportRepository;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.entity.Club;
import com.ariari.ariari.domain.club.activity.ClubActivityRepository;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityCommentRepository;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.commons.entity.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.notice.ClubNoticeRepository;
import com.ariari.ariari.domain.club.passreview.repository.PassReviewRepository;
import com.ariari.ariari.domain.club.question.ClubQuestionRepository;
import com.ariari.ariari.domain.club.review.repository.ClubReviewRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UnregisterService {

    private final MemberRepository memberRepository;
    private final PassReviewRepository passReviewRepository;
    private final ClubReviewRepository clubReviewRepository;
    private final ReportRepository reportRepository;
    private final ClubNoticeRepository clubNoticeRepository;
    private final ClubQuestionRepository clubQuestionRepository;
    private final ClubActivityRepository clubActivityRepository;
    private final ClubActivityCommentRepository clubActivityCommentRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;

    private final KakaoAuthManager kakaoAuthManager;

    @PersistenceContext
    private EntityManager em;

    public void unregister(Long reqMemberId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        // handle ADMIN club
        entrustClubAdmin(reqMember);

        // member_id -> null
        // persistence context clear after update query
        passReviewRepository.updateMemberNull(reqMember);
        clubReviewRepository.updateMemberNull(reqMember);
        reportRepository.updateMemberNull(reqMember);
        clubNoticeRepository.updateMemberNull(reqMember);
        clubQuestionRepository.updateMemberNull(reqMember);
        clubActivityRepository.updateMemberNull(reqMember);
        clubActivityCommentRepository.updateMemberNull(reqMember);
        clubMemberRepository.updateMemberNull(reqMember);

        // flush and clear for sync between memory and DB
        em.flush();
        em.clear();

        // reload reqMember to reattach it
        reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        kakaoAuthManager.unregister(reqMember);
        em.flush(); // for forcing dirty checking update

        memberRepository.delete(reqMember);
    }

    /**
     * entrust MANAGER or GENERAL member if reqMember is ADMIN (priority : MANAGER -> GENERAL)
     *  - remove club if no member excluding reqMember
     */
    void entrustClubAdmin(Member reqMember) {
        List<Club> clubs = reqMember.getClubMembers().stream().map(ClubMember::getClub).toList();

        for (Club club : clubs) {
            ClubMember clubMember = clubMemberRepository.findByClubAndMember(club, reqMember).get();

            if (clubMember.getClubMemberRoleType() == ClubMemberRoleType.ADMIN) {
                List<ClubMember> cmList = clubMemberRepository.findByClubAndClubMemberRoleTypeExceptMember(club, ClubMemberRoleType.MANAGER, reqMember);
                if (!cmList.isEmpty()) {
                    cmList.get(0).setClubMemberRoleType(ClubMemberRoleType.ADMIN);
                } else {
                    List<ClubMember> cmList2 = clubMemberRepository.findByClubAndClubMemberRoleTypeExceptMember(club, ClubMemberRoleType.GENERAL, reqMember);
                    if (!cmList2.isEmpty()) {
                        cmList2.get(0).setClubMemberRoleType(ClubMemberRoleType.ADMIN);
                    } else {
                        clubRepository.delete(club);
                    }
                }
            }
        }
    }

}

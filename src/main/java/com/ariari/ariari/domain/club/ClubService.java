package com.ariari.ariari.domain.club;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.views.ViewsManager;
import com.ariari.ariari.domain.club.deletedclub.DeletedClub;
import com.ariari.ariari.domain.club.deletedclub.DeletedClubRepository;
import com.ariari.ariari.domain.club.dto.ClubDetailRes;
import com.ariari.ariari.domain.club.dto.ClubModifyReq;
import com.ariari.ariari.domain.club.dto.ClubSaveReq;
import com.ariari.ariari.domain.club.exception.NoClubAuthException;
import com.ariari.ariari.domain.clubmember.ClubMember;
import com.ariari.ariari.domain.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.RecruitmentRepository;
import com.ariari.ariari.domain.recruitment.deletedrecruitment.DeletedRecruitment;
import com.ariari.ariari.domain.recruitment.deletedrecruitment.DeletedRecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final DeletedClubRepository deletedClubRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final DeletedRecruitmentRepository deletedRecruitmentRepository;
    private final ViewsManager viewsManager;

    public ClubDetailRes findClubDetail(Long reqMemberId, Long clubId, String clientIp) {
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        // views 처리
        if (!viewsManager.checkForDuplicateView(club, clientIp)) {
            viewsManager.addViews(club);
            viewsManager.addClientIp(club, clientIp);
        }

        return ClubDetailRes.fromEntity(club);
    }

    public void saveClub(Long reqMemberId, ClubSaveReq saveReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        Club club = saveReq.toEntity();
        club.setHasRecruitment(Boolean.FALSE);
        clubRepository.save(club);

        // 요청 Member 를 admin ClubMember 로 저장
        ClubMember clubMember = ClubMember.builder()
                .club(club)
                .member(reqMember)
                .clubMemberRoleType(ClubMemberRoleType.ADMIN)
                .build();
        clubMemberRepository.save(clubMember);
    }

    public void modifyClub(Long reqMemberId, Long clubId, ClubModifyReq modifyReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotFoundEntityException::new);

        // ClubMember 권한 검증
        if (clubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        modifyReq.modifyEntity(club);
    }

    /**
     * 논리 삭제 처리
     * 연관관계 엔티티 : Recruitment(logical), ...
     */
    public void removeClub(Long reqMemberId, Long clubId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        // ClubMember 권한 검증
        if (!clubMember.getClubMemberRoleType().equals(ClubMemberRoleType.ADMIN)) {
            throw new NoClubAuthException();
        }
        
        // 연관관계 처리
        List<Recruitment> recruitments = club.getRecruitments();
        for (Recruitment recruitment : recruitments) {
            DeletedRecruitment deletedRecruitment = DeletedRecruitment.fromRecruitment(recruitment);
            deletedRecruitmentRepository.save(deletedRecruitment);
            recruitmentRepository.delete(recruitment);
        }

        // 논리 삭제 처리
        DeletedClub deletedClub = DeletedClub.fromClub(club);
        deletedClubRepository.save(deletedClub);
        clubRepository.delete(club);

    }

}

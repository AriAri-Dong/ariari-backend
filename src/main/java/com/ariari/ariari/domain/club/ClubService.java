package com.ariari.ariari.domain.club;

import com.ariari.ariari.commons.entitydelete.EntityDeleteManager;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.views.ViewsManager;
import com.ariari.ariari.domain.club.dto.ClubDetailRes;
import com.ariari.ariari.domain.club.dto.ClubModifyReq;
import com.ariari.ariari.domain.club.dto.ClubSaveReq;
import com.ariari.ariari.domain.club.exception.NoClubAuthException;
import com.ariari.ariari.domain.clubmember.ClubMember;
import com.ariari.ariari.domain.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ViewsManager viewsManager;
    private final EntityDeleteManager entityDeleteManager;

    public ClubDetailRes findClubDetail(Long reqMemberId, Long clubId, String clientIp) {
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        // views 처리
        if (!viewsManager.checkForDuplicateView(club, clientIp)) {
            viewsManager.addViews(club);
            viewsManager.addClientIp(club, clientIp);
        }

        return ClubDetailRes.fromEntity(club);
    }

    @Transactional(readOnly = false)
    public void saveClub(Long reqMemberId, ClubSaveReq saveReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        Club club = saveReq.toEntity();
        clubRepository.save(club);

        // 요청 Member 를 admin ClubMember 로 저장
        ClubMember clubMember = ClubMember.builder()
                .club(club)
                .member(reqMember)
                .clubMemberRoleType(ClubMemberRoleType.ADMIN)
                .build();
        clubMemberRepository.save(clubMember);
    }

    @Transactional(readOnly = false)
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

    @Transactional(readOnly = false)
    public void removeClub(Long reqMemberId, Long clubId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        // ClubMember 권한 검증
        if (!clubMember.getClubMemberRoleType().equals(ClubMemberRoleType.ADMIN)) {
            throw new NoClubAuthException();
        }

        entityDeleteManager.deleteEntity(club);
    }

}

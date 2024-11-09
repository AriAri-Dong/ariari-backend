package com.ariari.ariari.domain.club;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.views.ViewsManager;
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
    private final DeletedClubRepository deletedClubRepository;
    private final ViewsManager viewsManager;

    public ClubDetailRes findClubDetail(Long memberId, Long clubId, String clientIp) {
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        // views 처리
        if (!viewsManager.checkForDuplicateView(club, clientIp)) {
            viewsManager.addViews(club);
            viewsManager.addClientIp(club, clientIp);
        }

        return ClubDetailRes.fromEntity(club);
    }

    public void saveClub(Long memberId, ClubSaveReq saveReq) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundEntityException::new);

        Club club = saveReq.toEntity();
        clubRepository.save(club);

        // 요청 Member 를 admin ClubMember 로 저장
        ClubMember clubMember = ClubMember.builder()
                .club(club)
                .member(member)
                .clubMemberRoleType(ClubMemberRoleType.ADMIN)
                .build();
        clubMemberRepository.save(clubMember);
    }

    public void modifyClub(Long memberId, Long clubId, ClubModifyReq modifyReq) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findByClubAndMember(club, member).orElseThrow(NotFoundEntityException::new);

        // ClubMember 권한 검증
        if (clubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        modifyReq.modifyEntity(club);
    }

    public void removeClub(Long memberId, Long clubId) {

    }

}

package com.ariari.ariari.domain.club.club.invite;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.domain.club.club.invite.dto.req.InviteRequest;
import com.ariari.ariari.domain.club.club.invite.exception.ExistsClubMemberException;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InviteService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final InviteManager inviteManager;

    @Transactional
    public String createInvite(Long reqMemberId, Long clubId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isSameClubMemberAsRequester(reqClubMember.getMember(), reqMember);
        return inviteManager.createKey(clubId);
    }

    @Transactional
    public String verifyInviteKey(Long reqMemberId, InviteRequest inviteRequest) {
        Member reqMember = memberRepository.findByIdWithSchool(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Long clubId = inviteManager.getInviteKey(inviteRequest.getInviteKey());
        Club club = clubRepository.findByIdWithSchool(clubId).orElseThrow(NotFoundEntityException::new);

        // 회원이 이미 해당 동아리에 가입되어 있는지 확인
        if(clubMemberRepository.existsByMemberIdAndClubId(reqMemberId, clubId)){
            throw new ExistsClubMemberException();
        }

        // 해당 교내 동아리와 회원의 학교가 같은지 확인
        if(club.getSchool()!=null){
            GlobalValidator.eqClubSchoolAsreqMember(reqMember.getSchool().getId(), club.getSchool().getId());
        }

        ClubMember clubMember= ClubMember.createInvited(inviteRequest.getName(), reqMember, club);
        clubMemberRepository.save(clubMember);

        return club.getName();
    }
}

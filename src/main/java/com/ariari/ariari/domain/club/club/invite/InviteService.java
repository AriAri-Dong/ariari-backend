package com.ariari.ariari.domain.club.club.invite;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.domain.club.club.invite.dto.res.InviteDetailRes;
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
    public InviteDetailRes createInvite(Long reqMemberId, Long clubId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isSameClubMemberAsRequester(reqClubMember.getMember(), reqMember);
        return InviteDetailRes.of(inviteManager.createKey(clubId), club.getName());
    }

    @Transactional
    public void verifyInviteKey(Long reqMemberId, String inviteKey) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Long clubId = inviteManager.getInviteKey(inviteKey);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        if(club.getSchool()!=null){
            GlobalValidator.eqClubSchoolAsreqMember(reqMember.getSchool(), club.getSchool());
        }
    }
}

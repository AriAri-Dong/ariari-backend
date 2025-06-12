package com.ariari.ariari.domain.club.club.invite;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.MemberAlarmManger;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.domain.club.club.invite.dto.req.InviteAcceptRequest;
import com.ariari.ariari.domain.club.club.invite.dto.req.InviteAlarmRequest;
import com.ariari.ariari.domain.club.club.invite.dto.req.InviteRequest;
import com.ariari.ariari.domain.club.club.invite.exception.ExistsClubMemberException;
import com.ariari.ariari.domain.club.club.invite.exception.InvalidClubInviteException;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.alarm.MemberAlarmRepository;
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
    private final MemberAlarmManger memberAlarmManger;
    private final MemberAlarmRepository memberAlarmRepository;

    @Transactional(readOnly = true)
    public String createInviteKey(Long reqMemberId, Long clubId) {
        Member reqMember = memberRepository.findByIdWithSchool(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findByIdWithSchool(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        // 해당 교내 동아리와 회원의 학교가 같은지 확인
        validateSchoolMatchIfNeeded(club, reqMember);
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
        validateSchoolMatchIfNeeded(club, reqMember);

        ClubMember clubMember= ClubMember.createInvited(inviteRequest.getName(), reqMember, club);
        clubMemberRepository.save(clubMember);

        return club.getName();
    }

    @Transactional(readOnly = true)
    public void createInviteAlarm(Long reqMemberId, InviteAlarmRequest inviteAlarmRequest) {
        Member reqMember = memberRepository.findByIdWithSchool(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findByIdWithSchool(inviteAlarmRequest.getClubId()).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);
        Member inviteMember = memberRepository.findByIdWithSchool(inviteAlarmRequest.getMemberId()).orElseThrow(NotFoundEntityException::new);

        GlobalValidator.isHigherRoleTypeThan(clubMember, clubMember);

        // 만약 교내 동아리라면 초대 대상이 같은 학교여야 함
        validateSchoolMatchIfNeeded(club, reqMember);
        validateSchoolMatchIfNeeded(club, inviteMember);

        String inviteAlarmCode = inviteManager.createKey(club.getId());
        memberAlarmManger.sendInviteAlarm(club, inviteMember, inviteAlarmCode);
    }

    @Transactional
    public void acceptInviteAlarm(Long reqMemberId, InviteAcceptRequest inviteAcceptRequest) {
        Member reqMember = memberRepository.findByIdWithSchool(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findByIdWithSchool(inviteAcceptRequest.getClubId()).orElseThrow(NotFoundEntityException::new);
        String clubMemberName = inviteAcceptRequest.getName();
        Long reqClubId = inviteManager.getInviteKey(inviteAcceptRequest.getInviteAlarmCode());

        // 초대한 동아리와 키 값의 동아리 일치 여부 확인
        if (!reqClubId.equals(club.getId())) {
            throw new InvalidClubInviteException();
        }

        // 회원이 이미 해당 동아리에 가입되어 있는지 확인
        if(clubMemberRepository.existsByMemberIdAndClubId(reqMemberId, club.getId())){
            throw new ExistsClubMemberException();
        }

        // 교내 동아리일 때만 학교 일치 검사
        validateSchoolMatchIfNeeded(club, reqMember);

        ClubMember clubMember = ClubMember.createInvited(clubMemberName, reqMember, club);
        clubMemberRepository.saveAndFlush(clubMember);

        memberAlarmRepository.deleteAlarmsByClubId(club, "초대장");
    }

    // 교내 동아리일 경우에만 inviteMember(또는 수락하는 회원)의 학교가 동아리 학교와 같은지 검증
    // 연합 동아리는 무조건 통과
    private void validateSchoolMatchIfNeeded(Club club, Member reqMember) {
        if (club.getSchool() != null) {
            GlobalValidator.eqClubSchoolAsreqMember(reqMember.getSchool().getId(), club.getSchool().getId());
        }
    }
}

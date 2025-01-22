package com.ariari.ariari.domain.club.clubmember;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.dto.res.ClubMemberListRes;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import com.ariari.ariari.domain.club.clubmember.exception.NoAdminException;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.club.exception.NoClubAuthException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubMemberService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;

    public ClubMemberListRes findClubMemberList(Long reqMemberId, Long clubId, ClubMemberStatusType statusType, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        Page<ClubMember> page = clubMemberRepository.findByClub(club, statusType, pageable);
        return ClubMemberListRes.createRes(page);
    }

    @Transactional
    public void modifyRoleType(Long reqMemberId, Long clubMemberId, ClubMemberRoleType roleType) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubMember.getClub(), reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        clubMember.setClubMemberRoleType(roleType);
    }

    @Transactional
    public void entrustAdmin(Long reqMemberId, Long clubMemberId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubMember.getClub(), reqMember).orElseThrow(NoClubAuthException::new);

        if (!reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.ADMIN)) {
            throw new NoAdminException();
        }

        clubMember.setClubMemberRoleType(ClubMemberRoleType.ADMIN);
        reqClubMember.setClubMemberRoleType(ClubMemberRoleType.GENERAL);
    }

    @Transactional
    public void modifyStatusType(Long reqMemberId, Long clubMemberId, ClubMemberStatusType statusType) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubMember.getClub(), reqMember).orElseThrow(NoClubAuthException::new);

        if (!reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.ADMIN)) {
            throw new NoAdminException();
        }

        clubMember.setClubMemberStatusType(statusType);
    }

    @Transactional
    public void modifyStatusTypes(Long reqMemberId, Long clubId, List<Long> clubMemberIds, ClubMemberStatusType statusType) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        if (!reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.ADMIN)) {
            throw new NoClubAuthException();
        }

        List<ClubMember> clubMembers = clubMemberRepository.findAllById(clubMemberIds);
        for (ClubMember clubMember : clubMembers) {
            if (!clubMember.getClub().equals(club)) {
                throw new NotBelongInClubException();
            }

            clubMember.setClubMemberStatusType(statusType);
        }
    }

    @Transactional
    public void removeClubMember(Long reqMemberId, Long clubMemberId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubMember.getClub(), reqMember).orElseThrow(NoClubAuthException::new);

        if (!reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.ADMIN)) {
            throw new NoClubAuthException();
        }

        clubMemberRepository.delete(clubMember);
    }

    public ClubMemberListRes searchClubMembers(Long reqMemberId, Long clubId, String query, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        if (!reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.ADMIN)) {
            throw new NoClubAuthException();
        }

        Page<ClubMember> page = clubMemberRepository.findByClubAndNameContains(club, query, pageable);
        return ClubMemberListRes.createRes(page);
    }

}

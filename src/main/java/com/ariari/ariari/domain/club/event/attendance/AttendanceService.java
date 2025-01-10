package com.ariari.ariari.domain.club.event.attendance;

import com.ariari.ariari.commons.entitydelete.EntityDeleteManager;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.event.ClubEvent;
import com.ariari.ariari.domain.club.event.ClubEventRepository;
import com.ariari.ariari.domain.club.event.attendance.exception.ExistingAttendanceException;
import com.ariari.ariari.domain.club.exception.NoClubAuthException;
import com.ariari.ariari.domain.club.exception.NotMatchedClubException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubEventRepository clubEventRepository;
    private final AttendanceRepository attendanceRepository;
    private final EntityDeleteManager entityDeleteManager;

    @Transactional
    public void saveAttendancesByManager(Long reqMemberId, Long clubEventId, List<Long> clubMemberIds) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubEvent clubEvent = clubEventRepository.findById(clubEventId).orElseThrow(NotFoundEntityException::new);
        Club club = clubEvent.getClub();
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        List<Attendance> attendances = new ArrayList<>();

        List<ClubMember> clubMembers = clubMemberRepository.findAllById(clubMemberIds);
        for (ClubMember clubMember : clubMembers) {
            if (!clubMember.getClub().equals(club)) {
                throw new NotMatchedClubException();
            }

            if (attendanceRepository.findByClubEventAndClubMember(clubEvent, clubMember).isPresent()) {
                throw new ExistingAttendanceException();
            }

            attendances.add(new Attendance(clubMember, clubEvent));
        }

        attendanceRepository.saveAll(attendances);
    }

    @Transactional
    public void removeAttendances(Long reqMemberId, Long clubEventId, List<Long> clubMemberIds) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubEvent clubEvent = clubEventRepository.findById(clubEventId).orElseThrow(NotFoundEntityException::new);
        Club club = clubEvent.getClub();
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        List<ClubMember> clubMembers = clubMemberRepository.findAllById(clubMemberIds);
        for (ClubMember clubMember : clubMembers) {
            if (!clubMember.getClub().equals(club)) {
                throw new NotMatchedClubException();
            }

            Attendance attendance = attendanceRepository.findByClubEventAndClubMember(clubEvent, clubMember).orElseThrow(NotFoundEntityException::new);
            entityDeleteManager.deleteEntity(attendance);
        }
    }

}

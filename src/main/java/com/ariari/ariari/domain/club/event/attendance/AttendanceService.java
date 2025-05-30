package com.ariari.ariari.domain.club.event.attendance;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.dto.res.ClubMemberListRes;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.club.event.ClubEvent;
import com.ariari.ariari.domain.club.event.ClubEventRepository;
import com.ariari.ariari.domain.club.event.attendance.exception.ExistingAttendanceException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubEventRepository clubEventRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceTokenManager attendanceTokenManager;

    @Transactional
    public void saveAttendancesByManager(Long reqMemberId, Long clubEventId, List<Long> clubMemberIds) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubEvent clubEvent = clubEventRepository.findById(clubEventId).orElseThrow(NotFoundEntityException::new);
        Club club = clubEvent.getClub();
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        List<Attendance> attendances = new ArrayList<>();

        List<ClubMember> clubMembers = clubMemberRepository.findAllById(clubMemberIds);
        for (ClubMember clubMember : clubMembers) {
            GlobalValidator.belongsToClub(clubMember, club);

            Member member = clubMember.getMember();
            if (attendanceRepository.findByClubEventAndMember(clubEvent, member).isPresent()) {
                throw new ExistingAttendanceException();
            }

            attendances.add(new Attendance(clubMember.getMember(), clubEvent));
        }

        attendanceRepository.saveAll(attendances);
    }

    @Transactional
    public void removeAttendances(Long reqMemberId, Long clubEventId, List<Long> clubMemberIds) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubEvent clubEvent = clubEventRepository.findById(clubEventId).orElseThrow(NotFoundEntityException::new);
        Club club = clubEvent.getClub();
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        List<ClubMember> clubMembers = clubMemberRepository.findAllById(clubMemberIds);
        for (ClubMember clubMember : clubMembers) {
            GlobalValidator.belongsToClub(clubMember, club);

            Member member = clubMember.getMember();
            Attendance attendance = attendanceRepository.findByClubEventAndMember(clubEvent, member).orElseThrow(NotFoundEntityException::new);
            attendanceRepository.delete(attendance);
        }
    }

    @Transactional
    public String issueAttendanceToken(Long reqMemberId, Long clubEventId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubEvent clubEvent = clubEventRepository.findById(clubEventId).orElseThrow(NotFoundEntityException::new);
        Club club = clubEvent.getClub();
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        return attendanceTokenManager.createAttendanceKey(clubEvent);
    }

    @Transactional
    public void attendClubEvent(Long reqMemberId, String attendanceKey) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Long clubEventId = attendanceTokenManager.getClubEventId(attendanceKey);

        ClubEvent clubEvent = clubEventRepository.findById(clubEventId).orElseThrow(NotFoundEntityException::new);
        Club club = clubEvent.getClub();
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        if (attendanceRepository.findByClubEventAndMember(clubEvent, reqMember).isPresent()) {
            throw new ExistingAttendanceException();
        }

        Attendance attendance = new Attendance(reqMember, clubEvent);
        attendanceRepository.save(attendance);
    }

    @Transactional
    public ClubMemberListRes findAttendees(Long reqMemberId, Long clubEventId, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubEvent clubEvent = clubEventRepository.findById(clubEventId).orElseThrow(NotFoundEntityException::new);
        Club club = clubEvent.getClub();
        List<ClubMember> clubMemberList = clubMemberRepository.findAllByClub(club);

        if (!reqMember.isSuperAdmin() && clubMemberRepository.findByClubAndMember(club, reqMember).isEmpty()) {
            throw new NotBelongInClubException();
        }

        Page<Attendance> page = attendanceRepository.findByClubEvent(clubEvent, pageable);
        return ClubMemberListRes.createResByAttendances(page, clubMemberList);
    }

}

package com.ariari.ariari.domain.club.event;

import com.ariari.ariari.commons.entitydelete.EntityDeleteManager;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.event.attendance.Attendance;
import com.ariari.ariari.domain.club.event.attendance.AttendanceRepository;
import com.ariari.ariari.domain.club.event.dto.ClubEventListRes;
import com.ariari.ariari.domain.club.event.dto.ClubEventSaveReq;
import com.ariari.ariari.domain.club.exception.NoClubAuthException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubEventService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubEventRepository clubEventRepository;
    private final AttendanceRepository attendanceRepository;
    private final EntityDeleteManager entityDeleteManager;

    @Transactional
    public void saveClubEvent(Long reqMemberId, Long clubId, ClubEventSaveReq saveReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        ClubEvent clubEvent = saveReq.toEntity(club);

        clubEventRepository.save(clubEvent);
    }

    @Transactional
    public void modifyClubEvent(Long reqMemberId, Long clubEventId, ClubEventSaveReq modifyReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubEvent clubEvent = clubEventRepository.findById(clubEventId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubEvent.getClub(), reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        modifyReq.modifyEntity(clubEvent);
    }

    @Transactional
    public void removeClubEvent(Long reqMemberId, Long clubEventId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubEvent clubEvent = clubEventRepository.findById(clubEventId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubEvent.getClub(), reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        entityDeleteManager.deleteEntity(clubEvent);
    }

    public ClubEventListRes findClubEvents(Long reqMemberId, Long clubId, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        Page<ClubEvent> page = clubEventRepository.findByClubOrderByEventDateTimeDesc(club, pageable);

        Map<ClubEvent, List<ClubMember>> clubMemberListMap = new HashMap<>();
        Map<ClubEvent, Long> attendeeCountMap = new HashMap<>();
        for (ClubEvent clubEvent : page.getContent()) {
            List<Attendance> attendances = attendanceRepository.findTop3ByClubEvent(clubEvent);
            List<ClubMember> clubMembers = attendances.stream().map(Attendance::getClubMember).toList();
            clubMemberListMap.put(clubEvent, clubMembers);

            Long attendeeCount = attendanceRepository.countByClubEvent(clubEvent);
            attendeeCountMap.put(clubEvent, attendeeCount);
        }

        return ClubEventListRes.createRes(page, clubMemberListMap, attendeeCountMap);
    }

}

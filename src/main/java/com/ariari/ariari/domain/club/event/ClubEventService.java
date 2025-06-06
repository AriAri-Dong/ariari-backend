package com.ariari.ariari.domain.club.event;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.MemberAlarmManger;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.bookmark.ClubBookmark;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.club.event.attendance.Attendance;
import com.ariari.ariari.domain.club.event.attendance.AttendanceRepository;
import com.ariari.ariari.domain.club.event.dto.ClubEventListRes;
import com.ariari.ariari.domain.club.event.dto.ClubEventSaveReq;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.alarm.event.MemberAlarmEvent;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubEventService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubEventRepository clubEventRepository;
    private final AttendanceRepository attendanceRepository;
    private final MemberAlarmManger memberAlarmManger;

    @Transactional
    public void saveClubEvent(Long reqMemberId, Long clubId, ClubEventSaveReq saveReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);


        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        ClubEvent clubEvent = saveReq.toEntity(club);

        clubEventRepository.save(clubEvent);
        List<Member> memberList = clubMemberRepository.findAllByClub(club).stream()
                .map(ClubMember::getMember)
                .toList();
        memberAlarmManger.sendClubEventAlarm(memberList, clubId);
    }

    @Transactional
    public void modifyClubEvent(Long reqMemberId, Long clubEventId, ClubEventSaveReq modifyReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubEvent clubEvent = clubEventRepository.findById(clubEventId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubEvent.getClub(), reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        modifyReq.modifyEntity(clubEvent);
    }

    @Transactional
    public void removeClubEvent(Long reqMemberId, Long clubEventId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubEvent clubEvent = clubEventRepository.findById(clubEventId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubEvent.getClub(), reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        clubEventRepository.delete(clubEvent);
    }

    public ClubEventListRes findClubEvents(Long reqMemberId, Long clubId, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        if (!reqMember.isSuperAdmin() && clubMemberRepository.findByClubAndMember(club, reqMember).isEmpty()) {
            throw new NotBelongInClubException();
        }

        Page<ClubEvent> page = clubEventRepository.findByClubOrderByEventDateTimeDesc(club, pageable);

        Map<ClubEvent, List<ClubMember>> clubMemberListMap = new HashMap<>();
        Map<ClubEvent, Long> attendeeCountMap = new HashMap<>();
        List<ClubMember> clubMembers = clubMemberRepository.findAllByClub(club);
        for (ClubEvent clubEvent : page.getContent()) {
            List<Attendance> attendances = attendanceRepository.findTop3ByClubEvent(clubEvent);
            List<Member> members = attendances.stream().map(Attendance::getMember).toList();

            Set<Long> memberIds = members.stream().map(Member::getId).collect(Collectors.toSet());
            List<ClubMember> attendedClubMembers = clubMembers.stream()
                            .filter(clubMember -> memberIds.contains(clubMember.getMember().getId()))
                                    .toList();
            clubMemberListMap.put(clubEvent, attendedClubMembers);

            Long attendeeCount = attendanceRepository.countByClubEvent(clubEvent);
            attendeeCountMap.put(clubEvent, attendeeCount);
        }

        return ClubEventListRes.createRes(page, clubMemberListMap, attendeeCountMap);
    }

}

package com.ariari.ariari.domain.club.alarm;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.alarm.dto.res.ClubAlarmListRes;
import com.ariari.ariari.domain.club.alarm.event.ClubAlarmEvent;
import com.ariari.ariari.domain.club.alarm.event.ClubAlarmEventList;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubAlarmService {

    private final MemberRepository memberRepository;
    private final ClubAlarmRepository clubAlarmRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;


    @Transactional(readOnly = true)
    public ClubAlarmListRes getAlarms(Long reqMemberId, Long clubId, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        Page<ClubAlarm> clubAlarmPage = clubAlarmRepository.findAllByClub(club, pageable);
        int unreadCount = 0;
        for(ClubAlarm clubAlarm : clubAlarmPage){
            if(!clubAlarm.getIsChecked()){
                unreadCount++;
            }
        }

        return ClubAlarmListRes.fromPage(clubAlarmPage, unreadCount);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void saveAlarms(ClubAlarmEvent clubAlarmEvent){

        // 단일 이벤트 처리
        ClubAlarm clubAlarm = ClubAlarm.builder()
                .title(clubAlarmEvent.getTitle())
                .clubAlarmType(clubAlarmEvent.getClubAlarmType())
                .club(clubAlarmEvent.getClub())
                .uri(clubAlarmEvent.getUri())
                .isChecked(false)
                .build();
        // 알람 저장
        clubAlarmRepository.save(clubAlarm);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void saveAlarms(ClubAlarmEventList clubAlarmEventList){
        // 다중 이벤트 처리
        List<ClubAlarm> clubAlarms = clubAlarmEventList.getClubAlarmEventList().stream()
                .map( clubAlarmEvent -> ClubAlarm.builder()
                        .title(clubAlarmEvent.getTitle())
                        .uri(clubAlarmEvent.getUri())
                        .clubAlarmType(clubAlarmEvent.getClubAlarmType())
                        .club(clubAlarmEvent.getClub())
                        .build())
                .toList();
        // 알림 저장
        // 배치 처리 해야할까?
        clubAlarmRepository.saveAll(clubAlarms);
    }

    @Transactional
    public void readAlarm(Long reqMemberId, Long clubId, Long alarmId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        ClubAlarm clubAlarm = clubAlarmRepository.findByIdAndClub(alarmId, club)
                .orElseThrow(NotFoundEntityException::new);
        // 읽음 처리
        if(!clubAlarm.getIsChecked()) {
            clubAlarm.MarkRead();
        }
    }

    @Transactional
    public void removeAlarm(Long reqMemberId, Long clubId, Long alarmId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        // 알림을 조회하여 없으면 예외 처리
        ClubAlarm clubAlarm = clubAlarmRepository.findByIdAndClub(alarmId, club)
                .orElseThrow(NotFoundEntityException::new);
        // 알림을 논리 삭제 처리
        clubAlarmRepository.delete(clubAlarm);
    }
}

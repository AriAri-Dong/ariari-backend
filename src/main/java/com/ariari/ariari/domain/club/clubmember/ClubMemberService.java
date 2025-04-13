package com.ariari.ariari.domain.club.clubmember;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.ClubAlarmManger;
import com.ariari.ariari.commons.manager.MemberAlarmManger;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.activity.ClubActivityRepository;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityComment;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityCommentRepository;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.dto.res.ClubMemberListRes;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.club.event.attendance.Attendance;
import com.ariari.ariari.domain.club.event.attendance.AttendanceRepository;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import com.ariari.ariari.domain.club.notice.ClubNoticeRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import jakarta.persistence.OneToMany;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubMemberService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubActivityCommentRepository clubActivityCommentRepository;
    private final ClubNoticeRepository clubNoticeRepository;
    private final ClubActivityRepository clubActivityRepository;
    private final MemberAlarmManger memberAlarmManger;
    private final ClubAlarmManger clubAlarmManger;
    private final AttendanceRepository attendanceRepository;

    public ClubMemberListRes findClubMemberList(Long reqMemberId, Long clubId, ClubMemberStatusType statusType, String query, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        Page<ClubMember> page = clubMemberRepository.searchClubMember(club, statusType, query, pageable);
        return ClubMemberListRes.createRes(page);
    }

    @Transactional
    public void modifyRoleType(Long reqMemberId, Long clubMemberId, ClubMemberRoleType roleType) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubMember.getClub(), reqMember).orElseThrow(NotBelongInClubException::new);
        Club club = reqClubMember.getClub();

        GlobalValidator.isClubAdmin(reqClubMember);
        GlobalValidator.belongsToClub(clubMember, club);

        clubMember.setClubMemberRoleType(roleType);
        memberAlarmManger.sendClubRoleStateAlarm(clubMember.getMember(), roleType, club.getId());
    }

    @Transactional
    public void entrustAdmin(Long reqMemberId, Long clubMemberId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubMember.getClub(), reqMember).orElseThrow(NotBelongInClubException::new);
        Club club = reqClubMember.getClub();

        GlobalValidator.isClubAdmin(reqClubMember);
        GlobalValidator.belongsToClub(clubMember, club);

        clubMember.setClubMemberRoleType(ClubMemberRoleType.ADMIN);
        reqClubMember.setClubMemberRoleType(ClubMemberRoleType.GENERAL);
    }

    /**
     * 비활성화
     */
    @Transactional
    public void modifyStatusType(Long reqMemberId, Long clubMemberId, ClubMemberStatusType statusType) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubMember.getClub(), reqMember).orElseThrow(NotBelongInClubException::new);
        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        clubMember.setClubMemberStatusType(statusType);
        memberAlarmManger.sendClubMemberStatusType(statusType, clubMember.getMember(), reqClubMember.getClub().getId());
    }

    @Transactional
    public void modifyStatusTypes(Long reqMemberId, Long clubId, List<Long> clubMemberIds, ClubMemberStatusType statusType) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        List<ClubMember> clubMembers = clubMemberRepository.findAllById(clubMemberIds);
        for (ClubMember clubMember : clubMembers) {
            GlobalValidator.belongsToClub(clubMember, club);
            GlobalValidator.isHigherRoleTypeThan(reqClubMember, clubMember);
            clubMember.setClubMemberStatusType(statusType);
        }
        List<Member> memberList = clubMembers.stream()
                .map(ClubMember::getMember)
                .toList();
        memberAlarmManger.sendClubMembersStatusType(statusType, memberList, clubId);
    }

    @Transactional
    public void removeClubMember(Long reqMemberId, Long clubMemberId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubMember.getClub(), reqMember).orElseThrow(NotBelongInClubException::new);
        Club club = reqClubMember.getClub();

        GlobalValidator.isClubManagerOrHigher(reqClubMember);
        GlobalValidator.belongsToClub(clubMember, club);
        GlobalValidator.isHigherRoleTypeThan(reqClubMember, clubMember);

        clubMemberRepository.delete(clubMember);
        memberAlarmManger.sendClubMemberRemove(clubMember.getMember(), club.getName(), club.getId());
    }

    /**
     * 비활성화
     */
    public ClubMemberListRes searchClubMembers(Long reqMemberId, Long clubId, String query, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        Page<ClubMember> page = clubMemberRepository.findByClubAndNameContains(club, query, pageable);
        return ClubMemberListRes.createRes(page);
    }

    @Transactional
    public void quitClubMember(Long reqMemberId, Long clubMemberId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(reqClubMember.getClub().getId()).orElseThrow(NotFoundEntityException::new);
        String clubMemberName = reqClubMember.getName();

        GlobalValidator.isClubMemberAdmin(reqClubMember);
        GlobalValidator.isSameClubMemberAsRequester(reqClubMember.getMember(), reqMember);
        // 해당 req클럽 멤버만 진행할수있ㄷㅎㅀㄱ햐여헌더


        // DB에서는 ON DELETE SET NULL 가능하지만 JPA는 X 그래서 전부 업데이트 처리 아니면 배치 update JPQL 해야함
        deleteClubMember(reqClubMember);
        clubMemberRepository.delete(reqClubMember);
        clubAlarmManger.quitClubMember(clubMemberName, club, LocalDateTime.now());


    }



    public void deleteClubMember(ClubMember reqClubMember) {
        List<ClubActivityComment> clubActivityCommentList = clubActivityCommentRepository.findAllByClubMember(reqClubMember);
        List<ClubNotice> clubNoticeList = clubNoticeRepository.findAllByClubMember(reqClubMember);
        List<ClubActivity> clubActivityList = clubActivityRepository.findAllByClubMember(reqClubMember);
        List<Attendance> attendanceList = attendanceRepository.findAllByClubAndMember(reqClubMember.getClub(), reqClubMember.getMember());
        attendanceRepository.deleteAll(attendanceList);

        for (ClubActivityComment clubActivityComment : clubActivityCommentList) {
            clubActivityComment.modifyClubMember();
        }
        for (ClubActivity clubActivity : clubActivityList) {
            clubActivity.modifyClubMember();
        }
        for (ClubNotice clubNotice : clubNoticeList) {
            clubNotice.modifyClubMember();
        }

        clubActivityCommentRepository.saveAll(clubActivityCommentList);
        clubNoticeRepository.saveAll(clubNoticeList);
        clubActivityRepository.saveAll(clubActivityList);

    }
}

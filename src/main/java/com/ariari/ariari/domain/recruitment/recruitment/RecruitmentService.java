package com.ariari.ariari.domain.recruitment.recruitment;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.ClubAlarmManger;
import com.ariari.ariari.commons.manager.MemberAlarmManger;
import com.ariari.ariari.commons.manager.file.FileManager;
import com.ariari.ariari.commons.manager.views.ViewsManager;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.bookmark.ClubBookmark;
import com.ariari.ariari.domain.club.bookmark.ClubBookmarkRepository;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.apply.ApplyRepository;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTempRepository;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.applyform.ApplyFormRepository;
import com.ariari.ariari.domain.recruitment.applyform.exception.NoApplyFormException;
import com.ariari.ariari.domain.recruitment.bookmark.RecruitmentBookmark;
import com.ariari.ariari.domain.recruitment.bookmark.RecruitmentBookmarkRepository;
import com.ariari.ariari.domain.recruitment.recruitment.dto.req.RecruitmentSaveReq;
import com.ariari.ariari.domain.recruitment.recruitment.dto.res.RecruitmentDetailRes;
import com.ariari.ariari.domain.recruitment.exception.ExistsDuplicatePeriodRecruitment;
import com.ariari.ariari.domain.recruitment.exception.StartAfterEndException;
import com.ariari.ariari.domain.recruitment.note.RecruitmentNote;
import com.ariari.ariari.domain.school.School;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitmentService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final RecruitmentBookmarkRepository recruitmentBookmarkRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ApplyRepository applyRepository;
    private final ApplyFormRepository applyFormRepository;
    private final ViewsManager viewsManager;
    private final FileManager fileManager;
    private final MemberAlarmManger memberAlarmManger;
    private final ClubAlarmManger clubAlarmManger;
    private final ClubBookmarkRepository clubBookmarkRepository;
    private final ApplyTempRepository applyTempRepository;

    @Transactional
    public RecruitmentDetailRes findRecruitmentDetail(Long reqMemberId, Long recruitmentId, String clientIp) {
        Member reqMember = null;
        if (reqMemberId != null) {
            reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        }
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(NotFoundEntityException::new);

        School school = recruitment.getClub().getSchool();
        GlobalValidator.eqSchoolAuth(reqMember, school);

        if (!viewsManager.checkForDuplicateView(recruitment, clientIp)) {
            viewsManager.addViews(recruitment);
            viewsManager.addClientIp(recruitment, clientIp);
        }

        Integer bookmarks = recruitmentBookmarkRepository.countByRecruitment(recruitment).intValue();

        Boolean isMyCLub = Boolean.FALSE;
        Boolean isMyApply = Boolean.FALSE;
        if (reqMemberId != null) {
            isMyCLub = clubMemberRepository.findByClubAndMember(recruitment.getClub(), reqMember).isPresent();
            isMyApply = applyRepository.findByMemberAndRecruitment(reqMember, recruitment).isPresent();
        }

        Long myRecentApplyTempId = null;
        if (reqMemberId != null) {
            Optional<ApplyTemp> applyTempOptional = applyTempRepository.findFirstByMemberAndRecruitmentOrderByCreatedDateTimeDesc(reqMember, recruitment);
            if (applyTempOptional.isPresent()) {
                myRecentApplyTempId = applyTempOptional.get().getId();
            }
        }

        return RecruitmentDetailRes.fromEntity(recruitment, bookmarks, reqMember, isMyCLub, isMyApply, myRecentApplyTempId);
    }

    @Transactional
    public void saveRecruitment(Long reqMemberId, Long clubId, RecruitmentSaveReq saveReq, MultipartFile file) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        if (saveReq.getStartDateTime().isAfter(saveReq.getEndDateTime())) {
            throw new StartAfterEndException();
        }

        if (recruitmentRepository.existsDuplicatePeriodRecruitment(club, saveReq.getStartDateTime(), saveReq.getEndDateTime())) {
            throw new ExistsDuplicatePeriodRecruitment();
        }

        ApplyForm applyForm = applyFormRepository.findFirstByClubOrderByCreatedDateTimeDesc(club).orElseThrow(NoApplyFormException::new);
        Recruitment recruitment = saveReq.toEntity(club, applyForm);
        for (RecruitmentNote recruitmentNote : recruitment.getRecruitmentNotes()) {
            recruitmentNote.setRecruitment(recruitment);
        }

        if (file != null) {
            String uri = fileManager.saveFile(file, "recruitment");
            recruitment.setPosterUri(uri);
        }

        recruitmentRepository.save(recruitment);
        // 북마크 동아리 모집 시작시
        List<Member> memberList = clubBookmarkRepository.findAllByClub(club).stream()
                        .map(ClubBookmark::getMember)
                        .toList();

        memberAlarmManger.sendClubBookmarkRecruitmentAlarm(memberList, club.getName(), clubId);

    }

    @Transactional
    public void closeRecruitment(Long reqMemberId, Long recruitmentId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(NotFoundEntityException::new);
        Club club = recruitment.getClub();
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        recruitment.setIsEarlyClosed(Boolean.FALSE);
        // 북마크 모집 마감시
        List<RecruitmentBookmark> recruitmentBookmarkList = recruitmentBookmarkRepository.findAllByRecruitment(recruitment);
        if(!recruitmentBookmarkList.isEmpty()) {
            List<Member> memberList = recruitmentBookmarkList.stream()
                    .map(RecruitmentBookmark::getMember)
                    .toList();
            memberAlarmManger.sendRecruitmentClosed(memberList, recruitment.getTitle());
        }
        // 임시 저장된 지원서에 해당하는 모집이 마감시
        List<ApplyTemp> applyTempList = applyTempRepository.findAllByRecruitment(recruitment);
        if(!applyTempList.isEmpty()){
            List<Member> memberList = applyTempList.stream()
                    .map(ApplyTemp::getMember)
                    .toList();
            memberAlarmManger.sendApplyTempClosed(memberList, recruitment.getTitle());
        }


    }

    @Transactional
    public void removeRecruitment(Long reqMemberId, Long recruitmentId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(NotFoundEntityException::new);
        Club club = recruitment.getClub();
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        recruitmentRepository.delete(recruitment);
    }

    // 관심모집 마감임박 (D-1) && (D-7) 알림
    @Scheduled(cron ="0 0 0 * * ?")
    @Transactional(readOnly = true)
    public void sendApplyTempReminderD1(){

        LocalDateTime d1StartTime = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDateTime d1EndTime = LocalDate.now().plusDays(2).atStartOfDay();  // D-1 범위 (현재+1일 ~ 현재+2일)

        LocalDateTime d7StartTime = LocalDate.now().plusDays(7).atStartOfDay();
        LocalDateTime d7EndTime = LocalDate.now().plusDays(8).atStartOfDay();  // D-7 범위 (현재+7일 ~ 현재+8일)


        List<Recruitment> recruitmentList = recruitmentRepository
                .findAllByWithinRecruitment(LocalDateTime.now(), d1EndTime, d7StartTime, d7EndTime);

        if(recruitmentList.isEmpty()){
            return;
        }

        Map<Integer, List<Club>> recruitmentMap = recruitmentList.stream()
                .collect(Collectors.groupingBy(
                        recruitment -> {
                            LocalDateTime end = recruitment.getEndDateTime();
                            if (end.isAfter(d1StartTime) && end.isBefore(d1EndTime)) {
                                return 1; // D-1
                            } else {
                                return 7; // D-7
                            }
                        },
                        Collectors.mapping(Recruitment::getClub, Collectors.toList())
                ));

        clubAlarmManger.sendRecruitmentReminderD1AndD7(recruitmentMap);

    }

//    public boolean findRecruitmentsExists(Long reqMemberId, Long clubId) {
//        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
//        return recruitmentRepository.findByRecruitmentClosedCheck(clubId);
//    }


}






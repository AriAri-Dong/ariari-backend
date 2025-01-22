package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.commons.exception.exceptions.NoSchoolAuthException;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.file.FileManager;
import com.ariari.ariari.commons.manager.views.ViewsManager;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.exception.NoClubAuthException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.apply.ApplyRepository;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.applyform.ApplyFormRepository;
import com.ariari.ariari.domain.recruitment.applyform.exception.NoApplyFormException;
import com.ariari.ariari.domain.recruitment.bookmark.RecruitmentBookmarkRepository;
import com.ariari.ariari.domain.recruitment.dto.req.RecruitmentSaveReq;
import com.ariari.ariari.domain.recruitment.dto.res.RecruitmentDetailRes;
import com.ariari.ariari.domain.recruitment.exception.ExistsDuplicatePeriodRecruitment;
import com.ariari.ariari.domain.recruitment.exception.StartAfterEndException;
import com.ariari.ariari.domain.recruitment.note.RecruitmentNote;
import com.ariari.ariari.domain.school.School;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Transactional
    public RecruitmentDetailRes findRecruitmentDetail(Long reqMemberId, Long recruitmentId, String clientIp) {
        Member reqMember = null;
        if (reqMemberId != null) {
            reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        }
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(NotFoundEntityException::new);

        School recruitmentSchool = recruitment.getClub().getSchool();
        if (recruitmentSchool != null &&
                (reqMember == null || reqMember.getSchool() == null || !recruitmentSchool.equals(reqMember.getSchool()))) {
            throw new NoSchoolAuthException();
        }

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

        return RecruitmentDetailRes.fromEntity(recruitment, bookmarks, reqMember, isMyCLub, isMyApply);
    }

    @Transactional
    public void saveRecruitment(Long reqMemberId, Long clubId, RecruitmentSaveReq saveReq, MultipartFile file) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        ApplyForm applyForm = applyFormRepository.findFirstByClubOrderByCreatedDateTimeDesc(club).orElseThrow(NoApplyFormException::new);

        if (recruitmentRepository.existsDuplicatePeriodRecruitment(club, saveReq.getStartDateTime(), saveReq.getEndDateTime())) {
            throw new ExistsDuplicatePeriodRecruitment();
        }

        if (saveReq.getStartDateTime().isAfter(saveReq.getEndDateTime())) {
            throw new StartAfterEndException();
        }

        Recruitment recruitment = saveReq.toEntity(club, applyForm);
        for (RecruitmentNote recruitmentNote : recruitment.getRecruitmentNotes()) {
            recruitmentNote.setRecruitment(recruitment);
        }

        if (file != null) {
            String uri = fileManager.saveFile(file, "recruitment");
            recruitment.setPosterUri(uri);
        }

        recruitmentRepository.save(recruitment);
    }

    @Transactional
    public void closeRecruitment(Long reqMemberId, Long recruitmentId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(NotFoundEntityException::new);
        Club club = recruitment.getClub();
        ClubMember clubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        if (clubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        recruitment.setIsActivated(Boolean.FALSE);
    }

    @Transactional
    public void removeRecruitment(Long reqMemberId, Long recruitmentId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(NotFoundEntityException::new);
        Club club = recruitment.getClub();
        ClubMember clubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        if (clubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        recruitmentRepository.delete(recruitment);
    }


}

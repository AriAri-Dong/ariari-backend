package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.file.FileManager;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.clubmember.exception.ExistingClubMemberException;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.recruitment.RecruitmentRepository;
import com.ariari.ariari.domain.recruitment.apply.dto.req.ApplySaveReq;
import com.ariari.ariari.domain.recruitment.apply.dto.res.ApplyDetailRes;
import com.ariari.ariari.domain.recruitment.apply.enums.ApplyStatusType;
import com.ariari.ariari.domain.recruitment.apply.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyService {

    private final MemberRepository memberRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ApplyRepository applyRepository;
    private final FileManager fileManager;

    public ApplyDetailRes findApplyDetail(Long reqMemberId, Long applyId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Apply apply = applyRepository.findById(applyId).orElseThrow(NotFoundEntityException::new);
        Recruitment recruitment = apply.getRecruitment();
        Club club = recruitment.getClub();

        if (!reqMember.equals(apply.getMember())) {
            ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoApplyAuthException::new);
            if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
                throw new NoApplyAuthException();
            }
        }

        return ApplyDetailRes.fromEntity(apply);
    }

    @Transactional
    public void saveApply(Long reqMemberId, Long recruitmentId, ApplySaveReq saveReq, MultipartFile file) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(NotFoundEntityException::new);
        Club club = recruitment.getClub();

        GlobalValidator.eqSchoolAuth(reqMember, club.getSchool());
        GlobalValidator.isOpenRecruitment(recruitment);

        if (clubMemberRepository.findByClubAndMember(recruitment.getClub(), reqMember).isPresent()) {
            throw new AlreadyBelongToClubException();
        }

        if (applyRepository.findByMemberAndRecruitment(reqMember, recruitment).isPresent()) {
            throw new ExistingApplyException();
        }

        Apply apply = saveReq.toEntity(reqMember, recruitment);

        if (file != null) {
            String fileUri = fileManager.saveFile(file, "apply");
            apply.setFileUri(fileUri);
        }

        applyRepository.save(apply);
    }

    @Transactional
    public void approveApply(Long reqMemberId, Long applyId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Apply apply = applyRepository.findById(applyId).orElseThrow(NotFoundEntityException::new);
        Club club = apply.getRecruitment().getClub();
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        if (apply.getApplyStatusType().equals(ApplyStatusType.REFUSAL)) {
            throw new ApplyProcessingException();
        }

        if (clubMemberRepository.findByClubAndMember(club, apply.getMember()).isPresent()) {
            throw new ExistingClubMemberException();
        }

        apply.setApplyStatusType(ApplyStatusType.APPROVE);

        ClubMember clubMember = ClubMember.createGeneral(apply);
        clubMemberRepository.save(clubMember);
    }

    @Transactional
    public void refuseApply(Long reqMemberId, Long applyId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Apply apply = applyRepository.findById(applyId).orElseThrow(NotFoundEntityException::new);
        Club club = apply.getRecruitment().getClub();
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        if (apply.getApplyStatusType().equals(ApplyStatusType.APPROVE)) {
            throw new ApplyProcessingException();
        }

        apply.setApplyStatusType(ApplyStatusType.REFUSAL);
    }

    @Transactional
    public void processApply(Long reqMemberId, Long applyId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Apply apply = applyRepository.findById(applyId).orElseThrow(NotFoundEntityException::new);
        Club club = apply.getRecruitment().getClub();
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        if (!apply.getApplyStatusType().equals(ApplyStatusType.PENDENCY)) {
            throw new ApplyProcessingException();
        }

        apply.setApplyStatusType(ApplyStatusType.INTERVIEW);
    }

    @Transactional
    public void removeApply(Long reqMemberId, Long applyId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Apply apply = applyRepository.findById(applyId).orElseThrow(NotFoundEntityException::new);
        Club club = apply.getRecruitment().getClub();

        if (!reqMember.equals(apply.getMember())) {
            ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoApplyAuthException::new);
            if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
                throw new NoApplyAuthException();
            }
        }

        if (apply.getCreatedDateTime().plusMonths(1).isAfter(LocalDateTime.now())) {
            throw new RemovingApplyException();
        }

        applyRepository.delete(apply);
    }

}

package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.RecruitmentRepository;
import com.ariari.ariari.domain.recruitment.apply.dto.req.ApplySaveReq;
import com.ariari.ariari.domain.recruitment.apply.dto.res.ApplyDetailRes;
import com.ariari.ariari.domain.recruitment.apply.enums.ApplyStatusType;
import com.ariari.ariari.domain.recruitment.apply.exception.*;
import com.ariari.ariari.domain.school.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyService {

    private final MemberRepository memberRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ApplyRepository applyRepository;

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

    @Transactional(readOnly = false)
    public void saveApply(Long reqMemberId, Long recruitmentId, ApplySaveReq saveReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(NotFoundEntityException::new);
        Club club = recruitment.getClub();

        if (club.getSchool() != null) {
            School reqSchool = reqMember.getSchool();
            if (reqSchool == null || !reqSchool.equals(club.getSchool())) {
                throw new SavingApplyException();
            }
        }

        if (recruitment.getIsActivated().equals(false) || recruitment.getEndDateTime().isBefore(LocalDateTime.now())) {
            throw new ClosedRecruitmentException();
        }

        Apply apply = saveReq.toEntity(reqMember, recruitment);
        applyRepository.save(apply);
    }

    @Transactional(readOnly = false)
    public void approveApply(Long reqMemberId, Long applyId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Apply apply = applyRepository.findById(applyId).orElseThrow(NotFoundEntityException::new);
        Club club = apply.getRecruitment().getClub();

        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoApplyAuthException::new);
        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoApplyAuthException();
        }

        if (apply.getApplyStatusType().equals(ApplyStatusType.REFUSAL)) {
            throw new ApplyProcessingException();
        }

        apply.setApplyStatusType(ApplyStatusType.APPROVE);

        ClubMember clubMember = new ClubMember(
                apply.getName(),
                ClubMemberRoleType.GENERAL,
                apply.getMember(),
                club);
        clubMemberRepository.save(clubMember);
    }

    @Transactional(readOnly = false)
    public void refuseApply(Long reqMemberId, Long applyId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Apply apply = applyRepository.findById(applyId).orElseThrow(NotFoundEntityException::new);
        Club club = apply.getRecruitment().getClub();

        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoApplyAuthException::new);
        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoApplyAuthException();
        }

        if (apply.getApplyStatusType().equals(ApplyStatusType.APPROVE)) {
            throw new ApplyProcessingException();
        }

        apply.setApplyStatusType(ApplyStatusType.REFUSAL);
    }

    @Transactional(readOnly = false)
    public void processApply(Long reqMemberId, Long applyId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Apply apply = applyRepository.findById(applyId).orElseThrow(NotFoundEntityException::new);

        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(apply.getRecruitment().getClub(), reqMember).orElseThrow(NoApplyAuthException::new);
        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoApplyAuthException();
        }

        if (!apply.getApplyStatusType().equals(ApplyStatusType.PENDENCY)) {
            throw new ApplyProcessingException();
        }

        apply.setApplyStatusType(ApplyStatusType.INTERVIEW);
    }

    @Transactional(readOnly = false)
    public void removeApply(Long reqMemberId, Long applyId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Apply apply = applyRepository.findById(applyId).orElseThrow(NotFoundEntityException::new);

        if (!apply.getMember().equals(reqMember)) {
            throw new NoApplyAuthException();
        }

        if (apply.getCreatedDateTime().plusMonths(1).isAfter(LocalDateTime.now())) {
            throw new RemovingApplyException();
        }

        applyRepository.delete(apply);
    }

}

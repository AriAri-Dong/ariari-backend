package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.ClubAlarmManger;
import com.ariari.ariari.commons.manager.MemberAlarmManger;
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
import com.ariari.ariari.domain.recruitment.apply.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyService {

    private final MemberRepository memberRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ApplyRepository applyRepository;
    private final FileManager fileManager;
    private final MemberAlarmManger memberAlarmManger;
    private final ClubAlarmManger clubAlarmManger;

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
        clubAlarmManger.sendRecruitmentMember(reqMember, club, recruitment.getTitle());
    }

    @Transactional
    public void approveApplies(Long reqMemberId, List<Long> applyIds) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        List<Apply> applies = applyRepository.findAllByIdsWithClub(applyIds);

        Club club = applies.get(0).getRecruitment().getClub();
        for (Apply apply : applies) {
            if (!apply.getRecruitment().getClub().equals(club)) {
                throw new NotSameClubsAppliesException();
            }
        }

        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);
        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        for (Apply apply : applies) {
            if (apply.getApplyStatusType().equals(ApplyStatusType.REFUSAL)) {
                throw new ApplyProcessingException();
            }

            if (clubMemberRepository.findByClubAndMember(club, apply.getMember()).isPresent()) {
                throw new ExistingClubMemberException();
            }

            apply.setApplyStatusType(ApplyStatusType.APPROVE);

            ClubMember clubMember = ClubMember.createGeneral(apply);
            clubMemberRepository.save(clubMember);
            memberAlarmManger.sendApplyStateAlarm(ApplyStatusType.APPROVE, apply.getMember(), club.getName());
        }
    }

    @Transactional
    public void refuseApply(Long reqMemberId, List<Long> applyIds) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        List<Apply> applies = applyRepository.findAllByIdsWithClub(applyIds);

        Club club = applies.get(0).getRecruitment().getClub();
        for (Apply apply : applies) {
            if (!apply.getRecruitment().getClub().equals(club)) {
                throw new NotSameClubsAppliesException();
            }
        }

        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);
        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        for (Apply apply : applies) {
            if (apply.getApplyStatusType().equals(ApplyStatusType.APPROVE)) {
                throw new ApplyProcessingException();
            }

            apply.setApplyStatusType(ApplyStatusType.REFUSAL);
            memberAlarmManger.sendApplyStateAlarm(ApplyStatusType.REFUSAL, apply.getMember(), club.getName());
        }
    }

    @Transactional
    public void processApply(Long reqMemberId, List<Long> applyIds) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        List<Apply> applies = applyRepository.findAllByIdsWithClub(applyIds);

        Club club = applies.get(0).getRecruitment().getClub();
        for (Apply apply : applies) {
            if (!apply.getRecruitment().getClub().equals(club)) {
                throw new NotSameClubsAppliesException();
            }
        }

        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);
        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        for (Apply apply : applies) {
            if (!apply.getApplyStatusType().equals(ApplyStatusType.PENDENCY)) {
                throw new ApplyProcessingException();
            }

            apply.setApplyStatusType(ApplyStatusType.INTERVIEW);
            memberAlarmManger.sendApplyStateAlarm(ApplyStatusType.INTERVIEW, apply.getMember(), club.getName());
        }
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

    // 매주 월요일 미처리된 지원서가 있을 시
    @Scheduled(cron = "0 0 0 * * MON")
    public void sendUncheckMember(){
        List<Apply> applyList = applyRepository.findApplyByApplyStatusType_Pendency(ApplyStatusType.PENDENCY);

        if(applyList.isEmpty()){
            return;
        }
        // 모집별로 불류
        Map<Long, List<Apply>> groupByRecruitmentId = applyList.stream()
                .collect(Collectors.groupingBy(a -> a.getRecruitment().getId()));

        groupByRecruitmentId.forEach((id, applys) -> {

            String recruitmentTitle = applys.get(0).getRecruitment().getTitle();
            List<Club> clubList = applys.stream().map( apply -> apply.getRecruitment().getClub()).toList();
            clubAlarmManger.sendUncheckMember(clubList, recruitmentTitle);
        });

    }






}

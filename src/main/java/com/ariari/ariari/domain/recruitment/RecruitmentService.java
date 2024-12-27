package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.commons.entitydelete.EntityDeleteManager;
import com.ariari.ariari.commons.exception.exceptions.NoSchoolAuthException;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.S3Manager;
import com.ariari.ariari.commons.manager.views.ViewsManager;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.exception.NoClubAuthException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.applyform.ApplyFormRepository;
import com.ariari.ariari.domain.recruitment.dto.req.RecruitmentSaveReq;
import com.ariari.ariari.domain.recruitment.dto.res.RecruitmentDetailRes;
import com.ariari.ariari.domain.school.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * 1. 상세 조회
 * 2. 등록
 * 3. 수정 -> 마감
 * 4. 삭제
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitmentService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ApplyFormRepository applyFormRepository;
    private final ViewsManager viewsManager;
    private final EntityDeleteManager entityDeleteManager;
    private final S3Manager s3Manager;

    public RecruitmentDetailRes findRecruitmentDetail(Long memberId, Long recruitmentId, String clientIp) {
        Member reqMember = memberRepository.findById(memberId).orElse(null);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(NotFoundEntityException::new);

        School reqSchool = reqMember.getSchool();
        School recruitmentSchool = recruitment.getClub().getSchool();
        if (recruitmentSchool != null && reqSchool != null && !recruitmentSchool.equals(reqSchool)) {
            throw new NoSchoolAuthException();
        }

        if (!viewsManager.checkForDuplicateView(recruitment, clientIp)) {
            viewsManager.addViews(recruitment);
            viewsManager.addClientIp(recruitment, clientIp);
        }

        return RecruitmentDetailRes.fromEntity(recruitment, reqMember);
    }

    @Transactional(readOnly = false)
    public void saveRecruitment(Long memberId, Long clubId, RecruitmentSaveReq saveReq, MultipartFile file) {
        Member reqMember = memberRepository.findById(memberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        ApplyForm applyForm = applyFormRepository.findFirstByClubOrderByCreatedDateTimeDesc(club).orElseThrow(NotFoundEntityException::new);

        Recruitment recruitment = saveReq.toEntity(club, applyForm);

        String uri = s3Manager.uploadImage(file, "recruitment");
        recruitment.setPosterUri(uri);

        recruitmentRepository.save(recruitment);
    }

    @Transactional(readOnly = false)
    public void removeRecruitment(Long memberId, Long recruitmentId) {
        Member reqMember = memberRepository.findById(memberId).orElseThrow(NotFoundEntityException::new);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(NotFoundEntityException::new);
        Club club = recruitment.getClub();
        ClubMember clubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        if (clubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        // 이미지 삭제 처리 필요

        entityDeleteManager.deleteEntity(recruitment);
    }

}

package com.ariari.ariari.domain.recruitment.apply.temp;

import com.ariari.ariari.commons.entitydelete.EntityDeleteManager;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.image.FileManager;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.RecruitmentRepository;
import com.ariari.ariari.domain.recruitment.apply.exception.ClosedRecruitmentException;
import com.ariari.ariari.domain.recruitment.apply.exception.SavingApplyException;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.req.ApplyTempModifyReq;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.req.ApplyTempSaveReq;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.res.ApplyTempDetailRes;
import com.ariari.ariari.domain.recruitment.apply.temp.exception.NoApplyTempAuthException;
import com.ariari.ariari.domain.school.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyTempService {

    private final MemberRepository memberRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ApplyTempRepository applyTempRepository;
    private final EntityDeleteManager entityDeleteManager;
    private final FileManager fileManager;

    public ApplyTempDetailRes findApplyTempDetail(Long reqMemberId, Long applyTempId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ApplyTemp applyTemp = applyTempRepository.findById(applyTempId).orElseThrow(NotFoundEntityException::new);

        if (!reqMember.equals(applyTemp.getMember())) {
            throw new NoApplyTempAuthException();
        }

        return ApplyTempDetailRes.fromEntity(applyTemp);
    }

    @Transactional(readOnly = false)
    public void saveApplyTemp(Long reqMemberId, Long recruitmentId, ApplyTempSaveReq saveReq, MultipartFile file) {
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

        ApplyTemp applyTemp = saveReq.toEntity(reqMember, recruitment);

        // 파일 처리
        if (file != null) {
            String fileUri = fileManager.saveFile(file, "applyTemp");
            applyTemp.setFileUri(fileUri);
        }

        applyTempRepository.save(applyTemp);
    }

    @Transactional(readOnly = false)
    public void modifyApplyTemp(Long reqMemberId, Long applyTempId, ApplyTempModifyReq modifyReq, MultipartFile file) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ApplyTemp applyTemp = applyTempRepository.findById(applyTempId).orElseThrow(NotFoundEntityException::new);

        if (!applyTemp.getMember().equals(reqMember)) {
            throw new NoApplyTempAuthException();
        }

        modifyReq.modifyEntity(applyTemp);

        // 파일 처리 ( 이미 존재하면 삭제하고 재등록 )
        if (file != null) {
            if (applyTemp.getFileUri() != null) {
                fileManager.deleteFile(applyTemp.getFileUri());
            }

            String fileUri = fileManager.saveFile(file, "applyTemp");
            applyTemp.setFileUri(fileUri);
        }

    }

    @Transactional(readOnly = false)
    public void removeApplyTemp(Long reqMemberId, Long applyTempId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ApplyTemp applyTemp = applyTempRepository.findById(applyTempId).orElseThrow(NotFoundEntityException::new);

        if (!applyTemp.getMember().equals(reqMember)) {
            throw new NoApplyTempAuthException();
        }

        // 파일 삭제
        fileManager.deleteFile(applyTemp.getFileUri());

        entityDeleteManager.deleteEntity(applyTemp);
    }

    @Transactional(readOnly = false)
    public void deletePortfolio(Long reqMemberId, Long applyTempId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ApplyTemp applyTemp = applyTempRepository.findById(applyTempId).orElseThrow(NotFoundEntityException::new);

        if (!applyTemp.getMember().equals(reqMember)) {
            throw new NoApplyTempAuthException();
        }

        fileManager.deleteFile(applyTemp.getFileUri());
        applyTemp.setFileUri(null);
    }

}

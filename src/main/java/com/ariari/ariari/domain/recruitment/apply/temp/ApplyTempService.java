package com.ariari.ariari.domain.recruitment.apply.temp;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.file.FileManager;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.recruitment.RecruitmentRepository;
import com.ariari.ariari.domain.recruitment.apply.exception.AlreadyBelongToClubException;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.req.ApplyTempModifyReq;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.req.ApplyTempSaveReq;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.res.ApplyTempDetailRes;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.res.ApplyTempListRes;
import com.ariari.ariari.domain.recruitment.apply.temp.exception.NoApplyTempAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyTempService {

    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ApplyTempRepository applyTempRepository;
    private final FileManager fileManager;

    public ApplyTempDetailRes findApplyTempDetail(Long reqMemberId, Long applyTempId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ApplyTemp applyTemp = applyTempRepository.findById(applyTempId).orElseThrow(NotFoundEntityException::new);

        if (!reqMember.equals(applyTemp.getMember())) {
            throw new NoApplyTempAuthException();
        }

        return ApplyTempDetailRes.fromEntity(applyTemp);
    }

    @Transactional
    public void saveApplyTemp(Long reqMemberId, Long recruitmentId, ApplyTempSaveReq saveReq, MultipartFile file) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(NotFoundEntityException::new);
        Club club = recruitment.getClub();

        GlobalValidator.eqSchoolAuth(reqMember, club.getSchool());
        GlobalValidator.isOpenRecruitment(recruitment);

        if (clubMemberRepository.findByClubAndMember(recruitment.getClub(), reqMember).isPresent()) {
            throw new AlreadyBelongToClubException();
        }

        ApplyTemp applyTemp = saveReq.toEntity(reqMember, recruitment);

        if (file != null) {
            String fileUri = fileManager.saveFile(file, "applyTemp");
            applyTemp.setFileUri(fileUri);
        }

        applyTempRepository.save(applyTemp);
    }

    @Transactional
    public void modifyApplyTemp(Long reqMemberId, Long applyTempId, ApplyTempModifyReq modifyReq, MultipartFile file) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ApplyTemp applyTemp = applyTempRepository.findById(applyTempId).orElseThrow(NotFoundEntityException::new);

        if (!applyTemp.getMember().equals(reqMember)) {
            throw new NoApplyTempAuthException();
        }

        modifyReq.modifyEntity(applyTemp);

        if (applyTemp.getPortfolioUrl() != null && applyTemp.getFileUri() != null) {
            fileManager.deleteFile(applyTemp.getFileUri());
        }

        if (applyTemp.getPortfolioUrl() == null && file != null) {
            if (applyTemp.getFileUri() != null) {
                fileManager.deleteFile(applyTemp.getFileUri());
            }

            String fileUri = fileManager.saveFile(file, "applyTemp");
            applyTemp.setFileUri(fileUri);
        }

    }

    @Transactional
    public void removeApplyTemp(Long reqMemberId, Long applyTempId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ApplyTemp applyTemp = applyTempRepository.findById(applyTempId).orElseThrow(NotFoundEntityException::new);

        if (!applyTemp.getMember().equals(reqMember)) {
            throw new NoApplyTempAuthException();
        }

        if (applyTemp.getFileUri() != null) {
            fileManager.deleteFile(applyTemp.getFileUri());
        }

        applyTempRepository.delete(applyTemp);
    }

    public ApplyTempListRes findMyApplyTemps(Long reqMemberId, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        Page<ApplyTemp> page = applyTempRepository.searchByMember(reqMember, pageable);
        return ApplyTempListRes.fromPage(page);
    }

}

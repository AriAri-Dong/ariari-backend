package com.ariari.ariari.domain.school.auth;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.MailManager;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.school.School;
import com.ariari.ariari.domain.school.SchoolRepository;
import com.ariari.ariari.domain.school.auth.dto.req.SchoolAuthCodeReq;
import com.ariari.ariari.domain.school.auth.dto.req.SchoolAuthReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SchoolAuthService {

    private final MemberRepository memberRepository;
    private final SchoolRepository schoolRepository;
    private final SchoolAuthManager schoolAuthManager;
    private final MailManager mailManager;

    public void sendSchoolAuthCode(Long reqMemberId, SchoolAuthReq schoolAuthReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        String email = schoolAuthReq.getEmail();

        String emailSuffix = email.split("@")[1];
        School school = schoolRepository.findByEmail(emailSuffix).orElseThrow(NotFoundEntityException::new);

        String authCode = schoolAuthManager.issueSchoolAuthCode(reqMember, school);

        mailManager.sendMail(
                email,
                "[Ariari] 학교 인증 번호",
                authCode);
    }

    public void validateSchoolAuthCode(Long reqMemberId, SchoolAuthCodeReq schoolAuthCodeReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        String schoolAuthCode = schoolAuthCodeReq.getSchoolAuthCode();

        Long schoolId = schoolAuthManager.validateAuthCode(reqMember, schoolAuthCode);
        School school = schoolRepository.findById(schoolId).orElseThrow(NotFoundEntityException::new);

        reqMember.setSchool(school);
    }

}

package com.ariari.ariari.domain.school.auth;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.MailManager;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.school.School;
import com.ariari.ariari.domain.school.SchoolRepository;
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

    public void sendSchoolAuthCode(Long reqMemberId, String email) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        String emailSuffix = email.split("@")[1];
        School school = schoolRepository.findByEmail(emailSuffix).orElseThrow(NotFoundEntityException::new);

        String authCode = schoolAuthManager.issueSchoolAuthCode(reqMember, school);

        mailManager.sendMail(
                email,
                "[Ariari] 학교 인증 번호",
                authCode);
    }

    public void validateSchoolAuthCode(Long reqMemberId, String schoolAuthCode) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        Long schoolId = schoolAuthManager.validateAuthCode(reqMember, schoolAuthCode);
        School school = schoolRepository.findById(schoolId).orElseThrow(NotFoundEntityException::new);

        reqMember.setSchool(school);
    }

}

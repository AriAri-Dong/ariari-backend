package com.ariari.ariari.domain.school.auth;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.MailManager;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import com.ariari.ariari.domain.school.School;
import com.ariari.ariari.domain.school.auth.exceptions.ClubMemberRoleAdminLockException;
import com.ariari.ariari.domain.school.school.SchoolRepository;
import com.ariari.ariari.domain.school.auth.dto.req.SchoolAuthCodeReq;
import com.ariari.ariari.domain.school.auth.dto.req.SchoolAuthReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SchoolAuthService {

    private final MemberRepository memberRepository;
    private final SchoolRepository schoolRepository;
    private final SchoolAuthManager schoolAuthManager;
    private final MailManager mailManager;
    private final ClubMemberRepository clubMemberRepository;

    public void sendSchoolAuthCode(Long reqMemberId, SchoolAuthReq schoolAuthReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        String email = schoolAuthReq.getEmail();

        String emailSuffix = email.split("@")[1];
        School school = schoolRepository.findByEmail(emailSuffix).orElseThrow(NotFoundEntityException::new);

        String authCode = schoolAuthManager.issueSchoolAuthCode(reqMember, school);

        mailManager.sendTemplateMail(
                email,
                "[Ariari] 학교 인증 번호",
                authCode);
    }

    public void sendSchoolAuthCodeForFirstLogin(SchoolAuthReq schoolAuthReq) {
        String email = schoolAuthReq.getEmail();

        String emailSuffix = email.split("@")[1];
        School school = schoolRepository.findByEmail(emailSuffix).orElseThrow(NotFoundEntityException::new);

        String authCode = schoolAuthManager.issueSchoolAuthCode(schoolAuthReq.getEmail());

        mailManager.sendTemplateMail(
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

    public void validateSchoolAuthCode(Member member, String email, String schoolAuthCode) {
        schoolAuthManager.validateAuthCode(email, schoolAuthCode);

        String schoolDomain = email.substring(email.indexOf('@') + 1);

        School school = schoolRepository.findByEmail(schoolDomain).orElseThrow(NotFoundEntityException::new);

        member.setSchool(school);
    }

    public void cancelMySchoolAuth(Long reqMemberId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        School school = reqMember.getSchool();

        List<ClubMember> clubMembers = reqMember.getClubMembers();
        for (ClubMember clubMember : clubMembers) {
            School clubSchool = clubMember.getClub().getSchool();
            if (clubSchool == null || !clubSchool.equals(school)) {
                continue;
            }

            if (clubMember.getClubMemberRoleType() == ClubMemberRoleType.ADMIN) {
                throw new ClubMemberRoleAdminLockException();
            }

            clubMemberRepository.delete(clubMember);
        }

        reqMember.setSchool(null);
    }

}

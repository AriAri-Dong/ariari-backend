package com.ariari.ariari.commons.validator;

import com.ariari.ariari.commons.exception.exceptions.NoSchoolAuthException;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.clubmember.exception.DelegateAdminException;
import com.ariari.ariari.domain.club.clubmember.exception.LowerRoleTypeException;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.club.exceptions.NoClubAdminException;
import com.ariari.ariari.domain.club.exceptions.NoClubManagerException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.apply.exceptions.ClosedRecruitmentException;
import com.ariari.ariari.domain.school.School;
import com.ariari.ariari.domain.school.exceptions.NoProperSchoolAuthException;

public class GlobalValidator {

    public static void hasSchoolAuth(Member member) {
        if (member == null || member.getSchool() == null) {
            throw new NoSchoolAuthException();
        }
    }

    public static void eqSchoolAuth(Member member, School school) {
        if (school == null) {
            return;
        }

        if (member == null || member.getSchool() == null || !member.getSchool().equals(school)) {
            throw new NoProperSchoolAuthException();
        }
    }

    public static void isClubManagerOrHigher(ClubMember reqClubMember) {
        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubManagerException();
        }
    }

    public static void isClubAdmin(ClubMember reqClubMember) {
        if (!reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.ADMIN)) {
            throw new NoClubAdminException();
        }
    }

    public static void isHigherRoleTypeThan(ClubMember my, ClubMember he) {
        if (!my.isHigherRoleTypeThan(he)) {
            throw new LowerRoleTypeException();
        }
    }

    public static void belongsToClub(ClubMember clubMember, Club club) {
        if (!clubMember.getClub().equals(club)) {
            throw new NotBelongInClubException();
        }
    }

    public static void isOpenRecruitment(Recruitment recruitment) {
        if (!recruitment.isRecruiting()) {
            throw new ClosedRecruitmentException();
        }
    }

    public static void isClubMemberAdmin(ClubMember clubMember) {
        if(clubMember.getClubMemberRoleType().equals(ClubMemberRoleType.ADMIN)) {
            throw new DelegateAdminException();
        }
    }



}

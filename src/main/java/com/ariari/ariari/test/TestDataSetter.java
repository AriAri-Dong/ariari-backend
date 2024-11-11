package com.ariari.ariari.test;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubbookmark.ClubBookmark;
import com.ariari.ariari.domain.club.clubbookmark.ClubBookmarkRepository;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.clubmember.ClubMember;
import com.ariari.ariari.domain.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.RecruitmentRepository;
import com.ariari.ariari.domain.school.School;
import com.ariari.ariari.domain.school.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class TestDataSetter {

    private final MemberRepository memberRepository;
    private final SchoolRepository schoolRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubBookmarkRepository clubBookmarkRepository;
    private final RecruitmentRepository recruitmentRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initTestData() {
        // school
        School school1 = new School("세종대학교");
        School school2 = new School("두종대학교");
        School school3 = new School("네종대학교");
        schoolRepository.saveAll(List.of(school1, school2, school3));

        // member
        Member admin = Member.createMember(null);
        admin.addAuthority(new SimpleGrantedAuthority("ROLE_MANAGER"));
        admin.addAuthority(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Member member1 = Member.createMember(null);
        member1.setNickName("haus");
        member1.setSchool(school1);
        memberRepository.saveAll(List.of(admin, member1));

        // club
        Club club1 = new Club("club1", "intro", ClubCategoryType.AMITY, null);
        Club club2 = new Club("club2", "intro", ClubCategoryType.AMITY, null);
        Club club3 = new Club("club3", "intro", ClubCategoryType.AMITY, null);
        Club club4 = new Club("club4", "intro", ClubCategoryType.SPORTS, null);
        Club club5 = new Club("club5", "intro", ClubCategoryType.STARTUP, null);
        Club club6 = new Club("club6", "intro", ClubCategoryType.EMPLOYMENT, null);
        Club club7 = new Club("club7", "intro", ClubCategoryType.AMITY, school1);
        Club club8 = new Club("club8", "intro", ClubCategoryType.AMITY, school1);
        Club club9 = new Club("club9", "intro", ClubCategoryType.AMITY, school1);
        Club club10 = new Club("club10", "intro", ClubCategoryType.SPORTS, school1);
        Club club11 = new Club("club11", "intro", ClubCategoryType.STARTUP, school1);
        Club club12 = new Club("club12", "intro", ClubCategoryType.EMPLOYMENT, school1);
        clubRepository.saveAll(List.of(club1, club2, club3, club4, club5, club6, club7, club8, club9, club10, club11, club12));

        club3.addViews(4);
        club2.addViews(1);
        club6.addViews(3);

        // clubMember
        ClubMember clubMember1 = ClubMember.builder()
                .club(club1)
                .member(member1)
                .clubMemberRoleType(ClubMemberRoleType.ADMIN)
                .build();
        ClubMember clubMember2 = ClubMember.builder()
                .club(club4)
                .member(member1)
                .clubMemberRoleType(ClubMemberRoleType.ADMIN)
                .build();
        ClubMember clubMember3 = ClubMember.builder()
                .club(club12)
                .member(member1)
                .clubMemberRoleType(ClubMemberRoleType.ADMIN)
                .build();
        clubMemberRepository.saveAll(List.of(clubMember1, clubMember2, clubMember3));

        // clubBookmark
        ClubBookmark clubBookmark1 = new ClubBookmark(member1, club1);
        ClubBookmark clubBookmark2 = new ClubBookmark(member1, club2);
        ClubBookmark clubBookmark3 = new ClubBookmark(member1, club3);
        ClubBookmark clubBookmark4 = new ClubBookmark(member1, club7);
        ClubBookmark clubBookmark5 = new ClubBookmark(member1, club8);
        clubBookmarkRepository.saveAll(List.of(clubBookmark1, clubBookmark2, clubBookmark3, clubBookmark4, clubBookmark5));

        // recruitment
        Recruitment recruitment1 = new Recruitment("recruitment1", "body1", club1);
        Recruitment recruitment2 = new Recruitment("recruitment2", "body2", club2);
        Recruitment recruitment3 = new Recruitment("recruitment3", "body3", club3);
        Recruitment recruitment4 = new Recruitment("recruitment4", "body4", club4);
        Recruitment recruitment5 = new Recruitment("recruitment5", "body5", club5);
        Recruitment recruitment6 = new Recruitment("recruitment6", "body6", club6);
        Recruitment recruitment7 = new Recruitment("recruitment7", "body7", club7);
        Recruitment recruitment8 = new Recruitment("recruitment8", "body8", club8);
        Recruitment recruitment9 = new Recruitment("recruitment9", "body9", club9);
        Recruitment recruitment10 = new Recruitment("recruitment10", "body10", club10);
        Recruitment recruitment11 = new Recruitment("recruitment11", "body11", club11);
        Recruitment recruitment12 = new Recruitment("recruitment12", "body12", club12);
        recruitmentRepository.saveAll(List.of(recruitment1, recruitment2, recruitment3, recruitment4, recruitment5, recruitment6, recruitment7, recruitment8, recruitment9, recruitment10, recruitment11, recruitment12));

    }

}

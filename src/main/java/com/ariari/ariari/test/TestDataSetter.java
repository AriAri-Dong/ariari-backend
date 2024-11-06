package com.ariari.ariari.test;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
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

    @EventListener(ApplicationReadyEvent.class)
    public void initTestData() {
        // School
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
    }

}

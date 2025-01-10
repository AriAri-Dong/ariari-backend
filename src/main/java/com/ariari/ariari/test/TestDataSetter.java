package com.ariari.ariari.test;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.bookmark.ClubBookmark;
import com.ariari.ariari.domain.club.bookmark.ClubBookmarkRepository;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.enums.ClubRegionType;
import com.ariari.ariari.domain.club.enums.ParticipantType;
import com.ariari.ariari.domain.club.financial.FinancialRecord;
import com.ariari.ariari.domain.club.financial.FinancialRecordRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.RecruitmentRepository;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.apply.ApplyRepository;
import com.ariari.ariari.domain.recruitment.apply.answer.ApplyAnswer;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.applyform.ApplyFormRepository;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import com.ariari.ariari.domain.recruitment.bookmark.RecruitmentBookmark;
import com.ariari.ariari.domain.recruitment.bookmark.RecruitmentBookmarkRepository;
import com.ariari.ariari.domain.recruitment.enums.ProcedureType;
import com.ariari.ariari.domain.recruitment.note.RecruitmentNote;
import com.ariari.ariari.domain.school.School;
import com.ariari.ariari.domain.school.SchoolRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
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
    private final ApplyFormRepository applyFormRepository;
    private final RecruitmentBookmarkRepository recruitmentBookmarkRepository;
    private final ApplyRepository applyRepository;
    private final FinancialRecordRepository financialRecordRepository;

    private final EntityManager em;

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
        Member m1 = Member.createMember(null);
        m1.setNickName("m1");
        m1.setSchool(school1);
        Member m2 = Member.createMember(null);
        m2.setNickName("m2");
        m2.setSchool(school1);
        Member m3 = Member.createMember(null);
        m3.setNickName("m3");
        m3.setSchool(school1);
        Member m4 = Member.createMember(null);
        m4.setNickName("m4");
        m4.setSchool(school1);
        Member m5 = Member.createMember(null);
        m5.setNickName("m5");
        m5.setSchool(school1);
        Member m6 = Member.createMember(null);
        m6.setNickName("m6");
        m6.setSchool(school1);
        Member m7 = Member.createMember(null);
        m7.setNickName("m7");
        m7.setSchool(school2);
        Member m8 = Member.createMember(null);
        m8.setNickName("m8");
        memberRepository.saveAll(List.of(admin, m1, m2, m3, m4, m5, m6, m7, m8));

        // club
        Club c1 = new Club("c1", "intro", ClubCategoryType.AMITY, ClubRegionType.SEOUL_GYEONGGI, ParticipantType.UNIVERSITY_STUDENT, school1);
        Club c2 = new Club("c2", "intro", ClubCategoryType.AMITY, ClubRegionType.SEOUL_GYEONGGI, ParticipantType.UNIVERSITY_STUDENT, school1);
        Club c3 = new Club("c3", "intro", ClubCategoryType.AMITY, ClubRegionType.SEOUL_GYEONGGI, ParticipantType.UNIVERSITY_STUDENT, school1);
        Club c4 = new Club("c4", "intro", ClubCategoryType.SPORTS, ClubRegionType.SEOUL_GYEONGGI, ParticipantType.UNIVERSITY_STUDENT, school1);
        Club c5 = new Club("c5", "intro", ClubCategoryType.STARTUP, ClubRegionType.SEOUL_GYEONGGI, ParticipantType.UNIVERSITY_STUDENT, school1);
        Club c6 = new Club("c6", "intro", ClubCategoryType.EMPLOYMENT, ClubRegionType.SEOUL_GYEONGGI, ParticipantType.UNIVERSITY_STUDENT, school1);
        Club c7 = new Club("c7", "intro", ClubCategoryType.AMITY, ClubRegionType.JEONNAM, ParticipantType.UNIVERSITY_STUDENT, school2);
        Club c8 = new Club("c8", "intro", ClubCategoryType.AMITY, ClubRegionType.JEONNAM, ParticipantType.UNIVERSITY_STUDENT, school2);
        Club c9 = new Club("c9", "intro", ClubCategoryType.AMITY, ClubRegionType.SEOUL_GYEONGGI, ParticipantType.OFFICE_WORKER, null);
        Club c10 = new Club("c10", "intro", ClubCategoryType.SPORTS, ClubRegionType.CHUNGCHEONG, ParticipantType.OFFICE_WORKER, null);
        Club c11 = new Club("c11", "intro", ClubCategoryType.STARTUP, ClubRegionType.GYEONGNAM, ParticipantType.OFFICE_WORKER, null);
        Club c12 = new Club("c12", "intro", ClubCategoryType.EMPLOYMENT, ClubRegionType.JEJU, ParticipantType.OFFICE_WORKER, null);
        clubRepository.saveAll(List.of(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12));

        c3.addViews(4);
        c2.addViews(1);
        c6.addViews(3);

        // clubMember
        ClubMember cm1_1 = new ClubMember("cm1_1", ClubMemberRoleType.ADMIN, m1, c1);
        ClubMember cm1_2 = new ClubMember("cm1_2", ClubMemberRoleType.MANAGER, m2, c1);
        ClubMember cm1_3 = new ClubMember("cm1_3", ClubMemberRoleType.GENERAL, m3, c1);
        ClubMember cm1_4 = new ClubMember("cm1_4", ClubMemberRoleType.GENERAL, m4, c1);

        ClubMember cm2_1 = new ClubMember("cm2_1", ClubMemberRoleType.GENERAL, m1, c2);
        ClubMember cm2_2 = new ClubMember("cm2_2", ClubMemberRoleType.ADMIN, m2, c2);
        ClubMember cm2_3 = new ClubMember("cm2_3", ClubMemberRoleType.GENERAL, m3, c2);

        ClubMember cm3_1 = new ClubMember("clubMember3_1", ClubMemberRoleType.ADMIN, m1, c3);
        ClubMember cm4_1 = new ClubMember("clubMember4_1", ClubMemberRoleType.ADMIN, m1, c4);
        ClubMember cm5_1 = new ClubMember("clubMember5_1", ClubMemberRoleType.GENERAL, m1, c5);
        ClubMember cm6_1 = new ClubMember("clubMember6_1", ClubMemberRoleType.GENERAL, m1, c6);

        ClubMember cm7_1 = new ClubMember("clubMember7_1", ClubMemberRoleType.ADMIN, m7, c7);
        ClubMember cm8_1 = new ClubMember("clubMember8_1", ClubMemberRoleType.ADMIN, m7, c8);

        ClubMember cm9_1 = new ClubMember("clubMember9_1", ClubMemberRoleType.ADMIN, m1, c9);
        ClubMember cm10_1 = new ClubMember("clubMember10_1", ClubMemberRoleType.ADMIN, m1, c10);
        ClubMember cm11_1 = new ClubMember("clubMember11_1", ClubMemberRoleType.GENERAL, m1, c11);
        ClubMember cm12_1 = new ClubMember("clubMember12_1", ClubMemberRoleType.GENERAL, m1, c12);
        clubMemberRepository.saveAll(List.of(cm1_1, cm1_2, cm1_3, cm1_4, cm2_1, cm2_2, cm2_3, cm3_1, cm4_1, cm5_1, cm6_1, cm7_1, cm8_1, cm9_1, cm10_1, cm11_1, cm12_1));

        // clubBookmark
        ClubBookmark cb1 = new ClubBookmark(m1, c1);
        ClubBookmark cb2 = new ClubBookmark(m1, c2);
        ClubBookmark cb3 = new ClubBookmark(m1, c3);
        ClubBookmark cb4 = new ClubBookmark(m1, c4);
        ClubBookmark cb5 = new ClubBookmark(m1, c11);
        ClubBookmark cb6 = new ClubBookmark(m1, c12);
        ClubBookmark cb7 = new ClubBookmark(m2, c8);
        ClubBookmark cb8 = new ClubBookmark(m2, c12);
        clubBookmarkRepository.saveAll(List.of(cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8));

        // applyForm
        ApplyQuestion aq1_1 = new ApplyQuestion("aq1_1");
        ApplyQuestion aq1_2 = new ApplyQuestion("aq1_2");
        ApplyQuestion aq1_3 = new ApplyQuestion("aq1_3");
        ApplyForm af1 = new ApplyForm(c1, List.of(aq1_1, aq1_2, aq1_3));
        aq1_1.setApplyForm(af1);
        aq1_2.setApplyForm(af1);
        aq1_3.setApplyForm(af1);
        ApplyQuestion aq2_1 = new ApplyQuestion("aq2_1");
        ApplyQuestion aq2_2 = new ApplyQuestion("aq2_2");
        ApplyQuestion aq2_3 = new ApplyQuestion("aq2_3");
        ApplyForm af2 = new ApplyForm(c2, List.of(aq2_1, aq2_2, aq2_3));
        aq2_1.setApplyForm(af2);
        aq2_2.setApplyForm(af2);
        aq2_3.setApplyForm(af2);
        ApplyQuestion aq3_1 = new ApplyQuestion("aq3_1");
        ApplyQuestion aq3_2 = new ApplyQuestion("aq3_2");
        ApplyQuestion aq3_3 = new ApplyQuestion("aq3_3");
        ApplyForm af3 = new ApplyForm(c9, List.of(aq3_1, aq3_2, aq3_3));
        aq3_1.setApplyForm(af3);
        aq3_2.setApplyForm(af3);
        aq3_3.setApplyForm(af3);
        ApplyQuestion aq4_1 = new ApplyQuestion("aq4_1");
        ApplyQuestion aq4_2 = new ApplyQuestion("aq4_2");
        ApplyQuestion aq4_3 = new ApplyQuestion("aq4_3");
        ApplyForm af4 = new ApplyForm(c10, List.of(aq4_1, aq4_2, aq4_3));
        aq4_1.setApplyForm(af4);
        aq4_2.setApplyForm(af4);
        aq4_3.setApplyForm(af4);
        applyFormRepository.saveAll(List.of(af1, af2, af3, af4));

        // recruitment
        RecruitmentNote rn1_1 = new RecruitmentNote("question1", "answer1");
        RecruitmentNote rn1_2 = new RecruitmentNote("question2", "answer2");
        Recruitment r1 = new Recruitment("r1", "body1", ProcedureType.DOCUMENT, 10, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), c1, af1, List.of(rn1_1, rn1_2));
        rn1_1.setRecruitment(r1);
        rn1_2.setRecruitment(r1);
        RecruitmentNote rn2_1 = new RecruitmentNote("question1", "answer1");
        RecruitmentNote rn2_2 = new RecruitmentNote("question2", "answer2");
        Recruitment r2 = new Recruitment("r2", "body2", ProcedureType.DOCUMENT, 10, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), c2, af2, List.of(rn2_1, rn2_2));
        rn2_1.setRecruitment(r2);
        rn2_2.setRecruitment(r2);
        Recruitment r3 = new Recruitment("r3", "body3", c3);
        Recruitment r4 = new Recruitment("r4", "body4", c4);
        Recruitment r5 = new Recruitment("r5", "body5", c5);
        Recruitment r6 = new Recruitment("r6", "body6", c6);
        Recruitment r7 = new Recruitment("r7", "body7", c7);
        Recruitment r8 = new Recruitment("r8", "body8", c8);
        RecruitmentNote rn9_1 = new RecruitmentNote("q1", "a1");
        RecruitmentNote rn9_2 = new RecruitmentNote("q2", "a2");
        Recruitment r9 = new Recruitment("r9", "body9", ProcedureType.INTERVIEW, 20, LocalDateTime.now(), LocalDateTime.now().plusMonths(2), c9, af3, List.of(rn9_1, rn9_2));
        rn9_1.setRecruitment(r9);
        rn9_2.setRecruitment(r9);
        RecruitmentNote rn10_1 = new RecruitmentNote("q1", "a1");
        RecruitmentNote rn10_2 = new RecruitmentNote("q2", "a2");
        Recruitment r10 = new Recruitment("r10", "body10", ProcedureType.INTERVIEW, 30, LocalDateTime.now(), LocalDateTime.now().plusMonths(3), c10, af4, List.of(rn10_1, rn10_2));
        rn10_1.setRecruitment(r10);
        rn10_2.setRecruitment(r10);
        Recruitment r11 = new Recruitment("r11", "body11", ProcedureType.DOCUMENT, 50, LocalDateTime.now(), LocalDateTime.now().plusMonths(3), c11, null, null);
        Recruitment r12 = new Recruitment("r12", "body12", ProcedureType.DOCUMENT, 50, LocalDateTime.now(), LocalDateTime.now().plusMonths(3), c12, null, null);
        Recruitment r13 = new Recruitment("r13", "body12", ProcedureType.DOCUMENT, 50, LocalDateTime.now(), LocalDateTime.now().plusMonths(3), c1, null, null);
        Recruitment r14 = new Recruitment("r14", "body12", ProcedureType.DOCUMENT, 50, LocalDateTime.now(), LocalDateTime.now().plusMonths(3), c1, null, null);
        Recruitment r15 = new Recruitment("r15", "body12", ProcedureType.DOCUMENT, 50, LocalDateTime.now(), LocalDateTime.now().plusMonths(3), c1, null, null);
        recruitmentRepository.saveAll(List.of(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15));

        // recruitmentBookmark
        RecruitmentBookmark rb1 = new RecruitmentBookmark(r1, m1);
        RecruitmentBookmark rb2 = new RecruitmentBookmark(r2, m1);
        RecruitmentBookmark rb3 = new RecruitmentBookmark(r3, m1);
        RecruitmentBookmark rb4 = new RecruitmentBookmark(r9, m1);
        recruitmentBookmarkRepository.saveAll(List.of(rb1, rb2, rb3, rb4));

        // apply
        ApplyAnswer aa1_1_1 = new ApplyAnswer("aa1_1_1", aq1_1);
        ApplyAnswer aa1_2_1 = new ApplyAnswer("aa1_2_1", aq1_2);
        ApplyAnswer aa1_3_1 = new ApplyAnswer("aa1_3_1", aq1_3);
        Apply a1 = new Apply("a1", "portfolioUri1", m5, r1, List.of(aa1_1_1, aa1_2_1, aa1_3_1));
        aa1_1_1.setApply(a1);
        aa1_2_1.setApply(a1);
        aa1_3_1.setApply(a1);
        ApplyAnswer aa1_1_2 = new ApplyAnswer("aa1_1_2", aq1_1);
        ApplyAnswer aa1_2_2 = new ApplyAnswer("aa1_2_2", aq1_2);
        ApplyAnswer aa1_3_2 = new ApplyAnswer("aa1_3_2", aq1_3);
        Apply a2 = new Apply("a2", "portfolioUri2", m6, r1, List.of(aa1_1_2, aa1_2_2, aa1_3_2));
        aa1_1_2.setApply(a1);
        aa1_2_2.setApply(a1);
        aa1_3_2.setApply(a1);
        ApplyAnswer aa2_1_1 = new ApplyAnswer("aa2_1_1", aq2_1);
        ApplyAnswer aa2_2_1 = new ApplyAnswer("aa2_2_1", aq2_2);
        ApplyAnswer aa2_3_1 = new ApplyAnswer("aa2_3_1", aq2_3);
        Apply a3 = new Apply("a3", "portfolioUri3", m5, r2, List.of(aa2_1_1, aa2_2_1, aa2_3_1));
        aa2_1_1.setApply(a3);
        aa2_2_1.setApply(a3);
        aa2_3_1.setApply(a3);
        ApplyAnswer aa2_1_2 = new ApplyAnswer("aa2_1_2", aq2_1);
        ApplyAnswer aa2_2_2 = new ApplyAnswer("aa2_2_2", aq2_2);
        ApplyAnswer aa2_3_2 = new ApplyAnswer("aa2_3_2", aq2_3);
        Apply a4 = new Apply("a4", "portfolioUri4", m6, r2, List.of(aa2_1_2, aa2_2_2, aa2_3_2));
        aa2_1_2.setApply(a4);
        aa2_2_2.setApply(a4);
        aa2_3_2.setApply(a4);
        ApplyAnswer aa3_1_1 = new ApplyAnswer("aa3_1_1", aq3_1);
        ApplyAnswer aa3_2_1 = new ApplyAnswer("aa3_2_1", aq3_2);
        ApplyAnswer aa3_3_1 = new ApplyAnswer("aa3_3_1", aq3_3);
        Apply a5 = new Apply("a5", "portfolioUri5", m1, r9, List.of(aa3_1_1, aa3_2_1, aa3_3_1));
        aa3_1_1.setApply(a5);
        aa3_2_1.setApply(a5);
        aa3_3_1.setApply(a5);
        ApplyAnswer aa4_1_1 = new ApplyAnswer("aa4_1_1", aq4_1);
        ApplyAnswer aa4_2_1 = new ApplyAnswer("aa4_2_1", aq4_2);
        ApplyAnswer aa4_3_1 = new ApplyAnswer("aa4_3_1", aq4_3);
        Apply a6 = new Apply("a6", "portfolioUri6", m1, r10, List.of(aa4_1_1, aa4_2_1, aa4_3_1));
        aa4_1_1.setApply(a6);
        aa4_2_1.setApply(a6);
        aa4_3_1.setApply(a6);
        applyRepository.saveAll(List.of(a1, a2, a3, a4, a5, a6));

        // financial-record
        FinancialRecord fr1 = new FinancialRecord(10000L, "body1", LocalDateTime.now().minusMinutes(8), c1);
        FinancialRecord fr2 = new FinancialRecord(40000L, "body2", LocalDateTime.now().minusMinutes(7), c1);
        FinancialRecord fr3 = new FinancialRecord(50000L, "body3", LocalDateTime.now().minusMinutes(6), c1);
        FinancialRecord fr4 = new FinancialRecord(-30000L, "body4", LocalDateTime.now().minusMinutes(5), c1);
        FinancialRecord fr5 = new FinancialRecord(50000L, "body5", LocalDateTime.now().minusMinutes(4), c1);
        FinancialRecord fr6 = new FinancialRecord(50000L, "body6", LocalDateTime.now().minusMinutes(3), c1);
        FinancialRecord fr7 = new FinancialRecord(50000L, "body7", LocalDateTime.now().minusMinutes(2), c1);
        FinancialRecord fr8 = new FinancialRecord(50000L, "body8", LocalDateTime.now().minusMinutes(1), c1);
        financialRecordRepository.saveAll(List.of(fr1, fr2, fr3, fr4, fr5, fr6, fr7, fr8));

    }

}

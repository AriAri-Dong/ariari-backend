package com.ariari.ariari.domain.club.clubmember;

import com.ariari.ariari.commons.entitydelete.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityComment;
import com.ariari.ariari.domain.club.event.attendance.Attendance;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import com.ariari.ariari.domain.club.faq.ClubFaq;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.club.question.answer.ClubAnswer;
import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class ClubMember implements LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "club_member_id")
    private Long id;

    @Column(length = 20)
    private String name;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ClubMemberRoleType clubMemberRoleType;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ClubMemberStatusType clubMemberStatusType = ClubMemberStatusType.ACTIVE;

    @CreationTimestamp
    private LocalDateTime createdDateTime;
    private LocalDateTime deletedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "clubMember")
    private List<ClubAnswer> clubAnswers;

    @OneToMany(mappedBy = "clubMember")
    private List<ClubReview> clubReviews;

    @OneToMany(mappedBy = "clubMember")
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "clubMember")
    private List<PassReview> passReviews;

    @OneToMany(mappedBy = "clubMember")
    private List<ClubActivityComment> clubActivityComments;

    @OneToMany(mappedBy = "clubMember")
    private List<ClubNotice> clubNotices;

    @OneToMany(mappedBy = "clubMember")
    private List<ClubFaq> clubFaqs;

    @OneToMany(mappedBy = "clubMember")
    private List<ClubActivity> clubActivitys;

    public ClubMember(String name, ClubMemberRoleType clubMemberRoleType, Member member, Club club) {
        this.name = name;
        this.clubMemberRoleType = clubMemberRoleType;
        this.member = member;
        this.club = club;
    }

    @Override
    public void deleteLogically() {
        this.deletedDateTime = LocalDateTime.now();
    }

}
